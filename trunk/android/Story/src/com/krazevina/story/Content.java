package com.krazevina.story;

import com.krazevina.objects.Global;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Content extends Activity implements OnClickListener {
	TextView title, content;
	Button btnBooked, btnNext, btnPrev;
	ScrollView scrollView;
	int position;
	int y;
//	Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);
		title = (TextView) findViewById(R.id.title);
		content = (TextView) findViewById(R.id.content);
		btnBooked = (Button) findViewById(R.id.btnbookmark);
		btnNext = (Button) findViewById(R.id.btnnext);
		btnPrev = (Button) findViewById(R.id.btnprev);
		scrollView = (ScrollView)findViewById(R.id.scrollview);
		
		boolean isBookmark = getIntent().getBooleanExtra("bookmark",false);
		if(isBookmark){
			position = getIntent().getExtras().getInt("pos",0);
			y = getIntent().getExtras().getInt("y",0);
		}else{
			position = getIntent().getIntExtra("position", 0);
		}
		
		Log.e("!@#.y=pos",""+y+"="+position);

		title.setText(Global.vt.elementAt(position).title);
		content.setText(Global.vt.elementAt(position).content+"\n\n\n");

//		new sc().start();
		
		scrollView.post(new Runnable() {
		    @Override
		    public void run() {
		    	scrollView.scrollTo(0, y);
		    } 
		});
		
//		handler = new Handler();		
//		new Thread(new Runnable(){
//			
//			public void run(){
//				handler.post(new Runnable() {
//					@Override
//					public void run() {
//						scrollView.scrollTo(0, 500);
//						
//					}
//				});
//			}}).start();

		btnBooked.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnPrev.setOnClickListener(this);

	}
	
//	protected class sc extends Thread{
//		public void run(){
//			handler.post(new Runnable() {
//				@Override
//				public void run() {
//					scrollView.scrollTo(0, 500);
//				}
//			});
//		}
//	}
//	
	@Override
	public void onClick(View v) {
		if (v.getId() == btnBooked.getId()) {
			y = scrollView.getScrollY();			
			SharedPreferences sp = getSharedPreferences("a", MODE_PRIVATE);
			Editor e = sp.edit();
			e.putInt("pos",position);
			e.putInt("y", y);
			e.commit();			
		}

		if (v.getId() == btnNext.getId()) {
			position++;
			if (position > Global.vt.size()-1) {
				Toast.makeText(this, "End", 2).show();
				position = Global.vt.size()-1;
			}
			title.setText(Global.vt.elementAt(position).title);
			content.setText(Global.vt.elementAt(position).content+"\n\n\n");
		}

		if (v.getId() == btnPrev.getId()) {
			position--;
			if (position <0) {
				Toast.makeText(this, "First", 2).show();
				position = 0;
			}
			
			title.setText(Global.vt.elementAt(position).title);
			content.setText(Global.vt.elementAt(position).content+"\n\n\n");
		}

	}
}
