package com.krazevina.objects;


import java.util.ArrayList;

public class Users {
	private ArrayList<User> _users;
	
	public Users(){
		_users= new ArrayList<User>();
	}
	
	public void add(User user){
		_users.add(user);
	}	
	
	public int getCount(){
		return _users.size();
	}
	
	public User getItemAtPosition(int position){
		return _users.get(position);
	}
	
	public void add(Users users) {
		if(users!=null) {
			int i=0;
			while(i<users.getCount()) {
				_users.add(users.getItemAtPosition(i++));
			}
		}
	}
}
