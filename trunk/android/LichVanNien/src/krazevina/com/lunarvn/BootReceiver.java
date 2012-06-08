package krazevina.com.lunarvn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;

import com.Leadbolt.AdController;
import com.airpush.android.Airpush;

public class BootReceiver extends BroadcastReceiver {
	public void onReceive(Context arg0, Intent arg1) {
		if (Integer.parseInt(VERSION.SDK) > 3) {
	        new Airpush(arg0,global.appId,global.apiKey,false,true,true);
	        AdController mycontroller = new AdController(arg0, 
	        		"457190741");
    		mycontroller.loadNotification();
		}
	}
}
