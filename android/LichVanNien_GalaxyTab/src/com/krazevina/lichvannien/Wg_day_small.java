package com.krazevina.lichvannien;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;


import com.krazevina.calendar.VietCalendar;
import com.krazevina.calendar.VietCalendar.Holiday;

public class Wg_day_small extends AppWidgetProvider {
	private static Date currentDate = null;
	private static int dayOfWeekColor = 0;
	private static int weekendColor = 0;
	private static int holidayColor = 0;
	RemoteViews remoteViews;
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1,60000);
		remoteViews = new RemoteViews(context.getPackageName(),R.layout.wg_day_small);
		Intent intent = new Intent(context, Main.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.wgsmall_txt_day, pendingIntent);

	}

	private class MyTime extends TimerTask {
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;

		public MyTime(Context context, AppWidgetManager appWidgetManager) {
			this.appWidgetManager = appWidgetManager;
			remoteViews = new RemoteViews(context.getPackageName(),R.layout.wg_day_small);
			thisWidget = new ComponentName(context, Wg_day_small.class);
			if (currentDate == null) {        	
	        	dayOfWeekColor = context.getResources().getColor(R.color.dayOfWeekColor);
	    		weekendColor = context.getResources().getColor(R.color.weekendColor);
	    		holidayColor = context.getResources().getColor(R.color.holidayColor);
	        }
			
	        
		}

		@Override
		public void run() {
			currentDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);

			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);

			int[] lunars = VietCalendar.convertSolar2LunarInVietnam(currentDate);
			String[] vnmCalendarTexts = VietCalendar.getCanChiInfo(lunars[VietCalendar.DAY], lunars[VietCalendar.MONTH],lunars[VietCalendar.YEAR], dayOfMonth, month, year);
			Holiday holiday = VietCalendar.getHoliday(currentDate);
			int dayColor = dayOfWeekColor;
			if (dayOfWeek == 1) {
				dayColor = weekendColor;
			} else if (holiday != null) {
				dayColor = holidayColor;
			}
			
			Log.d("Wg_day_small",""+dayOfMonth);

			remoteViews.setTextViewText(R.id.wgsmall_txt_title, "Tháng "+ month + "/" + year);
			remoteViews.setTextViewText(R.id.wgsmall_txt_date, VietCalendar.getDayOfWeekText(dayOfWeek));
			remoteViews.setTextViewText(R.id.wgsmall_txt_day, "" + dayOfMonth);
			remoteViews.setTextViewText(R.id.wgsmall_txt_daylunar_number, ""+ lunars[VietCalendar.DAY]);
			remoteViews.setTextViewText(R.id.wgsmall_txt_monthlunar_number, ""+ lunars[VietCalendar.MONTH]);
			remoteViews.setTextViewText(R.id.wgsmall_txt_daylunar, ""+ vnmCalendarTexts[VietCalendar.DAY]);
			remoteViews.setTextViewText(R.id.wgsmall_txt_monthlunar, ""+ vnmCalendarTexts[VietCalendar.MONTH]);
			remoteViews.setTextColor(R.id.wgsmall_txt_day, dayColor);
			remoteViews.setTextColor(R.id.wgsmall_txt_date, dayColor);
			appWidgetManager.updateAppWidget(thisWidget, remoteViews);
			
		}
	}
}
