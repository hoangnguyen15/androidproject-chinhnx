package com.krazevina.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Stack;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class SocketConnect {

	String sv="123.30.187.134";
	int port = 8888;
	Socket sk;
	BufferedReader br;
	/**
	 * 0:not connect
	 * 1:connected
	 * 2:dis
	 */
	int status=-1;
	
	public SocketConnect(){
	}
	
    public void connect() {
        try {
			Log.e("Connecting", "Connecting");
			sk = new Socket(sv,port);
			br=new BufferedReader(new InputStreamReader(sk.getInputStream()));
			Log.e("Connected", "Connected");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

    protected void disconnect() {
    	try {
			sk.close();
		} catch (Exception e) {
		}
    }
    
    void send(String s){
    	PrintWriter pw;
		try {
			pw = new PrintWriter(sk.getOutputStream(),true);
			pw.println(""+s);
			Log.e("Send", ""+s);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    String output;
    String receive(){
		try {
			output = null;
			output=br.readLine();
			if(output!=null)return output;
		} catch (IOException e) {
		}
		return "";
    }
    
    public void update(String s,Context con){
    	new updateData(s, con).start();
    }
    
    class updateData extends Thread{
    	String str;
    	Context c;
    	public updateData(String s,Context con) {
    		str = s;
    		c = con;
		}
    	public void run(){
    		connect();
    		send(str);
    		String js = receive();
    		sqlite sql = new sqlite(c);
    		if (str.equals("Matches")) {
    			sql.updateMatches(js);
    		}
    	}
    }
}