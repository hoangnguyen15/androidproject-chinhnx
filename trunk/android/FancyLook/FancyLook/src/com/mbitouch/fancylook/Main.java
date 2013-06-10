package com.mbitouch.fancylook;

import java.util.ArrayList;
import com.mbitouch.imagemanager.BaseActivity;
import com.mbitouch.imagemanager.Constants;
import com.mbitouch.objects.Global;
import com.mbitouch.objects.Image;
import com.mbitouch.slideanim.CollapseAnimation;
import com.mbitouch.slideanim.ExpandAnimation;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main extends BaseActivity implements OnItemClickListener{

	// Declare
	private LinearLayout slidingPanel;
	private boolean isExpanded=false;
	private DisplayMetrics metrics;
	private ListView listView;
	private LinearLayout headerPanel;
//	private RelativeLayout menuPanel;
	private LinearLayout menuPanel;
	private int panelWidth;
	private Button menuViewButton;
	private TextView title;
	Button btnHot,btnNew;
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	
	ArrayList<Image> arrImage;
	int currentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Initialize
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * 0.75);

		headerPanel = (LinearLayout) findViewById(R.id.header);
		headerPanelParameters = (LinearLayout.LayoutParams) headerPanel.getLayoutParams();
		headerPanelParameters.width = metrics.widthPixels;
		headerPanel.setLayoutParams(headerPanelParameters);

		menuPanel = (LinearLayout) findViewById(R.id.menuPanel);
		menuPanelParameters = (FrameLayout.LayoutParams) menuPanel.getLayoutParams();
		menuPanelParameters.width = panelWidth;
		menuPanel.setLayoutParams(menuPanelParameters);

		slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);

		listView = (ListView) findViewById(R.id.lstImage);
		listViewParameters = (LinearLayout.LayoutParams) listView.getLayoutParams();
		listViewParameters.width = metrics.widthPixels;
		listView.setLayoutParams(listViewParameters);
		
		
		// Slide the Panel
		btnHot = (Button) findViewById(R.id.btnHot);
		btnNew = (Button) findViewById(R.id.btnNew);
		title = (TextView)findViewById(R.id.txtTitle);
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		arrImage = new ArrayList<Image>();
		for(int i=0;i<10;i++){
			Image image = new Image();
			switch (i) {
			case 0:
				image.setUrlImage("http://mbitouch.com/demo/fancylook/wp-content/uploads/2013/05/hot-girl1.jpg");
				break;
			case 1:
				image.setUrlImage("http://mbitouch.com/demo/fancylook/wp-content/uploads/2013/05/huong1.jpg");
				break;
			case 2:
				image.setUrlImage("http://mbitouch.com/demo/fancylook/wp-content/uploads/2013/05/SinhVienIT.NET-xkcn-by-sinhvienit.net-2008-.jpg");
				break;
			case 3:
				image.setUrlImage("http://mbitouch.com/demo/fancylook/wp-content/uploads/2013/05/EX1.jpg");
				break;
			case 4:
				image.setUrlImage("http://mbitouch.com/demo/fancylook/wp-content/uploads/2013/05/Van_Anh.jpg");
				break;
			case 5:
				image.setUrlImage("http://mbitouch.com/demo/fancylook/wp-content/uploads/2013/05/50b6d2d9_4d565802_anh-girl-xinh-4.jpg");
				break;
			default:
				image.setUrlImage("http://mbitouch.com/demo/fancylook/wp-content/uploads/2013/05/girl-xinh-22.jpg");
				break;
			}
				
			
			image.setName("Toc dai");
			image.setComment(10);
			image.setLike(15);
			arrImage.add(image);
		}
		Global.arrImage = arrImage;
		ImageAdapter adapter = new ImageAdapter(arrImage);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		
		currentView = 1;
		title.setText("Ảnh hot");
		
		btnHot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toogle();
				if(currentView!=1){
					arrImage = new ArrayList<Image>();
					for(int i=0;i<20;i++){
						Image image = new Image();
						image.setUrlImage("http://mbitouch.com/demo/fancylook/wp-content/uploads/2013/05/SinhVienIT.NET-xkcn-by-sinhvienit.net-1435-.jpg");
						image.setName("Toc dai");
						image.setComment(10);
						image.setLike(15);
						arrImage.add(image);
					}
					ImageAdapter adapter = new ImageAdapter(arrImage);
					listView.setAdapter(adapter);
					currentView = 1;
					title.setText("Ảnh hot");
				}
			}
		});

		btnNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toogle();
				if(currentView!=2){
					arrImage = new ArrayList<Image>();
					for(int i=0;i<20;i++){
						Image image = new Image();
						image.setUrlImage("http://mbitouch.com/demo/fancylook/wp-content/uploads/2013/05/SinhVienIT.NET-xkcn-by-sinhvienit.net-1435-.jpg");
						image.setName("Toc nau");
						image.setComment(10);
						image.setLike(15);
						arrImage.add(image);
					}
					ImageAdapter adapter = new ImageAdapter(arrImage);
					listView.setAdapter(adapter);
					currentView = 2;
					title.setText("Ảnh mới");
				}
			}
		});
		menuViewButton = (Button) findViewById(R.id.menuViewButton);
		menuViewButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toogle();
			}
		});
		
	}
	
	public void Toogle(){
		if (!isExpanded) {
			isExpanded = true;
			// Expand
			new ExpandAnimation(slidingPanel, panelWidth,
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);
		} else {
			isExpanded = false;
			// Collapse
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f,
					0, 0.0f);
		}
	}
	
	class ImageAdapter extends BaseAdapter {
		private ArrayList<Image> arrImage;
		private class ViewHolder {
			public TextView name,like,comment;
			public ImageView image;
		}
		public ImageAdapter(ArrayList<Image> arrImage){
			this.arrImage = arrImage;
		}
		@Override
		public int getCount() {
			return arrImage.size();
		}
		@Override
		public Object getItem(int position) {
			return position;
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item, parent, false);
				holder = new ViewHolder();
				holder.name = (TextView) view.findViewById(R.id.item_name);
				holder.like = (TextView) view.findViewById(R.id.item_like);
				holder.comment = (TextView) view.findViewById(R.id.item_comment);
				holder.image = (ImageView) view.findViewById(R.id.item_img);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.name.setText(arrImage.get(position).getName());
			holder.like.setText(""+arrImage.get(position).getLike());
			holder.comment.setText(""+arrImage.get(position).getComment());
			imageLoader.displayImage(arrImage.get(position).getUrlImage(), holder.image, Constants.options, Constants.animateFirstListener);
			return view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if(parent.getId() == listView.getId()){
			Intent intent = new Intent();
			intent.setClass(Main.this,ImageViewPager.class);
			intent.putExtra("position", position);
			startActivity(intent);
		}
	}

	
	

}
