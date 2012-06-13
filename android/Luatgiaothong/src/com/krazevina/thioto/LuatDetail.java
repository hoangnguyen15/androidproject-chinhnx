package com.krazevina.thioto;




import com.krazevina.thioto.R;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class LuatDetail extends Activity {

	private Button back;
	private Button truoc;
	private Button tiep;
	private TextView chuong;
	private TextView dieu;
	private TextView content[] = new TextView[3];
	private TextView content1[] = new TextView[3];
	private LinearLayout scroll;
    private ScrollView cuon[]=new ScrollView[3];
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.luatdetail);
		back = (Button) findViewById(R.id.back);
		truoc = (Button) findViewById(R.id.truoc);
		tiep = (Button) findViewById(R.id.tiep);
		chuong = (TextView) findViewById(R.id.chuong);
		dieu = (TextView) findViewById(R.id.dieu);
		
		setClickButton();
        addview();
	}

	LayoutInflater li;
	HorizontalPaper cuon_ngang;

	private void addview() {
		scroll = (LinearLayout) findViewById(R.id.scroll);
		li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cuon_ngang = new HorizontalPaper(this);
		cuon_ngang.setOnScreenSwitchListener(switchListener);
		cuon_ngang.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		for (int i = 1; i < 4; i++) {
			View view = (LinearLayout) li.inflate(R.layout.itemluatdetail, null);

			cuon[i-1]=(ScrollView)view.findViewById(R.id.cuon);
			content[i - 1] = (TextView)view.findViewById(R.id.content);
			content1[i - 1] = (TextView)view.findViewById(R.id.content1);
			cuon_ngang.addView(view);
		}
		
		scroll.addView(cuon_ngang);
	}

	int dieuhientai=-1;
	private HorizontalPaper.OnScreenSwitchListener switchListener = new HorizontalPaper.OnScreenSwitchListener() {
		@Override
		public void onScreenSwitched(int screen) {
			Log.d("dieuhientai",""+dieuhientai);
			
			if (screen > 1) {
//				if(dieuhientai==88)
//					{
//					  Toast.makeText(LuatDetail.this, "Đây là điều cuối cùng", 0).show();
//					  return;
//					}
			
				if (dieuhientai + 1 <= 87) {
					
					cuon[0].scrollTo(0,0);
					cuon[1].scrollTo(0,0);
					cuon[2].scrollTo(0,0);
					
					dieuhientai++;
					add_dieuluat(0, dieuhientai - 1);
					add_dieuluat(1, dieuhientai);
					new changelayout_next().execute((Void) null);
				}else{
					if(dieuhientai==87)
					dieuhientai++;
				}

				chuong.setText("Chương "+Luat.get_chuong_from_dieu(dieuhientai+1));
				dieu.setText("Điều "+(dieuhientai+1));
				
			} else if (screen < 1) {
//				if(dieuhientai==0)
//					{
//					Toast.makeText(LuatDetail.this, "Đây là điều đầu tiên", 0).show();
//					return;
//					}
				
				if (dieuhientai - 1 >= 1) {
					cuon[0].scrollTo(0,0);
					cuon[1].scrollTo(0,0);
					cuon[2].scrollTo(0,0);
					
					dieuhientai--;
					add_dieuluat(1, dieuhientai);
					add_dieuluat(2, dieuhientai + 1);

					new changelayout_prev().execute((Void) null);
				}else{
					if(dieuhientai==1)
					dieuhientai--;
				}
				chuong.setText("Chương "+Luat.get_chuong_from_dieu(dieuhientai+1));
				dieu.setText("Điều "+(dieuhientai+1));
				
			} else{
				if(dieuhientai==88){
					dieuhientai--;
				}else if(dieuhientai==0){
					dieuhientai=1;
				
				}
				chuong.setText("Chương "+Luat.get_chuong_from_dieu(dieuhientai+1));
				dieu.setText("Điều "+(dieuhientai+1));
			}
			
			
			
			if(dieuhientai==0){
				if(!isshowedtoast){
					isshowedtoast=true;
				Toast.makeText(LuatDetail.this, " Đây là điều đầu tiên",0).show();
				}
			}
			 if (dieuhientai == 88) {
					if(!isshowedtoast){
					isshowedtoast=true;
					Toast.makeText(LuatDetail.this, " Đây là điều cuối cùng",
							0).show();
					}
			 }
			
			if(dieuhientai!=0&&dieuhientai!=88){
				isshowedtoast=false;
			}
			
			
		}
	};
boolean isshowedtoast=false;
	@Override
	protected void onResume() {
		
		SetView();
		super.onResume();
	}
	
	private void add_dieuluat(int so_thutu_view, int current_dieu) {

		Log.d("currentdieu",""+current_dieu);
		String cau = getCau(current_dieu+1);
		String s = "";
//		cau = cau.replaceAll("ư", "ư");
//		cau = cau.replaceAll("ộ", "ộ");
//		cau = cau.replaceAll("ệ", "ệ");
//		cau = cau.replaceAll("ậ", "ậ");
//		cau = cau.replaceAll("ơ", "ơ");
//		cau = cau.replaceAll("ă", "ă");
//		cau = cau.replaceAll("ặ", "ặ");

		if (cau.length() > 5000) {
			int i = cau.indexOf(".", 4500);
			int com = cau.substring(i - 1, i).compareTo("0");
	

			if (com >= 0 && com <= 9) // truoc dau cham la mot so
			{
				i = cau.substring(0, i).lastIndexOf(".");
				s = cau.substring(i + 2);
				content[so_thutu_view].setText(cau.substring(0, i + 1));
				content1[so_thutu_view].setText(s);
			} else { // neu khong
				s = cau.substring(i + 2);
				content[so_thutu_view].setText(cau.substring(0, i + 1));
				content1[so_thutu_view].setText(s);
			}
		} else
			content[so_thutu_view].setText(cau);
	
	}
	int current_dieu = 0;
	int current_chuong = 0;

	private void SetView() {
		
		Log.d("toancuc.dieuhientai",""+Toancuc.dieuhientai);
		dieuhientai = Toancuc.dieuhientai;
		chuong.setText("Chương "+Luat.get_chuong_from_dieu(dieuhientai+1));
		dieu.setText("Điều "+(dieuhientai+1));
		
		
		if (dieuhientai == 0) {
			add_dieuluat(0, 0);
			add_dieuluat(1, 1);
			add_dieuluat(2, 2);
			cuon_ngang.setCurrentScreen(0, false);

		} else if (dieuhientai == 88) {
			add_dieuluat(0, 86);
			add_dieuluat(1, 87);
			add_dieuluat(2, 88);
			cuon_ngang.setCurrentScreen(2, false);

		} else {
			add_dieuluat(0, dieuhientai - 1);
			add_dieuluat(1, dieuhientai);
			add_dieuluat(2, dieuhientai + 1);
			cuon_ngang.setCurrentScreen(1, false);
		}

	}

	private void setClickButton() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toancuc.viewing_detail = false;
				Tab.tabHost.setCurrentTab(0);
			}
		});

		truoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dieuhientai == 0) {
					Toast.makeText(LuatDetail.this, "Đây là điều đầu tiên", 0)
							.show();
					return;
				}
				
				Log.d("dieuhientai",""+dieuhientai);

				if (dieuhientai == 88) 
					cuon_ngang.setCurrentScreen(1, true);
				 else
					cuon_ngang.setCurrentScreen(0, true);
				
				
			}
		});
		tiep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dieuhientai == 88) {
					Toast.makeText(LuatDetail.this, "Đây là điều cuối cùng", 0)
							.show();
					return;
				}

				if (dieuhientai == 0) {
					cuon_ngang.setCurrentScreen(1, true);
				} else
				cuon_ngang.setCurrentScreen(2, true);
			}
		});
	}

	public static String getCau(int cauthu) {

		if (cauthu < 1 || cauthu >= 90)
			return null;

		switch (cauthu) {
		case 1:
			return cau1;

		case 2:
			return cau2;

		case 3:
			return cau3;
		case 4:
			return cau4;
		case 5:
			return cau5;
		case 6:
			return cau6;
		case 7:
			return cau7;
		case 8:
			return cau8;
		case 9:
			return cau9;
		case 10:
			return cau10;
		case 11:
			return cau11;
		case 12:
			return cau12;
		case 13:
			return cau13;
		case 14:
			return cau14;
		case 15:
			return cau15;
		case 16:
			return cau16;
		case 17:
			return cau17;
		case 18:
			return cau18;
		case 19:
			return cau19;
		case 20:
			return cau20;
		case 21:
			return cau21;
		case 22:
			return cau22;
		case 23:
			return cau23;
		case 24:
			return cau24;
		case 25:
			return cau25;
		case 26:
			return cau26;
		case 27:
			return cau27;
		case 28:
			return cau28;
		case 29:
			return cau29;
		case 30:
			return cau30;
		case 31:
			return cau31;
		case 32:
			return cau32;
		case 33:
			return cau33;
		case 34:
			return cau34;
		case 35:
			return cau35;
		case 36:
			return cau36;
		case 37:
			return cau37;
		case 38:
			return cau38;
		case 39:
			return cau39;
		case 40:
			return cau40;
		case 41:
			return cau41;
		case 42:
			return cau42;
		case 43:
			return cau43;
		case 44:
			return cau44;
		case 45:
			return cau45;
		case 46:
			return cau46;
		case 47:
			return cau47;
		case 48:
			return cau48;
		case 49:
			return cau49;
		case 50:
			return cau50;
		case 51:
			return cau51;
		case 52:
			return cau52;
		case 53:
			return cau53;
		case 54:
			return cau54;
		case 55:
			return cau55;
		case 56:
			return cau56;
		case 57:
			return cau57;
		case 58:
			return cau58;
		case 59:
			return cau59;
		case 60:
			return cau60;
		case 61:
			return cau61;
		case 62:
			return cau62;
		case 63:
			return cau63;
		case 64:
			return cau64;
		case 65:
			return cau65;
		case 66:
			return cau66;
		case 67:
			return cau67;
		case 68:
			return cau68;
		case 69:
			return cau69;
		case 70:

			return cau70;
		case 71:
			return cau71;
		case 72:
			return cau72;
		case 73:
			return cau73;
		case 74:
			return cau74;
		case 75:
			return cau75;
		case 76:
			return cau76;
		case 77:
			return cau77;
		case 78:
			return cau78;
		case 79:
			return cau79;
		case 80:
			return cau80;
		case 81:
			return cau81;
		case 82:
			return cau82;
		case 83:
			return cau83;
		case 84:
			return cau84;
		case 85:
			return cau85;
		case 86:
			return cau86;
		case 87:
			return cau87;
		case 88:
			return cau88;
		case 89:
			return cau89;

		}
		return null;
	}

	@Override
	public void onBackPressed() {
		Toancuc.viewing_detail=false;
		Tab.tabHost.setCurrentTab(0);

		return;
	}

	protected class changelayout_prev extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void v) {
			cuon_ngang.setCurrentScreen(1, false);
			add_dieuluat(0, dieuhientai - 1);

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	protected class changelayout_next extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void v) {
			cuon_ngang.setCurrentScreen(1, false);
			add_dieuluat(2, dieuhientai + 1);
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}


	public static	String cau1 = "	Luật này quy định về quy tắc giao thông đường bộ; kết cấu hạ tầng giao thông đường bộ; phương tiện và người tham gia giao thông đường bộ; vận tải đường bộ và quản lý nhà nước về giao thông đường bộ.";
	public static String cau2 = "	Luật này áp dụng đối với tổ chức, cá nhân liên quan đến giao thông đường bộ trên lãnh thổ nước Cộng hòa xã hội chủ nghĩa Việt Nam.";
	public static String cau3 = "	Trong Luật này, các từ ngữ dưới đây được hiểu như sau:"
			+ "\n	1. Đường bộ gồm đường, cầu đường bộ, hầm đường bộ, bến phà đường bộ."
			+ "\n	2. Công trình đường bộ gồm đường bộ, nơi dừng xe, đỗ xe trên đường bộ, đèn tín hiệu, biển báo hiệu, vạch kẻ đường, cọc tiêu, rào chắn, đảo giao thông, dải phân cách, cột cây số, tường, kè, hệ thống thoát nước, trạm kiểm tra tải trọng xe, trạm thu phí và các công trình, thiết bị phụ trợ đường bộ khác."
			+ "\n	3. Kết cấu hạ tầng giao thông đường bộ gồm công trình đường bộ, bến xe, bãi đỗ xe, trạm dừng nghỉ và các công trình phụ trợ khác trên đường bộ phục vụ giao thông và hành lang an toàn đường bộ."
			+ "\n	4. Đất của đường bộ là phần đất trên đó công trình đường bộ được xây dựng và phần đất dọc hai bên đường bộ để quản lý, bảo trì, bảo vệ công trình đường bộ."
			+ "\n	5. Hành lang an toàn đường bộ là dải đất dọc hai bên đất của đường bộ, tính từ mép ngoài đất của đường bộ ra hai bên để bảo đảm an toàn giao thông đường bộ."
			+ "\n	6. Phần đường xe chạy là phần của đường bộ được sử dụng cho phương tiện giao thông qua lại."
			+ "\n	7. Làn đường là một phần của phần đường xe chạy được chia theo chiều dọc của đường, có bề rộng đủ cho xe chạy an toàn."
			+ "\n	8. Khổ giới hạn của đường bộ là khoảng trống có kích thước giới hạn về chiều cao, chiều rộng của đường, cầu, bến phà, hầm đường bộ để các xe kể cả hàng hóa xếp trên xe đi qua được an toàn."
			+ "\n	9. Đường phố là đường đô thị, gồm lòng đường và hè phố."
			+ "\n	10. Dải phân cách là bộ phận của đường để phân chia mặt đường thành hai chiều xe chạy riêng biệt hoặc để phân chia phần đường của xe cơ giới và xe thô sơ. Dải phân cách gồm loại cố định và loại di động."
			+ "\n	11. Nơi đường giao nhau cùng mức (sau đây gọi là nơi đường giao nhau) là nơi hai hay nhiều đường bộ gặp nhau trên cùng một mặt phẳng, gồm cả mặt bằng hình thành vị trí giao nhau đó."
			+ "\n	12. Đường cao tốc là đường dành cho xe cơ giới, có dải phân cách chia đường cho xe chạy hai chiều riêng biệt; không giao nhau cùng mức với một hoặc các đường khác; được bố trí đầy đủ trang thiết bị phục vụ, bảo đảm giao thông liên tục, an toàn, rút ngắn thời gian hành trình và chỉ cho xe ra, vào ở những điểm nhất định."
			+ "\n	13. Đường chính là đường bảo đảm giao thông chủ yếu trong khu vực."
			+ "\n	14. Đường nhánh là đường nối vào đường chính."
			+ "\n	15. Đường ưu tiên là đường mà trên đó phương tiện tham gia giao thông đường bộ được các phương tiện giao thông đến từ hướng khác nhường đường khi qua nơi đường giao nhau, được cắm biển báo hiệu đường ưu tiên."
			+ "\n	16. Đường gom là đường để gom hệ thống đường giao thông nội bộ của các khu đô thị, công nghiệp, kinh tế, dân cư, thương mại - dịch vụ và các đường khác vào đường chính hoặc vào đường nhánh trước khi đấu nối vào đường chính."
			+ "\n	17. Phương tiện giao thông đường bộ gồm phương tiện giao thông cơ giới đường bộ, phương tiện giao thông thô sơ đường bộ."
			+ "\n	18. Phương tiện giao thông cơ giới đường bộ (sau đây gọi là xe cơ giới) gồm xe ô tô; máy kéo; rơ moóc hoặc sơ mi rơ moóc được kéo bởi xe ô tô, máy kéo; xe mô tô hai bánh; xe mô tô ba bánh; xe gắn máy (kể cả xe máy điện) và các loại xe tương tự."
			+ "\n	19. Phương tiện giao thông thô sơ đường bộ (sau đây gọi là xe thô sơ) gồm xe đạp (kể cả xe đạp máy), xe xích lô, xe lăn dùng cho người khuyết tật, xe súc vật kéo và các loại xe tương tự."
			+ "\n	20. Xe máy chuyên dùng gồm xe máy thi công, xe máy nông nghiệp, lâm nghiệp và các loại xe đặc chủng khác sử dụng vào mục đích quốc phòng, an ninh có tham gia giao thông đường bộ."
			+ "\n	21. Phương tiện tham gia giao thông đường bộ gồm phương tiện giao thông đường bộ và xe máy chuyên dùng."
			+ "\n	22. Người tham gia giao thông gồm người điều khiển, người sử dụng phương tiện tham gia giao thông đường bộ; người điều khiển, dẫn dắt súc vật; người đi bộ trên đường bộ."
			+ "\n	23. Người điều khiển phương tiện gồm người điều khiển xe cơ giới, xe thô sơ, xe máy chuyên dùng tham gia giao thông đường bộ."
			+ "\n	24. Người lái xe là người điều khiển xe cơ giới."
			+ "\n	25. Người điều khiển giao thông là cảnh sát giao thông; người được giao nhiệm vụ hướng dẫn giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt."
			+ "\n	26. Hành khách là người được chở trên phương tiện vận tải hành khách đường bộ, có trả tiền."
			+ "\n	27. Hành lý là vật phẩm mà hành khách mang theo trên cùng phương tiện hoặc gửi theo phương tiện khác."
			+ "\n	28. Hàng hóa là máy móc, thiết bị, nguyên vật liệu, nhiên liệu, hàng tiêu dùng, động vật sống và các động sản khác được vận chuyển bằng phương tiện giao thông đường bộ."
			+ "\n	29. Hàng nguy hiểm là hàng hóa có chứa các chất nguy hiểm khi chở trên đường có khả năng gây nguy hại tới tính mạng, sức khỏe con người, môi trường, an toàn và an ninh quốc gia."
			+ "\n	30. Vận tải đường bộ là hoạt động sử dụng phương tiện giao thông đường bộ để vận chuyển người, hàng hóa trên đường bộ."
			+ "\n	31. Người vận tải là tổ chức, cá nhân sử dụng phương tiện giao thông đường bộ để thực hiện hoạt động vận tải đường bộ."
			+ "\n	32. Cơ quan quản lý đường bộ là cơ quan thực hiện chức năng quản lý nhà nước chuyên ngành thuộc Bộ Giao thông vận tải; cơ quan chuyên môn thuộc Ủy ban nhân dân tỉnh, thành phố trực thuộc trung ương (sau đây gọi chung là cấp tỉnh), Ủy ban nhân dân huyện, quận, thị xã, thành phố thuộc tỉnh (sau đây gọi chung là cấp huyện); Ủy ban nhân dân xã, phường, thị trấn (sau đây gọi chung là cấp xã).";
	public static String cau4 = "	1. Hoạt động giao thông đường bộ phải bảo đảm thông suốt, trật tự, an toàn, hiệu quả; góp phần phát triển kinh tế - xã hội, bảo đảm quốc phòng, an ninh và bảo vệ môi trường."
			+ "\n	2. Phát triển giao thông đường bộ theo quy hoạch, từng bước hiện đại và đồng bộ; gắn kết phương thức vận tải đường bộ với các phương thức vận tải khác."
			+ "\n	3. Quản lý hoạt động giao thông đường bộ được thực hiện thống nhất trên cơ sở phân công, phân cấp trách nhiệm, quyền hạn cụ thể, đồng thời có sự phối hợp chặt chẽ giữa các bộ, ngành và chính quyền địa phương các cấp."
			+ "\n	4. Bảo đảm trật tự, an toàn giao thông đường bộ là trách nhiệm của cơ quan, tổ chức, cá nhân."
			+ "\n	5. Người tham gia giao thông phải có ý thức tự giác, nghiêm chỉnh chấp hành quy tắc giao thông, giữ gìn an toàn cho mình và cho người khác. Chủ phương tiện và người điều khiển phương tiện phải chịu trách nhiệm trước pháp luật về việc bảo đảm an toàn của phương tiện tham gia giao thông đường bộ."
			+ "\n	6. Mọi hành vi vi phạm pháp luật giao thông đường bộ phải được phát hiện, ngăn chặn kịp thời, xử lý nghiêm minh, đúng pháp luật.";
	public static String cau5 = "	1. Nhà nước tập trung các nguồn lực phát triển giao thông đường bộ, ưu tiên đầu tư phát triển kết cấu hạ tầng giao thông đường bộ ở vùng kinh tế trọng điểm, các thành phố, miền núi, vùng sâu, vùng xa, biên giới, hải đảo, vùng dân tộc thiểu số; có chính sách huy động các nguồn lực để quản lý, bảo trì đường bộ."
			+ "\n	2. Nhà nước có chính sách ưu tiên phát triển vận tải hành khách công cộng; hạn chế sử dụng phương tiện giao thông cá nhân ở các thành phố."
			+ "\n	3. Nhà nước khuyến khích, tạo điều kiện cho tổ chức, cá nhân Việt Nam và nước ngoài đầu tư, kinh doanh khai thác kết cấu hạ tầng giao thông đường bộ và hoạt động vận tải đường bộ; nghiên cứu, ứng dụng khoa học, công nghệ tiên tiến và đào tạo nguồn nhân lực trong lĩnh vực giao thông đường bộ.";
	public static String cau6 = "	1. Quy hoạch giao thông vận tải đường bộ là quy hoạch lĩnh vực chuyên ngành, gồm quy hoạch kết cấu hạ tầng, quy hoạch phương tiện giao thông và vận tải đường bộ."
			+ "\n	2. Quy hoạch giao thông vận tải đường bộ được lập trên cơ sở chiến lược phát triển kinh tế - xã hội, bảo đảm quốc phòng, an ninh và hội nhập quốc tế, đồng bộ với quy hoạch ngành, lĩnh vực; gắn kết chặt chẽ với quy hoạch các chuyên ngành giao thông vận tải khác."
			+ "\n	3. Quy hoạch giao thông vận tải đường bộ được lập cho ít nhất 10 năm và định hướng phát triển cho ít nhất 10 năm tiếp theo; được điều chỉnh phù hợp với tình hình phát triển kinh tế - xã hội trong từng giai đoạn. Việc điều chỉnh quy hoạch phải bảo đảm tính kế thừa của các quy hoạch đã được phê duyệt."
			+ "\n	Quy hoạch giao thông vận tải đường bộ sau khi được phê duyệt phải được công bố để cơ quan, tổ chức, cá nhân có liên quan biết, thực hiện và tham gia giám sát."
			+ "\n	4. Quy hoạch giao thông vận tải đường bộ phải xác định rõ mục tiêu, quan điểm, tính chất và quy mô phát triển; nhu cầu sử dụng đất, nhu cầu vốn, nguồn vốn, nguồn nhân lực; xác định danh mục các dự án, dự án ưu tiên; đánh giá tác động của quy hoạch; xác định cơ chế, chính sách và giải pháp thực hiện quy hoạch."
			+ "\n	5. Bộ Giao thông vận tải lập quy hoạch giao thông vận tải đường bộ trong phạm vi cả nước, liên vùng, vùng; quy hoạch quốc lộ, đường cao tốc trình Thủ tướng Chính phủ phê duyệt sau khi có ý kiến của các bộ, cơ quan ngang bộ và Uỷ ban nhân dân cấp tỉnh có liên quan."
			+ "\n	6. Uỷ ban nhân dân cấp tỉnh tổ chức lập, trình Hội đồng nhân dân cùng cấp quyết định quy hoạch giao thông vận tải đường bộ do địa phương quản lý, trước khi trình Hội đồng nhân dân cùng cấp quyết định phải có ý kiến của Bộ Giao thông vận tải."
			+ "\n	Đối với quy hoạch giao thông vận tải đường bộ của thành phố trực thuộc trung ương loại đô thị đặc biệt thì Uỷ ban nhân dân thành phố lập, trình Hội đồng nhân dân cùng cấp thông qua và phải có ý kiến của Bộ Giao thông vận tải, Bộ Xây dựng trước khi trình Thủ tướng Chính phủ phê duyệt."
			+ "\n	7. Quy hoạch các công trình kỹ thuật hạ tầng khác phải phù hợp, đồng bộ với quy hoạch kết cấu hạ tầng giao thông đường bộ."
			+ "\n	8. Nhà nước bảo đảm vốn ngân sách nhà nước và có chính sách huy động các nguồn vốn khác cho công tác lập quy hoạch giao thông vận tải đường bộ.";
	public static String cau7 = "	1. Cơ quan thông tin, tuyên truyền có trách nhiệm tổ chức tuyên truyền, phổ biến pháp luật về giao thông đường bộ thường xuyên, rộng rãi đến toàn dân."
			+ "\n	2. Uỷ ban nhân dân các cấp trong phạm vi nhiệm vụ, quyền hạn của mình có trách nhiệm tổ chức tuyên truyền, phổ biến, giáo dục pháp luật về giao thông đường bộ tại địa phương, có hình thức tuyên truyền, phổ biến phù hợp đến đồng bào các dân tộc thiểu số."
			+ "\n	3. Cơ quan quản lý nhà nước về giáo dục và đào tạo có trách nhiệm đưa pháp luật về giao thông đường bộ vào chương trình giảng dạy trong nhà trường và các cơ sở giáo dục khác phù hợp với từng ngành học, cấp học."
			+ "\n	4. Mặt trận Tổ quốc Việt Nam và các tổ chức thành viên của Mặt trận có trách nhiệm phối hợp với cơ quan hữu quan và chính quyền địa phương tuyên truyền, vận động nhân dân thực hiện pháp luật về giao thông đường bộ."
			+ "\n	5. Cơ quan, tổ chức có trách nhiệm tổ chức tuyên truyền, phổ biến, giáo dục pháp luật về giao thông đường bộ cho cán bộ, chiến sĩ, công chức, viên chức, người lao động khác thuộc thẩm quyền quản lý."
			+ "\n	Thành viên trong gia đình có trách nhiệm tuyên truyền, giáo dục, nhắc nhở thành viên khác chấp hành pháp luật về giao thông đường bộ.";
	public static String cau8 = "	1. Phá hoại đường, cầu, hầm, bến phà đường bộ, đèn tín hiệu, cọc tiêu, biển báo hiệu, gương cầu, dải phân cách, hệ thống thoát nước và các công trình, thiết bị khác thuộc kết cấu hạ tầng giao thông đường bộ."
			+ "\n	2. Đào, khoan, xẻ đường trái phép; đặt, để chướng ngại vật trái phép trên đường; đặt, rải vật nhọn, đổ chất gây trơn trên đường; để trái phép vật liệu, phế thải, thải rác ra đường; mở đường, đấu nối trái phép vào đường chính; lấn, chiếm hoặc sử dụng trái phép đất của đường bộ, hành lang an toàn đường bộ; tự ý tháo mở nắp cống, tháo dỡ, di chuyển trái phép hoặc làm sai lệch công trình đường bộ."
			+ "\n	3. Sử dụng lòng đường, lề đường, hè phố trái phép."
			+ "\n	4. Đưa xe cơ giới, xe máy chuyên dùng không bảo đảm tiêu chuẩn an toàn kỹ thuật và bảo vệ môi trường tham gia giao thông đường bộ."
			+ "\n	5. Thay đổi tổng thành, linh kiện, phụ kiện xe cơ giới để tạm thời đạt tiêu chuẩn kỹ thuật của xe khi đi kiểm định."
			+ "\n	6. Đua xe, cổ vũ đua xe, tổ chức đua xe trái phép, lạng lách, đánh võng."
			+ "\n	7. Điều khiển phương tiện giao thông đường bộ mà trong cơ thể có chất ma túy."
			+ "\n	8. Điều khiển xe ô tô, máy kéo, xe máy chuyên dùng trên đường mà trong máu hoặc hơi thở có nồng độ cồn."
			+ "\n	Điều khiển xe mô tô, xe gắn máy mà trong máu có nồng độ cồn vượt quá 50 miligam/100 mililít máu hoặc 0,25 miligam/1 lít khí thở."
			+ "\n	9. Điều khiển xe cơ giới không có giấy phép lái xe theo quy định."
			+ "\n	Điều khiển xe máy chuyên dùng tham gia giao thông đường bộ không có chứng chỉ bồi dưỡng kiến thức pháp luật về giao thông đường bộ, bằng hoặc chứng chỉ điều khiển xe máy chuyên dùng."
			+ "\n	10. Giao xe cơ giới, xe máy chuyên dùng cho người không đủ điều kiện để điều khiển xe tham gia giao thông đường bộ."
			+ "\n	11. Điều khiển xe cơ giới chạy quá tốc độ quy định, giành đường, vượt ẩu."
			+ "\n	12. Bấm còi, rú ga liên tục; bấm còi trong thời gian từ 22 giờ đến 5 giờ, bấm còi hơi, sử dụng đèn chiếu xa trong đô thị và khu đông dân cư, trừ các xe được quyền ưu tiên đang đi làm nhiệm vụ theo quy định của Luật này."
			+ "\n	13. Lắp đặt, sử dụng còi, đèn không đúng thiết kế của nhà sản xuất đối với từng loại xe cơ giới; sử dụng thiết bị âm thanh gây mất trật tự an toàn giao thông, trật tự công cộng."
			+ "\n	14. Vận chuyển hàng cấm lưu thông, vận chuyển trái phép hoặc không thực hiện đầy đủ các quy định về vận chuyển hàng nguy hiểm, động vật hoang dã."
			+ "\n	15. Đe dọa, xúc phạm, tranh giành, lôi kéo hành khách; bắt ép hành khách sử dụng dịch vụ ngoài ý muốn; chuyển tải, xuống khách hoặc các hành vi khác nhằm trốn tránh phát hiện xe chở quá tải, quá số người quy định."
			+ "\n	16. Kinh doanh vận tải bằng xe ô tô khi không đáp ứng đủ điều kiện kinh doanh theo quy định."
			+ "\n	17. Bỏ trốn sau khi gây tai nạn để trốn tránh trách nhiệm."
			+ "\n	18. Khi có điều kiện mà cố ý không cứu giúp người bị tai nạn giao thông."
			+ "\n	19. Xâm phạm tính mạng, sức khỏe, tài sản của người bị nạn và người gây tai nạn."
			+ "\n	20. Lợi dụng việc xảy ra tai nạn giao thông để hành hung, đe dọa, xúi giục, gây sức ép, làm mất trật tự, cản trở việc xử lý tai nạn giao thông."
			+ "\n	21. Lợi dụng chức vụ, quyền hạn, nghề nghiệp của bản thân hoặc người khác để vi phạm pháp luật về giao thông đường bộ."
			+ "\n	22. Sản xuất, sử dụng trái phép hoặc mua, bán biển số xe cơ giới, xe máy chuyên dùng."
			+ "\n	23. Hành vi vi phạm quy tắc giao thông đường bộ, hành vi khác gây nguy hiểm cho người và phương tiện tham gia giao thông đường bộ.";
	public static String cau9 = "	1. Người tham gia giao thông phải đi bên phải theo chiều đi của mình, đi đúng làn đường, phần đường quy định và phải chấp hành hệ thống báo hiệu đường bộ."
			+ "\n	2. Xe ô tô có trang bị dây an toàn thì người lái xe và người ngồi hàng ghế phía trước trong xe ô tô phải thắt dây an toàn.";
	public static String cau10 = "	1. Hệ thống báo hiệu đường bộ gồm hiệu lệnh của người điều khiển giao thông; tín hiệu đèn giao thông, biển báo hiệu, vạch kẻ đường, cọc tiêu hoặc tường bảo vệ, rào chắn."
			+ "\n	2. Hiệu lệnh của người điều khiển giao thông quy định như sau:"
			+ "\n	a) Tay giơ thẳng đứng để báo hiệu cho người tham gia giao thông ở các hướng dừng lại;"
			+ "\n	b) Hai tay hoặc một tay dang ngang để báo hiệu cho người tham gia giao thông ở phía trước và ở phía sau người điều khiển giao thông phải dừng lại; người tham gia giao thông ở phía bên phải và bên trái của người điều khiển giao thông được đi;"
			+ "\n	c) Tay phải giơ về phía trước để báo hiệu cho người tham gia giao thông ở phía sau và bên phải người điều khiển giao thông phải dừng lại; người tham gia giao thông ở phía trước người điều khiển giao thông được rẽ phải; người tham gia giao thông ở phía bên trái người điểu khiển giao thông được đi tất cả các hướng; người đi bộ qua đường phải đi sau lưng người điều khiển giao thông."
			+ "\n	3. Tín hiệu đèn giao thông có ba mầu, quy định như sau: a) Tín hiệu xanh là được đi; b) Tín hiệu đỏ là cấm đi;"
			+ "\n	c) Tín hiệu vàng là phải dừng lại trước vạch dừng, trừ trường hợp đã đi quá vạch dừng thì được đi tiếp; trong trường hợp tín hiệu vàng nhấp nháy là được đi nhưng phải giảm tốc độ, chú ý quan sát, nhường đường cho người đi bộ qua đường."
			+ "\n	4. Biển báo hiệu đường bộ gồm năm nhóm, quy định như sau:"
			+ "\n	a) Biển báo cấm để biểu thị các điều cấm;"
			+ "\n	b) Biển báo nguy hiểm để cảnh báo các tình huống nguy hiểm có thể xảy ra;"
			+ "\n	c) Biển hiệu lệnh để báo các hiệu lệnh phải thi hành;"
			+ "\n	d) Biển chỉ dẫn để chỉ dẫn hướng đi hoặc các điều cần biết;"
			+ "\n	đ) Biển phụ để thuyết minh bổ sung các loại biển báo cấm, biển báo nguy hiểm, biển hiệu lệnh và biển chỉ dẫn."
			+ "\n	5. Vạch kẻ đường là vạch chỉ sự phân chia làn đường, vị trí hoặc hướng đi, vị trí dừng lại."
			+ "\n	6. Cọc tiêu hoặc tường bảo vệ được đặt ở mép các đoạn đường nguy hiểm để hướng dẫn cho người tham gia giao thông biết phạm vi an toàn của nền đường và hướng đi của đường."
			+ "\n	7. Rào chắn được đặt ở nơi đường bị thắt hẹp, đầu cầu, đầu cống, đầu đoạn đường cấm, đường cụt không cho xe, người qua lại hoặc đặt ở những nơi cần điều khiển, kiểm soát sự đi lại."
			+ "\n	8. Bộ trưởng Bộ Giao thông vận tải quy định cụ thể về báo hiệu đường bộ.";
	public static String cau11 = "	1. Người tham gia giao thông phải chấp hành hiệu lệnh và chỉ dẫn của hệ thống báo hiệu đường bộ."
			+ "\n	2. Khi có người điều khiển giao thông thì người tham gia giao thông phải chấp hành hiệu lệnh của người điều khiển giao thông."
			+ "\n	3. Tại nơi có biển báo hiệu cố định lại có báo hiệu tạm thời thì người tham gia giao thông phải chấp hành hiệu lệnh của báo hiệu tạm thời."
			+ "\n	4. Tại nơi có vạch kẻ đường dành cho người đi bộ, người điều khiển phương tiện phải quan sát, giảm tốc độ và nhường đường cho người đi bộ, xe lăn của người khuyết tật qua đường."
			+ "\n	Những nơi không có vạch kẻ đường cho người đi bộ, người điều khiển phương tiện phải quan sát, nếu thấy người đi bộ, xe lăn của người khuyết tật đang qua đường thì phải giảm tốc độ, nhường đường cho người đi bộ, xe lăn của người khuyết tật qua đường bảo đảm an toàn.";
	public static String cau12 = "	1. Người lái xe, người điều khiển xe máy chuyên dùng phải tuân thủ quy định về tốc độ xe chạy trên đường và phải giữ một khoảng cách an toàn đối với xe chạy liền trước xe của mình; ở nơi có biển báo Cự ly tối thiểu giữa hai xe phải giữ khoảng cách không nhỏ hơn số ghi trên biển báo."
			+ "\n	2. Bộ trưởng Bộ Giao thông vận tải quy định về tốc độ xe và việc đặt biển báo tốc độ; tổ chức thực hiện đặt biển báo tốc độ trên các tuyến quốc lộ."
			+ "\n	3. Chủ tịch Uỷ ban nhân dân cấp tỉnh tổ chức thực hiện việc đặt biển báo tốc độ trên các tuyến đường do địa phương quản lý.";
	public static String cau13 = "	1. Trên đường có nhiều làn đường cho xe đi cùng chiều được phân biệt bằng vạch kẻ phân làn đường, người điều khiển phương tiện phải cho xe đi trong một làn đường và chỉ được chuyển làn đường ở những nơi cho phép; khi chuyển làn đường phải có tín hiệu báo trước và phải bảo đảm an toàn."
			+ "\n	2. Trên đường một chiều có vạch kẻ phân làn đường, xe thô sơ phải đi trên làn đường bên phải trong cùng, xe cơ giới, xe máy chuyên dùng đi trên làn đường bên trái."
			+ "\n	3. Phương tiện tham gia giao thông đường bộ di chuyển với tốc độ thấp hơn phải đi về bên phải.";
	public static String cau14 = "	1. Xe xin vượt phải có báo hiệu bằng đèn hoặc còi; trong đô thị và khu đông dân cư từ 22 giờ đến 5 giờ chỉ được báo hiệu xin vượt bằng đèn."
			+ "\n	2. Xe xin vượt chỉ được vượt khi không có chướng ngại vật phía trước, không có xe chạy ngược chiều trong đoạn đường định vượt, xe chạy trước không có tín hiệu vượt xe khác và đã tránh về bên phải."
			+ "\n	3. Khi có xe xin vượt, nếu đủ điều kiện an toàn, người điều khiển phương tiện phía trước phải giảm tốc độ, đi sát về bên phải của phần đường xe chạy cho đến khi xe sau đã vượt qua, không được gây trở ngại đối với xe xin vượt."
			+ "\n	4. Khi vượt, các xe phải vượt về bên trái, trừ các trường hợp sau đây thì được phép vượt bên phải:"
			+ "\n	a) Khi xe phía trước có tín hiệu rẽ trái hoặc đang rẽ trái; "
			+ "\n	b) Khi xe điện đang chạy giữa đường;"
			+ "\n	c) Khi xe chuyên dùng đang làm việc trên đường mà không thể vượt bên trái được."
			+ "\n	5. Không được vượt xe khi có một trong các trường hợp sau đây: "
			+ "\n	a) Không bảo đảm các điều kiện quy định tại khoản 2 Điều này; "
			+ "\n	b) Trên cầu hẹp có một làn xe; "
			+ "\n	c) Đường vòng, đầu dốc và các vị trí có tầm nhìn hạn chế;"
			+ "\n	d) Nơi đường giao nhau, đường bộ giao nhau cùng mức với đường sắt;"
			+ "\n	đ) Khi điều kiện thời tiết hoặc đường không bảo đảm an toàn cho việc vượt;"
			+ "\n	e) Xe được quyền ưu tiên đang phát tín hiệu ưu tiên đi làm nhiệm vụ.";
	public static String cau15 = "	1. Khi muốn chuyển hướng, người điều khiển phương tiện phải giảm tốc độ và có tín hiệu báo hướng rẽ."
			+ "\n	2. Trong khi chuyển hướng, người lái xe, người điều khiển xe máy chuyên dùng phải nhường quyền đi trước cho người đi bộ, người đi xe đạp đang đi trên phần đường dành riêng cho họ, nhường đường cho các xe đi ngược chiều và chỉ cho xe chuyển hướng khi quan sát thấy không gây trở ngại hoặc nguy hiểm cho người và phương tiện khác."
			+ "\n	3. Trong khu dân cư, người lái xe, người điều khiển xe máy chuyên dùng chỉ được quay đầu xe ở nơi đường giao nhau và nơi có biển báo cho phép quay đầu xe."
			+ "\n	4. Không được quay đầu xe ở phần đường dành cho người đi bộ qua đường, trên cầu, đầu cầu, gầm cầu vượt, ngầm, trong hầm đường bộ, đường cao tốc, tại nơi đường bộ giao nhau cùng mức với đường sắt, đường hẹp, đường dốc, đoạn đường cong tầm nhìn bị che khuất.";
	public static String cau16 = "	1. Khi lùi xe, người điều khiển phải quan sát phía sau, có tín hiệu cần thiết và chỉ khi nào thấy không nguy hiểm mới được lùi."
			+ "\n	2. Không được lùi xe ở khu vực cấm dừng, trên phần đường dành cho người đi bộ qua đường, nơi đường bộ giao nhau, đường bộ giao nhau cùng mức với đường sắt, nơi tầm nhìn bị che khuất, trong hầm đường bộ, đường cao tốc.";
	public static String cau17 = "	1. Trên đường không phân chia thành hai chiều xe chạy riêng biệt, hai xe đi ngược chiều tránh nhau, người điều khiển phải giảm tốc độ và cho xe đi về bên phải theo chiều xe chạy của mình."
			+ "\n	2. Các trường hợp nhường đường khi tránh nhau quy định như sau:"
			+ "\n	a) Nơi đường hẹp chỉ đủ cho một xe chạy và có chỗ tránh xe thì xe nào ở gần chỗ tránh hơn phải vào vị trí tránh, nhường đường cho xe kia đi;"
			+ "\n	b) Xe xuống dốc phải nhường đường cho xe đang lên dốc;"
			+ "\n	c) Xe nào có chướng ngại vật phía trước phải nhường đường cho xe không có chướng ngại vật đi trước."
			+ "\n	3. Xe cơ giới đi ngược chiều gặp nhau không được dùng đèn chiếu xa.";
	public static String cau18 = "	1. Dừng xe là trạng thái đứng yên tạm thời của phương tiện giao thông trong một khoảng thời gian cần thiết đủ để cho người lên, xuống phương tiện, xếp dỡ hàng hóa hoặc thực hiện công việc khác."
			+ "\n	2. Đỗ xe là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian."
			+ "\n	3. Người điều khiển phương tiện khi dừng xe, đỗ xe trên đường bộ phải thực hiện quy định sau đây:"
			+ "\n	a) Có tín hiệu báo cho người điều khiển phương tiện khác biết;"
			+ "\n	b) Cho xe dừng, đỗ ở nơi có lề đường rộng hoặc khu đất ở bên ngoài phần đường xe chạy; trường hợp lề đường hẹp hoặc không có lề đường thì phải cho xe dừng, đỗ sát mép đường phía bên phải theo chiều đi của mình;"
			+ "\n	c) Trường hợp trên đường đã xây dựng nơi dừng xe, đỗ xe hoặc quy định các điểm dừng xe, đỗ xe thì phải dừng, đỗ xe tại các vị trí đó;"
			+ "\n	d) Sau khi đỗ xe, chỉ được rời khỏi xe khi đã thực hiện các biện pháp an toàn; nếu xe đỗ chiếm một phần đường xe chạy phải đặt ngay biển báo hiệu nguy hiểm ở phía trước và phía sau xe để người điều khiển phương tiện khác biết;"
			+ "\n	đ) Không mở cửa xe, để cửa xe mở hoặc bước xuống xe khi chưa bảo đảm điều kiện an toàn;"
			+ "\n	e) Khi dừng xe, không được tắt máy và không được rời khỏi vị trí lái;"
			+ "\n	g) Xe đỗ trên đoạn đường dốc phải được chèn bánh."
			+ "\n	4. Người điều khiển phương tiện không được dừng xe, đỗ xe tại các vị trí sau đây:"
			+ "\n	a) Bên trái đường một chiều;"
			+ "\n	b) Trên các đoạn đường cong và gần đầu dốc tầm nhìn bị che khuất;"
			+ "\n	c) Trên cầu, gầm cầu vượt;"
			+ "\n	d) Song song với một xe khác đang dừng, đỗ;"
			+ "\n	đ) Trên phần đường dành cho người đi bộ qua đường;"
			+ "\n	e) Nơi đường giao nhau và trong phạm vi 5 mét tính từ mép đường giao nhau;"
			+ "\n	g) Nơi dừng của xe buýt;"
			+ "\n	h) Trước cổng và trong phạm vi 5 mét hai bên cổng trụ sở cơ quan, tổ chức;"
			+ "\n	i) Tại nơi phần đường có bề rộng chỉ đủ cho một làn xe;"
			+ "\n	k) Trong phạm vi an toàn của đường sắt;"
			+ "\n	l) Che khuất biển báo hiệu đường bộ.";
	public static String cau19 = "	Người điều khiển phương tiện khi dừng xe, đỗ xe trên đường phố phải tuân theo quy định tại Điều 18 của Luật này và các quy định sau đây:"
			+ "\n	1. Phải cho xe dừng, đỗ sát theo lề đường, hè phố phía bên phải theo chiều đi của mình; bánh xe gần nhất không được cách xa lề đường, hè phố quá 0,25 mét và không gây cản trở, nguy hiểm cho giao thông. Trường hợp đường phố hẹp, phải dừng xe, đỗ xe ở vị trí cách xe ô tô đang đỗ bên kia đường tối thiểu 20 mét."
			+ "\n	2. Không được dừng xe, đỗ xe trên đường xe điện, trên miệng cống thoát nước, miệng hầm của đường điện thoại, điện cao thế, chỗ dành riêng cho xe chữa cháy lấy nước. Không được để phương tiện giao thông ở lòng đường, hè phố trái quy định.";
	public static String cau20 = "	1. Hàng hóa xếp trên xe phải gọn gàng, chằng buộc chắc chắn, không để rơi vãi dọc đường, không kéo lê hàng hóa trên mặt đường và không cản trở việc điều khiển xe."
			+ "\n	2. Khi xếp hàng hóa vượt phía trước và phía sau xe thì ban ngày phải có cờ báo hiệu màu đỏ, ban đêm hoặc khi trời tối phải có đèn đỏ báo hiệu."
			+ "\n	3. Bộ trưởng Bộ Giao thông vận tải quy định cụ thể việc xếp hàng hóa trên phương tiện giao thông đường bộ.";
	public static String cau21 = "	1. Chỉ được chở người trên xe ô tô chở hàng trong các trường hợp sau đây:"
			+ "\n	a) Chở người đi làm nhiệm vụ phòng, chống thiên tai hoặc thực hiện nhiệm vụ khẩn cấp; chở cán bộ, chiến sĩ của lực lượng vũ trang nhân dân đi làm nhiệm vụ; chở người bị nạn đi cấp cứu;"
			+ "\n	b) Chở công nhân duy tu, bảo dưỡng đường bộ; chở người đi thực hành lái xe trên xe tập lái; chở người diễu hành theo đoàn;"
			+ "\n	c) Giải tỏa người ra khỏi khu vực nguy hiểm hoặc trong trường hợp khẩn cấp khác theo quy định của pháp luật."
			+ "\n	2. Xe ô tô chở người trong các trường hợp quy định tại khoản 1 Điều này phải có thùng cố định, bảo đảm an toàn khi tham gia giao thông.";
	public static String cau22 = "	1. Những xe sau đây được quyền ưu tiên đi trước xe khác khi qua đường giao nhau từ bất kỳ hướng nào tới theo thứ tự:"
			+ "\n	a) Xe chữa cháy đi làm nhiệm vụ;"
			+ "\n	b) Xe quân sự, xe công an đi làm nhiệm vụ khẩn cấp, đoàn xe có xe cảnh sát dẫn đường;"
			+ "\n	c) Xe cứu thương đang thực hiện nhiệm vụ cấp cứu;"
			+ "\n	d) Xe hộ đê, xe đi làm nhiệm vụ khắc phục sự cố thiên tai, dịch bệnh hoặc xe đi làm nhiệm vụ trong tình trạng khẩn cấp theo quy định của pháp luật;"
			+ "\n	đ) Đoàn xe tang."
			+ "\n	2. Xe quy định tại các điểm a, b, c và d khoản 1 Điều này khi đi làm nhiệm vụ phải có tín hiệu còi, cờ, đèn theo quy định; không bị hạn chế tốc độ; được phép đi vào đường ngược chiều, các đường khác có thể đi được, kể cả khi có tín hiệu đèn đỏ và chỉ phải tuân theo chỉ dẫn của người điều khiển giao thông."
			+ "\n	Chính phủ quy định cụ thể tín hiệu của xe được quyền ưu tiên."
			+ "\n	3. Khi có tín hiệu của xe được quyền ưu tiên, người tham gia giao thông phải nhanh chóng giảm tốc độ, tránh hoặc dừng lại sát lề đường bên phải để nhường đường. Không được gây cản trở xe được quyền ưu tiên.";
	public static String cau23 = "	1. Khi đến bến phà, cầu phao, các xe phải xếp hàng trật tự, đúng nơi quy định, không làm cản trở giao thông."
			+ "\n	2. Khi xuống phà, đang ở trên phà và khi lên bến, mọi người phải xuống xe, trừ người điều khiển xe cơ giới, xe máy chuyên dùng, người bệnh, người già yếu và người khuyết tật."
			+ "\n	3. Xe cơ giới, xe máy chuyên dùng phải xuống phà trước, xe thô sơ, người đi bộ xuống phà sau; khi lên bến, người đi bộ lên trước, các phương tiện giao thông lên sau theo hướng dẫn của người điều khiển giao thông."
			+ "\n	4. Thứ tự ưu tiên qua phà, qua cầu phao quy định như sau:"
			+ "\n	a) Các xe được quyền ưu tiên quy định tại khoản 1 Điều 22 của Luật này;"
			+ "\n	b) Xe chở thư báo; "
			+ "\n	c) Xe chở thực phẩm tươi sống;"
			+ "\n	d) Xe chở khách công cộng."
			+ "\n	Trong trường hợp các xe cùng loại ưu tiên đến bến phà, cầu phao thì xe nào đến trước được qua trước.";
	public static String cau24 = "	Khi đến gần đường giao nhau, người điều khiển phương tiện phải cho xe giảm tốc độ và nhường đường theo quy định sau đây:"
			+ "\n	1. Tại nơi đường giao nhau không có báo hiệu đi theo vòng xuyến, phải nhường đường cho xe đi đến từ bên phải;"
			+ "\n	2. Tại nơi đường giao nhau có báo hiệu đi theo vòng xuyến, phải nhường đường cho xe đi bên trái;"
			+ "\n	3. Tại nơi đường giao nhau giữa đường không ưu tiên và đường ưu tiên hoặc giữa đường nhánh và đường chính thì xe đi từ đường không ưu tiên hoặc đường nhánh phải nhường đường cho xe đi trên đường ưu tiên hoặc đường chính từ bất kỳ hướng nào tới.";
	public static String cau25 = "	1. Trên đoạn đường bộ giao nhau cùng mức với đường sắt, cầu đường bộ đi chung với đường sắt, phương tiện giao thông đường sắt được quyền ưu tiên đi trước."
			+ "\n	2. Tại nơi đường bộ giao nhau cùng mức với đường sắt có đèn tín hiệu, rào chắn và chuông báo hiệu, khi đèn tín hiệu mầu đỏ đã bật sáng, có tiếng chuông báo hiệu, rào chắn đang dịch chuyển hoặc đã đóng, người tham gia giao thông đường bộ phải dừng lại phía phần đường của mình và cách rào chắn một khoảng cách an toàn; khi đèn tín hiệu đã tắt, rào chắn mở hết, tiếng chuông báo hiệu ngừng mới được đi qua."
			+ "\n	3. Tại nơi đường bộ giao nhau cùng mức với đường sắt chỉ có đèn tín hiệu hoặc chuông báo hiệu, khi đèn tín hiệu mầu đỏ đã bật sáng hoặc có tiếng chuông báo hiệu, người tham gia giao thông đường bộ phải dừng ngay lại và giữ khoảng cách tối thiểu 5 mét tính từ ray gần nhất; khi đèn tín hiệu đã tắt hoặc tiếng chuông báo hiệu ngừng mới được đi qua."
			+ "\n	4. Tại nơi đường bộ giao nhau cùng mức với đường sắt không có đèn tín hiệu, rào chắn và chuông báo hiệu, người tham gia giao thông đường bộ phải quan sát cả hai phía, khi thấy chắc chắn không có phương tiện đường sắt đang đi tới mới được đi qua, nếu thấy có phương tiện đường sắt đang đi tới thì phải dừng lại và giữ khoảng cách tối thiểu 5 mét tính từ ray gần nhất và chỉ khi phương tiện đường sắt đã đi qua mới được đi."
			+ "\n	5. Khi phương tiện tham gia giao thông đường bộ bị hư hỏng tại nơi đường bộ giao nhau cùng mức với đường sắt hoặc trong phạm vi an toàn đường sắt thì người điều khiển phương tiện phải bằng mọi cách nhanh nhất đặt báo hiệu trên đường sắt cách tối thiểu 500 mét về hai phía để báo cho người điều khiển phương tiện đường sắt và tìm cách báo cho người quản lý đường sắt, nhà ga nơi gần nhất, đồng thời phải bằng mọi biện pháp nhanh chóng đưa phương tiện ra khỏi phạm vi an toàn đường sắt."
			+ "\n	6. Những người có mặt tại nơi phương tiện tham gia giao thông đường bộ bị hư hỏng trên đoạn đường bộ giao nhau cùng mức với đường sắt có trách nhiệm giúp đỡ người điều khiển phương tiện đưa phương tiện ra khỏi phạm vi an toàn đường sắt.";
	public static String cau26 = "	1. Người lái xe, người điều khiển xe máy chuyên dùng trên đường cao tốc ngoài việc tuân thủ các quy tắc giao thông quy định tại Luật này còn phải thực hiện các quy định sau đây:"
			+ "\n	a) Khi vào đường cao tốc phải có tín hiệu xin vào và phải nhường đường cho xe đang chạy trên đường, khi thấy an toàn mới cho xe nhập vào dòng xe ở làn đường sát mép ngoài, nếu có làn đường tăng tốc thì phải cho xe chạy trên làn đường đó trước khi vào làn đường của đường cao tốc;"
			+ "\n	b) Khi ra khỏi đường cao tốc phải thực hiện chuyển dần sang làn đường phía bên phải, nếu có làn đường giảm tốc thì phải cho xe chạy trên làn đường đó trước khi rời khỏi đường cao tốc;"
			+ "\n	c) Không được cho xe chạy ở làn dừng xe khẩn cấp và phần lề đường;"
			+ "\n	d) Không được cho xe chạy quá tốc độ tối đa và dưới tốc độ tối thiểu ghi trên biển báo hiệu, sơn kẻ trên mặt đường."
			+ "\n	2. Người lái xe, người điều khiển xe máy chuyên dùng phải cho xe chạy cách nhau một khoảng cách an toàn ghi trên biển báo hiệu."
			+ "\n	3. Chỉ được dừng xe, đỗ xe ở nơi quy định; trường hợp buộc phải dừng xe, đỗ xe không đúng nơi quy định thì người lái xe phải đưa xe ra khỏi phần đường xe chạy, nếu không thể được thì phải báo hiệu để người lái xe khác biết."
			+ "\n	4. Người đi bộ, xe thô sơ, xe gắn máy, xe mô tô và máy kéo; xe máy chuyên dùng có tốc độ thiết kế nhỏ hơn 70 km/h không được đi vào đường cao tốc, trừ người, phương tiện, thiết bị phục vụ việc quản lý, bảo trì đường cao tốc.";
	public static String cau27 = "	Người điều khiển phương tiện trong hầm đường bộ ngoài việc tuân thủ các quy tắc giao thông quy định tại Luật này còn phải thực hiện các quy định sau đây:"
			+ "\n	1. Xe cơ giới, xe máy chuyên dùng phải bật đèn; xe thô sơ phải bật đèn hoặc có vật phát sáng báo hiệu;"
			+ "\n	2. Chỉ được dừng xe, đỗ xe ở nơi quy định.";
	public static String cau28 = "	1. Người điều khiển phương tiện phải tuân thủ các quy định về tải trọng, khổ giới hạn của đường bộ và chịu sự kiểm tra của cơ quan có thẩm quyền."
			+ "\n	2. Trường hợp đặc biệt, xe quá tải trọng, quá khổ giới hạn của đường bộ, xe bánh xích gây hư hại mặt đường có thể được lưu hành trên đường nhưng phải được cơ quan quản lý đường bộ có thẩm quyền cấp giấy phép và phải thực hiện các biện pháp bắt buộc để bảo vệ đường bộ, bảo đảm an toàn giao thông."
			+ "\n	3. Bộ trưởng Bộ Giao thông vận tải quy định về tải trọng, khổ giới hạn của đường bộ và công bố về tải trọng, khổ giới hạn của quốc lộ; quy định việc cấp giấy phép lưu hành cho xe quá tải trọng, quá khổ giới hạn của đường bộ, xe bánh xích gây hư hại mặt đường."
			+ "\n	4. Chủ tịch Uỷ ban nhân dân cấp tỉnh công bố về tải trọng, khổ giới hạn của đường bộ do địa phương quản lý.";
	public static String cau29 = "	1. Một xe ô tô chỉ được kéo theo một xe ô tô hoặc xe máy chuyên dùng khác khi xe này không tự chạy được và phải bảo đảm các quy định sau đây:"
			+ "\n	a) Xe được kéo phải có người điều khiển và hệ thống lái của xe đó phải còn hiệu lực;"
			+ "\n	b) Việc nối xe kéo với xe được kéo phải bảo đảm chắc chắn, an toàn; trường hợp hệ thống hãm của xe được kéo không còn hiệu lực thì xe kéo nhau phải nối bằng thanh nối cứng;"
			+ "\n	c) Phía trước của xe kéo và phía sau của xe được kéo phải có biển báo hiệu."
			+ "\n	2. Xe kéo rơ moóc phải có tổng trọng lượng lớn hơn tổng trọng lượng của rơ moóc hoặc phải có hệ thống hãm có hiệu lực cho rơ moóc."
			+ "\n	3. Không được thực hiện các hành vi sau đây:"
			+ "\n	a) Xe kéo rơ moóc, xe kéo sơ mi rơ moóc kéo thêm rơ moóc hoặc xe khác;"
			+ "\n	b) Chở người trên xe được kéo;"
			+ "\n	c) Kéo theo xe thô sơ, xe gắn máy, xe mô tô.";
	public static String cau30 = "	1. Người điều khiển xe mô tô hai bánh, xe gắn máy chỉ được chở một người, trừ những trường hợp sau thì được chở tối đa hai người:"
			+ "\n	a) Chở người bệnh đi cấp cứu;"
			+ "\n	b) Áp giải người có hành vi vi phạm pháp luật;"
			+ "\n	c) Trẻ em dưới 14 tuổi."
			+ "\n	2. Người điều khiển, người ngồi trên xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy phải đội mũ bảo hiểm có cài quai đúng quy cách."
			+ "\n	3. Người điều khiển xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy không được thực hiện các hành vi sau đây:"
			+ "\n	a) Đi xe dàn hàng ngang;"
			+ "\n	b) Đi xe vào phần đường dành cho người đi bộ và phương tiện khác;"
			+ "\n	c) Sử dụng ô, điện thoại di động, thiết bị âm thanh, trừ thiết bị trợ thính;"
			+ "\n	d) Sử dụng xe để kéo, đẩy xe khác, vật khác, mang, vác và chở vật cồng kềnh;"
			+ "\n	đ) Buông cả hai tay hoặc đi xe bằng một bánh đối với xe hai bánh, bằng hai bánh đối với xe ba bánh;"
			+ "\n	e) Hành vi khác gây mất trật tự, an toàn giao thông."
			+ "\n	4. Người ngồi trên xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy khi tham gia giao thông không được thực hiện các hành vi sau đây:"
			+ "\n	a) Mang, vác vật cồng kềnh;"
			+ "\n	b) Sử dụng ô;"
			+ "\n	c) Bám, kéo hoặc đẩy các phương tiện khác;"
			+ "\n	d) Đứng trên yên, giá đèo hàng hoặc ngồi trên tay lái;"
			+ "\n	đ) Hành vi khác gây mất trật tự, an toàn giao thông.";

	public static String cau31 = "	1. Người điều khiển xe đạp chỉ được chở một người, trừ trường hợp chở thêm một trẻ em dưới 7 tuổi thì được chở tối đa hai người."
			+ "\n	Người điều khiển xe đạp phải thực hiện quy định tại khoản 3 Điều 30 của Luật này; người ngồi trên xe đạp khi tham gia giao thông phải thực hiện quy định tại khoản 4 Điều 30 của Luật này."
			+ "\n	2. Người điều khiển, người ngồi trên xe đạp máy phải đội mũ bảo hiểm có cài quai đúng quy cách."
			+ "\n	3. Người điều khiển xe thô sơ khác phải cho xe đi hàng một, nơi có phần đường dành cho xe thô sơ thì phải đi đúng phần đường quy định; khi đi ban đêm phải có báo hiệu ở phía trước và phía sau xe. Người điều khiển xe súc vật kéo phải có biện pháp bảo đảm vệ sinh trên đường."
			+ "\n	4. Hàng hóa xếp trên xe thô sơ phải bảo đảm an toàn, không gây cản trở giao thông và che khuất tầm nhìn của người điều khiển.";
	public static String cau32 = "	1. Người đi bộ phải đi trên hè phố, lề đường; trường hợp đường không có hè phố, lề đường thì người đi bộ phải đi sát mép đường."
			+ "\n	2. Người đi bộ chỉ được qua đường ở những nơi có đèn tín hiệu, có vạch kẻ đường hoặc có cầu vượt, hầm dành cho người đi bộ và phải tuân thủ tín hiệu chỉ dẫn."
			+ "\n	3. Trường hợp không có đèn tín hiệu, không có vạch kẻ đường, cầu vượt, hầm dành cho người đi bộ thì người đi bộ phải quan sát các xe đang đi tới, chỉ qua đường khi bảo đảm an toàn và chịu trách nhiệm bảo đảm an toàn khi qua đường."
			+ "\n	4. Người đi bộ không được vượt qua dải phân cách, không đu bám vào phương tiện giao thông đang chạy; khi mang vác vật cồng kềnh phải bảo đảm an toàn và không gây trở ngại cho người và phương tiện tham gia giao thông đường bộ."
			+ "\n	5. Trẻ em dưới 7 tuổi khi đi qua đường đô thị, đường thường xuyên có xe cơ giới qua lại phải có người lớn dắt; mọi người có trách nhiệm giúp đỡ trẻ em dưới 7 tuổi khi đi qua đường.";
	public static String cau33 = "	1. Người khuyết tật sử dụng xe lăn không có động cơ được đi trên hè phố và nơi có vạch kẻ đường dành cho người đi bộ."
			+ "\n	2. Người khiếm thị khi đi trên đường bộ phải có người dắt hoặc có công cụ để báo hiệu cho người khác nhận biết đó là người khiếm thị."
			+ "\n	3. Mọi người có trách nhiệm giúp đỡ người khuyết tật, người già yếu khi đi qua đường.";
	public static String cau34 = "	1. Người dẫn dắt súc vật đi trên đường bộ phải cho súc vật đi sát mép đường và bảo đảm vệ sinh trên đường; trường hợp cần cho súc vật đi ngang qua đường thì phải quan sát và chỉ được đi qua đường khi có đủ điều kiện an toàn."
			+ "\n	2. Không được dẫn dắt súc vật đi vào phần đường dành cho xe cơ giới.";
	public static String cau35 = "	1. Tổ chức hoạt động văn hóa, thể thao, diễu hành, lễ hội trên đường bộ thực hiện theo quy định sau đây:"
			+ "\n	a) Cơ quan, tổ chức có nhu cầu sử dụng đường bộ để tiến hành hoạt động văn hóa, thể thao, diễu hành, lễ hội phải được cơ quan quản lý đường bộ có thẩm quyền thống nhất bằng văn bản về phương án bảo đảm giao thông trước khi xin phép tổ chức các hoạt động trên theo quy định của pháp luật;"
			+ "\n	b) Trường hợp cần hạn chế giao thông hoặc cấm đường thì cơ quan quản lý đường bộ phải ra thông báo phương án phân luồng giao thông; cơ quan, tổ chức có nhu cầu sử dụng đường bộ quy định tại điểm a khoản 1 Điều này phải thực hiện việc đăng tải thông báo trên các phương tiện thông tin đại chúng và thực hiện các biện pháp bảo đảm trật tự, an toàn cho người và phương tiện tham gia giao thông đường bộ;"
			+ "\n	c) Ủy ban nhân dân nơi tổ chức hoạt động văn hóa, thể thao, diễu hành, lễ hội có trách nhiệm chỉ đạo cơ quan chức năng của địa phương tổ chức việc phân luồng, bảo đảm giao thông tại khu vực diễn ra hoạt động văn hóa, thể thao, diễu hành, lễ hội."
			+ "\n	2. Không được thực hiện các hành vi sau đây:"
			+ "\n	a) Họp chợ, mua, bán hàng hóa trên đường bộ;"
			+ "\n	b) Tụ tập đông người trái phép trên đường bộ;"
			+ "\n	c) Thả rông súc vật trên đường bộ;"
			+ "\n	d) Phơi thóc, lúa, rơm rạ, nông sản hoặc để vật khác trên đường bộ;"
			+ "\n	đ) Đặt biển quảng cáo trên đất của đường bộ;"
			+ "\n	e) Lắp đặt biển hiệu, biển quảng cáo hoặc thiết bị khác làm giảm sự chú ý, gây nhầm lẫn nội dung biển báo hiệu hoặc gây cản trở người tham gia giao thông;"
			+ "\n	g) Che khuất biển báo hiệu, đèn tín hiệu giao thông;"
			+ "\n	h) Sử dụng bàn trượt, pa-tanh, các thiết bị tương tự trên phần đường xe chạy;"
			+ "\n	i) Hành vi khác gây cản trở giao thông.";
	public static String cau36 = "	1. Lòng đường và hè phố chỉ được sử dụng cho mục đích giao thông."
			+ "\n	2. Các hoạt động khác trên đường phố phải thực hiện theo quy định tại khoản 1 Điều 35 của Luật này, trường hợp đặc biệt, việc sử dụng tạm thời một phần lòng đường, hè phố vào mục đích khác do Ủy ban nhân dân cấp tỉnh quy định nhưng không được làm ảnh hưởng đến trật tự, an toàn giao thông."
			+ "\n	3. Không được thực hiện các hành vi sau đây:"
			+ "\n	a) Các hành vi quy định tại khoản 2 Điều 35 của Luật này;"
			+ "\n	b) Đổ rác hoặc phế thải không đúng nơi quy định;"
			+ "\n	c) Xây, đặt bục, bệ trái phép trên đường.";
	public static String cau37 = "	1. Tổ chức giao thông gồm các nội dung sau đây:"
			+ "\n	a) Phân làn, phân luồng, phân tuyến và quy định thời gian đi lại cho người và phương tiện tham gia giao thông đường bộ;"
			+ "\n	b) Quy định các đoạn đường cấm đi, đường đi một chiều, nơi cấm dừng, cấm đỗ, cấm quay đầu xe; lắp đặt báo hiệu đường bộ;"
			+ "\n	c) Thông báo khi có sự thay đổi về việc phân luồng, phân tuyến, thời gian đi lại tạm thời hoặc lâu dài; thực hiện các biện pháp ứng cứu khi có sự cố xảy ra và các biện pháp khác về đi lại trên đường bộ để bảo đảm giao thông thông suốt, an toàn."
			+ "\n	2. Trách nhiệm tổ chức giao thông quy định như sau:"
			+ "\n	a) Bộ trưởng Bộ Giao thông vận tải chịu trách nhiệm tổ chức giao thông trên hệ thống quốc lộ;"
			+ "\n	b) Chủ tịch Ủy ban nhân dân cấp tỉnh chịu trách nhiệm tổ chức giao thông trên các hệ thống đường bộ thuộc phạm vi quản lý."
			+ "\n	3. Trách nhiệm điều khiển giao thông của cảnh sát giao thông như sau:"
			+ "\n	a) Chỉ huy, điều khiển giao thông trên đường; hướng dẫn, bắt buộc người tham gia giao thông chấp hành quy tắc giao thông;"
			+ "\n	b) Khi có tình huống gây ách tắc giao thông hoặc có yêu cầu cần thiết khác về bảo đảm an ninh, trật tự được tạm thời đình chỉ đi lại ở một số đoạn đường nhất định, phân lại luồng, phân lại tuyến và nơi tạm dừng xe, đỗ xe.";
	public static String cau38 = "	1. Người điều khiển phương tiện và những người liên quan trực tiếp đến vụ tai nạn có trách nhiệm sau đây:"
			+ "\n	a) Dừng ngay phương tiện; giữ nguyên hiện trường; cấp cứu người bị nạn và phải có mặt khi cơ quan có thẩm quyền yêu cầu;"
			+ "\n	b) Ở lại nơi xảy ra tai nạn cho đến khi người của cơ quan công an đến, trừ trường hợp người điều khiển phương tiện cũng bị thương phải đưa đi cấp cứu hoặc phải đưa người bị nạn đi cấp cứu hoặc vì lý do bị đe dọa đến tính mạng, nhưng phải đến trình báo ngay với cơ quan công an nơi gần nhất;"
			+ "\n	c) Cung cấp thông tin xác thực về vụ tai nạn cho cơ quan có thẩm quyền."
			+ "\n	2. Những người có mặt tại nơi xảy ra vụ tai nạn có trách nhiệm sau đây: "
			+ "\n	a) Bảo vệ hiện trường;"
			+ "\n	b) Giúp đỡ, cứu chữa kịp thời người bị nạn;"
			+ "\n	c) Báo tin ngay cho cơ quan công an, y tế hoặc Ủy ban nhân dân nơi gần nhất;"
			+ "\n	d) Bảo vệ tài sản của người bị nạn;"
			+ "\n	đ) Cung cấp thông tin xác thực về vụ tai nạn theo yêu cầu của cơ quan có thẩm quyền."
			+ "\n	3. Người điều khiển phương tiện khác khi đi qua nơi xảy ra vụ tai nạn có trách nhiệm chở người bị nạn đi cấp cứu. Các xe được quyền ưu tiên, xe chở người được hưởng quyền ưu đãi, miễn trừ ngoại giao, lãnh sự không bắt buộc thực hiện quy định tại khoản này."
			+ "\n	4. Cơ quan công an khi nhận được tin về vụ tai nạn có trách nhiệm cử người tới ngay hiện trường để điều tra vụ tai nạn, phối hợp với cơ quan quản lý đường bộ và Ủy ban nhân dân địa phương bảo đảm giao thông thông suốt, an toàn."
			+ "\n	5. Ủy ban nhân dân cấp xã nơi xảy ra tai nạn có trách nhiệm kịp thời thông báo cho cơ quan công an, y tế đến để xử lý, giải quyết vụ tai nạn; tổ chức cứu chữa, giúp đỡ người bị nạn, bảo vệ hiện trường, bảo vệ tài sản của người bị nạn; trường hợp có người chết mà không rõ tung tích, không có thân nhân hoặc thân nhân không có khả năng chôn cất thì sau khi cơ quan nhà nước có thẩm quyền đã hoàn tất các công việc theo quy định của pháp luật và đồng ý cho chôn cất, Ủy ban nhân dân cấp xã có trách nhiệm tổ chức chôn cất."
			+ "\n	Trường hợp vụ tai nạn vượt quá khả năng giải quyết, Uỷ ban nhân dân cấp xã phải kịp thời báo cáo Uỷ ban nhân dân cấp trên."
			+ "\n	6. Bộ Công an có trách nhiệm thống kê, tổng hợp, xây dựng cơ sở dữ liệu thông tin về tai nạn giao thông đường bộ, cung cấp cho cơ quan, tổ chức, cá nhân theo quy định của pháp luật.";
	public static String cau39 = "	1. Mạng lưới đường bộ được chia thành sáu hệ thống, gồm quốc lộ, đường tỉnh, đường huyện, đường xã, đường đô thị và đường chuyên dùng, quy định như sau:"
			+ "\n	a) Quốc lộ là đường nối liền Thủ đô Hà Nội với trung tâm hành chính cấp tỉnh; đường nối liền trung tâm hành chính cấp tỉnh từ ba địa phương trở lên; đường nối liền từ cảng biển quốc tế, cảng hàng không quốc tế đến các cửa khẩu quốc tế, cửa khẩu chính trên đường bộ; đường có vị trí đặc biệt quan trọng đối với sự phát triển kinh tế - xã hội của vùng, khu vực;"
			+ "\n	b) Đường tỉnh là đường nối trung tâm hành chính của tỉnh với trung tâm hành chính của huyện hoặc trung tâm hành chính của tỉnh lân cận; đường có vị trí quan trọng đối với sự phát triển kinh tế - xã hội của tỉnh;"
			+ "\n	c) Đường huyện là đường nối trung tâm hành chính của huyện với trung tâm hành chính của xã, cụm xã hoặc trung tâm hành chính của huyện lân cận; đường có vị trí quan trọng đối với sự phát triển kinh tế - xã hội của huyện;"
			+ "\n	d) Đường xã là đường nối trung tâm hành chính của xã với các thôn, làng, ấp, bản và đơn vị tương đương hoặc đường nối với các xã lân cận; đường có vị trí quan trọng đối với sự phát triển kinh tế - xã hội của xã;"
			+ "\n	đ) Đường đô thị là đường trong phạm vi địa giới hành chính nội thành, nội thị;"
			+ "\n	e) Đường chuyên dùng là đường chuyên phục vụ cho việc vận chuyển, đi lại của một hoặc một số cơ quan, tổ chức, cá nhân."
			+ "\n	2. Thẩm quyền phân loại và điều chỉnh các hệ thống đường bộ quy định như sau:"
			+ "\n	a) Hệ thống quốc lộ do Bộ trưởng Bộ Giao thông vận tải quyết định;"
			+ "\n	b) Hệ thống đường tỉnh, đường đô thị do Chủ tịch Ủy ban nhân dân cấp tỉnh quyết định sau khi thỏa thuận với Bộ Giao thông vận tải (đối với đường tỉnh) và thỏa thuận với Bộ Giao thông vận tải và Bộ Xây dựng (đối với đường đô thị);"
			+ "\n	c) Hệ thống đường huyện, đường xã do Chủ tịch Ủy ban nhân dân cấp huyện quyết định sau khi được Chủ tịch Ủy ban nhân dân cấp tỉnh đồng ý;"
			+ "\n	d) Hệ thống đường chuyên dùng do cơ quan, tổ chức, cá nhân có đường chuyên dùng quyết định sau khi có ý kiến chấp thuận bằng văn bản của Bộ trưởng Bộ Giao thông vận tải đối với đường chuyên dùng đấu nối vào quốc lộ; ý kiến chấp thuận bằng văn bản của Chủ tịch Ủy ban nhân dân cấp tỉnh đối với đường chuyên dùng đấu nối vào đường tỉnh, đường đô thị, đường huyện; ý kiến chấp thuận bằng văn bản của Chủ tịch Ủy ban nhân dân cấp huyện đối với đường chuyên dùng đấu nối vào đường xã.";
	public static String cau40 = "	1. Đường bộ được đặt tên hoặc số hiệu như sau:"
			+ "\n	a) Tên đường được đặt tên danh nhân, người có công hoặc tên di tích, sự kiện lịch sử, văn hóa, tên địa danh hoặc tên theo tập quán; số hiệu đường được đặt theo số tự nhiên kèm theo chữ cái nếu cần thiết; trường hợp đường đô thị trùng với quốc lộ thì sử dụng cả tên đường đô thị và tên, số hiệu quốc lộ;"
			+ "\n	b) Tên, số hiệu đường bộ tham gia vào mạng lưới đường bộ trong khu vực, đường bộ quốc tế thực hiện theo thỏa thuận giữa Việt Nam với các quốc gia có liên quan. Đường bộ kết nối vào mạng lưới đường bộ trong khu vực, đường bộ quốc tế thì sử dụng cả tên, số hiệu đường trong nước và tên, số hiệu đường trong khu vực, đường bộ quốc tế."
			+ "\n	2. Việc đặt tên, số hiệu đường bộ do cơ quan có thẩm quyền phân loại đường bộ quyết định; riêng đường đô thị, đường tỉnh, việc đặt tên do Hội đồng nhân dân cấp tỉnh quyết định trên cơ sở đề nghị của Uỷ ban nhân dân cùng cấp."
			+ "\n	3. Chính phủ quy định cụ thể việc đặt tên, số hiệu đường bộ.";
	public static String cau41 = "	1. Đường bộ được chia theo cấp kỹ thuật gồm đường cao tốc và các cấp kỹ thuật khác."
			+ "\n	2. Đường bộ xây dựng mới phải bảo đảm tiêu chuẩn kỹ thuật của từng cấp đường; các tuyến đường bộ đang khai thác chưa vào cấp phải được cải tạo, nâng cấp để đạt tiêu chuẩn kỹ thuật của cấp đường phù hợp; đối với đường chuyên dùng còn phải áp dụng cả tiêu chuẩn riêng theo quy định của pháp luật."
			+ "\n	3. Trách nhiệm của các bộ quy định như sau:"
			+ "\n	a) Bộ Giao thông vận tải xây dựng, hướng dẫn thực hiện tiêu chuẩn kỹ thuật các cấp đường;"
			+ "\n	b) Bộ Khoa học và Công nghệ ban hành tiêu chuẩn kỹ thuật quốc gia các cấp đường."
			+ "\n	4. Trường hợp áp dụng tiêu chuẩn kỹ thuật đường bộ của nước ngoài thì phải được chấp thuận của cơ quan quản lý nhà nước có thẩm quyền.";
	public static String cau42 = "	1. Quỹ đất dành cho kết cấu hạ tầng giao thông đường bộ được xác định tại quy hoạch kết cấu hạ tầng giao thông đường bộ. Ủy ban nhân dân cấp tỉnh xác định và quản lý quỹ đất dành cho dự án xây dựng kết cấu hạ tầng giao thông đường bộ theo quy hoạch đã được phê duyệt."
			+ "\n	2. Tỷ lệ quỹ đất giao thông đô thị so với đất xây dựng đô thị phải bảo đảm từ 16% đến 26%. Chính phủ quy định cụ thể tỷ lệ quỹ đất phù hợp với loại đô thị.";
	public static String cau43 = "	1. Phạm vi đất dành cho đường bộ gồm đất của đường bộ và đất hành lang an toàn đường bộ."
			+ "\n	2. Trong phạm vi đất dành cho đường bộ, không được xây dựng các công trình khác, trừ một số công trình thiết yếu không thể bố trí ngoài phạm vi đó nhưng phải được cơ quan có thẩm quyền cho phép, gồm công trình phục vụ quốc phòng, an ninh, công trình phục vụ quản lý, khai thác đường bộ, công trình viễn thông, điện lực, đường ống cấp, thoát nước, xăng, dầu, khí."
			+ "\n	3. Trong phạm vi đất hành lang an toàn đường bộ, ngoài việc thực hiện quy định tại khoản 2 Điều này, được tạm thời sử dụng vào mục đích nông nghiệp, quảng cáo nhưng không được làm ảnh hưởng đến an toàn công trình, an toàn giao thông đường bộ. Việc đặt biển quảng cáo trên đất hành lang an toàn đường bộ phải được cơ quan quản lý đường bộ có thẩm quyền đồng ý bằng văn bản."
			+ "\n	4. Người đang sử dụng đất được pháp luật thừa nhận mà đất đó nằm trong hành lang an toàn đường bộ thì được tiếp tục sử dụng đất theo đúng mục đích đã được xác định và không được gây cản trở cho việc bảo vệ an toàn công trình đường bộ."
			+ "\n	Trường hợp việc sử dụng đất gây ảnh hưởng đến việc bảo vệ an toàn công trình đường bộ thì chủ công trình và người sử dụng đất phải có biện pháp khắc phục, nếu không khắc phục được thì Nhà nước thu hồi đất và bồi thường theo quy định của pháp luật."
			+ "\n	5. Chính phủ quy định cụ thể phạm vi đất dành cho đường bộ, việc sử dụng, khai thác đất hành lang an toàn đường bộ và việc xây dựng các công trình thiết yếu trong phạm vi đất dành cho đường bộ.";
	public static String cau44 = "	1. Công trình đường bộ xây dựng mới, nâng cấp và cải tạo phải bảo đảm tiêu chuẩn kỹ thuật và điều kiện an toàn giao thông cho người, phương tiện tham gia giao thông đường bộ, trong đó có người đi bộ và người khuyết tật. Đường đô thị xây dựng phải có hè phố, phần đường, cầu vượt, hầm và tổ chức giao thông cho người đi bộ, người khuyết tật đi lại an toàn, thuận tiện."
			+ "\n	2. Công trình đường bộ phải được thẩm định về an toàn giao thông từ khi lập dự án, thiết kế, thi công, trước và trong quá trình khai thác. Người quyết định đầu tư, chủ đầu tư có trách nhiệm tiếp thu kết quả thẩm định an toàn giao thông để phê duyệt bổ sung vào dự án."
			+ "\n	3. Khu đô thị, khu công nghiệp, khu kinh tế, khu dân cư, khu thương mại - dịch vụ và công trình khác phải có hệ thống đường gom được xây dựng ngoài hành lang an toàn đường bộ; bảo đảm khoảng cách với quốc lộ theo quy định của Chính phủ."
			+ "\n	4. Việc đấu nối được quy định như sau:"
			+ "\n	a) Trường hợp có đường nhánh thì đường gom phải nối vào đường nhánh;"
			+ "\n	b) Trường hợp đường nhánh, đường gom nối trực tiếp vào đường chính thì điểm đấu nối phải được cơ quan quản lý nhà nước có thẩm quyền về đường bộ cho phép từ khi lập dự án và thiết kế;"
			+ "\n	c) Việc đấu nối các đường từ khu đô thị, khu công nghiệp, khu kinh tế, khu dân cư, khu thương mại - dịch vụ và công trình khác vào đường bộ theo quy định của Bộ trưởng Bộ Giao thông vận tải."
			+ "\n	5. Bên cạnh tuyến quốc lộ đi qua khu dân cư phải có đường gom để phục vụ yêu cầu dân sinh.";
	public static String cau45 = "	1. Công trình báo hiệu đường bộ bao gồm: "
			+ "\n	a) Đèn tín hiệu giao thông;"
			+ "\n	b) Biển báo hiệu;"
			+ "\n	c) Cọc tiêu, rào chắn hoặc tường bảo vệ;"
			+ "\n	d) Vạch kẻ đường;"
			+ "\n	đ) Cột cây số;"
			+ "\n	e) Công trình báo hiệu khác."
			+ "\n	2. Đường bộ trước khi đưa vào khai thác phải được lắp đặt đầy đủ công trình báo hiệu đường bộ theo thiết kế được phê duyệt."
			+ "\n	3. Không được gắn vào công trình báo hiệu đường bộ các nội dung không liên quan tới ý nghĩa, mục đích của công trình báo hiệu đường bộ.";
	public static String cau46 = "	1. Đầu tư xây dựng kết cấu hạ tầng giao thông đường bộ là việc đầu tư xây dựng mới, nâng cấp, cải tạo kết cấu hạ tầng giao thông đường bộ."
			+ "\n	2. Việc đầu tư xây dựng kết cấu hạ tầng giao thông đường bộ phải phù hợp với quy hoạch giao thông vận tải đường bộ đã được cấp có thẩm quyền phê duyệt; tuân thủ trình tự quản lý đầu tư xây dựng và các quy định khác của pháp luật; bảo đảm cấp kỹ thuật đường bộ, cảnh quan, bảo vệ môi trường."
			+ "\n	3. Tổ chức, cá nhân Việt Nam và nước ngoài được đầu tư xây dựng, kinh doanh khai thác kết cấu hạ tầng giao thông đường bộ theo quy định của pháp luật."
			+ "\n	4. Ủy ban nhân dân cấp có thẩm quyền chủ trì tổ chức giải phóng mặt bằng theo đúng quyết định thu hồi đất của cơ quan nhà nước có thẩm quyền và tạo điều kiện thuận lợi cho tổ chức, cá nhân đầu tư xây dựng, kinh doanh khai thác kết cấu hạ tầng giao thông đường bộ."
			+ "\n	5. Kết cấu hạ tầng giao thông đường bộ sau khi xây dựng, nâng cấp, cải tạo phải được cơ quan có thẩm quyền nghiệm thu, quyết định đưa vào khai thác theo quy định.";
	public static String cau47 = "	1. Thi công công trình trên đường bộ đang khai thác chỉ được tiến hành khi có giấy phép của cơ quan nhà nước có thẩm quyền, thực hiện theo đúng nội dung của giấy phép và quy định của pháp luật về xây dựng."
			+ "\n	2. Trong quá trình thi công, đơn vị thi công phải bố trí báo hiệu, rào chắn tạm thời tại nơi thi công và thực hiện các biện pháp bảo đảm giao thông thông suốt, an toàn."
			+ "\n	3. Thi công công trình trên đường đô thị phải tuân thủ quy định tại khoản 1, khoản 2 Điều này và các quy định sau đây:"
			+ "\n	a) Chỉ được đào đường để sửa chữa công trình hoặc xây dựng mới hầm kỹ thuật dọc theo đường hoặc ngang qua đường nhưng phải có kế hoạch hàng năm thống nhất trước với cơ quan quản lý đường bộ, trừ trường hợp có sự cố đột xuất;"
			+ "\n	b) Phải có phương án thi công và thời gian thi công thích hợp với đặc điểm từng đường phố để không gây ùn tắc giao thông;"
			+ "\n	c) Khi thi công xong phải hoàn trả phần đường theo nguyên trạng; đối với công trình ngầm phải lập hồ sơ hoàn công và chuyển cho cơ quan quản lý đường bộ."
			+ "\n	4. Đơn vị thi công không thực hiện các biện pháp bảo đảm giao thông thông suốt, an toàn theo quy định, để xảy ra tai nạn giao thông, ùn tắc giao thông, ô nhiễm môi trường nghiêm trọng thì phải chịu trách nhiệm theo quy định của pháp luật.";
	public static String cau48 = "	1. Bảo trì đường bộ là thực hiện các công việc bảo dưỡng và sửa chữa đường bộ nhằm duy trì tiêu chuẩn kỹ thuật của đường đang khai thác."
			+ "\n	2. Đường bộ đưa vào khai thác phải được quản lý, bảo trì với các nội dung sau"
			+ "\n	đây: tra việc bảo vệ kết cấu hạ tầng giao thông đường bộ;"
			+ "\n	a) Theo dõi tình trạng công trình đường bộ; tổ chức giao thông; kiểm tra, thanh"
			+ "\n	b) Bảo dưỡng thường xuyên, sửa chữa định kỳ và sửa chữa đột xuất."
			+ "\n	3. Trách nhiệm tổ chức quản lý, bảo trì đường bộ quy định như sau:"
			+ "\n	a) Hệ thống quốc lộ do Bộ Giao thông vận tải chịu trách nhiệm;"
			+ "\n	b) Hệ thống đường tỉnh, đường đô thị do Ủy ban nhân dân cấp tỉnh chịu trách nhiệm. Việc quản lý, bảo trì hệ thống đường huyện, đường xã do Ủy ban nhân dân cấp tỉnh quy định;"
			+ "\n	c) Đường chuyên dùng, đường không do Nhà nước quản lý khai thác, đường được đầu tư xây dựng không bằng nguồn vốn từ ngân sách nhà nước do chủ đầu tư tổ chức quản lý, bảo trì theo quy định."
			+ "\n	4. Bộ trưởng Bộ Giao thông vận tải quy định việc quản lý, bảo trì đường bộ.";
	public static String cau49 = "	1. Nguồn tài chính để quản lý, bảo trì hệ thống quốc lộ và đường địa phương được bảo đảm từ quỹ bảo trì đường bộ."
			+ "\n	Nguồn tài chính để quản lý, bảo trì đường chuyên dùng, đường không do Nhà nước quản lý khai thác, đường được đầu tư xây dựng không bằng nguồn vốn từ ngân sách nhà nước do tổ chức, cá nhân quản lý khai thác chịu trách nhiệm."
			+ "\n	2. Quỹ bảo trì đường bộ được hình thành từ các nguồn sau đây:"
			+ "\n	a) Ngân sách nhà nước phân bổ hàng năm;"
			+ "\n	b) Các nguồn thu liên quan đến sử dụng đường bộ và các nguồn thu khác theo quy định của pháp luật."
			+ "\n	3. Chính phủ quy định cụ thể việc lập, quản lý và sử dụng quỹ bảo trì đường bộ ở trung ương và địa phương.";
	public static String cau50 = "	Việc xây dựng đoạn đường giao nhau cùng mức giữa đường bộ với đường sắt phải được cơ quan nhà nước có thẩm quyền cho phép; có thiết kế bảo đảm tiêu chuẩn kỹ thuật và điều kiện an toàn giao thông được cơ quan nhà nước có thẩm quyền phê duyệt theo quy định của Bộ trưởng Bộ Giao thông vận tải.";
	public static String cau51 = "	1. Trong đô thị, khi xây dựng trụ sở cơ quan, trường học, bệnh viện, trung tâm thương mại - dịch vụ, văn hóa và khu dân cư phải xây dựng đủ nơi đỗ xe phù hợp với quy mô của công trình."
			+ "\n	2. Bến xe, bãi đỗ xe, trạm dừng nghỉ phải xây dựng theo quy hoạch đã được cơ quan nhà nước có thẩm quyền phê duyệt, bảo đảm tiêu chuẩn kỹ thuật."
			+ "\n	3. Trạm thu phí là nơi thực hiện việc thu phí phương tiện tham gia giao thông đường bộ, được xây dựng theo quy hoạch hoặc dự án đầu tư được cơ quan nhà nước có thẩm quyền phê duyệt. Hoạt động của trạm thu phí phải bảo đảm giao thông thông suốt, an toàn."
			+ "\n	4. Trạm kiểm tra tải trọng xe là nơi cơ quan quản lý đường bộ thực hiện việc thu thập, phân tích, đánh giá tác động của tải trọng xe, khổ giới hạn xe đến an toàn đường bộ; kiểm tra, xử lý vi phạm đối với xe quá khổ giới hạn, quá tải trọng cho phép của đường bộ và xe bánh xích lưu hành trên đường bộ, được xây dựng theo quy hoạch do Bộ Giao thông vận tải lập, trình Thủ tướng Chính phủ phê duyệt."
			+ "\n	Trường hợp cần thiết, để bảo vệ kết cấu hạ tầng giao thông đường bộ, Bộ trưởng Bộ Giao thông vận tải quyết định thành lập trạm kiểm tra tải trọng xe tạm thời."
			+ "\n	5. Bộ trưởng Bộ Giao thông vận tải quy định về quy chuẩn kỹ thuật bến xe, bãi đỗ xe, trạm dừng nghỉ, trạm thu phí, trạm kiểm tra tải trọng xe; quy định về tổ chức, hoạt động của trạm thu phí, trạm kiểm tra tải trọng xe.";
	public static String cau52 = "	1. Bảo vệ kết cấu hạ tầng giao thông đường bộ gồm hoạt động bảo đảm an toàn và tuổi thọ của công trình đường bộ, biện pháp phòng ngừa, ngăn chặn và xử lý hành vi xâm phạm kết cấu hạ tầng giao thông đường bộ."
			+ "\n	Phạm vi bảo vệ kết cấu hạ tầng giao thông đường bộ gồm đất của đường bộ, hành lang an toàn đường bộ, phần trên không, phần dưới mặt đất, phần dưới mặt nước có liên quan đến an toàn công trình và an toàn giao thông đường bộ."
			+ "\n	2. Tổ chức, cá nhân được phép xây dựng, cải tạo, mở rộng, bảo trì công trình và tiến hành hoạt động khác trong phạm vi bảo vệ kết cấu hạ tầng giao thông đường bộ phải thực hiện theo quy định của pháp luật."
			+ "\n	3. Đơn vị quản lý công trình đường bộ có trách nhiệm bảo đảm an toàn kỹ thuật của công trình, liên đới chịu trách nhiệm đối với tai nạn giao thông xảy ra do chất lượng quản lý, bảo trì công trình; trường hợp phát hiện công trình bị hư hỏng hoặc có nguy cơ gây mất an toàn giao thông thì phải xử lý, sửa chữa kịp thời, có biện pháp phòng, chống và khắc phục kịp thời hậu quả do thiên tai gây ra đối với công trình đường bộ."
			+ "\n	4. Trách nhiệm bảo vệ kết cấu hạ tầng giao thông đường bộ quy định như sau:"
			+ "\n	a) Bộ Giao thông vận tải tổ chức và hướng dẫn thực hiện việc bảo vệ kết cấu hạ tầng giao thông đường bộ; kiểm tra, thanh tra việc thực hiện các quy định của pháp luật về quản lý, bảo vệ kết cấu hạ tầng giao thông đường bộ;"
			+ "\n	b) Bộ Công an chỉ đạo, hướng dẫn lực lượng công an kiểm tra, xử lý vi phạm pháp luật về bảo vệ kết cấu hạ tầng giao thông đường bộ theo thẩm quyền;"
			+ "\n	c) Uỷ ban nhân dân các cấp tổ chức bảo vệ kết cấu hạ tầng giao thông đường bộ trong phạm vi địa phương; bảo vệ hành lang an toàn giao thông đường bộ theo quy định của pháp luật;"
			+ "\n	d) Bộ, cơ quan ngang bộ trong phạm vi nhiệm vụ, quyền hạn của mình có trách nhiệm phối hợp bảo vệ kết cấu hạ tầng giao thông đường bộ;"
			+ "\n	đ) Chính phủ quy định việc phối hợp giữa các bộ, cơ quan ngang bộ, Ủy ban nhân dân về bảo vệ kết cấu hạ tầng giao thông đường bộ."
			+ "\n	5. Người nào phát hiện công trình đường bộ bị hư hỏng hoặc bị xâm hại, hành lang an toàn đường bộ bị lấn chiếm phải kịp thời báo cho Uỷ ban nhân dân, cơ quan quản lý đường bộ hoặc cơ quan công an nơi gần nhất để xử lý; trường hợp cần thiết có biện pháp báo hiệu ngay cho người tham gia giao thông biết. Khi nhận được tin báo, cơ quan có trách nhiệm phải nhanh chóng thực hiện các biện pháp khắc phục để bảo đảm giao thông thông suốt, an toàn.";
	public static String cau53 = "	1. Xe ô tô đúng kiểu loại được phép tham gia giao thông phải bảo đảm các quy định về chất lượng, an toàn kỹ thuật và bảo vệ môi trường sau đây:"
			+ "\n	a) Có đủ hệ thống hãm có hiệu lực;"
			+ "\n	b) Có hệ thống chuyển hướng có hiệu lực;"
			+ "\n	c) Tay lái của xe ô tô ở bên trái của xe; trường hợp xe ô tô của người nước ngoài đăng ký tại nước ngoài có tay lái ở bên phải tham gia giao thông tại Việt Nam thực hiện theo quy định của Chính phủ;"
			+ "\n	d) Có đủ đèn chiếu sáng gần và xa, đèn soi biển số, đèn báo hãm, đèn tín hiệu;"
			+ "\n	đ) Có bánh lốp đúng kích cỡ và đúng tiêu chuẩn kỹ thuật của từng loại xe;"
			+ "\n	e) Có đủ gương chiếu hậu và các trang bị, thiết bị khác bảo đảm tầm nhìn cho người điều khiển;"
			+ "\n	g) Kính chắn gió, kính cửa là loại kính an toàn;"
			+ "\n	h) Có còi với âm lượng đúng quy chuẩn kỹ thuật;"
			+ "\n	i) Có đủ bộ phận giảm thanh, giảm khói và các trang bị, thiết bị khác bảo đảm khí thải, tiếng ồn theo quy chuẩn môi trường;"
			+ "\n	k) Các kết cấu phải đủ độ bền và bảo đảm tính năng vận hành ổn định."
			+ "\n	2. Xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy đúng kiểu loại được phép tham gia giao thông phải bảo đảm các quy định về chất lượng, an toàn kỹ thuật và bảo vệ môi trường quy định tại các điểm a, b, d, đ, e, h, i và k khoản 1 Điều này."
			+ "\n	3. Xe cơ giới phải đăng ký và gắn biển số do cơ quan nhà nước có thẩm quyền cấp."
			+ "\n	4. Chính phủ quy định niên hạn sử dụng đối với xe cơ giới."
			+ "\n	5. Bộ trưởng Bộ Giao thông vận tải quy định về chất lượng an toàn kỹ thuật và bảo vệ môi trường của xe cơ giới được phép tham gia giao thông, trừ xe cơ giới của quân đội, công an sử dụng vào mục đích quốc phòng, an ninh.";
	public static String cau54 = "	1. Xe cơ giới có nguồn gốc hợp pháp, bảo đảm tiêu chuẩn chất lượng, an toàn kỹ thuật và bảo vệ môi trường theo quy định của Luật này được cơ quan nhà nước có thẩm quyền cấp đăng ký và biển số."
			+ "\n	2. Bộ trưởng Bộ Công an quy định và tổ chức cấp, thu hồi đăng ký, biển số các loại xe cơ giới; Bộ trưởng Bộ Quốc phòng quy định và tổ chức cấp, thu hồi đăng ký, biển số các loại xe cơ giới của quân đội sử dụng vào mục đích quốc phòng.";
	public static String cau55 = "	1. Việc sản xuất, lắp ráp, cải tạo, sửa chữa, bảo dưỡng và nhập khẩu xe cơ giới tham gia giao thông đường bộ phải tuân theo quy định về chất lượng an toàn kỹ thuật và bảo vệ môi trường. Không được cải tạo các xe ô tô khác thành xe ô tô chở khách."
			+ "\n	2. Chủ phương tiện không được tự thay đổi kết cấu, tổng thành, hệ thống của xe không đúng với thiết kế của nhà chế tạo hoặc thiết kế cải tạo đã được cơ quan có thẩm quyền phê duyệt."
			+ "\n	3. Xe ô tô và rơ moóc, sơ mi rơ moóc được kéo bởi xe ô tô tham gia giao thông đường bộ phải được kiểm tra định kỳ về an toàn kỹ thuật và bảo vệ môi trường (sau đây gọi là kiểm định)."
			+ "\n	4. Người đứng đầu cơ sở đăng kiểm và người trực tiếp thực hiện việc kiểm định phải chịu trách nhiệm về việc xác nhận kết quả kiểm định."
			+ "\n	5. Chủ phương tiện, người lái xe ô tô chịu trách nhiệm duy trì tình trạng an toàn kỹ thuật của phương tiện theo tiêu chuẩn quy định khi tham gia giao thông đường bộ giữa hai kỳ kiểm định."
			+ "\n	6. Bộ trưởng Bộ Giao thông vận tải quy định điều kiện, tiêu chuẩn và cấp giấy phép cho cơ sở đăng kiểm xe cơ giới; quy định và tổ chức thực hiện kiểm định xe cơ giới. Bộ trưởng Bộ Quốc phòng, Bộ trưởng Bộ Công an quy định và tổ chức kiểm định xe cơ giới của quân đội, công an sử dụng vào mục đích quốc phòng, an ninh.";
	public static String cau56 = "	1. Khi tham gia giao thông, xe thô sơ phải bảo đảm điều kiện an toàn giao thông đường bộ."
			+ "\n	2. Ủy ban nhân dân cấp tỉnh quy định cụ thể điều kiện, phạm vi hoạt động của xe thô sơ tại địa phương mình.";
	public static String cau57 = "	1. Bảo đảm các quy định về chất lượng an toàn kỹ thuật và bảo vệ môi trường sau đây:"
			+ "\n	a) Có đủ hệ thống hãm có hiệu lực;"
			+ "\n	b) Có hệ thống chuyển hướng có hiệu lực;"
			+ "\n	c) Có đèn chiếu sáng;"
			+ "\n	d) Bảo đảm tầm nhìn cho người điều khiển;"
			+ "\n	đ) Các bộ phận chuyên dùng phải lắp đặt đúng vị trí, chắc chắn, bảo đảm an toàn khi di chuyển;"
			+ "\n	e) Bảo đảm khí thải, tiếng ồn theo quy chuẩn môi trường."
			+ "\n	2. Có đăng ký và gắn biển số do cơ quan nhà nước có thẩm quyền cấp."
			+ "\n	3. Hoạt động trong phạm vi quy định, bảo đảm an toàn cho người, phương tiện và công trình đường bộ khi di chuyển."
			+ "\n	4. Việc sản xuất, lắp ráp, cải tạo, sửa chữa và nhập khẩu xe máy chuyên dùng phải tuân theo quy định về chất lượng an toàn kỹ thuật và bảo vệ môi trường."
			+ "\n	5. Chủ phương tiện và người điều khiển xe máy chuyên dùng chịu trách nhiệm duy trì tình trạng an toàn kỹ thuật và kiểm định theo quy định đối với xe máy chuyên dùng khi tham gia giao thông đường bộ."
			+ "\n	6. Bộ trưởng Bộ Giao thông vận tải quy định cụ thể về chất lượng an toàn kỹ thuật và bảo vệ môi trường, cấp, thu hồi đăng ký, biển số; quy định danh mục xe máy chuyên dùng phải kiểm định và tổ chức việc kiểm định; Bộ trưởng Bộ Quốc phòng, Bộ trưởng Bộ Công an quy định và tổ chức việc cấp, thu hồi đăng ký, biển số và kiểm định xe máy chuyên dùng của quân đội, công an sử dụng vào mục đích quốc phòng, an ninh.";
	public static String cau58 = "	1. Người lái xe tham gia giao thông phải đủ độ tuổi, sức khoẻ quy định tại Điều 60 của Luật này và có giấy phép lái xe phù hợp với loại xe được phép điều khiển do cơ quan nhà nước có thẩm quyền cấp."
			+ "\n	Người tập lái xe ô tô khi tham gia giao thông phải thực hành trên xe tập lái và có giáo viên bảo trợ tay lái."
			+ "\n	2. Người lái xe khi điều khiển phương tiện phải mang theo các giấy tờ sau:"
			+ "\n	a) Đăng ký xe;"
			+ "\n	b) Giấy phép lái xe đối với người điều khiển xe cơ giới quy định tại Điều 59 của Luật này;"
			+ "\n	c) Giấy chứng nhận kiểm định an toàn kỹ thuật và bảo vệ môi trường đối với xe cơ giới quy định tại Điều 55 của Luật này;"
			+ "\n	d) Giấy chứng nhận bảo hiểm trách nhiệm dân sự của chủ xe cơ giới.";
	public static String cau59 = "	1. Căn cứ vào kiểu loại, công suất động cơ, tải trọng và công dụng của xe cơ giới, giấy phép lái xe được phân thành giấy phép lái xe không thời hạn và giấy phép lái xe có thời hạn."
			+ "\n	2. Giấy phép lái xe không thời hạn bao gồm các hạng sau đây:"
			+ "\n	a) Hạng A1 cấp cho người lái xe mô tô hai bánh có dung tích xi-lanh từ 50 cm3 đến dưới 175 cm3;"
			+ "\n	b) Hạng A2 cấp cho người lái xe mô tô hai bánh có dung tích xi-lanh từ 175 cm3 trở lên và các loại xe quy định cho giấy phép lái xe hạng A1;"
			+ "\n	c) Hạng A3 cấp cho người lái xe mô tô ba bánh, các loại xe quy định cho giấy phép lái xe hạng A1 và các xe tương tự."
			+ "\n	3. Người khuyết tật điều khiển xe mô tô ba bánh dùng cho người khuyết tật được cấp giấy phép lái xe hạng A1."
			+ "\n	4. Giấy phép lái xe có thời hạn gồm các hạng sau đây:"
			+ "\n	a) Hạng A4 cấp cho người lái máy kéo có trọng tải đến 1.000 kg;"
			+ "\n	b) Hạng B1 cấp cho người không hành nghề lái xe điều khiển xe ô tô chở người đến 9 chỗ ngồi; xe ô tô tải, máy kéo có trọng tải dưới 3.500 kg;"
			+ "\n	c) Hạng B2 cấp cho người hành nghề lái xe điều khiển xe ô tô chở người đến 9 chỗ ngồi; xe ô tô tải, máy kéo có trọng tải dưới 3.500 kg;"
			+ "\n	d) Hạng C cấp cho người lái xe ô tô tải, máy kéo có trọng tải từ 3.500 kg trở lên và các loại xe quy định cho các giấy phép lái xe hạng B1, B2;"
			+ "\n	đ) Hạng D cấp cho người lái xe ô tô chở người từ 10 đến 30 chỗ ngồi và các loại xe quy định cho các giấy phép lái xe hạng B1, B2, C;"
			+ "\n	e) Hạng E cấp cho người lái xe ô tô chở người trên 30 chỗ ngồi và các loại xe quy định cho các giấy phép lái xe hạng B1, B2, C, D;"
			+ "\n	g) Giấy phép lái xe hạng FB2, FD, FE cấp cho người lái xe đã có giấy phép lái xe hạng B2, D, E để lái các loại xe quy định cho các giấy phép lái xe hạng này khi kéo rơ moóc hoặc xe ô tô chở khách nối toa; hạng FC cấp cho người lái xe đã có giấy phép lái xe hạng C để lái các loại xe quy định cho hạng C khi kéo rơ moóc, đầu kéo kéo sơ mi rơ moóc."
			+ "\n	5. Giấy phép lái xe có giá trị sử dụng trong phạm vi lãnh thổ Việt Nam và lãnh thổ của nước hoặc vùng lãnh thổ mà Việt Nam ký cam kết công nhận giấy phép lái xe của nhau.";
	public static String cau60 = "	1. Độ tuổi của người lái xe quy định như sau: "
			+ "\n	a) Người đủ 16 tuổi trở lên được lái xe gắn máy có dung tích xi-lanh dưới 50"
			+ "\n	b) Người đủ 18 tuổi trở lên được lái xe mô tô hai bánh, xe mô tô ba bánh có dung tích xi-lanh từ 50 cm3 trở lên và các loại xe có kết cấu tương tự; xe ô tô tải, máy kéo có trọng tải dưới 3.500 kg; xe ô tô chở người đến 9 chỗ ngồi;"
			+ "\n	c) Người đủ 21 tuổi trở lên được lái xe ô tô tải, máy kéo có trọng tải từ 3.500 kg trở lên; lái xe hạng B2 kéo rơ moóc (FB2);"
			+ "\n	d) Người đủ 24 tuổi trở lên được lái xe ô tô chở người từ 10 đến 30 chỗ ngồi; lái xe hạng C kéo rơ moóc, sơ mi rơ moóc (FC);"
			+ "\n	đ) Người đủ 27 tuổi trở lên được lái xe ô tô chở người trên 30 chỗ ngồi; lái xe hạng D kéo rơ moóc (FD);"
			+ "\n	e) Tuổi tối đa của người lái xe ô tô chở người trên 30 chỗ ngồi là 50 tuổi đối với nữ và 55 tuổi đối với nam."
			+ "\n	2. Người lái xe phải có sức khỏe phù hợp với loại xe, công dụng của xe. Bộ trưởng Bộ Y tế chủ trì, phối hợp với Bộ trưởng Bộ Giao thông vận tải quy định về tiêu chuẩn sức khỏe của người lái xe, việc khám sức khỏe định kỳ đối với người lái xe ô tô và quy định về cơ sở y tế khám sức khoẻ của người lái xe.";
	public static String cau61 = "	1. Cơ sở đào tạo lái xe là loại hình cơ sở dạy nghề, phải có đủ điều kiện về lớp học, sân tập lái, xe tập lái, đội ngũ giáo viên, giáo trình, giáo án và phải được giấy cấp phép theo quy định."
			+ "\n	2. Cơ sở đào tạo lái xe phải thực hiện đúng nội dung và chương trình quy định cho từng loại, hạng giấy phép lái xe."
			+ "\n	3. Người có nhu cầu được cấp giấy phép lái xe hạng A1, A2, A3, A4, B1 phải được đào tạo. Người có nhu cầu được cấp giấy phép lái xe hạng B2, C, D, E và các giấy phép lái xe hạng F phải được đào tạo tập trung tại cơ sở đào tạo."
			+ "\n	4. Việc đào tạo để nâng hạng giấy phép lái xe thực hiện cho những trường hợp sau đây:"
			+ "\n	a) Nâng hạng giấy phép lái xe từ hạng B1 lên hạng B2;"
			+ "\n	b) Nâng hạng giấy phép lái xe từ hạng B2 lên hạng C hoặc lên hạng D;"
			+ "\n	c) Nâng hạng giấy phép lái xe từ hạng C lên hạng D hoặc lên hạng E;"
			+ "\n	d) Nâng hạng giấy phép lái xe từ hạng D lên hạng E;"
			+ "\n	đ) Nâng hạng giấy phép lái xe từ các hạng B2, C, D, E lên các hạng giấy phép lái các xe tương ứng có kéo rơ moóc, sơ mi rơ moóc."
			+ "\n	5. Người có nhu cầu được đào tạo nâng hạng giấy phép lái xe theo quy định tại khoản 4 Điều này còn phải có đủ thời gian và số ki-lô-mét lái xe an toàn quy định cho từng hạng giấy phép lái xe; người có nhu cầu được đào tạo nâng hạng giấy phép lái xe lên hạng D, E tối thiểu phải có trình độ văn hóa trung học cơ sở."
			+ "\n	6. Việc đào tạo lái xe ô tô chở người từ 10 chỗ ngồi trở lên và lái xe kéo rơ moóc chỉ được thực hiện bằng hình thức đào tạo nâng hạng với các điều kiện quy định tại khoản 4 và khoản 5 Điều này."
			+ "\n	7. Việc sát hạch để cấp giấy phép lái xe ô tô phải thực hiện tại các trung tâm sát hạch lái xe. Các trung tâm sát hạch lái xe phải được xây dựng theo quy hoạch, có đủ cơ sở vật chất - kỹ thuật đáp ứng yêu cầu sát hạch lái xe theo quy định."
			+ "\n	8. Người sát hạch lái xe phải có thẻ sát hạch viên theo quy định và phải chịu trách nhiệm về kết quả sát hạch của mình."
			+ "\n	9. Người đã qua đào tạo và đạt kết quả kỳ sát hạch được cấp giấy phép lái xe đúng hạng đã trúng tuyển."
			+ "\n	Trường hợp giấy phép lái xe có thời hạn, trước khi hết thời hạn sử dụng người lái xe phải khám sức khỏe và làm các thủ tục theo quy định để được đổi giấy phép lái xe."
			+ "\n	10. Bộ trưởng Bộ Giao thông vận tải quy định điều kiện, tiêu chuẩn và cấp giấy phép cho cơ sở đào tạo; quy định hình thức, nội dung, chương trình đào tạo; sát hạch và cấp, đổi, thu hồi giấy phép lái xe; Bộ trưởng Bộ Quốc phòng, Bộ trưởng Bộ Công an quy định về cơ sở đào tạo, tổ chức đào tạo, sát hạch và cấp, đổi, thu hồi giấy phép lái xe cho lực lượng quân đội, công an làm nhiệm vụ quốc phòng, an ninh.";
	public static String cau62 = "	1. Người điều khiển xe máy chuyên dùng tham gia giao thông phải đủ độ tuổi, sức khỏe phù hợp với ngành nghề lao động và có chứng chỉ bồi dưỡng kiến thức pháp luật về giao thông đường bộ, bằng hoặc chứng chỉ điều khiển xe máy chuyên dùng do cơ sở đào tạo người điều khiển xe máy chuyên dùng cấp."
			+ "\n	2. Người điều khiển xe máy chuyên dùng khi tham gia giao thông phải mang theo các giấy tờ sau đây:"
			+ "\n	a) Đăng ký xe;"
			+ "\n	b) Chứng chỉ bồi dưỡng kiến thức pháp luật về giao thông đường bộ, bằng hoặc chứng chỉ điều khiển xe máy chuyên dùng;"
			+ "\n	c) Giấy chứng nhận kiểm định an toàn kỹ thuật và bảo vệ môi trường đối với xe máy chuyên dùng quy định tại Điều 57 của Luật này.";
	public static String cau63 = "	1. Có sức khỏe bảo đảm điều khiển xe an toàn."
			+ "\n	2. Hiểu biết quy tắc giao thông đường bộ.";
	public static String cau64 = "	1. Hoạt động vận tải đường bộ gồm hoạt động vận tải không kinh doanh và hoạt động kinh doanh vận tải đường bộ. Kinh doanh vận tải đường bộ là ngành nghề kinh doanh có điều kiện theo quy định của pháp luật."
			+ "\n	2. Kinh doanh vận tải đường bộ gồm kinh doanh vận tải hành khách, kinh doanh vận tải hàng hóa."
			+ "\n	3. Hoạt động vận tải đường bộ phải phù hợp với quy hoạch giao thông vận tải đường bộ và mạng lưới tuyến vận tải.";
	public static String cau65 = "	1. Thời gian làm việc của người lái xe ô tô không được quá 10 giờ trong một ngày và không được lái xe liên tục quá 4 giờ."
			+ "\n	2. Người vận tải và người lái xe ô tô chịu trách nhiệm thực hiện quy định tại khoản 1 Điều này.";
	public static String cau66 = "	1. Kinh doanh vận tải hành khách bằng xe ô tô bao gồm:"
			+ "\n	a) Kinh doanh vận tải hành khách theo tuyến cố định có xác định bến đi, bến đến với lịch trình, hành trình nhất định;"
			+ "\n	b) Kinh doanh vận tải hành khách bằng xe buýt theo tuyến cố định có các điểm dừng đón, trả khách và xe chạy theo biểu đồ vận hành với cự ly, phạm vi hoạt động nhất định;"
			+ "\n	c) Kinh doanh vận tải hành khách bằng xe taxi có lịch trình và hành trình theo yêu cầu của hành khách; cước tính theo đồng hồ tính tiền;"
			+ "\n	d) Kinh doanh vận tải hành khách theo hợp đồng không theo tuyến cố định được thực hiện theo hợp đồng vận tải;"
			+ "\n	đ) Kinh doanh vận tải khách du lịch theo tuyến, chương trình và địa điểm du lịch."
			+ "\n	2. Kinh doanh vận tải hàng hóa bằng xe ô tô bao gồm: "
			+ "\n	a) Kinh doanh vận tải hàng hóa thông thường; "
			+ "\n	b) Kinh doanh vận tải hàng hóa bằng xe taxi tải;"
			+ "\n	c) Kinh doanh vận tải hàng hóa siêu trường, siêu trọng;"
			+ "\n	d) Kinh doanh vận tải hàng nguy hiểm."
			+ "\n	3. Chính phủ quy định cụ thể về kinh doanh vận tải bằng xe ô tô.";
	public static String cau67 = "	1. Doanh nghiệp, hợp tác xã, hộ kinh doanh hoạt động kinh doanh vận tải bằng xe ô tô phải có đủ các điều kiện sau đây:"
			+ "\n	a) Đăng ký kinh doanh vận tải bằng xe ô tô theo quy định của pháp luật;"
			+ "\n	b) Bảo đảm số lượng, chất lượng và niên hạn sử dụng của phương tiện phù hợp với hình thức kinh doanh; phương tiện kinh doanh vận tải phải gắn thiết bị giám sát hành trình của xe theo quy định của Chính phủ;"
			+ "\n	c) Bảo đảm số lượng lái xe, nhân viên phục vụ trên xe phù hợp với phương án kinh doanh và phải có hợp đồng lao động bằng văn bản; nhân viên phục vụ trên xe phải được tập huấn nghiệp vụ kinh doanh vận tải, an toàn giao thông; không được sử dụng người lái xe đang trong thời kỳ bị cấm hành nghề theo quy định của pháp luật;"
			+ "\n	d) Người trực tiếp điều hành hoạt động vận tải của doanh nghiệp, hợp tác xã phải có trình độ chuyên môn về vận tải;"
			+ "\n	đ) Có nơi đỗ xe phù hợp với quy mô của doanh nghiệp, hợp tác xã, hộ kinh doanh, bảo đảm yêu cầu về trật tự, an toàn, phòng, chống cháy nổ và vệ sinh môi trường."
			+ "\n	2. Chỉ các doanh nghiệp, hợp tác xã mới được kinh doanh vận tải hành khách theo tuyến cố định, kinh doanh vận tải hành khách bằng xe buýt, bằng xe taxi và phải có đủ các điều kiện sau đây:"
			+ "\n	a) Các điều kiện quy định tại khoản 1 Điều này;"
			+ "\n	b) Có bộ phận quản lý các điều kiện về an toàn giao thông;"
			+ "\n	c) Đăng ký tiêu chuẩn chất lượng dịch vụ vận tải hành khách với cơ quan có thẩm quyền và phải niêm yết công khai."
			+ "\n	3. Chỉ các doanh nghiệp, hợp tác xã mới được kinh doanh vận tải hàng hóa bằng công-ten-nơ và phải có đủ các điều kiện quy định tại khoản 1, điểm b khoản 2 Điều này."
			+ "\n	4. Chính phủ quy định cụ thể điều kiện và việc cấp giấy phép kinh doanh vận tải bằng xe ô tô.";
	public static String cau68 = "	1. Người vận tải, người lái xe khách phải chấp hành các quy định sau đây:"
			+ "\n	a) Đón, trả hành khách đúng nơi quy định;"
			+ "\n	b) Không chở hành khách trên mui, trong khoang chở hành lý hoặc để hành khách đu, bám bên ngoài xe;"
			+ "\n	c) Không chở hàng nguy hiểm, hàng có mùi hôi thối hoặc động vật, hàng hóa khác có ảnh hưởng đến sức khỏe của hành khách;"
			+ "\n	d) Không chở hành khách, hành lý, hàng hóa vượt quá trọng tải, số người theo quy định;"
			+ "\n	e) Không để hàng hóa trong khoang chở hành khách; có biện pháp giữ gìn vệ sinh trong xe."
			+ "\n	2. Bộ trưởng Bộ Giao thông vận tải quy định về tổ chức và quản lý hoạt động vận tải hành khách bằng xe ô tô.";
	public static String cau69 = "	1. Người kinh doanh vận tải hành khách có các quyền sau đây:"
			+ "\n	a) Thu cước, phí vận tải;"
			+ "\n	b) Từ chối vận chuyển trước khi phương tiện rời bến xe, rời vị trí đón, trả hành khách theo hợp đồng vận chuyển những người đã có vé hoặc người trong danh sách hợp đồng có hành vi gây rối trật tự công cộng, gây cản trở công việc của người kinh doanh vận tải, ảnh hưởng đến sức khoẻ, tài sản của người khác, gian lận vé hoặc hành khách đang bị dịch bệnh nguy hiểm."
			+ "\n	2. Người kinh doanh vận tải hành khách có các nghĩa vụ sau đây:"
			+ "\n	a) Thực hiện đầy đủ các cam kết về chất lượng vận tải, hợp đồng vận tải;"
			+ "\n	b) Mua bảo hiểm cho hành khách; phí bảo hiểm được tính vào giá vé hành khách;"
			+ "\n	c) Giao vé, chứng từ thu cước, phí vận tải cho hành khách;"
			+ "\n	d) Bồi thường thiệt hại do người làm công, người đại diện gây ra trong khi thực hiện công việc được người kinh doanh vận tải giao;"
			+ "\n	đ) Chịu trách nhiệm về hậu quả mà người làm công, người đại diện gây ra do thực hiện yêu cầu của người kinh doanh vận tải trái quy định của Luật này."
			+ "\n	3. Bộ trưởng Bộ Tài chính chủ trì, phối hợp với Bộ trưởng Bộ Giao thông vận tải quy định về vé và chứng từ thu cước, phí vận tải hành khách.";
	public static String cau70 = "	1. Kiểm tra các điều kiện bảo đảm an toàn của xe trước khi khởi hành."
			+ "\n	2. Có thái độ văn minh, lịch sự, hướng dẫn hành khách ngồi đúng nơi quy định."
			+ "\n	3. Kiểm tra việc sắp xếp, chằng buộc hành lý, hàng hóa bảo đảm an toàn."
			+ "\n	4. Có biện pháp bảo vệ tính mạng, sức khỏe, tài sản của hành khách đi xe, giữ gìn trật tự, vệ sinh trong xe."
			+ "\n	5. Đóng cửa lên xuống của xe trước và trong khi xe chạy.";
	public static String cau71 = "	1. Hành khách có các quyền sau đây:"
			+ "\n	a) Được vận chuyển theo đúng hợp đồng vận tải, cam kết của người kinh doanh vận tải về chất lượng vận tải;"
			+ "\n	b) Được miễn cước hành lý với trọng lượng không quá 20 kg và với kích thước phù hợp với thiết kế của xe;"
			+ "\n	c) Được từ chối chuyến đi trước khi phương tiện khởi hành và được trả lại tiền vé theo quy định của Bộ trưởng Bộ Giao thông vận tải."
			+ "\n	2. Hành khách có các nghĩa vụ sau đây:"
			+ "\n	a) Mua vé và trả cước, phí vận tải hành lý mang theo quá mức quy định;"
			+ "\n	b) Có mặt tại nơi xuất phát đúng thời gian đã thỏa thuận; chấp hành quy định về vận chuyển; thực hiện đúng hướng dẫn của lái xe, nhân viên phục vụ trên xe về các quy định bảo đảm trật tự, an toàn giao thông;"
			+ "\n	c) Không mang theo hành lý, hàng hóa mà pháp luật cấm lưu thông.";
	public static String cau72 = "	1. Việc vận chuyển hàng hóa bằng xe ô tô phải chấp hành các quy định sau đây:"
			+ "\n	a) Hàng vận chuyển trên xe phải được xếp đặt gọn gàng và chằng buộc chắc chắn;"
			+ "\n	b) Khi vận chuyển hàng rời phải che đậy, không để rơi vãi."
			+ "\n	2. Không được thực hiện các hành vi sau đây:"
			+ "\n	a) Chở hàng vượt quá trọng tải thiết kế và quá kích thước giới hạn cho phép của xe;"
			+ "\n	b) Chở người trong thùng xe, trừ trường hợp quy định tại khoản 1 Điều 21 của Luật này."
			+ "\n	3. Bộ trưởng Bộ Giao thông vận tải quy định về tổ chức và quản lý hoạt động vận tải hàng hóa bằng xe ô tô.";
	public static String cau73 = "	1. Người kinh doanh vận tải hàng hóa có các quyền sau đây:"
			+ "\n	a) Yêu cầu người thuê vận tải cung cấp thông tin cần thiết về hàng hóa để ghi vào giấy vận chuyển và có quyền kiểm tra tính xác thực của thông tin đó;"
			+ "\n	b) Yêu cầu người thuê vận tải thanh toán đủ cước, phí vận tải và chi phí phát sinh; yêu cầu người thuê vận tải bồi thường thiệt hại do vi phạm thỏa thuận trong hợp đồng;"
			+ "\n	c) Từ chối vận chuyển nếu người thuê vận tải không giao hàng hóa theo thỏa thuận trong hợp đồng;"
			+ "\n	d) Yêu cầu giám định hàng hóa khi cần thiết."
			+ "\n	2. Người kinh doanh vận tải hàng hóa có các nghĩa vụ sau đây:"
			+ "\n	a) Cung cấp phương tiện đúng loại, thời gian, địa điểm và giao hàng hóa cho người nhận hàng theo thỏa thuận trong hợp đồng;"
			+ "\n	b) Hướng dẫn xếp, dỡ hàng hóa trên phương tiện;"
			+ "\n	c) Bồi thường thiệt hại cho người thuê vận tải do mất mát, hư hỏng hàng hóa xảy ra trong quá trình vận tải từ lúc nhận hàng đến lúc giao hàng, trừ trường hợp miễn bồi thường thiệt hại theo quy định của pháp luật;"
			+ "\n	d) Bồi thường thiệt hại do người làm công, người đại diện gây ra trong khi thực hiện công việc được người kinh doanh vận tải giao;"
			+ "\n	đ) Chịu trách nhiệm về hậu quả mà người làm công, người đại diện gây ra do thực hiện yêu cầu của người kinh doanh vận tải trái quy định của Luật này."
			+ "\n	3. Chính phủ quy định giới hạn trách nhiệm của người kinh doanh vận tải hàng hóa.";
	public static String cau74 = "	1. Người thuê vận tải hàng hóa có các quyền sau đây:"
			+ "\n	a) Từ chối xếp hàng hóa lên phương tiện mà phương tiện đó không đúng thỏa thuận trong hợp đồng;"
			+ "\n	b) Yêu cầu người kinh doanh vận tải giao hàng đúng thời gian, địa điểm đã thỏa thuận trong hợp đồng;"
			+ "\n	c) Yêu cầu người kinh doanh vận tải bồi thường thiệt hại theo quy định của pháp luật."
			+ "\n	2. Người thuê vận tải hàng hóa có các nghĩa vụ sau đây:"
			+ "\n	a) Chuẩn bị đầy đủ giấy tờ hợp pháp về hàng hóa trước khi giao hàng hóa cho người kinh doanh vận tải; đóng gói hàng hóa đúng quy cách, ghi ký hiệu, mã hiệu hàng hóa đầy đủ, rõ ràng; giao hàng hóa cho người kinh doanh vận tải đúng thời gian, địa điểm và nội dung khác ghi trong giấy gửi hàng;"
			+ "\n	b) Thanh toán đủ cước, phí vận tải và chi phí phát sinh cho người kinh doanh vận tải hàng hóa;"
			+ "\n	c) Cử người áp tải hàng hóa trong quá trình vận tải đối với loại hàng hóa bắt buộc phải có người áp tải.";
	public static String cau75 = "	1. Người nhận hàng có các quyền sau đây:"
			+ "\n	a) Nhận và kiểm tra hàng hóa nhận được theo giấy vận chuyển hoặc chứng từ tương đương khác;"
			+ "\n	b) Yêu cầu người kinh doanh vận tải thanh toán chi phí phát sinh do giao hàng chậm;"
			+ "\n	c) Yêu cầu hoặc thông báo cho người thuê vận tải để yêu cầu người kinh doanh vận tải bồi thường thiệt hại do mất mát, hư hỏng hàng hóa;"
			+ "\n	d) Yêu cầu giám định hàng hóa khi cần thiết."
			+ "\n	2. Người nhận hàng có các nghĩa vụ sau đây:"
			+ "\n	a) Nhận hàng hóa đúng thời gian, địa điểm đã thỏa thuận; xuất trình giấy vận chuyển và giấy tờ tùy thân cho người kinh doanh vận tải trước khi nhận hàng hóa;"
			+ "\n	b) Thanh toán chi phí phát sinh do nhận hàng chậm.";
	public static String cau76 = "	1. Hàng siêu trường, siêu trọng là hàng có kích thước hoặc trọng lượng vượt quá"
			+ "giới hạn quy định nhưng không thể tháo rời ra được."
			+ "\n	2. Việc vận chuyển hàng siêu trường, siêu trọng phải sử dụng xe vận tải phù hợp với loại hàng và phải có giấy phép sử dụng đường bộ do cơ quan nhà nước có thẩm quyền cấp."
			+ "\n	3. Xe vận chuyển hàng siêu trường, siêu trọng phải chạy với tốc độ quy định trong giấy phép và phải có báo hiệu kích thước của hàng, trường hợp cần thiết phải bố trí người chỉ dẫn giao thông để bảo đảm an toàn giao thông."
			+ "\n	4. Bộ trưởng Bộ Giao thông vận tải quy định cụ thể về vận chuyển hàng siêu trường, siêu trọng.";
	public static String cau77 = "	1. Tùy theo loại động vật sống, người kinh doanh vận tải yêu cầu người thuê vận tải bố trí người áp tải để chăm sóc trong quá trình vận tải."
			+ "\n	2. Người thuê vận tải chịu trách nhiệm về việc xếp, dỡ động vật sống theo hướng dẫn của người kinh doanh vận tải; trường hợp người thuê vận tải không thực hiện được thì phải trả cước, phí xếp, dỡ cho người kinh doanh vận tải."
			+ "\n	3. Việc vận chuyển động vật sống trên đường phải tuân theo quy định của pháp luật về vệ sinh, phòng dịch và bảo vệ môi trường.";
	public static String cau78 = "	1. Xe vận chuyển hàng nguy hiểm phải có giấy phép do cơ quan nhà nước có thẩm quyền cấp."
			+ "\n	2. Xe vận chuyển hàng nguy hiểm không được dừng, đỗ ở nơi đông người, những nơi dễ xảy ra nguy hiểm."
			+ "\n	3. Chính phủ quy định Danh mục hàng nguy hiểm, vận chuyển hàng nguy hiểm và thẩm quyền cấp giấy phép vận chuyển hàng nguy hiểm.";
	public static String cau79 = "	1. Xe buýt phải chạy đúng tuyến, đúng lịch trình và dừng, đỗ đúng nơi quy định."
			+ "\n	2. Người lái xe taxi khách, xe taxi tải đón, trả hành khách, hàng hóa theo thỏa thuận giữa hành khách, chủ hàng và người lái xe nhưng phải chấp hành các quy định về bảo đảm an toàn giao thông."
			+ "\n	3. Xe chở hàng phải hoạt động theo đúng tuyến, phạm vi và thời gian quy định đối với từng loại xe."
			+ "\n	4. Xe vệ sinh môi trường, xe ô tô chở phế thải, vật liệu rời phải được che phủ kín không để rơi, vãi trên đường phố; trường hợp để rơi, vãi thì người vận tải phải chịu trách nhiệm thu dọn ngay."
			+ "\n	5. Ủy ban nhân dân cấp tỉnh quy định cụ thể về hoạt động vận tải đường bộ trong đô thị và tỷ lệ phương tiện vận tải hành khách đáp ứng nhu cầu đi lại của người khuyết tật.";
	public static String cau80 = "	1. Việc sử dụng xe thô sơ, xe gắn máy, xe mô tô hai bánh, xe mô tô ba bánh và các loại xe tương tự để vận chuyển hành khách, hàng hóa phải theo đúng quy định về trật tự, an toàn giao thông."
			+ "\n	2. Bộ trưởng Bộ Giao thông vận tải quy định việc thực hiện khoản 1 Điều này."
			+ "\n	3. Căn cứ quy định của Bộ trưởng Bộ Giao thông vận tải, Ủy ban nhân dân cấp tỉnh quy định cụ thể việc tổ chức thực hiện tại địa phương.";

	public static String cau81 = "	1. Vận tải đa phương thức quy định trong Luật này là việc vận chuyển hàng hóa từ địa điểm nhận hàng đến địa điểm trả hàng cho người nhận hàng bằng ít nhất hai phương thức vận tải, trong đó có phương thức vận tải bằng đường bộ trên cơ sở một hợp đồng vận tải đa phương thức."
			+ "\n	2. Chính phủ quy định cụ thể về vận tải đa phương thức.";
	public static String cau82 = "	1. Dịch vụ hỗ trợ vận tải đường bộ gồm dịch vụ tại bến xe, bãi đỗ xe, trạm dừng nghỉ, đại lý vận tải, đại lý bán vé, dịch vụ thu gom hàng, dịch vụ chuyển tải, dịch vụ kho hàng, dịch vụ cứu hộ vận tải đường bộ."
			+ "\n	2. Bộ trưởng Bộ Giao thông vận tải quy định cụ thể về dịch vụ hỗ trợ vận tải đường bộ.";
	public static String cau83 = "	1. Hoạt động của bến xe ô tô khách, bến xe ô tô hàng, bãi đỗ xe, trạm dừng nghỉ phải bảo đảm trật tự, an toàn, vệ sinh môi trường, phòng, chống cháy nổ và chịu sự quản lý của cơ quan quản lý nhà nước có thẩm quyền ở địa phương."
			+ "\n	2. Doanh nghiệp, hợp tác xã khai thác bến xe ô tô khách có quyền, nghĩa vụ sắp xếp nơi bán vé hoặc tổ chức bán vé cho hành khách theo hợp đồng với người kinh doanh vận tải; sắp xếp xe ô tô có đủ điều kiện kinh doanh vận tải vào bến đón, trả khách đúng tuyến."
			+ "\n	3. Doanh nghiệp, hợp tác xã khai thác bến xe ô tô hàng có quyền, nghĩa vụ sắp xếp xe ô tô vào bến xếp dỡ hàng hóa, dịch vụ kho bãi, ký gửi, đóng gói, bảo quản hàng hóa."
			+ "\n	4. Doanh nghiệp, hợp tác xã khai thác bãi đỗ xe có quyền, nghĩa vụ tổ chức dịch vụ trông giữ phương tiện."
			+ "\n	5. Doanh nghiệp, hợp tác xã khai thác trạm dừng nghỉ có quyền, nghĩa vụ tổ chức dịch vụ phục vụ người và phương tiện tham gia giao thông đường bộ; thực hiện công việc theo hợp đồng ủy thác với người vận tải."
			+ "\n	6. Ủy ban nhân dân cấp tỉnh căn cứ vào loại bến xe ô tô để quy định giá dịch vụ xe ra, vào bến xe ô tô.";
	public static String cau84 = "	1. Xây dựng quy hoạch, kế hoạch và chính sách phát triển giao thông đường bộ; xây dựng và chỉ đạo thực hiện chương trình quốc gia về an toàn giao thông đường bộ."
			+ "\n	2. Ban hành và tổ chức thực hiện các văn bản quy phạm pháp luật về giao thông đường bộ; quy chuẩn, tiêu chuẩn về giao thông đường bộ."
			+ "\n	3. Tuyên truyền, phổ biến, giáo dục pháp luật về giao thông đường bộ."
			+ "\n	4. Tổ chức quản lý, bảo trì, bảo vệ kết cấu hạ tầng giao thông đường bộ."
			+ "\n	5. Đăng ký, cấp, thu hồi biển số phương tiện giao thông đường bộ; cấp, thu hồi giấy chứng nhận chất lượng, an toàn kỹ thuật và bảo vệ môi trường của phương tiện giao thông đường bộ."
			+ "\n	6. Quản lý đào tạo, sát hạch lái xe; cấp, đổi, thu hồi giấy phép lái xe, chứng chỉ bồi dưỡng kiến thức pháp luật về giao thông đường bộ"
			+ "\n	7. Quản lý hoạt động vận tải và dịch vụ hỗ trợ vận tải; tổ chức cứu nạn giao thông đường bộ."
			+ "\n	8. Tổ chức nghiên cứu, ứng dụng khoa học và công nghệ về giao thông đường bộ; đào tạo cán bộ và công nhân kỹ thuật giao thông đường bộ."
			+ "\n	9. Kiểm tra, thanh tra, giải quyết khiếu nại, tố cáo; xử lý vi phạm pháp luật về giao thông đường bộ."
			+ "\n	10. Hợp tác quốc tế về giao thông đường bộ.";
	public static String cau85 = "	1. Chính phủ thống nhất quản lý nhà nước về giao thông đường bộ."
			+ "\n	2. Bộ Giao thông vận tải chịu trách nhiệm trước Chính phủ thực hiện quản lý nhà nước về giao thông đường bộ."
			+ "\n	3. Bộ Công an thực hiện các nhiệm vụ quản lý nhà nước về giao thông đường bộ theo quy định của Luật này và các quy định khác của pháp luật có liên quan; thực hiện các biện pháp bảo đảm trật tự, an toàn giao thông; phối hợp với Bộ Giao thông vận tải bảo vệ kết cấu hạ tầng giao thông đường bộ."
			+ "\n	Bộ Công an, Bộ Giao thông vận tải có trách nhiệm phối hợp trong việc cung cấp số liệu đăng ký phương tiện giao thông đường bộ, dữ liệu về tai nạn giao thông và cấp, đổi, thu hồi giấy phép lái xe."
			+ "\n	4. Bộ Quốc phòng thực hiện các nhiệm vụ quản lý nhà nước về giao thông đường bộ theo quy định của Luật này và các quy định khác của pháp luật có liên quan."
			+ "\n	5. Bộ, cơ quan ngang bộ trong phạm vi nhiệm vụ, quyền hạn của mình có trách nhiệm phối hợp với Bộ Giao thông vận tải thực hiện quản lý nhà nước về giao thông đường bộ."
			+ "\n	6. Ủy ban nhân dân các cấp trong phạm vi nhiệm vụ, quyền hạn của mình tổ chức thực hiện quản lý nhà nước về giao thông đường bộ theo quy định của Luật này và các quy định khác của pháp luật có liên quan trong phạm vi địa phương.";
	public static String cau86 = "	1. Thanh tra đường bộ thực hiện chức năng thanh tra chuyên ngành về giao thông đường bộ."
			+ "\n	2. Thanh tra đường bộ có các nhiệm vụ và quyền hạn sau đây:"
			+ "\n	a) Thanh tra, phát hiện, ngăn chặn và xử phạt vi phạm hành chính trong việc chấp hành các quy định của pháp luật về bảo vệ kết cấu hạ tầng giao thông đường bộ, bảo đảm tiêu chuẩn kỹ thuật của công trình đường bộ; trường hợp cấp thiết, để kịp thời ngăn chặn hậu quả có thể xảy ra đối với công trình đường bộ, được phép dừng phương tiện giao thông và yêu cầu người điều khiển phương tiện thực hiện các biện pháp để bảo vệ công trình theo quy định của pháp luật và phải chịu trách nhiệm về quyết định đó;"
			+ "\n	b) Thanh tra, phát hiện, ngăn chặn và xử phạt vi phạm hành chính trong việc chấp hành các quy định về hoạt động vận tải và dịch vụ hỗ trợ vận tải tại các điểm dừng xe, đỗ xe trên đường bộ, bến xe, bãi đỗ xe, trạm dừng nghỉ, trạm kiểm tra tải trọng xe, trạm thu phí và tại cơ sở kinh doanh vận tải đường bộ;"
			+ "\n	c) Thanh tra, phát hiện, ngăn chặn và xử phạt vi phạm hành chính trong việc đào tạo, sát hạch, cấp, đổi, thu hồi giấy phép lái xe cơ giới đường bộ, hoạt động kiểm định an toàn kỹ thuật và bảo vệ môi trường đối với xe cơ giới. Việc thanh tra đào tạo, sát hạch, cấp, đổi, thu hồi giấy phép lái xe của lực lượng quân đội, công an do Bộ trưởng Bộ Quốc phòng, Bộ trưởng Bộ Công an quy định;"
			+ "\n	d) Thực hiện nhiệm vụ và quyền hạn khác theo quy định của pháp luật về thanh tra."
			+ "\n	3. Tổ chức và hoạt động của Thanh tra đường bộ thực hiện theo quy định của Luật này và pháp luật về thanh tra."
			+ "Bộ trưởng Bộ Giao thông vận tải quy định cụ thể nhiệm vụ, quyền hạn của Thanh tra đường bộ.";
	public static String cau87 = "	1. Cảnh sát giao thông đường bộ thực hiện việc tuần tra, kiểm soát để kiểm soát người và phương tiện tham gia giao thông đường bộ; xử lý vi phạm pháp luật về giao thông đường bộ đối với người và phương tiện tham gia giao thông đường bộ và chịu trách nhiệm trước pháp luật về quyết định của mình; phối hợp với cơ quan quản lý đường bộ phát hiện, ngăn chặn hành vi vi phạm quy định bảo vệ công trình đường bộ và hành lang an toàn đường bộ."
			+ "\n	2. Bộ trưởng Bộ Công an quy định cụ thể nhiệm vụ, quyền hạn, hình thức, nội dung tuần tra, kiểm soát của cảnh sát giao thông đường bộ."
			+ "\n	3. Chính phủ quy định việc huy động các lực lượng cảnh sát khác và công an xã phối hợp với cảnh sát giao thông đường bộ tham gia tuần tra, kiểm soát trật tự, an toàn giao thông đường bộ trong trường hợp cần thiết.";
	public static String cau88 = "	1. Luật này có hiệu lực thi hành từ ngày 01 tháng 7 năm 2009."
			+ "\n	2. Luật này thay thế Luật giao thông đường bộ ngày 29 tháng 6 năm 2001.";
	public static String cau89 = "	Chính phủ và cơ quan có thẩm quyền quy định chi tiết và hướng dẫn thi hành các điều, khoản được giao trong Luật; hướng dẫn những nội dung cần thiết khác của Luật này để đáp ứng yêu cầu quản lý nhà nước.";
}
