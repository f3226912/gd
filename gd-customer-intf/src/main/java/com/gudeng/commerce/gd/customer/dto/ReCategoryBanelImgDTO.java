package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.customer.entity.ReCategoryBanelImgEntity;

public class ReCategoryBanelImgDTO extends ReCategoryBanelImgEntity  implements Serializable, Comparator{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6825798221456127159L;

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private String cateName;
	

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

}
