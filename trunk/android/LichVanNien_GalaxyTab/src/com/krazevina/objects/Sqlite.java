package com.krazevina.objects;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Sqlite {
	private static final String DATABASE_NAME = "Calendar";
	private static final String DATABASE_TABLE = "alarm";
	private static final int DATABASE_VERSION = 6;
	String[] allrow = new String[] { "_id", "title", "content", "alarmtime",
			"alarmdate", "alarmtone", "repeatstyle", "calendarstyle",
			"alarmbefore" ,"alarmst"};

	private static final String TAG = "Staff_DbAdapter";

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_CREATE1 = "create table setting ( alarmbefore integer, "
			+ "alarmstyle integer, repeatstyle integer);";

	private static final String DATABASE_CREATE = "create table alarm (_id integer primary key autoincrement, title text null, "
			+ "content text not null, "
			+ " alarmtime text, alarmdate text, alarmbefore integer, "
			+ "alarmtone text, repeatstyle integer, calendarstyle integer, alarmst integer);";

	private static final String CREATE_TABLE_WALLPAPER = "create table wallpaper (_id integer primary key autoincrement, codewallpaper integer, pathwallpaper text);";

	public static final String INSERT_TABLE_WALLPAPER = "Insert into wallpaper(codewallpaper) values (0);";

	private final Context mCtx;

	public Sqlite(Context ctx) {
		this.mCtx = ctx;
		mDbHelper = new DatabaseHelper(ctx);
		mDb = mDbHelper.getWritableDatabase();
		if (mDb.getVersion() != DATABASE_VERSION)
			mDbHelper.onUpgrade(mDb, mDb.getVersion(), DATABASE_VERSION);
		Log.d("ZZZZZZZ", "" + (mDb != null));
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DATABASE_CREATE);
			db.execSQL(DATABASE_CREATE1);
			db.execSQL(CREATE_TABLE_WALLPAPER);
			db.execSQL(INSERT_TABLE_WALLPAPER);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS alarm");
			db.execSQL("DROP TABLE IF EXISTS setting");
			db.execSQL("DROP TABLE IF EXISTS wallpaper");
			onCreate(db);
		}
	}

	public void close() {
		mDbHelper.close();
	}

	// do something with database
	public long INSERTSTAFF(String tit, String content, String time, String date, 
    		int alarmbefore, int calstyle,int repeatstyle,String alarmtone,int als)
    {
        //return new rowid if seccessfully, return -1 if else
        mDb.execSQL("insert into alarm(title,content,alarmtime,alarmdate,alarmbefore,calendarstyle,repeatstyle,alarmtone,alarmst)" +
        		" values ('"+tit+"','"+content+"','"+time+"','"+date+"',"+alarmbefore+","+calstyle+","+repeatstyle+",'"+alarmtone+"',"+als+")");
        return 1;
    }   

	public boolean UPDATESTAFF(long rowId,String tit, String content, String time, String date, 
    		int alarmbefore, int calstyle,int repeatstyle,String alarmtone,int als)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("title", tit);
        initialValues.put("content", content);
        initialValues.put("alarmtime", time);
        initialValues.put("alarmdate", date);
        initialValues.put("alarmbefore", alarmbefore);
        initialValues.put("calendarstyle", calstyle);
        initialValues.put("repeatstyle", repeatstyle);
        initialValues.put("alarmtone", alarmtone);
        initialValues.put("alarmst", als);
        
        return mDb.update(DATABASE_TABLE, initialValues, "_id" + "=" + rowId, null) > 0;
    }

	public boolean DELETESTAFF(long rowId) {
		mDb.execSQL("Delete from alarm where _id="+rowId);
		return true;
	}

	public boolean DELETEALLSTAFF() {

		return mDb.delete(DATABASE_TABLE, null, null) > 0;
	}

	public Reminders getReminders()
    {
    	Reminders res = new Reminders();
    	Cursor c = mDb.query(true, DATABASE_TABLE, allrow, null, null, null, null, null, null);
    	
	    if(c.getCount()==0)
	    {
	    	c.close();
	    	return res;
	    }
        c.moveToFirst();
        
        Reminder r;
        
        Calendar cal = Calendar.getInstance();
        for(int i=0;i<c.getCount();i++)
        {
        	String[] time = c.getString(c.getColumnIndex("alarmtime")).split("\\:");
        	String[] date = c.getString(c.getColumnIndex("alarmdate")).split("/");
        	Log.d("sql:", ""+c.getString(c.getColumnIndex("alarmtime"))+"#"+c.getString(c.getColumnIndex("alarmdate")));
        	String cont = c.getString(c.getColumnIndex("content"));
        	String tit = c.getString(c.getColumnIndex("title"));
        	String alarmtone = c.getString(c.getColumnIndex("alarmtone"));
        	int repeatstyle = c.getInt(c.getColumnIndex("repeatstyle"));
        	int calendarstyle = c.getInt(c.getColumnIndex("calendarstyle"));
        	int alarmbefore = c.getInt(c.getColumnIndex("alarmbefore"));
        	int id = c.getInt(c.getColumnIndex("_id"));
        	int als = c.getInt(c.getColumnIndex("alarmst"));
        	cal = Calendar.getInstance();
        	cal.set(Integer.parseInt(date[2]), Integer.parseInt(date[1])-1, Integer.parseInt(date[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        	
        	r = new Reminder(id,cal,tit,cont, alarmbefore, calendarstyle, repeatstyle, alarmtone, als);
        	res.reminder.add(r);
        	c.moveToNext();
        }
        c.close();
    	return res;
    }


	public void add(Reminder r) {
		INSERTSTAFF(r.title, r.content, r.getTime(), r.getDate(), r.alarmbefore,
				r.calendarstyle, r.repeatstyle, r.alarmtone, r.alarmstyle);
		Log.d("ZZZZZZ", "sql add:" + r.getTime() + "#" + r.getDate());
	}

	public void del(Reminder r) {
		Log.d("Delete", ""+r.id);
		DELETESTAFF(r.id);
	}

	public void update(Reminder r) {
		UPDATESTAFF(r.id, r.title, r.content, r.getTime(), r.getDate(),
				r.alarmbefore, r.calendarstyle, r.repeatstyle, r.alarmtone, r.alarmstyle);
	}


	// Get - Set wallpaper

	public int getWallpaper() {
		Cursor c;
		try {
			c = mDb.query("wallpaper", new String[] { "codewallpaper" }, null, null,
					null, null, null);
			c.moveToFirst();
			String s = c.getString(0);
			c.close();
			return Integer.parseInt(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	public String getPathWallpaper() {
		Cursor c;
		try {
			c = mDb.query("wallpaper", new String[] { "pathwallpaper" }, null, null,
					null, null, null);
			c.moveToFirst();
			String s = c.getString(0);
			c.close();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean setWallpaper(int i,String path) {
		try {
			mDb.execSQL("update wallpaper set codewallpaper=" + i);
			mDb.execSQL("update wallpaper set pathwallpaper="+"'" + path+"'");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}