package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "product_baseinfo")
public class ProductBaseinfoEntity  implements java.io.Serializable{
	
	private static final long serialVersionUID = 2003616909244697740L;

	/**
    *产品ID,主键
    */
    private Long productId;

    /**
    *产品分类ID
    */
    private Long cateId;

    /**
    *商铺ID
    */
    private Long businessId;

    /**
    *市场ID
    */
    private Long marketId;

    /**
    *产品名称
    */
    private String name;

    /**
    *价格（取价格最小的记录)
    */
    private Double price;

    /**
    *价格类型(0-单价, 1-区间价格)
    */
    private String priceType;

    /**
    *状态(0.草稿;1.待审核;2.审核不通过;3.已上架;4.已下架;5.已删除;6:历史版本)
    */
    private String state;

    /**
    *单位(dict.dict and dictType='unit')
    */
    private String unit;

    /**
    *省份ID
    */
    private Long provinceId;

    /**
    *城市ID
    */
    private Long cityId;

    /**
    *区县ID
    */
    private Long areaId;

    /**
    *详细地址
    */
    private String address;

    /**
    *到期时间
    */
    private Date expirationDate;

    /**
    *信息有效期(数据字典: dict.id and dictType='infoLiftDay')
    */
    private String infoLifeDay;

    /**
    *创建人员ID
    */
    private String createUserId;

    /**
    *创建时间
    */
    private Date createTime;

    /**
    *修改人员ID
    */
    private String updateUserId;

    /**
    *修改时间
    */
    private Date updateTime;

    /**
    *说明
    */
    private String description;

    /**
    *备注
    */
    private String content;

    /**
    *类似别名，用于检索使用
    */
    private String keyword;

    /**
    *点击量
    */
    private Long visitCount;

    /**
    *物流说明
    */
    private String logisticsRemark;

    /**
    *库存数量
    */
    private Double stockCount;

    /**
    *最小起订量
    */
    private Double minBuyCount;

    /**
    *发布渠道
    */
    private String channel;

    /**
    *价格变动时间
    */
    private Date updatePriceTime;

    /**
    *角色类型(4-产地供应商)
    */
    private String roleType;

    /**
    *标志关键内容是否变更, 0-未变更, 1-已变更
    */
    private String editSign;

    /**
    *产地-省
    */
    private Long originProvinceId;

    /**
    *产地-市
    */
    private Long originCityId;

    /**
    *商品标签
    */
    private String productSign;

    /**
    *产地-县
    */
    private Long originAreaId;

    @Id
    @Column(name = "productId")
    public Long getProductId(){

        return this.productId;
    }
    public void setProductId(Long productId){

        this.productId = productId;
    }
    @Column(name = "cateId")
    public Long getCateId(){

        return this.cateId;
    }
    public void setCateId(Long cateId){

        this.cateId = cateId;
    }
    @Column(name = "businessId")
    public Long getBusinessId(){

        return this.businessId;
    }
    public void setBusinessId(Long businessId){

        this.businessId = businessId;
    }
    @Column(name = "marketId")
    public Long getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Long marketId){

        this.marketId = marketId;
    }
    @Column(name = "name")
    public String getName(){

        return this.name;
    }
    public void setName(String name){

        this.name = name;
    }
    @Column(name = "price")
    public Double getPrice(){

        return this.price;
    }
    public void setPrice(Double price){

        this.price = price;
    }
    @Column(name = "priceType")
    public String getPriceType(){

        return this.priceType;
    }
    public void setPriceType(String priceType){

        this.priceType = priceType;
    }
    @Column(name = "state")
    public String getState(){

        return this.state;
    }
    public void setState(String state){

        this.state = state;
    }
    @Column(name = "unit")
    public String getUnit(){

        return this.unit;
    }
    public void setUnit(String unit){

        this.unit = unit;
    }
    @Column(name = "provinceId")
    public Long getProvinceId(){

        return this.provinceId;
    }
    public void setProvinceId(Long provinceId){

        this.provinceId = provinceId;
    }
    @Column(name = "cityId")
    public Long getCityId(){

        return this.cityId;
    }
    public void setCityId(Long cityId){

        this.cityId = cityId;
    }
    @Column(name = "areaId")
    public Long getAreaId(){

        return this.areaId;
    }
    public void setAreaId(Long areaId){

        this.areaId = areaId;
    }
    @Column(name = "address")
    public String getAddress(){

        return this.address;
    }
    public void setAddress(String address){

        this.address = address;
    }
    @Column(name = "expirationDate")
    public Date getExpirationDate(){

        return this.expirationDate;
    }
    public void setExpirationDate(Date expirationDate){

        this.expirationDate = expirationDate;
    }
    @Column(name = "infoLifeDay")
    public String getInfoLifeDay(){

        return this.infoLifeDay;
    }
    public void setInfoLifeDay(String infoLifeDay){

        this.infoLifeDay = infoLifeDay;
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
    @Column(name = "description")
    public String getDescription(){

        return this.description;
    }
    public void setDescription(String description){

        this.description = description;
    }
    @Column(name = "content")
    public String getContent(){

        return this.content;
    }
    public void setContent(String content){

        this.content = content;
    }
    @Column(name = "keyword")
    public String getKeyword(){

        return this.keyword;
    }
    public void setKeyword(String keyword){

        this.keyword = keyword;
    }
    @Column(name = "visitCount")
    public Long getVisitCount(){

        return this.visitCount;
    }
    public void setVisitCount(Long visitCount){

        this.visitCount = visitCount;
    }
    @Column(name = "logisticsRemark")
    public String getLogisticsRemark(){

        return this.logisticsRemark;
    }
    public void setLogisticsRemark(String logisticsRemark){

        this.logisticsRemark = logisticsRemark;
    }
    @Column(name = "stockCount")
    public Double getStockCount(){

        return this.stockCount;
    }
    public void setStockCount(Double stockCount){

        this.stockCount = stockCount;
    }
    @Column(name = "minBuyCount")
    public Double getMinBuyCount(){

        return this.minBuyCount;
    }
    public void setMinBuyCount(Double minBuyCount){

        this.minBuyCount = minBuyCount;
    }
    @Column(name = "channel")
    public String getChannel(){

        return this.channel;
    }
    public void setChannel(String channel){

        this.channel = channel;
    }
    @Column(name = "updatePriceTime")
    public Date getUpdatePriceTime(){

        return this.updatePriceTime;
    }
    public void setUpdatePriceTime(Date updatePriceTime){

        this.updatePriceTime = updatePriceTime;
    }
    @Column(name = "roleType")
    public String getRoleType(){

        return this.roleType;
    }
    public void setRoleType(String roleType){

        this.roleType = roleType;
    }
    @Column(name = "editSign")
    public String getEditSign(){

        return this.editSign;
    }
    public void setEditSign(String editSign){

        this.editSign = editSign;
    }
    @Column(name = "originProvinceId")
    public Long getOriginProvinceId(){

        return this.originProvinceId;
    }
    public void setOriginProvinceId(Long originProvinceId){

        this.originProvinceId = originProvinceId;
    }
    @Column(name = "originCityId")
    public Long getOriginCityId(){

        return this.originCityId;
    }
    public void setOriginCityId(Long originCityId){

        this.originCityId = originCityId;
    }
    @Column(name = "productSign")
    public String getProductSign(){

        return this.productSign;
    }
    public void setProductSign(String productSign){

        this.productSign = productSign;
    }
    @Column(name = "originAreaId")
    public Long getOriginAreaId(){

        return this.originAreaId;
    }
    public void setOriginAreaId(Long originAreaId){

        this.originAreaId = originAreaId;
    }
}
