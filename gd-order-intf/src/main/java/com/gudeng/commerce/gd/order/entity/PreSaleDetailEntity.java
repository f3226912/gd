package com.gudeng.commerce.gd.order.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pre_sale_detail")
public class PreSaleDetailEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6972837485023716531L;

	private Integer id;

    private Long orderNo;

    private Integer productId;

    private String productName;

    private Double purQuantity;
    
    private String unit;

    private Double price;

    private Double tradingPrice;

    private Integer ruleId;

    private String subRule;

    private Double subAmount;

    private Double needToPayAmount;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

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
    @Column(name = "productId")
    public Integer getProductId(){

        return this.productId;
    }
    public void setProductId(Integer productId){

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
    public Double getPurQuantity(){

        return this.purQuantity;
    }
    public void setPurQuantity(Double purQuantity){

        this.purQuantity = purQuantity;
    }
    @Column(name = "price")
    public Double getPrice(){

        return this.price;
    }
    public void setPrice(Double price){

        this.price = price;
    }
    @Column(name = "tradingPrice")
    public Double getTradingPrice(){

        return this.tradingPrice;
    }
    public void setTradingPrice(Double tradingPrice){

        this.tradingPrice = tradingPrice;
    }
    @Column(name = "ruleId")
    public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	@Column(name = "subRule")
	public String getSubRule() {
		return subRule;
	}
	public void setSubRule(String subRule) {
		this.subRule = subRule;
	}
    @Column(name = "subAmount")
    public Double getSubAmount(){

        return this.subAmount;
    }
    
	public void setSubAmount(Double subAmount){

        this.subAmount = subAmount;
    }
    @Column(name = "needToPayAmount")
    public Double getNeedToPayAmount(){

        return this.needToPayAmount;
    }
    public void setNeedToPayAmount(Double needToPayAmount){

        this.needToPayAmount = needToPayAmount;
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
    public void setUpdateuserid(String updateUserId){

        this.updateUserId = updateUserId;
    }
    @Column(name = "unit")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
