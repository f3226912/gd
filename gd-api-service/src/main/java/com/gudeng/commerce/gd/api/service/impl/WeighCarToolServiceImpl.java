package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.WeighCarToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarOrderDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;
import com.gudeng.commerce.gd.order.service.WeighCarService;

@Service
public class WeighCarToolServiceImpl implements WeighCarToolService{
	
	@Autowired
	private GdProperties gdProperties;
	
	public static WeighCarService weighCarService;
	
	public WeighCarService getHessianWeighCarService() throws MalformedURLException{
		if(weighCarService == null){
			String url = gdProperties.getWeighCarSerivceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			weighCarService = (WeighCarService) factory.create(WeighCarService.class, url);
		}
		return weighCarService;
	}
	
	@Override
	public List<WeighCarDTO> getWeighCar(Map<String, Object> map) throws Exception {
		return getHessianWeighCarService().getWeighCar(map);
	}

	@Override
	public Long insertWeighCar(WeighCarEntity entity) throws Exception {
		return getHessianWeighCarService().insertEntity(entity);
	}

	@Override
	public WeighCarDTO getById(Long id) throws Exception{
		return getHessianWeighCarService().getById(id);
	}

	@Override
	public int updateTotalWeight(WeighCarEntity entity) throws Exception {
		return getHessianWeighCarService().udateTotalWeight(entity);
	}
	
	@Override
	public int updateTareWeight(WeighCarEntity entity) throws Exception {
		return getHessianWeighCarService().updateTareWeight(entity);
	}

	@Override
	public WeighCarDTO getLastWeighCar(Map<String, Object> map) throws Exception{
		return getHessianWeighCarService().getLastWeighCar(map);
	}

	@Override
	public List<WeighCarDTO> getLastFiveWeighCar(Map<String, Object> map) throws Exception {
		return getHessianWeighCarService().getLastFiveWeighCar(map);
	}

	@Override
	public List<WeighCarOrderDTO> getLastTwoDayWeighCarOrder(Long memberId) throws Exception {
		return getHessianWeighCarService().getLastTwoDayWeighCarOrder(memberId);
	}

	@Override
	public int getLastTwoDayWeighCarOrderTotal(Long memberId) throws Exception {
		return getHessianWeighCarService().getLastTwoDayWeighCarOrderTotal(memberId);
	}

	@Override
	public int updateStatus(WeighCarEntity weighCarEntity) throws Exception{
		return getHessianWeighCarService().updateStatus(weighCarEntity);
	}

	@Override
	public int updateStatusByCarId(String status, Long carId) throws Exception {
		return getHessianWeighCarService().updateStatusByCarId(status, carId);
	}

	@Override
	public int updateStatusByWeiCarId(String status, Long weighCarId) throws Exception {
		return getHessianWeighCarService().updateStatusByWeighCarId(status, weighCarId);
	}

	@Override
	public List<WeighCarOrderDTO> getWeighCarOrderPage(Map<String, Object> map) throws Exception {
		return getHessianWeighCarService().getWeighCarOrderPage(map);
	}

	@Override
	public int getWeighCarOrderTotal(Map<String, Object> map) throws Exception {
		return getHessianWeighCarService().getWeighCarOrderTotal(map);
	}

	

}
