package krazevina.com.lunarvn;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class app_baothuc extends Activity
{
	TextView mcontent;		
	android.media.Ringtone currentRT;
	int _reminder=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_baothuc_dialog);
        mcontent=(TextView)findViewById(R.id.txtnoidung_baothuc);
        // nut set hay nut cancel
        Button buttonSET = (Button) findViewById(R.id.set_baothuc);
        Button buttonCANCEL= (Button) findViewById(R.id.cancel_baothuc);
        try
        {
	        DbAdapter mDb = new DbAdapter(app_baothuc.this);
			mDb.open();
			Cursor mCursor=mDb.fetchStaff(global._id);
			mDb.close();
			if(mCursor!=null)
			{
				Log.e("app_baothuc mCusor", " !=null");
				String title=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_TITLE));
				String content=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_CONTENT));
				String reminder=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
				String start=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_START));
				String end=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_END));				
				if(global.index_alarm %2==0)
				{
					mcontent.setText("Trong: "+reminder+" phút nữa bạn sẽ có một sự kiện \n" + "Tiêu đề:" +title+"\n"+"Nội dung: "+content+"\n"+"Từ: "+start+" đến "+end);
				}
				else
				{
					mcontent.setText("Có một sự kiện bạn cần thực hiện ngay bây giờ \n" + "Tiêu đề:" +title+"\n"+"Nội dung: "+content+"\n"+"Từ: "+start+" đến "+end);
				}				
				String _uri=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_URI));
				Uri uriRT=Uri.parse(_uri);
				currentRT = RingtoneManager.getRingtone(this, uriRT);
				currentRT.play();				
			}
        }catch (Exception e) {
			Log.e("app_baothuc", e.toString());			
		}        
        buttonSET.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
							
				if(global.index_alarm<global.datetime.length-1)
				{
					global.index_alarm++;
					DbAdapter mDb= new DbAdapter(app_baothuc.this);
					mDb.open();
					Cursor mCursor= mDb.fetchStaff(global.datetime[global.index_alarm][5]);
		        	String reminder=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
		        	int _reminder =Integer.parseInt(reminder);
		        	if(_reminder==0 && global.index_alarm%2==0)
		        		global.index_alarm++;		        	
					int rowindex=global.index_alarm;	
					setTime(global.datetime[rowindex][0],global.datetime[rowindex][1],global.datetime[rowindex][2],global.datetime[rowindex][3],global.datetime[rowindex][4],global.datetime[rowindex][5]);
				}
				else// bo notification vi dat het cuoc hen
				{
					if(global.checknotification==true)
	        		{
		        		global.mNotificationManager.cancel(321);
		        		global.checknotification=false;
	        		}
				}					
				currentRT.stop();
				finish();
			}
		});
        buttonCANCEL.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				currentRT.stop();
				finish();
			}
		});
    }  
    public void setTime(int yyyy, int mm, int dd, int hour, int minutes, int id)
    {    	
    	Calendar ca = Calendar.getInstance();
        ca.set(yyyy, mm-1, dd, hour, minutes);
        global._id=id;
        Cursor mCursor;
        DbAdapter mDb= new DbAdapter(app_baothuc.this);
        mDb.open();
        mCursor=mDb.fetchStaff(global._id);
        
        if(global.index_alarm-1 %2!=0)
		{
        	String title= mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_TITLE));
	    	String content= mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_CONTENT));
	    	String start = mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_START));
	    	String end= mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_END));
	    	String date= mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_DATE));
	    	String ringtone= mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_RINGTONE));	    	
	    	String reminder= mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
	    	String repeat=  mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_REPEAT));
	    	String style=  mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_TIMESTYLE));
	    	String uri=  mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_URI));
	    	mDb.UPDATESTAFF(id, title, content, start, end, date, ringtone, 0, reminder, repeat, style, uri);	    	
		}		
        mDb.close();
    	Intent intent = new Intent(this, OneTimeAlarm.class);
    	intent.putExtra(DbAdapter.KEY_URI, mCursor.getString(
    	mCursor.getColumnIndexOrThrow(DbAdapter.KEY_URI)));    	 
        PendingIntent pi = PendingIntent.getBroadcast(this,0, intent,0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, ca.getTimeInMillis(), pi);        
        
    }
}