package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.bo.CacheBo;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.ReCategoryBanelImgDTO;
import com.gudeng.commerce.gd.customer.service.ReCategoryBanelImgService;

public class ReCategoryBanelImgServiceImpl implements ReCategoryBanelImgService {

	
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private CacheBo cacheBo;
	

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}
	
	
	
	
	@Override
	public List<ReCategoryBanelImgDTO> getByCategoryId(Long categoryId)
			throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("categoryId", categoryId);
		return this.baseDao.queryForList("ReCategoryBanelImg.getByCategoryId", params ,ReCategoryBanelImgDTO.class);
	}

	@Override
	public int getCountByCategoryId(Long categoryId) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("categoryId", categoryId);		
		return (int) baseDao.queryForObject("ReCategoryBanelImg.getCount", params, Integer.class);
	}
	
//	@Override
//	public int getCountByGroupId(Long grouId) throws Exception {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("grouId", grouId);		
//		return (int) baseDao.queryForObject("ReCategoryBanelImg.getCount", params, Integer.class);
//	}
//	
	@Override
	public int getCount(Map map) throws Exception {
		return (int) baseDao.queryForObject("ReCategoryBanelImg.getCountByMap", map, Integer.class);
	}
	
	@Override
	public ReCategoryBanelImgDTO getById(Long id)
			throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		return this.baseDao.queryForObject("ReCategoryBanelImg.getById", params ,ReCategoryBanelImgDTO.class);	
	}




	@Override
	public List<ReCategoryBanelImgDTO> getAllByGroupId(Long groupId) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("groupId", groupId);		
		return this.baseDao.queryForList("ReCategoryBanelImg.getByGroupId", params ,ReCategoryBanelImgDTO.class);	
	}
	
	@Override
	public List<ReCategoryBanelImgDTO> getByAll() throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		return this.baseDao.queryForList("ReCategoryBanelImg.getAll", params ,ReCategoryBanelImgDTO.class);	
	}




	@Override
	public List<ReCategoryBanelImgDTO> getByAllByPage(Map map) throws Exception {
		return this.baseDao.queryForList("ReCategoryBanelImg.getAllByPage", map ,ReCategoryBanelImgDTO.class);	
	}




	@Override
	public List<ReCategoryBanelImgDTO> getAllGroups() throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		return this.baseDao.queryForList("ReCategoryBanelImg.getAllGroups", params ,ReCategoryBanelImgDTO.class);	
	}

}
