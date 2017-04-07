package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PvStatisticBusinessToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;
import com.gudeng.commerce.gd.customer.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.customer.service.statis.PvStatisticBusinessService;

/**
 * 功能描述：
 */
@Service
public class PvStatisticBusinessToolServiceImpl implements PvStatisticBusinessToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static PvStatisticBusinessService pvStatisticBusinessService;

	/**
	 * 功能描述: memberChangeLogService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected PvStatisticBusinessService getHessianPvStatisticBusinessService() throws MalformedURLException {
		String url = gdProperties.getPvStatisticBusinessUrl();
		if (pvStatisticBusinessService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pvStatisticBusinessService = (PvStatisticBusinessService) factory.create(PvStatisticBusinessService.class, url);
		}
		return pvStatisticBusinessService;
	}

	@Override
	public void sendMsg(Long memberId, String mobile, Integer alidauCode, MessageTemplateEnum template,
			Map<String, Object> params) throws MalformedURLException {
		getHessianPvStatisticBusinessService().sendMsg(memberId, mobile, alidauCode, template, params);
	}
	
	@Override
	public int getTotal(Map map) throws Exception{
		return getHessianPvStatisticBusinessService().getTotal(map);
	}
	
	@Override
	public List<BusinessPvStatisDTO> getBySearch(Map map) throws Exception{
		return getHessianPvStatisticBusinessService().getBySearch(map);
	}
	
	@Override
	public int getAmountTotal(Map map) throws Exception{
		return getHessianPvStatisticBusinessService().getAmountTotal(map);
	}
	
	@Override
	public List<BusinessPvStatisDTO> getAmountBySearch(Map map) throws Exception{
		return getHessianPvStatisticBusinessService().getAmountBySearch(map);
	}
	
	@Override
	public void updatePv(Map map) throws Exception{
		getHessianPvStatisticBusinessService().updatePv(map);
	}
}
