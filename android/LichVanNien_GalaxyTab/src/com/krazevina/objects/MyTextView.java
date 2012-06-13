package com.krazevina.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

	private Paint mTextPaint;
	private Paint mTextPaintOutline;
	private int mAscent;


	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyTextView(Context context) {
		super(context);
	}

	private void initTextViewOutline() {
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(56);
		mTextPaint.setColor(Color.RED);
		mTextPaint.setStyle(Paint.Style.FILL);

		mTextPaintOutline = new Paint();
		mTextPaintOutline.setAntiAlias(true);
		mTextPaintOutline.setTextSize(46);
		mTextPaintOutline.setColor(Color.WHITE);
		mTextPaintOutline.setStyle(Paint.Style.STROKE);
		mTextPaintOutline.setStrokeWidth(4);

		setPadding(3, 3, 3, 3);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initTextViewOutline();
		mAscent = (int) mTextPaint.ascent();
		canvas.drawText("12", getPaddingLeft(), getPaddingTop() - mAscent,
				mTextPaintOutline);
		canvas.drawText("12", getPaddingLeft(), getPaddingTop() - mAscent,
				mTextPaint);

		// Paint strokePaint = new Paint();
		// strokePaint.setARGB(255, 0, 0, 0);
		// strokePaint.setTextAlign(Paint.Align.CENTER);
		// strokePaint.setTextSize(16);
		// strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
		// strokePaint.setStyle(Paint.Style.STROKE);
		// strokePaint.setStrokeWidth(2);
		// canvas.drawT

		// Paint textPaint = new Paint();
		// textPaint.setARGB(255, 255, 255, 255);
		// textPaint.setTextAlign(Paint.Align.CENTER);
		// textPaint.setTextSize(16);
		// textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		//
		// canvas.drawText("Some Text", 100, 100, strokePaint);
		// canvas.drawText("Some Text", 100, 100, textPaint);

		// super.draw(canvas, mapView, shadow);
	}
}
