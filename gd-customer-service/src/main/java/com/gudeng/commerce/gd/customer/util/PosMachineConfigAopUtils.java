package com.gudeng.commerce.gd.customer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PosMachineConfigDto;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.ReBusinessPosEntity;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.customer.service.ReBusinessMarketService;

@Aspect
public class PosMachineConfigAopUtils {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PosMachineConfigAopUtils.class);
	@Autowired
	public GdProperties gdProperties;

	@Autowired
	private BaseDao<?> baseDao;

	@Autowired
	private BusinessBaseinfoService businessBaseinfoService;
	@Autowired
	private ReBusinessMarketService reBusinessMarketService;

	public BaseDao<?> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.ReBusinessPosServiceImpl.addReBusinessPosEntity(..))",returning="id")
	public void persistBusinessPos(JoinPoint jp,Object id) {
		Object[] objects = jp.getArgs();
		Long infoId =(Long)id;
		logger.info(infoId+"");
		ReBusinessPosEntity entity = (ReBusinessPosEntity) objects[0];
		Map<String,Object> map=new HashMap<>();		
		map.put("businessId", entity.getBusinessId());
		try {
			MqUtil.send(castPostMachineDto(entity));
		} catch (Exception e) {
			logger.error("pos终端信息 --> 发送MQ推送信息异常; ex : {}", e);
		}
	}
	                                        
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.ReBusinessPosServiceImpl.updateByOriNewPosNum(..))",returning="id")
	public void deleteBusinessPos(JoinPoint jp,Object id) {
		Object[] objects = jp.getArgs();
		
		PosMachineConfigDto posMachineConfigDto =new PosMachineConfigDto();
		Map<String,Object> paraMap=(Map)objects[0];
		posMachineConfigDto.setBusinessId(Long.valueOf(paraMap.get("businessId")+""));
		posMachineConfigDto.setMachineNum(paraMap.get("posNumber")+"");
		posMachineConfigDto.setState("0");
		try {
			MqUtil.send(posMachineConfigDto);
		} catch (Exception e) {
			logger.error("pos终端信息 --> 发送MQ推送信息异常; ex : {}", e);
		}
	}
	private PosMachineConfigDto  castPostMachineDto(ReBusinessPosEntity entity) throws Exception {
		PosMachineConfigDto posMachineConfigDto =new PosMachineConfigDto();
		String dt = entity.getCreateTime()+"";
		SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			posMachineConfigDto.setCreateTime(sdf2.format(sdf1.parse(dt)));
			posMachineConfigDto.setUpdateTime(sdf2.format(sdf1.parse(dt)));
			posMachineConfigDto.setCreateUserId(entity.getCreatUserId());
			posMachineConfigDto.setUpdateUserId(entity.getUpdateUserId());
			
			BusinessBaseinfoDTO busdto=businessBaseinfoService.getById(entity.getBusinessId().toString());
			if (null!=busdto.getUserId()) {
				posMachineConfigDto.setMemberId(busdto.getUserId());
			}
			
			/*if (null!=busdto.getUserId()) {
			 	MemberBaseinfoDTO mbudto=memberBaseinfoService.getById(busdto.getUserId().toString());
			 	if (null!=mbudto.getUserType()) {
			 		posMachineConfigDto.setUserType(mbudto.getUserType().toString());
				}
				//posMachineConfigDto.setMobile(mbudto.getMobile());
				posMachineConfigDto.setAccount(mbudto.getAccount());
			}*/
			
			ReBusinessMarketDTO rReBusinessMarketDTO=reBusinessMarketService.getByBusinessId(entity.getBusinessId()); //根据商铺找对应的市场
			posMachineConfigDto.setId(entity.getId());
			posMachineConfigDto.setMarketId(rReBusinessMarketDTO.getMarketId());
			posMachineConfigDto.setMarketName(rReBusinessMarketDTO.getMarketName());
			posMachineConfigDto.setMachineNum(entity.getPosNumber());
			posMachineConfigDto.setBusinessId(entity.getBusinessId());
			posMachineConfigDto.setShopsName(busdto.getShopsName());
			posMachineConfigDto.setHasClear(entity.getHasClear());
            posMachineConfigDto.setState(entity.getState());
		} catch (ParseException e) {
			logger.error("时间格式转换错误...");
			e.printStackTrace();
		}

		return posMachineConfigDto;
	}

}
