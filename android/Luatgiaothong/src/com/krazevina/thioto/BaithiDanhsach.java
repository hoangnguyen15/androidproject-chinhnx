package com.krazevina.thioto;

import java.util.ArrayList;

import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemCauhoi;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;


import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BaithiDanhsach extends Activity {

	// show nhung cau tra loi va chua tra loi

	Button back;
	LinearLayout baithidanhsach;
	LinearLayout datraloi0;
	LinearLayout chuatraloi0;
	
	LinearLayout datraloi;
	LinearLayout chuatraloi;
	
	LinearLayout lintitle;
	ListView list;
	TextView textheader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baithidanhsach);
		list = (ListView) findViewById(R.id.list);
		list.setDivider(null);
		list.setDividerHeight(0);
		list.setFadingEdgeLength(0);
		list.setFocusable(false);
		list.setFocusableInTouchMode(false);
		baithidanhsach = (LinearLayout) findViewById(R.id.baithidanhsach);
		back = (Button) findViewById(R.id.back);
		textheader = (TextView) findViewById(R.id.textheader);
		datraloi0 = (LinearLayout) findViewById(R.id.datraloi0);
		chuatraloi0 = (LinearLayout) findViewById(R.id.chuatraloi0);
		datraloi = (LinearLayout) findViewById(R.id.datraloi);
		chuatraloi = (LinearLayout) findViewById(R.id.chuatraloi);
		lintitle = (LinearLayout) findViewById(R.id.title);
		setClick();
		setOnItemClickList();
	}

	private void setOnItemClickList() {
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				if (seeing_datraloi == true)
					Toancuc.baithi_current_cauhoi =Integer.parseInt(list_datraloi.get(position).idcauhoi);
				else
					Toancuc.baithi_current_cauhoi = Integer.parseInt(list_chuatraloi.get(position).idcauhoi);
				Tab.tabHost.setCurrentTab(2);
			}
		});
	}

	boolean isdatraloi=true;
	
	private void setClick() {
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				Tab.tabHost.setCurrentTab(2);
			}
		});
		datraloi0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!isdatraloi) return;
				
				datraloi.setBackgroundResource(R.drawable.option_selected);
				chuatraloi.setBackgroundDrawable(null);
				textheader.setText("Đã trả lời : "
						+ (list_datraloi.size() >= 10 ? list_datraloi.size()
								: "0" + list_datraloi.size()) +" câu");
				lintitle.setBackgroundResource(R.drawable.da_tra_loi_section);
			
				new datraloi().execute((Void)null);
				isdatraloi=false;
			}
		});
		chuatraloi0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isdatraloi) return;
				chuatraloi.setBackgroundResource(R.drawable.option_selected);
				datraloi.setBackgroundDrawable(null);
				lintitle.setBackgroundResource(R.drawable.chua_tra_loi_section);
				textheader.setText("Chưa trả lời : "
						+ (list_chuatraloi.size() >= 10 ? list_chuatraloi
								.size() : "0" + list_chuatraloi.size()) +"câu");
				new chuatraloi().execute((Void)null);
				isdatraloi=true;
			}
		});
	}

	ArrayList<ItemCauhoi> list_datraloi = new ArrayList<ItemCauhoi>();
	ArrayList<ItemCauhoi> list_chuatraloi = new ArrayList<ItemCauhoi>();

	boolean isresume = false;

	@Override
	protected void onResume() {
		Log.d("baithidanhsach resume", "sggrhr");
		list_chuatraloi.clear();
		list_datraloi.clear();
		isresume = true;
		baithidanhsach.setAnimation(inFromRightAnimation(300));
		Log.d("Initdata","Initdata bai thi danhsach");
		for(int i=0;i<Toancuc.list_cauhoithi.size();i++){
			Log.d("idcauhoi"+Toancuc.list_cauhoithi.get(i).idcauhoi,""+Toancuc.list_cauhoithi.get(i).dachon);
		}
		InitData();
		if (Toancuc.on_datraloi) // neu pause at da tra loi
			setView(true);
		else
			setView(false);

		super.onResume();
	}

	boolean seeing_datraloi = false;

	private void setView(boolean datraloi) {
		if (datraloi) {

			seeing_datraloi = true;
			Toancuc.on_datraloi = true; // khoi tao bien nay , neu pause o list
			// nay thi resume cung goi list nay
			list.setAdapter(new BaithidanhsachAdapter(this,
					R.layout.itemviewdanhsachcauhoi, list_datraloi));
			textheader.setText("Đã trả lời : " + list_datraloi.size()+" câu");

		} else {

			seeing_datraloi = false;
			Toancuc.on_datraloi = false;

			list.setAdapter(new BaithidanhsachAdapter(this,
					R.layout.itemviewdanhsachcauhoi, list_chuatraloi));
			textheader.setText("Chưa trả lời : " + list_chuatraloi.size()+" câu");
		}
		if (isresume) {
			list.setAnimation(inFromRightAnimation(500));
			isresume=false;
		}else
		list.setAnimation(dropdown());
	}

	private void InitData() {
	
		for (ItemCauhoi item : Toancuc.list_cauhoithi) {
			if (item.dachon != -1) {
				Log.d("caudachon",""+item.idcauhoi);
				list_datraloi.add(item);
			} else {
				list_chuatraloi.add(item);
			}
		}

	}

	// /animation
	// Animation
	private Animation dropdown() {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(300);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	private Animation inFromRightAnimation(int duration) {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(duration);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	@Override
	public void onBackPressed() {
		
			Tab.tabHost.setCurrentTab(2);
			
	
		return;
	}
	protected class datraloi extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {
			setView(true);

		}

		@Override
		protected Void doInBackground(Void... arg0) {

		
			return null;
		}
	}

	protected class chuatraloi extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {
			setView(false);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

		
			return null;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("keycode", "" + keyCode);
		if (keyCode == 21) {
			Tab.tabHost.setCurrentTab(2);
			return true;
		}else
			if (keyCode == 22) {
				Tab.tabHost.setCurrentTab(3);
				return true;
			}
		
		return super.onKeyDown(keyCode, event);
	}
}