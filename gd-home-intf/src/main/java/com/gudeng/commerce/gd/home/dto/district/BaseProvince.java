package com.gudeng.commerce.gd.home.dto.district;

import java.io.Serializable;

/**
 * @Description 页面省市区县所需json格式
 * @Project gd-home-intf
 * @ClassName DistrictProvince.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年11月18日 下午4:39:34
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public class BaseProvince implements Serializable {
	private static final long serialVersionUID = -1632207780529801141L;
	private String id;// 省ID
	private String updateTime;// 修改时间
	private String provinceName;// 省名称
	private String indexId;// 排序

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

}
