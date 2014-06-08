/*
 * Copyright (c) 2014, Fashiontec (http://fashiontec.org)
 * Licensed under LGPL, Version 3
 */

package fossasia.valentina.bodyapp.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import fossasia.valentina.bodyapp.managers.PersonManager;
import fossasia.valentina.bodyapp.managers.UserManager;
import fossasia.valentina.bodyapp.models.Measurement;
import fossasia.valentina.bodyapp.models.Person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateActivity extends Activity {
	private Button btnCreate;
	private Spinner spnUnits;
	private Spinner spnGender;
	private TextView txtEmail;
	private TextView txtName;
	private Person person;
	private Measurement measurement;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);

		btnCreate = (Button) findViewById(R.id.create_btn_create);
		btnCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (setData()) {
					//System.out.println("here");
					Intent intent = new Intent(CreateActivity.this,
							MeasurementActivity.class);
					intent.putExtra("measurement", measurement);
					startActivity(intent);
					Activity host=(Activity)v.getContext();
					host.finish();
				}

			}
		});

		spnUnits = (Spinner) findViewById(R.id.create_spn_unit);
		ArrayAdapter<CharSequence> uitsAdapter = ArrayAdapter
				.createFromResource(this, R.array.units_array,
						android.R.layout.simple_spinner_item);
		spnUnits.setAdapter(uitsAdapter);

		spnGender = (Spinner) findViewById(R.id.create_spn_gender);
		ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter
				.createFromResource(this, R.array.gender_array,
						android.R.layout.simple_spinner_item);
		spnGender.setAdapter(genderAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
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

	public boolean setData() {
		txtEmail = (TextView) findViewById(R.id.create_txt_gmail);
		txtName = (TextView) findViewById(R.id.create_txt_name);
		String name;
		String email;
		if (!txtEmail.getText().toString().equals("")) {
			email = txtEmail.getText().toString();
			System.out.println(email);
		} else {
			return false;
		}
		if (!txtName.getText().toString().equals("")) {
			System.out.println("boo2");
			name = txtName.getText().toString();
		} else {
			return false;
		}
		// System.out.println("boo");
		person = new Person(email, name, spnGender.getSelectedItemPosition());
		//adds the person to DB and gets his ID
		int personID=PersonManager.getInstance(this).getPerson(person);
		if(personID==-1){
			PersonManager.getInstance(this).addPerson(person);
			personID = PersonManager.getInstance(this).getPerson(person);
		}
		System.out.println(personID);
		person.setID(personID);

		String userID = UserManager.getInstance(this).getCurrent();
		if (userID != null) {
			System.out.println(userID + " createActivity");

			measurement = new Measurement(getID(), userID, person.getID(),
					spnUnits.getSelectedItemPosition());

			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
			String dateText="";
			try {
				Date date = new Date();
				dateText = dateformat.format(date);
				System.out.println("Current Date Time 2: " + dateText);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			measurement.setCreated(dateText);

		} else {
			return false;
		}
		return true;
	}

	/**
	 * Gets a UUID for measurement
	 * @return
	 */
	public String getID() {
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		System.out.println("Random UUID String = " + randomUUIDString);
		return randomUUIDString;
	}

}
