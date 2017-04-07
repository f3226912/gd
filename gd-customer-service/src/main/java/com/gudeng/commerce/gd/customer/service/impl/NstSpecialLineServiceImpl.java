package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.NstSpecialLineDTO;
import com.gudeng.commerce.gd.customer.entity.NstSpecialLineEntity;
import com.gudeng.commerce.gd.customer.service.NstSpecialLineService;

public class NstSpecialLineServiceImpl implements NstSpecialLineService {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<NstSpecialLineDTO> getSpecialLineByCondition(
			Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("NstSpecialLine.getSpecialLineByCondition", paramMap, NstSpecialLineEntity.class);
	}

}
