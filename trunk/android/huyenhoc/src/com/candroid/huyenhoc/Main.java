package com.candroid.huyenhoc;

import com.candroid.objects.Global;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;

public class Main extends Activity {
    EditText edt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/HL-OngDo-Unicode.ttf");
        Global.face = tf;
        setContentView(R.layout.main);
        edt=(EditText)findViewById(R.id.edt_name);
        edt.setTypeface(tf);
    }
}