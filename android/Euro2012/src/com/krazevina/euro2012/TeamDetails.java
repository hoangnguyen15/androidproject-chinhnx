package com.krazevina.euro2012;

import java.util.Vector;

import com.krazevina.objects.Player;
import com.krazevina.objects.Team;
import com.krazevina.objects.sqlite;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TeamDetails extends Activity{
	Team t;
	Vector<Player>players;
	TextView txtteam,txtdesc;
	ListView lv;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teamdetail);
		
		sqlite sql = new sqlite(this);
	    int teamId = getIntent().getIntExtra("idTeam", 155);
	    Log.e("ID", ""+teamId);
	    t = sql.getTeam(teamId);
	    players = sql.getTeamPlayers(teamId);
	    sql.recycle();
	    
	    txtteam = (TextView)findViewById(R.id.txtteam);
	    txtdesc = (TextView)findViewById(R.id.txtdesc);
	    lv = (ListView)findViewById(R.id.lvplayers);
	    
	    txtteam.setText(t.name);
	    txtdesc.setText(t.desc);
	    lv.setAdapter(new adapter());
	};
	
	class adapter extends BaseAdapter{
		LayoutInflater li;
		TextView no,name,pos;
		public adapter() {
			li = LayoutInflater.from(TeamDetails.this);
		}
		@Override
		public int getCount() {
			return players.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView = li.inflate(R.layout.itemteamplayers, null);
			}
			no = (TextView)convertView.findViewById(R.id.no);
			name = (TextView)convertView.findViewById(R.id.name);
			pos = (TextView)convertView.findViewById(R.id.pos);
			
			no.setText(""+players.get(position).number);
			name.setText(players.get(position).name);
			pos.setText(players.get(position).pos);
			return convertView;
		}
		
	}
}
