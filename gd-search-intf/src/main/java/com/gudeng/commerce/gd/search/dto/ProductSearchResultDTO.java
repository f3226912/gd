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
public class ProductSearchResultDTO   implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2886557928052104714L;
	
	public Long count;
	
	public List<ProductSolrDTO> list=new ArrayList<ProductSolrDTO>() ;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<ProductSolrDTO> getList() {
		return list;
	}

	public void setList(List<ProductSolrDTO> list) {
		this.list = list;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
