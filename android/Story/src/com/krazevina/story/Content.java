package com.krazevina.story;

import com.krazevina.objects.Global;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Content extends Activity implements OnClickListener,
		OnTouchListener {
	TextView title, content;
	Button btnBookmarked, btnNext, btnPrev;
	Button btnDay, btnNight, btnMinus, btnPlus;
	ScrollView scrollView;
	int position, y, textSize;
	int minTextSize = 8;
	int maxTextSize = 30;
	String chap;
	LinearLayout llmenu;
	RelativeLayout llbg;
	boolean isMenu = false;
	SharedPreferences sp;

	// Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);
		title = (TextView) findViewById(R.id.title);
		content = (TextView) findViewById(R.id.content);
		btnBookmarked = (Button) findViewById(R.id.btnbookmark);
		btnNext = (Button) findViewById(R.id.btnnext);
		btnPrev = (Button) findViewById(R.id.btnprev);
		scrollView = (ScrollView) findViewById(R.id.scrollview);
		llmenu = (LinearLayout) findViewById(R.id.llmenu);
		llbg = (RelativeLayout) findViewById(R.id.llbg);
		btnDay = (Button) findViewById(R.id.btnday);
		btnNight = (Button) findViewById(R.id.btnnight);
		btnMinus = (Button) findViewById(R.id.btnminus);
		btnPlus = (Button) findViewById(R.id.btnplus);
		
		
		sp = getSharedPreferences("a", MODE_PRIVATE);
		textSize = sp.getInt("textSize", 14);
		content.setTextSize(textSize);
		boolean isBookmark = getIntent().getBooleanExtra("bookmark", false);
		if (isBookmark) {
			position = getIntent().getExtras().getInt("pos", 0);
			y = getIntent().getExtras().getInt("y", 0);
		} else {
			position = getIntent().getIntExtra("position", 0);
		}
		if (position == Global.vt.size() - 1) {
			btnNext.setVisibility(View.INVISIBLE);
		} else if (position == 0) {
			btnPrev.setVisibility(View.INVISIBLE);
		}

		chap = Global.vt.elementAt(position).title;
		title.setText(chap);
		content.setText(Global.vt.elementAt(position).content);
		content.setOnTouchListener(this);

		// new sc().start();

		scrollView.post(new Runnable() {
			@Override
			public void run() {
				scrollView.scrollTo(0, y);
			}
		});

		// handler = new Handler();
		// new Thread(new Runnable(){
		//
		// public void run(){
		// handler.post(new Runnable() {
		// @Override
		// public void run() {
		// scrollView.scrollTo(0, 500);
		//
		// }
		// });
		// }}).start();

		btnBookmarked.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnPrev.setOnClickListener(this);
		btnDay.setOnClickListener(this);
		btnNight.setOnClickListener(this);
		btnMinus.setOnClickListener(this);
		btnPlus.setOnClickListener(this);

	}

	// protected class sc extends Thread{
	// public void run(){
	// handler.post(new Runnable() {
	// @Override
	// public void run() {
	// scrollView.scrollTo(0, 500);
	// }
	// });
	// }
	// }
	//
	@Override
	public void onClick(View v) {
		if (v.getId() == btnBookmarked.getId()) {
			y = scrollView.getScrollY();
			sp = getSharedPreferences("a", MODE_PRIVATE);
			Editor e = sp.edit();
			e.putInt("pos", position);
			e.putInt("y", y);
			e.commit();
			chap = Global.vt.elementAt(position).title;
			Toast.makeText(this, getString(R.string.bookmarked) + " " + chap, 1)
					.show();
			Global.bookmarked = true;
		}

		if (v.getId() == btnNext.getId()) {
			btnPrev.setVisibility(View.VISIBLE);
			scrollView.scrollTo(0, 0);
			position++;
			if (position == Global.vt.size() - 1) {
				btnNext.setVisibility(View.INVISIBLE);
				position = Global.vt.size() - 1;
			}
			if (position > Global.vt.size() - 1) {
				position = Global.vt.size() - 1;
			}

			title.setText(Global.vt.elementAt(position).title);
			content.setText(Global.vt.elementAt(position).content);
		}

		if (v.getId() == btnPrev.getId()) {
			btnNext.setVisibility(View.VISIBLE);
			scrollView.scrollTo(0, 0);
			position--;
			if (position == 0) {
				btnPrev.setVisibility(View.INVISIBLE);
				position = 0;
			}
			if (position < 0) {
				position = 0;
			}

			title.setText(Global.vt.elementAt(position).title);
			content.setText(Global.vt.elementAt(position).content);

		}

		if (v.getId() == btnDay.getId()) {
			content.setTextColor(Color.parseColor("#393939"));
			llbg.setBackgroundResource(R.drawable.bg);
		}

		if (v.getId() == btnNight.getId()) {
			content.setTextColor(Color.parseColor("#ffffff"));
			llbg.setBackgroundColor(Color.parseColor("#000000"));
			
		}
		if( v.getId() == btnMinus.getId()){
			textSize --;
			if(textSize < minTextSize){
				textSize = minTextSize;
			}
			content.setTextSize(textSize);
			sp = getSharedPreferences("a", MODE_PRIVATE);
			Editor e = sp.edit();
			e.putInt("textSize", textSize);
			e.commit();
			
		}
		if( v.getId() == btnPlus.getId()){
			
			textSize ++;
			if(textSize > maxTextSize){
				textSize = maxTextSize;
			}
			content.setTextSize(textSize);
			sp = getSharedPreferences("a", MODE_PRIVATE);
			Editor e = sp.edit();
			e.putInt("textSize", textSize);
			e.commit();
			
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (isMenu) {
				llmenu.setVisibility(View.GONE);
				isMenu = false;
			} else {
				llmenu.setVisibility(View.VISIBLE);
				isMenu = true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	float downx;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == content.getId()) {
			// hide menu
			if (isMenu) {
				llmenu.setVisibility(View.GONE);
				isMenu = false;
			}

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				downx = event.getRawX();
				return true;
			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				Log.d("z", "next" + (downx - event.getRawX()));

				if (event.getRawX() - downx > 20) {
					Log.d("z", "prev");
					btnNext.setVisibility(View.VISIBLE);
					scrollView.scrollTo(0, 0);
					position--;
					if (position == 0) {
						btnPrev.setVisibility(View.INVISIBLE);
						position = 0;
					}
					if (position < 0) {
						position = 0;
					}
					try {
						title.setText(Global.vt.elementAt(position).title);
						content.setText(Global.vt.elementAt(position).content);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				if (downx - event.getRawX() > 20) {
					Log.d("z", "next" + (downx - event.getRawX()));
					btnPrev.setVisibility(View.VISIBLE);
					scrollView.scrollTo(0, 0);
					position++;
					if (position == Global.vt.size() - 1) {
						btnNext.setVisibility(View.INVISIBLE);
						position = Global.vt.size() - 1;
					}
					if (position > Global.vt.size() - 1) {
						position = Global.vt.size() - 1;
					}
					try {
						title.setText(Global.vt.elementAt(position).title);
						content.setText(Global.vt.elementAt(position).content);
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}
		}
		return false;
	}
}
