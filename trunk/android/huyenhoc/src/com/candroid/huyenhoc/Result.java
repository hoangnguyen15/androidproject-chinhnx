package com.candroid.huyenhoc;



import com.candroid.objects.Global;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Result extends Activity implements OnClickListener {
	TextView txtResult;
	LinearLayout btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		txtResult = (TextView)findViewById(R.id.txtResult);
		txtResult.setText(Global.result);
		btnBack = (LinearLayout)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == btnBack.getId()){
			finish();
		}

	}
}
