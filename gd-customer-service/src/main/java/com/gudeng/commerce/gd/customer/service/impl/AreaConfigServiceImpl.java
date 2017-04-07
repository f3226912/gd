package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AreaConfigDTO;
import com.gudeng.commerce.gd.customer.service.AreaConfigService;

@SuppressWarnings("unchecked")
@Service
public class AreaConfigServiceImpl implements AreaConfigService {
	
	@Autowired
	private BaseDao<?> baseDao;

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	public AreaConfigDTO getById(String id) throws Exception {
		
		Map<String ,String> map=new HashMap<String ,String>();
		map.put("id", id);
		return (AreaConfigDTO)baseDao.queryForObject("AreaConfig.getById", map, AreaConfigDTO.class);

	}


	@Override
	public AreaConfigDTO getByName(String areaName) throws Exception {
		
		Map<String ,String> map=new HashMap<String ,String>();
		map.put("areaName", areaName);
		return (AreaConfigDTO)baseDao.queryForObject("AreaConfig.getByAreaName", map, AreaConfigDTO.class);

	}


	@Override
	public List<AreaConfigDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		
		List<AreaConfigDTO> list= baseDao.queryForList("AreaConfig.getByCondition", map, AreaConfigDTO.class);
		return list;
	}


	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.queryForObject("AreaConfig.getTotal", map, Integer.class);

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
		return baseDao.batchUpdate("AreaConfig.deleteAreaConfigDTO", batchValues).length;
	}

	
	

	@Override
	public int addAreaConfigDTO(AreaConfigDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.execute("AreaConfig.addAreaConfigDTO", dto);
	}


	@Override
	public int updateAreaConfigDTO(AreaConfigDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.execute("AreaConfig.updateAreaConfigDTO", dto);
	}

}
