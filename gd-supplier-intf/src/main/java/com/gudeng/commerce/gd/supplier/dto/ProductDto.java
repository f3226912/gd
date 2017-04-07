package com.gudeng.commerce.gd.supplier.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import com.gudeng.commerce.gd.supplier.entity.ProductEntity;

public class ProductDto extends ProductEntity  implements Serializable, Comparator<Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1954007538434162647L;
	
	private String cateName;
	
	private String  shopsName;
	
	private String  provinceName;
	
	private String  cityName;
	
	private String  areaName;
	
	private String  realName;
	
	private String  account;
	
	private String  nickName;
	
	private String unitName;
	
	private String sysUserCode;
	
	private String formattedPrice;
	
	/**
	 * 是否有买家补贴 1有 0无
	 */
	private String hasBuySub; 
	
	/**
	 * 是否有农批商补贴  1有 0无
	 */
	private String hasSellSub;
	/**
	 * 会员id
	 */
	private Long memberId;
	
	/**
	 * 审核信息
	 */
	private List<Object> auditInfos;
	
	/**
	 * app列表展示用的价格
	 */
	private String showedPrice ;
	
	private String updatePriceTimeString;

	private String originProvinceName;
	private String originCityName;
	private String originAreaName;
	
	private String shopsUrl;
	
	private String marketName;
	
	private Long businessUserId;
	
	private String mobile;
	
	private String managementType;
	
	private String mainProduct;
	
	private String certifstatus ;
	
	/**
	 * 广告名称
	 */
	private String adName;
	
	//是否参加活动，0：否，1 是
	private Integer promotion = 0;
	
	//活动开始时间
	private String promotionStartTime;
	
	//活动结束时间
	private String promotionEndTime;
	
	private String imgHost;
	
	public String getCertifstatus() {
		return certifstatus;
	}
	public void setCertifstatus(String certifstatus) {
		this.certifstatus = certifstatus;
	}
	public String getImgHost() {
		return imgHost;
	}
	public void setImgHost(String imgHost) {
		this.imgHost = imgHost;
	}
	public Integer getPromotion() {
		return promotion;
	}
	public void setPromotion(Integer promotion) {
		this.promotion = promotion;
	}
	public String getShowedPrice() {
		return showedPrice;
	}
	public void setShowedPrice(String showedPrice) {
		this.showedPrice = showedPrice;
	}
	public String getOriginProvinceName() {
		return originProvinceName;
	}
	public void setOriginProvinceName(String originProvinceName) {
		this.originProvinceName = originProvinceName;
	}

	public String getOriginCityName() {
		return originCityName;
	}
	public void setOriginCityName(String originCityName) {
		this.originCityName = originCityName;
	}
	public String getOriginAreaName() {
		return originAreaName;
	}

	public void setOriginAreaName(String originAreaName) {
		this.originAreaName = originAreaName;
	}

	private List<ProductPictureDto> pictures ;
	
	public List<ProductPictureDto> getPictures() {
		return pictures;
	}

	public void setPictures(List<ProductPictureDto> pictures) {
		this.pictures = pictures;
	}

	public List<Object> getAuditInfos() {
		return auditInfos;
	}

	public void setAuditInfos(List<Object> auditInfos) {
		this.auditInfos = auditInfos;
	}

	public String getHasSellSub() {
		return hasSellSub;
	}

	public void setHasSellSub(String hasSellSub) {
		this.hasSellSub = hasSellSub;
	}

	public String getHasBuySub() {
		return hasBuySub;
	}

	public void setHasBuySub(String hasBuySub) {
		this.hasBuySub = hasBuySub;
	}

	public String getSysUserCode() {
		return sysUserCode;
	}

	public void setSysUserCode(String sysUserCode) {
		this.sysUserCode = sysUserCode;
	}

	/**
	 * 200图片链接
	 */
	private String url170;
	
	/**
	 * 400图片链接
	 */
	private String url400;
	
	/**
	 * 商铺列表图片地址
	 */
	private String imageUrl;

	public String getUrl170() {
		return url170;
	}
	public void setUrl170(String url170) {
		this.url170 = url170;
	}
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUrl400() {
		return url400;
	}

	public void setUrl400(String url400) {
		this.url400 = url400;
	}

	private Integer  hasFocused=0;
	
	
	public Integer getHasFocused() {
		return hasFocused;
	}

	public void setHasFocused(Integer hasFocused) {
		this.hasFocused = hasFocused;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	private String expirationDateString;
	
	private String createTimeString;
	
	private String updateTimeString;

    /**
     *  产品原图Url
     */
    private String urlOrg;
	
	
	public String getUrlOrg() {
		return urlOrg;
	}

	public void setUrlOrg(String urlOrg) {
		this.urlOrg = urlOrg;
	}

	public String getExpirationDateString() {
		return expirationDateString;
	}

	public void setExpirationDateString(String expirationDateString) {
		this.expirationDateString = expirationDateString;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public String getUpdateTimeString() {
		return updateTimeString;
	}

	public void setUpdateTimeString(String updateTimeString) {
		this.updateTimeString = updateTimeString;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	@Override
	public int compare(Object o1, Object o2) {
		return 0;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUpdatePriceTimeString() {
		return updatePriceTimeString;
	}
	public void setUpdatePriceTimeString(String updatePriceTimeString) {
		this.updatePriceTimeString = updatePriceTimeString;
	}
	public String getShopsUrl() {
		return shopsUrl;
	}
	public void setShopsUrl(String shopsUrl) {
		this.shopsUrl = shopsUrl;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public Long getBusinessUserId() {
		return businessUserId;
	}
	public void setBusinessUserId(Long businessUserId) {
		this.businessUserId = businessUserId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getManagementType() {
		return managementType;
	}
	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}
	public String getMainProduct() {
		return mainProduct;
	}
	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}
   public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getPromotionStartTime() {
		return promotionStartTime;
	}
	public void setPromotionStartTime(String promotionStartTime) {
		this.promotionStartTime = promotionStartTime;
	}
	public String getPromotionEndTime() {
		return promotionEndTime;
	}
	public void setPromotionEndTime(String promotionEndTime) {
		this.promotionEndTime = promotionEndTime;
	}
}
