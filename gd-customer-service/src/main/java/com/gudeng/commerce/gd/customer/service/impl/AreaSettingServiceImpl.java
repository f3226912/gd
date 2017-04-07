package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.service.AreaSettingService;

@SuppressWarnings("unchecked")
@Service
public class AreaSettingServiceImpl implements AreaSettingService {
	
	@Autowired
	private BaseDao<?> baseDao;

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	public AreaSettingDTO getById(String id) throws Exception {
		
		Map<String ,String> map=new HashMap<String ,String>();
		map.put("id", id);
		return (AreaSettingDTO)baseDao.queryForObject("AreaSetting.getById", map, AreaSettingDTO.class);

	}


	@Override
	public AreaSettingDTO getByName(String areaName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AreaSettingDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		
		List<AreaSettingDTO> list= baseDao.queryForList("AreaSetting.getByCondition", map, AreaSettingDTO.class);
		return list;
	}


	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.queryForObject("AreaSetting.getTotal", map, Integer.class);

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
		return baseDao.batchUpdate("AreaSetting.deleteAreaSettingDTO", batchValues).length;
	}

	
	

	@Override
	public int addAreaSettingDTO(AreaSettingDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.execute("AreaSetting.addAreaSettingDTO", dto);
	}


	@Override
	public int updateAreaSettingDTO(AreaSettingDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.execute("AreaSetting.updateAreaSettingDTO", dto);
	}


	@Override
	public List<AreaSettingDTO> getAllAreaName(Map<String, Object> map)
			throws Exception {
		List<AreaSettingDTO> list= baseDao.queryForList("AreaSetting.getAllAreaName", map, AreaSettingDTO.class);
	    return list;
	}
	
	
	
	@Override
	public int batchAddAreaSetting(List<AreaSettingDTO> list) throws Exception {
		Map<String, Object>[] batchValues = new HashMap[list.size()];
		for (int i = 0; i < list.size(); i++) {
			AreaSettingDTO dto = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("areaName", dto.getAreaName());
			map.put("memberId", dto.getMemberId());
			map.put("mobile", dto.getMobile());
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("AreaSetting.addAreaSettingDTO", batchValues).length;
	}
	
	
}
