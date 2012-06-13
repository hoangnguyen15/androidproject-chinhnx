package com.krazevina.lichvannien;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import com.krazevina.objects.Alarmreciever;
import com.krazevina.objects.Reminder;
import com.krazevina.objects.Reminders;
import com.krazevina.objects.Sqlite;

public class Alarm extends Service
{
	public static Reminders r;
//	public static Reminders remalarming;
	Vibrator v;
	public static Context context;
	
	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

	public static void update(Context c)
	{
		if(r!=null)
		for(Reminder rem:r.reminder)
		{
			cancel(rem);
		}
		
		Log.d("ZZZZZZ", "update serv");
		Sqlite sql = new Sqlite(c);
		r = sql.getReminders();
		for(int i=0;i<r.reminder.size();i++)
		{
			Reminder re = r.reminder.get(i); 
			Log.d("ZZZZZZZ", "reminder "+i+" :"+re.c.get(Calendar.HOUR)+":"+re.c.get(Calendar.MINUTE));
		}
		sql.close();
	}
	
	static NotificationManager mNotificationManager;
	static Notification notif;
	static PendingIntent pendingIntent;
	@Override
	public void onCreate() 
	{
		Log.d("ZZZZZZ", "create serv");
		String ns = Context.NOTIFICATION_SERVICE;
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mNotificationManager = (NotificationManager) getSystemService(ns);
		
		ComponentName comp = new ComponentName(this.getPackageName(), getClass().getName());
		Intent intent = new Intent().setComponent(comp);
		pendingIntent = PendingIntent.getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
	}
	@Override
	public void onDestroy() 
	{
	}
	
	Timer t1;
	TimerTask t;
	long time;
	Calendar c;
	
	@Override
	public void onStart(Intent intent, int startid) 
	{
		Log.d("ZZZZZZ", "start serv");
		c = Calendar.getInstance();
		t1 = new Timer();
		t = new TimerTask() 
		{
			public void run() 
			{
				try{
					time = System.currentTimeMillis();
					c.setTimeInMillis(time);
					for(int i=0;i<r.reminder.size();i++)
					{
						Reminder rcur = r.reminder.get(i);
						if(rcur.repeatstyle == 0)			// repeat per month
						{
							rcur.c.set(Calendar.MONTH, c.get(Calendar.MONTH));
							rcur.c.set(Calendar.YEAR, c.get(Calendar.YEAR));
							long timeleft =  rcur.c.getTimeInMillis()-time ;
							
							// n min to alarm
							if(timeleft>0&&timeleft<rcur.alarmbefore*60*1000)
							{
								if(rcur.status)
								{
									alarm(rcur);
									notifyAlarm(rcur, Alarm.this);
									rcur.status = false;
								}
							}
							else
							{
								rcur.status = true;
								mNotificationManager.cancel(rcur.id);
							}
						}
						if(rcur.repeatstyle == 1)			// repeat per year
						{
							rcur.c.set(Calendar.YEAR, c.get(Calendar.YEAR));
							long timeleft = rcur.c.getTimeInMillis()-time;
							
							// n min to alarm
							if(timeleft>0&&timeleft<rcur.alarmbefore*60*1000)
							{
								if(rcur.status)
								{
									notifyAlarm(rcur, Alarm.this);
									alarm(rcur);
									rcur.status = false;
								}
							}
							else if(timeleft>0&&timeleft<24*60*60*1000 && rcur.status)
							{
								if(!rcur.notif)
								{
									notifyAlarm(rcur, Alarm.this);
									rcur.notif = true;
								}
							}
							else
							{
								rcur.status = true;
								rcur.notif = false;
								mNotificationManager.cancel(rcur.id);
							}
						}
					}
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		};
		t1.schedule(t, 0,5000);
	}
	
	public void notifyAlarm(Reminder r, Context c)
	{
		notif = new Notification(R.drawable.icon, getString(R.string.app_name), r.c.getTimeInMillis());
		notif.setLatestEventInfo(this, getString(R.string.app_name), r.content, pendingIntent);
		mNotificationManager.notify(r.id, notif);
	}
	public static void cancel(Reminder r)
	{
		mNotificationManager.cancel(r.id);
	}
	Ringtone ring;
	public void alarm(Reminder r)
	{
		ring = RingtoneManager.getRingtone(Alarm.this, Uri.parse(r.alarmtone));
		try{
			AlarmDiag.re = r;
			AlarmDiag.r = ring;
			
			Intent dialogIntent = new Intent(getBaseContext(), AlarmDiag.class);
			dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplication().startActivity(dialogIntent);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
