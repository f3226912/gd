package com.gudeng.commerce.gd.api.dto.output;

import java.util.Date;

/**
 * 卖家Pos机订单列表DTO
 *
 * @author TerryZhang
 */
public class PosOrderListDTO {

	/**
	 * 订单号
	 */
	private Long orderNo;

	/**
	 * 订单创建时间
	 */
	private String createDate;

	/**
	 * 产品名称列表
	 */
	private String productsName;

	/**
	 * 买主名
	 */
	private String buyerName;

	/**
	 * 买家手机号
	 */
	private String mobile;

	/**
	 * 产品个数
	 */
	private Integer productNum;

	/**
	 * 订单总额
	 */
	private Double orderAmount;

	/**
	 * 用户实际支付金额
	 */
	private String payAmount;

	/**
	 * 格式化后价格
	 */
	private String formattedPrice;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 订单是否加锁
	 */
	private Integer isLock = 0;

	/**
	 * 预付款
	 */
	private Double prepayAmt;
	/**
	 * 尾款
	 */
	private Double restAmt;

	/**
	 * 送达时间
	 */
	private String arrivedTime;
	/**
	 * 配送方式0自提 1平台配送，2商家配送'
	 */
	private String distributeMode;
	/**
	 * 商铺名称
	 */
	private String shopName;

	/**
	 * 物流公司ID
	 */
	private Integer companyId;

	/**
	 * 买家id
	 */
	private Integer buyerId;

	/**
	 * 卖家id
	 */
	private Integer sellerId;
	/**
	 * 订单创建时间
	 */
	private String createTime;

	private Double totalPayAmt;//'总共支付(含佣金)',

	public Double getTotalPayAmt() {
		return totalPayAmt;
	}

	public void setTotalPayAmt(Double totalPayAmt) {
		this.totalPayAmt = totalPayAmt;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Double getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public String getDistributeMode() {
		return distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}

	public Double getRestAmt() {
		return restAmt;
	}

	public void setRestAmt(Double restAmt) {
		this.restAmt = restAmt;
	}

	public String getArrivedTime() {
		return arrivedTime;
	}

	public void setArrivedTime(String arrivedTime) {
		this.arrivedTime = arrivedTime;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}
}
