package com.krazevina.euro2012;

import com.krazevina.objects.Global;
import com.krazevina.objects.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;


public class Setting extends Activity implements OnClickListener {
	
    Button btnSchedule,btnNews,btnTeams,btnSetting;
    LinearLayout llbtnsched,llbtnnews,llbtnteams,llbtnsetting;
    Button btnnotify,btnvibrate,radeng,radkor,radvie;
    LinearLayout llabouts,llfeedback;
    
    boolean notify,vibrate,eng,kor,vie;
    
    sqlite sql;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        llbtnsched = (LinearLayout)findViewById(R.id.llbtnsched);
        llbtnnews = (LinearLayout)findViewById(R.id.llbtnnews);
        llbtnteams = (LinearLayout)findViewById(R.id.llbtnteams);
        llbtnsetting = (LinearLayout)findViewById(R.id.llbtnsetting);
        llbtnnews.setOnTouchListener(touch);
        llbtnsched.setOnTouchListener(touch);
        llbtnteams.setOnTouchListener(touch);
        llbtnsetting.setOnTouchListener(touch);
        
        btnSchedule = (Button)findViewById(R.id.btnSchedule);
        btnNews = (Button)findViewById(R.id.btnNews);
        btnTeams = (Button)findViewById(R.id.btnTeams);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        
        btnNews.setOnClickListener(this);
        btnTeams.setOnClickListener(this);
        btnSchedule.setOnClickListener(this);
        
        btnnotify = (Button)findViewById(R.id.btnNotify);
        btnvibrate = (Button)findViewById(R.id.btnVibrate);
        radeng = (Button)findViewById(R.id.radeng);
        radkor = (Button)findViewById(R.id.radkor);
        radvie = (Button)findViewById(R.id.radvie);
        
        btnnotify.setOnClickListener(this);
        btnvibrate.setOnClickListener(this);
        radeng.setOnClickListener(this);
        radkor.setOnClickListener(this);
        radvie.setOnClickListener(this);
        
        llabouts = (LinearLayout)findViewById(R.id.llabouts);
        llfeedback = (LinearLayout)findViewById(R.id.llfeedback);
        
        llabouts.setOnClickListener(this);
        llfeedback.setOnClickListener(this);
        
        //GetSetting
        sql = new sqlite(this);
        sql.getSetting();
        if(Global.notify == 1){
        	notify = true;
        	btnnotify.setBackgroundResource(R.drawable.btnon);
        }else if (Global.notify == 0){
        	notify = false;
        	btnnotify.setBackgroundResource(R.drawable.btnoff);
        }
        
        if(Global.vibrate == 1){
        	vibrate = true;
        	btnvibrate.setBackgroundResource(R.drawable.btnon);
        }else if (Global.notify == 0){
        	vibrate = false;
        	btnvibrate.setBackgroundResource(R.drawable.btnoff);
        }
        
        //GetLang
        Global.getLang(this);
        Log.d("global.lang",Global.lang);
        if(Global.lang.equals("VI")){
        	vie = true;
        	kor = false;
        	eng = false;
        	radvie.setBackgroundResource(R.drawable.radon);
        	radeng.setBackgroundResource(R.drawable.radoff);
        	radkor.setBackgroundResource(R.drawable.radoff);
        }
        if(Global.lang.equals("KOR")){
        	kor = true;
        	vie = false;
        	eng = false;
        	radkor.setBackgroundResource(R.drawable.radon);
        	radvie.setBackgroundResource(R.drawable.radoff);
        	radeng.setBackgroundResource(R.drawable.radoff);
        }
        if(Global.lang.equals("ENG")){
        	eng = true;
        	vie = false;
        	kor = false;
        	radeng.setBackgroundResource(R.drawable.radon);
        	radvie.setBackgroundResource(R.drawable.radoff);
        	radkor.setBackgroundResource(R.drawable.radoff);
        }
        
    }
    
    OnTouchListener touch = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			try{
				LinearLayout l = (LinearLayout)v;
				((Button)l.getChildAt(0)).onTouchEvent(event);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	};
    
	@Override
	public void onClick(View v) {
		if(v.getId() == btnNews.getId()){
			startActivity(new Intent(this,News.class));
			finish();
		}
		
		if(v.getId() == btnTeams.getId()){
			startActivity(new Intent(this,Teams.class));
			finish();
		}
		
		if(v.getId() == btnSchedule.getId()){
			startActivity(new Intent(this,Main.class));
			finish();
		}
		
		if(v.getId() == btnnotify.getId()){
			if(notify){
				btnnotify.setBackgroundResource(R.drawable.btnoff);
				notify = false;
				sql.setNotify(0);
			}else{
				btnnotify.setBackgroundResource(R.drawable.btnon);
				notify = true;
				sql.setNotify(1);
			}
		}
		if(v.getId() == btnvibrate.getId()){
			if(vibrate){
				btnvibrate.setBackgroundResource(R.drawable.btnoff);
				vibrate = false;
				sql.setVibrate(0);
			}else{
				btnvibrate.setBackgroundResource(R.drawable.btnon);
				vibrate = true;
				sql.setVibrate(1);
			}
		}
		
		if(v.getId() == radeng.getId()){
			if(!eng){
				radeng.setBackgroundResource(R.drawable.radon);
				radkor.setBackgroundResource(R.drawable.radoff);
				radvie.setBackgroundResource(R.drawable.radoff);
				eng=true;
				kor=false;
				vie=false;
				Global.setLang(this,1);
			}
		}
		
		if(v.getId() == radkor.getId()){
			if(!kor){
				radkor.setBackgroundResource(R.drawable.radon);
				radeng.setBackgroundResource(R.drawable.radoff);
				radvie.setBackgroundResource(R.drawable.radoff);
				kor=true;
				eng=false;
				vie=false;
				Global.setLang(this,3);
			}
		}
		
		if(v.getId() == radvie.getId()){
			if(!vie){
				radvie.setBackgroundResource(R.drawable.radon);
				radkor.setBackgroundResource(R.drawable.radoff);
				radeng.setBackgroundResource(R.drawable.radoff);
				vie=true;
				eng=false;
				kor=false;
				Global.setLang(this,2);
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sql.recycle();
	}
	
}