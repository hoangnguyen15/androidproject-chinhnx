package com.krazevina.beautifulgirl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.krazevina.objects.Global;
import com.krazevina.objects.KHorizontalScrollView;
import com.krazevina.objects.sqlite;
import com.krazevina.webservice.CallWebService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class Editinfo extends Activity implements OnClickListener{
	EditText edt_username,edt_name,edt_email,edt_phone,edt_yahoo;
	
	Button btn_update,btn_changepass;
	String username,name, email,phone,yahoo;
	Handler handler;
	ProgressDialog pg;
	CallWebService call;
	String response;
	sqlite sql;
	LinearLayout m1,m2;
	Button btnacc,btnsetting,btnlist,btnall,btnmyimg,btnimgfollow;
	TextView btnlabel;
	LinearLayout prev;
	
	public KHorizontalScrollView layout_menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_info);
		
		edt_username = (EditText)findViewById(R.id.edt_username);
		edt_name = (EditText)findViewById(R.id.edt_name);
		edt_email = (EditText)findViewById(R.id.edt_email);
		edt_phone = (EditText)findViewById(R.id.edt_phone);
		edt_yahoo = (EditText)findViewById(R.id.edt_yahoo);
		
		btn_update = (Button)findViewById(R.id.btn_update);
		btn_changepass = (Button)findViewById(R.id.btn_change_pass);
		m1 = (LinearLayout)findViewById(R.id.m1);
		m2 = (LinearLayout)findViewById(R.id.m2);
		
		edt_username.setText(Global.username);
		edt_name.setText(Global.name);
		edt_email.setText(Global.email);
		edt_phone.setText(Global.phone);
		edt_yahoo.setText(Global.yahoo);
		prev = (LinearLayout)findViewById(R.id.llprev);
		prev.setOnClickListener(this);
		edt_name.clearFocus();
		
		call = new CallWebService(this);
		sql = new sqlite(this);
		btn_update.setOnClickListener(this);
		btn_changepass.setOnClickListener(this);
		
		layout_menu = (KHorizontalScrollView)findViewById(R.id.layout_menu);
		btnacc = (Button)findViewById(R.id.btnacc);
		btnlist = (Button)findViewById(R.id.btnlist);
		btnall = (Button)findViewById(R.id.btnall);
		btnmyimg = (Button)findViewById(R.id.btnmyimg);
		btnimgfollow = (Button)findViewById(R.id.btnimgfollow);
		btnlabel = (TextView)findViewById(R.id.txt);
		btnsetting = (Button)findViewById(R.id.btnsetting);
		
		btnacc.setOnClickListener(this);
		btnlist.setOnClickListener(this);
		btnall.setOnClickListener(this);
		btnmyimg.setOnClickListener(this);
		btnimgfollow.setOnClickListener(this);
		btnlabel.setOnClickListener(this);
		btnsetting.setOnClickListener(this);
		handler = new Handler();
		registerReceiver(prevReceiver, new IntentFilter("com.krazevina.beautifulgirl.prev"));
		LayoutParams lp;
		lp = (LayoutParams) m1.getLayoutParams();
		lp.width = getWindowManager().getDefaultDisplay().getWidth();
		m1.setLayoutParams(lp);
		m2.setLayoutParams(lp);
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(100);
					handler.post(new Runnable() {
						public void run() {
							layout_menu.setCurrview(1,getWindowManager().getDefaultDisplay().getWidth());
							MotionEvent e1 = MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0, 0, 0, 0, 0, 0, 0, 0, 0);
							MotionEvent e = MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 0, 0, 0, 0, 0, 0, 0, 0, 0);
							layout_menu.onTouchEvent(e);
							layout_menu.onTouchEvent(e1);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	private final BroadcastReceiver prevReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	if(!nextprev)return;
        	Intent i = new Intent("com.krazevina.beautifulgirl.follow");
        	sendBroadcast(i);
        	finish();
        }
    };
    boolean nextprev = true;
    protected void onDestroy() 
	{
		super.onDestroy();
		unregisterReceiver(prevReceiver);
		nextprev = false;
	}
    
    @Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}
	
    boolean statemenu = true;
	@Override
	public void onClick(View v) {
		if(v.getId() == btn_update.getId()){
			username = Global.username;
			name = edt_name.getText().toString();
			email = edt_email.getText().toString();
			phone = edt_phone.getText().toString();
			yahoo = edt_yahoo.getText().toString();
			
			
			
			String regex_email = "^([\\w-\\.])+@([\\w])+\\.(\\w){2,6}(\\.([\\w]){2,4})*$";
			String regex_phone = "^[0-9]{10,11}$";
			Pattern p = Pattern.compile(regex_email);
			Matcher matcher = p.matcher(email);
			
			if(matcher.find() || email.equals("")){
			}else{
				Toast.makeText(this,getString(R.string.err_email), 2).show();
				return;
			}
			
			p = Pattern.compile(regex_phone);
			matcher = p.matcher(phone);
			if(matcher.find() || phone.equals("")){
			}else{
				Toast.makeText(this,getString(R.string.err_phone), 2).show();
				return;
			}
			
			pg = new ProgressDialog(this);
			pg.setMessage(getString(R.string.waiting));
			pg.show();
			new update().start();
		}
		if(v.getId() == btn_changepass.getId()){
			startActivity(new Intent(Editinfo.this,ChangePassword.class));
		}
		if(v.getId()==btnlabel.getId())
		{
			if(statemenu)
			{
				btnacc.setVisibility(View.GONE);
				btnsetting.setVisibility(View.GONE);
				btnlist.setVisibility(View.GONE);
				btnall.setVisibility(View.VISIBLE);
				btnmyimg.setVisibility(View.VISIBLE);
				btnimgfollow.setVisibility(View.VISIBLE);
				statemenu = !statemenu;
			}else{
				btnacc.setVisibility(View.VISIBLE);
				btnsetting.setVisibility(View.VISIBLE);
				btnlist.setVisibility(View.VISIBLE);
				btnall.setVisibility(View.GONE);
				btnmyimg.setVisibility(View.GONE);
				btnimgfollow.setVisibility(View.GONE);
				statemenu = !statemenu;
			}
		}
		if(v.getId()==btnmyimg.getId())
		{
			Intent i = new Intent("com.krazevina.beautifulgirl.down");
            sendBroadcast(i);
            finish();
		}
		if(v.getId()==btnimgfollow.getId())
		{
			Intent i = new Intent("com.krazevina.beautifulgirl.follow");
            sendBroadcast(i);
            finish();
		}
		if(v.getId()==btnall.getId())
		{
			Intent i = new Intent("com.krazevina.beautifulgirl.all");
            sendBroadcast(i);
            finish();
		}
		if(v.getId()==btnall.getId())
		{
			Intent i = new Intent("com.krazevina.beautifulgirl.all");
            sendBroadcast(i);
            finish();
		}
		if(v.getId()==btnlist.getId())
		{
			Intent i = new Intent(this,FollowList.class);
            startActivity(i);
            finish();
		}
		if(v.getId()==btnsetting.getId())
		{
			Intent i = new Intent(this,Setting.class);
            startActivity(i);
            finish();
		}
		if(v.getId()==prev.getId())
		{
			Intent i = new Intent("com.krazevina.beautifulgirl.follow");
			sendBroadcast(i);
            finish();
		}
	}
	
	MotionEvent downev;
    boolean downed = false,dontcare = false;
    
    float downx,downy;
    
    public boolean dispatchTouchEvent(MotionEvent ev) 
    {
    	if(ev.getAction()==MotionEvent.ACTION_DOWN)
    	{
    		downev = MotionEvent.obtain(ev);
    		downed = false; dontcare = false;
    		downx = ev.getX();downy = ev.getY();
    	}
    	if(ev.getAction()==MotionEvent.ACTION_MOVE)
    	{
    		if(!dontcare&&Math.abs(ev.getY()-downy)*1.5<Math.abs(ev.getX()-downx)
					&&Math.abs(ev.getX()-downx)+Math.abs(ev.getY()-downy)>70)
			{
				layout_menu.onTouchEvent(downev);
				dontcare = true;
				return true;
			}
			if(dontcare)
			{
				layout_menu.onTouchEvent(ev);
				return true;
			}
    	}
    	if(ev.getAction()==MotionEvent.ACTION_UP&&dontcare)
    	{
    		layout_menu.onTouchEvent(ev);
    		return true;
    	}
    	return super.dispatchTouchEvent(ev);
    };
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getAction()==KeyEvent.ACTION_UP&&event.getKeyCode()==KeyEvent.KEYCODE_BACK)
		{
			Intent i = new Intent("com.krazevina.beautifulgirl.follow");
			sendBroadcast(i);
		}
		return super.dispatchKeyEvent(event);
	}
	
	boolean internet = true;;
	protected class update extends Thread{
		@Override
		public void run(){
			response = call.updateInfo(username, name, email, phone, yahoo);
			try{
				if(!response.equals("")){
					Thread.sleep(1000);
					internet = true;;
					pg.dismiss();
				}else{
					internet = false;
					pg.dismiss();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			handler.post(new Runnable() {
				@Override
				public void run() {
					if(response.equals("true")){
						sqlite sql = new sqlite(Editinfo.this);
						Toast.makeText(Editinfo.this,getString(R.string.updated), 2).show();
						sql.updateInfo(name, email, phone, yahoo);
						Global.name = name;
						Global.email = email;
						Global.phone = phone;
						Global.yahoo = yahoo;
						sql.close();
						finish();
					}
					
					if(!internet){
						Toast.makeText(Editinfo.this,getString(R.string.err_internet), 2).show();
						return;
					}
				}
			});
		}
	}
}
