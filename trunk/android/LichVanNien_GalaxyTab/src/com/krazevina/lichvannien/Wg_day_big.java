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
import android.view.View;
import android.widget.RemoteViews;


import com.krazevina.calendar.VietCalendar;
import com.krazevina.calendar.VietCalendar.Holiday;
import com.krazevina.objects.Global;
import com.krazevina.objects.LichXuatHanh;
import com.krazevina.objects.Utils;

public class Wg_day_big extends AppWidgetProvider {
	private static Date currentDate = null;
	private static int dayOfWeekColor = 0;
	private static int weekendColor = 0;
	private static int holidayColor = 0;
	RemoteViews remoteViews;
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1,60000);
		remoteViews = new RemoteViews(context.getPackageName(),R.layout.wg_day_big);
		Intent intent = new Intent(context, Main.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.wgdaybig_day, pendingIntent);

	}

	private class MyTime extends TimerTask {
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;
		String specialdays;
		String ngayamdacbiet;
		public MyTime(Context context, AppWidgetManager appWidgetManager) {
			this.appWidgetManager = appWidgetManager;
			remoteViews = new RemoteViews(context.getPackageName(),R.layout.wg_day_big);
			thisWidget = new ComponentName(context, Wg_day_big.class);
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
			Log.d("Wg_day_big",""+dayOfMonth);
			
			int[] lunars = VietCalendar.convertSolar2LunarInVietnam(currentDate);
			String[] vnmCalendarTexts = VietCalendar.getCanChiInfo(lunars[VietCalendar.DAY], lunars[VietCalendar.MONTH],lunars[VietCalendar.YEAR], dayOfMonth, month, year);
			
			specialdays= Utils.getDaySpecialYearlyEvent(dayOfMonth, month);
			ngayamdacbiet= Utils.getDayYearlyEvent(lunars[VietCalendar.DAY], lunars[VietCalendar.MONTH]);
			Holiday holiday = VietCalendar.getHoliday(currentDate);
			
			if(specialdays!=""){
	    		remoteViews.setViewVisibility(R.id.wgdaybig_specialday, View.VISIBLE);
	    	}else{
	    		remoteViews.setViewVisibility(R.id.wgdaybig_specialday, View.GONE);
	    	}
	    	
	    	if(ngayamdacbiet!=""){
	    		remoteViews.setViewVisibility(R.id.wgdaybig_specialday_lunar, View.VISIBLE);
	    	}else{
	    		remoteViews.setViewVisibility(R.id.wgdaybig_specialday_lunar, View.GONE);
	    	}

			int dayColor = dayOfWeekColor;
			if (dayOfWeek == 1) {
				dayColor = weekendColor;
			} else if (holiday != null) {
				dayColor = holidayColor;
			}
			
			
			String lichxuathanh=LichXuatHanh.getLichXuatHanh(lunars[VietCalendar.DAY],lunars[VietCalendar.MONTH]);
			remoteViews.setTextViewText(R.id.wgdaybig_title, "Tháng "+ month + "/" + year);
			remoteViews.setTextViewText(R.id.wgdaybig_date, VietCalendar.getDayOfWeekText(dayOfWeek));
			remoteViews.setTextViewText(R.id.wgdaybig_day, "" + dayOfMonth);
			remoteViews.setTextViewText(R.id.wgdaybig_xuathanh, lichxuathanh);
			
			remoteViews.setTextViewText(R.id.wgdaybig_lunarday_number, ""+ lunars[VietCalendar.DAY]);
			remoteViews.setTextViewText(R.id.wgdaybig_lunarmonth_number, ""+ lunars[VietCalendar.MONTH]);
//			remoteViews.setTextViewText(R.id.wgdaybig_lunaryear_number, ""+ lunars[VietCalendar.YEAR]);
			
			remoteViews.setTextViewText(R.id.wgdaybig_lunarday, ""+ vnmCalendarTexts[VietCalendar.DAY]);
			remoteViews.setTextViewText(R.id.wgdaybig_lunarmonth, ""+ vnmCalendarTexts[VietCalendar.MONTH]);
//			remoteViews.setTextViewText(R.id.wgdaybig_lunaryear, ""+ vnmCalendarTexts[VietCalendar.YEAR]);
			
			remoteViews.setTextViewText(R.id.wgdaybig_specialday, specialdays);
			remoteViews.setTextViewText(R.id.wgdaybig_specialday_lunar, ngayamdacbiet);
			
			remoteViews.setTextColor(R.id.wgdaybig_day, dayColor);
			remoteViews.setTextColor(R.id.wgdaybig_date, dayColor);
			remoteViews.setTextColor(R.id.wgdaybig_specialday, dayColor);
			remoteViews.setTextColor(R.id.wgdaybig_specialday_lunar, dayColor);
			
			
			appWidgetManager.updateAppWidget(thisWidget, remoteViews);
			
		}
	}
}
