package com.krazevina.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class Images {
	private ArrayList<Image> _images;
	
	public ArrayList<Image> img1,img2;
	
	long h1,h2;
	
	public Images(){
		_images= new ArrayList<Image>();
		img1 = new ArrayList<Image>();
		img2 = new ArrayList<Image>();
		h1=1;h2=1;
		op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
	}
	
	public int find(Image i)
	{
		return _images.indexOf(i);
	}
	
	public void add(Image image,int left){
		_images.add(image);
		if(left==1)
			img1.add(image);
		else
			img2.add(image);
	}
	
	public int getCount(){
		return _images.size();
	}
	public int getCount1(){
		return img1.size();
	}
	public int getCount2(){
		return img2.size();
	}
	
	public Image getItemAtPosition(int position){
		return _images.get(position);
	}
	
	public Image getItem1AtPosition(int position){
		return img1.get(position);
	}
	
	public Image getItem2AtPosition(int position){
		return img2.get(position);
	}
	
	public void clear() {
		_images.clear();
	}
	static BitmapFactory.Options op;
	
	public void scanSDCard(Activity father,int w)
	{
		File f;
		SharedPreferences sp = father.getSharedPreferences("dir", Context.MODE_PRIVATE); 
		File from = new File(sp.getString("dir", Environment.getExternalStorageDirectory()+"/GX"));
		f = new File(Environment.getExternalStorageDirectory()+"/GX/dir");
		
		try {
			FileOutputStream fos = new FileOutputStream(f);
			File[]inside = from.listFiles();
			if(inside==null)return;
			for(File i:inside)
			{
				if(i.isFile()&&i.length()>10000&&i.length()<10000000l)
				{
					if(i.getName().endsWith("png")||i.getName().endsWith("jpg")||
							i.getName().endsWith("jpe")||i.getName().endsWith("jpeg")||
							i.getName().endsWith("gif")||i.getName().endsWith("bmp")||
							i.getName().endsWith("pcx"))
					{
						try
						{
							byte[] b = i.getAbsolutePath().getBytes();
							fos.write(intToByteArray(b.length));
							fos.write(b, 0, b.length);
						}catch (Exception e) {
						}
					}
				}
			}
			fos.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			readLog(fis, w,father);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void updateFav(Context context)
	{
		sqlite sql = new sqlite(context);
		for(int i=0;i<_images.size();i++)
		{
			_images.get(i).favor = sql.getfavor(_images.get(i).getId());
		}
		sql.close();
	}
	
	public void readLog(FileInputStream log,int w,Activity f)
	{
		String fn;int l;
		byte b[] = new byte[4];
		sqlite sql = new sqlite(f);
		byte b100[];
		ByteBuffer bb = ByteBuffer.allocate(4);
		IntBuffer intb = bb.asIntBuffer();
		h1=1;h2=1;
		try{
			while(true)
			{
				if(log.read(b, 0, 4)<=0)break;
				bb = ByteBuffer.allocate(4);
				intb = bb.asIntBuffer();
				bb.put(b);
				l = intb.get();
				b100 = new byte[l];
				log.read(b100, 0, l);
				fn = new String(b100);
				
				BitmapFactory.decodeFile(fn,op);
				Image im = new Image();
				if(op.outWidth<=0)continue;
				im.wImg = op.outWidth;
				im.hImg = op.outHeight;
				im.wthumb = w;
				im.hthumb = (int) ((float)im.hImg *w / im.wImg);
				im.setName(fn);
				sql.getImg(im);
				im.isdownloaded = true;
				
				if(h1<=h2)
				{
					im.top = (int) h1;
					h1 += im.hthumb;
					im.inleft = true;
					add(im,1);
				}else{
					im.top = (int) h2;
					h2 += im.hthumb;
					im.inleft = false;
					add(im,2);
				}
			}
		}catch (Exception e) {
		}
		sql.close();
	}
	
	public static final byte[] intToByteArray(int value) {
		return new byte[]{
		(byte)(value >>> 24), (byte)(value >> 16 & 0xff), (byte)(value >> 8 & 0xff), (byte)(value & 0xff) };
	}

}
