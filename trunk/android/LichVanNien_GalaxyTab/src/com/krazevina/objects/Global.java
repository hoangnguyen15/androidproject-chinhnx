package com.krazevina.objects;
import java.util.Calendar;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

final public class Global 
{
	public static int dd=0;
	public static int mm=0;
	public static int yyyy=0;
	public static int lunar_dd=0;
	public static String lunar_mm="10";
	public static int lunar_yyyy=2011;
	public static DayAdapter dayAdapter;
	public static WeekAdapter weekAdapter;
	public static Context context;
	public static int select_position = -1;
	public static Drawable your_bg = null;
	
	public static Calendar selectDate;
	
	public static String title;
	public static TextView TextViewTilte;
	public static int leapmonth=0;
	public static int leapmonth_tem=0;	
	public static String[] count_lunar;
	public static int index=2;
	public static NotificationManager mNotificationManager;	
	public static GridView nextGridView;
	public static ViewFlipper viewFlipper;
	public static long _id;
	public static int socuochen=0;
	public static int[][]datetime;
	public static int index_alarm=0;
	public static boolean checknotification=false;//check da co notification	
	public static String appId = "56775";
	public static String apiKey = "1337935329105815363";

	
	public static Reminder curReminder;
	
	public final static class ViewContent
	   {		  
		   public static  TextView content_describle;
	   }  
	public final static class ViewChoice
	   {
			public  ToggleButton Tog_ONOFF;
	   }  
	public final  static class ViewTime
	   {		
		public  TextView txtTime_Describle;
	   }  
	public final  static class ViewDate
	   {	
		public  TextView txtDate_Describle;
	   }  
	public final  static class ViewRingtone
	   {		
		public static  TextView txtRingtone_Describle;
	   }  
	public final  static class ViewRemider
	   {		
		public static  TextView txtReminder_Describle;
	   }
	final  static class ViewRepeat
	   {		
		public TextView txtRepeat_Describle;
	   }
	public final  static class ViewXOA_OK
	   {		
		public Button bt_set;
		public  Button bt_xoa;
	   }
}
