package com.krazevina.euro2012;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krazevina.objects.Team;
import com.krazevina.objects.sqlite;


public class Teams extends Activity implements OnClickListener {
	
    Button btnSchedule,btnNews,btnTeams,btnSetting;
    LinearLayout llbtnsched,llbtnnews,llbtnteams,llbtnsetting;
    LinearLayout lla,llb,llc,lld;
    Vector<Team>teams,ta,tb,tc,td;
    
    int viewCount = 0;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teams);
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
        
        lla = (LinearLayout)findViewById(R.id.lla);
        llb = (LinearLayout)findViewById(R.id.llb);
        llc = (LinearLayout)findViewById(R.id.llc);
        lld = (LinearLayout)findViewById(R.id.lld);
        
        btnNews.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnSchedule.setOnClickListener(this);
        
        sqlite sql = new sqlite(this);
        teams = sql.getAllTeams();
        sort();
        viewCount=0;addView(ta, lla);
        viewCount=0;addView(tb, llb);
        viewCount=0;addView(tc, llc);
        viewCount=0;addView(td, lld);
    }
    
    void sort(){
    	ta = new Vector<Team>();tc = new Vector<Team>();
    	tb = new Vector<Team>();td = new Vector<Team>();
    	for(int i=0;i<teams.size();i++){
    		if(teams.get(i).roundID==1)ta.add(teams.get(i));
    		if(teams.get(i).roundID==2)tb.add(teams.get(i));
    		if(teams.get(i).roundID==3)tc.add(teams.get(i));
    		if(teams.get(i).roundID==4)td.add(teams.get(i));
    	}
    	sort1(ta);sort1(tb);
    	sort1(tc);sort1(td);
    }
    
    void addView(Vector<Team>v,LinearLayout lll){
    	int i;LinearLayout ll;
    	LayoutInflater mInflater = LayoutInflater.from(this);
    	ImageView imgflag;
    	TextView name,pld,win,lose,dr,gf,gd,pts;
    	
    	for(i = 0;i<v.size();i++){
			ll = (LinearLayout) mInflater.inflate(R.layout.itemschedule, null);
			ll.setOrientation(LinearLayout.HORIZONTAL);

			imgflag = (ImageView)ll.findViewById(R.id.imgflag);
			name = (TextView)ll.findViewById(R.id.txtname);
			pld = (TextView)ll.findViewById(R.id.pld);
			win = (TextView)ll.findViewById(R.id.win);
			lose = (TextView)ll.findViewById(R.id.lose);
			dr = (TextView)ll.findViewById(R.id.draw);
			gf = (TextView)ll.findViewById(R.id.gf);
			gd = (TextView)ll.findViewById(R.id.gd);
			pts = (TextView)ll.findViewById(R.id.pts);
			
			imgflag.setImageResource(flag(v.get(i).ID));
			name.setText(v.get(i).name);
			pld.setText(""+(v.get(i).win+v.get(i).lose+v.get(i).draw));
			win.setText(""+(v.get(i).win));
			lose.setText(""+(v.get(i).lose));
			dr.setText(""+(v.get(i).draw));
			gf.setText(""+(v.get(i).goalscore));
			gd.setText(""+(v.get(i).goallose));
			pts.setText(""+v.get(i).point);
			
			viewCount++;
			if(viewCount%2==0){
				ll.setBackgroundColor(Color.parseColor("#ffffff"));
			}else{
				ll.setBackgroundColor(Color.parseColor("#e9efe9"));
			}
			lll.addView(ll);
		}
    }
    
    public static int flag(int teamID){
		switch (teamID) {
		case 157:return R.drawable.pol;
		case 155:return R.drawable.ger;
		case 171:return R.drawable.ita;
		case 159:return R.drawable.eng;
		case 10144:return R.drawable.gre;
		case 10145:return R.drawable.rus;
		case 175:return R.drawable.cze;
		case 166:return R.drawable.ned;
		case 10141:return R.drawable.den;
		case 168:return R.drawable.por;
		case 185:return R.drawable.esp;
		case 10148:return R.drawable.irl;
		case 179:return R.drawable.cro;
		case 182:return R.drawable.fra;
		case 186:return R.drawable.ukr;
		case 162:return R.drawable.swe;
		}
		return 0;
	}
    
    void sort1(Vector<Team> temp){
    	Team templ;
    	for(int i=0;i<temp.size()-1;i++)
        	for(int j=i+1;j<temp.size();j++)
        		if(temp.get(i).point<temp.get(j).point){
        			templ = temp.get(i);
        			temp.set(i, temp.get(j));
        			temp.set(j, templ);
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
		
		if(v.getId() == btnSchedule.getId()){
			startActivity(new Intent(this,Main.class));
			finish();
		}
		
		if(v.getId() == btnSetting.getId()){
			startActivity(new Intent(this,Setting.class));
			finish();
		}
		
	}
	
}