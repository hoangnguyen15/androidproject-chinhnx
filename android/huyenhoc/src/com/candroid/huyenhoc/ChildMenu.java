package com.candroid.huyenhoc;

import com.candroid.objects.ChildMenus;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.childmenu);
		lstChildmenu = (ListView) findViewById(R.id.lstChildMenu);
		lstChildmenu.setDivider(null);
		lstChildmenu.setDividerHeight(0);
		
		childmenus = new ChildMenus();
		
		for(int i =0;i<10;i++){
			childmenu = new ChildMenu();
			childmenu.setTitle("Cuộc sống");
			childmenus.addItem(childmenu);
		}
		
		ChildMenuAdapter adapter = new ChildMenuAdapter(this, childmenus);
		lstChildmenu.setAdapter(adapter);
		lstChildmenu.setOnItemClickListener(this);

	}

	public class ChildMenuAdapter extends BaseAdapter {
		ChildMenus Achildmenus;
		private LayoutInflater mInflater;
		
		public ChildMenuAdapter(Context context, ChildMenus childmenus){
			mInflater = LayoutInflater.from(context);
			Achildmenus = childmenus;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Achildmenus.count();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return Achildmenus.getItem(position);
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
			  
			  holder.txt.setText(Achildmenus.getItem(position).getTitle());

			  return convertView;
			 }
	}
		static class ViewHolder {
			TextView txt;
			LinearLayout line;
		}
		@Override
		public void onClick(View v) {
			
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int positon,
				long id) {
			if(parent.getId() == lstChildmenu.getId()){
				startActivity(new Intent(ChildMenu.this,Details.class));
				Log.d("postion",""+positon);
				
			}
			
		}
}
