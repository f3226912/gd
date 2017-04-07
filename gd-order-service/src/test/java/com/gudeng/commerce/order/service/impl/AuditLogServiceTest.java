package com.gudeng.commerce.order.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.AuditLogDTO;
import com.gudeng.commerce.gd.order.service.AuditLogService;

/**
 * 
 * @description: TODO - 对AuditLogService进行测试
 * @Classname: AuditLogServiceTest
 * @author lmzhang@gdeng.cn
 *
 */
public class AuditLogServiceTest extends TestCase{
	private AuditLogService service;
	
	private AuditLogDTO al; 	//该类在初始化时创建，作为测试中的实例类直接使用
	
	@Before
	public void initService(){
		try {
			String url = "http://127.0.0.1:8080/gd-order/service/auditLogService.hs" ;
			HessianProxyFactory factory = new HessianProxyFactory();
			service = (AuditLogService)factory.create(AuditLogService.class, url);
			assertNotNull("创建服务失败！", service);
			
			this.al = new AuditLogDTO();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddAuditLog() throws Exception{
		al.setType("1"); 	//类型 1补贴审核 2提现审核
		al.setOrderNo((long)29358729);
		al.setAuditUserId("18");
		al.setAuditUserName("test01");
		al.setDescription("test 描述信息！");
		al.setAuditTime(new Date());
		al.setCreateUserId("19");
		al.setCreateUserName("test001");
		al.setUpdateUserId("20");
		
		int result = this.service.addAuditLog(al);
		assertEquals("添加AuditLog失败！",1, result);
	}
	
	@Test
	public void testGetListByOrderNo() throws Exception{
		List<AuditLogDTO> list = service.getListByOrderNo((long)29358729, "1");
		assertNotNull("getListByOrderNo()执行失败！", list);
		
		System.out.println("list.size():" + list.size());
		
	}
}






















