package com.krazevina.euro2012;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.krazevina.objects.ImageDownloader;
import com.krazevina.rss.RSSFeed;
import com.krazevina.rss.RSSItem;
import com.krazevina.rss.XmlReader;


public class News extends Activity implements OnClickListener {
	
    Button btnSchedule,btnNews,btnTeams,btnSetting;
    LinearLayout llbtnsched,llbtnnews,llbtnteams,llbtnsetting;
    ViewFlipper vfnew;
    ListView lvnew;
    RSSFeed rss1;
    static int lang = 0;
    Handler handler;
    ProgressDialog pr;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        Log.e("LANG",Locale.getDefault().getDisplayLanguage());

        llbtnsched = (LinearLayout)findViewById(R.id.llbtnsched);
        llbtnnews = (LinearLayout)findViewById(R.id.llbtnnews);
        llbtnteams = (LinearLayout)findViewById(R.id.llbtnteams);
        llbtnsetting = (LinearLayout)findViewById(R.id.llbtnsetting);
        llbtnnews.setOnTouchListener(touch);
        llbtnsched.setOnTouchListener(touch);
        llbtnteams.setOnTouchListener(touch);
        llbtnsetting.setOnTouchListener(touch);
        
        btnSchedule = (Button)findViewById(R.id.btnSchedule);
        btnNews = (Button)findViewById(R.id.btnNews);
        btnTeams = (Button)findViewById(R.id.btnTeams);
        btnSetting = (Button)findViewById(R.id.btnSetting);
        
        vfnew = (ViewFlipper)findViewById(R.id.vfnews);
        lvnew = (ListView)findViewById(R.id.lvnews);
        
        btnTeams.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnSchedule.setOnClickListener(this);
        handler = new Handler();
        
        if(lang==0){
	        final CharSequence[] items = {"Tin tiếng Việt", "English news"};
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("");
	        builder.setItems(items, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int item) {
	                lang = item+1;
	                loadRss();
	            }
	        });
	        AlertDialog alert = builder.create();
	        alert.show();
        }else loadRss();
    }
    
    void loadRss(){
    	pr = ProgressDialog.show(News.this, null, getString(R.string.pleasewait));
        new Thread(new Runnable() {
			@Override
			public void run() {
				if(lang==1)rss1 = new XmlReader("http://bongdaplus.vn/_RSS_/4.rss").parse();
				else rss1 = new XmlReader("http://www.teamtalk.com/rss/15274").parse();
		        handler.post(new Runnable() {
					public void run() {
						pr.dismiss();
						if(rss1==null)Toast.makeText(News.this, R.string.nonetwork, 0).show();
						else{
							lvnew.setAdapter(new NewsAdapter(News.this));
							lvnew.setOnItemClickListener(new OnItemClickListener() {
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									showInfo(rss1.get(arg2));
									vfnew.setDisplayedChild(1);
								}
							});
						}
					}
				});
			}
		}).start();
    }
    
    public void onBackPressed() {
    	if(vfnew.getDisplayedChild()>0)vfnew.setDisplayedChild(0);
    	else super.onBackPressed();
    };
    
    ImageView img;
    WebView txtcon;
    Bitmap b;String head,tail;
    
    void showInfo(final RSSItem item){
    	head="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"+
    	"<html xmlns=\"http://www.w3.org/1999/xhtml\" dir=\"ltr\" lang=\"VN\">"+
    	"<head>"+
    		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
    	"</head>" +
    		"<body>";
    	String loading = "<p><img alt=\"loading\" src=\"load.gif\"/></p>";
    	tail = "</body></html>";
    	img = (ImageView)findViewById(R.id.imgnews);
    	img.setImageBitmap(null);
    	TextView tit = (TextView)findViewById(R.id.txtinfotitle);
    	txtcon = (WebView)findViewById(R.id.txtcontent);
//    	txtcon.set
    	tit.setText(item.getTitle());
    	txtcon.loadDataWithBaseURL("file:///android_asset/",head+" "+item.getDescription()+loading+tail, "text/html", "UTF-8", null);
    	new Thread(new Runnable() {
    		String url = item.getEnclosure();
    		RSSItem ite = item;
			public void run() {
				b = ImageDownloader.readImageByUrl(url);
				handler.post(new Runnable() {
					public void run() {
						img.setImageBitmap(b);
					}
				});
				final String s = getAdditionInfo(ite.getLink());
				handler.post(new Runnable() {
					public void run() {
						txtcon.loadData(head+" "+ite.getDescription()+s+tail,"text/html","UTF-8");
						Log.e("HTML:", head+" "+ite.getDescription()+s+tail);
					}
				});
			}
		}).start();
    }
    
    String getAdditionInfo(String url){
    	String s = "";
    	if(url.contains("bongda")){
    		try{
	    		HtmlCleaner cleaner = new HtmlCleaner();
	    		Uri ss = Uri.parse(url);
	    		URL u = new URL(ss.getScheme(), ss.getHost(), ss.getPort(), ss.getPath());
	            TagNode root = cleaner.clean(u);
	            TagNode div = root.findElementByAttValue("class", "story-body article", true, false);
	            TagNode[]child = div.getElementsByName("strong", true);
	            for(int i=0;i<child.length;i++)
	            	child[i].removeFromTree();
	            child = div.getElementsByName("p", true);
	            for(int i=0;i<child.length;i++)
	            	child[i].removeFromTree();
	            child = div.getElementsByName("img", true);
	            for(int i=0;i<child.length;i++)
	            	child[i].setAttribute("width", "100%");
	            s = cleaner.getInnerHtml(div);
	            s = s.replaceAll("src=\"/", "src=\"http://bongdaplus.vn/");
	            s = s.replaceAll("href=\"/", "href=\"http://bongdaplus.vn/");
	            s = URLEncoder.encode(s).replaceAll("\\+"," ");
    		}catch (Exception e) {
    			e.printStackTrace();
			}
    	}else if(url.contains("teamtalk")){
    		try{
	    		HtmlCleaner cleaner = new HtmlCleaner();
	    		Uri ss = Uri.parse(url);
	    		URL u = new URL(ss.getScheme(), ss.getHost(), ss.getPort(), ss.getPath());
	            TagNode root = cleaner.clean(u);
	            TagNode div = root.findElementByAttValue("class", "tt-article-text", true, false);
	            TagNode[]child = div.getElementsByName("p", true);
	            for(int i=0;i<child.length;i++)
	            	if(child[i].hasAttribute("class")||child[i].hasAttribute("style"))
	            		child[i].removeFromTree();
	            child = div.getElementsByName("img", true);
	            for(int i=0;i<child.length;i++)
	            	child[i].setAttribute("width", "100%");
	            s = cleaner.getInnerHtml(div);
	            s = s.replaceAll("src=\"/", "src=\"http://bongdaplus.vn/");
	            s = s.replaceAll("href=\"/", "href=\"http://bongdaplus.vn/");
	            s = URLEncoder.encode(s).replaceAll("\\+"," ");
    		}catch (Exception e) {
    			e.printStackTrace();
			}
    	}
    	return s;
    }
    
    OnTouchListener touch = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			try{
				LinearLayout l = (LinearLayout)v;
				((Button)l.getChildAt(0)).onTouchEvent(event);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	};
	
	@Override
	public void onClick(View v) {
		if(v.getId() == btnSchedule.getId()){
			startActivity(new Intent(this,Main.class));
			finish();
		}
		
		if(v.getId() == btnTeams.getId()){
			startActivity(new Intent(this,Teams.class));
			finish();
		}
		
		if(v.getId() == btnSetting.getId()){
			startActivity(new Intent(this,Setting.class));
			finish();
		}
	}
	
	public class NewsAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		
		public NewsAdapter(Context context){
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return rss1.getCount();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			  if (convertView == null) {
			   convertView = mInflater.inflate(R.layout.itemnews, null);
			   holder = new ViewHolder();
			   holder.tit = (TextView)convertView.findViewById(R.id.txttitle);
			   holder.time = (TextView)convertView.findViewById(R.id.txttime);
			   
			   
			   convertView.setTag(holder);
			  } else {
			   holder = (ViewHolder) convertView.getTag();
			  }
			  if(position%2==0){
					convertView.setBackgroundColor(Color.parseColor("#ffffff"));
				}else{
					convertView.setBackgroundColor(Color.parseColor("#e9efe9"));
				}
			  holder.tit.setText(rss1.get(position).getTitle());
			  holder.time.setText(rss1.get(position).getPubDate());

			  return convertView;
			 }
	}
		static class ViewHolder {
			TextView tit,time;
//			LinearLayout line;
		}
}