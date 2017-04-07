package com.gudeng.commerce.test;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;

public class SendMessageUtil {

	private ProducerBean producer;
	
	public SendMessageUtil() {
		producer = new ProducerBean();
		Properties properties = new Properties();
		properties.setProperty("AccessKey", "FmQ1FZSfeGcLxl13");
		properties.setProperty("SecretKey", "t3QfuZjugirBJraeD8TIz1G5cpTfUY");
		properties.setProperty("ProducerId", "PID_dev_member_order_pay_detail");
		producer.setProperties(properties);

		producer.start();
	}

	/**
	 * 同步发送
	 * 
	 * @param topic
	 * @param tag
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("unused")
	public boolean sendMessage(String topic, String tag, Object msg) {
		Message message = new Message(topic, tag, SerializeUtil.serialize(GSONUtils.toJson(msg, false)));
		boolean isSuccess = false;
		SendResult sendResult = null;
		try {
			sendResult = producer.send(message);
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 异步发送
	 * 
	 * @param topic
	 * @param tag
	 * @param msg
	 * @param sendCallback
	 */
	public void sendAsyncMessage(String topic, String tag, Object msg, SendCallback sendCallback) {
		Message message = new Message(topic, tag, SerializeUtil.serialize(GSONUtils.toJson(msg, false)));
		producer.sendAsync(message, sendCallback);
	}

	public ProducerBean getProducer() {
		return producer;
	}

	public void setProducer(ProducerBean producer) {
		this.producer = producer;
	}

}
