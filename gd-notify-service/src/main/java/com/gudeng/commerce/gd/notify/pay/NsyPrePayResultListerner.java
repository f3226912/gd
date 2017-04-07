package com.gudeng.commerce.gd.notify.pay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.gudeng.commerce.gd.customer.entity.PayTradeEntity;
import com.gudeng.commerce.gd.notify.enums.ApiRequestV1Enum;
import com.gudeng.commerce.gd.notify.service.PayResultToolService;
import com.gudeng.commerce.gd.notify.service.PosOrderToolService;
import com.gudeng.commerce.gd.notify.util.Des3;
import com.gudeng.commerce.gd.notify.util.GdProperties;
import com.gudeng.commerce.gd.notify.util.HttpClients;
import com.gudeng.commerce.gd.notify.util.SerializeUtil;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;

/**
 * 
 * 农商友，支付预付款成功后的处理
 * 农商友，支付尾款成功后的处理
 * 
 * // TODO 支付预付款成功，记录支付流水 
 * // TODO 支付预付款成功，发送农速通消息
 * 
 * 
 * 
 **/
public class NsyPrePayResultListerner implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public GdProperties gdProperties;
	@Resource
	private PayResultToolService payResultToolService;
	@Resource
	private PosOrderToolService posOrderToolService;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		String topic = message.getTopic();
		//logger.info("接收到POS支付成功MQ发送的信息,topic: " + topic);
		try {
			// 获得数据
			PayTradeEntity payTradeEntity = JSONObject
					.parseObject(SerializeUtil.unserialize(message.getBody()).toString(), PayTradeEntity.class);
			logger.info("接收到预付款支付成功MQ发送的信息,topic: " + topic+",msg="+JSON.toJSONString(payTradeEntity));
			/*
			 * 如果支付成功
			 */
			if ("2".equals(payTradeEntity.getPayStatus())) {
				// 支付预付款成功，记录支付流水 
				if("1".equals(payTradeEntity.getRequestNo())|| "2".equals(payTradeEntity.getRequestNo())){
					// 修改订单状态为待发货
					updateOrderStatus(payTradeEntity);
					addSerialnumber(payTradeEntity);
				    // 支付预付款成功，发送农速通消息
					sendNstMessage(payTradeEntity);
				}
			} else { 
				logger.error("支付中心返回：" + payTradeEntity.getPayCenterNumber());
			}
			return Action.CommitMessage;
		} catch (Exception e) {
			e.printStackTrace();
			// 消费失败
			return Action.ReconsumeLater;
		}
	}
	/**
	 * 支付预付款通过后，通知农速通
	 * */
	private void sendNstMessage(PayTradeEntity payTradeEntity) throws Exception {
			String url = null;
			if ("1".equals(payTradeEntity.getRequestNo())) {
				url = gdProperties.getApiUrl() + ApiRequestV1Enum.PAY_PREPAY_SUCC.getUri();//预付款成功
			} else {
				url = gdProperties.getApiUrl() + ApiRequestV1Enum.PAY_RESTPAY_SUCC.getUri();//尾款成功
			}
			String nstOrderNo = posOrderToolService.getNstOrderNo(Long.valueOf(payTradeEntity.getOrderNo()));
			Map map = new HashMap();
			map.put("orderNo", nstOrderNo);
			map.put("memberId", payTradeEntity.getPayerUserId());
			String paramValue = Des3.encode(new Gson().toJson(map));
			HttpClients.doPost(url, paramValue);
	}

	private void addSerialnumber(PayTradeEntity payTradeEntity) throws Exception {
		String orderNo = payTradeEntity.getOrderNo();
		PaySerialnumberEntity paySerialnumberEntity = new PaySerialnumberEntity();
		paySerialnumberEntity.setCreateTime(new Date());
		paySerialnumberEntity.setUpdatetime(new Date());
		paySerialnumberEntity.setOrderNo(Long.parseLong(orderNo));

		/*
		 * 设置支付流水数据
		 */
		paySerialnumberEntity.setPayStatus("1");
		paySerialnumberEntity.setStatementId(payTradeEntity.getPayCenterNumber());
		paySerialnumberEntity.setTradeAmount(payTradeEntity.getPayAmt());
		if("1".equals(payTradeEntity.getRequestNo())){
			paySerialnumberEntity.setPayType("4"); //预付款，使用支付宝
		}else{
			paySerialnumberEntity.setPayType("2");//尾款，使用pos刷卡
		}
		paySerialnumberEntity.setSerialType(payTradeEntity.getRequestNo());//'1预付款流水，2尾款流水
		
//		if(StringUtils.equals(payTradeEntity.getPayType(), EPaySubType4Stament.GXRCB.getChannel())){
//			paySerialnumberEntity.setPaySubType(EPaySubType4Stament.GXRCB.getCode());
//			paySerialnumberEntity.setPayAreaId("450000");
//		} else if(StringUtils.equals(payTradeEntity.getPayType(), EPaySubType4Stament.NNCCB.getChannel())){
//			paySerialnumberEntity.setPaySubType(EPaySubType4Stament.NNCCB.getCode());
//			paySerialnumberEntity.setPayAreaId("450100");
//		}
		
		paySerialnumberEntity.setPaymentAcc(payTradeEntity.getThirdPayerAccount());
		paySerialnumberEntity.setRecipientAcc(payTradeEntity.getThirdPayeeAccount());
		paySerialnumberEntity.setPayTime(payTradeEntity.getPayTime());
		paySerialnumberEntity.setThirdStatementId(payTradeEntity.getThirdPayNumber());
		paySerialnumberEntity.setPosNumber(payTradeEntity.getPosClientNo());
		paySerialnumberEntity.setMemberId(Integer.parseInt(payTradeEntity.getPayerUserId()));

		/*
		 * 添加支付流水信息
		 */
		payResultToolService.addPaySerialnumber(paySerialnumberEntity);
	}
	
	private void updateOrderStatus(PayTradeEntity payTradeEntity) throws Exception{
		OrderBaseinfoEntity entity=new OrderBaseinfoEntity();
		entity.setOrderNo(Long.valueOf(payTradeEntity.getOrderNo()));
		if("1".equals(payTradeEntity.getRequestNo())){
			entity.setOrderStatus(EOrderStatus.WAIT_SEND.getCode());
		}else{
			entity.setOrderStatus(EOrderStatus.PAYED.getCode());
		}
		payResultToolService.updateOrderbase(entity);
	}

}
