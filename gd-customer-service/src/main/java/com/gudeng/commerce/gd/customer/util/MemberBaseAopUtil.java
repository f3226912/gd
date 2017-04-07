package com.gudeng.commerce.gd.customer.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberSendDto;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;

@Aspect
@Component
public class  MemberBaseAopUtil {

	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private MemberBaseinfoService memberBaseinfoService;
	
	public BaseDao<?> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.MemberBaseinfoServiceImpl.addMemberBaseinfoEnt(..))",returning="id")
	public void addMemberBaseinfo(JoinPoint jp,Object id) {
		Object[] objects = jp.getArgs();
		Long memberId =(Long)id;
		MemberBaseinfoEntity entity = (MemberBaseinfoEntity) objects[0];
		entity.setMemberId(memberId);
		MqUtil.send(castMemberSendDto(entity,0));
		MqUtil.sendPay(castMemberSendDto(entity,0));
	}

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.MemberBaseinfoServiceImpl.addMemberBaseinfoDTO(..))")
	public void addMemberBaseinfoDTO(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		MemberBaseinfoDTO entity = (MemberBaseinfoDTO) objects[0];
		MemberBaseinfoDTO dto = null;
		try {
			dto = memberBaseinfoService.getByMobile(entity.getMobile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		entity.setMemberId(dto.getMemberId());
		entity.setCreateTime(dto.getCreateTime());
		MqUtil.send(castMemberSendDto(dto,0));
		MqUtil.sendPay(castMemberSendDto(dto,0));
	}
	/**
	 * 处理会员注册
	 * @param jp
	 */
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.MemberBaseinfoServiceImpl.addMember(..))")
	public void addMember(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		MemberBaseinfoDTO entity = (MemberBaseinfoDTO) objects[0];
		MemberBaseinfoDTO dto = null;
		try {
			dto = memberBaseinfoService.getByMobile(entity.getMobile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		entity.setMemberId(dto.getMemberId());
		entity.setCreateTime(dto.getCreateTime());
		MqUtil.send(castMemberSendDto(dto,0));
		MqUtil.sendPay(castMemberSendDto(dto,0));
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.MemberBaseinfoServiceImpl.updateMemberBaseinfoDTO(..))")
	public void updateMemberBaseinfoDTO(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		MemberBaseinfoDTO entity = (MemberBaseinfoDTO) objects[0];
		MqUtil.send(castMemberSendDto(entity,1));
		MqUtil.sendPay(castMemberSendDto(entity,1));
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.MemberBaseinfoServiceImpl.updateStatus(..))")
	public void updateStatus(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		MemberBaseinfoDTO entity = (MemberBaseinfoDTO) objects[0];
		MqUtil.send(castMemberSendDto(entity,1));
		MqUtil.sendPay(castMemberSendDto(entity,1));
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.MemberBaseinfoServiceImpl.updateMobile(..))")
	public void updateMobile(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		Long memberId =(Long) objects[0];
		String mobile =(String) objects[1];
		MemberBaseinfoDTO entity = new MemberBaseinfoDTO();
		entity.setMemberId(memberId);
		entity.setMobile(mobile);
		MqUtil.send(castMemberSendDto(entity,1));
		MqUtil.sendPay(castMemberSendDto(entity,1));
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.MemberBaseinfoServiceImpl.updateRealName(..))")
	public void updateRealName(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		Long memberId =(Long) objects[0];
		String realName =(String) objects[1];
		MemberBaseinfoDTO entity = new MemberBaseinfoDTO();
		entity.setMemberId(memberId);
		entity.setRealName(realName);
		MqUtil.send(castMemberSendDto(entity,1));
		MqUtil.sendPay(castMemberSendDto(entity,1));
	}
	
//	修改密码不提交mq
//	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.MemberBaseinfoServiceImpl.updatePassword(..))")
//	public void updatePassword(JoinPoint jp) {
//		Object[] objects = jp.getArgs();
//		MemberBaseinfoDTO entity = (MemberBaseinfoDTO) objects[0];
//		MqUtil.send(castMemberSendDto(entity,1));
//	}
				
	private MemberSendDto  castMemberSendDto(MemberBaseinfoEntity entity,Integer crud) {
		MemberSendDto memberSendDto =new MemberSendDto();
		memberSendDto.setCrud(crud);
		memberSendDto.setMemberId(Integer.valueOf(String.valueOf(entity.getMemberId())));
		if(StringUtils.isEmpty(entity.getRealName())){
			memberSendDto.setRealName(null);
		}else{
			memberSendDto.setRealName(entity.getRealName());
		}
		if(StringUtils.isEmpty(entity.getMobile())){
			memberSendDto.setMobile(null);
		}else{
			memberSendDto.setMobile(entity.getMobile());
		}
		if(StringUtils.isEmpty(entity.getAccount())){
			memberSendDto.setAccount(null);
		}else{
			memberSendDto.setAccount(entity.getAccount());
		}
		if(StringUtils.isEmpty(entity.getRegetype())){
			memberSendDto.setRegetype(null);
		}else{
			memberSendDto.setRegetype(Integer.valueOf(entity.getRegetype()));
		}
		if(StringUtils.isEmpty(entity.getStatus())){
			memberSendDto.setStatus(null);
		}else{  
			memberSendDto.setStatus(Integer.valueOf(entity.getStatus()));
		}
		if(crud==1){
			memberSendDto.setUpdateTime(new Date());
		}else{
			memberSendDto.setUpdateTime(entity.getCreateTime());
		}
		if (null!=entity.getLevel()) {
			memberSendDto.setLevel(entity.getLevel());
		}
		return memberSendDto;
	}
	
}
