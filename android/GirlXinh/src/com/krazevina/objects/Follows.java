package com.krazevina.objects;


import java.util.ArrayList;

public class Follows {
	private ArrayList<Follow> _follows;
	
	public Follows(){
		_follows= new ArrayList<Follow>();
	}
	
	public void add(Follow follow){
		_follows.add(follow);
	}	
	
	public int getCount(){
		return _follows.size();
	}
	
	public Follow getItemAtPosition(int position){
		return _follows.get(position);
	}
	
	public void add(Follows follows) {
		if(follows!=null) {
			int i=0;
			while(i<follows.getCount()) {
				_follows.add(follows.getItemAtPosition(i++));
			}
		}
	}
}
