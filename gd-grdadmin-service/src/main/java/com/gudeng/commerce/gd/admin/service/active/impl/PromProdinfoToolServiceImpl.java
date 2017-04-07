package com.gudeng.commerce.gd.admin.service.active.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.active.PromBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.active.PromProdinfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.service.PromProdinfoService;

public class PromProdinfoToolServiceImpl implements PromProdinfoToolService{
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private PromProdinfoService promProdinfoService;
	
	@Resource
	private PromBaseinfoToolService promBaseinfoToolService;
	
	private PromProdinfoService hessianPromProdinfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getPromProdinfoServiceUrl();
		if(promProdinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promProdinfoService = (PromProdinfoService) factory.create(PromProdinfoService.class, hessianUrl);
		}
		return promProdinfoService;
	}
	

	@Override
	public List<PromProdInfoDTO> querySupplerPageByCondition(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return hessianPromProdinfoService().querySupplerPageByCondition(params);
	}

	@Override
	public Integer getSupplerTotalCountByCondition(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return hessianPromProdinfoService().getSupplerTotalCountByCondition(map);
	}


	@Override
	public List<PromProdInfoDTO> queryProductPageByCondition(Map<String, Object> map)throws Exception {
		// TODO Auto-generated method stub
		return hessianPromProdinfoService().queryProductPageByCondition(map);
	}


	@Override
	public Integer getProductTotalCountByCondition(Map<String, Object> map) throws Exception{
		// TODO Auto-generated method stub
		return hessianPromProdinfoService().getProductTotalCountByCondition(map);
	}


	@Override
	public Integer updatePromProdInfo(List<PromProdInfoDTO> list) throws Exception {
		
		if(null != list && list.size() > 0){
			PromProdInfoDTO dto = list.get(0);
			if(promBaseinfoToolService.validatePromStart(dto.getActId())){
				throw new RuntimeException("活动["+dto.getActId()+"]已开始，不能修改");
			}
		}
		return hessianPromProdinfoService().updatePromProdInfo(list);
	}

}
