package com.krazevina.thioto.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Common {	
	
	public static Bitmap readImageByUrl(String url)throws MalformedURLException,IOException{    	
    	URL mURL=new URL(url);
    	HttpURLConnection connect=(HttpURLConnection)mURL.openConnection();
    	connect.setDoInput(true);
    	connect.connect();
    	InputStream input=connect.getInputStream();    	
    	Bitmap mBitMap=BitmapFactory.decodeStream(input);
    	return mBitMap;
}
	
}
