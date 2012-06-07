package com.krazevina.beautifulgirl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;

import com.Leadbolt.AdController;
import com.airpush.android.Airpush;
import com.krazevina.objects.Global;

public class BootReceiver extends BroadcastReceiver {
	public void onReceive(Context arg0, Intent arg1) {
		if (Integer.parseInt(VERSION.SDK) > 3) {
	        new Airpush(arg0,Global.appId,Global.apiKey,false,true,true);
	        AdController mycontroller = new AdController(arg0, 
	        		"376887763");
    		mycontroller.loadNotification();
		}
	}
}
