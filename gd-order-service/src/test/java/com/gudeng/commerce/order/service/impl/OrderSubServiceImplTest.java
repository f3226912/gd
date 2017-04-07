package com.gudeng.commerce.order.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.service.OrderSubService;
import com.gudeng.commerce.order.service.impl.AbstractServiceTest;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2015年12月14日 下午6:52:30
 */
public class OrderSubServiceImplTest extends AbstractServiceTest {
	
	@Autowired
	private OrderSubService orderSubService;
	
	@Test
	public void testHandleOrderSubAmtToDB() throws ServiceException {
		orderSubService.handleOrderSubAmtToDB(14502446419330596L);
		System.out.println("hello");
	}
	
	@Test
	public void testCheckOrderSubRuleToDB() throws ServiceException {
		orderSubService.checkOrderSubRuleToDB(14502446419330596L);
	}
	
	@Test
	public void testQueryProductSubList() throws Exception {
		List<OrderProductDetailDTO> orderProductDetails = new ArrayList<OrderProductDetailDTO>();
		OrderProductDetailDTO productDetail = new OrderProductDetailDTO();
		productDetail.setProductId(36431);
		orderProductDetails.add(productDetail);
		
//		productDetail = new OrderProductDetailDTO();
//		productDetail.setProductId(33886);
//		orderProductDetails.add(productDetail);
		orderSubService.queryProductSubList(orderProductDetails);
	}
	
	@Test
	public void testSubTotalAmountCheckRule() throws Exception {
		orderSubService.subTotalAmountCheckRule(392015000000892L);
	}

}
