package com.krazevina.euro2012;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.krazevina.objects.Bet;
import com.krazevina.objects.Global;
import com.krazevina.objects.Match;
import com.krazevina.objects.Player;
import com.krazevina.objects.SocketConnect;
import com.krazevina.objects.Team;
import com.krazevina.objects.sqlite;

public class MatchDetail extends Activity implements OnClickListener{
	
	ImageView flag1,flag2;
	TextView name1,name2,score,bet11,bet12,bet13,bet21,bet22,bet23,bet31,bet32,bet33;
	TextView stadium,referee;
	LinearLayout llevent;
	sqlite sql;
	Match m;Team t1,t2;
	Player p;
	int i;WebView wv;
	Button btnBack,btnvote1,btnvote2;
	Vector<Bet> b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matchdetail);
		sql = new sqlite(this);
		m = Global.match;
		m.events = sql.getEvents(m);
		b = sql.getBet(m.ID);
		flag1 = (ImageView)findViewById(R.id.flag1);
		flag2 = (ImageView)findViewById(R.id.flag2);
		name1 = (TextView)findViewById(R.id.name1);
		name2 = (TextView)findViewById(R.id.name2);
		score = (TextView)findViewById(R.id.score);
		stadium = (TextView)findViewById(R.id.stadium);
		referee = (TextView)findViewById(R.id.referee);
		llevent = (LinearLayout)findViewById(R.id.llevent);
		btnBack = (Button)findViewById(R.id.btnBack);
		bet11 = (TextView)findViewById(R.id.txtbet11);
		bet12 = (TextView)findViewById(R.id.txtbet12);
		bet13 = (TextView)findViewById(R.id.txtbet13);
		bet21 = (TextView)findViewById(R.id.txtbet21);
		bet22 = (TextView)findViewById(R.id.txtbet22);
		bet23 = (TextView)findViewById(R.id.txtbet23);
		bet31 = (TextView)findViewById(R.id.txtbet31);
		bet32 = (TextView)findViewById(R.id.txtbet32);
		bet33 = (TextView)findViewById(R.id.txtbet33);
		btnvote1 = (Button)findViewById(R.id.btnvote1);
		btnvote2 = (Button)findViewById(R.id.btnvote2);
		wv = (WebView)findViewById(R.id.wvcomment);
		parentLayout = (LinearLayout)findViewById(R.id.llwvcontainer);
		
		
		String head=""+
    	"<html xmlns:fb=\"http://ogp.me/ns/fb#\">\n"+
    	"<head>\n"+
    		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
    		"<meta property=\"fb:admins\" content=\"http//facebook.com/nna.cuong\"/>"+
			"<meta property=\"fb:app_id\" content=\"111569915535689\"/>"+
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
		"</script>"+
		"<script src=\"http://connect.facebook.net/en_US/all.js#xfbml=1\"></script>";
    	String tail = "<p><div id=\"facebook-comments-4159769\" class=\"facebook-comments inited\">"+
			"<fb:comments href=\"http://gaixinh.com.vn/euro"+m.ID+"\" num_posts=\"5\" width=\""+(getWindowManager().getDefaultDisplay().getWidth()*80/100)+"\" colorscheme=\"dark\"></fb:comments>"+
		"</div></p>\n"+
    	"</body></html>";
		wv.setWebViewClient(new FaceBookClient());
    	wv.setWebChromeClient(new MyChromeClient());
    	wv.getSettings().setJavaScriptEnabled(true);
    	wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    	wv.getSettings().setSupportMultipleWindows(true);
    	wv.getSettings().setSupportZoom(true);
    	wv.getSettings().setBuiltInZoomControls(true);
    	wv.getSettings().setJavaScriptEnabled(true);
    	wv.getSettings().setPluginsEnabled(true);
    	wv.getSettings().setAllowFileAccess(true);
    	wv.loadDataWithBaseURL("http://gaixinh.com.vn",head+tail, "text/html", "UTF-8",null);
		btnBack.setOnClickListener(this);
		
		flag1.setImageResource(TeamDetails.flag(m.team1));
		flag2.setImageResource(TeamDetails.flag(m.team2));
		t1 = sql.getTeam(m.team1);
		t2 = sql.getTeam(m.team2);
		
		name1.setText(t1.name);
		name2.setText(t2.name);
		btnvote1.setText(""+m.firstPick);
		btnvote2.setText(""+m.secPick);
		if(m.finalScore!=null&&m.finalScore.length()>0)score.setText(m.finalScore);
		else score.setText("?-?");
		stadium.setText(m.stadium);
		referee.setText(m.referee);
		
		LinearLayout ll;
		LayoutInflater mInflater = LayoutInflater.from(this);
		TextView txttime,txtname;
		ImageView imgtype1,imgtype2;
		boolean end = false;
		int j = 0;
		if(m.events.size()>0){
			for(i = 0;i<m.events.size();i++){
				ll = (LinearLayout) mInflater.inflate(R.layout.itemschedule, null);
				ll.setOrientation(LinearLayout.HORIZONTAL);
	
				txttime = (TextView)ll.findViewById(R.id.time);
				txtname = (TextView)ll.findViewById(R.id.name);
				imgtype1 = (ImageView)ll.findViewById(R.id.type1);
				imgtype2 = (ImageView)ll.findViewById(R.id.type2);
				txttime.setText(m.events.get(i).time);
				p = sql.getlayer(m.events.get(i).playerID);
				txtname.setText(p.name);
				
				if(m.team1==m.events.get(i).teamID){
					imgtype1.setImageResource(Type(m.events.get(i).eventID));
					imgtype2.setVisibility(View.GONE);
					txtname.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
				}else{
					imgtype2.setImageResource(Type(m.events.get(i).eventID));
					imgtype1.setVisibility(View.GONE);
					txtname.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
				}
				
				j++;
				if(j%2==0){
					ll.setBackgroundColor(Color.parseColor("#ffffff"));
				}else{
					ll.setBackgroundColor(Color.parseColor("#e9efe9"));
				}
				if(m.events.get(i).eventID==7)end = true;
				llevent.addView(ll);
			}
			if(end==false){
				ImageButton b = new ImageButton(MatchDetail.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(70, 40);
				b.setLayoutParams(lp);
				b.setBackgroundColor(Color.argb(0, 0, 0, 0));
				b.setImageResource(R.drawable.icondetail);
				llevent.addView(b);
				b.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						try{
							Intent i = new Intent(MatchDetail.this,Tv.class);
					        i.setData(Uri.parse("rtsp://tv.hdvnbits.org:1935/live/VTV3azn.stream"));
					        startActivity(i);
						}catch (Exception e) {
							Toast.makeText(MatchDetail.this, R.string.needvideoplayer, 1).show();
						}
					}
				});
			}
		}
		else{
			TextView t = new TextView(MatchDetail.this);
			t.setText(R.string.notstart);
			llevent.addView(t);
			ImageButton b = new ImageButton(MatchDetail.this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(70, 40);
			b.setLayoutParams(lp);
			b.setBackgroundColor(Color.argb(0, 0, 0, 0));
			b.setImageResource(R.drawable.icondetail);
			llevent.addView(b);
			b.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					try{
						if(m.tv==0){
							Intent i = new Intent(Intent.ACTION_VIEW);
					        i.setData(Uri.parse("rtsp://tv.hdvnbits.org:1935/live/VTV3azn.stream"));
					        startActivity(i);
						}else{
							Intent i = new Intent(MatchDetail.this,Tv.class);
					        startActivity(i);
						}
					}catch (Exception e) {
						Toast.makeText(MatchDetail.this, R.string.needvideoplayer, 1).show();
					}
				}
			});
		}
		bet11.setText(getAsia().col1);
		bet12.setText(getAsia().col2);
		bet13.setText(getAsia().col3);
		bet21.setText(getEuro().col1);
		bet22.setText(getEuro().col2);
		bet23.setText(getEuro().col3);
		bet31.setText(getUO().col1);
		bet32.setText(getUO().col2);
		bet33.setText(getUO().col3);
		btnvote1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new SocketConnect().vote(m.ID, 1);
				m.firstPick++;
				btnvote1.setText(""+m.firstPick);
				btnvote1.setEnabled(false);
			}
		});
		btnvote2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new SocketConnect().vote(m.ID, 2);
				m.secPick++;
				btnvote2.setText(""+m.secPick);
				btnvote2.setEnabled(false);
			}
		});
	}
	int Type(int t){
		switch (t) {
		case 1:return R.drawable.goal;
		case 2:return R.drawable.yellow;
		case 3:return R.drawable.red;
		case 4:return 0;
		case 5:return 0;

		default:
			return 0;
		}
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnBack.getId()){
			finish();
		}
	}
	Bet getAsia(){
		for(int i=0;i<b.size();i++)if(b.get(i).bethouse==1)return b.get(i);
		return new Bet();
	}
	Bet getEuro(){
		for(int i=0;i<b.size();i++)if(b.get(i).bethouse==3)return b.get(i);
		return new Bet();
	}
	Bet getUO(){
		for(int i=0;i<b.size();i++)if(b.get(i).bethouse==2)return b.get(i);
		return new Bet();
	}
	@Override
	protected void onDestroy() {
		sql.recycle();
		super.onDestroy();
	}
	
	WebView childView;
	LinearLayout parentLayout;
	
	final class MyChromeClient extends WebChromeClient{
	    @Override
	    public boolean onCreateWindow(WebView view, boolean dialog,
	            boolean userGesture, Message resultMsg) {
	        childView = new WebView(MatchDetail.this);
	        childView.getSettings().setJavaScriptEnabled(true);
	        childView.getSettings().setSupportZoom(true);
	        childView.getSettings().setBuiltInZoomControls(true);
	        childView.setWebViewClient(new FaceBookClient());
	        childView.setWebChromeClient(this);
	        childView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));

	        parentLayout.addView(childView);

	        childView.requestFocus();
	        wv.setVisibility(View.GONE);

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
	        wv.setVisibility(View.VISIBLE);
	        wv.requestFocus();
	    }
	}

    private class FaceBookClient extends WebViewClient{
	     @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        return false;
	    }   
	}
    
}

