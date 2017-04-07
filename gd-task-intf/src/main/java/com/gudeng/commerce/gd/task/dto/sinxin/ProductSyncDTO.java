package com.gudeng.commerce.gd.task.dto.sinxin;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Description: TODO(商品同步DTO)
 * @author mpan
 * @date 2016年3月24日 下午4:02:40
 */
public class ProductSyncDTO implements Serializable {

	private static final long serialVersionUID = 2037326014557240300L;

	@JSONField(name = "CdOId")
	public Long cdOId; // 商品ID

	@JSONField(name = "Defaultprice")
	public String defaultprice; // 商品单价

	@JSONField(name = "Unit")
	public String unit; // 单位 传 斤、公斤等

	@JSONField(name = "CdCode")
	public String cdCode; // 检索快捷编码，可以不传，默认商品ID

	@JSONField(name = "CdName")
	public String cdName; // 商品名称

	@JSONField(name = "Status")
	public Integer status; // 0正常，1停用

	@JSONField(name = "SellerId")
	public String sellerId; // 秤ID

	public Long getCdOId() {
		return cdOId;
	}

	public void setCdOId(Long cdOId) {
		this.cdOId = cdOId;
	}

	public String getDefaultprice() {
		return defaultprice;
	}

	public void setDefaultprice(String defaultprice) {
		this.defaultprice = defaultprice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCdCode() {
		return cdCode;
	}

	public void setCdCode(String cdCode) {
		this.cdCode = cdCode;
	}

	public String getCdName() {
		return cdName;
	}

	public void setCdName(String cdName) {
		this.cdName = cdName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

}
