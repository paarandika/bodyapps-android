package fossasia.valentina.bodyapp.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fossasia.valentina.bodyapp.db.DBContract;
import fossasia.valentina.bodyapp.db.DatabaseHandler;
import fossasia.valentina.bodyapp.models.Person;
import fossasia.valentina.bodyapp.models.User;

public class PersonManager {
	private SQLiteDatabase database;
	private DatabaseHandler dbHandler;
	private static PersonManager PersonManager;

	private PersonManager(Context context) {
		dbHandler = DatabaseHandler.getInstance(context);
	}

	public static PersonManager getInstance(Context context) {
		if (PersonManager == null) {
			PersonManager = new PersonManager(context);
		}
		return PersonManager;
	}
	
	public int getPerson(Person person){
		Log.d("personmanager", "getPerson");
		this.database = this.dbHandler.getReadableDatabase();
		Cursor cursor = database.query(DBContract.Person.TABLE_NAME,
				new String[] { DBContract.Person.COLUMN_NAME_ID },
				DBContract.Person.COLUMN_NAME_EMAIL + " ='" + person.getEmail()
						+ "'", null, null, null, null);

		if (cursor.moveToFirst()) {

			int out = cursor.getInt(0);
			cursor.close();
			database.close();
			return out;
		} else {
			return -1;
		}
	}
	
	public void addPerson(Person person){
		Log.d("personmanager", "addPerson");
		Log.d("personmanager", person.getEmail());
		System.out.println(person.getEmail());
		Log.d("personmanager", person.getName());
		this.database = this.dbHandler.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBContract.Person.COLUMN_NAME_EMAIL, person.getEmail());
		values.put(DBContract.Person.COLUMN_NAME_NAME, person.getName());
		values.put(DBContract.Person.COLUMN_NAME_GENDER, person.getGender());
		database.insert(DBContract.Person.TABLE_NAME, null, values);
		database.close();
	}
	
	public void delUser(Person person) {
		this.database = this.dbHandler.getWritableDatabase();
		database.delete(DBContract.Person.TABLE_NAME,
				DBContract.User.COLUMN_NAME_ID + " ='" + person.getID() + "'",
				null);
		database.close();
	}
}
