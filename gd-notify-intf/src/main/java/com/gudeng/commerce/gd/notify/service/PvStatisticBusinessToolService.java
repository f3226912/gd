package com.gudeng.commerce.gd.notify.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.enums.MessageTemplateEnum;

/**
 * 商铺浏览量业务
 * 
 * @author Ailen
 *
 */
public interface PvStatisticBusinessToolService {

	/**
	 * 发送短信
	 * 
	 * @param memberId
	 * @param mobile
	 * @param alidauCode
	 * @param template
	 * @param params
	 */
	public void sendMsg(Long memberId, String mobile, Integer alidauCode, MessageTemplateEnum template,
			Map<String, Object> params) throws Exception;

}
