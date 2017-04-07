package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "cart_info")
public class CartInfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3485409349115725139L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	private Long memberId;

	/**
	 * 商铺ID
	 */
	private Long businessId;

	/**
	 * 商铺名称
	 */
	private String shopsName;

	/**
	 * 商品ID
	 */
	private Long productId;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 单价
	 */
	private Double price;

	/**
	 * 采购数量
	 */
	private Double purQuantity;

	/**
	 * 商品总价
	 */
	private Double tradingPrice;

	/**
	 * 状态（0售罄、-1已下架、正常1）
	 */
	private String state;

	/**
	 * 是否勾选
	 */
	private String selected;

	/**
	 * extend json
	 */
	private String commonJson;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createUserId;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 更新人
	 */
	private String updateUserId;

	@Id
	@Column(name = "id")
	public Long getId() {

		return this.id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	@Column(name = "memberId")
	public Long getMemberId() {

		return this.memberId;
	}

	public void setMemberId(Long memberId) {

		this.memberId = memberId;
	}

	@Column(name = "businessId")
	public Long getBusinessId() {

		return this.businessId;
	}

	public void setBusinessId(Long businessId) {

		this.businessId = businessId;
	}

	@Column(name = "shopsName")
	public String getShopsName() {

		return this.shopsName;
	}

	public void setShopsName(String shopsName) {

		this.shopsName = shopsName;
	}

	@Column(name = "productId")
	public Long getProductId() {

		return this.productId;
	}

	public void setProductId(Long productId) {

		this.productId = productId;
	}

	@Column(name = "productName")
	public String getProductName() {

		return this.productName;
	}

	public void setProductName(String productName) {

		this.productName = productName;
	}

	@Column(name = "price")
	public Double getPrice() {

		return this.price;
	}

	public void setPrice(Double price) {

		this.price = price;
	}

	@Column(name = "purQuantity")
	public Double getPurQuantity() {

		return this.purQuantity;
	}

	public void setPurQuantity(Double purQuantity) {

		this.purQuantity = purQuantity;
	}

	@Column(name = "tradingPrice")
	public Double getTradingPrice() {

		return this.tradingPrice;
	}

	public void setTradingPrice(Double tradingPrice) {

		this.tradingPrice = tradingPrice;
	}

	@Column(name = "state")
	public String getState() {

		return this.state;
	}

	public void setState(String state) {

		this.state = state;
	}

	@Column(name = "selected")
	public String getSelected() {

		return this.selected;
	}

	public void setSelected(String selected) {

		this.selected = selected;
	}

	@Column(name = "commonJson")
	public String getCommonJson() {

		return this.commonJson;
	}

	public void setCommonJson(String commonJson) {

		this.commonJson = commonJson;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}
}
