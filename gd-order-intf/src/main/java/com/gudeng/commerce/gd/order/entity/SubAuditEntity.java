package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sub_audit")
public class SubAuditEntity  implements java.io.Serializable{
	
    /**
	 * 补贴审核表
	 */
	private static final long serialVersionUID = -8085123818487746011L;

	private Integer auditId;

    private Long orderNo;			// 订单编号

    private Double subAmount;		// 补贴金额

    private String memberID;		// 买家账号(id)

    private String buyerName;		// 买家姓名

    private String buyerShop;		// 卖家店铺

    private String subStatus;		// 补贴状态: 1-待补贴; 2-系统驳回; 3-已补贴; 4-不予补贴
    
    //private String rejectReason;	// 驳回原因(当subStatus=2时，该字段有效), 产品取消类该字段

    private String subComment;		// 备注信息

    private Date createTime;		// 创建时间

    private String createUserId;	// 创建人

    private Date updateTime;		// 更新时间

    private String updateUserId;	// 更新人
    
    @Id
    @Column(name = "auditId")
    public Integer getAuditId(){
        return this.auditId;
    }
    public void setAuditId(Integer auditId){
        this.auditId = auditId;
    }
    @Column(name = "orderNo")
    public Long getOrderNo(){
        return this.orderNo;
    }
    public void setOrderNo(Long orderNo){
        this.orderNo = orderNo;
    }
    @Column(name = "subAmount")
    public Double getSubAmount(){
        return this.subAmount;
    }
    public void setSubAmount(Double subAmount){
        this.subAmount = subAmount;
    }
    @Column(name = "memberID")
    public String getMemberID(){
        return this.memberID;
    }
    public void setMemberID(String memberID){
        this.memberID = memberID;
    }
    @Column(name = "buyerName")
    public String getBuyerName(){
        return this.buyerName;
    }
    public void setBuyerName(String buyerName){
        this.buyerName = buyerName;
    }
    @Column(name = "buyerShop")
    public String getBuyerShop(){
        return this.buyerShop;
    }
    public void setBuyerShop(String buyerShop){
        this.buyerShop = buyerShop;
    }
    @Column(name = "subStatus")
    public String getSubStatus(){

        return this.subStatus;
    }
    public void setSubStatus(String subStatus){
        this.subStatus = subStatus;
    }
	@Column(name = "subComment")
    public String getSubComment(){
        return this.subComment;
    }
    public void setSubComment(String subComment){
        this.subComment = subComment;
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
