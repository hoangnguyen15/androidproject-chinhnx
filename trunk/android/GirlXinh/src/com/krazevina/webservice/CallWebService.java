package com.krazevina.webservice;

/** This class use to call webservice from server. */
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
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
	private static final String NAME_SPACE = "http://tempuri.org/";
	private static final String URL = "http://aloandroid.vn/WS/Img/Service.asmx";
	private String ACTION_NAME = "";
	private String METHOD_NAME = "";
	Params params;

	Context mContext;

	public CallWebService(Context context) {
		this.mContext = context;
	}
	
	public String serviceServer() {
		ACTION_NAME = "http://tempuri.org/CheckAPK";
		METHOD_NAME = "CheckAPK";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

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

	public String serviceImageRate(int index) {
		ACTION_NAME = "http://tempuri.org/ImageRateK";
		METHOD_NAME = "ImageRateK";
		
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			response = envelope.getResponse().toString();
			// SoapPrimitive soapString = (SoapPrimitive)
			// envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageHot(int index) {
		ACTION_NAME = "http://tempuri.org/ImageHot";
		METHOD_NAME = "ImageHot";
		
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("index", index+1);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			response = envelope.getResponse().toString();
			// SoapPrimitive soapString = (SoapPrimitive)
			// envelope.getResponse();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageRandom() {
		ACTION_NAME = "http://tempuri.org/ImageRandomK";
		METHOD_NAME = "ImageRandomK";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

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
	
	public String serviceImageAll(int ind) 
	{
		ACTION_NAME = "http://tempuri.org/ImageAll";
		METHOD_NAME = "ImageAll";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("index", ind+1);
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
	
	
	public String serviceImageFollowID(int index) 
	{
		if(Global.username==null||Global.username.length()<=0)
		{
			return "";
		}
		ACTION_NAME = "http://tempuri.org/ImageFollow";
		METHOD_NAME = "ImageFollow";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			if(Global.id<=0)return "";
			Request.addProperty("ID", Global.id);
			Request.addProperty("index", index+1);

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
	
	public String serviceImageFollowNew(int index) 
	{
		if(Global.username==null||Global.username.length()<=0)
		{
			return "";
		}
		ACTION_NAME = "http://tempuri.org/ImageFollowN";
		METHOD_NAME = "ImageFollowN";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			if(Global.id<=0)return "";
			Request.addProperty("ID", Global.id);
			Request.addProperty("index", index+1);

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
	
	public String serviceImageFollowRand(int index) 
	{
		if(Global.username==null||Global.username.length()<=0)
		{
			return "";
		}
		ACTION_NAME = "http://tempuri.org/ImageFollowR";
		METHOD_NAME = "ImageFollowR";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			if(Global.id<=0)return "";
			Request.addProperty("ID", Global.id);
			Request.addProperty("index", index+1);

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
	
	public String serviceImageDate1() {
		ACTION_NAME = "http://tempuri.org/ImageDateK";
		METHOD_NAME = "ImageDateK";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
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

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String serviceImageUploaded(int index) {
		ACTION_NAME = "http://tempuri.org/ImageMember";
		METHOD_NAME = "ImageMember";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("userName", ""+Global.username);
			Request.addProperty("index", index+1);

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
	public String serviceImageFavorited(int index) {
		ACTION_NAME = "http://tempuri.org/ImageLike";
		METHOD_NAME = "ImageLike";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("userName", ""+Global.username);
			Request.addProperty("index", index+1);

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
	
	public String serviceImageSearchNew(int index, String user) {
		ACTION_NAME = "http://tempuri.org/ImageMemberS";
		METHOD_NAME = "ImageMemberS";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("userName", ""+user);
			Request.addProperty("index", index+1);

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
	
	public String serviceImageSearchHot(int index, String user) {
		ACTION_NAME = "http://tempuri.org/ImageMemberHot";
		METHOD_NAME = "ImageMemberHot";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("userName", ""+user);
			Request.addProperty("index", index+1);

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
	
	public String serviceImageSearchTop(int index, String user) {
		ACTION_NAME = "http://tempuri.org/ImageMemberTop";
		METHOD_NAME = "ImageMemberTop";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("userName", ""+user);
			Request.addProperty("index", index+1);

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
	
	public String uploadImage(String s,String filename,String username){
		ACTION_NAME = "http://tempuri.org/ImageUpload";
		METHOD_NAME = "ImageUpload";
		 
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("img", s);
			Request.addProperty("imgName", filename);
			Request.addProperty("userName",username);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);

			aht.call(ACTION_NAME, envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			return "false";
		}
		return response;
	}
	
	public String imageVote(int id){
		ACTION_NAME = "http://tempuri.org/ImageVote";
		METHOD_NAME = "ImageVote";
		 
		String response = "";
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("ID", id);
			Request.addProperty("userName", Global.username);
			

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			response = envelope.getResponse().toString();
			// SoapPrimitive soapString = (SoapPrimitive)
			// envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String imageUnVote(int id){
		ACTION_NAME = "http://tempuri.org/Unlike";
		METHOD_NAME = "Unlike";
		 
		String response = "";
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("ID", id);
			Request.addProperty("userName", Global.username);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
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
		ACTION_NAME = "http://tempuri.org/Follow";
		METHOD_NAME = "Follow";
		 
		String response = "";
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("ID", Global.id);
			Request.addProperty("userName", user);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			response = envelope.getResponse().toString();
			Log.e("FOLLOW", ""+response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String register(String username, String password){
		ACTION_NAME = "http://tempuri.org/Register";
		METHOD_NAME = "Register";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("userName", username);
			Request.addProperty("password", password);
			

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public String login(String username, String password){
		ACTION_NAME = "http://tempuri.org/Login";
		METHOD_NAME = "Login";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("userName", username);
			Request.addProperty("password", password);
			

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public String logout(String username){
		ACTION_NAME = "http://tempuri.org/Logout";
		METHOD_NAME = "Logout";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("userName", username);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean checkCode(String code,String user) {
		ACTION_NAME = "http://tempuri.org/ActiverUser";
		METHOD_NAME = "ActiverUser";
		
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("userName", user);
			Request.addProperty("code", code);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
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
		ACTION_NAME = "http://tempuri.org/UpdateInfo";
		METHOD_NAME = "UpdateInfo";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

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

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public String changePass(String username,String oldpass, String newpass){
		ACTION_NAME = "http://tempuri.org/ChangePass";
		METHOD_NAME = "ChangePass";
		 
		String flag = "";
		try {
			// Creat Soap Object
			
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("userName", username);
			Request.addProperty("oldPass", oldpass);
			Request.addProperty("newPass", newpass);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; // .NET Web Services
			envelope.setOutputSoapObject(Request);

			HttpTransportSE aht = new HttpTransportSE(URL);
			aht.call(ACTION_NAME, envelope);
			flag = envelope.getResponse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public String FollowList(String userName) {
		ACTION_NAME = "http://tempuri.org/FollowList";
		METHOD_NAME = "FollowList";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("userName", ""+userName);

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
	
	public String UnFollow(String userName,String userNameFollow) {
		ACTION_NAME = "http://tempuri.org/UnFollow";
		METHOD_NAME = "UnFollow";
		String response = null;
		try {
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);
			Request.addProperty("userNameInfo", ""+userName);
			Request.addProperty("userNameFollow", ""+userNameFollow);

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
	
	public String UsernameList(int index,int size) {
		ACTION_NAME = "http://tempuri.org/UsernameList";
		METHOD_NAME = "UsernameList";
		
		String response = null;
		try {
			// Creat Soap Object
			SoapObject Request = new SoapObject(NAME_SPACE, METHOD_NAME);

			/* add param for it */
			Request.addProperty("index", index);
			Request.addProperty("size", size);
			

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

	public static boolean isOnline() {
		try {
			URL url = new URL("http://aloandroid.vn/WS/Img/Service.asmx");
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
