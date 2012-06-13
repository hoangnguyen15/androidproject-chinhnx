package com.krazevina.objects;

import java.util.Calendar;

import android.util.Log;

public class LunarCalendar extends Calendar
{
	private static final long serialVersionUID = 143L;
	boolean leapyear;
	int leapm;
	boolean leap;
	
	public LunarCalendar()
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		int[] lunar = Utils.convertSolar2Lunar(c.get(DATE), c.get(MONTH)+1, c.get(YEAR), 7);
		fields = new int[FIELD_COUNT];
		fields[DATE] = lunar[0];
		fields[MONTH] = lunar[1];
		fields[YEAR] = lunar[2];
		if(lunar[3]==1)
		{
			leap = true;
			leapm = lunar[1];
			leapyear = true;
		}
		else
		{
			leapm = Utils.getLeapMonth(lunar[2], 7);
			if(leapm>0)
			{
				leapyear = true;
			}
		}
	}
	@Override
	public void add(int field, int value) {
	}

	@Override
	protected void computeFields() {
	}

	@Override
	protected void computeTime() {
	}

	@Override
	public int getGreatestMinimum(int field) {
		return 0;
	}

	@Override
	public int getLeastMaximum(int field) {
		return 0;
	}

	@Override
	public int getMaximum(int field) {
		switch (field) 
		{
			case YEAR:return 9999;
			case MONTH:return 12;
			case DATE:return 30;
			case HOUR:return 23;
			default:
				break;
		}
		return 60;
	}

	@Override
	public int getMinimum(int field) 
	{
		switch (field) 
		{
			case YEAR:return 0;
			case MONTH:return 1;
			case DATE:return 1;
			case HOUR:return 0;
			default:
				break;
		}
		return 0;
	}

	@Override
	public void roll(int field, boolean increment) 
	{
		switch (field) 
		{
			case YEAR:
				if(increment)this.fields[YEAR]++;
				else this.fields[YEAR]--;
				
				leapm = Utils.getLeapMonth(fields[YEAR], 7);
				if(leapm<0)
				{
					leap = false;
					leapyear = false;
				}
				else leapyear = true;
				break;
				
			case MONTH:
				if(leapyear&&fields[MONTH]==leapm)
				{
					if(leap)
					{
						leap = !leap;
						if(increment)
						{
							if(fields[MONTH]==getMaximum(MONTH))
							{
								fields[YEAR]++;
								fields[MONTH]=1;
							}
							else
							fields[MONTH]++;
						}
						else
						{
							leap = false;
						}
					}
					else leap = true;
				}
				else
				{
					if(increment)
					{
						if(fields[MONTH]==getMaximum(MONTH))
						{
							roll(YEAR,increment);
							fields[MONTH]=1;
						}
						else
						fields[MONTH]++;
					}
					else
					{
						if(fields[MONTH]==getMinimum(MONTH))
						{
							roll(YEAR,increment);
							fields[MONTH]=12;
							if(leapyear&&leapm==12)leap = true;
						}
						else
						fields[MONTH]--;
						if(leapyear&&leapm==fields[MONTH])leap = true;
					}
				}
				break;
			case DATE:
				if(increment)
				{
					if(fields[DATE]==getMaximum(DATE))
					{
						fields[DATE]=1;
						roll(MONTH,increment);
					}
					else
					fields[DATE]++;
				}
				else
				{
					if(fields[DATE]==getMinimum(DATE))
					{
						fields[DATE]=30;
						roll(MONTH,increment);
					}
					else
					fields[DATE]--;
				}
				break;
			default:
				break;
		}
	}
	
	public void setMonth(int month,boolean leap)
	{
		fields[MONTH] = month;
		if(leapyear&&leapm==month)this.leap=leap;
	}
	public String getLeap()
	{
		if(leap)return "+";
		else return "";
	}
	public int getLeapi()
	{
		if(leap)return 1;
		else return 0; 
	}

}
