package com.krazevina.beautifulgirl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;
import android.widget.LinearLayout.LayoutParams;
import com.krazevina.objects.Global;
import com.krazevina.objects.Image;
import com.krazevina.objects.Images;
import com.krazevina.objects.KSV;
import com.krazevina.objects.SingleView;
import com.krazevina.objects.sqlite;
import com.krazevina.webservice.CallWebService;

public class Single extends Activity implements OnClickListener,OnTouchListener{
	SingleView v;
	Button btnfav,btndown,btninfo,btnfollow,btnupload;
	protected static final int PHOTO_PICKED = 15;
	TextView btnback;
	public ZoomControls zoombtn;
	public LinearLayout llmenu,lltop;
	RelativeLayout body;
	public boolean multi,menuvisible;
	TextView txtUploader;
	LinearLayout ll_txt_Uploader,llupload;
	Animation an,an1;
	Handler handler;
	sqlite sql;
	public static Images images;
	public static boolean fromSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		zoombtn = new ZoomControls(this);
		setContentView(R.layout.single);
		sql = new sqlite(this);
		
		try{
			if(images==null)
			{
				finish();
				return;
			}
		}catch (Exception e) {
			try{
				if(images==null)
				{
					finish();
					return;
				}
			}catch (Exception ex) {
			}
		}
		int in = getIntent().getIntExtra("index", 0);
		try{
			v = new SingleView(this,images,in,zoombtn);
		}catch (Exception e) {
			try{
				v = new SingleView(this,images,in,zoombtn);
			}catch (Exception ex) {
				finish();
				return;
			}
		}
		v.setFocusable(true);
		
		
		LinearLayout llv = (LinearLayout)findViewById(R.id.llimgview);
		llupload = (LinearLayout)findViewById(R.id.llupload);
		btnupload = (Button)findViewById(R.id.btnupload);
		if(Main.type==KSV.DOWNLOADED||Main.type==KSV.UPLOADED||Main.type==KSV.FAVORITED)
		{
			llupload.setVisibility(View.VISIBLE);
		}
		llmenu = (LinearLayout)findViewById(R.id.llmenu);
		ll_txt_Uploader = (LinearLayout)findViewById(R.id.ll_txtUploader);
		lltop = (LinearLayout)findViewById(R.id.ll_toppanel);
		txtUploader = (TextView)findViewById(R.id.txtuploader);
		btnfollow = (Button)findViewById(R.id.btnfollow);
		body = (RelativeLayout)findViewById(R.id.singlebody);
		body.bringChildToFront(lltop);
		btnupload.setOnClickListener(this);
		ll_txt_Uploader.setOnClickListener(this);
		ll_txt_Uploader.setOnTouchListener(this);
		llv.addView(v);
		llv.addView(zoombtn);
		zoombtn.setOnZoomInClickListener(i);
		zoombtn.setOnZoomOutClickListener(o);
		
		PackageManager pm = getPackageManager();
		if(pm.hasSystemFeature("android.hardware.touchscreen.multitouch"))
		{
			zoombtn.setVisibility(View.GONE);
			multi = true;
		}else multi = false;
		
		btnback = (TextView)findViewById(R.id.btnmain);
		btnfav = (Button)findViewById(R.id.btnfav);
		btninfo = (Button)findViewById(R.id.btninfo);
		btndown  = (Button)findViewById(R.id.btndown);
		
		btnback.setOnClickListener(this);
		btnfav.setOnClickListener(this);
		btninfo.setOnClickListener(this);
		btndown.setOnClickListener(this);
		btnfollow.setOnClickListener(this);
		
		showUploader();
		an1 = AnimationUtils.loadAnimation(this, R.anim.upout);
		an1.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			public void onAnimationRepeat(Animation animation) {}
			public void onAnimationEnd(Animation animation) {
				lltop.setVisibility(View.GONE);
			}
		});
		an = AnimationUtils.loadAnimation(this, R.anim.downout);
		an.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			public void onAnimationRepeat(Animation animation) {}
			public void onAnimationEnd(Animation animation) {
				llmenu.setVisibility(View.GONE);
			}
		});
		folder = null;
		sfolder = null;
		menuvisible = true;
		handler = new Handler();
	}
	
	public void showUploader()
	{
		txtUploader.setText(v.item.get(v.focusItem).i.user);
		if(v.item.get(v.focusItem).i.favor>0)
		{
			btnfav.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_hotactiv, 0, 0, 0);
			btnfav.setTextColor(Color.RED);
		}else
		{
			btnfav.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btnfavorite, 0, 0, 0);
			btnfav.setTextColor(Color.WHITE);
		}
		if(sql.getfollow(v.item.get(v.focusItem).i.user))
			btnfollow.setBackgroundResource(R.drawable.followactive);
		else
			btnfollow.setBackgroundResource(R.drawable.followbtn);
		btnfav.setText(""+v.item.get(v.focusItem).i.getRate());
	}
	
	public void toggleMenu()
	{
		if(menuvisible)
			hideMenu();
		else showMenu();
	}
	public void showMenu()
	{
		if(menuvisible)return;
		llmenu.setVisibility(View.VISIBLE);
		lltop.setVisibility(View.VISIBLE);
		menuvisible = true;
	}
	public void hideMenu()
	{
		if(!menuvisible)return;
		llmenu.startAnimation(an);
		lltop.startAnimation(an1);
		menuvisible = false;
	}
	
	OnClickListener o = new OnClickListener() 
	{
		public void onClick(View vi) 
		{
			v.zoom(false);
		}
	};
	
	OnClickListener i = new OnClickListener() 
	{
		public void onClick(View vi) 
		{
			v.zoom(true);
		}
	};
	
	public void onWindowFocusChanged(boolean hasFocus) 
	{
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = (int) (-getWindowManager().getDefaultDisplay().getWidth()*2f/3f);
		lp.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 52, getResources().getDisplayMetrics()) + zoombtn.getHeight();
		zoombtn.setLayoutParams(lp);
		lp = (LayoutParams) llmenu.getLayoutParams();
		lp.width = getWindowManager().getDefaultDisplay().getWidth();
		llmenu.setLayoutParams(lp);
	};
	
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getAction()==KeyEvent.ACTION_UP
				&&event.getKeyCode()==KeyEvent.KEYCODE_MENU)
		{
			if(llmenu.getVisibility()==View.VISIBLE)
				zoombtn.setVisibility(View.GONE);
			else if(!multi)zoombtn.setVisibility(View.VISIBLE);
			toggleMenu();
		}
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK)
			if(event.getAction()==KeyEvent.ACTION_DOWN)
				setResult(v.focusItem);
		return super.dispatchKeyEvent(event);
	};
	@Override
	public void onClick(View vi) 
	{
		if(vi.getId()==btnupload.getId())
		{
			if(Global.username==null||Global.username.length()<=0)
			{
				Intent i = new Intent(this,Login.class);
	            startActivity(i);
	            Toast.makeText(Single.this, R.string.needlogin, 0).show();
	            return;
			}
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, PHOTO_PICKED);
		}
		if(vi.getId()==btnback.getId())
		{
			setResult(v.focusItem);
			finish();
			
		}
		if(vi.getId()==btnfav.getId())
		{
			if(Global.username==null||Global.username.length()<=0)
			{
				Intent in = new Intent(Single.this,Login.class);
				startActivityForResult(in,5);
				Toast.makeText(getApplicationContext(), getString(R.string.needlogin), 0).show();
				return;
			}
			if(v.item.get(v.focusItem).i.favor==0)
			{
				try{
					sql.favor(v.item.get(v.focusItem).i,Single.this,handler,v);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
			{
				try{
					sql.unfavor(v.item.get(v.focusItem).i,Single.this,handler,v);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			showUploader();
			v.invalidate();
		}
		if(vi.getId()==btninfo.getId())// properties
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(Single.this);
    		builder.setCancelable(true)
    		       .setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		                dialog.cancel();
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		
    		try{
	    		if(images==null)
	    		{
	    			alert.setMessage("\t\t"+getString(R.string.info)+"\n" +
	        				"\t"+getString(R.string.measure)+ "\t"+v.item.get(v.focusItem).i.wImg+"x"+v.item.get(v.focusItem).i.hImg
	        				);
	    		}else if(v.item.get(v.focusItem).i.wImg>0&&v.item.get(v.focusItem).i.hImg>0)
	    			alert.setMessage("\t\t"+getString(R.string.info)+"\n" +
	    				"\t"+getString(R.string.userupload)+ "\t"+v.item.get(v.focusItem).i.user+"\n"+
	    				"\t"+getString(R.string.uploaddate)+ "\t"+v.item.get(v.focusItem).i.date+"\n"+
	    				"\t"+getString(R.string.measure)+ "\t"+v.item.get(v.focusItem).i.wImg+"x"+v.item.get(v.focusItem).i.hImg+"\n"+
	    				"\t"+getString(R.string.voted)+ "\t\t"+v.item.get(v.focusItem).i.getRate()+" "+getString(R.string.times));
	    		else
	    			alert.setMessage("\t\t"+getString(R.string.info)+"\n" +
	    					"\t"+getString(R.string.userupload)+ "\t"+v.item.get(v.focusItem).i.user+"\n"+
	        				"\t"+getString(R.string.uploaddate)+ "\t"+v.item.get(v.focusItem).i.date+"\n"+
	        				"\t"+getString(R.string.measure)+ "\t"+getString(R.string.loading)+"\n"+
	        				"\t"+getString(R.string.voted)+ "\t\t"+v.item.get(v.focusItem).i.getRate()+" "+getString(R.string.times));
	    		alert.show();
    		}catch (Exception e) {
    			try{
	    			if(images==null)
		    		{
		    			alert.setMessage("\t\t"+getString(R.string.info)+"\n" +
		        				"\t"+getString(R.string.measure)+ "\t"+v.item.get(v.focusItem).i.wImg+"x"+v.item.get(v.focusItem).i.hImg
		        				);
		    		}else if(v.item.get(v.focusItem).i.wImg>0&&v.item.get(v.focusItem).i.hImg>0)
		    			alert.setMessage("\t\t"+getString(R.string.info)+"\n" +
		    				"\t"+getString(R.string.userupload)+ "\t"+v.item.get(v.focusItem).i.user+"\n"+
		    				"\t"+getString(R.string.uploaddate)+ "\t"+v.item.get(v.focusItem).i.date+"\n"+
		    				"\t"+getString(R.string.measure)+ "\t"+v.item.get(v.focusItem).i.wImg+"x"+v.item.get(v.focusItem).i.hImg+"\n"+
		    				"\t"+getString(R.string.voted)+ "\t\t"+v.item.get(v.focusItem).i.getRate()+" "+getString(R.string.times));
		    		else
		    			alert.setMessage("\t\t"+getString(R.string.info)+"\n" +
		    					"\t"+getString(R.string.userupload)+ "\t"+v.item.get(v.focusItem).i.user+"\n"+
		        				"\t"+getString(R.string.uploaddate)+ "\t"+v.item.get(v.focusItem).i.date+"\n"+
		        				"\t"+getString(R.string.measure)+ "\t"+getString(R.string.loading)+"\n"+
		        				"\t"+getString(R.string.voted)+ "\t\t"+v.item.get(v.focusItem).i.getRate()+" "+getString(R.string.times));
		    		alert.show();
    			}catch (Exception exx) {
				}
			}
		}
		if(vi.getId()==btndown.getId())
		{
			if(Global.username==null||Global.username.length()<=0)
			{
				Intent in = new Intent(Single.this,Login.class);
				startActivityForResult(in,5);
				Toast.makeText(getApplicationContext(), getString(R.string.needlogin), 0).show();
				return;
			}else{
				if(downloadType==null)
					initDownloadTypeDialog();
				downloadType.show();
			}
		}
		if(vi.getId()==btnfollow.getId())
		{
			if(Global.username==null||Global.username.length()<=0)
			{
				Intent in = new Intent(Single.this,Login.class);
				startActivityForResult(in,5);
				Toast.makeText(getApplicationContext(), getString(R.string.needlogin), 0).show();
				return;
			}
			if(txtUploader.getText().equals("")) return;
			new follow().start();
		}
		
		if(vi.getId() == ll_txt_Uploader.getId())
		{
			if(fromSearch)return;
			if(txtUploader.getText().equals("")) return;
			Global.userSearch = v.item.get(v.focusItem).i.user;
			Intent i = new Intent("com.krazevina.beautifulgirl.search");
			sendBroadcast(i);
			setResult(10);
			finish();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sql.close();
		images = null;
	}
	
	
	class follow extends Thread
	{
		Image i = v.item.get(v.focusItem).i;
		public void run()
		{
			CallWebService call = new CallWebService(Single.this);
			if(call.follow(i.user).length()<=0)
			{
				handler.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(Single.this, R.string.noconnect, 0).show();
						showUploader();
					}
				});
			}
			sqlite sql = new sqlite(Single.this);
			sql.follow(i.user);
			sql.close();
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(Single.this, R.string.followed, 0).show();
					showUploader();
				}
			});
		}
	}
	
	void share()
	{
		try{
			File f = new File(Environment.getExternalStorageDirectory()+"/GX/img/share");
			f.getParentFile().mkdirs();
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			v.item.get(v.focusItem).i.getBm().compress(CompressFormat.PNG, 90, fos);
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
		    shareIntent.setType("image/png");
		    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
		    startActivity(Intent.createChooser(shareIntent, getText(R.string.menu_share_intent)));      
		}catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), getString(R.string.menu_cant_share), 1).show();
		}
	}
	
	void initDownloadTypeDialog()
	{
		final String[] ar = getResources().getStringArray(R.array.actionarr);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.action));
		builder.setItems(ar, new DialogInterface.OnClickListener() 
		{
		    public void onClick(DialogInterface dialog, int item) 
		    {
		    	if(item<4&&v.item.get(v.focusItem).i.getBm()==null)
		    	{
		    		Toast.makeText(getApplicationContext(), getString(R.string.downloading), 1).show();
		    		return;
		    	}
		    	if(item == 0)//SAVE
		    	{
	    			browseForFolder();
		    	}
		    	if(item == 1)//WALLPAPER
		    	{
		    		if(chooseWallType==null)
		    			initChooseWallTypeDialog();
					chooseWallType.show();
		    	}
		    	if(item == 2)//SHARE
		    	{
		    		share();
		    	}
		    	if(item == 3)//CANCEL
		    	{
		    		dialog.dismiss();
		    	}
		    }
		});
		downloadType = builder.create();
		downloadType.setCanceledOnTouchOutside(true);
	}
	
	void initChooseWallTypeDialog()
	{
		final String[] ar = getResources().getStringArray(R.array.wallarr);
		AlertDialog.Builder builder = new AlertDialog.Builder(Single.this);
		builder.setTitle(getString(R.string.action));
		builder.setItems(ar, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				try{
		    		if(which==0)	// keep size
		    		{
		    			pr = ProgressDialog.show(Single.this, "", getString(R.string.pleasewait));
			        	new Thread(new Runnable() {
							public void run() {
								try {
									WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
					    			int w = getWindowManager().getDefaultDisplay().getWidth(),h = getWindowManager().getDefaultDisplay().getHeight();
									
						    		try {
						    			wm.suggestDesiredDimensions(w*2, h);
						    			wm.clear();
						    			wm.suggestDesiredDimensions(w*2, h);
										wm.setBitmap(v.item.get(v.focusItem).i.getBm());
										handler.post(new Runnable() {
											public void run() {
												pr.dismiss();
												pr = null;
												Toast.makeText(getApplicationContext(), getString(R.string.setwallpered), 1).show();
											}
										});
									} catch (IOException e) {
										handler.post(new Runnable() {
											public void run() {
												pr.dismiss();
												pr = null;
												Toast.makeText(getApplicationContext(), getString(R.string.cantsetwallper), 1).show();
											}
										});
									}
								} catch (Exception e1) {
									handler.post(new Runnable() {
										public void run() {
											try{
												pr.dismiss();
												pr = null;
												Toast.makeText(getApplicationContext(), getString(R.string.cantsetwallper), 0).show();
											}catch (Exception e) {
											}
										}
									});
								}
							}
						}).start();
		    		}
		    		else if(which == 1)		// keep ratio, notscrollable
		    		{
		    			pr = ProgressDialog.show(Single.this, "", getString(R.string.pleasewait));
			        	new Thread(new Runnable() {
							public void run() {
								try {
									WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
					    		
					    			int w = getWindowManager().getDefaultDisplay().getWidth(),h = getWindowManager().getDefaultDisplay().getHeight();
					    			int wscale,hscale;
					    			if((float)v.item.get(v.focusItem).i.getBm().getWidth() / w >
				    					(float)v.item.get(v.focusItem).i.getBm().getHeight() / h )
					    			{
					    				wscale = w;
					    				hscale = (int) (v.item.get(v.focusItem).i.getBm().getHeight() * (float) getWindowManager().getDefaultDisplay().getWidth() / v.item.get(v.focusItem).i.getBm().getWidth());
					    			}
					    			else
					    			{
					    				hscale = h;
					    				wscale = (int) (v.item.get(v.focusItem).i.getBm().getWidth() * (float) getWindowManager().getDefaultDisplay().getHeight() / v.item.get(v.focusItem).i.getBm().getHeight());
					    			}
					    			Bitmap bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
					    			Canvas c = new Canvas(bmp);
					    			Rect src = new Rect(0,0,v.item.get(v.focusItem).i.getBm().getWidth(),v.item.get(v.focusItem).i.getBm().getHeight());
					    			Rect dst = new Rect((w-wscale)/2,(h-hscale)/2,wscale+(w-wscale)/2,hscale+(h-hscale)/2);
					    			c.drawBitmap(v.item.get(v.focusItem).i.getBm(), src, dst, null);
					    			
					    			wm.suggestDesiredDimensions(w, h);
					    			wm.clear();
					    			wm.suggestDesiredDimensions(w, h);
									wm.setBitmap(bmp);
									wm.suggestDesiredDimensions(w, h);
									wm.setBitmap(bmp);
									handler.post(new Runnable() {
										public void run() {
											pr.dismiss();
											pr = null;
											Toast.makeText(getApplicationContext(), getString(R.string.setwallpered), 1).show();
										}
									});
									bmp.recycle();
									c = null;
								} catch (Exception e) {
									handler.post(new Runnable() {
										public void run() {
											try{
												pr.dismiss();
												pr = null;
												Toast.makeText(getApplicationContext(), getString(R.string.cantsetwallper), 1).show();
											}catch (Exception e) {											}
										}
									});
								}
							}
						}).start();
		    		}
		    		else if(which == 2||which == 3)
		    		{
		    			Intent intent = new Intent("com.android.camera.action.CROP");
			    		File temp = new File(Environment.getExternalStorageDirectory()+"/GX/temp/temp.img");
			    		temp.getParentFile().mkdirs();
			    		temp.createNewFile();
			    		FileOutputStream fos = new FileOutputStream(temp);
			    		v.item.get(v.focusItem).i.getBm().compress(Bitmap.CompressFormat.PNG, 1, fos);
			    		intent.setDataAndType(Uri.fromFile(temp), "image/*");
			    		intent.putExtra("crop", "true");
		    			if(which == 2)	// scrollable
		    			{}
		    			else	// not scrollable
		    			{
		    				intent.putExtra("aspectX", getWindowManager().getDefaultDisplay().getWidth());
			    			intent.putExtra("outputX", getWindowManager().getDefaultDisplay().getWidth());
			    			intent.putExtra("outputY", getWindowManager().getDefaultDisplay().getHeight());
				    		intent.putExtra("aspectY", getWindowManager().getDefaultDisplay().getHeight());
		    			}
			    		File tempout = new File(Environment.getExternalStorageDirectory()+"/GX/temp/out.img");
			    		if(tempout.canWrite()&&tempout.canRead())tempout.delete();
			    		intent.putExtra("return-data", false);
			    		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempout));
			    		if(which==3)startActivityForResult(intent, 2);
			    		else startActivityForResult(intent, 6);
		    		}
				}
				catch (Exception e) 
				{
					e.printStackTrace();
	    			WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
		    		try {
		    			wm.clear();
						wm.setBitmap(v.item.get(v.focusItem).i.getBm());
						Toast.makeText(getApplicationContext(), getString(R.string.setwallpered), 1).show();
					} catch (IOException ex) {
						Toast.makeText(getApplicationContext(), getString(R.string.cantsetwallper), 1).show();
					}
				}
			}
		});
		chooseWallType = builder.create();
		chooseWallType.setCanceledOnTouchOutside(true);
	}
	
	
	AlertDialog chooseWallType=null,downloadType=null,saveto = null;
	
	private void browseForFolder() 
	{
		if(saveto==null)
		{
			final String[] ar = getResources().getStringArray(R.array.saveto);
			AlertDialog.Builder builder = new AlertDialog.Builder(Single.this);
			builder.setTitle(getString(R.string.action));
			builder.setItems(ar, new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					switch (which) {
					case 0:		// save in default
						SharedPreferences sp = getSharedPreferences("dir", MODE_PRIVATE);
						if(folder==null)
							folder = sp.getString("dir", Environment.getExternalStorageDirectory()+"/GX");
						if(v.save(folder))
		                	Toast.makeText(Single.this, getString(R.string.saved), 1).show();
		                else
		                	Toast.makeText(Single.this, getString(R.string.cantsave), 1).show();
						break;
					case 1:		// choose folder
						Intent intent = new Intent("org.openintents.action.PICK_DIRECTORY");
					    intent.putExtra("org.openintents.extra.TITLE", getString(R.string.choosedirectory));
					    intent.putExtra("org.openintents.extra.BUTTON_TEXT", getString(R.string.choose));
					    try {
					    	startActivityForResult(intent,1);
					    } catch (ActivityNotFoundException e) {
					    	intent = new Intent(Single.this,Browse.class);
					        intent.putExtra("folder", folder);
					        startActivityForResult(intent, 1);
					    }
						break;
					}
				}
			});
			saveto = builder.create();
			saveto.setCanceledOnTouchOutside(true);
		}
		saveto.show();
	}
	
	String folder;
	File sfolder;
	ProgressDialog pr;
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    switch (requestCode) {
	        case 1: 
	            if (resultCode==RESULT_OK) {
	            	try{
		                folder = data.getStringExtra("folder");
		                sfolder = new File(folder);
	            	}catch (Exception e) {
	            		folder = data.getData().getPath();
		                sfolder = new File(folder);
					}
	                // DO SOMETHING WITH THE PATH
	                if(v.save(folder))
	                	Toast.makeText(this, getString(R.string.saved), 1).show();
	                else
	                	Toast.makeText(this, getString(R.string.cantsave), 1).show();
	            }
	            break;
	            
	        case 2:	// not scrollable 
	        	pr = ProgressDialog.show(Single.this, "", getString(R.string.pleasewait));
	        	new Thread(new Runnable() {
					public void run() {
						try {
							WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
				        	
							Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/GX/temp/out.img");
							bmp.getWidth();
							File f = new File(Environment.getExternalStorageDirectory()+"/GX/temp/out.img");
							f.delete();
							int w = getWindowManager().getDefaultDisplay().getWidth(),h = getWindowManager().getDefaultDisplay().getHeight();
			    			Bitmap bmp1 = Bitmap.createScaledBitmap(bmp,w,h,true);
				    		try {
				    			wm.suggestDesiredDimensions(w, h);
				    			wm.clear();
				    			wm.suggestDesiredDimensions(w, h);
								wm.setBitmap(bmp1);
								handler.post(new Runnable() {
									public void run() {
										pr.dismiss();
										pr = null;
										Toast.makeText(getApplicationContext(), getString(R.string.setwallpered), 1).show();
									}
								});
							} catch (IOException e) {
								handler.post(new Runnable() {
									public void run() {
										pr.dismiss();
										pr = null;
										Toast.makeText(getApplicationContext(), getString(R.string.cantsetwallper), 1).show();
									}
								});
							}
						} catch (Exception e1) {
							handler.post(new Runnable() {
								public void run() {
									try{
										pr.dismiss();
										pr = null;
									}catch (Exception e) {
									}
								}
							});
						}
					}
				}).start();
	        break;
	        case 6:	// scrollable
	        	pr = ProgressDialog.show(Single.this, "", getString(R.string.pleasewait));
	        	new Thread(new Runnable() {
					public void run() {
						try {
							WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
						
							Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/GX/temp/out.img");
							int w = getWindowManager().getDefaultDisplay().getWidth(),h = getWindowManager().getDefaultDisplay().getHeight();
							int wscale,hscale;
			    			if((float)bmp.getWidth() / w <
		    					(float)bmp.getHeight() / h )
			    			{
			    				wscale = w;
			    				hscale = (int) (bmp.getHeight() * (float) w / bmp.getWidth());
			    			}
			    			else
			    			{
			    				hscale = h;
			    				wscale = (int) (bmp.getWidth() * (float) h / bmp.getHeight());
			    			}
			    			File f = new File(Environment.getExternalStorageDirectory()+"/GX/temp/out.img");
							f.delete();
			    			Bitmap bmp1 = Bitmap.createScaledBitmap(bmp,wscale,hscale,true);
							
				    		try {
				    			wm.suggestDesiredDimensions(w, h);
				    			wm.clear();
				    			wm.suggestDesiredDimensions(w*2, h);
								wm.setBitmap(bmp1);
								handler.post(new Runnable() {
									public void run() {
										pr.dismiss();
										pr = null;
										Toast.makeText(getApplicationContext(), getString(R.string.setwallpered), 1).show();
									}
								});
							bmp.recycle();bmp1.recycle();
							} catch (IOException e) {
								handler.post(new Runnable() {
									public void run() {
										pr.dismiss();
										pr = null;
										Toast.makeText(getApplicationContext(), getString(R.string.cantsetwallper), 1).show();
									}
								});
							}
						} catch (Exception e1) {
							handler.post(new Runnable() {
								public void run() {
									try{
										pr.dismiss();
										pr = null;
									}catch (Exception e) {
									}
								}
							});
						}
					}
				}).start();
	        break;
	        
	        case 3:
	        	switch(resultCode)
	        	{
	        	case 21:
	        		sendBroadcast(new Intent("com.krazevina.beautifulgirl.up"));
	        		finish();
	        		break;
	        	case 11:
	        		sendBroadcast(new Intent("com.krazevina.beautifulgirl.ret"));
	        		finish();
	        		break;
	        	case 31:
		        	sendBroadcast(new Intent("com.krazevina.beautifulgirl.fav"));
		        	finish();
	        		break;
	        	case 41:
		        	sendBroadcast(new Intent("com.krazevina.beautifulgirl.down"));
		        	finish();
	        		break;
	        	case 71:
    				setResult(71);
//    				finish();
    				break;
	        	case 10:
	        		finish();
	        		break;
	        	}
	        break;
	        case PHOTO_PICKED:
	        	if(resultCode == RESULT_OK){
		            Uri selectedImage = data.getData();
		            Global.data_img = selectedImage;
		            startActivity(new Intent(this,Upload.class));
		            
		        }
	        	break;
	    }
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == ll_txt_Uploader.getId() && event.getAction() == MotionEvent.ACTION_DOWN){
			txtUploader.setTextColor(Color.RED);
		}
		if(v.getId() == ll_txt_Uploader.getId() && event.getAction() == MotionEvent.ACTION_UP){
			txtUploader.setTextColor(Color.WHITE);
		}
		return false;
	}
}
