package com.krazevina.beautifulgirl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Accept_rule extends Activity implements OnClickListener{
	Button btn_exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accept_rule);
		btn_exit = (Button)findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btn_exit.getId()){
			finish();
		}
	}
}
