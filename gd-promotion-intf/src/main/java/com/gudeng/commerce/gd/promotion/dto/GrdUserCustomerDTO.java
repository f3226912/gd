package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdUserCustomerEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdUserCustomerDTO extends GrdUserCustomerEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1724751844471057328L;
	
	/**
	 * 客户姓名
	 */
	private String memberName;
	
	private String memberMobile;
	
	private String grdUserName;
	
	private String grdMobile;
	
	private String marketName;
	
	private Integer marketId;
	
	private String remarks;
	
	private Integer grdOldUserId;
	
	 /**
     *操作人ID:即后台用户ID
     */
     private String createUserId;

     /**
     *操作人姓名:后台用户姓名
     */
     private String createUserName;

     /**
     *操作人账号:后台用户账号
     */
     private String createUserCode;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public String getGrdUserName() {
		return grdUserName;
	}

	public void setGrdUserName(String grdUserName) {
		this.grdUserName = grdUserName;
	}

	public String getGrdMobile() {
		return grdMobile;
	}

	public void setGrdMobile(String grdMobile) {
		this.grdMobile = grdMobile;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getGrdOldUserId() {
		return grdOldUserId;
	}

	public void setGrdOldUserId(Integer grdOldUserId) {
		this.grdOldUserId = grdOldUserId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
}