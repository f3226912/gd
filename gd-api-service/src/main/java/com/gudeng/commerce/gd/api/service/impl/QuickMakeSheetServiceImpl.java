package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.QuickSheetCategoryDTO;
import com.gudeng.commerce.gd.customer.service.QuickMakeSheetService;

public class QuickMakeSheetServiceImpl implements com.gudeng.commerce.gd.api.service.QuickMakeSheetService {

	@Autowired
	private GdProperties gdProperties;
	
	private static QuickMakeSheetService quickMakeSheetService;
	
	public QuickMakeSheetService getHessianOrderCommentService() throws MalformedURLException{
		if(quickMakeSheetService == null){
			String hessianUrl = gdProperties.getQuickMakeSheetServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			quickMakeSheetService = (QuickMakeSheetService)factory.create(QuickMakeSheetService.class, hessianUrl);
		}
		return quickMakeSheetService;
	}
	@Override
	public List<QuickSheetCategoryDTO> getQuickMakeSheetList() throws Exception {
		return getHessianOrderCommentService().getQuickMakeSheetList();
	}
	@Override
	public Integer addProduct(Map<String, Object> map) throws Exception {
		return getHessianOrderCommentService().addProduct(map);
	}
	@Override
	public Integer delProduct(Map<String, Object> map) throws Exception {
		return getHessianOrderCommentService().delProduct(map);
	}
	@Override
	public List<QuickSheetCategoryDTO> getStandardLibraryProductList(Map<String, Object> map) throws Exception {
		return getHessianOrderCommentService().getStandardLibraryProductList(map);
	}
	@Override
	public Integer getQuickMakeSheetCount(Map<String, Object> map) throws Exception {
		return getHessianOrderCommentService().getQuickMakeSheetCount(map);
	}
	

}
