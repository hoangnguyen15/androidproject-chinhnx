package com.icsoft.ic_lichvannien;

import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import com.icsoft.calendar.VNMDate;
import com.icsoft.calendar.VietCalendar;

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
		
		Calendar calendar = Calendar.getInstance();

        final WheelView monthSolar = (WheelView) findViewById(R.id.monthSolar);
        final WheelView yearSolar = (WheelView) findViewById(R.id.yearSolar);
        final WheelView daySolar = (WheelView) findViewById(R.id.daySolar);
        
        final WheelView monthLunar = (WheelView) findViewById(R.id.monthLunar);
        final WheelView yearLunar = (WheelView) findViewById(R.id.yearLunar);
        final WheelView dayLunar = (WheelView) findViewById(R.id.dayLunar);
        
        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(yearSolar, monthSolar, daySolar);
            }
        };

        // monthSolar
        int curMonth = calendar.get(Calendar.MONTH);
        String months[] = new String[] {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};
        monthSolar.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
        monthSolar.setCurrentItem(curMonth);
        monthSolar.addChangingListener(listener);
    
        // yearSolar
        int curYear = calendar.get(Calendar.YEAR);
        yearSolar.setViewAdapter(new DateNumericAdapter(this, curYear, curYear + 10, 0));
        yearSolar.setCurrentItem(curYear);
        yearSolar.addChangingListener(listener);
        
        //daySolar
        updateDays(yearSolar, monthSolar, daySolar);
        daySolar.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        
        // monthLunar
        int curMonthLunar = calendar.get(Calendar.MONTH);
        String monthsLunar[] = new String[] {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};
        monthLunar.setViewAdapter(new DateArrayAdapter(this, monthsLunar, curMonth));
        monthLunar.setCurrentItem(curMonthLunar);
        monthLunar.addChangingListener(listener);
    
        // yearLunar
        int curYearLunar = calendar.get(Calendar.YEAR);
        yearLunar.setViewAdapter(new DateNumericAdapter(this, curYearLunar, curYearLunar + 10, 0));
        yearLunar.setCurrentItem(curYearLunar);
        yearLunar.addChangingListener(listener);
        
        //dayLunar
        updateDays(yearLunar, monthLunar, dayLunar);
        dayLunar.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
                
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
