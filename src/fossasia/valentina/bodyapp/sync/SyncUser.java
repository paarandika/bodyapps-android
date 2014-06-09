/*
 * Copyright (c) 2014, Fashiontec (http://fashiontec.org)
 * Licensed under LGPL, Version 3
 */

package fossasia.valentina.bodyapp.sync;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles the Sync of users
 */
public class SyncUser extends Sync {

	private static String json;
	private static final String URL = "http://192.168.1.2:8020/user";
	private static String result;
	private static final int CON_TIMEOUT=5000;
	private static final int SOC_TIMEOUT=5000;
	

	/**
	 * Get the user ID of the given user from web application
	 * 
	 * @param email
	 * @param name
	 * @return
	 */
	public static String getUserID(String email, String name) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.accumulate("name", name);
			jsonObject.accumulate("age", "22");
			jsonObject.accumulate("dob", "12/10/1990");
			jsonObject.accumulate("emailId", email);
			json = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		result = POST(URL, json, CON_TIMEOUT,SOC_TIMEOUT);
		return result;
	}

}
