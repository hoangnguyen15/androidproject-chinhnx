package com.krazevina.euro2012;

import com.krazevina.objects.Team;
import com.krazevina.objects.sqlite;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

public class TeamDetails extends Activity{
	Team t;
	TextView txtteam,txtdesc;
	ListView lv;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teamdetail);
		
		sqlite sql = new sqlite(this);
	    int teamId = getIntent().getIntExtra("idTeam", 155);
	    t = sql.getTeam(teamId);
	    sql.recycle();
	    
	    txtteam = (TextView)findViewById(R.id.txtteam);
	    txtdesc = (TextView)findViewById(R.id.txtdesc);
	    lv = (ListView)findViewById(R.id.lvplayers);
	    
	    txtteam.setText(t.name);
	    txtdesc.setText(t.desc);
	};
	

}
