package krazevina.com.rss;

import java.util.ArrayList;

import android.util.Log;

public class RSSFeed 
{
	private String _title = null;
	private String _description=null;
	private String _link=null;
	private String _pubdate = null;
	private int _itemcount = 0;
	public int index=0;
	private ArrayList<RSSItem> _itemlist;	
	
	public RSSFeed()
	{
		_itemlist = new ArrayList<RSSItem>();
	}
	public int addItem(RSSItem item)
	{		
		_itemlist.add(item);
		_itemcount++;
		return _itemcount;
	}
	public RSSItem getItem(int location)
	{
		return _itemlist.get(location);
	}
	public int getTotalPage()
	{
		int totalpage=0;
		if(_itemlist.size()%2==0){
			totalpage=_itemlist.size()/2;
		}else{
			totalpage=(_itemlist.size()+1)/2;
		}
		return totalpage;
	}
	public void Clear()
	{
		_itemlist.clear();
		index=0;
		_itemcount=0;
	}
	public int getCount()
	{
		return _itemlist.size();
	}
	public ArrayList<RSSItem> get2ItemNext(){
		ArrayList<RSSItem> rssfeed=null;
		try{
			rssfeed=new ArrayList<RSSItem>();
			if(index>=_itemlist.size())
				return null;
			if(index==_itemlist.size()-1){	
				rssfeed.add(_itemlist.get(index++));
				index++;
			}else{
				rssfeed.add(_itemlist.get(index++));
				rssfeed.add(_itemlist.get(index++));			
			}
		}catch (Exception e) {
			Log.e("get2ItemNext", e.toString());
		}
		
		return rssfeed;
	}
	public ArrayList<RSSItem> get2Items(int position){
		ArrayList<RSSItem> list2item=null;
		try{
			list2item=new ArrayList<RSSItem>();
			list2item.add(_itemlist.get(position));
			list2item.add(_itemlist.get(position+1));
		}catch (Exception e) {
			Log.e("get2Items", e.toString());
		}
			
			return list2item;
	}
	public ArrayList<RSSItem> get2LastItems(){
		ArrayList<RSSItem> list2item=null;
		try{
			list2item=new ArrayList<RSSItem>();
			list2item.add(_itemlist.get(_itemlist.size()-2));
			list2item.add(_itemlist.get(_itemlist.size()-1));
			index=_itemlist.size();
		}catch (Exception e) {
			Log.e("get2LastItems", e.toString());
		}			
			return list2item;
	}
	public ArrayList<RSSItem> get2ItemPrevious(){
		ArrayList<RSSItem> rssfeed=null;
		try{
			index=index-4;
			if(index<0){
				index=0;
				return null;
			}				
			rssfeed=get2ItemNext();
		}catch (Exception e) {
			Log.e("get2ItemPrevious", e.toString());
		}		
		return rssfeed;
	}
	public ArrayList<RSSItem> getAllItems()
	{
		return _itemlist;
	}
	public void FormatRSSFeed()
	{
		for(int i=0;i<_itemlist.size();i++){
			_itemlist.get(i).subDescription();
			_itemlist.get(i).subTitle();			
		}
	}
	public int getItemsCount() {
		return _itemcount;
	}
	
	public void setTitle(String title) {
		_title = title;
	}
	
	public String getTitle() {
		return _title;
	}
	
	public void setPubDate(String pubdate) {
		_pubdate = pubdate;
	}
	public String getPubDate() {
		return _pubdate;
	}
	
	public void setDecription(String description) {
		_description=description;
	}
	public String getDescription() {
		return _description;
	}
	
	public void setLink(String link) {
		_link=link;
	}
	public String getLink() {
		return _link;
	}
	
}
