package com.gudeng.commerce.order.service.impl;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.junit.Before;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketInfoDTO;
import com.gudeng.commerce.gd.order.entity.OrderOutmarketinfoEntity;
import com.gudeng.commerce.gd.order.service.OrderOutmarketinfoService;

/**
 * 
 * @description: TODO - 对OrderOutmarketinfoService的方法进行测试
 * @Classname: OrderOutmarketinfoServiceTest
 * @author lmzhang@gdeng.cn
 *
 */
public class OrderOutmarketinfoServiceTest {
	private OrderOutmarketinfoService service;
	
	private OrderOutmarketinfoEntity entiry;
	
	private OrderOutmarketInfoDTO dto;
	
	@Before
    public void startService(){
		try {
			String url = "http://127.0.0.1:8080/gd-order/service/orderOutmarketinfoService.hs" ;
			HessianProxyFactory factory = new HessianProxyFactory();
			service = (OrderOutmarketinfoService) factory.create(OrderOutmarketinfoService.class , url);
			assertNotNull("创建服务成功！", service );
			
			// 实例化entiry
			entiry = new OrderOutmarketinfoEntity();
			entiry.setOmId(1L);
			entiry.setControllerId(18);
			entiry.setCarNumber("粤B77293");
			entiry.setCarNumberImage("\test\001.jpg");
			entiry.setCarWeightStatus("1");
			entiry.setType("1");
			entiry.setCreateUserId("18");
			entiry.setUpdateUserId("18");
			entiry.setCarId((long)4422);
			entiry.setCreateTime(new Date());
			entiry.setUpdateTime(new Date());
			
			dto = new OrderOutmarketInfoDTO();
			dto.setControllerId(222);
			dto.setCarNumber("粤B222222");
			dto.setCarNumberImage("/test02/222.jsp");
			dto.setCarWeightStatus("2");
			dto.setType("2");
			dto.setCreateUserId("2222");
			dto.setUpdateUserId("22");
			dto.setCarId((long)22);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertEntity(){
		try {
			long r = this.service.insertEntity(entiry);
			assertEquals("insertEntity方法执行失败！", 1, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertSQL(){
		try {
			this.entiry.setOmId(2L);
			int r = this.service.insertSQL(entiry);
			assertEquals("insertSQL方法执行失败！", 2, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteById(){
		try {
			int r = this.service.deleteById((long)2);
			assertEquals("deleteById方法执行失败！", 1, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBatchDeleteById(){
		try {
			List<Long> iList = new ArrayList<Long>();
			iList.add((long)1);
			iList.add((long)2);
			iList.add((long)3);
			iList.add((long)4);
			int r = this.service.batchDeleteById(iList);
			// 当前包的版本不支持hamcrest
			//assertThat(reason, r, org.hamcrest.MatcherAssert.);
			System.out.println("r = " + r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateDTO(){
		try {
			service.updateDTO(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTotal(){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("controllerId", 222);
			int r = service.getTotal(map);
			assertEquals("getTotal测试结果不正确！", 1,r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetById(){
		try {
			OrderOutmarketInfoDTO dto = service.getById((long)2);
			assertNotNull("getById测试失败！", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetListByConditionPage(){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "22");
			map.put("controllerId", 18);
			List<OrderOutmarketInfoDTO> list = service.getListByConditionPage(map);
			assertNotNull("getListByConditionPage方法调用失败！", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
