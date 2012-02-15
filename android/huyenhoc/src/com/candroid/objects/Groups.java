package com.candroid.objects;

import java.util.ArrayList;

import com.candroid.huyenhoc.ChildMenu;

public class Groups {
	ArrayList<Group> groups;
	
	public Groups(){
		groups = new ArrayList<Group>();
	}
	
	public void addItem(Group group){
		groups.add(group);
	}
	public Group getItem(int position){
		return groups.get(position);
	}
	public int count(){
		return groups.size();
	}
}
