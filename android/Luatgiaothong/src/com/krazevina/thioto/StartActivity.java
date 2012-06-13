package com.krazevina.thioto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;


import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemBienbao;
import com.krazevina.thioto.objects.ItemCauhoi;
import com.krazevina.thioto.sqlite.CauhoiDB;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class StartActivity extends Activity {
	private LinearLayout baithi;
	private LinearLayout bienbao;
	private LinearLayout bode;
	private LinearLayout luat;
	private LinearLayout ketqua;

	private LinearLayout progress;
	private LinearLayout buttons;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startview);
		baithi = (LinearLayout) findViewById(R.id.baithi);
		bienbao = (LinearLayout) findViewById(R.id.bienbao);
		bode = (LinearLayout) findViewById(R.id.bode);
		luat = (LinearLayout) findViewById(R.id.luat);
		ketqua = (LinearLayout) findViewById(R.id.ketqua);
		
		progress=(LinearLayout)findViewById(R.id.progress);
		buttons=(LinearLayout)findViewById(R.id.buttons);
		
		setClick();
		cauhoiDB = new CauhoiDB(this);
		DB_PATH = "/data/data/" + this.getApplicationContext().getPackageName()
				+ "/databases/";
	}

	private void setClick() {
		
		baithi.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	baithi.setBackgroundResource(R.drawable.main_option_highlight);
                }else  if(event.getAction()==MotionEvent.ACTION_UP){
                	baithi.setBackgroundResource(R.drawable.main_option);
                }
				return false;
			}
		});

		baithi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toancuc.tabselected=2;
				 StartActivity.this.finish();
				new Runprogram().execute((Void)null);
				
			}
		});
		bode.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	bode.setBackgroundResource(R.drawable.main_option_highlight);
                }else  if(event.getAction()==MotionEvent.ACTION_UP){
                	bode.setBackgroundResource(R.drawable.main_option);
                }
				return false;
			}
		});

		bode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toancuc.tabselected=3;
				 StartActivity.this.finish();
				new Runprogram().execute((Void)null);
				
			}
		});
		
		bienbao.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	bienbao.setBackgroundResource(R.drawable.main_option_highlight);
                }else  if(event.getAction()==MotionEvent.ACTION_UP){
                	bienbao.setBackgroundResource(R.drawable.main_option);
                }
				return false;
			}
		});

		bienbao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toancuc.tabselected=1;
				 StartActivity.this.finish();
				new Runprogram().execute((Void)null);
				
			}
		});
		
		luat.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	luat.setBackgroundResource(R.drawable.main_option_highlight);
                }else  if(event.getAction()==MotionEvent.ACTION_UP){
                	luat.setBackgroundResource(R.drawable.main_option);
                }
				return false;
			}
		});

		luat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toancuc.tabselected=0;
				 StartActivity.this.finish();
				new Runprogram().execute((Void)null);
				
			}
		});
		ketqua.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	ketqua.setBackgroundResource(R.drawable.main_option_highlight);
                }else  if(event.getAction()==MotionEvent.ACTION_UP){
                	ketqua.setBackgroundResource(R.drawable.main_option);
                }
				return false;
			}
		});

		ketqua.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toancuc.tabselected=4;
				 StartActivity.this.finish();
				new Runprogram().execute((Void)null);
				
			}
		});
	}
protected class Runprogram extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPostExecute(Void v) {
			
			 Intent i=new Intent(StartActivity.this,Tab.class);
			 startActivity(i);
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			
		
			
			return null;
		}
	}
	
	
	
	private static String DB_PATH;
	private static String DB_NAME = "Cauhoi";

	private void copydatabase() {

		try {
			InputStream myinput = this.getAssets().open(DB_NAME);
			String outfilename = DB_PATH + DB_NAME;
			File file = new File(outfilename);
			if (!file.exists())
				file.createNewFile();
			OutputStream myoutput = new FileOutputStream(outfilename);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myinput.read(buffer)) > 0) {
				myoutput.write(buffer, 0, length);
			}

			myoutput.flush();
			myoutput.close();
			myinput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	boolean load = false;

	@Override
	protected void onResume() {
		if (!load) {
			load = true;
			new LoadingData().execute((Void) null);
		}

		super.onResume();
	}

	protected class LoadingData extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {
			
			progress.setVisibility(LinearLayout.GONE);
			buttons.setVisibility(LinearLayout.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			getDataBienbao();
			getDataCauhoithi1();
			getDataUseMeothi();
		
			Toancuc.viewing_detail = false;
			cauhoiDB.close();
			return null;
		}
	}

	private void getDataBienbao() {

		Toancuc.bb_biencams.clear();
		Toancuc.bb_nguyhiems.clear();
		Toancuc.bb_hieulenhs.clear();
		Toancuc.bb_chidans.clear();
		Toancuc.bb_bienphus.clear();
		Toancuc.bb_vachkes.clear();

		try {
			read();
		} catch (Exception e) {

			e.printStackTrace();
		}

		for (int i = 0; i < Toancuc.list_item_bienbao.size(); i++) {
			ItemBienbao item = Toancuc.list_item_bienbao.get(i);
			switch (Integer.parseInt(item.phan_loai)) {
			case 1:
				Toancuc.bb_biencams.add(item);
				break;
			case 2:
				Toancuc.bb_nguyhiems.add(item);
				break;
			case 3:
				Toancuc.bb_hieulenhs.add(item);
				break;
			case 4:
				Toancuc.bb_chidans.add(item);
				break;
			case 5:
				Toancuc.bb_bienphus.add(item);
				break;
			case 6:
				Toancuc.bb_vachkes.add(item);
				break;
			default:
				Log.d("Eror", "Fatal");
			}

		}
	}

	public void getDataUseMeothi() {
		cauhoiDB.openToWrite();
		cauhoiDB.deleteAll_sms();
		// cauhoiDB.addItem_sms("vuvancuong", "0");
		cauhoiDB.openToRead();
		Cursor c = cauhoiDB.getAllItems_sms();
		Log.d("c.getcount", "" + c.getCount());
		startManagingCursor(c);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			String keyRequest = c.getString(c
					.getColumnIndex(CauhoiDB.KEYREQUEST));
			String Status = c.getString(c.getColumnIndex(CauhoiDB.STATUS));
			Log.d("keyrequest", "" + keyRequest);
			Log.d("status", "" + Status);

			if (Status.toLowerCase().compareTo("1") == 0) {
				Toancuc.duocxemmeothi = true;
				Log.d("ban duoc su dung meo thi", "ban duoc su dung meo thi");
			}
			c.moveToNext();
		}

	}

	

	int ii=1;
	public void read() throws Exception {
		try {
			InputStream in = getResources().openRawResource(R.raw.dulieubienbao);
			InputStreamReader inputstream = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(inputstream);

			String s;

			
			itembienbao item;
			ItemBienbao itembb;
			int x = 0;
			while ((s = reader.readLine()) != null) {
				item = new itembienbao();
				s = s.trim();
				x = 0;

				while (s.indexOf("\t") != -1) {
					x++;

					if (x == 1) {
						item.id = "" + s.substring(0, s.indexOf("\t"));
					} else if (x == 3) {
						item.ten = s.substring(0, s.indexOf("\t"));
					} else if (x == 5) {
						item.noidung = s.substring(0, s.indexOf("\t"));
					} else if (x == 7) {
						item.tenanh = "" + s.substring(0, s.indexOf("\t"));

						//Log.d("temamj",""+item.tenanh);
					}
					s = s.trim().substring(s.indexOf("\t"));
				}

				item.phanloai = Integer.parseInt(s);

			
				itembb = new ItemBienbao(ii + "", item.ten, item.noidung,
						item.tenanh, item.phanloai + "");
				// Log.d("ii=",""+ii);
				if (item.tenanh.compareTo("0") == 0)
					itembb.link_anh = item.id + ".png";
				Toancuc.list_item_bienbao.add(itembb);

		ii++;
			}
			Log.d("at startactivity", "" + Toancuc.list_item_bienbao.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// /printdata();
	}

	class itembienbao {
		String id = null;
		String ten = null;
		String noidung = null;
		String tenanh = null;
		int phanloai = -1;
	}

	@Override
	protected void onDestroy() {

		cauhoiDB.close();

		super.onDestroy();
	}

	// /////////////////////************** load dữ liệu câu hỏi thi
	// get Data

	CauhoiDB cauhoiDB;

	private void getDataCauhoithi1() {

		Toancuc.list_cauhoi.clear();
		Toancuc.list1.clear();
		Toancuc.list2.clear();
		Toancuc.list3.clear();
		Toancuc.list4.clear();
		Toancuc.list5.clear();
		Toancuc.list6.clear();
		Toancuc.list7.clear();
		Toancuc.list8.clear();

		ItemCauhoi item;
		cauhoiDB.openToRead();
		Cursor c = cauhoiDB.getAllItems();

		if (c.getCount() == 0) {// chua co du lieu
			// getData1();// read data text and add sqlite

			copydatabase();
			cauhoiDB.openToRead();
			c = cauhoiDB.getAllItems();

		}
		// else

		{ // da co du lieu

			startManagingCursor(c);
			c.moveToFirst();
			while (!c.isAfterLast()) {

				// int idcauhoi = c.getInt(c.getColumnIndex(CauhoiDB.KEY));
				String cauhoi = c.getString(c.getColumnIndex(CauhoiDB.cCAUHOI));
				String tla = c.getString(c.getColumnIndex(CauhoiDB.cTLA));
				String tlb = c.getString(c.getColumnIndex(CauhoiDB.cTLB));
				String tlc = c.getString(c.getColumnIndex(CauhoiDB.cTLC));
				String tld = c.getString(c.getColumnIndex(CauhoiDB.cTLD));
				String ketqua = c.getString(c.getColumnIndex(CauhoiDB.cKETQUA));
				String image = c.getString(c.getColumnIndex(CauhoiDB.cIMAGE));
				String phanloai = c.getString(c
						.getColumnIndex(CauhoiDB.cPHANLOAI));
				String thi = c.getString(c.getColumnIndex(CauhoiDB.cTHI));
				String datraloidapdan = c.getString(c
						.getColumnIndex(CauhoiDB.cDatraloidapan));
				item = new ItemCauhoi();

				item.idcauhoi = i++ + "";
				item.cauhoi = cauhoi;
				item.traloia = tla;
				item.traloib = tlb;
				item.traloic = tlc;
				item.traloid = tld;
				item.ketqua = ketqua;
				item.image = image;
				item.phanloai = phanloai;
				item.thi = thi;
				item.datraloidapan = datraloidapdan;

				int datldapan = Integer.parseInt(item.datraloidapan);
				int phanLoai = Integer.parseInt(item.phanloai);

				if (phanLoai == 1) {// luật
					Toancuc.luat_list.add(item);
					if (datldapan == -1)
						Toancuc.luat_chua_tl.add(item);
					else
						Toancuc.luat_da_tl.add(item);
				} else if (phanLoai == 2) {// biển báo
					Toancuc.bienbao_list.add(item);
					if (datldapan == -1)
						Toancuc.bienbao_chua_tl.add(item);
					else
						Toancuc.bienbao_da_tl.add(item);
				} else if (phanLoai == 3) {// tình huống
					Toancuc.tinhuong_list.add(item);
					if (datldapan == -1)
						Toancuc.tinhuong_chua_tl.add(item);
					else
						Toancuc.tinhuong_da_tl.add(item);
				}

				Toancuc.list_cauhoi.add(item);

				c.moveToNext();
				themdulieuvaolist(item);
				Log.d("idcauhoi:", "" + item.idcauhoi);
				Log.d("phanloai", "" + phanloai);
				Log.d("thi", "" + thi);
				Log.d("datraloidapan", "" + datraloidapdan);

			}
		}
		Log.d("Tat ca co ", Toancuc.list_cauhoi.size() + " mục ");
	}

	private void themdulieuvaolist(ItemCauhoi item) {
		int i = Integer.parseInt(item.thi);
		switch (i) {
		case 1:
			Toancuc.list1.add(item);
			break;
		case 2:
			Toancuc.list2.add(item);
			break;
		case 3:
			Toancuc.list3.add(item);
			break;
		case 4:
			Toancuc.list4.add(item);
			break;
		case 5:
			Toancuc.list5.add(item);
			break;
		case 6:
			Toancuc.list6.add(item);
			break;
		case 7:
			Toancuc.list7.add(item);
			break;
		case 8:
			Toancuc.list8.add(item);
			break;
		}
	}

	private void getData1() {
		Toancuc.list_cauhoi.clear();
		InputStream is = getResources().openRawResource(R.raw.dulieubaithi);
		InputStreamReader inputStreamReader = new InputStreamReader(is);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = "";
		try {
			ArrayList<String> list = new ArrayList<String>();
			while ((str = bufferedReader.readLine()) != null) {

				list.clear();
				str = str.trim();
				while (str.indexOf("\t") != -1) {

					list.add(str.substring(0, str.indexOf("\t")));
					str = str.substring(str.indexOf("\t")).trim();
				}
				list.add(str);
				parsedata(list);
				sodong++;

			}
			Log.d("so dong ", "" + sodong);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	int sodong = 0;
	ArrayList<ItemCauhoi> list_cauhoi = new ArrayList<ItemCauhoi>();
	int i = 1;

	private void parsedata(ArrayList<String> list) {

		ItemCauhoi itemcauhoi = null;
		if (list.size() == 10) { // day du
			// Log.d("so cot ", "10");
			itemcauhoi = new ItemCauhoi();

			itemcauhoi.idcauhoi = i + "";
			itemcauhoi.cauhoi = list.get(1);
			itemcauhoi.traloia = list.get(2);
			itemcauhoi.traloib = list.get(3);
			if (list.get(4).compareTo("$$$") != 0)
				itemcauhoi.traloic = list.get(4);
			else
				itemcauhoi.traloic = "";

			if (list.get(5).compareTo("$$$") != 0)
				itemcauhoi.traloid = list.get(5);
			else
				itemcauhoi.traloid = "";
			itemcauhoi.ketqua = list.get(6);
			itemcauhoi.image = list.get(7);
			// Log.d("image", ":" + itemcauhoi.image);
			itemcauhoi.phanloai = list.get(8);
			itemcauhoi.thi = list.get(9);

			i = i + 1;

		} else if (list.size() == 9) {
			// Log.d("so cot ", "9");
			itemcauhoi = new ItemCauhoi();

			itemcauhoi.idcauhoi = i + "";
			itemcauhoi.cauhoi = list.get(1);
			itemcauhoi.traloia = list.get(2);
			itemcauhoi.traloib = list.get(3);
			if (list.get(4).compareTo("$$$") != 0)
				itemcauhoi.traloic = list.get(4);
			else
				itemcauhoi.traloic = "";

			if (list.get(5).compareTo("$$$") != 0)
				itemcauhoi.traloid = list.get(5);
			else
				itemcauhoi.traloid = "";
			itemcauhoi.ketqua = list.get(6);
			itemcauhoi.image = "";
			// Log.d("image", ":" + itemcauhoi.image);
			itemcauhoi.phanloai = list.get(7);
			itemcauhoi.thi = list.get(8);

			i = i + 1;

		}
		Log.d("item.idcauhoi", "" + itemcauhoi.idcauhoi);
		int datldapan = Integer.parseInt(itemcauhoi.datraloidapan);
		int phanLoai = Integer.parseInt(itemcauhoi.phanloai);

		if (phanLoai == 1) {// luật
			Toancuc.luat_list.add(itemcauhoi);
			if (datldapan == -1)
				Toancuc.luat_chua_tl.add(itemcauhoi);
			else
				Toancuc.luat_da_tl.add(itemcauhoi);
		} else if (phanLoai == 2) {// biển báo
			Toancuc.bienbao_list.add(itemcauhoi);
			if (datldapan == -1)
				Toancuc.bienbao_chua_tl.add(itemcauhoi);
			else
				Toancuc.bienbao_da_tl.add(itemcauhoi);
		} else if (phanLoai == 3) {// tình huống
			Toancuc.tinhuong_list.add(itemcauhoi);
			if (datldapan == -1)
				Toancuc.tinhuong_chua_tl.add(itemcauhoi);
			else
				Toancuc.tinhuong_da_tl.add(itemcauhoi);
		}

		Toancuc.list_cauhoi.add(itemcauhoi);
		themdulieuvaolist(itemcauhoi);

		cauhoiDB.addItem(itemcauhoi.cauhoi, itemcauhoi.traloia,
				itemcauhoi.traloib, itemcauhoi.traloic, itemcauhoi.traloid,
				itemcauhoi.ketqua, itemcauhoi.image, itemcauhoi.phanloai,
				itemcauhoi.thi, itemcauhoi.datraloidapan);

	}
	// ////////////////database for lich van nien
	
	

}
