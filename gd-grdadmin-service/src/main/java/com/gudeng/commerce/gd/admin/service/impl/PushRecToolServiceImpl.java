package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.PushRecordDTO;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.commerce.gd.customer.service.PushRecordService;
import com.gudeng.commerce.gd.admin.service.PushRecToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;;

public class PushRecToolServiceImpl implements  PushRecToolService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static PushRecordService pushRecordService;
	
	protected PushRecordService getPushRecToolService() throws MalformedURLException {
		String url = gdProperties.getPushRecUrl();

		if (pushRecordService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushRecordService = (PushRecordService) factory.create(PushRecordService.class, url);
		}
		return pushRecordService;
	}
	
	@Override
	public int getTotal(Map map) throws Exception {
		return getPushRecToolService().getTotal(map);
	}
	
	@Override
	public List<PushRecordDTO> getBySearch(Map map) throws Exception {
		return getPushRecToolService().getBySearch(map);
	}

	@Override
	public Long add(PushRecordEntity ushRecordEntity) throws Exception {
		return getPushRecToolService().add(ushRecordEntity);
	}

	@Override
	public int delete(Long id) throws Exception {
		  return getPushRecToolService().delete(id);
	}

	@Override
	public List<PushRecordDTO> getList() throws Exception {
		  return getPushRecToolService().getList();
	}

	@Override
	public List<PushRecordDTO> getList(Integer state)
			  throws Exception {
		  return getPushRecToolService().getList(state);
	}


	@Override
	public int update(PushRecordDTO pushRecordDTO) throws  Exception {
		  return getPushRecToolService().update(pushRecordDTO);
		
	}

	@Override
	public Integer getCount(Integer state) throws   Exception {
		  return getPushRecToolService().getCount(state);
	}
	
	
	
}
