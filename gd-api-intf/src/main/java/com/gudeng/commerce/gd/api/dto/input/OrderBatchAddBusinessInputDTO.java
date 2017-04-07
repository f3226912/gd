package com.gudeng.commerce.gd.api.dto.input;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 批量增加订单商铺信息输入DTO
 * @author TerryZhang
 */
public class OrderBatchAddBusinessInputDTO implements Serializable{

	private static final long serialVersionUID = 8976171064162069702L;
	
	/** 收货方式 */
	private String businessId;
	
	/** 收货方式 */
	private String distributeMode;
	
	/** 订单重量 */
	private String orderWeight;

	/** 买家留言*/
	private String message;
	
	/** 买家佣金*/
	private String buyerCommsn;
	
	/** 卖家佣金*/
	private String sellerCommsn;
	
	/**
	 * 商品总金额
	 */
	private String orderAmount;

	/**
	 * 抵扣金额
	 */
	private String discountAmount;
	
	/**
	 * 应付金额
	 */
	private String payAmount;  
	
	/**
	 * 订单商品信息列表, 格式:
	 * 商品id_数量#_#商品id_数量
	 */
	private String productDetails;
	
	/**
	 * 预付款
	 */
	private String prepayAmt;  

	public String getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(String prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public String getDistributeMode() {
		return StringUtils.isBlank(distributeMode) ? "0" : distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}
	
	public String getOrderWeight() {
		return orderWeight;
	}

	public void setOrderWeight(String orderWeight) {
		this.orderWeight = orderWeight;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBuyerCommsn() {
		return buyerCommsn;
	}

	public void setBuyerCommsn(String buyerCommsn) {
		this.buyerCommsn = buyerCommsn;
	}

	public String getSellerCommsn() {
		return sellerCommsn;
	}

	public void setSellerCommsn(String sellerCommsn) {
		this.sellerCommsn = sellerCommsn;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
}
