package com.krazevina.objects;

import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;

public class Global 
{
	public static String z = "";
	
	/**
	 * VI,EN,KO</br>
	 * not VN and KR
	 */
	public static String lang;
	
	public static void getLang(Context c){
		SharedPreferences sp = c.getSharedPreferences("lang", Context.MODE_PRIVATE);
		lang = sp.getString("lang", "");
		if(lang.length()<=0){
			if(Locale.getDefault().getDisplayLanguage().equals("Vietnamese"))lang = "VI";
			else if(Locale.getDefault().getDisplayLanguage().equals("Korean"))lang = "KO";
			else lang = "EN";
			sp.edit().putString("lang", lang);
			sp.edit().commit();
		}
	}
}
