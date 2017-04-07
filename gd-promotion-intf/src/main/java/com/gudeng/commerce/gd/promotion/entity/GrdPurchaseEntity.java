package com.gudeng.commerce.gd.promotion.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_purchase")
public class GrdPurchaseEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String purchaseNO;

    private String purchaser;

    private Integer marketId;

    private Integer warehouseId;

    private String status;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date updateTime;

    private String updateUser;

    private String updateUserName;

    private String remark;

	@Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "purchaseNO")
    public String getPurchaseNO(){

        return this.purchaseNO;
    }
    public void setPurchaseNO(String purchaseNO){

        this.purchaseNO = purchaseNO;
    }
    @Column(name = "purchaser")
    public String getPurchaser(){

        return this.purchaser;
    }
    public void setPurchaser(String purchaser){

        this.purchaser = purchaser;
    }
    @Column(name = "marketId")
    public Integer getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Integer marketId){

        this.marketId = marketId;
    }
    @Column(name = "warehouseId")
    public Integer getWarehouseId(){

        return this.warehouseId;
    }
    public void setWarehouseId(Integer warehouseId){

        this.warehouseId = warehouseId;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
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
    @Column(name = "remark")
    public String getRemark(){

        return this.remark;
    }
    public void setRemark(String remark){

        this.remark = remark;
    }
}
