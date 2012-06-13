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

public class BienbaoListSearchAdapter extends BaseAdapter {

	ArrayList<ItemBienbao> list_bienbao;
	Context c;
	HashMap<String, Integer> map = null;

	public BienbaoListSearchAdapter(Context context, int textViewResourceId,
			ArrayList<ItemBienbao> list,
	        HashMap<String, Integer> m) {
		super();
		list_bienbao = list;
		c = context;
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		map = m;
	//	Log.d("BienbaoListAdapter", "BienbaoListAdapter");
		Log.d("num item of list bien bao ", "" + list_bienbao.size());
	}

	LayoutInflater li;
	TextView ten_bienbao;
	TextView noidung_bienbao;
	TextView title;
	ImageView image;
	int x = 0;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View itemView = convertView;

	//	Log.d("position of bien bao ", "" + position);
		ItemBienbao itembb = list_bienbao.get(position);
		if (itembb.ten_bien_bao.compareTo("khac") == 0) {
			itemView = li.inflate(R.layout.titlebienbao, parent, false);
			title = (TextView) itemView.findViewById(R.id.title);
			title.setText("Khác");
			return itemView;
		}
		
		// if (itemView == null) {
		itemView = li.inflate(R.layout.itemviewbienbao, parent, false);
		// }

		ten_bienbao = (TextView) itemView.findViewById(R.id.name);
		noidung_bienbao = (TextView) itemView.findViewById(R.id.content);
		image = (ImageView) itemView.findViewById(R.id.image);

		if (itembb.ten_bien_bao.length() >= 20)
			ten_bienbao.setText(itembb.ten_bien_bao.substring(0, 20) + "...");
		else
			ten_bienbao.setText(itembb.ten_bien_bao);

		if (itembb.noi_dung.length() >= 51)
			noidung_bienbao.setText(itembb.noi_dung.substring(0, 50) + "...");
		else
			noidung_bienbao.setText(itembb.noi_dung);
		Log.d("link_anh", "" + itembb.link_anh);

		image.setBackgroundDrawable(this.c.getResources().getDrawable(
				map.get(itembb.link_anh)));

		return itemView;
	}

	@Override
	public int getCount() {
		return list_bienbao.size();
	}

	@Override
	public Object getItem(int position) {
		return list_bienbao.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
