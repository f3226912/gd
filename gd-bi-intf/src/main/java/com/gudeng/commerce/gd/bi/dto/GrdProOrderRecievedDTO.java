package com.gudeng.commerce.gd.bi.dto;


import com.gudeng.commerce.gd.bi.entity.GrdProOrderRecievedEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProOrderRecievedDTO extends GrdProOrderRecievedEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8494727365817750983L;
	
   /**
    *发布时间
    */
    private String publisherTimeString;

    /**
    *接单时间
    */
    private String recieveTimeString;
    
    /**
     *确认时间
     */
     private String confirmTimeString;
     
     private String confirmStatusStr;
     
     private String sourceTypeStr;
     
     private String totalWeightStr;

	public String getPublisherTimeString() {
		return publisherTimeString;
	}

	public void setPublisherTimeString(String publisherTimeString) {
		this.publisherTimeString = publisherTimeString;
	}

	public String getRecieveTimeString() {
		return recieveTimeString;
	}

	public void setRecieveTimeString(String recieveTimeString) {
		this.recieveTimeString = recieveTimeString;
	}

	public String getConfirmTimeString() {
		return confirmTimeString;
	}

	public void setConfirmTimeString(String confirmTimeString) {
		this.confirmTimeString = confirmTimeString;
	}

	public String getConfirmStatusStr() {
		return confirmStatusStr;
	}

	public void setConfirmStatusStr(String confirmStatusStr) {
		this.confirmStatusStr = confirmStatusStr;
	}

	public String getSourceTypeStr() {
		return sourceTypeStr;
	}

	public void setSourceTypeStr(String sourceTypeStr) {
		this.sourceTypeStr = sourceTypeStr;
	}

	public String getTotalWeightStr() {
		return totalWeightStr;
	}

	public void setTotalWeightStr(String totalWeightStr) {
		this.totalWeightStr = totalWeightStr;
	}
     
}