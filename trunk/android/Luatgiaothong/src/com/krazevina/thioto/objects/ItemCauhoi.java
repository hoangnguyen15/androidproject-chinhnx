package com.krazevina.thioto.objects;

public class ItemCauhoi {

	public String idcauhoi=null;
	public int sothutucauhoithi=-1;
	public String cauhoi=null;
	public String traloia=null;
	public String traloib=null;
	public String traloic=null;
	public String traloid=null;
	public String traloi=null;
	public String ketqua=null;
	public String image=null;
	public String phanloai=null;
	public String thi=null;
	public String datraloidapan="-1";//phục vụ cho ghi nhớ lại phần bộ đề
	public ItemCauhoi(){
		
	}
	public ItemCauhoi(String cauhoi,String traloia,String traloib,String traloic,String traloid,
			String ketqua,String image,String phanloai,String thi){
		this.cauhoi=cauhoi;
		this.traloia=traloia;
		this.traloib=traloib;
		this.traloic=traloic;
		this.traloid=traloid;
		this.ketqua=ketqua;
		this.image=image;
		this.phanloai=phanloai;
		this.thi=thi;
		
	}
	public int dachon=-1;
	
	
}
