
package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.gudeng.commerce.gd.order.enm.EOrderSource;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;

public class OrderBaseinfoDTO extends OrderBaseinfoEntity implements Serializable {

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

	private List<OrderProductDetailDTO> detailList;

	private List<AuditLogDTO> auditLogList;

	private PaySerialnumberDTO paySerialnumberDTO;

	private InvoiceInfoDTO invoiceInfoDTO;

	private List<PaySerialnumberDTO> payments; // 支付信息

	private OrderOutmarketInfoDTO outmarketInfo; // 出场信息

	private OrderDeliveryAddressDTO orderDelivery; // 订单收货信息

	private String sellAccount; // 卖家帐号

	private String sellMobile; // 买家手机

	private Map<String, MemberSubResultDTO> memberSubResultPool; // 会员补贴结果

	private Date arriveTime;// 订单送达时间
	/**
	 * 违约金
	 */
	private Double penalty;
	/**
	 * 退款
	 */
	private Double refundment;
	/**
	 * POS刷卡手续费
	 */
	private String posFee;
	/**
	 *
	 * */
	private String nstOrderNo;

	/**
	 * 物流公司ID
	 */
	private Integer companyId;

	private String address;//商铺地址
	
	private String oorStatus;//退款状态
	
	public String getOorStatus() {
		return oorStatus;
	}

	public void setOorStatus(String oorStatus) {
		this.oorStatus = oorStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getNstOrderNo() {
		return nstOrderNo;
	}

	public void setNstOrderNo(String nstOrderNo) {
		this.nstOrderNo = nstOrderNo;
	}

	public String getPosFee() {
		return posFee;
	}

	public void setPosFee(String posFee) {
		this.posFee = posFee;
	}

	public Double getRefundment() {
		return refundment;
	}

	public void setRefundment(Double refundment) {
		this.refundment = refundment;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Double getBuyerPlatformCommision() {
		return buyerPlatformCommision;
	}

	public void setBuyerPlatformCommision(Double buyerPlatformCommision) {
		this.buyerPlatformCommision = buyerPlatformCommision;
	}

	public Double getBuyerMarketCommision() {
		return buyerMarketCommision;
	}

	public void setBuyerMarketCommision(Double buyerMarketCommision) {
		this.buyerMarketCommision = buyerMarketCommision;
	}

	public Double getBuyerLogisticsCommision() {
		return buyerLogisticsCommision;
	}

	public void setBuyerLogisticsCommision(Double buyerLogisticsCommision) {
		this.buyerLogisticsCommision = buyerLogisticsCommision;
	}

	public PaySerialnumberDTO getPaySerialnumberAllipay() {
		return paySerialnumberAllipay;
	}

	public void setPaySerialnumberAllipay(PaySerialnumberDTO paySerialnumberAllipay) {
		this.paySerialnumberAllipay = paySerialnumberAllipay;
	}

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

	private String stateComment; // 状态词

	private Double totalAmt; // 总额(包含手续费)

	private Double prepayAmt; // 预付款

	private Date prepayTime; // 预付款时间

	private Double buyerPoundage; // 买家手续费

	private Double sellerPoundage; // 卖家手续费

	private String marketName; // 市场名称

	private String bMarketName;// 买家市场名称 农批商采购显示市场名称为买家（农批商市场）

	private Integer isFirst; // 是否首单 1:首单 0:不是首单，2：现场采销

	private Date finishedTime; // 完成时间 支付时间

	private String marketRealName;

	private String serviceName;

	private String invoice;

	private String invoiceContent;

	private String thirdStatementId;

	private String sellMemberName;// 卖家名称

	private Double sellerCommision;// 卖家佣金

	private Double buyerCommision;// 买家佣金

	private PaySerialnumberDTO paySerialnumberPosFinal;// 尾款

	private PaySerialnumberDTO paySerialnumberAllipay; // 支付宝支付预付款流水记录

	private PaySerialnumberDTO paySerialnumberPosAll; // POS直接刷卡

	// 买家费用明细
	private Double buyerPlatformCommision;// 买家平台佣金
	private Double buyerMarketCommision;// 买家市场佣金
	private Double buyerLogisticsCommision;// 买家物流公司平台佣金
	private Double buyerPenaltyToSeller;// 违约金（卖家）
	private Double buyerPenaltyToWLCompany;// 违约金（物流公司）
	private Double buyerPenaltyToPlatform;// 违约金（平台）
	private Double refundAmt;// 买家预付款退款额
	private String refundReason;// 退款理由
	private String refundNo;// 退款編號
	// 卖家费用明细
	private Double sellerPlatformCommision;// 卖家平台佣金
	private Double sellerMarketCommision;// 卖家市场佣金
	private Double sellerPenaltyToSeller;// 违约金（卖家）
	private Double sellerPosCardCommision;// 刷卡手续费
	private Double sellerPosThirdCommision;// 第三方付款手续费
	private Double sellerPosSubsidyCommision;// 刷卡补贴

	private OrderRefundRecordDTO refundRecord;// 退款记录

	private RefundResponseLogResult refundLog;// 退款日志

	private String memberAddressId;// 货源Id

	private String deliverTimeStr; //发货时间

	private String sellRealName;//卖家真实姓名
	
	private String closeTimeStr;//关闭时间
	
	private String activityMessage;//订单活动信息，多个用逗号隔开
	
	private String statusWord;//订单状态词

	public PaySerialnumberDTO getPaySerialnumberPosFinal() {
		return paySerialnumberPosFinal;
	}

	public void setPaySerialnumberPosFinal(PaySerialnumberDTO paySerialnumberPosFinal) {
		this.paySerialnumberPosFinal = paySerialnumberPosFinal;
	}

	public PaySerialnumberDTO getPaySerialnumberPosAll() {
		return paySerialnumberPosAll;
	}

	public void setPaySerialnumberPosAll(PaySerialnumberDTO paySerialnumberPosAll) {
		this.paySerialnumberPosAll = paySerialnumberPosAll;
	}

	public Double getBuyerPenaltyToSeller() {
		return buyerPenaltyToSeller;
	}

	public void setBuyerPenaltyToSeller(Double buyerPenaltyToSeller) {
		this.buyerPenaltyToSeller = buyerPenaltyToSeller;
	}

	public Double getBuyerPenaltyToWLCompany() {
		return buyerPenaltyToWLCompany;
	}

	public void setBuyerPenaltyToWLCompany(Double buyerPenaltyToWLCompany) {
		this.buyerPenaltyToWLCompany = buyerPenaltyToWLCompany;
	}

	public Double getBuyerPenaltyToPlatform() {
		return buyerPenaltyToPlatform;
	}

	public void setBuyerPenaltyToPlatform(Double buyerPenaltyToPlatform) {
		this.buyerPenaltyToPlatform = buyerPenaltyToPlatform;
	}

	public Double getSellerPlatformCommision() {
		return sellerPlatformCommision;
	}

	public void setSellerPlatformCommision(Double sellerPlatformCommision) {
		this.sellerPlatformCommision = sellerPlatformCommision;
	}

	public Double getSellerMarketCommision() {
		return sellerMarketCommision;
	}

	public void setSellerMarketCommision(Double sellerMarketCommision) {
		this.sellerMarketCommision = sellerMarketCommision;
	}

	public Double getSellerPenaltyToSeller() {
		return sellerPenaltyToSeller;
	}

	public void setSellerPenaltyToSeller(Double sellerPenaltyToSeller) {
		this.sellerPenaltyToSeller = sellerPenaltyToSeller;
	}

	public Double getSellerPosCardCommision() {
		return sellerPosCardCommision;
	}

	public void setSellerPosCardCommision(Double sellerPosCardCommision) {
		this.sellerPosCardCommision = sellerPosCardCommision;
	}

	public Double getSellerPosThirdCommision() {
		return sellerPosThirdCommision;
	}

	public void setSellerPosThirdCommision(Double sellerPosThirdCommision) {
		this.sellerPosThirdCommision = sellerPosThirdCommision;
	}

	public Double getSellerPosSubsidyCommision() {
		return sellerPosSubsidyCommision;
	}

	public void setSellerPosSubsidyCommision(Double sellerPosSubsidyCommision) {
		this.sellerPosSubsidyCommision = sellerPosSubsidyCommision;
	}

	public OrderRefundRecordDTO getRefundRecord() {
		return refundRecord;
	}

	public void setRefundRecord(OrderRefundRecordDTO refundRecord) {
		this.refundRecord = refundRecord;
	}

	public String getMemberAddressId() {
		return memberAddressId;
	}

	public void setMemberAddressId(String memberAddressId) {
		this.memberAddressId = memberAddressId;
	}

	public Map<String, MemberSubResultDTO> getMemberSubResultPool() {
		return memberSubResultPool;
	}

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
		 * 修改状态词 <option value="">---请选择---</option> <option
		 * value="1">待付款</option> <option value="3">已付款</option> <option
		 * value="11">待发货</option> <option value="12">待收货</option> <option
		 * value="8">已关闭</option>
		 */
		if ("1".equals(this.getOrderStatus())) {
			return "等待买家付款";
		} else if ("3".equals(this.getOrderStatus())) {
			if ("0".equals(this.getPromType())) {
				return "买家已付款";
			} else if ("1".equals(this.getPromType())) {
				return "买家已付款收货";
			}
		} else if ("12".equals(this.getOrderStatus())) {
			Calendar calendar = Calendar.getInstance();
			if (this.getDeliverTime() != null) {
				calendar.setTime(this.getDeliverTime());

				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 7);

				if (calendar.getTime().before(new Date())) {
					long diffDays = ((new Date()).getTime() - calendar.getTimeInMillis()) / (1000 * 60 * 60 * 24);
					return "收货已超时" + diffDays + "天";
				} else {
					return "等待买家付款收货";
				}
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
			if (this.getSellMemberId() != null && this.getSellMemberId().toString().equals(this.getCloseUserId())) {
				return "卖家已取消";
			} else if (this.getMemberId() != null && this.getMemberId().toString().equals(this.getCloseUserId())) {
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

	public List<OrderProductDetailDTO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderProductDetailDTO> detailList) {
		this.detailList = detailList;
	}

	public List<AuditLogDTO> getAuditLogList() {
		return auditLogList;
	}

	public void setAuditLogList(List<AuditLogDTO> auditLogList) {
		this.auditLogList = auditLogList;
	}

	public List<PaySerialnumberDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<PaySerialnumberDTO> payments) {
		this.payments = payments;
	}

	public PaySerialnumberDTO getPaySerialnumberDTO() {
		return paySerialnumberDTO;
	}

	public void setPaySerialnumberDTO(PaySerialnumberDTO paySerialnumberDTO) {
		this.paySerialnumberDTO = paySerialnumberDTO;
	}

	public OrderOutmarketInfoDTO getOutmarketInfo() {
		return outmarketInfo;
	}

	public void setOutmarketInfo(OrderOutmarketInfoDTO outmarketInfo) {
		this.outmarketInfo = outmarketInfo;
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

	public String getSubAuditDesc() {
		if (MapUtils.isEmpty(memberSubResultPool)) {
			return null;
		}
		StringBuilder desc = new StringBuilder();
		for (MemberSubResultDTO memberSubResult : memberSubResultPool.values()) {
			desc.append(memberSubResult.getAuditDesc());
			desc.append("，");
		}
		return desc.substring(0, desc.length() - 1);
	}

	public void setMemberSubResultPool(Map<String, MemberSubResultDTO> memberSubResultPool) {
		this.memberSubResultPool = memberSubResultPool;
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
	 * @return
	 * @Title: getReceAmount
	 * @Description: TODO(应付金额)
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

	public String getOrderStatementId() {
		if (CollectionUtils.isEmpty(payments)) {
			return null;
		}
		for (PaySerialnumberDTO payment : payments) {
			if (EPayType.OFFLINE_CARD.getCode().equals(payment.getPayType())) {
				return payment.getStatementId();
			}
		}
		return null;
	}

	/**
	 * 获取支付方式视图
	 */
	public String getPayTypeView() {
		return EPayType.getDescByCode(getPayType());
	}

	/**
	 * 获取订单状态视图
	 */
	public String getOrderStatusView() {
		/*
		 * 当是农批商采购订单时，已付款状态显示已完成
		 */
//		if ("2".equals(this.getOrderType())) {
//			if ("3".equals(this.getOrderStatus())) {
//				return "已完成";
//			}
//		}
		return EOrderStatus.getDescByCode(getOrderStatus());
	}

	/**
	 * 获取订单来源视图
	 */
	public String getOrderSourceView() {
		return EOrderSource.getDescByCode(getOrderSource());
	}

	/**
	 * 获取支付时间视图
	 */
	public Date getPayTime() {
		if (CollectionUtils.isNotEmpty(getPayments())) {
			return getPayments().get(0).getPayTime();
		}
		return null;
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

	public Double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public OrderDeliveryAddressDTO getOrderDelivery() {
		return orderDelivery;
	}

	public void setOrderDelivery(OrderDeliveryAddressDTO orderDelivery) {
		this.orderDelivery = orderDelivery;
	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, 21, 31);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);

		System.out.println(calendar.getTime());
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public InvoiceInfoDTO getInvoiceInfoDTO() {
		return invoiceInfoDTO;
	}

	public void setInvoiceInfoDTO(InvoiceInfoDTO invoiceInfoDTO) {
		this.invoiceInfoDTO = invoiceInfoDTO;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getThirdStatementId() {
		return thirdStatementId;
	}

	public void setThirdStatementId(String thirdStatementId) {
		this.thirdStatementId = thirdStatementId;
	}

	public String getSellMemberName() {
		return sellMemberName;
	}

	public void setSellMemberName(String sellMemberName) {
		this.sellMemberName = sellMemberName;
	}

	public Double getSellerCommision() {
		return sellerCommision;
	}

	public void setSellerCommision(Double sellerCommision) {
		this.sellerCommision = sellerCommision;
	}

	public Double getBuyerCommision() {
		return buyerCommision;
	}

	public void setBuyerCommision(Double buyerCommision) {
		this.buyerCommision = buyerCommision;
	}

	public String getSellMobile() {
		return sellMobile;
	}

	public void setSellMobile(String sellMobile) {
		this.sellMobile = sellMobile;
	}

	public Double getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(Double refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public RefundResponseLogResult getRefundLog() {
		return refundLog;
	}

	public void setRefundLog(RefundResponseLogResult refundLog) {
		this.refundLog = refundLog;
	}

	public String getDeliverTimeStr() {
		return deliverTimeStr;
	}

	public void setDeliverTimeStr(String deliverTimeStr) {
		this.deliverTimeStr = deliverTimeStr;
	}

	public String getSellRealName() {
		return sellRealName;
	}

	public void setSellRealName(String sellRealName) {
		this.sellRealName = sellRealName;
	}

	public String getCloseTimeStr() {
		return closeTimeStr;
	}

	public void setCloseTimeStr(String closeTimeStr) {
		this.closeTimeStr = closeTimeStr;
	}

	public String getActivityMessage() {
		return activityMessage;
	}

	public void setActivityMessage(String activityMessage) {
		this.activityMessage = activityMessage;
	}

	public String getStatusWord() {
		return statusWord;
	}

	public void setStatusWord(String statusWord) {
		this.statusWord = statusWord;
	}


}
