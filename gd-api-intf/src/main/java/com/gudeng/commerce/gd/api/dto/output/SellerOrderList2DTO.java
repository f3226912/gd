package com.gudeng.commerce.gd.api.dto.output;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gudeng.commerce.gd.api.Constant.Order;

/**
 * 卖家订单列表DTO
 * @author TerryZhang
 *
 */
public class SellerOrderList2DTO {

	/**
	 * 订单号
	 */
	private Long orderNo;

	/**
	 * 订单创建时间
	 */
	private String createTime;
	
	/**
	 * 产品信息数组
	 */
	private List<OrderProductDTO> productDetails;
	
	/**
	 * 买家id
	 */
	private Long memberId;
	
	/**
	 * 买主名
	 */
	private String buyerName;
	
	/**
	 * 产品个数
	 */
	private Integer productNum;
	
	/**
	 * 订单总额
	 */
	private Double orderAmount;
	
	/**
	 * 订单实收金额
	 */
	private String payAmount;
	
	/**
	 * 格式化后价格
	 */
	private String formattedPrice;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 是否有客户: 0否 1是
	 */
	private Integer hasCustomer = 1;
	/**
	 * 配送方式 0自提 1平台配送2 商家配送
	 */
	private String distributeMode;

	/**
	 * 物流公司ID
	 */
	private Integer companyId;


	private String arriveTime;//送达时间

	private Double totalPayAmt;//'总共支付(含佣金)',
	
	private String activityType;//活动类型1无活动2现场采销
	
	private Integer activityIntegral;//活动获取积分
	

	public Double getTotalPayAmt() {
		return totalPayAmt;
	}

	public void setTotalPayAmt(Double totalPayAmt) {
		this.totalPayAmt = totalPayAmt;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getDistributeMode() {
		return distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = StringUtils.isBlank(buyerName) ? "农商友用户": buyerName;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = Order.STATUS_FINISH.equals(orderStatus)? Order.STATUS_PAID : orderStatus;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getHasCustomer() {
		return hasCustomer;
	}

	public void setHasCustomer(Integer hasCustomer) {
		this.hasCustomer = hasCustomer;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
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
