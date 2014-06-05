package fossasia.valentina.bodyapp.main;

import java.io.Serializable;

import fossasia.valentina.bodyapp.managers.MeasurementManager;
import fossasia.valentina.bodyapp.managers.PersonManager;
import fossasia.valentina.bodyapp.managers.UserManager;
import fossasia.valentina.bodyapp.models.Measurement;
import fossasia.valentina.bodyapp.models.Person;
import fossasia.valentina.bodyapp.models.User;
import fossasia.valentina.bodyapp.sync.SyncMeasurement;
import fossasia.valentina.bodyapp.sync.SyncUser;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.os.Build;

public class MeasurementActivity extends Activity {
	private static Measurement measurement;
	private static Person person;
	private static String mID;

	private static EditText mid_neck_girth;
	private static EditText bust_girth;
	private static EditText waist_girth;
	private static EditText hip_girth;
	private static EditText across_back_shoulder_width;
	private static EditText shoulder_drop;
	private static EditText shoulder_slope_degrees;
	private static EditText arm_length;
	private static EditText upper_arm_girth;
	private static EditText armscye_girth;
	private static EditText height;
	private static EditText hip_height;
	private static EditText wrist_girth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measurement);
		final Serializable extra = this.getIntent().getSerializableExtra(
				"measurement");
		measurement = (Measurement) extra;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.measurement, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class GridFragment extends Fragment {

		Boolean dualPane;
		int shownIndex = 0;

		final static int HEAD = 0;
		final static int NECK = 1;
		final static int SHOULDER = 2;
		final static int CHEST = 3;
		final static int ARM = 4;
		final static int HAND = 5;
		final static int HIP_AND_WAIST = 6;
		final static int LEG = 7;
		final static int FOOT = 8;
		final static int TRUNK = 9;
		final static int HEIGHTS = 10;
		final static int PICS = 11;

		private Button btnSave;
		private Button btnSaveSync;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_measurement,
					container, false);
			GridView gridView = (GridView) rootView
					.findViewById(R.id.grid_view);
			gridView.setAdapter(new GridAdapter(rootView.getContext()));

			if (savedInstanceState != null) {
				// Restore last state for checked position.
				shownIndex = savedInstanceState.getInt("shownIndex", 0);
			}

			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int index, long id) {

					shownIndex = index;
					viewSet(view);
				}
			});

			btnSave = (Button) rootView.findViewById(R.id.measurement_btn_save);
			btnSave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DBSaver(v.getContext());
					// Intent intent=new Intent(v.getContext(),
					// MainActivity.class);
					// startActivity(intent);
					Activity host = (Activity) v.getContext();
					host.finish();
				}
			});
			
			btnSaveSync=(Button)rootView.findViewById(R.id.measurement_btn_save_sync);
			btnSaveSync.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DBSaver(v.getContext());
					// Intent intent=new Intent(v.getContext(),
					// MainActivity.class);
					// startActivity(intent);
					person=PersonManager.getInstance(v.getContext()).getPersonbyID(measurement.getPersonID());
					System.out.println(person.getName());
					new HttpAsyncTaskMeasurement().execute("http://192.168.1.2:8020/user/measurements");
					Activity host = (Activity) v.getContext();
					host.finish();
				}
			});

			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			View measurement = getActivity().findViewById(
					R.id.measurement_frame);
			dualPane = measurement != null
					&& measurement.getVisibility() == View.VISIBLE;
			if (dualPane) {

				System.out.println(shownIndex + "a");
				viewSet(getView());

			}

		}

		public void viewSet(View view) {

			if (dualPane) {
				Fragment item = (Fragment) getActivity().getFragmentManager()
						.findFragmentById(R.id.item_container);
				if (item == null) {
					System.out.println("if");
					item = chooseView(shownIndex);
					FragmentTransaction ft = getActivity().getFragmentManager()
							.beginTransaction();
					ft.replace(R.id.measurement_frame, item);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}
			} else {
				System.out.println("else");
				Intent editnote = new Intent(view.getContext(),
						ItemActivity.class);
				editnote.putExtra("item", shownIndex);
				startActivity(editnote);
			}
		}

		public static Fragment chooseView(int index) {
			Fragment fragment = null;
			switch (index) {
			case HEAD:
				fragment = new Head();
				return fragment;
			case NECK:
				fragment = new Neck();
				return fragment;
			case SHOULDER:
				fragment = new Shoulder();
				return fragment;
			case CHEST:
				fragment = new Chest();
				return fragment;
			case ARM:
				fragment = new Arm();
				return fragment;
			case HAND:
				fragment = new Hand();
				return fragment;
			case HIP_AND_WAIST:
				fragment = new HipAndWaist();
				return fragment;
			case LEG:
				fragment = new Leg();
				return fragment;
			case FOOT:
				fragment = new Foot();
				return fragment;
			case TRUNK:
				fragment = new Trunk();
				return fragment;
			case HEIGHTS:
				fragment = new Heights();
				return fragment;
			case PICS:
				fragment = new Pics();
				return fragment;

			}
			return fragment;
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);

			outState.putInt("shownIndex", shownIndex);
		}

		public boolean DBSaver(Context context) {
			MeasurementManager.getInstance(context).addMeasurement(measurement);
			// MeasurementManager.getInstance(context).getch(measurement.getID());
			return true;
		}

	}

	public static class ItemActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_item);
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				finish();
				return;
			}

			final int extra = (Integer) this.getIntent().getSerializableExtra(
					"item");
			Fragment fragment;
			fragment = GridFragment.chooseView(extra);
			if (savedInstanceState == null) {
				getFragmentManager().beginTransaction()
						.add(R.id.item_container, fragment).commit();
			}
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			// Inflate the menu; this adds items to the action bar if it is
			// present.
			getMenuInflater().inflate(R.menu.item, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}

	}

	public static class Head extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.head, container, false);
			return rootView;
		}

	}

	public static class Neck extends Fragment {

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.neck, container, false);

			mid_neck_girth = (EditText) rootView
					.findViewById(R.id.mid_neck_girth);
			if (!measurement.getMid_neck_girth().equals("")) {
				mid_neck_girth.setText(measurement.getMid_neck_girth());
			}
			return rootView;
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			Log.d("measurement", "onDestroy");
			if (!mid_neck_girth.getText().equals("")) {
				measurement.setMid_neck_girth(mid_neck_girth.getText()
						.toString());
			}
		}

	}

	public static class Shoulder extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.shoulder, container,
					false);

			across_back_shoulder_width = (EditText) rootView
					.findViewById(R.id.across_back_shoulder_width);
			shoulder_drop = (EditText) rootView
					.findViewById(R.id.shoulder_drop);
			shoulder_slope_degrees = (EditText) rootView
					.findViewById(R.id.shoulder_slope_degrees);

			if (!measurement.getAcross_back_shoulder_width().equals("")) {
				across_back_shoulder_width.setText(measurement
						.getAcross_back_shoulder_width());
			}
			if (!measurement.getShoulder_drop().equals("")) {
				shoulder_drop.setText(measurement.getShoulder_drop());
			}
			if (!measurement.getShoulder_slope_degrees().equals("")) {
				shoulder_slope_degrees.setText(measurement
						.getShoulder_slope_degrees());
			}

			return rootView;
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if (!across_back_shoulder_width.getText().equals("")) {
				measurement
						.setAcross_back_shoulder_width(across_back_shoulder_width
								.getText().toString());
			}
			if (!shoulder_drop.getText().equals("")) {
				measurement
						.setShoulder_drop(shoulder_drop.getText().toString());
			}
			if (!shoulder_slope_degrees.getText().equals("")) {
				measurement.setShoulder_slope_degrees(shoulder_slope_degrees
						.getText().toString());
			}
		}

	}

	public static class Chest extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.chest, container, false);

			bust_girth = (EditText) rootView.findViewById(R.id.bust_girth);

			if (!measurement.getBust_girth().equals("")) {
				bust_girth.setText(measurement.getBust_girth());
			}
			return rootView;
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if (!bust_girth.getText().equals("")) {
				measurement.setBust_girth(bust_girth.getText().toString());
			}
		}

	}

	public static class Arm extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.arm, container, false);

			arm_length = (EditText) rootView.findViewById(R.id.arm_length);
			upper_arm_girth = (EditText) rootView
					.findViewById(R.id.upper_arm_girth);
			armscye_girth = (EditText) rootView
					.findViewById(R.id.armscye_girth);
			wrist_girth=(EditText)rootView.findViewById(R.id.wrist_girth);

			if (!measurement.getArm_length().equals("")) {
				arm_length.setText(measurement.getArm_length());
			}
			if (!measurement.getUpper_arm_girth().equals("")) {
				upper_arm_girth.setText(measurement.getUpper_arm_girth());
			}
			if (!measurement.getArmscye_girth().equals("")) {
				armscye_girth.setText(measurement.getArmscye_girth());
			}
			if (!measurement.getWrist_girth().equals("")) {
				wrist_girth.setText(measurement.getWrist_girth());
			}
			return rootView;
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if (!arm_length.getText().equals("")) {
				measurement.setArm_length(arm_length.getText().toString());
			}
			if (!upper_arm_girth.getText().equals("")) {
				measurement.setUpper_arm_girth(upper_arm_girth.getText()
						.toString());
			}
			if (!armscye_girth.getText().equals("")) {
				measurement
						.setArmscye_girth(armscye_girth.getText().toString());
			}
			if (!wrist_girth.getText().equals("")) {
				measurement
						.setWrist_girth(wrist_girth.getText().toString());
			}
		}

	}

	public static class Hand extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.hand, container, false);
			return rootView;
		}
	}

	public static class HipAndWaist extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.hip_and_waist, container,
					false);

			hip_girth = (EditText) rootView.findViewById(R.id.hip_girth);
			waist_girth = (EditText) rootView.findViewById(R.id.waist_girth);

			if (!measurement.getHip_girth().equals("")) {
				hip_girth.setText(measurement.getHip_girth());
			}
			if (!measurement.getWaist_girth().equals("")) {
				waist_girth.setText(measurement.getWaist_girth());
			}

			return rootView;
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if (!hip_girth.getText().equals("")) {
				measurement.setHip_girth(hip_girth.getText().toString());
			}
			if (!waist_girth.getText().equals("")) {
				measurement.setWaist_girth(waist_girth.getText().toString());
			}
		}
	}

	public static class Leg extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.leg, container, false);
			return rootView;
		}
	}

	public static class Foot extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.foot, container, false);
			return rootView;
		}
	}

	public static class Trunk extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.trunk, container, false);
			return rootView;
		}
	}

	public static class Heights extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater
					.inflate(R.layout.heights, container, false);

			height = (EditText) rootView.findViewById(R.id.height);
			hip_height = (EditText) rootView.findViewById(R.id.hip_height);

			if (!measurement.getHeight().equals("")) {
				height.setText(measurement.getHeight());
			}
			if (!measurement.getHip_height().equals("")) {
				hip_height.setText(measurement.getHip_height());
			}
			return rootView;
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if (!height.getText().equals("")) {
				measurement.setHeight(height.getText().toString());
			}
			if (!hip_height.getText().equals("")) {
				measurement.setHip_height(hip_height.getText().toString());
			}
		}
	}

	public static class Pics extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.pics, container, false);
			return rootView;
		}
	}
	
	public static void postUser(String url) {

		mID = SyncMeasurement.sendMeasurement(measurement, person);
	}

	private static class HttpAsyncTaskMeasurement extends AsyncTask<String, Void, String> {

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Log.d("settings", "dataSent");
			// Insert the user to the DataBase
			if (mID != null) {
				Log.d("settings", "done");
			} else {
				Log.d("settings", "cannot");
			}
		}

		@Override
		protected String doInBackground(String... urls) {

			postUser(urls[0]);
			System.out.println(mID + "async");
			return mID;
		}
	}

}
