package com.krazevina.euro2012;

import java.io.IOException;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import com.krazevina.objects.Event;
import com.krazevina.objects.Global;
import com.krazevina.objects.Match;
import com.krazevina.objects.Player;
import com.krazevina.objects.SocketConnect;
import com.krazevina.objects.Team;
import com.krazevina.objects.sqlite;

public class OnlineService extends Service{
	SocketConnect socketLive,socketTime,socketUpdate;
	sqlite sql;
	Handler h;
	@Override
	public IBinder onBind(Intent intent) {
		Log.e("SERVICE", "BIND");
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.e("SERVICE", "CREATE");
		timeToNextMatch = 10000000000l;
		sql = new sqlite(OnlineService.this);
		super.onCreate();
		h = new Handler();
		new CheckTime().start();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.e("SERVICE", "START");
		super.onStart(intent, startId);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("SERVICE", "STARTCOM");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		rec.interrupt();
		sql.recycle();
		super.onDestroy();
	}
	
	LiveReceive rec;
	
	class LiveReceive extends Thread{
		String s;
		public void run(){
			while(true){
				
				if(!live)return;
				
				try {
					Thread.sleep(5000);
					if(socketLive.checkError()){
						liveConnect();
					}
					
					s = socketLive.receive();
					if(s!=null){
						Log.e("Receive", s);

						// Update sql
						Event md = new Event(s);
						sql.updateLiveMatchEvent(md);
						
						// if start match: timeStartMatch = System.currentTimeMilis();
						if(md.eventID==6)timeStartMatch = System.currentTimeMillis();
						
						//GetSetting
				        sql.getSetting();
				        if(Global.notify == 1)notification(md);
				        if(Global.vibrate == 1 && md.eventID == 1){
				            Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
				            v.vibrate(1000);
				        }
						
						// if end match: update sql
						//               timeToNextMatch = 10000000000;
						//               live = false;
						if(md.eventID==7)break;
					}
					
					// if match longer than 150min, no end match from server, force end match.
					if(System.currentTimeMillis()-timeStartMatch>150*60*1000l)break;
				} catch (Exception e) {
					e.printStackTrace();
					try{
						// if connect interrupt,
						// update match online, and reconnect.
						socketUpdate = new SocketConnect();
						socketUpdate.connect();
						socketUpdate.send("MatchOnline");
						String s = socketUpdate.receive();
						sql.updateMatchEvent(s,h);
						liveConnect();
					}catch (Exception ex) {
					}
				}
			}
			timeToNextMatch = 10000000000l;
			timeStartMatch = -1;
			live = false;
			try {
				Thread.sleep(new Random().nextInt(30000));
			} catch (Exception e) {
				e.printStackTrace();
			}
			updateMatchEvent();
		}
	}
	
	void notification(Event e){
		Team t1,t2;Player p;
		Match m;
		m = sql.getMatch(e.matchID);
		t1 = sql.getTeam(m.team1);
		t2 = sql.getTeam(m.team2);
		p = sql.getPlayer(e.playerID);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		if(e.eventID==1){//GOAL
			int icon = R.drawable.goal;
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, "Goal", when);
			Context context = getApplicationContext();
			CharSequence contentTitle = t1.getName()+e.detail+t2.getName();
			CharSequence contentText = p.name+" "+e.time+"'";
			Global.match = m;
			Intent notificationIntent = new Intent(this, MatchDetail.class);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			mNotificationManager.notify(NOTIFY, notification);
		}
		if(e.eventID==6){//START
			int icon = R.drawable.goal;
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, getString(R.string.start), when);
			Context context = getApplicationContext();
			CharSequence contentTitle = t1.getName()+"-"+t2.getName();
			CharSequence contentText = m.stadium;
			Global.match = m;
			Intent notificationIntent = new Intent(this, MatchDetail.class);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			mNotificationManager.notify(NOTIFY, notification);
		}
		if(e.eventID==7){//END
			int icon = R.drawable.goal;
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, getString(R.string.end), when);
			Context context = getApplicationContext();
			CharSequence contentTitle = t1.getName()+e.detail+t2.getName();
			CharSequence contentText = m.stadium;
			Global.match = m;
			Intent notificationIntent = new Intent(this, MatchDetail.class);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			mNotificationManager.notify(NOTIFY, notification);
		}
	}
	private static final int NOTIFY = 1;

	static long timeToNextMatch;
	static long timeReceiveNextMatch;
	static long timeStartMatch;
	static boolean live;
	static boolean needUpdateMatchEvent = true;
	static long lastUpdateMatchEvent = 0;
	
	void updateMatchEvent(){
		try {
			socketUpdate = new SocketConnect();
			socketUpdate.connect();
			socketUpdate.send("MatchOnline");
			String s;
			s = socketUpdate.receive();
			sql.updateMatchEvent(s,h);
			needUpdateMatchEvent = false;
			lastUpdateMatchEvent = System.currentTimeMillis();
		} catch (IOException e1) {
		}
	}
	
	class CheckTime extends Thread{
		String time;
		long timePassed;
		public void run(){
			while(true){
				if(System.currentTimeMillis()-lastUpdateMatchEvent>24*60*60*1000l)needUpdateMatchEvent = true;
				if(needUpdateMatchEvent)updateMatchEvent();				
				try{
					socketTime = new SocketConnect();
					socketTime.connect();
					socketTime.send("Time");
					timeReceiveNextMatch = System.currentTimeMillis();
					time = socketTime.receive();
					socketTime.disconnect();
					timeToNextMatch = Long.parseLong(time.substring(5));
					timeToNextMatch = 1;
				}catch (Exception e) {
				}
				
				try{
					timePassed = System.currentTimeMillis() - timeReceiveNextMatch;
					Log.e("Time to next match:", ""+minuteLeft()+"min");
					// Start live socket before match start 10 min
					if(timeToNextMatch-timePassed<10*60*1000&&!live){
						liveConnect();
					}
					// update time server per 30 min
					if(timeToNextMatch!=10000000000l)Thread.sleep(30*60*1000);
					// if havent updated time server yet, retry in 2 min
					else Thread.sleep(120000);
				}catch (Exception e) {
				}
			}
		}
	}
	
	void liveConnect(){
		try{
		live = true;
		socketLive = new SocketConnect();
		socketLive.connect();
		socketLive.send("EndSocket");
		if(rec==null||!rec.isAlive()){
			rec = new LiveReceive();
			rec.start();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	int minuteLeft(){
		return (int) (timeToNextMatch / (1000*60));
	}
}
