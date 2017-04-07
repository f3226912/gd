package com.gudeng.commerce.gd.customer.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO;
import com.gudeng.commerce.gd.customer.dto.AccBankCardSendDto;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;
import com.gudeng.commerce.gd.customer.service.AccBankCardInfoService;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;

@Aspect
public class AccBankCardInfoAopUtils {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AccBankCardInfoAopUtils.class);
	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private AccBankCardInfoService accBankCardInfoService;
	
	@Autowired
	private MemberBaseinfoService memberBaseinfoService;
	
	@Autowired
	private AreaService areaService;
	
	public BaseDao<?> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.AccBankCardInfoServiceImpl.persistBankCard(..))",returning="id")
	public void persistBankCard(JoinPoint jp,Object id) {
		Object[] objects = jp.getArgs();
		Long infoId =(Long)id;
		AccBankCardInfoEntity entity = (AccBankCardInfoEntity) objects[0];
		logger.info("@AfterReturning persistBankCard AccBankCardInfoEntity : {} ", new Object[]{entity});
		Map map = new HashedMap();
		map.put("memberId", entity.getMemberId());
		map.put("startRow", 0);
		map.put("endRow", 10);
		entity.setInfoId(infoId);
		try {
			List<MemberBaseinfoDTO> list = memberBaseinfoService.getBySearch(map);
			if (list.isEmpty() || list.size() > 1 ){
				logger.error("用户信息异常: size : {}, memberId : {}", new Object[]{list.size(), entity.getMemberId()});
				throw new Exception("查询用户信息异常");
			}
			Map<String, Object> params = new HashedMap();
			params.put("userMobile", list.get(0).getMobile());
			params.put("account", list.get(0).getAccount());
			params.put("provinceName", areaService.getArea(String.valueOf(entity.getProvinceId())).getArea());
			Long areaId = entity.getAreaId();
			if (!CommonUtils.isEmpty(areaId)){
				AreaDTO area = areaService.getArea(String.valueOf(areaId));
				if (!CommonUtils.isEmpty(area)){
					params.put("areaName", area.getArea());
				}else{
					params.put("areaName", "");
				}
			}else{
				params.put("areaName", "");
			}
			Long cityId = entity.getCityId();
			if (!CommonUtils.isEmpty(cityId)){
				AreaDTO city = areaService.getArea(String.valueOf(cityId));
				if (!CommonUtils.isEmpty(city)){
					params.put("cityName", city.getArea());
				}else{
					params.put("cityName", "");
				}
			}else{
				params.put("cityName", "");
			}
			MqUtil.send(cast(castAccBankCardSendDto(entity,0),params));
		} catch (Exception e) {
			logger.error("绑定银行卡 persistBankCard --> 发送MQ推送信息异常; ex : {}", e);
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.AccBankCardInfoServiceImpl.addBankCard(..))")
	public void addBankCard(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		AccBankCardInfoDTO accBankCardInfoDTO = (AccBankCardInfoDTO) objects[0];
		logger.info("@AfterReturning addBankCard accBankCardInfoDTO : {} ", new Object[]{accBankCardInfoDTO});
		Map map = new HashedMap();
		map.put("memberId", accBankCardInfoDTO.getMemberId());
		map.put("startRow", 0);
		map.put("endRow", 10);
		try {
			List<MemberBaseinfoDTO> mlist = memberBaseinfoService.getBySearch(map);
			if (mlist.isEmpty() || mlist.size() > 1 ){
				logger.error("用户信息异常: size : {}, memberId : {}", new Object[]{mlist.size(), accBankCardInfoDTO.getMemberId()});
				throw new Exception("查询用户信息异常");
			}
			List<AccBankCardInfoDTO> list = accBankCardInfoService.getByCondition(accBankCardInfoDTO);
			if (list.isEmpty() || list.size() > 1 ){
				logger.error("查询用户绑定银行卡信息异常");
				throw new Exception("查询用户绑定银行卡信息异常");
			}
			accBankCardInfoDTO.setInfoId(list.get(0).getInfoId());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userMobile", mlist.get(0).getMobile());
			params.put("account", mlist.get(0).getAccount());
			params.put("provinceName", areaService.getArea(String.valueOf(accBankCardInfoDTO.getProvinceId())).getArea());
			Long areaId = accBankCardInfoDTO.getAreaId();
			if (!CommonUtils.isEmpty(areaId)){
				AreaDTO area = areaService.getArea(String.valueOf(areaId));
				if (!CommonUtils.isEmpty(area)){
					params.put("areaName", area.getArea());
				}else{
					params.put("areaName", "");
				}
			}else{
				params.put("areaName", "");
			}
			Long cityId = accBankCardInfoDTO.getCityId();
			if (!CommonUtils.isEmpty(cityId)){
				AreaDTO city = areaService.getArea(String.valueOf(cityId));
				if (!CommonUtils.isEmpty(city)){
					params.put("cityName", city.getArea());
				}else{
					params.put("cityName", "");
				}
			}else{
				params.put("cityName", "");
			}
			MqUtil.send(cast(castAccBankCardSendDto(accBankCardInfoDTO,0),params));
		} catch (Exception e) {
			logger.error("绑定银行卡 addBankCard --> 发送MQ推送信息异常; ex : {}", e);
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.AccBankCardInfoServiceImpl.updateBankCard(..))")
	public void updateBankCard(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		AccBankCardInfoDTO accBankCardInfoDTO = (AccBankCardInfoDTO) objects[0];
		logger.info("@AfterReturning updateBankCard accBankCardInfoDTO : {} ", new Object[]{accBankCardInfoDTO});
		Map map = new HashedMap();
		map.put("memberId", accBankCardInfoDTO.getMemberId());
		map.put("startRow", 0);
		map.put("endRow", 10);
		try {
			List<MemberBaseinfoDTO> list = memberBaseinfoService.getBySearch(map);
			if (list.isEmpty() || list.size() > 1 ){
				logger.error("用户信息异常: size : {}, memberId : {}", new Object[]{list.size(), accBankCardInfoDTO.getMemberId()});
				throw new Exception("查询用户信息异常");
			}
			Map<String, Object> params = new HashedMap();
			params.put("provinceName", areaService.getArea(String.valueOf(accBankCardInfoDTO.getProvinceId())).getArea());
			Long areaId = accBankCardInfoDTO.getAreaId();
			if (!CommonUtils.isEmpty(areaId)){
				AreaDTO area = areaService.getArea(String.valueOf(areaId));
				if (!CommonUtils.isEmpty(area)){
					params.put("areaName", area.getArea());
				}else{
					params.put("areaName", "");
				}
			}else{
				params.put("areaName", "");
			}
			Long cityId = accBankCardInfoDTO.getCityId();
			if (!CommonUtils.isEmpty(cityId)){
				AreaDTO city = areaService.getArea(String.valueOf(cityId));
				if (!CommonUtils.isEmpty(city)){
					params.put("cityName", city.getArea());
				}else{
					params.put("cityName", "");
				}
			}else{
				params.put("cityName", "");
			}
			accBankCardInfoDTO.setAccount(list.get(0).getAccount());
			accBankCardInfoDTO.setUserMobile(list.get(0).getMobile());
			MqUtil.send(cast(castAccBankCardSendDto(accBankCardInfoDTO,1),params));
		} catch (Exception e) {
			logger.error("绑定银行卡 updateBankCard --> 发送MQ推送信息异常; ex : {}", e);
		}
	}
	private static AccBankCardSendDto cast(AccBankCardSendDto dto, Map<String, Object> map) {
		
		logger.info("AccBankCardSendDto : {} , map {}", new Object[]{dto, map});
		//
		if(map.get("provinceName") != null){
			dto.setProvinceName(String.valueOf(map.get("provinceName")));
		}
		//
		if(map.get("cityName") != null){
			dto.setCityName(String.valueOf(map.get("cityName")));
		}
		//
		if(map.get("areaName") != null){
			dto.setAreaName(String.valueOf(map.get("areaName")));
		}
		if(map.get("userMobile") != null){
			dto.setTelephone(String.valueOf(map.get("userMobile")));
		}
		if(map.get("account") != null){
			dto.setAccount(String.valueOf(map.get("account")));
		}
		return dto;
	}
	private static AccBankCardSendDto castAccBankCardSendDto(AccBankCardInfoEntity entity, Integer crud) {
		
		logger.info("castAccBankCardSendDto : {} ", new Object[]{entity});
		AccBankCardSendDto sendDto = new AccBankCardSendDto();
		sendDto.setCrud(crud);
		if(entity instanceof AccBankCardInfoDTO){
			AccBankCardInfoDTO temp = ((AccBankCardInfoDTO)entity);
			if (temp.getUserMobile() != null){
				sendDto.setTelephone(String.valueOf(temp.getUserMobile()));
			}
		}
		//
		if(CommonUtils.isEmpty(entity.getAuditStatus())){
			sendDto.setAuditStatus(null);
		}else{
			sendDto.setAuditStatus(entity.getAuditStatus());
		}		
		//
		if(CommonUtils.isEmpty(entity.getRegtype())){
			sendDto.setRegtype(null);
		}else{
			sendDto.setRegtype(entity.getRegtype());
		}
		//
		if(CommonUtils.isEmpty(entity.getMobile())){
			sendDto.setMobile(null);
		}else{
			sendDto.setMobile(entity.getMobile());
		}
		//
		if(CommonUtils.isEmpty(entity.getInfoId())){
			sendDto.setInfoId(null);
		}else{
			sendDto.setInfoId(entity.getInfoId());
		}
		//
		if(CommonUtils.isEmpty(entity.getMemberId())){
			sendDto.setMemberId(null);
		}else{
			sendDto.setMemberId(entity.getMemberId().intValue());
		}
		//
		if(CommonUtils.isEmpty(entity.getRealName())){
			sendDto.setRealName(null);
		}else{
			sendDto.setRealName(entity.getRealName());
		}
		//
		if(CommonUtils.isEmpty(entity.getIdCard())){
			sendDto.setIdCard(null);
		}else{
			sendDto.setIdCard(entity.getIdCard());
		}
		//
		if(CommonUtils.isEmpty(entity.getDepositBankName())){
			sendDto.setDepositBankName(null);
		}else{
			sendDto.setDepositBankName(entity.getDepositBankName());
		}
		//
		if(CommonUtils.isEmpty(entity.getSubBankName())){
			sendDto.setSubBankName(null);
		}else{
			sendDto.setSubBankName(entity.getSubBankName());
		}
		//
		if(CommonUtils.isEmpty(entity.getBankCardNo())){
			sendDto.setBankCardNo(null);
		}else{
			sendDto.setBankCardNo(entity.getBankCardNo());
		}
		//
		if(CommonUtils.isEmpty(entity.getProvinceId())){
			sendDto.setProvinceId(null);
		}else{
			sendDto.setProvinceId(entity.getProvinceId().intValue());
		}
		//
		if(CommonUtils.isEmpty(entity.getCityId())){
			sendDto.setCityId(null);
		}else{
			sendDto.setCityId(entity.getCityId().intValue());
		}
		//
		if(CommonUtils.isEmpty(entity.getAreaId())){
			sendDto.setAreaId(null);
		}else{
			Long areaId = entity.getAreaId();
			sendDto.setAreaId(areaId.intValue());
		}
		//
		if(CommonUtils.isEmpty(entity.getAccCardType())){
			sendDto.setAccCardType(null);
		}else{
			sendDto.setAccCardType(entity.getAccCardType());
		}
		//
		if(CommonUtils.isEmpty(entity.getStatus())){
			sendDto.setStatus(null);
		}else{
			sendDto.setStatus(entity.getStatus());
		}
		//
		if(crud == 1){
			sendDto.setUpdateTime(new Date());
		}else{
			sendDto.setUpdateTime(entity.getCreateTime());
		}
		return sendDto;
	}
}
