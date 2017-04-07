package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.entity.MarketEntity;
import com.gudeng.commerce.gd.customer.service.MarketService;


/**
 *功能描述：
 */
@Service
@SuppressWarnings("unchecked")
public class MarketServiceImpl implements MarketService{
	
	@Autowired
	private BaseDao<?>  baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public MarketDTO getById(String id) throws Exception {
		Map <String, String> p = new HashMap<String, String>();
		p.put("id", id);
		return (MarketDTO)this.baseDao.queryForObject("Market.getMarket", p, MarketDTO.class);
	}
	
	@Override
	public MarketDTO getMarketByName(String marketName) throws Exception {
		Map <String, String> p = new HashMap<String, String>();
		p.put("marketName", marketName);
		return (MarketDTO)this.baseDao.queryForObject("Market.getMarketByName", p, MarketDTO.class);
	}

	@Override
	public List<MarketDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		List<MarketDTO> list= baseDao.queryForList("Market.getByCondition", map, MarketDTO.class);
		return list;
	}
	
	
	@Override
	public List<MarketDTO> getAllByStatus(String status)
			throws Exception {
		Map <String, String> map = new HashMap<String, String>();
		map.put("status", status);
		List<MarketDTO> list= baseDao.queryForList("Market.getAllByStatus", map, MarketDTO.class);
		return list;
	}
	
	@Override
	public List<MarketDTO> getAllByStatusAndType(String status, String type)
			throws Exception {
		Map <String, String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("marketType", type);
		List<MarketDTO> list= baseDao.queryForList("Market.getAllByStatusAndType", map, MarketDTO.class);
		return list;
	}
	
	
	@Override
	public List<MarketDTO> getAllByType(String type)
			throws Exception {
		Map <String, String> map = new HashMap<String, String>();
		map.put("marketType", type);
		List<MarketDTO> list= baseDao.queryForList("Market.getAllByType", map, MarketDTO.class);
		return list;
	}
	

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Market.getTotal", map, Integer.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		int len = id.split(",").length;
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(id.split(",")[i]));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("Market.deleteMarketDTO", batchValues).length;
	}

	@Override
	public int addMarketDTO(Map<String, Object> map) throws Exception {
		return (int) baseDao.execute("Market.addMarketDTO", map);
	}
	
	@Override
	public int addMarketDTO(MarketDTO market) throws Exception {
		return (int) baseDao.execute("Market.addMarketDTO", market);
	}

	@Override
	public int updateMarketDTO(MarketDTO market) throws Exception {
		
		return (int) baseDao.execute("Market.updateMarketDTO", market);
	}

	@Override
	public List<MarketDTO> getByName(Map<String, Object> map) {
		List<MarketDTO> list= baseDao.queryForList("Market.getListByName", map, MarketDTO.class);
		return list;
	}

	@Override
	public List<MarketDTO> getAllByCondition(Map<String, Object> map)
			throws Exception {
		List<MarketDTO> list= baseDao.queryForList("Market.getAllByCondition", map, MarketDTO.class);
		return list;
	}

	@Override
	public Long addMarket(MarketDTO dto) throws Exception {
		MarketEntity entity = new MarketEntity();
		entity.setMarketName(dto.getMarketName());
		entity.setProvinceId(dto.getProvinceId());
		entity.setCityId(dto.getCityId());
		entity.setAreaId(dto.getAreaId());
		entity.setMarketType(dto.getMarketType());
		entity.setStatus(dto.getStatus());
		Long id = (Long) baseDao.persist(entity, Long.class);
		return id;
	}

	@Override
	public List<MarketDTO> getAllByTypes(List<String> types) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!CollectionUtils.isEmpty(types)){
			paramMap.put("marketTypes", types);
		}
		return baseDao.queryForList("Market.getAllByTypes", paramMap, MarketDTO.class);
	}

	@Override
	public List<MarketDTO> getAllBySearch(Map<String, Object> params) throws Exception {
		return baseDao.queryForList("Market.getAllBySearch", params, MarketDTO.class);
	}

	@Override
	public List<MarketDTO> getNearbyMarket(Map<String, Object> map)
			throws Exception {
		return baseDao.queryForList("Market.getNearbyMarket", map, MarketDTO.class);
	}

	@Override
	public MarketDTO getMarketById(Map<String, Object> map) throws Exception {
		return (MarketDTO)baseDao.queryForObject("Market.getMarketById", map, MarketDTO.class);
	}
	
	@Override
	public List<MarketDTO> getMarketList(Map<String, Object> paramsMap) throws Exception {
		return baseDao.queryForList("Market.getMarketList", paramsMap, MarketDTO.class);
	}
	
	@Override
	public int getCountOfMarketList(Map<String, Object> params) throws Exception {
		return baseDao.queryForObject("Market.getCountOfMarketList", params, Integer.class);
	}
	
	@Override
	public List<MarketDTO> getListOfMarket(Map<String, Object> params) throws Exception {
		List<MarketDTO> list= baseDao.queryForList("Market.getListOfMarket", params, MarketDTO.class);
		return list;
	}
	@Override
	public int getMarketCountByCondition(Map<String, Object> map)
			throws Exception {
		return (int) baseDao.queryForObject("Market.getMarketCountByCondition", map, Integer.class);
	}

	@Override
	public List<MarketDTO> getMarketListByCondition(Map<String, Object> params)
			throws Exception {
		List<MarketDTO> list= baseDao.queryForList("Market.getMarketListByCondition", params, MarketDTO.class);
		return list;
	}
}
