package com.gudeng.commerce.gd.supplier.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;
import com.gudeng.commerce.gd.supplier.service.PricesService;

/**
 * 市场价格操作服务接口实现
 * 
 * @author 李冬
 * @time 2015年10月12日 下午7:07:21
 */
@Service(value = "pricesService")
public class PricesServiceImpl implements PricesService {
	@Autowired
	private BaseDao<PricesDTO> baseDao;

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
			throws Exception {
		List<PricesDTO> list = baseDao.queryForList("Prices.getPricesList",
				map, PricesDTO.class);
		return list;
	}

	/**
	 * 查询记录条数
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:05:39
	 */
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Prices.getTotal", map,
				Integer.class);
	}

	/**
	 * 根据ID查找数据
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午3:44:43
	 */
	@Override
	public PricesDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		return baseDao.queryForObject("Prices.getById", map, PricesDTO.class);
	}

	/**
	 * 新增市场价格信息
	 * 
	 * @param priceDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:03
	 */
	public int addPricesDTO(PricesDTO priceDTO) throws Exception {
		int i = baseDao.execute("Prices.addPricesDTO", priceDTO);
		return i;
	}
	
	/**
	 * @Description addPricesBacth 批量添加市场价格信息，主要用于Excel导入
	 * @param List<PricesDTO> list
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月6日 下午3:43:00
	 * @Author lidong(dli@cnagri-products.com)
	*/
	@Override
	public int addPricesBacth(List<PricesDTO> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			PricesDTO pricesDTO = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productName", pricesDTO.getProductName());
			map.put("averagePrice", pricesDTO.getAveragePrice());
			map.put("minPrice", pricesDTO.getMinPrice());
			map.put("maxPrice", pricesDTO.getMaxPrice());
			map.put("date_string", pricesDTO.getDate_string());
			map.put("locale", pricesDTO.getLocale());
			map.put("createUserId", pricesDTO.getCreateUserId());
			map.put("createTime_str", pricesDTO.getCreateTime_str());
			map.put("publishTime_str", pricesDTO.getPublishTime_str());
			map.put("targetUrl", pricesDTO.getTargetUrl());
			map.put("maketId", pricesDTO.getMaketId());
			map.put("productTypeId", null);
			map.put("status", null);
			map.put("description", null);
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("Prices.addPricesDTO", batchValues).length;
	}
	

	/**
	 * 修改市场价格信息
	 * 
	 * @param priceDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:06
	 */
	public int updatePricesDTO(PricesDTO priceDTO) throws Exception {
		int i = baseDao.execute("Prices.updatePricesDTO", priceDTO);
		return i;
	}

	/**
	 * 根据ID删除数据
	 * 
	 * @param id集合
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午4:52:12
	 */
	@SuppressWarnings("unchecked")
	public int deletePrices(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("Prices.deletePricesDTO", batchValues).length;
	}

	/**
	 * 判断数据是否存在
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午2:37:35
	 */
	public int checkExsit(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Prices.checkExsit", map,
				Integer.class);
	}
}
