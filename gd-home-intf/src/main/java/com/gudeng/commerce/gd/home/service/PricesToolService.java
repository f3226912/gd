package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dto.PricesDTO;

/**
 * 市场价格
 */
public interface PricesToolService {

	/**
	 * 根据ID查找数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午3:44:43
	 */
	public PricesDTO getById(String id) throws Exception;

	/**
	 * 根据条件查找市场价格列表
	 * 
	 * @param map
	 *            查找条件
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月12日 下午8:01:37
	 */
	public List<PricesDTO> getByCondition(Map<String, Object> map)
			throws Exception;

	/**
	 * 查询记录条数
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:05:39
	 */
	public int getTotal(Map<String, Object> map) throws Exception;


}