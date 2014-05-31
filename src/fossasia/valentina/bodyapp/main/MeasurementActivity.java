package fossasia.valentina.bodyapp.main;

import java.io.Serializable;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MeasurementActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measurement);

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
				Fragment item = (Fragment) getActivity()
						.getFragmentManager().findFragmentById(R.id.item_container);
				if (item  == null) {
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
				Intent editnote = new Intent(view.getContext(),ItemActivity.class);
				editnote.putExtra("item", shownIndex);
				startActivity(editnote);
			}
		}
		
		public static Fragment chooseView(int index){
			Fragment fragment=null;
			switch(index){
			case HEAD:
				fragment=new Head();
				return fragment;
			case NECK:
				fragment=new Neck();
				return fragment;
			case SHOULDER:
				fragment=new Shoulder();
				return fragment;
			case CHEST:
				fragment=new Chest();
				return fragment;
			case ARM:
				fragment=new Arm();
				return fragment;
			case HAND:
				fragment=new Hand();
				return fragment;
			case HIP_AND_WAIST:
				fragment=new HipAndWaist();
				return fragment;
			case LEG:
				fragment=new Leg();
				return fragment;
			case FOOT:
				fragment=new Foot();
				return fragment;
			case TRUNK:
				fragment=new Trunk();
				return fragment;
			case HEIGHTS:
				fragment=new Heights();
				return fragment;
			case PICS:
				fragment=new Pics();
				return fragment;
				
			}
			return fragment;
		}
		
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);

			outState.putInt("shownIndex",shownIndex);
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
			
			final int extra = (Integer) this.getIntent().getSerializableExtra("item");
			Fragment fragment;
			fragment=GridFragment.chooseView(extra);
			if (savedInstanceState == null) {
				getFragmentManager().beginTransaction()
						.add(R.id.item_container, fragment).commit();
			}
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			// Inflate the menu; this adds items to the action bar if it is present.
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
			View rootView = inflater.inflate(R.layout.head,
					container, false);
			return rootView;
			}
		

	}

	public static class Neck extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.neck,
					container, false);
			return rootView;
			}

	}

	public static class Shoulder extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.shoulder,
					container, false);
			return rootView;
			}

	}

	public static class Chest extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.chest,
					container, false);
			return rootView;
			}

	}

	public static class Arm extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.arm,
					container, false);
			return rootView;
			}
	}

	public static class Hand extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.hand,
					container, false);
			return rootView;
			}
	}

	public static class HipAndWaist extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.hip_and_waist,
					container, false);
			return rootView;
			}
	}

	public static class Leg extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.leg,
					container, false);
			return rootView;
			}
	}

	public static class Foot extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.foot,
					container, false);
			return rootView;
			}
	}

	public static class Trunk extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.trunk,
					container, false);
			return rootView;
			}
	}

	public static class Heights extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.heights,
					container, false);
			return rootView;
			}
	}

	public static class Pics extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.pics,
					container, false);
			return rootView;
			}
	}

}
