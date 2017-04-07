package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdUserCustomerLogEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdUserCustomerLogDTO extends GrdUserCustomerLogEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1724751844491057328L;
	
 
    private String grdOldUserName;

    private String grdNewUserName;
    
    private String grdOldUserMobile;
    
    private String grdNewUserMobile;

	public String getGrdOldUserName() {
		return grdOldUserName;
	}

	public void setGrdOldUserName(String grdOldUserName) {
		this.grdOldUserName = grdOldUserName;
	}

	public String getGrdNewUserName() {
		return grdNewUserName;
	}

	public void setGrdNewUserName(String grdNewUserName) {
		this.grdNewUserName = grdNewUserName;
	}

	public String getGrdOldUserMobile() {
		return grdOldUserMobile;
	}

	public void setGrdOldUserMobile(String grdOldUserMobile) {
		this.grdOldUserMobile = grdOldUserMobile;
	}

	public String getGrdNewUserMobile() {
		return grdNewUserMobile;
	}

	public void setGrdNewUserMobile(String grdNewUserMobile) {
		this.grdNewUserMobile = grdNewUserMobile;
	}
    
    
     
}