package com.krazevina.thioto;

import java.util.ArrayList;

import com.krazevina.thioto.R;
import com.krazevina.thioto.objects.ItemCauhoi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BodeAdapter extends BaseAdapter {

	ArrayList<ItemCauhoi> list_cauhoi;
	Context c;

	public BodeAdapter(Context context, int textViewResourceId,
			ArrayList<ItemCauhoi> objects) {
		super();
		list_cauhoi = objects;
		c = context;
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d("Create BodeAdapter", "BodeAdapter");
		Log.d("num item of list bien bao ", "" + list_cauhoi.size());
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
		
		
		
		Log.d("position of bien bao ", "" + position);
		ItemCauhoi item = list_cauhoi.get(position);
		if (item.cauhoi.compareTo("title") == 0) {
			itemView = li.inflate(R.layout.itemviewheaderdanhsachcauhoi, parent, false);
			return itemView;
		}
		
		//if (itemView == null) {
			itemView = li.inflate(R.layout.itemviewdanhsachcauhoi, parent, false);
		//}

		cauhoi = (TextView) itemView.findViewById(R.id.textcauhoi);
		noidung = (TextView) itemView.findViewById(R.id.noidung);
		image = (ImageView) itemView.findViewById(R.id.image);
		traloi=(TextView)itemView.findViewById(R.id.traloi);
		traloi.setVisibility(TextView.GONE);
		
		cauhoi.setText("Câu hỏi "+item.idcauhoi);
		noidung.setText(item.cauhoi);
//		image.setBackgroundDrawable(this.c.getResources().getDrawable(
//				Bienbao.getIdImage(itembb.link_anh)));

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
