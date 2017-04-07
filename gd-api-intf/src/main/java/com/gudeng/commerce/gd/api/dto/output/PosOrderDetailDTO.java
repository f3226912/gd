package com.gudeng.commerce.gd.api.dto.output;

import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;

import java.util.List;

/**
 * Pos机订单详情DTO
 * @author TerryZhang
 */
public class PosOrderDetailDTO extends PosOrderListDTO {
	private DeliveryAddressDTO deliveryAddress;//收货地址

	private String message;//买家留言

	/**
	 * 买家是否有补贴: 0:否 1:是
	 */
	private String hasBuySubPay = "0";

	/**
	 * 卖家是否有补贴: 0:否 1:是 2:已清算
	 */
	private String hasSellSubPay = "0";

	/** 卖家是否有佣金标志 0否1是 */
	private String hasSellerCommsn = "0";

	/** 买家是否有佣金标志 0否1是 */
	private String hasBuyerCommsn = "0";

	/**
	 * 买家佣金 无则是 0
	 */
	private String buyerCommsn = "0";

	/**
	 * 卖家佣金  无则是 0
	 */
	private String sellerCommsn = "0";

	private String subAmount = "0";

	/**
	 * 预付款支付时间
	 */
	private String prepayTime;

	public String getPrepayTime() {
		return prepayTime;
	}

	public void setPrepayTime(String prepayTime) {
		this.prepayTime = prepayTime;
	}

	public String getSubAmount() {
		return subAmount;
	}

	public void setSubAmount(String subAmount) {
		this.subAmount = subAmount;
	}

	public String getHasBuySubPay() {
		return hasBuySubPay;
	}

	public void setHasBuySubPay(String hasBuySubPay) {
		this.hasBuySubPay = hasBuySubPay;
	}

	public String getHasSellSubPay() {
		return hasSellSubPay;
	}

	public void setHasSellSubPay(String hasSellSubPay) {
		this.hasSellSubPay = hasSellSubPay;
	}

	public String getHasSellerCommsn() {
		return hasSellerCommsn;
	}

	public void setHasSellerCommsn(String hasSellerCommsn) {
		this.hasSellerCommsn = hasSellerCommsn;
	}

	public String getHasBuyerCommsn() {
		return hasBuyerCommsn;
	}

	public void setHasBuyerCommsn(String hasBuyerCommsn) {
		this.hasBuyerCommsn = hasBuyerCommsn;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DeliveryAddressDTO getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(DeliveryAddressDTO deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * 产品详情
	 */
	private List<PosOrderProductDTO> productList;

	public List<PosOrderProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<PosOrderProductDTO> productList) {
		this.productList = productList;
	}
}
