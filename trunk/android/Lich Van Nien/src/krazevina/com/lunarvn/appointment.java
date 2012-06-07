package krazevina.com.lunarvn;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class appointment extends Activity
{private static final int ACTIVITY_CREATE=0;
private static final int ACTIVITY_EDIT=2;

private static final int MENU_INSERT=0;
private static final int MENU_DELETE=1;
private static final int NOTIFICATION=321;

private Cursor mCursor;
private ListAdapteradd listadapter;
ViewAppointment vapp;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	try{
		setContentView(R.layout.appointments);
		global.listview=(ListView)findViewById(R.id.listview_appointment);
		Filldata();		
        vapp = new ViewAppointment();
		global.listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			  public boolean onItemLongClick(AdapterView<?> parent, View view,
		    	        int position, long id)
			  {				
				  app_delete delete= new app_delete(appointment.this);
				  delete.position(position);
				  delete.show();
				  return true;
			  }
			
		});
		global.listview.setOnItemClickListener(new OnItemClickListener() {
	    	    public void onItemClick(AdapterView<?> parent, View view,
	    	        int position, long id)
	    	    {	    	    	
	    	    	Cursor c = mCursor;
		    	    c.moveToPosition(position);
		    	    Intent i = new Intent();
		    	    i.setClass(getApplicationContext(), addappointment.class);    	    	    
		    	    global._id = c.getInt(0) ;
		    	   // global._id=position;
		    	    i.putExtra(DbAdapter.KEY_ROWID, global._id);
		    	    i.putExtra(DbAdapter.KEY_TITLE, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_TITLE)));
		    	    
		    	    i.putExtra(DbAdapter.KEY_CONTENT, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_CONTENT)));
		    	    i.putExtra(DbAdapter.KEY_START, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_START)));
		    	    i.putExtra(DbAdapter.KEY_END, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_END)));
		    	    i.putExtra(DbAdapter.KEY_DATE, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_DATE)));
		    	    i.putExtra(DbAdapter.KEY_RINGTONE, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_RINGTONE)));
		    	    i.putExtra(DbAdapter.KEY_STATE, c.getInt(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_STATE)));
		    	    i.putExtra(DbAdapter.KEY_REMINDER, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER)));
		    	    i.putExtra(DbAdapter.KEY_REPEAT, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_REPEAT)));
		    	    i.putExtra(DbAdapter.KEY_TIMESTYLE, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_TIMESTYLE)));
		    	    i.putExtra(DbAdapter.KEY_URI, c.getString(
		    	    		c.getColumnIndexOrThrow(DbAdapter.KEY_URI)));
		    	    
		    	    startActivityForResult(i, ACTIVITY_EDIT);  
		    	    
	    	    }
	    	  });
	}catch (Exception e) {
		Log.e("appoointdfadfa", e.toString());
	}
	registerForContextMenu(global.listview);
		}
@Override
public boolean onCreateOptionsMenu(Menu menu) 
{
	 menu.add(0,MENU_INSERT,0,"Thêm cuộc hẹn").setIcon(R.drawable.menu_datlich);
	 menu.add(0,MENU_DELETE,0,"Xóa tất cả").setIcon(R.drawable.menu_xoalich);
	 return true;
}
@Override
public boolean onOptionsItemSelected(MenuItem item) 
{
	Intent myintent;
	DbAdapter mDb;
	switch (item.getItemId())
	{
		
		case MENU_DELETE:
			
        	mDb= new DbAdapter(appointment.this);
        	mDb.open();
        	mDb.DELETEALLSTAFF();        	
        	mDb.close();
        	if(global.checknotification==true)
    		{
        		global.mNotificationManager.cancel(321);
        		global.checknotification=false;
    		}
        	Filldata();
			break;
		case MENU_INSERT:	
			try
			{
			    Calendar c = Calendar.getInstance();
			   int yyyy = c.get(Calendar.YEAR);        
			   int mm = c.get(Calendar.MONTH)+1;
			   int dd = c.get(Calendar.DATE); 
			    String date=String.valueOf(dd)+"/"+String.valueOf(mm)+"/"+String.valueOf(yyyy);			    
				mDb=new DbAdapter(appointment.this);
				mDb.open();
				global._id = (long)mDb.INSERTSTAFF("Tieu De", "Noi Dung", "0:0", "24:0", date, "Beat Plucker", 1, "0", "Một Lần","24H","content://media/internal/audio/media/16");
				mDb.close();
				myintent = new Intent(this, addappointment.class);
				startActivityForResult(myintent, ACTIVITY_CREATE);
			}catch (Exception e) {
				Log.e("Insert have problem", e.toString());
			}
			return true;
		default:
			break;
		}
	return false;
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
{
    super.onActivityResult(requestCode, resultCode, intent);
    DbAdapter mDb;
    switch(requestCode)
    {
        case ACTIVITY_CREATE:
        	mDb= new DbAdapter(appointment.this);
        	mDb.open();
        	try
        	{        	
	        	global.datetime=mDb.GetDateFromDatabase();        	
	        	global.index_alarm=0;
	        	if(global.datetime[0][0]!=-1)//tao ra status bar notification
	        	{        
	        		Log.e(" ACTIVITY_EDIT datetime[0][0]::", "!= -1");
	        		Cursor mCursor= mDb.fetchStaff(global.datetime[global.index_alarm][5]);
	            	String reminder=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
	            	int _reminder =Integer.parseInt(reminder);
	            	if(_reminder==0)
	            		global.index_alarm++;
	            	
	        		global._id=global.datetime[global.index_alarm][5];
	            	setTime(global.datetime[global.index_alarm][0],global.datetime[global.index_alarm][1],global.datetime[global.index_alarm][2],global.datetime[global.index_alarm][3],global.datetime[global.index_alarm][4],global._id);            	
	            	             	
	        		String ns = Context.NOTIFICATION_SERVICE;
	        		global.mNotificationManager = (NotificationManager) getSystemService(ns);
	        		int icon = R.drawable.notify_alarm;
	        		CharSequence tickerText = "Sự kiện";
	        		long when = System.currentTimeMillis();
	        		Notification notification = new Notification(icon, tickerText, when);
	        		
	        		//dinh nghia thong tin mo rong va intent
	        		Context context = getApplicationContext();
	        		CharSequence contentTitle = "Các sự kiện của bạn";
	        		CharSequence contentText = "Chọn vào đây để mở các sự kiện";
	        		Intent notificationIntent = new Intent(this, appointment.class);
	        		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	        		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	        		global.mNotificationManager.notify(NOTIFICATION, notification);
	        		global.checknotification=true;
	        		
	        		
	        	}
	        	else//cancel notification
	        	{        		
	        		if(global.checknotification==true)
	        		{
	        		String ns = Context.NOTIFICATION_SERVICE;
	        		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
	        		int icon = R.drawable.notify_alarm;
	        		CharSequence tickerText = "Sự kiện";
	        		long when = System.currentTimeMillis();
	        		Notification notification = new Notification(icon, tickerText, when);
	        		
	        		//dinh nghia thong tin mo rong va intent
	        		Context context = getApplicationContext();
	        		CharSequence contentTitle = "Các sự kiện của bạn";
	        		CharSequence contentText = "Chọn vào đây để mở các sự kiện";
	        		Intent notificationIntent = new Intent(this, appointment.class);
	        		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	        		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	        		global.mNotificationManager.cancel(NOTIFICATION);
	        		global.checknotification=false;
	        		}  	        		
        	}        	
        	}catch (Exception e) {        		
				Log.e("Activity Edit", e.toString());				
			}    
        	mDb.close();
        	Filldata();
            break;
        case ACTIVITY_EDIT:
        	
        	mDb= new DbAdapter(appointment.this);
        	mDb.open();
        	try
        	{        	
	        	global.datetime=mDb.GetDateFromDatabase();        	
	        	global.index_alarm=0;
	        	if(global.datetime[0][0]!=-1)//tao ra status bar notification
	        	{        
	        		Log.e(" ACTIVITY_EDIT datetime[0][0]::", "!= -1");
	        		Cursor mCursor= mDb.fetchStaff(global.datetime[global.index_alarm][5]);
	            	String reminder=mCursor.getString(mCursor.getColumnIndexOrThrow(DbAdapter.KEY_REMINDER));
	            	int _reminder =Integer.parseInt(reminder);
	            	if(_reminder==0)
	            		global.index_alarm++;
	            	
	        		global._id=global.datetime[global.index_alarm][5];
	            	setTime(global.datetime[global.index_alarm][0],global.datetime[global.index_alarm][1],global.datetime[global.index_alarm][2],global.datetime[global.index_alarm][3],global.datetime[global.index_alarm][4],global._id);            	
	            	             	
	        		String ns = Context.NOTIFICATION_SERVICE;
	        		global.mNotificationManager = (NotificationManager) getSystemService(ns);
	        		int icon = R.drawable.notify_alarm;
	        		CharSequence tickerText = "Sự kiện";
	        		long when = System.currentTimeMillis();
	        		Notification notification = new Notification(icon, tickerText, when);
	        		
	        		//dinh nghia thong tin mo rong va intent
	        		Context context = getApplicationContext();
	        		CharSequence contentTitle = "Các sự kiện của bạn";
	        		CharSequence contentText = "Chọn vào đây để mở các sự kiện";
	        		Intent notificationIntent = new Intent(this, appointment.class);
	        		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	        		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	        		global.mNotificationManager.notify(NOTIFICATION, notification);
	        		global.checknotification=true;
	        	}
	        	else//cancel notification
	        	{        		
	        		if(global.checknotification==true)
	        		{
	        		String ns = Context.NOTIFICATION_SERVICE;
	        		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
	        		int icon = R.drawable.notify_alarm;
	        		CharSequence tickerText = "Sự kiện";
	        		long when = System.currentTimeMillis();
	        		Notification notification = new Notification(icon, tickerText, when);
	        		
	        		//dinh nghia thong tin mo rong va intent
	        		Context context = getApplicationContext();
	        		CharSequence contentTitle = "Các sự kiện của bạn";
	        		CharSequence contentText = "Chọn vào đây để mở các sự kiện";
	        		Intent notificationIntent = new Intent(this, appointment.class);
	        		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	        		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	        		global.mNotificationManager.cancel(NOTIFICATION);
	        		global.checknotification=false;
	        		}  	        		
        	}        	
        	}catch (Exception e) {        		
				Log.e("Activity Edit", e.toString());				
			}    
        	mDb.close();
        	Filldata();
            break;
    }        
}
@Override
public void onCreateContextMenu(ContextMenu menu, View v,
		ContextMenuInfo menuInfo) {
	super.onCreateContextMenu(menu, v, menuInfo);
   // menu.add(0, MENU_DELETE, 0, "Xóa cuộc hẹn này");
}
@Override
public boolean onContextItemSelected(MenuItem item) {
	switch(item.getItemId()) 
	{
    	case MENU_DELETE:    		
	        return true;
	}
	return super.onContextItemSelected(item);
}	
public class ListAdapteradd extends BaseAdapter {
    private LayoutInflater mInflater;
    public ListAdapteradd(Context context)
    {
    	DbAdapter mDb;
    	mDb = new DbAdapter(appointment.this);
    	mDb.open();
    	mCursor=mDb.fetchAllStaffs();
    	global.socuochen=mCursor.getCount();
    	mDb.close();
    	mInflater = LayoutInflater.from(context);
    }
    public int getCount() 
    {    	
    	return global.socuochen;
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
    	if(position==0)
    		mCursor.moveToFirst();       
        if (convertView == null)
        {
        	//convertView = mInflater.inflate(R.layout.title_content, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.	         	
        		convertView = mInflater.inflate(R.layout.viewappointment, null);
        		//vapp.tgbtonoff= (ToggleButton)convertView.findViewById(R.id.onoffapp);
        		vapp.txtviewapp= (TextView)convertView.findViewById(R.id.viewapp);
        		vapp.txtviewapp_nho= (TextView)convertView.findViewById(R.id.viewapp_nho);        		
        		//vapp.lilayout=(LinearLayout)findViewById(R.id.viewapp_root);
	        	convertView.setTag(vapp);         	        
        } else 
        {
        	 // Get the ViewHolder back to get fast access to the TextView
        	vapp=(ViewAppointment)convertView.getTag();
        }        
        try
        {	        
	        vapp.txtviewapp.setText(mCursor.getString(1));
	        vapp.txtviewapp_nho.setText(mCursor.getString(2));	        
	        mCursor.moveToNext();
	        
        }catch (Exception e)
        {
			Toast.makeText(appointment.this, e.getMessage(), Toast.LENGTH_LONG);
		}       
        return convertView;
    }   
}
class ViewAppointment
{
	   LinearLayout lilayout;		   
	   TextView txtviewapp;
	   TextView txtviewapp_nho;
	   ToggleButton tgbtonoff;
}  
public void Filldata()
{
	listadapter= new ListAdapteradd(appointment.this);
	global.listview.setAdapter(listadapter);
	if(global.listview.getCount()==0)
		Toast.makeText(appointment.this, "Không có sự kiện nào cả, nếu bạn muốn tạo sự kiện vui lòng nhấn phím menu và chọn đặt lịch", Toast.LENGTH_LONG).show();
	
}
public void setTime(int yyyy,int mm, int dd, int hour, int minutes, long id)
{
	DbAdapter mDb= new DbAdapter(appointment.this);
	mDb.open();
	Cursor mcursor=mDb.fetchStaff(id);
	Calendar ca = Calendar.getInstance();
    ca.clear();
    ca.set(yyyy, mm-1, dd, hour, minutes);
	Intent intent = new Intent(this, OneTimeAlarm.class);
	intent.putExtra(DbAdapter.KEY_URI, mcursor.getString(
	mcursor.getColumnIndexOrThrow(DbAdapter.KEY_URI)));    	 
    PendingIntent pi = PendingIntent.getBroadcast(this,0, intent,0);
    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
    am.set(AlarmManager.RTC_WAKEUP, ca.getTimeInMillis(), pi);        
    
}
// lop dung de delete cac cuoc hen
public class app_delete extends Dialog
{
	Context mcontext;
	private int _position=0;
	
	public int position()
	{
		return _position;
	}
	public void position(int value)
	{
		_position=value;
	}
	public app_delete(Context context) 
    {    			
        super(context);
        this.mcontext=context;
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_app);       
        Button app_delete_set=(Button)findViewById(R.id.bt_delete_app_set);
        Button app_delete_cancel=(Button)findViewById(R.id.bt_delete_app_cancel);                
        app_delete_set.setOnClickListener(new SETListener());
        app_delete_cancel.setOnClickListener(new CANCELListener());
    }
    private class SETListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {        	
            //readyListener.ready(String.valueOf(etName.getText()));
        	DbAdapter mDb = new DbAdapter(mcontext);
			mDb.open();
			Cursor mCursor=mDb.fetchAllStaffs();
			mCursor.moveToPosition(_position);
			long id=mCursor.getLong(0);
	    	mDb.DELETESTAFF(id);	    	
	    	mDb.close();	    	
        	app_delete.this.dismiss(); 
        	Filldata();
        	if(mCursor.getCount()==1)
        	{
        		if(global.checknotification==true)
        		{
	        		global.mNotificationManager.cancel(NOTIFICATION);
	        		global.checknotification=false;
        		}   
        	}
        }   
    }    
    private class CANCELListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            //readyListener.ready(String.valueOf(etName.getText()));
        	app_delete.this.dismiss();
        }
    }
}
}
