package com.gudeng.commerce.order.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketImageDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditWithMemInfoDTO;
import com.gudeng.commerce.gd.order.entity.SubAuditEntity;
import com.gudeng.commerce.gd.order.service.SubAuditService;

/**
 * 
 * @description: TODO - 对AuditService提供基本的测试功能
 * @Classname: 
 * @author lmzhang@gdeng.cn
 *
 */
public class SubAuditServiceTest {
	private SubAuditService service;
	private Map<String, Object> conditions;
	
	@Before
	public void initService(){
		try {
            String url = "http://127.0.0.1:8080/gd-order/service/subAuditService.hs" ;
            HessianProxyFactory factory = new HessianProxyFactory();
             service = (SubAuditService) factory.create(SubAuditService.class , url);
             assertNotNull("创建服务失败！", service );
             
             conditions = new HashMap<String, Object>();
             conditions.put("orderNo", Long.valueOf("33445555555"));
             conditions.put("buyerName", "");
             conditions.put("startRow", 0);
             conditions.put("endRow", 2);
      } catch (MalformedURLException e) {
            e.printStackTrace();
      }
	}
	
	@Test
	public void testQueryForSearch(){
		try {
			List<SubAuditDTO> result = service.getBySearch(conditions);
			int count = service.getTotal(conditions);
			assertNotNull("getBySearch()方法查询查询错误！", service );
			System.out.println("count.size = " + count);
			
			for(SubAuditDTO sa: result){
				System.out.println(sa.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateSubStatus(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditId", 1);
		map.put("subStatus", "4");
		map.put("subComment", "测试comments......");
		map.put("updateUserId", "uuxxx");
		
		try {
//			int result = service.updateSubStatusById(map);
//			assertEquals("updateSubStatusById()方法执行更新操作失败！！！", 1, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: testGetSubAuditById 
	* @Description: TODO(测试getSubAuditById方法)
	* 
	 */
	@Test
	public void testGetSubAuditById(){
		try {
			SubAuditEntity r = service.getSubAuditById(1);
			assertNotNull("方法getSubAuditById调用失败！", r);
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSubAuditByOrderNo(){
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("orderNo", Long.valueOf("33445555556"));
		
		try {
			List<SubAuditDTO> list = service.getSubAuditDTOByOrderNo(map);
			assertNotNull("getSubAuditDTOByOrderNo方法执行错误！", list);
			for(SubAuditDTO sa: list){
				System.out.println(sa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testGetSubAuditByIds(){
		try {
			List<Integer> list = new ArrayList<Integer>();
			list.add(1);
			list.add(2);
			
			//String idStr = "1,2";
			//String[] idArr = idStr.split(",");
			
			//List<Integer> list = new ArrayList<Integer>(Arrays.asList(idArr));
			
			List<SubAuditEntity> r = service.getSubAuditByIds(list);
			assertEquals("getSubAuditByIds方法错误！",2, r.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetCurrentTotalAmount() {
		try {
			BigDecimal r = new BigDecimal(service.getCurrentTotalAmount().toCharArray()).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			BigDecimal test = new BigDecimal("2641");
			System.out.println(r.compareTo(test));
			
			
			
			assertNotNull("getCurrentTotalAmount方法执行错误！", r);
			
			System.out.println(r);
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSubAuditInfo(){
		
		try {
			List<Integer> list = new ArrayList<Integer>();
			
			list.add(78);
			list.add(80);
			
			List<SubAuditWithMemInfoDTO> r = service.getSubAuditInfo(list);
			
			assertEquals(2, r.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetOutmarketImage(){
		try {
			OrderOutmarketImageDTO result = service.getOutmarketImage(Long.valueOf("492015000000045"));
			
			assertNotNull("getOutmarketImage出错！", result);
			
			System.out.println("orderNo:\t"+ result.getOrderNo());
			System.out.println("carNumber:\t"+ result.getCarNumber());
			System.out.println("carNumberImage:\t"+ result.getCarNumberImage());
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
