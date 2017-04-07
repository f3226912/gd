package com.gudeng.commerce.gd.customer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Description: TODO(账户贷款信息)
 * @author mpan
 * @date 2016年5月25日 上午10:00:44
 */
@Entity(name = "finance_credit")
public class FinanceCreditEntity implements Serializable {

	private static final long serialVersionUID = -5494840214656753091L;
	private Long id;
	private Long memberId; // 会员ID
	private String 	memberAccount;//用户帐号
	private String marketId;//所属市场
	private Double orderAmount;//订单总交易金额
	private String userStar;//用户星级
	private String creditQuotaRange; // 贷款额度区间
	private Date createTime; // 创建时间
	private String createUserId; // 创建人员ID
	private Date updateTime; // 更新时间
	private String updateUserId; // 更新人员ID
	
    private String marketName;//市场名称
    private String status;//用户状态  '状态（0未启用，1启用，其他状态待补）'
    private String realName;//真实姓名
    private String mobile; //用户手机号
    private String shopName;//商户名称
    private String address;//商户地址
    private String cateName;//主营分类

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "memberId")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name = "creditQuotaRange")
	public String getCreditQuotaRange() {
		return creditQuotaRange;
	}

	public void setCreditQuotaRange(String creditQuotaRange) {
		this.creditQuotaRange = creditQuotaRange;
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
	
	
	@Column(name = "memberAccount")
	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}
	@Column(name = "marketId")
	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	@Column(name = "orderAmount")
	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	@Column(name = "userStar")
	public String getUserStar() {
		return userStar;
	}

	public void setUserStar(String userStar) {
		this.userStar = userStar;
	}
	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	
	
}
