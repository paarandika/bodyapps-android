package fossasia.valentina.bodyapp.sync;


import org.json.JSONException;
import org.json.JSONObject;

import fossasia.valentina.bodyapp.models.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SyncUser extends Sync {
	
	private static String json;
	private static final String URL="http://192.168.1.2:8020/user";
	static  String result;

	public static String getUserID(String email,String name) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.accumulate("name", name);
			jsonObject.accumulate("age", "22");
			jsonObject.accumulate("dob", "12/10/1990");
			jsonObject.accumulate("emailId", email);
			json=jsonObject.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	//new HttpAsyncTaskUser().execute(URL);
		result=POST(URL, json);
		return result;
	}

	
}