package com.gudeng.commerce.gd.order.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;
import com.gudeng.commerce.gd.order.service.CarWeighProService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

@Service
public class CarWeighProServiceImpl implements CarWeighProService {
	
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<CarWeighProDTO> getCarWeighProList(Map<String, Object> map) {
		return baseDao.queryForList("CarWeighPro.getCarWeighProList", map, CarWeighProDTO.class);
	}

	@Override
	public int updateCarWeighPro(CarWeighProDTO carWeighProDTO) {
		return baseDao.execute("CarWeighPro.updateCarWeighPro", carWeighProDTO);
	}

	@Override
	@Transactional
	public int insertCarWeighPro(CarWeighProDTO carWeighProDTO) throws Exception {
		return baseDao.execute("CarWeighPro.insertCarWeighPro", carWeighProDTO);
	}
}
