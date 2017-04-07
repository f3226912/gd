package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "gd_act_activity_user_rule")
public class GdActActivityUserRuleEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4186036903528003174L;

	/**
    *主键id
    */
    private Integer id;
    /**
    *活动ID
    */
    private Integer activityId;
    /**
    *用户类型（1农商友 2商铺）
    */
    private Integer userType;
    /**
    *关联id（用户id/商铺id）
    */
    private Long referId;
    
    /**
     * 备注 类目级别(0:一级 1:二级 2:三级)
     */
    private String remark;
    
    private Double integralRate;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "activity_id")
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	@Column(name = "user_type")
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	@Column(name = "refer_id")
	public Long getReferId() {
		return referId;
	}
	public void setReferId(Long referId) {
		this.referId = referId;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "integralRate")
	public Double getIntegralRate() {
		return integralRate;
	}
	public void setIntegralRate(Double integralRate) {
		this.integralRate = integralRate;
	}
	

    
}
