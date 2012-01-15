package com.candroid.objects;

import java.util.ArrayList;

import com.candroid.huyenhoc.ChildMenu;

public class ChildMenus {
	ArrayList<ChildMenu> childmenus;
	
	public ChildMenus(){
		childmenus = new ArrayList<ChildMenu>();
	}
	
	public void addItem(ChildMenu childmenu){
		childmenus.add(childmenu);
	}
	public ChildMenu getItem(int position){
		return childmenus.get(position);
	}
	public int count(){
		return childmenus.size();
	}
}
