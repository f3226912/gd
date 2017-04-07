package com.gudeng.commerce.gd.task.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;


public class MqUtil {

	public static GdProperties gdProperties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MqUtil.class);
	
	public static GdProperties getGdProperties() {
		if(gdProperties==null){
			return (GdProperties) SpringContextUtil.getBean("gdProperties");
		}
		return gdProperties;
	}
	
	public static String mqMemberTopic;

	protected static String getMqMemberTopic() {
		if(mqMemberTopic == null || mqMemberTopic.equals("")){
			mqMemberTopic = getGdProperties().getMqMemberTopic();
		}
		return mqMemberTopic;
	}
	public static String mqAccBankTopic;

	protected static String getMqAccBankTopic() {
		if(mqAccBankTopic == null || mqAccBankTopic.equals("")){
			mqAccBankTopic = getGdProperties().getMqAccBankTopic();
		}
		return mqAccBankTopic;
	}
	
	public static String orderSyncTopic;

	protected static String getMqOrderSyncTopic() {
		if(orderSyncTopic == null || orderSyncTopic.equals("")){
			orderSyncTopic = getGdProperties().getProperties().getProperty("gd.MQ.orderSync.Topic");
		}
		return orderSyncTopic;
	}
	
	public static boolean send(String json,Integer type) {
		if(type==1){//发送会员的同步消息 
			Producer producer = (Producer) SpringContextUtil.getBean("producer");//获取会员的producer，在springimpl中配置
			Message msg = new Message(getMqMemberTopic(), "TagA", SerializeUtil.serialize(json));
//			msg.setKey("ORDERID_100");
			try {
				SendResult sendResult = producer.send(msg);
				assert sendResult != null;
				System.out.println("send success: " + sendResult.getMessageId());
				return true;
			} catch (ONSClientException e) {
				e.printStackTrace();
				System.out.println("发送失败");
				return false;
			}
		}else if (type == 2 ){//
			Producer producer = (Producer) SpringContextUtil.getBean("accBankProducer");
			Message msg = new Message(getMqAccBankTopic(), "TagA", SerializeUtil.serialize(json));
			try {
				SendResult sendResult = producer.send(msg);
				assert sendResult != null;
				System.out.println("send success: " + sendResult.getMessageId());
				return true;
			} catch (ONSClientException e) {
				e.printStackTrace();
				System.out.println("发送失败");
				return false;
			}
		}else if (type == 3 ){//
			Producer producer = (Producer) SpringContextUtil.getBean("orderSyncProducer");
			Message msg = new Message(getMqOrderSyncTopic(), "TagA", SerializeUtil.serialize(json));
			msg.setKey(json);
			try {
				SendResult sendResult = producer.send(msg);
				assert sendResult != null;
				LOGGER.info("订单生成成功，MQ发送流水号成功: " + sendResult.getMessageId());
				return true;
			} catch (ONSClientException e) {
				//e.printStackTrace();
				LOGGER.error("订单生成成功，MQ发送流水号失败"+e.getMessage(),e);

				return false;
			}
		}
		else{
		}
		return false;
		
	}
}