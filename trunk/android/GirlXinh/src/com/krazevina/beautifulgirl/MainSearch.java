package com.krazevina.beautifulgirl;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
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

public class MainSearch extends Activity implements OnClickListener
{
	protected static final int PHOTO_PICKED = 1;
	protected static final int RETRY = 2;
	public static final int LOGIN = 3;
	protected static final int MENU = 4;
	protected static final int SETTING = 7;
    public KHorizontalScrollView layout_menu;
    public KSV ksv;public static int type;
    Button btn_top,btn_moi,btn_hot,
    	btn_search;
    LinearLayout layout_image1,layout_image2,lltop;
    LinearLayout m1,m2,m3,m4;
    
    TextView btnmenu;
    public TextView txt_Uploader;
    Animation anmDown,anmUp;
    
    boolean menuvisible;
    static boolean scaned;
    public static boolean getbydate;
    Handler handler;
    CallWebService call;
    String response;
    boolean flag_logout = true,nextprev = true;
    boolean stateMenu = true;
    public static int menuViewInd;
    
    Users users;
	User user;
	
	ProgressDialog pg;
    String userlist;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        scaned = false;
        setContentView(R.layout.mainsearch);
        handler = new Handler();

        layout_menu = (KHorizontalScrollView)findViewById(R.id.layout_menu);
        lltop = (LinearLayout)findViewById(R.id.ll_toppanel);
        btn_hot = (Button)findViewById(R.id.btn_hot);
        btn_top = (Button)findViewById(R.id.btn_top);
        btn_moi = (Button)findViewById(R.id.btn_moi);
        btnmenu = (TextView)findViewById(R.id.btn_menu);
        
        m1 = (LinearLayout)findViewById(R.id.menu1);
        
        btn_search = (Button)findViewById(R.id.btn_SearchMain);
        
        txt_Uploader = (TextView)findViewById(R.id.txt_Uploader);
        txt_Uploader.setVisibility(View.VISIBLE);
        ksv = (KSV)findViewById(R.id.ksv);
        ksv.setup(this,KSV.SEARCHNEW);
        ksv.setFocusable(true);
        type = KSV.SEARCHNEW;
        nextprev = true;
        ksv.requestFocus();
        Global.err = 0;
        
        txt_Uploader.setText(Global.userSearch);
        txt_Uploader.setOnClickListener(this);
        btn_top.setOnClickListener(this);
        btn_hot.setOnClickListener(this);
        btn_moi.setOnClickListener(this);
        btnmenu.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        
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
        sqlite sql = new sqlite(MainSearch.this);
        if(sql.getfollow(Global.userSearch))
			btn_search.setBackgroundResource(R.drawable.followactive);
		else
			btn_search.setBackgroundResource(R.drawable.followactive);
        revertButton(1);
    }
    
    
    @Override
	public void onWindowFocusChanged(boolean hasFocus) 
    {
		super.onWindowFocusChanged(hasFocus);
		LayoutParams lp = (LayoutParams) m1.getLayoutParams();
		lp.width = getWindowManager().getDefaultDisplay().getWidth();
		m1.setLayoutParams(lp);
	}



	Thread checknet,checktimeout;
    
    @Override
    protected void onResume() 
    {
    	super.onResume();
    	nextprev = true;
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
    	}
    	return super.dispatchKeyEvent(event);
    }
    
    boolean ignore = true;
    Dialog exitDialog;
    
	@Override
	public void onClick(View v) 
	{
		if(v.getId() == btn_moi.getId())
		{
			if(ksv.type!=KSV.SEARCHNEW)
				ksv.change(KSV.SEARCHNEW);
			type = KSV.SEARCHNEW;
			revertButton(1);
		}
		if(v.getId() == btn_top.getId())
		{
			if(ksv.type!=KSV.SEARCHTOP)
				ksv.change(KSV.SEARCHTOP);
			type = KSV.SEARCHTOP;
			revertButton(3);
		}
		if(v.getId() == btn_hot.getId())
		{
			if(ksv.type!=KSV.SEARCHHOT)
				ksv.change(KSV.SEARCHHOT);
			type = KSV.SEARCHHOT;
			revertButton(2);
		}
		if(v.getId()==btn_search.getId())
		{
			if(Global.username==null||Global.username.length()<=0)
			{
				Intent in = new Intent(MainSearch.this,Login.class);
				startActivityForResult(in,5);
				Toast.makeText(getApplicationContext(), getString(R.string.needlogin), 0).show();
				return;
			}
			new follow().start();
		}
		
		if(v.getId()==btnmenu.getId() || v.getId() == txt_Uploader.getId())
		{
			finish();
		}
	}
	
	class follow extends Thread
	{
		public void run()
		{
			sqlite sql = new sqlite(MainSearch.this);
			sql.follow(Global.userSearch);
			sql.close();
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(MainSearch.this, R.string.followed, 0).show();
				}
			});
		}
	}
	
	public void hideMenu()
	{
		menuvisible = false;
		layout_menu.startAnimation(anmDown);
		lltop.startAnimation(anmUp);
	}
	public boolean getMenuVisible()
	{
		return menuvisible;
	}
	
	int dippx(int dip)
	{
		
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
	}

	int currevert;
	public void revertButton(int activ)
	{
		currevert = activ;
		btn_moi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.moibtn,0,0,0);
		btn_hot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btnhot,0,0,0);
		btn_top.setCompoundDrawablesWithIntrinsicBounds(R.drawable.topbtn,0,0,0);

		btn_moi.setTextColor(Color.WHITE);
		btn_hot.setTextColor(Color.WHITE);
		btn_top.setTextColor(Color.WHITE);
		if(activ==1)
		{
			btn_moi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.moiactive,0,0,0);
			btn_moi.setTextColor(Color.RED);
		}
		if(activ==2)
		{
			btn_hot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_hotactiv,0,0,0);
			btn_hot.setTextColor(Color.RED);
		}
		if(activ==3)
		{
			btn_top.setCompoundDrawablesWithIntrinsicBounds(R.drawable.topactive,0,0,0);
			btn_top.setTextColor(Color.RED);
		}
	}
	

	public ProgressDialog pr;
	@Override
	protected void onActivityResult(int requestCode, final int resultCode, Intent data) 
	{
		txt_Uploader.setText(Global.userSearch);
		ksv.setPos(resultCode);		// update position.
		layout_menu.setVisibility(View.VISIBLE);
		lltop.setVisibility(View.VISIBLE);
		ksv.restore();
	}

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
}
