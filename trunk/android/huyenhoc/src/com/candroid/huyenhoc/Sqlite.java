package com.candroid.huyenhoc;

import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqlite 
{
	private static final String DATABASE_NAME="hh.db";
	private static final int DATABASE_VERSION=2;
	static long maxcache = 0;
	static long cachesize = 0;
	
	public static final String CREATE_TABLE_USERDATA="create table userdata(" +
			" name nvarchar(100) null," +
			" ngaysinh nvarchar(200) null, " +
			" giosinh nvarchar(200) null, " +
			" gioitinh integer null)";

	private SQLiteDatabase mSqlDatabase;
	private SQLiteRssHelper sqlitehelper;
	
	public Sqlite(Context context) {
		if(mSqlDatabase==null||!mSqlDatabase.isOpen())
		{
			sqlitehelper=new SQLiteRssHelper(context);
			mSqlDatabase=sqlitehelper.getWritableDatabase();
			if(mSqlDatabase.getVersion()!=DATABASE_VERSION)
				sqlitehelper.onUpgrade(mSqlDatabase, mSqlDatabase.getVersion(), DATABASE_VERSION);
		}
	}
	
	public void setName(String name){
		mSqlDatabase.execSQL("Update userdata set name='"+name+"'");
	}
	
	public String getName(){
		try{
			Cursor c = mSqlDatabase.query("userdata", new String[]{"name"}, null, null, null, null, null);
			c.moveToFirst();
			return c.getString(0);
		}catch (Exception e) {
			return "";
		}
	}
	
	public void setDateOfBirth(int d,int m,int y){
		mSqlDatabase.execSQL("Update userdata set ngaysinh='"+d+"/"+m+"/"+y+"'");
	}
	public void setDateOfBirth(Calendar c){
		int d,m,y;
		d = c.get(Calendar.DATE);
		m = c.get(Calendar.MONTH)+1;
		y = c.get(Calendar.YEAR);
		mSqlDatabase.execSQL("Update userdata set ngaysinh='"+d+"/"+m+"/"+y+"'");
	}
	
	public Calendar getDateOfBirth(){
		int d,m,y;
		Cursor c = mSqlDatabase.query("userdata", new String[]{"ngaysinh"}, null, null, null, null, null);
		c.moveToFirst();
		String date = c.getString(0);
		String dat[] = date.split("/");
		d = Integer.parseInt(dat[0]);
		m = Integer.parseInt(dat[1])-1;
		y = Integer.parseInt(dat[2]);
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DATE, d);
		ca.set(Calendar.MONTH, m);
		ca.set(Calendar.YEAR, y);
		return ca;
	}
	/**
	 * return example: "1/1/2012" <br>
	 * dd/mm/yyyy
	 */
	public String getDateOfBirthString(){
		try{
			Cursor c = mSqlDatabase.query("userdata", new String[]{"ngaysinh"}, null, null, null, null, null);
			c.moveToFirst();
			return c.getString(0);
		}catch (Exception e) {
			return "1/1/2012";
		}
	}
	/**
	 * @return return string sample: Hour:Min:HourOf  <br>
	 * Hour of list: 1-Tí 2-Sửu 3-Dần ....
	 */
	public String getTimeOfBirthString(){
		try{
			Cursor c = mSqlDatabase.query("userdata", new String[]{"giosinh"}, null, null, null, null, null);
			c.moveToFirst();
			return c.getString(0);
		}catch (Exception e) {
			return "1:0:1";
		}
	}
	
	public Calendar getTimeOfBirth(){
		int h,m;
		Cursor c = mSqlDatabase.query("userdata", new String[]{"gio"}, null, null, null, null, null);
		c.moveToFirst();
		String date = c.getString(0);
		String dat[] = date.split(":");
		h = Integer.parseInt(dat[0]);
		m = Integer.parseInt(dat[1]);
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.HOUR_OF_DAY, h);
		ca.set(Calendar.MINUTE, m);
		return ca;
	}
	
	/**
	 * @param s : String. format: hour:min:HourOf<br>
	 * example: "1:0:1" -> 1h, giờ Tí<br>
	 * Hour of list: 1-Tí 2-Sửu 3-Dần ....
	 */
	public void setTimeOfBirth(String s){
		mSqlDatabase.execSQL("Update userdata set giosinh='"+s+"'");
	}
	
	public void setGender(boolean gender){
		mSqlDatabase.execSQL("Update userdata set gioitinh="+(gender?1:0));
	}
	
	public boolean getGender(){
		try{
			Cursor c = mSqlDatabase.query("userdata",new String[]{"gioitinh"},null,null,null,null,null);
			c.moveToFirst();
			if(c.getInt(0)>0)return true;
		}catch (Exception e) {
		}
		return false;
	}
	
	public void close() {
		if(mSqlDatabase!=null&&mSqlDatabase.isOpen())
			mSqlDatabase.close();
	}

	/*SQLite Helper use to help open connection to database*/
	private class SQLiteRssHelper extends SQLiteOpenHelper 
	{
		public SQLiteRssHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			try{
				db.execSQL("DROP TABLE IF EXISTS userdata");
				db.execSQL(CREATE_TABLE_USERDATA);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			try{
				db.execSQL("DROP TABLE IF EXISTS userdata");
				db.execSQL(CREATE_TABLE_USERDATA);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			db.setVersion(newVersion);
		}
	}
	
	// Close database, call when finished using
	public void recycle()
	{
		if(mSqlDatabase!=null)mSqlDatabase.close();
		if(sqlitehelper!=null)sqlitehelper.close();
	}
}