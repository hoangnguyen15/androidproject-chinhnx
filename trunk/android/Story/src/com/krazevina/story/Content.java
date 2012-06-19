package com.krazevina.story;

import com.krazevina.objects.Global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Content extends Activity implements OnClickListener {
	TextView title, content;
	Button btnBooked, btnNext, btnPrev;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);
		title = (TextView) findViewById(R.id.title);
		content = (TextView) findViewById(R.id.content);
		btnBooked = (Button) findViewById(R.id.btnbookmark);
		btnNext = (Button) findViewById(R.id.btnnext);
		btnPrev = (Button) findViewById(R.id.btnprev);

		position = getIntent().getIntExtra("position", 0);

		title.setText(Global.vt.elementAt(position).title);
		content.setText(Global.vt.elementAt(position).content);

		btnBooked.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnPrev.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btnBooked.getId()) {

		}

		if (v.getId() == btnNext.getId()) {
			position++;
			if (position > Global.vt.size()-1) {
				Toast.makeText(this, "End", 2).show();
				position = Global.vt.size()-1;
			}
			title.setText(Global.vt.elementAt(position).title);
			content.setText(Global.vt.elementAt(position).content);
		}

		if (v.getId() == btnPrev.getId()) {
			position--;
			if (position <0) {
				Toast.makeText(this, "First", 2).show();
				position = 0;
			}
			
			title.setText(Global.vt.elementAt(position).title);
			content.setText(Global.vt.elementAt(position).content);
		}

	}
}
