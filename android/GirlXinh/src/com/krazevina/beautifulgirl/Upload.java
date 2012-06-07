package com.krazevina.beautifulgirl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.kobjects.base64.Base64;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.krazevina.objects.Global;
import com.krazevina.webservice.CallWebService;

public class Upload extends Activity implements OnClickListener,OnTouchListener{
	protected static final int PHOTO_PICKED = 1;
	ImageView img_upload;
	RelativeLayout layout_menu_upload;
	LinearLayout layout_nameimg;
	Button btn_ok,btn_rorate,btn_reup;
	TextView btn_details,txt_name_img;
	String filename_upload;
	Bitmap bitmap_upload;
	Animation anmDown,anmUp;
	boolean menuvisible;
	String response;
	CallWebService call;
	Handler handler;
	ProgressDialog pg;
	String filePath;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
		
		img_upload = (ImageView)findViewById(R.id.img_upload);
		layout_menu_upload = (RelativeLayout)findViewById(R.id.layout_menu_upload);
		layout_nameimg = (LinearLayout)findViewById(R.id.layout_nameimg);
		btn_ok = (Button)findViewById(R.id.btn_ok_upload);
		btn_rorate = (Button)findViewById(R.id.btn_rorate);
		btn_reup = (Button)findViewById(R.id.btn_reup);
		btn_details = (TextView)findViewById(R.id.btn_details);
		txt_name_img = (TextView)findViewById(R.id.txt_nameimg);
		
		menuvisible = true;
		handler = new Handler();
		anmDown = AnimationUtils.loadAnimation(this, R.anim.downout);
		anmUp = AnimationUtils.loadAnimation(this, R.anim.upout);
        anmDown.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				layout_menu_upload.setVisibility(View.GONE);
			}
		});
        
        anmUp.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				layout_nameimg.setVisibility(View.GONE);
			}
		});
		
		btn_ok.setOnClickListener(this);
		btn_reup.setOnClickListener(this);
		btn_details.setOnClickListener(this);
		btn_rorate.setOnClickListener(this);
		call = new CallWebService(this);
		
		btn_ok.setOnTouchListener(this);
		btn_reup.setOnTouchListener(this);
		btn_rorate.setOnTouchListener(this);
		btn_details.setOnTouchListener(this);
		
		img_upload.setOnTouchListener(this);
		
		String[] filePathColumn = {MediaStore.Images.Media.DATA};

		Cursor cursor = getContentResolver().query(Global.data_img, filePathColumn, null, null, null);
		if(cursor!=null){
			cursor.moveToFirst();
		
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			filePath = cursor.getString(columnIndex);
			cursor.close();
		}else{
			filePath = Global.data_img.getPath();
		}
		
		String[] ma = filePath.split("/");	
		filename_upload = ma[ma.length-1];
		txt_name_img.setText(filename_upload);
		try {
			bitmap_upload = decodeUri(Global.data_img);
			img_upload.setImageBitmap(bitmap_upload);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	if(event.getAction()==KeyEvent.ACTION_UP &&event.getKeyCode()==KeyEvent.KEYCODE_MENU){
    		if(getMenuVisible())
    			hideMenu();
    		else
    		{
    			layout_menu_upload.setVisibility(View.VISIBLE);
				layout_nameimg.setVisibility(View.VISIBLE);
    			menuvisible = true;
    		}
    	}
    	return super.dispatchKeyEvent(event);
    }
    
	public void hideMenu()
	{
		menuvisible = false;
		layout_menu_upload.startAnimation(anmDown);
		layout_nameimg.startAnimation(anmUp);
	}
	public boolean getMenuVisible()
	{
		return menuvisible;
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == btn_ok.getId()){
			try{
				pg = new ProgressDialog(Upload.this);
				pg.setMessage(getString(R.string.waiting));
				pg.show();
			}catch (Exception e) {
			}
			new uploadImage().start();
		}
		
		if(v.getId() == btn_reup.getId()){
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, PHOTO_PICKED);
		}
		
		if(v.getId() == btn_rorate.getId()){
			int w = bitmap_upload.getWidth();
			int h = bitmap_upload.getHeight();
			Matrix mtx = new Matrix();
			mtx.preRotate(90);
			
			// Rotating Bitmap
			bitmap_upload = Bitmap.createBitmap(bitmap_upload, 0, 0, w, h, mtx, true);
			img_upload.setImageBitmap(bitmap_upload);
		}
		if(v.getId() == btn_details.getId()){
			AlertDialog.Builder builder = new AlertDialog.Builder(Upload.this);
    		builder.setCancelable(true)
    		       .setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		                dialog.cancel();
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		int width = bitmap_upload.getWidth();
    		int height = bitmap_upload.getHeight();
    		alert.setMessage("\t\t"+getString(R.string.info)+"\n" +
					"\t"+getString(R.string.imgname)+"\t"+filename_upload+"\n"+
    				"\t"+getString(R.string.measure)+ "\t"+""+width+"x"+""+height+"\n");
		alert.show();
		}
		
		
	}
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == PHOTO_PICKED){
			if(resultCode == RESULT_OK){
				try{
		            Uri selectedImage = data.getData();
		            
		            String[] filePathColumn = {MediaStore.Images.Media.DATA};
		            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
		            if(cursor!=null){
		    			cursor.moveToFirst();
		    		
		    			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		    			filePath = cursor.getString(columnIndex);
		    			cursor.close();
		    		}else{
		    			filePath = selectedImage.getPath();
		    		}
		            Log.d("filePath",filePath);
		            String[] ma = filePath.split("/");
		            filename_upload = ma[ma.length-1];
		            txt_name_img.setText(filename_upload);
		            bitmap_upload = null;
					bitmap_upload = decodeUri(selectedImage);
					img_upload.setImageBitmap(bitmap_upload);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
			
		}
	}
	
	
	
	boolean internet = true;
	protected class uploadImage extends Thread{
		
		@Override
		public void run(){
			try{
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
		        bitmap_upload.compress(Bitmap.CompressFormat.PNG, 1, stream);
				byte[] byteArrayUpload = stream.toByteArray();
				stream.close();
				stream = null;
				String s = Base64.encode(byteArrayUpload);
				response = call.uploadImage(s,filename_upload,Global.username);
			
				if(!response.equals("")){
				}else internet = false;
				pg.dismiss();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			handler.post(new Runnable() {
				@Override
				public void run() {
					try{
						Log.d("response","x+upload"+response);
						if(!response.equals("false")){
							Toast.makeText(Upload.this, getString(R.string.uploaded), 2).show();
							Intent in = new Intent("com.krazevina.beautifulgirl.ret");
							sendBroadcast(in);
							finish();
						}else{
							Toast.makeText(Upload.this, getString(R.string.uploadfail), 2).show();
							return;
						}
						
						if(!internet){
							Toast.makeText(Upload.this,getString(R.string.noconnect), 2).show();
							return;
						}
						
					}catch (Exception e) {
						Log.e("e.getMessage",""+e.getMessage());
						return;
					}
				}
			});
			
			
		}
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		bitmap_upload = null;
		img_upload.setImageBitmap(null);
	}
	
	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 700;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        
        int scale = 1;
        
        
        while (true) {
        if (width_tmp / 2 < REQUIRED_SIZE
            || height_tmp / 2 < REQUIRED_SIZE)
            break;
        width_tmp /= 2;
        height_tmp /= 2;
        scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == btn_rorate.getId() && event.getAction() == MotionEvent.ACTION_DOWN){
			btn_rorate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upload_btn_rotate,0,0,0);
			btn_rorate.setTextColor(Color.RED);
		}
		
		if(v.getId() == btn_rorate.getId() && event.getAction() == MotionEvent.ACTION_UP){
			btn_rorate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upload_btn_rotate_hi,0,0,0);
			btn_rorate.setTextColor(Color.WHITE);
		}
		
		if(v.getId() == btn_ok.getId() && event.getAction() == MotionEvent.ACTION_DOWN){
			btn_ok.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btnrecaptureactiv,0,0,0);
			btn_ok.setTextColor(Color.RED);
		}
		
		if(v.getId() == btn_ok.getId() && event.getAction() == MotionEvent.ACTION_UP){
			btn_ok.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btnrecapture,0,0,0);
			btn_ok.setTextColor(Color.WHITE);
		}
		
		if(v.getId() == btn_reup.getId() && event.getAction() == MotionEvent.ACTION_DOWN){
			btn_reup.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upload_btn_reup,0,0,0);
			btn_reup.setTextColor(Color.RED);
		}
		
		if(v.getId() == btn_reup.getId() && event.getAction() == MotionEvent.ACTION_UP){
			btn_reup.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upload_btn_reup_hi,0,0,0);
			btn_reup.setTextColor(Color.WHITE);
		}
		
		if(v.getId() == btn_details.getId() && event.getAction() == MotionEvent.ACTION_DOWN){
			btn_details.setTextColor(Color.RED);
		}
		
		if(v.getId() == btn_details.getId() && event.getAction() == MotionEvent.ACTION_UP){
			btn_details.setTextColor(Color.WHITE);
		}
		
		if(v.getId() == img_upload.getId()){
			if(getMenuVisible())
    			hideMenu();
    		else
    		{
    			layout_menu_upload.setVisibility(View.VISIBLE);
				layout_nameimg.setVisibility(View.VISIBLE);
    			menuvisible = true;
    		}
		}
		
		return false;
	}
	
}





