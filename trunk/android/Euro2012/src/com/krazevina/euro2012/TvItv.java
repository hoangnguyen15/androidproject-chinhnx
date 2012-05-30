package com.krazevina.euro2012;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class TvItv extends Activity{

	WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wv = new WebView(TvItv.this);
		String s = "<center><embed wmode=\"opaque\" type=\"application/x-shockwave-flash\" style=\"background-color: black; color: rgb(51, 51, 51); font-family: Arial,serif; font-size: 14px; height: 465px; line-height: 22px;height:"+(getWindowManager().getDefaultDisplay().getHeight()*6/10)+"; width: "+(getWindowManager().getDefaultDisplay().getWidth()*6.8/10)+";\" src=\"http://player.longtailvideo.com/player.swf\" quality=\"high\" flashvars=\"streamer=rtmp://210.245.82.6/live/&file=itvw_hh&type=rtmp&fullscreen=true&autostart=true&controlbar=bottom&\" allowscriptaccess=\"always\" allowfullscreen=\"true\"></center>";
    	wv.getSettings().setJavaScriptEnabled(true);
    	wv.getSettings().setPluginsEnabled(true);
    	wv.loadDataWithBaseURL("http://123tivi.com", s, "text/html", "UTF-8", null);
    	setContentView(wv);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		wv.loadData("", "text/html", "UTF-8");
	}
}
