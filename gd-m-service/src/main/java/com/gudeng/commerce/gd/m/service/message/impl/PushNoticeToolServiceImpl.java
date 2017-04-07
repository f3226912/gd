package com.gudeng.commerce.gd.m.service.message.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.service.PushNoticeService;
import com.gudeng.commerce.gd.m.service.message.PushNoticeToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;


public class PushNoticeToolServiceImpl implements PushNoticeToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static PushNoticeService  pushNoticeService;
	/**
	 * 功能描述:pushNoticeService接口服务
	 * 
	 * @param
	 * @return
	 * @throws MalformedURLException 
	 */
	protected PushNoticeService getHessionPushNoticeService() throws MalformedURLException{
		String url =gdProperties.getPushNoticeUrl();
		if(pushNoticeService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushNoticeService=(PushNoticeService)factory.create(PushNoticeService.class, url);
		}
		return pushNoticeService;
	}
	@Override
	public Integer getUnReadMessageCount(PushNoticeDTO inputParamDTO) throws Exception {
		return getHessionPushNoticeService().getUnReadMessageCount(inputParamDTO);
	}
	@Override
	public List<PushNoticeDTO> getMessageListByUser(PushNoticeDTO inputParamDTO)
			throws Exception {
		return getHessionPushNoticeService().getMessageListByUser(inputParamDTO);
	}
	@Override
	public PushNoticeDTO getMessageDetail(PushNoticeDTO inputParamDTO)
			throws Exception {
		return getHessionPushNoticeService().getMessageDetail(inputParamDTO);
	}
	@Override
	public int updateMessageIsdel(PushNoticeDTO inputParamDTO) throws Exception {
		return getHessionPushNoticeService().updateMessageIsdel(inputParamDTO);
	}

	@Override
	public int updateMessageIsread(PushNoticeDTO inputParamDTO) throws Exception {
		return getHessionPushNoticeService().updateMessageIsread(inputParamDTO);
	}
	@Override
	public Integer getPushUserCount(PushNoticeDTO inputParamDTO)
			throws Exception {
		return getHessionPushNoticeService().getPushUserCount(inputParamDTO);
	}
	@Override
	public PushNoticeDTO getUserRegisteTime(PushNoticeDTO inputParamDTO)
			throws Exception {
		return getHessionPushNoticeService().getUserRegisteTime(inputParamDTO);
	}
	
}
