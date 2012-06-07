package com.krazevina.beautifulgirl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.krazevina.objects.CView;
import com.krazevina.objects.Global;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GifActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private CView gifView;
    private Button btnPlay;
    private Button btnPause;
    private Button btnPrevFrame;
    private Button btnNextFrame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.gif);

            gifView = (CView) findViewById(R.id.gifView);
            btnPlay = (Button) findViewById(R.id.btnPlay);
            btnPause = (Button) findViewById(R.id.btnPause);
            btnPrevFrame = (Button) findViewById(R.id.btnPrevFrame);
            btnNextFrame = (Button) findViewById(R.id.btnNextFrame);

            gifView.setGif(Global.gif);
            btnPlay.setOnClickListener(this);
            btnPause.setOnClickListener(this);
            btnPrevFrame.setOnClickListener(this);
            btnNextFrame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnPlay) {
                    gifView.play();
            } else if (id == R.id.btnPause) {
                    gifView.pause();
            } else if (id == R.id.btnPrevFrame) {
                    gifView.prevFrame();
            } else if (id == R.id.btnNextFrame) {
                    gifView.nextFrame();
            }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	super.onWindowFocusChanged(hasFocus);
    	onClick(btnPlay);
    }
	public static byte[] convertInputStreamToByteArray(InputStream is) throws IOException
    {
	    BufferedInputStream bis = new BufferedInputStream(is,8*1024);
	    ByteArrayOutputStream buf = new ByteArrayOutputStream();
	    int result = bis.read();
	    while(result !=-1)
	    {
		    byte b = (byte)result;
		    buf.write(b);
		    result = bis.read();
	    }
	    return buf.toByteArray();
    }
}