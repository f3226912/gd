package com.gudeng.commerce.gd.bi.dto;

import com.gudeng.commerce.gd.bi.entity.GrdProSupplyofgoodHandoutEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProSupplyofgoodHandoutDTO extends GrdProSupplyofgoodHandoutEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1433287620515152005L;
	private String publisherTimeString;
	private String statusString;
	private String sourceTypeStr;
	public String getPublisherTimeString() {
		return publisherTimeString;
	}
	public void setPublisherTimeString(String publisherTimeString) {
		this.publisherTimeString = publisherTimeString;
	}
	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	public String getSourceTypeStr() {
		return sourceTypeStr;
	}
	public void setSourceTypeStr(String sourceTypeStr) {
		this.sourceTypeStr = sourceTypeStr;
	}
	
	
}