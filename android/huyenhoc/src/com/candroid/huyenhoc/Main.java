package com.candroid.huyenhoc;

import com.candroid.objects.Global;
import com.candroid.objects.Sqlite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	EditText edtName;
	Button btnFinish;
	Sqlite sql;
	TextView txtDayofbirth, txtMonthofbirth, txtYearofbirth;
	String name, dateofbith;
	String hourofbirth, minuteofbirth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/HL-OngDo-Unicode.ttf");
		Global.face = tf;
		setContentView(R.layout.main);

		edtName = (EditText) findViewById(R.id.edtName);
		edtName.setTypeface(tf);
		btnFinish = (Button) findViewById(R.id.btnFinish);
		txtDayofbirth = (TextView) findViewById(R.id.txtDayofbith);
		txtMonthofbirth = (TextView) findViewById(R.id.txtMonthofbirh);
		txtYearofbirth = (TextView) findViewById(R.id.txtYearofbirth);
		sql = new Sqlite(this);
		btnFinish.setOnClickListener(this);
		
		Log.d("getName",sql.getName());
		// GetName
//		if (sql.getName().length() != 0) {
//			startActivity(new Intent(this, MainMenu.class));
//			finish();
//		}
		

	}


	
	@Override
	public void onClick(View v) {
		if (v.getId() == btnFinish.getId()) {
			name=edtName.getText().toString();
			if(name.length() == 0){
				Toast.makeText(this,getString(R.string.err_name), 2).show();
			}else{
				dateofbith = txtYearofbirth.getText() + "-"+txtMonthofbirth.getText()+"-"+txtDayofbirth.getText();
				sql.setName(name, dateofbith, 0, 0);
				startActivity(new Intent(this, MainMenu.class));
			}
		}

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sql.close();
	}
}