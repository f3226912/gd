package com.gudeng.commerce.gd.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.api.redis.RedisService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.VerifyCodeService;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.innovane.win9008.exception.BusinessException;

public class VerifyCodeServiceImpl implements VerifyCodeService {
	/** 记录日志 */
	private static Logger logger = LoggerFactory
			.getLogger(VerifyCodeServiceImpl.class);

	@Autowired
	private MemberToolService memberToolService;

	public enum VERIFY_CODE_TYPE {
		ZC("ZC"), WJMM("WJMM"), XGZFMM("XGZFMM"), SZZFMM("SZZFMM");

		VERIFY_CODE_TYPE(String value) {
			this.value = value;
		}

		private final String value;

		public String getValue() {
			return value;
		}
	}

	@Autowired
	private RedisService redisService;

	@Override
	public void getVerifyCode(String mobile, String typeStr, String encryptStr,
			MemberBaseinfoDTO memberBaseinfoDTO) throws Exception {
		if (StringUtils.isBlank(mobile)) {
			// setResult(result, ErrorCodeEnum.FAIL.getValue(), "手机号不能为空",
			// request, response);
			throw new BusinessException("手机号不能为空");
		}

		if (!Validator.isMobile(mobile)) {
			// setResult(result, ErrorCodeEnum.FAIL.getValue(), "手机号不正确",
			// request, response);
			throw new BusinessException("手机号不正确");
		}

		if (StringUtils.isBlank(typeStr)) {
			// setResult(result, ErrorCodeEnum.FAIL.getValue(), "发送类型不能为空",
			// request, response);
			throw new BusinessException("发送类型不能为空");
		}

		if (StringUtils.isBlank(encryptStr)) {
			// setResult(result, ErrorCodeEnum.FAIL.getValue(), "密串不能为空",
			// request, response);
			throw new BusinessException("密串不能为空");
		}
		if (!CommonUtils.verifyMobile(mobile, encryptStr)) {
			// setResult(result, ErrorCodeEnum.FAIL.getValue(), "密串不匹配",
			// request, response);
			throw new BusinessException("密串不匹配");
		}
		// 取redis缓存,获取通道号
		String channel = "";
		Object obj = redisService.get("GDSMS_CHANNEL");
		logger.info("redis channel:###############" + obj);
		channel = obj == null ? Constant.Alidayu.DEFAULTNO : obj.toString();
		logger.info("redis channel:###############" + channel);

		// 发送验证码
		String verifycode = CommonUtils.genVerfiyCode(true, 6);

		//
		StringBuffer redisVerifyKey = new StringBuffer();

		logger.info("**************手机: " + mobile + ",验证码: " + verifycode);
		boolean isExistedAcc = memberToolService.isAccExisted(mobile);
		String content=null;
		if (VERIFY_CODE_TYPE.ZC.getValue().equals(typeStr)) { // 注册操作
			if (isExistedAcc) {
				// setResult(result, ErrorCodeEnum.FAIL.getValue(), "用户名已存在",
				// request, response);
				throw new BusinessException("用户名已存在");
			}
			if(Constant.Alidayu.REDISTYPE.equals(channel)){
				content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE3,verifycode);
				
			}else{
				content=MessageTemplateEnum.REGISTER.getTemplate().replace("{P}", verifycode);
			}
//			CommonUtils.sendMsg(channel, MessageTemplateEnum.REGISTER
//					.getTemplate().replace("{P}", verifycode), mobile);
			redisVerifyKey.append(mobile).append("-").append(typeStr);

		} else if (VERIFY_CODE_TYPE.WJMM.getValue().equals(typeStr)) { // 忘记密码操作
			if (!isExistedAcc) {
				// setResult(result, ErrorCodeEnum.FAIL.getValue(), "用户名不存在",
				// request, response);
				throw new BusinessException("手机号码尚未注册");
			}
			if(Constant.Alidayu.REDISTYPE.equals(channel)){
				content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE1,verifycode);
				
			}else{
				content=MessageTemplateEnum.FORGET_PWD.getTemplate().replace("{P}", verifycode);
			}
//			CommonUtils.sendMsg(channel, MessageTemplateEnum.FORGET_PWD
//					.getTemplate().replace("{P}", verifycode), mobile);
			redisVerifyKey.append(mobile).append("-").append(typeStr);
		} else if (VERIFY_CODE_TYPE.XGZFMM.getValue().equals(typeStr)) {
			// 修改支付密码
			if(Constant.Alidayu.REDISTYPE.equals(channel)){
				content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE5,verifycode);
				
			}else{
				content=MessageTemplateEnum.FORGET_PAY_PASSWORD.getTemplate().replace("{P}", verifycode);
			}
			mobile=memberBaseinfoDTO.getMobile();
			
//			CommonUtils.sendMsg(channel,
//					MessageTemplateEnum.FORGET_PAY_PASSWORD.getTemplate()
//							.replace("{P}", verifycode), memberBaseinfoDTO
//							.getMobile());
			redisVerifyKey.append(mobile).append("-").append(typeStr);
		} else if (VERIFY_CODE_TYPE.SZZFMM.getValue().equals(typeStr)) {
			// 修改支付密码
			if(Constant.Alidayu.REDISTYPE.equals(channel)){
				content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE5,verifycode);
				
			}else{
				content=MessageTemplateEnum.FORGET_PAY_PASSWORD.getTemplate().replace("{P}", verifycode);
			}
			mobile=memberBaseinfoDTO.getMobile();
//			CommonUtils.sendMsg(channel,
//					MessageTemplateEnum.FORGET_PAY_PASSWORD.getTemplate()
//							.replace("{P}", verifycode), memberBaseinfoDTO
//							.getMobile());
			redisVerifyKey.append(mobile).append("-").append(typeStr);
		}
		CommonUtils.sendMsg(channel,content, mobile);
		//验证码默认在redis中保存十分钟
		redisService.setex(redisVerifyKey.toString(), verifycode, 600L);

	}

	@Override
	public void verifyByMobile(String verifyType, String mobile,
			String verifyCode) throws Exception {
		StringBuffer redisVerifyKey = new StringBuffer();
		redisVerifyKey.append(mobile).append("-").append(verifyType);

		Long existedSeconds=600l-redisService.ttl(redisVerifyKey.toString());
		//验证码已存在超过三分钟
		if (existedSeconds>180) {
			logger.info("redisVerifyKey             " + redisVerifyKey.toString());
			logger.info("verifyCode             " + verifyCode);
			throw new BusinessException("验证码已失效，请重新获取");
		}
		String verifyCodeInRedis = redisService.get(redisVerifyKey.toString());
		logger.info("verifyCodeInRedis             " + verifyCodeInRedis);
		logger.info("verifyCode             " + verifyCode);
		logger.info("验证码匹配" + verifyCode);

		if (!verifyCode.equals(verifyCodeInRedis)) {
			throw new BusinessException("验证码不正确，请重新输入");
		}

		logger.info("验证码匹配" + verifyCode);

	}

	@Override
	public void verifyBySid(String verifyType,String sid,String verifyCode  ) {

	
	}
}
