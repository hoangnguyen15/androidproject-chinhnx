package com.krazevina.thioto.webservice;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class CallWebService {
	
	///http://aloandroid.vn/WS/AppWS/AndroidCatWebservices.asmx?op=IsBought

	private static final String tag = "CallWebService Class";
	private static String SOAP_ACTION;
	private static String METHOD_NAME;
	private static final String NAME_SPACE = "http://tempuri.org/";
	private static final String URL = "http://aloandroid.vn/WS/AppWS/AndroidCatWebservices.asmx";

	AndroidHttpTransport aht;
	Context mContext;

	public CallWebService(Context context) {
		this.mContext = context;
	}

	public String getUrlAlbum(String giophutgiayngaythangnam) {
		String urlxml = "";
		try {
			SOAP_ACTION = "http://tempuri.org/IsBought";
			METHOD_NAME = "IsBought";
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("strCode", giophutgiayngaythangnam);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			AndroidHttpTransport aht = new AndroidHttpTransport(URL);

			aht.call(SOAP_ACTION, envelope);
			urlxml = envelope.getResponse().toString();
			Log.d("url_xml", " " + urlxml);

		} catch (Exception e) {
			Log.e(tag, e.toString());
		}
		return urlxml;

	}

	public void countDownload(String albumId) {
		try {
			SOAP_ACTION = "http://tempuri.org/getImage";
			METHOD_NAME = "getImage";
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("albumID", albumId);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);
			AndroidHttpTransport aht = new AndroidHttpTransport(URL);
			aht.call(SOAP_ACTION, envelope);

		} catch (Exception e) {
			Log.e(tag, e.toString());
		}
	}

	public String callNetService(int i, ArrayList<Params> ap) {
		if (i == 1) {
			METHOD_NAME = "getImage";
			SOAP_ACTION = "http://tempuri.org/getImage";
		}

		String str = "";
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			for (Params p : ap) {
				Request.addProperty(p.getKey(), p.getValue());
			}

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			AndroidHttpTransport aht = new AndroidHttpTransport(URL);

			aht.call(SOAP_ACTION, envelope);
			str = envelope.getResponse().toString();
			Log.d("str", " " + str);
		} catch (Exception e) {
			Log.e(tag, e.toString());
		}
		return str;
	}
	public static boolean checknet(Context context) {
		boolean check;
		
		ConnectivityManager network = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = network
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		NetworkInfo mobile = network
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

	

		if (wifi.isAvailable() == true) {
			check = true;

		} else if (mobile.isAvailable() == true) {

			check = true;
		} else {

		
			check = false;

		}

		return check;
	}
	public static boolean isOnline(Context context) {
		Log.d("begin","begin");
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
				URL url = new URL(URL);
				HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
				urlc.setRequestProperty("User-Agent", "My Android Demo");
				urlc.setRequestProperty("Connection", "close");
				urlc.setConnectTimeout(1000); // mTimeout is in seconds
				urlc.connect();
				Log.d("begin","begin");
				if (urlc.getResponseCode() == 200) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
