package com.krazevina.beautifulgirl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.krazevina.objects.Global;
import com.krazevina.webservice.CallWebService;

public class Active extends Activity implements OnClickListener, OnCheckedChangeListener{

	EditText edt_key;
	
	Button btn_upgrade;
	Handler handler;
	ProgressDialog pg;
	CallWebService call;
	String response;
	RadioButton rbsms,rbcode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upgrade);
		handler = new Handler();
		edt_key = (EditText)findViewById(R.id.txtkey);
		rbsms = (RadioButton)findViewById(R.id.rbsms);
		rbcode = (RadioButton)findViewById(R.id.rbcode);
		btn_upgrade = (Button)findViewById(R.id.btn_upgrade);
		
		call = new CallWebService(this);
		
		btn_upgrade.setOnClickListener(this);
		
		rbsms.setOnCheckedChangeListener(this);
		rbcode.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onClick(View v) 
	{
		if(v.getId()==btn_upgrade.getId())
		{
			if(rbsms.isChecked())
			{
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				        	sendSMS("8032", "KV a_"+Global.username);
//				        	finish();
				            break;
				        case DialogInterface.BUTTON_NEGATIVE:
				            break;
				        }
				    }
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(getString(R.string.sms_question)).setPositiveButton(getString(R.string.yes), dialogClickListener)
				    .setNegativeButton(getString(R.string.no), dialogClickListener).show();
				
				setResult(11);
			}
			else
			{
				if(edt_key.getText().toString().length()>0)
					new checkCode().start();
				else Toast.makeText(this, getString(R.string.not_blank), 0).show();
			}
		}
	}
	
	ProgressDialog prog;
	
	class checkCode extends Thread
	{
		public void run()
		{
			try{
				handler.post(new Runnable() {
					public void run() {
						if(prog==null||!prog.isShowing())prog = ProgressDialog.show(Active.this, "", getString(R.string.pleasewait));
					}
				});
				CallWebService call = new CallWebService(Active.this);
				boolean ret = call.checkCode(edt_key.getText().toString(),Global.username);
				if(ret)
				{
					handler.post(new Runnable() {
						public void run() {
							try{
								Toast.makeText(Active.this, getString(R.string.legalcode), 1).show();
								setResult(12);
								if(prog!=null&&prog.isShowing())prog.dismiss();
								finish();
							}catch (Exception e) {
							}
						}
					});
				}
				else
				{
					handler.post(new Runnable() {
						public void run() {
							try{
								Toast.makeText(Active.this, getString(R.string.illegalcode), 1).show();
							if(prog!=null&&prog.isShowing())prog.dismiss();
							}catch (Exception e) {
							}
						}
					});
				}
			}catch (Exception e) {
			}
		}
	}
	
	
	private void sendSMS(String phoneNumber, String message)
    {        
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
 
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), getString(R.string.sms_sent), Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), getString(R.string.sms_fail), 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), getString(R.string.sms_fail), 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), getString(R.string.sms_fail), 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), getString(R.string.sms_fail), 
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));
 
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), getString(R.string.sms_sent), Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), getString(R.string.sms_fail), 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));        
 
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);        
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(rbsms.isChecked()){
			edt_key.setVisibility(View.INVISIBLE);
		}
		
		if(rbcode.isChecked()){
			edt_key.setVisibility(View.VISIBLE);
		}
		
	}
	
}
