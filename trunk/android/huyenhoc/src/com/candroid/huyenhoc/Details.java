package com.candroid.huyenhoc;


import java.util.HashMap;
import java.util.Map;

import com.candroid.objects.Global;
import com.candroid.webservice.WebServiceC;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Activity implements OnClickListener {
	Button btnBuy;
	TextView txtcap;
	LinearLayout btnBack;
	int position;
	String[]stype;
	EditText edtParam1,edtParam2,edtParam3,edtParam4;
	String des,mp,mc,smsnumber,url,option;
	String param1,param2,param3,param4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		btnBuy = (Btn)findViewById(R.id.btnBuy);
		txtcap = (TextView)findViewById(R.id.txtcap);
		btnBack = (LinearLayout)findViewById(R.id.btnBack);
		btnBuy.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		position = getIntent().getIntExtra("type", 0);
		txtcap.setText(Global.groups.getItem(position).getSrvName());
		
		edtParam1 = (EditText)findViewById(R.id.edtParam1);
		edtParam2 = (EditText)findViewById(R.id.edtParam2);
		edtParam3 = (EditText)findViewById(R.id.edtParam3);
		edtParam4 = (EditText)findViewById(R.id.edtParam4);
		
		des = Global.groups.getItem(position).getDes();
		mc = Global.groups.getItem(position).getMc();
		mp = Global.groups.getItem(position).getMp();
		url = Global.groups.getItem(position).getUrl();
		option = Global.groups.getItem(position).getOption();
		smsnumber = Global.groups.getItem(position).getSmsnumber();
		
		param1 = Global.groups.getItem(position).getParam1();
		param2 = Global.groups.getItem(position).getParam2();
		param3 = Global.groups.getItem(position).getParam3();
		param4 = Global.groups.getItem(position).getParam4();
		
		if(param1.length()!=0){
			edtParam1.setHint(param1);
		}else{
			edtParam1.setVisibility(View.GONE);
		}
		
		if(param2.length()!=0){
			edtParam2.setHint(param2);
		}else{
			edtParam2.setVisibility(View.GONE);
		}
		
		if(param3.length()!=0){
			edtParam3.setHint(param3);
		}else{
			edtParam3.setVisibility(View.GONE);
		}
		
		if(param4.length()!=0){
			edtParam4.setHint(param4);
		}else{
			edtParam4.setVisibility(View.GONE);
		}


	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btnBuy.getId()) {
//			Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//			long time = System.currentTimeMillis();
//			try {
//				String getParam1 = edtParam1.getText().toString();
//				String getParam2 = edtParam2.getText().toString();
//				String getParam3 = edtParam3.getText().toString();
//				String getParam4 = edtParam4.getText().toString();
//				
//				String contentsms = mc +" " + mp +" " + getParam1 +" " +getParam2+" " +getParam3+" " +getParam4+" " + time;
//				sendIntent.putExtra("address", Global.smsinbox);
//				sendIntent.putExtra("sms_body", contentsms);
//				sendIntent.setType("vnd.android-dir/mms-sms");
//				startActivity(sendIntent);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			String getParam1 = edtParam1.getText().toString();
			String getParam2 = edtParam2.getText().toString();
			String getParam3 = edtParam3.getText().toString();
			String getParam4 = edtParam4.getText().toString();
			WebServiceC w = new WebServiceC(WebServiceC.url+ WebServiceC.url_SmsMobile);
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", "84906289289");
			params.put("pas", "hehe");
			params.put("mcode", mc);
			params.put("scode",mp);
			params.put("param1",getParam1);
			params.put("param2",getParam2);
			params.put("param3",getParam3);
			params.put("param4",getParam4);
			String result_api = w.webGet("", params);
			Global.result = result_api;
			startActivity(new Intent(this,Result.class));
		}
		if(v.getId() == btnBack.getId()){
			finish();
		}

	}
}
