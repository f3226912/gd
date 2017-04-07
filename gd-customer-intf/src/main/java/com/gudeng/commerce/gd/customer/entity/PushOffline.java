package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * PushOffline entity. @author MyEclipse Persistence Tools
 */
/**   
 * @Description 线下推广统计
 * @Project gd-customer-intf
 * @ClassName PushOffline.java
 * @Author lidong(dli@gdeng.cn)    
 * @CreationDate 2016年2月18日 上午11:56:34
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
@Entity(name = "push_offline")
public class PushOffline implements java.io.Serializable {

    // Fields
    
    private static final long serialVersionUID = 4480265812465979788L;
    private Long id;//主键
    private String industry;//所属行业,工厂、学校/食堂、下级批发商、餐馆、超市、门店、垂直生鲜电商
    private String source;//推广来源 ,农速通、农商友、农商友-农批商
    private String pushName;//推广人
    private String pushMobile;//推广人手机号
    private String memberMobile;//会员手机号
    private Date createTime;//推广时间

    // Constructors

    /** default constructor */
    public PushOffline() {
    }

    /** full constructor */
    public PushOffline(String industry, String source, String pushName, String pushMobile, String memberMobile, Date createTime) {
        this.industry = industry;
        this.source = source;
        this.pushName = pushName;
        this.pushMobile = pushMobile;
        this.memberMobile = memberMobile;
        this.createTime = createTime;
    }

    // Property accessors
    @Id
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "industry", length = 50)
    public String getIndustry() {
        return this.industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Column(name = "source", length = 50)
    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "pushName", length = 50)
    public String getPushName() {
        return this.pushName;
    }

    public void setPushName(String pushName) {
        this.pushName = pushName;
    }

    @Column(name = "pushMobile", length = 13)
    public String getPushMobile() {
        return this.pushMobile;
    }

    public void setPushMobile(String pushMobile) {
        this.pushMobile = pushMobile;
    }

    @Column(name = "memberMobile", length = 13)
    public String getMemberMobile() {
        return this.memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    @Column(name = "createTime", length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}