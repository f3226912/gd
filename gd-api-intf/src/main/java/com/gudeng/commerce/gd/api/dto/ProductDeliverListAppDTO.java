package com.gudeng.commerce.gd.api.dto;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gudeng.commerce.gd.api.dto.output.OrderProductDTO;

public class ProductDeliverListAppDTO {
	/** 订单号 */
	private Long orderNo;
	
	/** 商铺名 */
	private String shopName;

	/** 商铺id */
	private Long businessId;
	
	/** 买家id */
	private Integer memberId;
	
	/** 买家名  */
	private String buyerName;
	
	/** 订单时间 */
	private String createTime;
	
	/** 订单商品详情 */
	private List<OrderProductDTO> productDetails;
	
	/** 出货状态: 0未出货 1出货中 2已出货 */
	private Integer status;

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = StringUtils.isBlank(buyerName) ? "农商友用户": buyerName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<OrderProductDTO> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<OrderProductDTO> productDetails) {
		this.productDetails = productDetails;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
