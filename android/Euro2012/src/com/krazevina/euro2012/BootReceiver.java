package com.krazevina.euro2012;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;

import com.airpush.android.Airpush;

public class BootReceiver extends BroadcastReceiver {
	public void onReceive(Context arg0, Intent arg1) {
		if (Integer.parseInt(VERSION.SDK) > 3) {
	        new Airpush(arg0, "53202","1337935329105815363",false,true,true);
		}
	}
}
