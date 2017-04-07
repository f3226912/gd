package com.gudeng.commerce.gd.authority.sysmgr.entity;

import java.io.Serializable;

import com.gudeng.commerce.gd.authority.sysmgr.base.dto.BaseDTO;


/**   
 * @Description 系统参数
 * @Project gd-auth-intf
 * @ClassName SysParams.java
 * @Author lidong(dli@cnagri-products.com)    
 * @CreationDate 2015年10月17日 下午2:36:43
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 *  Who        When                         What
 *  --------   -------------------------    -----------------------------------
 *  lidong     2015年10月17日 下午2:36:43       初始创建
 */
public class SysParams extends BaseDTO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6735371262833489870L;
	/**
	 * 主键ID
	 */
	private String sysParamsID;
	/**
	 * 参数名
	 */
	private String paramsName;
	/**
	 * 参数值
	 */
	private String paramsValue;
	/**
	 * 备注
	 */
	private String remark;

	public String getSysParamsID() {
		return sysParamsID;
	}

	public void setSysParamsID(String sysParamsID) {
		this.sysParamsID = sysParamsID;
	}

	public String getParamsName() {
		return paramsName;
	}

	public void setParamsName(String paramsName) {
		this.paramsName = paramsName;
	}

	public String getParamsValue() {
		return paramsValue;
	}

	public void setParamsValue(String paramsValue) {
		this.paramsValue = paramsValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
