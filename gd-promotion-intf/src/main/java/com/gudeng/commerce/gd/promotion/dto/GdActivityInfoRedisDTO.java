package com.gudeng.commerce.gd.promotion.dto;

import java.util.List;
import java.util.Map;

/**
 * 谷登活动详细信息对象
 * @author TerryZhang
 */
public class GdActivityInfoRedisDTO implements java.io.Serializable{

	private static final long serialVersionUID = -2302650799106496078L;

	/** 买家活动信息对应map buyerId -> actId */
	private Map<Integer, List<Integer>> buyerActMap;
	
	/** 活动信息对应买家map actId -> buyerId */
	private Map<Integer, List<Integer>> actBuyerMap;

	/** 商铺活动信息对应map businessID -> actId */
	private Map<Integer, Integer> businessActMap;

	/** 商品活动信息对应map productId -> actId */
	private Map<Integer, Integer> productActMap;

	/** 活动规则信息对应map actId ->  ruleMap
	 * ruleMap取值: 
	 * 			市场佣金规则map
	 * 				buyerMarketCommsnRule -> 买家佣金规则对象GdActActivityCommDTO
	 * 				sellerMarketCommsnRule -> 卖家佣金规则对象GdActActivityCommDTO
	 *          平台佣金规则map
	 *          	buyerPlatformCommsnRule -> 买家平台佣金规则对象GdActActivityCommDTO
	 *          	sellerPlatformCommsnRule -> 卖家平台佣金规则对象GdActActivityCommDTO
	 *          补贴规则map
	 *          	buyerSubsidyRule -> 买家补贴规则对象List<GdActActivityCommDTO>
	 *          	sellerSubsidyRule -> 卖家补贴规则对象List<GdActActivityCommDTO>
	 *          违约金规则map
	 *          	prepayAmtRule -> 预付款规则对象GdActActivityCommDTO
	 *          	penaltyRule -> 违约金规则对象List<GdActActivityCommDTO>
	 *          物流规则map
	 *          	deliveryRule -> 支持配送方式对象List<GdActActivityDistributionModeDTO>
	*/
	private Map<Integer, Map<String, Object>> actRuleMap;
	
	/** 不限买家对应的活动map marketId -> List<actId> */
	private Map<Integer, List<Integer>> noLimitBuyerActIdMap;
	
	/** 不限卖家对应的活动map marketId -> actId */
	private Map<Integer, Integer> noLimitSellerActIdMap;
	
	/** 活动信息对应map actId -> act info dto */
	private Map<Integer, GdActActivityDTO> actInfoMap;

	public Map<Integer, List<Integer>> getBuyerActMap() {
		return buyerActMap;
	}

	public void setBuyerActMap(Map<Integer, List<Integer>> buyerActMap) {
		this.buyerActMap = buyerActMap;
	}

	public Map<Integer, Integer> getBusinessActMap() {
		return businessActMap;
	}

	public void setBusinessActMap(Map<Integer, Integer> businessActMap) {
		this.businessActMap = businessActMap;
	}

	public Map<Integer, Integer> getProductActMap() {
		return productActMap;
	}

	public void setProductActMap(Map<Integer, Integer> productActMap) {
		this.productActMap = productActMap;
	}

	public Map<Integer, Integer> getNoLimitSellerActIdMap() {
		return noLimitSellerActIdMap;
	}

	public void setNoLimitSellerActIdMap(Map<Integer, Integer> noLimitSellerActIdMap) {
		this.noLimitSellerActIdMap = noLimitSellerActIdMap;
	}

	public Map<Integer, List<Integer>> getNoLimitBuyerActIdMap() {
		return noLimitBuyerActIdMap;
	}

	public void setNoLimitBuyerActIdMap(Map<Integer, List<Integer>> noLimitBuyerActIdMap) {
		this.noLimitBuyerActIdMap = noLimitBuyerActIdMap;
	}

	public Map<Integer, GdActActivityDTO> getActInfoMap() {
		return actInfoMap;
	}

	public void setActInfoMap(Map<Integer, GdActActivityDTO> actInfoMap) {
		this.actInfoMap = actInfoMap;
	}

	public Map<Integer, Map<String, Object>> getActRuleMap() {
		return actRuleMap;
	}

	public void setActRuleMap(Map<Integer, Map<String, Object>> actRuleMap) {
		this.actRuleMap = actRuleMap;
	}

	public Map<Integer, List<Integer>> getActBuyerMap() {
		return actBuyerMap;
	}

	public void setActBuyerMap(Map<Integer, List<Integer>> actBuyerMap) {
		this.actBuyerMap = actBuyerMap;
	}
}
