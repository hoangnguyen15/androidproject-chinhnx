/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package chau.nguyen.calendar.widget;

import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;

public class DatePicker extends android.widget.DatePicker {
	private static Locale vnLocale = new Locale("vi");
	public DatePicker(Context context) {
		super(context);		
	}
	public DatePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public DatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}	
	@Override
	public void init(int year, int monthOfYear, int dayOfMonth,
			OnDateChangedListener onDateChangedListener) {
		Locale defaultLocale = Locale.getDefault();		
		Locale.setDefault(vnLocale);
		super.init(year, monthOfYear, dayOfMonth, onDateChangedListener);
		Locale.setDefault(defaultLocale);
	}
	@Override
	public void updateDate(int year, int monthOfYear, int dayOfMonth) {
		Locale defaultLocale = Locale.getDefault();		
		Locale.setDefault(vnLocale);
		super.updateDate(year, monthOfYear, dayOfMonth);
		Locale.setDefault(defaultLocale);
	}		
}
