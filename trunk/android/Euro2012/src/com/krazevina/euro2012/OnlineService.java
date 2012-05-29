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
		super.onCreate();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.e("SERVICE", "START");
		super.onStart(intent, startId);
		socketLive = new SocketConnect();
		socketLive.connect();
		socketLive.send("EndSocket");
		rec = new LiveReceive();
		rec.start();
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
				s = socketLive.receive();
				if(s!=null)Log.e("Receive", s);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					return;
				}
			}
		}
	}
	
	class CheckTime extends Thread{
		public void run(){
			
		}
	}
}
