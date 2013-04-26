package com.icsoft.ic_lichvannien;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import com.icsoft.calendar.VNMDate;
import com.icsoft.calendar.VietCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

public class ChangeDate extends  Activity implements OnDateChangedListener{
	DatePicker dpSolar,dpLunar;
	TextView dayOfWeekText,vnmDayOfMonthInText,vnmMonthInText,vnmYearInText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changedate);
		dpSolar = (DatePicker)findViewById(R.id.dpSolar);
		dpLunar = (DatePicker)findViewById(R.id.dpLunar);
		dayOfWeekText = (TextView) findViewById(R.id.dayOfWeekText);
		vnmDayOfMonthInText = (TextView) findViewById(R.id.vnmDayOfMonthInText);
		vnmMonthInText = (TextView) findViewById(R.id.vnmMonthInText);
		vnmYearInText = (TextView) findViewById(R.id.vnmYearInText);
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 11) {
        	try {
        		Method m = dpSolar.getClass().getMethod("setCalendarViewShown", boolean.class);
        		m.invoke(dpSolar, false);
        		m = dpLunar.getClass().getMethod("setCalendarViewShown", boolean.class);
        		m.invoke(dpLunar, false);
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        	}
        }
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		VNMDate vnmDate = VietCalendar.convertSolar2LunarInVietnamese(date);

		
        dpSolar.init(year,month,dayOfMonth, this);
        dpLunar.init(vnmDate.getYear(),vnmDate.getMonth()-1,vnmDate.getDayOfMonth(), this);
        String[] vnmCalendarTexts = VietCalendar.getCanChiInfo(vnmDate.getDayOfMonth(), vnmDate.getMonth(), vnmDate.getYear(), dayOfMonth, month+1, year);
		
		dayOfWeekText.setText(VietCalendar.getDayOfWeekText(dayOfWeek));
		vnmDayOfMonthInText.setText(vnmCalendarTexts[VietCalendar.DAY]);
		vnmMonthInText.setText(vnmCalendarTexts[VietCalendar.MONTH]);
		vnmYearInText.setText(vnmCalendarTexts[VietCalendar.YEAR]);	
        
        
	}
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		if(view.getId() == dpSolar.getId()){
			
		}
		
	}
	
	
}
