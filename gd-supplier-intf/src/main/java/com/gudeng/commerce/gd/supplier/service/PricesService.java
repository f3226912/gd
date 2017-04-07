package com.gudeng.commerce.gd.supplier.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.supplier.dto.PricesDTO;

/**
 * 市场价格操作服务接口类
 * 
 * @author 李冬
 * @time 2015年10月12日 下午6:01:22
 */
public interface PricesService {
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
	public List<PricesDTO> getPricesList(Map<String, Object> map)
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

	
	/**
	 * 根据ID查找数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午3:44:43
	 */
	public PricesDTO getById(String id) throws Exception ;
	
	/**
	 * 新增市场价格信息
	 * 
	 * @param priceDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:03
	 */
	public int addPricesDTO(PricesDTO priceDTO) throws Exception;
	
	/**
	 * @Description addPricesBacth 批量添加市场价格信息，主要用于Excel导入
	 * @param List<PricesDTO> list
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月6日 下午3:43:00
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public int addPricesBacth(List<PricesDTO> list) throws Exception ;
	
	/**
	 * 修改市场价格信息
	 * 
	 * @param priceDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:06
	 */
	public int updatePricesDTO(PricesDTO priceDTO) throws Exception;
	

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
	 * 判断数据是否存在
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午2:37:35
	 */
	public int checkExsit(Map<String, Object> map) throws Exception;
}
