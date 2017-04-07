package com.gudeng.commerce.gd.api.params;

import java.util.Date;

public class CertifCorpParamsBean {
	
	private Integer id;

	/**
	 * 用户id
	 */
	private Integer memberId;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 合作社
	 */
	private String corpName;

	/**
	 * 邻近市场
	 */
	private String markets;

	/**
	 * 商铺名称
	 */
	private String shopName;

	/**
	 * 主营分类
	 */
	private Integer cateId;

	/**
	 * 店铺id( aim to 主营分类、所在地址 )
	 */
	private Integer businessId;

	/**
	 * 申请认证时间
	 */
	private Date commitTime;

	/**
	 * 供应量
	 */
	private Integer stockCount;

	/**
	 * 供应量单位
	 */
	private String units;

	/**
	 * 营业执照号码
	 */
	private String bzl;

	/**
	 * 营业执照照片
	 */
	private String bzlPhotoUrl;

	/**
	 * 状态(0:待审核1:已认证;2:已驳回)
	 */
	private String status;

	/**
	 * 省
	 */
	private Integer province;

	/**
	 * 市
	 */
	private Integer city;

	/**
	 * 县
	 */
	private Integer area;

	/**
	 * 地址
	 */
	private String address;

	/**
	*
	*/
	private String createUserId;

	/**
	*
	*/
	private Date createTime;

	/**
	*
	*/
	private String updateUserId;

	/**
	*
	*/
	private Date updateTime;

	/**
	 * 
	 */
	private String optionUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getMarkets() {
		return markets;
	}

	public void setMarkets(String markets) {
		this.markets = markets;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getBzl() {
		return bzl;
	}

	public void setBzl(String bzl) {
		this.bzl = bzl;
	}

	public String getBzlPhotoUrl() {
		return bzlPhotoUrl;
	}

	public void setBzlPhotoUrl(String bzlPhotoUrl) {
		this.bzlPhotoUrl = bzlPhotoUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOptionUser() {
		return optionUser;
	}

	public void setOptionUser(String optionUser) {
		this.optionUser = optionUser;
	}
	
	
}
