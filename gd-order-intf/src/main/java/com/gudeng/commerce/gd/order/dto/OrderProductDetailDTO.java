package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;

public class OrderProductDetailDTO extends OrderProductDetailEntity  implements Serializable {

	private static final long serialVersionUID = -2818660620810850599L;

	private String imageUrl;
	
	private String hasBuySub; // 是否有买家补贴 1有 0无
	
	private String hasSellSub; // 是否有农批商补贴  1有 0无
	
	private String hasSuppSub; // 是否有采购商补贴  1有 0无
	
	/**  产品单位*/
	private String unitName;
	
	/** 产品分类名称  */
	private String cateName;
	
	/** 格式化后价格 */
	private String formattedPrice;
	
	private Double goodsSubAmtTemp; // 商品补贴额，计算补贴时临时用
	
	private Double actPrice;
	
	private Integer memberAddressId; // 发货物流ID
	
	private Integer actId;  		//活动id
	
	private String  shopsName;			//商铺名称
	private String 	shopMainCateName;	//商铺主营分类
	private String  shopOtherCateName;	//商铺兼营其他分类

	public Double getActPrice() {
		return actPrice;
	}

	public void setActPrice(Double actPrice) {
		this.actPrice = actPrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getHasBuySub() {
		return hasBuySub;
	}

	public void setHasBuySub(String hasBuySub) {
		this.hasBuySub = hasBuySub;
	}

	public String getHasSellSub() {
		return hasSellSub;
	}

	public void setHasSellSub(String hasSellSub) {
		this.hasSellSub = hasSellSub;
	}

	public String getHasSuppSub() {
		return hasSuppSub;
	}

	public void setHasSuppSub(String hasSuppSub) {
		this.hasSuppSub = hasSuppSub;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getGoodsSubAmtTemp() {
		return goodsSubAmtTemp;
	}

	public void setGoodsSubAmtTemp(Double goodsSubAmtTemp) {
		this.goodsSubAmtTemp = goodsSubAmtTemp;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getMemberAddressId() {
		return memberAddressId;
	}

	public void setMemberAddressId(Integer memberAddressId) {
		this.memberAddressId = memberAddressId;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getShopMainCateName() {
		return shopMainCateName;
	}

	public void setShopMainCateName(String shopMainCateName) {
		this.shopMainCateName = shopMainCateName;
	}

	public String getShopOtherCateName() {
		return shopOtherCateName;
	}

	public void setShopOtherCateName(String shopOtherCateName) {
		this.shopOtherCateName = shopOtherCateName;
	}
	
	
}
