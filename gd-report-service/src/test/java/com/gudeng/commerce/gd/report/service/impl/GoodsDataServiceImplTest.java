package com.gudeng.commerce.gd.report.service.impl;

import javax.annotation.Resource;

import org.junit.Test;

import com.gudeng.commerce.gd.report.data.ADataService;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年3月25日 上午11:21:02
 */
public class GoodsDataServiceImplTest extends AbstractServiceTest {
	
	@Resource(name = "goodsDataService")
	private ADataService goodsDataService;
	
	@Test
	public void testQueryData() throws Exception {
		DataServiceQuery dataServiceQuery = new DataServiceQuery();
		goodsDataService.queryData(dataServiceQuery);
	}

}
