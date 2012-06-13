package com.krazevina.objects;

import java.util.Calendar;

import android.media.Ringtone;
import android.net.Uri;

public class Reminder 
{
	public Calendar c;
	public 	String content;
	public 	String title;
	public int repeatstyle,calendarstyle;
	public String alarmtone;
	public int alarmbefore,alarmstyle;
	public int id;
	public boolean status = true;
	public boolean notif = false;
	public Ringtone ring;
	
	
	public Reminder(int id,Calendar ca,String tit, String cont,int alarmbefore,int cals,int reps,String alas,int alarmstyle)
	{
		c = Calendar.getInstance();
		c.setTimeInMillis(ca.getTimeInMillis());
		title = tit;
		content = cont;
		this.alarmbefore = alarmbefore;
		calendarstyle = cals;
		repeatstyle = reps;
		alarmtone = alas;
		status = true;
		this.alarmstyle = alarmstyle;
		this.id = id;
	}
	
	public String getTime()
	{
		String s ="";
		s += ""+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
		return s;
	}
	public String getDate()
	{
		String s ="";
		s += ""+c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
		return s;
	}
}
