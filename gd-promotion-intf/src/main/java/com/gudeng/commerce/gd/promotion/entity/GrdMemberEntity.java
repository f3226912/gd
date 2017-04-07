package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * GrdMember entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "grd_member")
public class GrdMemberEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String mobile;
	private Long marketId;
	private Long giftteamId;//所属地推团队
	private String market;
	private String context;
	private String password;
	private String loginStatus;
	private String status;
	private Date createTime;
	private Date updateTime;
	private String createUserId;
	private String updateUserId;
	private Integer needLogin;

	// Constructors

	/** default constructor */
	public GrdMemberEntity() {
	}

	// Property accessors
	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "giftteamId")
	public Long getGiftteamId() {
		return giftteamId;
	}

	public void setGiftteamId(Long giftteamId) {
		this.giftteamId = giftteamId;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "marketId")
	public Long getMarketId() {
		return this.marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}
	
	@Column(name = "market")
	public String getMarket() {
		return this.market;
	}
	
	public void setMarket(String market) {
		this.market = market;
	}

	@Column(name = "context")
	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Column(name = "password", length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "loginStatus", length = 2)
	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	@Column(name = "status", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createTime", length = 10)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updateTime", length = 10)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "createUserId", length = 32)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "updateUserId", length = 32)
	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	@Column(name = "needLogin")
	public Integer getNeedLogin() {
		return needLogin;
	}

	public void setNeedLogin(Integer needLogin) {
		this.needLogin = needLogin;
	}
	
}