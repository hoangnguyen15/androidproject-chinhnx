package com.krazevina.objects;
/** this class use like a type of data of Arraylist. Have two couple (key,value)
 * when you declare parameters for webservice*/
public class Params {
	private String _key;
	private String _value;
	
	public String getKey(){
		return _key;
	}
	public String getValue(){
		return _value;
	}
	public void setKey(String key){
		_key=key;
	}
	public void setValue(String value){
		_value=value;
	}
	public void setKeyValue(String key,String value) {
		_key=key;_value=value;		
	}
}
