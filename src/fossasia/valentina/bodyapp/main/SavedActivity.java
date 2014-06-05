package fossasia.valentina.bodyapp.main;

import java.io.Serializable;

import java.util.List;

import fossasia.valentina.bodyapp.managers.MeasurementManager;
import fossasia.valentina.bodyapp.models.MeasurementListModel;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * Shows a list of saved measurements in the database.
 * 
 */

public class SavedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saved, menu);
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

	/**
	 * UI fragment to display a list of measurements.
	 */
	public static class SavedList extends Fragment {
		ListView list;
		Boolean dualPane;
		int shownIndex = 0;
		List<MeasurementListModel> measurementsList;

		public SavedList() {
		}

		/**
		 * Gets a List of measurementLisModel objects from database
		 */
		public static List<MeasurementListModel> getDataForListView(
				Context context) {
			System.out.println("check");
			return MeasurementManager.getInstance(context).getList();

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_saved,
					container, false);

			if (savedInstanceState != null) {
				// Restore last state for checked position.
				shownIndex = savedInstanceState.getInt("shownIndex", 0);
			}

			list = (ListView) rootView.findViewById(R.id.listView1);
			measurementsList = getDataForListView(rootView.getContext());
			ListAdapter adapter = new SavedAdapter(rootView.getContext(),
					measurementsList);
			list.setAdapter(adapter);

			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int index, long arg3) {
					shownIndex = index;
					viewSet(view);
				}
			});
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {

			super.onActivityCreated(savedInstanceState);

			View viewSaved = getActivity().findViewById(R.id.mesurements);
			dualPane = viewSaved != null
					&& viewSaved.getVisibility() == View.VISIBLE;
			list.setChoiceMode(ListView.CHOICE_MODE_NONE);

			if (dualPane) {
				System.out.println(shownIndex + "a");
				viewSet(getView());
			}
		}

		/**
		 * Checks if UI has two panes or not. If it has two panes(If device is
		 * horizontal) load measurement data in same activity. Otherwise start a
		 * new activity to show data.
		 */
		public void viewSet(View view) {

			if (dualPane) {
				ViewSavedFragment vsf = (ViewSavedFragment) getActivity()
						.getFragmentManager().findFragmentById(R.id.container2);
				if (vsf == null) {
					System.out.println("else");
					vsf = new ViewSavedFragment();
					vsf.setItem(measurementsList.get(shownIndex)
							.getPersonName());
					FragmentTransaction ft = getActivity().getFragmentManager()
							.beginTransaction();
					ft.replace(R.id.mesurements, vsf);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}
			} else {

				Intent intent = new Intent(view.getContext(),
						ViewSavedActivity.class);
				intent.putExtra("name",
						(CharSequence) measurementsList.get(shownIndex)
								.getPersonName());
				startActivityForResult(intent, 1);
			}
		}

		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);

			outState.putInt("shownIndex", shownIndex);
		}

	}

	/**
	 * Activity to show saved data if UI is in vertical mode.
	 */
	public static class ViewSavedActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_view_saved);
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				finish();
				return;
			}

			if (savedInstanceState == null) {
				android.app.Fragment vsf = new ViewSavedFragment();
				getFragmentManager().beginTransaction()
						.add(R.id.container2, vsf).commit();
			}

		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			// Inflate the menu; this adds items to the action bar if it is
			// present.
			getMenuInflater().inflate(R.menu.view_saved, menu);
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

	/**
	 * UI fragment to show data in a saved measurement record.
	 */
	public static class ViewSavedFragment extends Fragment {
		private TextView text;
		private String chapterName;
		private String item;

		public ViewSavedFragment() {
			super();
		}

		public void setItem(String item) {
			this.item = item;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_view_saved,
					container, false);
			text = (TextView) rootView.findViewById(R.id.tex);
			final Serializable extra = getActivity().getIntent()
					.getSerializableExtra("name");
			chapterName = (String) extra;
			if (chapterName != null) {
				text.setText(chapterName);
			} else {
				text.setText(item);
			}
			return rootView;
		}
	}

}
