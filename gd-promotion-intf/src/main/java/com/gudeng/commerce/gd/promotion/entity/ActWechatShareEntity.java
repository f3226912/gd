package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * ActWechatShare entity. @author MyEclipse Persistence Tools
 */
/**
 * 微信活动分享记录
 * 
 * @author lidong
 *
 */
@Entity(name = "act_wechat_share")
public class ActWechatShareEntity implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = -6818930523042741460L;
    private Integer id;
    private String openid;// 分享用户的openid
    private String unionid;// 对于拥有多个账号的企业来说，unionid可以帮助识别不同公众账号下的用户是否是同一个人
    private Short shareType;// 分享类型，1：好友分享 2：朋友圈分享
    private Date createTime;
    private Date updateTime;
    private Long userid;// 分享用户的userid
    private String ghid;// 公众号id
    private Integer activityId;// 活动id

    // Constructors

    /** default constructor */
    public ActWechatShareEntity() {
    }

    /** full constructor */
    public ActWechatShareEntity(String openid, String unionid, Short shareType, Date createTime, Date updateTime, Long userid, String ghid, Integer activityId) {
        this.openid = openid;
        this.unionid = unionid;
        this.shareType = shareType;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.userid = userid;
        this.ghid = ghid;
        this.activityId = activityId;
    }

    // Property accessors
    @Id
    @Column(name = "id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "openid", length = 64)
    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Column(name = "unionid", length = 64)
    public String getUnionid() {
        return this.unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Column(name = "shareType")
    public Short getShareType() {
        return this.shareType;
    }

    public void setShareType(Short shareType) {
        this.shareType = shareType;
    }

    @Column(name = "createTime", length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "updateTime", length = 19)
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "userid")
    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Column(name = "ghid", length = 64)
    public String getGhid() {
        return this.ghid;
    }

    public void setGhid(String ghid) {
        this.ghid = ghid;
    }

    @Column(name = "activityId")
    public Integer getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

}