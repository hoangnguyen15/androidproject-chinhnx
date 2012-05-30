package com.krazevina.objects;

import org.json.JSONObject;

public class MatchEvent {
	public int playerID,matchID,teamID,eventID;
	public int ID;
	public int status;
	public String time;
	public String detail;

	public MatchEvent(String js) throws Exception{
		JSONObject o = new JSONObject(js.substring(12));
		playerID = o.getInt("PlayerID");
		matchID = o.getInt("MatchID");
		teamID = o.getInt("TeamID");
		eventID = o.getInt("EventID");
		status = o.getInt("Status");
		time = o.getString("MatchTime");
		detail = o.getString("Detail");
	}
	public MatchEvent(JSONObject o) throws Exception{
		playerID = o.getInt("PlayerID");
		matchID = o.getInt("MatchID");
		teamID = o.getInt("TeamID");
		eventID = o.getInt("EventID");
		status = o.getInt("Status");
		time = o.getString("MatchTime");
		detail = o.getString("Detail");
	}
}
