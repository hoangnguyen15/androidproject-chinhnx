package com.krazevina.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.krazevina.beautifulgirl.R;

public class KImageView extends ImageView
{
	Image image;
	public boolean imgseted;
	static Bitmap imgfav;
	static Animation anim;
	
	public KImageView(Context context, Image im) {
		super(context);
		image = im;
		imgseted = false;
		if(imgfav==null)imgfav = BitmapFactory.decodeResource(getResources(), R.drawable.favor);
		if(anim==null)anim = AnimationUtils.loadAnimation(context, R.anim.fadein);
	}

	@Override
	protected void onDraw(Canvas c) 
	{
		super.onDraw(c);
		if(image.favor!=0)c.drawBitmap(imgfav, getWidth()-30, 10, null);
	}

	public void setImageBitmap(Bitmap bm,int y, int h) {
		super.setImageBitmap(bm);
		if((getTop()<y&&getBottom()>y)||
				(getTop()<y+h&&getBottom()>y+h))startAnimation(anim);
		if(bm!=null)
			imgseted = true;
		else imgseted = false;
	}
	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		if(bm!=null)
			imgseted = true;
		else imgseted = false;
		try{
		((BitmapDrawable)getDrawable()).setAntiAlias(true);
		}catch (Exception e) {
		}
	}
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		image = null;
	}
}
