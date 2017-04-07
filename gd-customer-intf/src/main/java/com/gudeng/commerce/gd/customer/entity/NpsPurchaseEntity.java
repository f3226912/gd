package com.gudeng.commerce.gd.customer.entity;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "nps_purchase")
public class NpsPurchaseEntity  implements java.io.Serializable{

	private static final long serialVersionUID = 1661168440128659311L;

	/**
    *ID
    */
    private Integer id;

    /**
    *手机号
    */
    private String mobilePhone;

    /**
     *用户ID
     */
     private Integer memberId;
     
    /**
    *商品名称
    */
    private String goodsName;

    /**
    *提交时间 记录生成的时间
    */
    private Date createTime;

    /**
    *发布时间 记录审核通过的时间
    */
    private Date releaseTime;

    /**
    *状态 1待审核、2审核通过、3已驳回、4用户撤销、5关闭
    */
    private String status;

    /**
    *结束时间 = 发布时间 + 采购时效
    */
    private Date endTime;

    /**
    *采购时效  单位为天
    */
    private Integer purchaseCycle;

    /**
    *采购量
    */
    private Double purchaseCount;

    /**
    *采购单位
    */
    private String purchaseUnit;

    /**
    *规格要求
    */
    private String specRequire;

    /**
    *省份ID
    */
    private String provinceId;

    /**
    *城市ID
    */
    private String cityId;

    /**
    *区县ID
    */
    private String areaId;

    /**
    *省、市、区名称，以逗号分隔
    */
    private String areaName;

    /**
    *心理价位min
    */
    private Double minPrice;

    /**
    *心理价位max
    */
    private Double maxPrice;

    /**
    *商品图片
    */
    private String goodsImg;

    /**
    *其他要求
    */
    private String remark;

    /**
    *累计被访问次数
    */
    private Integer visitCount;

    /**
    *累计报价数
    */
    private Integer priceCount;

    /**
    *审核人
    */
    private String authUserId;
    
    /**
     * 是否删除 isDelete
     */
    private Integer isDelete;

    /**
    *审核时间
    */
    private Date authTime;
    private Date userEndTime;
    private Date userCancelTime;
    /**
    *驳回原因
    */
    private String rejectReason;

    public Date getUserCancelTime() {
		return userCancelTime;
	}
	public void setUserCancelTime(Date userCancelTime) {
		this.userCancelTime = userCancelTime;
	}
	@Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "mobilePhone")
    public String getMobilePhone(){

        return this.mobilePhone;
    }
    public void setMobilePhone(String mobilePhone){

        this.mobilePhone = mobilePhone;
    }
    @Column(name = "goodsName")
    public String getGoodsName(){

        return this.goodsName;
    }
    public void setGoodsName(String goodsName){

        this.goodsName = goodsName;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "releaseTime")
    public Date getReleaseTime(){

        return this.releaseTime;
    }
    public void setReleaseTime(Date releaseTime){

        this.releaseTime = releaseTime;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
    @Column(name = "endTime")
    public Date getEndTime(){

        return this.endTime;
    }
    public void setEndTime(Date endTime){

        this.endTime = endTime;
    }
    @Column(name = "purchaseCycle")
    public Integer getPurchaseCycle(){

        return this.purchaseCycle;
    }
    public void setPurchaseCycle(Integer purchaseCycle){

        this.purchaseCycle = purchaseCycle;
    }
    
    @Column(name = "purchaseUnit")
    public String getPurchaseUnit(){

        return this.purchaseUnit;
    }
    public void setPurchaseUnit(String purchaseUnit){

        this.purchaseUnit = purchaseUnit;
    }
    @Column(name = "specRequire")
    public String getSpecRequire(){

        return this.specRequire;
    }
    public void setSpecRequire(String specRequire){

        this.specRequire = specRequire;
    }
    @Column(name = "provinceId")
    public String getProvinceId(){

        return this.provinceId;
    }
    public void setProvinceId(String provinceId){

        this.provinceId = provinceId;
    }
    @Column(name = "cityId")
    public String getCityId(){

        return this.cityId;
    }
    public void setCityId(String cityId){

        this.cityId = cityId;
    }
    @Column(name = "areaId")
    public String getAreaId(){

        return this.areaId;
    }
    public void setAreaId(String areaId){

        this.areaId = areaId;
    }
    @Column(name = "areaName")
    public String getAreaName(){

        return this.areaName;
    }
    public void setAreaName(String areaName){

        this.areaName = areaName;
    }
    
    @Column(name = "goodsImg")
    public String getGoodsImg(){

        return this.goodsImg;
    }
    public void setGoodsImg(String goodsImg){

        this.goodsImg = goodsImg;
    }
    @Column(name = "remark")
    public String getRemark(){

        return this.remark;
    }
    public void setRemark(String remark){

        this.remark = remark;
    }
    public Integer getVisitCount(){

        return this.visitCount;
    }
    public void setVisitCount(Integer visitCount){

        this.visitCount = visitCount;
    }
    public Integer getPriceCount(){

        return this.priceCount;
    }
    public void setPriceCount(Integer priceCount){

        this.priceCount = priceCount;
    }
    @Column(name = "authUserId")
    public String getAuthUserId(){

        return this.authUserId;
    }
    public void setAuthUserId(String authUserId){

        this.authUserId = authUserId;
    }
    @Column(name = "authTime")
    public Date getAuthTime(){

        return this.authTime;
    }
    public void setAuthTime(Date authTime){

        this.authTime = authTime;
    }
    @Column(name = "rejectReason")
    public String getRejectReason(){

        return this.rejectReason;
    }
    public void setRejectReason(String rejectReason){

        this.rejectReason = rejectReason;
    }
    @Column(name = "memberId")
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	 @Column(name = "userEndTime")
	public Date getUserEndTime() {
		return userEndTime;
	}
	public void setUserEndTime(Date userEndTime) {
		this.userEndTime = userEndTime;
	}
    public Integer getIsDelete() {
      return isDelete;
    }
    public void setIsDelete(Integer isDelete) {
      this.isDelete = isDelete;
    }
    @Column(name = "purchaseCount")
	public Double getPurchaseCount() {
		return purchaseCount;
	}
	public void setPurchaseCount(Double purchaseCount) {
		this.purchaseCount = purchaseCount;
	}
	 @Column(name = "minPrice")
	public Double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	@Column(name = "maxPrice")
	public Double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}
    
    
}