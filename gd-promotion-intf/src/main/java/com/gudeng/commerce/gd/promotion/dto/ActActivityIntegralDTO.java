package com.gudeng.commerce.gd.promotion.dto;

public class ActActivityIntegralDTO  implements java.io.Serializable{
   
	private Long activityId; //活动id
	private Long userId; //用户Id
	private double integralRate; //积分倍率
    private String ruleJson;//规则json
    //本次是10 
    private String commRuleType; //规则类型 佣金规则类型 1平台买家佣金 2平台卖家佣金 3市场买家佣金 4市\r\n\r\n场卖家佣金 5刷卡补贴,6买家预付款，7买家给平台违约金，8买家给卖家违约金，9买家给物流公司违约金，10现场采销
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public double getIntegralRate() {
		return integralRate;
	}
	public void setIntegralRate(double integralRate) {
		this.integralRate = integralRate;
	}
	public String getRuleJson() {
		return ruleJson;
	}
	public void setRuleJson(String ruleJson) {
		this.ruleJson = ruleJson;
	}
	public String getCommRuleType() {
		return commRuleType;
	}
	public void setCommRuleType(String commRuleType) {
		this.commRuleType = commRuleType;
	}
	
    
}
