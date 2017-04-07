package com.gudeng.commerce.gd.order.dto;

import java.util.Date;

/**
 * @Description 会员补贴统计DTO
 * @Project gd-order-intf
 * @ClassName MemberSubsidyDTO.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月9日 上午10:25:06
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public class MemberSubsidyDTO implements java.io.Serializable {
	private static final long serialVersionUID = 8802416438226720076L;
	/* 会员ID */
	private Long memberId;
	/* 会员账号 */
	private String account;
	/* 真实姓名 */
	private String realName;
	// 手机
	private String mobile;
	// 注册时间
	private Date createTime;
	// 标示位,注册来源, 会员类别（1谷登农批，2农速通，3农商友，4.产地供应商，5.门岗， 其余待补）
	private Integer level;
	// 所属市场,从用户表找到用户所属商铺，再查询商铺市场表得到市场ID
	private Long marketId;
	// 系统驳回次数,sub_audit
	private Integer rejectCount; // 补贴状态: 1-待补贴; 2-系统驳回; 3-已补贴; 4-不予补贴
	// 补贴累计金额sub_audit
	private Double subAmount; // 补贴金额
	// 采购订单总数，根据memberId统计订单个数
	private Integer orderCount;
	// 采购产品总数,根据订单号统计订单详情个数
	private Integer productCount;
	// 交易总额，根据memberId统计订单表中交易额总和
	private Double orderAmount;
	// 已抵扣金额,根据memberId统计订单表中折扣总和
	private Double discountAmount;
	// 已提现金额,cash_request,状态 0待提现 1已结款 status;
	private Double cashAmount;
	// 钱包现有余额,acc_info
	private Double balAvailable;

	private String levelStr;
	private String marketIdStr;

	public String getLevelStr() {
		if (level == null) {
			levelStr = "";
		} else if (level == 1) {
			levelStr = "谷登农批";
		} else if (level == 2) {
			levelStr = "农速通";
		} else if (level == 3) {
			levelStr = "农商友";
		} else if (level == 4) {
			levelStr = "产地供应商";
		} else if (level == 5) {
			levelStr = "门岗";
		}
		return levelStr;
	}

	public String getMarketIdStr() {
		if (marketId == null) {
			marketIdStr = "";
		} else if (marketId == 1) {
			marketIdStr = "武汉白沙洲批发市场";
		} else if (marketId == 2) {
			marketIdStr = "广西玉林批发市场";
		}
		return marketIdStr;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public Integer getRejectCount() {
		return rejectCount;
	}

	public void setRejectCount(Integer rejectCount) {
		this.rejectCount = rejectCount;
	}

	public Double getSubAmount() {
		return subAmount;
	}

	public void setSubAmount(Double subAmount) {
		this.subAmount = subAmount;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public Double getBalAvailable() {
		return balAvailable;
	}

	public void setBalAvailable(Double balAvailable) {
		this.balAvailable = balAvailable;
	}
}
