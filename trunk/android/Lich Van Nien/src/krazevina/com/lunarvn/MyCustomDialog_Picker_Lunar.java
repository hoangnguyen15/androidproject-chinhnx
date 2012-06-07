package krazevina.com.lunarvn;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class MyCustomDialog_Picker_Lunar extends Dialog
{
	public EditText mdate;
	public EditText mmonth;
	public EditText myear;
	utils lunarcalendar;	
	public static boolean checkIntNumber(String sNumber) 
    {    	
		try 
		{
			Integer.parseInt(sNumber);
			return true;
		} catch (Exception e) 
		{
			return false;
		}
    }
    public MyCustomDialog_Picker_Lunar(Context context) 
    {    	
        super(context);
    	lunarcalendar= new utils(); 
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_lunar);
        
        //capture edit text
        mdate=(EditText)findViewById(R.id.lunar_date);
        mmonth=(EditText)findViewById(R.id.lunar_month);
        myear=(EditText)findViewById(R.id.lunar_year);
        
    	mdate.setText(String.valueOf(global.lunar_dd));
    	mmonth.setText(global.lunar_mm);
    	myear.setText(String.valueOf(global.lunar_yyyy));

        // nut set hay nut cancel
        Button buttonSET = (Button) findViewById(R.id.lunar_set);
        Button buttonCANCEL= (Button) findViewById(R.id.lunar_cancel);
        buttonSET.setOnClickListener(new SETListener());
        buttonCANCEL.setOnClickListener(new CANCELListener());             
        
        //capture button
        Button dateplus=(Button)findViewById(R.id.lunar_dateplus);
        Button monthplus=(Button)findViewById(R.id.lunar_monthplus);
        Button yearplus=(Button)findViewById(R.id.lunar_yearplus);
        Button dateminus=(Button)findViewById(R.id.lunar_dateminus);
        Button monthminus=(Button)findViewById(R.id.lunar_monthminus);
        Button yearminus=(Button)findViewById(R.id.lunar_yearminus);
        
        dateplus.setOnClickListener(new DATEPLUS());
        monthplus.setOnClickListener(new MONTHPLUS());
        yearplus.setOnClickListener(new YEARPLUS());
        
        dateminus.setOnClickListener(new DATEMINUS());
        monthminus.setOnClickListener(new MONTHMINUS());
        yearminus.setOnClickListener(new YEARMINUS());        
    }    
    private class SETListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	int[] solar;
            //readyListener.ready(String.valueOf(etName.getText()));
        	try
        	{
        	MyCustomDialog_Picker_Lunar.this.dismiss();        	
        	global.lunar_dd = Integer.valueOf(mdate.getText().toString());
        	global.lunar_mm = mmonth.getText().toString();
        	global.lunar_yyyy =Integer.valueOf(myear.getText().toString());
        	textviewadapter txtviewadapter=new textviewadapter(global.context);       	
        	
        	String tem1= mmonth.getText().toString();
        	int temmonth;
        	if(checkIntNumber(tem1))
        		temmonth= Integer.valueOf(tem1);        		
        	else
        		temmonth= Integer.valueOf(tem1.substring(0, tem1.length()-1));
        	
        	int temdate= Integer.valueOf(mdate.getText().toString());
        	int temyear= Integer.valueOf(myear.getText().toString());
        	
        	if(checkIntNumber(tem1))//neu khong la leapmonth
        		solar= global.txtviewadapter.prodate.CONVERTLUNARTOSOLAR(temdate, temmonth, temyear,0);
        	else//doi thang nhuan ra duong lich
        		solar= global.txtviewadapter.prodate.CONVERTLUNARTOSOLAR(temdate, temmonth, temyear,1);
        	
        	global.dd=solar[0];
        	global.mm=solar[1];
        	global.yyyy=solar[2];
        	global.txtviewadapter= new textviewadapter(global.context, solar[0], solar[1], solar[2]);
        	
//        	if(global.txtviewadapter.prodate.countcells>35)     		
//     			global.gridview.setPadding(1, 0, 1, 0);     		
//     		else
//     			global.gridview.setPadding(1, 20, 1, 0);
//                 	   	
//        	global.gridview.setAdapter(global.txtviewadapter);
        	
        	GridView currentGridView = (GridView) global.viewFlipper.getCurrentView();
        	if(global.txtviewadapter.prodate.countcells>35)   {  		
        		currentGridView.setPadding(1, 0, 1, 0);    
        	}
     		else {
     			currentGridView.setPadding(1, 20, 1, 0);
     		}     	   
        	currentGridView.setAdapter(global.txtviewadapter);
        	
        	global.title = "Tháng "+String.valueOf(solar[1])+" Năm "+ String.valueOf(solar[2]);
            global.TextViewTilte.setText(global.title);        	
        	}
        	catch (Exception e) 
        	{
        		Log.e("Loi", "Loi o day  "+e.getMessage());
			}
        }           

    }
    private class CANCELListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            //readyListener.ready(String.valueOf(etName.getText()));
        	MyCustomDialog_Picker_Lunar.this.dismiss();
        }
    }
    private class DATEMINUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	if(global.lunar_dd>1)
        	{
        		global.lunar_dd--;        		
        		mdate.setText(String.valueOf(global.lunar_dd));
        	}
        }
    }
    private class MONTHMINUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {        	  	
        	
        	if(global.index>0)
        	{
        		global.index--;
        		mmonth.setText(global.count_lunar[global.index]);
        		
        	}
        }
    }
    private class YEARMINUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {        	
			global.lunar_yyyy--;
        	int leapmonth=utils.getLeapMonth(global.lunar_yyyy, utils.TIME_ZONE);
        	int count;
        	int next=0;
        	if(leapmonth==-1)
        		count=12;
        	else
        		count=13;
    		global.count_lunar= new String[count];
    		for(int i=0;i<=count-1;i++)
    		{
    			if(i!=leapmonth-1)
    			{
    				global.count_lunar[i]=String.valueOf(next+1);
    				next++;    				
    			}
    			else
    			{
    				global.count_lunar[i]=String.valueOf(next+1);
    				i++;
    				global.count_lunar[i]=String.valueOf(next+1)+"+";
    				next++;
    				
    			}
    		}
    		myear.setText(String.valueOf(global.lunar_yyyy));   
        }
    }
    private class DATEPLUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	if(global.lunar_dd<31)
        	{
        		global.lunar_dd++;        	
        		mdate.setText(String.valueOf(global.lunar_dd));
        	}
        }
    }
    private class MONTHPLUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {     
        	if(global.index<global.count_lunar.length-1)
        	{
        		global.index++;
        		mmonth.setText(global.count_lunar[global.index]);        		
        	}
        }
    }
    private class YEARPLUS implements android.view.View.OnClickListener {
        
        
		@Override
        public void onClick(View v)
        {			
			global.lunar_yyyy++;
        	int leapmonth=utils.getLeapMonth(global.lunar_yyyy, utils.TIME_ZONE);   		
    		
    		int count;
        	if(leapmonth==-1)
        		count=12;
        	else
        		count=13;
    		global.count_lunar= new String[count];
    		int next=0;
    		for(int i=0;i<=count-1;i++)
    		{
    			if(i!=leapmonth-1)
    			{
    				global.count_lunar[i]=String.valueOf(next+1);
    				next++;    				
    			}
    			else
    			{
    				global.count_lunar[i]=String.valueOf(next+1);
    				i++;
    				global.count_lunar[i]=String.valueOf(next+1)+"+";
    				next++;    				
    			}
    		}
    		myear.setText(String.valueOf(global.lunar_yyyy));    		
        }
    }
}
