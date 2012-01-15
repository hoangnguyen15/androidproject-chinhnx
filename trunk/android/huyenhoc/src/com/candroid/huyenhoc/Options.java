package com.candroid.huyenhoc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class Options extends Activity implements OnClickListener{
	LinearLayout btnBack,btnOptions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		btnBack = (LinearLayout)findViewById(R.id.btnBack);
		btnOptions = (LinearLayout)findViewById(R.id.btnOptions);
		
		btnBack.setOnClickListener(this);
		btnOptions.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnBack.getId()){
			finish();
		}
		if(v.getId() == btnOptions.getId()){
			startActivity(new Intent(Options.this,ChildMenu.class));
		}
		
	}
}
