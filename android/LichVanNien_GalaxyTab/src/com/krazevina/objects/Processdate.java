package com.krazevina.objects;

import java.util.Calendar;

import android.app.Application;
import android.util.Log;

import com.krazevina.lichvannien.Alarm;
public class Processdate extends Application
{
	private int _YEAR=0;
	private int _MONTH=0;
	private int _DAY=0;
	public String[] DAYS_SOLAR;	
	public String[] DAYS_LUNAR;
	public String[] DAYS_LUNAR_TEMP;
	public int count=0;
	public static Utils lunarcld;
	public int countcells=0;
	
	public Processdate()
	{
		DAYS_SOLAR= new String[50];		
		DAYS_LUNAR= new String[50];		
		DAYS_LUNAR_TEMP= new String[50];
		lunarcld= new Utils();
		
	}
	public int YEAR()
	{
		return _YEAR;
	}
	public void YEAR( int value)
	{
		_YEAR=value;
	}
	
	public int MONTH()
	{
		return _MONTH;
	}
	public void DAY( int value)
	{
		_DAY=value;
	}
	public int DAY()
	{
		return _DAY;
	}
	public void MONTH( int value)
	{
		_MONTH=value;
	}
	public boolean ISLEAPMONTH(int dd,int mm, int yyyy)
	{
		return Utils.IsLeapMonth(mm, yyyy, Utils.TIME_ZONE);
	}
	public void FILLSOLARDATE()
	{
		int k=0;
		String[][] solarmonth= lunarcld.getSolarMonth(_YEAR, _MONTH);	
		this.countcells=lunarcld.countcells;
		for(int i=0;i<Utils.NUMBER_ROW;i++)
			for(int j=0;j<Utils.NUMBER_COLUMN;j++)
			{
				DAYS_SOLAR[k]=solarmonth[i][j];
				k++;				
				
			}
		DAYS_SOLAR[43]=solarmonth[6][0];//index0
		DAYS_SOLAR[44]=solarmonth[6][1];//index1
		DAYS_SOLAR[45]=solarmonth[6][2];//1 or 2
	}
	public static String GETLUNARMONTHNAME(int lunarMonth)
	{
		
		String result;
		result=Utils.getLunarMonthName(lunarMonth);
		return result;
	}
	public static String GETLUNARYEARNAME(int lunarYear)
	{
	
		String result;
		result=Utils.getLunarYearName(lunarYear);
		return result;
	}
	public static String GETLUNARMONTHCANCHINAME(int lunarYear, int lunarMonth)
	{
		
		String result;
		result=Utils.getLunarMonthCanChiName(lunarYear,lunarMonth);
		return result;
	}
	public static int[]CONVERTSOLAR2LUNAR(int dd, int mm, int yy)
	{
		return Utils.convertSolar2Lunar(dd,mm,yy,Utils.TIME_ZONE);
	}
	 public int[] CONVERTLUNARTOSOLAR(int lunarDay,int lunarMonth, int lunarYear,int lunarLeap)
	{
					
		return Utils.convertLunar2Solar(lunarDay, lunarMonth, lunarYear, lunarLeap,Utils.TIME_ZONE);
	}
	public static String GETLUNARDAYCANCHINAME(int dd, int mm, int yy)
	{
		String result;
		int jd = Utils.jdFromDate(dd, mm, yy);
		result=Utils.getLunarDayCanChiName(jd);
		return result;
		
	}
	public String GETDAYYEARLYEVENT(int dd, int mm)
	{
		String result="";
		result=Utils.getDayYearlyEvent(dd,mm);
		return result;
	}
	public String GETDAYSPECIALYEARLTEVENT(int dd, int mm)
	{
		String result;
		result=Utils.getDaySpecialYearlyEvent(dd,mm);
		return result;
	}
	public void FILLLUNARDATE()
	{
		int k=0;
		Utils lunarcld= new Utils();
		String[][] lunarmonth= lunarcld.getLunarMonth(_YEAR, _MONTH);
		String[][]tem =lunarcld.datatemp;
		for(int i=0;i<Utils.NUMBER_ROW;i++)
		{
			for(int j=0;j<Utils.NUMBER_COLUMN;j++)
			{				
				DAYS_LUNAR[k]=lunarmonth[i][j];		
				DAYS_LUNAR_TEMP[k]=tem[i][j];
				k++;				
			}
		}	
	}
	public int GetPosition(int dd,int mm, int yyyy, int index0,int index1, int month,String[]data)
	   {
		   //month=or= 12
		   int result=0;// result is position on calendar
		   int count=0;
		   int countcellstemp=0;
		   if (countcells>35) countcellstemp=countcells;
		   else countcellstemp=35;
		   for(int i=0;i<countcellstemp;i++)
		   {
			   count++;			 
	          int daytemp=Integer.valueOf(data[count-1]);          
	           if(count >index0 && count<=index1 && dd== daytemp)
	           {
	        	   result=count-1;
	           }	           
		   }
		   return result;	   
	   }
	public boolean SPECIALYSOLARDATE(int dd, int mm)
	{
		return Utils.hasSpecialYearlyEvents(dd, mm);
	}
	
	public boolean REMINDERSOLARDATE(int dd, int mm,int yyyy)
	{
		if(mm>12)
		{
			mm=1;
			yyyy++;
		}
		if(mm<0)
		{
			mm=12;
			yyyy--;
		}
		try{
			if(Alarm.r==null)Alarm.update(Global.context);
			Reminder re;
			for(int i=0;i<Alarm.r.reminder.size();i++)
			{
				re = Alarm.r.reminder.get(i);
				if(re.c.get(Calendar.DAY_OF_MONTH)==dd &&
					re.c.get(Calendar.MONTH)==mm-1 &&
					re.c.get(Calendar.YEAR)==yyyy)
					return true;
			}
		}catch(Exception e)
		{
		}
		return false;
	}
	
	public boolean SPECIALLUNARDATE(int dd, int mm)
	{
		return Utils.hasYearlyEvents(dd, mm);
	}
	public boolean HOLIDAYSOLAR(int dd, int mm){
		return Utils.hasPublicSolarHolidays(dd, mm);
	}
	
	public boolean HOLIDAYLUNAR(int dd, int mm){	
		return Utils.hasPublicLunarHolidays(dd, mm);
	}
	
	
}