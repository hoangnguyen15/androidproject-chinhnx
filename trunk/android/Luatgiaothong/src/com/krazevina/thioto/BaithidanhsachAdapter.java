package com.krazevina.thioto;

import java.util.ArrayList;

import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemCauhoi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BaithidanhsachAdapter extends BaseAdapter {

	ArrayList<ItemCauhoi> list_cauhoi;
	Context c;

	public BaithidanhsachAdapter(Context context, int textViewResourceId,
			ArrayList<ItemCauhoi> objects) {
		super();
		list_cauhoi = objects;
		c = context;
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}

	LayoutInflater li;
	TextView cauhoi;
	TextView noidung;
	TextView traloi;
	ImageView image;
	int x = 0;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View itemView = convertView;
		
	//	Log.d("position of bien bao ", "" + position);
		ItemCauhoi item = list_cauhoi.get(position);
		
		if (itemView == null) {
			itemView = li.inflate(R.layout.itemviewdanhsachcauhoi, parent, false);
		}

		cauhoi = (TextView) itemView.findViewById(R.id.textcauhoi);
		noidung = (TextView) itemView.findViewById(R.id.noidung);
		traloi=(TextView)itemView.findViewById(R.id.traloi);
		image = (ImageView) itemView.findViewById(R.id.image);
        
		cauhoi.setText("Câu hỏi "+item.idcauhoi);
		noidung.setText(item.cauhoi);
        if(item.dachon==-1)
        	traloi.setVisibility(TextView.GONE);
        else traloi.setText("Đã trả lời : "+getAnswer(item.dachon));
		return itemView;
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
