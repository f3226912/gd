package com.gudeng.commerce.gd.task.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.PreSaleDTO;

/**
 * 预销售表
 * @author xiaojun
 */
public interface PreSaleToolService {

	/**
	 * 查询预售表创建时间超过48小时的，且在订单表中没有记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PreSaleDTO> getOverPreSale(Map<String, Object> map) throws Exception;
	
	/**
	 * 通过对象更新数据库
	 * 
	 * @param PreSaleDTO
	 * @return int
	 * 
	 */
	public int updateDTO(PreSaleDTO obj) throws Exception;
}
