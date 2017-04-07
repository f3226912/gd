package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.List;

public class MemberBusinessInfoDTO implements Serializable{

	private static final long serialVersionUID = 5733377373871989451L;

	private Integer memberId;
	
	//真实姓名
	private String realName;
	
	private String mobile;
	
	private String shopsName;
	
	private String address;//详细地址
	
	private String  area;//省市区
	
	private String shopsDesc;
	//经营模式
	private String businessModel;
	//经营类型
	private String managementType;
	
	private String mainProduct;
	
	private String cateName;//主营分类名称
	
	private String categoryId;//主营分类名称
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getRealName() {
		return realName==null?mobile:realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getShopsName() {
		return shopsName;
	}
	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getShopsDesc() {
		return shopsDesc;
	}
	public void setShopsDesc(String shopsDesc) {
		this.shopsDesc = shopsDesc;
	}
	public String getBusinessModel() {
		if(businessModel==null){
			return null;
		}
		if(businessModel.equals("0")){
			return "个人经营";
		}
		if(businessModel.equals("1")){
			return "企业经营";
		}
		return businessModel;
	}
	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}
	public String getManagementType() {
		if(managementType==null){
			return null;
		}
		if(managementType.equals("1")){
			return "种养大户";
		}
		if(managementType.equals("2")){
			return "合作社";
		}
		if(managementType.equals("3")){
			return "基地";
		}
		return managementType;
	}
	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}
	public String getMainProduct() {
		return mainProduct;
	}
	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}
	/**
	 * 分类列表
	 */
	private List<?> categoryList;
	public List<?> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<?> categoryList) {
		this.categoryList = categoryList;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
