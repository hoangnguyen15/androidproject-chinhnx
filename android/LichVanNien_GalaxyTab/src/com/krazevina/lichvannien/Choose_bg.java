package com.krazevina.lichvannien;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.krazevina.objects.Global;
import com.krazevina.objects.Sqlite;

public class Choose_bg extends Activity implements OnItemClickListener {
	private ListView lst_choose_bg;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_bg);
		lst_choose_bg = (ListView)findViewById(R.id.lst_choose_bg);
		String[] choose = {"GalaxyTab","Ảnh của bạn"};
		lst_choose_bg.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, choose));
		
		lst_choose_bg.setOnItemClickListener(this);
	}
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(parent.getId() == lst_choose_bg.getId()){
			if(lst_choose_bg.getPositionForView(view) == 0){
				Global.your_bg = null;
				intent = new Intent();
				intent.setClass(this, Galaxy_bg.class);
				startActivity(intent);
				finish();
			}
			if(lst_choose_bg.getPositionForView(view) == 1){
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, 2);

			}
		}
	}
	
	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 2){
			 if(resultCode == RESULT_OK){  
				Uri selectedImage = data.getData();
		        Bitmap yourSelectedImage = null;
				try {
					yourSelectedImage = decodeUri(selectedImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            String[] filePathColumn = {MediaStore.Images.Media.DATA};

	            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            Log.d("filePath",filePath);
	            cursor.close();


//	            Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
	            Global.your_bg = new BitmapDrawable(yourSelectedImage);
	            
	            Sqlite sql = new Sqlite(this);
				sql.setWallpaper(0,filePath);
				sql.close();
				finish();
		            
		        }

		}
	}
}
