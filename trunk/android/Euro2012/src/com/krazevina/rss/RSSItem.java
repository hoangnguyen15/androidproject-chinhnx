package com.krazevina.rss;

public class RSSItem 
{
	private String _title = null;
	private String _description = null;
	private String _link = null;
	private String _category = null;
	private String _pubdate = null;
	private String _enclosure = null;
	
	public void setTitle(String title){
		this._title = title;	
	}
	public void setDescription(String description){
		this._description = description;	
	}
	
	public void setLink(String link){
		this._link = link;	
	}
	
	public void setCategory(String category){
		this._category = category;	
	}
	
	public void setPubDate(String pubdate)
	{	this._pubdate = pubdate;	}
	
	public String getTitle()
	{	return _title;	}
	
	public String getDescription(){
		return _description;	
	}
	
	public String getLink(){
		return _link;	
	}
	public String getCategory(){
		return _category;	
	}
	
	public String getPubDate(){
		return _pubdate;	
	}
	
	public void subDescription()
	{
		// limit how much text we display
		if (_description.length()> 400)
		{
			_description= _description.substring(0, 400-2) + "...";
			/*
			while(true){
				if(!_description.substring(_description.length()-2, _description.length()-1).equals(" ")){
					_description.substring(0, _description.length()-1);					
				}
				else break;
			}*/
			
		}	
		
	}	
	public void subTitle()
	{
		// limit how much text we display
		if (_title.length()> 50)
		{
			_title=_title.substring(0, 50-2) + "...";
		}
		
		
	}
	public String getEnclosure() {
		return _enclosure;
	}
	public void setEnclosure(String _enclosure) {
		this._enclosure = _enclosure;
	}
}
