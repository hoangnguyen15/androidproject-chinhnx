package com.krazevina.lichvannien;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krazevina.objects.Sqlite;

public class Intro extends Activity implements OnClickListener,OnTouchListener{
	LinearLayout layout_intro_root;
	TextView intro_tel,intro_email,intro_website1,intro_website2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		layout_intro_root = (LinearLayout) findViewById(R.id.layout_intro_root);
		intro_tel = (TextView)findViewById(R.id.intro_tel);
		intro_email = (TextView)findViewById(R.id.intro_email);
		intro_website1 = (TextView)findViewById(R.id.intro_website1);
		intro_website2 = (TextView)findViewById(R.id.intro_website2);
		
		intro_tel.setOnClickListener(this);
		intro_email.setOnClickListener(this);
		intro_website1.setOnClickListener(this);
		intro_website2.setOnClickListener(this);
		
		intro_tel.setOnTouchListener(this);
		intro_email.setOnTouchListener(this);
		intro_website1.setOnTouchListener(this);
		intro_website2.setOnTouchListener(this);
		
//		
//		layout_intro_root = (LinearLayout)findViewById(R.id.layout_intro_root);
//		((BitmapDrawable)layout_intro_root.getBackground()).setCallback(null);
//		BitmapFactory.Options op = new BitmapFactory.Options();
//		op.inSampleSize = 2;
//		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_color_07, op);
//		layout_intro_root.setBackgroundDrawable(new BitmapDrawable(b));
		
		//SetBackGround
		Sqlite sql = new Sqlite(this);
		switch (sql.getWallpaper()) {
		case 1:
			layout_intro_root.setBackgroundResource(R.drawable.wallpaper_color_01);
			break;
		case 2:
			layout_intro_root.setBackgroundResource(R.drawable.wallpaper_color_02);
			break;
		case 3:
			layout_intro_root.setBackgroundResource(R.drawable.wallpaper_color_03);
			break;
		case 4:
			layout_intro_root.setBackgroundResource(R.drawable.wallpaper_color_04);
			break;
		case 5:
			layout_intro_root.setBackgroundResource(R.drawable.wallpaper_color_05);
			break;
		case 6:
			layout_intro_root.setBackgroundResource(R.drawable.wallpaper_color_06);
			break;
		case 7:
			layout_intro_root.setBackgroundResource(R.drawable.wallpaper_color_07);
			break;
		default:
			break;
		}
		
		String filePathWallpaper = sql.getPathWallpaper();		
		if(filePathWallpaper!= null){
			File imgFile = new  File(filePathWallpaper);
			if(imgFile.exists()){
			    Bitmap myBitmap = BitmapFactory.decodeFile(filePathWallpaper);
			    Drawable yourbg = new BitmapDrawable(myBitmap);
			    layout_intro_root.setBackgroundDrawable(yourbg);
			}
		}
		sql.close();
	}

	public void onClick(View v) {
		//Call
		if(v.getId() == intro_tel.getId()){
			try {
		        Intent callIntent = new Intent(Intent.ACTION_CALL);
		        callIntent.setData(Uri.parse("tel:"+getString(R.string.intro_tel)));
		        startActivity(callIntent);
			    } 
			catch (ActivityNotFoundException e) {
				Log.d("Call Failed", "Call failed");
			}
		}
		
		//SendEmail
		if(v.getId() == intro_email.getId()){
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			String[] recipient = new String[] {getString(R.string.intro_email)};
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,recipient);
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"");
			emailIntent.setType("text/plain");
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		}
		//Access Website
		if(v.getId() == intro_website1.getId()){
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			//intent.setData(Uri.parse("http://google.com.vn"));
			intent.setData(Uri.parse(getString(R.string.intro_website1)));
			startActivity(intent);
		}
		
		if(v.getId() == intro_website2.getId()){
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			//intent.setData(Uri.parse("http://google.com.vn"));
			intent.setData(Uri.parse(getString(R.string.intro_website2)));
			startActivity(intent);
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == intro_tel.getId()){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				intro_tel.setTextColor(Color.parseColor("#7FFF00"));
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				intro_tel.setTextColor(Color.parseColor("#FFFFFF"));
			}
		}
		
		if(v.getId() == intro_email.getId()){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				intro_email.setTextColor(Color.parseColor("#7FFF00"));
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				intro_email.setTextColor(Color.parseColor("#FFFFFF"));
			}
		}
		
		
		if(v.getId() == intro_website1.getId()){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				intro_website1.setTextColor(Color.parseColor("#7FFF00"));
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				intro_website1.setTextColor(Color.parseColor("#FFFFFF"));
			}
		}
		
		if(v.getId() == intro_website2.getId()){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				intro_website2.setTextColor(Color.parseColor("#7FFF00"));
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				intro_website2.setTextColor(Color.parseColor("#FFFFFF"));
			}
		}
		return false;
	}
}
