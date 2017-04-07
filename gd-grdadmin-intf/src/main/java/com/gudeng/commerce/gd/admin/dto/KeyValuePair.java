package com.gudeng.commerce.gd.admin.dto;

import java.io.Serializable;

public class KeyValuePair implements Serializable{

	private static final long serialVersionUID = 8398650429482737962L;
	
	private String keyString ;
	private String valueString;
	public String getKeyString() {
		return keyString;
	}
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	public String getValueString() {
		return valueString;
	}
	public void setValueString(String valueString) {
		this.valueString = valueString;
	}
	
}
