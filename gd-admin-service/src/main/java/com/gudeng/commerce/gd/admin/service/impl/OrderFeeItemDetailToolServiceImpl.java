package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.OrderFeeItemDetailToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderFeeItemDetailService;

public class OrderFeeItemDetailToolServiceImpl implements OrderFeeItemDetailToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static OrderFeeItemDetailService orderFeeItemDetailService;

	protected OrderFeeItemDetailService getHessianOrderFeeItemDetailService() throws MalformedURLException {
		String url = gdProperties.getOrderFeeItemDetailServiceUrl();
		if (orderFeeItemDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderFeeItemDetailService = (OrderFeeItemDetailService) factory.create(OrderFeeItemDetailService.class, url);
		}
		return orderFeeItemDetailService;
	}

	@Override
	public OrderFeeItemDetailDTO getById(String id) throws Exception {
		return getHessianOrderFeeItemDetailService().getById(id);
	}

	@Override
	public List<OrderFeeItemDetailDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianOrderFeeItemDetailService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianOrderFeeItemDetailService().deleteById(id);
	}
	@Override
	public int deleteByParam(Map<String,Object> map) throws Exception {
		return getHessianOrderFeeItemDetailService().deleteByParam(map);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianOrderFeeItemDetailService().deleteBatch(list);
	}

	@Override
	public int update(OrderFeeItemDetailDTO t) throws Exception {
		return getHessianOrderFeeItemDetailService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianOrderFeeItemDetailService().getTotal(map);
	}

	@Override
	public Long insert(OrderFeeItemDetailEntity entity) throws Exception {
		return getHessianOrderFeeItemDetailService().insert(entity);
	}

	@Override
	public List<OrderFeeItemDetailDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianOrderFeeItemDetailService().getListPage(map);
	}

	@Override
	public int batchInsert(List<OrderFeeItemDetailEntity> feelList) throws Exception {
		return getHessianOrderFeeItemDetailService().batchInsert(feelList);
	}
}