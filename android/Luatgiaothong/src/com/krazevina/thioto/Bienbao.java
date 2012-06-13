package com.krazevina.thioto;

import java.util.ArrayList;
import java.util.HashMap;


import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemBienbao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView.OnEditorActionListener;

public class Bienbao extends Activity implements OnEditorActionListener {

	private Button select;
	private LinearLayout detailbienbaolinearlayout;

	private ArrayList<ItemBienbao> list_item_bienbao = new ArrayList<ItemBienbao>();
	private ArrayList<ItemBienbao> list_for_adapter;
	private HashMap<String, Integer> map;

	ListView list1;
	Button back;
	EditText edit;

	TextView loaibientext;

	LayoutInflater li;
	HorizontalPaper cuon_ngang;
	HorizontalPaper cuon_ngang_grid;

	LinearLayout prev[] = new LinearLayout[6];
	LinearLayout next[] = new LinearLayout[6];
	LinearLayout lin3[] = new LinearLayout[6];
	ImageView image[] = new ImageView[6];
	TextView tenbienbao[] = new TextView[6];
	TextView noidung[] = new TextView[6];
	InputMethodManager inputMgr;

	LinearLayout header1;
	LinearLayout idtitle;
	LinearLayout linsearch;

	LinearLayout linviewinglist;
	LinearLayout linviewinggrid; // chua horizontalpaper grid
	LinearLayout linsearchinput;

	private Button viewmode;
	private GridView gridview[] = new GridView[6];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bienbao);

		inputMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		loaibientext = (TextView) findViewById(R.id.loaibientext);
		linsearch = (LinearLayout) findViewById(R.id.linsearch);
		idtitle = (LinearLayout) findViewById(R.id.idtitle);
		viewmode = (Button) findViewById(R.id.viewmode);

		linviewinglist = (LinearLayout) findViewById(R.id.linviewinglist);
		linviewinggrid = (LinearLayout) findViewById(R.id.linviewinggrid);
		linsearchinput = (LinearLayout) findViewById(R.id.linsearch1);
		// gridview=(GridView)findViewById(R.id.gridview);

		kbcheck kb = new kbcheck();
		kb.start();
	}

	int currentSreen = -1;

	private void addView() {

		for (int i = 1; i < 4; i++) {

			final int jj = i;
			View view = (LinearLayout) li.inflate(
					R.layout.itemviewbienbaodetail, null);
			cuon_ngang.addView(view);

			prev[i - 1] = (LinearLayout) view.findViewById(R.id.prev);
			next[i - 1] = (LinearLayout) view.findViewById(R.id.next);

			tenbienbao[i - 1] = (TextView) view.findViewById(R.id.tenbienbao);
			noidung[i - 1] = (TextView) view.findViewById(R.id.noidung);
			image[i - 1] = (ImageView) view.findViewById(R.id.image);

			prev[jj - 1].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (bienbaohientai == 0) {
						Toast.makeText(Bienbao.this,
								" Đây là câu hỏi đầu tiên ", 0).show();
						return;
					}
					if (bienbaohientai == 404) {
						cuon_ngang.setCurrentScreen(1, true);
					} else
						cuon_ngang.setCurrentScreen(0, true);
					inputMgr.hideSoftInputFromWindow(prev[1].getWindowToken(),
							0);
				}
			});
			next[jj - 1].setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (bienbaohientai == 404) {
						Toast.makeText(Bienbao.this,
								" Đây là câu hỏi cuối cùng", 0).show();
						return;
					}
					if (bienbaohientai == 0) {
						cuon_ngang.setCurrentScreen(1, true);
					} else
						cuon_ngang.setCurrentScreen(2, true);
					inputMgr.hideSoftInputFromWindow(next[1].getWindowToken(),
							0);
				}
			});
		}
		detailbienbaolinearlayout.addView(cuon_ngang);

		// /////////addview grid
		for (int i = 1; i < 7; i++) {

			final int jj = i;
			View view = (LinearLayout) li.inflate(R.layout.viewgridbienbao,null);
			gridview[jj - 1] = (GridView) view.findViewById(R.id.gridview);

			gridview[jj - 1].setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					xx= (int)event.getX();
					yy=(int) event.getY();
					
					int p = gridview[jj - 1].pointToPosition(xx,yy)-
							 gridview[jj-1].getFirstVisiblePosition();

				    
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
					
						if (gridview[jj - 1].getChildAt(p) == null){
							Log.d("ACTION_DOWN null","actiondown null");
							
						   return false;
						}
							
						AlphaAnimation alpha = new AlphaAnimation(0.3F, 0.3F);
						alpha.setDuration(100);
						alpha.setFillAfter(true);
						position_pressed = p;
						xxsave=xx;
						yysave=yy;
						viewpressed=gridview[jj - 1].getChildAt(p);
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
					
					if (event.getAction() == MotionEvent.ACTION_UP){
						
						if(p==position_pressed){
							ItemBienbao itembb=list_item_bienbao.get(getsttview(sttdatahientai).from
									+p+gridview[jj-1].getFirstVisiblePosition());
							Log.d("id bien",""+itembb.id);
								
							detailbienbaolinearlayout.setVisibility(LinearLayout.VISIBLE);
							back.setVisibility(Button.VISIBLE);
							linviewinggrid.setVisibility(LinearLayout.GONE);
							viewmode.setVisibility(LinearLayout.GONE);
							linsearch.setVisibility(LinearLayout.GONE);
							
							fromgridview=true;
							bienbaohientai = Integer.parseInt(itembb.id) - 1;
							if (bienbaohientai == 0) {
								adddatatoView(0, bienbaohientai);
								adddatatoView(1, bienbaohientai + 1);
								adddatatoView(2, bienbaohientai + 2);
								cuon_ngang.setCurrentScreen(0, false);
							} else if (bienbaohientai == 206) {
								adddatatoView(0, bienbaohientai - 2);
								adddatatoView(1, bienbaohientai - 1);
								adddatatoView(2, bienbaohientai);
								cuon_ngang.setCurrentScreen(2, false);
							} else {
								adddatatoView(0, bienbaohientai - 1);
								adddatatoView(1, bienbaohientai);
								adddatatoView(2, bienbaohientai + 1);
								cuon_ngang.setCurrentScreen(1, false);
							}
						}
					}
					return false;
				}
			});
			cuon_ngang_grid.addView(view);
		}
		linviewinggrid.addView(cuon_ngang_grid);
	}

	int xx;
	int yy;
	int xxsave;
	int yysave;
	
	View viewpressed=null;
    boolean fromgridview=false;
    
	protected class InitView0 extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			new InitView().execute((Void) null);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class InitView extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			// dialog.setMessage("Đang chuẩn bị show...");
			select = (Button) findViewById(R.id.select);

			header1 = (LinearLayout) findViewById(R.id.header1);
			detailbienbaolinearlayout = (LinearLayout) findViewById(R.id.detailbienbao);
			cuon_ngang = new HorizontalPaper(Bienbao.this);
			li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			cuon_ngang.setOnScreenSwitchListener(switchListener);
			cuon_ngang.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

			cuon_ngang_grid = new HorizontalPaper(Bienbao.this);
			li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			cuon_ngang_grid.setOnScreenSwitchListener(switchListener_grid);
			cuon_ngang_grid.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

			edit = (EditText) findViewById(R.id.edit);
			edit.setOnEditorActionListener(Bienbao.this);

			list1 = (ListView) findViewById(R.id.list1);
			textheader = (TextView) findViewById(R.id.textheader);
			back = (Button) findViewById(R.id.back);
			back.setVisibility(Button.GONE);
			setClick();
			setCrollList();
			setAtributeList(list1);
			map = Toancuc.getMap();
			list_for_adapter = new ArrayList<ItemBienbao>();
			addView();
			setView();

			new laydulieu().execute((Void) null);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class laydulieu extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {
			setView();
			setGridView();
			dialog.dismiss();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			getDataBienbao(); // lấy lên từ sqilite
			initData(list_item_bienbao); // chia dữ liệu từ 1->6 loại
			initData_showAll(list_for_adapter);
           
			return null;
		}
	}

	TextView textheader;
	boolean first = true;

	private void setCrollList() {
		list1.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if (search) {
					Log.d("firstvisible", "" + firstVisibleItem);
					Log.d("listkhac.size", "" + list_bienhientai.size());
					if (firstVisibleItem > list_bienhientai.size()) {
						textheader.setText("Nhóm khác : " + list_khac.size()
								+ " biển");
					} else {
						textheader.setText(getTitle(bienhientai) + ":"
								+ list_bienhientai.size() + " biển");
					}
					return;
				}

				Log.d("first visible ", "" + firstVisibleItem);
				if (nochangetitle) { // truong hop hien thi tung bien bao

					return;
				}

				Log.d("nochange title", "=true");
				if (first) {
					textheader.setText("Biển cấm");
					first = false;
				} else if (firstVisibleItem > bb_biencam.size() // 1
						&& firstVisibleItem <= size_item_nguyhiem // 1
				) {
					textheader.setText("Biển nguy hiểm");

				} else if (firstVisibleItem > size_item_nguyhiem // 2
						&& firstVisibleItem <= size_item_hieulenh // 1
				) {
					textheader.setText("Biển hiệu lệnh");

				} else if (firstVisibleItem > size_item_hieulenh // 3
						&& firstVisibleItem <= size_item_chidan // 1
				) {
					textheader.setText("Biển chỉ dẫn");

				} else if (firstVisibleItem > size_item_chidan // 4
						&& firstVisibleItem <= size_item_bienphu // 1
				) {
					textheader.setText("Biển phụ");

				} else if (firstVisibleItem > size_item_bienphu // 5
						&& firstVisibleItem <= size_item_vachke // 1
				) {
					textheader.setText(" vạch kẻ");

				} else
					textheader.setText("Biển cấm");
			}
		});
	}

	public void setGridView() {
		adddatatoGridView(0, 0);
		adddatatoGridView(1, 1);
		adddatatoGridView(2, 2);
		adddatatoGridView(3, 3);
		adddatatoGridView(4, 4);
		adddatatoGridView(5, 5);
		
	}

	private void UnsetDetailBienbao() {
		detailbienbaolinearlayout.setVisibility(LinearLayout.GONE);

	}

	private void adddatatoGridView(int sttview, int sttdulieu) {

		ItemView item = getsttview(sttdulieu);
		gridview[sttview].setAdapter(new BienbaoGridAdapter(item.from, item.to,
				Bienbao.this, list_item_bienbao, map));

	}

	int curr_postion_listall = -1;

	private void adddatatoView(int sttview, int vitritronglist) {
		Log.d("addatatoview tai vi tri", "" + vitritronglist);
		ItemBienbao it0 = list_item_bienbao.get(vitritronglist);

		image[sttview].setImageResource(map.get(it0.link_anh));
		tenbienbao[sttview].setText("  " + it0.ten_bien_bao);
		noidung[sttview].setText("  " + it0.noi_dung);
	}

	private int setDetailBienbao(int position) {
		// position la vi tri trong list_for_adapter

		ItemBienbao i = list_for_adapter.get(position);
		Log.d("id cua bien bao ", "" + i.id);
		bienbaohientai = Integer.parseInt(i.id) - 1;
		if (bienbaohientai == 0) {
			adddatatoView(0, bienbaohientai);
			adddatatoView(1, bienbaohientai + 1);
			adddatatoView(2, bienbaohientai + 2);
			cuon_ngang.setCurrentScreen(0, false);
		} else if (bienbaohientai == 206) {
			adddatatoView(0, bienbaohientai - 2);
			adddatatoView(1, bienbaohientai - 1);
			adddatatoView(2, bienbaohientai);
			cuon_ngang.setCurrentScreen(2, false);
		} else {
			adddatatoView(0, bienbaohientai - 1);
			adddatatoView(1, bienbaohientai);
			adddatatoView(2, bienbaohientai + 1);
			cuon_ngang.setCurrentScreen(1, false);
		}

		return bienbaohientai;
	}

	boolean ok = false;
	int currentview = -1;

	private void setClick() {
		idtitle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!xulyclickitem) {
					inputMgr.hideSoftInputFromWindow(idtitle.getWindowToken(),
							0);
					xulyclickitem = true;
					return;
				}
			}
		});

		select.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				CustomPopupWindow customepopup = new CustomPopupWindow(select,
						Bienbao.this);
				customepopup.setContentView(R.layout.popup1);
				customepopup.showDropDown();
			}
		});

		list1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.d("vi tri bien bao:", "" + position);

				if (!xulyclickitem) {
					inputMgr.hideSoftInputFromWindow(list1.getWindowToken(), 0);
					xulyclickitem = true;
					return;
				}
				// click vao title => return
				if (list_for_adapter.get(position).link_anh == null)
					return;

				setDetailBienbao(position);

				detailbienbaolinearlayout.setVisibility(LinearLayout.VISIBLE);
				back.setVisibility(Button.VISIBLE);
				linsearch.setVisibility(LinearLayout.GONE);
                viewmode.setVisibility(Button.GONE);
			}
		});

		back.setOnClickListener(new View.OnClickListener() {// of hearder
			@Override
			public void onClick(View v) {
				if(fromgridview)
				{
			
				fromgridview=false;
				linviewinggrid.setVisibility(LinearLayout.VISIBLE);
				}
//				else{
//					UnsetDetailBienbao();
//				}
				linsearch.setVisibility(LinearLayout.VISIBLE);
				
				viewmode.setVisibility(LinearLayout.VISIBLE);
				back.setVisibility(Button.INVISIBLE);
				
				UnsetDetailBienbao();
				inputMgr.hideSoftInputFromWindow(back.getWindowToken(), 0);
			}
		});
		header1.setOnClickListener(new View.OnClickListener() {// of hearder

			@Override
			public void onClick(View v) {
				inputMgr.hideSoftInputFromWindow(header1.getWindowToken(), 0);
			}
		});

		viewmode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				inputMgr.hideSoftInputFromWindow(list1.getWindowToken(), 0);
				
				if (isviewinglist) {
					isviewinglist = false;
					linviewinglist.setVisibility(LinearLayout.GONE);
					linsearchinput.setVisibility(LinearLayout.INVISIBLE);
					linviewinggrid.setVisibility(LinearLayout.VISIBLE);
//					linsearch.setVisibility(LinearLayout.GONE);
					viewmode.setText("Xem dạng list");
					// gridview.setAdapter(new BienbaoGridAdapter(Bienbao.this,
					// list_for_adapter, map));

					
				} else {
					isviewinglist = true;
					linviewinglist.setVisibility(LinearLayout.VISIBLE);
					linviewinggrid.setVisibility(LinearLayout.GONE);
					linsearchinput.setVisibility(LinearLayout.VISIBLE);
//					linsearch.setVisibility(LinearLayout.VISIBLE);
					viewmode.setText("Xem dạng grid");

					linsearchinput.setVisibility(LinearLayout.VISIBLE);
				}
			}
		});

		// gridview.setOnTouchListener(new OnTouchListener() {
		//
		// // @Override
		// // public boolean onTouch(View v, MotionEvent event) {
		// // int p = gridview.pointToPosition((int) event.getX(),
		// // (int) event.getY());
		// //// if (gridview.getChildAt(p) == null) {
		// ////
		// gridview.getChildAt(position_pressed).setBackgroundDrawable(null);
		// //// return false;
		// //// }
		// //
		// // Log.d("vitri ", "" + p);
		// // if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// // AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
		// // alpha.setDuration(100);
		// // alpha.setFillAfter(true);
		// // position_pressed = p;
		// // gridview.getChildAt(p).setBackgroundColor(R.color.green);
		// // (gridview.getChildAt(p)).startAnimation(alpha);
		// //
		// // }
		// //
		// // if (event.getAction() ==
		// MotionEvent.ACTION_UP||gridview.getChildAt(p)==null) {
		// // Log.d("action up","action up");
		// // AlphaAnimation alpha = new AlphaAnimation(1F, 1F);
		// // alpha.setDuration(0);
		// // alpha.setFillAfter(true);
		// // gridview.getChildAt(position_pressed).setBackgroundDrawable(null);
		// // if ((gridview.getChildAt(position_pressed)) != null) {
		// // (gridview.getChildAt(position_pressed))
		// // .startAnimation(alpha);
		// // (gridview.getChildAt(p)).startAnimation(alpha);
		// // }
		// // }
		// //
		// // return false;
		// // }
		// });

	}

	int position_pressed = -1;
	boolean isviewinglist = false;
	private HorizontalPaper.OnScreenSwitchListener switchListener = new HorizontalPaper.OnScreenSwitchListener() {

		@Override
		public void onScreenSwitched(int screen) {

			if (screen > 1) {

				if (bienbaohientai + 1 <= 205) {
					bienbaohientai++;
					adddatatoView(0, bienbaohientai - 1);
					adddatatoView(1, bienbaohientai);
					new changelayout_next().execute((Void) null);
				}

			} else if (screen < 1) {
				if (bienbaohientai - 1 >= 1) {
					bienbaohientai--;
					adddatatoView(1, bienbaohientai);
					adddatatoView(2, bienbaohientai + 1);

					new changelayout_prev().execute((Void) null);
				}

			} else {
				if (bienbaohientai == 206) {
					// traloihientai=-1;
					bienbaohientai--;
				} else if (bienbaohientai == 0) {
					// traloihientai=-1;
					bienbaohientai++;

				}
			}

			if(screen==0){
				if(!isshowedtoast1){
					isshowedtoast1=true;
				Toast.makeText(Bienbao.this, " Đây là biển đầu tiên",0).show();
				}
			}
			 if (screen == 206) {
					if(!isshowedtoast1){
						isshowedtoast1=true;
					Toast.makeText(Bienbao.this, " Đây là biển cuối cùng",
							0).show();
					}
			 }
			
			if(currentscreen1!=0&&currentscreen1!=206){
				isshowedtoast1=false;
			}
			
			currentscreen1=screen;
			
		}

	};
	int sttdatahientai = 0;
	int currentscreen=0;
	int currentscreen1=0;
	boolean isshowedtoast=false;
	boolean isshowedtoast1=false;
	private HorizontalPaper.OnScreenSwitchListener switchListener_grid = new HorizontalPaper.OnScreenSwitchListener() {

		@Override
		public void onScreenSwitched(int screen) {

			// //
			// bieen cam 0-5:
			// bien nguy hiem 6-11:
			// bien hieu lenh 12-13:
			// bien chi dan 14-21:
			// bien phu 22-23:
			// bien vach ke 24:

//			if(screen>currentscreen){
//				sttdatahientai++;
//			}else if(screen<currentscreen){
//				sttdatahientai++;
//			}
		
			if(screen==0){
				if(!isshowedtoast){
					isshowedtoast=true;
				Toast.makeText(Bienbao.this, " Đây là các biển đầu tiên",0).show();
				}
			}
			 if (screen == 5) {
					if(!isshowedtoast){
					isshowedtoast=true;
					Toast.makeText(Bienbao.this, " Đây là các biển cuối cùng",
							0).show();
					}
			 }
			
			if(currentscreen!=0&&currentscreen!=5){
				isshowedtoast=false;
			}
			
			currentscreen=screen;
			
//			 if (screen == 5) {
//					Toast.makeText(Bienbao.this, " Đây là các biển cuối cùng",
//							0).show();
//			 }
//			 if (screen == 0) {
//					Toast.makeText(Bienbao.this, " Đây là các biển đâu tiên",
//							0).show();
//			 }
			sttdatahientai=screen;
//			currentscreen=screen;
			Log.d("sttdatahientai", "" + sttdatahientai);
//			if (screen > 1) {
//
//				if (sttdatahientai + 1 <= 4) {
//					// cuon[0].scrollTo(0, 0);
//					// cuon[1].scrollTo(0, 0);
//					// cuon[2].scrollTo(0, 0);
//
//					sttdatahientai++;
//					adddatatoGridView(0, sttdatahientai - 1);
//					
//					gridview[1].setSelection(0);
//					gridview[1].setAdapter(null);
//					adddatatoGridView(1, sttdatahientai);
//					new changelayout_next_grid().execute((Void) null);
//					
//				} else if (sttdatahientai == 5) {
//					Toast.makeText(Bienbao.this, " Đây là các biển cuối cùng",
//							0).show();
//
//				} else {
//					sttdatahientai = 5;
//				}
//
//			} else if (screen < 1) {
//
//				if (sttdatahientai - 1 >= 1) {
//					// cuon[0].scrollTo(0, 0);
//					// cuon[1].scrollTo(0, 0);
//					// cuon[2].scrollTo(0, 0);
//
//					sttdatahientai--;
//					adddatatoGridView(1, sttdatahientai);
//					adddatatoGridView(2, sttdatahientai + 1);
//					new changelayout_prev_grid().execute((Void) null);
//					
//				} else if (sttdatahientai == 0) {
//					Toast.makeText(Bienbao.this, " Đây là các biển đầu tiên", 0)
//							.show();
//
//				} else {
//					sttdatahientai = 0;
//				}
//
//			} else {
//
//				if (sttdatahientai == 5) {
//					adddatatoGridView(2, 5);
//					sttdatahientai--;
//				} else if (sttdatahientai == 0) {
//					adddatatoGridView(0, 0);
//					sttdatahientai++;
//
//				}
//			}
			// 0-5
			// 6-11
			// 12-13
			// 14-21
			// 22-23
			// 24-27

			if (!xemtungvung)
				return;
			if (sttdatahientai == 0 ) {
				setTextButton("Biển cấm");
				textheader.setText("Biển cấm");
				list1.setAdapter(new BienbaoListAdapter(Bienbao.this,
						R.layout.itemviewbienbao, Toancuc.bb_biencams, map));
				list_for_adapter = Toancuc.bb_biencams;
			}
			if (sttdatahientai == 1) {
				setTextButton("Nguy hiểm");
				textheader.setText("Biển nguy hiểm");
				list1.setAdapter(new BienbaoListAdapter(Bienbao.this,
						R.layout.itemviewbienbao, Toancuc.bb_nguyhiems, map));
				list_for_adapter = Toancuc.bb_nguyhiems;
			}
			if (sttdatahientai == 2 ) {
				setTextButton("Hiệu lệnh");
				textheader.setText("Biển hiệu lệnh");
				list1.setAdapter(new BienbaoListAdapter(Bienbao.this,
						R.layout.itemviewbienbao, Toancuc.bb_hieulenhs, map));
				list_for_adapter = Toancuc.bb_hieulenhs;
			}

			if (sttdatahientai == 3 ) {
				setTextButton("Chỉ dẫn");
				textheader.setText("Biển chỉ dẫn");
				list1.setAdapter(new BienbaoListAdapter(Bienbao.this,
						R.layout.itemviewbienbao, Toancuc.bb_chidans, map));
				list_for_adapter = Toancuc.bb_chidans;
			}
			if (sttdatahientai == 4) {
				setTextButton("Biển phụ");
				textheader.setText("Biển phụ");
				list1.setAdapter(new BienbaoListAdapter(Bienbao.this,
						R.layout.itemviewbienbao, Toancuc.bb_bienphus, map));
				list_for_adapter = Toancuc.bb_bienphus;
			}
			if (sttdatahientai == 5) {
				setTextButton("Vạch kẻ");
				textheader.setText("Vạch kẻ");
				list1.setAdapter(new BienbaoListAdapter(Bienbao.this,
						R.layout.itemviewbienbao, Toancuc.bb_vachkes, map));
				list_for_adapter = Toancuc.bb_vachkes;
			}

		}

	};
	int bienbaohientai = -1;// 0->206

	protected class changelayout_prev extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			cuon_ngang.setCurrentScreen(1, false);
			adddatatoView(0, bienbaohientai - 1);

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class changelayout_next extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {

			cuon_ngang.setCurrentScreen(1, false);
			adddatatoView(2, bienbaohientai + 1);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class changelayout_prev_grid extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {

			cuon_ngang_grid.setCurrentScreen(1, false);
			adddatatoGridView(0, sttdatahientai - 1);

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	protected class changelayout_next_grid extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {

			cuon_ngang_grid.setCurrentScreen(1, false);
			adddatatoGridView(2, sttdatahientai + 1);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			return null;
		}
	}

	boolean loaddata = false;

	@Override
	protected void onResume() {
		super.onResume();
		isactive = true;
		if (Toancuc.dangthi) {
			Tab.tabHost.setCurrentTab(2);
			return;
		}
		Log.d("onresume of Bien bao", "resume bien bao");
		if (!loaddata) {
			loaddata = true;
			dialog = new ProgressDialog(Bienbao.this);
			dialog.setMessage("Đang tải dữ liệu...");
			dialog.show();

			new InitView0().execute((Void) null);
		}
	}

	@Override
	protected void onPause() {
		isactive = false;
		super.onPause();
	}

	ArrayList<ItemBienbao> bb_biencam = new ArrayList<ItemBienbao>();
	ArrayList<ItemBienbao> bb_nguyhiem = new ArrayList<ItemBienbao>();
	ArrayList<ItemBienbao> bb_hieulenh = new ArrayList<ItemBienbao>();
	ArrayList<ItemBienbao> bb_chidan = new ArrayList<ItemBienbao>();
	ArrayList<ItemBienbao> bb_bienphu = new ArrayList<ItemBienbao>();
	ArrayList<ItemBienbao> bb_vachke = new ArrayList<ItemBienbao>();

	private void getDataBienbao() { // lay hết dữ liệu trong db ra đưa vào
		list_item_bienbao.clear();
		Log.d("at bienbao toancuc.list_item_bienbao", ""
				+ Toancuc.list_item_bienbao.size());
		list_item_bienbao = copy(Toancuc.list_item_bienbao);
		Log.d("Du lieu sqlite ", list_item_bienbao.size() + " hang ");
	}

	private ArrayList<ItemBienbao> copy(ArrayList<ItemBienbao> ll) {
		ArrayList<ItemBienbao> l = new ArrayList<ItemBienbao>();
		ItemBienbao item;
		for (int i = 0; i < ll.size(); i++) {

			ItemBienbao it = ll.get(i);
			item = new ItemBienbao();

			item.id = it.id;
			item.ten_bien_bao = it.ten_bien_bao;
			item.noi_dung = it.noi_dung;
			item.link_anh = it.link_anh;
			item.phan_loai = it.phan_loai;

			l.add(item);
		}
		return l;
	}

	int num_item_nguyhiem = 0;
	int num_item_hieulenh = 0;
	int num_item_chidan = 0;
	int num_item_bienphu = 0;
	int num_item_vachke = 0;

	int size_item_nguyhiem = 0; // giới hạn
	int size_item_hieulenh = 0; // giới hạn
	int size_item_chidan = 0;
	int size_item_bienphu = 0;
	int size_item_vachke = 0;

	private synchronized void initData_showAll(ArrayList<ItemBienbao> lisst) { // co
																				// 6
																				// loai
		// bien bao

		lisst.clear();
		Log.d("so phan tu cua Lisitembienbao ", "" + list_item_bienbao.size());
		lisst.clear();

		ItemBienbao itemBienbao;

		Log.d("bb_biencam.size", "" + bb_biencam.size());
		for (ItemBienbao item : bb_biencam) {
			lisst.add(item);
		}

		itemBienbao = new ItemBienbao();
		itemBienbao.ten_bien_bao = "nguyhiem";
		lisst.add(itemBienbao);
		for (ItemBienbao item : bb_nguyhiem) {
			lisst.add(item);
		}

		itemBienbao = new ItemBienbao();
		itemBienbao.ten_bien_bao = "hieulenh";
		lisst.add(itemBienbao);
		for (ItemBienbao item : bb_hieulenh) {
			lisst.add(item);
		}

		itemBienbao = new ItemBienbao();
		itemBienbao.ten_bien_bao = "chidan";
		lisst.add(itemBienbao);
		for (ItemBienbao item : bb_chidan) {
			lisst.add(item);
		}

		itemBienbao = new ItemBienbao();
		itemBienbao.ten_bien_bao = "bienphu";
		lisst.add(itemBienbao);
		for (ItemBienbao item : bb_bienphu) {
			lisst.add(item);
		}
		itemBienbao = new ItemBienbao();
		itemBienbao.ten_bien_bao = "vachke";
		lisst.add(itemBienbao);
		for (ItemBienbao item : bb_vachke) {
			lisst.add(item);
		}

		size_item_nguyhiem = bb_nguyhiem.size() + bb_biencam.size();
		size_item_hieulenh = bb_hieulenh.size() + size_item_nguyhiem + 1;
		size_item_chidan = bb_chidan.size() + size_item_hieulenh + 1;
		size_item_bienphu = bb_bienphu.size() + size_item_chidan + 1;
		size_item_vachke = bb_vachke.size() + size_item_bienphu + 1;

		num_item_nguyhiem = bb_nguyhiem.size();
		num_item_hieulenh = bb_hieulenh.size();
		num_item_chidan = bb_chidan.size();
		num_item_bienphu = bb_bienphu.size();
		num_item_vachke = bb_vachke.size();

		Log.d(" size_item_nguyhiem (giới hạn để cuộn)", "" + size_item_nguyhiem);
		Log.d(" size_item_hieulenh ", "" + size_item_hieulenh);
		Log.d(" size_item_chidan ", "" + size_item_chidan);
		Log.d(" size_item_bienphu ", "" + size_item_bienphu);
		Log.d(" size_item_vachke ", "" + size_item_vachke);

		Log.d(" num_item_nguyhiem ", "" + num_item_nguyhiem);
		Log.d(" num_item_hieulenh ", "" + num_item_hieulenh);
		Log.d(" num_item_chidan ", "" + num_item_chidan);
		Log.d(" num_item_bienphu ", "" + num_item_bienphu);
		Log.d(" num_item_vachke ", "" + num_item_vachke);

		Log.d("so phan tu cua Lisitembienbao ", "" + list_item_bienbao.size());
	}

	private void initData(ArrayList<ItemBienbao> l) {

		bb_biencam.clear();
		bb_nguyhiem.clear();
		bb_hieulenh.clear();
		bb_chidan.clear();
		bb_bienphu.clear();
		bb_vachke.clear();
		for (ItemBienbao item : l) {

			switch (Integer.parseInt(item.phan_loai)) {
			case 1:
				bb_biencam.add(item);
				break;
			case 2:
				bb_nguyhiem.add(item);
				break;
			case 3:
				bb_hieulenh.add(item);
				break;
			case 4:
				bb_chidan.add(item);
				break;
			case 5:
				bb_bienphu.add(item);
				break;
			case 6:
				bb_vachke.add(item);
				break;
			default:
				Log.d("Eror", "Fatal");
			}

		}
	}

	private void setAtributeList(ListView list) {
		list.setDivider(null);
		list.setDividerHeight(0);
		list.setFadingEdgeLength(0);
		list.setFocusable(false);
		list.setFocusableInTouchMode(false);
	}

	private void setView() {
		UnsetDetailBienbao();

		list1.setAdapter(new BienbaoListAdapter(this, R.layout.itemviewbienbao,
				list_for_adapter, map));
		Rect rectgle = new Rect();
		Window window = getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		this.height = rectgle.bottom;

	}

	int height = -1;

	public void setTextButton(String string) {
		select.setText(string);
	}

	boolean nochangetitle = false;

	int bienhientai = 0;

	public synchronized void setViewBienbao(int position) {

		bienhientai = position;
		search = false;
		if (position == 0) {
			initData(list_item_bienbao);
			list_for_adapter = new ArrayList<ItemBienbao>();
			initData_showAll(list_for_adapter);
			select.setText("Tất cả");
			setView();
			nochangetitle = false;
			return;
		}

		nochangetitle = true;
		UnsetDetailBienbao(); // remove detail bienbao
		if (position == 1) {
			select.setText("Biển cấm");
			textheader.setText("Biển cấm");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, bb_biencam, map));
			list_for_adapter = bb_biencam;
		} else if (position == 2) {
			select.setText("Nguy hiểm");
			textheader.setText("Biển nguy hiểm");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, bb_nguyhiem, map));
			list_for_adapter = bb_nguyhiem;
		} else if (position == 3) {
			select.setText("Hiệu lệnh");
			textheader.setText("Biển hiệu lệnh");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, bb_hieulenh, map));
			list_for_adapter = bb_hieulenh;
		} else if (position == 4) {
			select.setText("Chỉ dẫn");
			textheader.setText("Biển chỉ dẫn");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, bb_chidan, map));
			list_for_adapter = bb_chidan;
		} else if (position == 5) {
			select.setText("Biển phụ");
			textheader.setText("Biển phụ");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, bb_bienphu, map));
			list_for_adapter = bb_bienphu;
		} else if (position == 6) {
			select.setText("Vạch kẻ");
			textheader.setText("Vạch kẻ");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, bb_vachke, map));
			list_for_adapter = bb_vachke;
		}
		// 2 nguy hiem
	}

	boolean xemtungvung = false;

	public synchronized void setViewBienbaos(int position) {

		bienhientai = position;
		search = false;
		if (position == 0) {
			initData(list_item_bienbao);
			list_for_adapter = new ArrayList<ItemBienbao>();
			initData_showAll(list_for_adapter);
			select.setText("Tất cả");
			setView();
			nochangetitle = false;

//			adddatatoGridView(0, 0);
//			adddatatoGridView(1, 1);
//			adddatatoGridView(2, 2);
			sttdatahientai = 0;
			cuon_ngang_grid.setCurrentScreen(0, false);

			xemtungvung = false;

			return;
		}
		xemtungvung = true;
		nochangetitle = true;
		UnsetDetailBienbao(); // remove detail bienbao
		if (position == 1) {
			select.setText("Biển cấm");
			textheader.setText("Biển cấm");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, Toancuc.bb_biencams, map));
			list_for_adapter = Toancuc.bb_biencams;

//			adddatatoGridView(0, 0);
//			adddatatoGridView(1, 1);
//			adddatatoGridView(2, 2);
			sttdatahientai = 0;
			cuon_ngang_grid.setCurrentScreen(0, false);

		} else if (position == 2) {
			select.setText("Nguy hiểm");
			textheader.setText("Biển nguy hiểm");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, Toancuc.bb_nguyhiems, map));
			list_for_adapter = Toancuc.bb_nguyhiems;

//			adddatatoGridView(0, 0);
//			adddatatoGridView(1, 1);
//			adddatatoGridView(2, 2);
			sttdatahientai = 1;
			cuon_ngang_grid.setCurrentScreen(1, false);

		} else if (position == 3) {
			select.setText("Hiệu lệnh");
			textheader.setText("Biển hiệu lệnh");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, Toancuc.bb_hieulenhs, map));
			list_for_adapter = Toancuc.bb_hieulenhs;

//			adddatatoGridView(0, 1);
//			adddatatoGridView(1, 2);
//			adddatatoGridView(2, 3);
			sttdatahientai = 2;
			cuon_ngang_grid.setCurrentScreen(2, false);

		} else if (position == 4) {
			select.setText("Chỉ dẫn");
			textheader.setText("Biển chỉ dẫn");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, Toancuc.bb_chidans, map));
			list_for_adapter = Toancuc.bb_chidans;

//			adddatatoGridView(0, 2);
//			adddatatoGridView(1, 3);
//			adddatatoGridView(2, 4);
			sttdatahientai =3;
			cuon_ngang_grid.setCurrentScreen(3, false);

		} else if (position == 5) {
			select.setText("Biển phụ");
			textheader.setText("Biển phụ");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, Toancuc.bb_bienphus, map));
			list_for_adapter = Toancuc.bb_bienphus;

//			adddatatoGridView(0, 3);
//			adddatatoGridView(1, 4);
//			adddatatoGridView(2, 5);
			sttdatahientai = 4;
			cuon_ngang_grid.setCurrentScreen(4, false);

		} else if (position == 6) {
			select.setText("Vạch kẻ");
			textheader.setText("Vạch kẻ");
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, Toancuc.bb_vachkes, map));
			list_for_adapter = Toancuc.bb_vachkes;

//			adddatatoGridView(0, 3);
//			adddatatoGridView(1, 4);
//			adddatatoGridView(2, 5);
			sttdatahientai = 5;
			cuon_ngang_grid.setCurrentScreen(5, false);

		}
		// 2 nguy hiem
	}

	private void showDialogConfirmExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(Html.fromHtml("Bạn muốn thoát chương trình?"))
				.setCancelable(false)
				.setPositiveButton("Thoát",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Bienbao.this.finish();
							}
						}).setNegativeButton("Không", new OnClickListener() {
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

	private void showDialogNotInformation() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Bienbao.this);

		builder.setMessage(Html.fromHtml("Không tìm thấy mục nào!"))
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	// ////////////////////////////////////////////Search
	String pharse;
	boolean load = true;
	ProgressDialog dialog;

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		if (event == null)
			return false;

		if (event.getKeyCode() == 66 && load) {

			pharse = edit.getText().toString().toLowerCase();
			if (pharse.compareTo("") != 0) {
				// / select.setText("Tìm kiếm");
				dialog = new ProgressDialog(Bienbao.this);
				dialog.setMessage("Đang tìm kiếm...");
				dialog.show();
				new search().execute((Void) null);

			}

			InputMethodManager inputMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMgr.toggleSoftInput(1, 0);
			load = false;
			return true;
		} else {
			load = true;
		}

		return false;
	}

	protected class search extends AsyncTask<Void, Void, Void> {
		ArrayList<ItemBienbao> items;

		@Override
		protected void onPostExecute(Void v) {

			Log.d("******", "post execute");

			dialog.dismiss();
			if (items.isEmpty() && list_bienhientai.isEmpty()
					&& list_khac.isEmpty()) {
				Log.d("items.empty", "empty");
				setView();
				showDialogNotInformation();
			} else {
				UnsetDetailBienbao();
				setViewSearch();
			}
			// initData(list_item_bienbao);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			items = searchItem();
			return null;
		}
	}// end search

	private boolean search(String phase, String title) {
		Log.d("" + phase, "" + title);
		String t = convert(title.toLowerCase());
		if (t.toLowerCase().indexOf(convert(phase).trim()) != -1)
			return true;
		return false;
	}

	public void setViewSearch() {
		initData(list_search);
		initData_showAll(list_search);
		Log.d("setview search", "setview search");
		if (bienhientai == 0) {
			list1.setAdapter(new BienbaoListAdapter(this,
					R.layout.itemviewbienbao, list_search, map));
			search = false;
			list_for_adapter = list_search;
		} else {
			textheader.setText(getTitle(bienhientai));
			list1.setAdapter(new BienbaoListSearchAdapter(this,
					R.layout.itemviewbienbao, combine(), map));
			search = true;
		}
	}

	boolean search = false;

	private String getTitle(int bienhientai2) {

		switch (bienhientai2) {
		case 1:
			return "Biển cấm";

		case 2:
			return "Biển nguy hiểm";

		case 3:
			return "Biển hiệu lệnh";

		case 4:
			return "Biển chỉ dẫn";
		case 5:
			return "Biển phụ";
		case 6:
			return "Vạch kẻ";
		}
		return null;
	}

	private ArrayList<ItemBienbao> combine() {
		ArrayList<ItemBienbao> l = new ArrayList<ItemBienbao>();
		for (ItemBienbao item : list_bienhientai) {
			l.add(item);
		}
		ItemBienbao it = new ItemBienbao();
		it.ten_bien_bao = "khac";
		l.add(it);
		for (ItemBienbao item : list_khac) {
			l.add(item);
		}
		Log.d("so ket qua", "" + l.size());
		list_for_adapter = l;
		return l;
	}

	ArrayList<ItemBienbao> list_search = new ArrayList<ItemBienbao>();

	ArrayList<ItemBienbao> list_bienhientai = new ArrayList<ItemBienbao>();
	ArrayList<ItemBienbao> list_khac = new ArrayList<ItemBienbao>();

	public ArrayList<ItemBienbao> searchItem() {
		list_search.clear();
		list_bienhientai.clear();
		list_khac.clear();

		for (ItemBienbao itemBienbao : list_item_bienbao) {

			Log.d("Ten bien bao", "" + itemBienbao.ten_bien_bao);
			if (search(pharse, itemBienbao.ten_bien_bao)) {

				// Log.d("pharse", "" + pharse);
				// Log.d("ten bien bao", "" + itemBienbao.ten_bien_bao);
				if (bienhientai == 0) {

					list_search.add(itemBienbao);
				} else {
					if (Integer.parseInt(itemBienbao.phan_loai) == bienhientai) {
						list_bienhientai.add(itemBienbao);
					} else {
						list_khac.add(itemBienbao);
					}
				}
			}
		}

		Log.d("tim thay", "" + list_search.size());
		Log.d("list_bienhientai", "" + list_bienhientai.size());
		Log.d("list_khac", "" + list_khac.size());
		return list_search;
	}

	private String convert(String title) {
		for (String s : astr) {
			title = title.replace(s, "a");
		}
		for (String s : dstr) {
			title = title.replace(s, "d");
		}
		for (String s : estr) {
			title = title.replace(s, "e");
		}
		for (String s : istr) {
			title = title.replace(s, "i");
		}

		for (String s : ustr) {
			title = title.replace(s, "u");
		}
		for (String s : ostr) {
			title = title.replace(s, "o");
		}
		for (String s : ystr) {
			title = title.replace(s, "y");
		}

		return title;
	}

	String dstr[] = { "đ" };
	String astr[] = { "á", "à", "ạ", "ã", "ả", "â", "ấ", "ầ", "ậ", "ẫ", "ẩ",
			"ă", "ắ", "ằ", "ặ", "ẵ", "ẳ" };
	String estr[] = { "é", "è", "ẹ", "ẽ", "ẻ", "ê", "ế", "ề", "ệ", "ễ", "ể" };
	String istr[] = { "í", "ì", "ị", "ĩ", "ỉ" };
	String ustr[] = { "ú", "ù", "ụ", "ũ", "ủ", "ư", "ứ", "ừ", "ự", "ữ", "ử" };

	String ostr[] = { "ó", "ò", "ọ", "õ", "ỏ", "ô", "ố", "ồ", "ộ", "ỗ", "ổ",
			"ơ", "ớ", "ờ", "ợ", "ỡ", "ở" };

	String ystr[] = { "ỷ", "ỳ", "ỵ", "ỹ", "ỷ" };

	// ///////////////////read data and adđto sqlite
	ArrayList<itembienbao> listallbienbao = new ArrayList<itembienbao>();

	class itembienbao {
		String id = null;
		String ten = null;
		String noidung = null;
		String tenanh = null;
		int phanloai = -1;
	}

	// ////////////////////////// init hashmap for image
	HashMap<String, Integer> hash = new HashMap<String, Integer>();

	// ////////////////////// hide softkeyboard

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Rect rectgle = new Rect();
		Window window = getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		if (rectgle.bottom < height)
			Tab.tabwidget.setVisibility(TabWidget.GONE);

		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onDestroy() {
		isrunning = false;
		super.onDestroy();
	}

	boolean isactive = true;
	boolean isrunning = true;

	class kbcheck extends Thread {
		@Override
		public void run() {
			while (isrunning) {
				try {
					if (isactive)
						exe();
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	boolean xulyclickitem = true;
	Handler handler = new Handler();

	private void exe() {
		handler.post(new Runnable() {
			public void run() {
				Rect rectgle = new Rect();
				Window window = getWindow();
				window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
				if (rectgle.bottom < height) {
					Tab.tabwidget.setVisibility(TabWidget.GONE);
					xulyclickitem = false;
				} else {
					xulyclickitem = true;
					Tab.tabwidget.setVisibility(TabWidget.VISIBLE);
				}
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			inputMgr.hideSoftInputFromInputMethod(header1.getWindowToken(), 0);
		}
		return super.onTouchEvent(event);
	}

	private ItemView getsttview(int sttview) {
		ItemView item = null;

		switch (sttview) {
		// bieen cam 46
		case 0:
			item = new ItemView(0, 45);
			break;
//		case 1:
//			item = new ItemView(9, 17);
//			break;
//		case 2:
//			item = new ItemView(18, 26);
//			break;
//		case 3:
//			item = new ItemView(27, 35);
//			break;
//		case 4:
//			item = new ItemView(36, 44);
//			break;
//		case 5:
//			item = new ItemView(45, 45);
//			break;

		// bien nguy hiem
		case 1:
			item = new ItemView(46, 97);
			break;
//		case 7:
//			item = new ItemView(55, 63);
//			break;
//		case 8:
//			item = new ItemView(64, 72);
//			break;
//		case 9:
//			item = new ItemView(73, 81);
//			break;
//		case 10:
//			item = new ItemView(82, 90);
//			break;
//		case 11:
//			item = new ItemView(91, 97);
//			break;
		// bien hieu lenh
		case 2:
			item = new ItemView(98, 115);
			break;
//		case 13:
//			item = new ItemView(107, 115);
//			break;
		// bien chi dan 44
		case 3:
			item = new ItemView(116, 159);
			break;
//		case 15:
//			item = new ItemView(125, 133);
//			break;
//		case 16:
//			item = new ItemView(134, 142);
//			break;
//		case 17:
//			item = new ItemView(116, 124);
//			break;
//		case 18:
//			item = new ItemView(125, 133);
//			break;
//		case 19:
//			item = new ItemView(134, 142);
//			break;
//		case 20:
//			item = new ItemView(143, 151);
//			break;
//		case 21:
//			item = new ItemView(152, 159);
//			break;
		// bien phu 15

		case 4:
			item = new ItemView(160, 174);
			break;
//		case 23:
//			item = new ItemView(169, 174);
//			break;
		// bien vach ke 32

		case 5:
			item = new ItemView(175, 206);
			break;
//		case 25:
//			item = new ItemView(184, 192);
//			break;
//		case 26:
//			item = new ItemView(193, 201);
//			break;
//		case 27:
//			item = new ItemView(202, 206);
//			break;
		default:
			break;
		}
		return item;
	}

	class ItemView {
		int from;
		int to;

		public ItemView(int from, int to) {
			this.from = from;
			this.to = to;
		}
	}
}