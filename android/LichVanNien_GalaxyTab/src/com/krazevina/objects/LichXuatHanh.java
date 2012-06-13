package com.krazevina.objects;

public class LichXuatHanh {
	static String lichxuathanh;
	
	
	//Thang 1,4,7,10
	public static String DUONGPHONG = "Rất tốt, xuất hành thuận, cầu tài được như ý muốn, gặp quý nhân phù trợ";
	public static String KIMTHO = "(Xấu) ra đi nhỡ tàu xe, cầu tài không được trên đường đi mất của, bất lợi";
	public static String KIMDUONG = "Xuất hành tốt, có quý nhân phù trợ, tàu xe thông suốt, thưa kiện có lý phải";
	public static String THUANDUONG = "Xuất hành tốt, lúc về cũng tốt nhiều thuận lợi, được người tốt giúp đỡ, cầu tài được tài, tranh luận thắng lợi";
	public static String DAOTAC = "(Rất xấu) Xuất hành bị hại, mất của";
	public static String BAOTHUONG = "Xuất hành thuận lợi, gặp người lớn vừa lòng, làm việc ý muốn, áo phẩm vinh quang";
	
	//Thang 2,5,8,11
	public static String THIENMON = "Xuất hành làm mọi việc vừa ý cầu được ước thấy, mọi việc đều thông đạt";
	public static String THIENDUONG = "Xuất hành tốt, có quý nhân phù trợ, buôn bán may mắn mọi việc đều như ý";
	public static String THIENTAI = "Xuất hành tốt, cầu tài thắng lợi được người giúp đỡ, mọi việc đều rất thuận lợi";
	public static String THIENTAC = "Xuất hành xấu, cầu tài không được đi đường mất cắp mọi việc đều xấu";
	public static String THIENDAO = "Xuất hành cầu tài nên tránh dù được cũng mất mát tốn kém thất lý mà thua thiệt";
	public static String THIENTHUONG = "Xuất hành để gặp cấp trên thì rất hay, cầu tài thì được, mọi việc đều thuận lợi";
	public static String THIENHAU = "Xuất hành dù ít cũng cãi cọ, tránh xảy ra tai nạn chảy máu";
	public static String THIENDUONG1 = "Xuất hành tốt, cầu tài được tài, mọi việc như ý muốn, hỏi vợ hỏi chồng đều tốt";
	
	//Thang 3,6,9,12
	public static String BACHHODAU = "Xuất hành cầu tài đều được, đi đâu đến thông đạt";
	public static String BACHHOKIEP = "Xuất hành cầu tài được như ý muốn đi hướng nam và bắc đều rất thuận lợi";
	public static String BACHHOTUC = "Cấm đi xa, làm việc gì cũng không công, rất xấu trong mọi việc";
	public static String HUYENVU = "Xuất hành nên đi vào sáng sớm, cầu tài thắng lớn, mọi việc đều tốt";
	public static String CHUTUOC = "Xuất hành cầu tài đều xấu, mất của, kiện thua vì đuối lý";
	public static String THANHLONGTUC = "Không nên đi xa, xuất hành tài lộc không có, kiện tụng đuối lý";
	public static String THANHLONGKIEP = "Xuất hành các phương đều tốt, trăm sự như ý";
	public static String THANHLONGDAU = "Xuất hành nên đi vào sáng sớm, cầu tài thắng lợi, mọi việc đều tốt";
	
	public static String getLichXuatHanh(int lunarDay,int lunarMonth){
		//1,4,7,10
		if((lunarMonth == 1 ||lunarMonth == 4 ||lunarMonth == 7 || lunarMonth == 10) && lunarDay %6 == 0){
			lichxuathanh = BAOTHUONG;
		}
		if((lunarMonth == 1 ||lunarMonth == 4 ||lunarMonth == 7 || lunarMonth == 10) && lunarDay %6 == 5){
			lichxuathanh = DAOTAC;
		}
		if((lunarMonth == 1 ||lunarMonth == 4 ||lunarMonth == 7 || lunarMonth == 10) && lunarDay %6 == 4){
			lichxuathanh = THUANDUONG;
		}
		if((lunarMonth == 1 ||lunarMonth == 4 ||lunarMonth == 7 || lunarMonth == 10) && lunarDay %6 == 3){
			lichxuathanh = KIMDUONG;
		}
		if((lunarMonth == 1 ||lunarMonth == 4 ||lunarMonth == 7 || lunarMonth == 10) && lunarDay %6 == 2){
			lichxuathanh = KIMTHO;
		}
		if((lunarMonth == 1 ||lunarMonth == 4 ||lunarMonth == 7 || lunarMonth == 10) && lunarDay %6 == 1){
			lichxuathanh = DUONGPHONG;
		}
		
		//2,5,8,11
		if((lunarMonth == 2 ||lunarMonth == 5 ||lunarMonth == 8 || lunarMonth == 11) && lunarDay %8 == 0){
			lichxuathanh = THIENTHUONG;
		}
		if((lunarMonth == 2 ||lunarMonth == 5 ||lunarMonth == 8 || lunarMonth == 11) && lunarDay %8 == 7){
			lichxuathanh = THIENHAU;
		}
		if((lunarMonth == 2 ||lunarMonth == 5 ||lunarMonth == 8 || lunarMonth == 11) && lunarDay %8 == 6){
			lichxuathanh = THIENDUONG1;
		}
		if((lunarMonth == 2 ||lunarMonth == 5 ||lunarMonth == 8 || lunarMonth == 11) && lunarDay %8 == 5){
			lichxuathanh = THIENTAC;
		}
		if((lunarMonth == 2 ||lunarMonth == 5 ||lunarMonth == 8 || lunarMonth == 11) && lunarDay %8 == 4){
			lichxuathanh = THIENTAI;
		}
		if((lunarMonth == 2 ||lunarMonth == 5 ||lunarMonth == 8 || lunarMonth == 11) && lunarDay %8 == 3){
			lichxuathanh = THIENDUONG;
		}
		if((lunarMonth == 2 ||lunarMonth == 5 ||lunarMonth == 8 || lunarMonth == 11) && lunarDay %8 == 2){
			lichxuathanh = THIENMON;
		}
		if((lunarMonth == 2 ||lunarMonth == 5 ||lunarMonth == 8 || lunarMonth == 11) && lunarDay %8 == 1){
			lichxuathanh = THIENDAO;
		}
		
		//3,6,9,12
		if((lunarMonth == 3 ||lunarMonth == 6 ||lunarMonth == 9 || lunarMonth == 12) && lunarDay %8 == 0){
			lichxuathanh = THANHLONGTUC;
		}
		if((lunarMonth == 3 ||lunarMonth == 6 ||lunarMonth == 9 || lunarMonth == 12) && lunarDay %8 == 7){
			lichxuathanh = THANHLONGKIEP;
		}
		if((lunarMonth == 3 ||lunarMonth == 6 ||lunarMonth == 9 || lunarMonth == 12) && lunarDay %8 == 6){
			lichxuathanh = THANHLONGDAU;
		}
		if((lunarMonth == 3 ||lunarMonth == 6 ||lunarMonth == 9 || lunarMonth == 12) && lunarDay %8 == 5){
			lichxuathanh = HUYENVU;
		}
		if((lunarMonth == 3 ||lunarMonth == 6 ||lunarMonth == 9 || lunarMonth == 12) && lunarDay %8 == 4){
			lichxuathanh = BACHHOTUC;
		}
		if((lunarMonth == 3 ||lunarMonth == 6 ||lunarMonth == 9 || lunarMonth == 12) && lunarDay %8 == 3){
			lichxuathanh = BACHHOKIEP;
		}
		if((lunarMonth == 3 ||lunarMonth == 6 ||lunarMonth == 9 || lunarMonth == 12) && lunarDay %8 == 2){
			lichxuathanh = BACHHODAU;
		}
		if((lunarMonth == 3 ||lunarMonth == 6 ||lunarMonth == 9 || lunarMonth == 12) && lunarDay %8 == 1){
			lichxuathanh = CHUTUOC;
		}
		return lichxuathanh;
	}

}
