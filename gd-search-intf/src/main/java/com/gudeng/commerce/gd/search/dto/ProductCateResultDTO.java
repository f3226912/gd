package com.gudeng.commerce.gd.search.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * 产品solr搜索查询结果集合
 * 
 * 包含 产品基础信息  ，ProductSolrDTO 
 * 
 * 和总共个数 count
 * 
 */
public class ProductCateResultDTO   implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2886557928052104714L;
	
	public Boolean  isChecked;//是否被选中
	public String cateId;//分类ID
	public String cateName;//分类名称

	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
