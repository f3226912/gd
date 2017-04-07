package com.gudeng.commerce.gd.bi.dto;

import com.gudeng.commerce.gd.bi.entity.GrdProPersonalAuthEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProPersonalAuthDTO extends GrdProPersonalAuthEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 701601059389781160L;
	private String applyTimeString;
	private String auditTimeString;
	private String statusString;
	
	public String getApplyTimeString() {
		return applyTimeString;
	}
	public void setApplyTimeString(String applyTimeString) {
		this.applyTimeString = applyTimeString;
	}
	public String getAuditTimeString() {
		return auditTimeString;
	}
	public void setAuditTimeString(String auditTimeString) {
		this.auditTimeString = auditTimeString;
	}
	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	

}