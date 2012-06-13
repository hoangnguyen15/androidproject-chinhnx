package com.krazevina.lichvannien;

import java.util.Calendar;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.LayoutInflater.Factory;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import com.krazevina.objects.AddReminder;
import com.krazevina.objects.DatePick;
import com.krazevina.objects.EditReminder;
import com.krazevina.objects.Global;
import com.krazevina.objects.LichXuatHanh;
import com.krazevina.objects.LunarCalendar;
import com.krazevina.objects.Processdate;
import com.krazevina.objects.Sqlite;
import com.krazevina.objects.Utils;
import com.krazevina.objects.WeekAdapter;

public class WeekView extends Activity implements OnClickListener, OnItemClickListener, OnItemSelectedListener 
{
	private static final int MENU_TODAY=0;
	private static final int CHANGEBG=1;	
	private static final int CONTACT=2;
	private static final int SETALARM=3;
	private static final int CHOOSE=4;
	private static final int MONTHVIEW=5;
	Button btnAddReminder;
	private static Gallery gridMonth;
	public static TextView txt_month;
	static LinearLayout llroot;
	
	private TextView left_date,left_day;
	private TextView left_datelunar,left_datelunar_number;
	private TextView left_monthlunar,left_monthlunar_number;
	private TextView left_specialday,left_specialday_lunar;
	private TextView left_lichxuathanh;
	private Calendar c;
	
	public static void onNotify()
	{
		try{
			gridMonth.setSelection(23);
		}catch(Exception e){}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weekview);
		if(Global.selectDate==null)Global.selectDate = Calendar.getInstance();
		c = Calendar.getInstance();
		c.setTimeInMillis(Global.selectDate.getTimeInMillis());
		
		left_day = (TextView)findViewById(R.id.txt_left_day);
		left_date = (TextView)findViewById(R.id.txt_left_date);
		left_datelunar = (TextView)findViewById(R.id.txt_left_datelunar);
		left_datelunar_number = (TextView)findViewById(R.id.txt_left_datelunar_number);
		left_monthlunar = (TextView)findViewById(R.id.txt_left_monthlunar);
		left_monthlunar_number = (TextView)findViewById(R.id.txt_left_monthlunar_number);
		left_specialday = (TextView)findViewById(R.id.txt_left_specialday);
		left_specialday_lunar = (TextView)findViewById(R.id.txt_left_specialday_lunar);
		left_lichxuathanh = (TextView)findViewById(R.id.txt_left_lichxuathanh);
		
		llroot = (LinearLayout)findViewById(R.id.root);
		((BitmapDrawable)llroot.getBackground()).setCallback(null);
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inSampleSize = 2;
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_color_07, op);
		
		llroot.setBackgroundDrawable(new BitmapDrawable(b));
		
		txt_month = (TextView)findViewById(R.id.txt_month);
		btnAddReminder = (Button) findViewById(R.id.btn_addevent);
		llroot = (LinearLayout)findViewById(R.id.root);
		
		btnAddReminder.setOnClickListener(this);

		gridMonth = (Gallery) findViewById(R.id.galweek);
		SetToDay();
		SetMonth();
		gridMonth.setOnItemClickListener(this);
		gridMonth.setSelection(23);
		gridMonth.setOnItemSelectedListener(this);
		Alarm.update(WeekView.this);
		Alarm.context = WeekView.this;
		startService(new Intent(WeekView.this, Alarm.class));
		
		LayoutInflater inflater = (LayoutInflater) WeekView.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		root = inflater.inflate(R.layout.popupalarm, null, false);
		int w =getWindowManager().getDefaultDisplay().getWidth();
		int h =getWindowManager().getDefaultDisplay().getHeight();
		pw = new AddReminder(root, w, h, true, c);
		root1 = inflater.inflate(R.layout.popupeditalarm, null, false);
		ed = new EditReminder(root1, w, h, true);
	}

	static public void showEdit()
	{
		ed.showAtLocation(llroot, Gravity.LEFT | Gravity.TOP, 0, 0);
	}
	View root,root1;
	AddReminder pw;
	static public EditReminder ed;

	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
	}

	float x, oldx;
	float currx = 0, tempx;
	int currentview = 0;

	

	DatePick dp;
	public void onClick(View v) {
		if (v.getId() == btnAddReminder.getId()) {
			pw.showAtLocation(llroot, Gravity.LEFT | Gravity.TOP, 0, 0);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, MENU_TODAY, 0, "Hôm nay").setIcon(android.R.drawable.ic_menu_today);
    	menu.add(0, MONTHVIEW, 0, "Xem theo tháng").setIcon(android.R.drawable.ic_menu_week);
        menu.add(0, CHANGEBG, 0, "Đổi hình nền").setIcon(android.R.drawable.ic_menu_gallery);
        menu.add(0, CHOOSE, 0, "Chọn ngày").setIcon(android.R.drawable.ic_menu_day);
        menu.add(0, SETALARM, 0, "Đặt lịch").setIcon(android.R.drawable.ic_menu_add);
        menu.add(0, CONTACT, 0, "Giới thiệu").setIcon(android.R.drawable.ic_menu_info_details);
//        setMenuBackground();
        
        return true;
    } 
	public boolean onOptionsItemSelected(MenuItem item) {    	
        switch (item.getItemId()) 
        {
	        case MENU_TODAY:
	        	Global.select_position = -1;
	        	SetToDay();	   
	        	SetMonth();        	
	            return true;
	        case CHANGEBG:
//	        	Random pickRand = new Random();
//				int numRand;
//				int[] mabg = {R.drawable.thang1,R.drawable.thang2,R.drawable.bg_buble,R.drawable.thang4};
//				numRand = pickRand.nextInt(mabg.length);
//				Sqlite sql = new Sqlite(this);
//				sql.setWallpaper(numRand+1);
//				sql.close();
//				((BitmapDrawable)llroot.getBackground()).setCallback(null);
//				llroot.setBackgroundResource(mabg[numRand]);
	        	return true;
	        case CONTACT:
	        	return true;
	        case SETALARM:
	        	
				pw.showAtLocation(llroot, Gravity.LEFT | Gravity.TOP, 0, 0);
	        	return true;
	        case CHOOSE:
	        	LayoutInflater inflater = (LayoutInflater) WeekView.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				root = inflater.inflate(R.layout.picker, null, false);
				dp = new DatePick(root,2000,2000,true, c,new OnClickListener() {
					public void onClick(View v) {
						dp.dismiss();
						if(dp.amduong)
						{
							Calendar c = dp.c;
							int year = c.get(Calendar.YEAR);
							int day = c.get(Calendar.DATE);
							int month = c.get(Calendar.MONTH)+1;
							Global.yyyy = year;
							Global.mm = month;
							Global.dd = day;
							int[] sol = Utils.convertSolar2Lunar(Global.dd, Global.mm, Global.yyyy, 7.0);
							Global.lunar_dd = sol[0];
							Global.lunar_mm = String.valueOf(sol[1]);
							Global.lunar_yyyy = sol[2];
							Global.select_position = Global.dd;
							Global.weekAdapter = new WeekAdapter(WeekView.this,Global.dd, Global.mm,Global.yyyy);
							gridMonth.setAdapter(Global.weekAdapter);
						}
						else
						{
							LunarCalendar lc = dp.lc;
							int year = lc.get(Calendar.YEAR);
							int day = lc.get(Calendar.DATE);
							int month = lc.get(Calendar.MONTH);
							Global.lunar_yyyy = year;
							Global.lunar_mm = ""+month+lc.getLeap();
							Global.lunar_dd = day;
							int[] solar = Utils.convertLunar2Solar(day, month, year, lc.getLeapi(), 7);
							Global.dd = solar[0];
							Global.mm = solar[1];
							Global.yyyy = solar[2];
							Global.select_position = Global.dd;
							Global.weekAdapter = new WeekAdapter(WeekView.this,Global.dd, Global.mm,Global.yyyy);
							gridMonth.setAdapter(Global.weekAdapter);
						}
						txt_month.setText("Tháng "+Global.mm+"/"+Global.yyyy);
						c.set(Global.yyyy, Global.mm-1, Global.dd);
						SetToDay();
						SetMonth();
					}
				});
				dp.showAtLocation(llroot, Gravity.TOP|Gravity.LEFT, 0, 0);
	        	
	        	return true;
	        case MONTHVIEW:
	        	Intent i = new Intent(WeekView.this, Main.class);
	        	startActivity(i);
	        	finish();
	        	return true;
	        
        }
        return false;
    }
	
	private void gridViewItemIsClicked(int position) {
    	//lay thu  begin
    	String thu=Global.weekAdapter.dayInWeekOfPos(position);
    	
    	int _mMonth=Global.weekAdapter.monthOfPosition(position);
    	int _mYear=Global.weekAdapter.yearOfPosition(position);
    	int _mDay=Global.weekAdapter.dayOfPosition(position);
    	
    	c.set(Calendar.DATE, _mDay);
    	c.set(Calendar.MONTH, _mMonth-1);
    	c.set(Calendar.YEAR, _mYear);
    	
    	//ngayduong= ngayduong+global.txtviewadapter.prodate.DAYS_SOLAR[position];
    	String thangnamduonglich="";
    	String ngayduonglich=""+Global.weekAdapter.dayOfPosition(position);
    	thangnamduonglich="Tháng "+Global.weekAdapter.monthOfPosition(position);
    	thangnamduonglich =thangnamduonglich +" Năm "+Global.weekAdapter.yearOfPosition(position);
    	//ngayduong=ngayduong+" (DL)"+"\n";

    	//tach lay ngay am da chon
    	int[] ngayam=Global.weekAdapter.solardate(position);
    	
    	String canchinam = Processdate.GETLUNARYEARNAME(ngayam[2]);//truyen vao nam am lich
    	String canchithang= Processdate.GETLUNARMONTHCANCHINAME(ngayam[2],ngayam[1]);//truyen vao thang nam am lich
    	String canchingay= Processdate.GETLUNARDAYCANCHINAME(_mDay,_mMonth,_mYear);// truyen vao ngay duong lich
    	String thongtinngayam="Âm lịch ";
    	if(ngayam[3]>0)
    		thongtinngayam += "(Tháng Nhuận)";   
    	
    	//lay nhung ngay dac biet trong nam
    	String specialdays= Global.weekAdapter.prodate.GETDAYSPECIALYEARLTEVENT(_mDay, _mMonth);    	    		
    	String ngayamdacbiet=Global.weekAdapter.prodate.GETDAYYEARLYEVENT(ngayam[0], ngayam[1]);
    	
    	
    	if(specialdays!=""){
    		left_specialday.setVisibility(View.VISIBLE);
    	}else{
    		left_specialday.setVisibility(View.GONE);
    	}
    	
    	if(ngayamdacbiet!=""){
    		left_specialday_lunar.setVisibility(View.VISIBLE);
    	}else{
    		left_specialday_lunar.setVisibility(View.GONE);
    	}
    	
    	String lichxuathanh=LichXuatHanh.getLichXuatHanh(ngayam[0], ngayam[1]);
    
    	
    	
    	left_day.setText(thu);
    	left_date.setText(ngayduonglich);
    	left_monthlunar.setText(canchithang);
    	left_datelunar.setText(canchingay);
    	left_monthlunar_number.setText("Tháng "+ ngayam[1]);
    	left_datelunar_number.setText("Ngày " + ngayam[0]);
    	left_specialday.setText(specialdays);
    	left_specialday_lunar.setText(ngayamdacbiet);
    	left_lichxuathanh.setText(lichxuathanh);
    	Log.d("Global.select",""+Global.select_position);
    }
	
	
	public void SetToDay() {
		Global.yyyy = c.get(Calendar.YEAR);
		Global.mm = c.get(Calendar.MONTH) + 1;
		Global.dd = c.get(Calendar.DATE);
		Log.d("setmonth"," "+Global.mm);
		Global.weekAdapter = new WeekAdapter(WeekView.this, Global.dd, Global.mm,Global.yyyy);
		
		// dieu chinh hien thi gridview
		gridMonth.setAdapter(Global.weekAdapter);
		gridMonth.setSelection(23);
	}
	
	public void SetMonth(){
		int[] lunar = Processdate.CONVERTSOLAR2LUNAR(Global.dd,Global.mm, Global.yyyy);
		Global.lunar_dd = lunar[0];
		Global.lunar_mm = String.valueOf(lunar[1]);
		Global.lunar_yyyy = lunar[2];
		String canchithang= Processdate.GETLUNARMONTHCANCHINAME(Global.lunar_yyyy,Integer.valueOf(Global.lunar_mm));//truyen vao thang nam am lich
    	String canchingay= Processdate.GETLUNARDAYCANCHINAME(Global.dd,Global.mm,Global.yyyy);// truyen vao ngay duong lich
    	
    	String specialdays= Global.weekAdapter.prodate.GETDAYSPECIALYEARLTEVENT(Global.dd, Global.mm);    	    		
    	String ngayamdacbiet=Global.weekAdapter.prodate.GETDAYYEARLYEVENT(Global.lunar_dd, Integer.valueOf(Global.lunar_mm));
    	String lichxuathanh = LichXuatHanh.getLichXuatHanh(Global.lunar_dd, Integer.valueOf(Global.lunar_mm));
    	
    	if(specialdays!=""){
    		left_specialday.setVisibility(View.VISIBLE);
    	}else{
    		left_specialday.setVisibility(View.GONE);
    	}
    	
    	if(ngayamdacbiet!=""){
    		left_specialday_lunar.setVisibility(View.VISIBLE);
    	}else{
    		left_specialday_lunar.setVisibility(View.GONE);
    	}
    	c = Calendar.getInstance();
		
		
		
		left_day.setText(""+c.get(Calendar.DAY_OF_WEEK));
		
		switch (c.get(Calendar.DAY_OF_WEEK)) {
		
		case 1:
			left_day.setText(getString(R.string.sunday));
			break;
		case 2:
			left_day.setText(getString(R.string.monday2));
			break;
		case 3:
			left_day.setText(getString(R.string.tuesday2));
			break;
		case 4:
			left_day.setText(getString(R.string.wednesday2));
			break;
		case 5:
			left_day.setText(getString(R.string.thursday2));
			break;
		case 6:
			left_day.setText(getString(R.string.friday2));
			break;
		case 7:
			left_day.setText(getString(R.string.saturday2));
			break;

		default:
			break;
		}
		left_date.setText(""+Global.dd);
		
		txt_month.setText(Global.mm + "/"+Global.yyyy);
		
		left_datelunar_number.setText("Ngày "+Global.lunar_dd);
		left_monthlunar_number.setText("Tháng "+Global.lunar_mm);
		left_datelunar.setText(canchingay);
		left_monthlunar.setText(canchithang);
		left_specialday.setText(specialdays);
    	left_specialday_lunar.setText(ngayamdacbiet);
    	left_lichxuathanh.setText(lichxuathanh);
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if(parent.getId() == gridMonth.getId()){
			gridViewItemIsClicked(position);
			Global.selectDate = Global.weekAdapter.dateOfPos(position);
			Global.weekAdapter.notifyDataSetChanged();
		}
	}
	
	protected void setMenuBackground(){

        getLayoutInflater().setFactory( new Factory() {

            public View onCreateView ( String name, Context context, AttributeSet attrs ) {

                if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {

                    try { // Ask our inflater to create the view
                        LayoutInflater f = getLayoutInflater();
                        final View view = f.createView( name, null, attrs );
                        /* 
                         * The background gets refreshed each time a new item is added the options menu. 
                         * So each time Android applies the default background we need to set our own 
                         * background. This is done using a thread giving the background change as runnable
                         * object
                         */
                        new Handler().post( new Runnable() {
                            public void run () {
                                view.setBackgroundColor(Color.BLACK);
                            }
                        } );
                        return view;
                    }
                    catch ( InflateException e ) {}
                    catch ( ClassNotFoundException e ) {}
                }
                return null;
            }
        });
    }

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		if(arg2<10)Global.weekAdapter.update(arg2);
		if(arg2>38)Global.weekAdapter.update(arg2);
	}

	public void onNothingSelected(AdapterView<?> arg0) 
	{
	}
	protected void onPause() {
    	super.onPause();
    	Alarm.context = null;
    	Global.context = null;
    	Log.d("onPause","onPause");
    }
	public void onResume()
    {
    	super.onResume();
    	Alarm.context = this;
    	Global.context = this;
    }
	
}