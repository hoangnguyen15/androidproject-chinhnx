package com.krazevina.beautifulgirl;

import com.krazevina.objects.Global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Search extends Activity implements OnClickListener{
	Button btn_search,btn_cancel;
	EditText edt_search;
	String txt_search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		btn_search = (Button)findViewById(R.id.btn_search);
		btn_cancel = (Button)findViewById(R.id.btn_cancel);
		edt_search = (EditText)findViewById(R.id.edt_search);
		btn_search.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btn_search.getId()){
			txt_search = edt_search.getText().toString();
			if(txt_search.equals("")){
				Toast.makeText(this,getString(R.string.not_blank),2).show();
				return;
			}else{
				Global.userSearch = txt_search;
				Intent i = new Intent("com.krazevina.beautifulgirl.search");
				sendBroadcast(i);
				setResult(10);
				finish();
			}
		}
		if(v.getId() == btn_cancel.getId()){
			finish();
		}
	}
}
