package com.gudeng.commerce.gd.customer.dto.certif;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;
import com.gudeng.commerce.gd.customer.entity.certif.CertifSpProductEntity;
/**
 *
 */
public class CertifSpProductDTO extends CertifSpProductEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -3100411651719667127L;

	@ExcelConf(excelHeader="状态", sort = 4)
	private String statusStr;
	
    @ExcelConf(excelHeader="申请时间", sort=3)
    private String commitTimeString;

    private String userStatus;
    
    /**
	 * 省,中文
	 */
	private String provinceStr;

	/**
	 * 市,中文
	 */
	private String cityStr;

	/**
	 * 县,中文
	 */
	private String areaStr;
	
	private String imgHost ;
    
	/**
	 * 地理标志产品认证驳回原因
	 */
	private String reason;
	
	/**
	 * 电话号码
	 */
	private String mobile;

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getImgHost() {
		return imgHost;
	}

	public void setImgHost(String imgHost) {
		this.imgHost = imgHost;
	}

	public String getProvinceStr() {
		return provinceStr;
	}

	public void setProvinceStr(String provinceStr) {
		this.provinceStr = provinceStr;
	}

	public String getCityStr() {
		return cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}

	public String getAreaStr() {
		return areaStr;
	}

	public void setAreaStr(String areaStr) {
		this.areaStr = areaStr;
	}

	public String getStatusStr() {
    	if("2".equals(this.getStatus())){
    		return "已驳回";
    	}else if("1".equals(this.getStatus())){
    		return "已认证";
    	}else{
    		return "待认证";
    	}
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getCommitTimeString() {
		return commitTimeString;
	}

	public void setCommitTimeString(String commitTimeString) {
		this.commitTimeString = commitTimeString;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}