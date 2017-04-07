package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "wechat_member")
public class WechatMemberEntity implements java.io.Serializable {
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
     * 微信用户绑定时间
     */
    private Date createTime;

    /**
     * 生成记录时的活动Id
     */
    private Integer actId;

    /**
     * 邀请人id,根据业务定制，可为memberId,可为openid
     */
    private String inviteId;

    /**
     * 数据来源类型，1：注册，2：首次登录绑定
     */
    private String type;

    /**
     * 公众号id
     */
    private String appId;

    public WechatMemberEntity() {
    }

    public WechatMemberEntity(String openId, String unionId, Integer memberId, Integer actId, String inviteId, String type, String appId) {
        this.openId = openId;
        this.unionId = unionId;
        this.memberId = memberId;
        this.actId = actId;
        this.inviteId = inviteId;
        this.type = type;
        this.appId = appId;
    }

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

    @Column(name = "createTime")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "actId")
    public Integer getActId() {
        return this.actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    @Column(name = "inviteId")
    public String getInviteId() {
        return this.inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    @Column(name = "type")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "appId")
    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

