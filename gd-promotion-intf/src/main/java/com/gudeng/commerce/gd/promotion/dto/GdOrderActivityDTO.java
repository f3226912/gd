package com.gudeng.commerce.gd.promotion.dto;

/**
 * 订单活动信息实体类
 * @author TerryZhang
 */
public class GdOrderActivityDTO implements java.io.Serializable{

	private static final long serialVersionUID = 140676689317588192L;

	/** 活动id */
	private Integer actId;
	
	/** 活动类型（1刷卡补贴，2市场，3平台，4预付款违约金，5物流） */
	private Integer actType;

	/** 活动名称 */
	private String actName;
	
	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
	
	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("actId: " + getActId());
		sb.append(", actType: " + getActType());
		sb.append(", actName: " + getActName());
		return sb.toString();
	}
}
