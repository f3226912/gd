package com.gudeng.commerce.gd.order.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 过磅 商铺关联
 * @author Ailen
 *
 */
@Entity(name = "re_weighCar_business")
public class ReWeighCarBusinessEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9116905092781711265L;

	private Long weighCarId;

    private Long businessId;
    
    private Long categoryId;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    @Column(name = "weighCarId")
    public Long getWeighCarId(){

        return this.weighCarId;
    }
    public void setWeighCarId(Long weighCarId){

        this.weighCarId = weighCarId;
    }
    @Column(name = "businessId")
    public Long getBusinessId(){

        return this.businessId;
    }
    public void setBusinessId(Long businessId){

        this.businessId = businessId;
    }
    @Column(name="categoryId")
    public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

