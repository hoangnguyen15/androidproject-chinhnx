package com.krazevina.objects;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;

public class Image {
	private int id;
	public int status;
	/*
	 * 0: no down
	 * 1: downloading
	 */
	
	private int rate;
	private String name;
	public String user;
	private String url;
	private Bitmap bm;
	private String urlthumb;
	private Bitmap bmthumb;
	
	public int wImg,hImg,wthumb,hthumb;
	public int top,left;
	public boolean freed; 
	public int favor;
	public String date;
	public Thread thread;
	public Rect src;
	public KImageView imgv;
	public boolean inleft,isdownloaded;
    
	public Image()
	{
		status = 0;
		wImg = hImg = wthumb = hthumb = 0;
		top = left = 0;
		favor = 0;
		freed = false;
		src = new Rect();
		isdownloaded = false;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setBm(Bitmap bm) 
	{
		if(bm==null)
		{
			this.bm = null;
			return;
		}
		this.bm = bm;
		wImg = bm.getWidth();
		hImg = bm.getHeight();
	}
	public Bitmap getBm() 
	{
		try{
			return bm;
		}catch (Exception e) {
			return null;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getThumbUrl()
	{
		return urlthumb;
	}
	
	public Bitmap getThumbBm()
	{
		try{
			return bmthumb;
		}catch (Exception e) {
			return null;
		}
	}
	public void setThumbUrl(String s)
	{
		urlthumb = s;
	}
	public void setThumbBm(Bitmap b)
	{
		if(bmthumb!=null&&b==null)freed = true;
		bmthumb = b;
		if(bmthumb!=null)
		src.set(0,0,bmthumb.getWidth(),bmthumb.getHeight());
	}
	public int getRate()
	{
		return rate;
	}
	public void setRate(int rate)
	{
		this.rate = rate;
	}
	
	public Bitmap getThmbFromCache()
	{
		Bitmap mBitMap=null;
		File f = new File(Environment.getExternalStorageDirectory()+"/GX/thumb/"+id);
		if(!f.canRead())return null;
		try{
			mBitMap=BitmapFactory.decodeFile(name);
		}catch (Error e) {
		}catch (Exception e) {
		}
		return mBitMap;
	}
	
	public Bitmap getThumbFromSD(int w)
	{
		Bitmap mBitMap,ret=null;
		wthumb = w;
		mBitMap = getThmbFromCache();
		if(mBitMap!=null)return mBitMap;
		File f = new File(name);
		if(!f.canRead())return null;
		try{
			mBitMap=BitmapFactory.decodeFile(name);
			hthumb = (int) (mBitMap.getHeight() * ((float)w/mBitMap.getWidth()));
			ret = Bitmap.createScaledBitmap(mBitMap, wthumb, hthumb, false);
			mBitMap.recycle();
		}catch (Error e) {
			try{
				BitmapFactory.Options op = new BitmapFactory.Options();
				op.inSampleSize = 2;
				mBitMap=BitmapFactory.decodeFile(name);
				hthumb = (int) (mBitMap.getHeight() * ((float)w/mBitMap.getWidth()));
				ret = Bitmap.createScaledBitmap(mBitMap, wthumb, hthumb, false);
				mBitMap.recycle();
			}catch (Error ex) {
				try{
				BitmapFactory.Options op = new BitmapFactory.Options();
				op.inSampleSize = 2;
				mBitMap=BitmapFactory.decodeFile(name);
				hthumb = (int) (mBitMap.getHeight() * ((float)w/mBitMap.getWidth()));
				ret = Bitmap.createScaledBitmap(mBitMap, wthumb, hthumb, false);
				mBitMap.recycle();
				}catch (Error exe) {
				}catch (Exception e2) {
				}
			}catch (Exception e2) {
			}
		}catch (Exception e) {
		}
		return ret;
	}
	public Bitmap getImgFromSD()
	{
		Bitmap mBitMap=BitmapFactory.decodeFile(name);
		return mBitMap;
	}
}
