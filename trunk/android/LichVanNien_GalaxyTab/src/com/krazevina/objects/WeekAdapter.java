package com.krazevina.objects;
import java.util.Calendar;
import java.util.Vector;

import com.krazevina.lichvannien.R;
import com.krazevina.lichvannien.WeekView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WeekAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public Processdate prodate;  
    public int today;
    public int thisyear;
    public int thismonth;
    private String _solarday_selected="";
    private String _lunar_selected="";
    private int _midpos=-1;
    Context con;
    
    Calendar c;
    
    public void update(int pos)
    {
    	c.setTimeInMillis(c.getTimeInMillis()+(pos-_midpos)*24l*60l*60l*1000l);
    	notifyDataSetChanged();
    	WeekView.onNotify();
    }
    public String lunar_selected()
    {
    	return _lunar_selected;
    }
    public String solar_selected()
    {
    	return _solarday_selected;
    }
    public WeekAdapter(Context context)
    {
    	mInflater = LayoutInflater.from(context);
    }
    public int monthOfPosition(int pos)
    {
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(c.getTimeInMillis()+(pos-_midpos)*24l*60l*60l*1000l);
    	return cal.get(Calendar.MONTH)+1;
    }
    public int dayOfPosition(int pos)
    {
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(c.getTimeInMillis()+(pos-_midpos)*24l*60l*60l*1000l);
    	return cal.get(Calendar.DATE);
    }
    public int yearOfPosition(int pos)
    {
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(c.getTimeInMillis()+(pos-_midpos)*24l*60l*60l*1000l);
    	return cal.get(Calendar.YEAR);
    }
    
    public WeekAdapter(Context context,int _DAY,int _MONTH,int _YEAR)
    {
        mInflater = LayoutInflater.from(context); 
        con = context;
        //dung de lay ngay hien tai
        c = Calendar.getInstance();
        thisyear=c.get(Calendar.YEAR);
        thismonth=c.get(Calendar.MONTH)+1;
        today = c.get(Calendar.DATE);
        
        prodate= new Processdate();
        prodate.MONTH(_MONTH);
        prodate.YEAR(_YEAR);
        prodate.DAY(_DAY);
        prodate.FILLSOLARDATE();
        prodate.FILLLUNARDATE();
        
        c.set(_YEAR, _MONTH-1, _DAY);
        adapters = new Vector<WeekRemindersAdapter>();
        for(int i=0;i<50;i++)
        {
        	adapters.add(new WeekRemindersAdapter(context, c, i));
        	lv.add(new ListView(context));
        }
        _midpos = 23;
    }
    public int getCount() 
    {
    	return 50;
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
        	convertView = mInflater.inflate(R.layout.grid_item_week, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.	 
        	
        	holder = new ViewHolder();
        	holder.solar= (TextView)convertView.findViewById(R.id.grid_item_day);
        	holder.title= (TextView)convertView.findViewById(R.id.txttitle);
        	holder.lunar=(TextView)convertView.findViewById(R.id.grid_item_daylunar);
        	holder.img_specialday = (ImageView)convertView.findViewById(R.id.img_item_specialday);
        	holder.lilayout=(LinearLayout)convertView.findViewById(R.id.layout_grid_item);
        	holder.lvreminder = (ListView)convertView.findViewById(R.id.lvreminder);
        	holder.adapter = new WeekRemindersAdapter(con, dateOfPos(posi),posi);
        	adapters.add(posi, holder.adapter);
        	lv.add(posi, holder.lvreminder);
        	convertView.setTag(holder);
        } else 
        {
        	holder=(ViewHolder)convertView.getTag();
        }
        holder.title.setText(""+dayInWeekOfPos(posi));
       
		if(prodate.SPECIALYSOLARDATE(dayOfPosition(posi), monthOfPosition(posi)))
			holder.img_specialday.setImageResource(R.drawable.star2);
		if(prodate.REMINDERSOLARDATE(dayOfPosition(posi), monthOfPosition(posi),yearOfPosition(posi)))
   			holder.img_specialday.setImageResource(R.drawable.star1);
    	holder.solar.setText(""+dayOfPosition(posi));
       
    	// co ngay am dac biet khong
        int[] lunarsplit= new int[4];
        lunarsplit=Utils.convertSolar2Lunar(dayOfPosition(posi), monthOfPosition(posi),yearOfPosition(posi), 7.0);
        int _lunarday= lunarsplit[0];
        int _lunarmonth= lunarsplit[1];
        //set nen khac cho ngay am dac biet
        holder.lunar.setText(""+_lunarday+"/"+_lunarmonth);
        if(prodate.SPECIALLUNARDATE(_lunarday, _lunarmonth)){
        	holder.img_specialday.setImageResource(R.drawable.star2);
        }
        
        //Ngay am dc nghi
        if(prodate.HOLIDAYLUNAR(_lunarday, _lunarmonth)){
        	holder.solar.setTextColor(Color.parseColor("#fc3838"));
        	holder.solar.setShadowLayer(1, 1, 1, Color.WHITE);
        }
        
        //Ngay duong duoc nghi
        if(prodate.HOLIDAYSOLAR(dayOfPosition(posi), monthOfPosition(posi))){
        	holder.solar.setTextColor(Color.parseColor("#fc3838"));
        	holder.solar.setShadowLayer(1, 1, 1, Color.WHITE);
        }
        
        //set Selected
        try{
        	WeekView.txt_month.setText("Tháng "+monthOfPosition(posi)+"/"+yearOfPosition(posi));
        	
	        if(Math.abs(Global.selectDate.getTimeInMillis() - dateOfPos(posi).getTimeInMillis())<23l*60l*60l*1000l)
	        	holder.lilayout.setBackgroundColor(Color.argb(88, 0, 0, 0));
        }catch(Exception e){}
        
        
        //Ngay CN
        if(dateOfPos(posi).get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        {
        	holder.solar.setTextColor(Color.parseColor("#fc3838"));
        	holder.solar.setShadowLayer(1, 1, 1, Color.WHITE);
        }
        
        // chon today
        if(dayOfPosition(posi)==today && thismonth==monthOfPosition(posi)&& thisyear==yearOfPosition(posi))
        {
        	holder.lilayout.setBackgroundColor(Color.argb(88, 255, 255, 255));        	
        }
        
        holder.lvreminder.setAdapter(holder.adapter);
        holder.lvreminder.setOnItemClickListener(new OnItemClickListener() 
        {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				Reminder r = adapters.get(lv.indexOf(arg0)).rem.reminder.get(arg2);
				Global.curReminder = r;
				WeekView.showEdit();
			}
		});
        return convertView;
    }
    
    static Vector<WeekRemindersAdapter> adapters;
    Vector<ListView> lv = new Vector<ListView>();
    
   class ViewHolder
   {
	   LinearLayout lilayout;
	   TextView solar;
	   TextView lunar;
	   TextView title;
	   ImageView img_specialday;
	   ListView lvreminder;
	   WeekRemindersAdapter adapter;
   }
   
   
   public Calendar dateOfPos(int pos)
   {
	   Calendar cal = Calendar.getInstance();
	   cal.setTimeInMillis(c.getTimeInMillis()+(pos-_midpos)*24l*60l*60l*1000l);
	   return cal;
   }
   public String dayInWeekOfPos(int pos)
   {
	   return dayInWeek(dateOfPos(pos).get(Calendar.DAY_OF_WEEK));
   }
   private String dayInWeek(int d)
   {
	   String s="";
	   switch(d)
	   {
		   case Calendar.SUNDAY:return "Chủ nhật";
		   case Calendar.MONDAY:return "Thứ hai";
		   case Calendar.TUESDAY:return "Thứ ba";
		   case Calendar.WEDNESDAY:return "Thứ tư";
		   case Calendar.THURSDAY:return "Thứ năm";
		   case Calendar.FRIDAY:return "Thứ sáu";
		   case Calendar.SATURDAY:return "Thứ bảy";
	   }
	   return s;
   }
   public int[] solardate(int pos)
   {
	   Calendar cal = dateOfPos(pos);
	   int[]d = Utils.convertSolar2Lunar(cal.get(Calendar.DATE), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR), 7.0);
	   return d;
   }
}
  