package com.krazevina.objects;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ZoomControls;

import com.krazevina.beautifulgirl.GifActivity;
import com.krazevina.beautifulgirl.Main;
import com.krazevina.beautifulgirl.MainSearch;
import com.krazevina.beautifulgirl.R;
import com.krazevina.beautifulgirl.Single;
import com.krazevina.beautifulgirl.Single1;
import com.krazevina.webservice.CallWebService;

public class SingleView extends View
{
	final int LEFT = 1,RIGHT = 2;
	boolean lock = false;
	public static final int RANDOM = 1,TOP = 2,DOWNLOADED = 3,UPLOADED = 4,FAVORITED = 5,ALBUM = 6,
			SEARCHNEW = 7,FOLLOWID = 8, HOT = 9, FOLLOWNEW = 10, FOLLOWRAND = 11, SEARCHHOT = 12, 
					SEARCHTOP = 13;
	public Vector<ImgItem> item;
	static Paint p;
	public int focusItem;
	float oldscale;
	boolean multi,scaleMove,ignore,scaled;
	float downx,downy,oldx,downpx,downpy,firstdistance;
	float x,itemwidth;
	public float top,left,oldtop,oldleft;
	float scalespace;
	
	long timetouchdown,timetouchup;
	int slidepos;
	Handler hand;
	Activity father;
	ZoomControls zb;
	
	sqlite sql;
	Bitmap bmpplay;
	boolean online;
	int type;

	public SingleView(Activity context,Images img, int pos,ZoomControls z) 
	{
		super(context);
		online = true;
		father = context;
		if(Main.class.isInstance(father))
			type = Main.type;
		else type = MainSearch.type;
		PackageManager pm;
		if(bmpplay==null)
			try{
				bmpplay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
			}catch (Error e) {
				Global.err = 0;
				father.finish();
				int pid = android.os.Process.myPid();
				android.os.Process.killProcess(pid); 
			}
		pm = father.getPackageManager();
		zb = z;
		hand = new Handler();
		focusItem = -1;

		item = new Vector<ImgItem>();
		for(int i=0;i<img.getCount();i++)
		{
			try{
				ImgItem im = new ImgItem(father,img.getItemAtPosition(i), i); 
				item.add(im);
				if(i==pos)
				{
					focusItem = i;
					im.scale(im.scalespace, this);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(focusItem<0)focusItem=0;
		setFocusableInTouchMode(true);
		if(pm.hasSystemFeature("android.hardware.touchscreen.multitouch"))
			multi = true;
		else multi = false;
		scaleMove = false;scaled = false;
		ignore = false;
		x = 0;
		
		slide = new Slide();
		autoslide = new AutoSlide();
		autoslide.run = false;
		slide.start();
		autoslide.start();
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		try{
			sql = new sqlite(father);
		}catch (Exception e) {
			sql = new sqlite(getContext());
		}
		if(Single.fromSearch)
			type = MainSearch.type;
		else type = Main.type;
	}
	
	public SingleView(Activity context,String img, int pos,ZoomControls z) 
	{
		super(context);
		online = false;
		father = context;
		PackageManager pm;
		if(bmpplay==null)bmpplay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
		pm = father.getPackageManager();
		zb = z;
		hand = new Handler();
		focusItem = 0;
		item = new Vector<ImgItem>();

		File f = new File(img);
		File files[];
		if(!f.isDirectory())files = f.getParentFile().listFiles();
		else files = f.listFiles();
		Image image = new Image();
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		for(int i=0;i<files.length;i++)
		{
			try{
				image = new Image();
				if(files[i].isFile()&&(files[i].getName().toLowerCase().endsWith("jpg")
						||files[i].getName().endsWith("jpe")||files[i].getName().endsWith("jpeg")
						||files[i].getName().endsWith("png")||files[i].getName().endsWith("bmp")
						||files[i].getName().endsWith("gif")))
				{}else continue;
				BitmapFactory.decodeFile(files[i].getAbsolutePath(),op);
				if(op.outWidth<=0)continue;
				image.wImg = op.outWidth;
				image.hImg = op.outHeight;
				image.setId(-2);
				image.setName(files[i].getAbsolutePath());
				if(image.getName().toLowerCase().endsWith("gif"))image.isanimate = true;
				ImgItem im = new ImgItem(father,image, item.size()); 
				item.add(im);
				if(files[i].getAbsolutePath().equals(img))focusItem = item.size()-1;
			}catch (Exception e) {
				e.printStackTrace();
			}catch (Error e) {
				image.setBm(null);
			}
		}
		setFocusableInTouchMode(true);
		if(pm.hasSystemFeature("android.hardware.touchscreen.multitouch"))
			multi = true;
		else multi = false;
		scaleMove = false;scaled = false;
		ignore = false;
		x = 0;
		
		slide = new Slide();
		autoslide = new AutoSlide();
		autoslide.run = false;
		slide.start();
		autoslide.start();
	}
	

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		ImgItem im;
		try{
			im = item.get(focusItem);
			left = im.dst.left;
			top = im.dst.top;
		}catch (Exception e) {
			return true;
		}
		if(event.getKeyCode()==KeyEvent.KEYCODE_DPAD_DOWN)
		{
			if(im.scale>im.scalespace)
			{
				top -= 40;
				im.move(this,left,top,item.size());
				top = im.dst.top;
			}
		}
		if(event.getKeyCode()==KeyEvent.KEYCODE_DPAD_UP)
		{
			if(item.get(focusItem).scale>item.get(focusItem).scalespace)
			{
				top += 40;
				im.move(this,left,top,item.size());
				top = im.dst.top;
			}
		}
		if(event.getKeyCode()==KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			if(item.get(focusItem).scale>item.get(focusItem).scalespace)
			{
				left -= 40;
				moveout = im.move(this,left,top,item.size());
				left = im.dst.left;
				if(moveout>0&&focusItem>0)
				{
					item.get(focusItem).scale(item.get(focusItem).scalespace,this);
					focusItem--;
					slide.st(focusItem);
				}
				if(moveout<0&&focusItem<item.size())
				{
					item.get(focusItem).scale(item.get(focusItem).scalespace,this);
					focusItem++;
					slide.st(focusItem);
				}
			}
			else
			if(focusItem<item.size())
				autoSlide(true);
			
		}
		if(event.getKeyCode()==KeyEvent.KEYCODE_DPAD_LEFT)
		{
			if(item.get(focusItem).scale>item.get(focusItem).scalespace)
			{
				left += 40;
				moveout = im.move(this,left,top,item.size());
				left = im.dst.left;
				if(moveout>0&&focusItem>0)
				{
					item.get(focusItem).scale(item.get(focusItem).scalespace,this);
					focusItem--;
					slide.st(focusItem);
				}
				if(moveout<0&&focusItem<item.size())
				{
					item.get(focusItem).scale(item.get(focusItem).scalespace,this);
					focusItem++;
					slide.st(focusItem);
				}
			}
			else
			if(focusItem>0)
				autoSlide(false);
		}
		if(event.getKeyCode()==KeyEvent.KEYCODE_VOLUME_UP)
			im.scale(im.scale+0.05f,this);
		if(event.getKeyCode()==KeyEvent.KEYCODE_VOLUME_DOWN)
			im.scale(im.scale-0.05f,this);
		postInvalidate();
		return true;
	}
	
	
	class LoadImg extends Thread
	{
		public LoadImg(int posi)
		{
			pos = posi;
			item.get(posi).loadeding = true;
		}
		int pos;
		public void run()
		{
			Bitmap b=null;
			try {
				if(item.get(pos).i.getThumbBm()==null)
				{
					b = item.get(pos).i.getThumbFromSD(getWidth()/2);
					if(b==null)
					{
						b = ImageDownloader.readImageByUrl(item.get(pos).i.getThumbUrl());
						item.get(pos).i.setThumbBm(b);
            			if(b!=null)
        					sql.insertThumb(item.get(pos).i,father);
					}
					item.get(pos).i.setThumbBm(b);
					if(b!=null)
					{
						if(item.get(pos).bmp==null)item.get(pos).bmp = b;
						item.get(pos).update(x, itemwidth);
						postInvalidate();
					}
				}
				
				if(item.get(pos).i.getBm()==null)
				{
					b = null;
					File f = new File(item.get(pos).i.getName());
					if(f.canRead())
	        		{
	        			try {
	            			b = BitmapFactory.decodeFile(f.getAbsolutePath());
	            			item.get(pos).i.setBm(b);
						}catch (Error e) {
							try{
								BitmapFactory.Options op = new BitmapFactory.Options();
								op.inSampleSize = 2;
								b = BitmapFactory.decodeFile(f.getAbsolutePath());
		            			item.get(pos).i.setBm(b);
							}catch (Error er) {
								try{
									BitmapFactory.Options op = new BitmapFactory.Options();
									op.inSampleSize = 4;
									b = BitmapFactory.decodeFile(f.getAbsolutePath());
			            			item.get(pos).i.setBm(b);
								}catch (Error err) {
								}catch (Exception e2) {
								}
							}catch (Exception e2) {
							}
						}catch (Exception e) {
						}
	        		}
					if(b==null)
					{
						b = ImageDownloader.readImageByUrl(item.get(pos).i.getUrl());
						if(Global.err>=10)
						{
							Global.err = 0;
							father.finish();
							int pid = android.os.Process.myPid();
							android.os.Process.killProcess(pid); 
						}
					}
					if(b!=null)
					{
						item.get(pos).i.setBm(b);
					}
					if(b==null)
					{
						item.get(pos).loadeding = false;
						Log.e("ZZZZZZZZ", "load full img fail");
						return;
					}
					item.get(pos).bmp = b;
					item.get(pos).update(x, itemwidth);
					b = null;
					postInvalidate();
				}else{
					if(item.get(pos).bmp==null)
					{
						item.get(pos).bmp = item.get(pos).i.getBm();
						item.get(pos).update(x,itemwidth);
					}
				}
				int fullspace = 2;
				for(int i=0;i<item.size();i++)
				{
					if(!online)fullspace = 1;
					if(Math.abs(i-focusItem)>fullspace)
					{
						if(item.get(i).bmp!=null&&item.get(i).bmp.equals(item.get(i).i.getBm()))
						{
							item.get(i).bmp = item.get(i).i.getThumbBm();
						}
						item.get(i).i.setBm(null);
						item.get(i).loadeding = false;
						item.get(i).update(x,itemwidth);
					}
					if(Math.abs(i-focusItem)>20)
					{
						item.get(i).i.setThumbBm(null);
						item.get(i).bmp = null;
					}
					else if(item.get(i).i.getThumbBm()==null)
					{
						b=null;
						b = item.get(i).i.getThumbFromSD(getWidth()/2);
						if(b==null)
						{
							b = ImageDownloader.readImageByUrl(item.get(i).i.getThumbUrl());
							item.get(i).i.setThumbBm(b);
	            			if(b!=null)
	            			{
	            				try{
	            					sql.insertThumb(item.get(i).i,father);
	            				}catch (Exception e) {
								}
	            			}
						}
						item.get(i).i.setThumbBm(b);
						if(b!=null)
						{
							if(item.get(i).bmp==null)item.get(i).bmp = b;
							item.get(i).update(x, itemwidth);
							postInvalidate();
						}
					}
				}
			} catch (Exception e) {
				try{
					item.get(pos).loadeding = false;
				}catch (Exception ex) {
				}
				e.printStackTrace();
			} catch (Error e) {
				try{
					item.get(pos).loadeding = false;
				}catch (Exception ex) {
				}
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		itemwidth = getWidth();
		if(itemwidth<=0)itemwidth = father.getWindowManager().getDefaultDisplay().getWidth();
		itemwidth = itemwidth * 1.1f;
		x = - focusItem * itemwidth;
		for(int i=0;i<item.size();i++)
			item.get(i).update(x,itemwidth);
		invalidate();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	public void zoom(boolean in)
	{
		if(autoslide.run)return;
		if(in)
			item.get(focusItem).scale(item.get(focusItem).scale+0.2f,this);
		else
			item.get(focusItem).scale(item.get(focusItem).scale-0.2f,this);
		invalidate();
	}
	
	int moveout;
	@Override
	public boolean onTouchEvent(MotionEvent e) 
	{
		try{
			if(item==null||item.size()<=0)return false;
			if(item.get(focusItem).bmp==null)return true;
		}catch (Exception ex) {
			return false;
		}
		if(multi)
		{
			if((e.getAction()&MotionEvent.ACTION_MASK)==MotionEvent.ACTION_DOWN)
			{
				if(slide.run)return false;
				downx = e.getX(0);
				downy = e.getY(0);
				oldx = x;
				oldtop = top;
				oldleft = left;
				timetouchdown = System.currentTimeMillis();
				scaled = false;
				autoslide.run = false;
				moveout = 0;
			}
			if((e.getAction()&MotionEvent.ACTION_MASK)==MotionEvent.ACTION_UP)
			{
				ignore = false;
				timetouchup = System.currentTimeMillis();
				if(Math.abs(e.getX(0)-downx)+Math.abs(e.getY(0)-downy)<20)
				{
					if(item.get(focusItem).i.isanimate)
					{
						Global.gif = item.get(focusItem).i.getName();
						father.startActivity(new Intent(father,GifActivity.class));
					}
					if(zb.getVisibility()==View.VISIBLE)
						zb.setVisibility(View.GONE);
					else 
						if(!multi)zb.setVisibility(View.VISIBLE);
					try{
						((Single)father).toggleMenu();
					}catch (Exception err) {
						((Single1)father).toggleMenu();
					}
				}
				if(!scaled&&item.get(focusItem).scale==item.get(focusItem).scalespace)
				{
					if(Math.abs(e.getX(0)-downx)>getWidth()/4){	// slide
						if(e.getX(0)>downx){
							if(focusItem>0){
								slideTo(focusItem-1);
								focusItem--;
							}else slideTo(focusItem);
						}else{
							if(focusItem<item.size()-1){
								slideTo(focusItem+1);
								focusItem++;
							}else slideTo(focusItem);
						}
					}else slideTo(focusItem);
				}
				else
				{
					if(moveout>0&&focusItem>0)
					{
						item.get(focusItem).scale(item.get(focusItem).scalespace,this);
						focusItem--;
						slide.st(focusItem);
					}
					if(moveout<0&&focusItem<item.size())
					{
						item.get(focusItem).scale(item.get(focusItem).scalespace,this);
						focusItem++;
						slide.st(focusItem);
					}
				}
			}
			if((e.getAction()&MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE&&!ignore)
			{
				if(!scaleMove)	// moving
				{
					if(item.get(focusItem).scale<=item.get(focusItem).scalespace)	// slide
					{
						x = oldx + (e.getX(0)-downx);
						for(int i=0;i<item.size();i++)
							item.get(i).slide(x,itemwidth);
					}
					else	// moving when zoomed in
					{
						top = oldtop + (e.getY(0)-downy);
						left = oldleft + (e.getX(0)-downx);
						moveout = item.get(focusItem).move(this,left,top,item.size());
						top = item.get(focusItem).dst.top;
						left = item.get(focusItem).dst.left;
					}
				}
				else	// zooming
				{
					float dis = (float) Math.sqrt(Math.pow(e.getX(0)-e.getX(1),2)+Math.pow(e.getY(0)-e.getY(1),2));
					item.get(focusItem).scale(oldscale + (dis - firstdistance)/200f,this);
					left = item.get(focusItem).dst.left;
					top = item.get(focusItem).dst.top;
					try{
						((Single)father).hideMenu();
					}catch (Exception ex) {
						((Single1)father).hideMenu();
					}
				}
			}
			if((e.getAction()&MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_DOWN&&!ignore)
			{
				moveout = 0;
				scaleMove = true;scaled = true;
				oldscale = item.get(focusItem).scale;
				firstdistance = (float) Math.sqrt(Math.pow(e.getX(0)-e.getX(1),2)+Math.pow(e.getY(0)-e.getY(1),2));
			}
			if((e.getAction()&MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP)
			{
				scaleMove = false;
				ignore = true;
			}
		}
		else
		{
			if(e.getAction()==MotionEvent.ACTION_DOWN)
			{
				if(slide.run)return false;
				downx = e.getX(0);
				downy = e.getY(0);
				oldx = x;
				oldtop = top;
				oldleft = left;
				timetouchdown = System.currentTimeMillis();
				scaled = false;
				autoslide.run = false;
				moveout = 0;
			}
			if(e.getAction()==MotionEvent.ACTION_UP)
			{
				ignore = false;
				timetouchup = System.currentTimeMillis();
				if(Math.abs(e.getX()-downx)+Math.abs(e.getY()-downy)<30)
				{
					if(item.get(focusItem).i.isanimate)
					{
						Global.gif = item.get(focusItem).i.getName();
						father.startActivity(new Intent(father,GifActivity.class));
					}
					if(zb.getVisibility()==View.VISIBLE)
						zb.setVisibility(View.GONE);
					else 
						zb.setVisibility(View.VISIBLE);
					try{
						((Single)father).toggleMenu();
					}catch (Exception err) {
						((Single1)father).toggleMenu();
					}
				}
				if(!scaled&&item.get(focusItem).scale==item.get(focusItem).scalespace)
				{
					if(Math.abs(e.getX()-downx)>getWidth()/4){	// slide
						if(e.getX()>downx){
							slideTo(focusItem-1);
							focusItem--;
						}else{
							slideTo(focusItem+1);
							focusItem++;
						}
					}else slideTo(focusItem);
				}
				else
				{
					if(moveout>0&&focusItem>0)
					{
						item.get(focusItem).scale(item.get(focusItem).scalespace,this);
						focusItem--;
						slide.st(focusItem);
					}
					if(moveout<0&&focusItem<item.size())
					{
						item.get(focusItem).scale(item.get(focusItem).scalespace,this);
						focusItem++;
						slide.st(focusItem);
					}
				}
			}
			if(e.getAction()==MotionEvent.ACTION_MOVE)
			{
				if(item.get(focusItem).scale<=item.get(focusItem).scalespace)
				{
					x = oldx + (e.getX(0)-downx);
					for(ImgItem i:item)
						i.slide(x,itemwidth);
				}
				else
				{
					top = oldtop + (e.getY(0)-downy);
					left = oldleft + (e.getX(0)-downx);
					moveout = item.get(focusItem).move(this,left,top,item.size());
					top = item.get(focusItem).dst.top;
					left = item.get(focusItem).dst.left;
				}
			}
		}
		postInvalidate();
		return true;
	}

	
	private int calcIndex(float x)
	{
		if(x>0)return 0;
		if(x<-(item.size()-1)*itemwidth)return item.size()-1;
		int i = (int) (-x/itemwidth);
		if( Math.abs(-x-i*itemwidth)>itemwidth/2)i++;
		return i;
	}
	
	@Override
	protected void onDraw(Canvas g) 
	{
		if(item.size()<=0)
		{
			Paint p = new Paint();
			p.setTextAlign(Paint.Align.CENTER);
			p.setTextSize(20);
			p.setColor(Color.WHITE);
			g.drawText(father.getString(R.string.empty), itemwidth/2, getHeight()/2, p);
		}
		
		try{
			if(item.get(focusItem).loadeding==false)new LoadImg(focusItem).start(); 
			if(item.get(focusItem).scale>item.get(focusItem).scalespace)
			{
				item.get(focusItem).draw(g,this);
				if(item.get(focusItem).i.isanimate)
					g.drawBitmap(bmpplay, getWidth()/2-bmpplay.getWidth()/2, getHeight()/2-bmpplay.getHeight()/2, null);
			}
			else
			{
				for(ImgItem i:item)
				{
					i.draw(g,this);
					if(item.get(focusItem).i.isanimate)
						g.drawBitmap(bmpplay, item.get(focusItem).dst.left+item.get(focusItem).dst.width()/2-bmpplay.getWidth()/2, getHeight()/2-bmpplay.getHeight()/2, null);
				}
			}
		}catch (Exception e) {
		}
		if(ignore)
			invalidate();
	}
	
	
	public void moveTo(final int pos)
	{
		x = -pos * itemwidth;
		focusItem = pos;
		autoslide.run = false;
		invalidate();
	}
	
	public void slideTo(final int pos)
	{
		if(ignore||slide.run)return;
		autoslide.run = false;
		invalidate();
		slide.st(pos);
	}
	int test;
	public void autoSlide(boolean toright)
	{
		invalidate();
		if(toright)autoslide.dx = -2;
		else autoslide.dx = 2;
		autoslide.run = true;
	}
	
	
	class Slide extends Thread 
	{
		float xdes;
		float dx;
		float dis;
		boolean run = false;
		
		public void st(int pos)
		{
			xdes = -pos * itemwidth;
			dx = (xdes - x)/15;
			run = true;
			if(pos>=item.size()-5)new LoadImgsDown(father).start();
		}
		
		public void run()
		{
			try{
				while( true )
				{
					if(!run)
					{
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
						}
						continue;
					}
					dis=Math.abs(x - xdes);
					if(dis<=4)
					{
						run = false;
						x = xdes;
						try{
							hand.post(new Runnable() {
								public void run() {
									try{
										((Single)father).showUploader();
									}catch (Exception e) {
										try{
											((Single1)father).showUploader();
										}catch (Exception ex) {
											ex.printStackTrace();
										}
										
									}
								}
							});
						}catch (Exception e) {
						}
					}
					
					if(Math.abs(x - xdes)>itemwidth/2)
						x += dx;
					else
					{
						dx = (xdes - x)/10;
						if(dx>0&&dx<2)dx=2;
						if(dx<0&&dx>-2)dx=-2;
						x += dx;
					}
					for(int i=0;i<item.size();i++)
					{
						if(item.get(i).scale>item.get(i).scalespace)
						{
							item.get(i).scale-=0.001;
							if(item.get(i).scale<item.get(i).scalespace)item.get(i).scale=item.get(i).scalespace;
						}
						item.get(i).slide(x,itemwidth);
					}
					try {
						Thread.sleep(10);
					} catch (Exception e) {
					}
					if(focusItem>=item.size()-5)new LoadImgsDown(father).start();
					postInvalidate();
				}
			}catch (Exception e) {
			}
		}
	}
	
	
	class AutoSlide extends Thread
	{
		public float dx = -2;
		public boolean run = false;
		public void run()
		{
			try{
				while( true )
				{
					if(x<-item.size()*itemwidth)
					{
						run = false;
					}
					if(!run)
					{
						try {
							Thread.sleep(30);
						} catch (Exception e) {
						}
						continue;
					}
					x += dx/2;
					for(int i=0;i<item.size();i++)
						item.get(i).slide(x,itemwidth);
					test = focusItem;
					focusItem = calcIndex(x);
					
					if(Math.abs(-x-focusItem*itemwidth)<200)
					{
						float sc = (200-Math.abs(-x-focusItem*itemwidth))/2000f;
						item.get(focusItem).scale(item.get(focusItem).scalespace+sc);
					}
					if(focusItem>=item.size()-5)new LoadImgsDown(father).start();
					if(x>0){x=0;run = false;slide.st(focusItem);}
					if(x<-item.size()*itemwidth){x=-item.size()*itemwidth;run = false;slide.st(focusItem);}
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
					postInvalidate();
				}
			}catch (Exception e) {
			}
		}
	}
	
	public boolean save(String path)
	{
		if(item.get(focusItem).i.isdownloaded)
		{
			File f = new File(item.get(focusItem).i.getName());
			if(path.endsWith("/"))path += f.getName();
			else path += "/"+f.getName();
		}
		else
		{
			if(path.endsWith("/"))path += item.get(focusItem).i.getName();
			else path += "/"+item.get(focusItem).i.getName();
		}
		
		File f = new File(path);
		FileOutputStream os;
		try {
			os = new FileOutputStream(f);
			item.get(focusItem).i.getBm().compress(Bitmap.CompressFormat.PNG, 1, os);
			sql.insertImg(item.get(focusItem).i);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	protected void onDetachedFromWindow() 
	{
		for(int i=0;i<item.size();i++)
		{
			item.get(i).bmp = null;
			item.get(i).dst = null;
			item.get(i).i.setBm(null);
			item.get(i).i = null;
		}
		ImgItem.p = null;
		try{
		item.clear();
		}catch (Exception e) {
		}
		item = null;
		hand = null;
		father = null;
		zb = null;
		slide.interrupt();
		autoslide.interrupt();
		sql.close();
	};
	
	Slide slide;
	AutoSlide autoslide;
	
	public class LoadImgsDown extends Thread
	{
		public LoadImgsDown(Activity c)
		{
			call = new CallWebService(c);
			ac = c;
		}
		CallWebService call;
		Activity ac;
		ImgItem it;
		Image image;
		String res;
		@Override
		public void run() 
		{
			if(!online)return;
			if(lock)return;
			lock = true;
			try 
			{
				int index;
				index = Single.images.getCount()/10;
				if(index*10<Single.images.getCount())index++;
				CallWebService call = new CallWebService(father);
				if(type==RANDOM)
					res = call.serviceImageAll(index);
				else if(type==TOP)	//TOP
					res = call.serviceImageRandom();
				else if(type==UPLOADED)
					res = call.serviceImageUploaded(index);
				else if(type==FAVORITED)
					res = call.serviceImageFavorited(index);
				else if(type==SEARCHNEW)
					res = call.serviceImageSearchNew(index, Global.userSearch);
				else if(type==SEARCHHOT)
					res = call.serviceImageSearchHot(index, Global.userSearch);
				else if(type==SEARCHTOP)
					res = call.serviceImageSearchTop(index, Global.userSearch);
				else if(type==FOLLOWID)
					res = call.serviceImageFollowID(index);
				else if(type==FOLLOWNEW)
					res = call.serviceImageFollowNew(index);
				else if(type==FOLLOWRAND)
					res = call.serviceImageFollowRand(index);
				else if(type==HOT)
					res = call.serviceImageHot(index);
				final JSONArray jArray = new JSONArray(res);
				for (int i = 0; i < jArray.length(); i++) 
				{
					JSONObject jObject = jArray.getJSONObject(i);
					image = new Image();
					image.setId(jObject.getInt("imageID"));
					image.setName(jObject.getString("imageName"));
					image.setUrl(jObject.getString("imageUrl"));
					image.setThumbUrl(jObject.getString("imageThumbnail"));
					image.wthumb = getWidth()/2;
					image.date = jObject.getString("updateTime");
					image.user = jObject.getString("userName");
					image.hthumb = getWidth() * Integer.parseInt(jObject.getString("height")) /(Integer.parseInt(jObject.getString("width"))*2);
					image.setRate(Integer.parseInt(jObject.getString("imageRate")));
					
					if(type==FAVORITED)
    				{
    					sql.favor(image);
    					image.favor = 1;
    				}
    				else
    					image.favor=sql.getfavor(image.getId());
					
					hand.post(new Runnable() {
						Image im = image;
						public void run() {
							try{
								if(Single.images.h1<=Single.images.h2){
									Single.images.add(im,LEFT);
									im.inleft = true;
									im.top = (int) Single.images.h1;
									Single.images.h1 += im.hthumb;
								}
								else{
									Single.images.add(im,RIGHT);
									im.top = (int) Single.images.h2;
									im.inleft = false;
									Single.images.h2 += im.hthumb;
								}
								ImgItem img = new ImgItem(father, im, Single.images.getCount()-1);
								item.add(img);
							}catch (Exception e) {
							}
						}
					});
				}
				postInvalidate();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			lock = false;
			postInvalidate();
		}
	}
}

