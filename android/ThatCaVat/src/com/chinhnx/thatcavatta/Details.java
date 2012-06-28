package com.chinhnx.thatcavatta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends Activity {
	int position;
	int[] imgSteps;
	TextView txtSteps,txtDes;
	ImageView img;
	int i =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		txtSteps = (TextView)findViewById(R.id.txtSteps);
		txtDes = (TextView)findViewById(R.id.txtDes);
		img = (ImageView)findViewById(R.id.img);
		
		
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		
		int[] details = { R.array.atlantic_descriptions,
				R.array.diagonal_descriptions,
				R.array.four_in_hand_descriptions,
				R.array.half_windsor_descriptions, R.array.kelvin_descriptions,
				R.array.oriental_descriptions, R.array.persian_descriptions,
				R.array.plattsburg_descriptions, R.array.pratt_descriptions,
				R.array.simple_double_descriptions,
				R.array.st_andrew_descriptions, R.array.windsor_descriptions,
				R.array.bowtie_descriptions };

		int[] imgAtlantic = { R.drawable.atlantic, R.drawable.atlantic01,
				R.drawable.atlantic02, R.drawable.atlantic03,
				R.drawable.atlantic04, R.drawable.atlantic05,
				R.drawable.atlantic06, R.drawable.atlantic07,
				R.drawable.atlantic };

		int[] imgDiagonal = { R.drawable.diagonal, R.drawable.diagonal01,
				R.drawable.diagonal02, R.drawable.diagonal03,
				R.drawable.diagonal04, R.drawable.diagonal05,
				R.drawable.diagonal06, R.drawable.diagonal07,
				R.drawable.diagonal08, R.drawable.diagonal09,
				R.drawable.diagonal };

		int[] imgFourInHand = { R.drawable.four_in_hand,
				R.drawable.four_in_hand01, R.drawable.four_in_hand02,
				R.drawable.four_in_hand03, R.drawable.four_in_hand04,
				R.drawable.four_in_hand05, R.drawable.four_in_hand06,
				R.drawable.four_in_hand07, R.drawable.four_in_hand };

		int[] imgHalfWindsor = { R.drawable.half_windsor,
				R.drawable.half_windsor01, R.drawable.half_windsor02,
				R.drawable.half_windsor03, R.drawable.half_windsor04,
				R.drawable.half_windsor05, R.drawable.half_windsor06,
				R.drawable.half_windsor07, R.drawable.half_windsor08,
				R.drawable.half_windsor };

		int[] imgKelvin = { R.drawable.kelvin, R.drawable.kelvin01,
				R.drawable.kelvin02, R.drawable.kelvin03, R.drawable.kelvin04,
				R.drawable.kelvin05, R.drawable.kelvin06, R.drawable.kelvin07,
				R.drawable.kelvin08, R.drawable.kelvin };

		int[] imgOriental = { R.drawable.oriental, R.drawable.oriental01,
				R.drawable.oriental02, R.drawable.oriental03,
				R.drawable.oriental04, R.drawable.oriental05,
				R.drawable.oriental06, R.drawable.oriental };

		int[] imgPersian = { R.drawable.persian, R.drawable.persian01,
				R.drawable.persian02, R.drawable.persian03,
				R.drawable.persian04, R.drawable.persian05,
				R.drawable.persian06, R.drawable.persian07,
				R.drawable.persian08, R.drawable.persian09,
				R.drawable.persian10, R.drawable.persian };

		int[] imgPlattsburg = { R.drawable.plattsburg, R.drawable.plattsburg01,
				R.drawable.plattsburg02, R.drawable.plattsburg03,
				R.drawable.plattsburg04, R.drawable.plattsburg05,
				R.drawable.plattsburg06, R.drawable.plattsburg07,
				R.drawable.plattsburg08, R.drawable.plattsburg09,
				R.drawable.plattsburg10, R.drawable.plattsburg08 };

		int[] imgPratt = { R.drawable.pratt, R.drawable.pratt01,
				R.drawable.pratt02, R.drawable.pratt03, R.drawable.pratt04,
				R.drawable.pratt05, R.drawable.pratt06, R.drawable.pratt07,
				R.drawable.pratt };

		int[] imgSimpleDouble = { R.drawable.simple_double,
				R.drawable.simple_double01, R.drawable.simple_double02,
				R.drawable.simple_double03, R.drawable.simple_double04,
				R.drawable.simple_double05, R.drawable.simple_double06,
				R.drawable.simple_double07, R.drawable.simple_double08,
				R.drawable.simple_double09, R.drawable.simple_double };

		int[] imgStAndrew = { R.drawable.st_andrew, R.drawable.st_andrew01,
				R.drawable.st_andrew02, R.drawable.st_andrew03,
				R.drawable.st_andrew04, R.drawable.st_andrew05,
				R.drawable.st_andrew06, R.drawable.st_andrew07,
				R.drawable.st_andrew08, R.drawable.st_andrew09,
				R.drawable.st_andrew };

		int[] imgWindsor = { R.drawable.windsor, R.drawable.windsor01,
				R.drawable.windsor02, R.drawable.windsor03,
				R.drawable.windsor04, R.drawable.windsor05,
				R.drawable.windsor06, R.drawable.windsor07,
				R.drawable.windsor08, R.drawable.windsor09,
				R.drawable.windsor10, R.drawable.windsor11, R.drawable.windsor };

		int[] imgBowtie = { R.drawable.bowtie, R.drawable.bowtie01,
				R.drawable.bowtie02, R.drawable.bowtie03, R.drawable.bowtie04,
				R.drawable.bowtie05, R.drawable.bowtie06, R.drawable.bowtie07,
				R.drawable.bowtie08, R.drawable.bowtie09, R.drawable.bowtie };

		String[] descriptions = getResources().getStringArray(details[position]);
		String []names = getResources().getStringArray(R.array.names);
		switch (position) {
		case 0:
			imgSteps = imgAtlantic;
			break;
		case 1:
			imgSteps = imgDiagonal;
			break;
		case 2:
			imgSteps = imgFourInHand;
			break;
		case 3:
			imgSteps = imgHalfWindsor;
			break;
		case 4:
			imgSteps = imgKelvin;
			break;
		case 5:
			imgSteps = imgOriental;
			break;
		case 6:
			imgSteps = imgPersian;
			break;
		case 7:
			imgSteps = imgPlattsburg;
			break;
		case 8:
			imgSteps = imgPratt;
			break;
		case 9:
			imgSteps = imgSimpleDouble;
			break;
		case 10:
			imgSteps = imgStAndrew;
			break;
		case 11:
			imgSteps = imgWindsor;
			break;
		case 12:
			imgSteps = imgBowtie;
			break;

		default:
			break;
		}
		
		img.setBackgroundResource(imgSteps[i]);
		txtSteps.setText(names[position]);
		txtDes.setText(descriptions[i]);
		

	}

}
