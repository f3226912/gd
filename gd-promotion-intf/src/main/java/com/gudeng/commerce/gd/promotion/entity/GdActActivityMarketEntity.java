package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "gd_act_activity_market")
public class GdActActivityMarketEntity  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6787773551593444875L;

	/**
    *主键id
    */
    private Integer id;
    /**
    *活动ID
    */
    private Integer activity_id;
    /**
    *市场ID
    */
    private Integer market_id;
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
    @Column(name = "market_id")
    public Integer getMarket_id(){
        return this.market_id;
    }
    public void setMarket_id(Integer market_id){
        this.market_id = market_id;
    }
}
