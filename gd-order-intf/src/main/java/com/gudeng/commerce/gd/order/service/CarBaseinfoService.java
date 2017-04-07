package com.gudeng.commerce.gd.order.service;

import com.gudeng.commerce.gd.order.dto.CarBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public interface CarBaseinfoService {

	public CarBaseinfoDTO getByCarNumber(String carNumber);
	
	public Long insertEntity(CarBaseinfoEntity entity);

	public int update(CarBaseinfoEntity carEntity);
	
	@Transactional
	public int addCarNumber(CarBaseinfoEntity entity, Long wcId);
}
