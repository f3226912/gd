package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;

@Service
public class PaySerialnumberToolServiceImpl implements PaySerialnumberToolService{
	@Autowired
	private GdProperties gdProperties;
	
	private PaySerialnumberService paySerialnumberService;

	
	protected PaySerialnumberService getPaySerialnumberService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.paySerialnumberService.url");
		if(paySerialnumberService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			paySerialnumberService = (PaySerialnumberService) factory.create(PaySerialnumberService.class, url);
		}
		return paySerialnumberService;
	}
	@Override
	public Long insertEntity(PaySerialnumberEntity obj) throws Exception {
		return getPaySerialnumberService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return 0;
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return 0;
	}

	@Override
	public int updateDTO(PaySerialnumberDTO obj) throws Exception {
		return 0;
	}

	@Override
	public int batchUpdateDTO(List<PaySerialnumberDTO> objList)
			throws Exception {
		return 0;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return 0;
	}

	@Override
	public PaySerialnumberDTO getById(Long id) throws Exception {
		return null;
	}

	@Override
	public List<PaySerialnumberDTO> getListByConditionPage(
			Map<String, Object> map) throws Exception {
		return null;
	}

	@Override
	public List<PaySerialnumberDTO> getListByCondition(Map<String, Object> map)
			throws Exception {
		return getPaySerialnumberService().getListByCondition(map);
	}

	@Override
	public PaySerialnumberDTO getByOrderNo(Long orderNo)
			throws Exception {
		return getPaySerialnumberService().getByOrderNo(orderNo);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PaySerialnumberDTO> getByMemberIdAndDay(PaySerialnumberDTO paySerialnumberDTO)
			throws Exception {
		List retList = new ArrayList<>();
		Map<String , Object > mapParam = new HashMap<>();
		for (int i = 0; i < 3; i++) {
			Map<String, Object> singleDayData = new HashMap<>();
			List<PaySerialnumberDTO> listTransPerDay = new ArrayList<>();
			Calendar calendarStatrt = DateTimeUtils.beforeNDays(Calendar.getInstance(), (paySerialnumberDTO.getCount()-1)*3);
			
			Date date = DateTimeUtils.beforeNDays(calendarStatrt, i).getTime();
			String dateParam = DateUtil.toString(date, DateUtil.DATE_FORMAT_DATEONLY);
			mapParam.put("createTime", dateParam); 
			mapParam.put("memberId", paySerialnumberDTO.getMemberId());
			mapParam.put("businessId", paySerialnumberDTO.getBusinessId());
			listTransPerDay = getPaySerialnumberService().getByMemberIdAndDay(mapParam);
			if (null == listTransPerDay || listTransPerDay.size() == 0) {
				 continue;
			}
			singleDayData.put("listTransPerDay", listTransPerDay);
			singleDayData.put("date", dateParam);
			retList.add(singleDayData);
		}
		return retList;
	}
	
	@Override
	public int getTotalByStatementId(String statementId) throws Exception {
		return getPaySerialnumberService().getTotalByStatementId(statementId);
	}

	@Override
	public int insertPayStatementId(String statementId) throws Exception {
		return getPaySerialnumberService().insertPayStatementId(statementId);
	}
}
