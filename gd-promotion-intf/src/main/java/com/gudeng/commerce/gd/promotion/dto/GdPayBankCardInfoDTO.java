package com.gudeng.commerce.gd.promotion.dto;

import java.io.Serializable;

/**
 * 支付银行卡信息
 * 用于补贴
 * @author TerryZhang
 */
public class GdPayBankCardInfoDTO implements Serializable{

	private static final long serialVersionUID = -7848434987812350283L;

	/** 业务类型1 0:全部 1:本行 2:跨行*/
	private Integer busiType1;

	/** 业务类型2 0:全部  1:同城 2:异地 */
	private Integer busiType2;

	/** 卡类型0:全部 1:贷记卡  2:借记卡 */
	private Integer cardType;

	/** 支付渠道 */
	private String payChannel;
	
	/** 买家已补贴金额 */
	private Double buyerSubsidyAmt = 0D;
	
	/** 卖家已补贴金额 */
	private Double sellerSubsidyAmt = 0D;
	
	/** 银行刷卡手续费实际发生额 */
	private Double tradingFee = 0D;

	public Integer getBusiType1() {
		return busiType1;
	}

	public void setBusiType1(Integer busiType1) {
		this.busiType1 = busiType1;
	}

	public Integer getBusiType2() {
		return busiType2;
	}

	public void setBusiType2(Integer busiType2) {
		this.busiType2 = busiType2;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	
	public Double getBuyerSubsidyAmt() {
		return buyerSubsidyAmt;
	}

	public void setBuyerSubsidyAmt(Double buyerSubsidyAmt) {
		this.buyerSubsidyAmt = buyerSubsidyAmt;
	}
	
	public Double getSellerSubsidyAmt() {
		return sellerSubsidyAmt;
	}

	public void setSellerSubsidyAmt(Double sellerSubsidyAmt) {
		this.sellerSubsidyAmt = sellerSubsidyAmt;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("busiType1: " + getBusiType1());
		sb.append(", busiType2: " + getBusiType2());
		sb.append(", cardType: " + getCardType());
		sb.append(", payChannel: " + getPayChannel());
		sb.append(", buyerSubsidyAmt: " + getBuyerSubsidyAmt());
		sb.append(", sellerSubsidyAmt: " + getSellerSubsidyAmt());
		sb.append(", tradingFee: " + getTradingFee());
		return sb.toString();
	}

	public Double getTradingFee() {
		return tradingFee;
	}

	public void setTradingFee(Double tradingFee) {
		this.tradingFee = tradingFee;
	}
}
