package com.krazevina.beautifulgirl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.krazevina.objects.Global;
import com.krazevina.webservice.CallWebService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class General extends Activity implements OnClickListener,OnTouchListener{
	Button btn_setting,btn_list;
	EditText edt_username,edt_email,edt_phone,edt_yahoo;
	
	Button btn_update,btn_changepass;
	String username,name, email,phone,yahoo;
	Handler handler;
	ProgressDialog pg;
	CallWebService call;
	String response;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.general);
		
		edt_username = (EditText)findViewById(R.id.edt_username);
		edt_email = (EditText)findViewById(R.id.edt_email);
		edt_phone = (EditText)findViewById(R.id.edt_phone);
		edt_yahoo = (EditText)findViewById(R.id.edt_yahoo);
		
		btn_update = (Button)findViewById(R.id.btn_update);
		btn_changepass = (Button)findViewById(R.id.btn_change_pass);
		
		edt_username.setText(Global.username);
		edt_email.setText(Global.email);
		edt_phone.setText(Global.phone);
		edt_yahoo.setText(Global.yahoo);
		
		call = new CallWebService(this);
		btn_update.setOnClickListener(this);
		btn_changepass.setOnClickListener(this);
		
		
		btn_setting = (Button)findViewById(R.id.btn_g_setting);
		btn_list = (Button)findViewById(R.id.btn_g_list);
		
		
		
		btn_setting.setOnClickListener(this);
		btn_list.setOnClickListener(this);
		
		
		btn_setting.setOnTouchListener(this);
		btn_list.setOnTouchListener(this);
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btn_setting.getId()){
			startActivity(new Intent(General.this,Setting.class));
		}

		if(v.getId() == btn_list.getId()){
			startActivity(new Intent(General.this,FollowList.class));
		}
		
		if(v.getId() == btn_update.getId()){
			username = Global.username;
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
			
			handler = new Handler();
			pg = new ProgressDialog(this);
			pg.setMessage(getString(R.string.waiting));
			pg.show();
			new update().start();
		}
		if(v.getId() == btn_changepass.getId()){
			startActivity(new Intent(General.this,ChangePassword.class));
		}
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == btn_setting.getId() && event.getAction() == MotionEvent.ACTION_DOWN){
			btn_setting.setCompoundDrawablesWithIntrinsicBounds(R.drawable.g_setting_hi,0,0,0);
			btn_setting.setTextColor(Color.RED);
		}
		
		if(v.getId() == btn_setting.getId() && event.getAction() == MotionEvent.ACTION_UP){
			btn_setting.setCompoundDrawablesWithIntrinsicBounds(R.drawable.g_setting,0,0,0);
			btn_setting.setTextColor(Color.WHITE);
		}

		
		if(v.getId() == btn_list.getId() && event.getAction() == MotionEvent.ACTION_DOWN){
			btn_list.setCompoundDrawablesWithIntrinsicBounds(R.drawable.g_follow_hi,0,0,0);
			btn_list.setTextColor(Color.RED);
		}
		
		if(v.getId() == btn_list.getId() && event.getAction() == MotionEvent.ACTION_UP){
			btn_list.setCompoundDrawablesWithIntrinsicBounds(R.drawable.g_follow,0,0,0);
			btn_list.setTextColor(Color.WHITE);
		}

		return false;
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
						Toast.makeText(General.this,getString(R.string.updated), 2).show();
						Global.name = name;
						Global.email = email;
						Global.phone = phone;
						Global.yahoo = yahoo;
					}
					
					if(!internet){
						Toast.makeText(General.this,getString(R.string.err_internet), 2).show();
						return;
					}
				}
			});
		}
	}
}
