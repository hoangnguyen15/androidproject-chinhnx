package chau.nguyen.calendar.ui;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
import chau.nguyen.EventManager;
import chau.nguyen.calendar.VietCalendar;
import chau.nguyen.calendar.VietCalendar.Holiday;
import chau.nguyen.calendar.util.StreamUtils;

public class MonthViewRenderer {
	Config config;
	private EventManager eventManager;
	
	public final static int dom[] = { 
		31, 28, 31, /* jan, feb, mar */
		30, 31, 30, /* apr, may, jun */
		31, 31, 30, /* jul, aug, sep */
		31, 30, 31 /* oct, nov, dec */
	};	
	
	private final static String dow[] = {
		"Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy", "CN"
	};
	
	public MonthViewRenderer(Config config, EventManager eventManager) {
		this.eventManager = eventManager;
		this.config = config;
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.config.date);
	}
	
	public void render(Canvas canvas) {		
		if (config.autoCalculateOffsets) {
			config.calculate(canvas.getWidth(), canvas.getHeight());
		}
		if (config.background != null) {			
			config.background.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			config.background.draw(canvas);
		}
		
		int leadSpaces = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(config.date);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		
		calendar.add(Calendar.MONTH, -1);
		int prevMonth = calendar.get(Calendar.MONTH);
		int prevYear = calendar.get(Calendar.YEAR);
		int prevDaysInMonth = dom[prevMonth];
		if (prevMonth == 1 && prevYear % 4 == 0) {
			prevDaysInMonth++;
		}
		
		calendar.add(Calendar.MONTH, 2);
		int nextMonth = calendar.get(Calendar.MONTH);
		int nextYear = calendar.get(Calendar.YEAR);				
		
		if (eventManager != null) {
			this.eventManager.setMonth(month, year);
		}
		
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		// Compute how much to leave before before the first day of the month.
		// getDay() returns 0 for Sunday.
		leadSpaces = getDayOfWeekVNLocale(cal.get(Calendar.DAY_OF_WEEK)) - 1;
		// total days in month
		int daysInMonth = dom[month];

		if (cal.isLeapYear(cal.get(Calendar.YEAR)) && month == 1) {
			++daysInMonth;
		}				
	       
		Calendar todayCal = Calendar.getInstance();
		int todayYear = todayCal.get(Calendar.YEAR);
		int todayMonth = todayCal.get(Calendar.MONTH);
		int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
		
		int count = 1;	       
		for (int i = 0; i < 8; i++) {
			if (i == 0) {
    		   drawTitle(canvas, config.titleOffsetX, config.titleOffsetY, config.titleWidth, config.titleHeight, month, year);
			} else if (i == 1) {
			   if (config.renderHeader) {
	    		   for (int j = 0; j < 7; j++) {
	    			   drawHeader(canvas, config.headerOffsetX + j * config.headerWidth, config.headerOffsetY, config.headerWidth, config.headerHeight, j);
	    		   }
			   }
			} else if (i == 2) {    		   
    		   for (int j = 0; j < 7 && count <= daysInMonth; j++) {    			          		   
    			   if (j >= leadSpaces) {
    				   boolean highlight = isSameDate(todayYear, todayMonth, todayDay, year, month, count);
    				   boolean hasEvent = this.eventManager != null && this.eventManager.hasEvent(count, month, year);
    				   drawCellContent(canvas, config.cellOffsetX + j * config.cellWidth, config.cellOffsetY + (i - 2) * config.cellHeight, config.cellWidth, config.cellHeight, 
    						   count, month + 1, year, j, highlight, hasEvent, false);
    				   count++;
    			   } else {    				   
    				   drawCellContent(canvas, config.cellOffsetX + j * config.cellWidth, config.cellOffsetY + (i - 2) * config.cellHeight, config.cellWidth, config.cellHeight, 
    						   prevDaysInMonth - (leadSpaces - (j + 1)), prevMonth + 1, prevYear, j, false, false, true);   
    			   }
    		   }
			} else {
    		   for (int j = 0; j < 7; j++) {
    			   if (count <= daysInMonth) {
    				   boolean highlight = isSameDate(todayYear, todayMonth, todayDay, year, month, count);
    				   boolean hasEvent = this.eventManager != null && this.eventManager.hasEvent(count, month, year);
    				   drawCellContent(canvas, config.cellOffsetX + j * config.cellWidth, config.cellOffsetY + (i - 2) * config.cellHeight, config.cellWidth, config.cellHeight, count, month + 1, year, j, highlight, hasEvent, false);
    			   } else {
    				   drawCellContent(canvas, config.cellOffsetX + j * config.cellWidth, config.cellOffsetY + (i - 2) * config.cellHeight, config.cellWidth, config.cellHeight, count - daysInMonth, nextMonth + 1, nextYear, j, false, false, true);
    			   }
    			   count++;
        	   }
			}
		}
	}
	
	private void drawTitle(Canvas canvas, int cellX, int cellY, int cellWidth, int cellHeight, int month, int year) {
		Paint paint = new Paint();
		if (config.enableShadow) {
			paint.setShadowLayer(1, 0, 0, config.titleTextShadowColor);
		}
		paint.setColor(config.titleTextColor);
		paint.setTextAlign(Align.CENTER);
		paint.setAntiAlias(true);
		paint.setTextSize(config.titleTextSize);		
		if (config.titleBackground != null) {
			paint.setDither(true);
			canvas.drawBitmap(config.titleBackground, new Rect(0, 0, config.titleBackground.getWidth(), config.titleBackground.getHeight()),
					new Rect(cellX + 1, cellY + 1, cellX + cellWidth, cellY + cellHeight), paint);
		}
		Rect textBounds = new Rect();
		paint.getTextBounds("Tháng", 0, 1, textBounds);
		float x = cellX + cellWidth / 2;
		float y = cellY + cellHeight - (cellHeight - textBounds.height()) / 2;		
		canvas.drawText("Tháng " + (month + 1) + " - " + year, x, y, paint);
	}
	
	private void drawHeader(Canvas canvas, int cellX, int cellY, int cellWidth, int cellHeight, int j) {
		Paint paint = new Paint();
		if (config.enableShadow) {
			paint.setShadowLayer(1, 0, 0, config.headerTextShadowColor);
		}
		paint.setColor(config.headerTextColor);
		paint.setTextAlign(Align.CENTER);
		paint.setAntiAlias(true);
		paint.setTextSize(config.headerTextSize);
		if (config.headerBackground != null) {
			paint.setDither(true);
			canvas.drawBitmap(config.headerBackground, new Rect(0, 0, config.headerBackground.getWidth(), config.headerBackground.getHeight()),
				new Rect(cellX + 1, cellY + 1, cellX + cellWidth, cellY + cellHeight), paint);
		}
		Rect textBounds = new Rect();
		paint.getTextBounds("Bảy", 0, 2, textBounds);
		
		float x = cellX + cellWidth / 2;
		float y = cellY + cellHeight - (cellHeight - textBounds.height()) / 2;
		if (j == 6) {
			paint.setColor(config.weekendColor);
			if (config.enableShadow) {
				paint.setShadowLayer(1, 0, 0, config.weekendShadowColor);
			}
		}	
		canvas.drawText(dow[j], x, y, paint);
	}
		
	private void drawCellContent(Canvas canvas, int cellX, int cellY, int cellWidth, int cellHeight, 
			int day, int month, int year, int dayOfWeek, boolean highlight, boolean hasEvent, boolean otherMonth) {		
		Paint paint = new Paint();					
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);		
		paint.setTextSize(config.cellMainTextSize);
		paint.setDither(true);
		
		if (config.selectedDate != null && isSameDate(config.selectedDate.getYear() + 1900, 
				config.selectedDate.getMonth(), 
				config.selectedDate.getDate(), 
				year, month - 1, day)) {
			highlight = true;
		}
		
		Bitmap bitmap = highlight ? config.cellHighlightBackground : config.cellBackground;
		if (bitmap != null) {
			Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			Rect destRect = new Rect(cellX + 1, cellY + 1,  cellX + cellWidth, cellY + cellHeight);
			canvas.drawBitmap(bitmap, srcRect, destRect, paint);			
		}
		if (hasEvent && config.cellEventBackground != null) {
			Bitmap eventBitmap = config.cellEventBackground;
			Rect srcRect = new Rect(0, 0, eventBitmap.getWidth(), eventBitmap.getHeight());
			Rect eventRect = new Rect(cellX + 4, cellY + 4,  cellX + 10, cellY + cellHeight/4);
			canvas.drawBitmap(eventBitmap, srcRect, eventRect, paint);
		}
		
		float x = cellX + cellWidth / 2;
		float y = cellY + cellHeight / 2;
		
		if (day > 0) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DAY_OF_MONTH, day);
			int[] lunars = VietCalendar.convertSolar2LunarInVietnam(cal.getTime());
			Holiday holiday = VietCalendar.getHoliday(cal.getTime());
			if (holiday != null && holiday.isSolar() && holiday.isOffDay()) {
				paint.setColor(config.holidayColor);
				if (config.enableShadow) {
					paint.setShadowLayer(1, 0, 0, config.holidayShadowColor);
				}
			} else {
				if (dayOfWeek == 6) {
					paint.setColor(config.weekendColor);
					if (config.enableShadow) {
						paint.setShadowLayer(1, 0, 0, config.weekendShadowColor);
					}
				} else {			
					paint.setColor(highlight ? config.todayColor : config.dayColor);
					if (config.enableShadow) {
						paint.setShadowLayer(1, 0, 0, highlight ? config.todayShadowColor : config.dayShadowColor);
					}
				}
			}			
			if (otherMonth) {
				paint.setColor(config.otherDayColor);
			}
			canvas.drawText("" + day, x, y, paint);

			if (dayOfWeek == 6) {
				paint.setColor(config.weekendColor);
			} else {
				paint.setColor(config.dayColor);
			}
			paint.setColor(otherMonth ? config.otherDayColor : config.dayColor);

			if (config.enableShadow) {
				paint.setShadowLayer(1, 0, 0, otherMonth ? config.otherDayShadowColor : config.dayShadowColor);
			}
			paint.setTextSize(config.cellSubTextSize);
			paint.setTextAlign(Align.RIGHT);
			if (holiday != null && !holiday.isSolar() && holiday.isOffDay()) {
				paint.setColor(config.holidayColor);
			}
			if (lunars[VietCalendar.DAY] == 1) {
				canvas.drawText(lunars[VietCalendar.DAY] + "/" + lunars[VietCalendar.MONTH], cellX + cellWidth - 5, y + config.cellSubTextSize + 2, paint);
			} else {
				canvas.drawText(lunars[VietCalendar.DAY] + "", cellX + cellWidth - 5, y + config.cellSubTextSize + 2, paint);
			}
		}
	}		
	
	public static int getDayOfWeekVNLocale(int dayOfWeekUSLocale) {
		if (dayOfWeekUSLocale == 1) return 7;
		else return dayOfWeekUSLocale - 1;
	}

	public static boolean isSameDate(int year1, int month1, int day1, int year2, int month2, int day2) {
		return year1 == year2 && month1 == month2 && day1 == day2;
	}	
	
	public static class Config {
		public Date date;
		public Date selectedDate;
		public boolean autoCalculateOffsets = true;
		public boolean enableShadow = false;
		
		public int width;
		public int height;		
		public Drawable background = null;
		
		public int titleOffsetX;
		public int titleOffsetY;
		public int titleWidth;
		public int titleHeight;
		public int titleTextSize = 25;
		public int titleTextColor = 0;
		public int titleTextShadowColor = 0;
		public Bitmap titleBackground;
		
		public boolean renderHeader = true;
		public int headerOffsetX;
		public int headerOffsetY;
		public int headerWidth;
		public int headerHeight;
		public int headerTextSize = 18;
		public int headerTextColor = 0;
		public int headerTextShadowColor = 0;
		public Bitmap headerBackground;
		
		public int cellOffsetX;
		public int cellOffsetY;
		public int cellWidth;
		public int cellHeight;
		public int cellMainTextSize = 25;
		public int cellSubTextSize = 14;
		public Bitmap cellBackground;
		public Bitmap cellEventBackground;
		public Bitmap cellHighlightBackground;
		public Drawable selectedCellDrawable;
				
		public int dayColor = 0;
		public int dayShadowColor = 0;
		public int todayColor = 0;
		public int todayShadowColor = 0;
		public int dayOfWeekColor = 0;
		public int dayOfWeekShadowColor = 0;
		public int weekendColor = 0;
		public int weekendShadowColor = 0;
		public int holidayColor = 0;
		public int holidayShadowColor = 0;
		public int otherDayColor = 0;
		public int otherDayShadowColor = 0;
		
		public void calculate(int width, int height) {
			this.width = width;
			this.height = height;
			this.cellWidth = width / 7;
			this.cellHeight = height / 8;
			
			int startX = (width - this.cellWidth * 7) / 2;
			int startY = (height - this.cellHeight * 8) / 2;
			
			this.titleOffsetX = startX;
			this.titleOffsetY = startY;
			this.titleWidth = cellWidth * 7;
			this.titleHeight = cellHeight;
			
			this.headerOffsetX = startX;
			this.headerOffsetY = this.titleOffsetY + this.titleHeight;
			this.headerWidth = this.cellWidth;
			this.headerHeight = this.cellHeight;
			
			this.cellOffsetX = startX;
			this.cellOffsetY = this.headerOffsetY + this.headerHeight;
			
			this.cellMainTextSize = (int)((float)cellHeight * 2 / 5);
			this.cellSubTextSize = (int)((float)this.cellMainTextSize * 3 / 5);
			this.headerTextSize = (int)((float)this.cellMainTextSize * 2 / 3);
			this.titleTextSize = (int)((float)this.cellMainTextSize);
		}
		
		public static Config load(InputStream themeInputStream, Context context) {			
			Config config = new Config();
			try {
				String themeJson = StreamUtils.readAllText(themeInputStream);
				JSONObject themeObject = new JSONObject(themeJson);
				config.width = themeObject.getInt("width");
				config.height = themeObject.getInt("height");				
				String background = themeObject.optString("background", null);
				if (background != null && background.length() > 0) {					
					config.background = getDrawable(background, context);
				}
				config.enableShadow = themeObject.getBoolean("enableShadow");
				config.autoCalculateOffsets = themeObject.getBoolean("autoCalculateOffsets");
				
				config.titleTextSize = themeObject.getInt("titleTextSize");
				config.titleTextColor = Color.parseColor(themeObject.getString("titleTextColor"));
				config.titleTextShadowColor = Color.parseColor(themeObject.getString("titleTextShadowColor"));
				
				config.headerTextSize = themeObject.getInt("headerTextSize");
				config.headerTextColor = Color.parseColor(themeObject.getString("headerTextColor"));
				config.headerTextShadowColor = Color.parseColor(themeObject.getString("headerTextShadowColor"));
				
				config.cellMainTextSize = themeObject.getInt("cellMainTextSize");
				config.cellSubTextSize = themeObject.getInt("cellSubTextSize");
				String cellHighlightBackground = themeObject.optString("cellHighlightBackground", null);
				if (cellHighlightBackground != null && cellHighlightBackground.length() > 0) {					
					config.cellHighlightBackground = getBitmap(cellHighlightBackground, context);
				}
				String cellEventBackground = themeObject.optString("cellEventBackground", null);
				if (cellEventBackground != null && cellEventBackground.length() > 0) {					
					config.cellEventBackground = getBitmap(cellEventBackground, context);
				}
				
				config.dayColor = Color.parseColor(themeObject.getString("dayColor"));
				config.dayShadowColor = Color.parseColor(themeObject.getString("dayShadowColor"));
				config.todayColor = Color.parseColor(themeObject.getString("todayColor"));
				config.todayShadowColor = Color.parseColor(themeObject.getString("todayShadowColor"));
				config.holidayColor = Color.parseColor(themeObject.getString("holidayColor"));
				config.holidayShadowColor = Color.parseColor(themeObject.getString("holidayShadowColor"));
				config.otherDayColor = Color.parseColor(themeObject.getString("otherDayColor"));
				config.otherDayShadowColor = Color.parseColor(themeObject.getString("otherDayShadowColor"));
				config.dayOfWeekColor = Color.parseColor(themeObject.getString("dayOfWeekColor"));
				config.dayOfWeekShadowColor = Color.parseColor(themeObject.getString("dayOfWeekShadowColor"));
				config.weekendColor = Color.parseColor(themeObject.getString("weekendColor"));
				config.weekendShadowColor = Color.parseColor(themeObject.getString("weekendShadowColor"));
				
				if (!config.autoCalculateOffsets) {
					config.titleOffsetX = themeObject.getInt("titleOffsetX");
					config.titleOffsetY = themeObject.getInt("titleOffsetY");
					config.titleWidth = themeObject.getInt("titleWidth");
					config.titleHeight = themeObject.getInt("titleHeight");					
					
					config.headerOffsetX = themeObject.getInt("headerOffsetX");
					config.headerOffsetY = themeObject.getInt("headerOffsetY");
					config.headerWidth = themeObject.getInt("headerWidth");
					config.headerHeight = themeObject.getInt("headerHeight");					
					
					config.cellOffsetX = themeObject.getInt("cellOffsetX");
					config.cellOffsetY = themeObject.getInt("cellOffsetY");
					config.cellWidth = themeObject.getInt("cellWidth");
					config.cellHeight = themeObject.getInt("cellHeight");					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return config;
		}
		
		private static Drawable getDrawable(String name, Context context) {
			int resId = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + name, null, null);					
			return context.getResources().getDrawable(resId);
		}
		
		private static Bitmap getBitmap(String name, Context context) {
			int resId = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + name, null, null);					
			return BitmapFactory.decodeResource(context.getResources(), resId);
		}
	}	
}
