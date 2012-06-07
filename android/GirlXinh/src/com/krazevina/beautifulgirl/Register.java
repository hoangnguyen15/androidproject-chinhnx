package com.krazevina.beautifulgirl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.krazevina.objects.Global;
import com.krazevina.objects.sqlite;
import com.krazevina.webservice.CallWebService;

public class Register extends Activity implements OnClickListener,OnTouchListener
{
	EditText edt_username,edt_password,edt_re_password;
	CheckBox chk_accept_rule;
	TextView txt_accept_rule;
	Button btn_register,btn_cancel;
	CallWebService call;
	Handler handler;
	
	String username,password,repassword;
	ProgressDialog pg;
	boolean isload=false;
	String response = null;
	sqlite sql;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		edt_username = (EditText)findViewById(R.id.edt_username);
		edt_password = (EditText)findViewById(R.id.edt_password);
		edt_re_password = (EditText)findViewById(R.id.edt_re_password);
		btn_register = (Button)findViewById(R.id.btn_register);
		btn_cancel = (Button)findViewById(R.id.btn_cancel);
		chk_accept_rule = (CheckBox)findViewById(R.id.chk_accept_rule);
		txt_accept_rule = (TextView)findViewById(R.id.txt_accept_rule);
		
		btn_register.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		call = new CallWebService(this);
		txt_accept_rule.setOnClickListener(this);
		txt_accept_rule.setOnTouchListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		if(v.getId() == btn_register.getId()){
			username = edt_username.getText().toString();
			password = edt_password.getText().toString();
			repassword = edt_re_password.getText().toString();
			
			if(username.equals("") || password.equals("")){
				Toast.makeText(this, getString(R.string.user_not_blank), 2).show();
				return;
			}
			
			if(!password.equals(repassword)){
				Toast.makeText(this, getString(R.string.notmatchpass), 2).show();
				return;
			}
			
			String regex_username = "^[A-Za-z0-9_-]{3,15}$";
			Pattern p = Pattern.compile(regex_username);
			Matcher matcher = p.matcher(username);
			
			if(!matcher.find()){
				Toast.makeText(this, getString(R.string.err_username), 2).show();
				return;
			}
			
			if(!chk_accept_rule.isChecked()){
				Toast.makeText(this, getString(R.string.err_accept_rule), 2).show();
				return;
			}
			
			pg = new ProgressDialog(this);
			pg.setMessage(getString(R.string.pleasewait));
			pg.show();
			handler = new Handler();
			new register().start();
		}
		if(v.getId() == btn_cancel.getId()){
			finish();
		}
		if(v.getId() == txt_accept_rule.getId()){
			startActivity(new Intent(Register.this,Accept_rule.class));
		}
	}
	
	boolean internet = true;
	protected class register extends Thread{
		@Override
		public void run(){
			response = call.register(username, password);
			try{
				if(!response.equals("")){
					internet = true;
					Thread.sleep(1000);
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
					if(response.equals("-1")){
						Toast.makeText(Register.this,"Tên đăng nhập đã có", 2).show();
						return;
					}else{
						sql = new sqlite(Register.this);
						sql.setSavePassword(false);
						sql.setUserId(Integer.valueOf(response));
						Global.id = Integer.valueOf(response);
						sql.setUserPass(username, password);
						sql.close();
						Toast.makeText(Register.this,"Bạn đã đăng ký thành công", 2).show();
						Global.username = username;
//						Intent inten = new Intent(Register.this,Info.class);
//						inten.putExtra("user", username);
//						inten.putExtra("pass", password);
//						startActivity(inten);
						setResult(10);
						finish();
					}
					if(!internet){
						Toast.makeText(Register.this,getString(R.string.err_internet), 2).show();
						return;
					}
					
				}
			});
			
		}
		
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			txt_accept_rule.setTextColor(Color.RED);
		}
		if(event.getAction() == MotionEvent.ACTION_UP){
			txt_accept_rule.setTextColor(Color.WHITE);
		}
		return false;
	}

}
