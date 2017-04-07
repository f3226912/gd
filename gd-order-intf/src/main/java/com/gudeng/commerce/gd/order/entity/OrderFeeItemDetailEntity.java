package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "order_fee_item_detail")
public class OrderFeeItemDetailEntity  implements java.io.Serializable{

	private static final long serialVersionUID = 7057693600807863971L;

	/**
    *
    */
    private Integer id;

    /**
    *订单号
    */
    private Long orderNo;

    /**
    *费用类型: 1佣金 2补贴
    */
    private String feeType;

    /**
    *付方类型: nsy农商友、nps农批商、platfm平台
    */
    private String payerType;

    /**
    *收方类型: nsy农商友、nps农批商、platfm平台、market市场
    */
    private String payeeType;

    /**
    *金额
    */
    private Double amount;

    /**
    *费用说明
    */
    private String description;

    /**
    *创建时间
    */
    private Date createTime;

    /**
    *
    */
    private String createUser;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "orderNo")
    public Long getOrderNo(){

        return this.orderNo;
    }
    public void setOrderNo(Long orderNo){

        this.orderNo = orderNo;
    }
    @Column(name = "feeType")
    public String getFeeType(){

        return this.feeType;
    }
    public void setFeeType(String feeType){

        this.feeType = feeType;
    }
    @Column(name = "payerType")
    public String getPayerType(){

        return this.payerType;
    }
    public void setPayerType(String payerType){

        this.payerType = payerType;
    }
    @Column(name = "payeeType")
    public String getPayeeType(){

        return this.payeeType;
    }
    public void setPayeeType(String payeeType){

        this.payeeType = payeeType;
    }
    @Column(name = "amount")
    public Double getAmount(){

        return this.amount;
    }
    public void setAmount(Double amount){

        this.amount = amount;
    }
    @Column(name = "description")
    public String getDescription(){

        return this.description;
    }
    public void setDescription(String description){

        this.description = description;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "createUser")
    public String getCreateUser(){

        return this.createUser;
    }
    public void setCreateUser(String createUser){

        this.createUser = createUser;
    }
}
