package com.gudeng.commerce.gd.api.params;

import java.util.Date;

public class CertifCompanyParamsBean  implements java.io.Serializable{
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
    private String account;
    /**
    *企业名称
    */
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
    private Date commitTime;
    /**
    *状态(0:待审核1:已认证;2:已驳回)
    */
    private String status;
    /**
    *app类型，记录属于哪个app或者客户端提交的认证。
            1为农批商，2为农商友，3为农速通，4为供应商，5为谷登农批web
    */
    private Integer appType;
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
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getMemberId(){
        return this.memberId;
    }
    public void setMemberId(Integer memberId){
        this.memberId = memberId;
    }
    public String getAccount(){
        return this.account;
    }
    public void setAccount(String account){
        this.account = account;
    }
    public String getCompanyName(){
        return this.companyName;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    public String getBzl(){
        return this.bzl;
    }
    public void setBzl(String bzl){
        this.bzl = bzl;
    }
    public String getBzlPhotoUrl(){
        return this.bzlPhotoUrl;
    }
    public void setBzlPhotoUrl(String bzlPhotoUrl){
        this.bzlPhotoUrl = bzlPhotoUrl;
    }
    public Date getCommitTime(){
        return this.commitTime;
    }
    public void setCommitTime(Date commitTime){
        this.commitTime = commitTime;
    }
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public Integer getAppType(){
        return this.appType;
    }
    public void setAppType(Integer appType){
        this.appType = appType;
    }
    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
    public String getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }
    public String getUpdateUserId(){
        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){
        this.updateUserId = updateUserId;
    }
    public String getOptionUser(){
        return this.optionUser;
    }
    public void setOptionUser(String optionUser){
        this.optionUser = optionUser;
    }
    public String getRealName(){
        return this.realName;
    }
    public void setRealName(String realName){
        this.realName = realName;
    }
    public String getIdCard(){
        return this.idCard;
    }
    public void setIdCard(String idCard){
        this.idCard = idCard;
    }
    public String getCardPhotoUrl(){
        return this.cardPhotoUrl;
    }
    public void setCardPhotoUrl(String cardPhotoUrl){
        this.cardPhotoUrl = cardPhotoUrl;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
	@Override
	public String toString() {
		return "CertifCompanyParamsBean [id=" + id + ", memberId=" + memberId + ", account=" + account
				+ ", companyName=" + companyName + ", bzl=" + bzl + ", bzlPhotoUrl=" + bzlPhotoUrl + ", commitTime="
				+ commitTime + ", status=" + status + ", appType=" + appType + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", createUserId=" + createUserId + ", updateUserId=" + updateUserId
				+ ", optionUser=" + optionUser + ", realName=" + realName + ", idCard=" + idCard + ", cardPhotoUrl="
				+ cardPhotoUrl + ", icon=" + icon + "]";
	}
    
}
