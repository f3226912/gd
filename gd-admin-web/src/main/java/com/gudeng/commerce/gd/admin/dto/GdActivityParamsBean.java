package com.gudeng.commerce.gd.admin.dto;

public class GdActivityParamsBean {
	/**
	    *活动ID
	    */
	    private Integer activityId;

	    /**
	    *活动编号
	    */
	    private String activityCode;

	    /**
	    *活动名称
	    */
	    private String activityName;

	    /**
	    *活动类型（1:6+1促销）
	    */
	    private String activityType;

	    /**
	    *活动开始时间
	    */
	    private String startDate;

	    /**
	    *活动结束时间
	    */
	    private String endDate;

	    /**
	    *活动状态（1有效 0无效）
	    */
	    private String state;
	    
	    private String marketId ;

		public String getMarketId() {
			return marketId;
		}

		public void setMarketId(String marketId) {
			this.marketId = marketId;
		}

		public Integer getActivityId() {
			return activityId;
		}

		public void setActivityId(Integer activityId) {
			this.activityId = activityId;
		}

		public String getActivityCode() {
			return activityCode;
		}

		public void setActivityCode(String activityCode) {
			this.activityCode = activityCode;
		}

		public String getActivityName() {
			return activityName;
		}

		public void setActivityName(String activityName) {
			this.activityName = activityName;
		}

		public String getActivityType() {
			return activityType;
		}

		public void setActivityType(String activityType) {
			this.activityType = activityType;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	    
}
