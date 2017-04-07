package com.gudeng.commerce.gd.api.dto;

import java.util.Date;
import java.util.List;

import com.gudeng.commerce.gd.api.dto.output.OrderProductDTO;

/**
 * 订单列表信息dto
 * @author TerryZhang
 */
public class OrderAppReturnDTO {
	
	private Long orderNo;

	private String orderStatus;
	
	private Double orderAmount;
	
	private String formatOrderAmount;
	
	private Double buyerSubAmount;
	
	private Double sellerSubAmount;
	
	private Long memberId;

	private String shopName;

	private Long businessId;

	private Long marketId;
	
	/**
	 * 是否有补贴: 0:否 1:是
	 */
	private String hasSubPay;
	
	/**
	 * 补贴状态
	 */
	private String subStatus;
	
	/**
	 * 补贴原因
	 */
	private String subReason;
	
	/**
	 * 补贴日期
	 */
	private String subDate;
	
	private List<OrderProductDTO> productDetails;
	
	/**
	 * 审核状态:0待审核 1审核通过 2审核驳回
	 */
	private String auditStatus;
	
	/**
	 * 审核原因
	 */
	private String auditDesc;
	
	/**
	 * 产品名称列表
	 */
	private String productsName;
	
	/**
	 * 产品名称数组
	 */
	private String[] productNameArr;
	
	private String createTime;
	
	/**
	 * 关闭原因
	 */
	private String closeReason;
	/** 配送方式 */
	private String distributeMode;
	
	/** 尾款
	 * */
	private Double  restAmt;
	
	/** 农速通运单号
	 * */
	private String nstOrderNo;
	/**
	 * 送达时间
	 * */
	private Date arriveTime;
	/**
	 * 物流公司ID
	 */
	private Integer companyId;
	
	private Double prepayAmt; //预付款
	
	private Integer sellMemberId; // 卖家ID
	
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

	public Integer getSellMemberId() {
		return sellMemberId;
	}

	public void setSellMemberId(Integer sellMemberId) {
		this.sellMemberId = sellMemberId;
	}

	public Double getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getNstOrderNo() {
		return nstOrderNo;
	}

	public void setNstOrderNo(String nstOrderNo) {
		this.nstOrderNo = nstOrderNo;
	}

	public Double getRestAmt() {
		return restAmt;
	}

	public void setRestAmt(Double restAmt) {
		this.restAmt = restAmt;
	}

	public String getDistributeMode() {
		return distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}

	public String[] getProductNameArr() {
		return productNameArr;
	}

	public void setProductNameArr(String[] productNameArr) {
		this.productNameArr = productNameArr;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public List<OrderProductDTO> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<OrderProductDTO> productDetails) {
		this.productDetails = productDetails;
	}

	public String getHasSubPay() {
		return hasSubPay;
	}

	public void setHasSubPay(String hasSubPay) {
		this.hasSubPay = hasSubPay;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getSubReason() {
		return subReason;
	}

	public void setSubReason(String subReason) {
		this.subReason = subReason;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getSubDate() {
		return subDate;
	}

	public void setSubDate(String subDate) {
		this.subDate = subDate;
	}

	public Double getSellerSubAmount() {
		return sellerSubAmount;
	}

	public void setSellerSubAmount(Double sellerSubAmount) {
		this.sellerSubAmount = sellerSubAmount;
	}

	public Double getBuyerSubAmount() {
		return buyerSubAmount;
	}

	public void setBuyerSubAmount(Double buyerSubAmount) {
		this.buyerSubAmount = buyerSubAmount;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String getFormatOrderAmount() {
		return formatOrderAmount;
	}

	public void setFormatOrderAmount(String formatOrderAmount) {
		this.formatOrderAmount = formatOrderAmount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
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
