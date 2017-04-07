package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Description 广告管理
 * @Project gd-customer-intf
 * @ClassName AdAdvertise.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月13日 上午11:18:33
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory AdAdvertise entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "ad_advertise")
public class AdAdvertise implements java.io.Serializable {

    // Fields
    private static final long serialVersionUID = 1039002793394393728L;
    private Long id;
    private Long adSpaceId;
    private String adName;
    private String state;
    private String jumpType;
    private String jumpUrl;
    private String adWord;
    private Date startTime;
    private Date endTime;
    private Long categoryId;
    private String productId;
    private String createUserId;
    private String createUserName;
    private Date createTime;
    private String updateUserId;
    private String updateUserName;
    private Date updateTime;
    private String adUrl;
    private String productSign;
    /**
     * @Fields marketId 所属市场
     * @since Ver 2.0
     */
    private Long marketId;
    // Constructors

    /** default constructor */
    public AdAdvertise() {
    }

    /** minimal constructor */
    public AdAdvertise(Long adSpaceId) {
        this.adSpaceId = adSpaceId;
    }

    /** full constructor */
    public AdAdvertise(Long adSpaceId, String adName, String state, String jumpType, String jumpUrl, String adWord, Date startTime, Date endTime, Long categoryId, String productId,
            String createUserId, String createUserName, Date createTime, String updateUserId, String updateUserName, Date updateTime) {
        this.adSpaceId = adSpaceId;
        this.adName = adName;
        this.state = state;
        this.jumpType = jumpType;
        this.jumpUrl = jumpUrl;
        this.adWord = adWord;
        this.startTime = startTime;
        this.endTime = endTime;
        this.categoryId = categoryId;
        this.productId = productId;
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

    @Column(name = "adSpaceId", nullable = false)
    public Long getAdSpaceId() {
        return this.adSpaceId;
    }

    public void setAdSpaceId(Long adSpaceId) {
        this.adSpaceId = adSpaceId;
    }

    @Column(name = "adName", length = 50)
    public String getAdName() {
        return this.adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    @Column(name = "state", length = 2)
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "jumpType", length = 2)
    public String getJumpType() {
        return this.jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    @Column(name = "jumpUrl", length = 200)
    public String getJumpUrl() {
        return this.jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    @Column(name = "adWord", length = 50)
    public String getAdWord() {
        return this.adWord;
    }

    public void setAdWord(String adWord) {
        this.adWord = adWord;
    }

    @Column(name = "startTime", length = 19)
    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "endTime", length = 19)
    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "categoryId")
    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "productId", length = 4000)
    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    @Column(name = "adUrl")
    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    @Column(name = "productSign")
    public String getProductSign() {
        return productSign;
    }

    public void setProductSign(String productSign) {
        this.productSign = productSign;
    }
    @Column(name = "marketId")
    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }
}