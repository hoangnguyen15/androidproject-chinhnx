package com.krazevina.beautifulgirl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.krazevina.objects.Global;
import com.krazevina.webservice.CallWebService;

public class ChangePassword extends Activity implements OnClickListener{
	Button btn_update,btn_cancel;
	EditText edt_oldpass,edt_newpass,edt_renewpass;
	String oldpass,newpass,renewpass;
	Handler handler;
	CallWebService call;
	ProgressDialog pg;
	String response;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepass);
		
		edt_oldpass = (EditText)findViewById(R.id.edt_oldpass);
		edt_newpass = (EditText)findViewById(R.id.edt_newpass);
		edt_renewpass = (EditText)findViewById(R.id.edt_re_newpass);
		
		btn_update = (Button)findViewById(R.id.btn_update);
		btn_cancel = (Button)findViewById(R.id.btn_cancel);
		
		btn_update.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		call = new CallWebService(this);
		
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == btn_update.getId()){
			oldpass = edt_oldpass.getText().toString();
			newpass = edt_newpass.getText().toString();
			renewpass = edt_renewpass.getText().toString();
			
			
			if(oldpass.equals("")){
				Toast.makeText(this, "Mật khẩu cũ không được bỏ trống", 2).show();
				return;
			}
			if(newpass.equals("")){
				Toast.makeText(this, "Mật khẩu mới không được bỏ trống", 2).show();
				return;
			}
			if(!newpass.equals(renewpass)){
				Toast.makeText(this, "Mật khẩu mới không trùng nhau", 2).show();
				return;
			}
			pg = new ProgressDialog(this);
			pg.setMessage(getString(R.string.waiting));
			pg.show();
			handler = new Handler();
			new changepass().start();
		}
		if(v.getId() == btn_cancel.getId()){
			finish();
		}
		
	}
	
	boolean internet = true;
	protected class changepass extends Thread{
		
		@Override
		public void run(){
			response = call.changePass(Global.username, oldpass, newpass);
			if(!response.equals("")){
				internet = true;
				pg.dismiss();
			}else{
				internet = false;
				pg.dismiss();
			}
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					if(response.equals("true")){
						Toast.makeText(ChangePassword.this, getString(R.string.updated), 2).show();
					}
					if(response.equals("false")){
						Toast.makeText(ChangePassword.this, "Mật khẩu cũ không đúng", 2).show();
						return;
					}
					if(!internet){
						Toast.makeText(ChangePassword.this,getString(R.string.err_internet), 2).show();
						return;
					}
				}
			});
		}
	}
	
}
