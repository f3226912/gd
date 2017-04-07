
package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import com.gudeng.commerce.gd.customer.entity.OrderBaseinfoEntity;

public class OrderBaseinfoDTO extends OrderBaseinfoEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7763695589189009706L;

	private String memberIdStr;
	
	/**
	 * 商铺店主电话
	 */
	private String mobile;
	
	/**
	 * 买家电话
	 */
	private String buyerMobile;
	
	private String account;
	
	private String realName;
	
	private Double receAmount; // 应收金额
	
	private String sellAccount; //卖家帐号
	/**
	 * 库存数量
	 */
	private Double stockCount;
	
	/**
	 * 是否有补贴
	 */
	private Integer hasSub;
	
	/**
	 * 买家类型
	 */
	private Integer buyerType;
	
	/**
	 * 订单客户关联表中的客户姓名
	 */
	private String customerName;
	
	/**
	 * 关联客户的手机号
	 */
	private String customerMobile;
	
	/**
	 * 审核描述
	 */
	private String auditDesc;
	
	private String cateNames; // 经营范围
	
	private String otherCateNames; // 其它分类
	
	private String posNumber; // pos终端号
	
	private String paymentAcc; // 付款卡号
	
	private String statementId; // 流水号
	
	private String stateComment; //状态词
	
	private Double restAmt; //尾款
	
	private Double totalAmt; //总额(包含手续费)
	
	private Double prepayAmt; //预付款
	
	private Date prepayTime; //预付款时间
	
	private Double buyerPoundage; //买家手续费
	
	private Double sellerPoundage; //卖家手续费
	
	private String marketName; //市场名称
	
	private String bMarketName;//买家市场名称 农批商采购显示市场名称为买家（农批商市场）
	
	private Integer isFirst; //是否首单 1:首单 0:不是首单

	private Date finishedTime; //完成时间  支付时间
	
	private String marketRealName;
	
	public String getMarketRealName() {
		return marketRealName;
	}

	public void setMarketRealName(String marketRealName) {
		this.marketRealName = marketRealName;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public String getbMarketName() {
		return bMarketName;
	}

	public void setbMarketName(String bMarketName) {
		this.bMarketName = bMarketName;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Date getPrepayTime() {
		return prepayTime;
	}

	public void setPrepayTime(Date prepayTime) {
		this.prepayTime = prepayTime;
	}

	public Double getBuyerPoundage() {
		return buyerPoundage;
	}

	public void setBuyerPoundage(Double buyerPoundage) {
		this.buyerPoundage = buyerPoundage;
	}

	public Double getSellerPoundage() {
		return sellerPoundage;
	}

	public void setSellerPoundage(Double sellerPoundage) {
		this.sellerPoundage = sellerPoundage;
	}

	public String getStateComment() {
		/*
		 * 修改状态词
		 * <option value="">---请选择---</option>
			<option value="1">待付款</option>
			<option value="3">已付款</option>
			<option value="11">待发货</option>
			<option value="12">待收货</option>
			<option value="8">已关闭</option>
		 */
		if ("1".equals(this.getOrderStatus())) {
			return "等待买家付款";
		} else if ("3".equals(this.getOrderStatus())) {
			if("0".equals(this.getPromType())) {
				return "买家已付款";
			} else if("1".equals(this.getPromType())) {
				return "买家已付款收货";
			}
		} else if ("12".equals(this.getOrderStatus())) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.getDeliverTime());
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 7);

			if (calendar.getTime().before(new Date())) {
				long diffDays = ((new Date()).getTime() - calendar.getTimeInMillis()) / (1000 * 60 * 60 * 24);
				return "收货已超时" + diffDays + "天";
			} else {
				return "等待买家付款收货";
			}
		} else if ("11".equals(this.getOrderStatus())) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.getOrderTime());
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 7);

			if (calendar.getTime().before(new Date())) {
				long diffDays = ((new Date()).getTime() - calendar.getTimeInMillis()) / (1000 * 60 * 60 * 24);
				return "发货已超时" + diffDays + "天";
			} else {
				return "等待卖家发货";
			}
		} else if ("8".equals(this.getOrderStatus())) {
			if(this.getSellMemberId()!=null&&this.getSellMemberId().toString().equals(this.getCloseUserId())) {
				return "卖家已取消";
			} else if(this.getMemberId()!=null&&this.getMemberId().toString().equals(this.getCloseUserId())) {
				return "买家已取消";
			}
			return "管理后台已取消";
		} else if ("9".equals(this.getOrderStatus())) {
			return "订单已超时";
		}
		
		return stateComment;
	}

	public void setStateComment(String stateComment) {
		this.stateComment = stateComment;
	}

	public Double getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public String getMemberIdStr() {
		return memberIdStr;
	}

	public void setMemberIdStr(String memberIdStr) {
		this.memberIdStr = memberIdStr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String getSellAccount() {
		return sellAccount;
	}

	public void setSellAccount(String sellAccount) {
		this.sellAccount = sellAccount;
	}

	public Double getStockCount() {
		return stockCount;
	}

	public void setStockCount(Double stockCount) {
		this.stockCount = stockCount;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public Integer getHasSub() {
		return hasSub;
	}

	public void setHasSub(Integer hasSub) {
		this.hasSub = hasSub;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String getCateNames() {
		return cateNames;
	}

	public void setCateNames(String cateNames) {
		this.cateNames = cateNames;
	}

	public String getOtherCateNames() {
		return otherCateNames;
	}

	public void setOtherCateNames(String otherCateNames) {
		this.otherCateNames = otherCateNames;
	}

	public String getPosNumber() {
		return posNumber;
	}

	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}

	public String getPaymentAcc() {
		return paymentAcc;
	}

	public void setPaymentAcc(String paymentAcc) {
		this.paymentAcc = paymentAcc;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	/**
	 * @Title: getReceAmount
	 * @Description: (应付金额)
	 * @return
	 */
	public Double getReceAmount() {
		if (receAmount != null) {
			return receAmount;
		}
		if (getDiscountAmount() == null) {
			return getOrderAmount();
		}
		receAmount = getOrderAmount() - getDiscountAmount();
		return receAmount;
	}

	public void setReceAmount(Double receAmount) {
		this.receAmount = receAmount;
	}

	public Integer getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(Integer buyerType) {
		this.buyerType = buyerType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public Double getRestAmt() {
		return restAmt;
	}

	public void setRestAmt(Double restAmt) {
		this.restAmt = restAmt;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, 21, 31);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		
		System.out.println(calendar.getTime());
	}
	
}
