package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.PreSaleDetailDTO;
import com.gudeng.commerce.gd.order.entity.PreSaleDetailEntity;
import com.gudeng.commerce.gd.order.service.PreSaleDetailService;
import com.gudeng.framework.dba.util.DalUtils;
@Service
public class PreSaleDetailServiceImpl implements PreSaleDetailService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	
	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Long insertEntity(PreSaleDetailEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("PreSaleDetail.deleteById", map);
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
	public int updateDTO(PreSaleDetailDTO obj) throws Exception {
		int count = baseDao.execute("PreSaleDetail.updateDTO", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<PreSaleDetailDTO> objList) throws Exception {
		int count=0;
		for(PreSaleDetailDTO dto:objList){
			count = baseDao.execute("PreSaleDetail.deleteById", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("PreSaleDetail.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PreSaleDetailDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (PreSaleDetailDTO)this.baseDao.queryForObject("PreSaleDetail.getById", map, PreSaleDetailDTO.class);
	}

	@Override
	public List<PreSaleDetailDTO> getListByConditionPage(Map<String, Object> map)
			throws Exception {
		List<PreSaleDetailDTO> list= baseDao.queryForList("PreSaleDetail.getListByConditionPage", map, PreSaleDetailDTO.class);
		return list;
	}

	@Override
	public List<PreSaleDetailDTO> getListByCondition(Map<String, Object> map)
			throws Exception {
		List<PreSaleDetailDTO> list= baseDao.queryForList("PreSaleDetail.getListByCondition", map, PreSaleDetailDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int batchInsertEntity(List<PreSaleDetailEntity> entityList)
			throws Exception {
		int len = entityList.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			PreSaleDetailEntity entity = entityList.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}
		return baseDao.batchUpdate("PreSaleDetail.batchInsertEntity", batchValues).length;
	}

	@Override
	public List<PreSaleDetailDTO> getListByOrderNoList(List<Long> orderNoList)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNoList", orderNoList);
		return baseDao.queryForList("PreSaleDetail.getListByOrderNoList", map, PreSaleDetailDTO.class);
	}
}
