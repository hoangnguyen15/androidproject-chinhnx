package com.krazevina.thioto;

import com.krazevina.thioto.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabWidget;

public class Abouts extends Activity {

	@Override
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.about);
	}
	
	@Override
	public void onResume(){
		Tab.tabwidget.setVisibility(TabWidget.GONE);
		super.onResume();
	}
	@Override
	public void onBackPressed() {
		
		Log.d("currentab",""+Tab.currentTab);
		Tab.tabwidget.setVisibility(TabWidget.VISIBLE);
		Tab.tabHost.setCurrentTab(Tab.currentTab);
		
	}
}
