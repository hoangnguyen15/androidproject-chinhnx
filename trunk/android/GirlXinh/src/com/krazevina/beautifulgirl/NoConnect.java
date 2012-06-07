package com.krazevina.beautifulgirl;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.krazevina.objects.Global;
import com.krazevina.objects.sqlite;
import com.krazevina.webservice.CallWebService;

public class NoConnect extends Activity implements OnClickListener
{
	LinearLayout llmain;
	Button btn_retry,btn_login,btn_picload,btn_picup,btn_picfav,btn_upload,btn_logout;
	public static int RETRY=1, LOGIN = 2, SDCARD = 3, GALLERY = 4;
	Handler h;
	boolean off;
	sqlite sql;
	protected static final int PHOTO_PICKED = 1;
	String response=null;
	boolean flag_logout;
	CallWebService call;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noconnect);
		llmain = (LinearLayout)findViewById(R.id.ll_main_noconnect);
		btn_retry = (Button)findViewById(R.id.btn_retry);
		btn_login = (Button)findViewById(R.id.btn_login);
		btn_picload = (Button)findViewById(R.id.btn_picload);
		btn_picup = (Button)findViewById(R.id.btn_picup);
		btn_picfav = (Button)findViewById(R.id.btn_picfav);
		btn_upload = (Button)findViewById(R.id.btn_upload);
		btn_logout = (Button)findViewById(R.id.btn_logout);
		h = new Handler();
		call = new CallWebService(this);
		sql = new sqlite(this);
		btn_retry.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		btn_picload.setOnClickListener(this);
		btn_picfav.setOnClickListener(this);
		btn_upload.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		btn_picup.setOnClickListener(this);
		
		setResult(-1);
		
		
		if(!Global.username.equals("")){
			btn_login.setText(getString(R.string.option));
			btn_logout.setVisibility(View.VISIBLE);
		}
		new Thread(new Runnable() {
			@Override
			public void run() 
			{
				if(!CallWebService.isOnline())
				{
					off = false;
					h.post(new Runnable() 
					{
						public void run() {
							TextView txt = (TextView)findViewById(R.id.txtnoconnect);
							txt.setVisibility(View.VISIBLE);
							btn_retry.setText(getString(R.string.btn_retry));
							btn_retry.setVisibility(View.VISIBLE);
							if(getIntent().getStringExtra("search")!=null&&getIntent().getStringExtra("search").startsWith("no"))
							{
								btn_retry.setVisibility(View.GONE);
							}
						}
					});
				}else{
					off = true;
				}
			}
		}).start();
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) 
	{
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK)
		{
			if(event.getAction()==KeyEvent.ACTION_UP)
			{
				finish();
				overridePendingTransition(R.anim.upin, R.anim.downout);
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==btn_retry.getId()){
			if(off){
				startActivityForResult(new Intent(NoConnect.this,Search.class),10);
			}else{
				setResult(11);
				finish();
				overridePendingTransition(R.anim.upin, R.anim.downout);
			}
			
		}
		
		if(v.getId()==btn_login.getId()){
			if(Global.username.equals(""))
			{
				Intent in = new Intent(NoConnect.this,Login.class);
				in.putExtra("first", "false");
				startActivity(in);
			}else{
				startActivity(new Intent(NoConnect.this,General.class));
			}
			finish();
			overridePendingTransition(R.anim.upin, R.anim.downout);
		}
		
		if(v.getId()==btn_picfav.getId())
		{
			if(Global.username==null||Global.username.length()<=0){
				Toast.makeText(this, getString(R.string.needlogin),2).show();
				Intent in = new Intent(NoConnect.this,Login.class);
				in.putExtra("first", "false");
				startActivity(in);
			}else{
				setResult(31);
			}
			finish();
			overridePendingTransition(R.anim.upin, R.anim.downout);
		}
		
		if(v.getId()==btn_picup.getId())
		{
			if(Global.username==null||Global.username.length()<=0){
				Toast.makeText(this, getString(R.string.needlogin),2).show();
				Intent in = new Intent(NoConnect.this,Login.class);
				in.putExtra("first", "false");
				startActivity(in);
			}else{
				setResult(21);
			}
			finish();
			overridePendingTransition(R.anim.upin, R.anim.downout);
		}
		
		if(v.getId()==btn_picload.getId()){
			File f = new File(Environment.getExternalStorageDirectory()+"/GX/dir");
	    	f.getParentFile().mkdirs();
//	    	FileOutputStream fos;
			try {
				f.createNewFile();
//				fos = new FileOutputStream(f);
//				SharedPreferences sp = getSharedPreferences("dir", Context.MODE_PRIVATE); 
//				File from = new File(sp.getString("dir", Environment.getExternalStorageDirectory()+"/GX"));
//				KSV.images.scanSDCard(from, getWindowManager().getDefaultDisplay().getWidth()/2, fos);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			setResult(41);
			finish();
			overridePendingTransition(R.anim.upin, R.anim.downout);
		}
		
		if(v.getId() == btn_upload.getId()){
			if(Global.username.equals("")){
				Toast.makeText(this, getString(R.string.needlogin),2).show();
				Intent in = new Intent(NoConnect.this,Login.class);
				in.putExtra("first", "false");
				startActivity(in);
				finish();
				overridePendingTransition(R.anim.upin, R.anim.downout);
			}else{
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, PHOTO_PICKED);
			}
		}
		
//		if(v.getId() == btn_logout.getId()){
//			
//			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//			    @Override
//			    public void onClick(DialogInterface dialog, int which) {
//			        switch (which){
//			        case DialogInterface.BUTTON_POSITIVE:
//			        	sql.setLogout();
//			        	Global.username = "";
//			        	Global.time = 0;
//			        	Global.id = -1;
//						if(KSV.images!=null)KSV.images.updateFav(NoConnect.this);
//						Toast.makeText(NoConnect.this, R.string.logedout, 2).show();
//						setResult(71);
//						finish();
//						overridePendingTransition(R.anim.upin, R.anim.downout);
//			            break;
//
//			        case DialogInterface.BUTTON_NEGATIVE:
//			            break;
//			        }
//			    }
//			};
//
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setMessage(getString(R.string.logout_question)).setPositiveButton(getString(R.string.yes), dialogClickListener)
//			    .setNegativeButton(getString(R.string.no), dialogClickListener).show();
//		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == PHOTO_PICKED){
			if(resultCode == RESULT_OK){  
	            Uri selectedImage = data.getData();
	            Global.data_img = selectedImage;
	            startActivity(new Intent(this,Upload.class));
	            finish();
	            overridePendingTransition(R.anim.upin, R.anim.downout);
	        }
		}
		if(requestCode==10)
		{
			if(resultCode==10)
			{
				setResult(10);
				finish();
				overridePendingTransition(R.anim.upin, R.anim.downout);
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sql.close();
	}
}
