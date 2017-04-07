package com.gudeng.commerce.gd.api.dto.output;

/**
 * 采购订单基本信息DTO
 * @author TerryZhang
 */
public class PurchaseOrderBaseDTO {

	/**
	 * 订单号
	 */
	private Long orderNo;

	/**
	 * 订单创建时间
	 */
	private String createTime;
	
	/**
	 * 买家id
	 */
	private Integer buyerId;
	
	/**
	 * 买主名
	 */
	private String buyerName;
	
	/**
	 * 供应商商铺id
	 */
	private Long businessId;
	
	/**
	 * 供应商商铺名字
	 */
	private String shopName;
	
	/**
	 * 订单总额
	 */
	private String orderAmount;
	
	/**
	 * 产品个数
	 */
	private Integer productNum;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 订单状态词
	 */
	private String statusWords;

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getStatusWords() {
		return statusWords;
	}

	public void setStatusWords(String statusWords) {
		this.statusWords = statusWords;
	}
}
