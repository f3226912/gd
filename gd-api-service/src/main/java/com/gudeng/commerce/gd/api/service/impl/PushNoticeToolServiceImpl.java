package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.PushNoticeToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.entity.PushNoticeEntity;
import com.gudeng.commerce.gd.customer.service.PushNoticeService;

@Service
public class PushNoticeToolServiceImpl implements PushNoticeToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static PushNoticeService pushNoticeService;
	
	private PushNoticeService gethessianPushNoticeService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getPushNoticeUrl();
		if (pushNoticeService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushNoticeService = (PushNoticeService) factory.create(PushNoticeService.class, hessianUrl);
		}
		return pushNoticeService;
	}

	@Override
	public Long insertEntity(PushNoticeEntity obj) throws Exception {
		return gethessianPushNoticeService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianPushNoticeService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianPushNoticeService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(PushNoticeDTO obj) throws Exception {
		return gethessianPushNoticeService().updateDTO(obj);
	}
	
	@Override
	public int updateNoticeInfo(PushNoticeDTO obj) throws Exception {
		return gethessianPushNoticeService().updateNoticeInfo(obj);
	}

	@Override
	public int batchUpdateDTO(List<PushNoticeDTO> objList) throws Exception {
		return gethessianPushNoticeService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianPushNoticeService().getTotal(map);
	}

	@Override
	public PushNoticeDTO getById(Long id) throws Exception {
		return gethessianPushNoticeService().getById(id);
	}

	@Override
	public List<PushNoticeDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianPushNoticeService().getListByConditionPage(map);
	}

	@Override
	public List<PushNoticeDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianPushNoticeService().getListByCondition(map);
	}
	
	@Override
	public Integer getUnReadMessageCount(PushNoticeDTO inputParamDTO) throws Exception {
		return gethessianPushNoticeService().getUnReadMessageCount(inputParamDTO);
	}
	@Override
	public Integer getUnReadMessageCount(String userId,String client) throws Exception {
		PushNoticeDTO inputParamDTO=new PushNoticeDTO();
		inputParamDTO.setUserID(userId);
		inputParamDTO.setClient(client);
		return gethessianPushNoticeService().getUnReadMessageCount(inputParamDTO);
	}

	
}
