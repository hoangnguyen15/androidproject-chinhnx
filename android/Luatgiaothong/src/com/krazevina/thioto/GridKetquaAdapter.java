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
import android.widget.LinearLayout;
import android.widget.TextView;


public class GridKetquaAdapter extends BaseAdapter {
    private Context mContext;
   ArrayList<ItemCauhoi> listcauhoi;
   LayoutInflater inflat;
    public GridKetquaAdapter(Context c,ArrayList<ItemCauhoi> list) {
        mContext = c;
        this.listcauhoi=list;
       inflat=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     
    }

    public int getCount() {
        return listcauhoi.size();
    }

    public Object getItem(int position) {
        return listcauhoi.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    LinearLayout linear;
    TextView textview;
    ImageView image;
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (convertView == null) { 
          view= inflat.inflate(R.layout.itemviewketqua, null);
        } else {
        	view =  convertView;
        }
       
        linear=(LinearLayout)view.findViewById(R.id.linear);
        textview=(TextView)view.findViewById(R.id.text);
        ItemCauhoi item=listcauhoi.get(position);
        Log.d("cau:"+(position+1),"dachon:"+item.dachon);
        
         if(item.dachon==-1)
            linear.setBackgroundResource(R.drawable.chua_tra_loi);
        else if(item.ketqua.compareTo(getAnswer(item.dachon))==0){
        	
        	linear.setBackgroundResource(R.drawable.tra_loi_dung);
        }else {
        	linear.setBackgroundResource(R.drawable.tra_loi_sai);
        }
        
        textview.setText(""+(position+1));
        
        return view;
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
}