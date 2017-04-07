package com.gudeng.commerce.gd.customer.entity.certif;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;

@Entity(name = "certif_company")
public class CertifCompanyEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5315053200663690027L;

	/**
    *
    */
    private Integer id;
    /**
    *会员ID
    */
    private Integer memberId;
    /**
    *账号
    */
    @ExcelConf(excelHeader="账号", sort = 1)
    private String account;
    /**
    *企业名称
    */
    @ExcelConf(excelHeader="企业名称", sort = 2)
    private String companyName;
    /**
    *营业执照号码
    */
    private String bzl;
    /**
    *营业执照图片
    */
    private String bzlPhotoUrl;
    /**
    *提交时间
    */
	@ExcelConf(excelHeader="申请时间", sort = 3)
    private Date commitTime;
    /**
    *状态(0:待审核1:已认证;2:已驳回)
    */
    private String status;
    /**
    *app类型，记录属于哪个app或者客户端提交的认证。
            1为农批商，2为农商友，3为农速通，4为供应商，5为谷登农批web
    */
    private Byte appType;
    /**
    *记录产生的时间
    */
    private Date createTime;
    /**
    *记录最新的更新时间
    */
    private Date updateTime;
    /**
    *
    */
    private String createUserId;
    /**
    *
    */
    private String updateUserId;
    /**
    *记录当前操作的操作员是谁，取管理后台用户的姓名
    */
	@ExcelConf(excelHeader="审核员", sort = 5)
    private String optionUser;
    /**
    *企业法人姓名
    */
    private String realName;
    /**
    *法人身份证号
    */
    private String idCard;
    /**
    *法人身份证图片, 逗号分隔
    */
    private String cardPhotoUrl;
    /**
    *头像
    */
    private String icon;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "memberId")
    public Integer getMemberId(){
        return this.memberId;
    }
    public void setMemberId(Integer memberId){
        this.memberId = memberId;
    }
    @Column(name = "account")
    public String getAccount(){
        return this.account;
    }
    public void setAccount(String account){
        this.account = account;
    }
    @Column(name = "companyName")
    public String getCompanyName(){
        return this.companyName;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    @Column(name = "bzl")
    public String getBzl(){
        return this.bzl;
    }
    public void setBzl(String bzl){
        this.bzl = bzl;
    }
    @Column(name = "bzlPhotoUrl")
    public String getBzlPhotoUrl(){
        return this.bzlPhotoUrl;
    }
    public void setBzlPhotoUrl(String bzlPhotoUrl){
        this.bzlPhotoUrl = bzlPhotoUrl;
    }
    @Column(name = "commitTime")
    public Date getCommitTime(){
        return this.commitTime;
    }
    public void setCommitTime(Date commitTime){
        this.commitTime = commitTime;
    }
    @Column(name = "status")
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    @Column(name = "appType")
    public Byte getAppType(){
        return this.appType;
    }
    public void setAppType(Byte appType){
        this.appType = appType;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){
        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){
        this.updateUserId = updateUserId;
    }
    @Column(name = "optionUser")
    public String getOptionUser(){
        return this.optionUser;
    }
    public void setOptionUser(String optionUser){
        this.optionUser = optionUser;
    }
    @Column(name = "realName")
    public String getRealName(){
        return this.realName;
    }
    public void setRealName(String realName){
        this.realName = realName;
    }
    @Column(name = "idCard")
    public String getIdCard(){
        return this.idCard;
    }
    public void setIdCard(String idCard){
        this.idCard = idCard;
    }
    @Column(name = "cardPhotoUrl")
    public String getCardPhotoUrl(){
        return this.cardPhotoUrl;
    }
    public void setCardPhotoUrl(String cardPhotoUrl){
        this.cardPhotoUrl = cardPhotoUrl;
    }
    @Column(name = "icon")
    public String getIcon(){
        return this.icon;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
}
