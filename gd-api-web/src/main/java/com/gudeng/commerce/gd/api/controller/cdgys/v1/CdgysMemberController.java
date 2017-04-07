package com.gudeng.commerce.gd.api.controller.cdgys.v1;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ComplaintToolService;
import com.gudeng.commerce.gd.api.service.MarketToolService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductCategoryToolService;
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.service.v160929.CertifBaseToolService;
import com.gudeng.commerce.gd.api.thread.GetNstTokenThread;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MemberLoginLogUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.SyncUtil;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.entity.ComplaintEntity;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 产地供应商 会员
 * @author Semon
 *
 */
@Controller
@RequestMapping("cdgys")
public class CdgysMemberController  extends GDAPIBaseController   {
	
	/** 记录日志־ */
	private static Logger logger = LoggerFactory.getLogger(CdgysMemberController.class);

	@Autowired
	public MemberToolService memberToolService;
	@Autowired
	private MarketToolService marketToolService;
	@Autowired
	private AreaToolService areaToolService;

	@Autowired
	private MemberCertifiApiService memberCertifiApiService;
	@Autowired
	public CertifBaseToolService certifBaseToolService;
	@Autowired
	public UsercollectProductCategoryToolService usercollectProductCategoryToolService;
	@Autowired
	public MemberAddressApiService memberAddressApiService;
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 
	
	@Autowired
	private ComplaintToolService complaintToolService;
	@Autowired
	private NstApiCommonService nstApiCommonService;
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	@Autowired
	public GdProperties gdProperties;
	
	@RequestMapping("v1/login")
	public void login(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		String username = memberDtoInput.getAccount();
		String password = memberDtoInput.getPassword();
		String deviceTokens = memberDtoInput.getDevice_tokens();
		if(StringUtils.isBlank(username)){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(password)){
			setResult(result, ErrorCodeEnum.PASSWORD_IS_EMPTY, request, response);
			return;
		}
		
		if(!Validator.isMobile(username)){
			setResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
			return;
		}
		
		try {
			logger.debug("User info: username: " + username + ", password: " + password);
			//判断用户是否存在, 根据手机号查询
			MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(username);
			if (null == memberDTO){
				//根据账号查询
				memberDTO = memberToolService.getByAccount(username);
				if(null == memberDTO){
					setResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
					return;
				}
			}
			//判断用户是否禁用
			String status = memberDTO.getStatus();
			if(!"1".equals(status)){
				setResult(result, ErrorCodeEnum.ACCOUNT_IS_DISABLE, request, response);
				return;
			}
			
			//判断密码是否正确
			if(!password.equals(memberDTO.getPassword())){
				setResult(result, ErrorCodeEnum.PASSWORD_ERROR, request, response);
				return;
			}
			
			//判断是否供应商的用户
			if(memberDTO.getLevel() != 4){
				setResult(result, ErrorCodeEnum.GYS_ACCOUNT_ONLY_PERMIT, request, response);
				return;
			}			
			
			//更新deviceTokens
			MemberBaseinfoDTO memberUpdateDTO = new MemberBaseinfoDTO();
			memberUpdateDTO.setMemberId(memberDTO.getMemberId());
			memberUpdateDTO.setDevice_tokens(deviceTokens);
			memberToolService.updateMember(memberUpdateDTO);
			
			MemberLoginLogUtil.insertLoginLog(memberDTO, "5", request);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberDTO.getMemberId());
			map.put("mobile", memberDTO.getMobile());
			map.put("userType", memberDTO.getUserType()==null ? 0 : memberDTO.getUserType());
			map.put("level", memberDTO.getLevel());
			map.put("realName", memberDTO.getRealName()==null ? "" : memberDTO.getRealName());
			map.put("sex", memberDTO.getSex()==null ? "" : memberDTO.getSex());
			map.put("companyName", memberDTO.getCompanyName()==null? "" :memberDTO.getCompanyName());
			map.put("companyContact", memberDTO.getCompanyContact()==null ? "":memberDTO.getCompanyContact());
			//设置金牌会员信息
			String memberGrade = memberDTO.getMemberGrade() == null ? "0" : memberDTO.getMemberGrade().toString();
			map.put("memberGrade", memberGrade);
			if("1".equals(memberGrade)){
				map.put("validTime", DateTimeUtils.formatDate(memberDTO.getValidTime(), DateUtil.DATE_FORMAT_DATEONLY));
				map.put("expireTime", DateTimeUtils.formatDate(memberDTO.getExpireTime(), DateUtil.DATE_FORMAT_DATEONLY));
			}
			
			//获取商铺信息
			BusinessBaseinfoDTO shopDto = businessBaseinfoToolService.getByUserId(memberDTO.getMemberId().toString());
			if(shopDto == null){
				map.put("marketId", "");
				map.put("businessId","");
				map.put("shopName", "");
				map.put("cityId", "");
				map.put("provinceId", "");
				map.put("areaId", "");
			}else{
				map.put("marketId", shopDto.getMarketId());
				map.put("businessId", shopDto.getBusinessId());
				map.put("shopName", shopDto.getShopsName());
				map.put("cityId", shopDto.getCityId() == null ? "" : shopDto.getCityId());
				map.put("provinceId", shopDto.getProvinceId() == null ? "" : shopDto.getProvinceId());
				map.put("areaId", shopDto.getAreaId() == null ? "" : shopDto.getAreaId());
			}			
			
			//获取认证信息
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberDTO.getMemberId());
			Map<String, Object> res = certifBaseToolService.getStatusCombination(params);
			map.put("cpstatus", res.get("cpstatus"));
			map.put("ccstatus", res.get("ccstatus"));
			map.put("cbstatus", 0);
			map.put("comstatus", res.get("comstatus"));
			result.setObject(map);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查找用户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}	
	
	
	/**
	 * 忘记密码
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("v1/forgetPwd")
	public void forgetPwd(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		String username = memberDtoInput.getAccount();
		String verifyCode = request.getParameter("verifyCode");
		if(StringUtils.isBlank(username)){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(!Validator.isMobile(username)){
			setResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
			return;
		}
		
		if(StringUtils.isBlank(verifyCode)){
			setResult(result, ErrorCodeEnum.VERIFYCODE_IS_EMPTY, request, response);
			return;
		}
		
		try {
			
			//判断用户是否存在, 根据手机号查询
			MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(username);
			//判断用户是否存在
			if (memberDTO==null){
				setResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			if (memberDTO.getLevel()!=4){
				setEncodeResult(result, ErrorCodeEnum.GYS_ACCOUNT_ONLY_PERMIT, request, response);
				return;
			}			
			logger.info("手机号: " + username +  ", 提交验证码: " + verifyCode);
			
			//比较校验码
			ErrorCodeEnum verifyResult = CommonUtils.isVerifyMsgInSeconds(username, 300, redisClient);
			if(ErrorCodeEnum.SUCCESS != verifyResult){ 
					setResult(result, verifyResult, request, response);
					return;
			}
			
			if(!CommonUtils.isVerifyCodeMatched(username, verifyCode, redisClient)){
				setResult(result, ErrorCodeEnum.VERIFYCODE_INCORRECT, request, response);
				return;
			}
			
			//验证码已校验正确，移除
			SyncUtil.removeVerifyCode(username, redisClient);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]调用忘记密码异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("v1/getVerifyCode")
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		String mobile = memberDtoInput.getMobile();
		String typeStr = request.getParameter("type");
		String encryptStr = request.getParameter("encryptStr");
		
		if(StringUtils.isBlank(mobile)){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(!Validator.isMobile(mobile)){
			setResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
			return;
		}
		
		if(StringUtils.isBlank(typeStr)){
			setResult(result, ErrorCodeEnum.TYPE_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(encryptStr)){
			setResult(result, ErrorCodeEnum.MOBILE_ENCRYPT_IS_NULL, request, response);
			return;
		}
		
		int type = Integer.parseInt(typeStr);
		if(type != 0 && type != 1){
			setResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
			return;
		}
		
		try {
			if(!CommonUtils.verifyMobile(mobile, encryptStr)){
				setResult(result, ErrorCodeEnum.MOBILE_IS_NOT_MATCHED, request, response);
				return;
			}
			
			//如果验证码已存在并且还在60秒内, 默认返回成功
			if(ErrorCodeEnum.SUCCESS == CommonUtils.isVerifyMsgInSeconds(mobile, 60, redisClient)){
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			
			//判断用户是否存在, 根据手机号查询
			MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(mobile);
			if(type==1){//已经注册
				if(memberDTO==null){
					setResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
					return;
				}
				
				if(memberDTO.getLevel()!=4){
					setResult(result, ErrorCodeEnum.GYS_ACCOUNT_ONLY_PERMIT, request, response);
					return;
				}

			}
			
			//发送验证码
			String verifycode = CommonUtils.genVerfiyCode(true, 6);
			logger.debug("手机: "+mobile+",验证码: " + verifycode);
			boolean isExistedAcc = memberToolService.isAccExisted(mobile);
			
			//取redis缓存,获取通道号
			String channel = "";
			try{
				Object obj = redisClient.get("GDSMS_CHANNEL");
				System.out.println("redis channel:###############"+ obj);
				channel = obj==null? Constant.Alidayu.DEFAULTNO:obj.toString();
				System.out.println("redis channel:###############"+ channel);
			}catch(Exception e){
				//处理redis服务器异常
				e.printStackTrace();
				logger.info("获取redis 消息通道出错!");
			}
			String content=null;
			if(type == 0){ //注册操作
				if (isExistedAcc){
					setResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
					return;
				}
				if(Constant.Alidayu.REDISTYPE.equals(channel)){
					content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE3,verifycode);
					
				}else{
					content=MessageTemplateEnum.REGISTER.getTemplate().replace("{P}", verifycode);
				}
				//CommonUtils.sendMsg(channel,MessageTemplateEnum.REGISTER.getTemplate().replace("{P}", verifycode), mobile);
			}else{  //忘记密码操作
				if (!isExistedAcc){
					setResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
					return;
				}
				if(Constant.Alidayu.REDISTYPE.equals(channel)){
					content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE1,verifycode);
					
				}else{
					content=MessageTemplateEnum.FORGET_PWD.getTemplate().replace("{P}", verifycode);
				}
			//	CommonUtils.sendMsg(channel,MessageTemplateEnum.FORGET_PWD.getTemplate().replace("{P}", verifycode), mobile);
			}
			logger.info("####################content: "+content);
			CommonUtils.sendMsg(channel,content, mobile);
			//保存到缓存中
			SyncUtil.setVerifyCode(mobile, verifycode, redisClient);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch (Exception e) {
			logger.warn("[ERROR]获取验证异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/addComplaint")
	public void addComplaint(HttpServletRequest request,HttpServletResponse response, ComplaintEntity complaintEntity) throws UnsupportedEncodingException {
		ObjectResult result = new ObjectResult();
		if(com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(complaintEntity.getContent())){
			setResult(result, ErrorCodeEnum.COMPLAINT_CONTENT_IS_NULL, request, response);
			return;
		}
		if(complaintEntity.getContent().length() > 500){
			setResult(result, ErrorCodeEnum.COMPLAINT_CONTENT_OVER_LENGTH, request, response);
			return;
		}
		if(com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(complaintEntity.getMember())){
			setResult(result, ErrorCodeEnum.COMPLAINT_MEMBER_IS_NULL,
					request, response);
			return;
		}
		if(com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(complaintEntity.getSource())){
			setResult(result, ErrorCodeEnum.COMPLAINT_SOURCE_IS_NULL,
					request, response);
			return;
		}
		try {
			complaintEntity.setCreateTime(new Date());
			complaintEntity.setState("0");
			Long l = complaintToolService.addComplaint(complaintEntity);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("result", l);
			result.setObject(map);
			setResult(result, ErrorCodeEnum.SUCCESS, request,
					response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.warn("[ERROR]添加投诉建议异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}	
	
	/**
	 * 参数加密
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("v0630/login")
	public void loginSec(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String username = (String)GSONUtils.getJsonValueStr(jsonStr, "account");
			String password = (String)GSONUtils.getJsonValueStr(jsonStr, "password");
			String deviceTokens = (String)GSONUtils.getJsonValueStr(jsonStr, "device_tokens");
			if(StringUtils.isBlank(username)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}
			
			if(StringUtils.isBlank(password)){
				setEncodeResult(result, ErrorCodeEnum.PASSWORD_IS_EMPTY, request, response);
				return;
			}
			
			if(!Validator.isMobile(username)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
				return;
			}			
			logger.debug("User info: username: " + username + ", password: " + password);
			//判断用户是否存在, 根据手机号查询
			MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(username);
			if (null == memberDTO){
				//根据账号查询
				memberDTO = memberToolService.getByAccount(username);
				if(null == memberDTO){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
					return;
				}
			}
			//判断用户是否禁用
			String status = memberDTO.getStatus();
			if(!"1".equals(status)){
				setEncodeResult(result, ErrorCodeEnum.ACCOUNT_IS_DISABLE, request, response);
				return;
			}
			
			//判断密码是否正确
			if(!password.equals(memberDTO.getPassword())){
				setEncodeResult(result, ErrorCodeEnum.PASSWORD_ERROR, request, response);
				return;
			}
			
			//判断是否供应商的用户
			if(memberDTO.getLevel() != 4){
				setEncodeResult(result, ErrorCodeEnum.GYS_ACCOUNT_ONLY_PERMIT, request, response);
				return;
			}			
			
			//更新deviceTokens
			MemberBaseinfoDTO memberUpdateDTO = new MemberBaseinfoDTO();
			memberUpdateDTO.setMemberId(memberDTO.getMemberId());
			memberUpdateDTO.setDevice_tokens(deviceTokens);
			memberToolService.updateMember(memberUpdateDTO);
			
			MemberCertifiDTO dto = memberCertifiApiService.getByUserId(Long.valueOf(memberDTO.getMemberId()));
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("certificationType", dto==null?"-1":dto.getCertificationType());
			map.put("certificationStatus", dto==null?"-1":dto.getStatus());
			map.put("memberId", memberDTO.getMemberId());
			map.put("userType", memberDTO.getUserType()==null ? 0 : memberDTO.getUserType());
			map.put("level", memberDTO.getLevel());
			map.put("realName", memberDTO.getRealName()==null?"":memberDTO.getRealName());
			map.put("sex", memberDTO.getSex()==null?"":memberDTO.getSex());
			map.put("companyName", memberDTO.getCompanyName()==null?"":memberDTO.getCompanyName());
			map.put("companyContact", memberDTO.getCompanyContact()==null?"":memberDTO.getCompanyContact());
			if(memberDTO.getCcityId()!=null && !"".equals(memberDTO.getCcityId())){
				AreaDTO areaDto= areaToolService.getByAreaId(memberDTO.getCcityId());
				if(areaDto!=null){
					map.put("ccityId", areaDto.getAreaID());

					map.put("ccityString", areaDto.getArea());
				}
				
			}else{
				map.put("ccityId", "");
				map.put("ccityString", "");
			}
			//获取商铺信息
			BusinessBaseinfoDTO shopDto = businessBaseinfoToolService.getByUserId(memberDTO.getMemberId().toString());
			if(shopDto == null){
				map.put("marketId", "");
				map.put("businessId","");
				map.put("shopName", "");
				map.put("cityId", "");
				map.put("provinceId", "");
				map.put("areaId", "");
				map.put("address", "");
			}else{
				map.put("marketId", shopDto.getMarketId());
				map.put("businessId", shopDto.getBusinessId());
				map.put("shopName", shopDto.getShopsName());
				map.put("cityId", shopDto.getCityId() == null ? "" : shopDto.getCityId());
				map.put("provinceId", shopDto.getProvinceId() == null ? "" : shopDto.getProvinceId());
				map.put("areaId", shopDto.getAreaId() == null ? "" : shopDto.getAreaId());
				map.put("address", shopDto.getProvince() + shopDto.getCity()+ shopDto.getArea());
			}				

			//设置金牌会员信息
			String memberGrade = memberDTO.getMemberGrade() == null ? "0" : memberDTO.getMemberGrade().toString();
			map.put("memberGrade", memberGrade);
			if("1".equals(memberGrade)){
				map.put("validTime", DateTimeUtils.formatDate(memberDTO.getValidTime(), DateUtil.DATE_FORMAT_DATEONLY));
				map.put("expireTime", DateTimeUtils.formatDate(memberDTO.getExpireTime(), DateUtil.DATE_FORMAT_DATEONLY));
			}
			
			//获取认证信息
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberDTO.getMemberId());
			Map<String, Object> res = certifBaseToolService.getStatusCombination(params);
			map.put("cpstatus", res.get("cpstatus"));
			map.put("ccstatus", res.get("ccstatus"));
			map.put("cbstatus", 0);
			map.put("comstatus", res.get("comstatus"));
			
			result.setObject(map);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			//获取农速通token
			new Thread(new GetNstTokenThread(nstApiCommonService, memberDTO.getMemberId().intValue())).start();
		} catch (Exception e) {
			logger.warn("[ERROR]查找用户信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}		

	/**
	 * 忘记密码——0630参数加密
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("v0630/forgetPwd")
	public void forgetPwdSec(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String username = (String)GSONUtils.getJsonValueStr(jsonStr, "account");
			String verifyCode = (String)GSONUtils.getJsonValueStr(jsonStr, "verifyCode");
			if(StringUtils.isBlank(username)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}
			
			if(!Validator.isMobile(username)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
				return;
			}
			
			if(StringUtils.isBlank(verifyCode)){
				setEncodeResult(result, ErrorCodeEnum.VERIFYCODE_IS_EMPTY, request, response);
				return;
			}
			
			//判断用户是否存在, 根据手机号查询
			MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(username);
			//判断用户是否存在
			if (memberDTO==null){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			if (memberDTO.getLevel() != 4){
				setEncodeResult(result, ErrorCodeEnum.GYS_ACCOUNT_ONLY_PERMIT, request, response);
				return;
			}			
			logger.info("手机号: " + username +  ", 提交验证码: " + verifyCode);
			
			//比较校验码
			ErrorCodeEnum verifyResult = CommonUtils.isVerifyMsgInSeconds(username, 300, redisClient);
			if(ErrorCodeEnum.SUCCESS != verifyResult){ 
				setEncodeResult(result, verifyResult, request, response);
					return;
			}
			
			if(!CommonUtils.isVerifyCodeMatched(username, verifyCode, redisClient)){
				setEncodeResult(result, ErrorCodeEnum.VERIFYCODE_INCORRECT, request, response);
				return;
			}
			
			//验证码已校验正确，移除
			SyncUtil.removeVerifyCode(username, redisClient);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]调用忘记密码异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 参数需要加密
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("v0630/getVerifyCode")
	public void getVerifyCodeSec(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		
		try {
			String jsonStr = getParamDecodeStr(request);
			String mobile = (String)GSONUtils.getJsonValueStr(jsonStr, "mobile");
			String typeStr = (String)GSONUtils.getJsonValueStr(jsonStr, "type");
			String encryptStr = (String)GSONUtils.getJsonValueStr(jsonStr, "encryptStr");
			
			if(StringUtils.isBlank(mobile)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}
			
			if(!Validator.isMobile(mobile)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
				return;
			}
			
			if(StringUtils.isBlank(typeStr)){
				setEncodeResult(result, ErrorCodeEnum.TYPE_IS_EMPTY, request, response);
				return;
			}
			
			if(StringUtils.isBlank(encryptStr)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_ENCRYPT_IS_NULL, request, response);
				return;
			}
			
			int type = Integer.parseInt(typeStr);
			if(type != 0 && type != 1){
				setEncodeResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
				return;
			}			
			
			
			if(!CommonUtils.verifyMobile(mobile, encryptStr)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_NOT_MATCHED, request, response);
				return;
			}
			
			//如果验证码已存在并且还在60秒内, 默认返回成功
			if(ErrorCodeEnum.SUCCESS == CommonUtils.isVerifyMsgInSeconds(mobile, 60, redisClient)){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			
			//判断用户是否存在, 根据手机号查询
			MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(mobile);
			if(type==1){//已经注册
				if(memberDTO==null){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
					return;
				}
				
				if(memberDTO.getLevel() != 4){
					setEncodeResult(result, ErrorCodeEnum.GYS_ACCOUNT_ONLY_PERMIT, request, response);
					return;
				}

			}

			
			//发送验证码
			String verifycode = CommonUtils.genVerfiyCode(true, 6);
			logger.debug("手机: "+mobile+",验证码: " + verifycode);
			boolean isExistedAcc = memberToolService.isAccExisted(mobile);
			
			//取redis缓存,获取通道号
			String channel = "";
			try{
				Object obj = redisClient.get("GDSMS_CHANNEL");
				System.out.println("redis channel:###############"+ obj);
				channel = obj==null? Constant.Alidayu.DEFAULTNO:obj.toString();
				System.out.println("redis channel:###############"+ channel);
			}catch(Exception e){
				//处理redis服务器异常
				e.printStackTrace();
				logger.info("获取redis 消息通道出错!");
			}
			String content=null;
			if(type == 0){ //注册操作
				if (isExistedAcc){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
					return;
				}
				if(Constant.Alidayu.REDISTYPE.equals(channel)){
					content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE3,verifycode);
					
				}else{
					content=MessageTemplateEnum.REGISTER.getTemplate().replace("{P}", verifycode);
				}
				//CommonUtils.sendMsg(channel,MessageTemplateEnum.REGISTER.getTemplate().replace("{P}", verifycode), mobile);
			}else{  //忘记密码操作
				if (!isExistedAcc){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
					return;
				}
				if(Constant.Alidayu.REDISTYPE.equals(channel)){
					content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE1,verifycode);
					
				}else{
					content=MessageTemplateEnum.FORGET_PWD.getTemplate().replace("{P}", verifycode);
				}
			//	CommonUtils.sendMsg(channel,MessageTemplateEnum.FORGET_PWD.getTemplate().replace("{P}", verifycode), mobile);
			}
			logger.info("####################content: "+content);
			CommonUtils.sendMsg(channel,content, mobile);
			//保存到缓存中
			SyncUtil.setVerifyCode(mobile, verifycode, redisClient);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch (Exception e) {
			logger.warn("[ERROR]获取验证异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}	
}
