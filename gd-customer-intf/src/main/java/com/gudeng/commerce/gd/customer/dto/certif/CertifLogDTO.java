package com.gudeng.commerce.gd.customer.dto.certif;

import com.gudeng.commerce.gd.customer.entity.certif.CertifLogEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 *
 * @author lidong
 *
 */
public class CertifLogDTO extends CertifLogEntity {
	
	private String statusStr;
	
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

	

}