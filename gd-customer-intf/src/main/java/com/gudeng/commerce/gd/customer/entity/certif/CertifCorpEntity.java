package com.gudeng.commerce.gd.customer.entity.certif;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;

@Entity(name = "certif_corp")
public class CertifCorpEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6078350907494788227L;

	/**
    *
    */
    private Integer id;
    /**
    *用户id
    */
    private Integer memberId;
    /**
    *账号
    */
    @ExcelConf(excelHeader="用户账户", sort=1)
    private String account;
    /**
    *合作社
    */
    @ExcelConf(excelHeader="合作社名称", sort=2)
    private String corpName;
    /**
    *邻近市场
    */
    private String markets;
    /**
    *商铺名称
    */
    private String shopName;
    /**
    *主营分类
    */
    private Integer cateId;
    /**
    *店铺id( aim to 主营分类、所在地址 )
    */
    private Integer businessId;
    /**
    *申请认证时间
    */
    private Date commitTime;
    /**
    *供应量
    */
    private Integer stockCount;
    /**
    *供应量单位
    */
    private String units;
    /**
    *营业执照号码
    */
    private String bzl;
    /**
    *营业执照照片
    */
    private String bzlPhotoUrl;
    /**
    *状态(0:待审核1:已认证;2:已驳回)
    */
    private String status;
    /**
    *省
    */
    private Integer province;
    /**
    *市
    */
    private Integer city;
    /**
    *县
    */
    private Integer area;
    /**
    *地址
    */
    private String address;
    /**
    *
    */
    private String createUserId;
    /**
    *
    */
    private Date createTime;
    /**
    *
    */
    private String updateUserId;
    /**
    *
    */
    private Date updateTime;

    private String optionUser ;

    @Column(name = "optionUser")
	public String getOptionUser() {
		return optionUser;
	}
	public void setOptionUser(String optionUser) {
		this.optionUser = optionUser;
	}
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
    @Column(name = "corpName")
    public String getCorpName(){
        return this.corpName;
    }
    public void setCorpName(String corpName){
        this.corpName = corpName;
    }
    @Column(name = "markets")
    public String getMarkets(){
        return this.markets;
    }
    public void setMarkets(String markets){
        this.markets = markets;
    }
    @Column(name = "shopName")
    public String getShopName(){
        return this.shopName;
    }
    public void setShopName(String shopName){
        this.shopName = shopName;
    }
    @Column(name = "cateId")
    public Integer getCateId(){
        return this.cateId;
    }
    public void setCateId(Integer cateId){
        this.cateId = cateId;
    }
    @Column(name = "businessId")
    public Integer getBusinessId(){
        return this.businessId;
    }
    public void setBusinessId(Integer businessId){
        this.businessId = businessId;
    }
    @Column(name = "commitTime")
    public Date getCommitTime(){
        return this.commitTime;
    }
    public void setCommitTime(Date commitTime){
        this.commitTime = commitTime;
    }
    @Column(name = "stockCount")
    public Integer getStockCount(){
        return this.stockCount;
    }
    public void setStockCount(Integer stockCount){
        this.stockCount = stockCount;
    }
    @Column(name = "units")
    public String getUnits(){
        return this.units;
    }
    public void setUnits(String units){
        this.units = units;
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
    @Column(name = "status")
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    @Column(name = "province")
    public Integer getProvince(){
        return this.province;
    }
    public void setProvince(Integer province){
        this.province = province;
    }
    @Column(name = "city")
    public Integer getCity(){
        return this.city;
    }
    public void setCity(Integer city){
        this.city = city;
    }
    @Column(name = "area")
    public Integer getArea(){
        return this.area;
    }
    public void setArea(Integer area){
        this.area = area;
    }
    @Column(name = "address")
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
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
}
