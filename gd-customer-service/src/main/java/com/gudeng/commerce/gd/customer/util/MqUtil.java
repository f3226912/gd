package com.gudeng.commerce.gd.customer.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.gudeng.commerce.gd.customer.dto.AccBankCardSendDto;
import com.gudeng.commerce.gd.customer.dto.MemberSendDto;
import com.gudeng.commerce.gd.customer.dto.PosMachineConfigDto;
import com.gudeng.commerce.gd.customer.entity.MqAsyncErrorEntity;
import com.gudeng.commerce.gd.customer.enums.MQAsyncTypeEnum;
import com.gudeng.commerce.gd.customer.service.MqAsyncErrorService;


public class MqUtil implements ApplicationContextAware {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MqUtil.class);
	public static GdProperties gdProperties;
	
	public static GdProperties getGdProperties() {
		if(gdProperties==null){
			return (GdProperties) getBean("gdProperties");
		}
		return gdProperties;
	}
	
	public static String mqMemberTopic;
	public static String nsyMqMemberTopic;
	public static String mqAccBankTopic;
	public static String mqPosMachineTopic;
	
	

	protected static String getMqMemberTopic() {
		if(mqMemberTopic == null || mqMemberTopic.equals("")){
			mqMemberTopic = getGdProperties().getMqMemberTopic();
		}
		return mqMemberTopic;
	}
	protected static String getMqAccBankTopic() {
		if(mqAccBankTopic == null || mqAccBankTopic.equals("")){
			mqAccBankTopic = getGdProperties().getMqmqAccBankTopicTopic();
		}
		return mqAccBankTopic;
	}
	protected static String getNsyMqMemberTopic() {
		if(nsyMqMemberTopic == null || nsyMqMemberTopic.equals("")){
			nsyMqMemberTopic = getGdProperties().getNsyMqMemberTopic();
		}
		return nsyMqMemberTopic;
	}

	/**
	 * add by weiwenke
	 * @return
	 */
	protected static String getMqPosMachineTopic() {
		if(mqPosMachineTopic == null || mqPosMachineTopic.equals("")){
			mqPosMachineTopic = getGdProperties().getMqPosMachineTopic();
		}
		return mqPosMachineTopic;
	}
	
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		MqUtil.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}
   
	public static void send(MemberSendDto memberSendDto) {
		Producer producer = (Producer) MqUtil.getBean("producer");//获取会员的producer，在springimpl中配置
		Message msg = new Message(getMqMemberTopic(), "TagA",
				SerializeUtil.serialize(GSONUtils.toJson(memberSendDto, false)));
//		 System.out.println(GSONUtils.toJson(memberSendDto, false)+"===============================================================");
//         msg.setKey("ORDERID_100");
         // 发送消息，只要不抛异常就是成功
         try {
             SendResult sendResult = producer.send(msg);
             assert sendResult != null;
             System.out.println("send success: " + sendResult.getMessageId());
         }catch (ONSClientException e) {
        	 e.printStackTrace();
//             System.out.println("发送失败");
        	 MqAsyncErrorService mqAsyncErrorService = (MqAsyncErrorService) MqUtil.getBean("mqAsyncErrorService");
        	 MqAsyncErrorEntity entity=new MqAsyncErrorEntity();
        	 entity.setCrud(memberSendDto.getCrud());
        	 entity.setCreateTime(new Date());
        	 entity.setStatus(0);
        	 entity.setContent(GSONUtils.toJson(memberSendDto, false));
        	 entity.setType(MQAsyncTypeEnum.TYPE_MEMBER.getKey());
        	 try {
				mqAsyncErrorService.insert(entity);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
         }
	}
	//发送会员信息到支付中心
	public static void sendPay(MemberSendDto memberSendDto) {
		Producer producer = (Producer) MqUtil.getBean("accMmemberProducer");//获取会员的producer，在springimpl中配置
		Message msg = new Message(getNsyMqMemberTopic(), "TagA",
				SerializeUtil.serialize(GSONUtils.toJson(memberSendDto, false)));
//		 System.out.println(GSONUtils.toJson(memberSendDto, false)+"===============================================================");
//         msg.setKey("ORDERID_100");
		// 发送消息，只要不抛异常就是成功
		try {
			SendResult sendResult = producer.send(msg);
			assert sendResult != null;
			System.out.println("send success: " + sendResult.getMessageId());
		}catch (ONSClientException e) {
			e.printStackTrace();
//             System.out.println("发送失败");
			MqAsyncErrorService mqAsyncErrorService = (MqAsyncErrorService) MqUtil.getBean("mqAsyncErrorService");
			MqAsyncErrorEntity entity=new MqAsyncErrorEntity();
			entity.setCrud(memberSendDto.getCrud());
			entity.setCreateTime(new Date());
			entity.setStatus(0);
			entity.setContent(GSONUtils.toJson(memberSendDto, false));
			entity.setType(MQAsyncTypeEnum.TYPE_MEMBER.getKey());
			try {
				mqAsyncErrorService.insert(entity);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
     
	/**
	 * 发送pos终端同步信息到支付系统
	 * @param dto
	 */
	public static void send(PosMachineConfigDto dto) {
		Producer producer = (Producer) MqUtil.getBean("posMachineProducer");
		Message msg = new Message(getMqPosMachineTopic(), "TagA", SerializeUtil.serialize(GSONUtils.toJson(dto, false)));
//		msg.setKey("ORDERID_100");
//		发送消息，只要不抛异常就是成功
		try {
			SendResult sendResult = producer.send(msg);
			assert sendResult != null;
			logger.info("send success: " + sendResult.getMessageId());
		} catch (ONSClientException e) {
			logger.error("发送失败 --> 发送pos终端MQ推送信息异常; ex : {}", e);
			MqAsyncErrorService mqAsyncErrorService = (MqAsyncErrorService) MqUtil.getBean("mqAsyncErrorService");
			MqAsyncErrorEntity entity = new MqAsyncErrorEntity();
			entity.setCreateTime(new Date());
			//0 未重新发送 1 已经重新发送
			entity.setStatus(0);
			entity.setContent(GSONUtils.toJson(dto, false));
			entity.setType(MQAsyncTypeEnum.TYPE_POS.getKey());
			try {
				mqAsyncErrorService.insert(entity);
			} catch (Exception e1) {
				logger.error("记录Pos终端MQ推送信息异常; ex : {}", e);
			}
		}

	}
	
	public static void send(AccBankCardSendDto dto) {
		
		Producer producer = (Producer) MqUtil.getBean("accBankProducer");
		Message msg = new Message(getMqAccBankTopic(), "TagA", SerializeUtil.serialize(GSONUtils.toJson(dto, false)));
//		msg.setKey("ORDERID_100");
//		发送消息，只要不抛异常就是成功
		try {
			SendResult sendResult = producer.send(msg);
			assert sendResult != null;
			System.out.println("send success: " + sendResult.getMessageId());
		} catch (ONSClientException e) {
			logger.error("发送失败 --> 发送绑定银行卡MQ推送信息异常; ex : {}", e);
			MqAsyncErrorService mqAsyncErrorService = (MqAsyncErrorService) MqUtil.getBean("mqAsyncErrorService");
			MqAsyncErrorEntity entity = new MqAsyncErrorEntity();
			entity.setCrud(dto.getCrud());
			entity.setCreateTime(new Date());
			//0 未重新发送 1 已经重新发送
			entity.setStatus(0);
			entity.setContent(GSONUtils.toJson(dto, false));
			entity.setType(MQAsyncTypeEnum.TYPE_BANK.getKey());
			try {
				mqAsyncErrorService.insert(entity);
			} catch (Exception e1) {
				logger.error("记录绑定银行卡MQ推送信息异常; ex : {}", e);
			}
		}
		
	}
}