package com.gudeng.commerce.gd.customer.entity.certif;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "certif_log")
public class CertifLogEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -607731135659473646L;

	/**
    *
    */
    private Integer id;
    /**
    *关联各种认证表的Id
    */
    private Integer optionId;
    /**
    *表示各种认证类型，1为个人认证，2为企业认证，3为实体商铺认证，4为合作社认证，5为基地认证，6为地理标志产品认证，7为货主认证，8为车主认证，9为信息部认证，其余待补充
    */
    private Integer type;
    /**
    *记录当前操作的原因，无原因则为空
    */
    private String reason;
    /**
    *记录当前操作的操作员是谁，取管理后台用户的姓名
    */
    private String optionUser;
    /**
    *状态(1:审核通过;2:审核驳回)
    */
    private String status;
    /**
    *
    */
    private Date createTime;
    /**
    *
    */
    private Date updateTime;
    /**
    *
    */
    private String createUserId;
    /**
    *
    */
    private String updateUserId;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "optionId")
    public Integer getOptionId(){
        return this.optionId;
    }
    public void setOptionId(Integer optionId){
        this.optionId = optionId;
    }
    @Column(name = "type")
    public Integer getType(){
        return this.type;
    }
    public void setType(Integer type){
        this.type = type;
    }
    @Column(name = "reason")
    public String getReason(){
        return this.reason;
    }
    public void setReason(String reason){
        this.reason = reason;
    }
    @Column(name = "optionUser")
    public String getOptionUser(){
        return this.optionUser;
    }
    public void setOptionUser(String optionUser){
        this.optionUser = optionUser;
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
    @Column(name = "updateTime")
    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){
        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){
        this.updateUserId = updateUserId;
    }
}
