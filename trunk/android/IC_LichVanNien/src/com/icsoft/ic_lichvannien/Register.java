package com.icsoft.ic_lichvannien;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends FragmentActivity implements OnClickListener {
	static EditText edtDOB;
	TextView btnMale, btnFemale;
	Button btnConfirm, btnCancel;
	boolean isMale = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		edtDOB = (EditText) findViewById(R.id.edtdob);
		btnMale = (TextView) findViewById(R.id.btnMale);
		btnFemale = (TextView) findViewById(R.id.btnFemale);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		edtDOB.setOnClickListener(this);
		btnMale.setOnClickListener(this);
		btnFemale.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			edtDOB.setText(day + "/" + (month + 1) + "/" + year);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == edtDOB.getId()) {
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getSupportFragmentManager(), "datePicker");
		}
		if (v.getId() == btnMale.getId()) {
			if(!isMale){
				btnMale.setTextColor(Color.parseColor("#ff0000"));
				btnFemale.setTextColor(Color.parseColor("#cccccc"));
				isMale = true;
			}
		}
		if (v.getId() == btnFemale.getId()) {
			if(isMale){
				btnFemale.setTextColor(Color.parseColor("#ff0000"));
				btnMale.setTextColor(Color.parseColor("#cccccc"));
				isMale = false;
			}
		}
		if(v.getId() == btnConfirm.getId()){
			Intent intent = new Intent();
			intent.setClass(this,Main.class);
			startActivity(intent);
		}
		if(v.getId() == btnCancel.getId()){
			finish();
		}
	}
}
