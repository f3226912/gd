package com.gudeng.commerce.gd.api.dto.output;

import java.util.List;
import java.util.Map;

/**
 * 所有省份城市DTO
 * @author TerryZhang
 *
 */
public class AreaListAppDTO {

	/**
	 * 父节点id
	 */
	private String parentId;
	
	/**
	 * 父节点名
	 */
	private String parentName;

	/**
	 * 子节点集合(含节点id, 节点名)
	 */
	private List<Map<String, String>> childAreas;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<Map<String, String>> getChildAreas() {
		return childAreas;
	}

	public void setChildAreas(List<Map<String, String>> childAreas) {
		this.childAreas = childAreas;
	}
}
