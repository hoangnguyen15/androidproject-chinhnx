package com.krazevina.thioto;

import java.util.ArrayList;

import com.krazevina.thioto.R;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LuatAdapter extends ArrayAdapter<Integer> {
	ArrayList< Integer> arr;
	public LuatAdapter(Context context, int textViewResourceId,ArrayList<Integer> objects) {
		super(context, textViewResourceId, objects);
		arr=objects;
		li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d("call list item adapter", "call list item adapter");
	}

    LayoutInflater li;
	TextView text1;
	TextView text2;
	
    int x=0;
    
	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		View itemView=convertView;
	   if(itemView==null){
		   itemView=li.inflate(R.layout.luatitem, null,true);
	   }
	   
	   
		text1=(TextView)itemView.findViewById(R.id.text1);
		text2=(TextView)itemView.findViewById(R.id.text);
    	int stt=arr.get(position);
		text1.setText("Chương "+stt);
		text2.setText(s[position].length()>28?s[position].substring(0,28)+"...":s[position]);
		return itemView;
	}
	
	String s[]={
			"Những quy định chung",
			"Qui tắc giao thông đường bộ",
			"Kết cấu hạ tầng giao thông đường bộ",
			"Phương tiện tham gia giao thông đường bộ",
			"Nguời điều khiển phương tiện tham gia giao thông",
			"Vận tải đường bộ",
			"Quản lý nhà nước về giao thông đường bộ",
			"Điều khoản thi hành"
			};
	
}
