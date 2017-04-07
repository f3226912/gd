package com.gudeng.commerce.gd.api.dto;

import java.util.List;

public class CommonFeeAppDTO {

	/** 费用类型 1:促销费用 */
	private Integer feeType;
	
	/** 买家费用 */
	private Double buyerFee;

	/** 卖家费用 */
	private Double sellerFee;
	
	/** 费用规则列表 */
	private List<?> feeRuleList;
	
	/** 成交金额 */
	private Double tradeAmount;
	
	/** 买家费用是否已支付 */
	private boolean isBuyerFeePaid = false;
	
	/** 卖家费用是否已支付 */
	private boolean isSellerFeePaid = false;

	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}

	public Double getBuyerFee() {
		return buyerFee;
	}

	public void setBuyerFee(Double buyerFee) {
		this.buyerFee = buyerFee;
	}

	public Double getSellerFee() {
		return sellerFee;
	}

	public void setSellerFee(Double sellerFee) {
		this.sellerFee = sellerFee;
	}

	public List<?> getFeeRuleList() {
		return feeRuleList;
	}

	public void setFeeRuleList(List<?> feeRuleList) {
		this.feeRuleList = feeRuleList;
	}

	public Double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public boolean isBuyerFeePaid() {
		return isBuyerFeePaid;
	}

	public void setBuyerFeePaid(boolean isBuyerFeePaid) {
		this.isBuyerFeePaid = isBuyerFeePaid;
	}

	public boolean isSellerFeePaid() {
		return isSellerFeePaid;
	}

	public void setSellerFeePaid(boolean isSellerFeePaid) {
		this.isSellerFeePaid = isSellerFeePaid;
	}
}
