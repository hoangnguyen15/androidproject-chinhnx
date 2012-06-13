package com.krazevina.thioto.webservice;
/** this class use like a type of data of Arraylist. Have two couple (key,value)
 * when you declare parameters for webservice*/
public class Params {
	private String _key;
	private Object _value;
	
	public String getKey(){
		return _key;
	}
	public Object getValue(){
		return _value;
	}
	public void setKey(String key){
		_key=key;
	}
	public void setValue(Object value){
		_value=value;
	}
	public void setKeyValue(String key,Object value) {
		_key=key;_value=value;		
	}
}
