package com.gudeng.commerce.gd.supplier.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.dto.MarketSaleDTO;
import com.gudeng.commerce.gd.supplier.service.MarketSaleService;

/**
 * @Description 市场交易额服务
 * @Project gd-supplier-intf
 * @ClassName MarketSaleService.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年11月10日 下午5:51:12
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Service(value = "marketSaleService")
public class MarketSaleServiceImpl implements MarketSaleService {
	@Autowired
	private BaseDao<MarketSaleDTO> baseDao;

	/**
	 * @Description addMarketSale 新增市场交易额信息
	 * @param marketSaleDTO
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月10日 下午5:53:10
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@Override
	public int addMarketSale(MarketSaleDTO marketSaleDTO) throws Exception {
		int i = baseDao.execute("MarketSale.addMarketSaleDTO", marketSaleDTO);
		return i;
	}

	/**
	 * @Description getMarketSaleDTOList 根据条件获取
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月10日 下午5:55:20
	 * @Author lidong(dli@cnagri-products.com)
	 */
	@Override
	public List<MarketSaleDTO> getMarketSaleDTOList(Map<String, Object> map) throws Exception {
		List<MarketSaleDTO> list = baseDao.queryForList("MarketSale.getMarketSaleList", map, MarketSaleDTO.class);
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
		return (int) baseDao.queryForObject("MarketSale.getTotal", map, Integer.class);
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
	public MarketSaleDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		return baseDao.queryForObject("MarketSale.getById", map, MarketSaleDTO.class);
	}

	/**
	 * 修改市场交易额信息
	 * 
	 * @param priceDTO
	 * @return
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月13日 下午12:17:06
	 */
	public int updateMarketSaleDTO(MarketSaleDTO MarketSaleDTO) throws Exception {
		int i = baseDao.execute("MarketSale.updateMarketSaleDTO", MarketSaleDTO);
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
	public int deleteMarketSale(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("MarketSale.deleteMarketSaleDTO", batchValues).length;
	}

}
