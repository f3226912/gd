package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Date;

public class OrderBaseinfoSendnowDTO extends OrderBaseinfoDTO  implements Serializable {

	private static final long serialVersionUID = 1177396071487810267L;

	private Integer preSalProductDetailId;

    private Integer productId;

    private String productName;

    private Double purQuantity;
    /**
     * 单位
     */
    private String unit;
    
    private Integer grossWeight; //  商品毛重

    private Double price;

    private Double tradingPrice;

    private Double needToPayAmount;

    private Date payTime;
    
    private String createUserId;

    private Date updateTime;

    private String updateUserId;
    
    /**
     * 是否出货 0未出货 1出货中 2已出货
     */
    private Integer hasDelivered;
    
    private String deliveryLinkman;
    private String deliveryDetail;
    private String deliveryMobile;

	public Integer getPreSalProductDetailId() {
		return preSalProductDetailId;
	}

	public void setPreSalProductDetailId(Integer preSalProductDetailId) {
		this.preSalProductDetailId = preSalProductDetailId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPurQuantity() {
		return purQuantity;
	}

	public void setPurQuantity(Double purQuantity) {
		this.purQuantity = purQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTradingPrice() {
		return tradingPrice;
	}

	public void setTradingPrice(Double tradingPrice) {
		this.tradingPrice = tradingPrice;
	}

	public Double getNeedToPayAmount() {
		return needToPayAmount;
	}

	public void setNeedToPayAmount(Double needToPayAmount) {
		this.needToPayAmount = needToPayAmount;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getHasDelivered() {
		return hasDelivered;
	}

	public void setHasDelivered(Integer hasDelivered) {
		this.hasDelivered = hasDelivered;
	}

	public String getDeliveryLinkman() {
		return deliveryLinkman;
	}

	public void setDeliveryLinkman(String deliveryLinkman) {
		this.deliveryLinkman = deliveryLinkman;
	}

	public String getDeliveryDetail() {
		return deliveryDetail;
	}

	public void setDeliveryDetail(String deliveryDetail) {
		this.deliveryDetail = deliveryDetail;
	}

	public String getDeliveryMobile() {
		return deliveryMobile;
	}

	public void setDeliveryMobile(String deliveryMobile) {
		this.deliveryMobile = deliveryMobile;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

}
