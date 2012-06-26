package com.chinhnx.anhcothichnuocmykhong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Info extends Activity implements OnClickListener{
	Button btnOK;
	TextView txtEmail;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		btnOK = (Button)findViewById(R.id.btnOk);
		txtEmail = (TextView)findViewById(R.id.txtEmail);
		btnOK.setOnClickListener(this);
		txtEmail.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnOK.getId()){
			finish();
		}
		
		if(v.getId() == txtEmail.getId()){
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.email)});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            startActivity(Intent.createChooser(emailIntent, getString(R.string.choose)));
			finish();
		}
		
	}
}
