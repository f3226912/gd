package com.gudeng.commerce.gd.api.dto.input;

public class GdOrderActivityApiQueryDTO implements java.io.Serializable{

	private static final long serialVersionUID = -7156173526091912006L;
	
	/** 订单号 */
	private String orderNo;
	
	/** 订单金额 */
	private String orderAmount;
	
	/** 订单实付金额 */
	private String payAmount;
	
	/** 商铺id */
	private String businessId;

	/** 买家id */
	private String buyerId;

	/** 商品列表字符串(无商品则不用设置) 
	 * 商品id_购买量_单价_购买金额#_#商品id_购买量_单价_购买金额
	 * */
	private String productListStr;
	
	/** 业务类型1 0:全部   1:本行 2:跨行*/
	private String busiType1;

	/** 业务类型2 0:全部  1:同城 2:异地 */
	private String busiType2;

	/** 卡类型 0:全部 1:贷记卡  2:借记卡 */
	private String cardType;

	/** 支付渠道 */
	private String payChannel;
	
	/** 清算标志  1表示清算*/
	private String flag;
	
	/** 买家已补贴金额 */
	private String buyerSubsidyAmt;
	
	/** 卖家已补贴金额 */
	private String sellerSubsidyAmt;

	/** 银行刷卡手续费实际发生额 */
	private String tradingFee;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBusiType1() {
		return busiType1;
	}

	public void setBusiType1(String busiType1) {
		this.busiType1 = busiType1;
	}

	public String getBusiType2() {
		return busiType2;
	}

	public void setBusiType2(String busiType2) {
		this.busiType2 = busiType2;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getProductListStr() {
		return productListStr;
	}

	public void setProductListStr(String productListStr) {
		this.productListStr = productListStr;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBuyerSubsidyAmt() {
		return buyerSubsidyAmt;
	}

	public void setBuyerSubsidyAmt(String buyerSubsidyAmt) {
		this.buyerSubsidyAmt = buyerSubsidyAmt;
	}

	public String getSellerSubsidyAmt() {
		return sellerSubsidyAmt;
	}

	public void setSellerSubsidyAmt(String sellerSubsidyAmt) {
		this.sellerSubsidyAmt = sellerSubsidyAmt;
	}

	public String getTradingFee() {
		return tradingFee;
	}

	public void setTradingFee(String tradingFee) {
		this.tradingFee = tradingFee;
	}
}
