package com.krazevina.objects;

import java.util.Calendar;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;

import com.krazevina.lichvannien.R;

public class DatePick extends PopupWindow implements OnClickListener, OnKeyListener, OnTouchListener
{
	Button btnPday,btnPmonth,btnPyear,btnMday,btnMmonth,btnMyear;
	Button close,search,btnamduong;
	EditText txtDay,txtMonth,txtYear;
	LinearLayout llroot,llcontainer;
	public boolean amduong = true;
	public Calendar c;
	public LunarCalendar lc;
	int []lunar;
	boolean leap = false;
	int leapm = 0;
	View anchor;
	int xx=0,yy=0;
	int maxx,maxy;
	public DatePick(View v, int w, int h, boolean f, Calendar cal, View.OnClickListener cl) 
	{
		super(v,w,h,f);
		c = Calendar.getInstance();
		c.setTimeInMillis(cal.getTimeInMillis());
		lc = new LunarCalendar();
		btnPday = (Button) v.findViewById(R.id.btnpd);
		btnMday = (Button) v.findViewById(R.id.btnmd);
		btnPmonth = (Button) v.findViewById(R.id.btnpm);
		btnMmonth = (Button) v.findViewById(R.id.btnmm);
		btnPyear = (Button) v.findViewById(R.id.btnpy);
		btnMyear = (Button) v.findViewById(R.id.btnmy);
		close = (Button)v.findViewById(R.id.btnclose);
		search = (Button)v.findViewById(R.id.btnsearch);
		btnamduong = (Button)v.findViewById(R.id.btnamduong);
		llroot = (LinearLayout)v.findViewById(R.id.llrootpicker);
		llcontainer = (LinearLayout)v.findViewById(R.id.llcontainer);
		
		txtDay = (EditText) v.findViewById(R.id.txtdate);
		txtMonth = (EditText) v.findViewById(R.id.txtmonth);
		txtYear = (EditText) v.findViewById(R.id.txtyear);
		
		btnPday.setOnClickListener(this);
		btnMday.setOnClickListener(this);
		btnPmonth.setOnClickListener(this);
		btnMmonth.setOnClickListener(this);
		btnPyear.setOnClickListener(this);
		btnMyear.setOnClickListener(this);
		close.setOnClickListener(this);
		btnamduong.setOnClickListener(this);
		search.setOnClickListener(cl);
		
		txtDay.setOnKeyListener(this);
		txtMonth.setOnKeyListener(this);
		txtYear.setOnKeyListener(this);
		
		llroot.setFocusable(true);
		llroot.setOnKeyListener(this);
		llroot.setOnTouchListener(this);
		llcontainer.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				dismiss();
				return false;
			}
		});
		
		maxy = 600;
		maxx = 1024;
		Log.d("ZZZZZZ", "W:"+v.getWidth()+":H:"+v.getHeight());
		Log.d("ZZZZZZ", "L:"+v.getLeft()+":R:"+v.getRight());
		setOutsideTouchable(true);
		lunar = Utils.convertSolar2Lunar(c.get(Calendar.DATE), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), 7.0);
		this.setAnimationStyle(R.anim.zoom_enter);
		display();
	}
	
	public void showAtLocation(View scr, int gr, int x,int y)
	{
		super.showAtLocation(scr, gr, x, y);
		anchor = scr;
		xx = x;yy = y;
//		
//		LayoutParams lp = new LayoutParams(llroot.getWidth(), llroot.getHeight());
//		
//		lp.setMargins(xx, yy, 0, 0);
//		llroot.setLayoutParams(lp);
		update(x, y, -1, -1);
	}
	
	public void display()
	{
		if(amduong)
		{
			txtDay.setText(""+c.get(Calendar.DATE));
			txtMonth.setText(""+(c.get(Calendar.MONTH)+1));
			txtYear.setText(""+c.get(Calendar.YEAR));
		}
		else
		{
			txtDay.setText(""+lc.get(Calendar.DATE));
			txtMonth.setText(""+((lc.get(Calendar.MONTH))+lc.getLeap()));
			txtYear.setText(""+(lc.get(Calendar.YEAR)));
		}
		txtDay.setTextColor(Color.WHITE);
		txtMonth.setTextColor(Color.WHITE);
		txtYear.setTextColor(Color.WHITE);
		if(amduong)btnamduong.setBackgroundResource(R.drawable.toogle_button);
		else btnamduong.setBackgroundResource(R.drawable.toogle_buttoninverse);
	}
	
	public void onClick(View v) 
	{
		if(v.getId()==btnPday.getId())
		{
			if(amduong)
				c.roll(Calendar.DATE, true);
			else lc.roll(Calendar.DATE, true);
			display(); 
		}
		if(v.getId()==btnMday.getId())
		{
			if(amduong)
				c.roll(Calendar.DATE, false);
			else lc.roll(Calendar.DATE, false);
			display(); 
		}
		if(v.getId()==btnPmonth.getId())
		{
			if(amduong)
				c.roll(Calendar.MONTH, true);
			else lc.roll(Calendar.MONTH, true);
			display(); 
		}
		if(v.getId()==btnMmonth.getId())
		{
			if(amduong)
				c.roll(Calendar.MONTH, false);
			else lc.roll(Calendar.MONTH, false);
			display(); 
		}
		if(v.getId()==btnPyear.getId())
		{
			if(amduong)
				c.roll(Calendar.YEAR, true);
			else lc.roll(Calendar.YEAR, true);
			display(); 
		}
		if(v.getId()==btnMyear.getId())
		{
			if(amduong)
				c.roll(Calendar.YEAR, false);
			else lc.roll(Calendar.YEAR, false);
			display(); 
		}
		if(v.getId()==close.getId())
		{
			dismiss();
		}
		if(v.getId()==btnamduong.getId())
		{
			amduong = !amduong;
			display();
		}
	}
	public boolean onKey(View v, int keyCode, KeyEvent event) 
	{
		if(event.getAction()==KeyEvent.ACTION_UP)
		{
			if(keyCode==KeyEvent.KEYCODE_BACK)
			{
				dismiss();
				return true;
			}
			
		}
		return false;
	}


	float startx,starty;
	
	public boolean onTouch(View v, MotionEvent e) 
	{
		if(e.getAction()==MotionEvent.ACTION_DOWN)
		{
			startx = e.getRawX();
			starty = e.getRawY();
			Log.d("ZZZZ", "touch down");
			LayoutParams lp = (LayoutParams) llroot.getLayoutParams();
			xx=lp.leftMargin;
			yy=lp.topMargin;
		}
		if(e.getAction()==MotionEvent.ACTION_MOVE)
		{
			LayoutParams lp = (LayoutParams) llroot.getLayoutParams();
			float dx = e.getRawX()-startx,dy = e.getRawY()-starty;
			
			lp.setMargins(xx+(int)(dx), yy+(int)(dy), 0, 0);
			llroot.setLayoutParams(lp);
		}
		if(e.getAction()==MotionEvent.ACTION_UP)
		{
			Log.d("ZZZZ", "touch up");
			float dx = e.getRawX()-startx,dy = e.getRawY()-starty;
			xx+=(int)(dx);
			yy+=(int)(dy);
		}
		return true;
	}
	

}
