package com.krazevina.objects;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageDownloader 
{
	public static Bitmap readImageByUrl(String url,int width)
    {
		if(url==null)return null;
    	try
    	{
    		Bitmap mBitMap;
    		byte[] by;
//    		if(url.endsWith("jpg")) mBitMap = loadPhotoBitmap(new URL(url));
//    		else
//    		{
		    	URL mURL=new URL(url);
		    	HttpURLConnection connect=(HttpURLConnection)mURL.openConnection();
		    	connect.setConnectTimeout(2000);
		    	connect.setDoInput(true);
		    	connect.connect();
		    	InputStream input=connect.getInputStream();
		    	by = convertInputStreamToByteArray(input);
		    	mBitMap=BitmapFactory.decodeByteArray(by, 0,by.length);
		    	by = null;
//    		}
	    	Bitmap ret = Bitmap.createScaledBitmap(mBitMap, width, width*mBitMap.getHeight()/mBitMap.getWidth(), false);
	    	
	    	mBitMap.recycle();
	    	return ret;
    	}
    	catch (Error er) {
    		er.printStackTrace();
    		Global.err++;
    		return null;
		}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
	    	return null;
		}
	}
	static Bitmap loadPhotoBitmap(URL url) {
	    Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            FileOutputStream fos = new FileOutputStream("/sdcard/GX/photo.tmp");
            BufferedOutputStream bfs = new BufferedOutputStream(fos);
            in = new BufferedInputStream(url.openStream(), 8*1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 8*1024);
            copy(in, out);
            out.flush();
            final byte[] data = dataStream.toByteArray();

            bfs.write(data, 0, data.length);
            bfs.flush();

            BitmapFactory.Options opt = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            closeStream(in);
            closeStream(out);
        }
        return bitmap;
    }

	private static void copy(InputStream in, OutputStream out) throws IOException {
	    byte[] b = new byte[8*1024];
	    int read;
	    while ((read = in.read(b)) != -1) {
	        out.write(b, 0, read);
	    }
	}

	private static void closeStream(Closeable stream) {
	    if (stream != null) {
	        try {
	            stream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
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
	    	Bitmap mBitMap=BitmapFactory.decodeByteArray(by, 0,by.length);
	    	by = null;
	    	return mBitMap;
    	}
    	catch (Error er) {
    		er.printStackTrace();
    		Global.err++;
    		return null;
		}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
	    	return null;
		}
	}
}
