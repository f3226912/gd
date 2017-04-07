package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.OrderRefundRecordToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderRefundRecordDTO;
import com.gudeng.commerce.gd.order.entity.OrderRefundRecordEntity;
import com.gudeng.commerce.gd.order.service.OrderRefundRecordService;

public class OrderRefundRecordToolServiceImpl implements OrderRefundRecordToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static OrderRefundRecordService orderRefundRecordService;

	protected OrderRefundRecordService getHessianOrderRefundRecordService() throws MalformedURLException {
		String url = gdProperties.getOrderRefundRecordServiceUrl();
		if (orderRefundRecordService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderRefundRecordService = (OrderRefundRecordService) factory.create(OrderRefundRecordService.class, url);
		}
		return orderRefundRecordService;
	}

	@Override
	public OrderRefundRecordDTO getById(String id) throws Exception {
		return getHessianOrderRefundRecordService().getById(id);
	}

	@Override
	public List<OrderRefundRecordDTO> getList(Map<String, Object> map) throws Exception {
		return getHessianOrderRefundRecordService().getList(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianOrderRefundRecordService().deleteById(id);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		return getHessianOrderRefundRecordService().deleteBatch(list);
	}

	@Override
	public int update(OrderRefundRecordDTO t) throws Exception {
		return getHessianOrderRefundRecordService().update(t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianOrderRefundRecordService().getTotal(map);
	}

	@Override
	public Long insert(OrderRefundRecordEntity entity) throws Exception {
		return getHessianOrderRefundRecordService().insert(entity);
	}

	@Override
	public List<OrderRefundRecordDTO> getListPage(Map<String, Object> map) throws Exception {
		return getHessianOrderRefundRecordService().getListPage(map);
	}
}