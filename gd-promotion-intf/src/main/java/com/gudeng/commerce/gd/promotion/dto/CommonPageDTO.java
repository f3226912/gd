package com.gudeng.commerce.gd.promotion.dto;

import java.io.Serializable;
import java.util.List;


public class CommonPageDTO<T> implements Serializable {

	private static final long serialVersionUID = 3594414284603482161L;

	private List<T> data;
	
	private int totalRow;

	public CommonPageDTO(){}
	
	public CommonPageDTO(List<T> data,int totalRow){
		this.data=data;
		this.totalRow = totalRow;
	}
	
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	
	
}
