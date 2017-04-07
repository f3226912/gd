package com.gudeng.commerce.gd.api.dto;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gudeng.commerce.gd.api.dto.output.OrderProductDTO;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;

/**
 * 订单详细信息dto
 *
 * @author TerryZhang
 */
public class OrderDetailAppDTO {

	private Long orderNo;

	private Double orderAmount;

	private Double discountAmount;

	private String subAmount = "0";

	private Double payAmount;

	private Double tradeAmount;

	private String payType;

	private String tradeType;

	private String orderStatus;

	private Integer memberId;

	private String createTime;

	private String closeTime;

	private String tradeTime;

	private String shopName;

	private Integer businessId;

	private String mobile;

	private String buyerMobile;

	private Integer marketId;

	/**
	 * 买家是否有补贴: 0:否 1:是
	 */
	private String hasBuySubPay = "0";

	/**
	 * 卖家是否有补贴: 0:否 1:是 2:已清算
	 */
	private String hasSellSubPay = "0";

	/**
	 * 卖家是否有佣金标志 0否1是
	 */
	private String hasSellerCommsn = "0";

	/**
	 * 买家是否有佣金标志 0否1是
	 */
	private String hasBuyerCommsn = "0";

	/**
	 * 买家佣金 无则是 0
	 */
	private String buyerCommsn = "0";

	/**
	 * 卖家佣金  无则是 0
	 */
	private String sellerCommsn = "0";

	/**
	 * 支付流水号
	 */
	private String paySerialNo;

	/**
	 * 二维码
	 */
	private String qrCode;

	/**
	 * 用户余额
	 */
	private Double balAvailable;

	/**
	 * 是否设置有支付密码  1 则有。0则没有
	 */
	private Integer hasPwd;

	/**
	 * 审核状态:0待审核 1审核通过 2审核驳回
	 */
	private String auditStatus;

	/**
	 * 审核原因
	 */
	private String auditDesc;

	/**
	 * 出货状态: 0未出货 1出货中 2已出货 3部分出货
	 */
	private Integer status;

	private List<OrderProductDTO> productDetails;

	private String buyerName;

	/**
	 * 是否有客户: 0否 1是
	 */
	private Integer hasCustomer = 1;

	private DeliveryAddressDTO deliveryAddress;//收货地址

	private String message;//买家留言

	private String distributeMode;//配送方式

	/**
	 * 关闭原因
	 */
	private String closeReason;
	/**
	 * 佣金
	 */
	private Double commission;
	/**
	 * 预付款
	 */
	private Double prepayAmt;
	/**
	 * 尾款
	 */
	private Double restAmt;
	/**
	 * 违约金
	 */
	private Double penalty;

	/**
	 * 退款
	 */
	private Double refundment;

	/**
	 * 物流运单
	 */
	private String nstOrderNo;

	/**
	 * 发货时间
	 */
	private String deliverTime;

	/**
	 * 预付款支付时间
	 */
	private String prepayTime;
	/**
	 * 尾款支付时间
	 */
	private String restpayTime;
	/**
	 * 预付款支付状态
	 */
	private String prepayStatus;
	/**
	 * 尾款支付时间
	 */
	private String restpayStatus;

	private String arriveTime;//送达时间

	private String statusWord;//状态词

	private String nstStatusWord;//物流状态词
	
	private String activityType;//活动类型1无活动2现场采销
	
	private Integer activityIntegral;//活动获取积分

	public String getStatusWord() {
		return statusWord;
	}

	public void setStatusWord(String statusWord) {
		this.statusWord = statusWord;
	}

	public String getNstStatusWord() {
		return nstStatusWord;
	}

	public void setNstStatusWord(String nstStatusWord) {
		this.nstStatusWord = nstStatusWord;
	}

	/**
	 * 关闭原因对应的状态码
	 * <p>
	 * BUYER_CANCLE("0","我已取消"), SELLER_CANCLE("1","卖家已取消"), PAY_TIMEOUT("2",
	 * "付款超时"), LOGISTICS_FAILURE("3", "分配物流失败"), PREPAY_TIMEOUT("4",
	 * "预付款支付超时"), INSPECTION_FAILURE("5", "验货不通过"), REFUND("6", "退预付款");
	 */
	private String closKey;

	/**
	 * 物流公司ID
	 */
	private Integer companyId;

	private String address;//商铺地址
	/**
	 * pos机刷卡手续费
	 */
	private Double posFee;

	private OrderObjectResult logisticaldetail;//订单详情

	public Double getPosFee() {
		return posFee;
	}

	public void setPosFee(Double posFee) {
		this.posFee = posFee;
	}

	public OrderObjectResult getLogisticaldetail() {
		return logisticaldetail;
	}

	public void setLogisticaldetail(OrderObjectResult logisticaldetail) {
		this.logisticaldetail = logisticaldetail;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getDeliverTime() {
		return deliverTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public String getClosKey() {
		return closKey;
	}

	public void setClosKey(String closKey) {
		this.closKey = closKey;
	}

	public String getNstOrderNo() {
		return nstOrderNo;
	}

	public void setNstOrderNo(String nstOrderNo) {
		this.nstOrderNo = nstOrderNo;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public Double getRefundment() {
		return refundment;
	}

	public void setRefundment(Double refundment) {
		this.refundment = refundment;
	}

	public String getPrepayTime() {
		return prepayTime;
	}

	public String getRestpayTime() {
		return restpayTime;
	}

	public String getPrepayStatus() {
		return prepayStatus;
	}

	public void setPrepayStatus(String prepayStatus) {
		this.prepayStatus = prepayStatus;
	}

	public String getRestpayStatus() {
		return restpayStatus;
	}

	public void setRestpayStatus(String restpayStatus) {
		this.restpayStatus = restpayStatus;
	}

	public void setPrepayTime(String prepayTime) {
		this.prepayTime = prepayTime;
	}

	public void setRestpayTime(String restpayTime) {
		this.restpayTime = restpayTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public Double getRestAmt() {
		return restAmt;
	}

	public void setRestAmt(Double restAmt) {
		this.restAmt = restAmt;
	}

	public Double getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getDistributeMode() {
		return distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = StringUtils.isBlank(distributeMode) ? "0" : distributeMode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DeliveryAddressDTO getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(DeliveryAddressDTO deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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

	public String getSubAmount() {
		return subAmount;
	}

	public void setSubAmount(String subAmount) {
		this.subAmount = subAmount;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public List<OrderProductDTO> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<OrderProductDTO> productDetails) {
		this.productDetails = productDetails;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPaySerialNo() {
		return paySerialNo;
	}

	public void setPaySerialNo(String paySerialNo) {
		this.paySerialNo = paySerialNo;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Double getBalAvailable() {
		return balAvailable;
	}

	public void setBalAvailable(Double balAvailable) {
		this.balAvailable = balAvailable;
	}

	public Integer getHasPwd() {
		return hasPwd;
	}

	public void setHasPwd(Integer hasPwd) {
		this.hasPwd = hasPwd;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getHasSellSubPay() {
		return hasSellSubPay;
	}

	public void setHasSellSubPay(String hasSellSubPay) {
		this.hasSellSubPay = hasSellSubPay;
	}

	public String getHasBuySubPay() {
		return hasBuySubPay;
	}

	public void setHasBuySubPay(String hasBuySubPay) {
		this.hasBuySubPay = hasBuySubPay;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = StringUtils.isBlank(buyerName) ? "农商友用户" : buyerName;
	}

	public Integer getHasCustomer() {
		return hasCustomer;
	}

	public void setHasCustomer(Integer hasCustomer) {
		this.hasCustomer = hasCustomer;
	}

	public String getBuyerCommsn() {
		return buyerCommsn;
	}

	public void setBuyerCommsn(String buyerCommsn) {
		this.buyerCommsn = buyerCommsn;
	}

	public String getSellerCommsn() {
		return sellerCommsn;
	}

	public void setSellerCommsn(String sellerCommsn) {
		this.sellerCommsn = sellerCommsn;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getHasSellerCommsn() {
		return hasSellerCommsn;
	}

	public void setHasSellerCommsn(String hasSellerCommsn) {
		this.hasSellerCommsn = hasSellerCommsn;
	}

	public String getHasBuyerCommsn() {
		return hasBuyerCommsn;
	}

	public void setHasBuyerCommsn(String hasBuyerCommsn) {
		this.hasBuyerCommsn = hasBuyerCommsn;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Integer getActivityIntegral() {
		return activityIntegral;
	}

	public void setActivityIntegral(Integer activityIntegral) {
		this.activityIntegral = activityIntegral;
	}
	
}
