package com.krazevina.euro2012;

import java.util.Vector;

import com.krazevina.euro2012.News.ViewHolder;
import com.krazevina.objects.Match;
import com.krazevina.objects.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class Main extends Activity implements OnClickListener {
	
    Button btnSchedule,btnNews,btnTeams,btnSetting;
    LinearLayout llbtnsched,llbtnnews,llbtnteams,llbtnsetting;
    Vector<Match> match;
    LinearLayout llgroupa,llgroupb,llgroupc,llgroupd,llquarter,llsemi,llfinal;
    int i ;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        llbtnsched = (LinearLayout)findViewById(R.id.llbtnsched);
        llbtnnews = (LinearLayout)findViewById(R.id.llbtnnews);
        llbtnteams = (LinearLayout)findViewById(R.id.llbtnteams);
        llbtnsetting = (LinearLayout)findViewById(R.id.llbtnsetting);
        
        llgroupa = (LinearLayout)findViewById(R.id.llgroupa);
        llgroupb = (LinearLayout)findViewById(R.id.llgroupb);
        llgroupc = (LinearLayout)findViewById(R.id.llgroupc);
        llgroupd = (LinearLayout)findViewById(R.id.llgroupd);
        llquarter = (LinearLayout)findViewById(R.id.llquarter);
        llsemi = (LinearLayout)findViewById(R.id.llsemi);
        llfinal = (LinearLayout)findViewById(R.id.llfinal);
                
        llbtnnews.setOnTouchListener(touch);
        llbtnsched.setOnTouchListener(touch);
        llbtnteams.setOnTouchListener(touch);
        llbtnsetting.setOnTouchListener(touch);
        
        btnSchedule = (Button)findViewById(R.id.btnSchedule);
        btnNews = (Button)findViewById(R.id.btnNews);
        btnTeams = (Button)findViewById(R.id.btnTeams);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        
        btnNews.setOnClickListener(this);
        btnTeams.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        sqlite sql;
        sql = new sqlite(this);
        
        match = sql.getAllMatches();
        sql.recycle();
        
        for(int i =0;i<match.size();i++){
        	Log.d("match.groupid",""+match.elementAt(i).groupID);
        }
        
		LinearLayout ll;
		LayoutInflater mInflater = LayoutInflater.from(this);
		TextView txtsday,txtstime,txtsteam1,txtsteam2,txtsratio;
		ImageView imgsflag1,imgsflag2;

//		   convertView = mInflater.inflate(R.layout.itemschedule, null);
		
		int j = 0;
		   for(i = 0;i<match.size();i++){
			   if(match.elementAt(i).groupID == 1){
				   
				   ll = (LinearLayout) mInflater.inflate(R.layout.itemschedule, null);
				   ll.setOrientation(LinearLayout.HORIZONTAL);

				   txtsday = (TextView)ll.findViewById(R.id.txtsday);
				   txtstime = (TextView)ll.findViewById(R.id.txtstime);
				   txtsteam1 = (TextView)ll.findViewById(R.id.txtsteam1);
				   txtsteam2 = (TextView)ll.findViewById(R.id.txtsteam2);
				   txtsratio = (TextView)ll.findViewById(R.id.txtsratio);
				   imgsflag1 = (ImageView)ll.findViewById(R.id.imgsflag1);
				   imgsflag2 = (ImageView)ll.findViewById(R.id.imgsflag2);
					
				   txtsday.setText(match.elementAt(i).start);
				   txtsteam1.setOnClickListener(new OnClickListener() {
					int ind = i;
					@Override
					public void onClick(View v) {
						Intent i  = new Intent();
						i.putExtra("idTeam", match.elementAt(ind).team1);
						startActivity(new Intent(Main.this,Teams.class));
						
						}
				   });
				   
				   llgroupa.addView(ll);
				   j++;
				   if(j%2==0){
					   ll.setBackgroundColor(Color.parseColor("#ffffff"));
				   }else{
					   ll.setBackgroundColor(Color.parseColor("#e9efe9"));
				   }
			   }
		   }


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
		if(v.getId() == btnNews.getId()){
			startActivity(new Intent(this,News.class));
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
	
}