package krazevina.com.lunarvn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.Leadbolt.AdController;
import com.airpush.android.Airpush;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

public class main extends Activity 
{
	private static final int MENU_TODAY=0;
	private static final int MENU_ABOUT=1;	
	private static final int APPOINTMENT=2;
	
	private Button choice1, choice2;	
	private Button next, prev;
	
	static final int DATE_DIALOG_CHOICE1 = 10;
	static final int DATE_DIALOG_CHOICE2=11;
	static final int DIALOG_SPECIAL=12;
	Intent myintent;
	View layout;	
	AlertDialog alertdialog;
	
	long durationAnimButtonClick = 1300;
	long durationAnimActionMoveLR = 800;
	long durationAnimActionMoveUD = 750;
	GridView currentGridView;
	ArrayList<Float> xArray, yArray;
	boolean flagActionMove = false;
	
	int padding = 70;
	
    /** Called when the activity is first created. */
    @Override 
    public void onCreate(Bundle savedInstanceState) 
    {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);    
        
        new Airpush(getApplicationContext(), global.appId,global.apiKey,false,true,true);
        AdController myController = new AdController(getApplicationContext(), 
        		"457190741");
        myController.setAsynchTask(true);
		myController.loadNotification();
        
        global.context=this;
        global.gridview = (GridView) findViewById(R.id.gridview);
        global.TextViewTilte=(TextView)findViewById(R.id.currentmonth);   
        
        global.viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        global.nextGridView = (GridView) findViewById(R.id.next_gridview);
        
        currentGridView = (GridView) global.viewFlipper.getCurrentView();
        
        //lay ngay hien tai        
        SetToDay();
        setmonth_title();
        //click next
        next=(Button)findViewById(R.id.next);
        prev=(Button)findViewById(R.id.prev);        
		next.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) 
			{
				gridViewAnim(1, durationAnimButtonClick);               
			}
		});
		
		prev.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				gridViewAnim(2, durationAnimButtonClick);
			}
		});	
		
		xArray = new ArrayList<Float>();
		yArray = new ArrayList<Float>();
		OnTouchListener touchListener = new OnTouchListener() {		
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float xPos, yPos;
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					System.out.println("-----------Action move");
					xPos = event.getRawX();
					yPos = event.getRawY();
					
					xArray.add(xPos);
					yArray.add(yPos);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					int move = 0;
					if (xArray.size() > 0) {
//						for (int i = 1; i < xArray.size(); i++) {
//							if (xArray.get(xArray.size() - 1) > xArray.get(0) + padding) {
//								move = 3; //Kéo từ trái sang phải;
//							}
//							else if (xArray.get(xArray.size() - 1) < xArray.get(0) - padding) {
//								move = 4; //Kéo từ phải sang trái;
//							}
//						}
						float width = xArray.get(xArray.size() - 1) - xArray.get(0);
						float height = yArray.get(yArray.size() - 1) - yArray.get(0);
						
						float absWidth = Math.abs(width);
						float absHeight = Math.abs(height);
						if (absWidth > padding || absHeight > padding) {
							if (absWidth >= absHeight) {
								if (width > 0) {
									move = 2;
								}
								else {
									move = 1;
								}
							}
							else {
								if (height > 0) {
									move = 3;
								}
								else {
									move = 4;
								}
							}
						}
					}
					
//					switch (move) {
//					case 3:
//						System.out.println("Move from left to right");
//						buttonClicked(3, durationAnimActionMove);
//						flagActionMove = true;
//						break;
//					case 4:
//						System.out.println("Move from right to left");
//						buttonClicked(4, durationAnimActionMove);
//						flagActionMove = true;
//						break;
//					default:
//						System.out.println("Not move");
//						flagActionMove = false;
//					}
					
					if (move > 0 && move <= 2) {
						gridViewAnim(move, durationAnimActionMoveLR);
						flagActionMove = true;
					}
					else if (move > 2 && move <= 4) {
						gridViewAnim(move, durationAnimActionMoveUD);
						flagActionMove = true;
					}
					else {
						flagActionMove = false;
					}
					
					xArray.clear();
					yArray.clear();
				}			
				return false;
			}
		};
		
		global.gridview.setOnTouchListener(touchListener);
		global.nextGridView.setOnTouchListener(touchListener);		
		
        choice1 = (Button) findViewById(R.id.choice1);
        choice2 = (Button) findViewById(R.id.choice2);
        // choice lunar dates
        choice2.setOnClickListener(new View.OnClickListener() 
        {
        	@Override
			public void onClick(View v) 
			{
				showDialog(DATE_DIALOG_CHOICE2);				
			}
		});
        // choice solar dates
        choice1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_CHOICE1);
				
			}
		});
        
	    global.gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	        {
	        	if (!flagActionMove) {
	        		gridViewItemIsClicked(position);
	        	}
            }
        });      
	    
	    global.nextGridView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	        {
	        	if (!flagActionMove) {
	        		gridViewItemIsClicked(position);
	        	}		   
            }
        }); 	    
    }
    
    private void gridViewAnim(int moveDirection, long durationAnimButtonClick2) {
    	Animation inAnimation, outAnimation;
    	if (moveDirection == 1) { //Phải sang trái
    		Next();
    		inAnimation = AnimationUtils.loadAnimation(main.this, R.anim.slide_left);
    		outAnimation = AnimationUtils.loadAnimation(main.this, R.anim.push_right_out);
    	}
    	else  if (moveDirection == 2){ //Trái sang phải
    		Prev();
    		inAnimation = AnimationUtils.loadAnimation(main.this, R.anim.slide_right);
    		outAnimation = AnimationUtils.loadAnimation(main.this, R.anim.push_left_out);
    	}
    	else  if (moveDirection == 3){ //Trên xuống dưới
    		nextYear();
    		inAnimation = AnimationUtils.loadAnimation(main.this, R.anim.slide_up);
    		outAnimation = AnimationUtils.loadAnimation(main.this, R.anim.push_down_out);
    	}
    	else  { //Dưới lên trên
    		prevYear();
    		inAnimation = AnimationUtils.loadAnimation(main.this, R.anim.slide_down);
    		outAnimation = AnimationUtils.loadAnimation(main.this, R.anim.push_up_out);
    	}
    	
		setmonth_title();//thiet lap lai title	
		currentGridView = (GridView) global.viewFlipper.getCurrentView();
		GridView nextGridView;
		if (currentGridView.equals(global.gridview)) {
			nextGridView = global.nextGridView;
		}
		else {
			nextGridView = global.gridview;
		}
		
		global.txtviewadapter= new textviewadapter(global.context,global.mm,global.yyyy);						
		
		
		if(global.txtviewadapter.prodate.countcells>35)
		{
			nextGridView.setPadding(1, 0, 1, 0);
		}
		else {
			nextGridView.setPadding(1, 20, 1, 0);
		}		
		nextGridView.setAdapter(global.txtviewadapter);			         
        
		
		inAnimation.setDuration(durationAnimButtonClick2);
		global.viewFlipper.setInAnimation(inAnimation); 
		
		
		outAnimation.setDuration(durationAnimButtonClick2);
		global.viewFlipper.setOutAnimation(outAnimation); 
		
		System.out.println("Show animation");
        global.viewFlipper.showNext(); 
    }
    
    private void gridViewItemIsClicked(int position) {
    	//lay thu  begin
    	String thu="";
    	
    	int _thu;
    	if(position>34)
    		_thu=position-35;
    	else if(position>27)
    		_thu=position-28;
    	else if (position>20 && position<=27)
    		_thu=position-21;
    	else if (position>13 && position<=20)
    		_thu=position-14;
    	else if (position>=7 && position<=13)
    		_thu=position-7;
    	else _thu=position;
    	
    	switch (_thu) 
    	{
			case 0:
				thu+="Chủ Nhật";
				break;
			case 1:
				thu+="Thứ Hai";
				break;	
			case 2:
				thu+="Thứ Ba";
				break;
			case 3:
				thu+="Thứ Tư";
				break;
			case 4:
				thu+="Thứ Tư";
				break;
			case 5:
				thu+="Thứ Sáu";
				break;
			case 6:
				thu+="Thứ Bảy";
				break;
			default:
				thu+="Không đúng ngày";
				break;
		}
    	//lay ngay da chon begin	        	
    	
    	int index0=Integer.valueOf(global.txtviewadapter.prodate.DAYS_SOLAR[43]);
    	int index1=Integer.valueOf(global.txtviewadapter.prodate.DAYS_SOLAR[44]);
    	int index_month=Integer.valueOf(global.txtviewadapter.prodate.DAYS_SOLAR[45]);
    	
    	//ngay duong se thuoc thang hien tai
    	int _mMonth=global.mm;
    	int _mYear=global.yyyy;
    	//lay ngay duong lich cua thang hien tai
    	int _mDay=global.dd;        	
    	if(position<index0)	        	
    	{
    		if(index_month==1)
    		{
    			_mYear=global.yyyy-1;
    			_mMonth=12;
    		}
    		else
    			_mMonth=global.mm-1;
    	}	        	
    	if(position>=index1)
    	{
    		if(index_month==12)
    		{
    			_mYear=global.yyyy+1;
    			_mMonth=1;
    		}
    		else
    			_mMonth=global.mm+1;
    	}	
    	
    	_mDay=Integer.valueOf(global.txtviewadapter.prodate.DAYS_SOLAR[position]);
    	//ngayduong= ngayduong+global.txtviewadapter.prodate.DAYS_SOLAR[position];
    	String thangnamduonglich="";
    	String ngayduonglich=global.txtviewadapter.prodate.DAYS_SOLAR[position];
    	thangnamduonglich="Tháng "+String.valueOf(_mMonth);
    	thangnamduonglich =thangnamduonglich +" Năm "+String.valueOf(_mYear);
    	//ngayduong=ngayduong+" (DL)"+"\n";
    		        	
    	String ngayam=global.txtviewadapter.prodate.DAYS_LUNAR_TEMP[position];	        	
    	String[] ngayamsplit= ngayam.split("/");
    	
    	
    	//tach lay ngay am da chon	        	
    	int[] ngayamint= new int[3];	        	
    	ngayamint[0]=Integer.valueOf(ngayamsplit[0]);//day
    	ngayamint[1]=Integer.valueOf(ngayamsplit[1]);//month
    	ngayamint[2]=Integer.valueOf(ngayamsplit[2]);//year lunar
    	String canchinam = processdate.GETLUNARYEARNAME(ngayamint[2]);//truyen vao nam am lich
    	String canchithang= processdate.GETLUNARMONTHCANCHINAME(ngayamint[2],ngayamint[1]);//truyen vao thang nam am lich
    	String canchingay= processdate.GETLUNARDAYCANCHINAME(_mDay,_mMonth,_mYear);// truyen vao ngay duong lich
    	String thongtinngayam="Âm lịch ";
    	if(global.txtviewadapter.prodate.ISLEAPMONTH(_mDay,_mMonth, _mYear))
    		thongtinngayam += "(Tháng Nhuận)";   
    	
    	//lay nhung ngay dac biet trong nam	        	
    	String specialdays= global.txtviewadapter.prodate.GETDAYSPECIALYEARLTEVENT(_mDay, _mMonth);    	    		
    	String ngayamdacbiet=global.txtviewadapter.prodate.GETDAYYEARLYEVENT(ngayamint[0], ngayamint[1]);
    	if(ngayamdacbiet!="")
    		specialdays+="\n";
    	specialdays +=ngayamdacbiet;
    
    	//String str_Inf = ngayduong+ngayam+canchinam+"\n"+canchithang+"\n"+canchingay;
    	
    	MyCustomDialog_DateInf myDialog = new MyCustomDialog_DateInf(main.this, "",new OnReadyListener());
    	//myDialog.information=str_Inf;
    	myDialog.thangnamduonglich=thangnamduonglich;
    	myDialog.ngayduonglich=ngayduonglich;
    	myDialog.thu_inf=thu;
    	myDialog.specialdays_inf=specialdays;
    	myDialog.canchinam=canchinam;
    	myDialog.canchingay=canchingay;
    	myDialog.canchithang=canchithang;
    	myDialog.ngayam=ngayamsplit[0];
    	myDialog.thangam=ngayamsplit[1];
    	myDialog.namam=ngayamsplit[2];
    	myDialog.thongtinngayam=thongtinngayam;
        myDialog.show();	
    }
    
 //Ham cua Datepick    
    private void updateDisplay() 
    {
  	}   
	protected Dialog onCreateDialog(int id) 
	{
		MyCustomDialog_Picker_Solar myDialog_solar;
		MyCustomDialog_Picker_Lunar myDialog_lunar;		
	    switch (id) 
	    {
	    	case DATE_DIALOG_CHOICE1:
	    		myDialog_solar = new MyCustomDialog_Picker_Solar(main.this); 
		        return myDialog_solar;  
		    case DATE_DIALOG_CHOICE2:
		    	
		    	utils lunarcld= new utils();				
	        	int leapmonth=lunarcld.getLeapMonth(global.lunar_yyyy, lunarcld.TIME_ZONE);
	        	int count;
	        	int next=0;
	        	if(leapmonth==-1)
	        		count=12;
	        	else
	        		count=13;
	    		global.count_lunar= new String[count];
	    		for(int i=0;i<=count-1;i++)
	    		{
	    			if(i!=leapmonth-1)
	    			{
	    				global.count_lunar[i]=String.valueOf(next+1);
	    				next++;    				
	    			}
	    			else
	    			{
	    				global.count_lunar[i]=String.valueOf(next+1);
	    				i++;
	    				global.count_lunar[i]=String.valueOf(next+1)+"+";
	    				next++;
	    				
	    			}
	    		}	
		    	myDialog_lunar = new MyCustomDialog_Picker_Lunar(main.this);
		    	return myDialog_lunar;		    	
	    }
	    return null;
	}
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	menu.add(0, MENU_TODAY, 0, "Hôm nay").setIcon(R.drawable.menu_today);        
        menu.add(0, APPOINTMENT, 0, "Thêm sự kiện").setIcon(R.drawable.menu_set);
        menu.add(0, MENU_ABOUT, 0, "Giới thiệu").setIcon(R.drawable.menu_about);
        
        return true;
    }      
   
    public boolean onOptionsItemSelected(MenuItem item) 
    {    	
        switch (item.getItemId()) 
        {
	        case MENU_TODAY:
	        	SetToDay();	   
	        	setmonth_title();	        	
	            return true;
	        case MENU_ABOUT:
	        	MyCustomDialog_About dialog = new MyCustomDialog_About(main.this);
	        	dialog.show();
	        	return true;
	        case APPOINTMENT:
	        	Intent myintent= new Intent();
	        	myintent.setClassName("krazevina.com.lunarvn", "krazevina.com.lunarvn.appointment");
	        	startActivity(myintent);
	        	return true;
	        
        }
        return false;
    }
   public void Next()
    {
    	if(global.mm<12)
    	{
    		global.mm++;
    	}
    	else
    	{
    		global.mm=1;
    		global.yyyy++;
    	}    	
    }
    public void Prev()
    {
    	if(global.mm>1)
    	{
    		global.mm--;
    	}
    	else
    	{
    		global.mm=12;
    		global.yyyy--;
    	}
    }
    
    public void nextYear()
    {   	
    	global.yyyy++;
    }
    
    public void prevYear()
    {
    	global.yyyy--;
    }
    
    public void setmonth_title()
    {
    	 global.title = "Tháng "+String.valueOf(global.mm)+" Năm "+ String.valueOf(global.yyyy);
         global.TextViewTilte.setText(global.title);
    }
   public static int[] GetToDay() 
    {
    	int[] temp= new int[6];
	    String DATE_FORMAT_NOW = "yyyy-MM-dd";
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String strtem=sdf.format(cal.getTime());
	    String[] strtemp=strtem.split("-");
	    for(int i=0;i<6;i++)
	    {
	    	temp[i]=Integer.valueOf(strtemp[i]);
	    }
	    return temp;
    }
   public int GetPosition(int dd,int mm, int yyyy, int index0,int index1, int month,String[]data)
   {
	   //month=or= 12
	   int result=0;// result is position on calendar
	   int count=0;
	   for(int i=0;i<global.txtviewadapter.getCount();i++)
	   {
		   count++;
          int daytemp=Integer.valueOf(data[count-1]);          
           if(count >index0 && count<=index1 && dd== daytemp)
           {
        	   result=count;
           }           
	   }
	   return result;	   
   }
   public void SetToDay()
   {
	   final Calendar c = Calendar.getInstance();
	   global.yyyy = c.get(Calendar.YEAR);        
	   global.mm = c.get(Calendar.MONTH)+1;
	   global.dd = c.get(Calendar.DATE);      
	   global.txtviewadapter= new textviewadapter(global.context,global.mm,global.yyyy);
	   int[] lunar= global.txtviewadapter.prodate.CONVERTSOLAR2LUNAR(global.dd, global.mm, global.yyyy);
	   global.lunar_dd=lunar[0];
	   global.lunar_mm=String.valueOf(lunar[1]);
	   global.lunar_yyyy=lunar[2];
	   //dieu chinh hien thi gridview
	   
	   currentGridView = (GridView) global.viewFlipper.getCurrentView();
	   if(global.txtviewadapter.prodate.countcells>35)
		{
		   currentGridView.setPadding(1, 0, 1, 0);
		}
		else
			currentGridView.setPadding(1, 20, 1, 0);
	   
	   currentGridView.setAdapter(global.txtviewadapter);
   }
   // bo sung cho dialog   
   public class OnReadyListener implements MyCustomDialog_DateInf.ReadyListener {
       @Override
       public void ready(String name) 
       {
           Toast.makeText(main.this, name, Toast.LENGTH_LONG).show();
       }
   }
}
