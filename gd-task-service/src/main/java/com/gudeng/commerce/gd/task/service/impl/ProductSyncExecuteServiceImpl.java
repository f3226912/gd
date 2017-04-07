package com.gudeng.commerce.gd.task.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.ServiceTaskFailException;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.task.agent.SinxinAdapter;
import com.gudeng.commerce.gd.task.common.job.AbstractExecuteServiceImpl;
import com.gudeng.commerce.gd.task.dto.sinxin.ProductSyncDTO;
import com.gudeng.commerce.gd.task.service.ProductToolService;

/**
 * @Description: TODO(商品同步任务执行实现类)
 * @author mpan
 * @date 2016年3月24日 下午3:37:40
 */
public class ProductSyncExecuteServiceImpl extends AbstractExecuteServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductSyncExecuteServiceImpl.class);
	
	@Autowired
	private ProductToolService productToolService;
	
	@Autowired
	private SinxinAdapter sinxinAdapter;

	@Override
	public void execTask(TaskDTO taskInfo) throws ServiceException {
		if (StringUtils.isBlank(taskInfo.getExtend1())) {
			LOGGER.debug("产品ID不能为空，任务ID=" + taskInfo.getTaskId());
			throw new ServiceException("产品ID不能为空");
		} else {
			Long productId = Long.valueOf(taskInfo.getExtend1());
			
			try {
				ProductDto product = productToolService.getById(productId);
				ProductSyncDTO productSyncDTO = new ProductSyncDTO();
				productSyncDTO.setCdOId(product.getProductId());
				productSyncDTO.setCdName(product.getProductName());
				productSyncDTO.setDefaultprice(product.getPrice().toString());
				productSyncDTO.setUnit(product.getUnitName());
				if ("3".equals(product.getState())) {
					productSyncDTO.setStatus(0);
				} else {
					productSyncDTO.setStatus(1);
				}
				productSyncDTO.setSellerId("1052"); // 秤ID
				sinxinAdapter.syncProduct(productSyncDTO);
			} catch (Exception e) {
				throw new ServiceTaskFailException("商品同步失败");
			}
			
			taskInfo.setErrInfo("已完成");
		}
	}

}
