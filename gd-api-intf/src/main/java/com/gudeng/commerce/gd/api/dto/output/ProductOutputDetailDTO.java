package com.gudeng.commerce.gd.api.dto.output;

import java.io.Serializable;

/**
 * 商品输出类
 * @date 2016年10月20日
 */
public class ProductOutputDetailDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -532390670571786068L;
	
	private String productName;  //商品名称
	private Long productId; //商品id
	private String price; //商品价格
	private String imageUrl; //商品图片地址
	private Long hits;    //商品浏览次数
	
	private String memberGrade;  //金牌会员等级 0普通会员 1金牌会员
	private String cbstatus;     //基地认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	private String certifstatus; //地理认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	private String ccstatus;     //合作社认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	private String comstatus;    //企业认证状态,-1:未提交认证,0:待认证,1:已认证,2:已驳回
	private String unitName;  //商品单位名
	
	private String businessName;
	private Long businessId;
	private String produceArea;//商品产地 省,市
	private String updateDate;
	private String stockCount; //库存
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Long getHits() {
		return hits;
	}
	public void setHits(Long hits) {
		this.hits = hits;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public String getProduceArea() {
		return produceArea;
	}
	public void setProduceArea(String produceArea) {
		this.produceArea = produceArea;
	}
	public String getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(String memberGrade) {
		this.memberGrade = "1".equals(memberGrade) ? "1" : "0";
	}
	public String getCbstatus() {
		return cbstatus;
	}
	public void setCbstatus(String cbstatus) {
		this.cbstatus = "1".equals(cbstatus) ? "1" : "0";
	}
	public String getCertifstatus() {
		return certifstatus;
	}
	public void setCertifstatus(String certifstatus) {
		this.certifstatus = "1".equals(certifstatus) ? "1" : "0";
	}
	public String getCcstatus() {
		return ccstatus;
	}
	public void setCcstatus(String ccstatus) {
		this.ccstatus = "1".equals(ccstatus) ? "1" : "0";
	}
	public String getComstatus() {
		return comstatus;
	}
	public void setComstatus(String comstatus) {
		this.comstatus = "1".equals(comstatus) ? "1" : "0";
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getStockCount() {
		return stockCount;
	}
	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}
}
