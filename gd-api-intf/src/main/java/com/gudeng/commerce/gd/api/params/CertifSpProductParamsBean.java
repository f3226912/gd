package com.gudeng.commerce.gd.api.params;

import java.util.Date;

public class CertifSpProductParamsBean  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6701373973105419837L;

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
    private String optionUser;
    /**
    *商铺名称
    */
    private String shopsName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getCertifOrg() {
		return certifOrg;
	}

	public void setCertifOrg(String certifOrg) {
		this.certifOrg = certifOrg;
	}

	public String getSigns() {
		return signs;
	}

	public void setSigns(String signs) {
		this.signs = signs;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getCertifNo() {
		return certifNo;
	}

	public void setCertifNo(String certifNo) {
		this.certifNo = certifNo;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSpecialImg() {
		return specialImg;
	}

	public void setSpecialImg(String specialImg) {
		this.specialImg = specialImg;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOptionUser() {
		return optionUser;
	}

	public void setOptionUser(String optionUser) {
		this.optionUser = optionUser;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

}
