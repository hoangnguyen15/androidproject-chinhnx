package krazevina.com.lunarvn;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;


public class MyCustomDialog_DateInf extends AlertDialog{

	public String ngayduonglich="";
	public String thangnamduonglich="";
	public String thongtinngayam="";
	public String specialdays_inf="";
	public String thu_inf="";
	public String ngayam="";
	public String thangam="";
	public String namam="";
	public String canchingay="";
	public String canchithang="";
	public String canchinam="";
    public interface ReadyListener {
        public void ready(String name);
    }
    TextView mspecialdays_inf;    
    TextView mthu_inf;
    TextView mngayduonglich;
    TextView mthangnamduonglich;
    TextView mngayam;
    TextView mthangam;
    TextView mnamam;
    TextView mcanchingay;
    TextView mcanchithang;
    TextView mcanchinam;
    TextView mthongtinngayam;
    TableRow mtablerow;
    
    public MyCustomDialog_DateInf(Context context, String name,
            ReadyListener readyListener) {
        super(context);       
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_inf_layout);
       // setTitle("Thông tin của ngày vừa chọn");
        Button buttonOK = (Button) findViewById(R.id.bt_inf_close);
        buttonOK.setOnClickListener(new CLOSEListener());
        mthangnamduonglich=(TextView)findViewById(R.id.thangnamduonglich);
        mngayduonglich=(TextView)findViewById(R.id.ngayduonglich);
        mthu_inf=(TextView)findViewById(R.id.thu_inf);
        mspecialdays_inf=(TextView)findViewById(R.id.date_special_inf);
        mthongtinngayam=(TextView)findViewById(R.id.thongtinngayam);
        mngayam=(TextView)findViewById(R.id.ngayam);
        mthangam=(TextView)findViewById(R.id.thangam);
        mnamam=(TextView)findViewById(R.id.namam);
        mcanchinam=(TextView)findViewById(R.id.canchinam);
        mcanchithang=(TextView)findViewById(R.id.canchithang);
        mcanchingay=(TextView)findViewById(R.id.canchingay);                
        mthangnamduonglich.setText(thangnamduonglich);
        mngayduonglich.setText(ngayduonglich);               
        mspecialdays_inf.setText(specialdays_inf);        
        mthu_inf.setText(thu_inf);
        mthongtinngayam.setText(thongtinngayam);
        mngayam.setText(ngayam);
        mnamam.setText(namam);
        mthangam.setText(thangam);
        mcanchingay.setText(canchingay);
        mcanchithang.setText(canchithang);
        mcanchinam.setText(canchinam);
    }

    private class CLOSEListener implements android.view.View.OnClickListener
    {
        @Override
        public void onClick(View v) 
        {
            //readyListener.ready(String.valueOf(etName.getText()));
            MyCustomDialog_DateInf.this.dismiss();
        }
    }

}
