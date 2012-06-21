package chau.nguyen.calendar.appwidget;

import java.util.HashMap;

import android.content.Context;
import chau.nguyen.R;
import chau.nguyen.calendar.ui.MonthViewRenderer;

public class ThemeManager {
	private static ThemeManager instance = null;	
	HashMap<String, Integer> themeMap;
	public ThemeManager() {
		themeMap = new HashMap<String, Integer>();
		themeMap.put("light", R.raw.light);
		themeMap.put("dark", R.raw.dark);
		themeMap.put("transparent_light", R.raw.transparent_light);
		themeMap.put("transparent_dark", R.raw.transparent_dark);
		themeMap.put("small_light", R.raw.small_light);
		themeMap.put("small_dark", R.raw.small_dark);
		themeMap.put("small_transparent_light", R.raw.small_transparent_light);
		themeMap.put("small_transparent_dark", R.raw.small_transparent_dark);
	}
	
	public static synchronized ThemeManager getInstance() {
		if (instance == null) {
			instance = new ThemeManager();
		}
		return instance;
	}
	
	public static MonthViewRenderer.Config getConfig(Context context, String theme) {		
		theme = theme.toLowerCase();
		return MonthViewRenderer.Config.load(
				context.getResources().openRawResource(getInstance().themeMap.get(theme)),
				context);
	}
}