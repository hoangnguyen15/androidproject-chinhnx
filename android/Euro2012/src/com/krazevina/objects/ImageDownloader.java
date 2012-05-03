package com.krazevina.objects;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ImageDownloader 
{
	public static Bitmap readImageByUrl(String url,int width)
    {
		if(url==null)return null;
    	try
    	{
    		Bitmap mBitMap;
    		byte[] by;
	    	URL mURL=new URL(url);
	    	HttpURLConnection connect=(HttpURLConnection)mURL.openConnection();
	    	connect.setConnectTimeout(2000);
	    	connect.setDoInput(true);
	    	connect.connect();
	    	InputStream input=connect.getInputStream();
	    	by = convertInputStreamToByteArray(input);
	    	Options options = new BitmapFactory.Options();
	        options.inScaled = false;
	        options.inDither = false;
	        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
	    	mBitMap=BitmapFactory.decodeByteArray(by, 0,by.length,options);
	    	by = null;
	    	Bitmap ret = Bitmap.createScaledBitmap(mBitMap, width, width*mBitMap.getHeight()/mBitMap.getWidth(), false);
	    	
	    	mBitMap.recycle();
	    	return ret;
    	}
    	catch (Error er) {
    		er.printStackTrace();
    		return null;
		}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
	    	return null;
		}
	}

	public static byte[] convertInputStreamToByteArray(InputStream is) throws IOException
    {
	    BufferedInputStream bis = new BufferedInputStream(is,8*1024);
	    ByteArrayOutputStream buf = new ByteArrayOutputStream();
	    int result = bis.read();
	    while(result !=-1)
	    {
		    byte b = (byte)result;
		    buf.write(b);
		    result = bis.read();
	    }
	    return buf.toByteArray();
    }
	
	public static Bitmap readImageByUrl(String url)
	{
		try
    	{
	    	URL mURL=new URL(url);
	    	HttpURLConnection connect=(HttpURLConnection)mURL.openConnection();
	    	connect.setConnectTimeout(2000);
	    	connect.setDoInput(true);
	    	connect.connect();
	    	InputStream input=connect.getInputStream();
	    	byte[] by = convertInputStreamToByteArray(input);
	    	Options options = new BitmapFactory.Options();
	    	options.inScaled = false;
	        options.inDither = false;
	        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
	    	Bitmap mBitMap=BitmapFactory.decodeByteArray(by, 0,by.length,options);
	    	by = null;
	    	return mBitMap;
    	}
    	catch (Error er) {
    		er.printStackTrace();
    		return null;
		}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
	    	return null;
		}
	}
}
