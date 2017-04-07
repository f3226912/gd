package com.gudeng.commerce.gd.admin.service.active.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.active.PromBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.active.PromRuleFeeToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.service.PromRuleFeeService;

public class PromRuleFeeToolServiceImpl implements PromRuleFeeToolService{
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	@Resource
	private PromBaseinfoToolService promBaseinfoToolService;
	
	private PromRuleFeeService promRuleFeeService;
	
	private PromRuleFeeService hessianPromProdinfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getPromRuleFeeServiceUrl();
		if(promRuleFeeService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promRuleFeeService = (PromRuleFeeService) factory.create(PromRuleFeeService.class, hessianUrl);
		}
		return promRuleFeeService;
	}

	@Override
	public void savePromRuleFee(PromBaseinfoDTO dto) throws Exception {
		if(null != dto.getActId()){
			if(promBaseinfoToolService.validatePromStart(dto.getActId())){
				throw new RuntimeException("活动["+dto.getActId()+"]已开始，不能修改");
			}
		}
		
		hessianPromProdinfoService().savePormRuleFee(dto);
	}

	@Override
	public PromBaseinfoDTO queryPromRuleFeeByActId(Integer actId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("actId", actId);
		return hessianPromProdinfoService().queryPromRuleFee(map);
	}

}
