package com.gudeng.commerce.gd.customer.entity.certif;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;

@Entity(name = "certif_sp_product")
public class CertifSpProductEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6857948158463216733L;

	/**
    *
    */
    private Integer id;
    /**
    *用户id
    */
    private Integer memberId;
    /**
    *用户账户
    */
    @ExcelConf(excelHeader="账号", sort = 1)
    private String account;
    /**
    *商品Id
    */
    private Integer productId;
    /**
    *商品名称
    */
    private String productName;
    /**
    *商品图片
    */
    private String productImg;
    /**
    *认证机构
    */
    private String certifOrg;
    /**
    *产品标识名称
    */
    @ExcelConf(excelHeader="产品标识名称 ", sort = 2)
    private String signs;
    /**
    *企业名称
    */
    private String companyName;
    /**
    *申请认证时间
    */
    private Date commitTime;
    /**
    *状态(0:待审核1:已认证;2:已驳回)
    */
    private String status;
    /**
    *商标
    */
    private String brand;
    /**
    *供应量
    */
    private Integer stockCount;
    /**
    *供应量单位
    */
    private String units;
    /**
    *证书编号
    */
    private String certifNo;
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
    *专用标志图片
    */
    private String specialImg;
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
    /**
    *记录当前操作的操作员是谁，取管理后台用户的姓名
    */
	@ExcelConf(excelHeader="审核员", sort = 5)
    private String optionUser;
    /**
    *商铺名称
    */
    private String shopsName;
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
    @Column(name = "productId")
    public Integer getProductId(){
        return this.productId;
    }
    public void setProductId(Integer productId){
        this.productId = productId;
    }
    @Column(name = "productName")
    public String getProductName(){
        return this.productName;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    @Column(name = "productImg")
    public String getProductImg(){
        return this.productImg;
    }
    public void setProductImg(String productImg){
        this.productImg = productImg;
    }
    @Column(name = "certifOrg")
    public String getCertifOrg(){
        return this.certifOrg;
    }
    public void setCertifOrg(String certifOrg){
        this.certifOrg = certifOrg;
    }
    @Column(name = "signs")
    public String getSigns(){
        return this.signs;
    }
    public void setSigns(String signs){
        this.signs = signs;
    }
    @Column(name = "companyName")
    public String getCompanyName(){
        return this.companyName;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
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
    @Column(name = "brand")
    public String getBrand(){
        return this.brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
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
    @Column(name = "certifNo")
    public String getCertifNo(){
        return this.certifNo;
    }
    public void setCertifNo(String certifNo){
        this.certifNo = certifNo;
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
    @Column(name = "specialImg")
    public String getSpecialImg(){
        return this.specialImg;
    }
    public void setSpecialImg(String specialImg){
        this.specialImg = specialImg;
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
    @Column(name = "optionUser")
    public String getOptionUser(){
        return this.optionUser;
    }
    public void setOptionUser(String optionUser){
        this.optionUser = optionUser;
    }
    @Column(name = "shopsName")
    public String getShopsName(){
        return this.shopsName;
    }
    public void setShopsName(String shopsName){
        this.shopsName = shopsName;
    }
}
