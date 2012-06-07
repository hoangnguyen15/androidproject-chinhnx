package krazevina.com.lunarvn;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
public class app_reminder extends Dialog
{
	Context mcontext;
	Spinner spinernhac;
	Spinner spinerlap;
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
	public app_reminder(Context context) 
    {    	
        super(context);
        this.mcontext=context;
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_reminder_dialog);        
    
        Button buttonSET = (Button) findViewById(R.id.set_reminder);
        Button buttonCANCEL= (Button) findViewById(R.id.cancel_reminder);
        
        spinernhac= (Spinner)findViewById(R.id.spiner_nhac);        
        //spinernhac.setBackgroundResource(R.drawable.button_dong);        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		this.mcontext, R.array.kieunhacnho, android.R.layout.simple_spinner_item);	        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinernhac.setAdapter(adapter);
        
        spinerlap=(Spinner)findViewById(R.id.spiner_lap);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
        		this.mcontext, R.array.cachlap, android.R.layout.simple_spinner_item);	        
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerlap.setAdapter(adapter1);
        DbAdapter mDb;
        mDb = new DbAdapter(getContext());
		mDb.open();    		
    	Cursor mcursor= mDb.fetchStaff(global._id);
    	mDb.close();
    	date="";
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
    	}
    	int position=0;
    	for(int i=0;i<spinernhac.getCount();i++)
    	{
    		if(reminder.compareTo(spinernhac.getItemAtPosition(i).toString())==0)
    			position=i;
    	}
    	spinernhac.setSelection(position);
    	for(int i=0;i<spinerlap.getCount();i++)
    	{
    		if(repeat.compareTo(spinerlap.getItemAtPosition(i).toString())==0)
    			position=i;
    	}
    	spinerlap.setSelection(position);
        buttonSET.setOnClickListener(new SETListener());
        buttonCANCEL.setOnClickListener(new CANCELListener());       
       
      
       
    }
    private class SETListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	
            //readyListener.ready(String.valueOf(etName.getText()));
        	
        	try
        	{       	
	        	
	        	reminder= spinernhac.getSelectedItem().toString();
	        	repeat=spinerlap.getSelectedItem().toString();
	        	DbAdapter mDb;
	        	mDb = new DbAdapter(getContext());
	         	mDb.open();
	        	mDb.UPDATESTAFF(global._id, title, content, start, end, date, ringtone, state, reminder, repeat,style,uri);
	        	mDb.close();
	        	global.ViewRemider.txtReminder_Describle.setText("Nhắc trước: "+reminder+"Lặp lại: "+ repeat);	        	
        	
        	}catch (Exception e) {
				Log.e("appcotent",e.toString());
				Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG);
			}        	
        	String reminder="Nhắc trước: "+spinernhac.getSelectedItem().toString()+" Phút --";
        	reminder+= "Lặp lại: "+spinerlap.getSelectedItem().toString();
        	global.ViewRemider.txtReminder_Describle.setText(reminder);
        	app_reminder.this.dismiss();       	
        
        }   
    }
    private class CANCELListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            //readyListener.ready(String.valueOf(etName.getText()));
        	app_reminder.this.dismiss();
        }
    }   
}
