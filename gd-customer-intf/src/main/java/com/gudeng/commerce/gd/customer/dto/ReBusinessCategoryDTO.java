package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.customer.entity.ReBusinessCategoryEntity;


public class ReBusinessCategoryDTO extends ReBusinessCategoryEntity  implements Serializable, Comparator{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8630943943170623417L;

	
	private String cateName;

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
	 
}
