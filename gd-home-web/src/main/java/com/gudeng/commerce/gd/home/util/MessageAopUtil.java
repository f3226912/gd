package com.gudeng.commerce.gd.home.util;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.commerce.gd.home.service.PushRecordToolService;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 消息推送aop拦截
 * 
 * @author lf
 * 
 */
@Component
@Aspect
public class MessageAopUtil {

	@Autowired
	public GdProperties gdProperties;

	@Autowired
	private PushRecordToolService pushRecordToolService;
	
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MessageAopUtil.class);

	// 找回密码成功
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.home.service.impl.LoginToolServiceImpl.updatePassword(..))")
	public void sendMessageByUpdatePassword(JoinPoint jp, Object rvt) {
		try {
			int resultFlag = (int)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					MemberBaseinfoDTO mbdto = (MemberBaseinfoDTO) objects[0];
					PushRecordEntity prdto = new PushRecordEntity();
					if(null != mbdto){
						prdto.setType("1");
						prdto.setReceiveType("1");
						prdto.setTitle("密码修改");
						prdto.setContent("尊敬的用户：您已经于"+DateUtil.getSysDateTimeString()+"修改了密码，请妥善保管您的密码。");
						prdto.setMemberId(mbdto.getMemberId());
						prdto.setCreateUserId(mbdto.getMemberId().toString());
						prdto.setCreateTime(new Date());
						prdto.setState("0");
						prdto.setOrigin(2);
						pushRecordToolService.add(prdto);
					}
				}
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
	}
	
	// 注册成功
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.home.service.impl.LoginToolServiceImpl.register(..))")
	public void sendMessageByRegister(JoinPoint jp, Object rvt) {
		try {
			Long resultFlag = (Long)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					MemberBaseinfoEntity mbdto = (MemberBaseinfoEntity) objects[0];
					PushRecordEntity prdto = new PushRecordEntity();
					if(null != mbdto){
						prdto.setType("1");
						prdto.setReceiveType("1");
						prdto.setTitle("注册成功");
						prdto.setContent("尊敬的用户：欢迎您入驻谷登农批网，您已经通过注册，您可以更新您的信息，祝您使用愉快！");
						prdto.setMemberId(mbdto.getMemberId());
						prdto.setCreateUserId(mbdto.getMemberId().toString());
						prdto.setCreateTime(new Date());
						prdto.setState("0");
						prdto.setOrigin(2);
						pushRecordToolService.add(prdto);
					}
				}
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
	}
	
	// 产品修改
	@AfterReturning(returning = "rvt", pointcut = "execution(* com.gudeng.commerce.gd.home.service.impl.ProductToolServiceImpl.updateProduct(..))")
	public void sendMessageByUpdateProduct(JoinPoint jp, Object rvt) {
		try {
			int resultFlag = (int)rvt;
			if(resultFlag > 0){
				Object[] objects = jp.getArgs();
				if (objects.length > 0) {
					ProductDto mbdto = (ProductDto) objects[0];
					PushRecordEntity prdto = new PushRecordEntity();
					if(null != mbdto){
						prdto.setType("1");
						prdto.setReceiveType("1");
						prdto.setTitle("产品修改");
						prdto.setContent("您发布的"+mbdto.getProductName()+"产品已修改，我们将尽快对其审核，审核完成后该产品将排序靠前。");
						prdto.setMemberId(mbdto.getMemberId());
						prdto.setCreateUserId(mbdto.getMemberId().toString());
						prdto.setCreateTime(new Date());
						prdto.setState("0");
						prdto.setOrigin(2);
						pushRecordToolService.add(prdto);
					}
				}
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
	}
}
