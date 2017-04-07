package com.gudeng.commerce.order.service.impl;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.entity.SubAuditEntity;
import com.gudeng.commerce.gd.order.service.OrderNoService;
import com.gudeng.commerce.gd.order.service.OrderNoService;

/**
 * 
 * @description: TODO - 对AuditService提供基本的测试功能
 * @Classname: 
 * @author lmzhang@gdeng.cn
 *
 */
public class OrderNoServiceTest {
	private OrderNoService service;
	private Map<String, Object> conditions;
	
	@Before
	public void initService(){
		try {
            String url = "http://127.0.0.1:8080/gd-order/service/orderNoService.hs" ;
            HessianProxyFactory factory = new HessianProxyFactory();
             service = (OrderNoService) factory.create(OrderNoService.class , url);
             assertNotNull("创建服务失败！", service );
      } catch (MalformedURLException e) {
            e.printStackTrace();
      }
	}
	
	@Test
	public void testQueryForSearch() throws Exception{
//		try {
//			List<SubAuditDTO> result = service.getBySearch(conditions);
//			int count = service.getTotal(conditions);
//			assertNotNull("getBySearch()方法查询查询错误！", service );
//			System.out.println("count.size = " + count);
//			
//			for(SubAuditDTO sa: result){
//				System.out.println(sa.toString());
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	
		for (int i = 0; i < 100; i++) {
			String orderNo=service.getOrderNo();
			System.out.println("order  " + orderNo);
		}
		
	}
	


}
