package com.gudeng.commerce.info.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.DatasourceDTO;
import com.gudeng.commerce.info.customer.entity.DatasourceEntity;
import com.gudeng.commerce.info.customer.service.DatasourceService;

@Service
public class DatasourceServiceImpl implements DatasourceService {

	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	
	
	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long insertEntity(DatasourceEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("Datasource.deleteById", map);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		int count=0;
		for(Long id:idList){
			count = deleteById(id);
			if(count<=0) {
				throw new Exception("批量删除记录失败！id:" + id);
			}
		}
		return count;
	}

	@Override
	public int updateDTO(DatasourceDTO obj) throws Exception {
		int count = baseDao.execute("Datasource.update", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<DatasourceDTO> objList) throws Exception {
		int count=0;
		for(DatasourceDTO dto:objList){
			count = baseDao.execute("Datasource.deleteById", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Datasource.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DatasourceDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (DatasourceDTO)this.baseDao.queryForObject("Datasource.getById", map, DatasourceDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatasourceDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		List<DatasourceDTO> list= baseDao.queryForList("Datasource.getListByConditionPage", map, DatasourceDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatasourceDTO> getListByCondition(Map<String, Object> map) throws Exception {
		List<DatasourceDTO> list= baseDao.queryForList("Datasource.getListByCondition", map, DatasourceDTO.class);
		return list;
	}
}
