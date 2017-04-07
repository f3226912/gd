package com.gudeng.commerce.gd.promotion.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_purchasegift")
public class GrdPurchasegiftEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer purchaseGiftId;

    private String purchaseNO;

    private String giftNO;

    private Double unitPrice;

    private Integer count;

    private Double amount;

    private String status;

    @Id
    @Column(name = "purchaseGiftId")
    public Integer getPurchaseGiftId(){

        return this.purchaseGiftId;
    }
    public void setPurchaseGiftId(Integer purchaseGiftId){

        this.purchaseGiftId = purchaseGiftId;
    }
    @Column(name = "purchaseNO")
    public String getPurchaseNO(){

        return this.purchaseNO;
    }
    public void setPurchaseNO(String purchaseNO){

        this.purchaseNO = purchaseNO;
    }
    @Column(name = "giftNO")
    public String getGiftNO(){

        return this.giftNO;
    }
    public void setGiftNO(String giftNO){

        this.giftNO = giftNO;
    }
    @Column(name = "unitPrice")
    public Double getUnitPrice(){

        return this.unitPrice;
    }
    public void setUnitPrice(Double unitPrice){

        this.unitPrice = unitPrice;
    }
    @Column(name = "count")
    public Integer getCount(){

        return this.count;
    }
    public void setCount(Integer count){

        this.count = count;
    }
    @Column(name = "amount")
    public Double getAmount(){

        return this.amount;
    }
    public void setAmount(Double amount){

        this.amount = amount;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
}


