package com.krazevina.objects;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.krazevina.beautifulgirl.R;

public class ImgItem
{
	boolean loadeding;
	public Bitmap bmp;
	public Image i;
	public float top,left;
	float basetop,baseleft;
	public float scale,scalespace;
	public Rect dst;
	int index,w,h;
	
	public ImgItem(Activity f,Image res,int ind)
	{
		i = res;
		if(res.getBm()!=null)
		{
			bmp = res.getBm();
			loadeding = true;
		}
		else
		{
			bmp = res.getThumbBm();
			loadeding = false;
		}
		if(bmp!=null)
			dst = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		else dst = new Rect(0, 0, 0, 0);
		w = f.getWindowManager().getDefaultDisplay().getWidth();
		h = f.getWindowManager().getDefaultDisplay().getHeight();
		index = ind;
		loadeding = false;
		if(p==null)
		{
			p = new Paint();
			p.setTextAlign(Paint.Align.CENTER);
		}
	}
	public void update()
	{
		if(i.getBm()!=null)
		{
			bmp = i.getBm();
			loadeding = true;
		}
		else
		{
			bmp = i.getThumbBm();
			loadeding = false;
		}
		if(bmp!=null)
			dst = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		else dst = new Rect(0, 0, 0, 0);
	}
	public void update(float x,float itemwidth)
	{
		if(bmp==null)return;
		scale = (float)h/(float)bmp.getHeight();
		if((float)w/(float)bmp.getWidth()<scale)
			scale = (float)w/(float)bmp.getWidth();
		top = h/2 - bmp.getHeight()*scale/2;
		left = w/2 - bmp.getWidth()*scale/2;
		dst.top = (int) top;
		dst.left = (int) (left+x+itemwidth*index);
		dst.right = (int) (dst.left + bmp.getWidth()*scale);
		dst.bottom = (int) (dst.top + bmp.getHeight()*scale);
		scalespace = scale;
		baseleft = left;
		basetop = top;
	}
	public int move(SingleView view,float dx,float dy,int max)		// moving when zoomed in
	{
		int ret = 0;
		top = dy;left = dx;
		dst.left = (int) (left);
		dst.top = (int) (top);
		int w = view.getWidth()/3;
		if(bmp.getWidth()*scale>view.getWidth())
		{
			if(dst.left>w)
			{
				if(index>0)ret = 1;
				else dst.left=w;
			}
			if(dst.left<view.getWidth()-w-bmp.getWidth()*scale)
			{
				if(index<max-1)ret = -1;
				else dst.left=(int) (view.getWidth()-w-bmp.getWidth()*scale);
			}
		}
		else
		{
			if(dst.left<w)ret = 1;
			if(dst.left>view.getWidth()+w-bmp.getWidth()*scale)ret = -1;
		}
		if(bmp.getHeight()*scale>view.getHeight())
		{
			if(dst.top>0)dst.top = 0;
			if(dst.top<view.getHeight()-bmp.getHeight()*scale)dst.top = (int) (view.getHeight()-bmp.getHeight()*scale);
		}
		else
		{
			if(dst.top<0)dst.top = 0;
			if(dst.top>view.getHeight()-bmp.getHeight()*scale)dst.top = (int) (view.getHeight()-bmp.getHeight()*scale);
		}
		dst.right = (int) (dst.left + bmp.getWidth()*scale);
		dst.bottom = (int) (dst.top + bmp.getHeight()*scale);
		return ret;
	}
	public void scale(float dscale, SingleView view)
	{
		if(dscale<=scalespace)
		{
			try{
				dscale = scalespace;
				top = basetop; left = baseleft;
				dst.top = (int) top;dst.bottom = (int) (dst.top + bmp.getHeight()*dscale);
				dst.left = (int) left;dst.right = (int) (dst.left + bmp.getWidth()*dscale);
				view.slideTo(index);
				scale = dscale;
			}catch (Exception e) {
			}
		}
		else
		{
			if(dscale>scalespace+5)dscale = scalespace+5;
			
			dst.left = (int) (dst.left-bmp.getWidth()*(dscale-scale)/2.0f);
			dst.top = (int) (dst.top-bmp.getHeight()*(dscale-scale)/2.0f);
			
			if(bmp.getWidth()*dscale>view.getWidth())
			{
				if(dst.left>0)dst.left = 0;
				if(dst.left<view.getWidth()-bmp.getWidth()*dscale)dst.left = (int) (view.getWidth()-bmp.getWidth()*dscale);
			}
			else
			{
				if(dst.left<0)dst.left = 0;
				if(dst.left>view.getWidth()-bmp.getWidth()*dscale)dst.left = (int) (view.getWidth()-bmp.getWidth()*dscale);
			}
			if(bmp.getHeight()*dscale>view.getHeight())
			{
				if(dst.top>0)dst.top = 0;
				if(dst.top<view.getHeight()-bmp.getHeight()*dscale)dst.top = (int) (view.getHeight()-bmp.getHeight()*dscale);
			}
			else
			{
				if(dst.top<0)dst.top = 0;
				if(dst.top>view.getHeight()-bmp.getHeight()*dscale)dst.top = (int) (view.getHeight()-bmp.getHeight()*dscale);
			}

			dst.bottom = (int) (dst.top + bmp.getHeight()*dscale);
			dst.right = (int) (dst.left + bmp.getWidth()*dscale);
			scale = dscale;
		}
	}
	public void scale(float dscale)
	{
		if(bmp==null)return;
		dst.left = (int) (dst.left - (dscale-scale)*bmp.getWidth()/2);
		dst.top = (int) (dst.top - (dscale-scale)*bmp.getWidth()/2);
		
		dst.bottom = (int) (dst.top + bmp.getHeight()*dscale);
		dst.right = (int) (dst.left + bmp.getWidth()*dscale);
	}
	public void slide(float x,float itemwidth)
	{
		if(bmp==null)return;
		dst.left = (int) (index*itemwidth+x+left);
		dst.top = (int) basetop;
		dst.right = (int) (dst.left + bmp.getWidth()*scale);
		dst.bottom = (int) (dst.top + bmp.getHeight()*scale);
	}
	static Paint p;
	public void draw(Canvas g,View v)
	{
		if(bmp==null){
			g.drawText(v.getContext().getString(R.string.loading), (dst.right+dst.left)/2, (dst.top+dst.bottom)/2, p);
			return;
		}
		
		g.drawBitmap(bmp, null, dst, null);
	}
}