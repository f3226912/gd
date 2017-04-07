package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.FarmersMarketToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.FarmersMarketDTO;
import com.gudeng.commerce.gd.customer.entity.FarmersMarketEntity;
import com.gudeng.commerce.gd.customer.service.FarmersMarketService;

@Service
public class FarmersMarketToolServiceImpl implements FarmersMarketToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static FarmersMarketService farmersMarketService;
	
	private FarmersMarketService gethessianFarmersMarketService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getFarmersMarketUrl();
		if (farmersMarketService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			farmersMarketService = (FarmersMarketService) factory.create(FarmersMarketToolService.class, hessianUrl);
		}
		return farmersMarketService;
	}

	@Override
	public Long insertEntity(FarmersMarketEntity obj) throws Exception {
		return gethessianFarmersMarketService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianFarmersMarketService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianFarmersMarketService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(FarmersMarketDTO obj) throws Exception {
		return gethessianFarmersMarketService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<FarmersMarketDTO> objList) throws Exception {
		return gethessianFarmersMarketService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianFarmersMarketService().getTotal(map);
	}

	@Override
	public FarmersMarketDTO getById(Long id) throws Exception {
		return gethessianFarmersMarketService().getById(id);
	}

	@Override
	public List<FarmersMarketDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianFarmersMarketService().getListByConditionPage(map);
	}

	@Override
	public List<FarmersMarketDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianFarmersMarketService().getListByCondition(map);
	}

	
}
