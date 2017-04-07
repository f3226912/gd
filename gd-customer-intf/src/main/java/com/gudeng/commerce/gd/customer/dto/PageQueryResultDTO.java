package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果对象
 * @author TerryZhang
 */
public class PageQueryResultDTO<T> implements Serializable{

	private static final long serialVersionUID = 8194382539327717785L;

	/**
	 * 分页的数据
	 */
	private List<T> dataList;
	
	/**
	 * 数据的总数
	 */
	private Integer totalCount = 0;

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
}
