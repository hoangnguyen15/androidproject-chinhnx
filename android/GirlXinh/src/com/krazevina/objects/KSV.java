package com.krazevina.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import com.krazevina.beautifulgirl.Main;
import com.krazevina.beautifulgirl.MainSearch;
import com.krazevina.beautifulgirl.R;
import com.krazevina.beautifulgirl.Single;
import com.krazevina.webservice.CallWebService;

public class KSV extends ScrollView
{
	public static final int RANDOM = 1,TOP = 2,DOWNLOADED = 3,UPLOADED = 4,FAVORITED = 5,ALBUM = 6,
		SEARCHNEW = 7,FOLLOWID = 8, HOT = 9, FOLLOWNEW = 10, FOLLOWRAND = 11, SEARCHHOT = 12, 
				SEARCHTOP = 13;
    sqlite sql;
    int leftCurrentIndex,rightCurrentIndex;
    int LEFT=1,RIGHT=2;
	public Images images;
	float top,vW,vH;
	int topspace;
	Activity father;
    Handler handler;
    LinearLayout layout_image2,layout_image1;
    Options op;
    
    public int type;
    Fling fling;
    
    
    public KSV(Context context) {
		super(context);
	}

	public KSV(Context context, AttributeSet attr) 	{
		super(context,attr);
	}
	
	public KSV(Context context, AttributeSet attr, int i) {
		super(context, attr, i);
	}
	
	public void clearImg()
	{
		for(int i=0;i<images.getCount();i++)
		{
			try{
				images.getItemAtPosition(i).setBm(null);
				images.getItemAtPosition(i).imgv.setImageBitmap(null);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setup(Activity a,int type)
	{
		father = a;
		this.type = type;
		try{
		switch (type) {
		case HOT:
			((Main)father).txt_Uploader.setText(R.string.hot);
			break;
		case RANDOM:
			((Main)father).txt_Uploader.setText(R.string.moi);
			break;
		case TOP:
			((Main)father).txt_Uploader.setText(R.string.top);
			break;
		case DOWNLOADED:
			((Main)father).txt_Uploader.setText(R.string.mn_picload);
			break;
		case FAVORITED:
			((Main)father).txt_Uploader.setText(R.string.favorited);
			break;
		case UPLOADED:
			((Main)father).txt_Uploader.setText(R.string.up);
			break;
		case FOLLOWID:
			((Main)father).txt_Uploader.setText(R.string.imgfollow);
			break;
		}
		}catch (Exception e) {
		}
		dst = new Rect();
		handler = new Handler();
		fling = new Fling();
		fling.start();
		images = new Images();
		
		LinearLayout ll = new LinearLayout(a);
		layout_image1 = new LinearLayout(a);
		layout_image2 = new LinearLayout(a);
		layout_image1.setOrientation(LinearLayout.VERTICAL);
		layout_image2.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams lp,lp1;
		lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		lp1 = new LinearLayout.LayoutParams(0,LayoutParams.FILL_PARENT);
		lp1.weight = 1;
		ll.setLayoutParams(lp);
		layout_image1.setLayoutParams(lp1);
		layout_image2.setLayoutParams(lp1);
		LinearLayout lltemp1 = new LinearLayout(a);
		LinearLayout lltemp2 = new LinearLayout(a);
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, a.getResources().getDisplayMetrics()));
		topspace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, a.getResources().getDisplayMetrics());
		lltemp1.setLayoutParams(lp);
		lltemp2.setLayoutParams(lp);
		layout_image1.addView(lltemp1);
		layout_image2.addView(lltemp2);
		ll.addView(layout_image1);
		ll.addView(layout_image2);
		addView(ll);
		op = new Options();
		op.inScaled = false;
	}
	public static boolean hasStorage(boolean requireWriteAccess) {  
        String state = Environment.getExternalStorageState();  
      
        if (Environment.MEDIA_MOUNTED.equals(state)) {  
            return true;  
        } else if (!requireWriteAccess  
                && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {  
            return true;  
        }  
        return false;  
    } 
	File f;
	public void change(int type)
	{
		this.type = type;
		try{
		((Main)father).findview();
		}catch (Exception e) {
		}
		time=0;
		y = 1;
		first = false;
		images = new Images();
		layout_image1.removeAllViews();
		layout_image2.removeAllViews();
		
		LinearLayout.LayoutParams lp;
		LinearLayout lltemp1 = new LinearLayout(father);
		LinearLayout lltemp2 = new LinearLayout(father);
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, father.getResources().getDisplayMetrics()));
		lltemp1.setLayoutParams(lp);
		lltemp2.setLayoutParams(lp);
		layout_image1.addView(lltemp1);
		layout_image2.addView(lltemp2);
		images.h1 = images.h2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, father.getResources().getDisplayMetrics());

		if(prog!=null&&prog.isShowing())prog.dismiss();
		prog = null;
		
		if(type == DOWNLOADED && hasStorage(true))
		{
			prog = ProgressDialog.show(father, "", father.getString(R.string.pleasewait));
			f = new File(Environment.getExternalStorageDirectory()+"/GX/dir");
			new Thread(new Runnable() {
				public void run() {
					images.scanSDCard(father,father.getWindowManager().getDefaultDisplay().getWidth()/2);
					restore(0);
					loadUp(LEFT);
					loadUp(RIGHT);
					handler.post(new Runnable() {
						public void run() {
							try{
								prog.dismiss();
							}catch (Exception e) {
							}
						}
					});
				}
			}).start();
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
		try{
			return images.h1>images.h2?images.h1:images.h2;
		}catch (Exception e) {
			return 0;
		}
	}
	public float getMin()
	{
		return layout_image1.getHeight()<layout_image2.getHeight()?layout_image1.getHeight():layout_image2.getHeight();
	}
	public int getMinPos()
	{
		return layout_image1.getHeight()<layout_image2.getHeight()?LEFT:RIGHT;
	}
	
	ArrayList<Image> arr;
	Vector<KImageView>img3,img4;
	
	long start,mid;
	long space;
	int i;
	boolean lock = false;
	
	public void restore(final int pos)
	{
		img3 = new Vector<KImageView>();
		img4 = new Vector<KImageView>();
		for(int count=0;count<images.getCount();count++)
		{
			image = images.getItemAtPosition(count);
			if(image.imgv==null)
			{
				image.imgv = new KImageView(father,image);
				image.imgv.setImageBitmap(image.getBm(),getScrollY(),getHeight());
				lp = new LinearLayout.LayoutParams(image.wthumb, image.hthumb);
				image.imgv.setLayoutParams(lp);
				if(image.inleft){
					img3.add(image.imgv);
					image.imgv.setImageBitmap(image.getThumbBm());
				}
				else{
					img4.add(image.imgv);
					image.imgv.setImageBitmap(image.getThumbBm());
				}
			}
		}
		handler.post(new Runnable() {
			int posi = pos;
			public void run() {
				if(img3==null||img4==null)return;
				for(i=0;i<img3.size();i++)
					try{
						layout_image1.addView(img3.get(i));
					}catch (Exception e) {
						e.printStackTrace();
					}
				for(i=0;i<img4.size();i++)
					try{
						layout_image2.addView(img4.get(i));
					}catch (Exception e) {
						e.printStackTrace();
					}
				img3.clear();img4.clear();
				setPos(posi);
			}
		});
		
	}
	
	public void loadUp(int left)
	{
		if(lock)return;
		lock = true;
		y = getScrollY();
//		if(y>=getMaxScrollAmount()&&type==DOWNLOADED)
//		{
//			try{
//			((Main)father).hideMenu();
//			}catch (Exception e) {
//				try{
//				((MainSearch)father).hideMenu();
//				}catch (Exception ex) {
//				}
//			}
//		}
		try{
			arr = (left==1?images.img1:images.img2);
	
			start = 0;mid = (int) (y);
			space = getHeight()*3;
			for(i = 0;i<arr.size();i++)
			{
				try{
					if(Math.abs(arr.get(i).top+arr.get(i).hthumb-mid)<space)
					{
						if(arr.get(i).imgv.imgseted==false&&arr.get(i).getThumbBm()!=null)
						{
							handler.post(new Runnable() {
								Image ima = arr.get(i);
								public void run() {
									ima.imgv.setImageBitmap(ima.getThumbBm(), getScrollY(), getHeight());
								}
							});
						}
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
						handler.post(new Runnable() {
							Image in = arr.get(i);
							public void run() {
								try{
									in.imgv.setImageBitmap(null);
								}catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				}catch (Exception e) {
					continue;
				}
			}
		}catch (Exception e) {
		}
		lock = false;
	}
	
	
	public void setPos(final int pos)
	{
		try{
			y = images.getItemAtPosition(pos).top-2*topspace;
		}catch (Exception e) {
			y = 1;
		}
		handler.post(new Runnable() {
			public void run() {
				try{
					scrollTo(0, images.getItemAtPosition(pos).top-2*topspace);
					loadUp(LEFT);
					loadUp(RIGHT);
				}catch (Exception e) {
				}
			}
		});
	}
	
	MotionEvent evdown;
	boolean dontcare;
	@Override
	public boolean onTouchEvent(MotionEvent e) 
	{
		if(e.getAction()==MotionEvent.ACTION_UP)
		{
			down = true;
			if(dontcare)
			{
				dontcare = false;
				try{
					return ((Main)father).layout_menu.onTouchEvent(e);
				}catch (Exception ex) {
				}
			}
			timetouchup = System.currentTimeMillis();
			if(Math.abs(e.getX()-downx)+Math.abs(e.getY()-downy)<30)	//click
			{
				if(images.getCount()<=0)return true;
				int ind = find(e.getX(), e.getY()+y-50);
				if(ind<0)
					return true;
				if(images.getItemAtPosition(ind).getThumbBm()==null)
				{
					if((images.getItemAtPosition(ind).thread==null||
							!images.getItemAtPosition(ind).thread.isAlive()))
					{
						if(e.getX()<getWidth()/2)
							new LoadImg(find(e.getY(),LEFT), LEFT).start();
						else
							new LoadImg(find(e.getY(),RIGHT), RIGHT).start();
					}
					Toast.makeText(father, R.string.loading, 0).show();
					return true;
				}
				Intent in = new Intent(father,Single.class); 
				in.putExtra("index", ind);
				Single.images = images;
				if(MainSearch.class.isInstance(father))Single.fromSearch = true;
				else Single.fromSearch = false;
				father.startActivityForResult(in,0);
				return true;
			}
			if(timetouchup - timetouchdown<550) // fling
			{
				fling((int) (-(e.getY()-firstdowny)*1000/(timetouchup-timetouchdown)));
			}
			loadUp(1);
			loadUp(2);
			checkLoadDown();
		}
		if(e.getAction()==MotionEvent.ACTION_DOWN)
		{
			evdown = MotionEvent.obtain(e);
			timetouchdown = System.currentTimeMillis();
			y = getScrollY();
			downx = e.getX(0);
			downy = e.getY(0);
			firstdowny = e.getY(0);
			oldy = y;
			autoSlide = false;
			v = 0;
			down = false;
			dontcare = false;
		}
		if(e.getAction()==MotionEvent.ACTION_MOVE)
		{
			if(!down&&Math.abs(e.getY()-downy)*1.5<Math.abs(e.getX()-downx)
					&&Math.abs(e.getX()-downx)+Math.abs(e.getY()-downy)>70)
			{
				try{
					((Main)father).layout_menu.onTouchEvent(evdown);
				}catch (Exception ex) {
				}
				dontcare = true;
				down = true;
				return true;
			}
			if(dontcare)
			{
				try{
				((Main)father).layout_menu.onTouchEvent(e);
				}catch (Exception ex) {
				}
				return true;
			}
			y = oldy-(e.getY()-downy);
			if(y<0)y = 0;
			if(getMax()>getHeight()&&y>getMax()-getHeight()/2)y = getMax()-getHeight()/2;
			if(getMax()<getHeight())y=1;
			smoothScrollTo(0, (int) y);
		}
		invalidate();
		return true;
	}
	
	public boolean lock1=false;
	ProgressDialog prog;
	LinearLayout.LayoutParams lp;
	Vector<KImageView>img1,img2;
	
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
		KImageView imgv;
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
								if(prog==null){
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
				if(res!=null)
					first = false;
				else{
				}
				
				try 
				{
					final JSONArray jArray = new JSONArray(res);
					img1 = new Vector<KImageView>();
					img2 = new Vector<KImageView>();
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
						
						imgv = new KImageView(father,image);
						imgv.setImageBitmap(image.getBm(),getScrollY(),getHeight());
						lp = new LinearLayout.LayoutParams(image.wthumb, image.hthumb);
						image.imgv = imgv;
						imgv.setLayoutParams(lp);
						if(images.h1<=images.h2){
							images.add(image,LEFT);
							image.top = (int) images.h1;
							images.h1 += image.hthumb;
							img1.add(imgv);
						}
						else{
							images.add(image,RIGHT);
							image.top = (int) images.h2;
							images.h2 += image.hthumb;
							img2.add(imgv);
						}
					}
					handler.post(new Runnable() {
						public void run() {
							if(img1==null||img2==null)return;
							for(int i=0;i<img1.size();i++)
								try{
								layout_image1.addView(img1.get(i));
								}catch (Exception e) {
									e.printStackTrace();
								}
							for(int i=0;i<img2.size();i++)
								try{
								layout_image2.addView(img2.get(i));
								}catch (Exception e) {
									e.printStackTrace();
								}
							img1.clear();img2.clear();
						}
					});
					loadUp(LEFT);
					loadUp(RIGHT);
				}
				catch (Exception e) {
					if(res==null)
					handler.post(new Runnable() {
						@Override
						public void run() {
							try{
								if(System.currentTimeMillis()-time>120*1000)
								{
									if(alert==null)
									{
										AlertDialog.Builder builder = new AlertDialog.Builder(father);
										builder.setMessage(R.string.noconnect)
									       .setCancelable(false)
									       .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
									           public void onClick(DialogInterface dialog, int id) {
									        	   dialog.cancel();
									        	   time = System.currentTimeMillis() - 117*1000;
									           }
									       });
										alert = builder.create();
										alert.setCancelable(true);
										alert.setCanceledOnTouchOutside(true);
									}
									alert.show();
									time = System.currentTimeMillis();
								}
							}catch (Exception e) {
							}
						}
					});
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
	public long time;
	AlertDialog alert;
	
	class LoadImg extends Thread
	{
		int pos,left;
		boolean insert1;
		LinearLayout.LayoutParams lp;
		ArrayList<Image>arr;
		KImageView imv;
		Bitmap b = null;
		
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
        		File f;
        		switch (arr.get(pos).getId()) {
				case -1:
					f = new File(Environment.getExternalStorageDirectory()+
							"/GX/thumb/"+arr.get(pos).getName());
					if(f.canRead())
	        		{
	        			try {
	            			b = BitmapFactory.decodeFile(f.getAbsolutePath(),op);
						} catch (Exception e) {
						} catch (Error e) {
						}
	        		}
					break;
				default:
					if(arr.get(pos).isdownloaded)
					{
						b = arr.get(pos).getThumbFromSD(getWidth()/2);
					}
					if(b!=null)break;
					f = new File(Environment.getExternalStorageDirectory()+
							"/GX/thumb/"+arr.get(pos).getId());
					if(f.canRead())
	        		{
	        			try {
	            			b = BitmapFactory.decodeFile(f.getAbsolutePath(),op);
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
        			}
					if(Global.err>=10)
					{
						Global.err = 0;
						father.overridePendingTransition(0, 0);
						father.finish();
						father.overridePendingTransition(0, 0);
						father.startActivity(father.getIntent());
						father.overridePendingTransition(0, 0);
					}
        		}
				
    			arr.get(pos).setThumbBm(b);
    			
    			if(type==FAVORITED)
				{
					sql.favor(arr.get(pos));
					arr.get(pos).favor = 1;
				}
				else
					arr.get(pos).favor=sql.getfavor(arr.get(pos).getId());
    			
    			handler.post(new Runnable() {
    				Bitmap bi = b;
    				int posi = pos;
					public void run() {
						try{
							arr.get(posi).imgv.setImageBitmap(bi,getScrollY(),getHeight());
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
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
		super.onDraw(c);
		try{
			if(images.getCount()<=0&&lock1==false){
				Paint p = new Paint();
				p.setTextAlign(Paint.Align.CENTER);
				p.setTextSize(20);
				p.setColor(Color.WHITE);
				if(type==FOLLOWID||type==FOLLOWRAND||type==FOLLOWNEW)
					c.drawText(father.getString(R.string.emptyfollow), getWidth()/2, getHeight()/2, p);
				else
					c.drawText(father.getString(R.string.empty), getWidth()/2, getHeight()/2, p);
				return;
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
	protected void onAttachedToWindow() 
    {
		super.onAttachedToWindow();
		try{
			sql = new sqlite(father);
		}catch (Exception e) {
			try{
			sql = new sqlite(getContext());
			}catch (Exception ex) {
			}
		}
	}

	@Override
    protected void onDetachedFromWindow() 
    {
    	super.onDetachedFromWindow();
    	father = null;
		images = null;
    	if(images!=null)
    	{
    		try{
	    		for(int i=0;i<images.getCount();i++)
	    		{
	    			images.getItemAtPosition(i).setBm(null);
	    			images.getItemAtPosition(i).setThumbBm(null);
	    		}
	    		images.clear();
    		}catch (Exception e) {
			}
    	}
    	try{
			sql.close();
	    	fling.interrupt();
    	}catch (Exception e) {
		}
    }
    
    class Fling extends Thread
    {
    	int firsL,firsR,last;
    	float lasty=-1110;
    	public void run()
    	{
    		while(true)
    		{
    			try 
    			{
					Thread.sleep(300);
					y = getScrollY();
					if(Math.abs(lasty-y)>500)
					{
						loadUp(RIGHT);
						loadUp(LEFT);
						lasty=y;
					}
					
					if(y>getMin()-getHeight()*2f&&!lock1)
					{
						new LoadImgsDown().start();
					}
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
