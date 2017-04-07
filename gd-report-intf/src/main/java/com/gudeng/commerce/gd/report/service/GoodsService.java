package com.gudeng.commerce.gd.report.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.report.dto.GoodsTradeResult;
import com.gudeng.commerce.gd.report.exception.ServiceException;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午4:46:54
 */
public interface GoodsService {
	
	/**
	 * 获取商品分析排名基础数据
	 * @param params
	 * @return
	 */
	public List<GoodsTradeResult> getGoodsTradeResult(Map<String, Object> params) throws ServiceException;

}
