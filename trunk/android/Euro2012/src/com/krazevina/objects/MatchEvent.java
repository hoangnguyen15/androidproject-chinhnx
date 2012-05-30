package com.krazevina.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class MatchEvent {
	int playerID,matchID,teamID,eventID;
	int ID;
	int status;
	String time;
	String detail;
//MatchDetail-{"ID":0,"MatchID":24,"TeamID":186,"PlayerID":66,"EventID":8,"Detail":"1-1","MatchTime":"89","Status":1}

	public MatchEvent(String js) throws Exception{
		JSONObject o = new JSONObject(js.substring(12));
		playerID = o.getInt("PlayerID");
		matchID = o.getInt("MatchID");
		teamID = o.getInt("TeamID");
	}
}
