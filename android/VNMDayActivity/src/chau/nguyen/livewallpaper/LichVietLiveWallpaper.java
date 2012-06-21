package chau.nguyen.livewallpaper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import chau.nguyen.R;
import chau.nguyen.R.drawable;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class LichVietLiveWallpaper extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		return new LichVietEngine();
	}
	
	
	private class LichVietEngine extends Engine {
		private boolean visible;
		private Handler handler;
		private Paint paint;
		private float xcenter;
		private float ycenter;
		
		private Runnable drawContent = new Runnable() {
			
			@Override
			public void run() {
				drawContent();
			}
		};
		
		public LichVietEngine() {
			handler = new Handler();
			paint = new Paint();
			paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(1);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            paint.setTextAlign(Align.CENTER);
            paint.setTextSize(25);
			
		}
		
		@Override
		public void onVisibilityChanged(boolean visible) {
			super.onVisibilityChanged(visible);
			this.visible = visible;
			if (this.visible) {
				drawContent();
			} else {
				handler.removeCallbacks(drawContent);
			}
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			handler.removeCallbacks(drawContent);
		}
		
		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
			super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
		}
		
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			xcenter = width / 2;
			ycenter = height / 2;
			drawContent();
		}
		
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawContent);
		}
		
		private void drawContent() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
					//draw
					
					Resources rs = getApplicationContext().getResources();
					Bitmap bitmap = BitmapFactory.decodeResource(rs, R.drawable.wallpaper1);
					canvas.drawBitmap(bitmap, 0, 0, paint);
					String[] strs = splitStatement(getQuoteOfDay());
					int i = 0;
					for (String string : strs) {
						canvas.drawText(string, xcenter, ycenter  + i, paint);
						i = i + 30;
					}
					
					
					//String str = getQuoteOfDay();
					//canvas.drawColor(0xff000000);
					//canvas.drawText(str, 0, index, paint);
					//Log.d("DEBUG", str);
					//index = index + 20;
				}
			} finally {
				if (canvas != null) holder.unlockCanvasAndPost(canvas);
			}
			handler.removeCallbacks(drawContent);
			
			if (visible) {
				handler.postDelayed(drawContent, 10000);
			}
		}

		private String[] splitStatement(String statement) {
			String[] strs = statement.split(" ");
			List<String> list = new ArrayList<String>();
			int i = 0;
			String temp = "";
			for (String string : strs) {
				temp = temp + " " + string;
				i = i + 1;
				if (i == 7) {
					list.add(temp);
					temp = "";
					i = 0;
					Log.d("DEBUG", temp);
				}
			}
			Log.d("DEBUG", temp);
			list.add(temp);
			return list.toArray(new String[0]);
		}
		
	}
	
	private String getQuoteOfDay() {
		String[] quotes = getResources().getStringArray(R.array.quotes);
		int index = Calendar.getInstance().get(Calendar.MILLISECOND) % quotes.length;
		return quotes[index];
	}

}
