package fossasia.valentina.bodyapp.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import fossasia.valentina.bodyapp.db.DatabaseHandler;

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
}
