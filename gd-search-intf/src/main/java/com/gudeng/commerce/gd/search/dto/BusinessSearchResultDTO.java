package com.gudeng.commerce.gd.search.dto;

import java.util.List;


/**
 * 产品solr搜索查询结果集合
 * 
 * 包含 产品基础信息  ，BusinessSolrDTO
 * 
 * 和总共个数 count
 * 
 */
public class BusinessSearchResultDTO   implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2706888605461432069L;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<BusinessSolrDTO> getList() {
		return list;
	}

	public void setList(List<BusinessSolrDTO> list) {
		this.list = list;
	}

	public Long count;
	
	public List<BusinessSolrDTO> list;


}
