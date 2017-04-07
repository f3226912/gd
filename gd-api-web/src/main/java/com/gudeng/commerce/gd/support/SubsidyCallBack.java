package com.gudeng.commerce.gd.support;

import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductDto;

public interface SubsidyCallBack {
	
	public void appendAuditInfo(List<ProductDto> refusedList) throws Exception;
	
}
