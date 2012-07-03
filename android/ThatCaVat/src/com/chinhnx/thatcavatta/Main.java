package com.chinhnx.thatcavatta;

import java.util.Vector;

import com.airpush.android.Airpush;
import com.chinhnx.objects.Global;
import com.chinhnx.objects.Tie;



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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends Activity implements OnItemClickListener{
    /** Called when the activity is first created. */
	String[] names,descriptions;
	Tie tie;
	Vector<Tie> vtTie;
	ListView list;
	TextView txtStep;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Airpush(getApplicationContext(), Global.APP_ID,Global.API_KEY,false,true,true);
        setContentView(R.layout.main);
        
        list = (ListView)findViewById(R.id.list);
        
        
        names = getResources().getStringArray(R.array.names);
        descriptions = getResources().getStringArray(R.array.descriptions);
        int imgicon[] = {R.drawable.ico_atlantic,R.drawable.ico_diagonal,R.drawable.ico_four_in_hand,
        		R.drawable.ico_half_windsor,R.drawable.ico_kelvin,R.drawable.ico_oriental,R.drawable.ico_persian,
        		R.drawable.ico_plattsburg,R.drawable.ico_pratt,R.drawable.ico_simple_double,R.drawable.ico_st_andrew,
        		R.drawable.ico_windsor,R.drawable.ico_bowtie};
        
        vtTie = new Vector<Tie>();
        for(int i = 0;i<names.length;i++){
        	tie = new Tie();
        	tie.img = imgicon[i];
        	tie.name = names[i];
        	tie.description = descriptions[i];
        	vtTie.add(tie);
        }
        
        TitleAdapter adapter = new TitleAdapter(this, vtTie);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        
    }
    
	public class TitleAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		
		public TitleAdapter(Context context,Vector<Tie> vtTie){
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return vtTie.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return vtTie.elementAt(position);
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
			   holder.img = (ImageView)convertView.findViewById(R.id.itemimg);
			   holder.name = (TextView) convertView.findViewById(R.id.itemname);
			   holder.description = (TextView) convertView.findViewById(R.id.itemdescription);
			   convertView.setTag(holder);
			  } else {
			   holder = (ViewHolder) convertView.getTag();
			  }
			  
			  String name = vtTie.elementAt(position).name;
			  String description = vtTie.elementAt(position).description;
			  int img = vtTie.elementAt(position).img;
			  
			  
			  holder.name.setText(name);
			  holder.description.setText(description);
			  holder.img.setImageResource(img);
			  
			  return convertView;
			 }
	}
	static class ViewHolder {
		ImageView img;
		TextView name,description;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg0.getId() == list.getId()){
			Intent i = new Intent();
			i.setClass(this, Details.class);
			i.putExtra("position", arg2);
			startActivity(i);
		}
		
	}
}