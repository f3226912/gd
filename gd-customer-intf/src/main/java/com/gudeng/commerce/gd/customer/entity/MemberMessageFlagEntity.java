package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "member_message_flag")
public class MemberMessageFlagEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3017188413279141329L;

	/**
    *
    */
    private Long id;
    /**
    *用户ID
    */
    private Long memberId;
    /**
    *是否发送，发送1，未发送0
    */
    private String sendFlag;
    
    /**
    *创建人
    */
    private String createUserId;
    /**
    *创建时间
    */
    private Date createTime;
    /**
    *修改人
    */
    private String updateUserId;
    /**
    *修改时间
    */
    private Date updateTime;
    @Id
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    @Column(name = "memberId")
    public Long getMemberId(){
        return this.memberId;
    }
    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }
    @Column(name = "sendFlag")
    public String getSendFlag(){
        return this.sendFlag;
    }
    public void setSendFlag(String sendFlag){
        this.sendFlag = sendFlag;
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
