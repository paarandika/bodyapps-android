package fossasia.valentina.bodyapp.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




import fossasia.valentina.bodyapp.models.SavedListObject;


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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

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


	public static class SavedList extends Fragment {
		ListView list;
		Boolean dualPane;
		int shownIndex=0;
		final List<SavedListObject> measurementsList =getDataForListView();
		
		public SavedList() {
		}

		 public static List<SavedListObject> getDataForListView()
		    {
		        List<SavedListObject> list = new ArrayList<SavedListObject>();

		        for(int i=0;i<20;i++)
		        {

		            SavedListObject chapter = new SavedListObject();
		            chapter.chapterName = "Chapter "+i;
		            chapter.chapterDescription = "This is description for chapter "+i;
		            list.add(chapter);
		        }
		        return list;

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
			
			list=(ListView)rootView.findViewById(R.id.listView1);
			ListAdapter adapter=new SavedAdapter(rootView.getContext(),measurementsList);
			list.setAdapter(adapter);
			
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int index,
						long arg3) {
					shownIndex=index;
					viewSet(view);
				}
			});
			return rootView;
		}

		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			View viewSaved=getActivity().findViewById(R.id.mesurements);
			dualPane=viewSaved!=null && viewSaved.getVisibility()==View.VISIBLE;
			list.setChoiceMode(ListView.CHOICE_MODE_NONE);
			if(dualPane){

				System.out.println(shownIndex+"a");
				viewSet(getView());	
				
			}

			
		}

		public void viewSet(View view){
			
			if(dualPane){
				ViewSavedFragment vsf=(ViewSavedFragment)getActivity().getFragmentManager().findFragmentById(R.id.container2);
				if (vsf == null){
					System.out.println("else");
					vsf=new ViewSavedFragment();
					vsf.setItem(measurementsList.get(shownIndex).chapterName);
					FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
					ft.replace(R.id.mesurements, vsf);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}
			}else{
				
				Intent intent=new Intent(view.getContext(), ViewSavedActivity.class);
				intent.putExtra("name", (CharSequence) measurementsList.get(shownIndex).chapterName);
				startActivityForResult(intent, 1);
			}
		}
		
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);

			outState.putInt("shownIndex",shownIndex);
		}
		
	}
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
				android.app.Fragment vsf=new ViewSavedFragment();
				getFragmentManager().beginTransaction()
						.add(R.id.container2, vsf).commit();
			}
			
			
		}
		

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			// Inflate the menu; this adds items to the action bar if it is present.
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
			text=(TextView)rootView.findViewById(R.id.tex);
			final Serializable extra = getActivity().getIntent().getSerializableExtra("name");
			chapterName=(String) extra;
			if(chapterName!=null){
				text.setText(chapterName);
			}else{
				text.setText(item);
			}
			return rootView;
		}
	}

}
