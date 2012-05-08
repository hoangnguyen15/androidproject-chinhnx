package com.krazevina.euro2012;

import com.krazevina.objects.Team;
import com.krazevina.objects.sqlite;

import android.app.Activity;

public class TeamDetails extends Activity{
	Team t;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teamdetail);
		
		sqlite sql = new sqlite(this);
	    int teamId = getIntent().getIntExtra("idTeam", 155);
	    t = sql.getTeam(teamId);
	    sql.recycle();
	    
	    
	    
	};
	

}
