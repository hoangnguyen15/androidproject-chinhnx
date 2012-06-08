package krazevina.com.lunarvn;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

final public class global 
{
	static int dd=0;
	static int mm=0;
	static int yyyy=0;
	static int lunar_dd=0;
	static String lunar_mm="10";
	
	static int lunar_yyyy=2010;
	static textviewadapter txtviewadapter;
	static Context context;
	static GridView gridview;
	static String title;
	static TextView TextViewTilte;
	static int leapmonth=0;
	static int leapmonth_tem=0;	
	static String[] count_lunar;
	static int index=2;
	static NotificationManager mNotificationManager;	
	static GridView nextGridView;
	static ViewFlipper viewFlipper;
	static ListView listview;
	static long _id;
	static int socuochen=0;
	static int[][]datetime;
	static int index_alarm=0;
	static boolean checknotification=false;//check da co notification	
	public static String appId = "53202";
	public static String apiKey = "1337935329105815363";
	final static class ViewContent
	   {		  
		   static TextView content_describle;
	   }  
	final static class ViewChoice
	   {
			static ToggleButton Tog_ONOFF;
	   }  
	final  static class ViewTime
	   {		
		static TextView txtTime_Describle;
	   }  
	final  static class ViewDate
	   {	
		static TextView txtDate_Describle;
	   }  
	final  static class ViewRingtone
	   {		
		static TextView txtRingtone_Describle;
	   }  
	final  static class ViewRemider
	   {		
		static TextView txtReminder_Describle;
	   }
	final  static class ViewRepeat
	   {		
		static TextView txtRepeat_Describle;
	   }
	final  static class ViewXOA_OK
	   {		
		static Button bt_set;
		static Button bt_xoa;
	   }
}
