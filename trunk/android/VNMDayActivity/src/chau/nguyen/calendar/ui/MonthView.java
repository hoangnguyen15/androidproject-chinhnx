package chau.nguyen.calendar.ui;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import chau.nguyen.EventManager;
import chau.nguyen.R;
import chau.nguyen.calendar.ui.MonthViewRenderer.Config;

public class MonthView extends View {	
	private Date date;
	private EventManager eventManager;
	
	MonthViewRenderer.Config config;
	MonthViewRenderer renderer;
	
	private float lastMotionX;
	private float lastMotionY;
	private int touchSlop;
		
	private OnDateSelectedListener onDateSelectedListener;
	
	public MonthView(Context context) {
		this(context, null);
	}
	
	public MonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
				
		this.eventManager = new EventManager(context.getContentResolver());
		this.date = new Date();				
		
		config = new Config();
		config.date = this.date;
		config.cellBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_bg);
		config.cellEventBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.event_cell_bg);
		config.headerBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_header_bg);
		config.cellHighlightBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_highlight_bg);
		config.titleBackground = config.cellBackground;
		config.titleTextColor = context.getResources().getColor(R.color.titleColor);
		config.headerTextColor = context.getResources().getColor(R.color.headerColor);
		config.dayColor = context.getResources().getColor(R.color.dayColor);
		config.todayColor = config.dayColor;
		config.dayOfWeekColor = context.getResources().getColor(R.color.dayOfWeekColor);
		config.weekendColor = context.getResources().getColor(R.color.weekendColor);
		config.holidayColor = context.getResources().getColor(R.color.holidayColor);
		config.otherDayColor = context.getResources().getColor(R.color.otherDayColor);		
		renderer = new MonthViewRenderer(config, this.eventManager);
		
		this.setDrawingCacheEnabled(true);
		
		ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop() * 5;
	}	
	
	public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
		this.onDateSelectedListener = onDateSelectedListener;
	}
	
	private int guessDaySelected(float x, float y) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.getTime();
		float cellHeight = getHeight() / 8;
		float cellWidth = getWidth() / 7;
		int xOrder = (int) (x / cellWidth) + 1;
		int yOrder = (int) (y / cellHeight) - 2; // title & day of week row
		int leadSpaces = MonthViewRenderer.getDayOfWeekVNLocale(cal.get(Calendar.DAY_OF_WEEK)) - 1;
		int dayOfMonth = 7 * yOrder + xOrder - leadSpaces;
		int daysInMonth = MonthViewRenderer.dom[cal.get(Calendar.MONTH)];
		if (cal.get(Calendar.YEAR) % 4  == 0) dayOfMonth++;
		if (dayOfMonth <= 0 || dayOfMonth > daysInMonth) {
			return 0;
		} else { 
			return dayOfMonth;
		}
	}
	
	public void setDate(Date date) {
		this.date = date;		
		this.config.date = date;
		this.invalidate();
	}
	
	public Date getDate() {
		return date;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
//		Bitmap cacheBitmap = getDrawingCache();
//		if (cacheBitmap != null) {			
//			canvas.drawColor(0, Mode.CLEAR);			
//			canvas.drawBitmap(cacheBitmap, 0, 0, null); 
//		} else {
			renderer.render(canvas);
//		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int action = e.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			lastMotionX = e.getX();
			lastMotionY = e.getY();
		} else if (action == MotionEvent.ACTION_UP) {
			int deltaX = (int)Math.abs(e.getX() - lastMotionX);
			int deltaY = (int)Math.abs(e.getY() - lastMotionY);
			if (deltaX < touchSlop && deltaY < touchSlop) {
				onSingleTapUp(e);
				return false;
			}
		}
		return true;
	}
	
	private void onSingleTapUp(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		int selectedDayOfMonth = guessDaySelected(x, y);
		if (selectedDayOfMonth != 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);			
			config.selectedDate = cal.getTime();
			invalidate();
			
			if (onDateSelectedListener != null) {
				onDateSelectedListener.onDateSelected(config.selectedDate);
			}
		}
	}
	
	public interface OnDateSelectedListener {
		void onDateSelected(Date date);
	}		
}
