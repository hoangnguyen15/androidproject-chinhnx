package com.icsoft.ic_lichvannien;

import java.util.Calendar;
import java.util.Date;
import com.icsoft.calendar.VNMDate;
import com.icsoft.calendar.VietCalendar;
import com.icsoft.calendar.VietCalendar.Holiday;

import android.app.Activity;
import android.os.Bundle;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dayview);
		this.monthText = (TextView)findViewById(R.id.monthText);
		this.dayOfMonthText = (TextView)findViewById(R.id.dayOfMonthText);
		this.dayOfWeekText = (TextView)findViewById(R.id.dayOfWeekText);
		this.noteText = (TextView)findViewById(R.id.noteText);
		
		this.vnmHourText = (TextView) findViewById(R.id.vnmHourText);
		this.vnmHourInText = (TextView) findViewById(R.id.vnmHourInText);
		this.vnmDayOfMonthText = (TextView) findViewById(R.id.vnmDayOfMonthText);
		this.vnmDayOfMonthInText = (TextView) findViewById(R.id.vnmDayOfMonthInText);
		this.vnmMonthText = (TextView) findViewById(R.id.vnmMonthText);
		this.vnmMonthInText = (TextView) findViewById(R.id.vnmMonthInText);
		this.vnmYearText = (TextView) findViewById(R.id.vnmYearText);
		this.vnmYearInText = (TextView) findViewById(R.id.vnmYearInText);
		this.dayOfWeekColor = getResources().getColor(R.color.dayOfWeekColor);
		this.weekendColor = getResources().getColor(R.color.weekendColor);
		this.holidayColor = getResources().getColor(R.color.holidayColor);
		this.eventColor = getResources().getColor(R.color.eventColor);
		
		setDate(new Date());
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
			this.dayOfMonthText.setTextColor(this.weekendColor);
			this.dayOfWeekText.setTextColor(this.weekendColor);
			this.noteText.setTextColor(this.weekendColor);
			this.monthText.setTextColor(this.weekendColor);
		} else if (holiday != null && holiday.isOffDay()) {
			this.dayOfMonthText.setTextColor(this.holidayColor);
			this.dayOfWeekText.setTextColor(this.holidayColor);
			this.noteText.setTextColor(this.holidayColor);
			this.monthText.setTextColor(this.holidayColor);
			if (!holiday.isSolar()) {
				this.vnmDayOfMonthText.setTextColor(this.holidayColor);
				this.vnmMonthText.setTextColor(this.holidayColor);
				this.vnmYearText.setTextColor(this.holidayColor);
			}
		} else {
			this.dayOfMonthText.setTextColor(this.dayOfWeekColor);			
			this.dayOfWeekText.setTextColor(this.dayOfWeekColor);
			this.noteText.setTextColor(this.dayOfWeekColor);
			this.monthText.setTextColor(this.dayOfWeekColor);
		}
		this.monthText.setText("Tháng " + month + " năm " + year);
		this.dayOfMonthText.setText(dayOfMonth + "");
		this.dayOfWeekText.setText(VietCalendar.getDayOfWeekText(dayOfWeek));
		if (holiday != null) {
			this.noteText.setText(holiday.getDescription());
		} else {
			this.noteText.setText(famousSaying);
		}
		
		this.vnmHourText.setText(hour + ":" + minute);
		this.vnmDayOfMonthText.setText(vnmDate.getDayOfMonth() + "");
		this.vnmMonthText.setText(vnmDate.getMonth() + "");
		this.vnmYearText.setText(vnmDate.getYear() + "");
		
		String[] vnmCalendarTexts = VietCalendar.getCanChiInfo(vnmDate.getDayOfMonth(), vnmDate.getMonth(), vnmDate.getYear(), dayOfMonth, month, year);
		
		this.vnmHourInText.setText(vnmCalendarTexts[VietCalendar.HOUR]);
		this.vnmDayOfMonthInText.setText(vnmCalendarTexts[VietCalendar.DAY]);
		this.vnmMonthInText.setText(vnmCalendarTexts[VietCalendar.MONTH]);
		this.vnmYearInText.setText(vnmCalendarTexts[VietCalendar.YEAR]);	
		
//		if (eventSumarize != null && eventSumarize.length() > 0) {
//			this.noteText.setTextColor(this.eventColor);
//			this.noteText.setText(eventSumarize);
//		}
		
		this.dayOfMonthText.setShadowLayer(1.2f, 1.0f, 1.0f, getResources().getColor(R.color.shadowColor));		
//		this.invalidate();
	}
}
