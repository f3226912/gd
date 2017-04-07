package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.AccTransInfoToolService;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AccTransInfoDTO;
import com.gudeng.commerce.gd.customer.service.AccTransInfoService;

public class AccTransInfoToolServiceImpl implements AccTransInfoToolService{
	@Autowired
	private GdProperties gdProperties;
	private static AccTransInfoService accTransInfoService;

	
	protected AccTransInfoService getAccTransInfoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.accTransInfoService.url");
		if(accTransInfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			accTransInfoService = (AccTransInfoService) factory.create(AccTransInfoService.class, url);
		}
		return accTransInfoService;
	}

	@Override
	public int add(AccTransInfoDTO accTransInfoDTO) throws Exception {
		return getAccTransInfoService().add(accTransInfoDTO);
	}

	@Override
	public List<AccTransInfoDTO> getByMemberId(Map map) throws Exception {
		return getAccTransInfoService().getByMemberId(map);
	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer getTotal(Map map) throws Exception {
		return getAccTransInfoService().getTotal(map);
	
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getByMemberIdAndDay(AccTransInfoDTO accTransInfoDTO) throws Exception {
		List retList=new ArrayList<>();
		Map<String , Object > mapParam=new HashMap<>();
		for (int i = 0; i <3; i++) {
			Map<String, Object> singleDayData=new HashMap<>();
			List<AccTransInfoDTO> listTransPerDay=new ArrayList<>();
			Calendar calendarStatrt=DateTimeUtils.beforeNDays(Calendar.getInstance(), (accTransInfoDTO.getCount()-1)*3);
			
			Date date=DateTimeUtils.beforeNDays(calendarStatrt,i).getTime();
			String dateParam=DateUtil.toString(date, DateUtil.DATE_FORMAT_DATEONLY);
			mapParam.put("createTime", dateParam); 
			mapParam.put("memberId", accTransInfoDTO.getMemberId());
			listTransPerDay=getAccTransInfoService().getByMemberIdAndDay(mapParam);
			singleDayData.put("listTransPerDay", listTransPerDay);
			singleDayData.put("date", dateParam);
			retList.add(singleDayData);
		}
		return retList;
	
	} 
	

}
