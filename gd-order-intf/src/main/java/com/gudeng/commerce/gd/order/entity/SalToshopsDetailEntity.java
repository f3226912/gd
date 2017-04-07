package com.gudeng.commerce.gd.order.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 货主出场销售商品表
 * @author Ailen
 *
 */
@Entity(name = "sal_toshops_detail")
public class SalToshopsDetailEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 820527351330883289L;

	private Long stdId;

    private Long outmarketId;

    private Long businessId;

    private Long productId;

    private String productName;

    private Integer purQuantity;

    private Double price;

    private String unit;

    private Double weigh;

    private Double tradingPrice;

    private Double needToPayAmount;

    private String type;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    @Id
    @Column(name = "stdId")
    public Long getStdId(){

        return this.stdId;
    }
    public void setStdId(Long stdId){

        this.stdId = stdId;
    }
    @Column(name = "outmarketId")
    public Long getOutmarketId(){

        return this.outmarketId;
    }
    public void setOutmarketId(Long outmarketId){

        this.outmarketId = outmarketId;
    }
    @Column(name = "businessId")
    public Long getBusinessId(){

        return this.businessId;
    }
    public void setBusinessId(Long businessId){

        this.businessId = businessId;
    }
    @Column(name = "productId")
    public Long getProductId(){

        return this.productId;
    }
    public void setProductId(Long productId){

        this.productId = productId;
    }
    @Column(name = "productName")
    public String getProductName(){

        return this.productName;
    }
    public void setProductName(String productName){

        this.productName = productName;
    }
    @Column(name = "purQuantity")
    public Integer getPurQuantity(){

        return this.purQuantity;
    }
    public void setPurQuantity(Integer purQuantity){

        this.purQuantity = purQuantity;
    }
    @Column(name = "price")
    public Double getPrice(){

        return this.price;
    }
    public void setPrice(Double price){

        this.price = price;
    }
    @Column(name = "unit")
    public String getUnit(){

        return this.unit;
    }
    public void setUnit(String unit){

        this.unit = unit;
    }
    @Column(name = "weigh")
    public Double getWeigh(){

        return this.weigh;
    }
    public void setWeigh(Double weigh){

        this.weigh = weigh;
    }
    @Column(name = "tradingPrice")
    public Double getTradingPrice(){

        return this.tradingPrice;
    }
    public void setTradingPrice(Double tradingPrice){

        this.tradingPrice = tradingPrice;
    }
    @Column(name = "needToPayAmount")
    public Double getNeedToPayAmount(){

        return this.needToPayAmount;
    }
    public void setNeedToPayAmount(Double needToPayAmount){

        this.needToPayAmount = needToPayAmount;
    }
    @Column(name = "type")
    public String getType(){

        return this.type;
    }
    public void setType(String type){

        this.type = type;
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

