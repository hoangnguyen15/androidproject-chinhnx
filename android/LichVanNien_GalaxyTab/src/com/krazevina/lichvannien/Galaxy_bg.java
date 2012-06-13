package com.krazevina.lichvannien;

import com.krazevina.objects.Sqlite;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class Galaxy_bg extends Activity implements OnItemClickListener{
	private GridView grid_galaxy_bg;
	int []mThumbIds = {
			R.drawable.wallpaper_color_icon_01,R.drawable.wallpaper_color_icon_02,
			R.drawable.wallpaper_color_icon_03,R.drawable.wallpaper_color_icon_04,
			R.drawable.wallpaper_color_icon_05,R.drawable.wallpaper_color_icon_06,
			R.drawable.wallpaper_color_icon_07
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.galaxy_bg);
		
		
		
		grid_galaxy_bg = (GridView)findViewById(R.id.grid_galaxy_bg);
		grid_galaxy_bg.setAdapter(new ImageAdapter(this));
		grid_galaxy_bg.setOnItemClickListener(this);

	}
	
	public class ImageAdapter extends BaseAdapter {
	    private Context mContext;

	    public ImageAdapter(Context c) {
	        mContext = c;
	    }

	    public int getCount() {
	        return mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(2, 2, 2, 2);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(mThumbIds[position]);
	        return imageView;
	    }

	    // references to our images

	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(parent.getId() == grid_galaxy_bg.getId()){
			Sqlite sql = new Sqlite(this);
			sql.setWallpaper(position+1,null);
			sql.close();
			finish();
		}
	}
}
