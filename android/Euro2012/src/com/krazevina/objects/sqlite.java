package com.krazevina.objects;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlite 
{
	private static String DB_PATH = "/data/data/com.krazevina.euro2012/databases/";
	private static final String DATABASE_NAME="euro2012.dbo";
	private static final int DATABASE_VERSION=1;
	
	public static final String CREATE_TABLE_GROUPS =" CREATE TABLE Groups("
		+" 	ID tinyint NOT NULL,"
		+" 	Name nvarchar(50) NULL,"
		+" 	TeamID smallint NULL,"
		+" 	Point tinyint NULL,"
		+" 	LooseScore tinyint NULL,"
		+" 	WinScore tinyint NULL,"
		+" 	Win tinyint NULL,"
		+" 	Lose tinyint NULL,"
		+" 	Draw tinyint NULL,"
		+" 	Status tinyint NULL)";

	public static final String CREATE_TABLE_BEGINNINGSTRATEGY = " CREATE TABLE BeginningStrategy("
		+" 	ID smallint IDENTITY(1,1) NOT NULL,"
		+" 	MatchID smallint NULL,"
		+" 	TeamID smallint NULL,"
		+" 	Detail nvarchar(350) NULL)";

	public static final String CREATE_TABLE_MATCHES = " CREATE TABLE Matches("
		+" 	ID smallint NULL,"
		+" 	GroupID smallint NULL,"
		+" 	FirstTeam smallint NULL,"
		+" 	SecondTeam smallint NULL,"
		+" 	Stadium nvarchar(50) NULL,"
		+" 	Start datetime NULL,"
		+" 	End datetime NULL,"
		+" 	FinalScore varchar(10) NULL,"
		+" 	FirstScore varchar(10) NULL,"
		+" 	SecondScore varchar(10) NULL,"
		+" 	MainReferee nvarchar(50) NULL,"
		+" 	FirstPickup int NULL,"
		+" 	SecondPickup int NULL,"
		+" 	Status tinyint NULL)";
	public static final String CREATE_TABLE_MATCHONLINE = " CREATE TABLE MatchOnline("
		+" 	ID smallint IDENTITY(1,1) NOT NULL,"
		+" 	MatchID smallint NULL,"
		+" 	TeamID smallint NULL,"
		+" 	PlayerID smallint NULL,"
		+" 	EventID smallint NULL,"
		+" 	Detail nvarchar(250) NULL,"
		+" 	MatchTime varchar(10) NULL,"
		+" 	Status tinyint NULL)";

	public static final String CREATE_TABLE_BETAHOUSES = " CREATE TABLE BetHouses("
		+" 	ID smallint IDENTITY(1,1) NOT NULL,"
		+" 	Name nvarchar(50) NULL,"
		+" 	Desc nvarchar(512) NULL,"
		+" 	Status tinyint NULL)";

	public static final String CREATE_TABLE_BETDETAIL=" CREATE TABLE BetDetail("
		+" 	ID smallint IDENTITY(1,1) NOT NULL,"
		+" 	MatchID smallint NULL,"
		+" 	BetHouseID smallint NULL,"
		+" 	Col1 varchar(10) NULL,"
		+" 	Col2 varchar(10) NULL,"
		+" 	Col3 varchar(10) NULL,"
		+" 	Status tinyint NULL)";

	public static final String CREATE_TABLE_EVENTS=" CREATE TABLE Events("
		+" 	ID tinyint NULL,"
		+" 	Name nchar(10) NULL,"
		+" 	Status bit NULL)";

	public static final String CREATE_TABLE_PLAYERS=" CREATE TABLE Players("
		+" 	ID smallint IDENTITY(1,1) NOT NULL,"
		+" 	TeamID smallint NULL,"
		+" 	Name nvarchar(50) NULL,"
		+" 	DOB smalldatetime NULL,"
		+" 	Height varchar(10) NULL,"
		+" 	Weight varchar(10) NULL,"
		+" 	Club nvarchar(50) NULL,"
		+" 	Position nvarchar(50) NULL,"
		+" 	Number tinyint NULL,"
		+" 	Score tinyint NULL,"
		+" 	Status tinyint NULL,"
		+" 	PlayerTip varchar(50) NULL)";

	public static final String CREATE_TABLE_TEAMS=" CREATE TABLE Teams("
		+" 	ID smallint IDENTITY(1,1) NOT NULL,"
		+" 	BongdasoID int NULL,"
		+" 	Name nvarchar(50) NULL,"
		+" 	Flag nvarchar(250) NULL,"
		+" 	Uniform1 nvarchar(250) NULL,"
		+" 	Uniform2 nvarchar(250) NULL,"
		+" 	FifaRanking tinyint NULL,"
		+" 	Coach nvarchar(50) NULL,"
		+" 	Desc nvarchar(512) NULL,"
		+" 	AttendTimes nvarchar(512) NULL,"
		+" 	status tinyint NULL)";

	private SQLiteDatabase mSqlDatabase;
	private SQLiteRssHelper sqlitehelper;
	
	public sqlite(Context context) {
		sqlitehelper=new SQLiteRssHelper(context);
		this.mSqlDatabase=sqlitehelper.getWritableDatabase();
		if(mSqlDatabase.getVersion()!=DATABASE_VERSION)
			sqlitehelper.onUpgrade(mSqlDatabase, mSqlDatabase.getVersion(), DATABASE_VERSION);
	}


	/*SQLite Helper use to help open connection to database*/
	private class SQLiteRssHelper extends SQLiteOpenHelper 
	{
//		public SQLiteRssHelper(Context context) {
//			super(context, DATABASE_NAME, null, DATABASE_VERSION);
//		}
//		@Override
//		public void onCreate(SQLiteDatabase db) 
//		{
//			try{
//				db.execSQL(CREATE_TABLE_BEGINNINGSTRATEGY);
//				db.execSQL(CREATE_TABLE_BETAHOUSES);
//				db.execSQL(CREATE_TABLE_BETDETAIL);
//				db.execSQL(CREATE_TABLE_EVENTS);
//				db.execSQL(CREATE_TABLE_GROUPS);
//				db.execSQL(CREATE_TABLE_MATCHES);
//				db.execSQL(CREATE_TABLE_MATCHONLINE);
//				db.execSQL(CREATE_TABLE_PLAYERS);
//				db.execSQL(CREATE_TABLE_TEAMS);
//			}catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//
//		@Override
//		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
//		{
//			try{
//				db.execSQL(CREATE_TABLE_BEGINNINGSTRATEGY);
//				db.execSQL(CREATE_TABLE_BETAHOUSES);
//				db.execSQL(CREATE_TABLE_BETDETAIL);
//				db.execSQL(CREATE_TABLE_EVENTS);
//				db.execSQL(CREATE_TABLE_GROUPS);
//				db.execSQL(CREATE_TABLE_MATCHES);
//				db.execSQL(CREATE_TABLE_MATCHONLINE);
//				db.execSQL(CREATE_TABLE_PLAYERS);
//				db.execSQL(CREATE_TABLE_TEAMS);
//			}catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//			db.setVersion(newVersion);
//		}
		
	    private SQLiteDatabase myDataBase; 
	    private Context myContext;
	 
	    /**
	     * Constructor
	     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	     * @param context
	     */
	    public SQLiteRssHelper(Context context) {
	 
	    	super(context, DATABASE_NAME, null, 1);
	        this.myContext = context;
	    }	
	 
	    public void createDataBase() throws IOException{
	    	boolean dbExist = checkDataBase();
	    	if(dbExist){
	    	}else{
	        	this.getReadableDatabase();
	        	try {
	    			copyDataBase();
	    		} catch (IOException e) {
	        		throw new Error("Error copying database");
	        	}
	    	}
	    }
	 
	    private boolean checkDataBase(){
	    	SQLiteDatabase checkDB = null;
	    	try{
	    		String myPath = DB_PATH + DATABASE_NAME;
	    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	    	}catch(SQLiteException e){
	    	}
	 
	    	if(checkDB != null){
	    		checkDB.close();
	    	}
	    	return checkDB != null ? true : false;
	    }
	 
	    private void copyDataBase() throws IOException{
	    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
	    	String outFileName = DB_PATH + DATABASE_NAME;
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	 
	    }
	 
	    public void openDataBase() throws SQLException{
	        String myPath = DB_PATH + DATABASE_NAME;
	    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	    }
	 
	    @Override
		public synchronized void close() {
			if(myDataBase != null)
			    myDataBase.close();
			super.close();
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				createDataBase();
			} catch (IOException e) {
				e.printStackTrace();
			}
			openDataBase();
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
	
	public void recycle()
	{
		if(mSqlDatabase!=null)mSqlDatabase.close();
		if(sqlitehelper!=null)sqlitehelper.close();
	}
}