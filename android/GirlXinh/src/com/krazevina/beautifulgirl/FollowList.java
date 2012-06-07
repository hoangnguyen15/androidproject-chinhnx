package com.krazevina.beautifulgirl;

import org.json.JSONArray;
import org.json.JSONObject;
import com.krazevina.objects.Follow;
import com.krazevina.objects.Follows;
import com.krazevina.objects.Global;
import com.krazevina.objects.KHorizontalScrollView;
import com.krazevina.objects.sqlite;
import com.krazevina.webservice.CallWebService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class FollowList extends Activity implements OnClickListener {
	ListView lst_follow;
	Follows follows;
	Follow follow;
	CallWebService call;
	Handler handler;
	String response;
	ProgressDialog pg;
	sqlite sql;
	Button btnacc,btnsetting,btnlist,btnall,btnmyimg,btnimgfollow;
	Button btnlabel;
	public KHorizontalScrollView layout_menu;
	LinearLayout m1,m2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.followlist);
		lst_follow = (ListView)findViewById(R.id.lst_follow);
		call = new CallWebService(this);
		sql = new sqlite(this);
		nextprev = true;
		
		handler = new Handler();
		new loadFollowList().start();
		
		layout_menu = (KHorizontalScrollView)findViewById(R.id.layout_menu);
		btnacc = (Button)findViewById(R.id.btnacc);
		btnlist = (Button)findViewById(R.id.btnlist);
		btnall = (Button)findViewById(R.id.btnall);
		btnmyimg = (Button)findViewById(R.id.btnmyimg);
		btnimgfollow = (Button)findViewById(R.id.btnimgfollow);
		btnlabel = (Button)findViewById(R.id.txt);
		btnsetting = (Button)findViewById(R.id.btnsetting);
		m1 = (LinearLayout)findViewById(R.id.m1);
		m2 = (LinearLayout)findViewById(R.id.m2);
		
		btnacc.setOnClickListener(this);
		btnlist.setOnClickListener(this);
		btnall.setOnClickListener(this);
		btnmyimg.setOnClickListener(this);
		btnimgfollow.setOnClickListener(this);
		btnlabel.setOnClickListener(this);
		btnsetting.setOnClickListener(this);
		registerReceiver(prevReceiver, new IntentFilter("com.krazevina.beautifulgirl.prev"));
		LayoutParams lp;
		lp = (LayoutParams) m1.getLayoutParams();
		lp.width = getWindowManager().getDefaultDisplay().getWidth();
		m1.setLayoutParams(lp);
		m2.setLayoutParams(lp);
		layout_menu.setCurrview(1,getWindowManager().getDefaultDisplay().getWidth());
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(100);
					handler.post(new Runnable() {
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
	private final BroadcastReceiver prevReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	if(!nextprev)return;
        	Intent i = new Intent("com.krazevina.beautifulgirl.follow");
        	sendBroadcast(i);
        	finish();
        }
    };
	boolean nextprev;
	int pos;
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		sql.close();
		unregisterReceiver(prevReceiver);
		nextprev = false;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}
	
	public class FollowAdapter extends BaseAdapter {
		private LayoutInflater mInflater = null;
		private ViewHolder mViewHolder;
		private Follows _follows;
		private int count = 0;

		public FollowAdapter(Context context, int textViewResourceId, Follows follows) {
			mInflater = LayoutInflater.from(context);
			this._follows = follows;
			if (_follows != null) {
				if (_follows.getCount() != 0)
					count = _follows.getCount();
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
				convertView = mInflater.inflate(R.layout.item_listfollow, null);

				mViewHolder = new ViewHolder();
				mViewHolder.mRoot = (LinearLayout) convertView.findViewById(R.id.root_listitem);
				
				// if(position%2!=0)
				// mViewHolder.mRoot.setBackgroundResource(R.drawable.bg_item1);
				// else
				// mViewHolder.mRoot.setBackgroundResource(R.drawable.bg_item2);

				mViewHolder.mUsername = (TextView) convertView.findViewById(R.id.item_uploader);
				mViewHolder.mBtnDel = (Button)convertView.findViewById(R.id.btn_item_delete);
				
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			pos = position;
			mViewHolder.mBtnDel.setOnClickListener(new OnClickListener() {
				int p = pos;
				@Override
				public void onClick(View v) {
					DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					        switch (which){
					        case DialogInterface.BUTTON_POSITIVE:
					        	new Thread(new Runnable() 
					        	{
									public void run() 
									{
										sqlite sql = new sqlite(FollowList.this);
										String s = call.UnFollow(Global.username, follows.getItemAtPosition(p).getUploader());
										if(s!=null)sql.unfollow(follows.getItemAtPosition(p).getUploader());
										sql.close();
										new loadFollowList().start();
									}
								}).start();
					        	
					            break;

					        case DialogInterface.BUTTON_NEGATIVE:
					            break;
					        }
					    }
					};
					AlertDialog.Builder builder = new AlertDialog.Builder(FollowList.this);
					builder.setMessage(getString(R.string.unfollow_question)).setPositiveButton(getString(R.string.yes), dialogClickListener)
					    .setNegativeButton(getString(R.string.no), dialogClickListener).show();
					
				}
			});
			mViewHolder.mUsername.setText(follows.getItemAtPosition(position).getUploader());
			mViewHolder.mUsername.setOnClickListener(new OnClickListener() {
				int p = pos;
				@Override
				public void onClick(View v) {
					Global.userSearch = follows.getItemAtPosition(p).getUploader();
					Intent i = new Intent("com.krazevina.beautifulgirl.search");
					sendBroadcast(i);
					setResult(10);
					finish();
				}
			});
			
			convertView.setId(count);
			return convertView;

		}

		class ViewHolder {
			LinearLayout mRoot;
			TextView mUsername;
			Button mBtnDel;
		}
	}
	
	
	boolean internet = true;
	protected class loadFollowList extends Thread{
		@Override
		public void run()
		{
			handler.post(new Runnable() {
				public void run() {
					pg = new ProgressDialog(FollowList.this);
					pg.setMessage(getString(R.string.pleasewait));
					pg.show();
				}
			});
			
			response = call.FollowList(Global.username);
			
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
					try{
						follows = new Follows();
						if(response.equals("anyType{}")){
							Toast.makeText(FollowList.this,getString(R.string.warn_list_follow),2).show();
						}else{
							JSONArray jArray = new JSONArray(response);
							JSONObject jsonObject;

							for(int i = 0;i<jArray.length();i++){
								follow = new Follow();
								jsonObject = new JSONObject(jArray.getString(i));
								String uploader = jsonObject.get("userName").toString();
								follow.setUploader(uploader);
								follows.add(follow);
							}
						}
						FollowAdapter adapter = new FollowAdapter(FollowList.this, R.layout.item_listfollow, follows);
						lst_follow.setAdapter(adapter);

					}catch (Exception e) {
					}
					if(!internet){
						Toast.makeText(FollowList.this,getString(R.string.err_internet), 2).show();
						return;
					}
				}
			});
			
		}
	}
	
	boolean statemenu = true;
	@Override
	public void onClick(View v) {
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
			Intent i = new Intent(this,Editinfo.class);
            startActivity(i);
            finish();
		}
		if(v.getId()==btnsetting.getId())
		{
			Intent i = new Intent(this,Setting.class);
            startActivity(i);
            finish();
		}
	}
	
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
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getAction()==KeyEvent.ACTION_UP&&event.getKeyCode()==KeyEvent.KEYCODE_BACK)
		{
			Intent i = new Intent("com.krazevina.beautifulgirl.follow");
			sendBroadcast(i);
		}
		return super.dispatchKeyEvent(event);
	}

	
}
