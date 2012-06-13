package com.krazevina.thioto;

import java.util.ArrayList;
import java.util.HashMap;

import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemBienbao;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BienbaoGridAdapter extends BaseAdapter {

	ArrayList<ItemBienbao> list_bienbao;
	Context c;
    HashMap<String, Integer> map=null;
    
    int start;
    int end;
	public BienbaoGridAdapter(int start,int end,Context context,
			ArrayList<ItemBienbao> objects,HashMap<String, Integer> m) {
		super();
		list_bienbao = objects;
		c = context;
		li = LayoutInflater.from(context);
		map=m;
	
		this.start=start;
		this.end=end;
		
	}

	LayoutInflater li;
	TextView ten_bienbao;
	ImageView image;


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View itemView = convertView;
		Log.d("start",""+start);
		Log.d("end",""+end);
		ItemBienbao itembb = list_bienbao.get(start+position);
		itemView = li.inflate(R.layout.itemgridbienbao, parent, false);
		image = (ImageView) itemView.findViewById(R.id.image);
		ten_bienbao=(TextView)itemView.findViewById(R.id.tenbienbao);
		ten_bienbao.setText(itembb.id);
		image.setImageResource(map.get(itembb.link_anh));

		
		return itemView;
	}

	@Override
	public int getCount() {
		return end-start+1;
	}

	@Override
	public Object getItem(int position) {
		return list_bienbao.get(start+position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
