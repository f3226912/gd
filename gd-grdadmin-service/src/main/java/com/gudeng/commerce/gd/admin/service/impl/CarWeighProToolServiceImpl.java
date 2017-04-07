package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.CarWeighProToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;
import com.gudeng.commerce.gd.order.service.CarWeighProService;

/**
 * 车载重数据
 * @author Ailen
 *
 */
@Service
public class CarWeighProToolServiceImpl implements CarWeighProToolService {
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	public static CarWeighProService carWeighProService;
	
	
	
	public CarWeighProService getCarWeighProService() throws MalformedURLException{
		String url = gdProperties.getCarWeighProServiceUrl();
		if(carWeighProService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			carWeighProService = (CarWeighProService) factory.create(CarWeighProService.class, url);
		}
		return carWeighProService;
	}

	@Override
	public List<CarWeighProDTO> getCarWeighProList(Map<String, Object> map) throws Exception {
		return getCarWeighProService().getCarWeighProList(map);
	}

	@Override
	public int updateCarWeighPro(CarWeighProDTO carWeighProDTO) throws Exception {
		return getCarWeighProService().updateCarWeighPro(carWeighProDTO);
	}

	@Override
	public int insertCarWeighPro(CarWeighProDTO carWeighProDTO)
			throws Exception {
		return getCarWeighProService().insertCarWeighPro(carWeighProDTO);
	}

}
