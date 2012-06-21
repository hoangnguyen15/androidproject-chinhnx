package chau.nguyen.managers;

import java.util.Calendar;

import chau.nguyen.R;
import chau.nguyen.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class BackgroundManager {
	private static final int TOTAL_NUMBER_BACKGROUND = 9;
	private static int[] backroundIds = new int[] {
													R.drawable.body, 
													R.drawable.body1, 
													R.drawable.body2, 
													R.drawable.body3, 
													R.drawable.body4,
													R.drawable.body5,
													R.drawable.body6,
													R.drawable.body7,
													R.drawable.body8,
													};
	private static int[] valentineBGIds = new int[] {
														R.drawable.valentine1,
														R.drawable.valentine2
													};
	private static Drawable[] backgroundDrawables = null;
	private static Drawable[] valentineBGDrawables = null;
	private static int currentIndex = -1; 
	
	public static void init(Context context) {
		if (backgroundDrawables != null) return;
		backgroundDrawables = new Drawable[backroundIds.length];
		Resources resources = context.getResources();
		for (int i = 0; i < backroundIds.length; i++) {
			backgroundDrawables[i] = resources.getDrawable(backroundIds[i]);
		}
		if (valentineBGDrawables != null) return;
		valentineBGDrawables = new Drawable[valentineBGIds.length];
		for (int i = 0; i < valentineBGIds.length; i++) {
			valentineBGDrawables[i] = resources.getDrawable(valentineBGIds[i]);
		}
	}
	
	public static Drawable getRandomBackground() {
		int randomIndex = Calendar.getInstance().get(Calendar.MILLISECOND) % backgroundDrawables.length;
		if (randomIndex == currentIndex) randomIndex = ++randomIndex % backgroundDrawables.length;
		currentIndex = randomIndex;
		return backgroundDrawables[currentIndex];
	}
	
	public static Drawable getRandomValentineBG() {
		int randomIndex = Calendar.getInstance().get(Calendar.MILLISECOND) % backgroundDrawables.length;
		if (randomIndex == currentIndex) randomIndex = ++randomIndex % backgroundDrawables.length;
		currentIndex = randomIndex;
		return valentineBGDrawables[currentIndex];
	}
	
	public static String getRandomBackgroundUrl() {
		int randomIndex = Calendar.getInstance().get(Calendar.MILLISECOND) % TOTAL_NUMBER_BACKGROUND;
		if (randomIndex == currentIndex) randomIndex = ++randomIndex % TOTAL_NUMBER_BACKGROUND;
		currentIndex = randomIndex;
		return "resource://drawable/body" + currentIndex;
	}
}
