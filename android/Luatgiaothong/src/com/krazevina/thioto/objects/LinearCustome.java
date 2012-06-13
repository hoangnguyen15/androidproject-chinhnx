package com.krazevina.thioto.objects;

import com.krazevina.thioto.interfaces.onSizeChangedListener;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LinearCustome extends LinearLayout {

	public LinearCustome(Context context) {
		super(context);
	}

	public LinearCustome(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	
	}

	@Override
	protected void onMeasure(int wSpec, int hSpec) {

		super.onMeasure(wSpec, hSpec);
	}

	onSizeChangedListener onsizechange = null;
	int sttview = -1;
	int sttlinear = -1;

	public void setOnSizeChanged(onSizeChangedListener onsizechange,
			int sttview, int sttlinear) {
		this.sttview = sttview;
		this.sttlinear = sttlinear;
		this.onsizechange = onsizechange;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	
		if (onsizechange != null)
			onsizechange.exeOnSizeChanged(w, h, sttview, sttlinear);
		
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	
}
