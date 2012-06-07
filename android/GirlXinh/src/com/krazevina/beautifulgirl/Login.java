package com.krazevina.beautifulgirl;

import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.krazevina.objects.Global;
import com.krazevina.objects.sqlite;
import com.krazevina.webservice.CallWebService;

public class Login extends Activity implements OnClickListener,OnKeyListener {
	EditText edt_username, edt_password;
	Button btn_login, btn_register;
	CheckBox chk_savepass, chk_autolog;
	CallWebService call;
	String username, password,logout;
	Handler handler;
	ProgressDialog pg;
	boolean isload = false;
	String response = null;
	String response_logout = null;
	sqlite sql;
	
	boolean flag_logout = true;
	boolean flag_main = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		handler = new Handler();

		edt_username = (EditText) findViewById(R.id.edt_username);
		edt_password = (EditText) findViewById(R.id.edt_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		chk_savepass = (CheckBox) findViewById(R.id.chk_savepass);
		chk_autolog = (CheckBox) findViewById(R.id.chk_autolog);
		edt_password.setOnKeyListener(this);

		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);

		sql = new sqlite(this);
		call = new CallWebService(this);
		username = sql.getUsername();
		password = sql.getPassword();
		
		edt_username.setText(username);

		if (sql.getSavepassword()) {
			edt_username.setText(username);
			edt_password.setText(password);
			chk_savepass.setChecked(true);
		}

		if (sql.getAutologin()) {
			edt_username.setText(username);
			edt_password.setText(password);
			chk_savepass.setChecked(true);
			chk_autolog.setChecked(true);
			pg = new ProgressDialog(this);
			pg.setMessage(getString(R.string.pleasewait));
			pg.show();
			handler = new Handler();
			new login().start();
		}

		chk_autolog.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					chk_savepass.setChecked(true);
					chk_savepass.setEnabled(false);
				} else {
					chk_savepass.setChecked(false);
					chk_savepass.setEnabled(true);
				}
			}
		});
	}

	
	@Override
	public void onClick(View v) 
	{
		if (v.getId() == btn_login.getId()) 
		{
			username = edt_username.getText().toString();
			password = edt_password.getText().toString();
			if (username.equals("") || password.equals("")) {
				Toast.makeText(this, getString(R.string.user_not_blank), 2).show();
				return;
			}

			try{
				if(pg == null||!pg.isShowing())
				{
					pg = new ProgressDialog(this);
					pg.setMessage(getString(R.string.waiting));
					pg.show();
				}
			}catch (Exception e) {
			}
			new login().start();
		}

		if (v.getId() == btn_register.getId()) {
			startActivityForResult(new Intent(Login.this, Register.class),0);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(requestCode==0&&resultCode==10)finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sql.close();
	}

	boolean internet = true;

	class login extends Thread {

		@Override
		public void run() {
			response = call.login(username, password);
			try {
				if (!response.equals("")) {
					internet = true;
					pg.dismiss();
				} else {
					internet = false;
					pg.dismiss();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (response.equals("false")) {
						Toast.makeText(Login.this,getString(R.string.err_loginpass), 2).show();
						return;
					}else if (response.equals("")) {
						Toast.makeText(Login.this,getString(R.string.noconnect), 2).show();
						return;
					}else {
						try {							

							JSONObject jObject = new JSONObject(response);
							Global.username = jObject.get("userName").toString();
							Global.name = jObject.get("name").toString();
							Global.email = jObject.get("email").toString();
							Global.phone = jObject.get("phone").toString();
							Global.yahoo = jObject.get("chat").toString();
							Global.expDate = jObject.get("expDate").toString();
							Global.id = jObject.getInt("ID");
							Global.isActive = Integer.valueOf(jObject.get("isActive").toString());
							Global.time = Integer.valueOf(jObject.get("day").toString());
							
							sql.setUserPass(username, password);
							sql.setUserId(Global.id);
							
							sql.updateInfo(Global.name, Global.email, Global.phone, Global.yahoo);
//							sql.setAutoLogin(true);
							
							if (chk_autolog.isChecked()) {
								sql.setAutoLogin(true);
							} else {
								sql.setAutoLogin(false);
							}

							if (chk_savepass.isChecked()) {
								sql.setSavePassword(true);
							} else {
								sql.setSavePassword(false);
							}
							
							Intent in = new Intent("com.krazevina.beautifulgirl.updatefav");
							sendBroadcast(in);
							finish();
							Toast.makeText(Login.this,getString(R.string.loginsucess),2).show();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			});

		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(v.getId() == edt_password.getId() && keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
			onClick(btn_login);		
		}
		return false;
	}
	
}
