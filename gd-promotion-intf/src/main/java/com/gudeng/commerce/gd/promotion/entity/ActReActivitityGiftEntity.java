package com.gudeng.commerce.gd.promotion.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "act_re_activitity_gift")
public class ActReActivitityGiftEntity  implements java.io.Serializable{
    /**
	 *
	 */
	private static final long serialVersionUID = -413203953121583704L;

	private Integer id;

    private Integer activityId;

    private Integer giftId;

    private Integer cost;

    private Integer exchangeScore;
    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "activity_id")
    public Integer getActivityId(){

        return this.activityId;
    }
    public void setActivityId(Integer activityId){

        this.activityId = activityId;
    }
    @Column(name = "gift_id")
    public Integer getGiftId(){

        return this.giftId;
    }
    public void setGiftId(Integer giftId){

        this.giftId = giftId;
    }
    @Column(name = "cost")
    public Integer getCost(){

        return this.cost;
    }
    public void setCost(Integer cost){

        this.cost = cost;
    }
    @Column(name = "exchange_score")
    public Integer getExchangeScore(){

        return this.exchangeScore;
    }
    public void setExchangeScore(Integer exchangeScore){

        this.exchangeScore = exchangeScore;
    }
}

