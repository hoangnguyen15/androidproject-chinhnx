package com.candroid.objects;

import java.util.ArrayList;

import com.candroid.huyenhoc.ChildMenu;

public class Cates {
	ArrayList<Cate> cates;
	
	public Cates(){
		cates = new ArrayList<Cate>();
	}
	
	public void addItem(Cate cate){
		cates.add(cate);
	}
	public Cate getItem(int position){
		return cates.get(position);
	}
	public int count(){
		return cates.size();
	}
}
