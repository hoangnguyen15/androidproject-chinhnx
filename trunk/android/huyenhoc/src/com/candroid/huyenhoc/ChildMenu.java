package com.candroid.huyenhoc;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.candroid.objects.ChildMenus;
import com.candroid.objects.DigitalLoungeParser;
import com.candroid.objects.Global;
import com.candroid.objects.Groups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ChildMenu extends Activity implements OnClickListener,OnItemClickListener{
	ListView lstChildmenu;
	ChildMenu childmenu;
	ChildMenus childmenus;
	LinearLayout btnBack;
	String[]stype;
	private DigitalLoungeParser parser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.childmenu);
		lstChildmenu = (ListView) findViewById(R.id.lstChildMenu);
		btnBack = (LinearLayout)findViewById(R.id.btnBack);
		lstChildmenu.setDivider(null);
		lstChildmenu.setDividerHeight(0);
		stype = getResources().getStringArray(R.array.optiontype);
		childmenus = new ChildMenus();
		
		parser = new DigitalLoungeParser();

		try {
			parser.parseXML();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		for(int i = 0;i<Global.groups.count();i++){
			Log.d("serr",""+Global.groups.getItem(i).getServiceName());
		}
		
		int type = getIntent().getIntExtra("type", 0);
		Log.e("ZZZ", ""+type);
		
		for(int i =0;i<8;i++){
			childmenu = new ChildMenu();
			childmenu.setTitle(stype[i]);
			childmenus.addItem(childmenu);
		}
		
		ChildMenuAdapter adapter = new ChildMenuAdapter(this, Global.groups);
		lstChildmenu.setAdapter(adapter);
		lstChildmenu.setOnItemClickListener(this);
		btnBack.setOnClickListener(this);

	}

	public class ChildMenuAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		
		public ChildMenuAdapter(Context context, Groups groups){
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Global.groups.count();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return Global.groups.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			  if (convertView == null) {
			   convertView = mInflater.inflate(R.layout.itemchildmenu, null);
			   holder = new ViewHolder();
			   holder.txt = (TextView) convertView.findViewById(R.id.item_txt);
			   holder.line = (LinearLayout)convertView.findViewById(R.id.item_line);
			   convertView.setTag(holder);
			  } else {
			   holder = (ViewHolder) convertView.getTag();
			  }
			  
			  holder.txt.setText(Global.groups.getItem(position).getServiceName());

			  return convertView;
			 }
	}
		static class ViewHolder {
			TextView txt;
			LinearLayout line;
		}
		@Override
		public void onClick(View v) {
			if(v.getId() ==btnBack.getId()){
				finish();
			}
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int positon,
				long id) {
			if(parent.getId() == lstChildmenu.getId()){
				Intent i = new Intent(ChildMenu.this,Details.class);
				i.putExtra("type", positon);
				startActivity(i);
				Log.d("postion",""+positon);
				
			}
			
		}
}
