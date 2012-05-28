package com.krazevina.objects;

import java.util.Calendar;
import java.util.Vector;

public class Match {
	public int ID,groupID,team1,team2,firstPick,secPick,status;
	public String stadium,end,finalScore,referee;
	public Vector<Event> events;
	public int tv;
	private String start;
	
	public String start(){
		String s="";
		Calendar cal = Calendar.getInstance();
		String datetime[] = start.split(" ");
		String date[] = datetime[0].split("-");
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.DATE, Integer.parseInt(date[2]));
		cal.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		
		String time[] = datetime[1].split(":");
		cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
		cal.set(Calendar.MINUTE,Integer.parseInt(time[1]));
		cal.set(Calendar.SECOND, 0);
		
		cal.setTimeInMillis(cal.getTimeInMillis()+(Global.timeZone-7)*3600000l);
		
		s = "2012-"+(cal.get(Calendar.MONTH)+1<10?"0":"")+(cal.get(Calendar.MONTH)+1)+"-"+
		(cal.get(Calendar.DATE)<10?"0":"")+cal.get(Calendar.DATE)+" "+
		cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
		s = s.replaceAll(":0", ":00");
		return s;
	}
	
	public void setStart(String s){
		start = s;
	}
	public String rawStart(){
		return start;
	}
}
