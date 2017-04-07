package com.gudeng.commerce.gd.promotion.dto;

import java.util.List;

import com.gudeng.commerce.gd.promotion.annotation.ExcelConf;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 * 
 */                                    
public class GdActActivityDTO extends GdActActivityEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2233543595592222369L;
	
	/**
	 * 买家列表
	 */
	private List<GdActActivityUserRuleDTO> buyerList ;
	/**
	 * 卖家列表
	 */
	private List<GdActActivityUserRuleDTO> sellerList ;
	
	/**
	 * 物流配送模式列表
	 */
	private List<GdActActivityDistributionModeDTO> modeList ;
	
	/**
	 * 活动规则
	 */
	private List<GdActActivityCommDTO> rules;
	
	private String isExistEndTime;
	
	private String isExisStartTime;
	
	private Integer user_type;
	
	private Double maxAmount ;
	private Double minAmount ;
	private Double cost ;
	private Integer transportType;
	private Integer activityType;
	private String marketId ;
	private String createTimeString ;
	private Integer activity_rules_id;
	private Integer activity_participation_rule_id;
	
	/**
	 * 是否只是市场活动 1是 0否
	 */
	private Integer isMarketAct;
	/**
	 * 是否限制买家 1是 0否
	 */
	private Integer isLimitBuyerAct;
	
	public Integer getActivity_participation_rule_id() {
		return activity_participation_rule_id;
	}
	public void setActivity_participation_rule_id(Integer activity_participation_rule_id) {
		this.activity_participation_rule_id = activity_participation_rule_id;
	}
	public Integer getActivity_rules_id() {
		return activity_rules_id;
	}
	public void setActivity_rules_id(Integer activity_rules_id) {
		this.activity_rules_id = activity_rules_id;
	}
	@ExcelConf(excelHeader="所属市场", sort=5)
	private String marketName ;
	
	@ExcelConf(excelHeader="活动开始时间", sort=6)
	private String startTimeString ;
	
	@ExcelConf(excelHeader="活动结束时间", sort=7)
	private String endTimeString ;
	
	public List<GdActActivityDistributionModeDTO> getModeList() {
		return modeList;
	}
	public void setModeList(List<GdActActivityDistributionModeDTO> modeList) {
		this.modeList = modeList;
	}
	public Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getCreateTimeString() {
		return createTimeString;
	}
	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public String getStartTimeString() {
		return startTimeString;
	}
	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}
	public String getEndTimeString() {
		return endTimeString;
	}
	public void setEndTimeString(String endTimeString) {
		this.endTimeString = endTimeString;
	}
	public Double getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	public Double getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Integer getTransportType() {
		return transportType;
	}
	public void setTransportType(Integer transportType) {
		this.transportType = transportType;
	}
	public List<GdActActivityUserRuleDTO> getBuyerList() {
		return buyerList;
	}
	public void setBuyerList(List<GdActActivityUserRuleDTO> buyerList) {
		this.buyerList = buyerList;
	}
	public List<GdActActivityUserRuleDTO> getSellerList() {
		return sellerList;
	}
	public void setSellerList(List<GdActActivityUserRuleDTO> sellerList) {
		this.sellerList = sellerList;
	}
	public List<GdActActivityCommDTO> getRules() {
		return rules;
	}
	public void setRules(List<GdActActivityCommDTO> rules) {
		this.rules = rules;
	}

	public Integer getIsLimitBuyerAct() {
		return isLimitBuyerAct;
	}
	public void setIsLimitBuyerAct(Integer isLimitBuyerAct) {
		this.isLimitBuyerAct = isLimitBuyerAct;
	}
	public Integer getIsMarketAct() {
		return isMarketAct;
	}
	public void setIsMarketAct(Integer isMarketAct) {
		this.isMarketAct = isMarketAct;
	}

	public String getIsExisStartTime() {
		return isExisStartTime;
	}
	public void setIsExisStartTime(String isExisStartTime) {
		this.isExisStartTime = isExisStartTime;
	}
	public String getIsExistEndTime() {
		return isExistEndTime;
	}
	public void setIsExistEndTime(String isExistEndTime) {
		this.isExistEndTime = isExistEndTime;
	}
	public Integer getUser_type() {
		return user_type;
	}
	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}
	
}