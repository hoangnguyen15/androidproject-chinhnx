package com.krazevina.objects;

public class Team {
	public int ID;
	public String name,flag,uniform1,uniform2,coach,desc,attendTimes,
	nameEng,nameKor,descEng,descKor;
	public int establish,fifaJoin,fifaRank,status,
	roundID,point,win,lose,draw,goallose,goalscore;
	public String getName(){
		if(Global.lang.equals("EN"))return nameEng;
		else if(Global.lang.equals("KO"))return nameKor;
		else return name;
	}
	public String getDesc(){
		if(Global.lang.equals("EN"))return descEng;
		else if(Global.lang.equals("KO"))return descKor;
		else return desc;
	}
}
