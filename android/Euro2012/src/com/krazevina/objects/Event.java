package com.krazevina.objects;

import org.json.JSONObject;

public class Event{
	public int ID,matchID,teamID,playerID,eventID,status;
	public String detail,time;
	public Event(String js) throws Exception{
		System.out.println(js);
		JSONObject o = new JSONObject(js.substring(12));
		playerID = o.getInt("PlayerID");
		matchID = o.getInt("MatchID");
		teamID = o.getInt("TeamID");
		eventID = o.getInt("EventID");
		status = o.getInt("Status");
		time = o.getString("MatchTime");
		detail = o.getString("Detail");
	}
	public Event(JSONObject o) throws Exception{
		playerID = o.getInt("PlayerID");
		matchID = o.getInt("MatchID");
		teamID = o.getInt("TeamID");
		eventID = o.getInt("EventID");
		status = o.getInt("Status");
		time = o.getString("MatchTime");
		detail = o.getString("Detail");
	}
	public Event(){
	}
	
	public boolean equal(Event e){
		if(playerID!=e.playerID)return false;
		if(matchID!=e.matchID)return false;
		if(teamID!=e.teamID)return false;
		if(eventID!=e.eventID)return false;
		if(!time.equals(e.time))return false;
		if(!detail.equals(e.detail))return false;
		return true;
	}
}