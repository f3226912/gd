package com.gudeng.commerce.gd.promotion.service.prom.impl;

import java.util.Map;

import com.gudeng.commerce.gd.promotion.service.prom.PromChain;
import com.gudeng.commerce.gd.promotion.service.prom.PromChainControllerInti;

public class PromChainController implements PromChainControllerInti {

	protected Map<String, PromChain> promServices;

	public Map<String, PromChain> getPromServices() {
		return promServices;
	}

	public void setPromServices(Map<String, PromChain> promServices) {
		this.promServices = promServices;
	}

	/**
	 * 获得责任链第一个对象
	 * @param params
	 * @return
	 */
	public PromChain getProdChain(Map<String, Object> params) {
		if (promServices.size() > 0) {
			return promServices.values().iterator().next();
		} else {
			return null;
		}

	}

	/**
	 * 用于执行的方法
	 * @param
	 * paramMap productId:商品ID
	 * @return
	 * paramMap PromProdResult 包含商品促销信息
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> paramMap) {
		getProdChain(null).checkProms(paramMap);
		return paramMap;
	}

}
