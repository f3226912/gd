package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dto.PricesDTO;

/**
 * 市场价格操作接口
 * 
 * @author 李冬
 * @time 2015年10月16日 下午3:21:36
 */
public interface PricesToolService {

	/**
	 * 根据ID查找数据
	 * 
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
	public List<PricesDTO> getByCondition(Map<String, Object> map) throws Exception;

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

	/**
	 * 根据ID删除数据
	 * 
	 * @param id集合
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午4:52:12
	 */
	public int deletePrices(List<String> list) throws Exception;

	/**
	 * 持久化市场价格实例数据
	 * 
	 * @param t
	 *            市场价格实体类
	 * @return 影响行数
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月12日 下午8:15:30
	 */
	public int addPricesDTO(PricesDTO priceDTO) throws Exception;

	/**
	 * @Description addPricesBacth 批量添加市场价格信息，主要用于Excel导入
	 * @param List
	 *            <PricesDTO> list
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月6日 下午3:43:00
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public int addPricesBacth(List<PricesDTO> list) throws Exception;

	/**
	 * 合并市场价格实例数据（不存在则添加，存在则更新）
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月12日 下午8:17:07
	 */
	public int updatePricesDTO(PricesDTO priceDTO) throws Exception;

	/**
	 * 判断数据是否存在
	 * 
	 * @param map
	 * @return 存在为 true，不存在为false
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午2:37:35
	 */
	public boolean checkExsit(Map<String, Object> map) throws Exception;

}