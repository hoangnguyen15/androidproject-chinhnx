package com.krazevina.beautifulgirl;

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.krazevina.objects.Global;
import com.krazevina.objects.Images;
import com.krazevina.objects.KHorizontalScrollView;
import com.krazevina.objects.sqlite;
import com.krazevina.webservice.CallWebService;

public class Setting extends Activity implements OnClickListener
{
	int PHOTO_PICKED = 2;
	int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 3;
	Button btnclear,btnchange,btnchangemax,
		btnacc,btnsetting,btnlist,btnall,btnmyimg,btnimgfollow;
	Button btnlabel;
	TextView txtsize,txtdir,txtmax;
	LinearLayout txtintro,txtfeedback,txtupload,txtlogout;
	ProgressDialog prog;
	Handler hand;
	sqlite sql;
	boolean nextprev = true;
	int maxcache;
	public KHorizontalScrollView layout_menu;
	LinearLayout m1,m2;
	CallWebService call;
    AlertDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		nextprev = true;
		
		btnclear = (Button)findViewById(R.id.btnclear);
		txtfeedback = (LinearLayout)findViewById(R.id.txtfeedback);
		txtintro = (LinearLayout)findViewById(R.id.txtintro);
		txtupload = (LinearLayout)findViewById(R.id.txt_uploadimg);
		txtlogout = (LinearLayout)findViewById(R.id.txt_logout);
		
		txtsize = (TextView)findViewById(R.id.txtcache);
		txtdir = (TextView)findViewById(R.id.txtdir);
		txtmax = (TextView)findViewById(R.id.txtmax);
		btnchangemax = (Button)findViewById(R.id.btnchangemax);
		btnchange = (Button)findViewById(R.id.btnchange);
		m1 = (LinearLayout)findViewById(R.id.m1);
		m2 = (LinearLayout)findViewById(R.id.m2);
		
		layout_menu = (KHorizontalScrollView)findViewById(R.id.layout_menu);
		btnacc = (Button)findViewById(R.id.btnacc);
		btnlist = (Button)findViewById(R.id.btnlist);
		btnall = (Button)findViewById(R.id.btnall);
		btnmyimg = (Button)findViewById(R.id.btnmyimg);
		btnimgfollow = (Button)findViewById(R.id.btnimgfollow);
		btnlabel = (Button)findViewById(R.id.txt);
		btnsetting = (Button)findViewById(R.id.btnsetting);
		
		btnchange.setOnClickListener(this);
		txtfeedback.setFocusable(true);
		txtintro.setFocusable(true);
		btnclear.setOnClickListener(this);
		txtfeedback.setOnClickListener(this);
		txtintro.setOnClickListener(this);
		btnchange.setOnClickListener(this);
		btnchangemax.setOnClickListener(this);
		txtupload.setOnClickListener(this);
		txtlogout.setOnClickListener(this);
		btnacc.setOnClickListener(this);
		btnlist.setOnClickListener(this);
		btnall.setOnClickListener(this);
		btnmyimg.setOnClickListener(this);
		btnimgfollow.setOnClickListener(this);
		btnlabel.setOnClickListener(this);
		
        registerReceiver(prevReceiver, new IntentFilter("com.krazevina.beautifulgirl.prev"));
		
        if(Global.username.equals("")){
        	txtlogout.setVisibility(View.GONE);
        }
		hand = new Handler();
		call = new CallWebService(this);
		sql = new sqlite(this);
		new Thread(new Runnable() {
			public void run() {
				try {
					hand.post(new Runnable() {
						public void run() {
							LayoutParams lp;
							lp = (LayoutParams) m1.getLayoutParams();
							lp.width = getWindowManager().getDefaultDisplay().getWidth();
							m1.setLayoutParams(lp);
							m2.setLayoutParams(lp);
						}
					});
					Thread.sleep(100);
					hand.post(new Runnable() {
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
	long t1;
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}
	
	protected void onResume() {
		super.onResume();
		maxcache = sql.getMaxCache();
		txtmax.setText(""+maxcache+"Mb");
		txtsize.setText(R.string.scanning);
		txtdir.setText(""+getSharedPreferences("dir", MODE_PRIVATE).getString("dir", Environment.getExternalStorageDirectory()+"/GX"));
		
		new Thread(new Runnable() {
			public void run() {
				sqlite sql;
				File f = new File(Environment.getExternalStorageDirectory()+"/GX/thumb");
				if(f.canRead())
				{
					sql = new sqlite(Setting.this);
					t1 = count(f);
					sql.setSizeCache(t1);
					sql.close();
					hand.post(new Runnable() {
						float t = t1;
						public void run() {
							if(t/1024f>1)
							{
								t = t/1024;
								if(t/1024>1)
								{
									t = t/1024;
									if(t/1024>1)
									{
										t = t/1024;
										txtsize.setText(""+val(t)+" G");
									}else txtsize.setText(""+val(t)+" M");
								}else txtsize.setText(""+val(t)+" K");
							}else txtsize.setText(""+val(t)+" bytes");
						}
					});
				}
			}
		}).start();
	};
	
	
	String val(Float f)
	{
		String s = ""+f;
		if(s.indexOf(".")<s.length()-2)
			s = s.substring(0,s.indexOf(".")+2);
		return s;
	}
	
	long count(File dir)
	{
		long result = 0;
	    File[] fileList = dir.listFiles();

	    for(int i = 0; i < fileList.length; i++) {
	        // Recursive call if it's a directory
	        if(fileList[i].isFile()) {
	            result += fileList[i].length();
	        }
	    }
	    return result; // return the file size
	}
	
	void del(File dir)
	{
		if(dir.isFile())dir.delete();
		else
		{
		    File[] fileList = dir.listFiles();
	
		    for(int i = 0; i < fileList.length; i++) 
		    {
		        if(fileList[i].isDirectory()) {
		            del(fileList[i]);
		        } else {
		        	fileList[i].delete();
		        }
		    }
		}
	}

	AlertDialog sureDelete;
	boolean statemenu = true;
	@Override
	public void onClick(View v) 
	{
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
		if(v.getId()==btnacc.getId())
		{
			if(Global.username==null||Global.username.length()<=0)
			{
				Intent i = new Intent(this,Login.class);
	            startActivity(i);
	            Toast.makeText(Setting.this, R.string.needlogin, 0).show();
	            return;
			}
			Intent i = new Intent(this,Editinfo.class);
            startActivity(i);
            finish();
		}
		if(v.getId()==btnlist.getId())
		{
			if(Global.username==null||Global.username.length()<=0)
			{
				Intent i = new Intent(this,Login.class);
	            startActivity(i);
	            Toast.makeText(Setting.this, R.string.needlogin, 0).show();
	            return;
			}
			Intent i = new Intent(this,FollowList.class);
            startActivity(i);
            finish();
		}
		if(v.getId()==txtfeedback.getId())
		{
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"androidkraze@gmail.com" });
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            startActivity(Intent.createChooser(emailIntent, getString(R.string.choose)));
		}
		if(v.getId()==btnclear.getId())
		{
			if(sureDelete==null)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(getString(R.string.suredelete))
				       .setCancelable(false)
				       .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   	prog = ProgressDialog.show(Setting.this, "", getString(R.string.pleasewait));
				   				new del().start();
				           }
				       })
				       .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				sureDelete = builder.create();
				sureDelete.setCanceledOnTouchOutside(true);
			}
			sureDelete.show();
		}
		if(v.getId()==btnchange.getId())
		{
			String f = getSharedPreferences("dir", MODE_PRIVATE).getString("dir", Environment.getExternalStorageDirectory()+"/GX");
			Intent intent = new Intent("org.openintents.action.PICK_DIRECTORY");
		    intent.putExtra("org.openintents.extra.TITLE", getString(R.string.choosedirectory));
		    intent.putExtra("org.openintents.extra.BUTTON_TEXT", getString(R.string.choose));
		    try {
		    	startActivityForResult(intent,1);
		    } catch (ActivityNotFoundException e) {
		    	intent = new Intent(this,Browse.class);
		        intent.putExtra("folder", f);
		        startActivityForResult(intent, 1);
		    }
		}
		if(v.getId()==btnchangemax.getId())
		{
			if(maxcache==50)
			{
				maxcache = 100;
				sql.setMaxCache(maxcache);
			}
			else if(maxcache==100)
			{
				maxcache = 10;
				sql.setMaxCache(maxcache);
			}
			else
			{
				maxcache = 50;
				sql.setMaxCache(maxcache);
			}
			txtmax.setText(""+maxcache+"Mb");
		}
		
		if(v.getId() == txtintro.getId()){
			startActivity(new Intent(Setting.this,Intro.class));
		}
		
		if(v.getId() == txtupload.getId())
		{
//			if(Global.username==null||Global.username.length()<=0)
//			{
//				Intent i = new Intent(this,Login.class);
//	            startActivity(i);
//	            Toast.makeText(Setting.this, R.string.needlogin, 0).show();
//	            return;
//			}
//			startActivity(new Intent(this,Upload.class));
			
			upload();
		}
		
		if(v.getId() == txtlogout.getId())
		{
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        case DialogInterface.BUTTON_POSITIVE:
			        	sql.setLogout();
			        	sql.updateInfo("", "", "", "");
			        	sql.setUserPass("", "");
			        	sql.setUserId(-1);
			        	call.logOut();
			        	Intent in = new Intent("com.krazevina.beautifulgirl.updatefav");
						sendBroadcast(in);
						Toast.makeText(Setting.this, R.string.logedout, 2).show();
						setResult(71);
						finish();
			            break;

			        case DialogInterface.BUTTON_NEGATIVE:
			            break;
			        }
			    }
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(getString(R.string.logout_question)).setPositiveButton(getString(R.string.yes), dialogClickListener)
			    .setNegativeButton(getString(R.string.no), dialogClickListener).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(requestCode == 1)
		{
			String folder=null;
			try{
                folder = data.getStringExtra("folder");
        	}catch (Exception e) {
        		if(data!=null&&data.getData()!=null)
        		folder = data.getData().getPath();
			}
        	if(folder!=null&&folder.length()>0)
        	{
        		SharedPreferences sp = getSharedPreferences("dir", MODE_PRIVATE);
        		Editor ed = sp.edit();
        		ed.putString("dir", folder);
        		ed.commit();
				new Images().scanSDCard(Setting.this, getWindowManager().getDefaultDisplay().getWidth()/2);
        	}
		}
		
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
		    if (resultCode == RESULT_OK) {
		    	try{
		    		Global.data_img = imageUri;
		    		imageUri = null;
                    startActivity(new Intent(this,Upload.class));
		    	}catch (Exception e) {
				}
		    }
		}
				
		if(requestCode == PHOTO_PICKED)
		{
			if(resultCode == RESULT_OK){  
	            Uri selectedImage = data.getData();
	            Global.data_img = selectedImage;
	            startActivity(new Intent(this,Upload.class));
	        }
		}

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(prevReceiver);
		sql.close();
		nextprev = false;
	}
	
	class del extends Thread
	{
		public void run()
		{
			File f = new File(Environment.getExternalStorageDirectory()+"/GX/thumb");
			del(f);
			hand.post(new Runnable() {
				public void run() {
					prog.dismiss();
					prog = null;
					File f = new File(Environment.getExternalStorageDirectory()+"/GX/thumb");
					if(f.canRead())
					{
						float t = count(f);
						if(t/1024f>1)
						{
							t = t/1024;
							if(t/1024>1)
							{
								t = t/1024;
								if(t/1024>1)
								{
									t = t/1024;
									txtsize.setText(""+val(t)+" G");
								}else txtsize.setText(""+val(t)+" M");
							}else txtsize.setText(""+val(t)+" K");
						}else txtsize.setText(""+val(t)+" bytes");
					}
				}
			});
		}
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
    
    boolean get = false;
    @Override
	public boolean dispatchKeyEvent(KeyEvent event) {
    	if(event.getAction()==KeyEvent.ACTION_DOWN&&event.getKeyCode()==KeyEvent.KEYCODE_BACK)
    	{
    		get = true;
    	}
		if(event.getAction()==KeyEvent.ACTION_UP&&event.getKeyCode()==KeyEvent.KEYCODE_BACK)
		{
			if(get)
			{
				Intent i = new Intent("com.krazevina.beautifulgirl.follow");
				sendBroadcast(i);
				get = false;
			}
		}
		return super.dispatchKeyEvent(event);
	}
    
    
    void upload(){
    	if(Global.username==null||Global.username.length()<=0)
		{
			Intent i = new Intent(this,Login.class);
            startActivity(i);
            Toast.makeText(Setting.this, R.string.needlogin, 0).show();
            return;
		}
    	if(dialog==null){
    		String[] items = getResources().getStringArray(R.array.choosefrom);
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setTitle(R.string.choosefrom);
    		builder.setItems(items, new DialogInterface.OnClickListener() {
    		    public void onClick(DialogInterface dialog, int item) {
    		    	if(item==0){
    		    		Intent intent = new Intent(Intent.ACTION_PICK);
    					intent.setType("image/*");
    					startActivityForResult(intent, PHOTO_PICKED);
    		    	}else{
    		    		String fileName = "photo.jpg";
    		    		ContentValues values = new ContentValues();
    		    		values.put(MediaStore.Images.Media.TITLE, fileName);
    		    		values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
    		    		imageUri = getContentResolver().insert(
    		    				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    		    		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    		    		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    		    		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
    		    		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    		    	}
    		    }
    		});
    		dialog = builder.create();
    	}
    	dialog.show();
    }
    Uri imageUri;
}
