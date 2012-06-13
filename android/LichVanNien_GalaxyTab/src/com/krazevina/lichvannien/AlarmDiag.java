package com.krazevina.lichvannien;

import android.app.Activity;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.krazevina.objects.Reminder;

public class AlarmDiag extends Activity
{
	public static Ringtone r;
	public static Reminder re;
	TextView txttit,txtcont;
	Button btnok;
	Vibrator vib;
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.alarm);
		txtcont = (TextView)findViewById(R.id.txtcont);
		txttit = (TextView)findViewById(R.id.txttit);
		btnok = (Button)findViewById(R.id.btnok);
		txtcont.setText(re.content);
		txttit.setText(re.title);
		int dot = 200;      // Length of a Morse Code "dot" in milliseconds
		int dash = 500;     // Length of a Morse Code "dash" in milliseconds
		int short_gap = 200;    // Length of Gap Between dots/dashes
		int medium_gap = 500;   // Length of Gap Between Letters
		int long_gap = 1000;    // Length of Gap Between Words
		long[] pattern = {
		    0,  // Start immediately
		    dot, short_gap, dot, short_gap, dot,    // s
		    medium_gap,
		    dash, short_gap, dash, short_gap, dash, // o
		    medium_gap,
		    dot, short_gap, dot, short_gap, dot,    // s
		    long_gap
		};
		
		try{
			vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
			if(re.alarmstyle!=2)
				vib.vibrate(pattern,10);
		}catch(Exception e){}
		
		try{
			if(re.alarmstyle==1)
				r.play();
		}catch(Exception e)
		{
		}
		
		
		btnok.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				try{
					r.stop();
				}catch(Exception e){}
				try{
					vib.cancel();
				}catch(Exception e){}
				finish();
			}
		});
	}
}
