package com.icsoft.ic_lichvannien;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icsoft.calendar.VNMDate;
import com.icsoft.calendar.VietCalendar;
import com.icsoft.calendar.VietCalendar.Holiday;

public class Dayview extends Activity implements OnTouchListener{
	TextView monthText,dayOfMonthText,dayOfWeekText,noteText;
	TextView vnmHourText,vnmHourInText,vnmDayOfMonthText,vnmDayOfMonthInText,vnmMonthInText,vnmYearText,vnmYearInText;
//	TextView vnmMonthText;
	int dayOfWeekColor,weekendColor,holidayColor,eventColor;
	
	private static final int MENU_CHANGE_DATE = 2;
	private static final int MENU_MONTH_VIEW = 1;
	private static int MENU_SETTINGS = 3;
	
	ArrayList<Float> xArray, yArray;
	boolean flagActionMove = false;
	int padding = 70;

	LinearLayout llday;
	Date curDate;
	
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
//		vnmMonthText = (TextView) findViewById(R.id.vnmMonthText);
		vnmMonthInText = (TextView) findViewById(R.id.vnmMonthInText);
		vnmYearText = (TextView) findViewById(R.id.vnmYearText);
		vnmYearInText = (TextView) findViewById(R.id.vnmYearInText);
		dayOfWeekColor = getResources().getColor(R.color.dayOfWeekColor);
		weekendColor = getResources().getColor(R.color.weekendColor);
		holidayColor = getResources().getColor(R.color.holidayColor);
		eventColor = getResources().getColor(R.color.eventColor);
		
		llday = (LinearLayout)findViewById(R.id.llday);
		
		setDate(new Date());
		
		xArray = new ArrayList<Float>();
		yArray = new ArrayList<Float>();
		
		llday.setOnTouchListener(this);
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
		int second = calendar.get(Calendar.SECOND);
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
//				vnmMonthText.setTextColor(holidayColor);
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
		
		vnmHourText.setText(hour + ":" + minute+ ":" + second);
		vnmDayOfMonthText.setText(vnmDate.getDayOfMonth() + "");
//		vnmMonthText.setText(vnmDate.getMonth() + "");
		vnmYearText.setText(vnmDate.getMonth() +"/"+vnmDate.getYear());
		
		String[] vnmCalendarTexts = VietCalendar.getCanChiInfo(vnmDate.getDayOfMonth(), vnmDate.getMonth(), vnmDate.getYear(), dayOfMonth, month, year);
		
		vnmHourInText.setText("Giờ "+vnmCalendarTexts[VietCalendar.HOUR]);
		vnmDayOfMonthInText.setText("Ngày "+vnmCalendarTexts[VietCalendar.DAY]);
		vnmMonthInText.setText("Tháng "+vnmCalendarTexts[VietCalendar.MONTH]);
		vnmYearInText.setText("Năm "+vnmCalendarTexts[VietCalendar.YEAR]);	
		
//		if (eventSumarize != null && eventSumarize.length() > 0) {
//			noteText.setTextColor(eventColor);
//			noteText.setText(eventSumarize);
//		}
		
		dayOfMonthText.setShadowLayer(1.2f, 1.0f, 1.0f, getResources().getColor(R.color.shadowColor));		
//		invalidate();
		curDate = date;
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == llday.getId()){
			float xPos, yPos;
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				System.out.println("-----------Action move");
				xPos = event.getRawX();
				yPos = event.getRawY();
				
				xArray.add(xPos);
				yArray.add(yPos);
			}
			else if (event.getAction() == MotionEvent.ACTION_UP) {
				int move = 0;
				if (xArray.size() > 0) {
					float width = xArray.get(xArray.size() - 1) - xArray.get(0);
					float height = yArray.get(yArray.size() - 1) - yArray.get(0);
					
					float absWidth = Math.abs(width);
					float absHeight = Math.abs(height);
					if (absWidth > padding || absHeight > padding) {
						if (absWidth >= absHeight) {
							if (width > 0) {
								move = 2;
							}
							else {
								move = 1;
							}
						}
						else {
							if (height > 0) {
								move = 3;
							}
							else {
								move = 4;
							}
						}
					}
				}

				if (move > 0 && move <= 2) {
					ProcessAction(move);
					flagActionMove = true;
				}
				else if (move > 2 && move <= 4) {
					ProcessAction(move);
					flagActionMove = true;
				}
				else {
					flagActionMove = false;
				}
				xArray.clear();
				yArray.clear();
			}						
		}
		return true;
	}
	
    void ProcessAction(int move) {
    	if (move == 1) { //Phải sang trái
    		Log.d("action","Next()");
    		Next();
    	}
    	else  if (move == 2){ //Trái sang phải
    		Log.d("action","Prev()");
    	}
    	else  if (move == 3){ //Trên xuống dưới
    		Log.d("action","nextYear()");
    	}
    	else  { //Dưới lên trên
    		Log.d("action","prevYear()");
    	}
    }
    
    void Next(){
    	setDate(addDays(new Date(), 1));
    }
    
    private Date addDays(Date date, int days) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, days);
    	return cal.getTime();
    }
	
}
