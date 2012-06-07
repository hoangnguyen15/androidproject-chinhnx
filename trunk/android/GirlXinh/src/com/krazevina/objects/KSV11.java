package com.krazevina.objects;

import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.krazevina.beautifulgirl.Main;
import com.krazevina.beautifulgirl.R;
import com.krazevina.beautifulgirl.Single;
import com.krazevina.webservice.CallWebService;


public class KSV11 extends View
{
	final int LEFT = 1,RIGHT = 2;
	public static final int RANDOM = 1,TOP = 2,DOWNLOADED = 3,UPLOADED = 4,FAVORITED = 5,ALBUM = 6,
		SEARCH = 7;
	
	public static Images images;
	public static int firstVisible;
	public static int lastVisible;
	Main father;
    Handler handler;
    sqlite sql;
    int leftCurrentIndex,rightCurrentIndex;
    Bitmap imgfav;
    
    public static int type;
    Fling fling;
    
	public KSV11(Context context) 
	{
		super(context);
		handler = new Handler();
		fling = new Fling();
		fling.start();
		images = new Images();
	}

	public KSV11(Context context, AttributeSet attr) 
	{
		super(context,attr);
		handler = new Handler();
		fling = new Fling();
		fling.start();
		images = new Images();
	}
	
	public KSV11(Context context, AttributeSet attr, int i) 
	{
		super(context, attr, i);
		handler = new Handler();
		fling = new Fling();
		fling.start();
		images = new Images();
	}
	
	public void clearImg()
	{
		for(int i=0;i<images.getCount();i++)
		{
			try{
				images.getItemAtPosition(i).setBm(null);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	int i;
	public void setup(Main a,int type)
	{
		father = a;
		sql = new sqlite(father);
		KSV11.type = type;
		dst = new Rect();
		Main.getbydate = true;
	}
	
	public void change(int type)
	{
		KSV11.type = type;
		Main.getbydate = true;
		y = 1;
		first = false;
		images = new Images();
		prog = null;
		
		if(type == DOWNLOADED)
		{
			images.scanSDCard(father, father.getWindowManager().getDefaultDisplay().getWidth()/2);
		}
		System.gc();
		new LoadImgsDown().start();
		
		downx = downy = -1000;
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
					loadUp(LEFT);
					loadUp(RIGHT);
				} catch (Exception e) {
				}
			}
		}).start();
		postInvalidate();
		MotionEvent e = MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		onTouchEvent(e);
	}
	
	int findFirstOnScreen(int left)
	{
		return find(y,left);
	}

	
	int find(float y,int left)
	{
		int i=0;
		ArrayList<Image> arr = images.img1;
		if(left==2)arr = images.img2;
		if(arr.size()<=0)return -1;
		try{
			for(i=0;i<arr.size();i++)
			{
				if( arr.get(i)!=null && 
					arr.get(i).top<=y && 
					arr.get(i).top+arr.get(i).hthumb>=y)break;
			}
			if(i>=arr.size())i=arr.size()-1;
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return i;
	}
	
	int find(float x,float y)
	{
		int pos;
		if(x<getWidth()/2)
		{
			pos = find(y,LEFT);
			if(pos<0)return -1;
			return images.find(images.img1.get(pos));
		}
		else
		{
			pos = find(y,RIGHT);
			if(pos<0)return -1;
			return images.find(images.img2.get(pos));
		}
	}
	
	float downx,downy,oldy,firstdowny;
	public float y=0;
	boolean autoSlide;
	long timetouchup,timetouchdown;
	boolean down=true;
	int firstonscr1,firstonscr2;
	
	public float getMax()
	{
		return images.h1>images.h2?images.h1:images.h2;
	}
	public float getMin()
	{
		return images.h1<images.h2?images.h1:images.h2;
	}
	public int getMinPos()
	{
		return images.h1<images.h2?LEFT:RIGHT;
	}
	
	ArrayList<Image> arr;
	long start,mid;
	long space;
	
	public void loadUp(int left)
	{
		try{
		arr = (left==1?images.img1:images.img2);

		start = 0;mid = (int) (y);
		space = getHeight()*3;
		
		for(int i= 0;i<arr.size();i++)
		{
			try{
				if(Math.abs(arr.get(i).top+arr.get(i).hthumb-mid)<space)
				{
					if(arr.get(i).getThumbBm()==null&&
							(arr.get(i).thread==null||!arr.get(i).thread.isAlive()))
					{
						arr.get(i).status=1;
						new LoadImg(i,left).start();
					}
				}
				else
				{
					arr.get(i).setThumbBm(null);
					try{
						if(arr.get(i).thread!=null&&arr.get(i).thread.isAlive())
						arr.get(i).thread.interrupt();
						arr.get(i).thread = null;
					}catch (Exception e) {
					}
				}
			}catch (Exception e) {
				continue;
			}
		}
		}catch (Exception e) {
		}
	}
	
	
	public void setPos(int pos)
	{
		try{
			y = images.getItemAtPosition(pos).top;
		}catch (Exception e) {
			y = 1;
		}
		loadUp(LEFT);
		loadUp(RIGHT);
		postInvalidate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) 
	{
		if(e.getAction()==MotionEvent.ACTION_UP)
		{
			down = true;
			timetouchup = System.currentTimeMillis();
			if(Math.abs(e.getX()-downx)+Math.abs(e.getY()-downy)<30)	//click
			{
				if(images.getCount()<=0)return true;
				int ind = find(e.getX(), e.getY()+y);
				if(ind<0)
					return true;
				if(images.getItemAtPosition(ind).getThumbBm()==null&&
						(images.getItemAtPosition(ind).thread==null||
						!images.getItemAtPosition(ind).thread.isAlive()))
				{
					if(e.getX()<getWidth()/2)
						new LoadImg(find(e.getY(),LEFT), LEFT).start();
					else
						new LoadImg(find(e.getY(),RIGHT), RIGHT).start();
					return true;
				}
//				if(Global.username==null||Global.username.length()<=0)
//				{
//					Toast.makeText(father, R.string.needlogin, 1).show();
//					father.startActivity(new Intent(father,Login.class));
//					return true;
//				}
//				if(Global.time<=0)
//				{
//					Toast.makeText(father, R.string.upgrade, 1).show();
//					father.startActivity(new Intent(father,Info.class));
//					return true;
//				}
				Intent in = new Intent(father,Single.class); 
				in.putExtra("index", ind);
				father.startActivityForResult(in,0);
				return true;
			}
			if(timetouchup - timetouchdown<550) // fling
				v = -(e.getY()-firstdowny)*600/(timetouchup-timetouchdown);

			loadUp(1);
			loadUp(2);
			checkLoadDown();
		}
		if(e.getAction()==MotionEvent.ACTION_DOWN)
		{
			timetouchdown = System.currentTimeMillis();
			downx = e.getX(0);
			downy = e.getY(0);
			firstdowny = e.getY(0);
			oldy = y;
			autoSlide = false;
			v = 0;
			down = false;
		}
		if(e.getAction()==MotionEvent.ACTION_MOVE)
		{
			y = oldy-(e.getY()-downy);
			if(y<0)y = 0;
			if(getMax()>getHeight()&&y>getMax()-getHeight()/2)y = getMax()-getHeight()/2;
			if(getMax()<getHeight())y=1;
		}
		postInvalidate();
		return true;
	}
	
	public boolean lock1=false;
	ProgressDialog prog;
	public void checkLoadDown()
	{
		if(y>getMin()-getHeight())
		{
			new LoadImgsDown().start();
		}
	}
	public class LoadImgsDown extends Thread
	{
		Image image;
		String res;
		@Override
		public void run() 
		{
			v = 0;
			if(lock1)return;
			lock1 = true;
			
			try{
				if(type==DOWNLOADED){lock1 = false;return;}
				if(prog==null)
					handler.post(new Runnable() {
						public void run() {
							try{
								if(prog==null)
								{
									prog = ProgressDialog.show(father, "", father.getString(R.string.pleasewait));
									new TimeOut().start();
								}
							}catch (Exception e) {
							}
						}
					});
					
				int index = images.getCount()/10;
				if(index*10<images.getCount())index++;
				CallWebService call = new CallWebService(father);
				if(type==RANDOM)
					res = call.serviceImageRandom();
				else if(type==TOP)	//TOP
					res = call.serviceImageRate(index);
				else if(type==UPLOADED)
					res = call.serviceImageUploaded(index);
				else if(type==FAVORITED)
					res = call.serviceImageFavorited(index);
				else if(type==SEARCH)
					res = call.serviceImageSearchNew(index, Global.userSearch);
				if(res!=null)first = false;
				
				try 
				{
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
						
						handler.post(new Runnable() {
							Image im = image;
							public void run() {
								try{
									if(images.h1<images.h2){
										images.add(im,LEFT);
										im.top = (int) images.h1;
										images.h1 += im.hthumb;
									}
									else{
										images.add(im,RIGHT);
										im.top = (int) images.h2;
										images.h2 += im.hthumb;
									}
								}catch (Exception e) {
								}
							}
						});
					}
					loadUp(LEFT);
					loadUp(RIGHT);
				}
				catch (Exception e) {
					e.printStackTrace();
					if(prog!=null&&prog.isShowing())
						handler.post(new Runnable() {
							public void run() {
								try{
									prog.dismiss();
								}catch (Exception e) {
								}
							}
						});
				}
				lock1 = false;
				first = false;
				postInvalidate();
			}catch (Exception e) {
				e.printStackTrace();
				if(prog!=null&&prog.isShowing())
					handler.post(new Runnable() {
						public void run() {
							try{
								prog.dismiss();
							}catch (Exception e) {
							}
						}
					});
			}
		}
	}
	
	class LoadImg extends Thread
	{
		int pos,left;
		boolean insert1;
		LinearLayout.LayoutParams lp;
		ArrayList<Image>arr;
		ImageView imv;
		
		public LoadImg(int pos, int left)
		{
			this.pos = pos;
			this.left = left;
			if(left==1)arr = images.img1;
			else arr = images.img2;
			arr.get(pos).thread = this;
		}
		public void run()
		{
        	try {
        		arr.get(pos).status=1;
        		Bitmap b = null;
        		File f;
        		switch (arr.get(pos).getId()) {
				case -1:
					f = new File(Environment.getExternalStorageDirectory()+
							"/GX/thumb/"+arr.get(pos).getName());
					if(f.canRead())
	        		{
	        			try {
	            			b = BitmapFactory.decodeFile(f.getAbsolutePath());
						} catch (Exception e) {
						} catch (Error e) {
						}
	        		}
					break;
				case-2:
					b = arr.get(pos).getThumbFromSD(getWidth()/2);
					break;
				default:
					f = new File(Environment.getExternalStorageDirectory()+
							"/GX/thumb/"+arr.get(pos).getId());
					if(f.canRead())
	        		{
	        			try {
	            			b = BitmapFactory.decodeFile(f.getAbsolutePath());
						} catch (Exception e) {
						} catch (Error e) {
						}
	        		}
					break;
				}
				
        		if(b==null)
        		{
        			try{
	        			b = ImageDownloader.readImageByUrl(arr.get(pos).getThumbUrl(),getWidth()/2);
	        			arr.get(pos).setThumbBm(b);
	        			if(b!=null)
		        			sql.insertThumb(arr.get(pos),father);
        			} catch (Exception e) {
					} catch (Error e) {
					}
        		}
				
    			arr.get(pos).setThumbBm(b);
    			if(b==null)
    			{
    				return;
    			}else{
    				if(type==FAVORITED)
    				{
    					sql.favor(arr.get(pos));
    					arr.get(pos).favor = 1;
    				}
    				else
    					arr.get(pos).favor=sql.getfavor(arr.get(pos).getId());
    			}
				postInvalidate();
				arr.get(pos).status = 0;
				b = null;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		if(prog!=null&&prog.isShowing())
				handler.post(new Runnable() {
					public void run() {
						try{
							prog.dismiss();
						}catch (Exception e) {
						}
					}
				});
		}
	}
	
	Image image;
	Paint p = new Paint();
	Rect dst;
	@Override
	protected void onDraw(Canvas c) 
	{
		if(imgfav==null)imgfav = BitmapFactory.decodeResource(getResources(), R.drawable.favor);
		try{
			if(images.getCount()<=0&&lock1==false){
				Paint p = new Paint();
				p.setTextAlign(Paint.Align.CENTER);
				p.setTextSize(20);
				p.setColor(Color.WHITE);
				c.drawText(father.getString(R.string.empty), getWidth()/2, getHeight()/2, p);
				return;
			}
			for(int i=0;i<images.img1.size();i++)
			{
				try{
					image = images.img1.get(i);
					if(image.top>y+getHeight()||image.top<y-image.hthumb||image.getThumbBm()==null||image.top==0)continue;
					dst.set(0, (int)(image.top-y), getWidth()/2, (int)(image.top-y+image.hthumb*getWidth()/(image.wthumb*2)));
					c.drawBitmap(image.getThumbBm(), image.src, dst, null);
					if(image.favor!=0)c.drawBitmap(imgfav, getWidth()/2-30, image.top-y+10, null);
				}catch (Exception e) {
				}
			}
			for(int i=0;i<images.img2.size();i++)
			{
				try{
					image = images.img2.get(i);
					if(image.top>y+getHeight()||image.top<y-image.hthumb||image.getThumbBm()==null||image.top==0)continue;
					dst.set(getWidth()/2, (int)(image.top-y), getWidth(), (int)(image.top-y+image.hthumb*getWidth()/(image.wthumb*2)));
					c.drawBitmap(image.getThumbBm(), image.src, dst, null);
					if(image.favor!=0)c.drawBitmap(imgfav, getWidth()-30, image.top-y+10, null);
				}catch (Exception e) {
				}
			}
		}catch (Exception e) {
		}
	}

	boolean first = true;
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(first)
		{
			images = new Images();
			new LoadImgsDown().start();
		}
		first = false;
		p.setTextSize(20);
	}

    float gravity = 8f,v=0;
    
    @Override
    protected void onDetachedFromWindow() 
    {
    	super.onDetachedFromWindow();
    	images = null;
		sql.close();
    	fling.interrupt();
    }
    
    class Fling extends Thread
    {
    	int firsL,firsR,last;
    	float lasty=0;
    	public void run()
    	{
    		while(true)
    		{
    			try {
					Thread.sleep(10);
					if(Math.abs(v)<=gravity)continue;
					
					v += v>0?-gravity:gravity;
					if(Math.abs(lasty-y)>500)
					{
						loadUp(RIGHT);
						loadUp(LEFT);
						lasty=y;
					}
					
					y+=(int)(v/100f);
					if(y<0){y=0;v=0;}
					if(getMax()>getHeight()&&y>getMax()-getHeight()/2){y=getMax()-getHeight()/2;v=0;}
					if(getMax()<getHeight()){y=1;v=0;}
					
					if(y>getMin()-getHeight()*1.5f&&!lock1)
					{
						new LoadImgsDown().start();
						v = 0;
					}
					postInvalidate();
				} catch (Exception e) {
				}
    		}
    	}
    }
    class TimeOut extends Thread
    {
    	public void run()
    	{
    		try {
				Thread.sleep(5000);
				handler.post(new Runnable() {
					public void run() {
						try{
							prog.dismiss();
						}catch (Exception e) {
						}
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
}
