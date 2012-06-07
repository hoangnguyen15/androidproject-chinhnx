package krazevina.com.lunarvn;
import java.util.Calendar;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OneTimeAlarm extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {				
		try
		{
		   Calendar c = Calendar.getInstance();
		   Date date= new Date();
		   int yyyy = c.get(Calendar.YEAR);        
		   int mm = c.get(Calendar.MONTH)+1;
		   int dd = c.get(Calendar.DATE);
		   int hour=c.get(Calendar.HOUR);
		   int minutes=c.get(Calendar.MINUTE);	   
		   hour=date.getHours();
		   
		  // hour=hour+12;//can sua lai
		   int rowindex=global.index_alarm;
		   int _yyyy=global.datetime[rowindex][0];
		   int _mm=global.datetime[rowindex][1];
		   int _dd=global.datetime[rowindex][2];
		   int _hour=global.datetime[rowindex][3];
		   int _minutes=global.datetime[rowindex][4];		 
		   //Toast.makeText(context, "day la o:: ONE TIMEALARM", Toast.LENGTH_LONG).show();		  
		   Log.e("rowid :",String.valueOf(rowindex));
		   Log.e("Hom nay:", String.valueOf(dd)+"/"+String.valueOf(mm)+"/"+String.valueOf(yyyy)+"/"+String.valueOf(hour)+"/"+String.valueOf(minutes));
		   Log.e("Ngay dong ho:", String.valueOf(_dd)+"/"+String.valueOf(_mm)+"/"+String.valueOf(_yyyy)+"/"+String.valueOf(_hour)+"/"+String.valueOf(_minutes));
		   if(yyyy==_yyyy&& mm==_mm && dd==_dd && hour==_hour && minutes==_minutes)
		   {
			 // Toast.makeText(context, "day la o:: ONE TIMEALARM va bang nhau:::", Toast.LENGTH_LONG).show();
			  Intent myintent = new Intent(context, app_baothuc.class);
			  myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			  context.startActivity(myintent);
		   }
		}catch (Exception e) {
			Log.e("Nhan receiver", e.toString());
		}	
	}
}
