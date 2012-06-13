package com.krazevina.objects;

import java.util.Calendar;
import java.util.Vector;

public class Reminders 
{
	public Vector<Reminder> reminder = new Vector<Reminder>();
	public Reminders getReminder(Calendar c)
	{
		Reminders re = new Reminders();
		for(Reminder r:reminder)
		{
			if(r.c.get(Calendar.DATE)==c.get(Calendar.DATE)
				&&r.c.get(Calendar.MONTH)==c.get(Calendar.MONTH)
				&&r.c.get(Calendar.YEAR)==c.get(Calendar.YEAR))
			{
				re.reminder.add(r);
			}
		}
		return re;
	}
}
