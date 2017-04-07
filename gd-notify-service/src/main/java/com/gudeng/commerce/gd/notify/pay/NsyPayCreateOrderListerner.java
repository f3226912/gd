package com.gudeng.commerce.gd.notify.pay;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.gudeng.commerce.gd.notify.dto.PosPayNotifyDto;
import com.gudeng.commerce.gd.notify.service.PosOrderToolService;
import com.gudeng.commerce.gd.notify.service.TaskToolService;
import com.gudeng.commerce.gd.notify.util.SerializeUtil;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.enm.ETaskStatus;
import com.gudeng.commerce.gd.order.enm.ETaskType;


/**
 * 逆向刷卡 MQ通知
 * @date 2017年3月6日
 */
public class NsyPayCreateOrderListerner  implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private PosOrderToolService posOrderToolService;
	
	@Resource
	private TaskToolService taskToolService;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		String topic = message.getTopic();
		//logger.info("接收到POS支付成功[逆向]MQ发送的信息,topic: " + topic);
		try {
			// 获得数据
			PosPayNotifyDto dto = JSONObject
					.parseObject(SerializeUtil.unserialize(message.getBody()).toString(), PosPayNotifyDto.class);
			//OrderBaseinfoDTO baseinfo = posOrderToolService.payByCard(dto);
			logger.info("接收到POS支付成功MQ发送的信息,topic: " + topic+",msg="+JSON.toJSONString(dto));
			posOrderToolService.payByCard(dto);
			//通过任务发MQ，不立即发送
			TaskDTO task = new TaskDTO();
			task.setTaskType(ETaskType.ORDER_SYNC.getCode());
			task.setTaskStatus(ETaskStatus.INIT.getCode());
			task.setOrderNumber(dto.getPayCenterNumber()); //存支付流水 
			taskToolService.addTask(task);
			logger.info("接收到POS支付成功[逆向]MQ发送的信息,处理成功,topic: " + topic);
			return Action.CommitMessage;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接收到POS支付成功[逆向]MQ发送的信息,处理失败：" + e.getMessage());
			// 消费失败
			return Action.ReconsumeLater;
		}
	}


}
