package com.krazevina.euro2012;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class TvItv extends Activity{
	String s = "<center><embed wmode=\"opaque\" type=\"application/x-shockwave-flash\" style=\"background-color: black; color: rgb(51, 51, 51); font-family: Arial,serif; font-size: 14px; height: 465px; line-height: 22px; width: 560px;\" src=\"http://player.longtailvideo.com/player.swf\" quality=\"high\" flashvars=\"streamer=rtmp://210.245.82.6/live/&amp;file=itvw_hh&amp;type=rtmp&amp;fullscreen=true&amp;autostart=true&amp;controlbar=bottom&amp;logo=logo.png&amp;\" allowscriptaccess=\"always\" allowfullscreen=\"true\"></center>";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView wv = new WebView(TvItv.this);
//    	wv.getSettings().setJavaScriptEnabled(true);
//    	wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//    	wv.getSettings().setSupportMultipleWindows(true);
//    	wv.getSettings().setSupportZoom(true);
//    	wv.getSettings().setBuiltInZoomControls(true);
    	wv.getSettings().setJavaScriptEnabled(true);
    	wv.getSettings().setPluginsEnabled(true);
//    	wv.getSettings().setAllowFileAccess(true);
//    	wv.loadUrl("http://farm.vtc.vn/media/vtcnews/resources/swf/flv/flvplayer.swf?file=vtv2&streamer=rtmp://117.103.225.20/live&provider=rtmp&autostart=true");
    	wv.loadDataWithBaseURL("http://123tivi.com", s, "text/html", "UTF-8", null);
    	setContentView(wv);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
}
