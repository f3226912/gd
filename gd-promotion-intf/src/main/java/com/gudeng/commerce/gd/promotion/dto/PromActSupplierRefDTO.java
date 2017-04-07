package com.gudeng.commerce.gd.promotion.dto;

import java.util.List;

import com.gudeng.commerce.gd.promotion.entity.PromActSupplierRefEntity;

public class PromActSupplierRefDTO extends PromActSupplierRefEntity implements java.io.Serializable  {
	
	private static final long serialVersionUID = -907829851135486645L;
	
	/**
	 * 
	 */
	private List<PromProdInfoDTO> promProdInfoList;

	public List<PromProdInfoDTO> getPromProdInfoList() {
		return promProdInfoList;
	}

	public void setPromProdInfoList(List<PromProdInfoDTO> promProdInfoList) {
		this.promProdInfoList = promProdInfoList;
	}
}
