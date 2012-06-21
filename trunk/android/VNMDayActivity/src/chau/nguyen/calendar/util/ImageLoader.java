package chau.nguyen.calendar.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class ImageLoader {
	private static Context context;
	
	
	public static synchronized void initialize(Context ct) {
		context = ct;
	}
	public static void loadImage(ImageView imageView, String imageUrl) {
		
	}
	
	public static void loadBackgroud(View view, String imageUrl) {
		if (imageUrl.startsWith("resource://drawable/")) {
			String resourceName = imageUrl.substring(20);
			int resourceId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
			Drawable drawable = context.getResources().getDrawable(resourceId);
			view.setBackgroundDrawable(drawable);
		}
	}
}
