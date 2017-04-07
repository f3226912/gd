package com.gudeng.commerce.gd.authority.sysmgr.entity;

import java.io.Serializable;

import com.gudeng.commerce.gd.authority.sysmgr.base.dto.BaseDTO;
/**   
 * @Description 资源权限
 * @Project gd-auth-intf
 * @ClassName SysResAuth.java
 * @Author lidong(dli@cnagri-products.com)    
 * @CreationDate 2015年10月17日 下午2:37:06
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 *  Who        When                         What
 *  --------   -------------------------    -----------------------------------
 *  lidong     2015年10月17日 下午2:37:06       初始创建
 */
public class SysResAuth extends BaseDTO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1579772642932615376L;
	
	/**资源权限id */
	private String resAuthID;
	
	/** 资源组ID */
	private String resGroupID;
	
	/** 资源类型 */
	private String resType;
	
	/**资源ID */
	private String resID;
	
	/** 资源名 */
	private String resName;
	
	/** 资源编码 */
	private String resCode;
	
	/**附加查询参数  */
	/***商品类别起 止 编码**/
	private String cateCodeStart;
	
	private String cateCodeEnd;
	
	/**供应商起 止 编码**/
	private String supplierCodeStart;
	
	private String supplierCodeEnd;
	
	/**区域或仓库编码;**/
	private String wareCodeStart;
	
	private String wareCodeEnd;
	
	public String getResAuthID() {
		return resAuthID;
	}
	public void setResAuthID(String resAuthID) {
		this.resAuthID = resAuthID;
	}
	public String getResGroupID() {
		return resGroupID;
	}
	public void setResGroupID(String resGroupID) {
		this.resGroupID = resGroupID;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getResID() {
		return resID;
	}
	public void setResID(String resID) {
		this.resID = resID;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getCateCodeStart() {
		return cateCodeStart;
	}
	public void setCateCodeStart(String cateCodeStart) {
		this.cateCodeStart = cateCodeStart;
	}
	public String getCateCodeEnd() {
		return cateCodeEnd;
	}
	public void setCateCodeEnd(String cateCodeEnd) {
		this.cateCodeEnd = cateCodeEnd;
	}
	public String getSupplierCodeStart() {
		return supplierCodeStart;
	}
	public void setSupplierCodeStart(String supplierCodeStart) {
		this.supplierCodeStart = supplierCodeStart;
	}
	public String getSupplierCodeEnd() {
		return supplierCodeEnd;
	}
	public void setSupplierCodeEnd(String supplierCodeEnd) {
		this.supplierCodeEnd = supplierCodeEnd;
	}
	public String getWareCodeStart() {
		return wareCodeStart;
	}
	public void setWareCodeStart(String wareCodeStart) {
		this.wareCodeStart = wareCodeStart;
	}
	public String getWareCodeEnd() {
		return wareCodeEnd;
	}
	public void setWareCodeEnd(String wareCodeEnd) {
		this.wareCodeEnd = wareCodeEnd;
	}
	
	
}





