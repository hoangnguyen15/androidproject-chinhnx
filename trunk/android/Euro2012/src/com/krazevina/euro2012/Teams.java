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
        
        btnNews.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnSchedule.setOnClickListener(this);
        
        sqlite sql = new sqlite(this);
        teams = sql.getAllTeams();
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
			
//			imgflag.setImageResource(resId)
//			name.setText(v.get(i).name);
//			txtstime.setText(time[1].split(":")[0]+":"+time[1].split(":")[1]);
//			int teamID1 = match.elementAt(i).team1;
//			int teamID2 = match.elementAt(i).team2;
//			imgsflag1.setImageResource(searchFlag(teamID1));
//			imgsflag2.setImageResource(searchFlag(teamID2));
//			txtsteam1.setText(sql.searchNameTeam(teamID1));
//			txtsteam2.setText(sql.searchNameTeam(teamID2));
//			String ratio = match.elementAt(i).finalScore;
//			if(ratio!=null){
//				txtsratio.setText(ratio);
//			}else{
//				txtsratio.setText("?-?");
//			}
//			
//			
//			j++;
//			if(j%2==0){
//				ll.setBackgroundColor(Color.parseColor("#ffffff"));
//			}else{
//				ll.setBackgroundColor(Color.parseColor("#e9efe9"));
//			}
//			
//			if(match.elementAt(i).groupID == 1){
//			   llgroupa.addView(ll);
//			}else if(match.elementAt(i).groupID ==2){
//				llgroupb.addView(ll);
//			}else if(match.elementAt(i).groupID ==3){
//				llgroupc.addView(ll);
//			}else if(match.elementAt(i).groupID ==4){
//				llgroupd.addView(ll);
//			}else if(match.elementAt(i).groupID ==5){
//				llquarter.addView(ll);
//			}else if(match.elementAt(i).groupID ==6){
//				llsemi.addView(ll);
//			}else if(match.elementAt(i).groupID ==7){
//				llfinal.addView(ll);
//			}
		}
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