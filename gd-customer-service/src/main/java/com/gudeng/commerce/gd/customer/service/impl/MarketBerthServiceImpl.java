package com.gudeng.commerce.gd.customer.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MarketBerthDTO;
import com.gudeng.commerce.gd.customer.service.MarketBerthService;

/**
 * 市场铺位Service
 * @author cai.x
 *
 */
@Service
public class MarketBerthServiceImpl implements MarketBerthService {

	@Autowired
	private BaseDao<?> baseDao;

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<MarketBerthDTO> getByCondition(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		List<MarketBerthDTO> list= baseDao.queryForList("MarketBerth.getByCondition", map, MarketBerthDTO.class);
		return list;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		int total = baseDao.queryForObject("MarketBerth.getTotal", map, Integer.class);
		return total;
	}
	@Override
	public int queryMarketGroupTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		int total = baseDao.queryForObject("MarketBerth.queryMarketGroupTotal", map, Integer.class);
		return total;
	}
	
	@Override
	public List<MarketBerthDTO> getByConditionByDtl(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		List<MarketBerthDTO> list= baseDao.queryForList("MarketBerth.getByConditionByDtl", map, MarketBerthDTO.class);
		return list;
	}

	@Override
	public int getTotalByDtl(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		int total = baseDao.queryForObject("MarketBerth.getTotalByDtl", map, Integer.class);
		return total;
	}

	@Override
	public List<MarketBerthDTO> queryMarketGroup(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		List<MarketBerthDTO> list= baseDao.queryForList("MarketBerth.queryMarketGroup", map, MarketBerthDTO.class);
		return list;
	}

	@Override
	public int addMerketBerth(MarketBerthDTO marketBerthDto) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.execute("MarketBerth.addMerketBerth", marketBerthDto);
	}
	@Override
	public int updateMarketBerth(MarketBerthDTO marketBerthDto) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.execute("MarketBerth.updateMarketBerth", marketBerthDto);
	}
	@Override
	public int deleteMarketBerth(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		int total = baseDao.execute("MarketBerth.deleteMarketBerth", map);
		return total;
	}

	/**
	 * 根据id修改市场铺位表
	 * @param dto
	 * @return
	 * @throws IOException
	 * @author 李冬 dli@gdeng.cn
	 * @time 2016年8月18日 上午10:42:53
	 */
	@Override
	public int updateMarketBerthById(MarketBerthDTO dto) throws Exception {
		return baseDao.execute("MarketBerth.updateMarketBerthById", dto);
	}

	@Override
	public int updateMarketBerthCodeById(MarketBerthDTO dto) throws Exception {
		return baseDao.execute("MarketBerth.updateMarketBerthCodeById", dto);
	}

	@Override
	public int updateMarketBerthBacth(List<MarketBerthDTO> list)
			throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			MarketBerthDTO dto = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", dto.getId());
			map.put("berthCode", dto.getBerthCode());
			map.put("updateUser", dto.getUpdateUser());
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("MarketBerth.updateMarketBerthCodeById", batchValues).length;
	}
}
