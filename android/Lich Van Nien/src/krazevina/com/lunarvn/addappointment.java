package krazevina.com.lunarvn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class addappointment extends Activity
{
	//ringtone
	int requestCode = 123;
	TextView tV;
	Button bT;
	String uriRingType;
	RingtoneManager rM;
	Uri currentRingtoneUri;
	//end ringtone
	
	ListView listview;
	ListAdapter listadapter;
	
	global.ViewContent vcontent;
    global.ViewChoice vchoice;
    global.ViewTime vtime;
    global.ViewDate vdate;
    global.ViewRingtone vringtone;
    global.ViewRemider vreminder;
    global.ViewXOA_OK vxoa_ok;
    private long mRowId;
    //Bundle extras;  
    ToggleButton monoff;
	private Cursor mcursor;
	private String content;
	private String title;
	private String ringtone;
	private String repeat;
	private String reminder;
	private String date;
	private String start;
	private int state;
	private String end;
	private String style;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		// extras = getIntent().getExtras();
		 
		try
		{
			setContentView(R.layout.addappointment_xml);
			listview=(ListView)findViewById(R.id.listview_addpoint);
			listadapter= new ListAdapter(this);
			listview.setAdapter(listadapter);			
			
		listview.setOnItemClickListener(new OnItemClickListener() {
    	    public void onItemClick(AdapterView<?> parent, View view,
    	        int position, long id)
    	    {
    	    	switch (position)
    	    	{
    	    	
				case 0:	//on/of			
					
					break;
				case 1:		//content	
					//Toast.makeText(addappointment.this, "chao em", Toast.LENGTH_LONG).show();
					app_content content= new app_content(addappointment.this);
					content.show();
					//app_baothuc appbaothuc= new app_baothuc(addappointment.this);
					//appbaothuc.show();
					break;
				case 2://date
					
					app_choicedate choicedate= new app_choicedate(addappointment.this);
					choicedate.show();	
					break;
				case 3://time		
					app_choicetime choicetime= new app_choicetime(addappointment.this);
					choicetime.show();	
								
					break;
				case 4://ringtone
					Intent i = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
					i.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Chọn nhạc chuông");
					i.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);					
					//String uri = null;
					if (uriRingType != null) {
						i.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,Uri.parse(uriRingType));
					}
					else {
						i.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,(Uri)null);
					}
					startActivityForResult(i, requestCode);
					
					break;
				case 5://reminder					
					try
					{
					app_reminder appreminder = new app_reminder(addappointment.this);
					appreminder.show();
					}catch (Exception e) {
						Log.e("Loi day nua nay:::", e.toString());
						Toast.makeText(addappointment.this, e.toString(), Toast.LENGTH_LONG);
					}
					break;
				case 6://
					break;
				default:
					break;
				}
    	     
    	    }
    	  });		
		}
		catch(Exception e)
		{
			Log.e("loi day nay:::: ", e.toString());
		}
	}	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) 
	    {	       
	        Bundle bundle = new Bundle();                  
            Intent mIntent = new Intent();
            mIntent.putExtras(bundle);
            setResult(RESULT_OK, mIntent);                
            finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	System.out.println("-------------Get result");
    	if (resultCode == this.RESULT_OK) {
    		Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
    		if (uri != null) {
    			String ringType = uri.toString();
    			System.out.println("----------------ring type is " + ringType);
    			uriRingType = ringType;
    			Ringtone currentRingtone = rM.getRingtone(this, uri);
    			currentRingtoneUri = uri;   
    			DbAdapter mDb;
    			mDb = new DbAdapter(addappointment.this);
        		mDb.open();    		
            	mcursor= mDb.fetchStaff(global._id);
    	        title= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_TITLE));        	
            	content= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_CONTENT));        	
            	date= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_DATE));
            	ringtone= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_RINGTONE));        	
            	state = mcursor.getInt(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_STATE));
            	reminder= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
            	repeat=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_REPEAT));
            	start = mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_START));
            	end=mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_END));
            	style=mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_TIMESTYLE));
            	//uri1=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_URI));
            	//uri1=currentRingtone.toString();
            	Log.e("ringtone uri", ringType);
            	ringtone=currentRingtone.getTitle(addappointment.this);            	
            	mDb.UPDATESTAFF(global._id, title, content, start, end, date, ringtone, state, reminder, repeat,style,ringType);
            	mDb.close(); 
            	global.ViewRingtone.txtRingtone_Describle.setText(ringtone);            	
    		}
    		else {
    			tV.setText("Silent");
    			uriRingType = null;
    		}
    	}
    	else {
    		System.out.println("---------------Failed");
    	}
    }
	public class ListAdapter extends BaseAdapter 
	{
	    private LayoutInflater mInflater;
	    public ListAdapter(Context context)
	    {
	    	mInflater = LayoutInflater.from(context);
	    }
	    public int getCount() 
	    {
	    	return 7;
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
	    	Bundle extras = getIntent().getExtras();	        
			if(extras!=null)
			{
				
				mRowId= extras.getLong(DbAdapter.KEY_ROWID);
				title= extras.getString(DbAdapter.KEY_TITLE);
				content=extras.getString(DbAdapter.KEY_CONTENT);
				start =extras.getString(DbAdapter.KEY_START);
				end=extras.getString(DbAdapter.KEY_END);				
				date=extras.getString(DbAdapter.KEY_DATE);
				reminder=extras.getString(DbAdapter.KEY_REMINDER);
				reminder+=extras.getString(DbAdapter.KEY_REPEAT);					
				ringtone=extras.getString(DbAdapter.KEY_RINGTONE);
				state=extras.getInt(DbAdapter.KEY_STATE);
			}
			else
			{
				DbAdapter mDb;
				mDb=new DbAdapter(addappointment.this);
				mDb.open();
				Cursor mCursor=mDb.fetchStaff(global._id);
				if(mCursor!=null)
				{
					//mRowId= extras.getLong(DbAdapter.KEY_ROWID);
					mRowId=global._id;
					title= mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_TITLE));
					content=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_CONTENT));
					start =mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_START));
					end=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_END));				
					date=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_DATE));
					reminder=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
					reminder+=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_REPEAT));					
					ringtone=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_RINGTONE));
					state=mCursor.getInt(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_STATE));
				}
			}
    		if(position==0)
        	{
    			vchoice= new global.ViewChoice();
        		convertView = mInflater.inflate(R.layout.app_choice, null);
        		vchoice.Tog_ONOFF=(ToggleButton)convertView.findViewById(R.id.app_choice_togbt);
    			if(state==0)
				{					
					vchoice.Tog_ONOFF.setChecked(false);
				}
				else
				{					
					vchoice.Tog_ONOFF.setChecked(true);
				}
        		
	        	convertView.setTag(vchoice);
	        	
        	}
        	if(position==1)
        	{
        		vcontent = new global.ViewContent();
        		convertView = mInflater.inflate(R.layout.app_title_content, null);	        		
	        	vcontent.content_describle=(TextView)convertView.findViewById(R.id.content_describle);
	        	vcontent.content_describle.setText(title);
	        	convertView.setTag(vcontent); 
        	}
        	if(position==2)
        	{        		
	        	convertView = mInflater.inflate(R.layout.app_date, null);
        		vdate = new global.ViewDate();	        		        	
        		vdate.txtDate_Describle=(TextView)convertView.findViewById(R.id.date_describle);
        		vdate.txtDate_Describle.setText(date);	
	        	convertView.setTag(vdate); 
        	}
        	if(position==3)
        	{
        		convertView = mInflater.inflate(R.layout.app_time, null);
        		vtime = new global.ViewTime();		        	        	
	        	vtime.txtTime_Describle=(TextView)convertView.findViewById(R.id.time_describle);        	
	        	vtime.txtTime_Describle.setText("từ "+ start+" đến "+end);		
	        	convertView.setTag(vtime);        		
        	}
        	if(position==4)
        	{
        		convertView = mInflater.inflate(R.layout.app_ringtone, null);
        		vringtone = new global.ViewRingtone();	        		        	
        		vringtone.txtRingtone_Describle=(TextView)convertView.findViewById(R.id.ringtone_describle);
        		vringtone.txtRingtone_Describle.setText(ringtone);
	        	convertView.setTag(vringtone); 
        	}
        	if(position==5)
        	{
        		convertView = mInflater.inflate(R.layout.app_reminder, null);
        		vreminder = new global.ViewRemider();	        		        	
        		vreminder.txtReminder_Describle=(TextView)convertView.findViewById(R.id.reminder_describle);
        		vreminder.txtReminder_Describle.setText(reminder);
	        	convertView.setTag(vreminder);
        	}
        	if(position==6)
        	{
        		convertView = mInflater.inflate(R.layout.app_xoa_ok, null);
        		vxoa_ok = new global.ViewXOA_OK();	        		        	
        		vxoa_ok.bt_set=(Button)convertView.findViewById(R.id.set_xoa_ok);
        		vxoa_ok.bt_xoa=(Button)convertView.findViewById(R.id.cancel_xoa_ok);
        		vxoa_ok.bt_set.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					Bundle bundle = new Bundle();                  
    			            Intent mIntent = new Intent();
    			            mIntent.putExtras(bundle);
    			            setResult(RESULT_OK, mIntent);                
    			            finish();
    					
    				}
    			});
            	vxoa_ok.bt_xoa.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					DbAdapter mDb;
    					mDb = new DbAdapter(addappointment.this);
    					mDb.open();
    			    	mDb.DELETESTAFF(global._id);
    			    	mDb.close();
    					Bundle bundle = new Bundle();                  
    		            Intent mIntent = new Intent();
    		            mIntent.putExtras(bundle);
    		            setResult(RESULT_OK, mIntent);                
    		            finish();
    					
    				}
    			});
	        	convertView.setTag(vxoa_ok);
        	}
        	//lay du lieu tu mot edit da chon/*
      
	    	vchoice.Tog_ONOFF.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
				{
					
					try
					{
					// TODO Auto-generated method stub
					DbAdapter mDb;
					mDb = new DbAdapter(addappointment.this);
					mDb.open();    		
			    	Cursor mcursor= mDb.fetchStaff(global._id);
			    	String date="";
					String title= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_TITLE));
			    	String content= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_CONTENT));
			    	String start = mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_START));
			    	String end= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_END));
			    	date= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_DATE));
			    	String ringtone= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_RINGTONE));
//			    	int state = mcursor.getInt(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_STATE));
			    	String reminder= mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
			    	String repeat=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_REPEAT));
			    	String style=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_TIMESTYLE));
			    	String uri=  mcursor.getString(mcursor.getColumnIndexOrThrow(DbAdapter.KEY_URI));
			    	if(isChecked)				    	
			    		mDb.UPDATESTAFF(mRowId, title, content, start, end, date, ringtone, 1, reminder, repeat,style,uri);			    	
			    	else			    
			    		mDb.UPDATESTAFF(mRowId, title, content, start, end, date, ringtone, 0, reminder, repeat,style,uri);
			    	mDb.close();
					}catch (Exception e) {
						Log.e("Tat mo co loi nay", "day co loi");						
					}		
					
				}
			});		    	
	        return convertView;
	    }
	}
}
