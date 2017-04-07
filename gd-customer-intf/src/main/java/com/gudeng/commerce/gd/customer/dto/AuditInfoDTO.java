package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.customer.entity.AuditInfoEntity;


public class AuditInfoDTO extends AuditInfoEntity  implements Serializable, Comparator{

  

	/**
	 * 
	 */
	private static final long serialVersionUID = 7802150855766929130L;

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	} 

	public String getUpdateTime_string() {
		return updateTime_string;
	}

	public void setUpdateTime_string(String updateTime_string) {
		this.updateTime_string = updateTime_string;
	}

	public String getCreateTime_string() {
		return createTime_string;
	}

	public void setCreateTime_string(String createTime_string) {
		this.createTime_string = createTime_string;
	}
	
	//增加updateTime的String类型，用于 mybaties操作数据库Date类型的insert和更新

	public String updateTime_string;
	
	//增加createTime的String类型，用于 mybaties操作数据库Date类型的insert和更新

	public String createTime_string;
	
	public String getAuditTime_string() {
		return auditTime_string;
	}

	public void setAuditTime_string(String auditTime_string) {
		this.auditTime_string = auditTime_string;
	}

	public String auditTime_string;
	
	
	
	
    
	 
	 
}
