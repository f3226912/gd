package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PushAdInfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.entity.PushAdInfoEntity;
import com.gudeng.commerce.gd.customer.service.PushAdInfoService;

@Service
public class PushAdInfoToolServiceImpl implements PushAdInfoToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static PushAdInfoService pushAdInfoService;
	
	private PushAdInfoService gethessianPushAdInfoService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getPushAdInfoUrl();
		if (pushAdInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushAdInfoService = (PushAdInfoService) factory.create(PushAdInfoService.class, hessianUrl);
		}
		return pushAdInfoService;
	}

	@Override
	public Long insertEntity(PushAdInfoEntity obj) throws Exception {
		return gethessianPushAdInfoService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianPushAdInfoService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianPushAdInfoService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(PushAdInfoDTO obj) throws Exception {
		return gethessianPushAdInfoService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<PushAdInfoDTO> objList) throws Exception {
		return gethessianPushAdInfoService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianPushAdInfoService().getTotal(map);
	}

	@Override
	public PushAdInfoDTO getById(Long id) throws Exception {
		return gethessianPushAdInfoService().getById(id);
	}

	@Override
	public List<PushAdInfoDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianPushAdInfoService().getListByConditionPage(map);
	}

	@Override
	public List<PushAdInfoDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianPushAdInfoService().getListByCondition(map);
	}

	
}
