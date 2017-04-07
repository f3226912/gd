package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 产品出货详细表
 * @author TerryZhang
 */
@Entity(name = "product_delivery_detail")
public class ProductDeliveryDetailEntity implements java.io.Serializable{

	private static final long serialVersionUID = 554954985048561296L;

	/**	主键id */
	private Long id;
	
	/**	发货物流id */
	private Long memberAddressId;
	
	
	/** 同城发货物流id */
	private Long same_memberAddressId;
	
	/**	货物来源 1订单 2商品 */
	private Integer type;
	
	/**	订单号 */
	private Long orderNo;
	
	/**	卖家id */
	private Long sellerId;
	
	/**	商铺id */
	private Long businessId;
	
	/**	商铺名字 */
	private String shopName;
	
	/**	买家id */
	private Long buyerId;
	
	/**	买家姓名 */
	private String buyerName;
	
	/**	商品id */
	private Long productId;
	
	/**	商品名字 */
	private String productName;
	
	/**	商品单价 */
	private Double price;
	
	/**	采购数量 */
	private Double purQuantity;
	
	/**	商品小计 */
	private Double subTotal;
	
	/**	商品图片地址 */
	private String imageUrl;
	
	/**	商品单位 */
	private String unit;
	
	/**	创建时间 */
	private Date createTime;

	private String createUserId;

	/**	更新时间 */
	private Date updateTime;

	private String updateUserId;

	@Id
    @Column(name = "id")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	

	

	@Column(name = "memberAddressId")
	public Long getMemberAddressId() {
		return memberAddressId;
	}

	public void setMemberAddressId(Long memberAddressId) {
		this.memberAddressId = memberAddressId;
	}
	
	@Column(name = "same_memberAddressId")
	public Long getSame_memberAddressId() {
		return same_memberAddressId;
	}

	public void setSame_memberAddressId(Long same_memberAddressId) {
		this.same_memberAddressId = same_memberAddressId;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "orderNo")
	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "sellerId")
	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	@Column(name = "shopName")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Column(name = "buyerId")
	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	@Column(name = "buyerName")
	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	@Column(name = "productId")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name = "productName")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "price")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "purQuantity")
	public Double getPurQuantity() {
		return purQuantity;
	}

	public void setPurQuantity(Double purQuantity) {
		this.purQuantity = purQuantity;
	}

	@Column(name = "subTotal")
	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	@Column(name = "imageUrl")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "unit")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "businessId")
	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
}
