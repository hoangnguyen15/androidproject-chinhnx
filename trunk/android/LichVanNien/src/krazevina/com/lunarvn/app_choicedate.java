package krazevina.com.lunarvn;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class app_choicedate extends Dialog
{
	public EditText mdate;
	public EditText mmonth;
	public EditText myear;
	int dd=0;
	int mm=0;
	int yyyy=0;
	utils lunarcalendar;
	private Cursor mcursor;
	private String title;
	private String content;
	private String start;
	private String end;
	private String date;
	private String ringtone;
	private String reminder;
	private String repeat;
	private String style;
	private int state;
	private String uri;
	public interface ReadyListener 
	{
        public void ready(String name);
    }
	public app_choicedate(Context context) 
    {    	
        super(context);
        DbAdapter mDb= new DbAdapter(context);
        mDb.open();
        Cursor mCursor= mDb.fetchStaff(global._id);
        String date=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_DATE));
        String[] datetem=date.split("/");
        yyyy=Integer.valueOf(datetem[2]);
        mm=Integer.valueOf(datetem[1]);
        dd=Integer.valueOf(datetem[0]);  
        mDb.close();
    	lunarcalendar= new utils(); 
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_solar);
        try
        {
        //capture edit text
        mdate=(EditText)findViewById(R.id.solar_date);
        mmonth=(EditText)findViewById(R.id.solar_month);
        myear=(EditText)findViewById(R.id.solar_year);
        
    	mdate.setText(String.valueOf(dd));
    	mmonth.setText(String.valueOf(mm));
    	myear.setText(String.valueOf(yyyy));
    	DbAdapter mDb;
    	mDb = new DbAdapter(getContext());
		mDb.open();    		
    	mcursor= mDb.fetchStaff(global._id);    
        // nut set hay nut cancel
        Button buttonSET = (Button) findViewById(R.id.set);
        Button buttonCANCEL= (Button) findViewById(R.id.cancel);
        buttonSET.setOnClickListener(new SETListener());
        buttonCANCEL.setOnClickListener(new CANCELListener());
        
        if(mcursor!=null)
        {
	    	title= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_TITLE));
	    	content= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_CONTENT));
	    	start = mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_START));
	    	end= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_END));
	    	date= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_DATE));
	    	ringtone= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_RINGTONE));
	    	state = mcursor.getInt(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_STATE));
	    	reminder= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
	    	repeat=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_REPEAT));
	    	style=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_TIMESTYLE));
	    	uri=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_URI));
	    	//dien vao dialog date
	    	String []datetem= date.split("/");
	    	mdate.setText(datetem[0]);
	    	mmonth.setText(datetem[1]);
	    	myear.setText(datetem[2]);
        }
        
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
        mDb.close();
        }catch (Exception e) {
			Log.e("Loi appchoidate ,,,", e.toString());
		}
        
    }
    private class SETListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	
            //readyListener.ready(String.valueOf(etName.getText()));
        	DbAdapter mDb;
        	app_choicedate.this.dismiss();   
        	mDb = new DbAdapter(getContext());
    		mDb.open();   
        	dd= Integer.valueOf(mdate.getText().toString());
        	mm= Integer.valueOf(mmonth.getText().toString());
        	yyyy=Integer.valueOf(myear.getText().toString());
        	try
        	{        	
	        	
	        		
		        	date=mdate.getText().toString()+"/"+mmonth.getText().toString()+"/"+myear.getText().toString();
		        	mDb.UPDATESTAFF(global._id, title, content, start, end, date, ringtone, state, reminder, repeat,style,uri);
		        	global.ViewDate.txtDate_Describle.setText(date);        		        		        	
	        
        	
        	}catch (Exception e) {
				Log.e("appcotent",e.toString());
				Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG);
			}        	
        	mDb.close();
        }   
    }
    private class CANCELListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            //readyListener.ready(String.valueOf(etName.getText()));
        	app_choicedate.this.dismiss();
        }
    }
    private class DATEMINUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {        	
        	int dayofmonth=utils.getDayOfMonth(yyyy,mm);
        	if(dd>1)
        	{
        		dd--;        		
        		mdate.setText(String.valueOf(dd));
        	}
        	else
        	{
        		dd=dayofmonth;        		
        		mdate.setText(String.valueOf(dd));
        	}
        }
    }
    private class MONTHMINUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	int dayofmonth;        	
        	if(mm>1)        		
        		mm--;        	
        	else mm=12;
        	
        	mmonth.setText(String.valueOf(mm));
    		dayofmonth=utils.getDayOfMonth(yyyy, mm);
    		int tem= Integer.valueOf(mdate.getText().toString());
    		if(tem>dayofmonth)
    			mdate.setText(String.valueOf(dayofmonth));
        }
    }
    private class YEARMINUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	if(yyyy>1)
        	{
        		yyyy--;
        		myear.setText(String.valueOf(yyyy));
        	}
        }
    }
    private class DATEPLUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	int dayofmonth=utils.getDayOfMonth(yyyy,mm);
        	if(dd<dayofmonth)
        	{
        		dd++;
        		mdate.setText(String.valueOf(dd));
        	}
        	else
        	{
        		dd=1;
        		mdate.setText(String.valueOf(dd));
        	}
        }
    }
    private class MONTHPLUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	int dayofmonth;
        	if(mm<12)
        	{
        		mm++;        		
        	}
        	else
        	{
        		mm=1;
        	}
        	mmonth.setText(String.valueOf(mm));
    		dayofmonth=utils.getDayOfMonth(yyyy, mm);
    		int tem= Integer.valueOf(mdate.getText().toString());
    		if(tem>dayofmonth)
    			mdate.setText(String.valueOf(dayofmonth));
        }
    }
    private class YEARPLUS implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	yyyy++;
        	myear.setText(String.valueOf(yyyy));
        }
    }
}
