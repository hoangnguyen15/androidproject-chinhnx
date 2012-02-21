package com.candroid.objects;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.graphics.Typeface;

public class Global {
	public static Typeface face;
	public static String name;
	public static Groups groups = null;
	public static String version;
	public static String smsinbox;
	public static String smsactive;
	public static String URL = "http://krazevina.com/merge.xml";
	public static int TimeZone = 7;
	
	
	@Override
	protected void finalize() {
		try {
			face = null;
			super.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void downloadFromUrl(String urlDownload, String fileName){
		try{
			File root = android.os.Environment.getExternalStorageDirectory();
			File dir = new File(root.getAbsolutePath() + "/huyenhocxml");
			if(dir.exists() == false){
				dir.mkdirs();
			}
			
			URL url = new URL(urlDownload);
			File file = new File(dir,fileName);
			long startTime = System.currentTimeMillis();
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(5000);
			int current = 0;
			while ((current = bis.read()) != -1) {
	              baf.append((byte) current);
	        }
			
			FileOutputStream fos = new FileOutputStream(file);
	        fos.write(baf.toByteArray());
			fos.flush();
			fos.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isOnline() {
		try {
			URL url = new URL(URL);
			HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
			urlc.setRequestProperty("User-Agent", "My Android Demo");
			urlc.setRequestProperty("Connection", "close");
			urlc.setConnectTimeout(1000); // mTimeout is in seconds
			urlc.connect();
			if (urlc.getResponseCode() == 200) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
