package com.krazevina.thioto;

import java.util.ArrayList;

import com.krazevina.thioto.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PopupAdapter extends BaseAdapter {
	ArrayList<String> list_bienbao;

	public PopupAdapter(Context context, int textViewResourceId,
			ArrayList<String> objects) {
		super();
		list_bienbao = objects;
		li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d("BienbaoSpinnerAdapter", "BienbaoSpinnerAdapter");
	}

	LayoutInflater li;
	TextView text_bienbao;

	int x = 0;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		if (itemView == null) {
			itemView = li.inflate(R.layout.popitem, parent, false);
		}
		text_bienbao = (TextView) itemView.findViewById(R.id.txt_bienbao);
		String ten_bienbao = list_bienbao.get(position);
		text_bienbao.setText(ten_bienbao);
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
