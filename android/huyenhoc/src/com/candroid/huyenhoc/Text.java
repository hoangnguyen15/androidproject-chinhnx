package com.candroid.huyenhoc;



import com.candroid.objects.Global;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class Text extends TextView {
        public Text(Context context) {
            super(context);
            setTypeface(Global.face);
        }

        public Text(Context context, AttributeSet attrs) {
            super(context, attrs);
            setTypeface(Global.face);
        }
        public Text(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            setTypeface(Global.face);
        }
        
}
