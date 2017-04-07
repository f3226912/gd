package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "wechat_login")
public class WechatLoginEntity implements java.io.Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 微信用户openid
     */
    private String openId;

    /**
     * 微信用户unionid
     */
    private String unionId;

    /**
     * 谷登系统用户id
     */
    private Integer memberId;

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

    @Column(name = "openId")
    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Column(name = "unionId")
    public String getUnionId() {
        return this.unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    @Column(name = "memberId")
    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

