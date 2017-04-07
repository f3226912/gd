package com.gudeng.commerce.gd.promotion.service.prom;

import java.util.Map;

public interface PromChainControllerInti {
	
	/**
	 * 用于执行的方法
	 * @param
	 * paramMap productId:商品ID
	 * @return
	 * paramMap PromProdResult 包含商品促销信息
	 */
	public Map<String, Object> execute(Map<String, Object> paramMap);

}
