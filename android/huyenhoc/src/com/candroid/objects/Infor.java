package com.candroid.objects;

import java.sql.Date;

public class Infor {
	private String name;
	private int dayofbith;
	private int monthofbith;
	private int yearofbith;
	private int hourofbith;
	private int minuteofbith;
	private int isactive;
	private int sex;
	private String deadline;
	
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setDayofbith(int dayofbith) {
		this.dayofbith = dayofbith;
	}
	public int getDayofbith() {
		return dayofbith;
	}
	public void setMonthofbith(int monthofbith) {
		this.monthofbith = monthofbith;
	}
	public int getMonthofbith() {
		return monthofbith;
	}
	public void setYearofbith(int yearofbith) {
		this.yearofbith = yearofbith;
	}
	public int getYearofbith() {
		return yearofbith;
	}
	public void setHourofbith(int hourofbith) {
		this.hourofbith = hourofbith;
	}
	public int getHourofbith() {
		return hourofbith;
	}
	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}
	public int getIsactive() {
		return isactive;
	}
	public void setMinuteofbith(int minuteofbith) {
		this.minuteofbith = minuteofbith;
	}
	public int getMinuteofbith() {
		return minuteofbith;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getSex() {
		return sex;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getDeadline() {
		return deadline;
	}
}
