package com.candroid.huyenhoc;


import com.candroid.objects.Global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Activity implements OnClickListener {
	Button btnBuy;
	TextView txtcap;
	int type;
	String[]stype;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		btnBuy = (Btn)findViewById(R.id.btnBuy);
		txtcap = (TextView)findViewById(R.id.txtcap);
		btnBuy.setOnClickListener(this);
//		stype = getResources().getStringArray(R.array.optiontype);
		type = getIntent().getIntExtra("type", 0);
//		txtcap.setText(stype[type]);
		Log.d("group.c",""+Global.groups.count());
		for(int i =0;i<Global.groups.count();i++){
			Log.d("group.c",""+Global.groups.getItem(i).getDes());
			Log.d("group.c",""+Global.groups.getItem(i).getMc());
			Log.d("group.c",""+Global.groups.getItem(i).getMp());
			Log.d("group.c",""+Global.groups.getItem(i).getParam1());
			Log.d("group.c",""+Global.groups.getItem(i).getParam2());
			Log.d("group.c",""+Global.groups.getItem(i).getParam3());
			Log.d("group.c",""+Global.groups.getItem(i).getParam4());
			Log.d("group.c",""+Global.groups.getItem(i).getSmsnumber());
			Log.d("group.c",""+Global.groups.getItem(i).getUrl());
			Log.d("group.c",""+Global.groups.getItem(i).getOption());
			Log.d("group.c","-----------------------------");
		}

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btnBuy.getId()) {
			Intent sendIntent = new Intent(Intent.ACTION_VIEW);
			long time = System.currentTimeMillis();
			try {
				String timerequest = "HUYENHOC" + time;
				sendIntent.putExtra("address", "8085");
				sendIntent.putExtra("sms_body", timerequest);
				sendIntent.setType("vnd.android-dir/mms-sms");
				startActivity(sendIntent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
