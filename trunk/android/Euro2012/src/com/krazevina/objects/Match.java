package com.krazevina.objects;

import java.util.Vector;

public class Match {
	public int ID,groupID,team1,team2,firstPick,secPick,status;
	public String stadium,start,end,finalScore,referee;
	public Vector<Event> events;
}
