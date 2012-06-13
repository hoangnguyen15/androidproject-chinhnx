package com.krazevina.thioto;

import java.util.ArrayList;

import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemCauhoi;
import com.krazevina.thioto.sqlite.CauhoiDB;
import com.krazevina.thioto.webservice.CallWebService;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class Tab extends TabActivity implements OnTabChangeListener {

	public static TabHost tabHost;
	public static TabWidget tabwidget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		setTabs();
		tabHost.setOnTabChangedListener(this);
		tabwidget = getTabWidget();

		call = new CallWebService(this);
		cauhoiDB = new CauhoiDB(this);
		dateutils = new DateUtils();
	}

	CallWebService call;
	CauhoiDB cauhoiDB;
	DateUtils dateutils;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	private void setTabs() {
		tabHost = getTabHost();
		this.addTab(0, R.drawable.luat_icon_1, R.string.luat, Luat.class, 1);
		this.addTab(1, R.drawable.bien_bao_icon_1, R.string.bienbao,Bienbao.class, 2);
		this.addTab(2, R.drawable.bai_thi_icon_1, R.string.baithi, Baithi.class,3);
		this.addTab(3, R.drawable.bo_de_icon_1, R.string.bode, Bode.class, 4);
		this.addTab(4, R.drawable.ket_qua_icon_1, R.string.ketqua, Ketqua.class,5);
		this.addTab(5, R.drawable.ket_qua_icon_1, R.string.ketqua,LuatDetail.class, 6);
		this.addTab(6, R.drawable.ket_qua_icon_1, R.string.ketqua,BaithiDanhsach.class, 7);
		this.addTab(7, R.drawable.ket_qua_icon_1, R.string.ketqua,Abouts.class, 8);
		this.addTab(8, R.drawable.ket_qua_icon_1, R.string.ketqua,Huongdan.class, 9);
		this.addTab(9, R.drawable.ket_qua_icon_1, R.string.ketqua,Meothi.class, 10);
		tabHost.setCurrentTab(Toancuc.tabselected);

	}

	public static int currentTab = 0;
	int tabchoxuly = -1;
	ArrayList<View> listTab = new ArrayList<View>();

	private void addTab(final int labelId, int drawableImage,
			int stringTextTab, Class<?> a, int count) {

		Intent intent = new Intent(this, a);
		TabHost.TabSpec tabspec = tabHost.newTabSpec("" + labelId);

		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tabitem,
				getTabWidget(), false);
		((TextView)tabIndicator.findViewById(R.id.text111)).setText(stringTextTab);
		
		LayoutParams params = tabIndicator.getLayoutParams();
		params.width = (getWindowManager().getDefaultDisplay().getWidth())/5;
		tabIndicator.setLayoutParams(params);
		
		tabIndicator.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
			

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (labelId != 2 && Toancuc.dangthi) {
						tabchoxuly = labelId;
						showDialogConfirmSave();
						new confirm_end().execute((Void) null);
						return true;
					}
					tabHost.setCurrentTab(labelId);
				}
				return true;
			}
		});

		ImageView image = (ImageView) tabIndicator.findViewById(R.id.img_indicator);
		image.setImageDrawable(getResources().getDrawable(drawableImage));

		LinearLayout lin = (LinearLayout) tabIndicator.findViewById(R.id.linearlayout);
		listTab.add(lin);
		if (labelId == Toancuc.tabselected)
			lin.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.highlight_icon_1));

		tabspec.setIndicator(tabIndicator);
		tabspec.setContent(intent);

		tabHost.addTab(tabspec);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		Toancuc.dangthi = false;
		cauhoiDB.close();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onBackPressed() {

		return;
	}

	@Override
	public void onTabChanged(String tabId) {

		if(tabId.compareTo("7")==0||tabId.compareTo("8")==0||tabId.compareTo("9")==0)
			return;
		currentTab = Integer.parseInt(tabId);
//		if (currentTab==5||currentTab == 6 || currentTab == 7 || currentTab == 8)
//			return;

		for (View v : listTab) {
			LinearLayout l = (LinearLayout) v.findViewById(R.id.linearlayout);
			l.setBackgroundDrawable(null);
		}
		View v=null;
		if(currentTab==5) 
		 v= listTab.get(0);
		else if(currentTab==6) 
			 v= listTab.get(2);
		else  v= listTab.get(currentTab);
		LinearLayout l = (LinearLayout) v.findViewById(R.id.linearlayout);
		l.setBackgroundDrawable(getResources().getDrawable(R.drawable.highlight_icon_1));

	}

	// ///////////////XULYTABCHANGE
	protected class confirm_end extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			if (dong_y) {// nếu đồng ý kết thúc
				Toancuc.dangthi = false; // dang thi =false
				Toancuc.dongykethuc = true; //
				Toancuc.thoigianlambai = 20 * 60 - Baithi.numsecondremain;// tính
																			// thời
																			// gian
				themKetqua();// thêm kết quả thi vào cơ sở dữ liệu
				Baithi.danglambai = false;
				Tab.tabHost.setCurrentTab(tabchoxuly);
			}
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
	boolean dashowalert = false;

	private void showDialogConfirmSave() {

		dashowalert = true;
		choice = false;
		dong_y = false;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(Html.fromHtml("Bạn muốn kết thúc bài thi?"))
				.setCancelable(false)
				.setPositiveButton("Kết thúc",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								choice = true;
								dong_y = true;
								dashowalert = false;
								Toancuc.dangthi = false;
							}
						})
				.setNegativeButton("Hủy bỏ",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								choice = true;
								dashowalert = false;
							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	String timerequest = null;

	Timer timer = new Timer();

	private void showDialogSendSMS() {

		dashowalert = true;
		choice = false;
		dong_y = false;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				Html.fromHtml("Bạn cần gửi tin SMS để xem được mục này!"))
				.setCancelable(false)
				.setPositiveButton("Soạn tin",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent sendIntent = new Intent(
										Intent.ACTION_VIEW);

								timerequest = "KV G"
										+ dateutils.getDetailTimeNow();
								cauhoiDB.openToWrite();
								cauhoiDB.addItem_sms(timerequest, "0");

								if (timer_running) {
									timer.setTotalTime(120);
								} else {
									timer_running = true;
									timer = new Timer();
									timer.execute((Void) null);
								}
//								Log.d("getdetailtimenow", "" + timerequest);
								sendIntent.putExtra("address", "8732");
								sendIntent.putExtra("sms_body", timerequest);
								sendIntent.setType("vnd.android-dir/mms-sms");
								startActivity(sendIntent);

							}
						})
				.setNegativeButton("Hủy bỏ",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	// / tính toán kết quả
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.huongdan) {
			Toast.makeText(Tab.this, "Hướng dẫn sử dụng", 0).show();
//			Intent intent = new Intent(Tab.this, Huongdan.class);
//			startActivity(intent);
			Tab.tabHost.setCurrentTab(8);
			Tab.tabwidget.setVisibility(TabWidget.GONE);
			return true;
		} else if (item.getItemId() == R.id.thongtin) {
			Toast.makeText(Tab.this, "Thông tin sản phẩm", 0).show();

			////Intent intent = new Intent(Tab.this, Abouts.class);
			///startActivity(intent);
			Tab.tabHost.setCurrentTab(7);
			Tab.tabwidget.setVisibility(TabWidget.GONE);
			return true;
		} else if (item.getItemId() == R.id.meothi) {
//			Tab.tabHost.setCurrentTab(9);
//			Tab.tabwidget.setVisibility(TabWidget.GONE);
			if (Toancuc.duocxemmeothi) {
				Intent intent = new Intent(Tab.this, Meothi.class);
				startActivity(intent);
				Tab.tabHost.setCurrentTab(9);
				Tab.tabwidget.setVisibility(TabWidget.GONE);
				Log.d("toancuc","toancuc");
			} else if (CallWebService.checknet(Tab.this)) {
				showDialogSendSMS();
			} else {
				Toast.makeText(Tab.this, "Bạn cần kết nối mạng Internet", 0).show();
			}

		}
		return true;
	}

	boolean timer_running = false;

	int x = 0;

	protected class Timer extends AsyncTask<Void, Void, Void> {

		boolean issent = false;
		int total = 120;

		public void setTotalTime(int total) {
			this.total = total;
		}

		@Override
		protected void onPostExecute(Void v) {
			timer_running = false;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			timer_running = true;
			x = 0;
			while (!issent && total > 0) {
				total--;
//				Log.d("total=", "" + total);
//				Log.d("x", "" + x);
				x++;
				try {
					Thread.sleep(500);
				
					if (call.getUrlAlbum(timerequest).compareTo("true") == 0) {
						cauhoiDB.openToWrite();
						cauhoiDB.update_sms(timerequest, "1");

						Toancuc.duocxemmeothi = true;
						issent = true;

					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			return null;
		}
	}


	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event) {
//		Log.d("keycode", "" + event.getKeyCode());
//		Log.d("current tab", "" + currentTab);
		
		if(keyCode!=21&&keyCode!=22) return super.onKeyDown(keyCode, event);
		
		if ((keyCode == 21||keyCode == 22) && Toancuc.dangthi) {
			if(keyCode==21)
			        tabchoxuly = 1;
			else 
			    	tabchoxuly =3;
			showDialogConfirmSave();
			new confirm_end().execute((Void) null);
			return true;
		}

		if (event.getKeyCode() == 21) {// prev
			switch (currentTab) {
			case 0:
				Tab.tabHost.setCurrentTab(4);
				break;
			case 1:
				Tab.tabHost.setCurrentTab(0);
				break;
			case 2:
				Tab.tabHost.setCurrentTab(1);
				break;
			case 3:
				Tab.tabHost.setCurrentTab(2);
				break;
			case 4:
				Tab.tabHost.setCurrentTab(3);
				break;
			case 5:
				Tab.tabHost.setCurrentTab(4);
				break;
			case 6:
				Tab.tabHost.setCurrentTab(1);
				break;
				
			default:
				break;
			}
			
			return true;
			
		} else if (event.getKeyCode() == 22) {
			switch (currentTab) {
			case 0:
				Tab.tabHost.setCurrentTab(1);
				break;
			case 1:
				Tab.tabHost.setCurrentTab(2);
				break;
			case 2:
				Tab.tabHost.setCurrentTab(3);
				break;
			case 3:
				Tab.tabHost.setCurrentTab(4);
				break;
			case 4:
				Tab.tabHost.setCurrentTab(0);
				break;
			case 5:
				Tab.tabHost.setCurrentTab(1);
				break;
			case 6:
				Tab.tabHost.setCurrentTab(3);
				break;
			default:
				break;
			}
			return true;
		}

		
		return super.dispatchKeyEvent(event);
	}
}
