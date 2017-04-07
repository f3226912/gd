package com.gudeng.commerce.gd.promotion.dto;

import java.io.Serializable;

public class PageDTO implements Serializable {

	private static final long serialVersionUID = -5290477031747093045L;
	/**当前页*/
	private int currentPage;
	/**每页条数*/
	private int pageSize;

	public PageDTO(){}
	
	public PageDTO(int page,int size){
		this.currentPage = page;
		this.pageSize = size;
	}
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
