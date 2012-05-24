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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TeamDetails extends Activity implements OnClickListener{
	Team t;
	Vector<Player>players;
	TextView txtteam,txtdesc,txtest,txtfifajoin,txtfifarank,txtattend,txtcoach;
	ImageView imgflag,imguni1,imguni2;
	
	LinearLayout lv;
	String pos[][]= new String[][]{
			{"Hậu vệ","Defender","수비"},
			{"Thủ môn","Goalkeeper", "골키퍼"},
			{"Tiền vệ","Midfielder","미드필드"},
			{"Tiền đạo","Striker","공격수"}
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
	    imgflag = (ImageView)findViewById(R.id.imgflag);
	    imguni1 = (ImageView)findViewById(R.id.imguniform1);
	    imguni2 = (ImageView)findViewById(R.id.imguniform2);
	    
	    lv = (LinearLayout)findViewById(R.id.lvplayers);
	    btnBack = (Button)findViewById(R.id.btnBack);
	    btnBack.setOnClickListener(this);
	    
	    if(Global.lang.equals("EN")){
	    	txtteam.setText(t.nameEng.toUpperCase());
	    	txtdesc.setText(t.descEng);
	    }
	    else if(Global.lang.equals("KO")){
	    	txtteam.setText(t.nameKor.toUpperCase());
	    	txtdesc.setText(t.descKor);
	    }
	    else{
	    	txtteam.setText(t.name.toUpperCase());
	    	txtdesc.setText(t.desc);
	    }
	    txtest.setText(getString(R.string.estimate)+":\t"+t.establish);
    	txtfifajoin.setText(getString(R.string.fifajoin)+":\t"+t.fifaJoin);
    	txtfifarank.setText(getString(R.string.fifarank)+":\t"+t.fifaRank);
    	txtcoach.setText(getString(R.string.coach)+":\t"+t.coach);
    	txtattend.setText(getString(R.string.attend)+":\t"+t.attendTimes);
    	imgflag.setImageResource(flag(t.ID));
    	imguni1.setImageResource(uni1(t.ID));
    	imguni2.setImageResource(uni2(t.ID));

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
	public static int flag(int teamID){
		switch (teamID) {
		case 157:return R.drawable.poll;
		case 155:return R.drawable.gerl;
		case 171:return R.drawable.itall;
		case 159:return R.drawable.engl;
		case 10144:return R.drawable.grel;
		case 10145:return R.drawable.rusl;
		case 175:return R.drawable.czel;
		case 166:return R.drawable.nedl;
		case 10141:return R.drawable.denl;
		case 168:return R.drawable.porl;
		case 185:return R.drawable.espl;
		case 10148:return R.drawable.irll;
		case 179:return R.drawable.crol;
		case 182:return R.drawable.fral;
		case 186:return R.drawable.ukrl;
		case 162:return R.drawable.swel;
		}
		return 0;
	}
	public static int uni1(int teamID){
		switch (teamID) {
		case 157:return R.drawable.aopoland1;
		case 155:return R.drawable.aogermany1;
		case 171:return R.drawable.aoitaly1;
		case 159:return R.drawable.ao_england1;
		case 10144:return R.drawable.aogreece1;
		case 10145:return R.drawable.aorussia1;
		case 175:return R.drawable.ao_czech1;
		case 166:return R.drawable.ao_holland1;
		case 10141:return R.drawable.aodenmark1;
		case 168:return R.drawable.ao_portugal1;
		case 185:return R.drawable.ao_spain1;
		case 10148:return R.drawable.ao_ireland1;
		case 179:return R.drawable.ao_croatia1;
		case 182:return R.drawable.ao_france1;
		case 186:return R.drawable.ao_ukraine1;
		case 162:return R.drawable.ao_sweden1;
		}
		return 0;
	}
	public static int uni2(int teamID){
		switch (teamID) {
		case 157:return R.drawable.aopoland2;
		case 155:return R.drawable.aogermany2;
		case 171:return R.drawable.aoitaly2;
		case 159:return R.drawable.ao_england2;
		case 10144:return R.drawable.aogreece2;
		case 10145:return R.drawable.aorussia2;
		case 175:return R.drawable.ao_czech2;
		case 166:return R.drawable.ao_holland2;
		case 10141:return R.drawable.aodenmark2;
		case 168:return R.drawable.ao_portugal2;
		case 185:return R.drawable.ao_spain2;
		case 10148:return R.drawable.ao_ireland2;
		case 179:return R.drawable.ao_croatia2;
		case 182:return R.drawable.ao_france2;
		case 186:return R.drawable.ao_ukraine2;
		case 162:return R.drawable.ao_sweden2;
		}
		return 0;
	}
}
