package com.icsoft.ic_lichvannien;

import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import com.icsoft.calendar.VNMDate;
import com.icsoft.calendar.VietCalendar;
import com.icsoft.objects.Global;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
public class ChangeDate extends  Activity{
	WheelView monthSolar,yearSolar,daySolar,monthLunar,yearLunar,dayLunar;
	TextView dayOfWeekText,vnmDayOfMonthInText,vnmMonthInText,vnmYearInText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changedate);
		
		dayOfWeekText = (TextView) findViewById(R.id.dayOfWeekText);
		vnmDayOfMonthInText = (TextView) findViewById(R.id.vnmDayOfMonthInText);
		vnmMonthInText = (TextView) findViewById(R.id.vnmMonthInText);
		vnmYearInText = (TextView) findViewById(R.id.vnmYearInText);
		
        monthSolar = (WheelView) findViewById(R.id.monthSolar);
        yearSolar = (WheelView) findViewById(R.id.yearSolar);
        daySolar = (WheelView) findViewById(R.id.daySolar);
        
        monthLunar = (WheelView) findViewById(R.id.monthLunar);
        yearLunar = (WheelView) findViewById(R.id.yearLunar);
        dayLunar = (WheelView) findViewById(R.id.dayLunar);
        
        Calendar calendar = Calendar.getInstance();
        int curDaySolar = calendar.get(Calendar.DAY_OF_MONTH);
        int curMonthSolar = calendar.get(Calendar.MONTH);
        int curYearSolar = calendar.get(Calendar.YEAR);

        
        OnWheelChangedListener listenerSolar = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(yearSolar, monthSolar, daySolar);
            }
        };
        
        OnWheelChangedListener listenerLunar = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
            	updateDaysnMonthsLunar(yearLunar, monthLunar, dayLunar);
            }
        };

        // monthSolar
        String months[] = new String[] {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5",
                "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
        monthSolar.setViewAdapter(new DateArrayAdapter(this, months, curMonthSolar));
        monthSolar.setCurrentItem(curMonthSolar);
        monthSolar.addChangingListener(listenerSolar);
    
        // yearSolar
        yearSolar.setViewAdapter(new DateNumericAdapter(this, 1900, 2030, curYearSolar-1900));
        yearSolar.setCurrentItem(curYearSolar-1900);
        yearSolar.addChangingListener(listenerSolar);
        
        //daySolar
        updateDays(yearSolar, monthSolar, daySolar);
        daySolar.setCurrentItem(curDaySolar-1);
        
        //Change Solar to Lunar
		int lunar[] = VietCalendar.convertSolar2Lunar(curDaySolar,curMonthSolar+1, curYearSolar, Global.timeZone);

        
        // monthLunar
        int curMonthLunar = lunar[1];
        String monthsLunar[] = new String[] {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5",
                "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
        monthLunar.setViewAdapter(new DateArrayAdapter(this, monthsLunar, curMonthLunar-1));
        monthLunar.setCurrentItem(curMonthLunar-1);
        monthLunar.addChangingListener(listenerLunar);
    
        // yearLunar
        int curYearLunar = lunar[2];
        yearLunar.setViewAdapter(new DateNumericAdapter(this, 1900, 2030,curYearLunar-1900));
        yearLunar.setCurrentItem(curYearLunar-1900);
        yearLunar.addChangingListener(listenerLunar);
        
        //dayLunar
        int curDayLunar = lunar[0];
        dayLunar.setViewAdapter(new DateNumericAdapter(this,1,30,curDayLunar-1));
        dayLunar.setCurrentItem(curDayLunar-1);
        
                
	}
	
	public void setText(String[] vnmCalendarTexts,int dayOfWeek){
		dayOfWeekText.setText(VietCalendar.getDayOfWeekText(dayOfWeek));
		vnmDayOfMonthInText.setText(vnmCalendarTexts[VietCalendar.DAY]);
		vnmMonthInText.setText(vnmCalendarTexts[VietCalendar.MONTH]);
		vnmYearInText.setText(vnmCalendarTexts[VietCalendar.YEAR]);
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
    
    void updateDaysnMonthsLunar(WheelView year, WheelView month, WheelView day) {
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
            setTextSize(16);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
    
    /**
     * Adapter for string based wheel. Highlights the current value.
     */
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
            setTextSize(16);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
	
}
