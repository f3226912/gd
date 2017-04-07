package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_gd_gift")
public class GrdGdGiftEntity  implements java.io.Serializable{
    
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	    
	private static final long serialVersionUID = 1L;

	/**
    *
    */
    private Integer id;

    /**
    *礼品编号(12位,系统自动生成，服务器的年月日+4位流水号如201608120001)
    */
    private String giftNo;

    /**
    *礼品名称
    */
    private String name;

    /**
    *单位
    */
    private String unit;

    /**
    *规格
    */
    private String spec;

    /**
    *参考价
    */
    private Double rePrice;

    /**
    *起订量
    */
    private Integer riseCount;

    /**
    *供货周期
    */
    private String supplyCycle;

    /**
    *最新价格
    */
    private Double newPrice;

    /**
    *创建时间
    */
    private Date createTime;

    /**
    *创建人ID
    */
    private String createUser;

    /**
    *创建人姓名
    */
    private String createUserName;

    /**
    *修改时间
    */
    private Date updateTime;

    /**
    *修改人ID
    */
    private String updateUser;

    /**
    *修改人姓名
    */
    private String updateUserName;

    /**
    *礼品图片(多个用,隔开)
    */
    private String giftImage;

    /**
    *备注
    */
    private String remarks;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "giftNo")
    public String getGiftNo(){

        return this.giftNo;
    }
    public void setGiftNo(String giftNo){

        this.giftNo = giftNo;
    }
    @Column(name = "name")
    public String getName(){

        return this.name;
    }
    public void setName(String name){

        this.name = name;
    }
    @Column(name = "unit")
    public String getUnit(){

        return this.unit;
    }
    public void setUnit(String unit){

        this.unit = unit;
    }
    @Column(name = "spec")
    public String getSpec(){

        return this.spec;
    }
    public void setSpec(String spec){

        this.spec = spec;
    }
    @Column(name = "rePrice")
    public Double getRePrice(){

        return this.rePrice;
    }
    public void setRePrice(Double rePrice){

        this.rePrice = rePrice;
    }
    @Column(name = "riseCount")
    public Integer getRiseCount(){

        return this.riseCount;
    }
    public void setRiseCount(Integer riseCount){

        this.riseCount = riseCount;
    }
    @Column(name = "supplyCycle")
    public String getSupplyCycle(){

        return this.supplyCycle;
    }
    public void setSupplyCycle(String supplyCycle){

        this.supplyCycle = supplyCycle;
    }
    @Column(name = "newPrice")
    public Double getNewPrice(){

        return this.newPrice;
    }
    public void setNewPrice(Double newPrice){

        this.newPrice = newPrice;
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
    @Column(name = "createUserName")
    public String getCreateUserName(){

        return this.createUserName;
    }
    public void setCreateUserName(String createUserName){

        this.createUserName = createUserName;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    @Column(name = "updateUser")
    public String getUpdateUser(){

        return this.updateUser;
    }
    public void setUpdateUser(String updateUser){

        this.updateUser = updateUser;
    }
    @Column(name = "updateUserName")
    public String getUpdateUserName(){

        return this.updateUserName;
    }
    public void setUpdateUserName(String updateUserName){

        this.updateUserName = updateUserName;
    }
    @Column(name = "giftImage")
    public String getGiftImage(){

        return this.giftImage;
    }
    public void setGiftImage(String giftImage){

        this.giftImage = giftImage;
    }
    @Column(name = "remarks")
    public String getRemarks(){

        return this.remarks;
    }
    public void setRemarks(String remarks){

        this.remarks = remarks;
    }
}

