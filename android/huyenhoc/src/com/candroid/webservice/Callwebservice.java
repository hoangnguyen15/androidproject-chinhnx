package com.candroid.webservice;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

public class Callwebservice {
	
	private static final String METHOD_NAME = "receiveMO";
	private static final String NAME_SPACE = "http://huyenhoc";
	private static final String SOAP_ACTION = ""; // NAMESPACE + method name
	private static final String URL = "http://222.255.8.122:8888/huyenHoc/wsdl/huyenHoc.wsdl?wsdl";

	AndroidHttpTransport aht;
	Context mContext;

	public Callwebservice(Context context) {
		this.mContext = context;
	}

	public void test(){
			
			String response = "";
			try {
				SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
				Request.addProperty("User_ID","1");
				Request.addProperty("Service_ID","1");
				Request.addProperty("Command_Code","1");
				Request.addProperty("Message","1");
				Request.addProperty("Request_ID","1");
				Request.addProperty("username","1");
				Request.addProperty("password","1");
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = false;
				envelope.setOutputSoapObject(Request);
				
				AndroidHttpTransport aht = new AndroidHttpTransport(URL);
				aht.debug = true;
		        HttpTransportSE ht = new HttpTransportSE(URL);
				aht.call(SOAP_ACTION, envelope);
	            SoapPrimitive sp= (SoapPrimitive) envelope.getResponse();
				response = sp.toString();
				Log.d("result","z"+response);
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}

	}
	
}
