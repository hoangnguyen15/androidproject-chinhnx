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

	// repeating nodes

	
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

	private boolean isParsed = false;

	public void parseXML() throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		// get a reference to the file.
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/huyenhocxml/merge.xml");
		
		// create an input stream to be read by the stream reader.
		FileInputStream fis = new FileInputStream(file);

		// set the input for the parser using an InputStreamReader
		xpp.setInput(new InputStreamReader(fis));

		int eventType = xpp.getEventType();

		// /

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
					currentSection = 0;
				}
				if (nodeName.contentEquals(SMSACTIVE)) {
					currentSection = 1;
				}
				if (nodeName.contentEquals(SMSINBOX)) {
					currentSection = 2;
				}
				if (nodeName.contentEquals(CANHAN) ) {
					currentSection = 3;
				}
				if (nodeName.contentEquals(HNGD) ) {
					currentSection = 4;
				}
				if (nodeName.contentEquals(NC) ) {
					currentSection = 5;
				}
				if (nodeName.contentEquals(SHCS) ) {
					currentSection = 6;
				}
				if (nodeName.contentEquals(CD) ) {
					currentSection = 7;
				}
				if (nodeName.contentEquals(TY) ) {
					currentSection = 8;
				}
				if (nodeName.contentEquals(TT) ) {
					currentSection = 9;
				}
				if (nodeName.contentEquals(CV) ) {
					currentSection = 10;
				}
				Log.d("parser", "current section :>>" + currentSection);
				switch (currentSection) {
				case 0:
					Log.d("version",xpp.nextText());
					break;
				case 1:
					Log.d("smsactive",xpp.nextText());
					break;
				case 2:
					Log.d("smsinbox",xpp.nextText());
					break;
				case 3:
					if(nodeName.contentEquals(SRVSNAME)){
						Log.d("canhan",xpp.getAttributeValue(0));
					}
					break;
					
				default:
					break;
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				// System.out.println("End tag " + nodeName);
			} else if (eventType == XmlPullParser.TEXT) {
			}
			eventType = xpp.next();
		}
		isParsed = true;
	}



	/**
	 * @return the currentSection
	 */
	public int getCurrentSection() {
		return currentSection;
	}

}
