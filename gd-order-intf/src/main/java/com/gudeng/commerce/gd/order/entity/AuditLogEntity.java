package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @description: TODO - 审核日志表
 * @Classname: AuditLogEntity
 * @author lmzhang@gdeng.cn
 *
 */
@Entity(name = "audit_log")
public class AuditLogEntity  implements java.io.Serializable{
	private static final long serialVersionUID = 5842388865384542943L;

	private Integer logId; 

    private String type;		// 类型 1补贴审核 2提现审核

    private Long orderNo;		// 订单号

    private String auditUserId;	// 审核人ID

    private String auditUserName;	// 审核人姓名

    private String description;	// 描述

    private Date auditTime;		// 审核时间

    private Date createTime;	// 创建时间

    private String createUserId;	// 创建人员id

    private Date updateTime;	// 更新时间

    private String updateUserId;	// 修改人员id
    
    @Id
    @Column(name = "logId")
    public Integer getLogId(){
        return this.logId;
    }
    public void setLogId(Integer logId){
        this.logId = logId;
    }
    @Column(name = "type")
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }
    @Column(name = "orderNo")
    public Long getOrderNo(){
        return this.orderNo;
    }
    public void setOrderNo(Long orderNo){
        this.orderNo = orderNo;
    }
    @Column(name = "auditUserId")
    public String getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	@Column(name = "auditUserName")
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	@Column(name = "description")
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    @Column(name = "auditTime")
    public Date getAuditTime(){
        return this.auditTime;
    }
    public void setAuditTime(Date auditTime){
        this.auditTime = auditTime;
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