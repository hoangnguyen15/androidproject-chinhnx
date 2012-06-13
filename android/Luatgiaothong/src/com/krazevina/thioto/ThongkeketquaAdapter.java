package com.krazevina.thioto;

import java.util.ArrayList;


import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.Itemthongke;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThongkeketquaAdapter extends BaseAdapter {

	ArrayList<Itemthongke> list_cauhoi;
	Context c;

	public ThongkeketquaAdapter(Context context, int textViewResourceId,
			ArrayList<Itemthongke> objects) {
		super();
		list_cauhoi = objects;
		c = context;
		li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d("Create BodeAdapter", "BodeAdapter");
		Log.d("num item of list bien bao ", "" + list_cauhoi.size());
	}

	LayoutInflater li;
	TextView cauhoi;
	TextView noidung;
	
	ImageView image;
	int x = 0;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View itemView = convertView;
		
		
		
		Log.d("position of bien bao ", "" + position);
		Itemthongke item = list_cauhoi.get(position);
	
        
		
		if (itemView == null) {
			itemView = li.inflate(R.layout.itemviewkq, parent, false);
		}

		TextView ngay=(TextView)itemView.findViewById(R.id.ngay);
		TextView gio=(TextView)itemView.findViewById(R.id.gio);
		TextView thoigianlambai=(TextView)itemView.findViewById(R.id.thoigian);
		TextView tyledung=(TextView)itemView.findViewById(R.id.tyledung);
		
		ngay.setText(item.ngay);
		gio.setText(item.gio);
		thoigianlambai.setText(item.thoigianlambai);
		tyledung.setText(item.tyledung);
		
		return itemView;
	}

	@Override
	public int getCount() {
		return list_cauhoi.size();
	}

	@Override
	public Object getItem(int position) {
		return list_cauhoi.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
