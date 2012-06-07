package krazevina.com.lunarvn;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class MyCustomDialog_Picker_Solar extends Dialog
{
	public EditText mdate;
	public EditText mmonth;
	public EditText myear;
	utils lunarcalendar;	
	public interface ReadyListener 
	{
        public void ready(String name);
    }
	public MyCustomDialog_Picker_Solar(Context context) 
    {    	
        super(context);
    	lunarcalendar= new utils(); 
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_solar);
        
        //capture edit text
        mdate=(EditText)findViewById(R.id.solar_date);
        mmonth=(EditText)findViewById(R.id.solar_month);
        myear=(EditText)findViewById(R.id.solar_year);
        
    	mdate.setText(String.valueOf(global.dd));
    	mmonth.setText(String.valueOf(global.mm));
    	myear.setText(String.valueOf(global.yyyy));

        // nut set hay nut cancel
        Button buttonSET = (Button) findViewById(R.id.set);
        Button buttonCANCEL= (Button) findViewById(R.id.cancel);
        buttonSET.setOnClickListener(new SETListener());
        buttonCANCEL.setOnClickListener(new CANCELListener());
        
       
        
        //capture button
        Button dateplus=(Button)findViewById(R.id.dateplus);
        Button monthplus=(Button)findViewById(R.id.monthplus);
        Button yearplus=(Button)findViewById(R.id.yearplus);
        Button dateminus=(Button)findViewById(R.id.dateminus);
        Button monthminus=(Button)findViewById(R.id.monthminus);
        Button yearminus=(Button)findViewById(R.id.yearminus);
        
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
            //readyListener.ready(String.valueOf(etName.getText()));
        	MyCustomDialog_Picker_Solar.this.dismiss();        	
        	global.dd= Integer.valueOf(mdate.getText().toString());
        	global.mm= Integer.valueOf(mmonth.getText().toString());
        	global.yyyy=Integer.valueOf(myear.getText().toString());
        	  
        	global.txtviewadapter= new textviewadapter(global.context,global.dd,global.mm,global.yyyy);
        	 //dieu chinh hien thi gridview
//     	   if(global.txtviewadapter.prodate.countcells>35)     		
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
        	
        	global.title = "Tháng "+String.valueOf(global.mm)+" Năm "+ String.valueOf(global.yyyy);
            global.TextViewTilte.setText(global.title);
        }   
    }
    private class CANCELListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            //readyListener.ready(String.valueOf(etName.getText()));
        	MyCustomDialog_Picker_Solar.this.dismiss();
        }
    }
    private class DATEMINUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {    
        	int dayofmonth=utils.getDayOfMonth(global.yyyy,global.mm);
        	if(global.dd>1)
        	{
        		global.dd--;        		
        		mdate.setText(String.valueOf(global.dd));
        	}
        	else
        	{
        		global.dd=dayofmonth;        		
        		mdate.setText(String.valueOf(global.dd));
        	}
        }
    }
    private class MONTHMINUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	int dayofmonth;        	
        	if(global.mm>1)
        	{        		
        		global.mm--;
        		mmonth.setText(String.valueOf(global.mm));        		       		
        	}
        	else
        	{
        		global.mm=12;
        		mmonth.setText(String.valueOf(global.mm));
        	}
        	dayofmonth=utils.getDayOfMonth(global.yyyy, global.mm);
    		int tem= Integer.valueOf(mdate.getText().toString());
    		if(tem>dayofmonth)
    			mdate.setText(String.valueOf(dayofmonth)); 
        }
    }
    private class YEARMINUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	if(global.yyyy>1)
        	{
        		global.yyyy--;
        		myear.setText(String.valueOf(global.yyyy));
        	}
        }
    }
    private class DATEPLUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	int dayofmonth=utils.getDayOfMonth(global.yyyy,global.mm);
        	if(global.dd<dayofmonth)
        	{
        		global.dd++;
        		mdate.setText(String.valueOf(global.dd));
        	}
        	else
        	{
        		global.dd=1;
        		mdate.setText(String.valueOf(global.dd));
        	}
        }
    }
    private class MONTHPLUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	int dayofmonth;
        	if(global.mm<12)
        	{
        		global.mm++;
        		mmonth.setText(String.valueOf(global.mm));        	
        	}
        	else
        	{
        		global.mm=1;
        		mmonth.setText(String.valueOf(global.mm));      		
        	}
        	dayofmonth=utils.getDayOfMonth(global.yyyy, global.mm);
    		int tem= Integer.valueOf(mdate.getText().toString());
    		if(tem>dayofmonth)
    			mdate.setText(String.valueOf(dayofmonth));
        }
    }
    private class YEARPLUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	global.yyyy++;
        	myear.setText(String.valueOf(global.yyyy));
        }
    }
}
