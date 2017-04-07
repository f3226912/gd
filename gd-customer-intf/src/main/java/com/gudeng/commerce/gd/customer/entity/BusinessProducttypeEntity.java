package com.gudeng.commerce.gd.customer.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity(name = "business_producttype")
public class BusinessProducttypeEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3721251460367712117L;

	private Long ptId;

    private Long businessId;

    private Integer curLevel;

    private String cateName;

    private Long parentId;

    private Integer orderNum;

    private String status;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    @Column(name = "ptId")
    public Long getPtId(){

        return this.ptId;
    }
    public void setPtId(Long ptId){

        this.ptId = ptId;
    }
    @Column(name = "businessId")
    public Long getBusinessId(){

        return this.businessId;
    }
    public void setBusinessId(Long businessId){

        this.businessId = businessId;
    }
    @Column(name = "curLevel")
    public Integer getCurLevel(){

        return this.curLevel;
    }
    public void setCurLevel(Integer curLevel){

        this.curLevel = curLevel;
    }
    @Column(name = "cateName")
    public String getCateName(){

        return this.cateName;
    }
    public void setCateName(String cateName){

        this.cateName = cateName;
    }
    @Column(name = "parentId")
    public Long getParentId(){

        return this.parentId;
    }
    public void setParentId(Long parentId){

        this.parentId = parentId;
    }
    @Column(name = "orderNum")
    public Integer getOrderNum(){

        return this.orderNum;
    }
    public void setOrderNum(Integer orderNum){

        this.orderNum = orderNum;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
}

