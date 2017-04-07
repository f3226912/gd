package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.CarManagerApiService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.service.CarsService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public class CarManagerApiServiceImpl implements CarManagerApiService {

	
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(CarManagerApiService.class);
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	private static CarsService carsService;
	

    
	private CarsService hessianCarsService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getCarsManagerUrl();
		if (carsService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			carsService = (CarsService) factory.create(
					CarsService.class, hessianUrl);
		}
		return carsService;
	}
	@Override
	public int addCarMessage(CarsDTO carsDTO) throws Exception {
		return hessianCarsService().addCarsDTO(carsDTO);
	}
	@Override
	public CarsDTO getCarMessageById(String id) throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().getById(id);
	}
	@Override
	public int updateCars(CarsDTO carsDTO) throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().updateCarsDTO(carsDTO);
	}
	@Override
	public int delCars(Long id) throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().deleteById(Long.toString(id));
	}
	@Override
	public List<CarsDTO> listCarByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().listCarByUserId(userId);
	}
	@Override
	public List<CarsDTO>  getCarNumber(CarsDTO carsDTO)  throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().listCarNumber(carsDTO);
	}
	@Override
	public CarsDTO getCarTotal(CarsDTO carsDTO) throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().getCarTotal(carsDTO.getUserId());
	}
	@Override
	public int addActity(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().addActity(map);
	}
	@Override
	public CarsDTO getCarByCarNumber(String carNumber) throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().getCarByCarNumber(carNumber);
	}
	
	/**
	 * 判断是否可以删除车辆
	 */
	@Override
	public int delTotalCars(Long id) throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().delTotalCars(id);
	}
	
	/**
	 * 判断是否可以新增车辆
	 */
	@Override
	public int addCarMessageVity(CarsDTO carsDTO) throws Exception {
		// TODO Auto-generated method stub
		return hessianCarsService().addCarMessageVity(carsDTO);
	}
	@Override
	public List<CarsDTO> queryByCarNumber(String carNumber, boolean isDeleted) throws Exception {
		return hessianCarsService().queryByCarNumber(carNumber, isDeleted);
	}

}
