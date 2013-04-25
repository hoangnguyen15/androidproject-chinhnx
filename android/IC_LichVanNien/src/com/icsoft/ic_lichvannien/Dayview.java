package com.icsoft.ic_lichvannien;

import java.util.Calendar;
import java.util.Date;
import com.icsoft.calendar.VNMDate;
import com.icsoft.calendar.VietCalendar;
import com.icsoft.calendar.VietCalendar.Holiday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Dayview extends Activity{
	TextView monthText;
	TextView dayOfMonthText;
	TextView dayOfWeekText;
	TextView noteText;
	
	TextView vnmHourText;
	TextView vnmHourInText;
	TextView vnmDayOfMonthText;
	TextView vnmDayOfMonthInText;
	TextView vnmMonthText;
	TextView vnmMonthInText;
	TextView vnmYearText;
	TextView vnmYearInText;
			
	int dayOfWeekColor;
	int weekendColor;
	int holidayColor;
	int eventColor;
	
	private static final int MENU_CHANGE_DATE = 2;
	private static final int MENU_MONTH_VIEW = 1;
	private static int MENU_SETTINGS = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dayview);
		monthText = (TextView)findViewById(R.id.monthText);
		dayOfMonthText = (TextView)findViewById(R.id.dayOfMonthText);
		dayOfWeekText = (TextView)findViewById(R.id.dayOfWeekText);
		noteText = (TextView)findViewById(R.id.noteText);
		
		vnmHourText = (TextView) findViewById(R.id.vnmHourText);
		vnmHourInText = (TextView) findViewById(R.id.vnmHourInText);
		vnmDayOfMonthText = (TextView) findViewById(R.id.vnmDayOfMonthText);
		vnmDayOfMonthInText = (TextView) findViewById(R.id.vnmDayOfMonthInText);
		vnmMonthText = (TextView) findViewById(R.id.vnmMonthText);
		vnmMonthInText = (TextView) findViewById(R.id.vnmMonthInText);
		vnmYearText = (TextView) findViewById(R.id.vnmYearText);
		vnmYearInText = (TextView) findViewById(R.id.vnmYearInText);
		dayOfWeekColor = getResources().getColor(R.color.dayOfWeekColor);
		weekendColor = getResources().getColor(R.color.weekendColor);
		holidayColor = getResources().getColor(R.color.holidayColor);
		eventColor = getResources().getColor(R.color.eventColor);
		
		setDate(new Date());
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_MONTH_VIEW, 0, "Xem tháng");
    	menu.add(0, MENU_CHANGE_DATE, 0, "Đổi ngày");
    	menu.add(0, MENU_SETTINGS, 0, "Tùy chọn");
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == MENU_MONTH_VIEW) {
    		Intent intent = new Intent();
    		intent.setClass(Dayview.this,MonthView.class);
    		startActivity(intent);
    	} else if (item.getItemId() == MENU_CHANGE_DATE) {
    		Intent intent = new Intent();
    		intent.setClass(Dayview.this,ChangeDate.class);
    		startActivity(intent);    		
    	} else if (item.getItemId() == MENU_SETTINGS) {
    		Intent intent = new Intent();
    		intent.setClass(Dayview.this,Settings.class);
    		startActivity(intent);
    	}
    	return true;
    }    	
	
	public void setDate(Date date) {
		String famousSaying = "danh ngon cuoc song";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		
		VNMDate vnmDate = VietCalendar.convertSolar2LunarInVietnamese(date);
		Holiday holiday = VietCalendar.getHoliday(date);
		if (dayOfWeek == 1) {
			dayOfMonthText.setTextColor(weekendColor);
			dayOfWeekText.setTextColor(weekendColor);
			noteText.setTextColor(weekendColor);
			monthText.setTextColor(weekendColor);
		} else if (holiday != null && holiday.isOffDay()) {
			dayOfMonthText.setTextColor(holidayColor);
			dayOfWeekText.setTextColor(holidayColor);
			noteText.setTextColor(holidayColor);
			monthText.setTextColor(holidayColor);
			if (!holiday.isSolar()) {
				vnmDayOfMonthText.setTextColor(holidayColor);
				vnmMonthText.setTextColor(holidayColor);
				vnmYearText.setTextColor(holidayColor);
			}
		} else {
			dayOfMonthText.setTextColor(dayOfWeekColor);			
			dayOfWeekText.setTextColor(dayOfWeekColor);
			noteText.setTextColor(dayOfWeekColor);
			monthText.setTextColor(dayOfWeekColor);
		}
		monthText.setText("Tháng " + month + " năm " + year);
		dayOfMonthText.setText(dayOfMonth + "");
		dayOfWeekText.setText(VietCalendar.getDayOfWeekText(dayOfWeek));
		if (holiday != null) {
			noteText.setText(holiday.getDescription());
		} else {
			noteText.setText(famousSaying);
		}
		
		vnmHourText.setText(hour + ":" + minute);
		vnmDayOfMonthText.setText(vnmDate.getDayOfMonth() + "");
		vnmMonthText.setText(vnmDate.getMonth() + "");
		vnmYearText.setText(vnmDate.getYear() + "");
		
		String[] vnmCalendarTexts = VietCalendar.getCanChiInfo(vnmDate.getDayOfMonth(), vnmDate.getMonth(), vnmDate.getYear(), dayOfMonth, month, year);
		
		vnmHourInText.setText(vnmCalendarTexts[VietCalendar.HOUR]);
		vnmDayOfMonthInText.setText(vnmCalendarTexts[VietCalendar.DAY]);
		vnmMonthInText.setText(vnmCalendarTexts[VietCalendar.MONTH]);
		vnmYearInText.setText(vnmCalendarTexts[VietCalendar.YEAR]);	
		
//		if (eventSumarize != null && eventSumarize.length() > 0) {
//			noteText.setTextColor(eventColor);
//			noteText.setText(eventSumarize);
//		}
		
		dayOfMonthText.setShadowLayer(1.2f, 1.0f, 1.0f, getResources().getColor(R.color.shadowColor));		
//		invalidate();
	}
	
	
}
