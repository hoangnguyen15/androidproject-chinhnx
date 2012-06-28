package com.chinhnx.thatcavatta;

import java.util.Vector;

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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        list = (ListView)findViewById(R.id.list);
        
        names = getResources().getStringArray(R.array.names);
        descriptions = getResources().getStringArray(R.array.descriptions);
        int imgicon[] = {R.drawable.atlantic,R.drawable.diagonal,R.drawable.four_in_hand,
        		R.drawable.half_windsor,R.drawable.kelvin,R.drawable.oriental,R.drawable.persian,
        		R.drawable.plattsburg,R.drawable.pratt,R.drawable.simple_double,R.drawable.st_andrew,
        		R.drawable.windsor,R.drawable.bowtie};
        
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