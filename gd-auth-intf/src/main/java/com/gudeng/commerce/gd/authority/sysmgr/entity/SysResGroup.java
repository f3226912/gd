package com.gudeng.commerce.gd.authority.sysmgr.entity;

import java.io.Serializable;

import com.gudeng.commerce.gd.authority.sysmgr.base.dto.BaseDTO;

/**   
 * @Description 资源组
 * @Project gd-auth-intf
 * @ClassName SysResGroup.java
 * @Author lidong(dli@cnagri-products.com)    
 * @CreationDate 2015年10月17日 下午2:37:49
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 *  Who        When                         What
 *  --------   -------------------------    -----------------------------------
 *  lidong     2015年10月17日 下午2:37:49       初始创建
 */
public class SysResGroup extends BaseDTO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5407429811727811084L;

	/** 资源组ID */
	private String resGroupID;

	/** 资源组名称 */
	private String resGroupName;

	/** 备注 */
	private String remark;

	public String getResGroupID() {
		return resGroupID;
	}

	public void setResGroupID(String resGroupID) {
		this.resGroupID = resGroupID;
	}

	public String getResGroupName() {
		return resGroupName;
	}

	public void setResGroupName(String resGroupName) {
		this.resGroupName = resGroupName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
