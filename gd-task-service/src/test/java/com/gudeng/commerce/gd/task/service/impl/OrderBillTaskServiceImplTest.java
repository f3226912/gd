package com.gudeng.commerce.gd.task.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.task.service.OrderBillTaskService;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年11月22日 下午1:32:16
 */
public class OrderBillTaskServiceImplTest extends AbstractServiceTest {
	
	@Autowired
	private OrderBillTaskService orderBillTaskService;
	
	@Test
	public void testImportData() throws Exception {
		orderBillTaskService.importData();
	}

}
