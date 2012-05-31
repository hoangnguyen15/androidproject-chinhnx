package com.krazevina.euro2012;

import java.net.URL;
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
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    ViewFlipper vfnew;Button btnlist;
    ListView lvnew;
    RSSFeed rss1;
    int site = 0;
    Handler handler;
    ProgressDialog pr;
    String sites[]= new String[]{"BongDaPlus.vn","LichEuro2012.com","TeamTalk.com","UEFA.com",
    		"Goal.com","Guardian.co.uk"};
    
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
        btnlist = (Button)findViewById(R.id.btnlist);
        
        vfnew = (ViewFlipper)findViewById(R.id.vfnews);
        lvnew = (ListView)findViewById(R.id.lvnews);
        
        btnTeams.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnSchedule.setOnClickListener(this);
        handler = new Handler();
        
        btnlist.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showList();
			}
		});
        showList();
    }
    AlertDialog alert;
    void showList(){
    	if(alert==null){
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("");
	        builder.setItems(sites, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int item) {
	            	if(vfnew.getDisplayedChild()>0){
		            	vfnew.setDisplayedChild(0);
		        		txtcon.loadData("", "text/html", "UTF-8");
	            	}
	                site = item+1;
	                loadRss();
	            }
	        });
	        alert = builder.create();
    	}
        alert.show();
    }
    
    void loadRss(){
    	pr = ProgressDialog.show(News.this, null, getString(R.string.pleasewait));
        new Thread(new Runnable() {
			@Override
			public void run() {
				if(site==1)rss1 = new XmlReader("http://bongdaplus.vn/_RSS_/4.rss").parse();
				else if(site==2)rss1 = new XmlReader("http://licheuro2012.com/feed/").parse();
				else if(site==3)rss1 = new XmlReader("http://www.uefa.com/rssfeed/uefaeuro/rss.xml").parse();
				else if(site==4)rss1 = new XmlReader("http://www.teamtalk.com/rss/15274").parse();
				else if(site==5)rss1 = new XmlReader("http://www.goal.com/en/feeds/news?id=2898&fmt=rss").parse();
				else if(site==6)rss1 = new XmlReader("http://www.guardian.co.uk/football/euro2012/rss").parse();
					
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
    	if(vfnew.getDisplayedChild()>0){
    		vfnew.setDisplayedChild(0);
    		txtcon.loadData("", "text/html", "UTF-8");
    	}
    	else super.onBackPressed();
    };
    
    ImageView img;
    WebView txtcon;
    LinearLayout parentLayout;
    Bitmap b;String head,tail;
    
    void showInfo(final RSSItem item){
    	img = (ImageView)findViewById(R.id.imgnews);
    	img.setImageBitmap(null);
    	TextView tit = (TextView)findViewById(R.id.txtinfotitle);
    	parentLayout = (LinearLayout)findViewById(R.id.wvcontainer);
    	txtcon = (WebView)findViewById(R.id.txtcontent);
    	head=""+
    	"<html xmlns:fb=\"http://ogp.me/ns/fb#\">\n"+
    	"<head>\n"+
    		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
    	"</head>\n" +
    	"<body>" +
    	"<p><div id=\"fb-root\"></div></p>"+
		"<script>"+
		  "window.fbAsyncInit = function() {"+
		    "FB.init({"+
		      "appId      : '111569915535689', // App ID"+
		      "status     : true, // check login status"+
		      "cookie     : true, // enable cookies to allow the server to access the session"+
		      "oauth      : true, // enable OAuth 2.0"+
		      "xfbml      : true  // parse XFBML"+
		    "});"+
		  "};"+
		"</script>\n"+
		"<script src=\"http://connect.facebook.net/en_US/all.js#xfbml=1\"></script>";
    	String loading = "";
    	tail = "<p style=\"clear:both;\"><div id=\"facebook-comments-4159769\" class=\"facebook-comments inited\">"+
			"<fb:comments href=\""+item.getLink()+"\" num_posts=\"5\" width=\""+(txtcon.getWidth()*80/100)+"\" colorscheme=\"dark\"></fb:comments>"+
		"</div></p>\n"+
    	"</body></html>";
    	
    	txtcon.setWebViewClient(new FaceBookClient());
    	txtcon.setWebChromeClient(new MyChromeClient());
    	txtcon.getSettings().setJavaScriptEnabled(true);
//    	txtcon.getSettings().setAppCacheEnabled(true);
    	txtcon.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    	txtcon.getSettings().setSupportMultipleWindows(true);
    	txtcon.getSettings().setSupportZoom(true);
    	txtcon.getSettings().setBuiltInZoomControls(true);
    	txtcon.getSettings().setJavaScriptEnabled(true);
    	txtcon.getSettings().setPluginsEnabled(true);
    	txtcon.getSettings().setAllowFileAccess(true);

    	tit.setText(item.getTitle());
    	Uri u = Uri.parse(item.getLink());
    	txtcon.loadDataWithBaseURL(u.getHost(),head+" "+item.getDescription()+loading+tail, "text/html", "UTF-8",null);
    	new Thread(new Runnable() {
    		String url = item.getEnclosure();
    		RSSItem ite = item;
			public void run() {
				b = ImageDownloader.readImageByUrl(url);
				if(b==null)
					handler.post(new Runnable() {
						public void run() {
							img.setVisibility(View.GONE);
						}
					});
				else
					handler.post(new Runnable() {
						public void run() {
							img.setImageBitmap(b);
						}
					});
				final String s = getAdditionInfo(ite.getLink());
				handler.post(new Runnable() {
					public void run() {
						Uri u = Uri.parse(item.getLink());
						if(site!=4)txtcon.loadDataWithBaseURL(u.getHost(),head+" "+ite.getDescription()+s+tail,"text/html","UTF-8",null);
						else txtcon.loadDataWithBaseURL(u.getHost(),head+" "+s+tail,"text/html","UTF-8",null);
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
	            TagNode div = root.findElementByAttValue("class", "tintuc_text", true, false);
	            TagNode[]child = div.getElementsByName("strong", true);
	            for(int i=0;i<child.length;i++)
	            	child[i].removeFromTree();
	            child = div.getElementsByName("p", true);
	            for(int i=0;i<child.length;i++)
	            	child[i].removeFromTree();
	            child = div.getElementsByName("img", true);
	            for(int i=0;i<child.length;i++)
	            	child[i].setAttribute("style", "width: 100%; height: auto;");
	            s = cleaner.getInnerHtml(div);
	            if(s.indexOf("VideoPlaying(")>0){
	            	int x = s.indexOf("VideoPlaying(");
	            	int st = s.indexOf("'", x);
	            	int en = s.indexOf("'", st+1);
	            	String urlvideo = "http://bongdaplus.vn"+s.substring(st+1, en);
	            	s = s+"<a href=\""+urlvideo+"\"> Video </a></br></br>";
	            }
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
	            	child[i].setAttribute("style", "max-width: 80%; height: auto;");
	            s = cleaner.getInnerHtml(div);
    		}catch (Exception e) {
    			e.printStackTrace();
			}
    	}else if(url.contains("licheuro")){
    		try{
	    		HtmlCleaner cleaner = new HtmlCleaner();
	    		Uri ss = Uri.parse(url);
	    		URL u = new URL(ss.getScheme(), ss.getHost(), ss.getPort(), ss.getPath());
	            TagNode root = cleaner.clean(u);
	            TagNode div = root.findElementByAttValue("class", "entry", true, false);
	            TagNode[]child = div.getElementsByName("span", true);
	            for(int i=0;i<child.length;i++)
	            		child[i].removeFromTree();
	            child = div.getElementsByAttValue("class","dd_outer", true, true);
	            for(int i=0;i<child.length;i++)
	            		child[i].removeFromTree();
	            child = div.getElementsByAttValue("class","ratingblock ", true, true);
	            for(int i=0;i<child.length;i++)
	            		child[i].removeFromTree();
	            child = div.getElementsByName("ol", true);
	            for(int i=0;i<child.length;i++)
	            		child[i].removeFromTree();
	            child = div.getElementsByName("table", true);
	            for(int i=0;i<child.length;i++){
	            	child[i].setAttribute("style", "max-width: 100%; height: auto;");
	            	child[i].setAttribute("width", "100%");
	            }
	            child = div.getElementsByName("script", true);
	            for(int i=0;i<child.length;i++)
	            	child[i].removeFromTree();
	            child = div.getElementsByName("img", true);
	            for(int i=0;i<child.length;i++)
	            	child[i].setAttribute("style", "max-width: 80%; height: auto;");
	            s = cleaner.getInnerHtml(div);
	            if(s.indexOf("\"file\":")>0){
	            	int x = s.indexOf("\"file\":");
	            	int st = s.indexOf("http", x);
	            	int en = s.indexOf("\"", st+1);
	            	String urlvideo = s.substring(st, en);
	            	s = s+"<a href=\""+urlvideo+"\"> Video </a></br></br>";
	            }
    		}catch (Exception e) {
    			e.printStackTrace();
			}
    	}
    	System.out.println(s);
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
			return null;
		}

		@Override
		public long getItemId(int position) {
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
		class ViewHolder {
			TextView tit,time;
		}
	}
	
	
	WebView childView;
	
	final class MyChromeClient extends WebChromeClient{
	    @Override
	    public boolean onCreateWindow(WebView view, boolean dialog,
	            boolean userGesture, Message resultMsg) {
	        childView = new WebView(News.this);
	        childView.getSettings().setJavaScriptEnabled(true);
	        childView.getSettings().setSupportZoom(true);
	        childView.getSettings().setBuiltInZoomControls(true);
	        childView.setWebViewClient(new FaceBookClient());
	        childView.setWebChromeClient(this);
	        childView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));


	        parentLayout.addView(childView);

	        childView.requestFocus();
	        txtcon.setVisibility(View.GONE);

	          /*I think this is the main part which handles all the log in session*/
	        WebView.WebViewTransport transport =(WebView.WebViewTransport)resultMsg.obj;
	        transport.setWebView(childView);
	        resultMsg.sendToTarget();
	        return true;
	    }


	    @Override
	    public void onProgressChanged(WebView view, int newProgress) {
	        setProgress(newProgress*100);
	    }

	    @Override
	    public void onCloseWindow(WebView window) {
	        parentLayout.removeViewAt(parentLayout.getChildCount()-1);
	        childView =null;
	        txtcon.setVisibility(View.VISIBLE);
	        txtcon.requestFocus();
	    }
	}

    private class FaceBookClient extends WebViewClient{
	     @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        Log.i("REQUEST URL",url);
	        return false;
	    }   
	}

}