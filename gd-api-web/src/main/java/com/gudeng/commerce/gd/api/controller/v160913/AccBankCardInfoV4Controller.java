package com.gudeng.commerce.gd.api.controller.v160913;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AccBankCardInfoToolService;
import com.gudeng.commerce.gd.api.service.impl.WalletToolServiceImpl;
import com.gudeng.commerce.gd.api.util.BankUtil;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO;
import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO.BANK_CARD_STATUS;
import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;
import com.innovane.win9008.exception.BusinessException;

@Controller
@RequestMapping("v4/accBankCard")
public class AccBankCardInfoV4Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AccBankCardInfoV4Controller.class);
	@Autowired
	private AccBankCardInfoToolService accBankCardInfoToolService;
	
	@Autowired
	private WalletToolServiceImpl walletToolServiceImpl;

	@SuppressWarnings("unchecked")
	@RequestMapping("/getBankCards")
	public void getBankCards(HttpServletRequest request, HttpServletResponse response) {
		
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			AccBankCardInfoDTO paramsDto = (AccBankCardInfoDTO) GSONUtils.fromJsonToObject(jsonStr, AccBankCardInfoDTO.class);
			paramsDto.setRegtype("1");
			
			List<AccBankCardInfoDTO> accBankCardInfoDTOs = accBankCardInfoToolService.getBankCards(paramsDto.getMemberId());
			result.setObject(accBankCardInfoDTOs);
			
		} catch (Exception e) {
			logger.info("获取绑定的银行卡信息异常; ex : {}", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}

	@RequestMapping("/updateBankCard")
	public void updateBankCard(HttpServletRequest request, HttpServletResponse response) {
		
		ObjectResult result = new ObjectResult();
		try {
			
			String jsonStr = getParamDecodeStr(request);
			AccBankCardInfoDTO paramsDto = (AccBankCardInfoDTO) GSONUtils.fromJsonToObject(jsonStr, AccBankCardInfoDTO.class);
			logger.info("api updateBankCard paramsDto : {} ", new Object[]{paramsDto});
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("infoId", paramsDto.getInfoId());
			AccBankCardInfoDTO older = accBankCardInfoToolService.getBankCardBySearch(params);
			if (older == null){
				result.setObject("记录丢失..");
				logger.info("记录丢失..");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
/*			if (paramsDto == null){
				result.setObject("参数对象为空..");
				logger.error("参数对象为空..");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			}*/
			//检测是否有内容变更
			if (isChanged(older, paramsDto)){
				paramsDto.setAuditStatus("2");// 2-未验证
			}
			accBankCardInfoToolService.updateBankCard(paramsDto);
			
		} catch (Exception e) {
			logger.info("修改绑定的银行卡信息异常 ; ex : {} ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}


	@RequestMapping("/newBankCard")
	public void newBankCard(HttpServletRequest request, HttpServletResponse response) {
		
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			AccBankCardInfoDTO paramsDto = (AccBankCardInfoDTO) GSONUtils.fromJsonToObject(jsonStr, AccBankCardInfoDTO.class);
			
			logger.info("api newBankCard paramsDto : {} ", new Object[]{paramsDto});
			if(StringUtils.isEmpty(String.valueOf(paramsDto.getMemberId()))){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			
			AccBankCardInfoEntity entity = new AccBankCardInfoEntity();
			entity.setMemberId(paramsDto.getMemberId());
			entity.setAccountId(paramsDto.getAccountId());
			
/*			// 账户信息
			AccInfoDTO accinfoTo = walletToolServiceImpl.getWalletIndex(paramsDto.getMemberId());
			if (null == accinfoTo) {
				throw new BusinessException("请先创建一个账户");
			}
			Integer accountId = accinfoTo.getAccId();
			entity.setAccountId(Long.valueOf(accountId));*/
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", paramsDto.getMemberId());
			params.put("regtype", 1);//1-农速通
			AccBankCardInfoDTO exists = accBankCardInfoToolService.getBankCardBySearch(params);
			if (exists != null){
				result.setObject("已经绑定过一张注册来源为农速通的银行卡了..");
				logger.info("已经绑定过一张注册来源为农速通的银行卡了..");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			String regtype = paramsDto.getRegtype();
			if (CommonUtils.isEmpty(regtype)){
				result.setObject("注册来源不能为空..");
				logger.info("注册来源不能为空..");
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
				return ;
			}
			Date now = new Date();
			entity.setAccCardType(paramsDto.getAccCardType());
			entity.setAreaId(paramsDto.getAreaId());
			entity.setBankCardNo(paramsDto.getBankCardNo());
			entity.setBankId(paramsDto.getBankId());
			entity.setCityId(paramsDto.getCityId());
			entity.setCreateTime(now);
			entity.setCreateUserId(String.valueOf(paramsDto.getMemberId()));
			entity.setDepositBankName(paramsDto.getDepositBankName());
			entity.setIdCard(paramsDto.getIdCard());
			entity.setMobile(paramsDto.getMobile());
			entity.setProvinceId(paramsDto.getProvinceId());
			entity.setRealName(paramsDto.getRealName());
			entity.setStatus(BANK_CARD_STATUS.VALIDATE.getValue());
			entity.setSubBankName(paramsDto.getSubBankName());
			entity.setUpdateTime(now);
			entity.setAuditStatus("2");// 2-未验证
			entity.setUpdateUserId(String.valueOf(paramsDto.getMemberId()));
			entity.setRegtype(regtype);
			accBankCardInfoToolService.newBankCard(entity);
//			accBankCardInfoToolService.addBankCard(dto);
		} catch (BusinessException e) {
			logger.info("新增绑定的银行卡信息异常..", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		} catch (Exception e) {
			logger.info("新增绑定的银行卡信息异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	@RequestMapping("/getNameOfBank")
	public void getNameOfBank(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String bankCardNo = (String)GSONUtils.getJsonValueStr(jsonStr, "bankCardNo");
			String bankName = BankUtil.getNameOfBank(bankCardNo);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", 1);
			map.put("bankType", bankName);
			result.setObject(map);
		} catch (Exception e) {
			logger.info("获取银行卡类型异常; ex : {} ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	@RequestMapping("/updateBankCardAuditStatus")
	public void updateBankCardAuditStatus(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String auditStatus = (String)GSONUtils.getJsonValueStr(jsonStr, "auditStatus");
			String infoId = (String)GSONUtils.getJsonValueStr(jsonStr, "infoId");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("infoId", infoId);
			params.put("auditStatus", auditStatus);
			accBankCardInfoToolService.updateBankCardAuditStatus(params);
		} catch (Exception e) {
			logger.info("更新绑定银行卡审核状态; ex : {} ", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			return ;
		}
		setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
	
	private boolean isChanged(AccBankCardInfoDTO older, AccBankCardInfoDTO newer){
		//双方的realName不同时为空
		if ( !(CommonUtils.isEmpty(older.getRealName()) && CommonUtils.isEmpty(older.getRealName())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getRealName()) && older.getRealName().equals(newer.getRealName()) ) ){
				logger.info("真实姓名变更 ， older value : {},  new value : {}" , older.getRealName(), newer.getRealName());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getIdCard()) && CommonUtils.isEmpty(older.getIdCard())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getIdCard()) && older.getIdCard().equals(newer.getIdCard()) ) ){
				logger.info("idCard变更 ， older value : {},  new value : {}" , older.getIdCard(), newer.getIdCard());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getDepositBankName()) && CommonUtils.isEmpty(older.getDepositBankName())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getDepositBankName()) && older.getDepositBankName().equals(newer.getDepositBankName()) ) ){
				logger.info("开户行名称变更 ， older value : {},  new value : {}" , older.getDepositBankName(), newer.getDepositBankName());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getSubBankName()) && CommonUtils.isEmpty(older.getSubBankName())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getSubBankName()) && older.getSubBankName().equals(newer.getSubBankName()) ) ){
				logger.info("开户行支行名称变更 ， older value : {},  new value : {}" , older.getSubBankName(), newer.getSubBankName());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getBankCardNo()) && CommonUtils.isEmpty(older.getBankCardNo())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getBankCardNo()) && older.getBankCardNo().equals(newer.getBankCardNo()) ) ){
				logger.info("银行卡号变更 ， older value : {},  new value : {}" , older.getBankCardNo(), newer.getBankCardNo());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getMobile()) && CommonUtils.isEmpty(older.getMobile())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getMobile()) && older.getMobile().equals(newer.getMobile()) ) ){
				logger.info("银行卡号预留手机号变更 ， older value : {},  new value : {}" , older.getMobile(), newer.getMobile());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getProvinceId()) && CommonUtils.isEmpty(older.getProvinceId())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getProvinceId()) && older.getProvinceId().equals(newer.getProvinceId()) ) ){
				logger.info("银行所在省份变更 ， older value : {},  new value : {}" , older.getProvinceId(), newer.getProvinceId());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getCityId()) && CommonUtils.isEmpty(older.getCityId())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getCityId()) && older.getCityId().equals(newer.getCityId()) ) ){
				logger.info("银行所在城市变更 ， older value : {},  new value : {}" , older.getCityId(), newer.getCityId());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getAreaId()) && CommonUtils.isEmpty(older.getAreaId())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getAreaId()) && older.getAreaId().equals(newer.getAreaId()) ) ){
				logger.info("区域id变更 ， older value : {},  new value : {}" , older.getAreaId(), newer.getAreaId());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getAccCardType()) && CommonUtils.isEmpty(older.getAccCardType())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getAccCardType()) && older.getAccCardType().equals(newer.getAccCardType()) ) ){
				logger.info("账号类型变更 ， older value : {},  new value : {}" , older.getAccCardType(), newer.getAccCardType());
				return true;
			}
		}
		if ( !(CommonUtils.isEmpty(older.getRegtype()) && CommonUtils.isEmpty(older.getRegtype())) ){
			//不相等
			if ( !( !CommonUtils.isEmpty(older.getRegtype()) && older.getRegtype().equals(newer.getRegtype()) ) ){
				logger.info("注册来源变更 ， older value : {},  new value : {}" , older.getRegtype(), newer.getRegtype());
				return true;
			}
		}
		return false;
	}	
	public static void main(String[] args) {
		System.out.println("".equals(null));
	}
}
