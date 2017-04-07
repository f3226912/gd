package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.DatasourceBaiduToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.DatasourceBaiduDTO;
import com.gudeng.commerce.info.customer.entity.DatasourceBaiduEntity;
import com.gudeng.commerce.info.customer.service.DatasourceBaiduService;

@Service
public class DatasourceBaiduToolServiceImpl implements DatasourceBaiduToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static DatasourceBaiduService datasourceService;
	
	private DatasourceBaiduService gethessianDatasourceBaiduService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getDatasourceBaiduServiceUrl();
		if (datasourceService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			datasourceService = (DatasourceBaiduService) factory.create(DatasourceBaiduService.class, hessianUrl);
		}
		return datasourceService;
	}

	@Override
	public Long insertEntity(DatasourceBaiduEntity obj) throws Exception {
		return gethessianDatasourceBaiduService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianDatasourceBaiduService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianDatasourceBaiduService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(DatasourceBaiduDTO obj) throws Exception {
		return gethessianDatasourceBaiduService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<DatasourceBaiduDTO> objList) throws Exception {
		return gethessianDatasourceBaiduService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianDatasourceBaiduService().getTotal(map);
	}

	@Override
	public DatasourceBaiduDTO getById(Long id) throws Exception {
		return gethessianDatasourceBaiduService().getById(id);
	}

	@Override
	public List<DatasourceBaiduDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianDatasourceBaiduService().getListByConditionPage(map);
	}

	@Override
	public List<DatasourceBaiduDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianDatasourceBaiduService().getListByCondition(map);
	}

	
}
