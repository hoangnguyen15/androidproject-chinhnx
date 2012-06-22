package com.chinhnx.objects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class ReadData {
	private static String DB_PATH = "/data/data/com.krazevina.story/databases/";
	private static final String DATABASE_NAME="AnhCoThichNuocMyKhong.mp3";
	private static final int DATABASE_VERSION=13;
	
	private SQLiteDatabase mSqlDatabase;
	private SQLiteRssHelper sqlitehelper;
	
	public ReadData(Context context) {
		sqlitehelper=new SQLiteRssHelper(context);
		this.mSqlDatabase=sqlitehelper.getWritableDatabase();
		if(mSqlDatabase.getVersion()!=DATABASE_VERSION)
			sqlitehelper.onUpgrade(mSqlDatabase, mSqlDatabase.getVersion(), DATABASE_VERSION);
	}

	private class SQLiteRssHelper extends SQLiteOpenHelper 
	{
	    private Context myContext;
	    public SQLiteRssHelper(Context context) {
	    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        this.myContext = context;
	    }
	    
	    @Override
		public synchronized SQLiteDatabase getWritableDatabase() {
			createDataBase();
			return super.getWritableDatabase();
		}
	 
	    public void createDataBase(){
	    	boolean dbExist = checkDataBase();
	    	if(dbExist){
	    	}else{
	        	try {
	    			copyDataBase();
	    		} catch (IOException e) {
//	    			e.printStackTrace();
	        	}
	    	}
	    }
	 
	    private boolean checkDataBase(){
	    	SQLiteDatabase checkDB = null;
	    	try{
	    		String myPath = DB_PATH + DATABASE_NAME;
	    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	    	}catch(SQLiteException e){
	    		e.printStackTrace();
	    	}
	 
	    	if(checkDB != null){
	    		checkDB.close();
	    	}
	    	return checkDB != null ? true : false;
	    }
	 
	    private void copyDataBase() throws IOException{
	    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
	    	String outFileName = DB_PATH + DATABASE_NAME;
	    	File f = new File(outFileName);
	    	f.getParentFile().mkdirs();
	    	f.createNewFile();
	    	OutputStream myOutput = new FileOutputStream(f);
	 
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	    }
		@Override
		public void onCreate(SQLiteDatabase db) {
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				copyDataBase();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void recycle(){
		if(mSqlDatabase!=null)mSqlDatabase.close();
		if(sqlitehelper!=null)sqlitehelper.close();
	}
	
	//Get Data
	public Vector<Story>getData(){
		Vector<Story>ret = new Vector<Story>();
		Cursor c = mSqlDatabase.query("story", new String[]{
				"rowid","tieude","noidung"}, null, null, null, null, null);
		if(c==null)return ret;
		Story story;
		c.moveToFirst();
		for(int i=0;i<c.getCount();i++){
			story = new Story();
			story.id = c.getInt(c.getColumnIndex("rowid"));
			story.title = c.getString(c.getColumnIndex("tieude"));
			story.content = c.getString(c.getColumnIndex("noidung"));
			
			ret.add(story);
			c.moveToNext();
		}
		c.close();
		return ret;
	}
}
