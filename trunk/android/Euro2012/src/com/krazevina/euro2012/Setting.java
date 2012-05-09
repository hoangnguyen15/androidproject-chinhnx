package com.krazevina.euro2012;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;


public class Setting extends Activity implements OnClickListener {
	
    Button btnSchedule,btnNews,btnTeams,btnSetting;
    LinearLayout llbtnsched,llbtnnews,llbtnteams,llbtnsetting;
    Button btnonoff1,btnonoff2,radeng,radkor,radvie;
    LinearLayout llabouts,llfeedback;
    
    boolean onoff1 = true;
    boolean onoff2 = true;
    boolean eng = true;
    boolean kor = false;
    boolean vie = false;
    
    
    
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
        
        btnonoff1 = (Button)findViewById(R.id.btnOnOff1);
        btnonoff2 = (Button)findViewById(R.id.btnOnOff2);
        radeng = (Button)findViewById(R.id.radeng);
        radkor = (Button)findViewById(R.id.radkor);
        radvie = (Button)findViewById(R.id.radvie);
        
        btnonoff1.setOnClickListener(this);
        btnonoff2.setOnClickListener(this);
        radeng.setOnClickListener(this);
        radkor.setOnClickListener(this);
        radvie.setOnClickListener(this);
        
        llabouts = (LinearLayout)findViewById(R.id.llabouts);
        llfeedback = (LinearLayout)findViewById(R.id.llfeedback);
        
        llabouts.setOnClickListener(this);
        llfeedback.setOnClickListener(this);
        
        
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
		
		if(v.getId() == btnonoff1.getId()){
			if(onoff1){
				btnonoff1.setBackgroundResource(R.drawable.btnoff);
				onoff1 = false;
			}else{
				btnonoff1.setBackgroundResource(R.drawable.btnon);
				onoff1 = true;
			}
		}
		if(v.getId() == btnonoff2.getId()){
			if(onoff2){
				btnonoff2.setBackgroundResource(R.drawable.btnoff);
				onoff2 = false;
			}else{
				btnonoff2.setBackgroundResource(R.drawable.btnon);
				onoff2 = true;
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
			}
		}
	}
	
}