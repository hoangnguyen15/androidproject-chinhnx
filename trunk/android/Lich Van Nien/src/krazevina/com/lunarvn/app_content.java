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

public class app_content extends Dialog
{
	EditText mtitle;
	EditText mcontent;
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
	public app_content(Context context) 
    {    	
        super(context);      
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_content_dialog); 
        mtitle =(EditText)findViewById(R.id.tieude);
        mcontent=(EditText)findViewById(R.id.noi_dung);
        // nut set hay nut cancel
        Button buttonSET = (Button) findViewById(R.id.set_content);
        Button buttonCANCEL= (Button) findViewById(R.id.cancel_content);
        
        buttonSET.setOnClickListener(new SETListener());
        buttonCANCEL.setOnClickListener(new CANCELListener()); 
        
	        try
	        {
	        	DbAdapter mDb;
	        	mDb = new DbAdapter(getContext());
	    		mDb.open();    		
	        	Cursor mcursor= mDb.fetchStaff(global._id);
	        	mDb.close();
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
	        	mtitle.setText(title);
	        	mcontent.setText(content);
		        
	        
        }catch (Exception e) 
        {
        	Log.e("APP_CONTENT ERROR", e.getMessage());
		}
        
    }
    private class OnEditTextClick implements android.view.View.OnClickListener 
    {
    	@Override 
    	public void onClick(View v)
    	{}
    }  
    private class SETListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	try
        	{
	            //readyListener.ready(String.valueOf(etName.getText()));
	        	title =mtitle.getText().toString();
	        	content=mcontent.getText().toString();
	        	DbAdapter mDb;
	        	mDb = new DbAdapter(getContext());
	        	mDb.open();
	        	mDb.UPDATESTAFF(global._id, title, content, start, end, date, ringtone, state, reminder, repeat,style,uri);
	        	mDb.close();
	        	global.ViewContent.content_describle.setText(mtitle.getText().toString());	        	
        	}catch (Exception e) {
				Log.e("appcotent",e.toString());
			}
        	app_content.this.dismiss();         
        }   
        
    }
    private class CANCELListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
        	app_content.this.dismiss();
        }
    }
}