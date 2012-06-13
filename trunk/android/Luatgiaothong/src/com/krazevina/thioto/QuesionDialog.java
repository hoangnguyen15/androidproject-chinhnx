package com.krazevina.thioto;


import com.krazevina.thioto.R;
import com.krazevina.thioto.sqlite.CauhoiDB;
import com.krazevina.thioto.webservice.CallWebService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TabWidget;
import android.widget.Toast;

public class QuesionDialog {

	AlertDialog.Builder builder;
	AlertDialog alertDialog;

	// TextView text = (TextView) layout.findViewById(R.id.text);
	// text.setText("Hello, this is a custom dialog!");
	// ImageView image = (ImageView) layout.findViewById(R.id.image);
	// image.setImageResource(R.drawable.android);

	Button sms;
	Button dontrequestion;
	Button cancel;
	Context c;
	View view;
    CauhoiDB cauhoiDB;
    CallWebService call;
    DateUtils dateutils;
	public QuesionDialog(Context context,CauhoiDB cauhoiDB) {

		
		this.cauhoiDB=cauhoiDB;
		this.c = context;
		this.call=new CallWebService(this.c);
		this.dateutils = new DateUtils();
		
		LayoutInflater inflater = (LayoutInflater) this.c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.question, null);
		builder = new AlertDialog.Builder(this.c);
		builder.setView(view);
		alertDialog = builder.create();
		setOnClickListener();
	}
	
	String timerequest = null;
	Timer timer = new Timer();
	
	private void setOnClickListener() {
		sms = (Button) view.findViewById(R.id.btn_sms);
		if(Toancuc.duocxemmeothi)
			sms.setText(R.string.view);
		else
			sms.setText(R.string.view);
		sms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Toancuc.duocxemmeothi)
				{
					Tab.tabHost.setCurrentTab(9);
					Tab.tabwidget.setVisibility(TabWidget.GONE);
				}
				else
				{
					Intent sendIntent = new Intent(
							Intent.ACTION_VIEW);
	
					timerequest = "KV G"
							+ dateutils.getDetailTimeNow();
					cauhoiDB.openToWrite();
					cauhoiDB.addItem_sms(timerequest, "0");
	
					if (timer_running) {
						timer.setTotalTime(120);
					} else {
						timer_running = true;
						timer = new Timer();
						timer.execute((Void) null);
					}
					Log.d("getdetailtimenow", "" + timerequest);
					sendIntent.putExtra("address", "8732");
					sendIntent.putExtra("sms_body", timerequest);
					sendIntent.setType("vnd.android-dir/mms-sms");
					try{
						QuesionDialog.this.c.startActivity(sendIntent);
					}catch (Exception e) {
						Toast.makeText(c, R.string.noapptodo, 0).show();
					}
					
					alertDialog.dismiss();
				}
			}
		});
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				alertDialog.dismiss();
			}
		});
		dontrequestion = (Button) view.findViewById(R.id.btn_dontrequestion);
		dontrequestion.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cauhoiDB.openToWrite();
				cauhoiDB.addItem_ask("kohoilai");
				alertDialog.dismiss();
			}
		});

	}

	public void show() {
		alertDialog.show();
	}
	

	boolean timer_running = false;

	int x = 0;

	protected class Timer extends AsyncTask<Void, Void, Void> {

		boolean issent = false;
		int total = 120;

		public void setTotalTime(int total) {
			this.total = total;
		}

		@Override
		protected void onPostExecute(Void v) {
			timer_running = false;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			timer_running=true;
			x = 0;
			while (!issent && total > 0) {
				total--;
				Log.d("total=",""+total);
				Log.d("x", "" + x);
				x++;
				try {
					Thread.sleep(500);

						if (call.getUrlAlbum(timerequest).compareTo(
								"true") == 0) {
							cauhoiDB.openToWrite();
							cauhoiDB.update_sms(timerequest, "1");
							
							Toancuc.duocxemmeothi = true;
							issent = true;
							
						}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			}

			return null;
		}
	}

}
