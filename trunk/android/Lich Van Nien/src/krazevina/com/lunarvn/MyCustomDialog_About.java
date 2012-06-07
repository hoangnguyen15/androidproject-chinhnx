package krazevina.com.lunarvn;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MyCustomDialog_About extends AlertDialog{
	
    public interface ReadyListener {
        public void ready(String name);
    }
    public MyCustomDialog_About(Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        Button buttonOK = (Button) findViewById(R.id.about_close);
        buttonOK.setOnClickListener(new CLOSEListener());       
    }
    private class CLOSEListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) 
        {           
        	MyCustomDialog_About.this.dismiss();
        }
    }

}
