package fossasia.valentina.bodyapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "BodyApp.db";
    public static DatabaseHandler dbHandler;
    
    
    
    
	private DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("SQLite", "Creating Databases");
		db.execSQL(DBContract.User.SQL_CREATE_ENTRIES);
		Log.d("table", "Created user");
		db.execSQL(DBContract.Person.SQL_CREATE_ENTRIES);
		Log.d("table", "Created person");
		db.execSQL(DBContract.Measurement.SQL_CREATE_ENTRIES);
		Log.d("table", "Created measurement");
		db.execSQL(DBContract.Delete.SQL_CREATE_ENTRIES);
		Log.d("table", "Created delete");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DBContract.User.SQL_DELETE_ENTRIES);
		db.execSQL(DBContract.Person.SQL_DELETE_ENTRIES);
		db.execSQL(DBContract.Measurement.SQL_DELETE_ENTRIES);
		db.execSQL(DBContract.Delete.SQL_DELETE_ENTRIES);
		onCreate(db);
		
	}
	
	public static DatabaseHandler getInstance (Context context){
		if(dbHandler==null){
			dbHandler=new DatabaseHandler(context);
		}
		return dbHandler;
	}

}
