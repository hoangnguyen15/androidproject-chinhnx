package chau.nguyen.calendar.appwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.AdapterView.OnItemClickListener;
import chau.nguyen.R;

public class MonthWidgetConfigure extends Activity {
	static final int SELECT_THEME_DIALOG = 1;
	static final String TAG = "MonthWidgetConfigure";

    private static final String PREFS_NAME
            = "chau.nguyen.calendar.appwidget.MonthWidgetProvider";
    private static final String PREF_PREFIX_KEY = "month_widget_theme_";

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetPrefix;

    public MonthWidgetConfigure() {
        super();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);        

        // Find the widget id from the intent. 
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }                

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        
        setContentView(R.layout.month_widget_config);
        
        final Gallery gallery = (Gallery)findViewById(R.id.gallery);        
        final ThemeAdapter themeAdapter = new ThemeAdapter(this);
        gallery.setAdapter(themeAdapter);
        gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MonthWidgetConfigure.this.setWidgetTheme((String)themeAdapter.getItem(position));
			}			       
        });
        Button prevButton = (Button)findViewById(R.id.prev);
        prevButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				int prev = gallery.getSelectedItemPosition() - 1;
				if (prev >= 0) {
					gallery.setSelection(prev, true);
				}
			}        	
        });
        Button nextButton = (Button)findViewById(R.id.next);
        nextButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				int next = gallery.getSelectedItemPosition() + 1;
				if (next < gallery.getCount()) { 
					gallery.setSelection(next, true);
				}
			}        	
        });
        Button chooseButton = (Button)findViewById(R.id.choose);
        chooseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MonthWidgetConfigure.this.setWidgetTheme((String)gallery.getSelectedItem());
			}        	
        });        
    }
    
    protected String getThemeName(String theme) {
    	return theme;
    }    
    
    private void setWidgetTheme(String theme) {
        // When the button is clicked, save the string in our prefs and return that they
        // clicked OK.            
        saveWidgetPref(this, mAppWidgetId, theme);

        // Push widget update to surface with newly set prefix
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        MonthWidgetProvider.updateAppWidget(this, appWidgetManager, mAppWidgetId, getThemeName(theme));

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();        
    }

    // Write the theme to the SharedPreferences object for this widget
    static void saveWidgetPref(Context context, int appWidgetId, String theme) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, theme);
        prefs.commit();
    }

    // Read the theme from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    public static String loadWidgetPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String themeName = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (themeName != null) {
            return themeName;
        } else {
            return "light";
        }
    }
}
