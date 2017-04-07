package com.gudeng.commerce.gd.customer.entity.certif;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;

@Entity(name = "certif_shop")
public class CertifShopEntity  implements java.io.Serializable{
    /**
    *
    */
    private Integer id;
    /**
    *用户Id，关联去用户数据
    */
    private Integer memberId;
    /**
    *用户账号
    */
    @ExcelConf(excelHeader="账号", sort = 1)
    private String account;
    /**
    *商铺Id，关联去商铺数据
    */
    private Integer businessId;
    /**
    *线上商铺名称
    */
    @ExcelConf(excelHeader="商铺名称", sort = 2)
    private String shopName;
    /**
    *线下实体商铺名称
    */
    private String realShopName;
    /**
    *实体商铺经营者的姓名
    */
    private String operatorName;
    /**
    *主营分类
    */
    private Integer cateId;
    /**
    *线上商铺所属市场Id
    */
    private Integer marketId;
    /**
    *pos终端号
    */
    private String posNo;
    /**
    *智能秤（MAC地址）
    */
    private String macNo;
    /**
    *商铺地址
    */
	@ExcelConf(excelHeader="商铺地址", sort = 6)
    private String address;
    /**
    *提交时间
    */
	@ExcelConf(excelHeader="申请时间", sort = 3)
    private Date commitTime;
    /**
    *租赁合同照片
    */
    private String contractImg;
    /**
    *状态(0:待审核1:已认证;2:已驳回)
    */
    private String status;
    /**
    *记录创建时间
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
	@ExcelConf(excelHeader="审核员", sort = 7)
    private String optionUser;
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
    @Column(name = "businessId")
    public Integer getBusinessId(){
        return this.businessId;
    }
    public void setBusinessId(Integer businessId){
        this.businessId = businessId;
    }
    @Column(name = "shopName")
    public String getShopName(){
        return this.shopName;
    }
    public void setShopName(String shopName){
        this.shopName = shopName;
    }
    @Column(name = "realShopName")
    public String getRealShopName(){
        return this.realShopName;
    }
    public void setRealShopName(String realShopName){
        this.realShopName = realShopName;
    }
    @Column(name = "operatorName")
    public String getOperatorName(){
        return this.operatorName;
    }
    public void setOperatorName(String operatorName){
        this.operatorName = operatorName;
    }
    @Column(name = "cateId")
    public Integer getCateId(){
        return this.cateId;
    }
    public void setCateId(Integer cateId){
        this.cateId = cateId;
    }
    @Column(name = "marketId")
    public Integer getMarketId(){
        return this.marketId;
    }
    public void setMarketId(Integer marketId){
        this.marketId = marketId;
    }
    @Column(name = "posNo")
    public String getPosNo(){
        return this.posNo;
    }
    public void setPosNo(String posNo){
        this.posNo = posNo;
    }
    @Column(name = "macNo")
    public String getMacNo(){
        return this.macNo;
    }
    public void setMacNo(String macNo){
        this.macNo = macNo;
    }
    @Column(name = "address")
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    @Column(name = "commitTime")
    public Date getCommitTime(){
        return this.commitTime;
    }
    public void setCommitTime(Date commitTime){
        this.commitTime = commitTime;
    }
    @Column(name = "contractImg")
    public String getContractImg(){
        return this.contractImg;
    }
    public void setContractImg(String contractImg){
        this.contractImg = contractImg;
    }
    @Column(name = "status")
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
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
}
