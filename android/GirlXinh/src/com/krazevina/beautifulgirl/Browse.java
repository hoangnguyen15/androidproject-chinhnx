package com.krazevina.beautifulgirl;

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Browse extends Activity
{
	String path;
	Button btnback;
	LinearLayout llbrow;
	File curfile;
	TextView txtpath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		path = i.getStringExtra("folder");
		setContentView(R.layout.folderpick);
		llbrow = (LinearLayout)findViewById(R.id.lvbrowser);
		btnback = (Button)findViewById(R.id.btnback);
		txtpath = (TextView)findViewById(R.id.txtpath);
		btnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(curfile.getAbsolutePath().equals(Environment.getRootDirectory().getAbsolutePath()))Toast.makeText(Browse.this, getString(R.string.root), 1).show();
				else
				{
					try{
						showfolder(curfile.getParentFile());
					}catch (Exception e) {
						Toast.makeText(Browse.this, getString(R.string.root), 1).show();
					}
				}
			}
		});
		
		try{
			curfile = new File(path);
		}catch (Exception e) {
			curfile = Environment.getExternalStorageDirectory().getParentFile();
		}
		li = LayoutInflater.from(this);
		showfolder(curfile);
		Toast.makeText(Browse.this, getString(R.string.hintbrowse), 1).show();
	}
	int i;
	String[]files;
	LayoutInflater li;
	
	private void showfolder(File f)
	{
		LayoutInflater li = LayoutInflater.from(this);
		if(!f.canRead())
		{
			f = Environment.getExternalStorageDirectory().getParentFile();
		}
		curfile = f;
		txtpath.setText(curfile.getAbsolutePath());
		llbrow.removeAllViews();
		files = f.list();
		for(i=0;i<files.length;i++)
		{
			File cf = new File(f,files[i]);
			if(cf.isDirectory())
			{
				View v = li.inflate(R.layout.folderpickitem, null);
				Button btn = (Button) v.findViewById(R.id.btnopen);
				TextView txt = (TextView) v.findViewById(R.id.txtnamefolder);
				
				btn.setOnClickListener(new OnClickListener() {
					int pos = i;
					public void onClick(View v) {
						showfolder(new File(curfile, files[pos]));
					}
				});
				
				if(!files[i].equals("sdcard"))
					txt.setText(files[i]);
				else
					txt.setText(getString(R.string.sdcard));
				txt.setOnClickListener(new OnClickListener() {
					int pos = i;
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(Browse.this);
						File choose = new File(curfile, files[pos]);
						builder.setMessage(getString(R.string.confirm)+" "+choose.getName())
						       .setCancelable(false)
						       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) 
						           {
						        	   File choose = new File(curfile, files[pos]);
						        	   path = choose.getAbsolutePath();
						        	   Intent in = new Intent();
						        	   in.putExtra("folder", path);
						        	   setResult(RESULT_OK, in);
						        	   finish();
						           }
						       })
						       .setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                dialog.cancel();
						           }
						       });
						AlertDialog alert = builder.create();
						alert.show();
					}
				});
				llbrow.addView(v);
			}
		}
	}
}
