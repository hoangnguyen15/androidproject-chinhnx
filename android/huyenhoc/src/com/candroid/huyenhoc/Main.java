package com.candroid.huyenhoc;

import com.candroid.objects.Global;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity implements OnClickListener {
    EditText edtName;
    Button btnFinish;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/HL-OngDo-Unicode.ttf");
        Global.face = tf;
        setContentView(R.layout.main);
        edtName=(EditText)findViewById(R.id.edtName);
        edtName.setTypeface(tf);
        btnFinish=(Button)findViewById(R.id.btnFinish);
        
        btnFinish.setOnClickListener(this);
        
    }
	@Override
	public void onClick(View v) {
		if(v.getId() == btnFinish.getId()){
			startActivity(new Intent(this,MainMenu.class));
		}
		
	}
}