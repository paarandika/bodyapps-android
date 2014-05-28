package fossasia.valentina.bodyapp.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button create;
	private Button saved;
	private Button settings;
	private Button exit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		exit=(Button)findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		saved=(Button)findViewById(R.id.saved);
		saved.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this, SavedActivity.class);
				startActivity(intent);
				
			}
		});
		settings=(Button)findViewById(R.id.settings);
		settings.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
