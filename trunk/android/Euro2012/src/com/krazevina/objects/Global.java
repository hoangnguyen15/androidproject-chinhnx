package com.krazevina.objects;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

public class Global 
{
	
	/**
	 * VI,EN,KO</br>
	 * not VN and KR
	 */
	public static String lang;
	public static int notify;
	public static int vibrate;
	
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
	/**
	 * @param lang
	 * lang = 1: eng </br>
	 * lang = 2: vn </br>
	 * lang = 3: kor
	 */
	public void setLang(Context c,int lang){
		SharedPreferences sp = c.getSharedPreferences("lang", Context.MODE_PRIVATE);
		if(lang==2){
			sp.edit().putString("lang", "VN");
			sp.edit().commit();
		}else if(lang==1){
			sp.edit().putString("lang", "EN");
			sp.edit().commit();
		}else {
			sp.edit().putString("lang", "KO");
			sp.edit().commit();
		}
	}
}
