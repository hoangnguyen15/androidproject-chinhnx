package krazevina.com.lunarvn;
import javax.crypto.spec.PSource;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class app_choicetime extends Dialog
{	
	Context context;
	private static final String[] gio1={"1","2","3","4","5","6","7"
										,"8","9","10","11","12","13"
										,"14","15","16","17","18","19"
										,"20","21","22","23","24"};
	private static final String[] gio2={"1 AM","2 AM","3 AM","4 AM","5 AM","6 AM","7 AM"
		,"8 AM","9 AM","10 AM","11 AM","12 AM","1 PM","2 PM","3 PM","4 PM","5 PM","6 PM","7 PM"
		,"8 PM","9 PM","10 PM","11 PM","12 PM"
		};
	private static final String[] phut={"0","5","10","15","20","25","30","35","40","45","50","55"};
	Spinner sstarthour;
	Spinner sendhour;
	Spinner sstartminute;
	Spinner sendminute;
	Spinner styletime;
	CheckBox checkallday;
	private String title;
	private String content;	
	private String date;
	private String ringtone;
	private String reminder;
	private String repeat;
	private String start;
	private String end;
	private int state;
	private Cursor mcursor;
	private String uri;
	public interface ReadyListener 
	{
        public void ready(String name);
    }
	public app_choicetime(Context context) 
    {    	
        super(context);       
        this.context = context;
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
	        setContentView(R.layout.app_time_dialog);
	        
	        sstarthour=(Spinner)findViewById(R.id.spiner_start_hour);
	        sstartminute=(Spinner)findViewById(R.id.spiner_start_minutes);
	        sendhour=(Spinner)findViewById(R.id.spiner_end_hour);
	        sendminute=(Spinner)findViewById(R.id.spiner_end_minutes);
	        styletime=(Spinner)findViewById(R.id.spiner_kieuthoigian);
	        checkallday=(CheckBox)findViewById(R.id.checkallday); 
	        
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	                context, R.array.kieuthoigian, R.layout.spiner_item);	        
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        
	        styletime.setAdapter(adapter);
	        ArrayAdapter<String> adapterphut=new ArrayAdapter<String>(context, R.layout.spiner_item, phut);
	        adapterphut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        sendminute.setAdapter(adapterphut);
	        sstartminute.setAdapter(adapterphut);
	        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(context, R.layout.spiner_item, gio1);
	        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		sstarthour.setAdapter(adapter1);
    		sendhour.setAdapter(adapter1);    		
    		//sstarthour.setOnItemSelectedListener(new sstarthour_select());
	        //styletime.setOnItemSelectedListener(new MyKieuThoiGian_Select());
    		
	        DbAdapter mDb;
	        mDb = new DbAdapter(getContext());
    		mDb.open();    		
        	mcursor= mDb.fetchStaff(global._id);
        	mDb.close();
	        title= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_TITLE));        	
        	content= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_CONTENT));        	
        	date= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_DATE));
        	ringtone= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_RINGTONE));        	
        	state = mcursor.getInt(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_STATE));
        	reminder= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
        	repeat=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_REPEAT));
        	start=mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_START));
        	end=mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_END));
        	uri=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_URI));  
        	String[] starttem=start.split(":");        	
        	String[] endtem=end.split(":");
        	for(int i=0;i<sstarthour.getCount();i++)
        	{
        		if(starttem[0].compareTo(sstarthour.getItemAtPosition(i).toString())==0)        		
        			sstarthour.setSelection(i);        		
        		if(endtem[0].compareTo(sendhour.getItemAtPosition(i).toString())==0)
        			sendhour.setSelection(i);
        	}   
        	for(int i=0;i<sstartminute.getCount();i++)
        	{
        		if(starttem[1].compareTo(sstartminute.getItemAtPosition(i).toString())==0)
        			sstartminute.setSelection(i);
        		if(endtem[1].compareTo(sendminute.getItemAtPosition(i).toString())==0)
        			sendminute.setSelection(i);
        	}
        	
	        Button buttonSET = (Button) findViewById(R.id.set_time);
	        Button buttonCANCEL= (Button) findViewById(R.id.cancel_time);
	        buttonSET.setOnClickListener(new SETListener());
	        buttonCANCEL.setOnClickListener(new CANCELListener());	        
	        checkallday.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if(isChecked)
					{
						sstarthour.setSelection(7);
						sstartminute.setSelection(0);
						sendhour.setSelection(16);
						sendminute.setSelection(0);
					}
				}
			});
	        
        }catch (Exception e) 
        {
        	Log.e("Loi chon thoi gian:::",e.getMessage());
		}   
        
    }
    
    public class sstarthour_select implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) 
        {           	
        	sendhour.setSelection(sstarthour.getSelectedItemPosition());
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    public class sendhour_select implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) 
        {        	
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    public class sstartminutes_select implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) 
        {        	
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    public class sendminutes_select implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) 
        {        	
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    public class MyKieuThoiGian_Select implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) 
        {
        	if(pos==0)
        	{
        		ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, R.layout.spiner_item, gio1);
        		sstarthour.setAdapter(adapter);
        		sendhour.setAdapter(adapter);
        	}
        	else
        	{
        		ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, R.layout.spiner_item, gio2);
        		sstarthour.setAdapter(adapter);
        		sendhour.setAdapter(adapter);
        	}         
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    private class SETListener implements android.view.View.OnClickListener {
       

		@Override
        public void onClick(View v)
        {   
			DbAdapter mDb = new DbAdapter(getContext());
	    	mDb.open();       	
        	try
        	{
        	        	
        	if(mcursor!=null)
        	{ 
        		String batdau= sstarthour.getSelectedItem().toString()+":"+sstartminute.getSelectedItem().toString();
        		String ketthuc=sendhour.getSelectedItem().toString()+":"+sendminute.getSelectedItem().toString();
	        	String kieuthoigian=styletime.getSelectedItem().toString();
	        	mDb.UPDATESTAFF(global._id, title, content, batdau, ketthuc, date, ringtone, state, reminder, repeat,kieuthoigian,uri);	        	
	        	global.ViewTime.txtTime_Describle.setText("Từ: "+batdau+" Đến: "+ketthuc);
	        		        		        	
        	}        	
        	}catch (Exception e) {
				Log.e("appcotent",e.toString());
				Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG);
			}        	
        	app_choicetime.this.dismiss();
        	mDb.close();
        }   
		
    }
    private class CANCELListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            //readyListener.ready(String.valueOf(etName.getText()));
        	app_choicetime.this.dismiss();
        }
    }
}
