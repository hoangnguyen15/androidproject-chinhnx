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
	TextView txtHourofbith,txtMinuteofbirth,txtLunarHourofbith;
	TextView txtMale,txtFemale;
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
		txtHourofbith = (TextView)findViewById(R.id.txtHourofbirth);
		txtMinuteofbirth = (TextView)findViewById(R.id.txtMinuteofbirth);
		txtLunarHourofbith = (TextView)findViewById(R.id.txtLunarHourofbith);
		
		sql = new Sqlite(this);
		btnFinish.setOnClickListener(this);
		txtDayofbirth.setOnTouchListener(this);
		txtMonthofbirth.setOnTouchListener(this);
		txtYearofbirth.setOnTouchListener(this);
		txtHourofbith.setOnTouchListener(this);
		txtMinuteofbirth.setOnTouchListener(this);
		


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
			int month = Integer.parseInt(txtMonthofbirth.getText().toString());
			int year = Integer.parseInt(txtYearofbirth.getText().toString());
			min = 1;
			max = 31;
			if(month == 2){
				max = 28;
				if(year %4 == 0){
					max = 29;
				}
			}
			switch (month) {
			case 4:
				max = 30;
				break;
			case 6:
				max = 30;
				break;
			case 9:
				max = 30;
				break;
			case 11:
				max = 30;
				break;
			default:
				break;
			}
			if(Integer.parseInt(txtMonthofbirth.getText().toString()) == 3){
				max = 28;
				if(Integer.parseInt(txtYearofbirth.getText().toString())%4 == 0){
					max = 29;
				}
			}
		}
		if(v.getId() == txtMonthofbirth.getId()){
			min = 1;
			max = 12;
		}
		if(v.getId() == txtYearofbirth.getId()){
			max = 2030;
			min = 1930;
		}
		if(v.getId() == txtHourofbith.getId()){
			min = 0;
			max = 23;
		}
		if(v.getId() == txtMinuteofbirth.getId()){
			min = 0;
			max = 59;
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
		if(v.getId() == txtHourofbith.getId()){
			int hourOfbirh = Integer.parseInt(txtHourofbith.getText().toString());
			int minuteOfbirth = Integer.parseInt(txtMinuteofbirth.getText().toString());
			switch (hourOfbirh) {
			case 23:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Hợi");
				}
				txtLunarHourofbith.setText("Tý");
				break;
			
			case 0:
				txtLunarHourofbith.setText("Tý");
				break;
			case 1:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Tý");
				}
				txtLunarHourofbith.setText("Sửu");
				break;
			case 2:
				txtLunarHourofbith.setText("Sửu");
				break;

			case 3:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Sửu");
				}
				txtLunarHourofbith.setText("Dần");
				break;
			case 4:
				txtLunarHourofbith.setText("Dần");
				break;
			case 5:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Dần");
				}
				txtLunarHourofbith.setText("Mão");
				break;
			case 6:
				txtLunarHourofbith.setText("Mão");
				break;

			case 7:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Mão");
				}
				txtLunarHourofbith.setText("Thìn");
				break;
			case 8:
				txtLunarHourofbith.setText("Thìn");
				break;
			case 9:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Thìn");
				}
				txtLunarHourofbith.setText("Tỵ");
				break;
			case 10:
				txtLunarHourofbith.setText("Tỵ");
				break;
			case 11:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Tỵ");
				}
				txtLunarHourofbith.setText("Ngọ");
				break;
			case 12:
				txtLunarHourofbith.setText("Ngọ");
				break;
			case 13:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Ngọ");
				}
				txtLunarHourofbith.setText("Mùi");
				break;
			case 14:
				txtLunarHourofbith.setText("Mùi");
				break;
			case 15:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Mùi");
				}
				txtLunarHourofbith.setText("Thân");
				break;
			case 16:
				txtLunarHourofbith.setText("Thân");
				break;
			case 17:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Thân");
				}
				txtLunarHourofbith.setText("Dậu");
				break;
			case 18:
				txtLunarHourofbith.setText("Dậu");
				break;
			case 19:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Dậu");
				}
				txtLunarHourofbith.setText("Tuất");
				break;
			case 20:
				txtLunarHourofbith.setText("Tuất");
				break;
			case 21:
				if(minuteOfbirth == 0){
					txtLunarHourofbith.setText("Tuất");
				}
				txtLunarHourofbith.setText("Hợi");
				break;
			case 22:
				txtLunarHourofbith.setText("Hợi");
				break;
				

			default:
				break;
			}
		}
		return false;
	}
}