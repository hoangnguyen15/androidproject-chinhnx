package com.krazevina.thioto.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BienbaoDB {

	public static final String TAG = "Bien bao";
	public static final String KEY       = "_id";
	public static final String cID     = "ID"; 
	public static final String cNAME     = "name"; 
	public static final String cCONTENT  = "content"; 
	public static final String cURLIMAGE = "urlImage"; 
	public static final String cCATEGORY = "category";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDB;
	private static final String DATABASE_CREATE = "create table Bienbao ("
			+ "_id integer primary key autoincrement,"
			+ " ID text not null , "
			+ " name text not null , "
			+ " content text not null,"
			+ " urlImage text not null," 
			+ " category text not null)" 
			;

	private static final String DATABASE_NAME = "BienbaoDB";
	private static final String DATABASE_TABLE = "Bienbao";
	private static final int DATABASE_VERSION = 2;
	private final Context mContext;

	public BienbaoDB(Context context) {
		this.mContext = context;
		mDbHelper = new DatabaseHelper(mContext, DATABASE_NAME, null,
				DATABASE_VERSION);
	}
	public BienbaoDB openToWrite() {
		mDB = mDbHelper.getWritableDatabase();
		return this;
	}
	public BienbaoDB openToRead() {openToWrite();
		//mDB = mDbHelper.getReadableDatabase();
		return this;
	}

	public void initdb() {
		Log.d("dbversion ", "" + mDB.getVersion());
	}

	public long addItem(String id,String name, String content,String urlImage,String category) {
	
		ContentValues inititalValues = new ContentValues();
		try {
			inititalValues.put(cID, id);
			inititalValues.put(cNAME, name);
			inititalValues.put(cCONTENT, content);
			inititalValues.put(cURLIMAGE, urlImage);
			inititalValues.put(cCATEGORY, category);
			
			return mDB.insert(DATABASE_TABLE, null, inititalValues);
		} catch (Exception e) {
			Log.d("additem to Bienbaodb", " ERROR");
		}
		return -1;
	}
	
	private String [] getColumName(){
		
		return new String[]{KEY, cNAME,cCONTENT,cURLIMAGE,cCATEGORY};
	}
	public Cursor getItembyId(long id){
		return mDB.query(DATABASE_TABLE, getColumName(), " _id ="+id, null, null, null, null);
	}
	public Cursor getItem(String category) {
		
			return mDB.query(DATABASE_TABLE, getColumName(), "category='" +category+"'", null, null, null, null);
	}
	public int deletebyID(long id) {
		return mDB.delete(DATABASE_TABLE, "_id=" + id, null);
	}
	public int deleteAll() {
		return mDB.delete(DATABASE_TABLE, null, null);
	}

	public Cursor getAllItems() {
		return mDB.query(DATABASE_TABLE, getColumName(), null, null, null, null, null);
	}
/////////////////////////////////////////////////////////////////////////////////////////
	public void close() {
		mDbHelper.close();
	}

	public void upgrade() {
		mDbHelper.onUpgrade(mDB, mDB.getVersion(), 8);
	}

	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);

		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i(TAG, "Upgrading DB");
			db.execSQL("DROP TABLE IF EXISTS Bienbao");
			onCreate(db);
			db.setVersion(newVersion);
		}
	}
}
