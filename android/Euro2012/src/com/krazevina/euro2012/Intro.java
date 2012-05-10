package com.krazevina.euro2012;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Intro extends Activity implements OnClickListener, OnTouchListener{
	LinearLayout layout_intro_root;
	TextView intro_email,intro_website;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		
//		intro_tel = (TextView)findViewById(R.id.intro_tel);
		intro_email = (TextView)findViewById(R.id.intro_email);
		intro_website = (TextView)findViewById(R.id.intro_website);
		
//		intro_tel.setOnClickListener(this);
		intro_email.setOnClickListener(this);
		intro_website.setOnClickListener(this);
		
//		intro_tel.setOnTouchListener(this);
		intro_email.setOnTouchListener(this);
		intro_website.setOnTouchListener(this);
	}
	
	public void onClick(View v) {
		
		//SendEmail
		if(v.getId() == intro_email.getId()){
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.intro_email)});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            startActivity(Intent.createChooser(emailIntent, getString(R.string.choose)));
		}
		//Access Website
		if(v.getId() == intro_website.getId()){
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			intent.setData(Uri.parse(getString(R.string.intro_website)));
			startActivity(intent);
		}
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(v.getId() == intro_email.getId() && event.getAction() == MotionEvent.ACTION_DOWN){
			intro_email.setTextColor(Color.GREEN);
		}
		if(v.getId() == intro_email.getId() && event.getAction() == MotionEvent.ACTION_UP){
			intro_email.setTextColor(Color.WHITE);
		}
		
		if(v.getId() == intro_website.getId() && event.getAction() == MotionEvent.ACTION_DOWN){
			intro_website.setTextColor(Color.GREEN);
		}
		if(v.getId() == intro_website.getId() && event.getAction() == MotionEvent.ACTION_UP){
			intro_website.setTextColor(Color.WHITE);
		}
		
		return false;
	}

}
