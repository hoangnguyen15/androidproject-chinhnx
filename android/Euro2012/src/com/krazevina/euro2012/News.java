package com.krazevina.euro2012;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.krazevina.objects.ImageDownloader;
import com.krazevina.rss.RSSFeed;
import com.krazevina.rss.RSSItem;
import com.krazevina.rss.XmlReader;


public class News extends Activity implements OnClickListener {
	
    Button btnSchedule,btnNews,btnTeams,btnSetting;
    LinearLayout llbtnsched,llbtnnews,llbtnteams,llbtnsetting;
    ViewFlipper vfnew;
    ListView lvnew;
    RSSFeed rss1;
    Handler handler;
    ProgressDialog pr;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        llbtnsched = (LinearLayout)findViewById(R.id.llbtnsched);
        llbtnnews = (LinearLayout)findViewById(R.id.llbtnnews);
        llbtnteams = (LinearLayout)findViewById(R.id.llbtnteams);
        llbtnsetting = (LinearLayout)findViewById(R.id.llbtnsetting);
        llbtnnews.setOnTouchListener(touch);
        llbtnsched.setOnTouchListener(touch);
        llbtnteams.setOnTouchListener(touch);
        llbtnsetting.setOnTouchListener(touch);
        
        btnSchedule = (Button)findViewById(R.id.btnSchedule);
        btnNews = (Button)findViewById(R.id.btnNews);
        btnTeams = (Button)findViewById(R.id.btnTeams);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        
        vfnew = (ViewFlipper)findViewById(R.id.vfnews);
        lvnew = (ListView)findViewById(R.id.lvnews);
        
        btnTeams.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnSchedule.setOnClickListener(this);
        pr = ProgressDialog.show(News.this, null, R.string.pleasewait);
        handler = new Handler();
        new Thread(new Runnable() {
			@Override
			public void run() {
		        rss1 = new XmlReader("http://bongdaplus.vn/_RSS_/4.rss").parse();
		        handler.post(new Runnable() {
					public void run() {
						lvnew.setAdapter(new adapter());
						lvnew.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								showInfo(rss1.get(arg2));
								vfnew.setDisplayedChild(1);
							}
						});
					}
				});
			}
		}).start();
    }
    
    public void onBackPressed() {
    	if(vfnew.getDisplayedChild()>0)vfnew.setDisplayedChild(0);
    	else super.onBackPressed();
    };
    
    ImageView img;
    Bitmap b;
    void showInfo(final RSSItem item){
    	img = (ImageView)findViewById(R.id.imgnews);
    	TextView tit = (TextView)findViewById(R.id.txtinfotitle);
    	TextView txtcon = (TextView)findViewById(R.id.txtcontent);
    	tit.setText(item.getTitle());
    	txtcon.setText(" "+item.getDescription());
    	new Thread(new Runnable() {
    		String url = item.getEnclosure();
			public void run() {
				b = ImageDownloader.readImageByUrl(url);
				handler.post(new Runnable() {
					public void run() {
						img.setImageBitmap(b);
					}
				});
			}
		}).start();
    }
    
    OnTouchListener touch = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			try{
				LinearLayout l = (LinearLayout)v;
				((Button)l.getChildAt(0)).onTouchEvent(event);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	};
	
	@Override
	public void onClick(View v) {
		if(v.getId() == btnSchedule.getId()){
			startActivity(new Intent(this,Main.class));
			finish();
		}
		
		if(v.getId() == btnTeams.getId()){
			startActivity(new Intent(this,Teams.class));
			finish();
		}
		
		if(v.getId() == btnSetting.getId()){
			startActivity(new Intent(this,Setting.class));
			finish();
		}
	}
	
	class adapter extends BaseAdapter{
		LayoutInflater li;
		TextView tit,time;
		public adapter() {
			li = LayoutInflater.from(News.this);
		}
		@Override
		public int getCount() {
			return rss1.getCount();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = li.inflate(R.layout.newitem, null);
			}
			tit = (TextView)convertView.findViewById(R.id.txttitle);
			time = (TextView)convertView.findViewById(R.id.txttime);
			tit.setText(rss1.get(position).getTitle());
			time.setText(rss1.get(position).getPubDate());
			return convertView;
		}
		
	}
}