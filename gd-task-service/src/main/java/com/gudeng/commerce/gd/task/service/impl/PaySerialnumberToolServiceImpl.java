package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import com.gudeng.commerce.gd.task.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;


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
	
	@Override
	public int getTotalByStatementId(String statementId) throws Exception {
		return getPaySerialnumberService().getTotalByStatementId(statementId);
	}

	@Override
	public int insertPayStatementId(String statementId) throws Exception {
		return getPaySerialnumberService().insertPayStatementId(statementId);
	}
}
