package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "nst_order_comment")
public class NstOrderCommentEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String orderNo;

    private Integer type;

    private Long memberId;

    private String serviceEvaluate;

    private String evaluateType;

    private String comment;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "orderNo")
    public String getOrderNo(){

        return this.orderNo;
    }
    public void setOrderNo(String orderNo){

        this.orderNo = orderNo;
    }
    @Column(name = "type")
    public Integer getType(){

        return this.type;
    }
    public void setType(Integer type){

        this.type = type;
    }
    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
    }
    @Column(name = "serviceEvaluate")
    public String getServiceEvaluate(){

        return this.serviceEvaluate;
    }
    public void setServiceEvaluate(String serviceEvaluate){

        this.serviceEvaluate = serviceEvaluate;
    }
    @Column(name = "evaluateType")
    public String getEvaluateType(){

        return this.evaluateType;
    }
    public void setEvaluateType(String evaluateType){

        this.evaluateType = evaluateType;
    }
    @Column(name = "comment")
    public String getComment(){

        return this.comment;
    }
    public void setComment(String comment){

        this.comment = comment;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
}


