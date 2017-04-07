package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.service.PushAdInfoService;
import com.gudeng.commerce.gd.customer.service.PushNoticeService;
import com.gudeng.commerce.gd.home.service.IndexToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;

public class IndexToolServiceImpl implements IndexToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static PushNoticeService pushNoticeService;
	
	private static PushAdInfoService pushAdInfoService;

	/**
	 * 功能描述: pushNoticeService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected PushNoticeService getHessianPushNoticeService()
			throws MalformedURLException {
		String url = gdProperties.getPushNoticeServiceUrl();
		if (pushNoticeService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushNoticeService = (PushNoticeService) factory.create(
					PushNoticeService.class, url);
		}
		return pushNoticeService;
	}
	
	/**
	 * 功能描述: pushNoticeService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected PushAdInfoService getHessianPushAdInfoService()
			throws MalformedURLException {
		String url = gdProperties.getPushAdInfoServiceUrl();
		if (pushAdInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushAdInfoService = (PushAdInfoService) factory.create(
					PushAdInfoService.class, url);
		}
		return pushAdInfoService;
	}

	@Override
	public int getPushNoticeTotal(Map<String, Object> map) throws Exception {
		return getHessianPushNoticeService().getTotal(map);
	}

	@Override
	public PushNoticeDTO getByPushNotice(Long id) throws Exception {
		return getHessianPushNoticeService().getById(id);
	}

	@Override
	public List<PushNoticeDTO> getListByPushNoticePage(Map<String, Object> map)
			throws Exception {
		return getHessianPushNoticeService().getListByConditionPage(map);
	}

	@Override
	public List<PushNoticeDTO> getListByPushNotice(Map<String, Object> map)
			throws Exception {
		return getHessianPushNoticeService().getListByCondition(map);
	}

	@Override
	public List<PushAdInfoDTO> getListByPushAdInfo(Map<String, Object> map)
			throws Exception {
		return getHessianPushAdInfoService().getListByConditionPage(map);
	}
	
	@Override
	public List<PushAdInfoDTO> getListByPushAdInfoShow(Map<String, Object> map)
			throws Exception {
		return getHessianPushAdInfoService().getListByShowPage(map);
	}

}
