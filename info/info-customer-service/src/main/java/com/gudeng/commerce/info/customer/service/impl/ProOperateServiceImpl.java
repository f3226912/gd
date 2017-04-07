package com.gudeng.commerce.info.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.ProOperateDTO;
import com.gudeng.commerce.info.customer.entity.ProOperateEntity;
import com.gudeng.commerce.info.customer.service.ProOperateService;

@Service
public class ProOperateServiceImpl implements ProOperateService {

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
	public Long insertEntity(ProOperateEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("ProOperate.deleteById", map);
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
	public int updateDTO(ProOperateDTO obj) throws Exception {
		int count = baseDao.execute("ProOperate.update", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<ProOperateDTO> objList) throws Exception {
		int count=0;
		for(ProOperateDTO dto:objList){
			count = baseDao.execute("ProOperate.deleteById", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("ProOperate.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ProOperateDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (ProOperateDTO)this.baseDao.queryForObject("ProOperate.getById", map, ProOperateDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProOperateDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		List<ProOperateDTO> list= baseDao.queryForList("ProOperate.getListByConditionPage", map, ProOperateDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProOperateDTO> getListByCondition(Map<String, Object> map) throws Exception {
		List<ProOperateDTO> list= baseDao.queryForList("ProOperate.getListByCondition", map, ProOperateDTO.class);
		return list;
	}

	@Override
	public List<ProOperateDTO> sumReport(Map<String, Object> map) {
		List<ProOperateDTO> list= baseDao.queryForList("ProOperate.sumReport", map, ProOperateDTO.class);
		return list;
	}
}
