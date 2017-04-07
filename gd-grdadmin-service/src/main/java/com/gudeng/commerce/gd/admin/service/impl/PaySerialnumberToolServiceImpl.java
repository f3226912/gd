package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;

@Service
public class PaySerialnumberToolServiceImpl implements PaySerialnumberToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static PaySerialnumberService paySerialnumberService;
	
	private PaySerialnumberService gethessianPaySerialnumberService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getPaySerialnumberUrl();
		if (paySerialnumberService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			paySerialnumberService = (PaySerialnumberService) factory.create(PaySerialnumberService.class, hessianUrl);
		}
		return paySerialnumberService;
	}

	@Override
	public Long insertEntity(PaySerialnumberEntity obj) throws Exception {
		return gethessianPaySerialnumberService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianPaySerialnumberService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianPaySerialnumberService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(PaySerialnumberDTO obj) throws Exception {
		return gethessianPaySerialnumberService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<PaySerialnumberDTO> objList) throws Exception {
		return gethessianPaySerialnumberService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianPaySerialnumberService().getTotal(map);
	}

	@Override
	public PaySerialnumberDTO getById(Long id) throws Exception {
		return gethessianPaySerialnumberService().getById(id);
	}
	
	@Override
	public PaySerialnumberDTO getByOrderNo(Long orderNo) throws Exception {
		return gethessianPaySerialnumberService().getByOrderNo(orderNo);
	}

	@Override
	public List<PaySerialnumberDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianPaySerialnumberService().getListByConditionPage(map);
	}

	@Override
	public List<PaySerialnumberDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianPaySerialnumberService().getListByCondition(map);
	}

	@Override
	public PaySerialnumberDTO getByOrderNoAndPayType(Long orderNo)
			throws Exception {
		return gethessianPaySerialnumberService().getByOrderNoAndPayType(orderNo);
	}

	
}
