package chau.nguyen.calendar.ui;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import chau.nguyen.calendar.widget.VerticalScrollView;
import chau.nguyen.managers.BackgroundManager;

public class ScrollableDayView extends VerticalScrollView {
	private OnDateChangedListener onDateChangedListener;
	private OnClickListener onClickListener;
	
	public ScrollableDayView(Context context) {
		super(context);		
		this.setOnScreenSelectedListener(onScreenSelectedListener);
	}
	
	public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
		this.onDateChangedListener = onDateChangedListener;
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		this.onClickListener = l;
	}

	public void setDate(Date date) {
		int childCount = this.getChildCount(); 
		for (int i = 0; i < 3 - childCount; i++) {
			DayView dayView = new DayView(getContext());
			dayView.setOnClickListener(onClickListener);
			dayView.setBackgroundDrawable(BackgroundManager.getRandomBackground());
			this.addView(dayView);			
		}
		
												
		DayView previousView = (DayView)getChildAt(0);
		previousView.setDate(addMonths(date, -1));
		
		DayView currentView = (DayView)getChildAt(1);
		currentView.setDate(date);
		
		DayView nextView = (DayView)getChildAt(2);
		nextView.setDate(addMonths(date, 1));
				
		this.setOnScreenSelectedListener(null);
		this.showScreen(1);
		this.setOnScreenSelectedListener(onScreenSelectedListener);
	}
	
	public Date getDate() {
		DayView currentView = (DayView)getChildAt(1);
		return currentView.getDisplayDate();
	}
	
	protected void prepareOtherViews(int selectedIndex) {
    	DayView currentView = (DayView)this.getChildAt(selectedIndex);
    	Date currentDate = currentView.getDisplayDate();    	
    	if (selectedIndex == 0) {
    		// remove last view, add new view at the beginning
    		DayView previousView = (DayView)this.getChildAt(2);    		
    		previousView.setDate(addMonths(currentDate, -1));
    		previousView.setBackgroundDrawable(BackgroundManager.getRandomBackground());
    		this.rotateLastView();
    		
    	} else if (selectedIndex == 2) {    		
    		// remove first view, append new view at the end
    		DayView nextView = (DayView)getChildAt(0);    		
    		nextView.setDate(addMonths(currentDate, +1));
    		nextView.setBackgroundDrawable(BackgroundManager.getRandomBackground());    		
    		this.rotateFirstView();
    	}
    	
    	if (this.onDateChangedListener != null) {
    		this.onDateChangedListener.onDateChanged(currentDate);
    	}
	}
	
	private Date addMonths(Date date, int months) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.MONTH, months);
    	return cal.getTime();
    }
	
	private OnScreenSelectedListener onScreenSelectedListener = new OnScreenSelectedListener() {
		public void onSelected(int selectedIndex) {				
			prepareOtherViews(selectedIndex);
		}
    };
	
	public interface OnDateChangedListener {
		void onDateChanged(Date date);
	}
}
