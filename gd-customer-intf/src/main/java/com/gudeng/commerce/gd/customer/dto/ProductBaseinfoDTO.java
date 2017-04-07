package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.ProductBaseinfoEntity;

public class ProductBaseinfoDTO extends ProductBaseinfoEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8088926119362310959L;
	
	private String url170;
	private String memberGrade;  //金牌会员等级 0普通会员 1金牌会员
	private String cbstatus;     //基地认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	private String certifstatus; //地理认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	private String ccstatus;     //合作社认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	private String comstatus;    //企业认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	private String businessName;
	private String originProvinceName;
	private String originCityName;
	private Integer memberId;
	private Integer memberLvl;
	private String unitName;  //商品单位名
	
	private String shopsName;
	private String mobile;
	private String cateName;
	private String marketName;
	private Long topParentId;
	private Long parentId;
	
	public String getShopsName() {
		return shopsName;
	}
	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public Long getTopParentId() {
		return topParentId;
	}
	public void setTopParentId(Long topParentId) {
		this.topParentId = topParentId;
	}
	public String getUrl170() {
		return url170;
	}
	public void setUrl170(String url170) {
		this.url170 = url170;
	}
	public String getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}
	public String getCbstatus() {
		return cbstatus;
	}
	public void setCbstatus(String cbstatus) {
		this.cbstatus = cbstatus;
	}
	public String getCertifstatus() {
		return certifstatus;
	}
	public void setCertifstatus(String certifstatus) {
		this.certifstatus = certifstatus;
	}
	public String getCcstatus() {
		return ccstatus;
	}
	public void setCcstatus(String ccstatus) {
		this.ccstatus = ccstatus;
	}
	public String getComstatus() {
		return comstatus;
	}
	public void setComstatus(String comstatus) {
		this.comstatus = comstatus;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getOriginProvinceName() {
		return originProvinceName;
	}
	public void setOriginProvinceName(String originProvinceName) {
		this.originProvinceName = originProvinceName;
	}
	public String getOriginCityName() {
		return originCityName;
	}
	public void setOriginCityName(String originCityName) {
		this.originCityName = originCityName;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getMemberLvl() {
		return memberLvl;
	}
	public void setMemberLvl(Integer memberLvl) {
		this.memberLvl = memberLvl;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
    
}