package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromFeeDTO;
import com.gudeng.commerce.gd.promotion.dto.PromOrderProminfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.service.PromActProdInfoService;

public class PromActProdInfoServiceImpl implements PromActProdInfoService {

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public PromProdInfoDTO getPromProdInfo(Long productId) {
		Map<String, Object> param = new HashMap<>();
		param.put("prodId", productId);
		return baseDao.queryForObject("PromActProdInfo.getPromProdInfo", param, PromProdInfoDTO.class);
	}
	
	@Override
	public PromBaseinfoDTO getProdBaseinfo(Integer actId, Long productId) {
		Map<String, Object> param = new HashMap<>();
		param.put("actId", actId);
		PromBaseinfoDTO prom =  baseDao.queryForObject("PromActProdInfo.getProdBaseinfo", param, PromBaseinfoDTO.class);
	
		prom.setPromFees(getPromFees(actId));
		
		return prom;
		
	}

	@Override
	public List<PromFeeDTO> getPromFees(Integer actId) {
		Map<String, Object> param = new HashMap<>();
		param.put("actId", actId);
		return baseDao.queryForList("PromActProdInfo.getPromFees", param, PromFeeDTO.class);

	}

	@Override
	public PromOrderProminfoDTO getPromOrderInfoByOrderNo(Long orderNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("orderNo", orderNo);
		return baseDao.queryForObject("PromActProdInfo.getPromOrderInfoByOrderNo", param, PromOrderProminfoDTO.class);
	}

}
