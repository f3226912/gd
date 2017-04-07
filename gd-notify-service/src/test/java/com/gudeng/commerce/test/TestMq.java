package com.gudeng.commerce.test;

import java.util.Date;

import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.gudeng.commerce.gd.customer.entity.PayTradeEntity;

import junit.framework.TestCase;

public class TestMq extends TestCase {
	
	private SendMessageUtil sendMessageUtil = new SendMessageUtil();
	
	public void testPayReturn() {
		PayTradeEntity payTradeEntity = new PayTradeEntity();
		
		payTradeEntity.setOrderNo("164332113767");
		payTradeEntity.setPayTime(new Date());
		payTradeEntity.setPayerAccount("15379782042");
		payTradeEntity.setPayeeAccount("11011010110");
		payTradeEntity.setPayAmt(998.00);
		payTradeEntity.setPayerUserId("5002");
		payTradeEntity.setPayStatus("2");
		payTradeEntity.setPayCenterNumber("3333333333");
		payTradeEntity.setThirdPayNumber("444444444444444");
		payTradeEntity.setPayerMobile("18617159286");
		
		
		sendMessageUtil.sendAsyncMessage("dev_member_order_pay_detail", "TAGS", payTradeEntity, new SendCallback() {
			
			@Override
			public void onSuccess(SendResult sendResult) {
				// TODO Auto-generated method stub
				System.out.println("成功");
			}
			
			@Override
			public void onException(OnExceptionContext context) {
				System.out.println("失败");
			}
		});
		
		
	}

}
