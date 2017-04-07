package com.gudeng.commerce.gd.customer.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "member_certifi")
public class MemberCertifiEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8950335520672543349L;
	// 认证表ID
	private Long certifiId;
	//会员ID
    private Long memberId;
    //    认证类别（1个人2企业）  目前需求中，个人认证直接通过，商家才需要认证
    private String type;
    //    公司名称
    private String companyName;
    //联系人
    private String linkMan;
    //联系电话
    private String mobile;
    //是否认证手机号码，0否，1 是
    private String ismobile;
    //注册资金
    private Double initialCapital;
    //身份证
    private String idCard;
    //身份证图片地址
    private String cardPhotoUrl;
    //驳回原因
    private String ngReason;
    //是否认证 状态(0:未认证 1:已认证;2:认证未通过)
    private String status;
    //公司网址
    private String url;
    //营业执照号码
    private String bzl;
    //营业执照图片地址
    private String bzlPhotoUrl;
    //组织机构代码
    private String orgCode;
    //    是否认证营业执照
    private String isbzl;
    //    组织机构代码照片(url)
    private String orgCodePhotoUrl;
    //    最新提交认证时间(提交认证时间)
    private Date commitTime;
    //    是否认证组织机构代码
    private String isOrgCode;
    //    认证时间
    private Date certificationTime;
    //    认证用户Id
    private Long certificationUserId;
    //    是否为品牌企业(0:不是品牌企业;1:品牌企业)
    private String isBrand;
    //    是否认证身份证
    private String isIdCard;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;
    
    //认证类型（商铺 或农速通）
    private String certificationType;
    //农速通认证状态
    private String nstStatus;
    
    ///农速通身份证
    private String nst_idCard;
    //农速通联系人
    private String nst_linkMan;
    //农速通身份证图片地址
    private String nst_cardPhotoUrl;
    //农速通营业执照图片地址
    private String nst_bzlPhotoUrl;
    //农速通驳回原因
    private String nst_ngReason;
  
    
    @Id
    @Column(name = "certifiId")
    public Long getCertifiId(){

        return this.certifiId;
    }
    public void setCertifiId(Long certifiId){

        this.certifiId = certifiId;
    }
    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
    }
    @Column(name = "type")
    public String getType(){

        return this.type;
    }
    public void setType(String type){

        this.type = type;
    }
    @Column(name = "companyName")
    public String getCompanyName(){

        return this.companyName;
    }
    public void setCompanyName(String companyName){

        this.companyName = companyName;
    }
    @Column(name = "linkMan")
    public String getLinkMan(){

        return this.linkMan;
    }
    public void setLinkMan(String linkMan){

        this.linkMan = linkMan;
    }
    @Column(name = "mobile")
    public String getMobile(){

        return this.mobile;
    }
    public void setMobile(String mobile){

        this.mobile = mobile;
    }
    @Column(name = "ismobile")
    public String getIsmobile(){

        return this.ismobile;
    }
    public void setIsmobile(String ismobile){

        this.ismobile = ismobile;
    }
    @Column(name = "initialCapital")
    public Double getInitialCapital(){

        return this.initialCapital;
    }
    public void setInitialCapital(Double initialCapital){

        this.initialCapital = initialCapital;
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
    @Column(name = "ngReason")
    public String getNgReason(){

        return this.ngReason;
    }
    public void setNgReason(String ngReason){

        this.ngReason = ngReason;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
    @Column(name = "url")
    public String getUrl(){

        return this.url;
    }
    public void setUrl(String url){

        this.url = url;
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
    @Column(name = "orgCode")
    public String getOrgCode(){

        return this.orgCode;
    }
    public void setOrgCode(String orgCode){

        this.orgCode = orgCode;
    }
    @Column(name = "isbzl")
    public String getIsbzl(){

        return this.isbzl;
    }
    public void setIsbzl(String isbzl){

        this.isbzl = isbzl;
    }
    @Column(name = "orgCodePhotoUrl")
    public String getOrgCodePhotoUrl(){

        return this.orgCodePhotoUrl;
    }
    public void setOrgCodePhotoUrl(String orgCodePhotoUrl){

        this.orgCodePhotoUrl = orgCodePhotoUrl;
    }
    @Column(name = "commitTime")
    public Date getCommitTime(){

        return this.commitTime;
    }
    public void setCommitTime(Date commitTime){

        this.commitTime = commitTime;
    }
    @Column(name = "isOrgCode")
    public String getIsOrgCode(){

        return this.isOrgCode;
    }
    public void setIsOrgCode(String isOrgCode){

        this.isOrgCode = isOrgCode;
    }
    @Column(name = "certificationTime")
    public Date getCertificationTime(){

        return this.certificationTime;
    }
    public void setCertificationTime(Date certificationTime){

        this.certificationTime = certificationTime;
    }
    @Column(name = "certificationUserId")
    public Long getCertificationUserId(){

        return this.certificationUserId;
    }
    public void setCertificationUserId(Long certificationUserId){

        this.certificationUserId = certificationUserId;
    }
    @Column(name = "isBrand")
    public String getIsBrand(){

        return this.isBrand;
    }
    public void setIsBrand(String isBrand){

        this.isBrand = isBrand;
    }
    @Column(name = "isIdCard")
    public String getIsIdCard(){

        return this.isIdCard;
    }
    public void setIsIdCard(String isIdCard){

        this.isIdCard = isIdCard;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    
    @Column(name = "certificationType") 
	public String getCertificationType() {
		return certificationType;
	}
	public void setCertificationType(String certificationType) {
		this.certificationType = certificationType;
	}
	
	@Column(name = "nstStatus") 
	public String getNstStatus() {
		return nstStatus;
	}
	public void setNstStatus(String nstStatus) {
		this.nstStatus = nstStatus;
	}
	
	@Column(name = "nst_idCard") 
	public String getNst_idCard() {
		return nst_idCard;
	}
	public void setNst_idCard(String nst_idCard) {
		this.nst_idCard = nst_idCard;
	}
	
	@Column(name = "nst_linkMan") 
	public String getNst_linkMan() {
		return nst_linkMan;
	}
	public void setNst_linkMan(String nst_linkMan) {
		this.nst_linkMan = nst_linkMan;
	}
	
	@Column(name = "nst_cardPhotoUrl") 
	public String getNst_cardPhotoUrl() {
		return nst_cardPhotoUrl;
	}
	public void setNst_cardPhotoUrl(String nst_cardPhotoUrl) {
		this.nst_cardPhotoUrl = nst_cardPhotoUrl;
	}
	
	@Column(name = "nst_bzlPhotoUrl") 
	public String getNst_bzlPhotoUrl() {
		return nst_bzlPhotoUrl;
	}
	public void setNst_bzlPhotoUrl(String nst_bzlPhotoUrl) {
		this.nst_bzlPhotoUrl = nst_bzlPhotoUrl;
	}
	
	@Column(name = "nst_ngReason") 
	public String getNst_ngReason() {
		return nst_ngReason;
	}
	public void setNst_ngReason(String nst_ngReason) {
		this.nst_ngReason = nst_ngReason;
	}
    
    
}

