package com.gudeng.commerce.gd.promotion.dto;

/**
 * 谷登活动信息汇总对象
 * @author TerryZhang
 */
public class GdActivityRedisDTO implements java.io.Serializable{

	private static final long serialVersionUID = -2302650799106496078L;

	/** 补贴活动信息 */
	private GdActivityInfoRedisDTO subsidyInfo;

	/** 市场佣金活动信息 */
	private GdActivityInfoRedisDTO marketCommsnInfo;

	/** 平台佣金活动信息 */
	private GdActivityInfoRedisDTO platformCommsnInfo;

	/** 预付款和违约金活动信息 */
	private GdActivityInfoRedisDTO prepayAmtAndPenaltyInfo;
	
	/** 物流活动信息 */
	private GdActivityInfoRedisDTO deliveryInfo;

	public GdActivityInfoRedisDTO getSubsidyInfo() {
		return subsidyInfo;
	}

	public void setSubsidyInfo(GdActivityInfoRedisDTO subsidyInfo) {
		this.subsidyInfo = subsidyInfo;
	}

	public GdActivityInfoRedisDTO getMarketCommsnInfo() {
		return marketCommsnInfo;
	}

	public void setMarketCommsnInfo(GdActivityInfoRedisDTO marketCommsnInfo) {
		this.marketCommsnInfo = marketCommsnInfo;
	}

	public GdActivityInfoRedisDTO getPlatformCommsnInfo() {
		return platformCommsnInfo;
	}

	public void setPlatformCommsnInfo(GdActivityInfoRedisDTO platformCommsnInfo) {
		this.platformCommsnInfo = platformCommsnInfo;
	}

	public GdActivityInfoRedisDTO getPrepayAmtAndPenaltyInfo() {
		return prepayAmtAndPenaltyInfo;
	}

	public void setPrepayAmtAndPenaltyInfo(
			GdActivityInfoRedisDTO prepayAmtAndPenaltyInfo) {
		this.prepayAmtAndPenaltyInfo = prepayAmtAndPenaltyInfo;
	}

	public GdActivityInfoRedisDTO getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(GdActivityInfoRedisDTO deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
}
