package com.gudeng.commerce.gd.task.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.service.PushTaskService;
import com.gudeng.commerce.gd.task.service.PushMessageTaskService;
import com.gudeng.commerce.gd.task.util.GdProperties;

public class PushMessageTaskServiceImpl implements PushMessageTaskService{


	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	public Logger logger = LoggerFactory.getLogger(PushMessageTaskServiceImpl.class);
	
	@Autowired
	private PushTaskService pushTaskService;
	
//	@Autowired
//	private UMengPushMessage uMengPushMessage;
//	
//	@Autowired
//	private PushRecordService pushRecordService;
	
//	if(null==list||list.size()==0)return;
//	
//	GdMessageDTO gdMessage = new GdMessageDTO();
//	gdMessage.setSendApp("1");
//	gdMessage.setSendType("1");
//	gdMessage.setTicket("test");
//	gdMessage.setTitle("【农商友告诉您市场价格行情动态】");
//	gdMessage.setProduction_mode(false);
//	gdMessage.setAfter_open("go_app");
//	gdMessage.setContent("您关注的商品价格波动了，马上了解详情");
//	gdMessage.setAfter_open("go_activity");
//	gdMessage.setActivity("com.gudeng.smallbusiness.activity.MainActivity");
//	
//	Map<String,String> extraMap = new HashMap<String,String>();
//	PushRecordEntity record = null;
//	
//	for(FollowProductDTO f:list){
//		extraMap.clear();
//		logger.info("userid="+f.getUserId()+"::"+f.getDeviceTokens());
//		extraMap.put("productId", f.getProductId()+"");
////		private static final String  NSY_TJ="NSY_TJ";//推荐界面
////		private static final String  NSY_GZ="NSY_GZ";//关注界面
//		extraMap.put("openmenu", "NSY_GZ");
//		gdMessage.setDevice_tokens(f.getDeviceTokens());
//		gdMessage.setExtraMap(extraMap);
//		uMengPushMessage.pushMessage(gdMessage);
//		
//		record = new PushRecordEntity();
//		record.setContent(content);
//		record.setCreateTime(new Date());
//		record.setCreateUserId("0");
//		record.setMemberId(f.getUserId());
//		record.setReceiveType("2");
//		record.setState("1");
//		record.setTitle(title);
//		record.setType("1");
//		record.setOrigin(0);
//		pushRecordService.add(record);
//	}
	
	
	@Override
	public void PushMessageTask() throws Exception {
		logger.info("--------------推送任务开始---------------------");
		//清理工作
		pushTaskService.deletePushProductContentByTime();
		//找出需要推送的内容到提送内容表
		pushTaskService.addPushProductContent();
		//判断当前时间是否是推送时间
		if(!pushTaskService.isPushTime())return;
		
		//给可以推送的用户推送消息
		pushTaskService.PushUsers(gdProperties.getPushFilterDaycn());

		
		logger.info("--------------推送任务结束---------------------");

		
	}

}
