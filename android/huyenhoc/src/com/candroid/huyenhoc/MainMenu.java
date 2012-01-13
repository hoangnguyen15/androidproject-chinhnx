package com.candroid.huyenhoc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener{
	Button btnApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		btnApp = (Button)findViewById(R.id.btnApp);
		btnApp.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnApp.getId()){
			startActivity(new Intent(this,ChildMenu.class));
		}
		
	}
}
