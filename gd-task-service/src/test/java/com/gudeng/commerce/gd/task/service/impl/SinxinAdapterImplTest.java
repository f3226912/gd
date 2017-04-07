package com.gudeng.commerce.gd.task.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.task.agent.SinxinAdapter;
import com.gudeng.commerce.gd.task.dto.sinxin.ProductSyncDTO;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年3月25日 上午11:21:02
 */
public class SinxinAdapterImplTest extends AbstractServiceTest {
	
	@Autowired
	private SinxinAdapter sinxinAdapter;
	
	@Test
	public void testSyncProduct() throws Exception {
		ProductSyncDTO productSyncDTO = new ProductSyncDTO();
		productSyncDTO.setCdOId(2001L);
		productSyncDTO.setCdName("西红柿");
		productSyncDTO.setDefaultprice("100.01");
		productSyncDTO.setUnit("公斤");
		productSyncDTO.setStatus(1);
		productSyncDTO.setSellerId("1052");
		sinxinAdapter.syncProduct(productSyncDTO);
	}

}
