package com.gudeng.commerce.gd.notify.pay;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.gudeng.commerce.gd.customer.entity.PayTradeEntity;
import com.gudeng.commerce.gd.notify.service.PayResultToolService;
import com.gudeng.commerce.gd.notify.util.Des3;
import com.gudeng.commerce.gd.notify.util.HttpClients;
import com.gudeng.commerce.gd.notify.util.SerializeUtil;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.enm.EPaySubType4Stament;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;

/**
 * POS尾款支付，业务系统异步通知
 * @date 2017年3月6日
 */
public class NsyPayResultListerner implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private PayResultToolService payResultToolService;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		String topic = message.getTopic();
		//logger.info("接收到POS支付成功MQ发送的信息,topic: " + topic);
		try {
			// 获得数据
			PayTradeEntity payTradeEntity = JSONObject
					.parseObject(SerializeUtil.unserialize(message.getBody()).toString(), PayTradeEntity.class);
			logger.info("接收到POS支付成功MQ发送的信息,topic: " + topic+",msg="+JSON.toJSONString(payTradeEntity));
			/*
			 * 如果支付成功
			 */
			if ("2".equals(payTradeEntity.getPayStatus())) {

				// 转为已付款状态 同时添加支付流水
				changeOrderAndSerialnumber(payTradeEntity);

			} else { // 支付失败 不存在
				logger.error("支付中心返回：" + payTradeEntity.getPayCenterNumber());
			}
			logger.info("接收到POS支付成功MQ发送的信息,处理成功,topic: " + topic);
			return Action.CommitMessage;
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("接收到POS支付成功MQ发送的信息,处理失败：" + e.getMessage(),e);
			// 消费失败
			return Action.ReconsumeLater;
		}
	}

	/**
	 * 修改对应订单状态
	 * 
	 * @param payTradeEntity
	 * @param orderNo
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	private void changeOrderAndSerialnumber(PayTradeEntity payTradeEntity) throws MalformedURLException, Exception {


		/*
		 * 修改订单状态
		 */
		String orderNo = payTradeEntity.getOrderNo();

		if (orderNo == null) {
			throw new RuntimeException("农商友接收支付中心结果订单号为空" + payTradeEntity.getPayCenterNumber());
		}

		/*
		 * 修改对应订单的状态
		 */
		OrderBaseinfoEntity entity = new OrderBaseinfoEntity();
		entity.setOrderNo(Long.valueOf(orderNo));
		entity.setPayType("2");//线下刷卡
		entity.setOrderStatus(EOrderStatus.PAYED.getCode());
		payResultToolService.updateOrderbase(entity);


		//是否重复通知
		//查询支付流水是否存在
		PaySerialnumberEntity paySerial = payResultToolService.getByStatementId(payTradeEntity.getPayCenterNumber());
		if(null == paySerial){
			/*
			 * 添加支付流水信息
			 */
			PaySerialnumberEntity paySerialnumberEntity = new PaySerialnumberEntity();
			paySerialnumberEntity.setCreateTime(new Date());
			paySerialnumberEntity.setUpdatetime(new Date());
			try {
				paySerialnumberEntity.setOrderNo(Long.parseLong(orderNo));
			} catch (NumberFormatException e) {
				logger.error("农商友接收订单数据有误" + e.getMessage());
			}

			/*
			 * 设置支付流水数据
			 */
			paySerialnumberEntity.setPayStatus("1");
			paySerialnumberEntity.setStatementId(payTradeEntity.getPayCenterNumber());
			paySerialnumberEntity.setTradeAmount(payTradeEntity.getPayAmt());
			paySerialnumberEntity.setPayType("2"); //线下刷卡
			paySerialnumberEntity.setSerialType("2");//2尾款流水

			if(StringUtils.equals(payTradeEntity.getPayType(), EPaySubType4Stament.GXRCB.getChannel())){
				paySerialnumberEntity.setPaySubType(EPaySubType4Stament.GXRCB.getCode());
				paySerialnumberEntity.setPayAreaId("450000");
			} else if(StringUtils.equals(payTradeEntity.getPayType(), EPaySubType4Stament.NNCCB.getChannel())){
				paySerialnumberEntity.setPaySubType(EPaySubType4Stament.NNCCB.getCode());
				paySerialnumberEntity.setPayAreaId("450100");
			}

			paySerialnumberEntity.setPaymentAcc(payTradeEntity.getThirdPayerAccount());
			paySerialnumberEntity.setRecipientAcc(payTradeEntity.getThirdPayeeAccount());
			paySerialnumberEntity.setPayTime(payTradeEntity.getPayTime());
			paySerialnumberEntity.setThirdStatementId(payTradeEntity.getThirdPayNumber());
			paySerialnumberEntity.setPosNumber(payTradeEntity.getPosClientNo());
			try {
				paySerialnumberEntity.setMemberId(Integer.parseInt(payTradeEntity.getPayerUserId()));
			} catch (NumberFormatException e) {
				logger.error("农商友接收买家ID转换错误" + e.getMessage());
			}

			/*
			 * 添加支付流水信息
			 */
			payResultToolService.addPaySerialnumber(paySerialnumberEntity);
			
			/*****************支付完成调用同步积分接口*******************/
			synOrderForIntegral(Long.valueOf(orderNo)); 
			/************************************/
		}

	}
    /**
     * 支付完成，同步积分相关数据
     * @param orderNo
     * @throws Exception
     */
	private void synOrderForIntegral(Long orderNo) throws Exception{
		String url =payResultToolService.getApiUrl(); //url后面带有 / 
		url += "v170209/order/orderPayfinsh";
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderNo);
		String requestData = Des3.encode(gson.toJson(map));; 
		logger.info("request para..."+requestData);
		String reponseData = HttpClients.doPost(url, requestData);
		logger.info("result..."+reponseData);
	}

}
