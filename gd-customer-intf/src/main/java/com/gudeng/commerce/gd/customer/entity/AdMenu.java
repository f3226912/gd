package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * AdMenu entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "ad_menu")
public class AdMenu implements java.io.Serializable {

    // Fields
    private static final long serialVersionUID = 2197555469888991033L;
    private Long id;
    private String menuName;
    private Long fatherId;
    private String menuSign;
    private String createUserId;
    private String createUserName;
    private Date createTime;
    private String updateUserId;
    private String updateUserName;
    private Date updateTime;
    private String state;
    // Constructors

    /** default constructor */
    public AdMenu() {
    }

    /** minimal constructor */
    public AdMenu(Long fatherId) {
        this.fatherId = fatherId;
    }

    /** full constructor */
    public AdMenu(String menuName, Long fatherId, String menuSign, String createUserId, String createUserName, Date createTime, String updateUserId, String updateUserName,
            Date updateTime) {
        this.menuName = menuName;
        this.fatherId = fatherId;
        this.menuSign = menuSign;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.createTime = createTime;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateTime = updateTime;
    }

    // Property accessors
    @Id
    @Column(name = "id")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "menuName", length = 50)
    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Column(name = "fatherId", nullable = false)
    public Long getFatherId() {
        return this.fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    @Column(name = "menuSign", length = 200)
    public String getMenuSign() {
        return this.menuSign;
    }

    public void setMenuSign(String menuSign) {
        this.menuSign = menuSign;
    }

    @Column(name = "createUserId", length = 32)
    public String getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    @Column(name = "createUserName", length = 32)
    public String getCreateUserName() {
        return this.createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Column(name = "createTime", length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "updateUserId", length = 32)
    public String getUpdateUserId() {
        return this.updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Column(name = "updateUserName", length = 32)
    public String getUpdateUserName() {
        return this.updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    @Column(name = "updateTime", length = 19)
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}