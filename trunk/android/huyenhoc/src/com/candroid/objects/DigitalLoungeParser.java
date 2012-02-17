package com.candroid.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;



import android.os.Environment;
import android.util.Log;

public class DigitalLoungeParser {
	private static final String APP = "app";
	private static final String VERSION = "version";
	private static final String SMSACTIVE = "smsactive";
	private static final String SMSINBOX = "smsinbox";
	private static final String SRVSNAME = "srvsname";
	private static final String CANHAN = "canhan";
	private static final String HNGD = "hngd";
	private static final String NC = "nc";
	private static final String SHCS = "shcs";
	private static final String CD = "cd";
	private static final String TY = "ty";
	private static final String TT = "tt";
	private static final String CV = "cv";

	private static final String DES = "des";
	private static final String	MC = "mc";
	private static final String MP = "mp";
	private static final String PARAM1 = "param1";
	private static final String PARAM2 = "param2";
	private static final String PARAM3 = "param3";
	private static final String PARAM4 = "param4";
	private static final String SMSNUMBER = "smsnumber";
	private static final String OPTION = "option";
	private static final String URL = "url";
	private int currentSection = -1;
	String nodeName;
	String des,mc,mp,param1,param2,param3,param4,smsnumber,option,url;
	private boolean isParsed = false;
	Cates cates;
	Cate cate;
	File file;
	Group group;
	Groups groups;

	public void getVersion(int type) throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		// get a reference to the file.
		file = new File(Environment.getExternalStorageDirectory()
				+ "/huyenhocxml/merge.xml");
		
		// create an input stream to be read by the stream reader.
		FileInputStream fis = new FileInputStream(file);

		// set the input for the parser using an InputStreamReader
		xpp.setInput(new InputStreamReader(fis));

		int eventType = xpp.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			// set flags for main tags.
			if (eventType == XmlPullParser.START_DOCUMENT) {
				// TODO only parse if the timestamps don't match.
				System.out.println("Start document");
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				System.out.println("End document");
			} else if (eventType == XmlPullParser.START_TAG) {
				String nodeName = xpp.getName();
				if (nodeName.contentEquals(VERSION)) {
					Global.version = xpp.nextText();
				}
				if (nodeName.contentEquals(SMSACTIVE)) {
				}
				if (nodeName.contentEquals(SMSINBOX)) {
				}
			}
		}
	}
	
	public Cates parseXML(int type) throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		// get a reference to the file.
		file = new File(Environment.getExternalStorageDirectory()
				+ "/huyenhocxml/merge.xml");
		
		// create an input stream to be read by the stream reader.
		FileInputStream fis = new FileInputStream(file);

		// set the input for the parser using an InputStreamReader
		xpp.setInput(new InputStreamReader(fis));

		int eventType = xpp.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {

			// set flags for main tags.
			if (eventType == XmlPullParser.START_DOCUMENT) {
				// TODO only parse if the timestamps don't match.
				System.out.println("Start document");
				cates = new Cates();
				groups = new Groups();
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				System.out.println("End document");
			} else if (eventType == XmlPullParser.START_TAG) {
				nodeName = xpp.getName();
				if (nodeName.contentEquals(CANHAN) ) {					
					currentSection = 0;
				}
				if (nodeName.contentEquals(HNGD) ) {
					currentSection = 1;
				}
				if (nodeName.contentEquals(NC) ) {
					currentSection = 2;
				}
				if (nodeName.contentEquals(SHCS) ) {
					currentSection = 3;
				}
				if (nodeName.contentEquals(CD) ) {
					currentSection = 4;
				}
				if (nodeName.contentEquals(TY) ) {
					currentSection = 5;
				}
				if (nodeName.contentEquals(TT) ) {
					currentSection = 6;
				}
				if (nodeName.contentEquals(CV) ) {
					currentSection = 7;
				}
				
				Log.d("curentSetion",""+currentSection);
				switch (type) {
				case 0:
					if(currentSection == type){
						if(nodeName.contentEquals(SRVSNAME)){
							cate = new Cate();
							group = new Group();
							cate.setServiceName(xpp.getAttributeValue(0));
							cates.addItem(cate);
						}

						if(nodeName.contentEquals(MC)){
							group.setMc(xpp.nextText());
						}
						if(nodeName.contentEquals(DES)){
							group.setDes(xpp.nextText());
						}
						if(nodeName.contentEquals(MP)){
							group.setMp(xpp.nextText());
						}
						if(nodeName.contentEquals(PARAM1)){
							group.setParam1(xpp.nextText());
						}
						if(nodeName.contentEquals(PARAM2)){
							group.setParam2(xpp.nextText());
						}
						if(nodeName.contentEquals(PARAM3)){
							group.setParam3(xpp.nextText());
						}
						if(nodeName.contentEquals(PARAM4)){
							group.setParam4(xpp.nextText());
						}
						if(nodeName.contentEquals(SMSNUMBER)){
							group.setSmsnumber(xpp.nextText());
						}
						if(nodeName.contentEquals(OPTION)){
							group.setOption(xpp.nextText());
						}
						if(nodeName.contentEquals(URL)){
							group.setUrl(xpp.nextText());
						}
						
					}
					break;
				case 1:
					if(currentSection == type){
						cate = new Cate();
						if(nodeName.contentEquals(SRVSNAME)){
							cate.setServiceName(xpp.getAttributeValue(0));
							cates.addItem(cate);
						}
						if(nodeName.contentEquals(MP)){
							Log.d("mc",xpp.nextText());
						}
					}
					break;
				case 2:
					if(currentSection == type){
						cate = new Cate();
						if(nodeName.contentEquals(SRVSNAME)){
							cate.setServiceName(xpp.getAttributeValue(0));
							cates.addItem(cate);
						}
					}
					break;
				case 3:
					if(currentSection == type){
						cate = new Cate();
						if(nodeName.contentEquals(SRVSNAME)){
							cate.setServiceName(xpp.getAttributeValue(0));
							cates.addItem(cate);
						}
					}
					break;
				case 4:
					if(currentSection == type){
						cate = new Cate();
						if(nodeName.contentEquals(SRVSNAME)){
							cate.setServiceName(xpp.getAttributeValue(0));
							cates.addItem(cate);
						}
					}
					break;
				case 5:
					if(currentSection == type){
						cate = new Cate();
						if(nodeName.contentEquals(SRVSNAME)){
							cate.setServiceName(xpp.getAttributeValue(0));
							cates.addItem(cate);
						}
					}
					break;
				case 6:
					if(currentSection == type){
						cate = new Cate();
						if(nodeName.contentEquals(SRVSNAME)){
							cate.setServiceName(xpp.getAttributeValue(0));
							cates.addItem(cate);
						}
					}
					break;
				case 7:
					if(currentSection == type){
						cate = new Cate();
						if(nodeName.contentEquals(SRVSNAME)){
							cate.setServiceName(xpp.getAttributeValue(0));
							cates.addItem(cate);
						}
					}
					break;
				default:
					break;
				}

			} else if (eventType == XmlPullParser.END_TAG) {
				nodeName = xpp.getName();
				
				if(currentSection == type){
					System.out.println("End tag " + xpp.getName());

					if(nodeName.contentEquals(SRVSNAME)){
						Log.d("getMP",""+group.getMp());
						groups.addItem(group);
						Global.groups = groups;
					}
				}
			} else if (eventType == XmlPullParser.TEXT) {
			}
			eventType = xpp.next();
		}
		return cates;
	}

}
