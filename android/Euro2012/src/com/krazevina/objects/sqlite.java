package com.krazevina.objects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;

public class sqlite 
{
	private static String DB_PATH = "/data/data/com.krazevina.euro2012/databases/";
	private static final String DATABASE_NAME="euro2012.dbo";
	private static final int DATABASE_VERSION=4;
	
	private SQLiteDatabase mSqlDatabase;
	private SQLiteRssHelper sqlitehelper;
	int firstPlayerID;
	
	public sqlite(Context context) {
		sqlitehelper=new SQLiteRssHelper(context);
		this.mSqlDatabase=sqlitehelper.getWritableDatabase();
		if(mSqlDatabase.getVersion()!=DATABASE_VERSION)
			sqlitehelper.onUpgrade(mSqlDatabase, mSqlDatabase.getVersion(), DATABASE_VERSION);
		getFirstPlayerID();
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
	    			e.printStackTrace();
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
	
	public synchronized void recycle()
	{
		if(mSqlDatabase!=null)mSqlDatabase.close();
		if(sqlitehelper!=null)sqlitehelper.close();
	}
	
	public Vector<Event>getEvents(Match mat){
		Vector<Event>ret = new Vector<Event>();
		Cursor c = mSqlDatabase.query("MatchOnline", new String[]{
				"ID","MatchID","TeamID","PlayerID","EventID","Detail","MatchTime",
				"Status"}, "MatchID="+mat.ID, null, null, null, null);
		if(c==null)return ret;
		Event m;
		c.moveToFirst();
		for(int i=0;i<c.getCount();i++){
			m = new Event();
			m.eventID = c.getInt(c.getColumnIndex("EventID"));
			m.matchID = c.getInt(c.getColumnIndex("MatchID"));
			m.teamID = c.getInt(c.getColumnIndex("TeamID"));
			m.playerID = c.getInt(c.getColumnIndex("PlayerID"));
			m.detail = c.getString(c.getColumnIndex("Detail"));
			m.time = c.getString(c.getColumnIndex("MatchTime"));
			m.status = c.getInt(c.getColumnIndex("Status"));
			if(m.eventID==1&&m.detail.length()>0)mat.finalScore = m.detail;
			ret.add(m);
			c.moveToNext();
		}
		c.close();
		return ret;
	}
	public void getFirstPlayerID(){
		Cursor c = mSqlDatabase.query("Players", new String[]{
				"ID"}, null, null, null, null, null);
		if(c==null)firstPlayerID = 0;
		c.moveToFirst();
		firstPlayerID = c.getInt(0)-1;
		c.close();
	}
	
	public Vector<Match>getAllMatches(){
		Vector<Match>ret = new Vector<Match>();
		Cursor c = mSqlDatabase.query("Matches", new String[]{
				"ID","GroupID","FirstTeam","SecondTeam","Stadium","Start","End",
				"FinalScore","MainReferee","FirstPickup","SecondPickup","Status","tv"}, null, null, null, null, null);
		if(c==null)return ret;
		Match m;
		c.moveToFirst();
		for(int i=0;i<c.getCount();i++){
			m = new Match();
			m.ID = c.getInt(c.getColumnIndex("ID"));
			m.groupID = c.getInt(c.getColumnIndex("GroupID"));
			m.team1 = c.getInt(c.getColumnIndex("FirstTeam"));
			m.team2 = c.getInt(c.getColumnIndex("SecondTeam"));
			m.stadium = c.getString(c.getColumnIndex("Stadium"));
			m.setStart(c.getString(c.getColumnIndex("Start")));
			m.end = c.getString(c.getColumnIndex("End"));
			m.finalScore = c.getString(c.getColumnIndex("FinalScore"));
			m.referee = c.getString(c.getColumnIndex("MainReferee"));
			m.firstPick = c.getInt(c.getColumnIndex("FirstPickup"));
			m.secPick = c.getInt(c.getColumnIndex("SecondPickup"));
			m.status = c.getInt(c.getColumnIndex("Status"));
			m.tv = c.getInt(c.getColumnIndex("tv"));
			m.events = getEvents(m);
			ret.add(m);
			c.moveToNext();
		}
		c.close();
		return ret;
	}
	
	public Team getTeam(int teamID){
		Team t = null;
		Cursor c = mSqlDatabase.query("Teams", new String[]{
				"ID","BongdasoID","Name","Flag","Uniform1","Uniform2","EstablishedYear",
				"FifaJoinedYear","FifaRanking","Coach","Desc","AttendTimes",
				"status","NameEng","NameKor","descEng","descKor"}, "BongdasoId="+teamID, null, null, null, null);
		if(c==null)return t;
		c.moveToFirst();
		t = new Team();
		t.ID = c.getInt(c.getColumnIndex("BongdasoID"));
		t.name = c.getString(c.getColumnIndex("Name"));
		t.flag = c.getString(c.getColumnIndex("Flag"));
		t.uniform1 = c.getString(c.getColumnIndex("Uniform1"));
		t.uniform2 = c.getString(c.getColumnIndex("Uniform2"));
		t.establish = c.getInt(c.getColumnIndex("EstablishedYear"));
		t.fifaJoin = c.getInt(c.getColumnIndex("FifaJoinedYear"));
		t.fifaRank = c.getInt(c.getColumnIndex("FifaRanking"));
		t.coach = c.getString(c.getColumnIndex("Coach"));
		t.desc = c.getString(c.getColumnIndex("Desc"));
		t.attendTimes = c.getString(c.getColumnIndex("AttendTimes"));
		t.status = c.getInt(c.getColumnIndex("status"));
		t.nameEng = c.getString(c.getColumnIndex("NameEng"));
		t.nameKor = c.getString(c.getColumnIndex("NameKor"));
		t.descEng = c.getString(c.getColumnIndex("descEng"));
		t.descKor = c.getString(c.getColumnIndex("descKor"));
		c.close();
		return t;
	}
	
	public void getTeamGroup(Team t){
		Cursor c = mSqlDatabase.query("TeamsInRound", new String[]{
			"ID","RoundID","TeamID","Point","LooseScore","WinScore","Win",
			"Lose","Draw","Status"}, "TeamID="+t.ID, null, null, null, null);
		c.moveToFirst();
		t.roundID = c.getInt(1);
		t.point = c.getInt(3);
		t.goallose = c.getInt(4);
		t.goalscore = c.getInt(5);
		t.win = c.getInt(6);
		t.lose = c.getInt(7);
		t.draw = c.getInt(8);
		t.status = c.getInt(9);
	}
	
	public Vector<Team> getAllTeams(){
		Vector<Team>tt = new Vector<Team>();
		Team t = null;
		Cursor c = mSqlDatabase.query("Teams", new String[]{
				"ID","BongdasoID","Name","Flag","Uniform1","Uniform2","EstablishedYear",
				"FifaJoinedYear","FifaRanking","Coach","Desc","AttendTimes",
				"status","NameEng","NameKor","descEng","descKor"}, null, null, null, null, null);
		if(c==null)return tt;
		c.moveToFirst();
		for(int i=0;i<c.getCount();i++){
			t = new Team();
			t.ID = c.getInt(c.getColumnIndex("BongdasoID"));
			t.name = c.getString(c.getColumnIndex("Name"));
			t.flag = c.getString(c.getColumnIndex("Flag"));
			t.uniform1 = c.getString(c.getColumnIndex("Uniform1"));
			t.uniform2 = c.getString(c.getColumnIndex("Uniform2"));
			t.establish = c.getInt(c.getColumnIndex("EstablishedYear"));
			t.fifaJoin = c.getInt(c.getColumnIndex("FifaJoinedYear"));
			t.fifaRank = c.getInt(c.getColumnIndex("FifaRanking"));
			t.coach = c.getString(c.getColumnIndex("Coach"));
			t.desc = c.getString(c.getColumnIndex("Desc"));
			t.attendTimes = c.getString(c.getColumnIndex("AttendTimes"));
			t.status = c.getInt(c.getColumnIndex("status"));
			t.nameEng = c.getString(c.getColumnIndex("NameEng"));
			t.nameKor = c.getString(c.getColumnIndex("NameKor"));
			t.descEng = c.getString(c.getColumnIndex("descEng"));
			t.descKor = c.getString(c.getColumnIndex("descKor"));
			c.moveToNext();
			tt.add(t);
			getTeamGroup(t);
		}
		c.close();
		return tt;
	}
	
	public Vector<Player> getTeamPlayers(int teamID){
		Vector<Player>t = new Vector<Player>();
		Cursor c = mSqlDatabase.query("Players", new String[]{
				"TeamID","Name","Image","DOB","Height","Weight",
				"Club","Position","Number","Score","Status","PlayerTip"}, "TeamID="+teamID, null, null, null, null);
		if(c==null)return t;
		Player m;
		c.moveToFirst();
		for(int i=0;i<c.getCount();i++){
			m = new Player();
			m.teamID = teamID;
			m.name = c.getString(c.getColumnIndex("Name"));
			m.imageUrl = c.getString(c.getColumnIndex("Image"));
			m.dob = c.getString(c.getColumnIndex("DOB"));
			m.height = c.getString(c.getColumnIndex("Height"));
			m.weight = c.getString(c.getColumnIndex("Weight"));
			m.club = c.getString(c.getColumnIndex("Club"));
			m.pos = c.getString(c.getColumnIndex("Position"));
			m.number = c.getString(c.getColumnIndex("Number"));
			m.score = c.getInt(c.getColumnIndex("Score"));
			m.status = c.getInt(c.getColumnIndex("Status"));
			m.tip = c.getString(c.getColumnIndex("PlayerTip"));
			t.add(m);
			c.moveToNext();
		}
		c.close();
		return t;
	}
	
	public Player getPlayer(int ID){
		Player m = null;
		Cursor c = mSqlDatabase.query("Players", new String[]{"ID",
				"TeamID","Name","Image","DOB","Height","Weight",
				"Club","Position","Number","Score","Status","PlayerTip"}, "ID="+(ID+firstPlayerID), null, null, null, null);
		if(c==null)return m;
		
		c.moveToFirst();
		m = new Player();
		m.teamID = c.getInt(c.getColumnIndex("TeamID"));
		m.name = c.getString(c.getColumnIndex("Name"));
		m.imageUrl = c.getString(c.getColumnIndex("Image"));
		m.dob = c.getString(c.getColumnIndex("DOB"));
		m.height = c.getString(c.getColumnIndex("Height"));
		m.weight = c.getString(c.getColumnIndex("Weight"));
		m.club = c.getString(c.getColumnIndex("Club"));
		m.pos = c.getString(c.getColumnIndex("Position"));
		m.number = c.getString(c.getColumnIndex("Number"));
		m.score = c.getInt(c.getColumnIndex("Score"));
		m.status = c.getInt(c.getColumnIndex("Status"));
		m.tip = c.getString(c.getColumnIndex("PlayerTip"));

		c.close();
		return m;
	}
	
	public String searchNameTeam(int teamID){
		String nameTeam="";
		try {
			Cursor c = mSqlDatabase.query("Teams", new String[]{
					"BongdasoID","Name","NameEng","NameKor"}, "BongdasoId="+teamID, null, null, null, null);
			if(c==null)return null;

			c.moveToFirst();
			if(Global.lang.equals("EN"))nameTeam = c.getString(2);
			else if(Global.lang.equals("KO"))nameTeam = c.getString(3);
			else nameTeam = c.getString(1);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nameTeam;
	}
	
	public void getSetting(){
		try {
			Cursor c = mSqlDatabase.query("Setting", new String[]{"notify","vibrate"}, null, null, null, null, null);
			if(c==null) return;
			c.moveToFirst();
			Global.notify = c.getInt(0);
			Global.vibrate = c.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setNotify(int i) {
		try {
			exec("update setting set notify=" + i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setVibrate(int i) {
		try {
			exec("update setting set vibrate=" + i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void updateMatches(String js,Handler h){
		try {
			System.out.println(js);
			int firstc = js.indexOf("-");
			String js2 = js.substring(firstc+1);
			JSONObject jo = new JSONObject(js2);
			JSONArray j = jo.getJSONArray("Table");
			Match m;
			for(int i=0;i<j.length();i++){
				JSONObject o = (JSONObject) j.get(i);
				m = new Match();
				m.ID = Integer.parseInt(o.getString("ID"));
				m.groupID = Integer.parseInt(o.getString("GroupID"));
				m.team1 = Integer.parseInt(o.getString("FirstTeam"));
				m.team2 = Integer.parseInt(o.getString("SecondTeam"));
				m.stadium = o.getString("Stadium");
				m.setStart(o.getString("StartT"));
				m.finalScore = o.getString("FinalScore");
				m.referee = o.getString("MainReferee");
				try {
					m.firstPick = Integer.parseInt(o.getString("FirstPickup"));
				}catch (Exception e) {
				}
				try{
					m.secPick = Integer.parseInt(o.getString("SecondPickup"));
				}catch (Exception e) {
				}
				try{
					m.status = Integer.parseInt(o.getString("Status"));
				} catch (Exception e) {
				}
				
				String cmd1 = "UPDATE Matches SET GroupID="+m.groupID+
						" , FirstTeam="+m.team1+
						" , SecondTeam="+m.team2+
						" , Start='"+m.rawStart()+"'"+
						" , FinalScore='"+m.finalScore+"'"+
						" , MainReferee='"+m.referee+"'"+
						" , FirstPickup="+m.firstPick+
						" , SecondPickup="+m.secPick+
						" , Status="+m.status;
				if(m.stadium!=null&&m.stadium.length()>0)
					cmd1 += " , Stadium='"+m.stadium+"'";
				final String cmd=cmd1+" where ID="+m.ID;
				h.post(new Runnable() {
					public void run() {
						try{
							exec(cmd);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void updateTeamsInRound(String js,Handler h){
		try {
			System.out.println(js);
			int firstc = js.indexOf("-");
			String js2 = js.substring(firstc+1);
			JSONObject jo = new JSONObject(js2);
			JSONArray j = jo.getJSONArray("Table");
			TeamsInRound t;
			for(int i=0;i<j.length();i++){
				JSONObject o = (JSONObject) j.get(i);
				t = new TeamsInRound();
				t.ID = Integer.parseInt(o.getString("ID"));
				t.roundID = Integer.parseInt(o.getString("RoundID"));
				t.teamID = Integer.parseInt(o.getString("TeamID"));
				t.point = Integer.parseInt(o.getString("Point"));
				t.looseScore = Integer.parseInt(o.getString("LooseScore"));
				t.winScore = Integer.parseInt(o.getString("WinScore"));
				t.win = Integer.parseInt(o.getString("Win"));
				t.lose = Integer.parseInt(o.getString("Lose"));
				t.draw = Integer.parseInt(o.getString("Draw"));
				t.status = Integer.parseInt(o.getString("Status"));
				
				
				final String s = "UPDATE TeamsInRound SET RoundID="+t.roundID+
						" , TeamID="+t.teamID+
						" , Point="+t.point+
						" , LooseScore="+t.looseScore+
						" , WinScore="+t.winScore+
						" , Win="+t.win+
						" , Lose="+t.lose+
						" , Draw="+t.draw+
						" , Status="+t.status+
						" where ID="+t.ID;
				h.post(new Runnable() {
					public void run() {
						try{
							exec(s);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Vector<Bet> getBet(int matchID){
		Vector<Bet>vb = new Vector<Bet>();
		Bet t = null;
		Cursor c = mSqlDatabase.query("BetDetail", new String[]{
				"ID","MatchID","BetHouseID","Col1","Col2","Col3","Status"}, "MatchID="+matchID, null, null, null, null);
		if(c==null)return vb;
		c.moveToFirst();
		for(int i=0;i<c.getCount();i++){
			t = new Bet();
			t.matchID = c.getInt(c.getColumnIndex("MatchID"));
			t.bethouse = c.getInt(c.getColumnIndex("BetHouseID"));
			t.col1 = c.getString(c.getColumnIndex("Col1"));
			t.col2 = c.getString(c.getColumnIndex("Col2"));
			t.col3 = c.getString(c.getColumnIndex("Col3"));
			t.status = c.getInt(c.getColumnIndex("Status"));
			vb.add(t);
			c.moveToNext();
		}
		c.close();
		return vb;
	}
	public void updateBet(String js,Handler h){
		try {
			System.out.println(js);
			int firstc = js.indexOf("-");
			String js2 = js.substring(firstc+1);
			JSONObject jo = new JSONObject(js2);
			JSONArray j = jo.getJSONArray("Table");
			Bet t;
			for(int i=0;i<j.length();i++){
				JSONObject o = (JSONObject) j.get(i);
				t = new Bet();
				t.ID = Integer.parseInt(o.getString("ID"));
				t.matchID = Integer.parseInt(o.getString("MatchID"));
				t.bethouse = Integer.parseInt(o.getString("BetHouseID"));
				if(o.getString("Col1").length()>0)t.col1 = o.getString("Col1");
				if(o.getString("Col2").length()>0)t.col2 = o.getString("Col2");
				if(o.getString("Col3").length()>0)t.col3 = o.getString("Col3");
				if(o.getString("Status").length()>0)t.status = Integer.parseInt(o.getString("Status"));
				
				final String s = "UPDATE BetDetail SET Col1='"+t.col1+"'"+
						" , Col2='"+t.col2+"'"+
						" , Col3='"+t.col3+"'"+
						" , Status="+t.status+
						" where MatchID="+t.matchID+" AND BetHouseID="+t.bethouse;
				h.post(new Runnable() {
					public void run() {
						try{
							exec(s);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	Event e;
	public void updateMatchEvent(String js,Handler h){
		try {
			exec("Delete from MatchOnline");
			System.out.println(js);
			int firstc = js.indexOf("-");
			String js2 = js.substring(firstc+1);
			JSONObject jo = new JSONObject(js2);
			JSONArray j = jo.getJSONArray("Table");
			
			for(int i=0;i<j.length();i++){
				JSONObject o = (JSONObject) j.get(i);
				e = new Event(o);
				h.post(new Runnable() {
					Event ev = e;
					public void run() {
						try{
							updateLiveMatchEvent(ev);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateLiveMatchEvent(Event ev){
		final String s = "INSERT INTO MatchOnline(MatchID,TeamID,PlayerID,EventID,Detail,MatchTime,Status) VALUES " +
				"(" +ev.matchID+","+ev.teamID+","+ev.playerID+","+ev.eventID+",'"+ev.detail+"','"+ev.time+"',"+ev.status+")";
		exec(s);
	}
	
	synchronized void exec(String s){
		mSqlDatabase.execSQL(s);
	}
	
}