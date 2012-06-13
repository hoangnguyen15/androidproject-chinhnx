package com.krazevina.thioto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	Calendar c;
	public DateUtils() {
		c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
	}



	public String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	
	
	public String timeremain(int time) {
	

		String remain = "";
		long phut = (long) (time)/ ( 60);
		long giay = (long) (time)% ( 60);
		
	   remain+=(phut>=10?phut:"0"+phut)+"':"+(giay>=10?giay:"0"+giay)+"''";

		return remain + "";
	}
	public String getDetailTimeNow(){
		c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		return (c.get(Calendar.HOUR_OF_DAY)>=10?c.get(Calendar.HOUR_OF_DAY):"0"+c.get(Calendar.HOUR_OF_DAY))
						   +""+(c.get(Calendar.MINUTE)>=10?c.get(Calendar.MINUTE):"0"+c.get(Calendar.MINUTE))
				           +""+(c.get(Calendar.SECOND)>=10?c.get(Calendar.SECOND):"0"+c.get(Calendar.SECOND))
				           +getNowDate().replace("/", "");
	}
	public String getNowDate() {// lay ve ngay thang hien tai
		String now = getDay() + "/";
		now += getMonth() + "/";
		now += getYear() + "";
		return now;
	}
	public String getDay() { // lay ve ngay trong thang
		return (c.get(Calendar.DATE)>=10?c.get(Calendar.DATE)+"":"0"+c.get(Calendar.DATE));
	}
	public String getMonth() {// lay ve thang 1-12
		return  (c.get(Calendar.MONTH)+1>=10?(c.get(Calendar.MONTH)+1)+"":"0"+(1+c.get(Calendar.MONTH)));
	}

	public String getYear() { // lay ve nam
		return  (c.get(Calendar.YEAR)>=10?c.get(Calendar.YEAR)+"":"0"+c.get(Calendar.YEAR));
	}

   
}