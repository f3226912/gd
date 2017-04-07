package com.gudeng.commerce.gd.customer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.QuickSheetCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.QuickSheetProductDTO;
import com.gudeng.commerce.gd.customer.service.QuickMakeSheetService;

public class QuickMakeSheetServiceImpl implements QuickMakeSheetService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<QuickSheetCategoryDTO> getQuickMakeSheetList() throws Exception {
		List<QuickSheetCategoryDTO> qsc =null;
		
		qsc=baseDao.queryForList("QuickMakeSheet.getQuickMakeSheetList",null, QuickSheetCategoryDTO.class);
		List<QuickSheetProductDTO> sqp=baseDao.queryForList("QuickMakeSheet.getQuickMakeSheetProductList",null, QuickSheetProductDTO.class);
		for (int i = 0; i < qsc.size(); i++) {
			Integer a1=qsc.get(i).getCategoryId();
			List<QuickSheetProductDTO> productList=new ArrayList<>();
			for (int j = 0; j < sqp.size(); j++) {
				if(a1.equals(sqp.get(j).getCategoryId())){
					productList.add(sqp.get(j));
				}
			}	
			if(productList!=null&&productList.size()>0){
				qsc.get(i).setProductList(productList);
			}
			
		}
		return  qsc;
	}

	@Override
	public Integer addProduct(Map<String, Object> map) throws Exception {
		return baseDao.execute("QuickMakeSheet.addProduct", map);
	}

	@Override
	public Integer delProduct(Map<String, Object> map) throws Exception {
		return baseDao.execute("QuickMakeSheet.delProduct", map);
	}

	@Override
	public List<QuickSheetCategoryDTO> getStandardLibraryProductList(Map<String, Object> map) throws Exception {
		List<QuickSheetCategoryDTO> qsc =null;
		
		qsc=baseDao.queryForList("QuickMakeSheet.getQuickMakeSheetList",null, QuickSheetCategoryDTO.class);
		List<QuickSheetProductDTO> sqp=baseDao.queryForList("QuickMakeSheet.getStandardLibraryProductList",map, QuickSheetProductDTO.class);
		for (int i = 0; i < qsc.size(); i++) {
			Integer a1=qsc.get(i).getCategoryId();
			List<QuickSheetProductDTO> productList=new ArrayList<>();
			for (int j = 0; j < sqp.size(); j++) {
				if(a1.equals(sqp.get(j).getCategoryId())){
					productList.add(sqp.get(j));
					
				}
			}
			if(productList!=null&&productList.size()>0){
			qsc.get(i).setProductList(productList);
			}
		}
		return  qsc;
	}

	@Override
	public Integer getQuickMakeSheetCount(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("QuickMakeSheet.getQuickMakeSheetCount", map, Integer.class);
	}

	@Override
	public Integer productCount(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("QuickMakeSheet.productCount", map, Integer.class);
	}

}
