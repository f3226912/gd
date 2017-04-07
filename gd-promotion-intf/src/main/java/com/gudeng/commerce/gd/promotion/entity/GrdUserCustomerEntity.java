package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "grd_user_customer")
public class GrdUserCustomerEntity  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2506006114157787925L;
    /**
    *地推用户id
    */
    private Integer grdUserId;
    /**
    *客户id，member_baseinfo 表的id
    */
    private Integer memberId;
    @Column(name = "grdUserId")
    public Integer getGrdUserId(){
        return this.grdUserId;
    }
    public void setGrdUserId(Integer grdUserId){
        this.grdUserId = grdUserId;
    }
    @Column(name = "memberId")
    public Integer getMemberId(){
        return this.memberId;
    }
    public void setMemberId(Integer memberId){
        this.memberId = memberId;
    }
}
