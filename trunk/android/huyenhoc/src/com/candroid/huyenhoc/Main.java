package com.candroid.huyenhoc;

import java.util.Calendar;


import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import com.candroid.objects.Global;
import com.candroid.objects.Infor;
import com.candroid.objects.Sqlite;

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
	int sex = 2;
	Infor infor;
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
		
		//setSex
		if(infor.getSex() == 1){
			male = true;
			txtMale.setTextColor(Color.parseColor("#7f0000"));
			txtFemale.setTextColor(Color.parseColor("#cccccc"));
		}else{
			male = false;
			txtFemale.setTextColor(Color.parseColor("#7f0000"));
			txtMale.setTextColor(Color.parseColor("#cccccc"));

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
        }else
        	month.setCurrentItem(infor.getMonthofbith());
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
        Log.d("dayOfbirth",""+infor.getDayofbith());
        Log.d("dayOfbirth",""+infor.getMonthofbith());
        Log.d("dayOfbirth",""+infor.getYearofbith());
        Log.d("sex",""+infor.getSex());
        Log.d("dayOfbirth",""+infor.getHourofbith());
        Log.d("dayOfbirth",""+infor.getMinuteofbith());

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
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());
        
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }
    
    void updateLunarTime(){
		if(hours.getCurrentItem() == 23 || hours.getCurrentItem() == 0){
			txtLunarHourofbith.setText("Tý");
		}
		if(hours.getCurrentItem() == 1 || hours.getCurrentItem() == 2){
			txtLunarHourofbith.setText("Sửu");
		}
		if(hours.getCurrentItem() == 3 || hours.getCurrentItem() == 4){
			txtLunarHourofbith.setText("Dần");
		}
		if(hours.getCurrentItem() == 5 || hours.getCurrentItem() == 6){
			txtLunarHourofbith.setText("Mão");
		}
		if(hours.getCurrentItem() == 7 || hours.getCurrentItem() == 8){
			txtLunarHourofbith.setText("Thìn");
		}
		if(hours.getCurrentItem() == 9 || hours.getCurrentItem() == 10){
			txtLunarHourofbith.setText("Tỵ");
		}
		if(hours.getCurrentItem() == 11 || hours.getCurrentItem() == 12){
			txtLunarHourofbith.setText("Ngọ");
		}
		if(hours.getCurrentItem() == 13 || hours.getCurrentItem() == 14){
			txtLunarHourofbith.setText("Mùi");
		}
		if(hours.getCurrentItem() == 15 || hours.getCurrentItem() == 16){
			txtLunarHourofbith.setText("Thân");
		}
		if(hours.getCurrentItem() == 17 || hours.getCurrentItem() == 18){
			txtLunarHourofbith.setText("Dậu");
		}
		if(hours.getCurrentItem() == 19 || hours.getCurrentItem() == 20){
			txtLunarHourofbith.setText("Tuất");
		}
		if(hours.getCurrentItem() == 21 || hours.getCurrentItem() == 22){
			txtLunarHourofbith.setText("Hợi");
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