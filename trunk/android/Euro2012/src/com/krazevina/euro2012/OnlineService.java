package com.krazevina.euro2012;

import com.krazevina.objects.SocketConnect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class OnlineService extends Service{
	SocketConnect socket;
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
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("SERVICE", "STARTCOM");
		return super.onStartCommand(intent, flags, startId);
	}

}
