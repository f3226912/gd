package com.gudeng.commerce.gd.customer.entity.certif;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;


@Entity(name = "certif_personal")
public class CertifPersonalEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5222170184244330570L;

	/**
    *
    */
    private Integer id;
    /**
    *
    */
    private Integer memberId;
    /**
    *账号
    */
    @ExcelConf(excelHeader="账号", sort = 1)
    private String account;
    /**
    *姓名
    */
    @ExcelConf(excelHeader="姓名", sort = 2)
    private String realName;
    /**
    *身份证号
    */
    private String idCard;
    /**
    *用',' 隔开
    */
    private String cardPhotoUrl;
    /**
    *状态(0:待审核1:已认证;2:已驳回)
    */
    private String status;
    /**
    *提交时间
    */
	@ExcelConf(excelHeader="申请时间", sort = 3)
    private Date commitTime;
    /**
    *头像
    */
    private String icon;

	@ExcelConf(excelHeader="审核员", sort = 5)
	private String optionUser;
        /**
    *记录产生时间
    */
    private Date createTime;
    /**
    *最新的更新时间
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
    @Column(name = "status")
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    @Column(name = "commitTime")
    public Date getCommitTime(){
        return this.commitTime;
    }
    public void setCommitTime(Date commitTime){
        this.commitTime = commitTime;
    }
    @Column(name = "icon")
    public String getIcon(){
        return this.icon;
    }
    public void setIcon(String icon){
        this.icon = icon;
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
	public String getOptionUser() {
		return optionUser;
	}
	public void setOptionUser(String optionUser) {
		this.optionUser = optionUser;
	}
    
}
