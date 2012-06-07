package com.krazevina.beautifulgirl;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.krazevina.objects.ImageDownloader;

public class ServerAlertDialog extends Dialog{
	String link,content,title,img,date;
	TextView tit,con,txtlink;
	ImageView image;
	Handler han;
	Bitmap b;
	Button btnclose,btnweb;
	int id;
	public boolean show = true;
	public ServerAlertDialog(final Context c,String s) {
		super(c);
		SharedPreferences sp = c.getSharedPreferences("IDpref", Context.MODE_PRIVATE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int currID = sp.getInt("IDSV", 0);
		try {
			JSONArray ja = new JSONArray(s);
			JSONObject js = ja.getJSONObject(0);
			Log.e("ID", ""+js.getInt("ID"));
			if(js.getInt("ID")<=currID){
				show = false;
				return;
			}else{
				SharedPreferences sp1 = c.getSharedPreferences("IDpref", Context.MODE_PRIVATE);
				Editor e = sp1.edit();
				e.putInt("IDSV", js.getInt("ID"));
				e.commit();
				link = js.getString("link");
				content = js.getString("content");
				title = js.getString("title");
				img = js.getString("image");
				date = js.getString("expDate");
				String[]d = date.split("/");
				Calendar cur = Calendar.getInstance();
				Calendar exp = Calendar.getInstance();
				exp.set(Calendar.DATE, Integer.parseInt(d[0]));
				exp.set(Calendar.MONTH, Integer.parseInt(d[1]));
				exp.set(Calendar.YEAR, Integer.parseInt(d[2]));
				if(cur.getTimeInMillis()>exp.getTimeInMillis())show = false;
			}
		} catch (Exception e) {
			show = false;
			return;
		}
		setContentView(R.layout.serverdialog);
		tit = (TextView)findViewById(R.id.txttitle);
		con = (TextView)findViewById(R.id.txtcontent);
		txtlink = (TextView)findViewById(R.id.txtlink);
		image = (ImageView)findViewById(R.id.img);
		btnclose = (Button)findViewById(R.id.btnclose);
		btnweb = (Button)findViewById(R.id.btnweb);
		
		tit.setText(title);
		con.setText(content);
		btnclose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
		
		View.OnClickListener cl = new View.OnClickListener() {
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""+link));
				c.startActivity(browserIntent);
			}
		};
		txtlink.setOnClickListener(cl);
		btnweb.setOnClickListener(cl);
		txtlink.setText(link);
		txtlink.setTextColor(Color.BLUE);
		han = new Handler();
		new Thread(new Runnable() {
			public void run() {
				b = ImageDownloader.readImageByUrl(img);
				han.post(new Runnable() {
					public void run() {
						image.setImageBitmap(b);
					}
				});
			}
		}).start();
		setCanceledOnTouchOutside(true);
	}
}
