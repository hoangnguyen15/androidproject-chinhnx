package com.krazevina.objects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;

import com.krazevina.lichvannien.Alarm;
import com.krazevina.lichvannien.R;
import com.krazevina.lichvannien.R.array;
import com.krazevina.lichvannien.R.id;
import com.krazevina.lichvannien.R.layout;
import com.krazevina.lichvannien.R.string;

public class EditReminder extends PopupWindow implements OnClickListener, OnTouchListener
{
	Button btnclose,btnsave,btndel,btnalarmtone,btnalarmstyle,btnrepeatstyle;
	EditText txtcont,txthour,txtmin,txttit,txtday,txtmon,txtyear;
	LinearLayout llroot,llcontainer;
	LinearLayout llextend;
	TextView txtcaption;
	public boolean amduong = true;
	ListView lvextend;
	
	ringAdapter ringadapter;
	resAdapter alarmadapter,repadapter;
	int oldsetting = 0;
	boolean extend = false;
	
	int alarmstyle = 0,repeatstyle = 0,alarmtoneindex = 0;
	String alarmarr[],repeatarr[];
	
	Reminder r;
	View anchor;
	int xx=0,yy=0;
	int maxx,maxy;
	
	Calendar calen;
	RingtoneManager rm;
	
	
	public EditReminder(View v,int w,int h, boolean focus) 
	{
		super(v,w,h,focus);

		llroot = (LinearLayout)v.findViewById(R.id.llrootpopup);
		
		txtcont = (EditText)v.findViewById(R.id.txtcont);
		txthour = (EditText)v.findViewById(R.id.txthour);
		txtmin = (EditText)v.findViewById(R.id.txtmin);
		btnclose = (Button)v.findViewById(R.id.btnclose);
		btnsave = (Button)v.findViewById(R.id.btnadd);
		btnalarmtone = (Button)v.findViewById(R.id.btnalarm);
		btnalarmstyle = (Button)v.findViewById(R.id.btnalarmstyle);
		btnrepeatstyle = (Button) v.findViewById(R.id.btnrepeatstyle);
		txttit = (EditText)v.findViewById(R.id.txttitle);
		txtday = (EditText)v.findViewById(R.id.txtday);
		txtmon = (EditText)v.findViewById(R.id.txtmon);
		txtyear = (EditText)v.findViewById(R.id.txtyear);
		llextend = (LinearLayout)v.findViewById(R.id.llextend);
		txtcaption = (TextView)v.findViewById(R.id.txtcaptionextend);
		lvextend = (ListView)v.findViewById(R.id.lvextend);
		llcontainer = (LinearLayout)v.findViewById(R.id.llcontainer);
		btndel = (Button)v.findViewById(R.id.btndel);
		
		btnsave.setOnClickListener(this);
		btnclose.setOnClickListener(this);
		btnalarmtone.setOnClickListener(this);
		btnalarmstyle.setOnClickListener(this);
		btnrepeatstyle.setOnClickListener(this);
		btndel.setOnClickListener(this);
		
		llcontainer.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				dismiss();
				return false;
			}
		});
		llroot.setFocusable(true);
		llroot.setOnTouchListener(this);
		txthour.setOnKeyListener(keyev);
		txtmin.setOnKeyListener(keyev);
		
		
		alarmarr = v.getResources().getStringArray(R.array.alarmstyle);
		repeatarr = v.getResources().getStringArray(R.array.repeatarr);
		btnalarmstyle.setText(alarmarr[0]);
		btnrepeatstyle.setText(repeatarr[0]);
		
		maxy = 600;
		maxx = 1024;
		
		rm = new RingtoneManager(v.getContext());
		rings = new ArrayList<Uri>();
		txt = new HashMap<Integer, TextView>();
		for(int i=0;i<rm.getCursor().getCount();i++)
		{
			rings.add(rm.getRingtoneUri(i));
			Log.d("ringtone", ""+rm.getRingtoneUri(i).toString());
		}
		rm.getCursor().deactivate();
		anchor = v;
		ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
		llextend.setVisibility(View.GONE);
	}
	

	public void showAtLocation(View parent, int gravity, int x, int y)
	{
		super.showAtLocation(parent, gravity, x, y);
		calen = Global.curReminder.c;
		anchor = parent;
		txtmon.setText(""+(calen.get(Calendar.MONTH)+1<10?"0":"")+(calen.get(Calendar.MONTH)+1));
		txtday.setText(""+(calen.get(Calendar.DATE)<10?"0":"")+calen.get(Calendar.DATE));
		txtyear.setText(""+calen.get(Calendar.YEAR));
		txthour.setText(""+(calen.get(Calendar.HOUR_OF_DAY)<10?"0":"")+calen.get(Calendar.HOUR_OF_DAY));
		txtmin.setText(""+(calen.get(Calendar.MINUTE)<10?"0":"")+calen.get(Calendar.MINUTE));
		
		txttit.setText(""+Global.curReminder.title);
		txtcont.setText(""+Global.curReminder.content);
		ring = Global.curReminder.alarmtone;
		alarmstyle = Global.curReminder.alarmstyle;
		repeatstyle = Global.curReminder.repeatstyle;
		Uri ringuri = Uri.parse(ring);
		for(int i=0;i<rm.getCursor().getCount();i++)
		{
			if(ringuri.equals(rm.getRingtoneUri(i)))
				alarmtoneindex = i;
		}
	}
	
	int hour,min,day,mon,year;
	public void display()
	{
		try
		{
			Calendar cal = Calendar.getInstance();
			hour = Integer.parseInt(txthour.getText().toString());
			min = Integer.parseInt(txtmin.getText().toString());
			mon = Integer.parseInt(txtmon.getText().toString());
			day = Integer.parseInt(txtday.getText().toString());
			year = Integer.parseInt(txtyear.getText().toString());
			if(hour<0)hour = 0;
			if(min<0)min = 0;
			if(hour>23)hour = 23;
			if(hour<0)hour = 0;
			if(mon>12)mon=12;
			cal.set(year, mon, 1);
			if(day>calen.getMaximum(Calendar.DAY_OF_MONTH))day=calen.getMaximum(Calendar.DAY_OF_MONTH);
			txthour.setText(""+(hour<10?"0":"")+hour);
			txtmin.setText(""+(min<10?"0":"")+min);
			txtday.setText(""+(day<10?"0":"")+day);
			txtmon.setText(""+(mon<10?"0":"")+mon);
			
		}catch(Exception e)
		{
			txthour.setText("09");
			txtmin.setText("00");
		}
	}
	
	public void onClick(View v) 
	{
		if(v.getId()==btnclose.getId())
		{
			dismiss();
			return;
		}
		if(v.getId()==btnsave.getId())
		{
			hour = Integer.parseInt(txthour.getText().toString());
			min = Integer.parseInt(txtmin.getText().toString());
			mon = Integer.parseInt(txtmon.getText().toString());
			day = Integer.parseInt(txtday.getText().toString());
			year = Integer.parseInt(txtyear.getText().toString());
			Calendar cal = Calendar.getInstance();
			cal.set(year, mon, 1);
			if(hour<0||hour>23)
			{
				txthour.requestFocus();
				txthour.setSelection(0, 2);
				Toast.makeText(anchor.getContext(), anchor.getContext().getString(R.string.wrongtime), 1).show();
				return;
			}
			if(min<0||min>59)
			{
				txtmin.requestFocus();
				txtmin.setSelection(0, 2);
				Toast.makeText(anchor.getContext(), anchor.getContext().getString(R.string.wrongtime), 1).show();
				return;
			}
			if(mon>12)
			{
				txtmon.requestFocus();
				txtmon.setSelection(0, 2);
				Toast.makeText(anchor.getContext(), anchor.getContext().getString(R.string.wrongtime), 1).show();
				return;
			}
			if(day>cal.getMaximum(Calendar.DAY_OF_MONTH))
			{
				txtday.requestFocus();
				txtday.setSelection(0, 2);
				Toast.makeText(anchor.getContext(), anchor.getContext().getString(R.string.wrongtime), 1).show();
				return;
			}
			txthour.setText(""+(hour<10?"0":"")+hour);
			txtmin.setText(""+(min<10?"0":"")+min);
			txtday.setText(""+(day<10?"0":"")+day);
			txtmon.setText(""+(mon<10?"0":"")+mon);
			
			dismiss();
			Sqlite sql = new Sqlite(anchor.getContext());
			calen.set(Calendar.DAY_OF_MONTH, day);
			calen.set(Calendar.MONTH, mon-1);
			calen.set(Calendar.YEAR, year);
			calen.set(Calendar.HOUR_OF_DAY, hour);
			calen.set(Calendar.MINUTE, min);
			String cont = txtcont.getText().toString();
			Log.d("ZZZZZZZ", "add:"+hour+":"+min+":"+cont);
			String tit = txttit.getText().toString();
			
			if(ring!=null)
				r = new Reminder(0,calen,tit, cont, 3, 0, repeatstyle, ring.toString(),alarmstyle);
			else r = new Reminder(0,calen,tit, cont, 3, 0, repeatstyle, "null", alarmstyle);
			
			sql.del(Global.curReminder);
			sql.add(r);
			sql.close();
			Alarm.update(anchor.getContext());
			WeekRemindersAdapter.update(anchor.getContext());
			
			if(Global.weekAdapter!=null)Global.weekAdapter.notifyDataSetChanged();
			if(Global.dayAdapter!=null)Global.dayAdapter.notifyDataSetChanged();
			WeekRemindersAdapter.update(anchor.getContext());
			for(int i=0;i<WeekAdapter.adapters.size();i++)
			{
				WeekAdapter.adapters.get(i).notifyDataSetChanged();
			}
			return;
		}
		if(v.getId()==btnalarmtone.getId())
		{
			if(!extend)
			{
				select = alarmtoneindex;
				llextend.setVisibility(View.VISIBLE);
				ringadapter = new ringAdapter(anchor.getContext());
				lvextend.setAdapter(ringadapter);
				lvextend.setOnItemClickListener(new OnItemClickListener() 
				{
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
					{
						select = arg2;
						alarmtoneindex = arg2;
						setchk();
						if(arg2==0)
							ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
						else 
							ring = rings.get(arg2-1).toString();
						ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
						btnalarmtone.setText(""+ring.toString());
					}
				});
				txtcaption.setText(anchor.getContext().getString(R.string.chonnhacchuong));
				oldsetting = 1;
				extend = true;
			}
			else
			{
				llextend.setVisibility(View.GONE);
				extend = false;
			}
		}
		if(v.getId()==btnrepeatstyle.getId())
		{
			if(!extend)
			{
				select = repeatstyle;
				llextend.setVisibility(View.VISIBLE);
				repadapter = new resAdapter(anchor.getContext(), repeatarr);
				lvextend.setAdapter(repadapter);
				lvextend.setOnItemClickListener(new OnItemClickListener() 
				{
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
					{
						select = arg2;
						repadapter.notifyDataSetChanged();
						repeatstyle = arg2%repeatarr.length;
						btnrepeatstyle.setText(repeatarr[repeatstyle]);
					}
				});
				txtcaption.setText(anchor.getContext().getString(R.string.chonkieulap));
				extend = true;
				oldsetting = 2;
			}
			else
			{
				llextend.setVisibility(View.GONE);
				extend = false;
			}
		}
		if(v.getId()==btnalarmstyle.getId())
		{
			if(!extend)
			{
				select = alarmstyle;
				llextend.setVisibility(View.VISIBLE);
				alarmadapter = new resAdapter(anchor.getContext(), alarmarr);
				lvextend.setAdapter(alarmadapter);
				lvextend.setOnItemClickListener(new OnItemClickListener() 
				{
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
					{
						select = arg2;
						alarmadapter.notifyDataSetChanged();
						alarmstyle = arg2%alarmarr.length;
						btnalarmstyle.setText(alarmarr[alarmstyle]);
					}
				});
				txtcaption.setText(anchor.getContext().getString(R.string.chonkieubao));
				extend = true;
				oldsetting = 3;
			}
			else
			{
				llextend.setVisibility(View.GONE);
				extend = false;
			}
		}
		if(v.getId()==btndel.getId())
		{
			dismiss();
			Sqlite sql = new Sqlite(anchor.getContext());
			sql.del(Global.curReminder);
			sql.close();
			Alarm.update(anchor.getContext());
			WeekRemindersAdapter.update(anchor.getContext());
			
			if(Global.weekAdapter!=null)Global.weekAdapter.notifyDataSetChanged();
			if(Global.dayAdapter!=null)Global.dayAdapter.notifyDataSetChanged();
			WeekRemindersAdapter.update(anchor.getContext());
			for(int i=0;i<WeekAdapter.adapters.size();i++)
			{
				WeekAdapter.adapters.get(i).notifyDataSetChanged();
			}
		}
	}
	OnKeyListener keyev = new OnKeyListener() 
	{
		public boolean onKey(View v, int keyCode, KeyEvent event) 
		{
			Log.d("ZZZZZZ", "key event");
			if(event.getAction()==KeyEvent.ACTION_UP)
			{
				if(keyCode==KeyEvent.KEYCODE_BACK)
				{
					dismiss();
					return true;
				}
				if(v.getId()==txthour.getId()&&txthour.getText().toString().length()>0)
				{
					txtmin.requestFocus();
				}
				Log.d("ZZZZZZ", "key up");
				display();
			}
			return true;
		}
	};
	
	float startx,starty;
	
	public boolean onTouch(View v, MotionEvent e) 
	{
		if(e.getAction()==MotionEvent.ACTION_DOWN)
		{
			startx = e.getRawX();
			starty = e.getRawY();
		}
		if(e.getAction()==MotionEvent.ACTION_MOVE)
		{
			LayoutParams lp = new LayoutParams(llroot.getLayoutParams());
			float dx = e.getRawX()-startx,dy = e.getRawY()-starty;
			
			lp.setMargins(xx+(int)(dx), yy+(int)(dy), 0, 0);
			llroot.setLayoutParams(lp);
			startx = e.getRawX();
			starty = e.getRawY();
			xx+=(int)(dx);
			yy+=(int)(dy);
		}
		if(e.getAction()==MotionEvent.ACTION_UP)
		{
			float dx = e.getRawX()-startx,dy = e.getRawY()-starty;
			xx+=(int)(dx);
			yy+=(int)(dy);
		}
		return true;
	}
	
	ArrayList<Uri> rings;
	HashMap<Integer,TextView> txt;
	int select=0;
	String ring;
	boolean def = true;
	
	OnClickListener ringClick = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			int ind = find(v);
			Log.d("Click at", ""+ind);
			select = ind;
			setchk();
			if(ind==0)
				ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
			else
				ring = rings.get(ind-1).toString();
		
			ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
		}
	};
	
	public int find(View v)
	{
		int i=0;
		for(i=0;i<txt.size();i++)
		{
			if(txt.get(i).equals(v))return i;
		}
		return 0;
	}
	

	public void setchk()
	{
		ringadapter.notifyDataSetChanged();
	}
	
	

	
	class ringAdapter extends BaseAdapter
	{
		LayoutInflater mInflater;
		ViewHolder v;
		public ringAdapter(Context context) 
		{
			mInflater = LayoutInflater.from(context);
			
		}
		public int getCount() 
		{
			return rings.size()+1;
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
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.ringitem, null);
				v = new ViewHolder();
				v.txt = (TextView) convertView.findViewById(R.id.txtname);
				if(position == 0)
					v.txt.setBackgroundResource(R.drawable.mark);
				convertView.setTag(v);
				txt.put(position, v.txt);
			} else {
				v = (ViewHolder) convertView.getTag();
			}

			if(position ==0)v.txt.setText(anchor.getContext().getString(R.string.macdinh));
			else v.txt.setText(rm.getRingtone(position-1).getTitle(anchor.getContext()));
			
			if(position!=select)
				v.txt.setBackgroundColor(0);
			else
				v.txt.setBackgroundResource(R.drawable.mark);
			
			return convertView;
		}
		class ViewHolder 
		{
			TextView txt;
		}
	}
	class resAdapter extends BaseAdapter
	{
		LayoutInflater mInflater;
		ViewHolder v;
		String [] res;
		public resAdapter(Context context,String[]res) 
		{
			mInflater = LayoutInflater.from(context);
			this.res = res;
		}
		public int getCount() 
		{
			return res.length;
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
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.ringitem, null);
				v = new ViewHolder();
				v.txt = (TextView) convertView.findViewById(R.id.txtname);
				convertView.setTag(v);
			} else {
				v = (ViewHolder) convertView.getTag();
			}

			v.txt.setText(res[position]);
			
			if(position!=select)
				v.txt.setBackgroundColor(0);
			else
				v.txt.setBackgroundResource(R.drawable.mark);
			
			return convertView;
		}
		class ViewHolder 
		{
			TextView txt;
		}
	}
}
