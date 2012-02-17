package com.candroid.objects;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
}
