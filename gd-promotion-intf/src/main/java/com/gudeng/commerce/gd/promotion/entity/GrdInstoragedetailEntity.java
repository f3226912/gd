package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_instoragedetail")
public class GrdInstoragedetailEntity  implements java.io.Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2506006114157787915L;
	
    private Integer inStorageDetailId;

    private String inStorageId;

    private Integer purchaseGiftId;

    private Integer inCount;

    @Column(name = "inStorageDetailId")
    public Integer getInStorageDetailId(){

        return this.inStorageDetailId;
    }
    public void setInStorageDetailId(Integer inStorageDetailId){

        this.inStorageDetailId = inStorageDetailId;
    }
    @Column(name = "inStorageId")
    public String getInStorageId(){

        return this.inStorageId;
    }
    public void setInStorageId(String inStorageId){

        this.inStorageId = inStorageId;
    }
    @Column(name = "purchaseGiftId")
    public Integer getPurchaseGiftId(){

        return this.purchaseGiftId;
    }
    public void setPurchaseGiftId(Integer purchaseGiftId){

        this.purchaseGiftId = purchaseGiftId;
    }
    @Column(name = "inCount")
    public Integer getInCount(){

        return this.inCount;
    }
    public void setInCount(Integer inCount){

        this.inCount = inCount;
    }
}

