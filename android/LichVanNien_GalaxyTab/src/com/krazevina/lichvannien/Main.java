package com.krazevina.lichvannien;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

import com.airpush.android.Airpush;
import com.fma.core.ta.TACommander;
import com.fma.core.ta.io.HttpClient;
import com.krazevina.objects.DatePick;
import com.krazevina.objects.DayAdapter;
import com.krazevina.objects.Global;
import com.krazevina.objects.LichXuatHanh;
import com.krazevina.objects.LunarCalendar;
import com.krazevina.objects.Processdate;
import com.krazevina.objects.Sqlite;
import com.krazevina.objects.Utils;

public class Main extends Activity implements OnTouchListener, OnClickListener, OnItemClickListener {
	
	private static final int MENU_TODAY=0;
	private static final int CHANGEBG=1;	
	private static final int CONTACT=2;
	private static final int SETALARM=3;
	private static final int CHOOSE=4;
	private static final int WEEKVIEW=5;
	private static final String APP_ID = "Kra-d0908ef5-c725-41b2-b5f2-71e42c1f8756"; 
	
	
	Button btnAddReminder;
	Button btnToday;
//	Button btnSearchDate;
	private TextView txt_month;
//	private Button btn_month_week;
	private boolean monthweek = false;
//	private Button btn_changebg;
	
	LinearLayout llroot;
	private TextView left_date,left_day;
	private TextView left_datelunar,left_datelunar_number;
	private TextView left_monthlunar,left_monthlunar_number;
	private TextView left_specialday,left_specialday_lunar;
	private TextView left_lichxuathanh;
	private Calendar c;
	
	//NextMonth
	ArrayList<Float> xArray, yArray;
	boolean flagActionMove = false;
	int padding = 70;
	long durationAnimButtonClick = 1300;
	long durationAnimActionMoveLR = 800;
	long durationAnimActionMoveUD = 750;
	GridView currentGridView;
	GridView nextGridView,gridView;
	ViewFlipper viewFlipper;
	 
	boolean available;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Global.select_position = -1;
		Global.context = this;
		if(Global.selectDate==null)Global.selectDate = Calendar.getInstance(); 

		
		
		//Check App Available
//		try {
//
//			available = HttpClient.isNetworkAvailable();
//
//			boolean status = TACommander.start(this, APP_ID, available);
//
//			if (!status) {
//
//			Toast.makeText(this, "Ứng dụng đã bị khóa", 2).show();
//
//			finish();
//			return;
//			
//
//			}
//
//		} catch (Exception e) {
//		}
		
//		btn_month_week = (Button)findViewById(R.id.btnmonth_week);
//		btn_month_week.setOnClickListener(this);
//		btn_changebg = (Button)findViewById(R.id.btn_changebg);
//		btn_changebg.setOnClickListener(this);
//		btn_changebg.setOnTouchListener(this);
		
		
		
		left_day = (TextView)findViewById(R.id.txt_left_day);
		left_date = (TextView)findViewById(R.id.txt_left_date);
		left_datelunar = (TextView)findViewById(R.id.txt_left_datelunar);
		left_datelunar_number = (TextView)findViewById(R.id.txt_left_datelunar_number);
		left_monthlunar = (TextView)findViewById(R.id.txt_left_monthlunar);
		left_monthlunar_number = (TextView)findViewById(R.id.txt_left_monthlunar_number);
		left_specialday = (TextView)findViewById(R.id.txt_left_specialday);
		left_specialday_lunar = (TextView)findViewById(R.id.txt_left_specialday_lunar);
		left_lichxuathanh = (TextView)findViewById(R.id.txt_left_lichxuathanh);
		
		gridView = (GridView) findViewById(R.id.gridview);
		txt_month = (TextView)findViewById(R.id.txt_month);
		
		c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		
//		LayoutInflater inflater = (LayoutInflater) Main.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		root = inflater.inflate(R.layout.popupalarm, null, false);
//		int w =getWindowManager().getDefaultDisplay().getWidth();
//		int h =getWindowManager().getDefaultDisplay().getHeight();
//		pw = new AddReminder(root, w, h, true, c);
		

//		Global.nextGridView = (GridView) findViewById(R.id.gridview_next);		
		
		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		nextGridView = (GridView) findViewById(R.id.gridview_next);
	     
	    currentGridView = (GridView)viewFlipper.getCurrentView();
	    currentGridView.setOnItemClickListener(this);
	    
	    SetToDay();
		SetMonth();
		
		llroot = (LinearLayout)findViewById(R.id.root);
//		llroot.setBackgroundResource(R.drawable.bg_sand);
		
		//setBackground
		Log.d("setBg","setBg");
		Sqlite sql = new Sqlite(this);
		
		switch (sql.getWallpaper()) {
		case 1:
			llroot.setBackgroundResource(R.drawable.wallpaper_color_01);
			break;
		case 2:
			llroot.setBackgroundResource(R.drawable.wallpaper_color_02);
			break;
		case 3:
			llroot.setBackgroundResource(R.drawable.wallpaper_color_03);
			break;
		case 4:
			llroot.setBackgroundResource(R.drawable.wallpaper_color_04);
			break;
		case 5:
			llroot.setBackgroundResource(R.drawable.wallpaper_color_05);
			break;
		case 6:
			llroot.setBackgroundResource(R.drawable.wallpaper_color_06);
			break;
		case 7:
			llroot.setBackgroundResource(R.drawable.wallpaper_color_07);
			break;
		default:
			break;
		}
		
		Log.d("pathwall",""+sql.getPathWallpaper());
		Log.d("sql.getWallpaper","zz"+sql.getWallpaper());
		
		String filePathWallpaper = sql.getPathWallpaper();
		
		if(filePathWallpaper!= null){
			File imgFile = new  File(filePathWallpaper);
			if(imgFile.exists()){
				llroot = (LinearLayout)findViewById(R.id.root);
				((BitmapDrawable)llroot.getBackground()).setCallback(null);
				BitmapFactory.Options op = new BitmapFactory.Options();
				op.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(filePathWallpaper,op);
		        final int REQUIRED_SIZE = 140;

		        
		        int width_tmp = op.outWidth, height_tmp = op.outHeight;
		        int scale = 1;
		        while (true) {
		        if (width_tmp / 2 < REQUIRED_SIZE
		            || height_tmp / 2 < REQUIRED_SIZE)
		            break;
		        width_tmp /= 2;
		        height_tmp /= 2;
		        scale *= 2;
		        }
				
		        BitmapFactory.Options o2 = new BitmapFactory.Options();
		        o2.inSampleSize = scale;
		        Bitmap myBitmap = BitmapFactory.decodeFile(filePathWallpaper,o2);
//				Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_color_07, op);
				
			    
			    Drawable yourbg = new BitmapDrawable(myBitmap);
			    llroot.setBackgroundDrawable(yourbg);
			}
		}
		sql.close();
//		scr = (HorizontalScrollView) findViewById(R.id.scrollview);
		btnAddReminder = (Button) findViewById(R.id.btn_addevent);
		btnToday = (Button) findViewById(R.id.btn_today);
//		btnSearchDate = (Button) findViewById(R.id.btn_searchdate);
		
//		btnAddReminder.setOnClickListener(this);
//		btnAddReminder.setOnTouchListener(this);
		btnToday.setOnClickListener(this);
		btnToday.setOnTouchListener(this);
		
		// WallpaperManager wall = WallpaperManager.getInstance(this);
		// scr.setBackgroundDrawable(wall.getDrawable());

		
		gridView.setOnItemClickListener(this);
		nextGridView.setOnItemClickListener(this);
		
		gridView.setOnItemClickListener(this);
		nextGridView.setOnItemClickListener(this);
		
		
		xArray = new ArrayList<Float>();
		yArray = new ArrayList<Float>();
		OnTouchListener touchListener = new OnTouchListener() {		
			
			public boolean onTouch(View v, MotionEvent event) {
				float xPos, yPos;
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					System.out.println("-----------Action move");
					xPos = event.getRawX();
					yPos = event.getRawY();
					
					xArray.add(xPos);
					yArray.add(yPos);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					int move = 0;
					if (xArray.size() > 0) {
						float width = xArray.get(xArray.size() - 1) - xArray.get(0);
						float height = yArray.get(yArray.size() - 1) - yArray.get(0);
						
						float absWidth = Math.abs(width);
						float absHeight = Math.abs(height);
						if (absWidth > padding || absHeight > padding) {
							if (absWidth >= absHeight) {
								if (width > 0) {
									move = 2;
								}
								else {
									move = 1;
								}
							}
							else {
								if (height > 0) {
									move = 3;
								}
								else {
									move = 4;
								}
							}
						}
					}
					
					
					if (move > 0 && move <= 2) {
						gridViewAnim(move, durationAnimActionMoveLR);
						flagActionMove = true;
					}
					else if (move > 2 && move <= 4) {
						gridViewAnim(move, durationAnimActionMoveUD);
						flagActionMove = true;
					}
					else {
						flagActionMove = false;
					}
					
					xArray.clear();
					yArray.clear();
				}			
				return false;
			}
		};
		
		gridView.setOnTouchListener(touchListener);
		nextGridView.setOnTouchListener(touchListener);
//
//		Alarm.update(Main.this);
//		Alarm.context = Main.this;
//		startService(new Intent(Main.this, Alarm.class));
	}

	View root;
	PopupWindow pw;

//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//	}
//	
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, MENU_TODAY, 0, "Hôm nay").setIcon(android.R.drawable.ic_menu_today);
//    	menu.add(0, WEEKVIEW, 0, "Xem theo tuần").setIcon(android.R.drawable.ic_menu_week);
        menu.add(0, CHANGEBG, 0, "Đổi hình nền").setIcon(android.R.drawable.ic_menu_gallery);
        menu.add(0, CHOOSE, 0, "Chọn ngày").setIcon(android.R.drawable.ic_menu_day);
//        menu.add(0, SETALARM, 0, "Đặt lịch").setIcon(android.R.drawable.ic_menu_add);
        menu.add(0, CONTACT, 0, "Giới thiệu").setIcon(android.R.drawable.ic_menu_info_details);
//        setMenuBackground();
        

        
        return true;
    } 
    
    public boolean onOptionsItemSelected(MenuItem item) {    	
        switch (item.getItemId()) 
        {
	        case MENU_TODAY:
	        	Global.select_position = -1;
				c = Calendar.getInstance();
				c.setTimeInMillis(System.currentTimeMillis());
		    	SetToDay();	   
		    	SetMonth();
	            return true;
	        case CHANGEBG:
	        	
	        	Intent intent = new Intent();
	        	intent.setClass(this, Choose_bg.class);
	        	startActivityForResult(intent, 1);
	        	
//	        	Random pickRand = new Random();
//				int numRand;
//				int[] mabg = {R.drawable.thang1,R.drawable.thang2,R.drawable.bg_buble,R.drawable.thang4};
//				numRand = pickRand.nextInt(mabg.length);
//				Sqlite sql = new Sqlite(this);
//				sql.setWallpaper(numRand+1);
//				sql.close();
//				llroot.setBackgroundResource(mabg[numRand]);
	        	return true;
	        case CONTACT:
	        	intent = new Intent();
	        	intent.setClass(this, Intro.class);
	        	startActivity(intent);
	        	return true;
//	        case SETALARM:
//	        	pw.showAtLocation(llroot, Gravity.LEFT | Gravity.TOP, 0, 0);
//	        	return true;
	        case CHOOSE:
	        	LayoutInflater inflater = (LayoutInflater) Main.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	        	int width = display.getWidth();
	        	int height = display.getHeight();
				root = inflater.inflate(R.layout.picker, null, false);
				dp = new DatePick(root,width,height,true, c,new OnClickListener() {
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
							Global.dayAdapter = new DayAdapter(Global.context,Global.dd, Global.mm,Global.yyyy);
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
							Global.dayAdapter = new DayAdapter(Global.context,solar[0], solar[1],solar[2]);
						}
						txt_month.setText("Tháng "+Global.mm+"/"+Global.yyyy);
						c.set(Global.yyyy, Global.mm-1, Global.dd);
						SetToDay();
						SetMonth();
						gridViewItemClicked(Global.dd);
					}
				});
				dp.showAtLocation(llroot,Gravity.CENTER, 0, 0);
	        	
	        	return true;
//	        case WEEKVIEW:
//	        	Intent i = new Intent(Main.this, WeekView.class);
//	        	startActivity(i);
//	        	finish();
//	        	return true;
	        
        }
        return false;
    }

	float x, oldx;
	float currx = 0, tempx;
	int currentview = 0;

	public boolean onTouch(View v, MotionEvent event) {
//		if(v.getId() == btnAddReminder.getId()){
//			if(event.getAction() == MotionEvent.ACTION_DOWN){
//				btnAddReminder.setBackgroundResource(R.drawable.leftside_add_button_highlight);
//			}
//			if(event.getAction() == MotionEvent.ACTION_UP){
//				btnAddReminder.setBackgroundResource(R.drawable.leftside_add_button);
//			}
//		}
		
		if(v.getId() == btnToday.getId()){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				btnToday.setBackgroundResource(R.drawable.add_reminder_button_highlight);
				btnToday.setTextColor(Color.BLACK);
			}
			if(event.getAction() == MotionEvent.ACTION_UP){
				btnToday.setBackgroundResource(R.drawable.add_reminder_button);
				btnToday.setTextColor(Color.WHITE);
			}
		}
//		if(v.getId() == btn_changebg.getId()){
//			if(event.getAction() == MotionEvent.ACTION_DOWN){
//				btn_changebg.setBackgroundResource(R.drawable.button_highlight);
//				btn_changebg.setTextColor(Color.BLACK);
//			}
//			if(event.getAction() == MotionEvent.ACTION_UP){
//				btn_changebg.setBackgroundResource(R.drawable.button_normal);
//				btn_changebg.setTextColor(Color.WHITE);
//			}
			
//		}
//		if (v.getId() == scr.getId()) {
//			int w = getWindowManager().getDefaultDisplay().getWidth();
//			int w = 783;
//			if (event.getAction() == MotionEvent.ACTION_DOWN) {
//				if (currentview == 2) {
//					scr.scrollTo((currentview - 1) * w, 0);
//					currentview -= 1;
//					currx -= w;
//				}
//				if (currentview == 0) {
//					scr.scrollTo((currentview + 1) * w, 0);
//					currentview += 1;
//					currx += w;
//				}
//				oldx = event.getX();
//			}
//			if (event.getAction() == MotionEvent.ACTION_MOVE) {
//				x = event.getX();
//				tempx = currx - x + oldx;
//				if (tempx < 0)
//					tempx = 0;
//				if (tempx > w * 2)
//					tempx = w * 2;
//				scr.smoothScrollTo((int) tempx, 0);
//			}
//			if (event.getAction() == MotionEvent.ACTION_UP) {
//
//				if (Math.abs(currx - tempx) > w / 2) {
//					if (currx > tempx) {
//						if (currentview > 0) {
//							currentview--;
//							scr.smoothScrollTo(currentview * w, 0);
//							currx = currentview * w;
//						}
//					} else {
//						if (currentview < 2) {
//							currentview++;
//							scr.smoothScrollTo(currentview * w, 0);
//							currx = currentview * w;
//						}
//					}
//				} else {
//					scr.smoothScrollTo(currentview * w, 0);
//					currx = currentview * w;
//				}
//			}
//			return true;
//		}
//
		return false;
	}

	DatePick dp;
	public void onClick(View v) {
		if (v.getId() == btnToday.getId()) 
		{
			Global.select_position = -1;
			c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
	    	SetToDay();	   
	    	SetMonth();        	
		}
    	
//		if (v.getId() == btnAddReminder.getId()) 
//		{
//			LinearLayout llroot = (LinearLayout)findViewById(R.id.root);
//			pw.showAtLocation(llroot, Gravity.LEFT | Gravity.TOP, 0, 0);
//		}
		
//		if(v.getId()==btnSearchDate.getId())
//		{
//			LayoutInflater inflater = (LayoutInflater) Main.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			root = inflater.inflate(R.layout.picker, null, false);
//			dp = new DatePick(root,600,350,true, c,new OnClickListener() {
//				public void onClick(View v) {
//					dp.dismiss();
//					if(dp.amduong)
//					{
//						Calendar c = dp.c;
//						int year = c.get(Calendar.YEAR);
//						int day = c.get(Calendar.DATE);
//						int month = c.get(Calendar.MONTH)+1;
//						Global.yyyy = year;
//						Global.mm = month;
//						Global.dd = day;
//						int[] sol = Utils.convertSolar2Lunar(Global.dd, Global.mm, Global.yyyy, 7.0);
//						Global.lunar_dd = sol[0];
//						Global.lunar_mm = String.valueOf(sol[1]);
//						Global.lunar_yyyy = sol[2];
//						Global.select_position = Global.dd;
//						Global.dayAdapter = new DayAdapter(Global.context,Global.dd, Global.mm,Global.yyyy);
//					}
//					else
//					{
//						LunarCalendar lc = dp.lc;
//						int year = lc.get(Calendar.YEAR);
//						int day = lc.get(Calendar.DATE);
//						int month = lc.get(Calendar.MONTH)+1;
//						Global.lunar_yyyy = year;
//						Global.lunar_mm = ""+month+lc.getLeap();
//						Global.lunar_dd = day;
//						int[] solar = Utils.convertLunar2Solar(day, month, year, lc.getLeapi(), 7);
//						Global.dd = solar[0];
//						Global.mm = solar[1];
//						Global.yyyy = solar[2];
//						Global.select_position = Global.dd;
//						Global.dayAdapter = new DayAdapter(Global.context,solar[0], solar[1],solar[2]);
//					}
//					txt_month.setText(Global.mm + "/"+Global.yyyy);
//					c.set(Global.yyyy, Global.mm-1, Global.dd);
//					SetToDay();
//					SetMonth();
//					gridViewItemClicked(Global.dd);
//				}
//			});
//			dp.showAtLocation(llroot, Gravity.TOP|Gravity.LEFT, 0, 0);
//		}
		
//		if(v.getId() == btn_month_week.getId()){
//			if(monthweek){
//				monthweek = false;
//				btn_month_week.setBackgroundResource(R.drawable.toogle_button);
//			}else{
//				monthweek = true;
//				btn_month_week.setBackgroundResource(R.drawable.toogle_buttoninverse);
//			}
//		}
		
//		if(v.getId() == btn_changebg.getId()){
//			Random pickRand = new Random();
//			int numRand;
//			int[] mabg = {R.drawable.thang1,R.drawable.thang2,R.drawable.thang3,R.drawable.thang4};
//			numRand = pickRand.nextInt(mabg.length);
//			llroot.setBackgroundResource(mabg[numRand]);
//		}

	}

	private void gridViewItemClicked(int day)
	{
		int index0=Integer.valueOf(Global.dayAdapter.prodate.DAYS_SOLAR[43]);
		gridViewItemIsClicked(day+index0-1,0);
//		if(Global.select_position==day+index0-1)
//		{
//			Intent i = new Intent(Main.this,WeekView.class);
//			startActivity(i);
//			finish();
//		}
		Global.select_position = day+index0-1;
	}
	
	private void gridViewItemIsClicked(int position,int whatgrid) {
    	//lay thu  begin
    	String thu="";
    	
    	int _thu;
    	if(position>34)
    		_thu=position-35;
    	else if(position>27)
    		_thu=position-28;
    	else if (position>20 && position<=27)
    		_thu=position-21;
    	else if (position>13 && position<=20)
    		_thu=position-14;
    	else if (position>=7 && position<=13)
    		_thu=position-7;
    	else _thu=position;
    	
    	switch (_thu) 
    	{
			case 0:
				thu+=getString(R.string.sunday);
				break;
			case 1:
				thu+=getString(R.string.monday2);
				break;	
			case 2:
				thu+=getString(R.string.tuesday2);
				break;
			case 3:
				thu+=getString(R.string.wednesday2);
				break;
			case 4:
				thu+=getString(R.string.thursday2);
				break;
			case 5:
				thu+=getString(R.string.friday2);
				break;
			case 6:
				thu+=getString(R.string.saturday2);
				break;
			default:
				thu+="Không đúng ngày";
				break;
		}
    	//lay ngay da chon begin	        	
    	
    	int index0=Integer.valueOf(Global.dayAdapter.prodate.DAYS_SOLAR[43]);
    	int index1=Integer.valueOf(Global.dayAdapter.prodate.DAYS_SOLAR[44]);
    	int index_month=Integer.valueOf(Global.dayAdapter.prodate.DAYS_SOLAR[45]);
    	
    	//ngay duong se thuoc thang hien tai
    	int _mMonth=Global.mm;
    	int _mYear=Global.yyyy;
    	//lay ngay duong lich cua thang hien tai
    	int _mDay=Global.dd;        	
    	if(position<index0)	        	
    	{
    		if(index_month==1)
    		{
    			_mYear=Global.yyyy-1;
    			_mMonth=12;
    		}
    		else
    			_mMonth=Global.mm-1;
    	}	        	
    	if(position>=index1)
    	{
    		if(index_month==12)
    		{
    			_mYear=Global.yyyy+1;
    			_mMonth=1;
    		}
    		else
    			_mMonth=Global.mm+1;
    	}
    	
    	_mDay=Integer.valueOf(Global.dayAdapter.prodate.DAYS_SOLAR[position]);
    	Global.selectDate.set(_mYear, _mMonth, _mDay);
    	c.set(Calendar.DATE, _mDay);
    	c.set(Calendar.MONTH, _mMonth-1);
    	c.set(Calendar.YEAR, _mYear);
    	
    	//ngayduong= ngayduong+global.txtviewadapter.prodate.DAYS_SOLAR[position];
    	String thangnamduonglich="";
    	String ngayduonglich=Global.dayAdapter.prodate.DAYS_SOLAR[position];
    	thangnamduonglich="Tháng "+String.valueOf(_mMonth);
    	thangnamduonglich =thangnamduonglich +" Năm "+String.valueOf(_mYear);
    	//ngayduong=ngayduong+" (DL)"+"\n";
    		        	
    	String ngayam=Global.dayAdapter.prodate.DAYS_LUNAR_TEMP[position];	        	
    	String[] ngayamsplit= ngayam.split("/");
    	
    	
    	//tach lay ngay am da chon	        	
    	int[] ngayamint= new int[3];	        	
    	ngayamint[0]=Integer.valueOf(ngayamsplit[0]);//day
    	ngayamint[1]=Integer.valueOf(ngayamsplit[1]);//month
    	ngayamint[2]=Integer.valueOf(ngayamsplit[2]);//year lunar
    	String canchinam = Processdate.GETLUNARYEARNAME(ngayamint[2]);//truyen vao nam am lich
    	String canchithang= Processdate.GETLUNARMONTHCANCHINAME(ngayamint[2],ngayamint[1]);//truyen vao thang nam am lich
    	String canchingay= Processdate.GETLUNARDAYCANCHINAME(_mDay,_mMonth,_mYear);// truyen vao ngay duong lich
    	String thongtinngayam="Âm lịch ";
    	if(Global.dayAdapter.prodate.ISLEAPMONTH(_mDay,_mMonth, _mYear))
    		thongtinngayam += "(Tháng Nhuận)";   
    	
    	//lay nhung ngay dac biet trong nam	        	
    	String specialdays= Utils.getDaySpecialYearlyEvent(_mDay, _mMonth);    	    		
    	String ngayamdacbiet=Utils.getDayYearlyEvent(ngayamint[0], ngayamint[1]);
    	
    	
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
//    	if(ngayamdacbiet!="")
//    		specialdays+="\n";
//    	specialdays +=ngayamdacbiet;
    	
    	String lichxuathanh=LichXuatHanh.getLichXuatHanh(ngayamint[0], ngayamint[1]);
    
    	
    	
    	left_day.setText(thu);
    	left_date.setText(ngayduonglich);
    	left_monthlunar.setText(canchithang);
    	left_datelunar.setText(canchingay);
    	left_monthlunar_number.setText("Tháng "+ ngayamsplit[1]);
    	left_datelunar_number.setText("Ngày " + ngayamsplit[0]);
    	left_specialday.setText(specialdays);
    	left_specialday_lunar.setText(ngayamdacbiet);
    	left_lichxuathanh.setText(lichxuathanh);
    	
    	Global.dayAdapter = new DayAdapter(Global.context, Global.mm,Global.yyyy);

		currentGridView.setAdapter(Global.dayAdapter);
		gridView.setAdapter(Global.dayAdapter);
		nextGridView.setAdapter(Global.dayAdapter);
    }
	
	
	public void SetToDay() {
		Log.d("setToday","setToday");
		Global.yyyy = c.get(Calendar.YEAR);
		Global.mm = c.get(Calendar.MONTH) + 1;
		Global.dd = c.get(Calendar.DATE);
		
		Global.dayAdapter = new DayAdapter(Global.context, Global.mm,Global.yyyy);
		currentGridView = (GridView)viewFlipper.getCurrentView();
		currentGridView.setAdapter(Global.dayAdapter);
	}
	
	public void SetMonth(){
		int[] lunar = Processdate.CONVERTSOLAR2LUNAR(Global.dd,Global.mm, Global.yyyy);
		Global.lunar_dd = lunar[0];
		Global.lunar_mm = String.valueOf(lunar[1]);
		Global.lunar_yyyy = lunar[2];
		String canchithang= Processdate.GETLUNARMONTHCANCHINAME(Global.lunar_yyyy,Integer.valueOf(Global.lunar_mm));//truyen vao thang nam am lich
    	String canchingay= Processdate.GETLUNARDAYCANCHINAME(Global.dd,Global.mm,Global.yyyy);// truyen vao ngay duong lich
    	
    	String specialdays= Utils.getDaySpecialYearlyEvent(Global.dd, Global.mm);    	    		
    	String ngayamdacbiet=Utils.getDayYearlyEvent(Global.lunar_dd, Integer.valueOf(Global.lunar_mm));
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
		
		txt_month.setText("Tháng "+Global.mm+"/"+Global.yyyy);
		
		left_datelunar_number.setText("Ngày "+Global.lunar_dd);
		left_monthlunar_number.setText("Tháng "+Global.lunar_mm);
		left_datelunar.setText(canchingay);
		left_monthlunar.setText(canchithang);
		left_specialday.setText(specialdays);
    	left_specialday_lunar.setText(ngayamdacbiet);
    	left_lichxuathanh.setText(lichxuathanh);
    	
    	//set mau ngay le
    	
    	
    	
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if(parent.getId() == currentGridView.getId()){
			Global.select_position = position;
			gridViewItemIsClicked(position,0);
		
		}
		if(parent.getId() == gridView.getId()){
			Global.select_position = position;
			gridViewItemIsClicked(position,1);
			
		}
		if(parent.getId() == nextGridView.getId()){
			Global.select_position = position;
			gridViewItemIsClicked(position,2);
			
		}
		
	}
	
	public void NextMonth(){
    	if(Global.mm<12){
    		Global.mm++;
    	}
    	else{
    		Global.mm=1;
    		Global.yyyy++;
    	}    	
    }
    public void PrevMonth(){
    	if(Global.mm>1){
    		Global.mm--;
    	}
    	else{
    		Global.mm=12;
    		Global.yyyy--;
    	}
    }
    
    public void nextYear(){   	
    	Global.yyyy++;
    }
    
    public void prevYear(){
    	Global.yyyy--;
    }
    
    private void gridViewAnim(int moveDirection, long durationAnimButtonClick2) {
    	Animation moveAnimation = null;
    	if (moveDirection == 1) { //Phải sang trái
    		NextMonth();
    		moveAnimation = AnimationUtils.loadAnimation(Main.this, R.anim.slide_left);
    		Global.select_position = -1;
    	}
    	else  if (moveDirection == 2){ //Trái sang phải
    		PrevMonth();
    		moveAnimation = AnimationUtils.loadAnimation(Main.this, R.anim.slide_right);
    		Global.select_position = -1;
    	}else  if (moveDirection == 3){ //Trên xuống dưới
    		prevYear();
    		moveAnimation = AnimationUtils.loadAnimation(Main.this, R.anim.slide_up);
    		Global.select_position = -1;
    	}
    	else  { //Dưới lên trên
    		nextYear();
    		moveAnimation = AnimationUtils.loadAnimation(Main.this, R.anim.slide_down);
    		Global.select_position = -1;
    	}
    	

//		SetMonth();
    	txt_month.setText("Tháng "+Global.mm+"/"+Global.yyyy);
    	Log.d("Global.yyyy",""+Global.yyyy);
		currentGridView = (GridView)viewFlipper.getCurrentView();
		GridView nextGridView;
		if (currentGridView.equals(gridView)) {
			nextGridView = this.nextGridView;
		}
		else {
			nextGridView = gridView;
		}
		
		
		Global.dayAdapter= new DayAdapter(Global.context,Global.mm,Global.yyyy);						
		nextGridView.setAdapter(Global.dayAdapter);        
		
		moveAnimation.setDuration(durationAnimButtonClick2);
		viewFlipper.setInAnimation(moveAnimation); 
		

		System.out.println("Show animation");
		viewFlipper.showNext(); 
    }
    
//    @Override
//    protected void onPause() {
//    	super.onPause();
//    	Alarm.context = null;
//    	Global.context = null;
//    	Log.d("onPause","onPause");
//    }
//    public void onResume()
//    {
//    	super.onResume();
//    	Alarm.context = this;
//    	Global.context = this;
//    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == 1){
    		
    		//setBackground
    		if(Global.your_bg != null){
    			llroot.setBackgroundDrawable(Global.your_bg);	
    		}else{
	    		Sqlite sql = new Sqlite(this);
	    		Log.d("sql.getWallpaper",""+sql.getWallpaper());
	    		switch (sql.getWallpaper()) {
	    		case 1:
	    			llroot.setBackgroundResource(R.drawable.wallpaper_color_01);
	    			break;
	    		case 2:
	    			llroot.setBackgroundResource(R.drawable.wallpaper_color_02);
	    			break;
	    		case 3:
	    			llroot.setBackgroundResource(R.drawable.wallpaper_color_03);
	    			break;
	    		case 4:
	    			llroot.setBackgroundResource(R.drawable.wallpaper_color_04);
	    			break;
	    		case 5:
	    			llroot.setBackgroundResource(R.drawable.wallpaper_color_05);
	    			break;
	    		case 6:
	    			llroot.setBackgroundResource(R.drawable.wallpaper_color_06);
	    			break;
	    		case 7:
	    			llroot.setBackgroundResource(R.drawable.wallpaper_color_07);
	    			break;
	    		default:
	    			break;
	    		}
	    		sql.close();
    		}
    	}
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	// TODO Auto-generated method stub
    	super.onConfigurationChanged(newConfig);
    	Log.d("config","config");
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	}

}