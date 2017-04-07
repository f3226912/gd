package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.order.dto.CarBaseinfoDTO;
import com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity;

public interface CarBaseinfoToolService {

	public CarBaseinfoDTO getByCarNumber(String carNumber) throws Exception;
	
	public Long insertEntity(CarBaseinfoEntity entity) throws Exception;

	public int update(CarBaseinfoEntity carEntity) throws Exception;
}
