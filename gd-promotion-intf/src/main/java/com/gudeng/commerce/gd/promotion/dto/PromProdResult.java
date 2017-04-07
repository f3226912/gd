package com.gudeng.commerce.gd.promotion.dto;

import java.io.Serializable;

public class PromProdResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2126017453444106919L;

	private PromProdInfoDTO promProdInfoDTO;
	
	private PromBaseinfoDTO promBaseInfo;
	
	private Integer status; //1：正常 2：过期 3：未到

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public PromProdInfoDTO getPromProdInfoDTO() {
		return promProdInfoDTO;
	}

	public void setPromProdInfoDTO(PromProdInfoDTO promProdInfoDTO) {
		this.promProdInfoDTO = promProdInfoDTO;
	}

	public PromBaseinfoDTO getPromBaseInfo() {
		return promBaseInfo;
	}

	public void setPromBaseInfo(PromBaseinfoDTO promBaseInfo) {
		this.promBaseInfo = promBaseInfo;
	}

}
