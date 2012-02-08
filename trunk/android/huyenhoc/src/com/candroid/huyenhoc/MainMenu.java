package com.candroid.huyenhoc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener{
	Button btnApp,btnChange,btnSetting,btnIntro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		btnApp = (Button)findViewById(R.id.btnApp);
		btnChange = (Button)findViewById(R.id.btnChange);
		btnSetting = (Button)findViewById(R.id.btnSetting);
		btnIntro = (Button)findViewById(R.id.btnIntro);
		btnApp.setOnClickListener(this);
		btnChange.setOnClickListener(this);
		btnSetting.setOnClickListener(this);
		btnIntro.setOnClickListener(this);
				
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnApp.getId()){
			startActivity(new Intent(this,Options.class));
		}
		if(v.getId() == btnSetting.getId()){
			startActivity(new Intent(this,Main.class));
			finish();
		}
		
	}
}
