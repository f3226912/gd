package com.gudeng.commerce.gd.api.dto.output;

import java.util.List;

import com.gudeng.commerce.gd.api.dto.input.MemberAddressInputDTO;

public class MemberAddressOutputDTO extends MemberAddressInputDTO {

	private static final long serialVersionUID = -3943354575057475984L;
	
	private String s_province;  //发货省名字;
	
	private String s_city;     //发货市名字;
	
	private String s_area;     //发货区县名字;
	
	private String f_province;  //收货省名字;
	
	private String f_city;     //收货市名字;
	
	private String f_area;     //收货区县名字;

	private List<?> productDetailList;

	public String getS_province() {
		return s_province;
	}

	public void setS_province(String s_province) {
		this.s_province = s_province;
	}

	public String getS_city() {
		return s_city;
	}

	public void setS_city(String s_city) {
		this.s_city = s_city;
	}

	public String getS_area() {
		return s_area;
	}

	public void setS_area(String s_area) {
		this.s_area = s_area;
	}

	public String getF_province() {
		return f_province;
	}

	public void setF_province(String f_province) {
		this.f_province = f_province;
	}

	public String getF_city() {
		return f_city;
	}

	public void setF_city(String f_city) {
		this.f_city = f_city;
	}

	public String getF_area() {
		return f_area;
	}

	public void setF_area(String f_area) {
		this.f_area = f_area;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<?> getProductDetailList() {
		return productDetailList;
	}

	public void setProductDetailList(List<?> productDetailList) {
		this.productDetailList = productDetailList;
	}
}
