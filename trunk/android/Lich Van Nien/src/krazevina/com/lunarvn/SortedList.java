package krazevina.com.lunarvn;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class SortedList {
private ArrayList<Date> list = new ArrayList<Date>();
	
	public ArrayList<Date> getList() {
		return list;
	}
	
	public Date get(int i) {
		return list.get(i);
	}
	
	public Date first() {
		return list.get(0);
	}
	
	public void clear() {
		list.clear();
	}
	
	public void add(Date c) {
		list.add(c);
		Collections.sort(list);
	}
	
	public void remove(Calendar c) {
		list.remove(c);
	}

	public int size() {
		return list.size();
	}
	
	public boolean contains(Date c) {
		return list.contains(c);}
	
	/*
	private ArrayList<Calendar> list = new ArrayList<Calendar>();
	
	public ArrayList<Calendar> getList() {
		return list;
	}
	
	public Calendar get(int i) {
		return list.get(i);
	}
	
	public Calendar first() {
		return list.get(0);
	}
	
	public void clear() {
		list.clear();
	}
	
	public void add(Calendar c) {
		list.add(c);
		Collections.sort(list);
	}
	
	public void remove(Calendar c) {
		list.remove(c);
	}

	public int size() {
		return list.size();
	}
	
	public boolean contains(Calendar c) {
		return list.contains(c);
	}*/
}
