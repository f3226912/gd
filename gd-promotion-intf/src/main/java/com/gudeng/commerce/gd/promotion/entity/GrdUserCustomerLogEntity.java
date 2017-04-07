package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
@Entity(name = "grd_user_customer_log")
public class GrdUserCustomerLogEntity  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2506006114157787917L;
    /**
    *原地推用户id
    */
    private Integer grdOldUserId;

    *现地推用户id
    */
    private Integer grdNewUserId;

    *客户id，member_baseinfo 表的id
    */
    private Integer memberId;

    *记录产生时间，即修改绑定关系的时间
    */
    private Date createTime;
    
    /**
     *操作人ID:即后台用户ID
     */
     private String createUserId;

     /**
     *操作人姓名:后台用户姓名
     */
     private String createUserName;

     /**
     *操作人账号:后台用户账号
     */
     private String createUserCode;

    public Integer getGrdOldUserId(){

    }
    public void setGrdOldUserId(Integer grdOldUserId){

    }
    @Column(name = "grdNewUserId")
    public Integer getGrdNewUserId(){

    }
    public void setGrdNewUserId(Integer grdNewUserId){

    }
    @Column(name = "memberId")
    public Integer getMemberId(){

    }
    public void setMemberId(Integer memberId){

    }
    @Column(name = "createTime")
    public Date getCreateTime(){

    }
    public void setCreateTime(Date createTime){

    }
    
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "createUserName")
    public String getCreateUserName(){

        return this.createUserName;
    }
    public void setCreateUserName(String createUserName){

        this.createUserName = createUserName;
    }
    @Column(name = "createUserCode")
    public String getCreateUserCode(){

        return this.createUserCode;
    }
    public void setCreateUserCode(String createUserCode){

        this.createUserCode = createUserCode;
    }
}