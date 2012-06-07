package krazevina.com.lunarvn;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class textviewadapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public processdate prodate;  
    public int today;
    public int thisyear;
    public int thismonth;
    private String _solarday_selected="";
    private String _lunar_selected="";
    private int _position=-1;
    public String lunar_selected()
    {
    	return _lunar_selected;
    }
    public String solar_selected()
    {
    	return _solarday_selected;
    }
    public textviewadapter(Context context)
    {
    	mInflater = LayoutInflater.from(context);
    }
    public textviewadapter(Context context,int _MONTH,int _YEAR)
    {
        mInflater = LayoutInflater.from(context); 
        prodate= new processdate();
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
    public textviewadapter(Context context,int _DAY,int _MONTH,int _YEAR)
    {
    	int index0,index1,month;
    	
        mInflater = LayoutInflater.from(context); 
        prodate= new processdate();
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
        	convertView = mInflater.inflate(R.layout.textview_cell, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.	 
        	
        	holder = new ViewHolder();
        	holder.solar= (TextView)convertView.findViewById(R.id.solar);        	
        	holder.lunar=(TextView)convertView.findViewById(R.id.lunar);
        	holder.lilayout=(LinearLayout)convertView.findViewById(R.id.bgcell);
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
	       	//chon ngay dac biet cua thang truoc thang hien tai
	       	if(position<Integer.valueOf(prodate.DAYS_SOLAR[43])&&prodate.SPECIALYSOLARDATE(_today, prodate.MONTH()-1))
	       	{
	       		holder.lilayout.setBackgroundResource(R.drawable.bgspecialdates);	       		
	       	}	       	
	       	//chon ngay duong dac biet cua thang sau thang hien tai
	       	else if (position>=Integer.valueOf(prodate.DAYS_SOLAR[44]) && prodate.SPECIALYSOLARDATE(_today, prodate.MONTH()+1))
	       		holder.lilayout.setBackgroundResource(R.drawable.bgspecialdates);
	       		
        }
        else if(prodate.SPECIALYSOLARDATE(_today, prodate.MONTH()))        
        	holder.lilayout.setBackgroundResource(R.drawable.bgspecialdates);        
       
    	holder.solar.setText(prodate.DAYS_SOLAR[position]);
       
    	// co ngay am dac biet khong    	
        String[] lunarsplit= new String[2];
        lunarsplit=prodate.DAYS_LUNAR[position].split("/");
        int _lunarday= Integer.valueOf(lunarsplit[0]);
        int _lunarmonth= Integer.valueOf(lunarsplit[1]);
        //set nen khac cho ngay am dac biet
        holder.lunar.setText(prodate.DAYS_LUNAR[position]);
        if(prodate.SPECIALLUNARDATE(_lunarday, _lunarmonth))
        	holder.lilayout.setBackgroundResource(R.drawable.bgspecialdates);
        // khi nhan nut chon ngay duong
        if(_position!=-1 && _position==position)
        {
        	holder.solar.setTextColor(R.color.color_today);
        	holder.lunar.setTextColor(R.color.color_today);
        	holder.lilayout.setBackgroundResource(R.drawable.event);
        }
       
        // chon today
        int index1=Integer.valueOf(prodate.DAYS_SOLAR[43]);
        int index2=Integer.valueOf(prodate.DAYS_SOLAR[44]);
        int[]tem=getnextprevious(Integer.valueOf(prodate.DAYS_SOLAR[position]), prodate.MONTH(),prodate.YEAR(), index1, index2, position);
        if(_today==today && thismonth==tem[1]&& thisyear==tem[2])
        {
        	holder.solar.setTextColor(R.color.color_today);
        	holder.lunar.setTextColor(R.color.color_today);
        	holder.lilayout.setBackgroundResource(R.drawable.event);        	
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
   }  
   
}
  