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

public class DigitalLoungeParser {
	private static final String APP = "app";
	private static final String VERSION = "version";
	private static final String SMSACTIVE = "smsactive";
	private static final String SMSINBOX = "smsinbox";
	private static final String SRVSNAME = "srvsname";
	private static final String GIODEP = "giodep";
	private static final String HUONGDEP = "huongdep";
	private static final String MAUDEP = "maudep";
	private static final String NGAYDEP = "ngaydep";
	private static final String PHONGTHUY = "phongthuy";
	private static final String QUANHE = "quanhe";
	private static final String TUVI = "tuvi";
	private static final String DUDOAN = "dudoan";

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
	File file;
	Group group;
	Groups groups;
	
	public void parseXML(int type) throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		// get a reference to the file.
		file = new File(Environment.getExternalStorageDirectory()
				+ "/huyenhocxml/huyenhoc.xml");
		
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
				groups = new Groups();
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				System.out.println("End document");
			} else if (eventType == XmlPullParser.START_TAG) {
				nodeName = xpp.getName();
				if (nodeName.contentEquals(VERSION)) {
					Global.version = xpp.nextText();
				}
				if (nodeName.contentEquals(SMSACTIVE)) {
					Global.smsactive = xpp.nextText();
				}
				if (nodeName.contentEquals(SMSINBOX)) {
					Global.smsinbox = xpp.nextText();
				}
				if (nodeName.contentEquals(TUVI) ) {					
					currentSection = 0;
				}
				if (nodeName.contentEquals(MAUDEP) ) {
					currentSection = 1;
				}
				if (nodeName.contentEquals(QUANHE) ) {
					currentSection = 2;
				}
				if (nodeName.contentEquals(GIODEP) ) {
					currentSection = 3;
				}
				if (nodeName.contentEquals(NGAYDEP) ) {
					currentSection = 4;
				}
				if (nodeName.contentEquals(DUDOAN) ) {
					currentSection = 5;
				}
				if (nodeName.contentEquals(HUONGDEP) ) {
					currentSection = 6;
				}
				if (nodeName.contentEquals(PHONGTHUY) ) {
					currentSection = 7;
				}
//				Log.d("curentSetion",""+currentSection);
				switch (type) {
				case 0:
					if(currentSection == type){
						if(nodeName.contentEquals(SRVSNAME)){
							group = new Group();
							group.setSrvName(xpp.getAttributeValue(0));
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
						if(nodeName.contentEquals(SRVSNAME)){
							group = new Group();
							group.setSrvName(xpp.getAttributeValue(0));
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
				case 2:
					if(currentSection == type){
						if(nodeName.contentEquals(SRVSNAME)){
							group = new Group();
							group.setSrvName(xpp.getAttributeValue(0));
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
				case 3:
					if(currentSection == type){
						if(nodeName.contentEquals(SRVSNAME)){
							group = new Group();
							group.setSrvName(xpp.getAttributeValue(0));
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
				case 4:
					if(currentSection == type){
						if(nodeName.contentEquals(SRVSNAME)){
							group = new Group();
							group.setSrvName(xpp.getAttributeValue(0));
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
				case 5:
					if(currentSection == type){
						if(nodeName.contentEquals(SRVSNAME)){
							group = new Group();
							group.setSrvName(xpp.getAttributeValue(0));
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
				case 6:
					if(currentSection == type){
						if(nodeName.contentEquals(SRVSNAME)){
							group = new Group();
							group.setSrvName(xpp.getAttributeValue(0));
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
				case 7:
					if(currentSection == type){
						if(nodeName.contentEquals(SRVSNAME)){
							group = new Group();
							group.setSrvName(xpp.getAttributeValue(0));
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
				default:
					break;
				}

			} else if (eventType == XmlPullParser.END_TAG) {
				nodeName = xpp.getName();
				if(currentSection == type){
					if(nodeName.contentEquals(SRVSNAME)){
						groups.addItem(group);
						Global.groups = groups;
					}
				}
			} else if (eventType == XmlPullParser.TEXT) {
			}
			eventType = xpp.next();
		}
	}

}
