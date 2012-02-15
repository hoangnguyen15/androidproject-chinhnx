package com.candroid.huyenhoc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.util.ByteArrayBuffer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.candroid.objects.Global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener{
	Button btnApp,btnChange,btnSetting,btnIntro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		btnApp = (Button)findViewById(R.id.btnApp);
		btnChange = (Button)findViewById(R.id.btnChange);
		btnSetting = (Button)findViewById(R.id.btnSetting);
		btnIntro = (Button)findViewById(R.id.btnIntro);
		btnApp.setOnClickListener(this);
		btnChange.setOnClickListener(this);
		btnSetting.setOnClickListener(this);
		btnIntro.setOnClickListener(this);
		
		//Save xml file to sdCard
		String fileName = "huyenhoc.xml";
		String urlDownload = "http://krazevina.com/merge.xml";
		Global.downloadFromUrl(urlDownload, fileName);
		
		//Parse xml

				
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == btnApp.getId()){
			startActivity(new Intent(this,Options.class));
		}
		if(v.getId() == btnSetting.getId()){
			startActivity(new Intent(this,Main.class));
			finish();
		}
		
	}
}
