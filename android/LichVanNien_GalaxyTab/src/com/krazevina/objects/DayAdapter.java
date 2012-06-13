package com.krazevina.objects;
import java.util.Calendar;

import com.krazevina.lichvannien.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public Processdate prodate;  
    public int today;
    public int thisyear;
    public int thismonth;
    private String _solarday_selected="";
    private String _lunar_selected="";
    private int _position=-1;
    private int _select_position;
    public String lunar_selected()
    {
    	return _lunar_selected;
    }
    public String solar_selected()
    {
    	return _solarday_selected;
    }
    public DayAdapter(Context context)
    {
    	mInflater = LayoutInflater.from(context);
    }
    public DayAdapter(Context context,int _MONTH,int _YEAR)
    {
        mInflater = LayoutInflater.from(context); 
        prodate= new Processdate();
        prodate.MONTH(_MONTH);
        prodate.YEAR(_YEAR);        
        prodate.FILLSOLARDATE();
        prodate.FILLLUNARDATE();
        //dung de lay ngay hien tai
        final Calendar c = Calendar.getInstance();
        thisyear=c.get(Calendar.YEAR);
        thismonth=c.get(Calendar.MONTH)+1;
        today = c.get(Calendar.DATE);        
    }
    public DayAdapter(Context context,int _DAY,int _MONTH,int _YEAR)
    {
    	int index0,index1,month;
    	
        mInflater = LayoutInflater.from(context); 
        prodate= new Processdate();
        prodate.MONTH(_MONTH);
        prodate.YEAR(_YEAR);
        prodate.DAY(_DAY);
        prodate.FILLSOLARDATE();
        prodate.FILLLUNARDATE();
        index0=Integer.valueOf(prodate.DAYS_SOLAR[43]);
    	index1=Integer.valueOf(prodate.DAYS_SOLAR[44]);
    	month=Integer.valueOf(prodate.DAYS_SOLAR[45]);
        _position= prodate.GetPosition(prodate.DAY(), prodate.MONTH(), prodate.YEAR(), index0, index1, month, prodate.DAYS_SOLAR);
        //dung de lay ngay hien tai
        final Calendar c = Calendar.getInstance();
        thisyear=c.get(Calendar.YEAR);
        thismonth=c.get(Calendar.MONTH)+1;
        today = c.get(Calendar.DATE);        
    }    
    public int getCount() 
    {
    	if(prodate.countcells>35)
    		return 42;
    	else return 35;
    }
    public Object getItem(int position) 
    {
    	return position;
    }
    public long getItemId(int position) 
    {
    	
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    {      	
        ViewHolder holder;
        
        
        if (convertView == null)
        {
        	convertView = mInflater.inflate(R.layout.grid_item, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.	 
        	
        	holder = new ViewHolder();
        	holder.solar= (TextView)convertView.findViewById(R.id.grid_item_day);        	
        	holder.lunar=(TextView)convertView.findViewById(R.id.grid_item_daylunar);
        	holder.img_specialday = (ImageView)convertView.findViewById(R.id.img_item_specialday);
        	holder.lilayout=(LinearLayout)convertView.findViewById(R.id.layout_grid_item);
        	convertView.setTag(holder);       	
        	
        } else 
        {
        	 // Get the ViewHolder back to get fast access to the TextView
        	holder=(ViewHolder)convertView.getTag();
        }
        
        int _today=0;        
        _today= Integer.valueOf(prodate.DAYS_SOLAR[position]);
        
        // doi mau nhung thang khong la thang hien tai
        if(position<Integer.valueOf(prodate.DAYS_SOLAR[43])|| position>= Integer.valueOf(prodate.DAYS_SOLAR[44]))
        {
	       	holder.solar.setTextColor(R.color.color_today);
	       	holder.lunar.setTextColor(R.color.color_today);
	       	holder.solar.setShadowLayer(0, 0, 0, Color.BLACK);
	       	holder.lunar.setShadowLayer(0, 0, 0, Color.BLACK);
	       	//chon ngay dac biet cua thang truoc thang hien tai
	       	if(position<Integer.valueOf(prodate.DAYS_SOLAR[43]))
	       	{
	       		if(prodate.SPECIALYSOLARDATE(_today, prodate.MONTH()-1))
	       			holder.img_specialday.setImageResource(R.drawable.star2);
	       		if(prodate.REMINDERSOLARDATE(_today, prodate.MONTH()-1,prodate.YEAR()))
	       			holder.img_specialday.setImageResource(R.drawable.star1);
	       	}
	       	//chon ngay duong dac biet cua thang sau thang hien tai
	       	else if (position>=Integer.valueOf(prodate.DAYS_SOLAR[44]))
	       	{
	       		if( prodate.SPECIALYSOLARDATE(_today, prodate.MONTH()+1))
	       			holder.img_specialday.setImageResource(R.drawable.star2);
	       		if(prodate.REMINDERSOLARDATE(_today, prodate.MONTH()+1,prodate.YEAR()))
	       			holder.img_specialday.setImageResource(R.drawable.star1);
	       	}
        }
        else 
    	{
    		if(prodate.SPECIALYSOLARDATE(_today, prodate.MONTH()))
    			holder.img_specialday.setImageResource(R.drawable.star2);
    		if(prodate.REMINDERSOLARDATE(_today, prodate.MONTH(),prodate.YEAR()))
       			holder.img_specialday.setImageResource(R.drawable.star1);
    	}	 
       
    	holder.solar.setText(prodate.DAYS_SOLAR[position]);
       
    	// co ngay am dac biet khong    	
        String[] lunarsplit= new String[2];
        lunarsplit=prodate.DAYS_LUNAR[position].split("/");
        int _lunarday= Integer.valueOf(lunarsplit[0]);
        int _lunarmonth= Integer.valueOf(lunarsplit[1]);
        
        //set nen khac cho ngay am dac biet
        holder.lunar.setText(prodate.DAYS_LUNAR[position]);
        if(prodate.SPECIALLUNARDATE(_lunarday, _lunarmonth)){
        	holder.img_specialday.setImageResource(R.drawable.star2);
        }
        
        //Ngay am dc nghi
        if(prodate.HOLIDAYLUNAR(_lunarday, _lunarmonth) && (!(position<Integer.valueOf(prodate.DAYS_SOLAR[43])|| position>= Integer.valueOf(prodate.DAYS_SOLAR[44])))){
        	holder.solar.setTextColor(Color.parseColor("#fc3838"));
        	holder.solar.setShadowLayer(1, 1, 1, Color.WHITE);
        }
        
        //Ngay duong duoc nghi
        if(prodate.HOLIDAYSOLAR(_today, prodate.MONTH()) && (!(position<Integer.valueOf(prodate.DAYS_SOLAR[43])|| position>= Integer.valueOf(prodate.DAYS_SOLAR[44])))){
        	holder.solar.setTextColor(Color.parseColor("#fc3838"));
        	holder.solar.setShadowLayer(1, 1, 1, Color.WHITE);
        }
        
        //set Selected
        if(Global.select_position == position && Global.select_position!=-1){
        	holder.lilayout.setBackgroundResource(R.drawable.highlight_black);
        }
        
        //khi nhan nut chon ngay duong
//        if(_position!=-1 && _position==position)
//        {
//        	holder.solar.setTextColor(Color.BLUE);
//        	holder.lunar.setTextColor(Color.RED);
//        	holder.lilayout.setBackgroundResource(R.drawable.highlight_white);
//        }
        
        //Ngay CN
        if(position%7 == 0 && (!(position<Integer.valueOf(prodate.DAYS_SOLAR[43])|| position>= Integer.valueOf(prodate.DAYS_SOLAR[44])))){
//        	holder.solar.getPaint().bgColor = Color.WHITE;
//        	color="#fc3838"
        	
        	holder.solar.setTextColor(Color.parseColor("#fc3838"));
        	holder.solar.setShadowLayer(1, 1, 1, Color.WHITE);
//        	holder.lilayout.setBackgroundResource(R.drawable.highlight_white);
        	
        	
//    		Typeface face=Typeface.createFromAsset(, "fonts/College_Halo.ttf");
//    		holder.solar.setTypeface(face);
        }
        
        
        
       
        // chon today
        int index1=Integer.valueOf(prodate.DAYS_SOLAR[43]);
        int index2=Integer.valueOf(prodate.DAYS_SOLAR[44]);
        int[]tem=getnextprevious(Integer.valueOf(prodate.DAYS_SOLAR[position]), prodate.MONTH(),prodate.YEAR(), index1, index2, position);
        if((_today==today && thismonth==tem[1]&& thisyear==tem[2]) && (!(position<Integer.valueOf(prodate.DAYS_SOLAR[43])|| position>= Integer.valueOf(prodate.DAYS_SOLAR[44]))))
        {
//        	holder.solar.setTextColor(R.color.color_today);
//        	holder.lunar.setTextColor(R.color.color_today);
        	holder.lilayout.setBackgroundResource(R.drawable.highlight_white);        	
        }
        return convertView;
    }
    public int[] getnextprevious(int dd, int mm, int yyyy, int index1, int index2,int position)
    {    	
    	if(position<index1)//thang truoc
    	{
    		if(mm==1) 
    			mm=12;    			
    		else mm--;
    		yyyy--;
    	}
    	else if(position>index2)//thang sau
    	{
    		if(mm==12)    		
    			mm=1;
    		else mm++;
    		yyyy++;
    	}
    	return new int[]{dd,mm,yyyy};
    }
   static class ViewHolder
   {
	   LinearLayout lilayout;
	   TextView solar;
	   TextView lunar;
	   ImageView img_specialday;
   }  
   
}
  