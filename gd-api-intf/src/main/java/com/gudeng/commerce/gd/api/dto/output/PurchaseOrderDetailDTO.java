package com.gudeng.commerce.gd.api.dto.output;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;

/**
 * 采购订单详细信息dto
 * @author TerryZhang
 */
public class PurchaseOrderDetailDTO extends PurchaseOrderBaseDTO{
	/** 预付款金额 */
	private String prepayAmount;

	/** 买家活动手续费用 */
	private String buyerFee;
	
	/** 卖家活动手续费用 */
	private String sellerFee;
	
	/** 还需付款金额 */
	private String needPayAmount;
	
	/** 实际付款金额 */
	private String tradeAmount;
	
	/** 买家手机号 */
	private String buyerMobile;

	/** 发货时间 */
	private String deliverTime;

	/** 关闭时间 */
	private String closeTime;

	/** 完成时间 */
	private String tradeTime;

	/** 商铺店主电话 */
	private String sellerMobile;
	
	/** 卖家id */
	private Integer sellerId;

	/** 订单商品信息 */
	private List<PurchaseOrderProductDTO> productDetails;
	
	/** 订单活动信息 */
	private Object actDetail;
	
	private DeliveryAddressDTO deliveryAddress;//收货地址
	
	public DeliveryAddressDTO getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(DeliveryAddressDTO deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPrepayAmount() {
		return prepayAmount;
	}

	public void setPrepayAmount(String prepayAmount) {
		this.prepayAmount = prepayAmount;
	}

	public String getBuyerFee() {
		return buyerFee;
	}

	public void setBuyerFee(String buyerFee) {
		this.buyerFee = buyerFee;
	}

	public String getSellerFee() {
		return sellerFee;
	}

	public void setSellerFee(String sellerFee) {
		this.sellerFee = sellerFee;
	}

	public String getNeedPayAmount() {
		return needPayAmount;
	}

	public void setNeedPayAmount(String needPayAmount) {
		this.needPayAmount = needPayAmount;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getSellerMobile() {
		return sellerMobile;
	}

	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
	}

	public List<PurchaseOrderProductDTO> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<PurchaseOrderProductDTO> productDetails) {
		this.productDetails = productDetails;
	}

	public Object getActDetail() {
		return actDetail;
	}

	public void setActDetail(Object actDetail) {
		this.actDetail = actDetail;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
}
