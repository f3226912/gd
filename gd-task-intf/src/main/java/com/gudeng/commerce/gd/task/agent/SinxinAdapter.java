package com.gudeng.commerce.gd.task.agent;

import com.gudeng.commerce.gd.task.dto.sinxin.ProductSyncDTO;

/**
 * @Description: TODO(深信接口调用适配接口类)
 * @author mpan
 * @date 2016年3月24日 下午3:59:00
 */
public interface SinxinAdapter {
	
	public void syncProduct(ProductSyncDTO productSyncDTO) throws Exception;

}
