package com.gudeng.commerce.gd.order.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.service.OrderNoService;

public class OrderNoServiceImpl implements OrderNoService{
	@Autowired
	private BaseDao<?> baseDao;
	
	
	private final String str="000000000";
	/**
	 * 随机数（2位）+年份（2015）+自增长序列（9位）
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getOrderNo() throws Exception{
		Map map = new HashMap();
		map.put("orderNo", "orderNo");
		StringBuffer orderNo=new StringBuffer("");
		String seq= baseDao.queryForObject("OrderNo.getOrderNo",map,String.class);
		Random random=new Random();
		
		int pre=random.nextInt(100);
		if(pre<10){
			pre=pre+10;
		}
		
		orderNo.append(pre);
		Integer year=Calendar.getInstance().get(Calendar.YEAR);
		
		orderNo.append(year);
		
		int lengthOfSeq=seq.length();
		orderNo.append(str.subSequence(0, str.length()-lengthOfSeq)).append(seq);
		
		return orderNo.toString();
		
	}
	
	@Override
	public String getOrderNo(String type) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("orderNo", type);
		return baseDao.queryForObject("OrderNo.getOrderNo",  map, String.class);
	}
}
