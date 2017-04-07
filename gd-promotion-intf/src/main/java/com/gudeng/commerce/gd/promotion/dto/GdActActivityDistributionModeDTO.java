package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GdActActivityDistributionModeEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GdActActivityDistributionModeDTO extends GdActActivityDistributionModeEntity {

	private static final long serialVersionUID = -332947770020898982L;
	
	private Integer transportType ;
	
	private String activityId;
	
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Integer getTransportType() {
		return transportType;
	}

	public void setTransportType(Integer transportType) {
		this.transportType = transportType;
	}
	
}