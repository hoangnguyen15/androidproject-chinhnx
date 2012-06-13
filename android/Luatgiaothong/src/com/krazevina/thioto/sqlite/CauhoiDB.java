package com.krazevina.thioto.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CauhoiDB {

	public static final String TAG = "Cau hoi";
	// ////////table du lieu Cauhoi
	public static final String KEY = "_id";
	public static final String cCAUHOI = "cauhoi";
	public static final String cTLA = "tla";
	public static final String cTLB = "tlb";
	public static final String cTLC = "tlc";
	public static final String cTLD = "tld";
	public static final String cKETQUA = "ketqua";
	public static final String cIMAGE = "image";
	public static final String cPHANLOAI = "phanloai";
	public static final String cTHI = "thi"; // thuoc cau hoi thi
	public static final String cDatraloidapan = "datraloidapan";
	
	private static final String DATABASE_CREATE_TABLE_CAUHOI = "create table Cauhoi ("
			+ "_id integer primary key autoincrement,"
			+ " cauhoi text not null , "
			+ " tla text not null , "
			+ " tlb text not null , "
			+ " tlc text not null , "
			+ " tld text not null,"
			+ " ketqua text not null,"
			+ " image text  ," 
			+ " phanloai text not null," // 1,2,3
			+ " thi text not null," 
			+ " datraloidapan text)" // 1=a,2=b,3=c,4=d, null = chua tra loi
	;

	////////////////////////////// END
	/// bang thong ke
	public static final String NGAY="ngay";
	public static final String GIO="gio";
	public static final String THOIGIANLAMBAI="thoigianlambai";
	public static final String TYLEDUNG="tyledung";
	public static final String DOTRUOT="dotruot";
	public static final String DATABASE_CREATE_TABLE_THONGKE = "create table Thongke ("
		+ "_id integer primary key autoincrement,"
		+ " ngay text not null , "
		+ " gio text not null , "
		+ " thoigianlambai text not null , "
		+ " tyledung text not null , "
		+ " dotruot text not null )" //=1 do
		
;
	/// bang thong ke
	public static final String KEYREQUEST="keyrequest";
	public static final String STATUS="status";

	public static final String DATABASE_CREATE_TABLE_USE_MEOTHI = "create table Meothi ("
		+ "_id integer primary key autoincrement,"
		+ " keyrequest text not null , "
		+ " status text not null )" //=1 has sent message,=0 default not sent
		
;
	/// bang requestion send sms
		public static final String ASK="ask";

		public static final String DATABASE_CREATE_TABLE_ASK = "create table Hoi ("
			+ "_id integer primary key autoincrement,"
			+ " ask text not null )" //=1 has sent message,=0 default not sent
			
	;
	/////////////////////////////////
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDB;

	private static final String DATABASE_NAME = "Cauhoi";
	private static final String DATABASE_TABLE_CAUHOI = "Cauhoi";
	private static final String DATABASE_TABLE_THONGKE = "Thongke";
	private static final String DATABASE_TABLE_MEOTHI = "Meothi";
	private static final String DATABASE_TABLE_ASK = "Hoi";
	private static final int DATABASE_VERSION = 2;
	private final Context mContext;

	public CauhoiDB(Context context) {
		this.mContext = context;
		mDbHelper = new DatabaseHelper(mContext, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public CauhoiDB openToWrite() {
		mDB = mDbHelper.getWritableDatabase();
		return this;
	}

	public CauhoiDB openToRead() {
		mDB = mDbHelper.getReadableDatabase();
		return this;
	}

	public void initdb() {
		Log.d("dbversion ", "" + mDB.getVersion());
	}

	public void update(int id,String datraloi){
		ContentValues inititalValues = new ContentValues();
		try {
			inititalValues.put(cDatraloidapan, datraloi);
			Log.d("id=", ""+id);
			mDB.update(DATABASE_TABLE_CAUHOI , inititalValues , "_id="+id, null);
		}catch (Exception e) {
			Log.d("error update ", ""+id);
			e.printStackTrace();
		}
		
	}

	public void update_sms(String keyrequest,String status){
		ContentValues inititalValues = new ContentValues();
		try {
			inititalValues.put(STATUS, status);
			
			mDB.update(DATABASE_TABLE_MEOTHI , inititalValues , "keyrequest='"+keyrequest+"'", null);
		}catch (Exception e) {
			Log.d("error update ", ""+keyrequest);
			e.printStackTrace();
		}
		
	}
	public long addItem(String cauhoi, String tla, String tlb, String tlc,
			String tld, String ketqua, String anh, String phanloai, String thi,String datraloidapan) {

		ContentValues inititalValues = new ContentValues();
		try {
			inititalValues.put(cCAUHOI, cauhoi);
			inititalValues.put(cTLA, tla);
			inititalValues.put(cTLB, tlb);
			inititalValues.put(cTLC, tlc);
			inititalValues.put(cTLD, tld);
			inititalValues.put(cKETQUA, ketqua);
			inititalValues.put(cIMAGE, anh);
			inititalValues.put(cPHANLOAI, phanloai);
			inititalValues.put(cTHI, thi);
			inititalValues.put(cDatraloidapan, datraloidapan);
			return mDB.insert(DATABASE_TABLE_CAUHOI, null, inititalValues);
		} catch (Exception e) {
			Log.d("additem to Cauhoi", " ERROR:"+e.getMessage());
		}
		return -1;
	}
	public long addItem1(String ngay, String gio, String thoigianlambai, String tyledung,String dotruot) {

		ContentValues inititalValues = new ContentValues();
		try {
			inititalValues.put(NGAY, ngay);
			inititalValues.put(GIO, gio);
			inititalValues.put(THOIGIANLAMBAI, thoigianlambai);
			inititalValues.put(TYLEDUNG, tyledung);
			inititalValues.put(DOTRUOT, dotruot);
		
			return mDB.insert(DATABASE_TABLE_THONGKE, null, inititalValues);
		} catch (Exception e) {
			Log.d("additem to thongke", " ERROR:"+e.getMessage());
		}
		return -1;
	}
	public long addItem_sms(String keyrequest, String status) {

		ContentValues inititalValues = new ContentValues();
		try {
			
			inititalValues.put(KEYREQUEST, keyrequest);
			inititalValues.put(STATUS, status);
		
			return mDB.insert(DATABASE_TABLE_MEOTHI, null, inititalValues);
		} catch (Exception e) {
			Log.d("additem to Meothi", " ERROR:"+e.getMessage());
		}
		return -1;
	}
	public long addItem_ask(String ask) {

		ContentValues inititalValues = new ContentValues();
		try {
			
			inititalValues.put(ASK, ask);
		
			return mDB.insert(DATABASE_TABLE_ASK, null, inititalValues);
		} catch (Exception e) {
			Log.d("additem to Hoi", " ERROR:"+e.getMessage());
		}
		return -1;
	}
	private String[] getColumName() {

		return new String[] { KEY, cCAUHOI, cTLA, cTLB, cTLC, cTLD, cKETQUA,
				cIMAGE, cPHANLOAI, cTHI ,cDatraloidapan};
	}
	private String[] getColumName1() {

		return new String[] { KEY, NGAY, GIO, THOIGIANLAMBAI, TYLEDUNG, DOTRUOT};
	}
	private String[] getColumName_sms() {

		return new String[] { KEY,KEYREQUEST,STATUS};
	}
	private String[] getColumName_ask() {

		return new String[] { KEY,ASK};
	}
	public Cursor getItembyId(long id) {
		return mDB.query(DATABASE_TABLE_CAUHOI, getColumName(), " _id =" + id,
				null, null, null, null);
	}

	public Cursor getItem(String category) {

		return mDB.query(DATABASE_TABLE_CAUHOI, getColumName(), "category='"
				+ category + "'", null, null, null, null);
	}

	public int deletebyID(long id) {
		return mDB.delete(DATABASE_TABLE_CAUHOI, "_id=" + id, null);
	}
	public int deleteAll() {
		return mDB.delete(DATABASE_TABLE_CAUHOI, null, null);
	}
	public int deleteAll1() {
		return mDB.delete(DATABASE_TABLE_THONGKE, null, null);
	}
	public int deleteAll_sms() {
		return mDB.delete(DATABASE_TABLE_MEOTHI, null, null);
	}
	public Cursor getAllItems() { // lay het du lieu trong table cauhoi
		return mDB.query(DATABASE_TABLE_CAUHOI, getColumName(), null, null,
				null, null, null);
	}
	public Cursor getAllItems1() { // lay het du lieu trong table cauhoi
		return mDB.query(DATABASE_TABLE_THONGKE, getColumName1(), null, null,
				null, null, null);
	}
	public Cursor getAllItems_sms() { // lay het du lieu trong table cauhoi
		return mDB.query(DATABASE_TABLE_MEOTHI, getColumName_sms(), null, null,
				null, null, null);
	}
	public Cursor getAllItems_ask() { // lay het du lieu trong table cauhoi
		return mDB.query(DATABASE_TABLE_ASK, getColumName_ask(), null, null,
				null, null, null);
	}
	// ///////////////////////////////////////////////////////////////////////////////////////
	public void close() {
		mDbHelper.close();
	}


	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE_TABLE_CAUHOI);
			db.execSQL(DATABASE_CREATE_TABLE_THONGKE);
			db.execSQL(DATABASE_CREATE_TABLE_ASK);
			db.execSQL(DATABASE_CREATE_TABLE_USE_MEOTHI);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i(TAG, "Upgrading DB");
			db.execSQL("DROP TABLE IF EXISTS Cauhoi");
			db.execSQL("DROP TABLE IF EXISTS Thongke");
			db.execSQL("DROP TABLE IF EXISTS Meothi");
			db.execSQL("DROP TABLE IF EXISTS Hoi");
			onCreate(db);
			db.setVersion(newVersion);
		}
	}

}
