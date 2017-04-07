package com.gudeng.commerce.gd.customer.mqconsumer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.gudeng.commerce.gd.customer.mqconsumer.bean.MemberBankcardInfoDTO;
import com.gudeng.commerce.gd.customer.service.AccBankCardInfoService;
import com.gudeng.commerce.gd.customer.util.GSONUtils;
import com.gudeng.commerce.gd.customer.util.SerializeUtil;


/**
 * 用户银行卡消费者，处理银行卡变更
 * 
 * @author Ailen
 *
 */
public class BankCardMessageListener implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccBankCardInfoService accBankCardInfoService;

	public Action consume(Message message, ConsumeContext context) {
		logger.info("Receive: " + message.getMsgID());
		try {
			String topic = message.getTopic();
			logger.info("topic: " + topic);

			// 获得数据
			MemberBankcardInfoDTO recived = (MemberBankcardInfoDTO) GSONUtils.fromJsonToObject(
					SerializeUtil.unserialize(message.getBody()).toString(), MemberBankcardInfoDTO.class);

			// 数据为空 抛出异常
			if (recived == null)
				throw new RuntimeException("获得用户银行信息为空！");
			
			int infoId = recived.getInfoId();
			String auditStatus = recived.getAuditStatus();
			logger.info("infoId: " + infoId + ", auditStatus: " + auditStatus);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("infoId", infoId);
			params.put("auditStatus", auditStatus);
			accBankCardInfoService.updateBankCardAuditStatus(params);

			return Action.CommitMessage;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("同步用户银行卡信息失败：" + e.getMessage());
			// 消费失败
			return Action.ReconsumeLater;
		}
	}

}
