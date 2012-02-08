package com.candroid.huyenhoc;

import java.util.Calendar;


import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import com.candroid.objects.Global;
import com.candroid.objects.Sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class Main extends Activity implements OnClickListener {
	EditText edtName;
	Button btnFinish;
	Sqlite sql;
	TextView txtDayofbirth, txtMonthofbirth, txtYearofbirth;

//	TextView txtHourofbith,txtMinuteofbirth,txtLunarHourofbith;
	TextView txtMale,txtFemale;
	String name, dateofbith;
	String hourofbirth, minuteofbirth;
	boolean male = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/HL-OngDo-Unicode.ttf");
		Global.face = tf;
		setContentView(R.layout.main);

		edtName = (EditText) findViewById(R.id.edtName);
		edtName.setTypeface(tf);
		btnFinish = (Button) findViewById(R.id.btnFinish);
//		txtDayofbirth = (TextView) findViewById(R.id.txtDayofbith);
//		txtMonthofbirth = (TextView) findViewById(R.id.txtMonthofbirh);
//		txtYearofbirth = (TextView) findViewById(R.id.txtYearofbirth);
//		txtHourofbith = (TextView)findViewById(R.id.txtHourofbirth);
//		txtMinuteofbirth = (TextView)findViewById(R.id.txtMinuteofbirth);
//		txtLunarHourofbith = (TextView)findViewById(R.id.txtLunarHourofbith);
		txtMale = (TextView)findViewById(R.id.txtMale);
		txtFemale = (TextView)findViewById(R.id.txtFemale);
		

		
		sql = new Sqlite(this);
		btnFinish.setOnClickListener(this);
//		txtDayofbirth.setOnTouchListener(this);
//		txtMonthofbirth.setOnTouchListener(this);
//		txtYearofbirth.setOnTouchListener(this);
//		txtHourofbith.setOnTouchListener(this);
//		txtMinuteofbirth.setOnTouchListener(this);
		
		txtMale.setOnClickListener(this);
		txtFemale.setOnClickListener(this);
		

		
        Calendar calendar = Calendar.getInstance();

        final WheelView month = (WheelView) findViewById(R.id.txtMonthofbirh);
        final WheelView year = (WheelView) findViewById(R.id.txtYearofbirth);
        final WheelView day = (WheelView) findViewById(R.id.txtDayofbith);
        
        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day);
            }
        };

        // month
        int curMonth = calendar.get(Calendar.MONTH);
        String months[] = new String[] {"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11", "12"};
        month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);
    
        // year
        int curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new DateNumericAdapter(this, 1900, 2099, 0));
        year.setCurrentItem(curYear-1900);
        year.addChangingListener(listener);
        
        //day
        updateDays(year, month, day);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);

		// GetName
//		if (sql.getName().length() != 0) {
//			startActivity(new Intent(this, MainMenu.class));
//			finish();
//		}
		

	}


	@Override
	public void onClick(View v) {
		if (v.getId() == btnFinish.getId()) {
			name=edtName.getText().toString();
			if(name.length() == 0){
				Toast.makeText(this,getString(R.string.err_name), 2).show();
			}else{
				dateofbith = txtYearofbirth.getText() + "-"+txtMonthofbirth.getText()+"-"+txtDayofbirth.getText();
				sql.setName(name, dateofbith, 0, 0);
				startActivity(new Intent(this, MainMenu.class));
				finish();
			}
		}
		
		if(v.getId() == txtMale.getId()){
			if(!male){
				txtMale.setTextColor(Color.parseColor("#7f0000"));
				txtFemale.setTextColor(Color.parseColor("#cccccc"));
				male = true;
			}
		}
		
		if(v.getId() == txtFemale.getId()){
			if(male){
				txtFemale.setTextColor(Color.parseColor("#7f0000"));
				txtMale.setTextColor(Color.parseColor("#cccccc"));
				male = false;
			}
		}

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sql.close();
	}


	  /**
     * Updates day wheel. Sets max days according to selected month and year
     */
    void updateDays(WheelView year, WheelView month, WheelView day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());
        
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
        Log.d("curDay",""+curDay);
    }
    
    /**
     * Adapter for numeric wheels. Highlights the current value.
     */
    private class DateNumericAdapter extends NumericWheelAdapter {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
            super(context, minValue, maxValue);
            this.currentValue = current;
            setTextSize(45);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
            	view.setTextColor(Color.parseColor("#7f0000"));
            }
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
    
    /**
     * Adapter for string based wheel. Highlights the current value.
     */
    private class DateArrayAdapter extends ArrayWheelAdapter<String> {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateArrayAdapter(Context context, String[] items, int current) {
            super(context, items);
            this.currentValue = current;
            setTextSize(45);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
            	view.setTextColor(Color.parseColor("#7f0000"));
            	
            }
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }

}