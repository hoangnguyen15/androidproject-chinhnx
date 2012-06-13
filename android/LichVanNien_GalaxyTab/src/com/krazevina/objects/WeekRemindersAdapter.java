package com.krazevina.objects;
import java.util.Calendar;

import com.krazevina.lichvannien.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeekRemindersAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
//    private int _select_position;
    public static Reminders r;
    Reminders rem;
    Calendar c;
    Context cont;
    public int stt;
   
    static void update(Context c)
    {
    	Sqlite sql = new Sqlite(c);
        r = sql.getReminders();
        sql.close();
        
    }
    
    public void notifyDataSetChanged()
    {
    	rem = r.getReminder(c);
    	super.notifyDataSetChanged();
    }
  
    public WeekRemindersAdapter(Context context,Calendar cal,int stt)
    {
        mInflater = LayoutInflater.from(context); 
        cont = context;
        //dung de lay ngay hien tai
        c = Calendar.getInstance();
        c.setTimeInMillis(cal.getTimeInMillis());
        if(r==null)
        {
	        Sqlite sql = new Sqlite(context);
	        r = sql.getReminders();
	        sql.close();
        }
        rem = r.getReminder(c);
        this.stt = stt;
    }
    public int getCount() 
    {
    	return rem.reminder.size();
    }
    public Object getItem(int position) 
    {
    	return position;
    }
    public long getItemId(int position) 
    {
        return position;
    }

    public View getView(int posi, View convertView, ViewGroup parent) 
    {
        ViewHolder holder;
        
        if (convertView == null)
        {
        	convertView = mInflater.inflate(R.layout.reminder_item_week, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.	 
        	
        	holder = new ViewHolder();
        	holder.time= (TextView)convertView.findViewById(R.id.txttime);
        	holder.tit= (TextView)convertView.findViewById(R.id.txttitle);
        	convertView.setTag(holder);
        } else 
        {
        	holder=(ViewHolder)convertView.getTag();
        }
        holder.time.setText(""+rem.reminder.get(posi).c.get(Calendar.HOUR_OF_DAY)+":"+rem.reminder.get(posi).c.get(Calendar.MINUTE));
        if(rem.reminder.get(posi).title == null||rem.reminder.get(posi).title.startsWith("null"))
        	holder.tit.setText(""+cont.getString(R.string.notitle));
        else
        	holder.tit.setText(""+rem.reminder.get(posi).title);
        return convertView;
    }
   static class ViewHolder
   {
	   TextView tit;
	   TextView time;
   }
}
  