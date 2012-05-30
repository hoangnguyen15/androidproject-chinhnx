package com.krazevina.euro2012;

import com.krazevina.objects.MatchEvent;
import com.krazevina.objects.SocketConnect;
import com.krazevina.objects.sqlite;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class OnlineService extends Service{
	SocketConnect socketLive,socketTime,socketUpdate;
	sqlite sql;
	@Override
	public IBinder onBind(Intent intent) {
		Log.e("SERVICE", "BIND");
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.e("SERVICE", "CREATE");
		timeToNextMatch = 10000000000l;
		super.onCreate();
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
		super.onDestroy();
	}
	
	LiveReceive rec;
	
	class LiveReceive extends Thread{
		String s;
		public void run(){
			sql = new sqlite(OnlineService.this);
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
						MatchEvent md = new MatchEvent(s);
						
						
						// if start match: timeStartMatch = System.currentTimeMilis();
						
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
						sql.updateBet(s,new Handler());
						liveConnect();
					}catch (Exception ex) {

					}
				}
			}
			timeToNextMatch = 10000000000l;
			live = false;
			sql.recycle();
		}
	}
	
	static long timeToNextMatch;
	static long timeReceiveNextMatch;
	static long timeStartMatch;
	static boolean live;
	
	class CheckTime extends Thread{
		String time;
		long timePassed;
		public void run(){
			while(true){
				try{
					socketTime = new SocketConnect();
					socketTime.connect();
					socketTime.send("Time");
					timeReceiveNextMatch = System.currentTimeMillis();
					time = socketTime.receive();
					socketTime.disconnect();
					timeToNextMatch = Long.parseLong(time.substring(5));
//					timeToNextMatch = 1;
				}catch (Exception e) {
					e.printStackTrace();
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
					// if havent updated time server yet, retry in 1 min
					else Thread.sleep(60000);
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
