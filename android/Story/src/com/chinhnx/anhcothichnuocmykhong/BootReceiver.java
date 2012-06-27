package com.chinhnx.anhcothichnuocmykhong;

import com.airpush.android.Airpush;
import com.chinhnx.objects.Global;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;

public class BootReceiver extends BroadcastReceiver {
	public void onReceive(Context arg0, Intent arg1) {
		if (Integer.parseInt(VERSION.SDK) > 3) {
			new Airpush(arg0, Global.APP_ID, Global.API_KEY, false, true, true);
		}
	}
}
