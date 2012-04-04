package com.candroid.huyenhoc;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import com.candroid.objects.Global;
import com.candroid.objects.Infor;
import com.candroid.objects.Sqlite;
import com.candroid.webservice.Callwebservice;
import com.candroid.webservice.WebServiceC;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class Main extends Activity implements OnClickListener {
	EditText edtName;
	Button btnFinish;
	Sqlite sql;
	WheelView month,year,day;
	WheelView hours,minutes;
	TextView txtLunarHourofbith;
	TextView txtMale,txtFemale;
	String name, dateofbith;
	String hourofbirth, minuteofbirth;
	boolean male = true;
	int sex;
	Infor infor;
	boolean isEdit;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/HL-OngDo-Unicode.ttf");
		Global.face = tf;
		setContentView(R.layout.main);

		edtName = (EditText) findViewById(R.id.edtName);
		edtName.setTypeface(tf);
		btnFinish = (Button) findViewById(R.id.btnFinish);
		txtMale = (TextView)findViewById(R.id.txtMale);
		txtFemale = (TextView)findViewById(R.id.txtFemale);
		
		sql = new Sqlite(this);
		btnFinish.setOnClickListener(this);
		txtMale.setOnClickListener(this);
		txtFemale.setOnClickListener(this);
		infor = sql.getInfo();
		isEdit = getIntent().getBooleanExtra("Edit",false);
		
		if(isEdit || infor.getName().length()==0){

		//setSex
		if(infor.getSex() == 1){
			male = true;
			txtMale.setTextColor(Color.parseColor("#7f0000"));
			txtFemale.setTextColor(Color.parseColor("#cccccc"));
			sex = 1;
		}else{
			male = false;
			txtFemale.setTextColor(Color.parseColor("#7f0000"));
			txtMale.setTextColor(Color.parseColor("#cccccc"));
			sex = 0;

		}
        Calendar calendar = Calendar.getInstance();
        
        day = (WheelView) findViewById(R.id.txtDayofbith);
        month = (WheelView) findViewById(R.id.txtMonthofbirh);
        year = (WheelView) findViewById(R.id.txtYearofbirth);
        hours = (WheelView) findViewById(R.id.txtHourofbirth);
        minutes = (WheelView) findViewById(R.id.txtMinuteofbirth);
        txtLunarHourofbith = (TextView)findViewById(R.id.txtLunarHourofbith);
        
        
        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day);
            }
        };

        // month
        int curMonth = calendar.get(Calendar.MONTH);
        month.setViewAdapter(new DateNumericAdapter(this, 1, 12,0));
        if(infor.getMonthofbith() == 0){
        	month.setCurrentItem(curMonth);
        }else{
        	month.setCurrentItem(infor.getMonthofbith());
        }
        month.addChangingListener(listener);
    
        // year
        int curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new DateNumericAdapter(this, 1900, 2099, 0));
        if(infor.getYearofbith() == 0){
        	year.setCurrentItem(curYear-1900);
        }else{
        	year.setCurrentItem(infor.getYearofbith());
        }
        year.addChangingListener(listener);
        
        //day
        updateDays(year, month, day);
        if(infor.getDayofbith() == 0){
        	day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        }else
        	day.setCurrentItem(infor.getDayofbith());
        

        //hour
        hours.setViewAdapter(new DateNumericAdapter(this, 0, 23,0));
        hours.setCurrentItem(infor.getHourofbith());
        updateLunarTime();
        hours.addChangingListener(new OnWheelChangedListener() {			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateLunarTime();
			}
		});
        
        //minute
        minutes.setViewAdapter(new DateNumericAdapter(this, 0, 59,0));
        minutes.setCurrentItem(infor.getMinuteofbith());
//      minutes.setCyclic(true);
        
        //GetInfo        
        if(infor.getName().length()!=0){
        	edtName.setText(infor.getName());
        }
		}else{
			finish();
			startActivity(new Intent(this,MainMenu.class));
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btnFinish.getId()) {
			name=edtName.getText().toString();
			if(name.length() == 0){
				Toast.makeText(this,getString(R.string.err_name), 2).show();
			}else{
				dateofbith = year.getCurrentItem()+1900 + "-"+(month.getCurrentItem()+1)+"-"+(day.getCurrentItem()+1);
				sql.setName(name, day.getCurrentItem(), month.getCurrentItem(), year.getCurrentItem(), hours.getCurrentItem(), minutes.getCurrentItem(), sex);
				startActivity(new Intent(this, MainMenu.class));
				finish();
			}
		}
		
		if(v.getId() == txtMale.getId()){
			if(!male){
				txtMale.setTextColor(Color.parseColor("#7f0000"));
				txtFemale.setTextColor(Color.parseColor("#cccccc"));
				male = true;
				sex = 1;
			}
		}
		
		if(v.getId() == txtFemale.getId()){
			if(male){
				txtFemale.setTextColor(Color.parseColor("#7f0000"));
				txtMale.setTextColor(Color.parseColor("#cccccc"));
				male = false;
				sex = 0;
			}
		}

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sql.close();
	}


	  /**
     * Updates day wheel. Sets max days according to selected month and year
     */
    void updateDays(WheelView year, WheelView month, WheelView day) {
        Calendar calendar = Calendar.getInstance();
        int maxDays1 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.d("calendar.dayofMonth",""+Calendar.DAY_OF_MONTH);
        Log.d("calendar.day",""+calendar.get(Calendar.DAY_OF_MONTH));
        Log.d("calendar.month",""+calendar.get(Calendar.MONTH));
        Log.d("calendar.year",""+calendar.get(Calendar.YEAR));
        Log.d("calendar.maxDays1",""+maxDays1);
        
        calendar.set(Calendar.YEAR, year.getCurrentItem()+1900);
        Log.d("calendar.monthCurrent",""+month.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());
        
        Log.d("calendar.year",""+calendar.get(Calendar.YEAR));
        Log.d("calendar.month",""+calendar.get(Calendar.MONTH));
        
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.d("maxDays",""+maxDays+"----"+Calendar.DAY_OF_MONTH+"----"+Calendar.MONTH);
        day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }
    
    void updateLunarTime(){
		if(hours.getCurrentItem() == 23 || hours.getCurrentItem() == 0){
			txtLunarHourofbith.setText(getString(R.string.ty));
		}
		if(hours.getCurrentItem() == 1 || hours.getCurrentItem() == 2){
			txtLunarHourofbith.setText(getString(R.string.suu));
		}
		if(hours.getCurrentItem() == 3 || hours.getCurrentItem() == 4){
			txtLunarHourofbith.setText(getString(R.string.dan));
		}
		if(hours.getCurrentItem() == 5 || hours.getCurrentItem() == 6){
			txtLunarHourofbith.setText(getString(R.string.mao));
		}
		if(hours.getCurrentItem() == 7 || hours.getCurrentItem() == 8){
			txtLunarHourofbith.setText(getString(R.string.thin));
		}
		if(hours.getCurrentItem() == 9 || hours.getCurrentItem() == 10){
			txtLunarHourofbith.setText(getString(R.string.tyj));
		}
		if(hours.getCurrentItem() == 11 || hours.getCurrentItem() == 12){
			txtLunarHourofbith.setText(getString(R.string.ngo));
		}
		if(hours.getCurrentItem() == 13 || hours.getCurrentItem() == 14){
			txtLunarHourofbith.setText(getString(R.string.mui));
		}
		if(hours.getCurrentItem() == 15 || hours.getCurrentItem() == 16){
			txtLunarHourofbith.setText(getString(R.string.than));
		}
		if(hours.getCurrentItem() == 17 || hours.getCurrentItem() == 18){
			txtLunarHourofbith.setText(getString(R.string.dau));
		}
		if(hours.getCurrentItem() == 19 || hours.getCurrentItem() == 20){
			txtLunarHourofbith.setText(getString(R.string.tuat));
		}
		if(hours.getCurrentItem() == 21 || hours.getCurrentItem() == 22){
			txtLunarHourofbith.setText(getString(R.string.hoi));
		}
    }
    
    /**
     * Adapter for numeric wheels. Highlights the current value.
     */
    private class DateNumericAdapter extends NumericWheelAdapter {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
            super(context, minValue, maxValue);
            this.currentValue = current;
            setTextSize(32);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
            	view.setTextColor(Color.parseColor("#7f0000"));
            }
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
 

}