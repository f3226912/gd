package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "gd_act_activity_rules")
public class GdActActivityRulesEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
    *主键id
    */
    private Integer id;
    /**
    *活动ID
    */
    private Integer activity_id;
    /**
    *用户类型（1农商友 2农批商 3物流公司）
    */
    private Integer user_type;
    /**
    *费用类型（1预付款 2佣金）
    */
    private Integer expense_type;
    /**
    *计费方式（1按比例 2按订单）
    */
    private Integer billing_method;
    /**
    *计费方式（1按比例 2按订单）
    */
    private Double cost;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "activity_id")
    public Integer getActivity_id(){
        return this.activity_id;
    }
    public void setActivity_id(Integer activity_id){
        this.activity_id = activity_id;
    }
    @Column(name = "user_type")
    public Integer getUser_type(){
        return this.user_type;
    }
    public void setUser_type(Integer user_type){
        this.user_type = user_type;
    }
    @Column(name = "expense_type")
    public Integer getExpense_type(){
        return this.expense_type;
    }
    public void setExpense_type(Integer expense_type){
        this.expense_type = expense_type;
    }
    @Column(name = "billing_method")
    public Integer getBilling_method(){
        return this.billing_method;
    }
    public void setBilling_method(Integer billing_method){
        this.billing_method = billing_method;
    }
    @Column(name = "cost")
    public Double getCost(){
        return this.cost;
    }
    public void setCost(Double cost){
        this.cost = cost;
    }
}
