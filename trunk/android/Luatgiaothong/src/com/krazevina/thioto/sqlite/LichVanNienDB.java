package com.krazevina.thioto.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LichVanNienDB {

	public static final String TAG = "Bien bao";
	public static final String KEY = "_id";

	public static final String cLICH = "Lich";
	public static final String cHANH = "Hanh";
	public static final String cTRUC = "Truc";
	public static final String cSAO = "Sao";

	public static final String cNGAY = "Ngay";
	public static final String cKIEN = "Kien";
	public static final String cTIET = "Tiet";
	public static final String cGIOKHOIDAUNGAY = "GioKhoiDauNgay";

	public static final String cNGAYNAYKYTUOI = "NgayNayKyTuoi";
	public static final String cNGAYLUCNHAM = "NgayLucNham";
	public static final String cKIENG = "Kieng";
	public static final String cNEN = "Nen";

	public static final String cHUNG = "Hung";
	public static final String cCAT = "Cat";
	public static final String cGIOTOT = "GioTot";
	public static final String cXAU = "Xau";

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDB;
	private static final String DATABASE_CREATE = "create table ngaytotxau ("
			+ "_id integer primary key autoincrement,"
			
			+ " Lich text not null , " 
			+ " Hanh text not null , "
			+ " Truc text not null," 
			+ " Sao text not null,"

			+ " Ngay text not null , " 
			+ " Kien text not null , "
			+ " Tiet text not null," 
			+ " GioKhoiDauNgay text not null,"

			+ " NgayNayKyTuoi text not null , "
			+ " NgayLucNham text not null , "
			+ " Kieng text not null,"
			+ " Nen text not null,"

			+ " Hung text not null , "
			+ " Cat text not null , "
			+ " GioTot text not null,"
			+ " Xau text not null)";


	private static final String DATABASE_NAME = "LichVanNiemDB";
	private static final String DATABASE_TABLE = "ngaytotxau";
	private static final int DATABASE_VERSION = 1;
	private final Context mContext;

	public LichVanNienDB(Context context) {
		this.mContext = context;
		mDbHelper = new DatabaseHelper(mContext, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public LichVanNienDB openToWrite() {
		mDB = mDbHelper.getWritableDatabase();
		return this;
	}

	public LichVanNienDB openToRead() {
		openToWrite();
		return this;
	}

	public void initdb() {
		Log.d("dbversion ", "" + mDB.getVersion());
	}

	
	public long addItem( 
			
			String Lich,
			String Hanh,
			String Truc,
			String Sao,
			
			String Ngay,
			String Kien,
			String Tiet,
			String GioKhoiDauNgay,
			
			String NgayNayKyTuoi,
			String NgayLucNham,
			String Kieng,
			String Nen,
			
			String Hung,
			String Cat,
			String GioTot,
			String Xau) {

		ContentValues inititalValues = new ContentValues();
		try {
			inititalValues.put(cLICH, Lich);
			inititalValues.put(cHANH, Hanh);
			inititalValues.put(cTRUC, Truc);
			inititalValues.put(cSAO, Sao);

			inititalValues.put(cNGAY, Ngay);
			inititalValues.put(cKIEN, Kien);
			inititalValues.put(cTIET, Tiet);
			inititalValues.put(cGIOKHOIDAUNGAY, GioKhoiDauNgay);

			inititalValues.put(cNGAYNAYKYTUOI, NgayNayKyTuoi);
			inititalValues.put(cNGAYLUCNHAM, NgayLucNham);
			inititalValues.put(cKIENG, Kieng);
			inititalValues.put(cNEN, Nen);
			
			inititalValues.put(cHUNG, Hung);
			inititalValues.put(cCAT, Cat);
			inititalValues.put(cGIOTOT, GioTot);
			inititalValues.put(cXAU, Xau);
			
			return mDB.insert(DATABASE_TABLE, null, inititalValues);
		} catch (Exception e) {
			Log.d("additem to Bienbaodb", " ERROR");
		}
		return -1;
	}

	private String[] getColumName() {

		return new String[] { KEY, cLICH, cLICH, cLICH, cLICH };
	}

	public Cursor getItembyId(long id) {
		return mDB.query(DATABASE_TABLE, getColumName(), " _id =" + id, null,
				null, null, null);
	}

	public Cursor getItem(String category) {

		return mDB.query(DATABASE_TABLE, getColumName(), "category='"
				+ category + "'", null, null, null, null);
	}

	public int deletebyID(long id) {
		return mDB.delete(DATABASE_TABLE, "_id=" + id, null);
	}

	public int deleteAll() {
		return mDB.delete(DATABASE_TABLE, null, null);
	}

	public Cursor getAllItems() {
		return mDB.query(DATABASE_TABLE, getColumName(), null, null, null,
				null, null);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////
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
			db.execSQL("DROP TABLE IF EXISTS ngaytotxau");
			onCreate(db);
			db.setVersion(newVersion);
		}
	}
}
