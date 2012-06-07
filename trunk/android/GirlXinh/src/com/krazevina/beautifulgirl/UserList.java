package com.krazevina.beautifulgirl;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.krazevina.objects.Global;
import com.krazevina.objects.User;
import com.krazevina.objects.Users;
import com.krazevina.webservice.CallWebService;

public class UserList extends Activity implements OnItemClickListener{
	ListView lst_users;
	Users users;
	User user;
	CallWebService call;
	Handler handler;
	String response;
	ProgressDialog pg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userlist);
		lst_users = (ListView) findViewById(R.id.lst_users);
		call = new CallWebService(this);
		handler = new Handler();
		new loadUser().start();
		lst_users.setOnItemClickListener(this);
	}
	
	
	public class UserAdapter extends BaseAdapter {
		private LayoutInflater mInflater = null;
		private ViewHolder mViewHolder;
		private Users _users;
		private int count = 0;

		public UserAdapter(Context context, int textViewResourceId, Users users) {
			mInflater = LayoutInflater.from(context);
			this._users = users;
			if (_users != null) {
				if (_users.getCount() != 0)
					count = _users.getCount();
			}
		}

		public int getCount() {
			return count;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_userlist, null);

				mViewHolder = new ViewHolder();
				mViewHolder.mRoot = (LinearLayout) convertView.findViewById(R.id.root_useritem);
				
				// if(position%2!=0)
				// mViewHolder.mRoot.setBackgroundResource(R.drawable.bg_item1);
				// else
				// mViewHolder.mRoot.setBackgroundResource(R.drawable.bg_item2);

				mViewHolder.mUsername = (TextView) convertView.findViewById(R.id.item_uploader);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}

			mViewHolder.mUsername.setText(users.getItemAtPosition(position).getUploader());

			convertView.setId(count);
			return convertView;

		}

		class ViewHolder {
			LinearLayout mRoot;
			TextView mUsername;
		}
	}

	boolean internet = true;

	class loadUser extends Thread {

		public void run() {
			handler.post(new Runnable() {

				@Override
				public void run() {
					pg = new ProgressDialog(UserList.this);
					pg.setMessage(getString(R.string.pleasewait));
					pg.show();
				}

			});

			response = call.FollowList(Global.username);
			try {
				if (!response.equals("")) {
					internet = true;
					Thread.sleep(1000);
					pg.dismiss();
				} else {
					internet = false;
					pg.dismiss();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			handler.post(new Runnable() {
				@Override
				public void run() {
					try{
						users = new Users();
						if(response.equals("anyType{}")){
							Toast.makeText(UserList.this,getString(R.string.warn_list_follow),2).show();
						}else{
							JSONArray jArray = new JSONArray(response);
							JSONObject jsonObject;

							for(int i = 0;i<jArray.length();i++){
								user = new User();
								jsonObject = new JSONObject(jArray.getString(i));
								String uploader = jsonObject.get("userName").toString();
								user.setUploader(uploader);
								users.add(user);
							}
							
							UserAdapter adapter = new UserAdapter(UserList.this, R.layout.item_listfollow, users);
							lst_users.setAdapter(adapter);
						}
						

					}catch (Exception e) {
					}
					if(!internet){
						Toast.makeText(UserList.this,getString(R.string.err_internet), 2).show();
						return;
					}
				}
			});
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(parent.getId() == lst_users.getId()){
			Global.userSearch = users.getItemAtPosition(position).getUploader();
			Intent i = new Intent("com.krazevina.beautifulgirl.search");
			sendBroadcast(i);
			setResult(10);
			finish();
			overridePendingTransition(R.anim.upout,0);

		}
		
	}

}
