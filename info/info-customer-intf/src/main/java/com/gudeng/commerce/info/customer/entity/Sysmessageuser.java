package com.gudeng.commerce.info.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Sysmessageuser entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "sysmessageuser")
public class Sysmessageuser implements java.io.Serializable {

    // Fields

    /**
     * @Fields serialVersionUID TODO
     * @since Ver 2.0
     */

    private static final long serialVersionUID = 1289378983603545459L;
    private Long rmId;
    private Long messageId;
    private String userId;
    private String createUserId;
    private Date createTime;
    private String isread;
    private String isdel;

    // Constructors

    /** default constructor */
    public Sysmessageuser() {
    }

    /** full constructor */
    public Sysmessageuser(Long rmId, Long messageId, String userId, String createUserId, Date createTime) {
        this.rmId = rmId;
        this.messageId = messageId;
        this.userId = userId;
        this.createUserId = createUserId;
        this.createTime = createTime;
    }

    // Property accessors
    @Id
    @Column(name = "rmID", unique = true, nullable = false)
    public Long getRmId() {
        return this.rmId;
    }

    public void setRmId(Long rmId) {
        this.rmId = rmId;
    }

    @Column(name = "messageID", nullable = false)
    public Long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Column(name = "userID", nullable = false, length = 32)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "createUserID", nullable = false, length = 32)
    public String getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    @Column(name = "createTime", nullable = false, length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

}