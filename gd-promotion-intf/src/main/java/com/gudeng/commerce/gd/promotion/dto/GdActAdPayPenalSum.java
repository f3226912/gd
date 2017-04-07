package com.gudeng.commerce.gd.promotion.dto;

import java.io.Serializable;

/**
 * 6+1采销活动预付款-违约金保存数据参数
 * @author gdeng
 *
 */
public class GdActAdPayPenalSum implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5964639344057229097L;

	/**
	 * 活动基本信息
	 */
	private String actBasicInfo;
	
	/**
	 * 商品用户信息
	 */
	private String goodsUserRule;
	
	/**
	 * 买家预付款规则
	 */
	private String advancePaymentRule;
	
	/**
	 * 给平台的违约金规则
	 */
	private String penalSumPlateformRule;
	
	/**
	 * 给卖家的违约金规则
	 */
	private String penalSumSellerRule;
	
	/**
	 * 给物流公司的违约金规则
	 */
	private String penalSumLogisticsRule;

	public String getActBasicInfo() {
		return actBasicInfo;
	}

	public void setActBasicInfo(String actBasicInfo) {
		this.actBasicInfo = actBasicInfo;
	}

	public String getGoodsUserRule() {
		return goodsUserRule;
	}

	public void setGoodsUserRule(String goodsUserRule) {
		this.goodsUserRule = goodsUserRule;
	}

	public String getAdvancePaymentRule() {
		return advancePaymentRule;
	}

	public void setAdvancePaymentRule(String advancePaymentRule) {
		this.advancePaymentRule = advancePaymentRule;
	}

	public String getPenalSumPlateformRule() {
		return penalSumPlateformRule;
	}

	public void setPenalSumPlateformRule(String penalSumPlateformRule) {
		this.penalSumPlateformRule = penalSumPlateformRule;
	}

	public String getPenalSumSellerRule() {
		return penalSumSellerRule;
	}

	public void setPenalSumSellerRule(String penalSumSellerRule) {
		this.penalSumSellerRule = penalSumSellerRule;
	}

	public String getPenalSumLogisticsRule() {
		return penalSumLogisticsRule;
	}

	public void setPenalSumLogisticsRule(String penalSumLogisticsRule) {
		this.penalSumLogisticsRule = penalSumLogisticsRule;
	}
}
