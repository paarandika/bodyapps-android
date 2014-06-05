package fossasia.valentina.bodyapp.managers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fossasia.valentina.bodyapp.db.DBContract;
import fossasia.valentina.bodyapp.db.DatabaseHandler;
import fossasia.valentina.bodyapp.models.Measurement;
import fossasia.valentina.bodyapp.models.MeasurementListModel;
import fossasia.valentina.bodyapp.models.User;

public class MeasurementManager {
	private SQLiteDatabase database;
	private DatabaseHandler dbHandler;
	private static MeasurementManager measurementManager;

	private MeasurementManager(Context context) {
		dbHandler = DatabaseHandler.getInstance(context);
	}

	public static MeasurementManager getInstance(Context context) {
		if (measurementManager == null) {
			measurementManager = new MeasurementManager(context);
		}
		return measurementManager;
	}
	
	public void addMeasurement(Measurement measurement) {
		Log.d("measurementmanager", "addMeasurement");
		this.database = this.dbHandler.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBContract.Measurement.COLUMN_NAME_ID, measurement.getID());
		values.put(DBContract.Measurement.COLUMN_NAME_USER_ID, measurement.getUserID());
		values.put(DBContract.Measurement.COLUMN_NAME_PERSON_ID, measurement.getPersonID());
		values.put(DBContract.Measurement.COLUMN_NAME_CREATED, measurement.getCreated());
		values.put(DBContract.Measurement.COLUMN_NAME_LAST_SYNC, measurement.getLastSync());
		values.put(DBContract.Measurement.COLUMN_NAME_LAST_EDIT, measurement.getLastEdit());
		values.put(DBContract.Measurement.COLUMN_NAME_UNIT, measurement.getUnit());
		values.put(DBContract.Measurement.COLUMN_NAME_MID_NECK_GIRTH, measurement.getMid_neck_girth());
		values.put(DBContract.Measurement.COLUMN_NAME_BUST_GIRTH, measurement.getBust_girth());
		values.put(DBContract.Measurement.COLUMN_NAME_WAIST_GIRTH, measurement.getWaist_girth());
		values.put(DBContract.Measurement.COLUMN_NAME_HIP_GIRTH, measurement.getHip_girth());
		values.put(DBContract.Measurement.COLUMN_NAME_ACROSS_BACK_SHOULDER_WIDTH, measurement.getAcross_back_shoulder_width());
		values.put(DBContract.Measurement.COLUMN_NAME_SHOULDER_DROP, measurement.getShoulder_drop());
		values.put(DBContract.Measurement.COLUMN_NAME_SHOULDER_SLOPE_DEGREES, measurement.getShoulder_slope_degrees());
		values.put(DBContract.Measurement.COLUMN_NAME_ARM_LENGTH, measurement.getArm_length());
		values.put(DBContract.Measurement.COLUMN_NAME_UPPER_ARM_GIRTH, measurement.getUpper_arm_girth());
		values.put(DBContract.Measurement.COLUMN_NAME_ARMSCYE_GIRTH, measurement.getArmscye_girth());
		values.put(DBContract.Measurement.COLUMN_NAME_HEIGHT, measurement.getHeight());
		values.put(DBContract.Measurement.COLUMN_NAME_HIP_HEIGHT, measurement.getHip_height());
		values.put(DBContract.Measurement.COLUMN_NAME_WRIST_GIRTH, measurement.getWrist_girth());

		database.insert(DBContract.Measurement.TABLE_NAME, null, values);
		database.close();
	}
	public void getch(String id){
		Log.d("measurementmanager", "checkMeasurement");
		this.database = this.dbHandler.getReadableDatabase();
		Cursor cursor = database.query(DBContract.Measurement.TABLE_NAME,
				new String[] { DBContract.Measurement.COLUMN_NAME_MID_NECK_GIRTH },
				DBContract.Measurement.COLUMN_NAME_ID + " ='" + id
						+ "'", null, null, null, null);

		if (cursor.moveToFirst()) {

			String out = cursor.getString(0);
			System.out.println(out);
			cursor.close();
			database.close();
		}
	}
	
	public List<MeasurementListModel> getList(){

		List<MeasurementListModel> measurementList = new ArrayList<MeasurementListModel>();
	    // Select All Query
	    String selectQuery = "SELECT  C."+DBContract.Measurement.COLUMN_NAME_ID
	    				+", "+DBContract.Measurement.COLUMN_NAME_PERSON_ID+" AS pid"
	    				+", "+DBContract.Measurement.COLUMN_NAME_CREATED
	    				+", "+DBContract.Person.COLUMN_NAME_NAME
	    				+", "+DBContract.Person.COLUMN_NAME_EMAIL
	    				+" FROM " 
	    				+ DBContract.Measurement.TABLE_NAME+" AS C JOIN "
	    				+DBContract.Person.TABLE_NAME+" AS R ON C."
	    				+DBContract.Measurement.COLUMN_NAME_PERSON_ID+" = R."
	    				+DBContract.Person.COLUMN_NAME_ID;
	    System.out.println(selectQuery);
	 
	    SQLiteDatabase db = this.dbHandler.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	MeasurementListModel msmnt = new MeasurementListModel();
	            msmnt.setID(cursor.getString(0));
	            msmnt.setPersonID(Integer.parseInt(cursor.getString(1)));
	            msmnt.setCreated(cursor.getString(2));
	            msmnt.setPersonName(cursor.getString(3));
	            msmnt.setPersonEmail(cursor.getString(4));
	            // Adding msmnt to list
	            measurementList.add(msmnt);
	        } while (cursor.moveToNext());
	    }
	    return measurementList;
	}
}
