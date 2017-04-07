package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.ProOperateToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.ProOperateDTO;
import com.gudeng.commerce.info.customer.entity.ProOperateEntity;
import com.gudeng.commerce.info.customer.service.ProOperateService;

@Service
public class ProOperateToolServiceImpl implements ProOperateToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static ProOperateService proOperateService;
	
	private ProOperateService gethessianProOperateService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getProOperateServiceUrl();
		if (proOperateService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			proOperateService = (ProOperateService) factory.create(ProOperateService.class, hessianUrl);
		}
		return proOperateService;
	}

	@Override
	public Long insertEntity(ProOperateEntity obj) throws Exception {
		return gethessianProOperateService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianProOperateService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianProOperateService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(ProOperateDTO obj) throws Exception {
		return gethessianProOperateService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<ProOperateDTO> objList) throws Exception {
		return gethessianProOperateService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianProOperateService().getTotal(map);
	}

	@Override
	public ProOperateDTO getById(Long id) throws Exception {
		return gethessianProOperateService().getById(id);
	}

	@Override
	public List<ProOperateDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianProOperateService().getListByConditionPage(map);
	}

	@Override
	public List<ProOperateDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianProOperateService().getListByCondition(map);
	}

	@Override
	public List<ProOperateDTO> sumReport(Map<String, Object> map) throws Exception {
		return gethessianProOperateService().sumReport(map);
	}
}
