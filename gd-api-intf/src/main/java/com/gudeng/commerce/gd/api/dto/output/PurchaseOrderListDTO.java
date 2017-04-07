package com.gudeng.commerce.gd.api.dto.output;

import java.util.List;

/**
 * 采购订单列表信息DTO
 * @author TerryZhang
 *
 */
public class PurchaseOrderListDTO extends PurchaseOrderBaseDTO{
	/**
	 * 产品信息数组
	 */
	private List<PurchaseOrderProductDTO> productDetails;
	
	/**
	 * 是否有活动: 0否 1是
	 */
	private Integer hasAct = 0;
	
	/**
	 * 格式化后订单实付金额
	 */
	private String formattedPrice;

	public List<PurchaseOrderProductDTO> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<PurchaseOrderProductDTO> productDetails) {
		this.productDetails = productDetails;
	}

	public Integer getHasAct() {
		return hasAct;
	}

	public void setHasAct(Integer hasAct) {
		this.hasAct = hasAct;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}
}
