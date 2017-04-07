package com.gudeng.commerce.gd.order.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "in_store_detail")
public class InStoreDetailEntity  implements java.io.Serializable{
   
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4806047005533391707L;

	private Long isdId;

    private Long pwdId;

    private Long businessId;

    private Long productId;

    private String productName;

    private Integer purQuantity;

    private Double price;

    private Double weigh;

    private String unit;
    
    private Long inStoreNo;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;
    
    @Column(name="inStoreNo")
    public Long getInStoreNo() {
		return inStoreNo;
	}
	public void setInStoreNo(Long inStoreNo) {
		this.inStoreNo = inStoreNo;
	}
	@Id
    @Column(name = "isdId")
    public Long getIsdId(){

        return this.isdId;
    }
    public void setIsdId(Long isdId){

        this.isdId = isdId;
    }
    @Column(name = "pwdId")
    public Long getPwdId(){

        return this.pwdId;
    }
    public void setPwdId(Long pwdId){

        this.pwdId = pwdId;
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
    @Column(name = "weigh")
    public Double getWeigh(){

        return this.weigh;
    }
    public void setWeigh(Double weigh){

        this.weigh = weigh;
    }
    @Column(name = "unit")
    public String getUnit(){

        return this.unit;
    }
    public void setUnit(String unit){

        this.unit = unit;
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

