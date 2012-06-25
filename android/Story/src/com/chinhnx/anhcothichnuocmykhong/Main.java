package com.chinhnx.anhcothichnuocmykhong;

import java.util.Vector;

import com.chinhnx.objects.Global;
import com.chinhnx.objects.ReadData;
import com.chinhnx.objects.Story;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Main extends Activity implements OnClickListener,OnItemClickListener {
    ListView lst;
    TextView txtTitle;
    Button btnBookMark;
    int pos,y;
    int book[];
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        lst = (ListView)findViewById(R.id.lst);
        txtTitle = (TextView)findViewById(R.id.title);
        btnBookMark = (Button)findViewById(R.id.btnbookmark);
        
        try{
        	ReadData readData = new ReadData(this);
        	Global.vt = readData.getData();
            readData.recycle();
        }catch (Exception e) {
        	e.printStackTrace();
		}
        
        TitleAdapter adapter = new TitleAdapter(this,Global.vt);
		lst.setAdapter(adapter);
		lst.setOnItemClickListener(this);
		btnBookMark.setOnClickListener(this);
		
		SharedPreferences sp = getSharedPreferences("a", MODE_PRIVATE);
		pos = sp.getInt("pos",0);
		y =sp.getInt("y", 0);

		if(pos == 0 && y == 0){
			btnBookMark.setVisibility(View.GONE);
		}else{
			btnBookMark.setVisibility(View.VISIBLE);
		}
		
        
    }
    
	public class TitleAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		
		public TitleAdapter(Context context,Vector<Story> vt){
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			try{
				return Global.vt.size();
			}catch (Exception e) {
				try{
					ReadData readData = new ReadData(Main.this);
		        	Global.vt = readData.getData();
		            readData.recycle();
		            return Global.vt.size();
				}catch (Exception ex) {
					return 0;
				}
			}
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
			  
			  String title = Global.vt.elementAt(position).title;
			  if(title.length()>30){
				  title = title.substring(0, 30)+"...";
			  }
			  holder.txt.setText(title);
			  return convertView;
			 }
	}
	static class ViewHolder {
		TextView txt;
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnBookMark.getId()){
			Intent i = new Intent(this,Content.class);
			Bundle b = new Bundle();
			b.putInt("pos",pos);
			b.putInt("y",y);
			b.putBoolean("bookmark", true);
			i.putExtras(b);
			startActivityForResult(i,1);
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		if(parent.getId() == lst.getId()){
			Intent i = new Intent();
			i.putExtra("position", position);
			i.setClass(this, Content.class);
			startActivityForResult(i,1);
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1){
			SharedPreferences sp = getSharedPreferences("a", MODE_PRIVATE);
			pos = sp.getInt("pos",0);
			y =sp.getInt("y", 0);

			if(pos == 0 && y == 0 && !Global.bookmarked){
				btnBookMark.setVisibility(View.GONE);
			}else{
				btnBookMark.setVisibility(View.VISIBLE);
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Global.bookmarked = false;
		Global.vt = null;
	}
}