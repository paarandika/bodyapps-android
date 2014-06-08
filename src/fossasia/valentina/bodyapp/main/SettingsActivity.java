/*
 * Copyright (c) 2014, Fashiontec (http://fashiontec.org)
 * Licensed under LGPL, Version 3
 */

package fossasia.valentina.bodyapp.main;

import java.io.InputStream;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import fossasia.valentina.bodyapp.managers.UserManager;
import fossasia.valentina.bodyapp.models.User;
import fossasia.valentina.bodyapp.sync.SyncUser;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

/**
 *Activity for settings. 
 *This activity handles user authentication via Google play services and obtains user ID from web application.
 *Then user gets added to the DB
 */
public class SettingsActivity extends ActionBarActivity implements
		OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {

	private static final int RC_SIGN_IN = 0;
	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 400;

	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	/**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;

	private boolean mSignInClicked;

	private ConnectionResult mConnectionResult;

	private SignInButton btnSignIn;
	private Button btnSignOut;
	private Button btnRevoke;
	private ImageView imgProfilePic;
	private TextView txtName;
	private TextView txtEmail;
	private LinearLayout llProfileLayout;
	private static ProgressDialog progress;
	private String email;
	private String personName;
	private String userID=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		btnSignIn = (SignInButton) findViewById(R.id.settings_btn_signin);
		btnSignOut = (Button) findViewById(R.id.settings_btn_signout);
		btnRevoke = (Button) findViewById(R.id.settings_btn_reovke);
		imgProfilePic = (ImageView) findViewById(R.id.settings_img_profile);
		txtName = (TextView) findViewById(R.id.settings_txt_name);
		txtEmail = (TextView) findViewById(R.id.settings_txt_email);
		llProfileLayout = (LinearLayout) findViewById(R.id.settings_layout);

		// Button click listeners
		btnSignIn.setOnClickListener(this);
		btnSignOut.setOnClickListener(this);
		btnRevoke.setOnClickListener(this);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_PROFILE).build();

		progress = new ProgressDialog(this);
		progress.setTitle("Loading");
		progress.setMessage("Wait while loading...");
		progress.setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	/**
	 * Sign in the user to Google
	 */
	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}

	private void resolveSignInError() {
		System.out.println(mConnectionResult);
		if (mConnectionResult == null) {
			return;
		}

		if (mConnectionResult.hasResolution()) {

			try {
				mIntentInProgress = true;
				System.out.println(this);
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
				System.out.println("RSE");
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
				System.out.println("RSE45");
			}
		}
	}

	/**
	 * Sign out user from Google
	 */
	private void signOutFromGplus() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
			updateUI(false);
		}
	}

	/**
	 * Revoking access from google
	 * */
	private void revokeGplusAccess() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
					.setResultCallback(new ResultCallback<Status>() {
						@Override
						public void onResult(Status arg0) {
							Log.e("settings", "User access revoked!");
							mGoogleApiClient.connect();
							updateUI(false);
						}

					});
		}

		reload();
	}

	/**
	 * Update the UI according to isSign in
	 * 
	 * @param isSignedIn
	 */
	private void updateUI(boolean isSignedIn) {
		if (isSignedIn) {
			btnSignIn.setVisibility(View.GONE);
			btnSignOut.setVisibility(View.VISIBLE);
			btnRevoke.setVisibility(View.VISIBLE);
			llProfileLayout.setVisibility(View.VISIBLE);
		} else {
			btnSignIn.setVisibility(View.VISIBLE);
			btnSignOut.setVisibility(View.GONE);
			btnRevoke.setVisibility(View.GONE);
			llProfileLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seetings, menu);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settings_btn_signin:
			// Signin button clicked
			signInWithGplus();
			break;
		case R.id.settings_btn_signout:
			// Signout button clicked
			signOutFromGplus();
			break;
		case R.id.settings_btn_reovke:
			// Revoke access button clicked
			revokeGplusAccess();
			break;
		}

	}

	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {

				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);

				personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				email = Plus.AccountApi.getAccountName(mGoogleApiClient);

				Log.e("settings", "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);

				txtName.setText(personName);
				txtEmail.setText(email);

				
				Log.d("settings", "here");
				//creates a new user and check if he exists on db
				User user = new User(email, personName, userID);
				String isUser = UserManager.getInstance(getBaseContext())
						.isUser(user);
				
				if (isUser.equals("NULL")) {
					//if user is not in DB a post goes to web app and gets a ID for user.
					//Then he will be added to the DB and set as current user.
					progress.show();
					new HttpAsyncTaskUser().execute("http://192.168.1.2:8020/user");
				} else {
					//if user exists in DB just sets him current user
					UserManager.getInstance(getBaseContext()).setCurrent(user);
					userID=isUser;
				}

				personPhotoUrl = personPhotoUrl.substring(0,
						personPhotoUrl.length() - 2)
						+ PROFILE_PIC_SIZE;

				new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		mSignInClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

		// Get user's information
		getProfileInformation();

		// Update the UI after signin
		updateUI(true);

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
		updateUI(false);
	}

	public void reload() {

		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();

		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	/**
	 *Async task to get profile image from google.
	 */
	private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public LoadProfileImage(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}

	/**
	 * Call to syncUser method
	 * @param url
	 */
	public void postUser(String url) {

		userID = SyncUser.getUserID(email, personName);
	}

	/**
	 *Async task to send user data to web app and get user ID.
	 */
	private class HttpAsyncTaskUser extends AsyncTask<String, Void, String> {

		@Override
		protected void onPostExecute(String result) {
			Log.d("settings", "dataSent");

			if (userID != null) {
				System.out.println(userID);
				User user = new User(email, personName, userID);
				//adds the user to the DB
				UserManager.getInstance(getBaseContext()).addUser(user);
				System.out.println(UserManager.getInstance(getBaseContext()).isUser(user)+"is a user");
			} else {
				Log.d("settings", "cannot");
			}
			 progress.dismiss();
		}

		@Override
		protected String doInBackground(String... urls) {

			postUser(urls[0]);
			System.out.println(userID + "async");
			return userID;
		}
	}
}
