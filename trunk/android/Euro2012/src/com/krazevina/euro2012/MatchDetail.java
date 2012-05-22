package com.krazevina.euro2012;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krazevina.objects.Bet;
import com.krazevina.objects.Global;
import com.krazevina.objects.Match;
import com.krazevina.objects.Player;
import com.krazevina.objects.SocketConnect;
import com.krazevina.objects.Team;
import com.krazevina.objects.sqlite;

public class MatchDetail extends Activity implements OnClickListener{
	
	ImageView flag1,flag2;
	TextView name1,name2,score,bet11,bet12,bet13,bet21,bet22,bet23,bet31,bet32,bet33;
//	T,stadium,referee;
	LinearLayout llevent;
	sqlite sql;
	Match m;Team t1,t2;
	Player p;
	int i;
	Button btnBack,btnvote1,btnvote2;
	Vector<Bet> b;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matchdetail);
		flag1 = (ImageView)findViewById(R.id.flag1);
		flag2 = (ImageView)findViewById(R.id.flag2);
		name1 = (TextView)findViewById(R.id.name1);
		name2 = (TextView)findViewById(R.id.name2);
		score = (TextView)findViewById(R.id.score);
//		stadium = (TextView)findViewById(R.id.stadium);
//		referee = (TextView)findViewById(R.id.referee);
		llevent = (LinearLayout)findViewById(R.id.llevent);
		btnBack = (Button)findViewById(R.id.btnBack);
		bet11 = (TextView)findViewById(R.id.txtbet11);
		bet11 = (TextView)findViewById(R.id.txtbet12);
		bet11 = (TextView)findViewById(R.id.txtbet13);
		bet11 = (TextView)findViewById(R.id.txtbet21);
		bet11 = (TextView)findViewById(R.id.txtbet22);
		bet11 = (TextView)findViewById(R.id.txtbet23);
		bet11 = (TextView)findViewById(R.id.txtbet31);
		bet11 = (TextView)findViewById(R.id.txtbet32);
		bet11 = (TextView)findViewById(R.id.txtbet33);
		btnvote1 = (Button)findViewById(R.id.btnvote1);
		btnvote2 = (Button)findViewById(R.id.btnvote2);
		btnBack.setOnClickListener(this);
		sql = new sqlite(this);
		m = Global.match;
		m.events = sql.getEvents(m);
		b = sql.getBet(m.ID);
		
		flag1.setImageResource(TeamDetails.flag(m.team1));
		flag2.setImageResource(TeamDetails.flag(m.team2));
		t1 = sql.getTeam(m.team1);
		t2 = sql.getTeam(m.team2);
		
		name1.setText(t1.name);
		name2.setText(t2.name);
		btnvote1.setText(m.firstPick);
		btnvote2.setText(m.secPick);
		if(m.finalScore!=null&&m.finalScore.length()>0)score.setText(m.finalScore);
		else score.setText("?-?");
//		stadium.setText(m.stadium);
//		referee.setText(m.referee);
		
		LinearLayout ll;
		LayoutInflater mInflater = LayoutInflater.from(this);
		TextView txttime,txtname;
		ImageView imgtype1,imgtype2;
		
		int j = 0;
		for(i = 0;i<m.events.size();i++){
			ll = (LinearLayout) mInflater.inflate(R.layout.itemschedule, null);
			ll.setOrientation(LinearLayout.HORIZONTAL);

			txttime = (TextView)ll.findViewById(R.id.time);
			txtname = (TextView)ll.findViewById(R.id.name);
			imgtype1 = (ImageView)ll.findViewById(R.id.type1);
			imgtype2 = (ImageView)ll.findViewById(R.id.type2);
			txttime.setText(m.events.get(i).time);
			p = sql.getlayer(m.events.get(i).playerID);
			txtname.setText(p.name);
			if(m.team1==m.events.get(i).teamID){
				imgtype1.setImageResource(Type(m.events.get(i).eventID));
				imgtype2.setVisibility(View.GONE);
				txtname.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
			}else{
				imgtype2.setImageResource(Type(m.events.get(i).eventID));
				imgtype1.setVisibility(View.GONE);
				txtname.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
			}
			
			j++;
			if(j%2==0){
				ll.setBackgroundColor(Color.parseColor("#ffffff"));
			}else{
				ll.setBackgroundColor(Color.parseColor("#e9efe9"));
			}
			llevent.addView(ll);
		}
		bet11.setText(getAsia().col1);
		bet12.setText(getAsia().col2);
		bet13.setText(getAsia().col3);
		bet21.setText(getEuro().col1);
		bet22.setText(getEuro().col2);
		bet23.setText(getEuro().col3);
		bet31.setText(getUO().col1);
		bet32.setText(getUO().col2);
		bet33.setText(getUO().col3);
		btnvote1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new SocketConnect().vote(m.ID, 1);
			}
		});
		btnvote2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new SocketConnect().vote(m.ID, 2);
			}
		});
	}
	int Type(int t){
		switch (t) {
		case 1:return R.drawable.goal;
		case 2:return R.drawable.yellow;
		case 3:return R.drawable.red;
		case 4:return 0;
		case 5:return 0;

		default:
			return 0;
		}
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnBack.getId()){
			finish();
		}
	}
	Bet getAsia(){
		for(int i=0;i<b.size();i++)if(b.get(i).bethouse==1)return b.get(i);
		return new Bet();
	}
	Bet getEuro(){
		for(int i=0;i<b.size();i++)if(b.get(i).bethouse==3)return b.get(i);
		return new Bet();
	}
	Bet getUO(){
		for(int i=0;i<b.size();i++)if(b.get(i).bethouse==2)return b.get(i);
		return new Bet();
	}
}

