package com.candroid.objects;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings.Secure;
import android.util.Log;

public class Sqlite 
{
	private static final String DATABASE_NAME="huyenhoc.db";
	private static final int DATABASE_VERSION=1;
	static long maxcache = 0;
	static long cachesize = 0;
	
	public static final String CREATE_TABLE_USER="create table user("+
			"id integer primary key autoincrement not null,"+
			"name nvarchar(128) null,"+
			"dateofbirth int null,"+
			"montheofbirth int null,"+
			"yearofbirth int null)";

	public static final String CREATE_TABLE_BUY="create table buy(" +
			" id integer primary key autoincrement not null,"+
			" ind integer null," +
			" time nvarchar(50) null)";
	public static final String INSERT_TABLE_USER="INSERT INTO user(name,dateofbirth,montheofbirth,yearofbirth) values('',0,0,0)";
	
	private SQLiteDatabase mSqlDatabase;
	private SQLiteRssHelper sqlitehelper;
	String id;
	
	public Sqlite(Context context) {
		id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		sqlitehelper=new SQLiteRssHelper(context);
		this.mSqlDatabase=sqlitehelper.getWritableDatabase();
		if(mSqlDatabase.getVersion()!=DATABASE_VERSION)
			sqlitehelper.onUpgrade(mSqlDatabase, mSqlDatabase.getVersion(), DATABASE_VERSION);
	}
	

	
	public String getName(){
		Cursor c = null;
		String name="";
		try{
			c = mSqlDatabase.query("user", new String[]{"name"}, null, null, null, null, null);
			c.moveToFirst();
			name = c.getString(0);
		}catch (Exception e) {
			if(c!=null)c.close();
			e.printStackTrace();
		}finally{
			if(c!=null)c.close();
		}
		return name;
	}
	
	public void setName(String name,int dateofbirth,int montheofbirth,int yearofbirth){
		mSqlDatabase.execSQL("UPDATE into user(name,dateofbirth,monthofbirth,yearofbirth) values("
				+ "'"+name+"'"
				+dateofbirth+","
				+montheofbirth+","
				+yearofbirth+")");
				
	}
	
	public void buy(int ind,String time){
		mSqlDatabase.execSQL("INSERT into buy(ind,time) values (" +
				+ ind +
				",'"+time+"')");
	}
	
	public Cursor getbuy(){
		return mSqlDatabase.query("buy", new String[]{"ind","time"}, null, null, null, null, null);
	}
	
	public void bought(int ind){
		mSqlDatabase.execSQL("DELETE FROM buy WHERE ind="+ind);
	}
	

	
	public void close() {
		if(mSqlDatabase!=null&&this.mSqlDatabase.isOpen())
			this.mSqlDatabase.close();
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
				db.execSQL("DROP TABLE IF EXISTS user");
				db.execSQL("DROP TABLE IF EXISTS buy");
				db.execSQL(CREATE_TABLE_USER);
				db.execSQL(CREATE_TABLE_BUY);
				db.execSQL(INSERT_TABLE_USER);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			try{
				db.execSQL("DROP TABLE IF EXISTS user");
				db.execSQL("DROP TABLE IF EXISTS buy");
				db.execSQL(CREATE_TABLE_USER);
				db.execSQL(CREATE_TABLE_BUY);
				db.execSQL(INSERT_TABLE_USER);
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