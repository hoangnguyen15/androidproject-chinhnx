package com.krazevina.webservice;

/** This class use to call webservice from server. */
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.content.Context;
import android.util.Log;
import com.krazevina.objects.Global;
import com.krazevina.objects.Params;

public class CallWebService 
{
//	private static final String NAME_SPACE = "kwws=22whpsxul1ruj2";
	private static final String URL = "kwws=22dordqgurlg1yq2ZV2Lpj2Vhuylfh1dvp{";
	private String ACTION_NAME = "";
	private String METHOD_NAME = "";
	Params params;

	Context mContext;
	
	private static String valnamespace(String code){
		String s = new String("kwws=22whpsxul1ruj2"+code);
        return val(s);
	}
	private static String val(String code){
		String s = new String(code);
		char[] s1 = s.toCharArray();
        for (int i = 0; i < s1.length; i++)
        {
            s1[i] = (char)((int)s1[i] - 3);
        }
        return new String(s1);
	}

	public CallWebService(Context context) {
		this.mContext = context;
	}
	
	public String serviceServerVersion() {
		ACTION_NAME = "Surprwlrq";
		METHOD_NAME = "Surprwlrq";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceServerPromotion() {
		ACTION_NAME = "FkhfnDSN";
		METHOD_NAME = "FkhfnDSN";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public String serviceImageRate(int index) {
		ACTION_NAME = "LpdjhUdwhN";
		METHOD_NAME = "LpdjhUdwhN";
		
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
			// SoapPrimitive soapString = (SoapPrimitive)
			// envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageHot(int index) {
		ACTION_NAME = "LpdjhKrw";
		METHOD_NAME = "LpdjhKrw";
		
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
			// SoapPrimitive soapString = (SoapPrimitive)
			// envelope.getResponse();
		} catch (Exception e) {
		}
		try{
			new JSONArray(response);
		}catch (Exception e) {
			return serviceImageRandom();
		}
		return response;
	}
	
	public String serviceImageRandom() {
		ACTION_NAME = "LpdjhUdqgrpN";
		METHOD_NAME = "LpdjhUdqgrpN";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageAll(int ind) 
	{
		ACTION_NAME = "LpdjhDoo";
		METHOD_NAME = "LpdjhDoo";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			Request.addProperty("index", ind+1);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);
			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	public String serviceImageFollowID(int index) 
	{
		if(Global.username==null||Global.username.length()<=0)
		{
			return "";
		}
		ACTION_NAME = "LpdjhIroorz";
		METHOD_NAME = "LpdjhIroorz";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			if(Global.id<=0)return "";
			Request.addProperty("ID", Global.id);
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageFollowNew(int index) 
	{
		if(Global.username==null||Global.username.length()<=0)
		{
			return "";
		}
		ACTION_NAME = "LpdjhIroorzQ";
		METHOD_NAME = "LpdjhIroorzQ";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			if(Global.id<=0)return "";
			Request.addProperty("ID", Global.id);
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageFollowRand(int index) 
	{
		if(Global.username==null||Global.username.length()<=0)
		{
			return "";
		}
		ACTION_NAME = "LpdjhIroorzU";
		METHOD_NAME = "LpdjhIroorzU";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			if(Global.id<=0)return "";
			Request.addProperty("ID", Global.id);
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageDate1() {
		ACTION_NAME = "LpdjhGdwhN";
		METHOD_NAME = "LpdjhGdwhN";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			Calendar c = Calendar.getInstance();
			int d = c.get(Calendar.DAY_OF_MONTH);
			int m = c.get(Calendar.MONTH)+1;
			int y = c.get(Calendar.YEAR);
			Request.addProperty("eDate", ""+(d<10?"0"+d:d)+"/"+(m<10?"0"+m:m)+
					"/"+y);
			c.setTimeInMillis(c.getTimeInMillis()-1000l*60l*60l*24l*1l);
			d = c.get(Calendar.DAY_OF_MONTH);
			m = c.get(Calendar.MONTH)+1;
			y = c.get(Calendar.YEAR);
			Request.addProperty("aDate", ""+(d<10?"0"+d:d)+"/"+(m<10?"0"+m:m)+
					"/"+y);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageUploaded(int index) {
		ACTION_NAME = "LpdjhPhpehu";
		METHOD_NAME = "LpdjhPhpehu";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			Request.addProperty("userName", ""+Global.username);
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	public String serviceImageFavorited(int index) {
		ACTION_NAME = "LpdjhOlnh";
		METHOD_NAME = "LpdjhOlnh";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			Request.addProperty("userName", ""+Global.username);
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageSearchNew(int index, String user) {
		ACTION_NAME = "LpdjhPhpehuV";
		METHOD_NAME = "LpdjhPhpehuV";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			Request.addProperty("userName", ""+user);
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageSearchHot(int index, String user) {
		ACTION_NAME = "LpdjhPhpehuKrw";
		METHOD_NAME = "LpdjhPhpehuKrw";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			Request.addProperty("userName", ""+user);
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageSearchTop(int index, String user) {
		ACTION_NAME = "LpdjhPhpehuWrs";
		METHOD_NAME = "LpdjhPhpehuWrs";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			Request.addProperty("userName", ""+user);
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String uploadImage(String s,String filename,String username){
		ACTION_NAME = "LpdjhXsordg";
		METHOD_NAME = "LpdjhXsordg";
		 
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("img", s);
			Request.addProperty("imgName", filename);
			Request.addProperty("userName",username);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));

			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			return "false";
		}
		return response;
	}
	
	public String imageVote(int id){
		ACTION_NAME = "LpdjhYrwh";
		METHOD_NAME = "LpdjhYrwh";
		 
		String response = "";
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("ID", id);
			Request.addProperty("userName", Global.username);
			

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
			// SoapPrimitive soapString = (SoapPrimitive)
			// envelope.getResponse();
		} catch (Exception e) {
		}
		return response;
	}
	
	public String imageUnVote(int id){
		ACTION_NAME = "Xqolnh";
		METHOD_NAME = "Xqolnh";
		 
		String response = "";
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("ID", id);
			Request.addProperty("userName", Global.username);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
			// SoapPrimitive soapString = (SoapPrimitive)
			// envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String follow(String user)
	{
		ACTION_NAME = "Iroorz";
		METHOD_NAME = "Iroorz";
		 
		String response = "";
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("ID", Global.id);
			Request.addProperty("userName", user);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
			Log.e("FOLLOW", ""+response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String register(String username, String password){
		ACTION_NAME = "Uhjlvwhu";
		METHOD_NAME = "Uhjlvwhu";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("userName", username);
			Request.addProperty("password", password);
			

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public String login(String username, String password){
		ACTION_NAME = "Orjlq";
		METHOD_NAME = "Orjlq";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("userName", username);
			Request.addProperty("password", password);
			

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public String logout(String username){
		ACTION_NAME = "Orjrxw";
		METHOD_NAME = "Orjrxw";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("userName", username);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean checkCode(String code,String user) {
		ACTION_NAME = "DfwlyhuXvhu";
		METHOD_NAME = "DfwlyhuXvhu";
		
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("userName", user);
			Request.addProperty("code", code);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
			// SoapPrimitive soapString = (SoapPrimitive)
			// envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.toLowerCase().startsWith("true"))
			return true;
		else return false; 
	}
	
	
	public String updateInfo(String username,String name, String email,String phone,String yahoo){
		ACTION_NAME = "XsgdwhLqir";
		METHOD_NAME = "XsgdwhLqir";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("name", name);
			Request.addProperty("userName", username);
			Request.addProperty("alias", "");
			Request.addProperty("email", email);
			Request.addProperty("phone", phone);
			Request.addProperty("chat", yahoo);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public String changePass(String username,String oldpass, String newpass){
		ACTION_NAME = "FkdqjhSdvv";
		METHOD_NAME = "FkdqjhSdvv";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("userName", username);
			Request.addProperty("oldPass", oldpass);
			Request.addProperty("newPass", newpass);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public String FollowList(String userName) {
		ACTION_NAME = "IroorzOlvw";
		METHOD_NAME = "IroorzOlvw";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			Request.addProperty("userName", ""+userName);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String UnFollow(String userName,String userNameFollow) {
		ACTION_NAME = "XqIroorz";
		METHOD_NAME = "XqIroorz";
		String response = null;
		try {
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));
			Request.addProperty("userNameInfo", ""+userName);
			Request.addProperty("userNameFollow", ""+userNameFollow);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String UsernameList(int index,int size) {
		ACTION_NAME = "XvhuqdphOlvw";
		METHOD_NAME = "XvhuqdphOlvw";
		
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(valnamespace(""), val(METHOD_NAME));

			/* add param for it */
			Request.addProperty("index", index);
			Request.addProperty("size", size);
			

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(val(URL));
			aht.call(valnamespace(ACTION_NAME), envelope);
			response = envelope.getResponse().toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static boolean isOnline() {
		try {
			URL url = new URL(val(URL));
			HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
			urlc.setRequestProperty("User-Agent", "My Android Demo");
			urlc.setRequestProperty("Connection", "close");
			urlc.setConnectTimeout(1000); // mTimeout is in seconds

			urlc.connect();

			if (urlc.getResponseCode() == 200) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void logOut(){
		Global.username = "";
		Global.name="";
		Global.email="";
		Global.phone="";
		Global.yahoo="";
		Global.expDate="";
		Global.isActive=0;
		Global.time=0;
		Global.userSearch = "";
		Global.id=-1;
	}
}
