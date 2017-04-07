package com.gudeng.commerce.gd.api.dto.input;

import java.io.Serializable;


/**
 * 搜索输入类
 * @date 2016年10月18日
 */
public class SearchInputDTO implements Serializable {
	
	private static final long serialVersionUID = 1401558300768296637L;
	//查询类型 1商品 (默认) 2商铺
	private String type;
	//用户id
	private String memberId;
	//查询关键字
	private String keyWord;
	//价格排序 空(默认) asc升序 desc降序
	private String priceSort;
	//产地省id
	private String productProvinceId;
	//产地市id
	private String productCityId;
	//产地区县id
	private String productAreaId;
	//商铺所在省id
	private String shopProvinceId;
	//商铺所在市id
	private String shopCityId;
	//商铺所在区县id
	private String shopAreaId;
	//经营类型 0全部 1种养大户，2合作社，3基地
	private String managementType;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPriceSort() {
		return priceSort;
	}

	public void setPriceSort(String priceSort) {
		this.priceSort = priceSort;
	}

	public String getProductCityId() {
		return productCityId;
	}

	public void setProductCityId(String productCityId) {
		this.productCityId = productCityId;
	}

	public String getShopCityId() {
		return shopCityId;
	}

	public void setShopCityId(String shopCityId) {
		this.shopCityId = shopCityId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getProductAreaId() {
		return productAreaId;
	}

	public void setProductAreaId(String productAreaId) {
		this.productAreaId = productAreaId;
	}

	public String getShopAreaId() {
		return shopAreaId;
	}

	public void setShopAreaId(String shopAreaId) {
		this.shopAreaId = shopAreaId;
	}

	public String getManagementType() {
		return managementType;
	}

	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}

	public String getProductProvinceId() {
		return productProvinceId;
	}

	public void setProductProvinceId(String productProvinceId) {
		this.productProvinceId = productProvinceId;
	}

	public String getShopProvinceId() {
		return shopProvinceId;
	}

	public void setShopProvinceId(String shopProvinceId) {
		this.shopProvinceId = shopProvinceId;
	}
}
