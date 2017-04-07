package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.PushRecordDTO;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.commerce.gd.customer.service.PushRecordService;
import com.gudeng.commerce.gd.home.service.PushRecordToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;

public class PushRecordToolServiceImpl implements  PushRecordToolService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static PushRecordService pushRecordService;
	@Override
	public void add(PushRecordEntity pushRecordEntity) throws MalformedURLException {

		getPushRecordService().add(pushRecordEntity);
	}

	@Override
	public void delete(Long id) throws MalformedURLException {

		getPushRecordService().delete(id);
	}
	
	
	@Override
	public void batchDel(String ids) throws MalformedURLException {
		List<String> para=new ArrayList<>();
		para=Arrays.asList(ids.split(","));
		for (String id : para) {
			getPushRecordService().delete(Long.valueOf(id));
		}
		
	}
	
	protected PushRecordService getPushRecordService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.pushRecord.url");
		if (pushRecordService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushRecordService = (PushRecordService) factory.create(PushRecordService.class, url);
		}
		return pushRecordService;
	}

	@Override
	public List getList() throws MalformedURLException {
		return getPushRecordService().getList();
	}
	
	public List getList(Integer state) throws MalformedURLException {
		return getPushRecordService().getList(state);
	}
	
	@Override
	public List getList(Long memberId,Integer state) throws MalformedURLException {
		return getPushRecordService().getList(memberId,state);
	}
	
	@Override
	public void update(PushRecordDTO pushRecordDTO)
			throws MalformedURLException {
		String ids=pushRecordDTO.getIds();
		List<String> para=new ArrayList<>();
		para=Arrays.asList(ids.split(","));
		if (para!=null&&para.size()>0) {
			for (String id : para) {
				pushRecordDTO.setId(Long.valueOf(id));
				pushRecordDTO.setState(pushRecordDTO.getState());
				getPushRecordService().update(pushRecordDTO);
			}
		}
	 }

	@Override
	public Integer getCount(Integer state) throws MalformedURLException {
		return 	getPushRecordService().getCount(state);
	}

	@Override
	public List getList(Long memberId) throws MalformedURLException {
		return 	getPushRecordService().getList(memberId);
	}

	@Override
	public Integer getCount(Integer isRead, Long memberId)
			throws MalformedURLException {
		
		return 	getPushRecordService().getCount(isRead,memberId);
	}

	@Override
	public List<PushRecordDTO> getList(Map mapParam)
			throws MalformedURLException {
		return 	getPushRecordService().getList(mapParam);
	
	}

}
