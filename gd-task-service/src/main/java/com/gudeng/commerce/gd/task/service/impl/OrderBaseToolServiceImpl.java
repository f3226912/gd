package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.task.service.OrderBaseToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;

/**
 * @Description: 
 * @author mpan
 * @date 2015年12月8日 下午8:00:10
 */
public class OrderBaseToolServiceImpl implements OrderBaseToolService {
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static OrderBaseinfoService orderBaseInfoService;
	
	private OrderProductDetailService orderProductDetailService;
	
	protected OrderBaseinfoService getHessianService() throws ServiceException {
		try {
			String url = gdProperties.getProperties().getProperty("gd.orderBaseinfoService.url");
			if (orderBaseInfoService == null) {
				HessianProxyFactory factory = new HessianProxyFactory();
				factory.setOverloadEnabled(true);
				orderBaseInfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, url);
			}
			return orderBaseInfoService;
		} catch (MalformedURLException e) {
			throw new ServiceException("获取hessian服务失败", e);
		}
	}
	
	private OrderProductDetailService getHessianOrderProductService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderProductDetail.url");
		if (orderProductDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderProductDetailService = (OrderProductDetailService) factory.create(OrderProductDetailService.class, hessianUrl);
		}
		return orderProductDetailService;
	}

	@Override
	public OrderBaseinfoDTO queryOrderDetail(Long orderNo) throws Exception {
		return orderBaseInfoService.queryOrderDetail(orderNo);
	}

	@Override
	public List<OrderBaseinfoDTO> getOverOrderInfoList(Map<String, Object> map)
			throws Exception {
		return getHessianService().getOverOrderInfoList(map);
	}

	@Override
	public Integer cancelOrderByOrderNo(Map<String, Object> map)
			throws Exception {
		return getHessianService().cancelByOrderNo(map);
	}

	@Override
	public List<OrderBaseinfoDTO> getTwoAlreadyPayOrderList(
			Map<String, Object> map) throws Exception {
		return getHessianService().getTwoAlreadyPayOrderList(map);
	}

	@Override
	public int updateByOrderNo(OrderBaseinfoDTO dto) throws Exception {
		return getHessianService().updateByOrderNo(dto);
	}

	@Override
	public List<OrderBaseinfoDTO> getUnpaidOrderList(Map<String, Object> map)
			throws Exception {
		return getHessianService().getUnpaidOrderList(map);
	}

	@Override
	public List<OrderProductDetailDTO> getListByOrderNo(
			Map<String, Object> orderNoMap) throws Exception {
		return getHessianOrderProductService().getListByOrderNo(orderNoMap);
	}

	@Override
	public OrderBaseinfoDTO getByOrderNo(long orderNo) throws Exception {
		return getHessianService().getByOrderNo(orderNo);
	}
}
