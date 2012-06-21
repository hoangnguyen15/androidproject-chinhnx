package chau.nguyen.calendar.appwidget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import chau.nguyen.EventManager;
import chau.nguyen.R;
import chau.nguyen.VNMDayActivity;
import chau.nguyen.calendar.content.LocalFileContentProvider;
import chau.nguyen.calendar.ui.MonthViewRenderer;

public class MonthWidgetProvider extends AppWidgetProvider {
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i]; 
            updateAppWidget(context, appWidgetManager, appWidgetId, MonthWidgetConfigure.loadWidgetPref(context, appWidgetId));            
        }
    }
	
	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String theme) {		
        AppWidgetProviderInfo info = appWidgetManager.getAppWidgetInfo(appWidgetId);
        if (info == null) return;
        
        MonthViewRenderer.Config config = ThemeManager.getConfig(context, theme);
        
        Log.d("DEBUG", info.minWidth + "x" + info.minHeight);
        Log.d("DEBUG", ">> " + config.width + "x" + config.height);
        
        Uri bitmapUri = null;
        bitmapUri = renderWidget(context, config, appWidgetId);  
       
        // Create an Intent to launch VNMDayActivity
        Intent intent = new Intent(context, VNMDayActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);        
        RemoteViews views = new RemoteViews(context.getPackageName(), info.initialLayout);
    	views.setImageViewUri(R.id.monthViewImage, bitmapUri);
    	views.setOnClickPendingIntent(R.id.monthViewImage, pendingIntent);
    	
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
		
	protected static Uri renderWidget(Context context, MonthViewRenderer.Config config, int appWidgetId) {		
		Bitmap bitmap = Bitmap.createBitmap(config.width, config.height, Bitmap.Config.ARGB_8888);		
		config.date = new Date();		
		cleanUp(context);
		EventManager eventManager = new EventManager(context.getContentResolver());
		Calendar cal = Calendar.getInstance();
		eventManager.setMonth(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
		MonthViewRenderer monthViewRenderer = new MonthViewRenderer(config, eventManager);
		monthViewRenderer.render(new Canvas(bitmap));
		String bitmapCacheFileName = context.getCacheDir() + "/" + appWidgetId + "-" + config.date.getTime() + ".png";		
		File file = new File(bitmapCacheFileName);		
		try {			
			file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, ostream);
            ostream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }                
		return Uri.parse(LocalFileContentProvider.constructUri(file.getAbsolutePath()));
	}	
	
	protected static void cleanUp(Context context) {
		File[] files = context.getCacheDir().listFiles();
		for (File file : files) {
			file.delete();
		}
	}
}
