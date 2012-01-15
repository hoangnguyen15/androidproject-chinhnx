package com.candroid.huyenhoc;

import com.candroid.objects.Global;
import com.candroid.objects.Sqlite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity implements OnClickListener {
    EditText edtName;
    Button btnFinish;
    Sqlite sql;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/HL-OngDo-Unicode.ttf");
        Global.face = tf;
        setContentView(R.layout.main);
        
        edtName=(EditText)findViewById(R.id.edtName);
        edtName.setTypeface(tf);
        btnFinish=(Button)findViewById(R.id.btnFinish);
        sql = new Sqlite(this);
        btnFinish.setOnClickListener(this);
        
        //GetName
        if(sql.getName().length() == 0){
        	
        }else{
        	startActivity(new Intent(this,MainMenu.class));
        	finish();
        }
        
        sql.close();
        
    }
	@Override
	public void onClick(View v) {
		if(v.getId() == btnFinish.getId()){
			startActivity(new Intent(this,MainMenu.class));
		}
		
	}
}