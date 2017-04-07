package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.SalToshopsDetailDTO;
import com.gudeng.commerce.gd.order.entity.SalToshopsDetailEntity;
import com.gudeng.commerce.gd.order.service.SalToshopsDetailService;

/**
 * 货主出场关联销售商品表操作service
 * @author Ailen
 *
 */
public class SalToshopsDetailServiceImpl implements SalToshopsDetailService{
	
	@Autowired
	private BaseDao baseDao;

	
	public int insert(SalToshopsDetailEntity salToshopsDetailEntity) {
		return baseDao.execute("SalToshopsDetail.insert", salToshopsDetailEntity);
	}
	
	public int update(SalToshopsDetailDTO salToshopsDetailDTO) {
		return baseDao.execute("SalToshopsDetail.update", salToshopsDetailDTO);
	}
	
	public List<SalToshopsDetailDTO> queryByCondition(Map<String, Object> params) {
		return (List<SalToshopsDetailDTO>)baseDao.queryForList("SalToshopsDetail.queryByCondition", params,SalToshopsDetailDTO.class);
	}
	
	public List<SalToshopsDetailDTO> queryByConditionPage(Map<String, Object> params) {
		return (List<SalToshopsDetailDTO>)baseDao.queryForList("SalToshopsDetail.queryByConditionPage", params,SalToshopsDetailDTO.class);
	}
	
	public int deleteById(Long stdId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stdId", stdId);
		return baseDao.execute("SalToshopsDetail.deleteById", param);
	}
}
