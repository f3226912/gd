package com.gudeng.commerce.gd.supplier.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class ProductPriceEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2079416812689892004L;

	private Long id;
	
	private Long productId;
	
	private Double buyCountStart;
	
	private Double buyCountEnd;
	
	private Double price;
	
    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "productId")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name = "buyCountStart")
	public Double getBuyCountStart() {
		return buyCountStart;
	}

	public void setBuyCountStart(Double buyCountStart) {
		this.buyCountStart = buyCountStart;
	}

	@Column(name = "buyCountEnd")
	public Double getBuyCountEnd() {
		return buyCountEnd;
	}

	public void setBuyCountEnd(Double buyCountEnd) {
		this.buyCountEnd = buyCountEnd;
	}

	@Column(name = "price")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    
}
