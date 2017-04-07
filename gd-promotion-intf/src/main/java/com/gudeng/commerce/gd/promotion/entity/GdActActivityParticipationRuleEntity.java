package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "gd_act_activity_participation_rule")
public class GdActActivityParticipationRuleEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7552772307786570584L;

	/**
    *主键id
    */
    private Integer id;

    *活动ID
    */
    private Integer activity_id;

    *订单金额最小
    */
    private Double min_cost;

    *订单金额最小
    */
    private Double max_cost;

    @Column(name = "id")
    public Integer getId(){

    }
    public void setId(Integer id){

    }
    @Column(name = "activity_id")
    public Integer getActivity_id(){

    }
    public void setActivity_id(Integer activity_id){

    }
    @Column(name = "min_cost")
    public Double getMin_cost(){

    }
    public void setMin_cost(Double min_cost){

    }
    @Column(name = "max_cost")
    public Double getMax_cost(){

    }
    public void setMax_cost(Double max_cost){

    }
}