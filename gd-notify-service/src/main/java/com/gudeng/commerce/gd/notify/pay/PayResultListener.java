package com.gudeng.commerce.gd.notify.pay;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberMessageFlagEntity;
import com.gudeng.commerce.gd.customer.entity.PayTradeEntity;
import com.gudeng.commerce.gd.customer.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.customer.util.Constant;
import com.gudeng.commerce.gd.notify.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.notify.service.MemberChangeLogToolService;
import com.gudeng.commerce.gd.notify.service.MemberMessageFlagToolService;
import com.gudeng.commerce.gd.notify.service.PayResultToolService;
import com.gudeng.commerce.gd.notify.service.PvStatisticBusinessToolService;
import com.gudeng.commerce.gd.notify.util.GdProperties;
import com.gudeng.commerce.gd.notify.util.SerializeUtil;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;

/**
 * 购买金牌会员，农商友接收异步通知
 * 
 * @author Ailen
 *
 */
public class PayResultListener implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private PayResultToolService payResultToolService;

	@Resource
	private PvStatisticBusinessToolService pvToolService;

	@Resource
	private GdProperties gdProperties;
	
	@Resource
	private MemberBaseinfoToolService memberService;
	
	@Resource
	public MemberChangeLogToolService memberChangeLogToolService;
	
	@Resource
	public MemberMessageFlagToolService memberMessageFlagToolService;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		logger.info("Receive: " + message.getMsgID());
		try {
			String topic = message.getTopic();
			logger.info("topic: " + topic);

			// 获得数据
			PayTradeEntity payTradeEntity = JSONObject
					.parseObject(SerializeUtil.unserialize(message.getBody()).toString(), PayTradeEntity.class);

			/*
			 * 如果支付成功
			 */
			if ("2".equals(payTradeEntity.getPayStatus())) {

				// 转为已付款状态 同时添加支付流水
				changeOrderAndSerialnumber(payTradeEntity);

				/*
				 * 修改会员信息 修改会员为金牌会员
				 */
				MemberBaseinfoDTO memberBaseinfoDTO = new MemberBaseinfoDTO();
				memberBaseinfoDTO.setMemberId(Long.parseLong(payTradeEntity.getPayerUserId()));
				memberBaseinfoDTO.setMemberGrade(1);
				
				memberService.updateMember(memberBaseinfoDTO);
				
				logger.info(payTradeEntity.getPayerUserId()+"notifymemberGrade:"+memberService.getMember(payTradeEntity.getPayerUserId()).getMemberGrade());
				/*
				 * 发送短信
				 */
				Map<String, Object> params = new HashMap<>();
				params.put(Constant.Alidayu.ACCOUNT, "供应商用户".equals(payTradeEntity.getPayerName())
						? payTradeEntity.getPayerMobile() : payTradeEntity.getPayerName());
				pvToolService.sendMsg(Long.parseLong(payTradeEntity.getPayerUserId()), payTradeEntity.getPayerMobile(),
						Constant.Alidayu.MESSAGETYPE2, MessageTemplateEnum.PAYSUCCESS, params);
				
				try {
					// 保存操作日志
					Map<String, Object> logMap = new HashMap<String, Object>();
					logMap.put("memberId", payTradeEntity.getPayerUserId());
					logMap.put("type", "1");
					logMap.put("description","系统变更：在线购买金牌会员服务");
					logMap.put("createUserId", "SYS");
					logMap.put("updateUserId", "SYS");
					memberChangeLogToolService.addMemberChangeLog(logMap);
					
					//从普通会员变更为金牌供应商时，状态改为未发送
					MemberMessageFlagEntity entity  = new MemberMessageFlagEntity();
					entity.setMemberId(Long.parseLong(payTradeEntity.getPayerUserId()));
					entity.setUpdateUserId("SYS");
					entity.setSendFlag("0");
					int row = memberMessageFlagToolService.update(entity);
					if(row == 0)
					{
						entity.setCreateUserId("SYS");
						entity.setCreateTime(new Date());
						memberMessageFlagToolService.insert(entity);
					}
				} catch (Exception e) {
					logger.error("用户"+payTradeEntity.getPayerUserId()+"在线购买金牌供应商支付成功，保存操作日志失败！");
				}
				

			} else { // 支付失败 不存在
				logger.error("金牌会员金失败：支付宝返回：" + payTradeEntity.getPayCenterNumber());
			}

			return Action.CommitMessage;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("金牌会员接收支付中心结果失败：" + e.getMessage());
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
		payResultToolService.updateOrderStatus(orderNo, EOrderStatus.PAYED.getCode());

		/*
		 * 添加支付流水信息
		 */
		PaySerialnumberEntity paySerialnumberEntity = new PaySerialnumberEntity();
		paySerialnumberEntity.setCreateTime(new Date());
		paySerialnumberEntity.setUpdatetime(new Date());
		try {
			paySerialnumberEntity.setOrderNo(Long.parseLong(orderNo));
		} catch (NumberFormatException e) {
			logger.error("农商友定金支付结果接收订单数据有误" + e.getMessage());
		}

		/*
		 * 设置支付流水数据
		 */
		paySerialnumberEntity.setPayStatus("1");
		paySerialnumberEntity.setStatementId(payTradeEntity.getPayCenterNumber());
		paySerialnumberEntity.setTradeAmount(payTradeEntity.getPayAmt());
		paySerialnumberEntity.setPayType("4");
		paySerialnumberEntity.setPaymentAcc(payTradeEntity.getThirdPayerAccount());
		paySerialnumberEntity.setRecipientAcc(payTradeEntity.getThirdPayeeAccount());
		paySerialnumberEntity.setPayTime(payTradeEntity.getPayTime());
		paySerialnumberEntity.setThirdStatementId(payTradeEntity.getThirdPayNumber());

		try {
			paySerialnumberEntity.setMemberId(Integer.parseInt(payTradeEntity.getPayerUserId()));
		} catch (NumberFormatException e) {
			logger.error("金牌会员结果接收买家ID转换错误" + e.getMessage());
		}

		/*
		 * 添加支付流水信息
		 */
		payResultToolService.addPaySerialnumber(paySerialnumberEntity);
	}

}
