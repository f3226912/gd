package com.gudeng.commerce.gd.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.service.PushTaskService;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.ProductService;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;
import com.gudeng.commerce.gd.task.service.ExpireProductTask;
import com.gudeng.commerce.gd.task.util.DateUtil;

public class ExpireProductTaskImpl implements ExpireProductTask {
	
	public Logger logger = LoggerFactory.getLogger(ExpireProductTaskImpl.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PushTaskService pushTaskService;
	
	@Autowired
	private SystemLogService systemLogService;

	@SuppressWarnings("unchecked")
	@Override
	public void HandleExpireProduct() throws Exception {
		/*Map<String,Object> map = new HashMap<String,Object>();
		String expireDate = DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATETIME);
		map.put("expirDate", expireDate);
		List<ProductDto> list = productService.getExpireProduct(map);
		logger.info("-------------------------->可更新产品数量"+list.size());
		if(null==list||list.size()<=0)return;
		//写入推送消息内容表
		List<Long> productIds = new ArrayList<Long>();
		Map<String,Object>[] records = new HashMap[list.size()];
		String content="尊敬的用户：您发布的“%s”产品已到期下架，您可以进行上架处理。查看详情请点击未上架的产品。";
		for(int n=0;n<list.size();n++){
			productIds.add(list.get(n).getProductId());
			
			Map<String,Object> record = new HashMap<String,Object>();
			String temcontent = String.format(content, list.get(n).getProductName());
//			logger.info("-------------------------->"+list.get(n).getProductName());
			record.put("type", "1");
			record.put("receiveType", "1");
			record.put("title", "产品下架通知");
			record.put("content", temcontent);
			record.put("memberId", list.get(n).getMemberId());
			record.put("createUserId", list.get(n).getCreateUserId());
			record.put("state", "0");
			record.put("origin", "0");
			record.put("redirectUrl", "");
			records[n]=record;
			
			String logContent ="下架产品    产品id:"+list.get(n).getProductId()+"产品名称："+list.get(n).getProductName();

			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(list.get(n).getProductId());
			entity.setChennel("4");
			entity.setContent(logContent);
			entity.setCreateTime(new Date());
			entity.setCreateUserId("SystemTask");
			entity.setType(SystemLogTypeEnum.PRODUCT.getKey());
			systemLogService.insertLog(entity);
		}

		
		pushTaskService.addPushRecord(records);
		//下架过期产品
		int[] updatecn = productService.updateProductState(productIds);
		logger.info("--------------------下架"+updatecn.length+"个产品--------------");*/
	}

}
