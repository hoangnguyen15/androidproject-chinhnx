package com.krazevina.euro2012;

import java.util.Vector;

import com.krazevina.objects.Global;
import com.krazevina.objects.Player;
import com.krazevina.objects.Team;
import com.krazevina.objects.sqlite;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TeamDetails extends Activity implements OnClickListener{
	Team t;
	Vector<Player>players;
	TextView txtteam,txtdesc,txtest,txtfifajoin,txtfifarank,txtattend,txtcoach;
	
	LinearLayout lv;
	String pos[][]= new String[][]{
			{"hậu vệ","Defender","옹호자"},
			{"thủ môn","Goalkeeper", "골키퍼"},
			{"tiền vệ","Midfielder","미드필더"},
			{"tiền đạo","Striker","수비수"},
	};
	Button btnBack;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teamdetail);
		
		sqlite sql = new sqlite(this);
	    int teamId = getIntent().getIntExtra("idTeam", 155);
	    t = sql.getTeam(teamId);
	    players = sql.getTeamPlayers(teamId);
	    sql.recycle();
	    
	    txtteam = (TextView)findViewById(R.id.txtteam);
	    txtdesc = (TextView)findViewById(R.id.txtdesc);
	    txtest = (TextView)findViewById(R.id.txtestyear);
	    txtfifajoin = (TextView)findViewById(R.id.txtfifajoin);
	    txtfifarank = (TextView)findViewById(R.id.txtfifarank);
	    txtattend = (TextView)findViewById(R.id.txtattend);
	    txtcoach = (TextView)findViewById(R.id.txtcoach);
	    
	    lv = (LinearLayout)findViewById(R.id.lvplayers);
	    btnBack = (Button)findViewById(R.id.btnBack);
	    btnBack.setOnClickListener(this);
	    
	    if(Global.lang.equals("EN")){
	    	txtteam.setText(t.nameEng);
	    	txtdesc.setText(t.descEng);
	    }
	    else if(Global.lang.equals("KO")){
	    	txtteam.setText(t.nameKor);
	    	txtdesc.setText(t.descKor);
	    }
	    else{
	    	txtteam.setText(t.name);
	    	txtdesc.setText(t.desc);
	    }
	    txtest.setText(getString(R.string.estimate)+":\t"+t.establish);
    	txtfifajoin.setText(getString(R.string.fifajoin)+":\t"+t.fifaJoin);
    	txtfifarank.setText(getString(R.string.fifarank)+":\t"+t.fifaRank);
    	txtcoach.setText(getString(R.string.coach)+":\t"+t.coach);
    	txtattend.setText(getString(R.string.attend)+":\t"+t.attendTimes);

	    LayoutInflater li = LayoutInflater.from(TeamDetails.this);;
		TextView no,name,pos;
		View convertView;
		for(int i=0;i<players.size();i++){
			convertView = li.inflate(R.layout.itemteamplayers, null);
			if(i%2==0){
				convertView.setBackgroundColor(Color.parseColor("#ffffff"));
			}else{
				convertView.setBackgroundColor(Color.parseColor("#e9efe9"));
			}
			no = (TextView)convertView.findViewById(R.id.no);
			name = (TextView)convertView.findViewById(R.id.name);
			pos = (TextView)convertView.findViewById(R.id.pos);
			
			no.setText(""+(players.get(i).number!=null?players.get(i).number:""));
			name.setText(players.get(i).name);
			pos.setText(pos(players.get(i).pos));
			lv.addView(convertView);
		}
	};
	
	String pos(String posi){
		if(Global.lang.equals("VI"))return posi;
		if(Global.lang.equals("EN"))
			for(int i=0;i<4;i++){
				if(posi.toLowerCase().equals(pos[i][0]))
					return pos[i][1];
			}
		if(Global.lang.equals("KO"))
			for(int i=0;i<4;i++){
				if(posi.toLowerCase().equals(pos[i][0]))
					return pos[i][2];
			}
		return "";
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnBack.getId()){
			finish();
		}
	}
}
