package chau.nguyen;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import chau.nguyen.calendar.ui.ScrollableMonthView;
import chau.nguyen.calendar.ui.MonthView.OnDateSelectedListener;
import chau.nguyen.calendar.ui.ScrollableMonthView.OnDateChangedListener;
import chau.nguyen.calendar.widget.HorizontalScrollView;
import chau.nguyen.calendar.widget.HorizontalScrollView.OnScreenSelectedListener;
import chau.nguyen.managers.BackgroundManager;

public class VNMMonthActivity extends Activity {
	public static final String SELECTED_DATE_RETURN = "selectedDateReturn";
	private static int MENU_DAY_VIEW = 1;
	//private static int MENU_SETTINGS = 2;		
	
	private HorizontalScrollView scrollView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        BackgroundManager.init(this);
        
        this.scrollView = new HorizontalScrollView(this);
        this.setContentView(this.scrollView);
        
        this.scrollView.setOnScreenSelectedListener(new OnScreenSelectedListener() {
			public void onSelected(int selectedIndex) {				
				prepareOtherViews(selectedIndex);
			}
        });
                      
        showDate(new Date());
    }
    
    private void showDate(Date date) {
    	int childCount = this.scrollView.getChildCount(); 
		for (int i = 0; i < 3 - childCount; i++) {
			ScrollableMonthView view = new ScrollableMonthView(this);
			view.setOnDateChangedListener(onDateChangedListener);
			view.setOnDateSelectedListener(onDateSelectedListener);		
			this.scrollView.addView(view);		
		}
    	
    	ScrollableMonthView previousView = (ScrollableMonthView)this.scrollView.getChildAt(0);
		previousView.setDate(addMonths(date, -1));
		 
		ScrollableMonthView currentView = (ScrollableMonthView)this.scrollView.getChildAt(1);
		currentView.setDate(date);
		 
		ScrollableMonthView nextView = (ScrollableMonthView)this.scrollView.getChildAt(2);
		nextView.setDate(addMonths(date, +1));
		         
		this.scrollView.showScreen(1);
    }
    
    protected void prepareOtherViews(int selectedIndex) {
    	ScrollableMonthView currentView = (ScrollableMonthView)this.scrollView.getChildAt(selectedIndex);
    	Date currentDate = currentView.getDate();
    	if (selectedIndex == 0) {
    		// remove last view, add new view at the beginning
    		ScrollableMonthView previousView = (ScrollableMonthView)this.scrollView.getChildAt(2);
    		previousView.setDate(addMonths(currentDate, -1));
    		this.scrollView.rotateLastView();
    	} else if (selectedIndex == 2) {
    		// remove first view, append new view at the end
    		ScrollableMonthView nextView = (ScrollableMonthView)this.scrollView.getChildAt(0);
    		nextView.setDate(addMonths(currentDate, +1));    		
    		this.scrollView.rotateFirstView();
    	}
	}
    
    private Date addMonths(Date date, int months) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	cal.add(Calendar.MONTH, months);
    	return cal.getTime();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_DAY_VIEW, 0, "Xem theo ngày").setIcon(android.R.drawable.ic_menu_day);
    	//menu.add(0, MENU_SETTINGS, 0, "Tùy chọn").setIcon(android.R.drawable.ic_menu_preferences);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == MENU_DAY_VIEW) {
    		this.finish();
    	}    	
    	return true;
    }
    
	private OnDateChangedListener onDateChangedListener = new OnDateChangedListener() {
		public void onDateChanged(Date date) {
			ScrollableMonthView previousView = (ScrollableMonthView)scrollView.getChildAt(0);
			previousView.setDate(addMonths(date, -1));

    		ScrollableMonthView nextView = (ScrollableMonthView)scrollView.getChildAt(2);
    		nextView.setDate(addMonths(date, +1));			
		}		
	};
	
	private OnDateSelectedListener onDateSelectedListener = new OnDateSelectedListener() {
		public void onDateSelected(Date date) {
			Intent data = new Intent();			
	    	data.putExtra(SELECTED_DATE_RETURN, date.getTime());
	    	setResult(RESULT_OK, data);
	    	finish();
		}		
	};
}
