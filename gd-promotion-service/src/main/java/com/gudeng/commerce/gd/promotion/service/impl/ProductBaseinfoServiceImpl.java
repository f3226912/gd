package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.service.ProductBaseinfoService;

public class ProductBaseinfoServiceImpl implements ProductBaseinfoService {
	@Resource
	private BaseDao<?> baseDao;
	@Override
	public List<ProductBaseinfoDTO> getProductsByUser(Map<String, Object> map) {

		return baseDao.queryForList("ProductBaseinfo.getUserProduct", map, ProductBaseinfoDTO.class);
	}

	@Override
	public int getProductTotalByUser(Map<String, Object> paramMap) {
		return (int)baseDao.queryForObject("ProductBaseinfo.getProductTotalByUser", paramMap, Integer.class);
	}
	
	
	@Override
	public int getProductTotalByParticipants(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("ProductBaseinfo.getProductTotalByParticipants", paramMap, Integer.class);
	}

	@Override
	public List<ProductBaseinfoDTO> getUserParticipantsProducts(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return baseDao.queryForList("ProductBaseinfo.getUserParticipantsProducts", paramMap, ProductBaseinfoDTO.class);
	}
	

}
