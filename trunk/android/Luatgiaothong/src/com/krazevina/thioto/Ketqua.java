package com.krazevina.thioto;

import java.util.ArrayList;
import java.util.HashMap;

import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemCauhoi;
import com.krazevina.thioto.objects.Itemthongke;
import com.krazevina.thioto.objects.LinearCustome;
import com.krazevina.thioto.sqlite.CauhoiDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Ketqua extends Activity {

	LinearLayout ketqua;
	LinearLayout ketquathi;
	LinearLayout thongke;
	GridView grid;
	LinearLayout scroll;

	LinearLayout thongkelayout;
	LinearLayout thongkeketquathi;
	LinearLayout headerketqua;
	LinearLayout title;
	LinearLayout option_bar;

	TextView chualambai;

	TextView solando;
	TextView diemtrungbinh;
	ListView listthongke;

	TextView[] sttcauhoi = new TextView[3];
	TextView[] cauhoi = new TextView[3];
	TextView[] tloia = new TextView[3];
	TextView[] tloib = new TextView[3];
	TextView[] tloic = new TextView[3];
	TextView[] tloid = new TextView[3];

	LinearLayout[] lc = new LinearLayout[3];
	LinearLayout[] ld = new LinearLayout[3];

	LinearLayout kephancach; // header cau hoi

	TextView texta[] = new TextView[3];// txt A,B,C,D
	TextView textb[] = new TextView[3];
	TextView textc[] = new TextView[3];
	TextView textd[] = new TextView[3];

	// //

	private ImageView image[] = new ImageView[4];

	private LinearLayout lineara[] = new LinearLayout[4]; // header cau hoi
	LinearLayout linearaa[] = new LinearLayout[4]; // header cau hoi

	private LinearLayout linearb[] = new LinearLayout[4];
	private LinearLayout linearbb[] = new LinearLayout[4];

	private LinearLayout linearc[] = new LinearLayout[4];
	private LinearLayout linearcc[] = new LinearLayout[4];

	private LinearLayout lineard[] = new LinearLayout[4];
	private LinearLayout lineardd[] = new LinearLayout[4];

	private LinearCustome lina[] = new LinearCustome[4];
	private LinearCustome linb[] = new LinearCustome[4];
	private LinearCustome linc[] = new LinearCustome[4];
	private LinearCustome lind[] = new LinearCustome[4];

	private TextView tcauhoi[] = new TextView[4];
	private TextView noidung[] = new TextView[4];

	private TextView tla[] = new TextView[4];// txt cau tra loi
	private TextView tlb[] = new TextView[4];
	private TextView tlc[] = new TextView[4];
	private TextView tld[] = new TextView[4];

	ScrollView cuon[] = new ScrollView[3];
	LayoutInflater li;
	HorizontalPaper cuon_ngang;

	TextView sodung;
	TextView thoigian;
	TextView dotruot;

	Button quaylaigrid;
	Button clear;
	
	LinearLayout linkqt;
	LinearLayout lintk;
	
	private HashMap<String, Integer> mapimage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ketqua);

		headerketqua = (LinearLayout) findViewById(R.id.headerketqua);
		title = (LinearLayout) findViewById(R.id.title);
		option_bar = (LinearLayout) findViewById(R.id.option_bar);

		chualambai = (TextView) findViewById(R.id.chualambai);

		grid = (GridView) findViewById(R.id.gridview);
		mapimage = Toancuc.getMapImagecauhoi();
		clear = (Button) findViewById(R.id.clear);
		clear.setVisibility(Button.GONE);
		quaylaigrid = (Button) findViewById(R.id.quaylaigrid);
		quaylaigrid.setVisibility(Button.GONE);

		Rect rectgle = new Rect();
		Window window = getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		this.width = rectgle.right;
	}

	int width = -1;
	private HorizontalPaper.OnScreenSwitchListener switchListener = new HorizontalPaper.OnScreenSwitchListener() {

		@Override
		public void onScreenSwitched(int screen) {

			Log.e("Man hinh hien tai", ":" + screen);
			if (screen > 1) {

				if (cauhoihientai + 1 <= 28) {
					cuon[0].scrollTo(0, 0);
					cuon[1].scrollTo(0, 0);
					cuon[2].scrollTo(0, 0);

					cauhoihientai++;
					addDatato(0, cauhoihientai - 1);
					addDatato(1, cauhoihientai);
					new changelayout_next().execute((Void) null);
				} else if (cauhoihientai == 29) {
//					Toast.makeText(Ketqua.this, " Đây là câu hỏi cuối cùng", 0)
//							.show();
				} else {
					cauhoihientai++;
				}

			} else if (screen < 1) {
				if (cauhoihientai - 1 >= 1) {
					cuon[0].scrollTo(0, 0);
					cuon[1].scrollTo(0, 0);
					cuon[2].scrollTo(0, 0);

					cauhoihientai--;
					addDatato(1, cauhoihientai);
					addDatato(2, cauhoihientai + 1);

					new changelayout_prev().execute((Void) null);
				} else if (cauhoihientai == 0) {
//					Toast.makeText(Ketqua.this, " Đây là câu hỏi đầu tiên ", 0)
//							.show();
				} else {
					cauhoihientai--;
				}
			} else {
				if (cauhoihientai == 29) {

					cauhoihientai--;

				} else if (cauhoihientai == 0) {

					cauhoihientai++;
				}

			}
			if(cauhoihientai==0){
				if(!isshowedtoast){
					isshowedtoast=true;
				Toast.makeText(Ketqua.this, " Đây là câu hỏi đầu tiên",0).show();
				}
			}
			 if (cauhoihientai == 29) {
					if(!isshowedtoast){
					isshowedtoast=true;
					Toast.makeText(Ketqua.this, " Đây là câu hỏi cuối cùng",
							0).show();
					}
			 }
			
			if(cauhoihientai!=0&&cauhoihientai!=29){
				isshowedtoast=false;
			}
		}

	};
	boolean isshowedtoast=false;
	protected class changelayout_prev extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			cuon_ngang.setCurrentScreen(1, false);

			addDatato(0, cauhoihientai - 1);

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

			cuon_ngang.setCurrentScreen(1, false);
			addDatato(2, cauhoihientai + 1);
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

	private void addView() {
		cuon_ngang.setFadingEdgeLength(0);
		for (int i = 1; i < 4; i++) {

			LinearLayout view = (LinearLayout) li.inflate(
					R.layout.itemviewcauhoithi, null);
			cuon_ngang.addView(view);

			image[i - 1] = (ImageView) view.findViewById(R.id.image);

			lina[i - 1] = (LinearCustome) view.findViewById(R.id.lina);
			linb[i - 1] = (LinearCustome) view.findViewById(R.id.linb);
			linc[i - 1] = (LinearCustome) view.findViewById(R.id.linc);
			lind[i - 1] = (LinearCustome) view.findViewById(R.id.lind);

			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					width, android.widget.LinearLayout.LayoutParams.FILL_PARENT);
			param.setMargins(-width, 0, 0, 0);
			lina[i - 1].setLayoutParams(param);
			linb[i - 1].setLayoutParams(param);
			linc[i - 1].setLayoutParams(param);
			lind[i - 1].setLayoutParams(param);

			cuon[i - 1] = (ScrollView) view.findViewById(R.id.cuon);

			tcauhoi[i - 1] = (TextView) view.findViewById(R.id.tcauhoi);
			noidung[i - 1] = (TextView) view.findViewById(R.id.noidung);

			tla[i - 1] = (TextView) view.findViewById(R.id.tla);
			tlb[i - 1] = (TextView) view.findViewById(R.id.tlb);
			tlc[i - 1] = (TextView) view.findViewById(R.id.tlc);
			tld[i - 1] = (TextView) view.findViewById(R.id.tld);

			lineara[i - 1] = (LinearLayout) view.findViewById(R.id.lineara);
			linearb[i - 1] = (LinearLayout) view.findViewById(R.id.linearb);
			linearc[i - 1] = (LinearLayout) view.findViewById(R.id.linearc);
			lineard[i - 1] = (LinearLayout) view.findViewById(R.id.lineard);

			linearaa[i - 1] = (LinearLayout) view.findViewById(R.id.linearaa);
			linearbb[i - 1] = (LinearLayout) view.findViewById(R.id.linearbb);
			linearcc[i - 1] = (LinearLayout) view.findViewById(R.id.linearcc);
			lineardd[i - 1] = (LinearLayout) view.findViewById(R.id.lineardd);

		}
		scroll.addView(cuon_ngang);
	}

	private void addDatato(int so_thutu_view, int so_thutu_cauhoi) {

		ItemCauhoi item = Toancuc.list_cauhoithi.get(so_thutu_cauhoi);

		Log.d("sothutucauhoi", "" + so_thutu_cauhoi);
		int i = so_thutu_view + 1;

		lina[i - 1].setBackgroundDrawable(null);
		linb[i - 1].setBackgroundDrawable(null);
		linc[i - 1].setBackgroundDrawable(null);
		lind[i - 1].setBackgroundDrawable(null);

		lineara[i - 1].setVisibility(LinearLayout.VISIBLE);
		linearaa[i - 1].setVisibility(LinearLayout.GONE);
		linearb[i - 1].setVisibility(LinearLayout.VISIBLE);
		linearbb[i - 1].setVisibility(LinearLayout.GONE);
		linearc[i - 1].setVisibility(LinearLayout.VISIBLE);
		linearcc[i - 1].setVisibility(LinearLayout.GONE);
		lineard[i - 1].setVisibility(LinearLayout.VISIBLE);
		lineardd[i - 1].setVisibility(LinearLayout.GONE);
		// reset layout c & d
		linc[i - 1].setVisibility(LinearLayout.VISIBLE);
		lind[i - 1].setVisibility(LinearLayout.VISIBLE);

		// set header cau hoi
		tcauhoi[i - 1].setText("Câu hỏi " + (so_thutu_cauhoi + 1));

		noidung[i - 1].setText(item.cauhoi);
		tla[i - 1].setText(item.traloia);
		tlb[i - 1].setText(item.traloib);

		if (item.image != null && item.image.compareTo("") != 0) {
			image[i - 1].setVisibility(ImageView.VISIBLE);
			image[i - 1].setImageResource(mapimage.get(item.image));
			image[i - 1].invalidate();
		} else
			image[i - 1].setVisibility(ImageView.GONE);

		if (item.traloic.compareTo("") != 0) {
			lind[i - 1].setVisibility(LinearLayout.VISIBLE);
			tlc[i - 1].setText(item.traloic);

		} else {
			linc[i - 1].setVisibility(LinearLayout.GONE);

		}
		if (item.traloid.compareTo("") != 0) {
			lind[i - 1].setVisibility(LinearLayout.VISIBLE);
			tld[i - 1].setText(item.traloid);
		} else {
			lind[i - 1].setVisibility(LinearLayout.GONE);
		}

		if (item.datraloidapan.compareTo("-1") != 0) {

			if (item.ketqua.compareTo(getAnswer(Integer
					.parseInt(item.datraloidapan))) != 0) {

				switch (item.dachon) {
				case 1:
					lina[i - 1].setBackgroundResource(R.drawable.doo);

					lineara[i - 1].setVisibility(LinearLayout.GONE);
					linearaa[i - 1].setVisibility(LinearLayout.VISIBLE);
					break;
				case 2:
					linb[i - 1].setBackgroundResource(R.drawable.doo);
					linearb[i - 1].setVisibility(LinearLayout.GONE);
					linearbb[i - 1].setVisibility(LinearLayout.VISIBLE);
					break;
				case 3:
					linc[i - 1].setBackgroundResource(R.drawable.doo);
					linearc[i - 1].setVisibility(LinearLayout.GONE);
					linearcc[i - 1].setVisibility(LinearLayout.VISIBLE);
					break;
				case 4:
					lind[i - 1].setBackgroundResource(R.drawable.doo);
					lineard[i - 1].setVisibility(LinearLayout.GONE);
					lineardd[i - 1].setVisibility(LinearLayout.VISIBLE);
					break;

				}
			}
		}
		switch (getSo(item.ketqua)) {
		case 1:
			lina[i - 1].setBackgroundResource(R.drawable.xanh);
			lineara[i - 1].setVisibility(LinearLayout.GONE);
			linearaa[i - 1].setVisibility(LinearLayout.VISIBLE);
			break;
		case 2:
			linb[i - 1].setBackgroundResource(R.drawable.xanh);
			linearb[i - 1].setVisibility(LinearLayout.GONE);
			linearbb[i - 1].setVisibility(LinearLayout.VISIBLE);
			break;
		case 3:
			linc[i - 1].setBackgroundResource(R.drawable.xanh);
			linearc[i - 1].setVisibility(LinearLayout.GONE);
			linearcc[i - 1].setVisibility(LinearLayout.VISIBLE);
			break;
		case 4:
			lind[i - 1].setBackgroundResource(R.drawable.xanh);
			lineard[i - 1].setVisibility(LinearLayout.GONE);
			lineardd[i - 1].setVisibility(LinearLayout.VISIBLE);
			break;

		}

	}

	boolean o_thongke = false;
	boolean o_ketquathi = false;

	int cauhoihientai = -1;

	boolean o_grid = false;
	boolean o_chitietgrid = false;
	ThongkeketquaAdapter adapter;
	ArrayList<Itemthongke> tk;

	private void setClick() {
		linkqt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!o_ketquathi)
					o_ketquathi = true;
				else
					return;
				o_thongke = false;

				ketquathi.setBackgroundResource(R.drawable.option_selected);
				thongke.setBackgroundDrawable(null);

				thongkeketquathi.setVisibility(LinearLayout.VISIBLE);
				title.setVisibility(LinearLayout.GONE);
				grid.setVisibility(GridView.GONE);
				scroll.setVisibility(LinearLayout.GONE);

				thongkelayout.setVisibility(LinearLayout.GONE);

				new thongke().execute((Void) null);
				quaylaigrid.setVisibility(Button.GONE);

			}
		});
		lintk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!o_thongke)
					o_thongke = true;
				else
					return;
				o_ketquathi = false;

				thongke.setBackgroundResource(R.drawable.option_selected);
				ketquathi.setBackgroundDrawable(null);

				title.setVisibility(LinearLayout.VISIBLE);
				grid.setVisibility(GridView.VISIBLE);
				scroll.setVisibility(LinearLayout.GONE);
				thongkeketquathi.setVisibility(LinearLayout.GONE);
				thongkelayout.setVisibility(LinearLayout.VISIBLE);
				thongkelayout.setAnimation(inFromRightAnimation());
				clear.setVisibility(Button.GONE);
			}
		});

		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				grid.setVisibility(GridView.GONE);
				scroll.setVisibility(LinearLayout.VISIBLE);
				headerketqua.setVisibility(LinearLayout.GONE);
				option_bar.setVisibility(LinearLayout.GONE);

				cauhoihientai = position;
				Log.d("cau hoi hien tai", "" + cauhoihientai);
				if (cauhoihientai == 0) {
					addDatato(0, 0);
					addDatato(1, 1);
					addDatato(2, 2);
					cuon_ngang.setCurrentScreen(0, false);

				} else if (cauhoihientai == 29) {
					addDatato(0, 27);
					addDatato(1, 28);
					addDatato(2, 29);
					cuon_ngang.setCurrentScreen(2, false);

				} else {
					addDatato(0, cauhoihientai - 1);
					addDatato(1, cauhoihientai);
					addDatato(2, cauhoihientai + 1);
					cuon_ngang.setCurrentScreen(1, false);

				}

				isback = true;
				quaylaigrid.setVisibility(Button.VISIBLE);
				Log.d("position", "" + position);
			}
		});

		grid.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				int p = grid.pointToPosition((int) event.getX(),
//						(int) event.getY());
//				if (grid.getChildAt(p) == null) {
//
//					return false;
//				}
//
//				Log.d("vitri ", "" + p);
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
//					alpha.setDuration(100);
//					alpha.setFillAfter(true);
//					position_pressed = p;
//					(grid.getChildAt(p)).startAnimation(alpha);
//
//				} else if (event.getAction() == MotionEvent.ACTION_UP) {
//					AlphaAnimation alpha = new AlphaAnimation(1F, 1F);
//					alpha.setDuration(0);
//					alpha.setFillAfter(true);
//					if ((grid.getChildAt(position_pressed)) != null) {
//						(grid.getChildAt(position_pressed))
//								.startAnimation(alpha);
//						(grid.getChildAt(p)).startAnimation(alpha);
//					}
//				}

				xx= (int)event.getX();
				yy=(int) event.getY();
				
				int p = grid.pointToPosition(xx,yy)-
						 grid.getFirstVisiblePosition();

			    
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
				
					if (grid.getChildAt(p) == null){
						Log.d("ACTION_DOWN null","actiondown null");
						
					   return false;
					}
						
					AlphaAnimation alpha = new AlphaAnimation(0.3F, 0.3F);
					alpha.setDuration(100);
					alpha.setFillAfter(true);
					position_pressed = p;
					xxsave=xx;
					yysave=yy;
					viewpressed=grid.getChildAt(p);
					viewpressed.startAnimation(alpha);
				    	return false;
				}

				if(Math.abs(xx-xxsave)>=10||Math.abs(yy-yysave)>=10){
					if(viewpressed==null) return false;
					AlphaAnimation alpha = new AlphaAnimation(1F, 1F);
					alpha.setDuration(0);
					alpha.setFillAfter(true);
				    viewpressed.startAnimation(alpha);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					
					Log.d("action up", "action up");
					AlphaAnimation alpha = new AlphaAnimation(1F, 1F);
					alpha.setDuration(0);
					alpha.setFillAfter(true);
				    viewpressed.startAnimation(alpha);
				}
				
				
//				if (event.getAction() == MotionEvent.ACTION_UP){
//					
//					if(p==position_pressed){
//						ItemBienbao itembb=list_item_bienbao.get(getsttview(sttdatahientai).from
//								+p+gridview[jj-1].getFirstVisiblePosition());
//						Log.d("id bien",""+itembb.id);
//							
//						detailbienbaolinearlayout.setVisibility(LinearLayout.VISIBLE);
//						back.setVisibility(Button.VISIBLE);
//						linviewinggrid.setVisibility(LinearLayout.GONE);
//						viewmode.setVisibility(LinearLayout.GONE);
//						linsearch.setVisibility(LinearLayout.GONE);
//						
//						fromgridview=true;
//						bienbaohientai = Integer.parseInt(itembb.id) - 1;
//						if (bienbaohientai == 0) {
//							adddatatoView(0, bienbaohientai);
//							adddatatoView(1, bienbaohientai + 1);
//							adddatatoView(2, bienbaohientai + 2);
//							cuon_ngang.setCurrentScreen(0, false);
//						} else if (bienbaohientai == 206) {
//							adddatatoView(0, bienbaohientai - 2);
//							adddatatoView(1, bienbaohientai - 1);
//							adddatatoView(2, bienbaohientai);
//							cuon_ngang.setCurrentScreen(2, false);
//						} else {
//							adddatatoView(0, bienbaohientai - 1);
//							adddatatoView(1, bienbaohientai);
//							adddatatoView(2, bienbaohientai + 1);
//							cuon_ngang.setCurrentScreen(1, false);
//						}
//					}
//				}
				
				return false;
			}
		});

		quaylaigrid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				option_bar.setVisibility(LinearLayout.VISIBLE);
				grid.setVisibility(GridView.VISIBLE);
				scroll.setVisibility(LinearLayout.GONE);
				quaylaigrid.setVisibility(Button.GONE);
				thongkeketquathi.setVisibility(LinearLayout.GONE);
				headerketqua.setVisibility(LinearLayout.VISIBLE);
			}
		});
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cauhoiDB.deleteAll1();
				tk.clear();
				adapter.notifyDataSetChanged();
				solando.setText("Số lần đỗ :0/0 lần thi");
				diemtrungbinh.setText("Điểm trung bình :" + "0/30");
				clear.setVisibility(Button.GONE);
				kephancach.setVisibility(LinearLayout.GONE);
			}
		});
	}

	int xx;
	int yy;
	int xxsave;
	int yysave;
	
	View viewpressed=null;
	protected class thongke extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {
			tk = getDataThongke();
			adapter = new ThongkeketquaAdapter(Ketqua.this,
					R.layout.itemviewkq, tk);
			listthongke.setAdapter(adapter);
			if (!tk.isEmpty())
				clear.setVisibility(Button.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected ArrayList<Itemthongke> getDataThongke() {
		Itemthongke item;
		ArrayList<Itemthongke> lisst = new ArrayList<Itemthongke>();
		cauhoiDB.openToRead();
		Cursor c = cauhoiDB.getAllItems1();
		startManagingCursor(c);
		c.moveToFirst();
		int solando = 0;
		int solanthi = 0;
		float tongsodung = 0;
		while (!c.isAfterLast()) {

			String ngay = c.getString(c.getColumnIndex(CauhoiDB.NGAY));
			String gio = c.getString(c.getColumnIndex(CauhoiDB.GIO));
			String thoigianlambai = c.getString(c.getColumnIndex(CauhoiDB.THOIGIANLAMBAI));
			String tyledung = c.getString(c.getColumnIndex(CauhoiDB.TYLEDUNG));
			String dotruot = c.getString(c.getColumnIndex(CauhoiDB.DOTRUOT));

			item = new Itemthongke();

			item.ngay = ngay;
			item.gio = gio;
			item.thoigianlambai = thoigianlambai;
			item.tyledung = tyledung;
			tongsodung += Integer.parseInt(tyledung.substring(0,
					tyledung.indexOf("/")));
			item.dotruot = dotruot;
			if (item.dotruot.compareTo("1") == 0) {
				solando++;
			}
			solanthi++;
			lisst.add(item);
			c.moveToNext();
		}

		this.solando.setText("Số lần đỗ : " + solando + "/" + solanthi
				+ " lần thi");
		if (solanthi > 0) {
			
			String dtb=(tongsodung / (float) solanthi)+"";
			if(dtb.length()>4)
				dtb=dtb.substring(0,4);
			this.diemtrungbinh.setText("Điểm trung bình : "
					+ dtb + "/30");
			kephancach.setVisibility(LinearLayout.VISIBLE);
		} else
			this.diemtrungbinh.setText("Điểm trung bình : " + "0/30");
		return lisst;
	}

	CauhoiDB cauhoiDB = new CauhoiDB(Ketqua.this);
	int position_pressed = -1;

	protected int getdapan(String ketqua) {
		if (ketqua.compareTo("A") == 0) {
			return 1;
		} else if (ketqua.compareTo("B") == 0) {
			return 2;
		} else if (ketqua.compareTo("C") == 0) {
			return 3;
		} else if (ketqua.compareTo("D") == 0) {
			return 4;
		}
		return -1;
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

	boolean initview = false;

	@Override
	protected void onResume() {
		super.onResume();

		if (!initview) { // chưa init view
			initview = true;
			dialog = new ProgressDialog(this);
			dialog.setMessage("Đang tải dữ liệu...");
			dialog.show();
			new initview0().execute((Void) null);

		} else { // trường hợp đã init view

			if (Toancuc.list_cauhoithi.isEmpty()) {
				headerketqua.setVisibility(LinearLayout.GONE);
				chualambai.setVisibility(TextView.VISIBLE);
				option_bar.setVisibility(LinearLayout.VISIBLE);
				grid.setVisibility(GridView.GONE);
				return;
			}

			if (!Toancuc.chuathemdulieu)
				return;

			Toancuc.chuathemdulieu = false;
			if (!dialog.isShowing()) {

				dialog = new ProgressDialog(this);
				dialog.setMessage("Đang chấm bài thi...");
				dialog.show();
			}
			new LoadingData0().execute((Void) null);
		}
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

	private int getSo(String s) {
		if (s.trim().compareTo("A") == 0) {
			return 1;
		} else if (s.trim().compareTo("B") == 0) {
			return 2;
		} else if (s.trim().compareTo("C") == 0) {
			return 3;
		} else if (s.trim().compareTo("D") == 0) {
			return 4;
		}
		return -1;
	}

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

	// / asyntask
	// ////asyn task
	ProgressDialog dialog;

	protected class LoadingData0 extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			new LoadingData().execute((Void) null);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}// end search

	protected class LoadingData extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			Log.d("******", "post execute");

			headerketqua.setVisibility(LinearLayout.VISIBLE);
			option_bar.setVisibility(LinearLayout.VISIBLE);
			chualambai.setVisibility(TextView.GONE);
			grid.setVisibility(GridView.VISIBLE);
			scroll.setVisibility(LinearLayout.GONE);

			int dung = get_so_cau_dung();
			String thoigianlambai = (new DateUtils()
					.timeremain(Toancuc.thoigianlambai));
			String tyledung = dung + "/30";
			sodung.setText("Số câu đúng : " + tyledung);
			thoigian.setText("Thời gian : " + thoigianlambai);
			dotruot.setText((dung >= 26 ? "Đỗ" : "Trượt"));

			if (dung >= 26) {
				title.setBackgroundResource(R.drawable.banner_ket_qua_do);

			} else {
				title.setBackgroundResource(R.drawable.banner_ket_qua_truot);
				cauhoiDB.openToRead();
				Cursor c = cauhoiDB.getAllItems_ask();
				if (c.getCount() == 0) {
					new QuesionDialog(Ketqua.this, cauhoiDB).show();
				}
			}
			cauhoiDB.openToWrite();
			grid.setAdapter(new GridKetquaAdapter(Ketqua.this,
					Toancuc.list_cauhoithi));
			// addDatatoView();

			// enable phần kết quả thi
			o_ketquathi = false;
			thongke.setBackgroundResource(R.drawable.option_selected);
			ketquathi.setBackgroundDrawable(null);

			title.setVisibility(LinearLayout.VISIBLE);
			grid.setVisibility(GridView.VISIBLE);
			scroll.setVisibility(LinearLayout.GONE);
			thongkeketquathi.setVisibility(LinearLayout.GONE);
			thongkelayout.setVisibility(LinearLayout.VISIBLE);
			thongkelayout.setAnimation(inFromRightAnimation());
			clear.setVisibility(Button.GONE);
			dialog.dismiss();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}// end search

	// /////////init view
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

	boolean dainitview = false;

	protected class initview extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			if (!dainitview) {
				dainitview = true;
				ketqua = (LinearLayout) findViewById(R.id.ketqua);
				ketquathi = (LinearLayout) findViewById(R.id.ketquathi);
				thongke = (LinearLayout) findViewById(R.id.thongke);
				thongkelayout = (LinearLayout) findViewById(R.id.thongkelayout);
				thongkeketquathi = (LinearLayout) findViewById(R.id.thongkeketquathi);

				linkqt=(LinearLayout)findViewById(R.id.kqt);
				lintk=(LinearLayout)findViewById(R.id.tk);
				
				kephancach = (LinearLayout) findViewById(R.id.kephancach);
				// headerketqua=(LinearLayout)findViewById(R.id.headerketqua);
				title = (LinearLayout) findViewById(R.id.title);
				// option_bar=(LinearLayout)findViewById(R.id.option_bar);

				sodung = (TextView) findViewById(R.id.sodung);
				thoigian = (TextView) findViewById(R.id.thoigian);
				dotruot = (TextView) findViewById(R.id.dotruot);
				// chualambai=(TextView)findViewById(R.id.chualambai);

				// grid = (GridView) findViewById(R.id.gridview);
				scroll = (LinearLayout) findViewById(R.id.scroll);
				scroll.setVisibility(ScrollView.GONE);
				thongkeketquathi.setVisibility(LinearLayout.GONE);

				li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				cuon_ngang = new HorizontalPaper(Ketqua.this);
				cuon_ngang.setFadingEdgeLength(0);
				cuon_ngang.setOnScreenSwitchListener(switchListener);
				cuon_ngang.setLayoutParams(new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

				solando = (TextView) findViewById(R.id.solando);
				diemtrungbinh = (TextView) findViewById(R.id.diemtrungbinh);
				listthongke = (ListView) findViewById(R.id.listthongke);
				listthongke.setDivider(null);
				listthongke.setDividerHeight(0);
				listthongke.setFadingEdgeLength(0);
				listthongke.setFocusable(false);
				listthongke.setFocusableInTouchMode(false);

				setClick();
				addView();
			}

			if (Toancuc.list_cauhoithi.isEmpty()) {
				headerketqua.setVisibility(LinearLayout.GONE);
				chualambai.setVisibility(TextView.VISIBLE);
				option_bar.setVisibility(LinearLayout.VISIBLE);
				grid.setVisibility(GridView.GONE);
				dialog.dismiss();
				return;
			}

			if (!Toancuc.chuathemdulieu) {
				dialog.dismiss();
				return;
			}

			Toancuc.chuathemdulieu = false;

			dialog.setMessage("Đang chấm bài thi...");

			new LoadingData().execute((Void) null);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	// / con fỉm ẽit
	private void showDialogConfirmExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(Html.fromHtml("Bạn muốn thoát chương trình?"))
				.setCancelable(false)
				.setPositiveButton("Thoát",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Ketqua.this.finish();
							}
						})
				.setNegativeButton("Không",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	boolean isback = false;

	@Override
	public void onBackPressed() {
		if (isback) {
			option_bar.setVisibility(LinearLayout.VISIBLE);
			grid.setVisibility(GridView.VISIBLE);
			scroll.setVisibility(LinearLayout.GONE);
			quaylaigrid.setVisibility(Button.GONE);
			thongkeketquathi.setVisibility(LinearLayout.GONE);
			headerketqua.setVisibility(LinearLayout.VISIBLE);
			isback = false;
		} else
			showDialogConfirmExit();
		return;
	}



}
