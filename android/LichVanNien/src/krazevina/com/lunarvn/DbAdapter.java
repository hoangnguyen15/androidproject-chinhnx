/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package krazevina.com.lunarvn;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbAdapter {
	
	public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_START="start";    
    public static final String KEY_END="end";
    public static final String KEY_DATE="date";
    public static final String KEY_RINGTONE="ringtone";
    public static final String KEY_STATE="state";
    public static final String KEY_REMINDER="reminder";
    public static final String KEY_REPEAT="repeat";
    public static final String KEY_TIMESTYLE="style";
    public static final String KEY_URI="uri";
    
    private static final String DATABASE_NAME = "Calendar";
    private static final String DATABASE_TABLE = "Appointments";
    private static final int DATABASE_VERSION = 2;

    private static final String TAG = "Staff_DbAdapter";
    
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    private static final String DATABASE_CREATE =
            "create table Appointments (_id integer primary key autoincrement, "
                    + "title text not null, content text not null, "
                    +"start text not null, end text, date text, ringtone text, state long, reminder text, repeat text, style text,uri text);";

    private final Context mCtx;
    
    public DbAdapter(Context ctx) 
    {
        this.mCtx = ctx; 
    }
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {

        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);            
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);            
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Appointments");
            onCreate(db);
        }
        public void onDELETEDATABASE(SQLiteDatabase db)
        {
        	 db.execSQL("DROP TABLE IF EXISTS Appointments");
        }
    }
    
    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();       
        return this;
    }
    
    public void close() 
    {
        mDbHelper.close();        
    }
    // do something with database
    public long INSERTSTAFF(String title, String content, String start, String end, String date, String ringtone, int state,String reminder,String repeat,String style,String uri )
    {
		ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_CONTENT, content);
        initialValues.put(KEY_START, start);
        initialValues.put(KEY_END, end);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_RINGTONE, ringtone);
        initialValues.put(KEY_STATE, state);
        initialValues.put(KEY_REMINDER, reminder);
        initialValues.put(KEY_REPEAT, repeat);
        initialValues.put(KEY_TIMESTYLE, style);
        initialValues.put(KEY_URI,uri);
        
        //return new rowid if seccessfully, return -1 if else        
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }   
    public boolean UPDATESTAFF(long rowId, String title, String content, String start, String end, String date, String ringtone, int state,String reminder,String repeat,String style,String uri)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_CONTENT, content);
        initialValues.put(KEY_START, start);
        initialValues.put(KEY_END, end);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_RINGTONE, ringtone);
        initialValues.put(KEY_STATE, state);
        initialValues.put(KEY_REMINDER, reminder);
        initialValues.put(KEY_REPEAT, repeat); 
        initialValues.put(KEY_TIMESTYLE, style);
        initialValues.put(KEY_URI,uri);
        
        return mDb.update(DATABASE_TABLE, initialValues, KEY_ROWID + "=" + rowId, null) > 0;
    }    
    public boolean DELETESTAFF(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    public boolean DELETEALLSTAFF() {

        return mDb.delete(DATABASE_TABLE, null, null) > 0;
    }
    public Cursor fetchAllStaffs() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                KEY_CONTENT,KEY_START,KEY_END,KEY_DATE,KEY_RINGTONE,KEY_STATE,KEY_REMINDER,KEY_REPEAT,KEY_TIMESTYLE,KEY_URI}, null, null, null, null, null);
    }

    public Cursor fetchStaff(long rowId) 
    throws SQLException 
    {
        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                        KEY_CONTENT,KEY_START,KEY_END,KEY_DATE,KEY_RINGTONE,KEY_STATE,KEY_REMINDER,KEY_REPEAT,KEY_TIMESTYLE,KEY_URI}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if(mCursor.getCount()==0)
        	return null;
        else
        {
            mCursor.moveToFirst();
            return mCursor;
        }
    }    
    public Cursor fetchAllStaffs(int state) 
    throws SQLException 
    {
        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                        KEY_CONTENT,KEY_START,KEY_END,KEY_DATE,KEY_RINGTONE,KEY_STATE,KEY_REMINDER,KEY_REPEAT,KEY_TIMESTYLE,KEY_URI}, KEY_STATE + "=" + state, null,
                        null, null, null, null);
        if(mCursor.getCount()==0)
        	return null;
        else
        {
            mCursor.moveToFirst();
            return mCursor;
        }
        
    }    
    public Cursor App_Status(int state,long rowid) 
    throws SQLException 
    {    	
        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                        KEY_CONTENT,KEY_START,KEY_END,KEY_DATE,KEY_RINGTONE,KEY_STATE,KEY_REMINDER,KEY_REPEAT,KEY_TIMESTYLE,KEY_URI},KEY_ROWID+"=" + rowid + " AND "+ KEY_STATE + "=" + state, null,
                        null, null, null, null);
        		
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public int[][] GetDateFromDatabase()
    {    	     
          //Set thời gian - YY/MM/DD HH:MM
          //Do tháng từ 0 - 11 nên phải trừ đi 1 
    	 int[][] datetime=null;
	  	  int[][] datetime1=null;
	  	  	try{
	  	  	Cursor mCursor=fetchAllStaffs(1);    	  	 
    	      int dd=0;
    	      int mm=0;
    	      int yyyy=0;
    	      int _hour=0;
    	      int _minutes=0;
    	      int _reminder=0;    	      
    	  	  int i=0,Rowid=0;
    	  	  
    	  
    	  	  if(mCursor!=null)
    	  	  {
    	  		  datetime= new int[mCursor.getCount()][8];
    	  		  	boolean flag=true;
    	  		  	  while(flag)
    	  		  	  {    	  		  	  
	    	  		  	  Rowid= mCursor.getInt(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_ROWID));
	    	  		  	  datetime[i][7]=Rowid;	    	  		  	 	    	  		  	  
	    		  		  String date = mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_DATE));
	    		  		  String[]datetem=date.split("/");
	    			      dd=Integer.valueOf(datetem[0]);
	    			      mm=Integer.valueOf(datetem[1]);        
	    			      yyyy= Integer.valueOf(datetem[2]);
	    			      datetime[i][0]=yyyy;
	    			      datetime[i][1]=mm;
	    			      datetime[i][2]=dd;   
	    			      
	    			      String start= mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_START));
	    			      start=start.trim();
	    			      String []starttem=start.split(":");
	    			      _hour=Integer.valueOf(starttem[0]);
	    			      _minutes=Integer.valueOf(starttem[1]);
	    			      datetime[i][5]=_hour;
	    			      datetime[i][6]=_minutes;	    			     
	    			      String reminder=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
	    			      reminder=reminder.trim();
	    			      _reminder=Integer.valueOf(reminder);	    			     
	    			      String style=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_TIMESTYLE));
	    			      style=style.trim();
	    			      if(_reminder>_minutes)
	    			      {
	    			      	_minutes=60-(_reminder-_minutes);    		
	    				      	if(style=="24H")
	    				      	{  
	    				      		
	    				      		if(_hour==1)
	    				      			_hour=24;
	    				      		else
	    				      			_hour=_hour-1;
	    				      	}
	    				      	else
	    				      	{
	    				      		if(_hour==1)
	    				      			_hour=12;
	    				      		else
	    				      			_hour=_hour-1;
	    				      	}
	    				      //	_minutes=(_minutes+_reminder)-60;
	    				      	
	    				   }
	    			      else
	    			      	_minutes=_minutes-_reminder;
	    			      
	    			  	datetime[i][3]=_hour;
	    			  	datetime[i][4]=_minutes;
	    			  	i++;    
	    			  	flag=mCursor.moveToNext();
	    	  		  }
    	  		  	  i=0;
    	  		      SortedList sortlist= new SortedList();
    	  		  	  for(int k=0;k< mCursor.getCount();k++)
    	  		  	  {
    	  		  		  Date a=new Date();
    	  		  		  Date b=new Date();
    	  		  		  a.setDate(datetime[k][2]);
    	  		  		  a.setMonth(datetime[k][1]);
    	  		  		  a.setYear(datetime[k][0]);
    	  		  		  a.setHours(datetime[k][3]);
    	  		  		  a.setMinutes(datetime[k][4]);   
    	  		  		  a.setSeconds(datetime[k][7]);
    	  		  		  sortlist.add(a);
    	  		  		  b.setDate(datetime[k][2]);    	  		  		  
	   	  		  		  b.setMonth(datetime[k][1]);
	   	  		  		  b.setYear(datetime[k][0]);
	   	  		  		  b.setHours(datetime[k][5]);
	   	  		  		  b.setMinutes(datetime[k][6]);   
	   	  		  		  b.setSeconds(datetime[k][7]);    	  		  		  
    	  		  		  sortlist.add(b);    	  		  		 
    	  		  	  }    	  		 
    	  		  	datetime1= new int[mCursor.getCount()*2][6];    	  		  	
    	  			for(i=0;i<mCursor.getCount()*2;i++)
    	  		  	{
    	  				
    	  		  		datetime1[i][0]=sortlist.get(i).getYear();
    	  		  		datetime1[i][1]=sortlist.get(i).getMonth();
    	  		  		datetime1[i][2]=sortlist.get(i).getDate();
    	  		  		datetime1[i][3]=sortlist.get(i).getHours();
    	  		  		datetime1[i][4]=sortlist.get(i).getMinutes();    	  		  		
    	  		  		datetime1[i][5]=sortlist.get(i).getSeconds();
    	  		  		String message= String.valueOf(datetime1[i][2])+"/"+String.valueOf(datetime1[i][1])+"/"+String.valueOf(datetime1[i][0])+":"+String.valueOf(datetime1[i][3])+"h "+String.valueOf(datetime1[i][4])+"phut"+ " id: "+String.valueOf(datetime1[i][5]);
    	  		  		Log.w("datatime["+i+ "]", message);
    	  		  	}
    	  	  }
    	  	  else
    	  	  {
    	  		  datetime1 = new int[1][1];
    	  		  datetime1[0][0]=-1;
    	  	  }
    	}catch (Exception e) {
			Log.e("Loi DBAdapter", e.toString());
		}
          return datetime1;
    }
  }