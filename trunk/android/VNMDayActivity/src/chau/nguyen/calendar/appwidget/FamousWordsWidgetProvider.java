package chau.nguyen.calendar.appwidget;

import java.util.Calendar;

import chau.nguyen.VNMDayActivity;
import chau.nguyen.R;
import chau.nguyen.calendar.VNMDate;
import chau.nguyen.calendar.VietCalendar;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class FamousWordsWidgetProvider extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			AppWidgetProviderInfo info = appWidgetManager.getAppWidgetInfo(appWidgetId);
			Intent intent = new Intent(context, VNMDayActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			
			RemoteViews views = new RemoteViews(context.getPackageName(), info.initialLayout);
			String str = getQuoteOfDay(context);
			Log.d("DEBUG", "Update famous words: " + str);
			views.setTextViewText(R.id.famousWords, str);
			
			Calendar calendar = Calendar.getInstance();
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			
			String solarDay = VietCalendar.getDayOfWeekText(dayOfWeek) + " ngày " + dayOfMonth + " tháng " + month + " năm " + year;
			views.setTextViewText(R.id.solarCurrentDay, solarDay);
			
			VNMDate date = VietCalendar.convertSolar2LunarInVietnamese(calendar.getTime());
			String lunarDay = "Ngày " + date.getDayOfMonth() + " tháng " + date.getMonth() + " năm " + date.getYear() + "(âm lịch)";
			views.setTextViewText(R.id.lunarCurrentDay, lunarDay);
			
			views.setOnClickPendingIntent(R.id.famousWordsWidget, pendingIntent);
			
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
	
	private String getQuoteOfDay(Context context) {
		String[] quotes = context.getResources().getStringArray(R.array.quotes);
		int index = Calendar.getInstance().get(Calendar.MILLISECOND) % quotes.length;
		return quotes[index];
	}
}
