package com.icsoft.ic_lichvannien;

import com.icsoft.objects.Global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
	private static final int SPLASH_DURATION = 1000; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
				
				//check info
				String info = Global.Info(Splash.this);
				if(info.length() == 0){
					startActivity(new Intent(Splash.this,Register.class));
				}else{
					startActivity(new Intent(Splash.this,Dayview.class));
				}
			}
		},SPLASH_DURATION);
	}
}
