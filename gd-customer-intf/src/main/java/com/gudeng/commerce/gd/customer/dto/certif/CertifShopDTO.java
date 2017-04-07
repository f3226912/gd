package com.gudeng.commerce.gd.customer.dto.certif;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;
import com.gudeng.commerce.gd.customer.entity.certif.CertifShopEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class CertifShopDTO extends CertifShopEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5238933981067379737L;

	@ExcelConf(excelHeader = "状态", sort = 4)
	private String statusStr;

	private String commitTimeStr;

	private String marketName;

	private String cateName;

    private String userStatus;

	private String mobile;
	private String imgHost ;
    
    private String reason;    public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getStatusStr() {
		if ("2".equals(this.getStatus())) {
    		return "已驳回";
		} else if ("1".equals(this.getStatus())) {
    		return "已认证";
		} else {
    		return "待认证";
    	}
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getCommitTimeStr() {
		return commitTimeStr;
	}

	public void setCommitTimeStr(String commitTimeStr) {
		this.commitTimeStr = commitTimeStr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getImgHost() {
		return imgHost;
	}

	public void setImgHost(String imgHost) {
		this.imgHost = imgHost;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}}