package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.DatasourceToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.DatasourceDTO;
import com.gudeng.commerce.info.customer.entity.DatasourceEntity;
import com.gudeng.commerce.info.customer.service.DatasourceService;

@Service
public class DatasourceToolServiceImpl implements DatasourceToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static DatasourceService datasourceService;
	
	private DatasourceService gethessianDatasourceService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getDatasourceServiceUrl();
		if (datasourceService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			datasourceService = (DatasourceService) factory.create(DatasourceService.class, hessianUrl);
		}
		return datasourceService;
	}

	@Override
	public Long insertEntity(DatasourceEntity obj) throws Exception {
		return gethessianDatasourceService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianDatasourceService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianDatasourceService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(DatasourceDTO obj) throws Exception {
		return gethessianDatasourceService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<DatasourceDTO> objList) throws Exception {
		return gethessianDatasourceService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianDatasourceService().getTotal(map);
	}

	@Override
	public DatasourceDTO getById(Long id) throws Exception {
		return gethessianDatasourceService().getById(id);
	}

	@Override
	public List<DatasourceDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianDatasourceService().getListByConditionPage(map);
	}

	@Override
	public List<DatasourceDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianDatasourceService().getListByCondition(map);
	}

	
}
