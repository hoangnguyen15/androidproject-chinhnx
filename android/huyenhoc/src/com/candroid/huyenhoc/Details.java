package com.candroid.huyenhoc;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Details extends Activity implements OnClickListener {
	Button btnBuy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		btnBuy = (Btn)findViewById(R.id.btnBuy);
		btnBuy.setOnClickListener(this);
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
