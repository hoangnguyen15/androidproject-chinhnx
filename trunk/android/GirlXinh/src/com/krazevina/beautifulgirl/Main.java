package com.krazevina.beautifulgirl;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.krazevina.objects.Global;
import com.krazevina.objects.KHorizontalScrollView;
import com.krazevina.objects.KSV;
import com.krazevina.objects.User;
import com.krazevina.objects.Users;
import com.krazevina.objects.sqlite;
import com.krazevina.webservice.CallWebService;

public class Main extends Activity implements OnClickListener, OnKeyListener
{
	protected static final int PHOTO_PICKED = 1;
	protected static final int RETRY = 2;
	public static final int LOGIN = 3;
	protected static final int MENU = 4;
	protected static final int SETTING = 7;
	protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 13;
	
    public KHorizontalScrollView layout_menu;
    public KSV ksv;public static int type;
    
//    LinearLayout llbtn_moi,llbtn_hot,llbtn_top,
//    llbtn_fav,llbtn_down,llbtn_up,
//    llbtn_new,llbtn_rand,llbtn_id;
    
    Button btn_top,btn_moi,btn_hot,btn_download,
    	btn_fav,btn_up,btn_follow,btn_search,
    	btnall2,btnall3,btnsetting1,btnsetting2,
    	btnsetting3,btnfollow1,btnfollow2,btnmyimg1,btnmyimg3,
    	btnsortnew,btnsortid,btnsortrand;
    Button btnupload;
//    LinearLayout next1,next2,prev2,prev3,next3;
    LinearLayout layout_image1,layout_image2,lltop;
    LinearLayout m1,m2,m3,m4;
    
    Button btnmenu,btnmenu2,btnmenu3;
    public TextView txt_Uploader;
    
    AutoCompleteTextView edtsearch;
    Animation anmDown,anmUp;
    
    boolean menuvisible;
    static boolean scaned;
    public static boolean getbydate;
    Handler handler;
    CallWebService call;
    sqlite sql;
    String response;
    boolean flag_logout = true,nextprev = true;
    boolean stateMenu = true;
    public static int menuViewInd;
    int versionNumber;
    Users users;
	User user;
	
	ProgressDialog pg;
    String str_userlist;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        scaned = false;
        setContentView(R.layout.main);
        handler = new Handler();

        layout_menu = (KHorizontalScrollView)findViewById(R.id.layout_menu);
        lltop = (LinearLayout)findViewById(R.id.ll_toppanel);
        btn_hot = (Button)findViewById(R.id.btn_hot);
        btn_top = (Button)findViewById(R.id.btn_top);
        btn_moi = (Button)findViewById(R.id.btn_moi);
        btnmenu = (Button)findViewById(R.id.btn_menu);
        btnmenu2 = (Button)findViewById(R.id.btn_menu2);
        btnmenu3 = (Button)findViewById(R.id.btn_menu3);
//        next1 = (LinearLayout)findViewById(R.id.llnext1);
//        next2 = (LinearLayout)findViewById(R.id.llnext2);
//        next3 = (LinearLayout)findViewById(R.id.llnext3);
//        prev2 = (LinearLayout)findViewById(R.id.llprev2);
//        prev3 = (LinearLayout)findViewById(R.id.llprev3);
        btnall2 = (Button)findViewById(R.id.btn_all2);
        btnall3 = (Button)findViewById(R.id.btn_all3);
        btnsetting1 = (Button)findViewById(R.id.btn_setting1);
        btnsetting2 = (Button)findViewById(R.id.btn_setting2);
        btnsetting3 = (Button)findViewById(R.id.btn_setting3);
        btnfollow1 = (Button)findViewById(R.id.btn_follow1);
        btnfollow2 = (Button)findViewById(R.id.btn_follow2);
        btnmyimg1 = (Button)findViewById(R.id.btn_myimg1);
        btnmyimg3 = (Button)findViewById(R.id.btn_myimg3);
        btnsortnew = (Button)findViewById(R.id.btn_sortnew);
        btnsortid = (Button)findViewById(R.id.btn_sortid);
        btnsortrand = (Button)findViewById(R.id.btn_sortrand);
//        llbtn_new = (LinearLayout)findViewById(R.id.llbtn_sortnew);
//        llbtn_id = (LinearLayout)findViewById(R.id.llbtn_sortid);
//        llbtn_rand = (LinearLayout)findViewById(R.id.llbtn_sortrand);
        btnupload = (Button)findViewById(R.id.btnupload);
        
        m1 = (LinearLayout)findViewById(R.id.menu1);
        m2 = (LinearLayout)findViewById(R.id.menu2);
        m3 = (LinearLayout)findViewById(R.id.menu3);
        m4 = (LinearLayout)findViewById(R.id.menu4);
        
        
        btn_download = (Button)findViewById(R.id.btn_down_main);
        btn_fav = (Button)findViewById(R.id.btn_favorite_main);
        btn_up = (Button)findViewById(R.id.btn_up_main);
//        llbtn_down= (LinearLayout)findViewById(R.id.llbtn_down_main);
//        llbtn_fav = (LinearLayout)findViewById(R.id.llbtn_favorite_main);
//        llbtn_up = (LinearLayout)findViewById(R.id.llbtn_up_main);
        btn_search = (Button)findViewById(R.id.btn_SearchMain);
        btn_follow = new Button(this);
        btnsortid.setOnClickListener(this);
        btnsortrand.setOnClickListener(this);
        btnsortnew.setOnClickListener(this);
        
        edtsearch = (AutoCompleteTextView)findViewById(R.id.edt_Search_main);
        edtsearch.setOnKeyListener(this);
      
        edtsearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt_Uploader = (TextView)findViewById(R.id.txt_Uploader);
        txt_Uploader.setVisibility(View.VISIBLE);
        txt_Uploader.setOnClickListener(this);
        ksv = (KSV)findViewById(R.id.ksv);
        ksv.setup(this,KSV.TOP);
        ksv.setFocusable(true);
        type = KSV.TOP;
        nextprev = true;
        ksv.requestFocus();
        Global.err = 0;
        
        btn_top.setOnClickListener(this);
        btn_hot.setOnClickListener(this);
        btn_moi.setOnClickListener(this);
        btnmenu.setOnClickListener(this);
        btnmenu2.setOnClickListener(this);
        btnmenu3.setOnClickListener(this);
        btn_download.setOnClickListener(this);
        btn_fav.setOnClickListener(this);
        btn_up.setOnClickListener(this);
        btn_search.setOnClickListener(this);
//        next1.setOnClickListener(this);
//        next2.setOnClickListener(this);
//        prev2.setOnClickListener(this);
//        prev3.setOnClickListener(this);
//        next3.setOnClickListener(this);
        btnall2.setOnClickListener(this);
        btnall3.setOnClickListener(this);
        btnsetting1.setOnClickListener(this);
        btnsetting2.setOnClickListener(this);
        btnsetting3.setOnClickListener(this);
        btnfollow1.setOnClickListener(this);
        btnfollow2.setOnClickListener(this);
        btnmyimg1.setOnClickListener(this);
        btnmyimg3.setOnClickListener(this);
        btnupload.setOnClickListener(this);
        
        menuvisible = true;
        anmDown = AnimationUtils.loadAnimation(this, R.anim.downout);
        anmUp = AnimationUtils.loadAnimation(this, R.anim.upout);
        anmUp.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				lltop.setVisibility(View.GONE);
			}
		});
        anmDown.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				layout_menu.setVisibility(View.GONE);
			}
		});
        
        call = new CallWebService(this);
        sql = new sqlite(this);
        Global.username = sql.getUsername();
        Global.id = sql.getUserId();
        sql.getInfoUser();
        new getUserList().start();
        if(!hasStorage(true))
        	Toast.makeText(Main.this, R.string.needsd, 1).show();
        
        
        //GetVersionCode
        
        try {
			PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionNumber = pinfo.versionCode;
			new ServerNotify().start();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
        
        registerReceiver(retReceiver, new IntentFilter("com.krazevina.beautifulgirl.ret"));
        registerReceiver(allReceiver, new IntentFilter("com.krazevina.beautifulgirl.all"));
        registerReceiver(downReceiver, new IntentFilter("com.krazevina.beautifulgirl.down"));
        registerReceiver(upReceiver, new IntentFilter("com.krazevina.beautifulgirl.up"));
        registerReceiver(favReceiver, new IntentFilter("com.krazevina.beautifulgirl.fav"));
        registerReceiver(nextReceiver, new IntentFilter("com.krazevina.beautifulgirl.next"));
        registerReceiver(prevReceiver, new IntentFilter("com.krazevina.beautifulgirl.prev"));
        registerReceiver(searchReceiver, new IntentFilter("com.krazevina.beautifulgirl.search"));
        registerReceiver(followReceiver, new IntentFilter("com.krazevina.beautifulgirl.follow"));
    }
    
    class ServerNotify extends Thread
    {
    	String ss1;
    	public void run()
    	{
			ss1 = ""+call.serviceServerVersion();
			handler.post(new Runnable() {
				public void run() {
					ServerAlertDialog sv = new ServerAlertDialog(Main.this, ss1);
					if(sv.show)sv.show();
				}
			});
    	}
    }
    
    public static boolean hasStorage(boolean requireWriteAccess) {  
        String state = Environment.getExternalStorageState();  
      
        if (Environment.MEDIA_MOUNTED.equals(state)) {  
            return true;  
        } else if (!requireWriteAccess  
                && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {  
            return true;  
        }  
        return false;  
    } 
    
    @Override
	public void onWindowFocusChanged(boolean hasFocus) 
    {
		super.onWindowFocusChanged(hasFocus);
		LayoutParams lp = (LayoutParams) m1.getLayoutParams();
		lp.width = getWindowManager().getDefaultDisplay().getWidth();
		m1.setLayoutParams(lp);
		m2.setLayoutParams(lp);
		m3.setLayoutParams(lp);
		m4.setLayoutParams(lp);
	}



	Thread checknet,checktimeout;
    
    @Override
    protected void onResume() 
    {
    	super.onResume();
    	updateMenu();
    	nextprev = true;
    	findview();
    }
    
    public void findview()
    {
    	new Thread(new Runnable() {
			public void run() {
				do
				{
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}while(!layout_menu.isShown());
				handler.post(new Runnable() {
					public void run() {
						try{
					    	switch (ksv.type) {
							case KSV.HOT:
								layout_menu.setCurrview(0);
								txt_Uploader.setText(R.string.hot);
								revertButton(2);
								break;
							case KSV.RANDOM:
								layout_menu.setCurrview(0);
								txt_Uploader.setText(R.string.moi);
								revertButton(1);
								break;
							case KSV.TOP:
								layout_menu.setCurrview(0);
								txt_Uploader.setText(R.string.random);
								revertButton(3);
								break;
							case KSV.DOWNLOADED:
								layout_menu.setCurrview(1);
								txt_Uploader.setText(R.string.mn_picload);
								revertButton(5);
								break;
							case KSV.FAVORITED:
								layout_menu.setCurrview(1);
								txt_Uploader.setText(R.string.favorite);
								revertButton(4);
								break;
							case KSV.UPLOADED:
								layout_menu.setCurrview(1);
								txt_Uploader.setText(R.string.up);
								revertButton(6);
								break;
							case KSV.FOLLOWID:
								layout_menu.setCurrview(2);
								txt_Uploader.setText(R.string.followsortid);
								revertButton(8);
								break;
							case KSV.FOLLOWNEW:
								layout_menu.setCurrview(2);
								txt_Uploader.setText(R.string.followsortnew);
								revertButton(7);
								break;
							case KSV.FOLLOWRAND:
								layout_menu.setCurrview(2);
								txt_Uploader.setText(R.string.followsortrand);
								revertButton(9);
								break;
							}
						}catch (Exception e) {
						}
					}
				});
			}
		}).start();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	nextprev = false;
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	if(event.getAction()==KeyEvent.ACTION_UP)
    	{
			if(event.getKeyCode()==KeyEvent.KEYCODE_MENU)
	    	{
	    		if(getMenuVisible())
	    		{
	    			hideMenu();
	    			if(ksv.getScrollY()<10)ksv.smoothScrollTo(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
	    		}
	    		else
	    		{
	    			layout_menu.setVisibility(View.VISIBLE);
	    			lltop.setVisibility(View.VISIBLE);
	    			menuvisible = true;
	    		}
	    		return true;
	    	}
			if(event.getKeyCode()==KeyEvent.KEYCODE_BACK&&!ignore)
	    	{
	    		if(exitDialog==null)
	    		{
	    			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    			builder.setMessage(getString(R.string.confirmexit))
	    			       .setCancelable(false)
	    			       .setPositiveButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
	    			           public void onClick(DialogInterface dialog, int id) {
	    			                finish();
	    			           }
	    			       })
	    			       .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
	    			           public void onClick(DialogInterface dialog, int id) {
	    			                dialog.cancel();
	    			           }
	    			       });
	    			exitDialog = builder.create();
	    			exitDialog.setCanceledOnTouchOutside(true);
	    		}
	    		exitDialog.show();
	    		ignore = true;
	    		return true;
	    	}
    	}
    	if(event.getAction()==KeyEvent.ACTION_DOWN&&event.getKeyCode()==KeyEvent.KEYCODE_BACK)
    	{
    		ignore = false;
    		return true;
    	}
    	if(event.getKeyCode()==KeyEvent.KEYCODE_ENTER)
    	{
    		onClick(btn_search);
    	}
    	return super.dispatchKeyEvent(event);
    }
    
    boolean ignore = true;
    Dialog exitDialog;
    AlertDialog dialog;
    
    void upload(){
    	if(Global.username==null||Global.username.length()<=0)
		{
			Intent i = new Intent(this,Login.class);
            startActivity(i);
            Toast.makeText(Main.this, R.string.needlogin, 0).show();
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
    
	@Override
	public void onClick(View v) 
	{
		if(v.getId()==btnupload.getId())
		{
			upload();
		}
		if(v.getId() == btn_moi.getId())
		{
			if(ksv.type!=KSV.RANDOM)
				ksv.change(KSV.RANDOM);
			type = KSV.RANDOM;
			revertButton(1);
		}
		if(v.getId() == btn_top.getId())
		{
			ksv.change(KSV.TOP);
			type = KSV.TOP;
			revertButton(3);
		}
		if(v.getId() == btn_hot.getId())
		{
			if(ksv.type!=KSV.HOT)
				ksv.change(KSV.HOT);
			type = KSV.HOT;
			revertButton(2);
		}
		if(v.getId()==btn_search.getId())
		{
			if(edtsearch.getVisibility()==View.GONE)
			{
				edtsearch.setVisibility(View.VISIBLE);
				txt_Uploader.setVisibility(View.GONE);
				return;
			}
			if(edtsearch.getText().toString().length()>0)
			{
				Global.userSearch = edtsearch.getText().toString(); 
				Intent i = new Intent(Main.this,MainSearch.class);
				startActivity(i);
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(edtsearch.getWindowToken(), 0);
			}
			edtsearch.setVisibility(View.GONE);
			txt_Uploader.setVisibility(View.VISIBLE);
		}
		
		if(v.getId()==btn_up.getId())
		{
			if(Global.username==null||Global.username.length()<=0)
			{
				
				Intent i = new Intent(Main.this,Login.class);
				startActivity(i);
				Toast.makeText(Main.this, R.string.needlogin, 0).show();
				return;
			}
			if(ksv.type!=KSV.UPLOADED)
				ksv.change(KSV.UPLOADED);
			type = KSV.UPLOADED;
			revertButton(6);
		}
		if(v.getId()==btn_fav.getId())
		{
			if(Global.username==null||Global.username.length()<=0)
			{
				Intent i = new Intent(Main.this,Login.class);
				startActivity(i);
				Toast.makeText(Main.this, R.string.needlogin, 0).show();
				return;
			}
			if(ksv.type!=KSV.FAVORITED)
				ksv.change(KSV.FAVORITED);
			type = KSV.FAVORITED;
			revertButton(4);
		}
		
		if(v.getId() == btn_download.getId())
		{
			if(ksv.type!=KSV.DOWNLOADED)
				ksv.change(KSV.DOWNLOADED);
			type = KSV.DOWNLOADED;
			revertButton(5);
		}

		if(v.getId()==btnmenu.getId()||v.getId()==btnmenu2.getId()||v.getId()==btnmenu3.getId())
		{
			stateMenu = !stateMenu;
			updateMenu();
		}
		if(v.getId()==btn_follow.getId())
		{
			if(ksv.type!=KSV.FOLLOWRAND)
				ksv.change(KSV.FOLLOWRAND);
			type = KSV.FOLLOWRAND;
		}
//		if(v.getId()==next1.getId())
//		{
//			onClick(btn_download);
//		}
//		if(v.getId()==next2.getId())
//		{
//			onClick(btn_follow);
//			if(Global.username==null||Global.username.length()<=0)
//        	{
//        		startActivity(new Intent(Main.this,Login.class));
//        		Toast.makeText(Main.this, R.string.needlogin, 0).show();
//        	}
//			revertButton(8);
//		}
//		if(v.getId()==prev2.getId())
//		{
//			onClick(btn_hot);
//		}
//		if(v.getId()==prev3.getId())
//		{
//			onClick(btn_download);
//		}
//		if(v.getId()==next3.getId())
//		{
//			clickSetting();
//			stateMenu = true;
//			updateMenu();
//		}
		
		if(v.getId()==btnall2.getId()||v.getId()==btnall3.getId())
		{
			layout_menu.setCurrview(0);
			onClick(btn_hot);
			stateMenu = true;
			updateMenu();
		}
        if(v.getId()==btnmyimg1.getId()||v.getId()==btnmyimg3.getId())
        {
        	layout_menu.setCurrview(1);
        	onClick(btn_download);
        	stateMenu = true;
        	updateMenu();
        }
        if(v.getId()==btnfollow1.getId()||v.getId()==btnfollow2.getId())
        {
        	layout_menu.setCurrview(2);
        	onClick(btn_follow);
        	stateMenu = true;updateMenu();
        }
        if(v.getId()==btnsetting1.getId()||v.getId()==btnsetting2.getId()||v.getId()==btnsetting3.getId())
        {
        	if(Global.username==null||Global.username.length()<=0)
        	{
        		startActivity(new Intent(Main.this,Login.class));
        		return;
        	}
        	clickSetting();stateMenu = true;updateMenu();
        }
        
        if(v.getId()==btnsortid.getId())
        {
        	if(ksv.type!=KSV.FOLLOWID)
        		ksv.change(KSV.FOLLOWID);
        	type = ksv.type;
        	revertButton(7);
        }
        
        if(v.getId()==btnsortnew.getId())
        {
        	if(ksv.type!=KSV.FOLLOWNEW)
        		ksv.change(KSV.FOLLOWNEW);
        	type = ksv.type;
        	revertButton(8);
        }
        
        if(v.getId()==btnsortrand.getId())
        {
        	if(ksv.type!=KSV.FOLLOWRAND)
        		ksv.change(KSV.FOLLOWRAND);
        	type = ksv.type;
        	revertButton(9);
        }
        
        if(v.getId() == txt_Uploader.getId()){
        	if(Global.username==null||Global.username.length()<=0){
        		startActivity(new Intent(Main.this,Login.class));
        		return;
        	}else{
//        		startActivity(new Intent(Main.this,UserList.class));
//        		overridePendingTransition(R.anim.downin,0);
        		new loadUser().start();
        	}
        }
	}
	
	void clickSetting()
	{
		nextprev = false;
		Intent i = new Intent(Main.this,Setting.class);
		startActivityForResult(i,SETTING);
	}
	
	public void hideMenu()
	{
		if(!menuvisible)return;
		menuvisible = false;
		layout_menu.startAnimation(anmDown);
		lltop.startAnimation(anmUp);
	}
	public boolean getMenuVisible()
	{
		return menuvisible;
	}
	
    public void updateMenu()
    {
    	if(stateMenu){
    		btn_moi.setVisibility(View.VISIBLE);
    		btn_hot.setVisibility(View.VISIBLE);
    		btn_top.setVisibility(View.VISIBLE);
    		btn_fav.setVisibility(View.VISIBLE);
    		btn_download.setVisibility(View.VISIBLE);
    		btn_up.setVisibility(View.VISIBLE);
    		btnsortid.setVisibility(View.VISIBLE);
    		btnsortnew.setVisibility(View.VISIBLE);
    		btnsortrand.setVisibility(View.VISIBLE);
    		
            btnall2.setVisibility(View.GONE);
            btnsetting1.setVisibility(View.GONE);
            btnsetting2.setVisibility(View.GONE);
            btnfollow1.setVisibility(View.GONE);
            btnfollow2.setVisibility(View.GONE);
            btnmyimg1.setVisibility(View.GONE);
            btnall3.setVisibility(View.GONE);
    		btnmyimg3.setVisibility(View.GONE);
    		btnsetting3.setVisibility(View.GONE);
    	}else{
    		btn_moi.setVisibility(View.GONE);
    		btn_hot.setVisibility(View.GONE);
    		btn_top.setVisibility(View.GONE);
    		btn_fav.setVisibility(View.GONE);
    		btn_download.setVisibility(View.GONE);
    		btn_up.setVisibility(View.GONE);
    		btnsortid.setVisibility(View.GONE);
    		btnsortnew.setVisibility(View.GONE);
    		btnsortrand.setVisibility(View.GONE);
    		
            btnall2.setVisibility(View.VISIBLE);
            btnsetting1.setVisibility(View.VISIBLE);
            btnsetting2.setVisibility(View.VISIBLE);
            btnfollow1.setVisibility(View.VISIBLE);
            btnfollow2.setVisibility(View.VISIBLE);
            btnmyimg1.setVisibility(View.VISIBLE);
            btnall3.setVisibility(View.VISIBLE);
    		btnmyimg3.setVisibility(View.VISIBLE);
    		btnsetting3.setVisibility(View.VISIBLE);

            
            if(Global.username==null||Global.username.length()<=0)
            {
            	btnsetting1.setText(R.string.login);
            	btnsetting2.setText(R.string.login);
            	btnsetting3.setText(R.string.login);
            }
            else
            {
            	btnsetting1.setText(R.string.option);
            	btnsetting2.setText(R.string.option);
            	btnsetting3.setText(R.string.option);
            }	
    	}
    }
	int dippx(int dip)
	{
		
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
	}
	public void revertButton()
	{
		btn_moi.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.moibtn,0,0);
		btn_hot.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.topbtn,0,0);
		btn_top.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnrandom,0,0);
		btn_fav.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnfavorite,0,0);
		btn_download.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btndownload,0,0);
		btn_up.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnuploadimage,0,0);
		btnsortid.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btndanhsach, 0, 0);
		btnsortnew.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.moibtn, 0, 0);
		btnsortrand.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnrandom, 0, 0);
		
		btn_moi.setTextColor(Color.WHITE);
		btn_hot.setTextColor(Color.WHITE);
		btn_top.setTextColor(Color.WHITE);
		btn_fav.setTextColor(Color.WHITE);
		btn_download.setTextColor(Color.WHITE);
		btn_up.setTextColor(Color.WHITE);
		btnsortid.setTextColor(Color.WHITE);
		btnsortnew.setTextColor(Color.WHITE);
		btnsortrand.setTextColor(Color.WHITE);
		currevert = 0;
	}
	int currevert;
	public void revertButton(int activ)
	{
		currevert = activ;
		if(activ==0)revertButton();
		btn_moi.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.moibtn,0,0);
		btn_hot.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnhot,0,0);
		btn_top.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnrandom,0,0);
		btn_fav.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnfavorite,0,0);
		btn_download.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btndownload,0,0);
		btn_up.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnuploadimage,0,0);
		btnsortid.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btndanhsach, 0, 0);
		btnsortnew.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.moibtn, 0, 0);
		btnsortrand.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnrandom, 0, 0);

		btn_moi.setTextColor(Color.WHITE);
		btn_hot.setTextColor(Color.WHITE);
		btn_top.setTextColor(Color.WHITE);
		btn_fav.setTextColor(Color.WHITE);
		btn_download.setTextColor(Color.WHITE);
		btn_up.setTextColor(Color.WHITE);
		btnsortid.setTextColor(Color.WHITE);
		btnsortnew.setTextColor(Color.WHITE);
		btnsortrand.setTextColor(Color.WHITE);
		if(activ==1)
		{
			btn_moi.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.moiactive,0,0);
			btn_moi.setTextColor(Color.RED);
		}
		if(activ==2)
		{
			btn_hot.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btn_hotactiv,0,0);
			btn_hot.setTextColor(Color.RED);
		}
		if(activ==3)
		{
			btn_top.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.random_highlight,0,0);
			btn_top.setTextColor(Color.RED);
		}
		
		if(activ==4)
		{
			btn_fav.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btn_hotactiv,0,0);
			btn_fav.setTextColor(Color.RED);
		}
		if(activ==5)
		{
			btn_download.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btndownactiv,0,0);
			btn_download.setTextColor(Color.RED);
		}
		if(activ==6)
		{
			btn_up.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.btnuploadactiv,0,0);
			btn_up.setTextColor(Color.RED);
		}
		if(activ==7)
		{
			btnsortnew.setTextColor(Color.RED);
			btnsortnew.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.moiactive, 0, 0);
		}
		if(activ==8)
		{
			btnsortid.setTextColor(Color.RED);
			btnsortid.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.danhsach_icon_highlight, 0, 0);
		}
		if(activ==9)
		{
			btnsortrand.setTextColor(Color.RED);
			btnsortrand.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.random_highlight, 0, 0);
		}
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		unregisterReceiver(retReceiver);
		unregisterReceiver(allReceiver);
		unregisterReceiver(downReceiver);
		unregisterReceiver(upReceiver);
		unregisterReceiver(nextReceiver);
		unregisterReceiver(prevReceiver);
		unregisterReceiver(favReceiver);
		unregisterReceiver(searchReceiver);
		unregisterReceiver(followReceiver);
		ksv.images = null;
		ksv = null;
		nextprev = false;
		sql.close();
	}
	

	public ProgressDialog pr;
	@Override
	protected void onActivityResult(int requestCode, final int resultCode, Intent data) 
	{
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

		txt_Uploader.setText(Global.userSearch);
		ksv.type = type;
		if(requestCode == PHOTO_PICKED)
		{
			if(resultCode == RESULT_OK){  
	            Uri selectedImage = data.getData();
	            Global.data_img = selectedImage;
	            startActivity(new Intent(this,Upload.class));
	        }
		}else if(requestCode == MENU)	// back from main menu
		{
			if(resultCode==11)
			{
				ksv.clearImg();
				ksv.change(type);
			}
			if(resultCode==21)
				onClick(btn_up);
			if(resultCode==31)
				onClick(btn_fav);
			if(resultCode==41)
				onClick(btn_download);
		}
		else if(requestCode==SETTING)
		{
			nextprev=true;
		}else
		{
			ksv.restore(resultCode);
		}
		
		layout_menu.setVisibility(View.VISIBLE);
		lltop.setVisibility(View.VISIBLE);
	}
	
	private final BroadcastReceiver retReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	ksv.clearImg();
			ksv.change(type);
        }
    };
	private final BroadcastReceiver allReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	onClick(btn_top);
        	postslide(0);
        }
    };
    private final BroadcastReceiver downReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	onClick(btn_download);
        	postslide(1);
        }
    };
    private final BroadcastReceiver upReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent){
        	onClick(btn_up);
        	postslide(1);
        }
    };
    private final BroadcastReceiver favReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	onClick(btn_fav);
        	postslide(1);
        }
    };
    private final BroadcastReceiver searchReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	Intent i = new Intent(Main.this,MainSearch.class);
			startActivity(i);
        }
    };
    
    void scansd()
    {
    	if(scaned)return;
    	scaned = true;
		ksv.images.scanSDCard(this, getWindowManager().getDefaultDisplay().getWidth()/2);
    }
    int view;
    private final BroadcastReceiver nextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	if(!nextprev)return;
        	if(layout_menu.getScrollX()<layout_menu.getWidth())	
        		onClick(btn_download);
        	else if(layout_menu.getScrollX()<2*layout_menu.getWidth())	
        	{
        		onClick(btn_follow);
        		if(Global.username==null||Global.username.length()<=0)
            	{
            		startActivity(new Intent(Main.this,Login.class));
            		Toast.makeText(Main.this, R.string.needlogin, 0).show();
            	}
        	}
        	else
        	{
        		clickSetting();
        	}
        }
    };
    
    private final BroadcastReceiver prevReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	if(!nextprev)return;
        	if(layout_menu.getScrollX()>2*layout_menu.getWidth())
        	{
        		onClick(btn_follow);
        		if(Global.username==null||Global.username.length()<=0)
            	{
            		startActivity(new Intent(Main.this,Login.class));
            		Toast.makeText(Main.this, R.string.needlogin, 0).show();
            	}
        	}
        	else if(layout_menu.getScrollX()>layout_menu.getWidth())	
        		onClick(btn_download);
        	else
        		onClick(btn_top);
        }
    };
    private final BroadcastReceiver followReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	onClick(btn_follow);
        	postslide(2);
        	if(Global.username==null||Global.username.length()<=0)
        	{
        		startActivity(new Intent(Main.this,Login.class));
        		Toast.makeText(Main.this, R.string.needlogin, 0).show();
        	}
        }
    };
    void postslide(final int i)
    {
    	menuViewInd = i;
    }
    
    //UserList
    
	public class UserAdapter extends BaseAdapter {
		private LayoutInflater mInflater = null;
		private ViewHolder mViewHolder;
		private Users _users;
		private int count = 0;

		public UserAdapter(Context context, int textViewResourceId, Users users) {
			mInflater = LayoutInflater.from(context);
			this._users = users;
			if (_users != null) {
				if (_users.getCount() != 0)
					count = _users.getCount();
			}
		}

		public int getCount() {
			return count;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_userlist, null);

				mViewHolder = new ViewHolder();
				mViewHolder.mRoot = (LinearLayout) convertView.findViewById(R.id.root_useritem);
				
				// if(position%2!=0)
				// mViewHolder.mRoot.setBackgroundResource(R.drawable.bg_item1);
				// else
				// mViewHolder.mRoot.setBackgroundResource(R.drawable.bg_item2);

				mViewHolder.mUsername = (TextView) convertView.findViewById(R.id.item_uploader);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}

			mViewHolder.mUsername.setText(users.getItemAtPosition(position).getUploader());

			convertView.setId(count);
			return convertView;

		}

		class ViewHolder {
			LinearLayout mRoot;
			TextView mUsername;
		}
	}
	
	
	boolean internet = true;

	class loadUser extends Thread {

		public void run() 
		{
			if(Global.username.equals(""))return;
			handler.post(new Runnable() {
				@Override
				public void run() {
					if(pg==null||!pg.isShowing())
					{
						pg = new ProgressDialog(Main.this);
						pg.setMessage(getString(R.string.pleasewait));
						pg.show();
					}
				}

			});
			
			response = call.FollowList(Global.username);
			try {
				
				if(!(response == null)){
					internet = true;
					Thread.sleep(1000);
					pg.dismiss();
				}else{
					internet = false;
					pg.dismiss();
				}
				
			} catch (Exception e) {
			}
			
			handler.post(new Runnable() {
				@Override
				public void run() {
					try{
						users = new Users();
						if(response.equals("anyType{}")){
							Toast.makeText(Main.this,getString(R.string.warn_list_follow),2).show();
						}else{
							JSONArray jArray = new JSONArray(response);
							JSONObject jsonObject;

							for(int i = 0;i<jArray.length();i++){
								user = new User();
								jsonObject = new JSONObject(jArray.getString(i));
								String uploader = jsonObject.get("userName").toString();
								user.setUploader(uploader);
								users.add(user);
							}
							
							UserAdapter adapter = new UserAdapter(Main.this, R.layout.item_userlist, users);
							
							AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
	        				builder.setTitle(getString(R.string.title_list_follow));
	        				builder.setAdapter(adapter,new DialogInterface.OnClickListener() {
	        							@Override
	        							public void onClick(DialogInterface dialog,int item) {
	        								Global.userSearch = users.getItemAtPosition(item).getUploader();
	        								txt_Uploader.setText(users.getItemAtPosition(item).getUploader());
	        								Intent i = new Intent("com.krazevina.beautifulgirl.search");
	        								sendBroadcast(i);
	        								setResult(10);
	        								dialog.dismiss();
	        							}
	        						});
	        				AlertDialog alert = builder.create();
	        				alert.show();
						}
						

					}catch (Exception e) {
					}
					if(!internet){
						Toast.makeText(Main.this,getString(R.string.err_internet), 2).show();
						return;
					}
				}
			});
		}
	}
	String[] uslist;
	class getUserList extends Thread{
		public void run(){
			try {
				str_userlist = call.UsernameList(0, 0);
				JSONArray jArray = new JSONArray(str_userlist);
				JSONObject jsonObject;
				sql.dropAndCreateUserList();
				for(int i = 0;i<jArray.length();i++){
					jsonObject = new JSONObject(jArray.getString(i));
					String uploader = jsonObject.get("userName").toString();
					sql.insertUser(uploader);
				}
				uslist = sql.selectAllUser();
				
			} catch (Exception e1) {
			}
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					try{
						if(str_userlist==null){
							uslist = sql.selectAllUser();
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main.this,android.R.layout.simple_dropdown_item_1line,uslist);
							edtsearch.setAdapter(adapter);
						}else if(str_userlist.equals("anyType{}")){
							Toast.makeText(Main.this,getString(R.string.warn_list_follow),2).show();
						}else{
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main.this,android.R.layout.simple_dropdown_item_1line,uslist);
							edtsearch.setAdapter(adapter);
						}
					}catch (Exception e) {
					}
				}
			});
		}
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_ENTER){
			onClick(btn_search);
		}
		return false;
	}
}
