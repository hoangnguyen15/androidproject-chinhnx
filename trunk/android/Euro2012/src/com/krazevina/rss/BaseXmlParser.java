package com.krazevina.rss;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public abstract class BaseXmlParser implements XmlParser {

    // names of the XML tags
	public static final String ITEM="item";
	public static final String TITLE="title";	
	public static final String LINK="link";
	public static final String DESCRIPTION="description";
	public static final String PUB_DATE="pubdate";
	public static final String CHANNEL="channel";
	
	final URL feedUrl;

    protected BaseXmlParser(String feedUrl){
    	
        try {
        	
           this.feedUrl = new URL(feedUrl);
           
        } catch (MalformedURLException e) {
        	
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
