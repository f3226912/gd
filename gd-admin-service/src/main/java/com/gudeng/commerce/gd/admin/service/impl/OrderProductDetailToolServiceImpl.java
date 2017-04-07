package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.OrderProductDetailToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;

@Service
public class OrderProductDetailToolServiceImpl implements OrderProductDetailToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static OrderProductDetailService orderProductDetailService;
	
	private OrderProductDetailService gethessianOrderProductDetailService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getOrderProductDetailUrl();
		if (orderProductDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderProductDetailService = (OrderProductDetailService) factory.create(OrderProductDetailService.class, hessianUrl);
		}
		return orderProductDetailService;
	}

	@Override
	public Long insertEntity(OrderProductDetailEntity obj) throws Exception {
		return gethessianOrderProductDetailService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianOrderProductDetailService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianOrderProductDetailService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(OrderProductDetailDTO obj) throws Exception {
		return gethessianOrderProductDetailService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<OrderProductDetailDTO> objList) throws Exception {
		return gethessianOrderProductDetailService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianOrderProductDetailService().getTotal(map);
	}

	@Override
	public OrderProductDetailDTO getById(Long id) throws Exception {
		return gethessianOrderProductDetailService().getById(id);
	}

	@Override
	public List<OrderProductDetailDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianOrderProductDetailService().getListByConditionPage(map);
	}

	@Override
	public List<OrderProductDetailDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianOrderProductDetailService().getListByCondition(map);
	}

	@Override
	public List<OrderProductDetailDTO> getListByOrderNo(Map<String, Object> map)
			throws Exception {
		return gethessianOrderProductDetailService().getListByOrderNo(map);
	}

	
}
