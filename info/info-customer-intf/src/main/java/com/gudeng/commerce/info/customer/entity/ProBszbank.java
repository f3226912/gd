package com.gudeng.commerce.info.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "pro_bszbank")
public class ProBszbank  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2899222456231346694L;

	private Long ID;

    private Long reportsID;

    private String frequency;

    private Date datatimes;

    private Double transactionAmount;

    private Long orderNumbers;

    private Double orderAmount;

    private Integer transactionUsers;

    private Integer newUsers;

    private Integer oldUsers;

    private Double newUserAmount;

    private Double oldUserAmount;

    private Double newUserAvgUnitPrice;

    private Double oldUserAvgUnitPrice;

    private Double userTradeAvg;

    private Double usersAmount;

    private String status;

    private String createUserID;

    private Date createTime;

    private String updateUserID;

    private Date updateTime;

    @Column(name = "ID")
    public Long getID(){

        return this.ID;
    }
    public void setID(Long ID){

        this.ID = ID;
    }
    @Column(name = "reportsID")
    public Long getReportsID(){

        return this.reportsID;
    }
    public void setReportsID(Long reportsID){

        this.reportsID = reportsID;
    }
    @Column(name = "frequency")
    public String getFrequency(){

        return this.frequency;
    }
    public void setFrequency(String frequency){

        this.frequency = frequency;
    }
    @Column(name = "datatimes")
    public Date getDatatimes(){

        return this.datatimes;
    }
    public void setDatatimes(Date datatimes){

        this.datatimes = datatimes;
    }
    @Column(name = "transactionAmount")
    public Double getTransactionAmount(){

        return this.transactionAmount;
    }
    public void setTransactionAmount(Double transactionAmount){

        this.transactionAmount = transactionAmount;
    }
    @Column(name = "orderNumbers")
    public Long getOrderNumbers(){

        return this.orderNumbers;
    }
    public void setOrderNumbers(Long orderNumbers){

        this.orderNumbers = orderNumbers;
    }
    @Column(name = "orderAmount")
    public Double getOrderAmount(){

        return this.orderAmount;
    }
    public void setOrderAmount(Double orderAmount){

        this.orderAmount = orderAmount;
    }
    @Column(name = "transactionUsers")
    public Integer getTransactionUsers(){

        return this.transactionUsers;
    }
    public void setTransactionUsers(Integer transactionUsers){

        this.transactionUsers = transactionUsers;
    }
    @Column(name = "newUsers")
    public Integer getNewUsers(){

        return this.newUsers;
    }
    public void setNewUsers(Integer newUsers){

        this.newUsers = newUsers;
    }
    @Column(name = "oldUsers")
    public Integer getOldUsers(){

        return this.oldUsers;
    }
    public void setOldUsers(Integer oldUsers){

        this.oldUsers = oldUsers;
    }
    @Column(name = "newUserAmount")
    public Double getNewUserAmount(){

        return this.newUserAmount;
    }
    public void setNewUserAmount(Double newUserAmount){

        this.newUserAmount = newUserAmount;
    }
    @Column(name = "oldUserAmount")
    public Double getOldUserAmount(){

        return this.oldUserAmount;
    }
    public void setOldUserAmount(Double oldUserAmount){

        this.oldUserAmount = oldUserAmount;
    }
    @Column(name = "newUserAvgUnitPrice")
    public Double getNewUserAvgUnitPrice(){

        return this.newUserAvgUnitPrice;
    }
    public void setNewUserAvgUnitPrice(Double newUserAvgUnitPrice){

        this.newUserAvgUnitPrice = newUserAvgUnitPrice;
    }
    @Column(name = "oldUserAvgUnitPrice")
    public Double getOldUserAvgUnitPrice(){

        return this.oldUserAvgUnitPrice;
    }
    public void setOldUserAvgUnitPrice(Double oldUserAvgUnitPrice){

        this.oldUserAvgUnitPrice = oldUserAvgUnitPrice;
    }
    @Column(name = "userTradeAvg")
    public Double getUserTradeAvg(){

        return this.userTradeAvg;
    }
    public void setUserTradeAvg(Double userTradeAvg){

        this.userTradeAvg = userTradeAvg;
    }
    @Column(name = "usersAmount")
    public Double getUsersAmount(){

        return this.usersAmount;
    }
    public void setUsersAmount(Double usersAmount){

        this.usersAmount = usersAmount;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
    @Column(name = "createUserID")
    public String getCreateUserID(){

        return this.createUserID;
    }
    public void setCreateUserID(String createUserID){

        this.createUserID = createUserID;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserID")
    public String getUpdateUserID(){

        return this.updateUserID;
    }
    public void setUpdateUserID(String updateUserID){

        this.updateUserID = updateUserID;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
}

