package com.candroid.huyenhoc;

import java.util.Calendar;

import com.candroid.objects.utils;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class Change extends Activity implements OnClickListener{

	Button btnChange;
	TextView txtDay,txtMonth,txtYear;
	WheelView month,year,day;
	TextView txtLunar,txtSolar;
	boolean isSolar = true;
	int curMonth,curYear ;
	Calendar calendar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change);
		calendar = Calendar.getInstance();
        
        btnChange = (Button)findViewById(R.id.btnChange);
        txtLunar = (TextView)findViewById(R.id.txtLunar);
        txtSolar = (TextView)findViewById(R.id.txtSolar);
		day = (WheelView) findViewById(R.id.txtDaytochange);
        month = (WheelView) findViewById(R.id.txtMonthtochange);
        year = (WheelView) findViewById(R.id.txtYeartochange);
        txtDay = (TextView)findViewById(R.id.txtChangedDay);
        txtMonth = (TextView)findViewById(R.id.txtChangedMonth);
        txtYear = (TextView)findViewById(R.id.txtChangedYear);
        
        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day);
            }
        };
		
        // month
        curMonth = calendar.get(Calendar.MONTH);
        month.setViewAdapter(new DateNumericAdapter(this, 1, 12,0));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);
    
        // year
        curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new DateNumericAdapter(this, 1900, 2099, 0));
        year.setCurrentItem(curYear-1900);
        year.addChangingListener(listener);
        
        //day
        updateDays(year, month, day);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        
        txtLunar.setOnClickListener(this);
        txtSolar.setOnClickListener(this);
        btnChange.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if(v.getId() == txtSolar.getId()){
			if(!isSolar){
				txtSolar.setTextColor(Color.parseColor("#7f0000"));
				txtLunar.setTextColor(Color.parseColor("#cccccc"));
				day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
				month.setCurrentItem(curMonth);
				year.setCurrentItem(curYear);
				isSolar = true;
			}
		}
		
		if(v.getId() == txtLunar.getId()){
			if(isSolar){
				txtLunar.setTextColor(Color.parseColor("#7f0000"));
				txtSolar.setTextColor(Color.parseColor("#cccccc"));
				int[] Lunar = utils.convertSolar2Lunar(calendar.get(Calendar.DAY_OF_MONTH), curMonth+1, (curYear), 7);
				day.setCurrentItem(Lunar[0]-1);
				month.setCurrentItem(Lunar[1]-1);
				year.setCurrentItem(Lunar[2]-1900);
				isSolar = false;
			}
		}
		
		if(v.getId() == btnChange.getId()){
			if(isSolar){
				int[] Lunar = utils.convertSolar2Lunar((day.getCurrentItem()+1), (month.getCurrentItem()+1), (year.getCurrentItem()+1900), 7);
				txtDay.setText(""+Lunar[0]);
				txtMonth.setText(""+Lunar[1]);
				txtYear.setText(""+Lunar[2]);
			}else{
				
//				utils.convertLunar2Solar(lunarDay, lunarMonth, lunarYear, lunarLeap, timeZone);
				Log.d("z",""+(day.getCurrentItem()+1));
				Log.d("z",""+(month.getCurrentItem()+1));
				Log.d("z",""+(year.getCurrentItem()+1900));
				
				int[] Solar = utils.convertLunar2Solar((day.getCurrentItem()+1), (month.getCurrentItem()+1), (year.getCurrentItem()+1900),1, 7);
				txtDay.setText(""+Solar[0]);
				txtMonth.setText(""+Solar[1]);
				txtYear.setText(""+Solar[2]);
			}
			
		}
	}
	
	public static boolean checkIntNumber(String sNumber) {    	
		try 
		{
			Integer.parseInt(sNumber);
			return true;
		} catch (Exception e) 
		{
			return false;
		}
    }
	
    void updateDays(WheelView year, WheelView month, WheelView day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());
        
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }
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
