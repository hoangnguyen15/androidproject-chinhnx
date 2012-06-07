package krazevina.com.lunarvn;

import android.app.Application;
public class processdate extends Application
{
	private int _YEAR=0;
	private int _MONTH=0;
	private int _DAY=0;
	public String[] DAYS_SOLAR;	
	public String[] DAYS_LUNAR;
	public String[] DAYS_LUNAR_TEMP;
	public int count=0;
	public static utils lunarcld;
	public int countcells=0;
	
	public processdate()
	{
		DAYS_SOLAR= new String[50];		
		DAYS_LUNAR= new String[50];		
		DAYS_LUNAR_TEMP= new String[50];
		lunarcld= new utils();
		
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
		return utils.IsLeapMonth(dd,mm, yyyy, utils.TIME_ZONE);
	}
	public void FILLSOLARDATE()
	{
		int k=0;		
		String[][] solarmonth= lunarcld.getSolarMonth(_YEAR, _MONTH);	
		this.countcells=lunarcld.countcells;
		for(int i=0;i<utils.NUMBER_ROW;i++)
			for(int j=0;j<utils.NUMBER_COLUMN;j++)
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
		result=utils.getLunarMonthName(lunarMonth);
		return result;
	}
	public static String GETLUNARYEARNAME(int lunarYear)
	{
	
		String result;
		result=utils.getLunarYearName(lunarYear);
		return result;
	}
	public static String GETLUNARMONTHCANCHINAME(int lunarYear, int lunarMonth)
	{
		
		String result;
		result=utils.getLunarMonthCanChiName(lunarYear,lunarMonth);
		return result;
	}
	public static int[]CONVERTSOLAR2LUNAR(int dd, int mm, int yy)
	{
		return utils.convertSolar2Lunar(dd,mm,yy,utils.TIME_ZONE);
	}
	 public int[] CONVERTLUNARTOSOLAR(int lunarDay,int lunarMonth, int lunarYear,int lunarLeap)
	{
					
		return utils.convertLunar2Solar(lunarDay, lunarMonth, lunarYear, lunarLeap,utils.TIME_ZONE);
	}
	public static String GETLUNARDAYCANCHINAME(int dd, int mm, int yy)
	{
		String result;
		int jd = utils.jdFromDate(dd, mm, yy);
		result=utils.getLunarDayCanChiName(jd);
		return result;
		
	}
	public String GETDAYYEARLYEVENT(int dd, int mm)
	{
		String result="";
		result=utils.getDayYearlyEvent(dd,mm);
		return result;
	}
	public String GETDAYSPECIALYEARLTEVENT(int dd, int mm)
	{
		String result;
		result=utils.getDaySpecialYearlyEvent(dd,mm);
		return result;
	}
	public void FILLLUNARDATE()
	{

		int k=0;
		utils lunarcld= new utils();
		String[][] lunarmonth= lunarcld.getLunarMonth(_YEAR, _MONTH);
		String[][]tem =lunarcld.datatemp;
		for(int i=0;i<utils.NUMBER_ROW;i++)
		{
			for(int j=0;j<utils.NUMBER_COLUMN;j++)
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
		return utils.hasSpecialYearlyEvents(dd, mm);
	}
	public boolean SPECIALLUNARDATE(int dd, int mm)
	{
		return utils.hasYearlyEvents(dd, mm);
	}
}