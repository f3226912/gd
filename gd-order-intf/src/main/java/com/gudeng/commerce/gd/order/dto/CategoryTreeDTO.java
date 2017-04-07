package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Semon
 *
 */
public class CategoryTreeDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8631960895172362861L;
	
	private String name;
	private Long categoryId;
	private  int curLevel;
	private Long parentId;
	private boolean open=true;
	private Set<CategoryTreeDTO> children;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getCurLevel() {
		return curLevel;
	}
	public void setCurLevel(int curLevel) {
		this.curLevel = curLevel;
	}

	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}

	public Set<CategoryTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(Set<CategoryTreeDTO> children) {
		this.children = children;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	
	
}
