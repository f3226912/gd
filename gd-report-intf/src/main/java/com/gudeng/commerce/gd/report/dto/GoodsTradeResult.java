package com.gudeng.commerce.gd.report.dto;

import java.io.Serializable;

/**
 * 商品交易额排名，交易额倒序排列
 * @author Ailen
 *
 */
public class GoodsTradeResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7147642208503243321L;

	//商品ID
	private Long productId;
	
	//销量
	private Double sales;
	
	//交易额
	private Double tradeAmt;
	
	//浏览量
	private Long pv;
	
	//成交笔数
	private Long orderNum;
	
	//单位
	private Integer unit;
	
	//单位字符串
	private String unitString;
	
	//商品名称
	private String productName;
	
	//图片地址
	private String url;
	
	//对应销售此产品的商铺主要分类（用于前端显示商铺排名的种类）
	private String mainCategory;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Double getSales() {
		return sales;
	}

	public void setSales(Double sales) {
		this.sales = sales;
	}

	public Double getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(Double tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	public Long getPv() {
		return pv;
	}

	public void setPv(Long pv) {
		this.pv = pv;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getUnitString() {
		return unitString;
	}

	public void setUnitString(String unitString) {
		this.unitString = unitString;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}
	

}
