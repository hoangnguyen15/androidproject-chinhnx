package com.krazevina.thioto.objects;

public class ItemBienbao {

	public String id;
	public String ten_bien_bao=null;
	public String noi_dung=null;
	public String link_anh=null;
	public String phan_loai=null;
	public ItemBienbao(String id,String name,String content,String urlimage,String category){
		this.id=id;
		this.ten_bien_bao=name;
		this.noi_dung=content;
		this.link_anh=urlimage;
		this.phan_loai=category;
	}
	
	public ItemBienbao(){
		
	}
}
