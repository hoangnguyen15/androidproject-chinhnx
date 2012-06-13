package com.krazevina.thioto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemCauhoi;
import com.krazevina.thioto.rotate.Flip3dAnimation;
import com.krazevina.thioto.sqlite.CauhoiDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Baithi extends Activity {

	// ArrayList<ItemCauhoi> list_cauhoi_baithi = new ArrayList<ItemCauhoi>();

	Button danhsach;
	Button tieptheo;
	TextView timeremain;

	ProgressDialog dialog;

	LinearLayout baithi;
	LinearLayout lin;
	LinearLayout linearstart;
	Button start;

	// / phần câu hỏi thi
	LinearLayout scroll;

	ImageView image[] = new ImageView[3];
	LinearLayout lineara[] = new LinearLayout[3]; // header cau hoi
	LinearLayout linearb[] = new LinearLayout[3];
	LinearLayout linearc[] = new LinearLayout[3];
	LinearLayout lineard[] = new LinearLayout[3];

	LinearLayout anima[] = new LinearLayout[3];
	LinearLayout animb[] = new LinearLayout[3];
	LinearLayout animc[] = new LinearLayout[3];
	LinearLayout animd[] = new LinearLayout[3];

	LinearLayout lina[] = new LinearLayout[3];
	LinearLayout linb[] = new LinearLayout[3];
	LinearLayout linc[] = new LinearLayout[3];
	LinearLayout lind[] = new LinearLayout[3];

	LinearLayout linearaa[] = new LinearLayout[3];
	LinearLayout linearbb[] = new LinearLayout[3];
	LinearLayout linearcc[] = new LinearLayout[3];
	LinearLayout lineardd[] = new LinearLayout[3];

	TextView tcauhoi[] = new TextView[3];
	TextView noidung[] = new TextView[3];

	TextView tla[] = new TextView[3];// txt cau tra loi
	TextView tlb[] = new TextView[3];
	TextView tlc[] = new TextView[3];
	TextView tld[] = new TextView[3];

	TextView texta[] = new TextView[3];// txt A,B,C,D
	TextView textb[] = new TextView[3];
	TextView textc[] = new TextView[3];
	TextView textd[] = new TextView[3];
	private ScrollView cuon[] = new ScrollView[3];

	LayoutInflater li;
	HorizontalPaper cuon_ngang;

	// // end câu hỏi thi
	HashMap<String, Integer> mapimage;
	CauhoiDB cauhoiDB;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baithi);
		initvalue();
	}
	boolean isshowedtoast=false;
	private HorizontalPaper.OnScreenSwitchListener switchListener = new HorizontalPaper.OnScreenSwitchListener() {

		@Override
		public void onScreenSwitched(int screen) {
			Log.d("traloihientai", "" + traloihientai);
			

			
			
			
			
			
			if (screen > 1) {
				// traloihientai[1] = -1;

				if (cauhoihientai + 1 <= 28) {
					cuon[0].scrollTo(0, 0);
					cuon[1].scrollTo(0, 0);
					cuon[2].scrollTo(0, 0);

					cauhoihientai++;
					addCauhoi(0, cauhoihientai - 1);
					addCauhoi(1, cauhoihientai);
					new changelayout_next().execute((Void) null);
				} else if (cauhoihientai == 29) {
//					Toast.makeText(Baithi.this, " Đây là câu hỏi cuối cùng", 0)
//							.show();
					tieptheo.setText("Kết thúc");
					is_button_end = true;
				} else {
					cauhoihientai++;
				}

				if (cauhoihientai == 29) {
					tieptheo.setText("Kết thúc");
					is_button_end = true;
				} else {
					tieptheo.setText("Tiếp theo");
					is_button_end = false;
				}

			} else if (screen < 1) {
				// / traloihientai = -1;

				if (cauhoihientai - 1 >= 1) {
					cuon[0].scrollTo(0, 0);
					cuon[1].scrollTo(0, 0);
					cuon[2].scrollTo(0, 0);

					cauhoihientai--;
					addCauhoi(1, cauhoihientai);
					addCauhoi(2, cauhoihientai + 1);
					new changelayout_prev().execute((Void) null);
				} 
				
				else if (cauhoihientai == 0) {
//					Toast.makeText(Baithi.this, " Đây là câu hỏi đầu tiên ", 0)
//							.show();
				}
				else {
					cauhoihientai--;
				}

			} else {// screen =1

				Log.d("screen==1", "" + screen);
				if (cauhoihientai == 29) {
					// traloihientai=-1;
					cauhoihientai--;
					tieptheo.setText("Tiếp theo");
					is_button_end = false;
				} else if (cauhoihientai == 0) {
					// traloihientai=-1;
					cauhoihientai++;
				}
			}

//			Log.d("on screen switch cauhoihientai", "" + (cauhoihientai + 1));
//			
			
			
			if(cauhoihientai==0){
				if(!isshowedtoast){
					isshowedtoast=true;
				Toast.makeText(Baithi.this, " Đây là các biển đầu tiên",0).show();
				}
			}
			 if (cauhoihientai == 29) {
					if(!isshowedtoast){
					isshowedtoast=true;
					Toast.makeText(Baithi.this, " Đây là các biển cuối cùng",
							0).show();
					}
			 }
			
			if(cauhoihientai!=0&&cauhoihientai!=29){
				isshowedtoast=false;
			}
			
			
		}
	};

	protected class changelayout_prev extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {
		//	setChange();
			cuon_ngang.setCurrentScreen(1, false);
			addCauhoi(0, cauhoihientai - 1);

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	protected class changelayout_next extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {
		//	setChange();
			cuon_ngang.setCurrentScreen(1, false);
			// Log.d("addcauhoi2", "cauhoihientai" + (cauhoihientai + 1));
			addCauhoi(2, cauhoihientai + 1);

			Log.d("toancuc list cauhoi thi .size",
					"" + Toancuc.list_cauhoithi.size());
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	int cauhoihientai = -1;

	private void addView() {

		for (int i = 1; i < 4; i++) {

			final int jj = i;
			View view = (LinearLayout) li.inflate(R.layout.itemviewcauhoithi,
					null);
			cuon_ngang.addView(view);
			image[i - 1] = (ImageView) view.findViewById(R.id.image);
			image[i - 1].setVisibility(ImageView.GONE);
			lineara[i - 1] = (LinearLayout) view.findViewById(R.id.lineara);
			linearaa[i - 1] = (LinearLayout) view.findViewById(R.id.linearaa);

			linearb[i - 1] = (LinearLayout) view.findViewById(R.id.linearb);
			linearbb[i - 1] = (LinearLayout) view.findViewById(R.id.linearbb);

			linearc[i - 1] = (LinearLayout) view.findViewById(R.id.linearc);
			linearcc[i - 1] = (LinearLayout) view.findViewById(R.id.linearcc);

			lineard[i - 1] = (LinearLayout) view.findViewById(R.id.lineard);
			lineardd[i - 1] = (LinearLayout) view.findViewById(R.id.lineardd);

			anima[i - 1] = (LinearLayout) view.findViewById(R.id.anima);
			animb[i - 1] = (LinearLayout) view.findViewById(R.id.animb);
			animc[i - 1] = (LinearLayout) view.findViewById(R.id.animc);
			animd[i - 1] = (LinearLayout) view.findViewById(R.id.animd);

			lina[i - 1] = (LinearLayout) view.findViewById(R.id.lina);
			linb[i - 1] = (LinearLayout) view.findViewById(R.id.linb);
			linc[i - 1] = (LinearLayout) view.findViewById(R.id.linc);
			lind[i - 1] = (LinearLayout) view.findViewById(R.id.lind);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					width, android.widget.LinearLayout.LayoutParams.FILL_PARENT);
			param.setMargins(-width, 0, 0, 0);
			lina[i - 1].setLayoutParams(param);
			linb[i - 1].setLayoutParams(param);
			linc[i - 1].setLayoutParams(param);
			lind[i - 1].setLayoutParams(param);

			tcauhoi[i - 1] = (TextView) view.findViewById(R.id.tcauhoi);
			noidung[i - 1] = (TextView) view.findViewById(R.id.noidung);

			tla[i - 1] = (TextView) view.findViewById(R.id.tla);
			tlb[i - 1] = (TextView) view.findViewById(R.id.tlb);
			tlc[i - 1] = (TextView) view.findViewById(R.id.tlc);
			tld[i - 1] = (TextView) view.findViewById(R.id.tld);

			cuon[i - 1] = (ScrollView) view.findViewById(R.id.cuon);

			texta[i - 1] = (TextView) view.findViewById(R.id.texta);
			// A,B,C,D
			textb[i - 1] = (TextView) view.findViewById(R.id.textb);
			textc[i - 1] = (TextView) view.findViewById(R.id.textc);
			textd[i - 1] = (TextView) view.findViewById(R.id.textd);

			lina[jj - 1].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					Log.d("clicked view:"+(jj-1), "click"+traloihientai[jj-1] );
					if (traloihientai[jj-1] == 1)
						return;

					animc[jj - 1].setBackgroundDrawable(null);
					animb[jj - 1].setBackgroundDrawable(null);
					anima[jj - 1].setBackgroundResource(R.drawable.cam);
					animd[jj - 1].setBackgroundDrawable(null);
					anima[jj - 1].setAnimation(inFromLeftAnimation());
					Log.d("isfirstimagea", "" + isFirstImagea);

					if (isFirstImagea[jj - 1]) {
						applyRotation(jj - 1, 0, 90, 0, 100);
						isFirstImagea[jj - 1] = false;

						if (!isFirstImageb[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 1, 0);
							isFirstImageb[jj - 1] = true;
						}
						if (!isFirstImagec[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 2, 0);
							isFirstImagec[jj - 1] = true;
						}
						if (!isFirstImaged[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 3, 0);
							isFirstImaged[jj - 1] = true;
						}
					}

					traloihientai[jj-1] = 1;
					Log.d("cau:" + (cauhoihientai + 1), "dachon:1");

					Toancuc.list_cauhoithi.get(cauhoihientai).dachon = 1;
					Toancuc.list_cauhoithi.get(cauhoihientai).traloi = "A";
					Toancuc.list_cauhoithi.get(cauhoihientai).datraloidapan = "1";

					// for (int i = 0; i < Toancuc.list_cauhoithi.size(); i++) {
					// Log.d("idcauhoi"
					// + Toancuc.list_cauhoithi.get(i).idcauhoi, ""
					// + Toancuc.list_cauhoithi.get(i).dachon);
					// }
					Log.d("toancuc list cauhoi thi .size", ""
							+ Toancuc.list_cauhoithi.size());
				}
			});
			linb[jj - 1].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (traloihientai[jj-1] == 2)
						return;
					anima[jj - 1].setBackgroundDrawable(null);
					animc[jj - 1].setBackgroundDrawable(null);
					animb[jj - 1].setBackgroundResource(R.drawable.cam);
					animd[jj - 1].setBackgroundDrawable(null);
					animb[jj - 1].setAnimation(inFromLeftAnimation());
					Log.d("isfirstimageb", "" + isFirstImageb);
					if (isFirstImageb[jj - 1]) {
						applyRotation(jj - 1, 0, 90, 1, 100);
						isFirstImageb[jj - 1] = false;

						if (!isFirstImagea[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 0, 0);
							isFirstImagea[jj - 1] = true;
						}
						if (!isFirstImagec[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 2, 0);
							isFirstImagec[jj - 1] = true;
						}
						if (!isFirstImaged[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 3, 0);
							isFirstImaged[jj - 1] = true;
						}
					}
					traloihientai[jj-1] = 2;
					Log.d("cau:" + (cauhoihientai + 1), "dachon:2");
					Toancuc.list_cauhoithi.get(cauhoihientai).dachon = 2;
					Toancuc.list_cauhoithi.get(cauhoihientai).traloi = "B";
					Toancuc.list_cauhoithi.get(cauhoihientai).datraloidapan = "2";
					//
					// for (int i = 0; i < Toancuc.list_cauhoithi.size(); i++) {
					// Log.d("idcauhoi"
					// + Toancuc.list_cauhoithi.get(i).idcauhoi, ""
					// + Toancuc.list_cauhoithi.get(i).dachon);
					// }
					Log.d("toancuc list cauhoi thi .size", ""
							+ Toancuc.list_cauhoithi.size());
				}
			});
			linc[jj - 1].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (traloihientai[jj-1] == 3)
						return;
					anima[jj - 1].setBackgroundDrawable(null);
					animb[jj - 1].setBackgroundDrawable(null);
					animc[jj - 1].setBackgroundResource(R.drawable.cam);
					animd[jj - 1].setBackgroundDrawable(null);
					animc[jj - 1].setAnimation(inFromLeftAnimation());
					Log.d("isfirstimagec", "" + isFirstImagec);
					if (isFirstImagec[jj - 1]) {
						applyRotation(jj - 1, 0, 90, 2, 100);
						isFirstImagec[jj - 1] = false;

						if (!isFirstImageb[jj - 1]) {

							applyRotation(jj - 1, 0, 90, 1, 0);
							isFirstImageb[jj - 1] = true;
						}
						if (!isFirstImagea[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 0, 0);
							isFirstImagea[jj - 1] = true;
						}
						if (!isFirstImaged[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 3, 0);
							isFirstImaged[jj - 1] = true;
						}
					}
					traloihientai[jj-1] = 3;
					Log.d("cau:" + (cauhoihientai + 1), "dachon:3");
					Toancuc.list_cauhoithi.get(cauhoihientai).dachon = 3;
					Toancuc.list_cauhoithi.get(cauhoihientai).traloi = "C";
					Toancuc.list_cauhoithi.get(cauhoihientai).datraloidapan = "3";
					//
					// for (int i = 0; i < Toancuc.list_cauhoithi.size(); i++) {
					// Log.d("idcauhoi"
					// + Toancuc.list_cauhoithi.get(i).idcauhoi, ""
					// + Toancuc.list_cauhoithi.get(i).dachon);
					// }
					Log.d("toancuc list cauhoi thi .size", ""
							+ Toancuc.list_cauhoithi.size());
				}
			});
			lind[jj - 1].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (traloihientai[jj-1] == 4)
						return;
					animc[jj - 1].setBackgroundDrawable(null);
					animb[jj - 1].setBackgroundDrawable(null);
					animd[jj - 1].setBackgroundResource(R.drawable.cam);
					anima[jj - 1].setBackgroundDrawable(null);
					animd[jj - 1].setAnimation(inFromLeftAnimation());
					Log.d("isfirstimaged", "" + isFirstImaged);
					if (isFirstImaged[jj - 1]) {
						applyRotation(jj - 1, 0, 90, 3, 100);
						isFirstImaged[jj - 1] = false;

						if (!isFirstImageb[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 1, 0);
							isFirstImageb[jj - 1] = true;
						}
						if (!isFirstImagec[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 2, 0);
							isFirstImagec[jj - 1] = true;
						}
						if (!isFirstImagea[jj - 1]) {
							applyRotation(jj - 1, 0, 90, 0, 0);
							isFirstImagea[jj - 1] = true;
						}
					}
					traloihientai[jj-1] = 4;
					Log.d("cau:" + (cauhoihientai + 1), "dachon:4");
					Toancuc.list_cauhoithi.get(cauhoihientai).dachon = 4;
					Toancuc.list_cauhoithi.get(cauhoihientai).traloi = "D";
					Toancuc.list_cauhoithi.get(cauhoihientai).datraloidapan = "4";

					// for (int i = 0; i < Toancuc.list_cauhoithi.size(); i++) {
					// Log.d("idcauhoi"
					// + Toancuc.list_cauhoithi.get(i).idcauhoi, ""
					// + Toancuc.list_cauhoithi.get(i).dachon);
					// }
					Log.d("toancuc list cauhoi thi .size", ""
							+ Toancuc.list_cauhoithi.size());
				}
			});
		}
		scroll.addView(cuon_ngang);
	}

	int xx = 0;

	private void addCauhoi(int so_thutu_view, int so_thutu_cauhoi) {

		// reset background cau hoi
		switch (so_thutu_view) {
		case 0:
			setChange_0();
			break;
		case 1:
			setChange_1();
			break;
		case 2:
			setChange_2();
			break;
		default:
			break;
		}

		int i = so_thutu_view + 1;

		anima[i - 1].setBackgroundDrawable(null);
		animb[i - 1].setBackgroundDrawable(null);
		animc[i - 1].setBackgroundDrawable(null);
		animd[i - 1].setBackgroundDrawable(null);

		// reset layout c & d

		lina[i - 1].setBackgroundDrawable(null);
		linb[i - 1].setBackgroundDrawable(null);
		linc[i - 1].setBackgroundDrawable(null);
		lind[i - 1].setBackgroundDrawable(null);

		lineara[i - 1].setVisibility(LinearLayout.VISIBLE);
		linearb[i - 1].setVisibility(LinearLayout.VISIBLE);
		linearc[i - 1].setVisibility(LinearLayout.VISIBLE);
		lineard[i - 1].setVisibility(LinearLayout.VISIBLE);
		
		linearaa[i - 1].setVisibility(LinearLayout.GONE);
		linearbb[i - 1].setVisibility(LinearLayout.GONE);
		linearcc[i - 1].setVisibility(LinearLayout.GONE);
		lineardd[i - 1].setVisibility(LinearLayout.GONE);
		
		linc[i - 1].setVisibility(LinearLayout.VISIBLE);
		lind[i - 1].setVisibility(LinearLayout.VISIBLE);

		// set header cau hoi
		tcauhoi[i - 1].setText("Câu hỏi " + (so_thutu_cauhoi + 1));

		ItemCauhoi item = Toancuc.list_cauhoithi.get(so_thutu_cauhoi);
		traloihientai[i - 1] = Integer.parseInt(item.datraloidapan);

		noidung[i - 1].setText(item.cauhoi);

		if (item.image != null && item.image.compareTo("") != 0) {
			image[i - 1].setVisibility(ImageView.VISIBLE);
			image[i - 1].setImageResource(mapimage.get(item.image));

		} else
			image[i - 1].setVisibility(ImageView.GONE);
		Log.d("item.idcauhoi", "" + item.idcauhoi);
		tla[i - 1].setText(item.traloia);
		tlb[i - 1].setText(item.traloib);
		if (item.traloic.compareTo("") != 0) {
			tlc[i - 1].setText(item.traloic);

		} else {
			linc[i - 1].setVisibility(LinearLayout.GONE);
		}

		if (item.traloid.compareTo("") != 0) {
			tld[i - 1].setText(item.traloid);

		} else {
			lind[i - 1].setVisibility(LinearLayout.GONE);
		}

		if (item.datraloidapan.compareTo("1") == 0) {
//			if (isFirstImagea[i - 1]) {
//				applyRotation(i - 1, 0, 90, 0, 0);
//				isFirstImagea[i - 1] = false;
//			}
			lineara[i - 1].setVisibility(LinearLayout.GONE);
			linearaa[i - 1].setVisibility(LinearLayout.VISIBLE);
			isFirstImagea[i - 1] = false;
			anima[i - 1].setBackgroundResource(R.drawable.cam);
		} else if (item.datraloidapan.compareTo("2") == 0) {
//			if (isFirstImageb[i - 1]) {
//				applyRotation(i - 1, 0, 90, 1, 0);
//				isFirstImageb[i - 1] = false;
//			}
			linearb[i - 1].setVisibility(LinearLayout.GONE);
			linearbb[i - 1].setVisibility(LinearLayout.VISIBLE);
			isFirstImageb[i - 1] = false;
			animb[i - 1].setBackgroundResource(R.drawable.cam);
		} else if (item.datraloidapan.compareTo("3") == 0) {
//			if (isFirstImagec[i - 1]) {
//				applyRotation(i - 1, 0, 90, 2, 0);
//				isFirstImagec[i - 1] = false;
//			}
			linearc[i - 1].setVisibility(LinearLayout.GONE);
			linearcc[i - 1].setVisibility(LinearLayout.VISIBLE);
			isFirstImagec[i - 1] = false;
			animc[i - 1].setBackgroundResource(R.drawable.cam);
		} else if (item.datraloidapan.compareTo("4") == 0) {
//			if (isFirstImaged[i - 1]) {
//				applyRotation(i - 1, 0, 90, 3, 0);
//				isFirstImaged[i - 1] = false;
//			}
			lineard[i - 1].setVisibility(LinearLayout.GONE);
			lineardd[i - 1].setVisibility(LinearLayout.VISIBLE);
			isFirstImaged[i - 1] = false;
			animd[i - 1].setBackgroundResource(R.drawable.cam);
		}

	}

	int traloihientai[] = { -1, -1, -1 };

	private void setClick() {
		danhsach.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (Toancuc.list_cauhoithi.isEmpty()) {
					Toast.makeText(Baithi.this,
							" Bạn phải bắt đầu làm bài thi ", 1).show();
					return false;
				}
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					danhsach.setTextColor(Color.WHITE);
				} else if (event.getAction() == MotionEvent.ACTION_UP
						|| event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					danhsach.setTextColor(Color.BLACK);
				}
				return false;
			}
		});
		danhsach.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("danhsach", "data danh sach");
				for (int i = 0; i < Toancuc.list_cauhoithi.size(); i++) {
					Log.d("idcauhoi" + Toancuc.list_cauhoithi.get(i).idcauhoi,
							"" + Toancuc.list_cauhoithi.get(i).dachon);
				}

				if (Toancuc.list_cauhoithi.isEmpty())
					return;
				Tab.tabHost.setCurrentTab(6);
			}
		});
		tieptheo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("next", "next");
				if (is_button_end) {
					is_button_end = false;
					danglambai = false;
					Toancuc.dangthi = false;
					Toancuc.thoigianlambai = 20 * 60 - numsecondremain;
					themKetqua();
					showDialogConfirmXemketqua();

					danhsach.setVisibility(Button.GONE);
					tieptheo.setVisibility(Button.GONE);
					linearstart.setVisibility(LinearLayout.VISIBLE);
					scroll.setVisibility(LinearLayout.GONE);

					timeremain.setText("20':00'");
					timeremain.setVisibility(TextView.GONE);
					Toancuc.baithi_current_cauhoi = -1;

					Toancuc.dongykethuc = false;
					return;
				}

				// nết câu 28
				if (cauhoihientai == 28) {
					tieptheo.setText("Kết thúc");
					is_button_end = true;
				}

				if (cauhoihientai == 0) {
					cuon_ngang.setCurrentScreen(1, true);
					cauhoihientai++;
				} else
					cuon_ngang.setCurrentScreen(2, true);

				if (cauhoihientai == 29) {
					Toast.makeText(Baithi.this, " Đây là câu hỏi cuối cùng", 0)
							.show();
					return;
				}
			}
		});

		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog = new ProgressDialog(Baithi.this);
				dialog.setMessage("Đang tải đề thi...");
				dialog.show();

				new InitQuestion0().execute((Void) null);
			}
		});
	}

	public static int numsecondremain = 20 * 60 + 1;
	boolean is_button_end = false;
	public static boolean danglambai = true;

	boolean initview = false;

	@Override
	protected void onResume() {

		if (!initview) {
			dialog = new ProgressDialog(Baithi.this);
			dialog.setMessage("Đang tải dữ liệu...");
			dialog.show();

			initview = true;
			new initview0().execute((Void) null);
		} else {
			if (Toancuc.dongykethuc) {
				// đang làm bài và đổi tab và chọn dồng ý kết thúc quay lại sẽ
				// invisible buttons
				danhsach.setVisibility(Button.GONE);
				tieptheo.setVisibility(Button.GONE);
				linearstart.setVisibility(LinearLayout.VISIBLE);
				scroll.setVisibility(LinearLayout.GONE);

				timeremain.setVisibility(TextView.GONE);
				timeremain.setText("20':00'");
				Toancuc.baithi_current_cauhoi = -1;

				Toancuc.dongykethuc = false;

			}

			if (Toancuc.baithi_current_cauhoi != -1) {

				Log.d("current cau hỏi ", "" + Toancuc.baithi_current_cauhoi);
				if (Toancuc.baithi_current_cauhoi == 1) {

					addCauhoi(0, Toancuc.baithi_current_cauhoi - 1);
					addCauhoi(1, Toancuc.baithi_current_cauhoi);
					addCauhoi(2, Toancuc.baithi_current_cauhoi + 1);
					cuon_ngang.setCurrentScreen(0, false);
				} else if (Toancuc.baithi_current_cauhoi == 30) {

					addCauhoi(0, Toancuc.baithi_current_cauhoi - 3);
					addCauhoi(1, Toancuc.baithi_current_cauhoi - 2);
					addCauhoi(2, Toancuc.baithi_current_cauhoi - 1);
					cuon_ngang.setCurrentScreen(2, false);
				} else {
					addCauhoi(0, Toancuc.baithi_current_cauhoi - 2);
					addCauhoi(1, Toancuc.baithi_current_cauhoi - 1);
					addCauhoi(2, Toancuc.baithi_current_cauhoi);
					cuon_ngang.setCurrentScreen(1, false);
				}
				cauhoihientai = Toancuc.baithi_current_cauhoi - 1;
				if (Toancuc.baithi_current_cauhoi == 30) {
					tieptheo.setText("Kết thúc");
					is_button_end = true;
				}

				else
					tieptheo.setText("Tiếp theo");
			}
		}
		super.onResume();
	}

	@Override
	protected void onPause() {

		super.onPause();
	}

	private void InitQuestion() {
		Toancuc.list_cauhoithi.clear();
		sttcauhoithi = 0;

		cauhoinhom1();
		cauhoinhom2();
		cauhoinhom3();
		cauhoinhom4();
		cauhoinhom5();
		cauhoinhom6();
		cauhoinhom7();
		cauhoinhom8();

		cauhoihientai = 0;
		addCauhoi(0, 0);
		addCauhoi(1, 1);
		addCauhoi(2, 2);
		cuon_ngang.setCurrentScreen(0, false);

		scroll.setVisibility(LinearLayout.VISIBLE);
		scroll.startAnimation(inFromRightAnimation());

		// for(int i=0;i<Toancuc.list_cauhoithi.size();i++){
		// Log.d("cau: "+(i+1),"dachon: "+Toancuc.list_cauhoithi.get(i).dachon);
		// }
		//

		// reset rotation
		// view 0
		if (!isFirstImagea[0]) {
			applyRotation(0, 0, 90, 0, 0);
			isFirstImagea[0] = true;
		}
		if (!isFirstImageb[0]) {
			applyRotation(0, 0, 90, 1, 0);
			isFirstImageb[0] = true;
		}
		if (!isFirstImagec[0]) {
			applyRotation(0, 0, 90, 2, 0);
			isFirstImagec[0] = true;
		}
		if (!isFirstImaged[0]) {
			applyRotation(0, 0, 90, 3, 0);
			isFirstImaged[0] = true;
		}
		// view 1
		if (!isFirstImagea[1]) {
			applyRotation(1, 0, 90, 0, 0);
			isFirstImagea[1] = true;
		}
		if (!isFirstImageb[1]) {
			applyRotation(1, 0, 90, 1, 0);
			isFirstImageb[1] = true;
		}
		if (!isFirstImagec[1]) {
			applyRotation(1, 0, 90, 2, 0);
			isFirstImagec[1] = true;
		}
		if (!isFirstImaged[1]) {
			applyRotation(1, 0, 90, 3, 0);
			isFirstImaged[1] = true;
		}
		// view 2
		if (!isFirstImagea[2]) {
			applyRotation(2, 0, 90, 0, 0);
			isFirstImagea[2] = true;
		}
		if (!isFirstImageb[2]) {
			applyRotation(2, 0, 90, 1, 0);
			isFirstImageb[2] = true;
		}
		if (!isFirstImagec[2]) {
			applyRotation(2, 0, 90, 2, 0);
			isFirstImagec[2] = true;
		}
		if (!isFirstImaged[2]) {
			applyRotation(2, 0, 90, 3, 0);
			isFirstImaged[2] = true;
		}
		// view 2
		Log.d("list_cauhoithi.size", "" + Toancuc.list_cauhoithi.size());
	}

	protected class InitQuestion0 extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			new InitQuestion().execute((Void) null);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class InitQuestion extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			// /view
			Toancuc.dangthi = true;
			Toancuc.chuathemdulieu = true;
			Toancuc.list_cauhoithi.clear();
			DateUtils du = new DateUtils();
			Calendar cal = Calendar.getInstance();
			int h = cal.get(Calendar.HOUR);
			int m = cal.get(Calendar.MINUTE);
			int s = cal.get(Calendar.SECOND);

			String gio = (h >= 10 ? h : "0" + h) + "";
			String phut = (m >= 10 ? m : "0" + m) + "";
			String giay = (s >= 10 ? s : "0" + s) + "";

			
			Toancuc.ngaybatdau = du.now("dd/MM/yyyy");
			Toancuc.giobatdau = gio + ":" + phut + ":" + giay;
			Log.d("ngaybatdau", "g" + Toancuc.ngaybatdau);
			Log.d("time start", "" + Toancuc.giobatdau);

			linearstart.setVisibility(LinearLayout.GONE);
			scroll.setVisibility(LinearLayout.GONE);

			// ///
			getData();
			InitQuestion();
			dialog.dismiss();
			tieptheo.setText("Tiếp theo");
			tieptheo.setVisibility(Button.VISIBLE);
			danhsach.setVisibility(Button.VISIBLE);
			timeremain.setVisibility(TextView.VISIBLE);
			
			cuon_ngang.setCurrentScreen(0, false);
			new TimeRemain().execute((Void) null);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	boolean shows = false;

	protected class TimeRemain extends AsyncTask<Void, Void, Void> {
		DateUtils d = new DateUtils();

		@Override
		protected void onPostExecute(Void v) {
			numsecondremain = 20 * 60 + 3;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Baithi.this.timeremain.setText(d.timeremain(numsecondremain));
			if (numsecondremain == 0) {
				danhsach.setVisibility(Button.GONE);
				tieptheo.setVisibility(Button.GONE);
				linearstart.setVisibility(LinearLayout.VISIBLE);
				scroll.setVisibility(LinearLayout.GONE);

				timeremain.setVisibility(TextView.GONE);
				timeremain.setText("20':00'");
				Toancuc.thoigianlambai = 20 * 60 - Baithi.numsecondremain;// tính
				// thời
				// gian
				Toancuc.baithi_current_cauhoi = -1;
				Toancuc.dongykethuc = false;
				Toancuc.dangthi = false;
				themKetqua();// thêm kết quả thi vào cơ sở dữ liệu
				if (!shows) {
					showDialogEndTime();
					shows = true;
				}
			}
			super.onProgressUpdate(values);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			danglambai = true;
			shows = false;
			while (numsecondremain > 0 && danglambai) {
				numsecondremain--;

				try {
					Thread.sleep(1000);

					publishProgress((Void) null);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	ArrayList<ItemCauhoi> list1;
	ArrayList<ItemCauhoi> list2;
	ArrayList<ItemCauhoi> list3;
	ArrayList<ItemCauhoi> list4;
	ArrayList<ItemCauhoi> list5;
	ArrayList<ItemCauhoi> list6;
	ArrayList<ItemCauhoi> list7;
	ArrayList<ItemCauhoi> list8;

	// lấy dữ liệu từ dabase len
	private void getData() {
		// if (!list_cauhoi.isEmpty())
		// return;

		// list_cauhoi.clear();

		// list_cauhoi = copy(Toancuc.list_cauhoi);

		list1 = copy(Toancuc.list1);
		list2 = copy(Toancuc.list2);
		list3 = copy(Toancuc.list3);
		list4 = copy(Toancuc.list4);
		list5 = copy(Toancuc.list5);
		list6 = copy(Toancuc.list6);
		list7 = copy(Toancuc.list7);
		list8 = copy(Toancuc.list8);

		// Log.d("Tat ca co ", list_cauhoi.size() + " mục ");

	}

	private ArrayList<ItemCauhoi> copy(ArrayList<ItemCauhoi> luatList) {
		ArrayList<ItemCauhoi> l = new ArrayList<ItemCauhoi>();
		ItemCauhoi item;
		for (int i = 0; i < luatList.size(); i++) {

			ItemCauhoi it = luatList.get(i);
			item = new ItemCauhoi();

			item.idcauhoi = it.idcauhoi;
			item.cauhoi = it.cauhoi;
			item.traloia = it.traloia;
			item.traloib = it.traloib;
			item.traloic = it.traloic;
			item.traloid = it.traloid;
			item.ketqua = it.ketqua;
			item.image = it.image;
			item.phanloai = it.phanloai;
			item.thi = it.thi;
			item.datraloidapan = "-1";

			l.add(item);
		}
		return l;
	}

	int sttcauhoithi = 0;

	private void cauhoinhom1() {
		Random random = new Random();
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		int socau = 0;
		while (true) {
			try{
				int x = random.nextInt(list1.size()) + 1;
				if (!m.containsKey(x)) {
					socau++;
					ItemCauhoi item = list1.get(x - 1);
					item.idcauhoi = ++sttcauhoithi + "";
					Toancuc.list_cauhoithi.add(item);
					m.put(x, x);
					if (socau == 7)
						break;

				}	
			}catch (Exception e) {
			}
		}
	}

	private void cauhoinhom2() {
		Random random = new Random();
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		int socau = 0;
		while (true) {
			int x = random.nextInt(list2.size()) + 1;
			if (!m.containsKey(x)) {
				socau++;
				ItemCauhoi item = list2.get(x - 1);
				item.idcauhoi = ++sttcauhoithi + "";
				Toancuc.list_cauhoithi.add(item);
				m.put(x, x);
				if (socau == 1)
					break;

			}
		}
	}

	private void cauhoinhom3() {
		Random random = new Random();
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		int socau = 0;
		while (true) {
			int x = random.nextInt(list3.size()) + 1;
			if (!m.containsKey(x)) {
				socau++;
				ItemCauhoi item = list3.get(x - 1);
				item.idcauhoi = ++sttcauhoithi + "";
				Toancuc.list_cauhoithi.add(item);
				m.put(x, x);
				if (socau == 1)
					break;

			}
		}
	}

	private void cauhoinhom4() {
		Random random = new Random();
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		int socau = 0;
		while (true) {
			int x = random.nextInt(list4.size()) + 1;
			if (!m.containsKey(x)) {
				socau++;
				ItemCauhoi item = list4.get(x - 1);
				item.idcauhoi = ++sttcauhoithi + "";
				Toancuc.list_cauhoithi.add(item);
				m.put(x, x);
				if (socau == 1)
					break;

			}
		}
	}

	private void cauhoinhom5() {
		Random random = new Random();
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		int socau = 0;
		while (true) {
			int x = random.nextInt(list5.size()) + 1;
			if (!m.containsKey(x)) {
				socau++;
				ItemCauhoi item = list5.get(x - 1);
				item.idcauhoi = ++sttcauhoithi + "";
				Toancuc.list_cauhoithi.add(item);
				m.put(x, x);
				if (socau == 1)
					break;

			}
		}
	}

	private void cauhoinhom6() {
		Random random = new Random();
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		int socau = 0;
		while (true) {
			int x = random.nextInt(list6.size()) + 1;
			if (!m.containsKey(x)) {
				socau++;
				ItemCauhoi item = list6.get(x - 1);
				item.idcauhoi = ++sttcauhoithi + "";
				Toancuc.list_cauhoithi.add(item);
				m.put(x, x);
				if (socau == 1)
					break;

			}
		}
	}

	private void cauhoinhom7() {
		Random random = new Random();
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		int socau = 0;
		while (true) {
			int x = random.nextInt(list7.size()) + 1;
			if (!m.containsKey(x)) {
				socau++;
				ItemCauhoi item = list7.get(x - 1);
				item.idcauhoi = ++sttcauhoithi + "";
				Toancuc.list_cauhoithi.add(item);
				m.put(x, x);
				if (socau == 10)
					break;

			}
		}
	}

	private void cauhoinhom8() {
		Random random = new Random();
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		int socau = 0;
		while (true) {
			int x = random.nextInt(list8.size()) + 1;
			if (!m.containsKey(x)) {
				socau++;
				ItemCauhoi item = list8.get(x - 1);
				item.idcauhoi = ++sttcauhoithi + "";
				Toancuc.list_cauhoithi.add(item);
				m.put(x, x);
				if (socau == 8)
					break;

			}
		}
	}

	int sodong = 0;

	// /animation
	// Animation
	private Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(400);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	// private Animation outToLeftAnimation() {
	// Animation outtoLeft = new TranslateAnimation(
	// Animation.RELATIVE_TO_PARENT, 0.0f,
	// Animation.RELATIVE_TO_PARENT, -1.0f,
	// Animation.RELATIVE_TO_PARENT, 0.0f,
	// Animation.RELATIVE_TO_PARENT, 0.0f);
	// outtoLeft.setDuration(300);
	// outtoLeft.setInterpolator(new AccelerateInterpolator());
	// return outtoLeft;
	// }

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(100);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	protected class confirm_end extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			if (dong_y)
				Toast.makeText(Baithi.this, " bạn chọn kết thúc ", 0).show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			while (!choice) {
				try {
					Thread.sleep(50);
					if (choice == true)
						break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {

			super.onProgressUpdate(values);
		}
	}

	boolean choice = false;
	boolean dong_y = false;

	@Override
	protected void onDestroy() {
		cauhoiDB.close();
		super.onDestroy();
	}

	protected class initview0 extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			new initview().execute((Void) null);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class initview extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			baithi = (LinearLayout) findViewById(R.id.baithi);
			danhsach = (Button) findViewById(R.id.btn_danhsach);
			tieptheo = (Button) findViewById(R.id.btn_tiep);
			timeremain = (TextView) findViewById(R.id.timeremain);
			linearstart = (LinearLayout) findViewById(R.id.linearstart);
			start = (Button) findViewById(R.id.start);

			danhsach.setVisibility(Button.GONE);
			tieptheo.setVisibility(Button.GONE);
			// / phần câu hỏi thi
			scroll = (LinearLayout) findViewById(R.id.scroll);
			li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			cuon_ngang = new HorizontalPaper(Baithi.this);
			cuon_ngang.setOnScreenSwitchListener(switchListener);
			cuon_ngang.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

			// end phần câu hỏi thi

			mapimage = Toancuc.getMapImagecauhoi();
			addView();
			setClick();

			cauhoiDB = new CauhoiDB(Baithi.this);
			cauhoiDB.openToRead();

			// /
			if (Toancuc.dongykethuc) {
				// đang làm bài và đổi tab và chọn dồng ý kết thúc quay lại sẽ
				// invisible buttons
				danhsach.setVisibility(Button.GONE);
				tieptheo.setVisibility(Button.GONE);
				linearstart.setVisibility(LinearLayout.VISIBLE);
				scroll.setVisibility(LinearLayout.GONE);

				timeremain.setText("20':00'");
				Toancuc.baithi_current_cauhoi = -1;

				Toancuc.dongykethuc = false;

			}

			if (Toancuc.baithi_current_cauhoi != -1) {

				Log.d("current cau hỏi ", "" + Toancuc.baithi_current_cauhoi);
				cuon_ngang.setCurrentScreen(Toancuc.baithi_current_cauhoi - 1,
						false);
			}
			start.setVisibility(Button.VISIBLE);
			dialog.dismiss();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	private void showDialogConfirmXemketqua() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Baithi.this);

		builder.setMessage(Html.fromHtml("Bài thi kết thúc , hãy chon :"))
				.setCancelable(false)
				.setPositiveButton("Xem kết quả",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Tab.tabHost.setCurrentTab(4);
							}
						})
				.setNegativeButton("Bỏ qua",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showDialogEndTime() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Baithi.this);

		builder.setMessage(Html.fromHtml("Đã hết thời gian làm bài!"))
				.setCancelable(false)
				.setPositiveButton("Xem kết quả",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Tab.tabHost.setCurrentTab(4);
							}
						})
				.setNegativeButton("Bỏ qua",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private int get_so_cau_dung() {
		int x = 0;
		for (ItemCauhoi item : Toancuc.list_cauhoithi) {
			if (item.dachon != -1)
				if (item.ketqua.compareTo(getAnswer(item.dachon)) == 0) {
					x++;
				}
		}
		return x;
	}

	private String getAnswer(int dachon) {
		switch (dachon) {
		case 1:
			return "A";
		case 2:
			return "B";
		case 3:
			return "C";
		case 4:
			return "D";

		}
		return null;
	}

	private void themKetqua() {
		int dung = get_so_cau_dung();
		String thoigianlambai = (new DateUtils()
				.timeremain(Toancuc.thoigianlambai));
		String tyledung = dung + "/30";
		cauhoiDB.openToWrite();
		if (dung >= 26) {

			cauhoiDB.addItem1(Toancuc.ngaybatdau, Toancuc.giobatdau,
					thoigianlambai, tyledung, "1");
		} else {

			cauhoiDB.addItem1(Toancuc.ngaybatdau, Toancuc.giobatdau,
					thoigianlambai, tyledung, "-1");
		}

	}

	// / con fỉm ẽit
	private void showDialogConfirmExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				Html.fromHtml("Nếu thoát chương trình bài thi sẽ bị hủy bỏ"))
				.setCancelable(false)
				.setPositiveButton("Thoát",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Baithi.this.finish();
							}
						})
				.setNegativeButton("Không",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
		if (!Toancuc.dangthi)
			builder.setMessage(Html.fromHtml("Bạn muốn thoát chương trình?"));
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onBackPressed() {
		showDialogConfirmExit();
		return;
	}

	// ///

	private boolean isFirstImagea[] = new boolean[3];
	private boolean isFirstImageb[] = new boolean[3];
	private boolean isFirstImagec[] = new boolean[3];
	private boolean isFirstImaged[] = new boolean[3];

	private void initvalue() {
		for (int i = 0; i < 3; i++) {
			isFirstImagea[i] = true;
			isFirstImageb[i] = true;
			isFirstImagec[i] = true;
			isFirstImaged[i] = true;
		}

		Rect rectgle = new Rect();
		Window window = getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		this.width = rectgle.right;
	}

	int width = -1;

	// // rotate
	private void applyRotation(int sttview012, float start, float end, int stt,
			int duration) {

		// Find the center of image
		final float centerX = lineara[1].getWidth() / 2.0f;
		final float centerY = lineara[1].getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Flip3dAnimation rotation = new Flip3dAnimation(start, end,
				centerX, centerY);
		rotation.setDuration(duration);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());

		set(sttview012, rotation, stt, duration);
	}

	private void set(int sttview012, Flip3dAnimation rotation, int stt,
			int duration) {
		LinearLayout l1 = null;
		LinearLayout l2 = null;

		if (stt == 0) {
			l1 = lineara[sttview012];
			l2 = linearaa[sttview012];
			rotation.setAnimationListener(new DisplayNextView(
					isFirstImagea[sttview012], l1, l2, duration));
			if (isFirstImagea[sttview012]) {
				l1.startAnimation(rotation);
			} else {
				l2.startAnimation(rotation);
			}
		} else if (stt == 1) {
			l1 = linearb[sttview012];
			l2 = linearbb[sttview012];
			rotation.setAnimationListener(new DisplayNextView(
					isFirstImageb[sttview012], l1, l2, duration));
			if (isFirstImageb[sttview012]) {
				l1.startAnimation(rotation);
			} else {
				l2.startAnimation(rotation);
			}
		}
		if (stt == 2) {
			l1 = linearc[sttview012];
			l2 = linearcc[sttview012];
			rotation.setAnimationListener(new DisplayNextView(
					isFirstImagec[sttview012], l1, l2, duration));
			if (isFirstImagec[sttview012]) {
				l1.startAnimation(rotation);
			} else {
				l2.startAnimation(rotation);
			}
		}
		if (stt == 3) {
			l1 = lineard[sttview012];
			l2 = lineardd[sttview012];
			rotation.setAnimationListener(new DisplayNextView(
					isFirstImaged[sttview012], l1, l2, duration));
			if (isFirstImaged[sttview012]) {
				l1.startAnimation(rotation);
			} else {
				l2.startAnimation(rotation);
			}
		}

	}

	// //

	public final class SwapViews implements Runnable {
		private boolean mIsFirstView;
		LinearLayout image1;
		LinearLayout image2;
		int duration = 0;

		public SwapViews(boolean isFirstView, LinearLayout image1,
				LinearLayout image2, int duration) {
			mIsFirstView = isFirstView;
			this.image1 = image1;
			this.image2 = image2;
			image1.setBackgroundResource(R.drawable.question_title);
			image2.setBackgroundResource(R.drawable.window);
			this.duration = duration;
		}

		public void run() {

			final float centerX = image1.getWidth() / 2.0f;
			final float centerY = image1.getHeight() / 2.0f;
			Flip3dAnimation rotation;

			if (mIsFirstView) {
				image1.setVisibility(LinearLayout.GONE);
				image2.setVisibility(LinearLayout.VISIBLE);
				image2.requestFocus();

				if(duration!=0)
				rotation = new Flip3dAnimation(-90, 0, centerX, centerY);
				else
					rotation = new Flip3dAnimation(0, 0, centerX, centerY);
			} else {
				image2.setVisibility(LinearLayout.GONE);
				image1.setVisibility(LinearLayout.VISIBLE);
				image1.requestFocus();

				if(duration!=0)
				rotation = new Flip3dAnimation(90, 0, centerX, centerY);
				else
					rotation = new Flip3dAnimation(0, 0, centerX, centerY);
			}

			
			
			rotation.setDuration(this.duration);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());

			if (mIsFirstView) {
				image2.startAnimation(rotation);
			} else {
				image1.startAnimation(rotation);
			}
			
			
		}
	}

	// //
	public final class DisplayNextView implements Animation.AnimationListener {
		private boolean mCurrentView;
		LinearLayout image1;
		LinearLayout image2;
		int duration = 0;

		public DisplayNextView(boolean currentView, LinearLayout image1,
				LinearLayout image2, int duration) {
			mCurrentView = currentView;
			this.image1 = image1;
			this.image2 = image2;
			this.duration = duration;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			image1.post(new SwapViews(mCurrentView, image1, image2,
					this.duration));
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	private void setChange_1() {/// quay het len

		if (!isFirstImagea[1])
			applyRotation(1, 0, 90, 0, 0);
		if (!isFirstImageb[1])
			applyRotation(1, 0, 90, 1, 0);
		if (!isFirstImagec[1])
			applyRotation(1, 0, 90, 2, 0);
		if (!isFirstImaged[1])
			applyRotation(1, 0, 90, 3, 0);

		isFirstImagec[1] = true;
		isFirstImagea[1] = true;
		isFirstImageb[1] = true;
		isFirstImaged[1] = true;
		
		lineara[1].setAnimation(null);
		linearb[1].setAnimation(null);
		linearc[1].setAnimation(null);
		lineard[1].setAnimation(null);
		linearaa[1].setAnimation(null);
		linearbb[1].setAnimation(null);
		linearcc[1].setAnimation(null);
		lineardd[1].setAnimation(null);
	}

	private void setChange_0() {// quay het len
		// //
		if (!isFirstImagea[0])
			applyRotation(0, 0, 90, 0, 0);
		if (!isFirstImageb[0])
			applyRotation(0, 0, 90, 1, 0);
		if (!isFirstImagec[0])
			applyRotation(0, 0, 90, 2, 0);
		if (!isFirstImaged[0])
			applyRotation(0, 0, 90, 3, 0);

		isFirstImagec[0] = true;
		isFirstImagea[0] = true;
		isFirstImageb[0] = true;
		isFirstImaged[0] = true;
		lineara[0].setAnimation(null);
		linearb[0].setAnimation(null);
		linearc[0].setAnimation(null);
		lineard[0].setAnimation(null);
		linearaa[0].setAnimation(null);
		linearbb[0].setAnimation(null);
		linearcc[0].setAnimation(null);
		lineardd[0].setAnimation(null);
	}

	private void setChange_2() {// quay het len
		// /2
		// //
		if (!isFirstImagea[2])
			applyRotation(2, 0, 90, 0, 0);
		if (!isFirstImageb[2])
			applyRotation(2, 0, 90, 1, 0);
		if (!isFirstImagec[2])
			applyRotation(2, 0, 90, 2, 0);
		if (!isFirstImaged[2])
			applyRotation(2, 0, 90, 3, 0);

		isFirstImagec[2] = true;
		isFirstImagea[2] = true;
		isFirstImageb[2] = true;
		isFirstImaged[2] = true;
		
		lineara[2].setAnimation(null);
		linearb[2].setAnimation(null);
		linearc[2].setAnimation(null);
		lineard[2].setAnimation(null);
		linearaa[2].setAnimation(null);
		linearbb[2].setAnimation(null);
		linearcc[2].setAnimation(null);
		lineardd[2].setAnimation(null);
	}

}