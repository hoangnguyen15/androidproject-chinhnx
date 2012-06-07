package com.krazevina.objects;

import com.krazevina.beautifulgirl.Main;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
public class KHorizontalScrollView extends HorizontalScrollView
{
	public KHorizontalScrollView(Context c)
	{
		super(c);
	}
	public KHorizontalScrollView(Context c,AttributeSet a)
	{
		super(c,a);
	}
	public KHorizontalScrollView(Context c,AttributeSet a,int i)
	{
		super(c,a,i);
	}
	private float sx,sy;
	public int currview = 0;
	int w=0;
	int maxv;
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		w = getWidth();
	}
	
	public void setCurrview(int currview) {
		this.currview = currview;
		if(w==0)w = getWidth();
		smoothScrollTo(currview*w, 0);
		Main.menuViewInd = currview;
	}
	public void setCurrview(int currview,int ww) {
		this.currview = currview;
		w = ww;
		scrollTo(currview*w, 0);
		Log.e("DISTANCE", ""+currview*w);
		Main.menuViewInd = currview;
	}
	@Override
	public boolean onTouchEvent(MotionEvent e) 
	{
		maxv = ((LinearLayout)getChildAt(0)).getChildCount();
		try{
			if(e.getAction()==MotionEvent.ACTION_UP)
			{
				if(Math.abs(e.getRawX()-sx)+Math.abs(e.getRawY()-sy)<105)
					((LinearLayout)getChildAt(0)).getChildAt(currview).dispatchTouchEvent(e);
			}
			else 
				((LinearLayout)getChildAt(0)).getChildAt(currview).dispatchTouchEvent(e);
		}catch (Exception ex) {
		}
		if(e.getAction()==MotionEvent.ACTION_DOWN)
		{
			sx = e.getRawX();
			sy = e.getRawY();
			try{
				currview = (getScrollX()+w/2)/w;
			}catch (Exception ex) {
				w = getWidth();
			}
		}
		if(e.getAction()==MotionEvent.ACTION_MOVE)
		{
			super.scrollTo((int) (-e.getRawX()+sx)+currview*w, 0);
		}
		if(e.getAction()==MotionEvent.ACTION_UP)
		{
			if(e.getRawX()-sx>0)
			{
				if(e.getRawX()-sx>w/2 && currview>0)
				{
					super.smoothScrollTo((currview-1)*w, 0);
					currview--;
					Intent i = new Intent("com.krazevina.beautifulgirl.prev");
					getContext().sendBroadcast(i);
				}
				else super.smoothScrollTo((currview)*w, 0);
			}
			else
			{
				if(-e.getRawX()+sx>w/2 && currview<((LinearLayout)getChildAt(0)).getChildCount())
				{
					super.smoothScrollTo((currview+1)*w, 0);
					currview++;
					Intent i = new Intent("com.krazevina.beautifulgirl.next");
					getContext().sendBroadcast(i);
				}
				else super.smoothScrollTo((currview)*w, 0);
			}
		}
		return true;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) 
	{
		return onTouchEvent(ev);
	}
}