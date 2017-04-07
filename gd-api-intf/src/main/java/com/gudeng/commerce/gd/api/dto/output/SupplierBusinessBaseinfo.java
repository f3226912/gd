package com.gudeng.commerce.gd.api.dto.output;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;

/**
 * 查询商铺输出类
 * 
 * @date 2016年10月18日
 */
public class SupplierBusinessBaseinfo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 9067299373861398861L;
	private String nickName;
	private String mobile;
	private String businessName;
	private String businessId;
	private String productCategory;
	private String marketName;
	private String businessMode;
	private Double tradingVolume; //商铺日交易额
	private String comstatus;//企业认证 1已认证
	private String cpstatus ;//个人认证 1已认证
	
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getBusinessMode() {
		return businessMode;
	}
	public void setBusinessMode(String businessMode) {
		this.businessMode = businessMode;
	}
	public Double getTradingVolume() {
		return tradingVolume;
	}
	public void setTradingVolume(Double tradingVolume) {
		this.tradingVolume = tradingVolume;
	}
	public String getComstatus() {
		return comstatus;
	}
	public void setComstatus(String comstatus) {
		this.comstatus = comstatus;
	}
	public String getCpstatus() {
		return cpstatus;
	}
	public void setCpstatus(String cpstatus) {
		this.cpstatus = cpstatus;
	}

	
}
