package com.gudeng.commerce.order.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.service.SubHelpService;
import com.gudeng.commerce.gd.order.util.DateUtil;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2015年12月18日 下午3:40:15
 */
public class SubHelpServiceImplTest extends AbstractServiceTest {
	
	@Autowired
	private SubHelpService subHelpService;
	
	@Test
	public void testGetAllSubAmountByDay() throws Exception {
		Double subAmount = subHelpService.getAllSubAmountByDay(1L);
		System.out.println(subAmount);
	}
	
	@Test
	public void testGetAllSubAmountByWeek() throws Exception {
		Double subAmount = subHelpService.getAllSubAmountByWeek(1L);
		System.out.println(subAmount);
	}
	
	@Test
	public void testGetAllSubAmountByMonth() throws Exception {
		Double subAmount = subHelpService.getAllSubAmountByMonth(1L);
		System.out.println(subAmount);
	}
	
	@Test
	public void testGetAllSubAmountByAll() throws Exception {
		Double subAmount = subHelpService.getAllSubAmountByAll(1L, DateUtil.formateDate("2015-01-01"), DateUtil.formateDate("2015-12-31"));
		System.out.println(subAmount);
	}
	
	@Test
	public void testGetMemberSubAmountByDay() throws Exception {
		Double subAmount = subHelpService.getMemberSubAmountByDay(1L, 261L, EMemberType.BUYER.getCode());
		System.out.println(subAmount);
		subAmount = subHelpService.getMemberSubAmountByDay(1L, 261L, EMemberType.WHOLESALER.getCode());
		System.out.println(subAmount);
	}
	
	@Test
	public void testGetMemberSubAmountByWeek() throws Exception {
		Double subAmount = subHelpService.getMemberSubAmountByWeek(1L, 261L, EMemberType.BUYER.getCode());
		System.out.println(subAmount);
		subAmount = subHelpService.getMemberSubAmountByWeek(1L, 261L, EMemberType.WHOLESALER.getCode());
		System.out.println(subAmount);
	}
	
	@Test
	public void testGetMemberSubAmountByMonth() throws Exception {
		Double subAmount = subHelpService.getMemberSubAmountByMonth(1L, 261L, EMemberType.BUYER.getCode());
		System.out.println(subAmount);
		subAmount = subHelpService.getMemberSubAmountByMonth(1L, 261L, EMemberType.WHOLESALER.getCode());
		System.out.println(subAmount);
	}
	
	@Test
	public void testGetMemberSubAmountByAll() throws Exception {
		Double subAmount = subHelpService.getMemberSubAmountByAll(1L, 261L, EMemberType.BUYER.getCode(), DateUtil.formateDate("2015-01-01"), DateUtil.formateDate("2015-12-31"));
		System.out.println(subAmount);
		subAmount = subHelpService.getMemberSubAmountByAll(1L, 261L, EMemberType.WHOLESALER.getCode(), DateUtil.formateDate("2015-01-01"), DateUtil.formateDate("2015-12-31"));
		System.out.println(subAmount);
	}
	
	@Test
	public void testGetSingleUserTradingFrequencyByDay() throws Exception {
		Integer count = subHelpService.getSingleUserTradingFrequencyByDay(1L, 261L, EMemberType.BUYER.getCode());
		System.out.println(count);
		count = subHelpService.getSingleUserTradingFrequencyByDay(1L, 261L, EMemberType.WHOLESALER.getCode());
		System.out.println(count);
	}
	
	@Test
	public void testGetSingleUserTradingFrequencyByWeek() throws Exception {
		Integer count = subHelpService.getSingleUserTradingFrequencyByWeek(1L, 261L, EMemberType.BUYER.getCode());
		System.out.println(count);
		count = subHelpService.getSingleUserTradingFrequencyByWeek(1L, 261L, EMemberType.WHOLESALER.getCode());
		System.out.println(count);
	}
	
	@Test
	public void testGetSingleUserTradingFrequencyByMonth() throws Exception {
		Integer count = subHelpService.getSingleUserTradingFrequencyByMonth(1L, 261L, EMemberType.BUYER.getCode());
		System.out.println(count);
		count = subHelpService.getSingleUserTradingFrequencyByMonth(1L, 261L, EMemberType.WHOLESALER.getCode());
		System.out.println(count);
	}
	
	@Test
	public void testGetSingleUserTradingFrequencyByAll() throws Exception {
		Integer count = subHelpService.getSingleUserTradingFrequencyByAll(1L, 261L, EMemberType.BUYER.getCode(), DateUtil.formateDate("2015-01-01"), DateUtil.formateDate("2015-12-31"));
		System.out.println(count);
		count = subHelpService.getSingleUserTradingFrequencyByAll(1L, 261L, EMemberType.WHOLESALER.getCode(), DateUtil.formateDate("2015-01-01"), DateUtil.formateDate("2015-12-31"));
		System.out.println(count);
	}

}
