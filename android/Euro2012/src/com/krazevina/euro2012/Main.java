package com.krazevina.euro2012;

import com.krazevina.objects.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Main extends Activity implements OnClickListener {
	
    Button btnSchedule,btnNews,btnTeams,btnSetting;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnSchedule = (Button)findViewById(R.id.btnSchedule);
        btnNews = (Button)findViewById(R.id.btnNews);
        btnTeams = (Button)findViewById(R.id.btnTeams);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        
        btnNews.setOnClickListener(this);
        btnTeams.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        sqlite sql;
        sql = new sqlite(this);
    }
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