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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
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
import com.krazevina.objects.SingleView;

public class Single1 extends Activity implements OnClickListener
{
	SingleView v;
	Button btnfav,btndown,btninfo,btnfollow;
	Button btnback;
	TextView txtUploader,lbl;
	public ZoomControls zoombtn;
	public LinearLayout llmenu,lltop;
	boolean multi,menuvisible;
	Animation an,an1;
	Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		zoombtn = new ZoomControls(this);
		setContentView(R.layout.single);
		
		int in = getIntent().getIntExtra("index", 0);
		LinearLayout llv = (LinearLayout)findViewById(R.id.llimgview);
		try{
			Intent intent = getIntent();
			String s = intent.getData().getPath();
			v = new SingleView(this,s,in,zoombtn);
			v.setFocusable(true);
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
		}catch (Exception e) {
			Toast.makeText(this, R.string.err, 0).show();
			finish();
		}
		
		llmenu = (LinearLayout)findViewById(R.id.llmenu);
		lltop = (LinearLayout)findViewById(R.id.ll_toppanel);
		btnfollow = (Button)findViewById(R.id.btnfollow);
		txtUploader = (TextView)findViewById(R.id.txtuploader);
		lbl = (TextView)findViewById(R.id.lbluploader);
		lbl.setText(R.string.filename);
		RelativeLayout body = (RelativeLayout)findViewById(R.id.singlebody);
		
		body.bringChildToFront(lltop);
		btnfollow.setEnabled(false);
		
		btnback = (Button)findViewById(R.id.btnmain);
		btnfav = (Button)findViewById(R.id.btnfav);
		btninfo = (Button)findViewById(R.id.btninfo);
		btndown  = (Button)findViewById(R.id.btndown);
		
		btnfav.setEnabled(false);
		btnback.setEnabled(false);

		btninfo.setOnClickListener(this);
		btndown.setOnClickListener(this);
		
		an = AnimationUtils.loadAnimation(this, R.anim.downout);
		an.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			
			public void onAnimationRepeat(Animation animation) {}
			
			public void onAnimationEnd(Animation animation) {
				llmenu.setVisibility(View.GONE);
			}
		});
		an1 = AnimationUtils.loadAnimation(this, R.anim.upout);
		an1.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			
			public void onAnimationRepeat(Animation animation) {}
			
			public void onAnimationEnd(Animation animation) {
				lltop.setVisibility(View.GONE);
			}
		});
		folder = null;
		sfolder = null;
		menuvisible = true;
		handler = new Handler();
		showUploader();
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
	
	public void showUploader()
	{
		try{
			File f = new File(v.item.get(v.focusItem).i.getName());
			txtUploader.setText(f.getName());
		}catch (Exception e) {
			txtUploader.setText(R.string.undefine);
		}
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
			if(event.getAction()==KeyEvent.ACTION_UP)
				finish();
		return super.dispatchKeyEvent(event);
	};
	@Override
	public void onClick(View vi) 
	{
		if(v.item==null||v.item.size()<=0)return;
		if(vi.getId()==btninfo.getId())// properties
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(Single1.this);
    		builder.setCancelable(true)
    		       .setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		                dialog.cancel();
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		
			alert.setMessage("\t\t"+getString(R.string.info)+"\n" +
    				"\t"+getString(R.string.measure)+ "\t"+v.item.get(v.focusItem).i.wImg+"x"+v.item.get(v.focusItem).i.hImg);
    		alert.show();
		}
		if(vi.getId()==btndown.getId())
		{
			if(downloadType==null)
				initDownloadTypeDialog();
			downloadType.show();
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
		    		try{
		    			Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
	    			    shareIntent.setType("image/*");
	    			    shareIntent.putExtra(android.content.Intent.EXTRA_STREAM, 
	    			    		Uri.parse(Environment.getExternalStorageDirectory()+"/GX/img/"+v.item.get(v.focusItem).i.getId()));
	    			    startActivity(Intent.createChooser(shareIntent, getText(R.string.menu_share_intent)));      
		    		}catch (Exception e) {
		    			e.printStackTrace();
		    			Toast.makeText(getApplicationContext(), getString(R.string.menu_cant_share), 1).show();
					}
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
		AlertDialog.Builder builder = new AlertDialog.Builder(Single1.this);
		builder.setTitle(getString(R.string.action));
		builder.setItems(ar, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				try{
		    		if(which==0)	// keep size
		    		{
		    			pr = ProgressDialog.show(Single1.this, "", getString(R.string.pleasewait));
			        	new Thread(new Runnable() {
							public void run() {
							try {
								WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
								wm.suggestDesiredDimensions(v.item.get(v.focusItem).i.getBm().getWidth(), 
				    					(int)(v.item.get(v.focusItem).i.getBm().getHeight()));
								
						    		try {
						    			wm.clear();
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
		    			pr = ProgressDialog.show(Single1.this, "", getString(R.string.pleasewait));
			        	new Thread(new Runnable() {
							public void run() {
								try {
									WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
					    		
					    			int w = getWindowManager().getDefaultDisplay().getWidth(),h = getWindowManager().getDefaultDisplay().getHeight();
					    			if((float)v.item.get(v.focusItem).i.getBm().getWidth() / w >
				    					(float)v.item.get(v.focusItem).i.getBm().getHeight() / h )
					    			{
					    				w = getWindowManager().getDefaultDisplay().getWidth();
					    				h = (int) (v.item.get(v.focusItem).i.getBm().getHeight() * (float) getWindowManager().getDefaultDisplay().getWidth() / v.item.get(v.focusItem).i.getBm().getWidth());
					    			}
					    			else
					    			{
					    				h = getWindowManager().getDefaultDisplay().getHeight();
					    				w = (int) (v.item.get(v.focusItem).i.getBm().getWidth() * (float) getWindowManager().getDefaultDisplay().getHeight() / v.item.get(v.focusItem).i.getBm().getHeight());
					    			}
					    			Bitmap bmp = Bitmap.createScaledBitmap(v.item.get(v.focusItem).i.getBm(), w, h, true);
					    			wm.suggestDesiredDimensions(bmp.getWidth(), 
					    					bmp.getHeight());
					    			wm.clear();
									wm.setBitmap(bmp);
									handler.post(new Runnable() {
										public void run() {
											pr.dismiss();
											pr = null;
											Toast.makeText(getApplicationContext(), getString(R.string.setwallpered), 1).show();
										}
									});
									bmp.recycle();
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
		    			{
		    			}
		    			else	// not scrollable
		    			{
		    				intent.putExtra("aspectX", getWindowManager().getDefaultDisplay().getWidth());
			    			intent.putExtra("outputX", getWindowManager().getDefaultDisplay().getWidth());
			    			intent.putExtra("outputY", getWindowManager().getDefaultDisplay().getHeight());
				    		intent.putExtra("aspectY", getWindowManager().getDefaultDisplay().getHeight());
		    			}
			    		// true to return a Bitmap, false to directly save the cropped iamge
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
						if(which==1)
						{
						}
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
			AlertDialog.Builder builder = new AlertDialog.Builder(Single1.this);
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
		                	Toast.makeText(Single1.this, getString(R.string.saved), 1).show();
		                else
		                	Toast.makeText(Single1.this, getString(R.string.cantsave), 1).show();
						break;
					case 1:		// choose folder
						Intent intent = new Intent("org.openintents.action.PICK_DIRECTORY");
					    intent.putExtra("org.openintents.extra.TITLE", getString(R.string.choosedirectory));
					    intent.putExtra("org.openintents.extra.BUTTON_TEXT", getString(R.string.choose));
					    try {
					    	startActivityForResult(intent,1);
					    } catch (ActivityNotFoundException e) {
					    	intent = new Intent(Single1.this,Browse.class);
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
	        	pr = ProgressDialog.show(Single1.this, "", getString(R.string.pleasewait));
	        	new Thread(new Runnable() {
					public void run() {
						try {
							WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
				        	wm.suggestDesiredDimensions(getWindowManager().getDefaultDisplay().getWidth(), 
			    					getWindowManager().getDefaultDisplay().getHeight());
						
							Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/GX/temp/out.img");
							File f = new File(Environment.getExternalStorageDirectory()+"/GX/temp/out.img");
							f.delete();
				    		try {
								wm.setBitmap(bmp);
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
	        	pr = ProgressDialog.show(Single1.this, "", getString(R.string.pleasewait));
	        	new Thread(new Runnable() {
					public void run() {
						try {
							WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
				        	wm.suggestDesiredDimensions(getWindowManager().getDefaultDisplay().getWidth(), 
			    					getWindowManager().getDefaultDisplay().getHeight());
						
							Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/GX/temp/out.img");
							File f = new File(Environment.getExternalStorageDirectory()+"/GX/temp/out.img");
							f.delete();
				    		try {
				    			wm.suggestDesiredDimensions(bmp.getWidth(), bmp.getHeight());
								wm.setBitmap(bmp);
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
	    }
	}
}
