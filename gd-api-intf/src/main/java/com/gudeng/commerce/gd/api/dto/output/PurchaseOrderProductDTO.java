package com.gudeng.commerce.gd.api.dto.output;

/**
 * 采购订单商品信息DTO
 * @author TerryZhang
 */
public class PurchaseOrderProductDTO {
	/** 订单号 */
	private Long orderNo;

    /** 商品id */
    private Integer productId;

    /** 商品名   */
    private String productName;

    /** 商品购买数量  */
    private String purQuantity;
    
    /**  商品单位*/
	private String unitName;

	/** 商品初始价格*/
    private String orgPrice;
    
    /** 商品促销价格 */
	private String promPrice;
    
    /** 商品总额*/
    private Double needToPayAmount;
    
    /** 格式化商品总额*/
    private String formatNeedToPayAmount;

    /** 商品图片地址*/
	private String imageUrl;
	
	/** 是否有活动 0否 1是 */
	private Integer hasAct;

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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

	public String getPurQuantity() {
		return purQuantity;
	}

	public void setPurQuantity(String purQuantity) {
		this.purQuantity = purQuantity;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getNeedToPayAmount() {
		return needToPayAmount;
	}

	public void setNeedToPayAmount(Double needToPayAmount) {
		this.needToPayAmount = needToPayAmount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getOrgPrice() {
		return orgPrice;
	}

	public void setOrgPrice(String orgPrice) {
		this.orgPrice = orgPrice;
	}

	public String getPromPrice() {
		return promPrice;
	}

	public void setPromPrice(String promPrice) {
		this.promPrice = promPrice;
	}

	public Integer getHasAct() {
		return hasAct;
	}

	public void setHasAct(Integer hasAct) {
		this.hasAct = hasAct;
	}

	public String getFormatNeedToPayAmount() {
		return formatNeedToPayAmount;
	}

	public void setFormatNeedToPayAmount(String formatNeedToPayAmount) {
		this.formatNeedToPayAmount = formatNeedToPayAmount;
	}
}
