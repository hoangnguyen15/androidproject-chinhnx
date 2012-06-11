package com.krazevina.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import com.krazevina.euro2012.R;


public class SocketConnect {
	String sv;
	int port = 4142;
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
		sv = ""+123+"."+30+"."+187+"."+134;
		port--;
	}
	
    public void connect() throws Exception{
		sk = new Socket(sv,port);
		br=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		pw = new PrintWriter(sk.getOutputStream(),true);
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
		}
    }
    
    String output;
    public String receive() throws IOException{
		output = null;
		output=br.readLine();
		if(output!=null)return output;
		return "";
    }
    
    public void update(String s,sqlite sql,Handler h,Context c){
    	new updateData(s, sql, h,c).start();
    }
    
    class updateData extends Thread{
    	String str;
    	sqlite sql;
    	Handler ha;
    	Context c;
    	public updateData(String s,sqlite sql,Handler h,Context c) {
    		str = s; ha = h;
    		this.sql = sql;this.c = c;
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
				Intent i = new Intent("updatelayout");
				c.sendBroadcast(i);
//	    		else System.out.println("receive unprocess text:"+js);
			} catch (Exception e) {
			}
    	}
    }
    
    public void vote(final int imatchID,final int iteam,final Handler h,final Context c, final Match m, final Button btnvote1, final Button btnvote2,final Team t1,final Team t2) throws Exception{
    	new Thread(new Runnable() {
    		int matchID = imatchID,team = iteam;
			public void run() {
				try{
					connect();
					String s = "Pickup-"+matchID+"-"+team;
			    	send(s);
			    	h.post(new Runnable() {
						@Override
						public void run() {
					    	if(iteam == 1){
					    		m.firstPick++;
								btnvote1.setText(""+m.firstPick);
								btnvote1.setEnabled(false);
								btnvote2.setEnabled(false);
								Toast.makeText(c,btnvote1.getContext().getString(R.string.vote1)+ t1.getName() +" " + btnvote1.getContext().getString(R.string.vote2), 0).show();
					    	}else if(iteam ==2){
					    		m.secPick++;
								btnvote2.setText(""+m.secPick);
								btnvote2.setEnabled(false);
								btnvote1.setEnabled(false);
								Toast.makeText(c,btnvote1.getContext().getString(R.string.vote1)+ t2.getName() +" "+ btnvote2.getContext().getString(R.string.vote2), 0).show();
					    	}
						}
					});
			    	
				}catch (Exception e) {
					h.post(new Runnable() {
						public void run() {
							Toast.makeText(c, R.string.nonetwork, 0).show();
						}
					});
					e.printStackTrace();
				}
			}
		}).start();
    }
    
    public boolean checkError(){
    	return pw.checkError();
    }
}