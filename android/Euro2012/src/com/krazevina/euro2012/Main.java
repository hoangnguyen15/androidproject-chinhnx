package com.krazevina.euro2012;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Main extends Activity {
	
    LinearLayout btnSchedule,btnNews,btnTeams,btnSetting;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnSchedule = (LinearLayout)findViewById(R.id.btnSchedule);
        btnNews = (LinearLayout)findViewById(R.id.btnNews);
        btnTeams = (LinearLayout)findViewById(R.id.btnTeams);
        btnSetting = (LinearLayout)findViewById(R.id.btnSetting);
    }
}