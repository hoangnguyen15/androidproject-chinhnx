package com.krazevina.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Handler;

public class SocketConnect {

	String sv="123.30.187.134";
	int port = 4141;
	Socket sk;
	BufferedReader br;
	/**
	 * 0:not connect
	 * 1:connected
	 * 2:dis
	 */
	int status=-1;
	PrintWriter pw;
	
	public SocketConnect(){
	}
	
    public void connect() {
        try {
			sk = new Socket(sv,port);
			br=new BufferedReader(new InputStreamReader(sk.getInputStream()));
			pw = new PrintWriter(sk.getOutputStream(),true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    

    public void disconnect() {
    	try {
			sk.close();
		} catch (Exception e) {
		}
    }
    
    public void send(String s){
		try {
			pw.println(""+s);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    String output;
    public String receive() throws IOException{
		output = null;
		output=br.readLine();
		if(output!=null)return output;
		return "";
    }
    
    public void update(String s,sqlite sql,Handler h){
    	new updateData(s, sql, h).start();
    }
    
    class updateData extends Thread{
    	String str;
    	sqlite sql;
    	Handler ha;
    	public updateData(String s,sqlite sql,Handler h) {
    		str = s; ha = h;
    		this.sql = sql;
		}
    	public void run(){
			try {
				connect();
	    		send(str);
	    		String js;
				js = receive();
				if (str.equals("Matches")) {
	    			sql.updateMatches(js,ha);
	    		}else if (str.equals("TeamsInRound")) {
	    			sql.updateTeamsInRound(js,ha);
	    		}else if (str.equals("BetDetail")) {
	    			sql.updateBet(js,ha);
	    		}
//	    		else System.out.println("receive unprocess text:"+js);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    public void vote(final int imatchID,final int iteam){
    	new Thread(new Runnable() {
    		int matchID = imatchID,team = iteam;
			public void run() {
//		    	String js;
				try {
					connect();
			    	String s = "Pickup-"+matchID+"-"+team;
			    	send(s);
//					js = receive();
//					System.out.println(js);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
    }
    
    public boolean checkError(){
    	return pw.checkError();
    }
}