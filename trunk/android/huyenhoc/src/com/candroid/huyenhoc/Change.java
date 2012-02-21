package com.candroid.huyenhoc;

import java.util.Calendar;

import com.candroid.objects.Global;
import com.candroid.objects.utils;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
	String months[] ;
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
                updateDays(month.getCurrentItem(), day);
            }
        };
        
        OnWheelChangedListener listener2 = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if(!isSolar)updateLunarMonths(year.getCurrentItem()+1900);
            }
        };
		
        months = new String[] {"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11", "12"};
        
        // month
        curMonth = calendar.get(Calendar.MONTH);
        month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);
    
        // year
        curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new DateNumericAdapter(this, 1900, 2099, 0));
        year.setCurrentItem(curYear-1900);
        year.addChangingListener(listener2);
        
        //day
        updateDays(month.getCurrentItem(), day);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        
        txtLunar.setOnClickListener(this);
        txtSolar.setOnClickListener(this);
        btnChange.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if(v.getId() == txtSolar.getId()){
			if(!isSolar){
				calendar = Calendar.getInstance();
		        months = new String[] {"1", "2", "3", "4", "5",
		                "6", "7", "8", "9", "10", "11", "12"};
		        month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
				txtSolar.setTextColor(Color.parseColor("#7f0000"));
				txtLunar.setTextColor(Color.parseColor("#cccccc"));
				day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
				month.setCurrentItem(curMonth);
				year.setCurrentItem(curYear-1900);
				isSolar = true;
			}
		}
		
		if(v.getId() == txtLunar.getId()){
			if(isSolar){
				updateLunarMonths(curYear);
	    		month.setViewAdapter(new DateArrayAdapter(this,months, 0));
				txtLunar.setTextColor(Color.parseColor("#7f0000"));
				txtSolar.setTextColor(Color.parseColor("#cccccc"));
				int[] Lunar = utils.convertSolar2Lunar(calendar.get(Calendar.DAY_OF_MONTH), (curMonth+1), (curYear), 7);
				updateDays(curMonth, day);
				day.setCurrentItem(Lunar[0]-1);
				month.setCurrentItem(Lunar[1]-1);
				year.setCurrentItem(Lunar[2]-1900);
				isSolar = false;
			}
		}
		
		if(v.getId() == btnChange.getId()){
			if(isSolar){
				int[] Lunar = utils.convertSolar2Lunar((day.getCurrentItem()+1), (month.getCurrentItem()+1), (year.getCurrentItem()+1900), Global.TimeZone);
				txtDay.setText(""+Lunar[0]);
				if(Lunar[3]!=0){
					txtMonth.setText(Lunar[1]+"+");
				}else{
					txtMonth.setText(""+Lunar[1]);
				}
				txtYear.setText(""+Lunar[2]);				
			}else{
	        	String monthL= months[month.getCurrentItem()];
	        	int temmonth;
	        	int[] Solar;
	        	if(monthL.contains("+")){
	        		temmonth = Integer.valueOf(monthL.substring(0, monthL.length()-1));
	        		Solar = utils.convertLunar2Solar((day.getCurrentItem()+1),temmonth, (year.getCurrentItem()+1900),1, Global.TimeZone);
	        	}else{
	        		temmonth= Integer.valueOf(monthL);
	        		Solar= utils.convertLunar2Solar((day.getCurrentItem()+1),temmonth, (year.getCurrentItem()+1900),0, Global.TimeZone);
	        	}
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
	
//    void updateDays(WheelView year, WheelView month, WheelView day) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
//        calendar.set(Calendar.MONTH, month.getCurrentItem());
//        
//        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
//        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
//        day.setCurrentItem(curDay - 1, true);
//    }
    
    public void updateDays(int month,WheelView day){
    	calendar.set(Calendar.MONTH, month);
    	int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, 0));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }
    
    public void updateLunarMonths(int year){
		//update lunar months
		int leapmonth=utils.getLeapMonth(year, Global.TimeZone);
    	int count;
    	int next=0;
    	if(leapmonth==-1)
    		count=12;
    	else
    		count=13;
		months= new String[count];
		for(int i=0;i<=count-1;i++){
			if(i!=leapmonth-1)
			{
				months[i]=String.valueOf(next+1);
				next++;    				
			}
			else
			{
				months[i]=String.valueOf(next+1);
				i++;
				months[i]=String.valueOf(next+1)+"+";
				next++;
				
			}
		}
		month.setViewAdapter(new DateArrayAdapter(this, months, 0));
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
    
    private class DateArrayAdapter extends ArrayWheelAdapter<String> {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateArrayAdapter(Context context, String[] items, int current) {
            super(context, items);
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
