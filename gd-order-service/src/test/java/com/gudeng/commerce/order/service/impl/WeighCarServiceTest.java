package com.gudeng.commerce.order.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;
import com.gudeng.commerce.gd.order.service.WeighCarService;

/**
 * 
 * @description: TODO - 对WeighCarService方法进行基本验证
 * @Classname: 
 * @author lmzhang@gdeng.cn
 *
 */
public class WeighCarServiceTest {
	
	private WeighCarService service;
	
	private WeighCarEntity entity;	// 待验证实体类
	
	@Before
	public void initService(){
		try {
			String url = "http://127.0.0.1:8080/gd-order/service/weighCarService.hs" ;
			HessianProxyFactory factory = new HessianProxyFactory();
			this.service = (WeighCarService)factory.create(WeighCarService.class, url);
			
			// 为测试实体设置值
			this.entity = new WeighCarEntity();
			this.entity.setWeighCarId((long)2);
			this.entity.setCarId((long)4444444);
			this.entity.setCreateUserId("18");
			this.entity.setMemberId((long)21);
			this.entity.setType("2");
			this.entity.setUpdateUserId("2");
			this.entity.setTare(34.5);			// 毛重
			this.entity.setNetWeight(20.50);	// 净重
			this.entity.setTotalWeight(55.00);	// 总重
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertEntity(){
		 try {
			long i = this.service.insertEntity(entity);
			assertEquals("插入数据失败！", 1, i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateById(){
		try {
			this.entity.setWeighCarId((long)1);
			this.entity.setMemberId((long)2122);
			this.entity.setCarId((long)444444422);
			this.entity.setTotalWeight(56.00);
			this.entity.setNetWeight(12.45);
			this.entity.setTare(22.22);
			this.entity.setType("22");
			this.entity.setCreateUserId("1822");
			this.entity.setUpdateUserId("222");
			int r = this.service.updateById(this.entity);
			assertEquals("UpdateById方法调用失败！",1, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTotal(){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", 21);
			map.put("carId", 4444444);
			map.put("weight", 55.55);
			map.put("type", 2);
			int r = this.service.getTotal(map);
			assertEquals("getTotal 测试失败！", 1, r);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetById(){
		try {
			WeighCarDTO wc = service.getById((long)1);
			assertNotNull("getById调用出现问题！", wc);
			System.out.println(wc.getTotalWeight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetWeighCarNum(){
		
		int r=-1;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = sdf.parse("2015-12-05");
			Date d2 = sdf.parse("2015-12-06");
			
			// 传入 开始和结束时间
			// 结果应该是 在该时间段内创建的记录数(包括传入的日期)
			r = service.getWeighCarNum("粤B88888", d1, d1);
			System.out.println("测试结果为：" + r);
			assertEquals("getWeighCarNum 方法查询有误！", 1, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
