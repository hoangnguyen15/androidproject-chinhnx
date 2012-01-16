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
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener,OnTouchListener {
	EditText edtName;
	Button btnFinish;
	Sqlite sql;
	TextView txtDayofbirth, txtMonthofbirth, txtYearofbirth;
	String name, dateofbith;
	String hourofbirth, minuteofbirth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/HL-OngDo-Unicode.ttf");
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
		txtDayofbirth.setOnTouchListener(this);
		txtMonthofbirth.setOnTouchListener(this);
		txtYearofbirth.setOnTouchListener(this);
		


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
				finish();
			}
		}

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sql.close();
	}


	float downx,downy;
	int val;
	int min,max;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		TextView tv = (TextView)v;
		if(v.getId() == txtDayofbirth.getId()){
			min = 1;
			max = 31;
		}
		if(v.getId() == txtMonthofbirth.getId()){
			min = 1;
			max = 12;
		}
		if(v.getId() == txtYearofbirth.getId()){
			max = 2030;
			min = 1930;
		}
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			downy = event.getRawY();
			return true;
		}
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(event.getRawY()-downy>20){
				val = Integer.parseInt(tv.getText().toString());
				val--;if(val<min)val = max;
				tv.setText(""+val);
				return true;
			}
			if(downy - event.getRawY()>20){
				val = Integer.parseInt(tv.getText().toString());
				val++;if(val>max)val = min;
				tv.setText(""+val);
				return true;
			}
		}
		return false;
	}
}