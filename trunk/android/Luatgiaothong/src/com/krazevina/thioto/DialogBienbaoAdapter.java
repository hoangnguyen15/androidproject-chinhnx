package com.krazevina.thioto;

import java.util.ArrayList;

import com.krazevina.thioto.R;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;




public class DialogBienbaoAdapter extends ArrayAdapter<String> {
	ArrayList< String> list_bienbao;
	Context c;
	public DialogBienbaoAdapter(Context context, int textViewResourceId,ArrayList<String> objects) {
		super(context, textViewResourceId,objects);
		list_bienbao=objects;
		c=context;
		li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d("BienbaoListAdapter", "BienbaoListAdapter");
	}

    LayoutInflater li;
	TextView ten_bienbao;
	TextView noidung_bienbao;
	ImageView image;
    int x=0;
    
	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		View itemView=convertView;
		Log.d("dialog Bien bao getview ", ""+position);
		
	   if(itemView==null){
		   itemView=li.inflate(R.layout.dialogbienbaoitem, parent,false);
	   }
	   ten_bienbao=(TextView)itemView.findViewById(R.id.text_bienbao);
	   ten_bienbao.setText(list_bienbao.get(position));
		return itemView;
	}




	
	
}
