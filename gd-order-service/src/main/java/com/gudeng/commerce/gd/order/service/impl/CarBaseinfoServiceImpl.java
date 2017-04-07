package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.CarBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;
import com.gudeng.commerce.gd.order.service.CarBaseinfoService;
import com.gudeng.commerce.gd.order.service.WeighCarService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

@Service
public class CarBaseinfoServiceImpl implements CarBaseinfoService{

	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private WeighCarService carService;
	
	@Override
	public CarBaseinfoDTO getByCarNumber(String carNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("carNumber", carNumber);
		return baseDao.queryForObject("CarBaseinfo.getByCarNumber", map, CarBaseinfoDTO.class);
	}

	@Override
	public Long insertEntity(CarBaseinfoEntity entity) {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public int update(CarBaseinfoEntity carEntity) {
		return baseDao.execute("CarBaseinfo.update", carEntity);
	}

	@Override
	@Transactional
	public int addCarNumber(CarBaseinfoEntity entity, Long wcId) {
		Long carId = insertEntity(entity);
		WeighCarEntity wce = new WeighCarEntity();
		
		if(carId!=0L) {
			wce.setCarId(carId);
			wce.setWeighCarId(wcId);
			return carService.updateById(wce);
		}
		return 0;
	}

}
