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

    *客户id，member_baseinfo 表的id
    */
    private Integer memberId;

    public Integer getGrdUserId(){

    }
    public void setGrdUserId(Integer grdUserId){

    }
    @Column(name = "memberId")
    public Integer getMemberId(){

    }
    public void setMemberId(Integer memberId){

    }
}