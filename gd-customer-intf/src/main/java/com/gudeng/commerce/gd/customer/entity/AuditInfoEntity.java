package com.gudeng.commerce.gd.customer.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "audit_info")
public class AuditInfoEntity  implements java.io.Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3721465054588522637L;

	private Long id;
//	  关联的主表ID   mainId          商品的审核信息，记录商品ID，商铺的审核信息，记录商铺ID 
    private Long mainId;
    
//    审核信息来源   type            标识此审核信息的来源，如：1标识商品审核信息，2标识商铺审核信息
    private String type;
    
//    是否通过       status          0标识审核不通过，1标识审核通过
    private String status;
    
//    操作人id       memberId        记录审核人的id
    private String memberId;
    
//    操作人姓名	 memberName	     记录审核人姓名
    private String memberName;
    
//    操作时间       auditTime       记录审核时间
    private Date auditTime;
    
//	  原因           reason          记录审核的备注说明/原因（通过的原因，不通过的原因）
    private String reason;
//    其他原因，用于产品审核中的多个原因
    private String otherReason;

    public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
	private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;
    
    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "mainId")
    public Long getMainId(){

        return this.mainId;
    }
    public void setMainId(Long mainId){

        this.mainId = mainId;
    }
    @Column(name = "type")
    public String getType(){

        return this.type;
    }
    public void setType(String type){

        this.type = type;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
    @Column(name = "memberId")
    public String getMemberId(){

        return this.memberId;
    }
    public void setMemberId(String memberId){

        this.memberId = memberId;
    }
    @Column(name = "memberName")
    public String getMemberName(){

        return this.memberName;
    }
    public void setMemberName(String memberName){

        this.memberName = memberName;
    }
    @Column(name = "auditTime")
    public Date getAuditTime(){

        return this.auditTime;
    }
    public void setAuditTime(Date auditTime){

        this.auditTime = auditTime;
    }
    @Column(name = "reason")
    public String getReason(){

        return this.reason;
    }
    public void setReason(String reason){

        this.reason = reason;
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

