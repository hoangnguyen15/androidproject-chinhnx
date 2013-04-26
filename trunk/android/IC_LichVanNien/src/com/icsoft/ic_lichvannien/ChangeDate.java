package com.icsoft.ic_lichvannien;

import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;

public class ChangeDate extends  Activity {
	DatePicker dpSolar,dpLunar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changedate);
		dpSolar = (DatePicker)findViewById(R.id.dpSolar);
		dpLunar = (DatePicker)findViewById(R.id.dpLunar);
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 11) {
          try {
            Method m = dpSolar.getClass().getMethod("setCalendarViewShown", boolean.class);
            m.invoke(dpSolar, false);
            m = dpLunar.getClass().getMethod("setCalendarViewShown", boolean.class);
            m.invoke(dpLunar, false);
          }
          catch (Exception e) {} // eat exception in our case
        }
	}
}
