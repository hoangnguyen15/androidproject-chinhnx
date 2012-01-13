package com.candroid.huyenhoc;



import com.candroid.objects.Global;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

public class Btn extends Button {
        public Btn(Context context) {
            super(context);
            setTypeface(Global.face);
            setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        }

        public Btn(Context context, AttributeSet attrs) {
            super(context, attrs);
            setTypeface(Global.face);
            setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        }
        public Btn(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            setTypeface(Global.face);
            setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        }
        
}
