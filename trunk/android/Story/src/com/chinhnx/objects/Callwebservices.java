package com.chinhnx.objects;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Callwebservices {
	private static final String URL = "http://aloandroid.vn/WS/AppVersion/Service.asmx";
	private String ACTION_NAME = "http://tempuri.org/AppVersion";
	private String METHOD_NAME = "AppVersion";
	private String NAMESPACE = "http://tempuri.org/";
	
	public Callwebservices(){
		
	}
	
	public String getAppVersion(String appName) {
		String response = "0";
		try {
			SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
			Request.addProperty("name",appName);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
