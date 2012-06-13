package com.krazevina.thioto;

import java.util.ArrayList;

import com.krazevina.thioto.R;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class DialogBienbao extends Dialog {

	ListView list;
	Context c;
	public DialogBienbao(Context context) {
		super(context);
	    this.c=context;	
	}
	@Override
	public void onCreate(Bundle saved){
		super.onCreate(saved);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 this.setContentView(R.layout.dialogbienbao);	
		 list=(ListView)findViewById(R.id.listbienbaodialog);
		 list.setAdapter(new DialogBienbaoAdapter(this.c, R.layout.dialogbienbaoitem, get_list_bienbao()));
		 setAtributeList(list);
		 setOnItemClickOfList();
	}
	private void setOnItemClickOfList() {

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				LinearLayout l=(LinearLayout)arg1.findViewById(R.id.linearlayout_bienbao);
				l.setBackgroundDrawable(c.getResources().getDrawable(R.drawable.option_mid_highlight_copy));
				((Bienbao)c).setTextButton(list_bb.get(position));
				DialogBienbao.this.dismiss();
				
			}
		});

	}
	public void setView(){
		list.setAdapter(new DialogBienbaoAdapter(this.c, R.layout.dialogbienbaoitem, get_list_bienbao()));
	}
	
	ArrayList<String> list_bb;
	private ArrayList<String> get_list_bienbao() {
		list_bb = new ArrayList<String>();
		list_bb.add("Tất cả");
		list_bb.add("Biển cấm ");
		list_bb.add("Nguy hiểm");
		list_bb.add("hiệu lệnh");
		list_bb.add("Chỉ dẫn");
		list_bb.add("Biển phụ");
		list_bb.add("Vạch kẻ ");
		Log.d("so biển báo là: ", "" + list_bb.size());
		return list_bb;
	}

	private void setAtributeList(ListView list){
		list.setDivider(null);
		list.setDividerHeight(0);
		list.setFadingEdgeLength(0);
		list.setFocusable(false);
		list.setFocusableInTouchMode(false);
	}
	
	
	
}
