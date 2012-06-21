package chau.nguyen;

import java.util.Calendar;
import java.util.Date;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import chau.nguyen.calendar.VietCalendar;
import chau.nguyen.calendar.ui.ScrollableDayView;
import chau.nguyen.calendar.ui.VNMDatePickerDialog;
import chau.nguyen.calendar.ui.ScrollableDayView.OnDateChangedListener;
import chau.nguyen.calendar.widget.HorizontalScrollView;
import chau.nguyen.calendar.widget.HorizontalScrollView.OnScreenSelectedListener;
import chau.nguyen.managers.BackgroundManager;

public class VNMDayActivity extends Activity {
	public static final String SELECTED_DATE = "SELECTED_DATE";
	public static final int SELECT_DATE = 1;
	
	private static final int MENU_SELECT_DATE = 1;
	private static final int MENU_SELECT_TODAY = 2;
	private static final int MENU_MONTH_VIEW = 3;
	private static final int MENU_DAY_INFO = 4;
	private static final int MENU_ADD_EVENT = 5;
	private static final int MENU_ABOUT = 6;
	//private static int MENU_SETTINGS = 4;
	public static final int DATE_DIALOG_ID = 0;
	public static final int ABOUT_DIALOG_ID = 1;
	
	private HorizontalScrollView scrollView;
	private Date selectedDate;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        BackgroundManager.init(this);                
        
        //Make a experiment with AdMob
        //AdManager.setTestDevices(new String[] {"0BE539087867A431F09D246AEDB36993"}); //My DroidX phone ^^
       // AdManager.setTestDevices( new String[] { "781D3C6A96B86CCC045A4D2EB93E74DC" } ); //Nexus one
        
        this.scrollView = new HorizontalScrollView(this);
        this.setContentView(R.layout.main);
        LinearLayout main = (LinearLayout)findViewById(R.id.main);
        
        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
        main.addView(this.scrollView, layoutParams1);
        
        //AdView adView = new AdView(this);
        //LayoutParams  layoutParams2 = new LayoutParams(
		//		LayoutParams.FILL_PARENT,
		//		LayoutParams.WRAP_CONTENT);  
		
		//main.addView(adView, layoutParams2);
        //adView.requestFreshAd();
		
        this.scrollView.setOnScreenSelectedListener(new OnScreenSelectedListener() {
			public void onSelected(int selectedIndex) {
				prepareOtherViews(selectedIndex);				
			}
        });        
        //Log.d("DEBUG", StreamUtils.readAllText(getResources().openRawResource(R.raw.test)));
                
        showDate(new Date());
    }
    
    private void showDate(Date date) {
    	this.selectedDate = date;
    	int childCount = this.scrollView.getChildCount(); 
		for (int i = 0; i < 3 - childCount; i++) {
			ScrollableDayView view = new ScrollableDayView(this);
	    	view.setOnDateChangedListener(onDateChangedListener);
	    	view.setOnClickListener(onClickListener);
			//view.setBackgroundDrawable(BackgroundManager.getRandomBackground());
			this.scrollView.addView(view);		
		}
    	    	
		ScrollableDayView previousView = (ScrollableDayView)this.scrollView.getChildAt(0);	    	
		previousView.setDate(addDays(date, -1));
		 
		ScrollableDayView currentView = (ScrollableDayView)this.scrollView.getChildAt(1);			
		currentView.setDate(date);
		 
		ScrollableDayView nextView = (ScrollableDayView)this.scrollView.getChildAt(2);
		nextView.setDate(addDays(date, 1));
    	
		this.scrollView.showScreen(1);
    }
    
    protected void prepareOtherViews(int selectedIndex) {
    	ScrollableDayView currentView = (ScrollableDayView)this.scrollView.getChildAt(selectedIndex);
    	Date currentDate = currentView.getDate();
    	if (selectedIndex == 0) {    		
    		// remove last view, add new view at the beginning
    		ScrollableDayView previousView = (ScrollableDayView)this.scrollView.getChildAt(2);
    		previousView.setDate(addDays(currentDate, -1));    		    	
    		this.scrollView.rotateLastView();
    	} else if (selectedIndex == 2) {
    		// remove first view, append new view at the end
    		ScrollableDayView nextView = (ScrollableDayView)this.scrollView.getChildAt(0);
    		nextView.setDate(addDays(currentDate, +1));    					    		
    		this.scrollView.rotateFirstView();
    	}
	}
    
    private Date addDays(Date date, int days) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, days);
    	return cal.getTime();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_DAY_INFO, 0, "Thông tin").setIcon(android.R.drawable.ic_menu_info_details);
    	menu.add(0, MENU_ADD_EVENT, 0, "Thêm sự kiện").setIcon(android.R.drawable.ic_menu_add);
    	menu.add(0, MENU_MONTH_VIEW, 0, "Xem tháng").setIcon(android.R.drawable.ic_menu_month);
    	menu.add(0, MENU_SELECT_DATE, 0, "Chọn ngày").setIcon(android.R.drawable.ic_menu_day);
    	menu.add(0, MENU_SELECT_TODAY, 0, "Hôm nay").setIcon(android.R.drawable.ic_menu_today);    	    	
    	menu.add(0, MENU_ABOUT, 0, "Giới thiệu").setIcon(android.R.drawable.ic_menu_help);
    	//menu.add(0, MENU_SETTINGS, 0, "Tùy chọn").setIcon(android.R.drawable.ic_menu_preferences);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == MENU_MONTH_VIEW) {
    		showMonthView();
    	} else if (item.getItemId() == MENU_SELECT_DATE) {
    		selectDate();
    	} else if (item.getItemId() == MENU_SELECT_TODAY) {
    		showDate(new Date());
    	} else if (item.getItemId() == MENU_DAY_INFO) {
    		showDayInfo();
    	} else if (item.getItemId() == MENU_ADD_EVENT) {
    		addEvent();    		
    	} else if (item.getItemId() == MENU_ABOUT) {
    		showDialog(ABOUT_DIALOG_ID);
    	}
    	return true;
    }    	
	
	public void selectDate() {
		showDialog(DATE_DIALOG_ID);
	}
	
	public void showMonthView() {
		Intent monthIntent = new Intent(this, VNMMonthActivity.class);
		startActivityForResult(monthIntent, SELECT_DATE);
	}	
	
	public void showDayInfo() {
		Intent dayInfoIntent = new Intent(this, DayInfoActivity.class);
		dayInfoIntent.putExtra("Date", getCurrentDate().getTime());
		startActivity(dayInfoIntent);
	}
	
	public void addEvent() {		
		Intent intent = new Intent(this, VNMEventDetailsActivity.class);
		String selectedDate = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		selectedDate = cal.get(Calendar.DAY_OF_MONTH) + "," + cal.get(Calendar.MONTH) + "," + cal.get(Calendar.YEAR);
		intent.putExtra(SELECTED_DATE, selectedDate);
		startActivity(intent);
		
	}
	
	private Date getCurrentDate() {
		int selectedIndex = this.scrollView.getDisplayedChild();
		ScrollableDayView currentView = (ScrollableDayView)this.scrollView.getChildAt(selectedIndex);
    	return currentView.getDate();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SELECT_DATE && resultCode == RESULT_OK) {			
			long result = data.getLongExtra(VNMMonthActivity.SELECTED_DATE_RETURN, 0);
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(result);
			showDate(cal.getTime());			
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    	case DATE_DIALOG_ID:
	    		Calendar cal = Calendar.getInstance();
	    		cal.setTime(getCurrentDate());
	    		//return new DatePickerDialog(this, null, 
	    		//		cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	    		return new VNMDatePickerDialog(this, mDateSetListener, 
	    				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	    	case ABOUT_DIALOG_ID:
	    		AlertDialog.Builder builder;
	    		AlertDialog aboutDialog;

	    		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
	    		View layout = inflater.inflate(R.layout.about,
	    		                               (ViewGroup) findViewById(R.id.layoutRoot));
	    		TextView version = (TextView)layout.findViewById(R.id.versionText);
	    		try {
	    			ComponentName comp = new ComponentName(this, VNMDayActivity.class);
		    	    PackageInfo pinfo = this.getPackageManager().getPackageInfo(comp.getPackageName(), PackageManager.GET_ACTIVITIES);
		    	    version.setText("Phiên bản: " + pinfo.versionName);
				} catch (Exception e) {
					version.setText("Phiên bản: 1.1");
				}
	    		

	    		
	    		builder = new AlertDialog.Builder(this);
	    		builder.setView(layout);
	    		aboutDialog = builder.create();
	    		aboutDialog.setTitle("Lịch Việt");
	    		aboutDialog.setIcon(R.drawable.icon);
	    		return aboutDialog;
	    }
	    return null;
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			showDayInfo();
		}		
	};
	
	private OnDateChangedListener onDateChangedListener = new OnDateChangedListener() {
		public void onDateChanged(Date date) {
			ScrollableDayView previousView = (ScrollableDayView)scrollView.getChildAt(0);
			previousView.setDate(addDays(date, -1));

    		ScrollableDayView nextView = (ScrollableDayView)scrollView.getChildAt(2);
    		nextView.setDate(addDays(date, +1));			
		}		
	};
	
	private VNMDatePickerDialog.OnDateSetListener mDateSetListener = new VNMDatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, boolean isSolarSelected, int year, int monthOfYear,
				int dayOfMonth) {			
			Calendar cal = Calendar.getInstance();						
			if (isSolarSelected) {
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, monthOfYear);
				cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				showDate(cal.getTime());								
			} else {
				int[] solar = VietCalendar.convertLunar2Solar(dayOfMonth, monthOfYear + 1, year);				
				cal.set(Calendar.DAY_OF_MONTH, solar[VietCalendar.DAY]);
				cal.set(Calendar.MONTH, solar[VietCalendar.MONTH] - 1);
				cal.set(Calendar.YEAR, solar[VietCalendar.YEAR]);
				showDate(cal.getTime());				
			}
		}
	};
}