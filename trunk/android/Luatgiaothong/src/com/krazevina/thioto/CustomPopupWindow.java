/**
 * Lorensius W. L. T
 * 
 * http://www.londatiga.net
 * 
 * lorenz@londatiga.net 
 */

package com.krazevina.thioto;

import java.util.ArrayList;

import com.krazevina.thioto.R;

import android.content.Context;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;

public class CustomPopupWindow {
	protected final View anchor;
	protected final PopupWindow window;
	private View root;
	private Drawable background = null;
	protected final WindowManager windowManager;
	private Context context;

	public CustomPopupWindow(View anchor, Context c) {
		this.anchor = anchor;
		this.window = new PopupWindow(anchor.getContext());

		this.context = c;
		window.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					CustomPopupWindow.this.window.dismiss();

					return true;
				}

				return false;
			}
		});

		windowManager = (WindowManager) anchor.getContext().getSystemService(
				Context.WINDOW_SERVICE);

		onCreate();
	}



	protected void onCreate() {

	}



	ArrayList<String> list_bb;

	private ArrayList<String> get_list_bienbao() {
		list_bb = new ArrayList<String>();
		list_bb.add("Tất cả");
		list_bb.add("Biển cấm ");
		list_bb.add("Biển nguy hiểm");
		list_bb.add("Biển hiệu lệnh");
		list_bb.add("Biển chỉ dẫn");
		list_bb.add("Biển phụ");
		list_bb.add("Vạch kẻ ");
		Log.d("so biển báo là: ", "" + list_bb.size());
		return list_bb;
	}

	protected void onShow() {
	}

	protected void preShow() {
		if (root == null) {
			throw new IllegalStateException(
					"setContentView was not called with a view to display.");
		}

		onShow();

		if (background == null) {
			window.setBackgroundDrawable(new BitmapDrawable());
		} else {
			window.setBackgroundDrawable(background);
		}

		window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
	//	window.setTouchable(true);
		window.setFocusable(true);
		window.setOutsideTouchable(true);

		window.setContentView(root);
	}

	public void setBackgroundDrawable(Drawable background) {
		this.background = background;
	}

	ListView listview;

	public void setContentView(View root) {
		this.root = root;

		window.setContentView(root);
		listview = (ListView) root.findViewById(R.id.listView1);
		
		listview.setDivider(null);
		listview.setDividerHeight(0);
		listview.setFadingEdgeLength(0);
		listview.setFocusable(true);
		listview.setFocusableInTouchMode(false);
		listview.setAdapter(new PopupAdapter(context, R.layout.popitem, get_list_bienbao()));
		setOnItemClick();

	}

	private void setOnItemClick() {
		listview.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int pos=listview.pointToPosition(0, (int) event.getY());
				
				
				
				for(int i=0;i<=6;i++){
					(listview.getChildAt(i).findViewById(R.id.linearLayout1)).setBackgroundDrawable(null);
				}
				
				Log.d("vi tri", ""+pos);
				if(pos==0){
					(listview.getChildAt(pos).findViewById(R.id.linearLayout1)).setBackgroundDrawable(
							context.getResources().getDrawable(R.drawable.option_up_highlight));
				}else if(pos==6){
					(listview.getChildAt(pos).findViewById(R.id.linearLayout1)).setBackgroundDrawable(
							context.getResources().getDrawable(R.drawable.option_down_highlight));
				}else{
					if(listview.getChildAt(pos)!=null)
					(listview.getChildAt(pos).findViewById(R.id.linearLayout1)).setBackgroundDrawable(
							context.getResources().getDrawable(R.drawable.option_mid_highlight_copy));
				}
				
				return false;
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,long arg3) {
				
				
				
				if(position==0){
					
					((Bienbao)context).setTextButton("Tất cả");
					bb=0;
					new set().execute((Void)null);
					dismiss();
				}else if(position==1){
					((Bienbao)context).setTextButton("Biển cấm");
					bb=1;
					new set().execute((Void)null);
					dismiss();
				}else if(position==2){
					((Bienbao)context).setTextButton("Nguy hiểm");
					bb=2;
					new set().execute((Void)null);
					dismiss();
				}else if(position==3){
					((Bienbao)context).setTextButton("Hiệu lệnh");
					bb=3;
					new set().execute((Void)null);
					dismiss();
				}else if(position==4){
					((Bienbao)context).setTextButton("Chỉ dẫn");
					bb=4;
					new set().execute((Void)null);
					
					dismiss();
				}else if(position==5){
					((Bienbao)context).setTextButton("Biển phụ");
					bb=5;
					new set().execute((Void)null);
					dismiss();
				}else if(position==6){
					((Bienbao)context).setTextButton("Vạch kẻ");
					bb=6;
					new set().execute((Void)null);
					dismiss();
				}
				
				
			}
		});
	}
	 int bb=0;
	protected class set extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {
			((Bienbao) context).setViewBienbaos(bb);
			
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			return null;
		}
	}

	public void setContentView(int layoutResID) {
		LayoutInflater inflator = (LayoutInflater) anchor.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		setContentView(inflator.inflate(layoutResID, null));
	}

	public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
		window.setOnDismissListener(listener);
	}

	public void showDropDown() {
		showDropDown(0, 0);
	}

	public void showDropDown(int xOffset, int yOffset) {
		preShow();

		window.setAnimationStyle(R.style.Animations_PopDownMenu_Left);
		window.showAsDropDown(anchor, xOffset, yOffset);
	}

	public void showLikeQuickAction() {
		showLikeQuickAction(0, 0);
	}

	public void showLikeQuickAction(int xOffset, int yOffset) {
		preShow();

		window.setAnimationStyle(R.style.Animations_PopUpMenu_Center);

		int[] location = new int[2];
		anchor.getLocationOnScreen(location);

		Rect anchorRect = new Rect(location[0], location[1], location[0]
				+ anchor.getWidth(), location[1] + anchor.getHeight());

		root.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootWidth = root.getMeasuredWidth();
		int rootHeight = root.getMeasuredHeight();

		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		// int screenHeight = windowManager.getDefaultDisplay().getHeight();

		int xPos = ((screenWidth - rootWidth) / 2) + xOffset;
		int yPos = anchorRect.top - rootHeight + yOffset;

		// display on bottom
		if (rootHeight > anchor.getTop()) {
			yPos = anchorRect.bottom + yOffset;

			window.setAnimationStyle(R.style.Animations_PopDownMenu_Center);
		}

		window.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
	}

	public void dismiss() {
		window.dismiss();
	}
}