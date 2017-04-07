package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_instorage")
public class GrdInstorageEntity  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2506006114157787315L;
	
    private Integer id;

    private String inStorageId;

    private String purchaseNO;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date updateTime;

    private String updateUser;

    private String updateUserName;
    
    private String remarks;

    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "inStorageId")
    public String getInStorageId(){

        return this.inStorageId;
    }
    public void setInStorageId(String inStorageId){

        this.inStorageId = inStorageId;
    }
    @Column(name = "purchaseNO")
    public String getPurchaseNO(){

        return this.purchaseNO;
    }
    public void setPurchaseNO(String purchaseNO){

        this.purchaseNO = purchaseNO;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
}

