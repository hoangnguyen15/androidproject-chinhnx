package com.krazevina.thioto;

import java.util.ArrayList;
import java.util.HashMap;

import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemCauhoi;
import com.krazevina.thioto.objects.LinearCustome;
import com.krazevina.thioto.rotate.Flip3dAnimation;
import com.krazevina.thioto.sqlite.CauhoiDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class Bode extends Activity {
	private ProgressDialog dialog;
	private CauhoiDB cauhoidatabase;
	private LinearLayout luat0;
	private LinearLayout bienbao0;
	private LinearLayout tinhhuong0;

	private LinearLayout luat;
	private LinearLayout bienbao;
	private LinearLayout tinhhuong;

	private LinearLayout header;
	private ListView list;
	private ListView list2;
	private ListView list3;

	private Button clear;
	private Button back;

	private ArrayList<ItemCauhoi> listcauhoi_luat = new ArrayList<ItemCauhoi>();
	private ArrayList<ItemCauhoi> listcauhoi_bienbao = new ArrayList<ItemCauhoi>();
	private ArrayList<ItemCauhoi> listcauhoi_tinhhuong = new ArrayList<ItemCauhoi>();

	// // cuooong
	private LinearLayout viewlistcaudetail;
	private LinearLayout viewlistcau;

	private ImageView image[] = new ImageView[3];

	private LinearLayout lineara[] = new LinearLayout[3]; // header cau hoi
	LinearLayout linearaa[] = new LinearLayout[4]; // header cau hoi

	private LinearLayout linearb[] = new LinearLayout[3];
	private LinearLayout linearbb[] = new LinearLayout[3];

	private LinearLayout linearc[] = new LinearLayout[3];
	private LinearLayout linearcc[] = new LinearLayout[3];

	private LinearLayout lineard[] = new LinearLayout[3];
	private LinearLayout lineardd[] = new LinearLayout[3];

	private LinearLayout anima[] = new LinearLayout[3];
	private LinearLayout animb[] = new LinearLayout[3];
	private LinearLayout animc[] = new LinearLayout[3];
	private LinearLayout animd[] = new LinearLayout[3];

	private LinearCustome lina[] = new LinearCustome[3];
	private LinearCustome linb[] = new LinearCustome[3];
	private LinearCustome linc[] = new LinearCustome[3];
	private LinearCustome lind[] = new LinearCustome[3];

	private TextView tcauhoi[] = new TextView[3];
	private TextView noidung[] = new TextView[3];

	private TextView tla[] = new TextView[3];// txt cau tra loi
	private TextView tlb[] = new TextView[3];
	private TextView tlc[] = new TextView[3];
	private TextView tld[] = new TextView[3];

	private ScrollView cuon[] = new ScrollView[3];

	private LayoutInflater li;
	private HorizontalPaper cuon_ngang;

	// / cuonend
	TextView textheader;
	LinearLayout bardetail;
	LinearLayout barlist;
	Button truoc;
	Button tiep;
	Button xemtraloi;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bode);

	}

	public void initview() {

		bardetail = (LinearLayout) findViewById(R.id.bardetail);
		barlist = (LinearLayout) findViewById(R.id.barlist);
		bardetail.setVisibility(LinearLayout.GONE);

		header = (LinearLayout) findViewById(R.id.title);
		textheader = (TextView) findViewById(R.id.textheader);
		list = (ListView) findViewById(R.id.list);
		list.setDivider(null);
		list.setDividerHeight(0);

		list2 = (ListView) findViewById(R.id.list2);
		list2.setDivider(null);
		list2.setDividerHeight(0);

		list3 = (ListView) findViewById(R.id.list3);
		list3.setDivider(null);
		list3.setDividerHeight(0);

		list2.setVisibility(ListView.GONE);
		list3.setVisibility(ListView.GONE);

		luat0 = (LinearLayout) findViewById(R.id.luat0);
		bienbao0 = (LinearLayout) findViewById(R.id.bienbao0);
		tinhhuong0 = (LinearLayout) findViewById(R.id.tinhhuong0);

		luat = (LinearLayout) findViewById(R.id.luat);
		bienbao = (LinearLayout) findViewById(R.id.bienbao);
		tinhhuong = (LinearLayout) findViewById(R.id.tinhhuong);

		clear = (Button) findViewById(R.id.clear);
		clear.setVisibility(Button.GONE);
		back = (Button) findViewById(R.id.back);
		back.setVisibility(Button.GONE);

		truoc = (Button) findViewById(R.id.truoc);
		tiep = (Button) findViewById(R.id.tiep);
		xemtraloi = (Button) findViewById(R.id.xemtraloi);
		setClick();
		setClick_Item_list();
		setOnScrollList();
		cauhoidatabase = new CauhoiDB(Bode.this);
		// cauhoidatabase.openToWrite();
		// cauhoidatabase.deleteAll();
		// getData();

		viewlistcau = (LinearLayout) findViewById(R.id.viewlistcau);
		viewlistcaudetail = (LinearLayout) findViewById(R.id.viewlistcaudetail);
		viewlistcaudetail.setVisibility(LinearLayout.GONE);
		li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cuon_ngang = new HorizontalPaper(Bode.this);
		cuon_ngang.setOnScreenSwitchListener(switchListener);
		cuon_ngang.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		mapimage = Toancuc.getMapImagecauhoi();
		addView();

		Rect rectgle = new Rect();
		Window window = getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		this.width = rectgle.right;

	}

	int width = -1;
	int so_datraloi_luat = -1;
	int so_datraloi_bienbao = -1;
	int so_dataloi_tinhhuong = -1;

	int so_chuatraloi_luat = -1;
	int so_chuatraloi_bienbao = -1;
	int so_chuatraloi_tinhhuong = -1;

	private void setOnScrollList() {
		list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= so_chuatraloi_luat
						&& so_datraloi_luat != -1) {
					textheader.setText("Đã trả lời");
					header.setBackgroundResource(R.drawable.da_tra_loi_section);
				} else {
					textheader.setText("Chưa trả lời");
					header.setBackgroundResource(R.drawable.chua_tra_loi_section);
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}
		});
		list2.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= so_chuatraloi_bienbao
						&& so_datraloi_bienbao != -1) {
					textheader.setText("Đã trả lời");
					header.setBackgroundResource(R.drawable.da_tra_loi_section);
				} else {
					textheader.setText("Chưa trả lời");
					header.setBackgroundResource(R.drawable.chua_tra_loi_section);
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}
		});
		list3.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= so_chuatraloi_tinhhuong
						&& so_dataloi_tinhhuong != -1) {
					textheader.setText("Đã trả lời");
					header.setBackgroundResource(R.drawable.da_tra_loi_section);
				} else {
					textheader.setText("Chưa trả lời");
					header.setBackgroundResource(R.drawable.chua_tra_loi_section);
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}
		});
	}

	private void addDatato(int so_thutu_view, int so_thutu_cauhoi) {
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
		ItemCauhoi item = list_all_cauhoi.get(so_thutu_cauhoi);

		Log.d("sothutucauhoi", "" + so_thutu_cauhoi);
		int i = so_thutu_view + 1;
		traloihientai[i - 1] = Integer.parseInt(item.datraloidapan);
		animc[i - 1].setBackgroundDrawable(null);
		animb[i - 1].setBackgroundDrawable(null);
		anima[i - 1].setBackgroundDrawable(null);
		animd[i - 1].setBackgroundDrawable(null);

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
		// reset layout c & d
		linc[i - 1].setVisibility(LinearLayout.VISIBLE);
		lind[i - 1].setVisibility(LinearLayout.VISIBLE);

		// set header cau hoi
		tcauhoi[i - 1].setText("Câu hỏi " + (1 + so_thutu_cauhoi));

		noidung[i - 1].setText(item.cauhoi);

		if (item.image != null && item.image.compareTo("") != 0) {
			image[i - 1].setVisibility(ImageView.VISIBLE);
			image[i - 1].setImageResource(mapimage.get(item.image));

		} else
			image[i - 1].setVisibility(ImageView.GONE);

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
			anima[i - 1].setBackgroundResource(R.drawable.cam);
			Log.d("stt" + so_thutu_view, "dapan1");
			
			lineara[i - 1].setVisibility(LinearLayout.GONE);
			linearaa[i - 1].setVisibility(LinearLayout.VISIBLE);
			isFirstImagea[i - 1] = false;
			//
		} else if (item.datraloidapan.compareTo("2") == 0) {
			animb[i - 1].setBackgroundResource(R.drawable.cam);
			Log.d("stt" + so_thutu_view, "dapan2");
		
			linearb[i - 1].setVisibility(LinearLayout.GONE);
			linearbb[i - 1].setVisibility(LinearLayout.VISIBLE);
			isFirstImageb[i - 1] = false;

		} else if (item.datraloidapan.compareTo("3") == 0) {
			animc[i - 1].setBackgroundResource(R.drawable.cam);
			Log.d("stt" + so_thutu_view, "dapan3");
		
			linearc[i - 1].setVisibility(LinearLayout.GONE);
			linearcc[i - 1].setVisibility(LinearLayout.VISIBLE);
			isFirstImagec[i - 1] = false;
		} else if (item.datraloidapan.compareTo("4") == 0) {
			animd[i - 1].setBackgroundResource(R.drawable.cam);
			
			Log.d("stt" + so_thutu_view, "dapan4");
			lineard[i - 1].setVisibility(LinearLayout.GONE);
			lineardd[i - 1].setVisibility(LinearLayout.VISIBLE);
			isFirstImaged[i - 1] = false;
		}

	}

	int cauhoihientai = -1;
	boolean isback = false;

	boolean isdetail=false;
	private void setClick_Item_list() {
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				
				if (l.get(position).idcauhoi.compareTo("-1") != 0) {

					isdetail=true;
					
					viewlistcaudetail.setVisibility(LinearLayout.VISIBLE);
					viewlistcau.setVisibility(LinearLayout.GONE);
					cuon_ngang.setCurrentScreen(1, false);
					back.setVisibility(Button.VISIBLE);

					cauhoihientai = Integer.parseInt(l.get(position).idcauhoi) - 1;
					isback = true;
					if (cauhoihientai == 0) {
						addDatato(0, 0);
						addDatato(1, 1);
						addDatato(2, 2);
						cuon_ngang.setCurrentScreen(0, false);

					} else if (cauhoihientai == 404) {
						addDatato(0, 402);
						addDatato(1, 403);
						addDatato(2, 404);
						cuon_ngang.setCurrentScreen(2, false);

					} else {
						addDatato(0, cauhoihientai - 1);
						addDatato(1, cauhoihientai);
						addDatato(2, cauhoihientai + 1);
						cuon_ngang.setCurrentScreen(1, false);

					}

					// setChange();
					barlist.setVisibility(LinearLayout.GONE);
					bardetail.setVisibility(LinearLayout.VISIBLE);
					clear.setVisibility(Button.GONE);
				}
			}
		});

		list2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				if (l.get(position).idcauhoi.compareTo("-1") != 0) {
					
					isdetail=true;

					viewlistcaudetail.setVisibility(LinearLayout.VISIBLE);
					viewlistcau.setVisibility(LinearLayout.GONE);
					cuon_ngang.setCurrentScreen(1, false);
					back.setVisibility(Button.VISIBLE);
					isback = true;
					cauhoihientai = Integer.parseInt(l.get(position).idcauhoi) - 1;

					if (cauhoihientai == 0) {
						addDatato(0, 0);
						addDatato(1, 1);
						addDatato(2, 2);
						cuon_ngang.setCurrentScreen(0, false);

					} else if (cauhoihientai == 404) {
						addDatato(0, 402);
						addDatato(1, 403);
						addDatato(2, 404);
						cuon_ngang.setCurrentScreen(2, false);

					} else {
						addDatato(0, cauhoihientai - 1);
						addDatato(1, cauhoihientai);
						addDatato(2, cauhoihientai + 1);
						cuon_ngang.setCurrentScreen(1, false);

					}
					// setChange();
					barlist.setVisibility(LinearLayout.GONE);
					bardetail.setVisibility(LinearLayout.VISIBLE);
					clear.setVisibility(Button.GONE);

				}
			}
		});
		list3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				if (l.get(position).idcauhoi.compareTo("-1") != 0) {
//					Toast.makeText(Bode.this,
//							" position " + l.get(position).idcauhoi, 0).show();

					isdetail=true;
					
					viewlistcaudetail.setVisibility(LinearLayout.VISIBLE);
					viewlistcau.setVisibility(LinearLayout.GONE);
					cuon_ngang.setCurrentScreen(1, false);
					back.setVisibility(Button.VISIBLE);

					cauhoihientai = Integer.parseInt(l.get(position).idcauhoi) - 1;
					isback = true;
					if (cauhoihientai == 0) {
						addDatato(0, 0);
						addDatato(1, 1);
						addDatato(2, 2);
						cuon_ngang.setCurrentScreen(0, false);

					} else if (cauhoihientai == 404) {
						addDatato(0, 402);
						addDatato(1, 403);
						addDatato(2, 404);
						cuon_ngang.setCurrentScreen(2, false);

					} else {
						addDatato(0, cauhoihientai - 1);
						addDatato(1, cauhoihientai);
						addDatato(2, cauhoihientai + 1);
						cuon_ngang.setCurrentScreen(1, false);

					}
					// setChange();
					barlist.setVisibility(LinearLayout.GONE);
					bardetail.setVisibility(LinearLayout.VISIBLE);
					clear.setVisibility(Button.GONE);
					// changelayout();
				}
			}
		});

	}

	private HorizontalPaper.OnScreenSwitchListener switchListener = new HorizontalPaper.OnScreenSwitchListener() {

		@Override
		public void onScreenSwitched(int screen) {

			if (screen > 1) {

				if (cauhoihientai + 1 <= 403) {
					cuon[0].scrollTo(0, 0);
					cuon[1].scrollTo(0, 0);
					cuon[2].scrollTo(0, 0);

					cauhoihientai++;
					addDatato(0, cauhoihientai - 1);
					addDatato(1, cauhoihientai);
					new changelayout_next().execute((Void) null);
				} else {
					cauhoihientai = 404;
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
				} else {
					cauhoihientai = 0;
				}

			} else {

				if (cauhoihientai == 404) {
					addDatato(2, 404);
					cauhoihientai--;
				} else if (cauhoihientai == 0) {
					addDatato(0, 0);
					cauhoihientai++;

				}
			}

			if (cauhoihientai >= 0 && cauhoihientai <= 224) {
			//	Toast.makeText(Bode.this, "luat  ", 0).show();
				luat.setBackgroundResource(R.drawable.option_selected);
				bienbao.setBackgroundDrawable(null);
				tinhhuong.setBackgroundDrawable(null);

				currentcategory = 1;
			} else if (cauhoihientai >= 225 && cauhoihientai <= 324) {
			//	Toast.makeText(Bode.this, " bienbao ", 0).show();
				bienbao.setBackgroundResource(R.drawable.option_selected);
				luat.setBackgroundDrawable(null);
				tinhhuong.setBackgroundDrawable(null);
				currentcategory = 2;
			} else {
			//	Toast.makeText(Bode.this, " tinhhuong ", 0).show();
				tinhhuong.setBackgroundResource(R.drawable.option_selected);
				bienbao.setBackgroundDrawable(null);
				luat.setBackgroundDrawable(null);
				currentcategory = 3;
			}

			if(cauhoihientai==0){
				if(!isshowedtoast){
					isshowedtoast=true;
				Toast.makeText(Bode.this, " Đây là câu hỏi đầu tiên",0).show();
				}
			}
			 if (cauhoihientai ==404) {
					if(!isshowedtoast){
					isshowedtoast=true;
					Toast.makeText(Bode.this, " Đây là câu hỏi cuối cùng",
							0).show();
					}
			 }
			
			if(cauhoihientai!=0&&cauhoihientai!=404){
				isshowedtoast=false;
			}
	
		}
	};

	boolean isshowedtoast=false;
	protected class changelayout_prev extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			// setChange();
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
			// setChange();
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

	protected class showdataluat extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {
			list.setAdapter(new BodeAdapter(Bode.this,
					R.layout.itemviewdanhsachcauhoi, listcauhoi_luat));

			list.setVisibility(ListView.VISIBLE);
			list2.setVisibility(ListView.GONE);
			list3.setVisibility(ListView.GONE);
			viewlistcaudetail.setVisibility(LinearLayout.GONE);
			viewlistcau.setVisibility(LinearLayout.VISIBLE);
			back.setVisibility(Button.GONE);
			if (luat_da_tl.isEmpty()) {
				clear.setVisibility(Button.GONE);
			} else {
				clear.setVisibility(Button.VISIBLE);
			}
			setView();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class showdatabienbao extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {
			list2.setAdapter(new BodeAdapter(Bode.this,
					R.layout.itemviewdanhsachcauhoi, listcauhoi_bienbao));
			list.setVisibility(ListView.GONE);
			list2.setVisibility(ListView.VISIBLE);
			list3.setVisibility(ListView.GONE);
			viewlistcaudetail.setVisibility(LinearLayout.GONE);
			viewlistcau.setVisibility(LinearLayout.VISIBLE);
			back.setVisibility(Button.GONE);
			if (bienbao_da_tl.isEmpty()) {
				clear.setVisibility(Button.GONE);
			} else {
				clear.setVisibility(Button.VISIBLE);
			}
			setView();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class showdatatinhhuong extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {
			list3.setAdapter(new BodeAdapter(Bode.this,
					R.layout.itemviewdanhsachcauhoi, listcauhoi_tinhhuong));
			list.setVisibility(ListView.GONE);
			list2.setVisibility(ListView.GONE);
			list3.setVisibility(ListView.VISIBLE);
			viewlistcaudetail.setVisibility(LinearLayout.GONE);
			viewlistcau.setVisibility(LinearLayout.VISIBLE);
			back.setVisibility(Button.GONE);
			if (tinhuong_da_tl.isEmpty()) {
				clear.setVisibility(Button.GONE);
			} else {
				clear.setVisibility(Button.VISIBLE);
			}
			setView();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	int currentcategory = 1;

	private void setClick() {
		luat0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentcategory == 1) {
					return;
				}
				currentcategory = 1;
				luat.setBackgroundResource(R.drawable.option_selected);
				bienbao.setBackgroundDrawable(null);
				tinhhuong.setBackgroundDrawable(null);
				new showdataluat().execute((Void) null);
			}
		});
		bienbao0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentcategory == 2) {
					return;
				}
				currentcategory = 2;

				bienbao.setBackgroundResource(R.drawable.option_selected);
				luat.setBackgroundDrawable(null);
				tinhhuong.setBackgroundDrawable(null);
				new showdatabienbao().execute((Void) null);
			}
		});
		tinhhuong0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentcategory == 3) {
					return;
				}
				currentcategory = 3;
				tinhhuong.setBackgroundResource(R.drawable.option_selected);
				bienbao.setBackgroundDrawable(null);
				luat.setBackgroundDrawable(null);
				new showdatatinhhuong().execute((Void) null);
			}
		});

		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("clear", "clear");
				if (currentcategory == 1 && !luat_da_tl.isEmpty()) {

					Log.d("luatdatraloi", "" + luat_da_tl.size());
					Log.d("luatchuatraloi", "" + luat_chua_tl.size());

					for (int i = luat_da_tl.size() - 1; i >= 0; i--) {
						ItemCauhoi item = luat_da_tl.get(i);
						cauhoidatabase.openToWrite();
						cauhoidatabase.update(Integer.parseInt(item.idcauhoi),"-1");
						item.datraloidapan = "-1";
						Log.d("cau"+(item.idcauhoi),"can remove dap an");
						list_all_cauhoi.get(Integer.parseInt(item.idcauhoi)-1).datraloidapan="-1";
						luat_chua_tl.add(0, item);
					}
			
					luat_da_tl.clear();
					
					Log.d("luatdatraloi", "" + luat_da_tl.size());
					Log.d("luatchuatraloi", "" + luat_chua_tl.size());
					
					setView();
				} else if (currentcategory == 2 && !bienbao_da_tl.isEmpty()) {
					for (int i = bienbao_da_tl.size() - 1; i >= 0; i--) {
						ItemCauhoi item = bienbao_da_tl.get(i);
						cauhoidatabase.openToWrite();
						cauhoidatabase.update(Integer.parseInt(item.idcauhoi),"-1");
						item.datraloidapan = "-1";
						
						Log.d("cau"+(item.idcauhoi),"can remove dap an");
						list_all_cauhoi.get(Integer.parseInt(item.idcauhoi)-1).datraloidapan="-1";
						
						bienbao_chua_tl.add(0, item);
					}
					bienbao_da_tl.clear();
					setView();
				} else if (currentcategory == 3 && !tinhuong_da_tl.isEmpty()) {
					for (int i = tinhuong_da_tl.size() - 1; i >= 0; i--) {
						ItemCauhoi item = tinhuong_da_tl.get(i);
						cauhoidatabase.openToWrite();
						cauhoidatabase.update(Integer.parseInt(item.idcauhoi),"-1");
						item.datraloidapan = "-1";
						
						Log.d("cau"+(item.idcauhoi),"can remove dap an");
						list_all_cauhoi.get(Integer.parseInt(item.idcauhoi)-1).datraloidapan="-1";
						
						tinhuong_chua_tl.add(0, item);
					}
					tinhuong_da_tl.clear();
					setView();
				}
				clear.setVisibility(Button.GONE);

				resetBackgroundLayout();
			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				back.setVisibility(Button.GONE);

				if (currentcategory == 1) {
					list.setVisibility(ListView.VISIBLE);
					list2.setVisibility(ListView.GONE);
					list3.setVisibility(ListView.GONE);
					viewlistcaudetail.setVisibility(LinearLayout.GONE);
					viewlistcau.setVisibility(LinearLayout.VISIBLE);

					// hide or visible button xoa da tra loi
					if (luat_da_tl.isEmpty()) {
						clear.setVisibility(Button.GONE);
					} else {
						clear.setVisibility(Button.VISIBLE);
					}
				} else if (currentcategory == 2) {
					list.setVisibility(ListView.GONE);
					list2.setVisibility(ListView.VISIBLE);
					list3.setVisibility(ListView.GONE);
					viewlistcaudetail.setVisibility(LinearLayout.GONE);
					viewlistcau.setVisibility(LinearLayout.VISIBLE);
					// hide or visible button xoa da tra loi
					if (bienbao_da_tl.isEmpty()) {
						clear.setVisibility(Button.GONE);
					} else {
						clear.setVisibility(Button.VISIBLE);
					}
				} else if (currentcategory == 3) {
					list.setVisibility(ListView.GONE);
					list2.setVisibility(ListView.GONE);
					list3.setVisibility(ListView.VISIBLE);
					viewlistcaudetail.setVisibility(LinearLayout.GONE);
					viewlistcau.setVisibility(LinearLayout.VISIBLE);
					// hide or visible button xoa da tra loi
					if (tinhuong_da_tl.isEmpty()) {
						clear.setVisibility(Button.GONE);
					} else {
						clear.setVisibility(Button.VISIBLE);
					}
				}

				barlist.setVisibility(LinearLayout.VISIBLE);
				bardetail.setVisibility(LinearLayout.GONE);
				setView();
			}
		});

		truoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d("cauhoihientai", "" + cauhoihientai);
				if (cauhoihientai == 0) {
					Toast.makeText(Bode.this, " Đây là câu hỏi đầu tiên ", 0)
							.show();
					return;
				}
				if (cauhoihientai == 404) {
					cuon_ngang.setCurrentScreen(1, true);
				} else
					cuon_ngang.setCurrentScreen(0, true);

				if (cauhoihientai >= 0 && cauhoihientai <= 224) {
				//	Toast.makeText(Bode.this, "luat  ", 0).show();
					luat.setBackgroundResource(R.drawable.option_selected);
					bienbao.setBackgroundDrawable(null);
					tinhhuong.setBackgroundDrawable(null);

					currentcategory = 1;
				} else if (cauhoihientai >= 225 && cauhoihientai <= 324) {
				//	Toast.makeText(Bode.this, " bienbao ", 0).show();
					bienbao.setBackgroundResource(R.drawable.option_selected);
					luat.setBackgroundDrawable(null);
					tinhhuong.setBackgroundDrawable(null);
					currentcategory = 2;
				} else {
				//	Toast.makeText(Bode.this, " tinhhuong ", 0).show();
					tinhhuong.setBackgroundResource(R.drawable.option_selected);
					bienbao.setBackgroundDrawable(null);
					luat.setBackgroundDrawable(null);
					currentcategory = 3;
				}

			}
		});
		tiep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cauhoihientai == 404) {
					Toast.makeText(Bode.this, " Đây là câu hỏi cuối cùng", 0)
							.show();
					return;
				}
				Log.d("cauhoihientai", "cauhoihientai" + cauhoihientai);
				if (cauhoihientai == 0) {
					cuon_ngang.setCurrentScreen(1, true);
					// cauhoihientai++;
				} else
					cuon_ngang.setCurrentScreen(2, true);

				if (cauhoihientai >= 0 && cauhoihientai <= 224) {
				//	Toast.makeText(Bode.this, "luat  ", 0).show();
					luat.setBackgroundResource(R.drawable.option_selected);
					bienbao.setBackgroundDrawable(null);
					tinhhuong.setBackgroundDrawable(null);

					currentcategory = 1;
				} else if (cauhoihientai >= 225 && cauhoihientai <= 324) {
				//	Toast.makeText(Bode.this, " bienbao ", 0).show();
					bienbao.setBackgroundResource(R.drawable.option_selected);
					luat.setBackgroundDrawable(null);
					tinhhuong.setBackgroundDrawable(null);
					currentcategory = 2;
				} else {
				//	Toast.makeText(Bode.this, " tinhhuong ", 0).show();
					tinhhuong.setBackgroundResource(R.drawable.option_selected);
					bienbao.setBackgroundDrawable(null);
					luat.setBackgroundDrawable(null);
					currentcategory = 3;
				}

			}

		});
		xemtraloi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ItemCauhoi item = list_all_cauhoi.get(cauhoihientai);
				int dachon = Integer.parseInt(item.datraloidapan);
				int ketqua = getdapan(item.ketqua);
				if (cauhoihientai == 0) {

					anima[0].setBackgroundDrawable(null);
					animd[0].setBackgroundDrawable(null);
					animc[0].setBackgroundDrawable(null);
					animb[0].setBackgroundDrawable(null);

					if (dachon != ketqua) {
						if (dachon == 1) {
							anima[0].setBackgroundResource(R.drawable.doo);
							Log.d("_isFirstImagea0", "" + isFirstImagea[0]);
							if (isFirstImagea[0]) {

								applyRotation(0, 0, 90, 0, 0);
								isFirstImagea[0] = false;
							}
						} else if (dachon == 2) {
							animb[0].setBackgroundResource(R.drawable.doo);
							Log.d("_isFirstImageb0", "" + isFirstImageb[0]);
							if (isFirstImageb[0]) {

								applyRotation(0, 0, 90, 1, 0);
								isFirstImageb[0] = false;
							}
						} else if (dachon == 3) {
							animc[0].setBackgroundResource(R.drawable.doo);
							Log.d("_isFirstImagec0", "" + isFirstImagec[0]);
							if (isFirstImagec[0]) {

								applyRotation(0, 0, 90, 2, 0);
								isFirstImagec[0] = false;
							}

						} else if (dachon == 4) {
							animd[0].setBackgroundResource(R.drawable.doo);
							Log.d("_isFirstImaged0", "" + isFirstImaged[0]);
							if (isFirstImaged[0]) {

								applyRotation(0, 0, 90, 3, 0);
								isFirstImaged[0] = false;
							}
						}
					}

					if (ketqua == 1) {
						anima[0].setBackgroundResource(R.drawable.xanh);
						Log.d("isFirstImagea0", "" + isFirstImagea[0]);
						if (isFirstImagea[0]) {

							applyRotation(0, 0, 90, 0, 0);
							isFirstImagea[0] = !isFirstImagea[0];
						}
					} else if (ketqua == 2) {
						animb[0].setBackgroundResource(R.drawable.xanh);
						Log.d("isFirstImageb0", "" + isFirstImageb[0]);
						if (isFirstImageb[0]) {

							applyRotation(0, 0, 90, 1, 0);
							isFirstImageb[0] = !isFirstImageb[0];
						}
					} else if (ketqua == 3) {
						animc[0].setBackgroundResource(R.drawable.xanh);
						Log.d("isFirstImagec0", "" + isFirstImagec[0]);
						if (isFirstImagec[0]) {

							applyRotation(0, 0, 90, 2, 0);
							isFirstImagec[0] = !isFirstImagec[0];
						}
					} else {
						animd[0].setBackgroundResource(R.drawable.xanh);
						Log.d("isFirstImaged0", "" + isFirstImaged[0]);
						if (isFirstImaged[0]) {

							applyRotation(0, 0, 90, 3, 0);
							isFirstImaged[0] = !isFirstImaged[0];
						}
					}
				} else if (cauhoihientai == 404) {

					anima[2].setBackgroundDrawable(null);
					animd[2].setBackgroundDrawable(null);
					animc[2].setBackgroundDrawable(null);
					animb[2].setBackgroundDrawable(null);

					if (dachon != ketqua) {
						if (dachon == 1) {
							anima[2].setBackgroundResource(R.drawable.doo);
							if (isFirstImagea[2]) {

								applyRotation(2, 0, 90, 0, 0);
								isFirstImagea[2] = !isFirstImagea[2];
							}
						} else if (dachon == 2) {
							animb[2].setBackgroundResource(R.drawable.doo);
							if (isFirstImageb[2]) {

								applyRotation(2, 0, 90, 1, 0);
								isFirstImageb[2] = !isFirstImageb[2];
							}
						} else if (dachon == 3) {
							animc[2].setBackgroundResource(R.drawable.doo);
							if (isFirstImagec[2]) {

								applyRotation(2, 0, 90, 2, 0);
								isFirstImagec[2] = !isFirstImagec[2];
							}
						} else if (dachon == 4) {
							animd[2].setBackgroundResource(R.drawable.doo);
							if (isFirstImaged[2]) {

								applyRotation(2, 0, 90, 3, 0);
								isFirstImaged[2] = !isFirstImaged[2];
							}
						}
					}

					if (ketqua == 1) {
						anima[2].setBackgroundResource(R.drawable.xanh);
						if (isFirstImagea[2]) {

							applyRotation(2, 0, 90, 0, 0);
							isFirstImagea[2] = !isFirstImagea[2];
						}
					} else if (ketqua == 2) {
						animb[2].setBackgroundResource(R.drawable.xanh);
						if (isFirstImageb[2]) {

							applyRotation(2, 0, 90, 1, 0);
							isFirstImageb[2] = !isFirstImageb[2];
						}
					} else if (ketqua == 3) {
						animc[2].setBackgroundResource(R.drawable.xanh);
						if (isFirstImagec[2]) {

							applyRotation(2, 0, 90, 2, 0);
							isFirstImagec[2] = !isFirstImagec[2];
						}
					} else {
						animd[2].setBackgroundResource(R.drawable.xanh);
						if (isFirstImaged[2]) {

							applyRotation(2, 0, 90, 3, 0);
							isFirstImaged[2] = !isFirstImaged[2];
						}
					}

				} else {
					Log.d("xu ly cau hoi hien tai", "1");
					anima[1].setBackgroundDrawable(null);
					animd[1].setBackgroundDrawable(null);
					animc[1].setBackgroundDrawable(null);
					animb[1].setBackgroundDrawable(null);

					if (dachon != ketqua) {
						if (dachon == 1) {
							anima[1].setBackgroundResource(R.drawable.doo);
							if (isFirstImagea[1]) {

								applyRotation(1, 0, 90, 0, 0);
								isFirstImagea[1] = !isFirstImagea[1];
							}
						} else if (dachon == 2) {
							animb[1].setBackgroundResource(R.drawable.doo);
							if (isFirstImageb[1]) {

								applyRotation(1, 0, 90, 1, 0);
								isFirstImageb[1] = !isFirstImageb[1];
							}
						} else if (dachon == 3) {
							animc[1].setBackgroundResource(R.drawable.doo);
							if (isFirstImagec[1]) {

								applyRotation(1, 0, 90, 2, 0);
								isFirstImagec[1] = !isFirstImagec[1];
							}
						} else if (dachon == 4) {
							animd[1].setBackgroundResource(R.drawable.doo);
							if (isFirstImaged[1]) {

								applyRotation(1, 0, 90, 3, 0);
								isFirstImaged[1] = !isFirstImaged[1];
							}
						}
					}

					Log.d("isfirstimagea", "" + isFirstImagea[1]);
					Log.d("isfirstimageb", "" + isFirstImageb[1]);
					Log.d("isfirstimagec", "" + isFirstImagec[1]);
					Log.d("isfirstimaged", "" + isFirstImaged[1]);

					Log.d("ketqua", "" + ketqua);
					if (ketqua == 1) {
						anima[1].setBackgroundResource(R.drawable.xanh);
						if (isFirstImagea[1]) {

							applyRotation(1, 0, 90, 0, 0);
							isFirstImagea[1] = false;
						}
					} else if (ketqua == 2) {
						animb[1].setBackgroundResource(R.drawable.xanh);
						Log.d("is first image b", "is first image b");
						if (isFirstImageb[1]) {
							Log.d("ok dap an dung la 2", "dap an dung la 2");
							applyRotation(1, 0, 90, 1, 0);
							isFirstImageb[1] = false;
						}
					} else if (ketqua == 3) {
						animc[1].setBackgroundResource(R.drawable.xanh);
						if (isFirstImagec[1]) {

							applyRotation(1, 0, 90, 2, 0);
							isFirstImagec[1] = false;
						}
					} else {
						animd[1].setBackgroundResource(R.drawable.xanh);
						if (isFirstImaged[1]) {

							applyRotation(1, 0, 90, 3, 0);
							isFirstImaged[1] = false;
						}
					}
				}

			}
		});
	}

	boolean inbutton = false;

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

	boolean isaddview = false;

	@Override
	protected void onResume() {
		super.onResume();

		// new Laydulieu().execute((Void) null);
		if (!isaddview) {
			isaddview = true;
			dialog = new ProgressDialog(this);
			dialog.setMessage("Đang kết nối dữ liệu...");
			dialog.show();
			new Addview().execute((Void) null);
		}
	}

	protected class Addview extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {

			dialog.setMessage("Đang thêm dữ liệu...");
			new Laydulieu().execute((Void) null);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class Laydulieu extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {
			// /init view addview,scroll
			initview();
			// /add data
			getData1();// init data for start
			// gọi hàm hiển thị dữ liệu
			setView(); // // hiển thị dữ liệu lên list
			dialog.dismiss();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	ArrayList<ItemCauhoi> l = null;

	BodeAdapter adapter1;
	BodeAdapter adapter2;
	BodeAdapter adapter3;

	private void setView() {

		Log.d("Setview", "setview");
		Log.d("luat_chua_tl", "" + luat_chua_tl.size());
		Log.d("bienbao_chua_tl", "" + bienbao_chua_tl.size());
		Log.d("tinhuong_chua_tl", "" + tinhuong_chua_tl.size());

		header.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		if (currentcategory == 1) {
			if (luat_da_tl.isEmpty()) {
				
				header.setVisibility(LinearLayout.GONE);
				clear.setVisibility(Button.GONE);
				so_datraloi_luat = -1;
				luat_chua_tl.clear();
				Log.d("luat_list",""+Toancuc.luat_list.size());
				luat_chua_tl = copy(Toancuc.luat_list);
				l = luat_chua_tl;
				Log.d("l.size",""+l.size());
				adapter1 = new BodeAdapter(this,R.layout.itemviewdanhsachcauhoi, l);
				list.setAdapter(adapter1);
				
				return;
			} else {
				header.setVisibility(LinearLayout.VISIBLE);
				so_datraloi_luat = luat_da_tl.size();
				so_chuatraloi_luat = luat_chua_tl.size();
			}
			l = combine(luat_da_tl, luat_chua_tl);
			Log.d("Luat.size", "" + l.size());
			adapter1 = new BodeAdapter(this, R.layout.itemviewdanhsachcauhoi, l);
			list.setAdapter(adapter1);

			clear.setVisibility(Button.VISIBLE);

		} else if (currentcategory == 2) {
			if (bienbao_da_tl.isEmpty()) {
				header.setVisibility(LinearLayout.GONE);
				so_datraloi_bienbao = -1;
				bienbao_chua_tl.clear();
				bienbao_chua_tl = copy(Toancuc.bienbao_list);
				l = bienbao_chua_tl;
				adapter2 = new BodeAdapter(this,
						R.layout.itemviewdanhsachcauhoi, l);
				list2.setAdapter(adapter2);
				return;
			} else {
				header.setVisibility(LinearLayout.VISIBLE);
				so_datraloi_bienbao = bienbao_da_tl.size();
				so_chuatraloi_bienbao = bienbao_chua_tl.size();
			}
			l = combine(bienbao_da_tl, bienbao_chua_tl);
			Log.d("Bienbao.size", "" + l.size());
			adapter2 = new BodeAdapter(this, R.layout.itemviewdanhsachcauhoi, l);
			list2.setAdapter(adapter2);

		} else if (currentcategory == 3) {
			if (tinhuong_da_tl.isEmpty()) {
				header.setVisibility(LinearLayout.GONE);
				so_dataloi_tinhhuong = -1;
				tinhuong_chua_tl.clear();
				tinhuong_chua_tl = copy(Toancuc.tinhuong_list);
				l = tinhuong_chua_tl;
				adapter3 = new BodeAdapter(this,
						R.layout.itemviewdanhsachcauhoi, l);
				list3.setAdapter(adapter3);
				return;
			} else {
				header.setVisibility(LinearLayout.VISIBLE);
				so_dataloi_tinhhuong = tinhuong_da_tl.size();
				so_chuatraloi_tinhhuong = tinhuong_chua_tl.size();
			}
			l = combine(tinhuong_da_tl, tinhuong_chua_tl);
			Log.d("Tinhhuong.size", "" + l.size());
			adapter3 = new BodeAdapter(this, R.layout.itemviewdanhsachcauhoi, l);
			list3.setAdapter(adapter3);

		}

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
			item.datraloidapan = it.datraloidapan;

			l.add(item);
		}
		return l;
	}

	private ArrayList<ItemCauhoi> combine(
			ArrayList<ItemCauhoi> listLuatDatraloi,
			ArrayList<ItemCauhoi> listLuatChuatraloi) {
		ArrayList<ItemCauhoi> re = new ArrayList<ItemCauhoi>();
		for (int i = 0; i < listLuatChuatraloi.size(); i++) {
			ItemCauhoi it = listLuatChuatraloi.get(i);
			re.add(it);
		}

		ItemCauhoi item = new ItemCauhoi();
		item.cauhoi = "title";
		item.idcauhoi = "-1";
		re.add(item);
		for (int i = 0; i < listLuatDatraloi.size(); i++) {
			ItemCauhoi it = listLuatDatraloi.get(i);
			re.add(it);
		}

		return re;
	}

	private ArrayList<ItemCauhoi> luat_da_tl;
	private ArrayList<ItemCauhoi> bienbao_da_tl;
	private ArrayList<ItemCauhoi> tinhuong_da_tl;

	private ArrayList<ItemCauhoi> luat_chua_tl;
	private ArrayList<ItemCauhoi> bienbao_chua_tl;
	private ArrayList<ItemCauhoi> tinhuong_chua_tl;

	private ArrayList<ItemCauhoi> luat_list = null;
	private ArrayList<ItemCauhoi> bienbao_list;
	private ArrayList<ItemCauhoi> tinhuong_list;

	private ArrayList<ItemCauhoi> list_all_cauhoi;

	private void getData1() {

		list_all_cauhoi = Toancuc.list_cauhoi;

		luat_da_tl = Toancuc.luat_da_tl;
		luat_chua_tl = Toancuc.luat_chua_tl;
		bienbao_chua_tl = Toancuc.bienbao_chua_tl;

		bienbao_da_tl = Toancuc.bienbao_da_tl;
		tinhuong_chua_tl = Toancuc.tinhuong_chua_tl;
		tinhuong_da_tl = Toancuc.tinhuong_da_tl;

		luat_list = Toancuc.luat_list;
		bienbao_list = Toancuc.bienbao_list;
		tinhuong_list = Toancuc.tinhuong_list;

		Log.d("GetData Bo de:  ", list_all_cauhoi.size() + " mục ");

		Log.d("luatdatraloi", "" + luat_da_tl.size() + "mục");
		Log.d("bienbaodatraloi", "" + bienbao_da_tl.size() + "mục");
		Log.d("tinhhuongdatraloi", "" + tinhuong_da_tl.size() + "mục");
		// Log.d("luat_chua_traloi:", ""+luat_chua_tl.size()+"mục");
		// Log.d("bienbao_chua_tl:", ""+bienbao_chua_tl.size()+"mục");
		// Log.d("tinhuong_chua_tl:", ""+tinhuong_chua_tl.size()+"mục");

	}

	@Override
	protected void onDestroy() {
		
		Toancuc.list_cauhoi.clear();
		
		Toancuc.luat_list.clear();
		Toancuc.bienbao_list.clear();
		Toancuc.tinhuong_list.clear();
		
		Toancuc.luat_da_tl.clear();
		Toancuc.bienbao_da_tl.clear();
		Toancuc.tinhuong_da_tl.clear();
		
		Toancuc.luat_chua_tl.clear();
		Toancuc.bienbao_chua_tl.clear();
		Toancuc.tinhuong_chua_tl.clear();
		
		super.onDestroy();
	}

	int traloihientai[] = { -1, -1, -1 };
	HashMap<String, Integer> mapimage;

	private void resetBackgroundLayout() {
		for (int i = 1; i < 4; i++) {
			animc[i - 1].setBackgroundDrawable(null);
			animb[i - 1].setBackgroundDrawable(null);
			anima[i - 1].setBackgroundDrawable(null);
			animd[i - 1].setBackgroundDrawable(null);
		}
	}

	private void addView() {

		for (int i = 1; i < 4; i++) {

			Log.d("i", "" + i);

			LinearLayout view = (LinearLayout) li.inflate(
					R.layout.itemviewcauhoithi, null);
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

			lina[i - 1] = (LinearCustome) view.findViewById(R.id.lina);
			linb[i - 1] = (LinearCustome) view.findViewById(R.id.linb);
			linc[i - 1] = (LinearCustome) view.findViewById(R.id.linc);
			lind[i - 1] = (LinearCustome) view.findViewById(R.id.lind);

			// LinearLayout.LayoutParams param=
			// new
			// LinearLayout.LayoutParams(width,android.widget.LinearLayout.LayoutParams.FILL_PARENT);
			// param.setMargins(-width, 0, 0, 0);
			// lina[i - 1].setLayoutParams(param);
			// linb[i - 1].setLayoutParams(param);
			// linc[i - 1].setLayoutParams(param);
			// lind[i - 1].setLayoutParams(param);

			cuon[i - 1] = (ScrollView) view.findViewById(R.id.cuon);

			tcauhoi[i - 1] = (TextView) view.findViewById(R.id.tcauhoi);
			noidung[i - 1] = (TextView) view.findViewById(R.id.noidung);

			tla[i - 1] = (TextView) view.findViewById(R.id.tla);
			tlb[i - 1] = (TextView) view.findViewById(R.id.tlb);
			tlc[i - 1] = (TextView) view.findViewById(R.id.tlc);
			tld[i - 1] = (TextView) view.findViewById(R.id.tld);

		}

		for (int i = 1; i < 4; i++) {
			final int jj = i;
			lina[jj - 1].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (traloihientai[jj - 1] == 1)
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
					}
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

					String idcauhoi = list_all_cauhoi.get(cauhoihientai).idcauhoi;
					list_all_cauhoi.get(cauhoihientai).datraloidapan = "1";

					cauhoidatabase.openToWrite();
					cauhoidatabase.update(Integer.parseInt(idcauhoi), "1");
					updatelist(idcauhoi, "1");

					traloihientai[jj - 1] = 1;
				}
			});
			linb[jj - 1].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (traloihientai[jj - 1] == 2)
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
					}
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

					String idcauhoi = list_all_cauhoi.get(cauhoihientai).idcauhoi;
					list_all_cauhoi.get(cauhoihientai).datraloidapan = "2";

					cauhoidatabase.openToWrite();
					cauhoidatabase.update(Integer.parseInt(idcauhoi), "2");
					updatelist(idcauhoi, "2");
					traloihientai[jj - 1] = 2;
				}
			});
			linc[jj - 1].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					//
					if (traloihientai[jj - 1] == 3)
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
					}
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

					String idcauhoi = list_all_cauhoi.get(cauhoihientai).idcauhoi;
					list_all_cauhoi.get(cauhoihientai).datraloidapan = "3";

					cauhoidatabase.openToWrite();
					cauhoidatabase.update(Integer.parseInt(idcauhoi), "3");
					updatelist(idcauhoi, "3");
					traloihientai[jj - 1] = 3;
				}
			});
			lind[jj - 1].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (traloihientai[jj - 1] == 4)
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
					}
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

					String idcauhoi = list_all_cauhoi.get(cauhoihientai).idcauhoi;
					list_all_cauhoi.get(cauhoihientai).datraloidapan = "4";

					cauhoidatabase.openToWrite();
					cauhoidatabase.update(Integer.parseInt(idcauhoi), "4");
					updatelist(idcauhoi, "4");
					traloihientai[jj - 1] = 4;
				}
			});
		}

		viewlistcaudetail.addView(cuon_ngang);
	}

	protected void updatelist(String idcauhoi, String string) {
		if (currentcategory == 1) {
			int ii = -1;
			Log.d("luatchuatraloi", "" + luat_chua_tl.size());
			Log.d("luat_da_tl", "" + luat_da_tl.size());

			ItemCauhoi item = null;
			for (int i = 0; i < luat_chua_tl.size(); i++) {
				item = luat_chua_tl.get(i);
				if (item.idcauhoi.compareTo(idcauhoi) == 0) {
					item.datraloidapan = string;
					ii = i;

					break;
				}
			}
			if (ii != -1) {
				item = luat_chua_tl.get(ii);
				luat_chua_tl.remove(ii);
				luat_da_tl.add(item);
			} else {
				for (ItemCauhoi it : luat_da_tl) {
					if (it.idcauhoi.compareTo(idcauhoi) == 0) {
						it.datraloidapan = string;
					}
				}
			}
			adapter1.notifyDataSetChanged();
			Log.d("luatchuatraloi", "" + luat_chua_tl.size());
			Log.d("luat_da_tl", "" + luat_da_tl.size());

		} else if (currentcategory == 2) {
			int ii = -1;
			Log.d("bienbao_chua_tl", "" + bienbao_chua_tl.size());
			Log.d("bienbao_da_tl", "" + bienbao_da_tl.size());
			ItemCauhoi item = null;
			for (int i = 0; i < bienbao_chua_tl.size(); i++) {
				item = bienbao_chua_tl.get(i);
				if (item.idcauhoi.compareTo(idcauhoi) == 0) {
					item.datraloidapan = string;
					ii = i;
				}
			}
			if (ii != -1) {
				item = bienbao_chua_tl.get(ii);
				bienbao_chua_tl.remove(ii);
				bienbao_da_tl.add(item);
			} else {
				for (ItemCauhoi it : bienbao_da_tl) {
					if (it.idcauhoi.compareTo(idcauhoi) == 0) {
						it.datraloidapan = string;
					}
				}
			}
			adapter2.notifyDataSetChanged();
			Log.d("bienbao_chua_tl", "" + bienbao_chua_tl.size());
			Log.d("bienbao_da_tl", "" + bienbao_da_tl.size());
		} else {
			int ii = -1;
			Log.d("tinhuong_chua_tl", "" + tinhuong_chua_tl.size());
			Log.d("tinhuong_da_tl", "" + tinhuong_da_tl.size());
			ItemCauhoi item = null;
			for (int i = 0; i < tinhuong_chua_tl.size(); i++) {
				item = tinhuong_chua_tl.get(i);
				if (item.idcauhoi.compareTo(idcauhoi) == 0) {
					item.datraloidapan = string;
					ii = i;
				}
			}

			if (ii != -1) {
				item = tinhuong_chua_tl.get(ii);
				tinhuong_chua_tl.remove(ii);
				tinhuong_da_tl.add(item);
			} else {
				for (ItemCauhoi it : tinhuong_da_tl) {
					if (it.idcauhoi.compareTo(idcauhoi) == 0) {
						it.datraloidapan = string;
					}
				}
			}
			adapter3.notifyDataSetChanged();
			Log.d("tinhuong_chua_tl", "" + tinhuong_chua_tl.size());
			Log.d("tinhuong_da_tl", "" + tinhuong_da_tl.size());
		}

	}

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(200);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	int sodong = 0;
	//ArrayList<ItemCauhoi> list_cauhoi = new ArrayList<ItemCauhoi>();

	// / con fỉm ẽit
	private void showDialogConfirmExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(Html.fromHtml("Bạn muốn thoát chương trình?"))
				.setCancelable(false)
				.setPositiveButton("Thoát",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Bode.this.finish();
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

	@Override
	public void onBackPressed() {
		
		if (isdetail) {
			isdetail=false;
			back.setVisibility(Button.GONE);

			if (currentcategory == 1) {
				list.setVisibility(ListView.VISIBLE);
				list2.setVisibility(ListView.GONE);
				list3.setVisibility(ListView.GONE);
				viewlistcaudetail.setVisibility(LinearLayout.GONE);
				viewlistcau.setVisibility(LinearLayout.VISIBLE);

				// hide or visible button xoa da tra loi
				if (luat_da_tl.isEmpty()) {
					clear.setVisibility(Button.GONE);
				} else {
					clear.setVisibility(Button.VISIBLE);
				}
			} else if (currentcategory == 2) {
				list.setVisibility(ListView.GONE);
				list2.setVisibility(ListView.VISIBLE);
				list3.setVisibility(ListView.GONE);
				viewlistcaudetail.setVisibility(LinearLayout.GONE);
				viewlistcau.setVisibility(LinearLayout.VISIBLE);
				// hide or visible button xoa da tra loi
				if (bienbao_da_tl.isEmpty()) {
					clear.setVisibility(Button.GONE);
				} else {
					clear.setVisibility(Button.VISIBLE);
				}
			} else if (currentcategory == 3) {
				list.setVisibility(ListView.GONE);
				list2.setVisibility(ListView.GONE);
				list3.setVisibility(ListView.VISIBLE);
				viewlistcaudetail.setVisibility(LinearLayout.GONE);
				viewlistcau.setVisibility(LinearLayout.VISIBLE);
				// hide or visible button xoa da tra loi
				if (tinhuong_da_tl.isEmpty()) {
					clear.setVisibility(Button.GONE);
				} else {
					clear.setVisibility(Button.VISIBLE);
				}
			}

			barlist.setVisibility(LinearLayout.VISIBLE);
			bardetail.setVisibility(LinearLayout.GONE);
			setView();
			return;
		}
		
		if (isback) {

			back.setVisibility(Button.GONE);

			if (currentcategory == 1) {
				list.setVisibility(ListView.VISIBLE);
				list2.setVisibility(ListView.GONE);
				list3.setVisibility(ListView.GONE);
				viewlistcaudetail.setVisibility(LinearLayout.GONE);
				viewlistcau.setVisibility(LinearLayout.VISIBLE);

				// hide or visible button xoa da tra loi
				if (luat_da_tl.isEmpty()) {
					clear.setVisibility(Button.GONE);
				} else {
					clear.setVisibility(Button.VISIBLE);
				}
			} else if (currentcategory == 2) {
				list.setVisibility(ListView.GONE);
				list2.setVisibility(ListView.VISIBLE);
				list3.setVisibility(ListView.GONE);
				viewlistcaudetail.setVisibility(LinearLayout.GONE);
				viewlistcau.setVisibility(LinearLayout.VISIBLE);
				// hide or visible button xoa da tra loi
				if (bienbao_da_tl.isEmpty()) {
					clear.setVisibility(Button.GONE);
				} else {
					clear.setVisibility(Button.VISIBLE);
				}
			} else if (currentcategory == 3) {
				list.setVisibility(ListView.GONE);
				list2.setVisibility(ListView.GONE);
				list3.setVisibility(ListView.VISIBLE);
				viewlistcaudetail.setVisibility(LinearLayout.GONE);
				viewlistcau.setVisibility(LinearLayout.VISIBLE);
				// hide or visible button xoa da tra loi
				if (tinhuong_da_tl.isEmpty()) {
					clear.setVisibility(Button.GONE);
				} else {
					clear.setVisibility(Button.VISIBLE);
				}
			}

			barlist.setVisibility(LinearLayout.VISIBLE);
			bardetail.setVisibility(LinearLayout.GONE);
			setView();

			isback = false;
		} else
			showDialogConfirmExit();
		return;
	}

	private boolean isFirstImagea[] = { true, true, true };
	private boolean isFirstImageb[] = { true, true, true };
	private boolean isFirstImagec[] = { true, true, true };
	private boolean isFirstImaged[] = { true, true, true };

	// // rotate
	private void applyRotation(int sttview012, float start, float end,
			int sttcauabcd, int time) {

		Log.d("applyrotation", "" + sttcauabcd);
		final float centerX = lineara[1].getWidth() / 2.0f;
		final float centerY = lineara[1].getHeight() / 2.0f;

		LinearLayout l1 = null;
		LinearLayout l2 = null;

		// if (time == 0) {
		// boolean is=false;
		// switch (sttcauabcd) {
		// case 0:
		// l1 = lineara[sttview012];
		// l2 = linearaa[sttview012];
		// is=isFirstImagea[sttview012];
		// break;
		// case 1:
		// l1 = linearb[sttview012];
		// l2 = linearbb[sttview012];
		// is=isFirstImageb[sttview012];
		// break;
		// case 2:
		// l1 = linearc[sttview012];
		// l2 = linearcc[sttview012];
		// is=isFirstImagec[sttview012];
		// break;
		// case 3:
		// l1 = lineard[sttview012];
		// l2 = lineardd[sttview012];
		// is=isFirstImaged[sttview012];
		// break;
		// default:
		// break;
		// }
		//
		// if(!is)
		// { l1.setVisibility(LinearLayout.VISIBLE);
		// l2.setVisibility(LinearLayout.GONE);
		// }else{
		// l1.setVisibility(LinearLayout.GONE);
		// l2.setVisibility(LinearLayout.VISIBLE);
		// }
		//
		// return;
		// }

		final Flip3dAnimation rotation = new Flip3dAnimation(start, end,
				centerX, centerY);
		rotation.setDuration(time);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());

		set(sttview012, rotation, sttcauabcd, time);
	}

	private void set(int sttview012, Flip3dAnimation rotation, int stt, int time) {
		LinearLayout l1 = null;
		LinearLayout l2 = null;

		if (stt == 0) {
			l1 = lineara[sttview012];
			l2 = linearaa[sttview012];

			rotation.setAnimationListener(new DisplayNextView(
					isFirstImagea[sttview012], l1, l2, time));
			if (isFirstImagea[sttview012]) {
				l1.startAnimation(rotation);
			} else {
				l2.startAnimation(rotation);
			}
		} else if (stt == 1) {
			l1 = linearb[sttview012];
			l2 = linearbb[sttview012];
			rotation.setAnimationListener(new DisplayNextView(
					isFirstImageb[sttview012], l1, l2, time));
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
					isFirstImagec[sttview012], l1, l2, time));
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
					isFirstImaged[sttview012], l1, l2, time));
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
		private LinearLayout image1;
		private LinearLayout image2;
		int time = 0;

		public SwapViews(boolean isFirstView, LinearLayout image1,
				LinearLayout image2, int time) {
			mIsFirstView = isFirstView;
			this.image1 = image1;
			this.image2 = image2;
			image1.setBackgroundResource(R.drawable.question_title);
			image2.setBackgroundResource(R.drawable.window);
			this.time = time;
		}

		public void run() {

			final float centerX = image1.getWidth() / 2.0f;
			final float centerY = image1.getHeight() / 2.0f;
			Flip3dAnimation rotation;

			if (mIsFirstView) {
				image1.setVisibility(LinearLayout.GONE);
				image2.setVisibility(LinearLayout.VISIBLE);
				image2.requestFocus();

				rotation = new Flip3dAnimation(-90, 0, centerX, centerY);
			} else {
				image2.setVisibility(LinearLayout.GONE);
				image1.setVisibility(LinearLayout.VISIBLE);
				image1.requestFocus();

				rotation = new Flip3dAnimation(90, 0, centerX, centerY);
			}

			rotation.setDuration(this.time);
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
		private LinearLayout image1;
		private LinearLayout image2;
		private int time = 0;

		public DisplayNextView(boolean currentView, LinearLayout image1,
				LinearLayout image2, int time) {
			this.mCurrentView = currentView;
			this.image1 = image1;
			this.image2 = image2;
			this.time = time;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			image1.post(new SwapViews(mCurrentView, image1, image2, this.time));
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	private void setChange_1() {// / quay het len

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