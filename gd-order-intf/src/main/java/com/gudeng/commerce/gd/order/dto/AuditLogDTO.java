package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.gudeng.commerce.gd.order.entity.AuditLogEntity;

/**
 * 
 * @description: TODO - 审核日志实体类
 * @Classname: AuditLogDTO
 * @author lmzhang@gdeng.cn
 *
 */
public class AuditLogDTO extends AuditLogEntity implements Serializable {
	
	private static final long serialVersionUID = -3729651241308787166L;
	
	private String createUserName;	// 创建人名字
	
	private String auditTimeStr;	// aditTime的字符串形式

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	// 将auditTime(Datel类型)格式化成字符串赋给auditTimeStr
	public String getAuditTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != this.getAuditTime()){
			this.auditTimeStr = sdf.format(this.getAuditTime());
		}
		return auditTimeStr;
	}
	
	
}
