package com.icsoft.objects;

import android.content.Context;
import android.content.SharedPreferences;


public class Global{

	public static double timeZone = 7;
	public static String Info(Context context){
		String info="";
		SharedPreferences prefs = context.getSharedPreferences("preferencename", 0); 
		info = prefs.getString("Info","");
		return info;
	}
	
	public static void saveInfo(Context context,String info){
		SharedPreferences prefs = context.getSharedPreferences("preferencename", 0);
		SharedPreferences.Editor editor = prefs.edit(); 
		editor.putString("Info",info);
		editor.commit();
	}
	
}