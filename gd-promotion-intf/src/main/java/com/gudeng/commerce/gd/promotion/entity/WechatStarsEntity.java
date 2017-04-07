package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "wechat_stars")
public class WechatStarsEntity implements java.io.Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 被点赞微信用户openid
     */
    private String openId1;

    /**
     * 点赞微信用户openid
     */
    private String openId2;

    /**
     * 活动id
     */
    private Integer actId;

    /**
     * 记录生成时间
     */
    private Date createTime;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "openId1")
    public String getOpenId1() {
        return this.openId1;
    }

    public void setOpenId1(String openId1) {
        this.openId1 = openId1;
    }

    @Column(name = "openId2")
    public String getOpenId2() {
        return this.openId2;
    }

    public void setOpenId2(String openId2) {
        this.openId2 = openId2;
    }

    @Column(name = "actId")
    public Integer getActId() {
        return this.actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    @Column(name = "createTime")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

