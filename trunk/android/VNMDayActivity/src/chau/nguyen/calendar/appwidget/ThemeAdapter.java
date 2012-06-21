package chau.nguyen.calendar.appwidget;

import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Gallery.LayoutParams;
import chau.nguyen.calendar.ui.MonthViewRenderer;

public class ThemeAdapter extends BaseAdapter {
	String[] themes = new String[] { "light", "dark", "transparent_light", "transparent_dark" };
	Bitmap[] themeBitmaps = new Bitmap[themes.length];
	Context context;
	
	public ThemeAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return themes.length;
	}

	@Override
	public Object getItem(int index) {
		return themes[index];
	}

	@Override
	public long getItemId(int index) {
		return index;
	}		

	@Override
	public View getView(int position, View convertView, ViewGroup rootView) {
		if (convertView == null) {
			convertView = new ImageView(context);			
		}		
		ImageView imageView = ((ImageView)convertView);
		Bitmap bitmap = getThemeBitmap(position);
		imageView.setImageBitmap(bitmap);
		LayoutParams layoutParams = new LayoutParams(bitmap.getWidth(), bitmap.getHeight());		
		imageView.setLayoutParams(layoutParams);
		return convertView;
	}
	
	private Bitmap getThemeBitmap(int index) {
		if (themeBitmaps[index] == null) {
			MonthViewRenderer.Config config = ThemeManager.getConfig(context, themes[index]);
			Bitmap bitmap = Bitmap.createBitmap(config.width, config.height, Bitmap.Config.ARGB_8888);		
			config.date = new Date();			
			MonthViewRenderer monthViewRenderer = new MonthViewRenderer(config, null);
			monthViewRenderer.render(new Canvas(bitmap));
			themeBitmaps[index] = bitmap;
		}
		return themeBitmaps[index];
	}
}
