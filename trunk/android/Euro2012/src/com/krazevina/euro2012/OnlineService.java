package com.krazevina.euro2012;

import com.krazevina.objects.SocketConnect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class OnlineService extends Service{
	SocketConnect socketLive,socketTime;
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
			while(true){
				
				if(!live)return;
				
				try {
					Thread.sleep(5000);
					if(socketLive.checkError()){
						liveConnect();
					}
					
					s = socketLive.receive();
					if(s!=null)Log.e("Receive", s);
					
					// Update sql
					
					// if start match: timeStartMatch = System.currentTimeMilis();
					
					// if end match: update sql
					//               timeToNextMatch = 10000000000;
					//               live = false;
					
					// if match longer than 150min, no end match from server, force end match.
				} catch (Exception e) {
					e.printStackTrace();
					liveConnect();
				}
			}
		}
	}
	
	static long timeToNextMatch;
	static long timeStartMatch;
	static boolean live;
	
	class CheckTime extends Thread{
		String time;
		public void run(){
			while(true){
				try{
					socketTime = new SocketConnect();
					socketTime.connect();
//					socketTime.send("StartSocket");
//					Thread.sleep(5000);
					socketTime.send("Time");
					time = socketTime.receive();
					socketTime.disconnect();
					timeToNextMatch = Long.parseLong(time.substring(5));
					timeToNextMatch = 1;
					Log.e("Time to next match:", ""+minuteLeft()+"min");
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				// Start live socket before match start 10 min
				if(timeToNextMatch<10*60*1000&&!live){
					liveConnect();
				}
				
				try {
					// update time server per 30 min
					if(timeToNextMatch!=10000000000l)Thread.sleep(30*60*1000);
					// if havent updated time server yet, retry in 1min
					else Thread.sleep(60000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	void liveConnect(){
		live = true;
		socketLive = new SocketConnect();
		socketLive.connect();
		socketLive.send("EndSocket");
		if(rec==null||!rec.isAlive()){
			rec = new LiveReceive();
			rec.start();
		}
	}
	
	int minuteLeft(){
		return (int) (timeToNextMatch / (1000*60));
	}
}
