package com.krazevina.lichvannien;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog 
{
	public CustomDialog(Context context, String s) 
	{
		super(context);
		setContentView(R.layout.dialog);
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		TextView tv = (TextView)findViewById(R.id.text);
		tv.setText(s);
		Button bt = (Button)findViewById(R.id.btnok);
		bt.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				dismiss();
			}
		});
	}

}
