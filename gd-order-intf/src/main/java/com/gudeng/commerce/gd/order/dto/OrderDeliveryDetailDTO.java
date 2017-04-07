package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderDeliveryDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**	主键id */
	private Long id;
	
	/**	发货物流id */
	private Long memberAddressId;
	
	/**	货物来源 1订单 2商品 */
	private Integer type;
	
	/**	订单号 */
	private Long orderNo;
	
	/**	商铺名字 */
	private String shopName;
	
	/**	创建时间 */
	private Date createTime;
	
	private List<ProductDeliveryDetailDTO> productList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberAddressId() {
		return memberAddressId;
	}

	public void setMemberAddressId(Long memberAddressId) {
		this.memberAddressId = memberAddressId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<ProductDeliveryDetailDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDeliveryDetailDTO> productList) {
		this.productList = productList;
	}

}
