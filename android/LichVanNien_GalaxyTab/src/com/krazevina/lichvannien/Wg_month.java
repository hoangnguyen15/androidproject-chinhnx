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
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.krazevina.calendar.VietCalendar;
import com.krazevina.calendar.VietCalendar.Holiday;
import com.krazevina.objects.Processdate;
import com.krazevina.objects.Utils;

public class Wg_month extends AppWidgetProvider{
	private static Date currentDate = null;
	RemoteViews remoteViews;
	private String[] days_solar;
	private String[] days_lunar;
	private Processdate prodate;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyMonth(context, appWidgetManager), 1,60000);
		
		remoteViews = new RemoteViews(context.getPackageName(),R.layout.wg_month);
		Intent intent = new Intent(context, Main.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.wgmonth_layoutcontent, pendingIntent);

	}
	
	private class MyMonth extends TimerTask {
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;

		public MyMonth(Context context, AppWidgetManager appWidgetManager) {
			this.appWidgetManager = appWidgetManager;
			remoteViews = new RemoteViews(context.getPackageName(),R.layout.wg_month);
			thisWidget = new ComponentName(context, Wg_month.class);	        
		}

		@Override
		public void run() {
			currentDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			
			Log.d("month_wgmonth",""+dayOfMonth);
			remoteViews.setTextViewText(R.id.wgmonth_title, "Tháng "+ month + "/" + year);
			
			int x=0,y =0;
			Utils util = new Utils();
			//Lich Duong
			String[][] solarmonth= util.getSolarMonth(year, month);
			days_solar= new String[50];
			days_lunar = new String[50];
			prodate = new Processdate();
			
			
			int [] id_solar = {R.id.wgmonth_solar1,R.id.wgmonth_solar2,R.id.wgmonth_solar3,R.id.wgmonth_solar4,R.id.wgmonth_solar5,R.id.wgmonth_solar6,
					R.id.wgmonth_solar7,R.id.wgmonth_solar8,R.id.wgmonth_solar9,R.id.wgmonth_solar10,R.id.wgmonth_solar11,R.id.wgmonth_solar12,
					R.id.wgmonth_solar13,R.id.wgmonth_solar14,R.id.wgmonth_solar15,R.id.wgmonth_solar16,R.id.wgmonth_solar17,R.id.wgmonth_solar18,
					R.id.wgmonth_solar19,R.id.wgmonth_solar20,R.id.wgmonth_solar21,R.id.wgmonth_solar22,R.id.wgmonth_solar23,R.id.wgmonth_solar24,
					R.id.wgmonth_solar25,R.id.wgmonth_solar26,R.id.wgmonth_solar27,R.id.wgmonth_solar28,R.id.wgmonth_solar29,R.id.wgmonth_solar30,
					R.id.wgmonth_solar31,R.id.wgmonth_solar32,R.id.wgmonth_solar33,R.id.wgmonth_solar34,R.id.wgmonth_solar35,R.id.wgmonth_solar36,
					R.id.wgmonth_solar37,R.id.wgmonth_solar38,R.id.wgmonth_solar39,R.id.wgmonth_solar40,R.id.wgmonth_solar41,R.id.wgmonth_solar42};
			
			int [] id_lunar = {R.id.wgmonth_lunar1,R.id.wgmonth_lunar2,R.id.wgmonth_lunar3,R.id.wgmonth_lunar4,R.id.wgmonth_lunar5,R.id.wgmonth_lunar6,
					R.id.wgmonth_lunar7,R.id.wgmonth_lunar8,R.id.wgmonth_lunar9,R.id.wgmonth_lunar10,R.id.wgmonth_lunar11,R.id.wgmonth_lunar12,
					R.id.wgmonth_lunar13,R.id.wgmonth_lunar14,R.id.wgmonth_lunar15,R.id.wgmonth_lunar16,R.id.wgmonth_lunar17,R.id.wgmonth_lunar18,
					R.id.wgmonth_lunar19,R.id.wgmonth_lunar20,R.id.wgmonth_lunar21,R.id.wgmonth_lunar22,R.id.wgmonth_lunar23,R.id.wgmonth_lunar24,
					R.id.wgmonth_lunar25,R.id.wgmonth_lunar26,R.id.wgmonth_lunar27,R.id.wgmonth_lunar28,R.id.wgmonth_lunar29,R.id.wgmonth_lunar30,
					R.id.wgmonth_lunar31,R.id.wgmonth_lunar32,R.id.wgmonth_lunar33,R.id.wgmonth_lunar34,R.id.wgmonth_lunar35,R.id.wgmonth_lunar36,
					R.id.wgmonth_lunar37,R.id.wgmonth_lunar38,R.id.wgmonth_lunar39,R.id.wgmonth_lunar40,R.id.wgmonth_lunar41,R.id.wgmonth_lunar42};
			
			
			for(int i=0;i<Utils.NUMBER_ROW;i++)
				for(int j=0;j<Utils.NUMBER_COLUMN;j++)
				{
					days_solar[x]=solarmonth[i][j];
					x++;				
				}
			days_solar[43]=solarmonth[6][0];//index0
			days_solar[44]=solarmonth[6][1];//index1
			
			//Lich Am
			String[][] lunarmonth= util.getLunarMonth(year, month);
			for(int i=0;i<Utils.NUMBER_ROW;i++)
			{
				for(int j=0;j<Utils.NUMBER_COLUMN;j++)
				{				
					days_lunar[y]=lunarmonth[i][j];		
					y++;				
				}
			}
			
			//Set Solar
			for(int i = 0;i<id_solar.length;i++){
				if(i<Integer.valueOf(days_solar[43]) || i>=Integer.valueOf(days_solar[44])){
					remoteViews.setTextColor(id_solar[i], Color.parseColor("#444444"));
					remoteViews.setTextColor(id_lunar[i], Color.parseColor("#444444"));
				}
				if(prodate.HOLIDAYSOLAR(Integer.valueOf(days_solar[i]), month) && (!(i<Integer.valueOf(days_solar[43]) || i>=Integer.valueOf(days_solar[44])))){
					remoteViews.setTextColor(id_solar[i], Color.parseColor("#fc3838"));
					remoteViews.setTextColor(id_lunar[i], Color.parseColor("#fc3838"));
				}
				if(dayOfMonth == Integer.valueOf(days_solar[i]) && (!(i<Integer.valueOf(days_solar[43]) || i>=Integer.valueOf(days_solar[44])))){
					remoteViews.setTextColor(id_solar[i], Color.parseColor("#7CFC00"));
					remoteViews.setTextColor(id_lunar[i], Color.parseColor("#7CFC00"));
				}
				
				remoteViews.setTextViewText(id_solar[i], days_solar[i]);
			}
			
			//Set Lunar
			
			String[] lunarsplit= new String[2];
			for(int i = 0;i<id_lunar.length;i++){
				lunarsplit=days_lunar[i].split("/");
				int _lunarday= Integer.valueOf(lunarsplit[0]);
		        int _lunarmonth= Integer.valueOf(lunarsplit[1]);
		        if(prodate.HOLIDAYLUNAR(_lunarday, _lunarmonth) && (!(i<Integer.valueOf(days_solar[43]) || i>=Integer.valueOf(days_solar[44])))){
					remoteViews.setTextColor(id_solar[i], Color.parseColor("#fc3838"));
					remoteViews.setTextColor(id_lunar[i], Color.parseColor("#fc3838"));
				}
		        if(dayOfMonth == Integer.valueOf(days_solar[i]) && (!(i<Integer.valueOf(days_solar[43]) || i>=Integer.valueOf(days_solar[44])))){
					remoteViews.setTextColor(id_solar[i], Color.parseColor("#7CFC00"));
					remoteViews.setTextColor(id_lunar[i], Color.parseColor("#7CFC00"));
				}
				remoteViews.setTextViewText(id_lunar[i], days_lunar[i]);
			}

			appWidgetManager.updateAppWidget(thisWidget, remoteViews);

			
		}
	}

}
