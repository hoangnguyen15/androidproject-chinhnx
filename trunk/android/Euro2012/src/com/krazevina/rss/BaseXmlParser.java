package com.krazevina.rss;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.Uri;

public abstract class BaseXmlParser implements XmlParser {

    // names of the XML tags
	public static final String ITEM="item";
	public static final String TITLE="title";	
	public static final String LINK="link";
	public static final String DESCRIPTION="description";
	public static final String PUB_DATE="pubdate";
	public static final String CHANNEL="channel";
	public static final String ENCLOSURE="enclosure";
	public static final String CONTENT="content";
	
	final URL feedUrl;
	String url;

    protected BaseXmlParser(String feedUrl){
    	
        try {
        	
           this.feedUrl = new URL(feedUrl);
           url = feedUrl;
           
        } catch (MalformedURLException e) {
        	
            throw new RuntimeException(e);
        }
    }

    protected Reader getInputStream() {
    	StringBuilder s = new StringBuilder();
		
        try {
        	Uri raw = Uri.parse(url);
			URI fine = new URI(raw.getScheme(),raw.getUserInfo(),raw.getHost(),
					raw.getPort(),raw.getPath(),raw.getQuery(),raw.getFragment());
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
			HttpGet httpget = new HttpGet(fine);
			httpget.addHeader("user-agent", "Mozilla/5.0 (Windows 7) Gecko/20100101 Firefox/8.0");
			httpget.addHeader("accept-language","en-us,en;q=0.5");
			
			HttpResponse res = httpclient.execute(httpget);
        	InputStream i = res.getEntity().getContent();
        	BufferedReader b = new BufferedReader(new InputStreamReader(i));
        	String s1;
        	while((s1=b.readLine())!=null){
        		s.append(s1);s.append("\n");}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new StringReader(s.toString());
    }
}
