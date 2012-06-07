package com.krazevina.objects;

import java.io.File;
import java.io.FileOutputStream;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import com.krazevina.beautifulgirl.R;
import com.krazevina.beautifulgirl.Single;
import com.krazevina.webservice.CallWebService;

public class sqlite 
{
	private static final String DATABASE_NAME="gx.db";
	private static final int DATABASE_VERSION=2;
	static long maxcache = 0;
	static long cachesize = 0;
	
	public static final String CREATE_TABLE_THUMB="create table thumb(" +
			" name nvarchar(100) null," +
			" filename nvarchar(200) null, " +
			" width integer null, " +
			" height integer null)";
	public static final String CREATE_TABLE_IMG="create table img(" +
			" name nvarchar(200) null," +
			" filename nvarchar(200) null, " +
			" url nvarchar(300) null, " +
			" width integer null, " +
			" height integer null, " +
			" user nvarchar(200) null, " +
			" favor integer null, " +
			" rate integer null " +
			")";
	public static final String CREATE_TABLE_IMG_UP="create table imgup(" +
			" name nvarchar(100) null)";
	public static final String CREATE_TABLE_FAVOR="create table favor(" +
	" user nvarchar(100) null, idimg integer null)";
	
	public static final String CREATE_TABLE_FOLLOW="create table follow(" +
	" user nvarchar(100) null, iduser integer null)";
	
	public static final String CREATE_TABLE_USER_LIST="create table userlist(" +
	" user nvarchar(100) null)";
	
	public static final String CREATE_TABLE_USER="create table user(" +
			" userid integer null," +
			" username nvarchar(100) null," +
			" password nvarchar(100) null," +
			" name nvarchar(100) null," +
			" email nvarchar(100) null," +
			" phone nvarchar(100) null," +
			" yahoo nvarchar(100) null," +
			" autologin integer null," + //#0 : auto
			" savepassword integer null," + //#0 : save
			" logedout integer null," + //#0 : logedout
			" maxcache integer null," +
			" sizecache nvarchar(50) null)";
	
	public static final String INSERT_TABLE_USER="INSERT INTO user values(-1,'','','','','','',0,0,0,50,'0')";
	

	
	private SQLiteDatabase mSqlDatabase;
	private SQLiteRssHelper sqlitehelper;
	
	public sqlite(Context context) {
		sqlitehelper=new SQLiteRssHelper(context);
		this.mSqlDatabase=sqlitehelper.getWritableDatabase();
		if(mSqlDatabase.getVersion()!=DATABASE_VERSION)
			sqlitehelper.onUpgrade(mSqlDatabase, mSqlDatabase.getVersion(), DATABASE_VERSION);
	}
	
	public String getKey()
	{
		Cursor c = mSqlDatabase.query("key", new String[]{"key"}, null, null, null, null, null);
		return c.getString(0);
	}
	public void setKey(String key)
	{
		mSqlDatabase.execSQL("Update key set key='"+key+"'");
	}
	
	public void setMaxCache(int max)
	{
		mSqlDatabase.execSQL("Update user set maxcache="+max);
		maxcache = max;
		return;
	}
	public int getMaxCache()
	{
		Cursor c = mSqlDatabase.query("user", new String[]{"maxcache"}, null, null, null, null, null);
		c.moveToFirst();
		int i = c.getInt(0);
		c.close();
		return i;
	}
	public long getSizeCache()
	{
		Cursor c = mSqlDatabase.query("user", new String[]{"sizecache"}, null, null, null, null, null);
		c.moveToFirst();
		try{
			long i = Long.parseLong(c.getString(0));
		c.close();
		return i;
		}catch (Exception e) {
			return 0;
		}
	}
	public void setSizeCache(long l)
	{
		mSqlDatabase.execSQL("Update user set sizecache='"+l+"'");
		cachesize = l;
	}
	
	void del(final File dir)
	{
		new Thread(new Runnable() {
			public void run() {
				try{
					if(dir.isFile())dir.delete();
					else
					{
					    File[] fileList = dir.listFiles();
					    for(int i = 0; i < fileList.length; i++) 
					    {
					        if(fileList[i].isDirectory()) {
					            del(fileList[i]);
					        } else {
					        	fileList[i].delete();
					        }
					    }
					}}catch (Exception e) {
						e.printStackTrace();
					}
			}
		}).start();
	}
	
	public void insertThumb(Image i, Activity a)
	{
		if(i.getId()<0)return;
		try {
			if(maxcache<=0)maxcache = getMaxCache();
			if(cachesize<=0)cachesize = getSizeCache();
			
			if(cachesize>maxcache*1024l*1024l)
			{
				String s = a.getSharedPreferences("dir", Context.MODE_PRIVATE).getString("dir", Environment.getExternalStorageDirectory()+"/GX")+"/thumb";
				del(new File(s));
				cachesize = 0;
				setSizeCache(cachesize);
			}
			
			File ff = new File(Environment.getExternalStorageDirectory()+
		    		   "/GX/thumb/");
			ff.mkdirs();
			File f = new File(ff,""+i.getId());
			f.createNewFile();
			
	        FileOutputStream out = new FileOutputStream(f);
	        if(i.getName().endsWith("jpg")||i.getName().endsWith("jpeg")||i.getName().endsWith("jpe"))
	        	i.getThumbBm().compress(Bitmap.CompressFormat.JPEG, 90, out);
	        else i.getThumbBm().compress(Bitmap.CompressFormat.PNG, 90, out);
	        
	        File f1 = new File(ff,""+i.getId());
	        cachesize += f1.length();
	        setSizeCache(cachesize);
	        
	        Cursor c = mSqlDatabase.query("thumb", new String[]{"filename"}, "filename='"+i.getId()+"'", null, null, null, null);
	        if(c.getCount()<=0)
	        mSqlDatabase.execSQL("INSERT INTO thumb values(" +
					"'" + i.getName() + "'," +
					"'" + i.getId() + "'," +
					"" + i.getThumbBm().getWidth() + "," +
					"" + i.getThumbBm().getHeight() + ")");
	        c.close();
		} catch (Exception e) {
	       e.printStackTrace();
		}
	}
	
	
	
	public String getUploadName()
	{
		Cursor c;
		c = mSqlDatabase.query("imgup", new String[]{"name"}, null, null, null, null, null);
		if(c.getCount()<=0)
		{
			c.close();
			return "0";
		}
		int max=0,cur;
		c.moveToFirst();
		for(int i=0;i<c.getCount();i++)
		{
			cur = Integer.parseInt(c.getString(0)); 
			if(cur>max)max = cur;
		}
		return ""+(max+1);
	}
	
	public void upload(String s)
	{
		mSqlDatabase.execSQL("INSERT INTO imgup values(" +"'" + s + "')");
	}
	
	public void favor(Image i)
	{
		if(i.getId()<0)return;
		try{
			if(Global.username!=null&&Global.username.length()>0&&i.getId()>=0)
			{
				mSqlDatabase.execSQL("Insert Into favor values('" +Global.username+"'"+
						","+i.getId()+")");
				i.favor = 1;
				i.setRate(i.getRate()+1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void unfavor(Image i)
	{
		if(i.getId()<0)return;
		try{
			mSqlDatabase.execSQL("Delete From favor Where user='"+Global.username+"' and " +
					" idimg="+i.getId());
			i.favor = 0;
			if(i.getRate()>0)
				i.setRate(i.getRate()-1);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getfavor(int id)
	{
		try{
			Cursor c = mSqlDatabase.query("favor", new String[]{"user","idimg"}, " user='"+Global.username+"' and idimg="+id, null, null, null, null);
			if(c.getCount()<=0)
			{
				c.close();
				return 0;
			}
			else
			{
				c.close();
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void follow(String id)
	{
		if(id.length()<=0)return;
		try{
			if(Global.username!=null&&Global.username.length()>0)
				mSqlDatabase.execSQL("Insert Into follow values('" +Global.username+"'"+",'"+id+"')");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void unfollow(String id)
	{
		if(id.length()<=0)return;
		try{
			mSqlDatabase.execSQL("Delete From follow Where user='"+Global.username+"' and " +
					" iduser='"+id+"'");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getfollow(String id)
	{
		try{
			Cursor c = mSqlDatabase.query("follow", new String[]{"user","iduser"}, " user='"+Global.username+"' and iduser='"+id+"'", null, null, null, null);
			if(c.getCount()<=0)
			{
				c.close();
				return false;
			}
			else
			{
				c.close();
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void favor(final Image i,final Single c,final Handler h,final View v)
	{
		if(i.getId()<0)return;
		
		new Thread(new Runnable() {
			public void run() {
				CallWebService call = new CallWebService(c);
				String ret = call.imageVote(i.getId()).toLowerCase();
				if(ret.length()>0)
				{
					try{
						mSqlDatabase.execSQL("Insert Into favor values('" +Global.username+"'"+
								","+i.getId()+")");
						i.favor = 1;
						if(ret.startsWith("true"))
							i.setRate(i.getRate()+1);
						h.post(new Runnable() {
							public void run() {
								try{
									Toast.makeText(c, R.string.favorited, 0).show();
									v.postInvalidate();
									c.showUploader();
								}catch (Exception e) {
								}
							}
						});
					}catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					h.post(new Runnable() {
						public void run() {
							try{
								Toast.makeText(c, R.string.noconnect, 1).show();
								i.favor = 1;
								v.postInvalidate();
								c.showUploader();
							}catch (Exception e) {
							}
						}
					});
				}
			}
		}).start();
	}
	public void unfavor(final Image i,final Single c,final Handler h,final View v)
	{
		if(i.getId()<0)return;
		new Thread(new Runnable() {
			public void run() {
				CallWebService call = new CallWebService(c);
				String ret = call.imageUnVote(i.getId()).toLowerCase(); 
				if(ret.length()>0)
				{
					try{
						mSqlDatabase.execSQL("Delete From favor Where user='"+Global.username+"' and " +
								" idimg="+i.getId());
						i.favor = 0;
						if(i.getRate()>0&&ret.startsWith("true"))
							i.setRate(i.getRate()-1);
						h.post(new Runnable() {
							public void run() {
								try{
									Toast.makeText(c, R.string.unfavorited, 0).show();
									v.postInvalidate();
									c.showUploader();
								}catch (Exception e) {
								}
							}
						});
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				else
				{
					h.post(new Runnable() {
						public void run() {
							try{
								Toast.makeText(c, R.string.noconnect, 1).show();
								i.favor = 0;
								v.postInvalidate();
								c.showUploader();
							}catch (Exception e) {
							}
						}
					});
				}
			}
		}).start();
	}
	
	
	public static String getThumb(int id)
	{
       return (Environment.getExternalStorageDirectory()+"/GX/thumb/"+id);
	}
	
	public void getImg(Image i)
	{
		if(i.getId()<0)return;
		Cursor c = null;
		try {
			c = mSqlDatabase.query("img", new String[]{"filename","user","favor","rate"}, null, null, null, null, null);
			c.moveToFirst();
			i.setId(Integer.parseInt(c.getString(0)));
			i.user = c.getString(1);
			i.favor = c.getInt(2);
			i.setRate(c.getInt(3));
			c.close();
		}catch (Exception e) {
			if(c!=null)c.close();
		}
	}
	
	public void insertImg(Image i)
	{
		if(i.getId()<0)return;
		try {
	       mSqlDatabase.execSQL("INSERT INTO img values(" +
					"'" + i.getName() + "'," +
					"'" + i.getId() + "'," +
					"'" + i.getUrl() +"'," +
					"" + i.getBm().getWidth() + "," +
					"" + i.getBm().getHeight() + "," +
					"'" + i.user +"'," +
					"" + i.favor + ","+i.getRate() +")");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkImg(int id)
	{
		Cursor c = mSqlDatabase.query("img", new String[]{"filename"}, "filename='"+id+"'", null, null, null, null);
		
		if(c.getCount()>0)
		{
			c.close();
			return true;
		}
		c.close();
		return false;
	}
	
	public boolean checkThumb(int id)
	{
		Cursor c = mSqlDatabase.query("thumb", new String[]{"filename"}, "filename='"+id+"'", null, null, null, null);
		
		if(c.getCount()>0)
		{
			c.close();
			return true;
		}
		c.close();
		return false;
	}
	
	
	//Login
	public boolean getAutologin(){
		Cursor c;
		try{
			c = mSqlDatabase.query("user", new String[]{"autologin"}, null, null, null, null, null);
			c.moveToFirst();
			int i  = c.getInt(0);
			c.close();
			if(i == 0){
				return false;
			}else{
				return true;
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean getSavepassword(){
		Cursor c;
		try{
			c = mSqlDatabase.query("user", new String[]{"savepassword"}, null, null, null, null, null);
			c.moveToFirst();
			int i  = c.getInt(0);
			c.close();
			if(i == 0){
				return false;
			}else{
				return true;
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setAutoLogin(boolean auto) {
		try {
			if(auto){
				mSqlDatabase.execSQL("update user set autologin=1");
			}else{
				mSqlDatabase.execSQL("update user set autologin=0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setSavePassword(boolean save) {
		try {
			if(save){
				mSqlDatabase.execSQL("update user set savepassword=1");
			}else{
				mSqlDatabase.execSQL("update user set savepassword=0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setLogout() {
		try {
			// action == 1 --> btnLogout
			// action == 2 --> Login
			// action == 3 --> Logout
			
//			if(action == 1){
				mSqlDatabase.execSQL("update user set autologin= 0");
//			}else if(action == 2){
//				mSqlDatabase.execSQL("update user set logedout= 1");
//			}else if(action == 3){
//				mSqlDatabase.execSQL("update user set logedout= 0");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getLogout(){
		Cursor c;
		c = mSqlDatabase.query("user", new String[]{"logedout"}, null, null, null, null, null);
		c.moveToFirst();
		String logout = c.getString(0);
		c.close();
		return logout;
	}
	
	public String getUsername(){
		String username = "";
		try{
		Cursor c;
		c = mSqlDatabase.query("user", new String[]{"username"}, null, null, null, null, null);
		c.moveToFirst();
		username = c.getString(0);
		c.close();
		
		}catch (Exception e) {
		}
		return username;
	}
	
	public String getPassword(){
		String password="";
		try {
			Cursor c;
			c = mSqlDatabase.query("user", new String[]{"password"}, null, null, null, null, null);
			c.moveToFirst();
			password = c.getString(0);
			c.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}
	
	public void setUserId(int id){
		try {
			mSqlDatabase.execSQL("update user set userid='" + id + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getUserId(){
		int id=-1;
		try {
			Cursor c;
			c = mSqlDatabase.query("user", new String[]{"userid"}, null, null, null, null, null);
			c.moveToFirst();
			id = Integer.valueOf(c.getString(0));
			c.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public void setUserPass(String username, String password){
		try {
			mSqlDatabase.execSQL("update user set username='" + username
					+ "' , password='" + password+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getInfoUser(){
		try{
			Cursor c;
			c = mSqlDatabase.query("user", new String[]{"name","email","phone","yahoo"}, null, null, null, null, null);
			c.moveToFirst();
			Global.name = c.getString(0);
			Global.email = c.getString(1);
			Global.phone = c.getString(2);
			Global.yahoo = c.getString(3);
			c.close();
		}catch (Exception e) {
		}
	}
	
	public void updateInfo(String name, String email, String phone, String yahoo) {
		try {
			mSqlDatabase.execSQL("update user set name='" + name
					+ "' , email='" + email
					+ "' , phone='" + phone
					+ "' , yahoo='" + yahoo+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//AutocompleteTextView
	
	public void insertUser(String user){
		this.mSqlDatabase=sqlitehelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("user", user);
		mSqlDatabase.insertOrThrow("userlist", null, values);
	}
	public String[] selectAllUser()
	{ 
		this.mSqlDatabase=sqlitehelper.getWritableDatabase();
		String[] result=null;
		Cursor mCursor= mSqlDatabase.query("userlist", new String[] {"user"}, null, null, null, null, null);
		if(mCursor!=null){
			result= new String[mCursor.getCount()];
			int i=0;
			mCursor.moveToFirst();
			while(!mCursor.isAfterLast()){
				result[i++]=mCursor.getString(mCursor.getColumnIndex("user"));
				mCursor.moveToNext();
			}			
		}
		if(mCursor!=null)mCursor.close();
		return result;
	}
	
	public void dropAndCreateUserList(){
		try {
			mSqlDatabase.execSQL("drop table userlist");
			mSqlDatabase.execSQL(CREATE_TABLE_USER_LIST);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				db.execSQL("DROP TABLE IF EXISTS img");
				db.execSQL("DROP TABLE IF EXISTS thumb");
				db.execSQL("DROP TABLE IF EXISTS imgup");
				db.execSQL("DROP TABLE IF EXISTS user");
				db.execSQL("DROP TABLE IF EXISTS favor");
				db.execSQL("DROP TABLE IF EXISTS follow");
				db.execSQL(CREATE_TABLE_THUMB);
				db.execSQL(CREATE_TABLE_IMG);
				db.execSQL(CREATE_TABLE_IMG_UP);
				db.execSQL(CREATE_TABLE_USER);
				db.execSQL(INSERT_TABLE_USER);
				db.execSQL(CREATE_TABLE_FAVOR);
				db.execSQL(CREATE_TABLE_FOLLOW);
				db.execSQL(CREATE_TABLE_USER_LIST);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			try{
				db.execSQL("DROP TABLE IF EXISTS img");
				db.execSQL("DROP TABLE IF EXISTS thumb");
				db.execSQL("DROP TABLE IF EXISTS imgup");
				db.execSQL("DROP TABLE IF EXISTS user");
				db.execSQL("DROP TABLE IF EXISTS favor");
				db.execSQL("DROP TABLE IF EXISTS follow");
				db.execSQL(CREATE_TABLE_THUMB);
				db.execSQL(CREATE_TABLE_IMG);
				db.execSQL(CREATE_TABLE_IMG_UP);
				db.execSQL(CREATE_TABLE_USER);
				db.execSQL(INSERT_TABLE_USER);
				db.execSQL(CREATE_TABLE_FAVOR);
				db.execSQL(CREATE_TABLE_FOLLOW);
				db.execSQL(CREATE_TABLE_USER_LIST);
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