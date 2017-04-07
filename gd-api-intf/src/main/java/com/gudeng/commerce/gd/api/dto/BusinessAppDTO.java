package com.gudeng.commerce.gd.api.dto;

public class BusinessAppDTO {

	/** 店主id  */
	private Long userId;
	
	/** 店铺id  */
	private Long businessId;
	
	/** 店铺名称  */
	private String shopsName;
	
	/** 店铺公司名称  */
	private String name;
	
	/** 主营商品  */
	private String mainProduct;
	
	/** 市场id  */
	private Long marketId;
	
	/** 市场名字  */
	private String marketName;
	
	/** 店主手机号  */
	private String mobile;
	
	/** 会员等级 1黄金会员  */
	private String memberGrade;
	
	/** 是否关注, 0:否, 1: 是 */
	private Integer isFocus;
	
	private String originAddress;
	
	private String cateNames;
	
	//经营模式
	public Integer businessModel;
	//经营类型
	public Integer managementType;
	//商铺中的产品个数
	public Integer productCount;
	
	/**
	 * 企业认证
	 */
	public Integer comstatus;
	/**
	 * 合作社认证
	 */
	public Integer ccstatus;
	/**
	 * 基地认证 
	 */
	public Integer cbstatus;
	
	public Integer getComstatus() {
		return comstatus;
	}
	public void setComstatus(Integer comstatus) {
		this.comstatus = comstatus;
	}
	public Integer getCcstatus() {
		return ccstatus;
	}
	public void setCcstatus(Integer ccstatus) {
		this.ccstatus = ccstatus;
	}
	public Integer getCbstatus() {
		return cbstatus;
	}
	public void setCbstatus(Integer cbstatus) {
		this.cbstatus = cbstatus;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public Integer getBusinessModel() {
		return businessModel;
	}
	public void setBusinessModel(Integer businessModel) {
		this.businessModel = businessModel;
	}
	public Integer getManagementType() {
		return managementType;
	}
	public void setManagementType(Integer managementType) {
		this.managementType = managementType;
	}
	public String getCateNames() {
		return cateNames;
	}
	public void setCateNames(String cateNames) {
		this.cateNames = cateNames;
	}
	public String getOriginAddress() {
		return originAddress;
	}
	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	
	public String getShopsName() {
		return shopsName;
	}
	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMainProduct() {
		return mainProduct;
	}
	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}
	
	public Long getMarketId() {
		return marketId;
	}
	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}
	
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Integer getIsFocus() {
		return isFocus;
	}
	public void setIsFocus(Integer isFocus) {
		this.isFocus = isFocus;
	}
	public String getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}
}
