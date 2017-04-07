package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "push_adinfo")
public class PushAdInfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 广告渠道(1农批宝2谷登物流)
	 */
	private String adCanal;
	
	/**
	 * 广告类型(01轮播图02产品推送)
	 */
	private String adType;

	/**
	 * 广告轮播名称
	 */
	private String adName;
	
	/**
	 * 农批中心ID
	 */
	private String marketId;

	/**
	 * 推送产品分类id
	 */
	private Long categoryId;
	
	/**
	 * 状态
	 */
	private String state;

	/**
	 * 广告图片URL
	 */
	private String adUrl;

	/**
	 * 广告链接URL
	 */
	private String adLinkUrl;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 创建人员ID
	 */
	private String createUserId;
	
	/**
	 * 创建人员name
	 */
	private String createUserName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人员ID
	 */
	private String updateUserId;
	
	/**
	 * 修改人员ID
	 */
	private String updateUserName;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 产品id
	 */
	private Long productId;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 开始时间
	 */
	private Date startTime;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "adCanal")
	public String getAdCanal() {
		return adCanal;
	}

	public void setAdCanal(String adCanal) {
		this.adCanal = adCanal;
	}

	@Column(name = "adName")
	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "adUrl")
	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	@Column(name = "adLinkUrl")
	public String getAdLinkUrl() {
		return adLinkUrl;
	}

	public void setAdLinkUrl(String adLinkUrl) {
		this.adLinkUrl = adLinkUrl;
	}

	@Column(name = "endTime")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	@Column(name = "createUserName")
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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

	@Column(name = "updateUserName")
	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "productId")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "startTime")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "adType")
	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	@Column(name = "marketId")
	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	@Column(name = "categoryId")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
