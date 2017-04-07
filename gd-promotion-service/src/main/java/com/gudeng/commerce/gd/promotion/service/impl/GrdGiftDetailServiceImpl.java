package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGiftDetailEntity;
import com.gudeng.commerce.gd.promotion.service.GrdGiftDetailService;
import com.gudeng.framework.dba.util.DalUtils;

public class GrdGiftDetailServiceImpl implements GrdGiftDetailService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public GrdGiftDetailDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("GrdGiftDetail.getByCondition", map, GrdGiftDetailDTO.class);
	}

	@Override
	public List<GrdGiftDetailDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGiftDetail.getByCondition", map, GrdGiftDetailDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("GrdGiftDetail.deleteById", map);
	}

	@Override
	public int update(GrdGiftDetailDTO t) throws Exception {
		return baseDao.execute("GrdGiftDetail.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("GrdGiftDetail.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(GrdGiftDetailEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<GrdGiftDetailDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GiftDetail.queryByConditionPage", map, GrdGiftDetailDTO.class);
	}

	@Override
	public List<GrdGiftDetailDTO> getDetailByMobileAndType(String mobile,String type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		if(type !=null && !type.trim().equals("")){
			map.put("type", type);
		}
		return baseDao.queryForList("GrdGiftDetail.getDetailByMobileAndType", map, GrdGiftDetailDTO.class);
	}
	@Override
	public List<GrdGiftDetailDTO> getDetailByMap(Map map) throws Exception {
		return baseDao.queryForList("GrdGiftDetail.getDetailByMap", map, GrdGiftDetailDTO.class);
	}

	@Override
	public int batchInsertEntity(List<GrdGiftDetailDTO> batchDetailDTO) throws Exception {
		int len = batchDetailDTO.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			GrdGiftDetailDTO entity = batchDetailDTO.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}
		return baseDao.batchUpdate("GrdGiftDetail.batchInsertEntity", batchValues).length;
			
	}

	@Override
	public List<GrdGiftDetailDTO> queryByRecordId(Integer recordId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("recordId", recordId);
		return baseDao.queryForList("GrdGiftDetail.queryByRecordId", map, GrdGiftDetailDTO.class);
	}

	@Override
	public List<GrdGiftDetailDTO> queryCreateOrderByConditionPage(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("GrdGiftDetail.queryCreateOrderByConditionPage", map, GrdGiftDetailDTO.class);
	}

	@Override
	public GrdGiftDetailDTO getTotalByUserId(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("GrdGiftDetail.getTotalByUserId", map, GrdGiftDetailDTO.class);
	}

	@Override
	public List<GrdGiftDetailDTO> queryOrderInfoByUserId(Map<String, Object> param) throws Exception {
		return baseDao.queryForList("GrdGiftDetail.queryOrderInfoByUserId", param, GrdGiftDetailDTO.class);
	}

	@Override
	public int countOrderInfoByUserId(Map<String, Object> param) throws Exception {
		return baseDao.queryForObject("GrdGiftDetail.countOrderInfoByUserId", param, Integer.class);
	}

	@Override
	public int deleteBatch(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getGrdOrderTotal(Map<String, Object> param) throws Exception {
		return baseDao.queryForObject("GrdGiftDetail.getGrdOrderTotal", param, Integer.class);
	}
	@Override
	public List<GrdGiftDetailDTO> getGrdOrderList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("GrdGiftDetail.getGrdOrderList", map, GrdGiftDetailDTO.class);
	}

	@Override
	public Integer insert(GrdGiftDetailDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}