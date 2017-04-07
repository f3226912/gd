package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_gd_giftteam")
public class GrdGdGiftteamEntity  implements java.io.Serializable{
    
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	    
	private static final long serialVersionUID = 1L;

	/**
    *主键,自增
    */
    private Integer id;

    /**
    *团队名称
    */
    private String name;

    /**
    *所属市场
    */
    private Integer marketId;

    /**
    *所属礼品仓库
    */
    private Integer giftstoreId;

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
    @Column(name = "name")
    public String getName(){

        return this.name;
    }
    public void setName(String name){

        this.name = name;
    }
    @Column(name = "marketId")
    public Integer getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Integer marketId){

        this.marketId = marketId;
    }
    @Column(name = "giftstoreId")
    public Integer getGiftstoreId(){

        return this.giftstoreId;
    }
    public void setGiftstoreId(Integer giftstoreId){

        this.giftstoreId = giftstoreId;
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
    @Column(name = "remarks")
    public String getRemarks(){

        return this.remarks;
    }
    public void setRemarks(String remarks){

        this.remarks = remarks;
    }
}
