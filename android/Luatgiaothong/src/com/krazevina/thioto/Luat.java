package com.krazevina.thioto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.Dieu;
import com.krazevina.thioto.objects.ItemBienbao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Luat extends Activity implements OnTouchListener{

	ProgressDialog dialog;
	ListView list1;
	ListView list2;
	LinearLayout linchuong;
	LinearLayout lindieu;
	ViewFlipper viewflipper;
	private int mCurrentLayoutState;
	
	private TextView texttitle;
	private Button back;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.luat);
		list1 = (ListView) findViewById(R.id.list1);
		list1.setDivider(null);
		list1.setDividerHeight(0);
		list1.setFadingEdgeLength(0);
		list1.setFocusable(false);
		list1.setFocusableInTouchMode(false);
		
		list2 = (ListView) findViewById(R.id.list2);
		list2.setDivider(null);
		list2.setDividerHeight(0);
		list2.setFadingEdgeLength(0);
		list2.setFocusable(false);
		list2.setFocusableInTouchMode(false);
		
		linchuong=(LinearLayout)findViewById(R.id.chuong);
		lindieu=(LinearLayout)findViewById(R.id.dieu);
		
		mCurrentLayoutState = 0;
		viewflipper=(ViewFlipper)findViewById(R.id.flipper);
		
		back=(Button)findViewById(R.id.back);
		texttitle=(TextView)findViewById(R.id.texttitle);
		setOnclickItemOfList();
		
	}
	
	boolean load = false;

	@Override
	protected void onResume() {
		super.onResume();
		 if(Toancuc.viewing_detail){
			Tab.tabHost.setCurrentTab(5);
		}
		
		setView();

	}

	private void setView() {
		list1.setAdapter(new LuatAdapter(this, R.layout.luatitem, getdata()));
		int first = list1.getFirstVisiblePosition();
		int last = list1.getLastVisiblePosition();

		for (int i = 0; i <= last - first; i++) {
			View v = list1.getChildAt(i);
			LinearLayout lin = (LinearLayout) v.findViewById(R.id.itemvv);
			lin.startAnimation(inFromLeftAnimation(400 - ((i) * 10)));
		}
	}

	private ArrayList<Integer> getdata() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 1; i < 9; i++) {
			arr.add(i);
		}
		return arr;
	}

	private void setOnclickItemOfList() {

		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.d("position", "" + position);

//				Toancuc.dieuhientai=get_dieu(position)-1;
//				Toancuc.viewing_detail=true;
//				Tab.tabHost.setCurrentTab(5);
//				
//				Log.d("luatgetdieu",""+Toancuc.dieuhientai);
				
				Toancuc.rangedieu=get_Dieu_from_Chuong(position+1);
				currenthchuong=position;
				list2.setAdapter(new LuatAdapter2(Luat.this));
				switchLayoutStateTo(1);
				texttitle.setText("Chương "+(position+1));
				back.setVisibility(Button.VISIBLE);
				//linchuong
			}
		});
		list2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.d("position", "" + position);
				
				Log.d("get_dieu",""+(Toancuc.rangedieu.from+position));
				Toancuc.dieuhientai=get_dieu(currenthchuong)+position-1;
				Log.d("dieuhientai",""+Toancuc.dieuhientai);
				
				Toancuc.viewing_detail=true;
				Tab.tabHost.setCurrentTab(5);
				
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				back.setVisibility(Button.INVISIBLE);
				texttitle.setText("Bộ luật");
				switchLayoutStateTo(0);
				
			}
		});
	}

	int currenthchuong=-1;
	public static int get_dieu(int position){

		position=position+1;
		switch (position) {
		case 1:
			return 1;
		case 2:
			return 9;
		case 3:
			return 39;
		case 4:
			return 53;
		case 5:
			return 63;
		case 6:
			return 64;
		case 7:
			return 84;

		case 8:
			return 88;
		}
		return -1;
	}
	public static Dieu get_Dieu_from_Chuong(int chuong) {

		switch (chuong) {
		case 1:
			return new Dieu(1, 8);
		case 2:
			return new Dieu(9, 38);
		case 3:
			return new Dieu(39, 52);
		case 4:
			return new Dieu(53, 57);
		case 5:
			return new Dieu(58, 63);
		case 6:
			return new Dieu(64, 83);
		case 7:
			return new Dieu(84, 87);

		case 8:
			return new Dieu(88, 89);

		default:
			break;
		}
		return null;
	}

	public static int get_chuong_from_dieu(int dieu) {

		int ch = 0;
		if (dieu >= 1 && dieu <= 8) {
			ch = 1;
		} else if (dieu >= 9 && dieu <= 38) {
			ch = 2;
		} else if (dieu >= 39 && dieu <= 52) {
			ch = 3;
		} else if (dieu >= 53 && dieu <= 57) {
			ch = 4;
		} else if (dieu >= 58 && dieu <= 63) {
			ch = 5;
		} else if (dieu >= 64 && dieu <= 83) {
			ch = 6;
		} else if (dieu >= 84 && dieu <= 87) {
			ch = 7;
		} else if (dieu >= 88 && dieu <= 89) {
			ch = 8;
		}

		return ch;
	}

	private Animation inFromLeftAnimation(int duration) {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(duration);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	// ///////////////dư liệu biển báo
//	BienbaoDB bienbaodatabase = new BienbaoDB(this);



	ArrayList<itembienbao> listallbienbao = new ArrayList<itembienbao>();

	int ii=1;
	public void read() throws Exception {
		try {
			InputStream in = getResources().openRawResource(R.raw.dulieubienbao);
			InputStreamReader inputstream = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(inputstream);

			String s;

			listallbienbao.clear();
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
						
					}
					s = s.trim().substring(s.indexOf("\t"));
				}

				item.phanloai = Integer.parseInt(s);

				listallbienbao.add(item);
				itembb = new ItemBienbao(
						ii+"", item.ten, item.noidung,
						item.tenanh, item.phanloai + "");
				if(item.tenanh.compareTo("0")==0)
					itembb.link_anh=item.id+".png";
				Toancuc.list_item_bienbao.add(itembb);
				
				
				ii++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		///printdata();
	}

	class itembienbao {
		String id = null;
		String ten = null;
		String noidung = null;
		String tenanh = null;
		int phanloai = -1;
	}

//	private void printdata() {
//		bienbaodatabase.openToWrite();
//		for (itembienbao i : listallbienbao) {
//
//			if (i.tenanh.compareTo("0") != 0)
//				bienbaodatabase.addItem(i.id, i.ten, i.noidung, "" + i.tenanh,
//						"" + i.phanloai);
//			else
//				{bienbaodatabase.addItem(i.id, i.ten, i.noidung, "" + i.id
//						+ ".png", "" + i.phanloai);
//				
//				}
//			Log.d("image", "" + i.tenanh);
//		}
//
//		System.out.println("size of listallbienbao " + listallbienbao.size());
//
//	}

	@Override
	protected void onDestroy() {
		Toancuc.list_cauhoithi.clear();
		Toancuc.list_item_bienbao.clear();
		
		super.onDestroy();
	}


	
	/// confirm exit
	private void showDialogConfirmExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(Html.fromHtml("Bạn muốn thoát chương trình?"))
				.setCancelable(false).setPositiveButton("Thoát",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Luat.this.finish();
							}
						}).setNegativeButton("Không", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onBackPressed() {
		showDialogConfirmExit();
		return;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		return false;
	}
	public void switchLayoutStateTo(int switchTo) {
		while (mCurrentLayoutState != switchTo) {
			if (mCurrentLayoutState > switchTo) {
				mCurrentLayoutState--;
				viewflipper.setInAnimation(inFromLeftAnimation());
				viewflipper.setOutAnimation(outToRightAnimation());
				viewflipper.showPrevious();
			} else {
				mCurrentLayoutState++;
				viewflipper.setInAnimation(inFromRightAnimation());
				viewflipper.setOutAnimation(outToLeftAnimation());
				viewflipper.showNext();
			}

		}
		;
	}

	protected Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(200);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	protected Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(200);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	protected Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(200);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	protected Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(200);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

}