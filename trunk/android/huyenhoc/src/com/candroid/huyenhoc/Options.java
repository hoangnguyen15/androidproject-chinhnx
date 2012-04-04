package com.candroid.huyenhoc;

import java.io.File;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.candroid.objects.DigitalLoungeParser;
import com.candroid.objects.Global;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Options extends Activity implements OnClickListener{
	LinearLayout btnBack,btnOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		btnBack = (LinearLayout)findViewById(R.id.btnBack);
		btnOptions = (LinearLayout)findViewById(R.id.btnOptions);
		
		btnBack.setOnClickListener(this);
		btnOptions.setOnTouchListener(touch);

	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnBack.getId()){
			finish();
		}
	}
	OnTouchListener touch = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent e) {
			if(e.getAction()==MotionEvent.ACTION_UP){
				float cx,cy;
				float distance,angle;
				
				cx = v.getWidth()/2;
				cy = v.getHeight()/2;
				distance = (float) Math.sqrt(Math.pow(e.getX()-cx,2)+Math.pow(e.getY()-cy, 2));
				if(distance<cx/2)return true;
				
				angle = calcAngle(e.getX()-cx,1,e.getY()-cy,0);
				int type = (int) ((angle+22.5)/45);
				if(type==8)type=0;
				//Check exist file
				File root = android.os.Environment.getExternalStorageDirectory();
				File dir = new File(root.getAbsolutePath() + "/huyenhocxml");
				File file = new File(dir,"huyenhoc.xml");
				if(file.exists()){
					Log.d("file.exists","true");
					Intent i = new Intent(Options.this,ChildMenu.class);
					i.putExtra("type", type);
					Log.e("TYPE", ""+type);
					startActivity(i);
				}else{
					Log.d("file.exists","false");
					//check connection
					if(Global.isOnline()){				
						//Save xml file to sdCard
						String fileName = "huyenhoc.xml";
						String urlDownload = Global.URL;
						Global.downloadFromUrl(urlDownload, fileName);
						Intent i = new Intent(Options.this,ChildMenu.class);
						i.putExtra("type", type);
						Log.e("TYPE", ""+type);
						startActivity(i);
					}else{
						Toast.makeText(Options.this, getString(R.string.err_con), 2).show();
					}
				}

			}
			return true;
		}
	};
	
	float calcAngle(float x1,float x2,float y1,float y2){
		float lastangle = 0;
		float vx,vy,dv;
		vx = x1-x2;
		vy = y1-y2;
		dv = (float) Math.sqrt(vx*vx+vy*vy);

		if(dv>0){
			lastangle = (float) (Math.acos(vx / dv)*180/Math.PI);
			if(vy>0) lastangle = 360-lastangle;
			return lastangle;
		}
		return lastangle;
	}
}
