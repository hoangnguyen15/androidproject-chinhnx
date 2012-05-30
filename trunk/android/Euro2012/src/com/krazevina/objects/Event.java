package com.krazevina.objects;

import org.json.JSONObject;

public class Event{
	public int ID,matchID,teamID,playerID,eventID,status;
	public String detail,time;
	public Event(String js) throws Exception{
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
}