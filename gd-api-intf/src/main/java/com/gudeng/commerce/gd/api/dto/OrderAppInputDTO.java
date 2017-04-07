package com.gudeng.commerce.gd.api.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;

/**
 * 订单信息dto
 * @author TerryZhang
 */
public class OrderAppInputDTO implements Serializable {
	
	private static final long serialVersionUID = -3123246963836731590L;

	/**
	 * 订单号
	 */
	private Long orderNo;
	
	/**
	 * 用户id
	 */
	private Long memberId;
	
	/**
	 * 订单来源 :1 卖家代客下单; 2买家下单
	 */
	private String orderSource;
	
	/**
	 * 渠道 1 android; 2 ios; 3 pc
	 */
	private String channel;
	
	/**
	 * 订单类型
	 */
	private String orderType;
	
	/**
	 * 支付方式： 1 pos刷卡; 2 现金; 3 余额交易; 
	 */
	private String payType;
	
	private String paySubType; // 支付子类型

	/**
	 * 商品总金额
	 */
	private Double orderAmount;

	/**
	 * 抵扣金额
	 */
	private Double discountAmount;

	/**
	 * 补贴状态  1已补贴 0未补贴
	 */
	private Integer subStatus; 
	
	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 应付金额
	 */
	private Double payAmount;  

	/**
	 * 产品订单信息列表,  json array字符串, 含有:
	 * productId: 产品id
	 * productName: 产品名
	 * quantity: 数量
	 * price: 单价
	 * tradePrice: 交易单价
	 * payAmount: 该产品总金额
	 */
	private String jProductDetails;
	
	/**
	 * 商铺id列表
	 */
	private Long businessId;
	
	/**
	 * 店铺名
	 */
	private String shopName;
	
	/**
	 * 市场id
	 */
	private Long marketId;
	
	/**
	 * 是否买家: 0为否, 1为是
	 */
	private Integer isBuyer;
	
	/**
	 * 二维码
	 */
	private String qcCode;
	
	/**
	 * 支付图片
	 */
	private String payImage;
	
	/**
	 * 交易密码
	 */
	private String tradePwd;
	
	/**
	 * 参考号, 即流水号
	 */
	private String statementId;
	
	/**
	 * 支付时间
	 */
	private Date payTime;
	
	/**
	 * 支付结果信息
	 */
	private String payInfo;
	
	/**
	 * 付款账号
	 */
	private String paymentAccount;
	
	private String posNumber; // POS终端号
	
	/**
	 * 操作类型 1取消订单 2确认发货
	 */
	private String opType;

	private List<OrderProductDetailEntity> detailList;
	
	private String jsonAddress;
	
	private String message;//买家留言;
	
	private String distributeMode; //配送方式
	
	//市场佣金, 默认0
	private String marketCommsn;
	
	private String version;
	/**
	 * 农速通货源id
	 */
	private String memberAddressId;
	/**
	 * 查询手机号
	 */
	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getMemberAddressId() {
		return memberAddressId;
	}

	public void setMemberAddressId(String memberAddressId) {
		this.memberAddressId = memberAddressId;
	}

	public String getDistributeMode() {
		return distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJsonAddress() {
		return jsonAddress;
	}

	public void setJsonAddress(String jsonAddress) {
		this.jsonAddress = jsonAddress;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public String getjProductDetails() {
		return jProductDetails;
	}

	public void setjProductDetails(String jProductDetails) {
		this.jProductDetails = jProductDetails;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getIsBuyer() {
		return isBuyer;
	}

	public void setIsBuyer(Integer isBuyer) {
		this.isBuyer = isBuyer;
	}

	public String getQcCode() {
		return qcCode;
	}

	public void setQcCode(String qcCode) {
		this.qcCode = qcCode;
	}

	public String getPayImage() {
		return payImage;
	}

	public void setPayImage(String payImage) {
		this.payImage = payImage;
	}

	public String getTradePwd() {
		return tradePwd;
	}

	public void setTradePwd(String tradePwd) {
		this.tradePwd = tradePwd;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPaySubType() {
		return paySubType;
	}

	public void setPaySubType(String paySubType) {
		this.paySubType = paySubType;
	}

	public Integer getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(Integer subStatus) {
		this.subStatus = subStatus;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public List<OrderProductDetailEntity> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderProductDetailEntity> detailList) {
		this.detailList = detailList;
	}

	public String getPosNumber() {
		return posNumber;
	}

	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getMarketCommsn() {
		return marketCommsn;
	}

	public void setMarketCommsn(String marketCommsn) {
		this.marketCommsn = marketCommsn;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
