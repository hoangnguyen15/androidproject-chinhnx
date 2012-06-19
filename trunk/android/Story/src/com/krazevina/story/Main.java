package com.krazevina.story;

import java.util.Vector;

import com.krazevina.objects.Global;
import com.krazevina.objects.ReadData;
import com.krazevina.objects.Story;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Main extends Activity implements OnClickListener,OnItemClickListener {
    ListView lst;
    TextView txtTitle;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        lst = (ListView)findViewById(R.id.lst);
        txtTitle = (TextView)findViewById(R.id.title);
        
        ReadData readData= new ReadData(this);
        Global.vt = readData.getData();
        
        TitleAdapter adapter = new TitleAdapter(this,Global.vt);
		lst.setAdapter(adapter);
		lst.setOnItemClickListener(this);
        
    }
    
	public class TitleAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		
		public TitleAdapter(Context context,Vector<Story> vt){
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Global.vt.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return Global.vt.elementAt(position);
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
			   convertView = mInflater.inflate(R.layout.item, null);
			   holder = new ViewHolder();
			   holder.txt = (TextView) convertView.findViewById(R.id.itemTitle);
			   convertView.setTag(holder);
			  } else {
			   holder = (ViewHolder) convertView.getTag();
			  }
			  
			  holder.txt.setText(Global.vt.elementAt(position).title);

			  return convertView;
			 }
	}
	static class ViewHolder {
		TextView txt;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		if(parent.getId() == lst.getId()){
			Intent i = new Intent();
			i.putExtra("position", position);
			i.setClass(this, Content.class);
			startActivity(i);
		}
		
	}
}