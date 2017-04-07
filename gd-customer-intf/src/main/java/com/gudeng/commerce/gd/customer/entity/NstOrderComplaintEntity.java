package com.gudeng.commerce.gd.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "nst_order_complaint")
public class NstOrderComplaintEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String orderNo;

    private String complaintsDetails;

    private String appealDetails;

    private String reply;
    
    private String replyStatus;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "orderNo")
    public String getOrderNo(){

        return this.orderNo;
    }
    public void setOrderNo(String orderNo){

        this.orderNo = orderNo;
    }
    @Column(name = "complaintsDetails")
    public String getComplaintsDetails(){

        return this.complaintsDetails;
    }
    public void setComplaintsDetails(String complaintsDetails){

        this.complaintsDetails = complaintsDetails;
    }
    @Column(name = "appealDetails")
    public String getAppealDetails(){

        return this.appealDetails;
    }
    public void setAppealDetails(String appealDetails){

        this.appealDetails = appealDetails;
    }
    @Column(name = "reply")
    public String getReply(){

        return this.reply;
    }
    public void setReply(String reply){

        this.reply = reply;
    }
    
    @Column(name = "replyStatus")
    public String getReplyStatus() {
		return replyStatus;
	}
	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
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