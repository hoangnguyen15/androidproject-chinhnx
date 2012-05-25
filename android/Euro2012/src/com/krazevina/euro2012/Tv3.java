package com.krazevina.euro2012;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Tv3 extends Activity{
	WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wv = new WebView(Tv3.this);
//    	wv.getSettings().setJavaScriptEnabled(true);
//    	wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//    	wv.getSettings().setSupportMultipleWindows(true);
//    	wv.getSettings().setSupportZoom(true);
//    	wv.getSettings().setBuiltInZoomControls(true);
    	wv.getSettings().setJavaScriptEnabled(true);
    	wv.getSettings().setPluginsEnabled(true);
//    	wv.getSettings().setAllowFileAccess(true);
    	wv.loadUrl("http://farm.vtc.vn/media/vtcnews/resources/swf/flv/flvplayer.swf?file=vtv31&streamer=rtmp://117.103.225.20/live&provider=rtmp&autostart=true");
    	setContentView(wv);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		wv.loadData("", "text/html", "UTF-8");
	}
}
