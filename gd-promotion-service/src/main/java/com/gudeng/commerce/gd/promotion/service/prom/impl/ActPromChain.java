package com.gudeng.commerce.gd.promotion.service.prom.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdResult;
import com.gudeng.commerce.gd.promotion.service.PromActProdInfoService;
import com.gudeng.commerce.gd.promotion.service.prom.PromChain;

public class ActPromChain extends PromChain {

	@Autowired
	private PromActProdInfoService actService;

	public void setActService(PromActProdInfoService actService) {
		this.actService = actService;
	}

	/**
	 * 活动商品模式 查询
	 */
	@Override
	public void excute(Map<String, Object> paramsMap) {
		
		//判断查询参数是否正确
		if (paramsMap.get("productId") != null) {
			//获得商品活动信息
			Long productId = Long.parseLong(paramsMap.get("productId").toString());
			PromProdInfoDTO prodInfoDTO = actService.getPromProdInfo(productId);
			
			
			/*
			 * 如果没有查到活动信息 直接返回
			 */
			if (prodInfoDTO == null) {
				return;
			}
			
			PromProdResult result = new PromProdResult();
			
			/*//默认正常状态
			result.setStatus(1);
			
			
			 * 判断活动时间状态
			 
			if(prodInfoDTO.getStartTime().after(new Date())) {
				
				 * 如果开始时间晚于当前时间
				 * 未开始活动
				 
				result.setStatus(3);
				
			}
			
			if(prodInfoDTO.getEndTime().before(new Date())) {
				
				 * 如果结束时间早于当前时间
				 * 已结束活动
				 
				result.setStatus(2);
				
			}*/
			
			/*
			 * 获得活动详细信息
			 */
			result.setPromProdInfoDTO(prodInfoDTO);
			
			result.setPromBaseInfo(actService.getProdBaseinfo(prodInfoDTO.getActId(), productId));
			
			paramsMap.put("PromProdResult", result);
		}

	}

}
