package com.gudeng.commerce.order.service.impl;

import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.entity.SubAmountEntity;
import com.gudeng.commerce.gd.order.service.SubAmountService;


/**
 * 
 * @ToDo: 对SubAmountService进行测试
 * @author lmzhang@gdeng.cn
 *
 */
public class SubAmountServiceTest {
	private SubAmountService service;
	
	@Before
	public void initService(){
		try {
			String url = "http://127.0.0.1:8080/gd-order/service/subAmountService.hs";
			HessianProxyFactory factory = new HessianProxyFactory();
			service = (SubAmountService)factory.create(SubAmountService.class,url);
			assertNotNull("创建服务失败", service);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAdd(){
		try {
			SubAmountEntity entity = new SubAmountEntity();
			entity.setSubAmountBal(30000000.00d);
			entity.setSubAmountTotal(30000000.00d);
			//entity.setMarketId(2);
			//entity.setMarketName("广西玉林批发市场");
			entity.setMarketId(1);
			entity.setMarketName("武汉白沙洲批发市场");
			entity.setIsAvailable("1");
			entity.setHasSubBalance("1");
			entity.setCreateUserId("100");
			entity.setUpdateUserId("100");
			int r = service.add(entity);
			
			Assert.assertEquals("add方法执行有错误！", 1, r);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetByMarketId(){
		try {
			Integer marketId = 1;
			SubAmountEntity sa = service.getByMarketId(marketId);
			assertNotNull("getByMarketId方法有错误！", sa);
			Assert.assertEquals("getByMarketId方法有错误,marketId错误", marketId,sa.getMarketId());
			Assert.assertEquals("getByMarketId方法有错误,isAvailable错误", "1", sa.getIsAvailable());
			printSubAmountEntity(sa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSubductAmount(){
		try {
			int r = service.subductAmount(30.811d, 1, "99");
			Assert.assertEquals("执行subductAmount方法失败！", 1, r);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetUnavailableByMarketId(){
		try {
			Integer marketId = 1;
			String updateUserId = "22";
			int r = service.setUnavailableByMarketId(marketId, updateUserId);
			Assert.assertEquals("setUnavailableByMarketId执行异常！",1, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllBalAmount(){
		try {
			Map<Integer, Double> result = service.getAllBalAmount();
			
			for(Integer mId: result.keySet()){
				DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置 
				String bal = decimalFormat.format(result.get(mId));
				System.out.println("marketId:" + mId + "\t剩余余额:"+bal);
			}
			
			assertNotNull("getAllBalAmount执行失败！", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSubductAmountBatch(){
		try {
			@SuppressWarnings("rawtypes")
			Map[] ps = new Map[2];
			Map<String, Object> p1 = new HashMap<String, Object>();
			Map<String, Object> p2 = new HashMap<String, Object>();
			p1.put("marketId", 1);
			p1.put("amount", 30.48);
			p1.put("updateUserId", "99");
			
			p2.put("marketId", 2);
			p2.put("amount", 50.48);
			p2.put("updateUserId", "99");
			
			ps[0] = p1;
			ps[1] = p2;
			
			int[] ressult = service.subductAmountBatch(ps);
			Assert.assertEquals("subductAmountBatch更新出错！", 2, ressult.length);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void printSubAmountEntity(SubAmountEntity en){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("======================================");
		System.out.println("subAmountId:\t"+en.getSubAmountId());
		System.out.println("subAmountBal:\t"+en.getSubAmountBal());
		System.out.println("subAmountTotal:\t"+en.getSubAmountTotal());
		System.out.println("marketId:\t"+en.getMarketId());
		System.out.println("marketName:\t"+en.getMarketName());
		System.out.println("isAvailable:\t"+en.getIsAvailable());
		System.out.println("hasSubBalance:\t"+en.getHasSubBalance());
		System.out.println("createTime:\t" + sdf.format(en.getCreateTime()));
		System.out.println("createUserId:\t"+en.getCreateUserId());
		System.out.println("updateTime:\t"+sdf.format(en.getUpdateTime()));
		System.out.println("updateUserId:\t"+en.getUpdateUserId());
	}
	
	
	
	
	
}















