package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GdActActivityUserRuleEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GdActActivityUserRuleDTO extends GdActActivityUserRuleEntity {

	private static final long serialVersionUID = 8446247887785392031L;

	/**
	 * 活动名称
	 */
	private String actName;
	
	/**
	 * 商品id
	 */
	private Integer productId;

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}