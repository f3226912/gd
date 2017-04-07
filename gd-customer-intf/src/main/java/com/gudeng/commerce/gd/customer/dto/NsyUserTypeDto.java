package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.customer.entity.ReBusinessCategoryEntity;


public class NsyUserTypeDto extends ReBusinessCategoryEntity  implements Serializable, Comparator{
 
	private static final long serialVersionUID = -8630943943170623417L;

	
	private String nsyUserTypeCode;
	private String nsyUserTypeName;
	
	public String getNsyUserTypeCode() {
		return nsyUserTypeCode;
	}

	public void setNsyUserTypeCode(String nsyUserTypeCode) {
		this.nsyUserTypeCode = nsyUserTypeCode;
	}

	public String getNsyUserTypeName() {
		return nsyUserTypeName;
	}

	public void setNsyUserTypeName(String nsyUserTypeName) {
		this.nsyUserTypeName = nsyUserTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int compare(Object o1, Object o2) {
		return 0;
	}
	 
}
