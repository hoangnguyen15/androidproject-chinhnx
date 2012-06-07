package com.krazevina.beautifulgirl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.krazevina.objects.Global;
import com.krazevina.objects.sqlite;
import com.krazevina.webservice.CallWebService;

public class Info extends Activity implements OnClickListener{
	TextView edt_username,edt_time,txtneed;
	
	Button btn_upgrade,btn_change,btn_relogin;
	Handler handler;
	ProgressDialog pg;
	CallWebService call;
	String response;
	boolean flag_logout;
	sqlite sql;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		edt_username = (TextView)findViewById(R.id.edt_username);
		edt_time = (TextView)findViewById(R.id.edt_time);
		txtneed = (TextView)findViewById(R.id.txtneed);
		
		btn_upgrade = (Button)findViewById(R.id.btn_upgrade);
		btn_change = (Button)findViewById(R.id.btn_change_info);
		btn_relogin = (Button)findViewById(R.id.btn_relogin);
		
		call= new CallWebService(this);
		sql = new sqlite(this);
		
//		if(Global.time>0){
			txtneed.setVisibility(View.GONE);
			btn_upgrade.setText(getString(R.string.cancel));
			btn_relogin.setVisibility(View.GONE);
//		}
		
		if(getIntent().getStringExtra("user")!=null)
			edt_username.setText(getIntent().getStringExtra("user"));
		else edt_username.setText(Global.username);
		if(Global.time>=0)edt_time.setText(""+Global.time+" "+getString(R.string.days));
		else edt_time.setText(""+getString(R.string.outofdate));
		
		call = new CallWebService(this);
		btn_upgrade.setOnClickListener(this);
		btn_change.setOnClickListener(this);
		btn_relogin.setOnClickListener(this);
		handler = new Handler();
	}
	
	@Override
	public void onClick(View v) 
	{
		if(v.getId()==btn_upgrade.getId())
		{
			if(Global.time>0){
				finish();
			}else{
				Intent i = new Intent(this,Active.class);
				startActivityForResult(i,0);
			}
		}
		if(v.getId()==btn_change.getId())
		{
			Intent i = new Intent(Info.this,Editinfo.class);
			startActivityForResult(i, 1);
		}
		
//		if(v.getId() == btn_relogin.getId()){
//			new relogin().start();
//		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(requestCode==0)
		{
			setResult(0);
			finish();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sql.close();
	}
	
//    class relogin extends Thread{
//    	public void run(){
//    		response = call.logout(Global.username);
//    		if(response.equals("")){
//    			flag_logout = false;
//    		}
//    		sql.setLogout(1);
//    		if(flag_logout){
//				sql.setLogout(3);
//			}
//    		Global.username = "";
//    		Global.time = 0;
//    		sql.close();
//    		handler.post(new Runnable() {				
//				@Override
//				public void run() {
//					startActivity(new Intent(Info.this,Login.class));
//					finish();
//				}
//			});
//    	}
//    }
}
