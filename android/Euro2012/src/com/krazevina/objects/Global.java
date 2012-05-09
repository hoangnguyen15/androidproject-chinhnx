package com.krazevina.objects;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

public class Global 
{
	public static String z = "";
	
	/**
	 * VI,EN,KO</br>
	 * not VN and KR
	 */
	public static String lang;
	
	public static void getLang(Activity c){
		SharedPreferences sp = c.getSharedPreferences("lang", Context.MODE_PRIVATE);
		lang = sp.getString("lang", "");
		if(lang.length()<=0){
			if(Locale.getDefault().getDisplayLanguage().equals("Vietnamese"))lang = "VI";
			else if(Locale.getDefault().getDisplayLanguage().equals("Korean"))lang = "KO";
			else lang = "EN";
			sp.edit().putString("lang", lang);
			sp.edit().commit();
		}
		Configuration config = c.getBaseContext().getResources().getConfiguration();
		Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        config.locale = locale;
        c.getBaseContext().getResources().updateConfiguration(config, c.getBaseContext().getResources().getDisplayMetrics());
	}
}
