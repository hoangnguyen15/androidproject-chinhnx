package com.krazevina.rss;


import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;

public class XmlReader extends BaseXmlParser {
	
	    public XmlReader(String feedUrl) {
	        super(feedUrl);
	    }
	    public RSSFeed parse() {
	    	RSSFeed rssFeeds = null;
	        try {
	        	XmlPullParser parser = Xml.newPullParser();
	            // auto-detect the encoding from the stream
	            parser.setInput(this.getInputStream(), null);
	            int eventType = parser.getEventType();
	            RSSItem currentFeed = null;
	            boolean done = false;
	            while (eventType != XmlPullParser.END_DOCUMENT && !done){
	                String name = null;
	                switch (eventType){
	                    case XmlPullParser.START_DOCUMENT:
	                    	rssFeeds = new RSSFeed(); 
	                    	rssFeeds.setLink(feedUrl.toString());
	                        break;
	                    case XmlPullParser.START_TAG:	                    	
	                    	name = parser.getName();	/*                        
	                        if(name.equalsIgnoreCase(TITLE)){
	                        	rssFeeds.setTitle(parser.nextText());
	                        }
	                        if(name.equalsIgnoreCase(DESCRIPTION)) {
	                        	rssFeeds.setDecription(parser.nextText());
	                        }*/
	                        if (name.equalsIgnoreCase(ITEM)){
	                        	currentFeed = new RSSItem();
	                        } else if (currentFeed != null){
	                            if (name.equalsIgnoreCase(LINK)){
	                            	currentFeed.setLink(parser.nextText());
	                            } else if (name.equalsIgnoreCase(DESCRIPTION)){
	                            	currentFeed.setDescription(parser.nextText());
	                            } else if (name.equalsIgnoreCase(PUB_DATE)){
	                            	currentFeed.setPubDate(parser.nextText());
	                            } else if (name.equalsIgnoreCase(TITLE)){
	                            	currentFeed.setTitle(parser.nextText());
	                            } else if (name.equalsIgnoreCase(ENCLOSURE)){
	                            	currentFeed.setEnclosure(parser.getAttributeValue(parser.getNamespace(), "url"));
	                            }  
	                        }
                        	break;
	                    case XmlPullParser.END_TAG:
	                        name = parser.getName();
	                        if (name.equalsIgnoreCase(ITEM) && currentFeed != null){
	                        	rssFeeds.addItem(currentFeed);	                        	
	                        } else if (name.equalsIgnoreCase(CHANNEL)){
	                            done = true;
	                        }
	                        break;
	                }	                
	                eventType = parser.next();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return rssFeeds;
	    }
}
	   
