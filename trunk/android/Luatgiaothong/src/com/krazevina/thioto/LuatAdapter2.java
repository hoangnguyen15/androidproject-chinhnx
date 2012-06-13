package com.krazevina.thioto;


import com.krazevina.thioto.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LuatAdapter2 extends BaseAdapter {

	public LuatAdapter2(Context context) {
		super();
		li = LayoutInflater.from(context);
		
	}

    private LayoutInflater li;
	private TextView text1;
	private TextView text2;
	

    
	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		View itemView=convertView;
	   if(itemView==null){
		   itemView=li.inflate(R.layout.luatitem, null,true);
	   }
	   
	   
		text1=(TextView)itemView.findViewById(R.id.text1);
		text2=(TextView)itemView.findViewById(R.id.text);
    
		text1.setText("Điều "+(Toancuc.rangedieu.from+position));
		String tomtat=LuatDetail.getCau(Toancuc.rangedieu.from+position);
		text2.setText(tomtat.substring(0,28)+"...");
		return itemView;
	}
	

	@Override
	public int getCount() {
		
		return (Toancuc.rangedieu.to-Toancuc.rangedieu.from+1);
	}

	@Override
	public Object getItem(int position) {
		    
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
