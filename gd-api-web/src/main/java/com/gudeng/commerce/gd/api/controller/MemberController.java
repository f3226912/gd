package com.gudeng.commerce.gd.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MarketToolService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductCategoryToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.SyncUtil;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBrowseMarketHistoryDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;
import com.gudeng.commerce.gd.exception.BusinessException;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MemberLoginLogUtil;

/**
 * 
 * 功能描述：会员信息控制中心
 */
@Controller
@RequestMapping("member")
public class MemberController extends GDAPIBaseController {
	/** 记录日志־ */
	private static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	public MemberToolService memberToolService;
	@Autowired
	private MarketToolService marketToolService;
	@Autowired
	private AreaToolService areaToolService;

	@Autowired
	private MemberCertifiApiService memberCertifiApiService;
	@Autowired
	public UsercollectProductCategoryToolService usercollectProductCategoryToolService;
	@Autowired
	public MemberAddressApiService memberAddressApiService;
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService; 
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	@Autowired
	public GdProperties gdProperties;
	
	@RequestMapping("/login")
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
			
			//更新deviceTokens
			memberDTO.setDevice_tokens(deviceTokens);
			memberToolService.updateMember(memberDTO);
			
			MemberLoginLogUtil.insertLoginLog(memberDTO, "4", request);

			Map<String, Object> map = new HashMap<String, Object>();
			//授权码
//			String sid = System.currentTimeMillis()+"";
			map.put("sid", "88888888");
			map.put("memberId", memberDTO.getMemberId());
			//add by yanghaoyu ,新增加餐宿,userType,如果当前用户是 公司,还是个人
			if(memberDTO.getUserType()==null){
				map.put("userType", 0);
			}else{
				map.put("userType", memberDTO.getUserType());
			}
			map.put("level", memberDTO.getLevel());
			map.put("realName", memberDTO.getRealName());
			map.put("sex", memberDTO.getSex());
			map.put("companyName", memberDTO.getCompanyName());
			map.put("companyContact", memberDTO.getCompanyContact());
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
			

			result.setObject(map);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查找用户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		String username = memberDtoInput.getAccount();
		String verifyCode = request.getParameter("verifyCode");
		if(StringUtils.isBlank(username)){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(verifyCode)){
			setResult(result, ErrorCodeEnum.VERIFYCODE_IS_EMPTY, request, response);
			return;
		}
		
		if(!Validator.isMobile(username)){
			setResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
			return;
		}
		
		try {
			logger.debug("User info: username: " + username + ", verifyCode: " + verifyCode);
			//判断用户是否存在
			if (memberToolService.isAccExisted(username)){
				setResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
				return;
			}
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
			logger.warn("[ERROR]用户注册异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		Long id = memberDtoInput.getMemberId();
		if(id == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		
		try {
			MemberBaseinfoDTO memberDTO = memberToolService.getById(id + "");
			//判断用户是否存在
			if (null == memberDTO){
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}
			
			//更改真实姓名
			String realName = memberDtoInput.getRealName();
			
			if(realName != null && realName.length() > 14) {
				setResult(result, ErrorCodeEnum.USERNAME_OVER_LENGTH, request, response);
				return;
			}
			
			if(!StringUtils.isBlank(realName)){
				memberDTO.setRealName(realName);;
			}
			//更改昵称
			String nickName = memberDtoInput.getNickName();
			if(!StringUtils.isBlank(nickName)){
				memberDTO.setNickName(nickName);
			}
			//更改类型
			Integer level = memberDtoInput.getLevel();
			if(level != null){
				memberDTO.setLevel(level);
			}
			//省份id
			Long provinceId = memberDtoInput.getProvinceId();
			if(provinceId != null){
				memberDTO.setProvinceId(provinceId);
			}
			//城市id
			Long cityId = memberDtoInput.getCityId();
			if(cityId != null){
				memberDTO.setCityId(cityId);
			}
			//区县id
			Long areaId = memberDtoInput.getAreaId();
			if(areaId != null){
				memberDTO.setAreaId(areaId);
			}
			//详细地址
			String address = memberDtoInput.getAddress();
			if(StringUtils.isNotBlank(address)){
				memberDTO.setAddress(address);
			}
			memberDTO.setUpdateTime_string(DateTimeUtils.getTimeString());
			memberDTO.setUpdateUserId(memberDTO.getMemberId()+"");
			memberToolService.updateMember(memberDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]更新用户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/forgetPwd")
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
			//判断用户是否存在
			if (!memberToolService.isAccExisted(username)){
				setResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
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
	
	@RequestMapping("/setPwd")
	public void setPwd(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		String username = memberDtoInput.getAccount();
		String password = memberDtoInput.getPassword();
		Integer level = memberDtoInput.getLevel();
		String typeStr = request.getParameter("type");
		if(StringUtils.isBlank(username)){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(!Validator.isMobile(username)){
			setResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
			return;
		}
		
		if(StringUtils.isBlank(password)){
			setResult(result, ErrorCodeEnum.PASSWORD_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(typeStr)){
			setResult(result, ErrorCodeEnum.TYPE_IS_EMPTY, request, response);
			return;
		}
		
		int type = Integer.parseInt(typeStr);
		if(type != 0 && type != 1){
			setResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
			return;
		}
		
		if(type == 0 && level == null){
			setResult(result, ErrorCodeEnum.USER_LEVEL_IS_EMPTY, request, response);
			return;
		}
		
		try {
			logger.debug("User info: username: " + username + ", password: " + password);
			
			if(type == 0){ //注册操作
				if (memberToolService.isAccExisted(username)){
					setResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
					return;
				}
				//0管理后台创建用户，1web前端注册用户，2农速通注册用户，3农商友-买家版注册用户，4农商友-卖家版注册用户，5门岗添加用户
				String regetype = memberDtoInput.getRegetype();
				
				if(StringUtils.isBlank(regetype)){
					switch(level){//会员类别（1谷登农批，2农速通，3农批宝，4产地供应商，5门岗，余待补）
						case 2: regetype = "2"; break;
						case 3: regetype = "3"; break;
						default: regetype = "3"; 
					}
				}
				
				if("2".equals(regetype)){
					setResult(result, ErrorCodeEnum.NST_APP_IS_NOT_USED, request, response);
					return;
				}

				//插入新用户
				MemberBaseinfoDTO memberDTO = new MemberBaseinfoDTO();
				memberDTO.setAccount(username);
				memberDTO.setPassword(password);
				memberDTO.setLevel(level);
				memberDTO.setRegetype(regetype);
				memberDTO.setStatus("1");
				memberDTO.setIp(request.getRemoteAddr());
				memberDTO.setCreateTime_string(DateTimeUtils.getTimeString());;
				memberDTO.setMobile(username);
				//2016-10-20 系统生成账户名
				String account="gd"+UUID.randomUUID().toString().replace("-", "").toUpperCase(); //由系统生成帐号：gd+UUID
				memberDTO.setAccount(account);
				memberDTO.setMemberGrade(0);
				memberToolService.addMember(memberDTO);
			}else{  //修改密码操作
				//检查手机号是否存在
				MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(username);
				if (null == memberDTO){
					//检查账号是否存在
					memberDTO = memberToolService.getByAccount(username);
					if(null == memberDTO){
						setResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
						return;
					}
				}
				//更改密码
				memberDTO.setPassword(password);
				memberDTO.setUpdateTime_string(DateTimeUtils.getTimeString());;
				memberDTO.setUpdateUserId(memberDTO.getMemberId()+"");
				memberToolService.updateMember(memberDTO);
			}
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]设置密码异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getVerifyCode")
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
	
	@RequestMapping("/checkVerifyCode")
	public void checkVerifyCode(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		String username = memberDtoInput.getAccount();
		String verifyCode = request.getParameter("verifyCode");
		String typeStr = request.getParameter("type");
		if(StringUtils.isBlank(username)){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(verifyCode)){
			setResult(result, ErrorCodeEnum.VERIFYCODE_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(typeStr)){
			setResult(result, ErrorCodeEnum.TYPE_IS_EMPTY, request, response);
			return;
		}
		
		try {
			int type = Integer.parseInt(typeStr);
			if(type != 0 && type != 1){
				setResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
				return;
			}
			
			logger.debug("User info: username: " + username + ", verifyCode: " + verifyCode);
			boolean isExistedAcc = memberToolService.isAccExisted(username);
			//判断用户是否存在
			if(type == 0){            //注册操作
				if (isExistedAcc){
					setResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
					return;
			}
			}else{                    //忘记密码操作
				if (!isExistedAcc){
					setResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
					return;
				}
			}
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
			logger.warn("[ERROR]校验验证码异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getInfo")
	public void getInfo(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		Long memberId = memberDtoInput.getMemberId();
		String cityName = memberDtoInput.getCityName();
		if(memberId == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		try{
			MemberBaseinfoDTO memberInfo = memberToolService.getById(memberId+"");
			if (null == memberInfo){
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}
			
			List<UsercollectProductCategoryDTO> list= usercollectProductCategoryToolService.getFocusCategory(memberId);
			if(list == null || list.size() == 0){
				memberInfo.setHasFocusCategory(0);
			}else{
				memberInfo.setHasFocusCategory(1);
				memberInfo.setFocusCategoryList(list);
			}
			
			MemberBrowseMarketHistoryDTO marketBrowseDto = memberToolService.getBrowseHistoryByMemberId(memberId);
			if(marketBrowseDto == null){
				//先定位,再保存用户浏览市场记录
				AreaDTO area = null;
				if(StringUtils.isNotBlank(cityName)){
					area = areaToolService.getByAreaName(cityName);
				}
				
				MarketDTO dto = null;
				//默认白沙洲
				if(area == null){
					dto = marketToolService.getByCondition("420000", "420100", "2").get(0);
				}else{
					//查自营的市场
					List<MarketDTO> marketList = marketToolService.getByCondition(area.getFather(), area.getAreaID(), "2");
					if(marketList == null || marketList.size() == 0){
						//查用户添加的市场
						marketList = marketToolService.getByCondition(area.getFather(), area.getAreaID(), "3");
					}
					if(marketList != null && marketList.size() > 0){
						dto = marketList.get(0);
					}else{
						dto = marketToolService.getByCondition("420000", "420100", "2").get(0);
					}
				}
				//设置用户的浏览市场记录
				memberInfo.setMarketId(dto.getId());
				memberInfo.setMarketName(dto.getMarketName());
				//新增用户浏览记录
				marketBrowseDto = new MemberBrowseMarketHistoryDTO();
				marketBrowseDto.setMemberId(memberId);
				marketBrowseDto.setMarketId(dto.getId());
				marketBrowseDto.setBrowseCount(1);
				marketBrowseDto.setBrowseTime(new Date());
				marketBrowseDto.setBrowseTimeStr(DateTimeUtils.getTimeString());
				memberToolService.addBrowseHistory(marketBrowseDto);
			}else{
				//设置用户的浏览市场记录
				memberInfo.setMarketId(marketBrowseDto.getMarketId());
				memberInfo.setMarketName(marketBrowseDto.getMarketName());
			}
			
			List<ReMemberMarketDTO>  reMarketList = marketToolService.getReMemberMarket(memberId);
			//yanghaoyu,如果UserType不为空,切为2 及用户为公司用户,需要查询出来公司名称
//			if(memberInfo.getUserType()!=null){
//				if(memberInfo.getUserType()==2){
//				//	MemberBaseinfoDTO mb= memberToolService.getCompanyName(memberId);
//					memberInfo.setCompanyName("");
//				}
//			}
			BusinessBaseinfoDTO businessInfo = businessBaseinfoToolService.getByUserId(memberId+"");
			if(businessInfo != null && "2".equals(businessInfo.getMarketType())){
				memberInfo.setMarketId(Long.parseLong(businessInfo.getMarketId()));
				memberInfo.setMarketName(businessInfo.getMarketName());
			}
			
			memberInfo.setReMarketList(reMarketList);
			memberInfo.setPassword("");
			result.setObject(memberInfo);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch (Exception e) {
			logger.warn("[ERROR]获取会员信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 农速宝APP,个人中心,当前用户在个人中心需要维护姓名,性别
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/updateAppMember")
	public void updateAppMember(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		Long id = memberDtoInput.getMemberId();
		String actityNo=memberDtoInput.getActityNo();
		String nst_CompanyAddress=memberDtoInput.getNst_CompanyAddress();
		///memberToolService.getById(id)
		
		MemberBaseinfoDTO mt = null;
		if(id == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		//判断用户是否存在, 如果不存在,则看是否是工作人员提供的推荐码,直接送积分,如果不是,提醒用户
		 int flag =0;
		if(memberDtoInput.getActityNo()!=null&&!"".equals(memberDtoInput.getActityNo())){
			
			try {
				//APP前端输入的
				mt = memberToolService.getByMobile(actityNo);
				if (null == mt ){
						flag=2;
				}else{
					flag=1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
		try {
			MemberBaseinfoDTO memberDTO = memberToolService.getById(id + "");
			//判断用户是否存在
			if (null == memberDTO){
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}
			
			String companyName ="";
			
			//更改真名,进行判断是否传空
			String realName ="";
			if(!StringUtils.isBlank(memberDtoInput.getRealName())){
				if(!StringUtils.isBlank(memberDtoInput.getApp())){
					if("IOS".equals(memberDtoInput.getApp())){
						realName =  new String(memberDtoInput.getRealName().getBytes("utf-8"),"utf-8");
					}
			    }else{
			    	realName =  memberDtoInput.getRealName();
			    }
				 
			}
			if(!StringUtils.isBlank(realName)){
				
			  	Pattern pattern = Pattern.compile("^[A-Za-z\u4e00-\u9fa5]+$");
		    	Matcher matcher = pattern.matcher(realName);
		    	if (!matcher.matches()) {
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("姓名/联系人请输入中文或者英文." );
					renderJson(result, request, response);
					return;
		    	}
				memberDTO.setRealName(realName);
			}
				
			if(!StringUtils.isBlank(memberDtoInput.getCompanyName())){
				if(!StringUtils.isBlank(memberDtoInput.getApp())){
					if("IOS".equals(memberDtoInput.getApp())){
						companyName =  new String(memberDtoInput.getCompanyName().getBytes("utf-8"),"utf-8");
					}
			    	}else{
			    		companyName =  memberDtoInput.getCompanyName();
			    	}
				 
			}
			if(!StringUtils.isBlank(companyName)){
				memberDTO.setCompanyName(companyName);
			}
			//cCityS是APP端传过来的字符串,做非空判断,再转Long类型
			if(memberDtoInput.getCcityIds()!=null&&!"".equals(memberDtoInput.getCcityIds())){
				memberDTO.setCcityId(Long.valueOf(memberDtoInput.getCcityIds()));
			}else{
				if(memberDtoInput.getCcityId()!=null&&!"".equals(memberDtoInput.getCcityId())){
					memberDTO.setCcityId(Long.valueOf(memberDtoInput.getCcityId()));
				}
				
			}
				
				Integer  sex = memberDtoInput.getSex();
				memberDTO.setSex(sex);
			
							
				String companyContact ="";
				if(!StringUtils.isBlank(memberDtoInput.getCompanyContact())){
			    	if(!StringUtils.isBlank(memberDtoInput.getApp())){
				    	if("IOS".equals(memberDtoInput.getApp())){
				    		companyContact =  new String(memberDtoInput.getCompanyContact().getBytes("utf-8"),"utf-8");
				    	}
				    	}else{
				    		companyContact =  memberDtoInput.getCompanyContact();
				    	}
					 
				}
				if(!StringUtils.isBlank(companyContact)){
				  	Pattern pattern = Pattern.compile("^[A-Za-z\u4e00-\u9fa5]+$");
			    	Matcher matcher = pattern.matcher(companyContact);
			    	if (!matcher.matches()) {
						result.setObject(null);
						result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
						result.setMsg("请输入中文和英文" );
						renderJson(result, request, response);
						return;
			    	}
					memberDTO.setCompanyContact(companyContact);
				}
				//userType前台APP传的是字符按,做非空判断
				if(memberDtoInput.getUserTypes()!=null&&!"".equals(memberDtoInput.getUserTypes())){
					memberDTO.setUserType(Integer.parseInt(memberDtoInput.getUserTypes()));
				}else{
					memberDTO.setUserType(memberDtoInput.getUserType());
				}
				
			
			//绑定推荐人 V1.4版本,增加积分系统
			Map<String,Object> map = new HashMap<String,Object>();
			
			Map<String,Object> map2 = new HashMap<String,Object>();
			//type  ,定义为3 ,3是绑定推荐人和被推荐的人的关系
			if(flag==1){
				map2.put("id",id);
				map2.put("edId",mt.getMemberId());
				int i=memberToolService.getRecordByMemberInfo(map2);
				if(i>0){
					result.setObject(null);
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					result.setMsg("您的账号不能被重复推荐" );
					renderJson(result, request, response);
					return;
				}
				
				map.put("type", 3);
				//当前登录人的id是被推荐
		        map.put("memberId_ed", memberDtoInput.getMemberId());
		        //推荐人的Id
		        map.put("memberId", mt.getMemberId());
		        map.put("createUserId", memberDtoInput.getMemberId());
		        map.put("updateUserId", memberDtoInput.getMemberId());
		        map.put("description", "推荐人绑定新用户,获取推荐绑定关系");	
		        memberToolService.getlink(map);
			}
			if(flag==2){
				map.put("type", 3);
				//当前登录人的id是被推荐
		        map.put("memberId_ed", memberDtoInput.getMemberId());
		        map.put("createUserId", memberDtoInput.getMemberId());
		        map.put("updateUserId", memberDtoInput.getMemberId());
		        map.put("description", "活动推广,使用推荐码获取积分,推荐码为"+actityNo);	
		       // memberToolService.getlink(map);
				map.put("a", actityNo);
				map.put("b", memberDtoInput.getMemberId());
		       int msg= memberToolService.addInt(map);
		       if(msg==2){
						setResult(result, ErrorCodeEnum.RECOMMEND_CODE_INCRECT, request, response);
						return;
		       }
		       if(msg==3){
						setResult(result, ErrorCodeEnum.RECOMMEND_CODE_ALREADY_USED, request, response);
						return;
		       }
			}

	        memberDTO.setAndupurl(memberDtoInput.getAndupurl());  
	        memberDTO.setNstCreateTimeFlag(memberDtoInput.getNstCreateTimeFlag()); 
	        //增加了一个参数,nstCreatTimeFlag ,标记是第一次登陆的界面,所以可以通知后台进行  nst创建时间, 标记是1
	        memberToolService.updateMember(memberDTO);
			
			//一位完善用户信息 ,用户表示有数据了,所以是update,但是认证表,第一次是没有信息的,所以要做判断
			 if(memberDTO.getUserType()!=null){
				 
			
			 if(memberDTO.getUserType()==2){
				 int mc= memberCertifiApiService.getMcId(id);
				 MemberCertifiDTO mcd= new MemberCertifiDTO();
				 mcd.setMemberId(Long.valueOf(id));
				 mcd.setType(memberDTO.getUserType()+"");
				 mcd.setCompanyName(companyName);
				 mcd.setLinkMan(companyContact);
				 mcd.setNst_CompanyAddress(nst_CompanyAddress);
				 
				 //取的手机是当前登录用户的  手机号码
				 mcd.setMobile(memberDTO.getMobile());	
			 if(mc<1){
                 memberCertifiApiService.addMemberCertifiDTO(mcd);
			 }else{
				 mcd.setCertifiId(memberCertifiApiService.getByUserId(id).getCertifiId());
				 memberCertifiApiService.updateMemberCertifiDTO(mcd);
			 }
			 }
			 }
			result.setObject(null);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]更新用户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 农速宝APP,个人中心,当前用户在个人中心需要维护姓名,性别
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/getContacts")
	public void getContacts(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		Long id = memberDtoInput.getMemberId();		
		
		try {		
			MemberBaseinfoDTO memberDTO = memberToolService.getContacts(id + "");
			//判断用户是否存在
			if (null == memberDTO){
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}
			
			//String status=memberCertifiApiService.queryCertStatus(Long.valueOf(id));
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
			//修改逻辑,个人中心的认证状态,只显示农速通的认证状态,否则,驳回原因无法显示
			if(memberDTO.getNstStatus()!=null&&!"".equals(memberDTO.getNstStatus())){
				memberDTO.setCertificationType(memberDTO.getNstStatus());
			}else{
				memberDTO.setCertificationType("-1");
			}
	        	
	        	

	        
			
			memberDTO.setAndupurl(imageHost+memberDTO.getAndupurl());
			if(memberDTO.getCcityId()!=null && !"".equals(memberDTO.getCcityId())){
				AreaDTO areaDto= areaToolService.getByAreaId(memberDTO.getCcityId());
				if(areaDto!=null){
					//memberDTO.setCcityIdareaDto.getAreaID());

					memberDTO.setCityName( areaDto.getArea());
				}
				
			}else{
							memberDTO.setCityName("");
			}
		     
			result.setObject(memberDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]更新用户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL,request, response);
		}
	}
	
	@RequestMapping("/batAddFocus")
	public void batAddFocus(HttpServletRequest request,
			HttpServletResponse response,MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		try {
			String a="D001";
			int b=244;
			Map<String, Object> p=new HashMap<String, Object>();
			p.put("a",a);
			p.put("b", b);
	       // memberToolService.addInt(p);
			memberToolService.addInt(p);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} 
		catch (BusinessException e) {
			logger.info("添加关注单品异常", e);
		} catch (Exception e) {
			logger.info("添加关注单品异常", e);
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 农速通个人中心,获取推送信息列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPushMessage")
	public void getPushMessage(HttpServletRequest request,
			HttpServletResponse response,MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		try {
			//
			Map<String, Object> map = new HashMap<String, Object>();
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total =  memberToolService.getPushMessageCount(memberDtoInput);
			memberDtoInput.setStartRow(Integer.parseInt(map.get(START_ROW).toString()));
			memberDtoInput.setEndRow(Integer.parseInt(map.get(END_ROW).toString()));

			List<PushNstMessageDTO> list = memberToolService.getPushMessage(memberDtoInput);
			//result.setObject(list);
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(list);
			result.setObject(pageDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} 
		catch (BusinessException e) {
			logger.info("获取推送信息第一列表异常", e);
		} catch (Exception e) {
			logger.info("获取推送信息第一列表异常", e);
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 个人中心,推送信息第二页,  推荐的货源列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPushMessageInfo")
	public void getPushMessageInfo(HttpServletRequest request,
			HttpServletResponse response,PushNstMessageDTO pushNstMessageDTO) {
		ObjectResult result = new ObjectResult();
		try {
			List<PushNstMessageInfoDTO> list = memberToolService.getPushMessageInfo(pushNstMessageDTO);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(list.get(i).getMemberId())));
				MemberBaseinfoDTO md=memberToolService.getById(list.get(i).getMemberId()+"");
		          if(md.getCompanyContact()!=null){
		        	  list.get(i).setContact(md.getCompanyContact());
		          }
		          if(md.getCompanyMobile()!=null){
		        	  list.get(i).setMobile(md.getCompanyMobile());
		          }
					
					
		
				if(list.get(i).getF_provinceId()!=null&&list.get(i).getF_provinceId()>1){
					list.get(i).setF_provinceIdString(memberAddressApiService.getAreaString(list.get(i).getF_provinceId()));  
					}
					if(list.get(i).getF_cityId()!=null&&list.get(i).getF_cityId()>1){
						list.get(i).setF_cityIdString(memberAddressApiService.getAreaString(list.get(i).getF_cityId()));
					}
					if(list.get(i).getF_areaId()!=null&&list.get(i).getF_areaId()>1){
						list.get(i).setF_areaIdString(memberAddressApiService.getAreaString(list.get(i).getF_areaId()));
					}
					                                      

					if(list.get(i).getS_provinceId()!=null&&list.get(i).getS_provinceId()>1){
						list.get(i).setS_provinceIdString(memberAddressApiService.getAreaString(list.get(i).getS_provinceId()));
					}
					if(list.get(i).getS_cityId()!=null &&list.get(i).getS_cityId()>1){
						list.get(i).setS_cityIdString(memberAddressApiService.getAreaString(list.get(i).getS_cityId()));
					}
					if(list.get(i).getS_areaId()!=null &&list.get(i).getS_areaId()>1){
						list.get(i).setS_areaIdString(memberAddressApiService.getAreaString(list.get(i).getS_areaId()));
					}
					if(list.get(i).getF_provinceId()!=null&&list.get(i).getF_provinceId()==0&&list.get(i).getF_cityId()==0&&list.get(i).getF_areaId()==0){
						list.get(i).setF_provinceIdString("全国");  
					}
					if(list.get(i).getS_provinceId()!=null&&list.get(i).getS_provinceId()==0&&list.get(i).getS_cityId()==0&&list.get(i).getS_areaId()==0){
						list.get(i).setS_provinceIdString("全国");
					}
				    
					if(list.get(i).getS_cityId()!=null&&list.get(i).getS_cityId()!=0){
						if ("市辖区".equals(list.get(i).getS_cityIdString()) || "县".equals(list.get(i).getS_cityIdString())||"市".equals(list.get(i).getS_cityIdString())){
							list.get(i).setS_cityIdString("");
						}else{
							list.get(i).setS_provinceIdString("");
						}
					}
					if(list.get(i).getF_cityId()!=null&&list.get(i).getF_cityId()!=0){
						if ("市辖区".equals(list.get(i).getF_cityIdString()) || "县".equals(list.get(i).getF_cityIdString())||"市".equals(list.get(i).getF_cityIdString())){
							list.get(i).setF_cityIdString("");
						}else{
							list.get(i).setF_provinceIdString("");
						}
					}
					if(list.get(i).getF_areaId()!=null&&list.get(i).getF_areaId()!=0){
						if ("市辖区".equals(list.get(i).getF_areaIdString()) || "县".equals(list.get(i).getF_areaIdString())||"市".equals(list.get(i).getF_areaIdString())|| "省直辖行政单位".equals(list.get(i).getF_areaIdString())){
							list.get(i).setF_areaIdString("");
						}
						
					}
					if(list.get(i).getS_areaId()!=null&&list.get(i).getS_areaId()!=0){
						if ("市辖区".equals(list.get(i).getS_areaIdString()) || "县".equals(list.get(i).getS_areaIdString())||"市".equals(list.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString())){
							list.get(i).setS_areaIdString("");
						}
						
					}
			}
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} 
		catch (BusinessException e) {
			logger.info("获取推送信息货源信息异常", e);
		} catch (Exception e) {
			logger.info("获取推送信息货源信息异常异常", e);
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 * 个人中心,推送信息第二页,  推荐的线路列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPushMessageInfoCarLine")
	public void getPushMessageInfoCarLine(HttpServletRequest request,
			HttpServletResponse response,PushNstMessageDTO pushNstMessageDTO) {
		ObjectResult result = new ObjectResult();
		try {
			List<PushNstMessageInfoDTO> list = memberToolService.getPushMessageInfoCarLine(pushNstMessageDTO);
			for (int i = 0; i < list.size(); i++) {
				//获取当前发布联系人的认证状况
				
				if(list.get(i).getMemberId()!=null){
					list.get(i).setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(list.get(i).getMemberId())));
				}
				
				MemberBaseinfoDTO md=memberToolService.getById(list.get(i).getMemberId()+"");
				if(md.getUserType()!=null&&md.getUserType()==1){

			          if(md.getRealName()!=null){
			        	  list.get(i).setContact(md.getRealName());
			          }
			          if(md.getRealName()!=null){
			        	  list.get(i).setMobile(md.getRealName());
			          }
						
					
				}
				if(md.getUserType()!=null&&md.getUserType()==2){
			          if(md.getCompanyContact()!=null){
			        	  list.get(i).setContact(md.getCompanyContact());
			          }
			          if(md.getCompanyMobile()!=null){
			        	  list.get(i).setMobile(md.getCompanyMobile());
			          }
						
					
				}
				//如果用户是网页发布的信息,而且没有做类型设置.
				//则默认真名
				if(md.getUserType()==null||"".equals(md.getUserType())){
			          if(md.getCompanyContact()!=null){
			        	  list.get(i).setContact(md.getRealName());
			          }
			          if(md.getCompanyMobile()!=null){
			        	  list.get(i).setMobile(md.getRealName());
			          }
						
					
				}
				if (list.get(i).getS_provinceId() != null
						&& list.get(i).getS_provinceId() > 1) {
					list.get(i).setS_provinceIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_provinceId()));
				}
				if (list.get(i).getS_cityId() != null
						&& list.get(i).getS_cityId() > 1) {
					list.get(i).setS_cityIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_cityId()));
				}
				if (list.get(i).getS_areaId() != null
						&& list.get(i).getS_areaId() > 1) {
					list.get(i).setS_areaIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_areaId()));
				}
				//起始地二
				if (list.get(i).getS_provinceId2() != null
						&& list.get(i).getS_provinceId2() > 1) {
					list.get(i).setS_provinceIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_provinceId2()));
				}
				if (list.get(i).getS_cityId2() != null
						&& list.get(i).getS_cityId2() > 1) {
					list.get(i).setS_cityIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_cityId2()));
				}
				if (list.get(i).getS_areaId2() != null
						&& list.get(i).getS_areaId2() > 1) {
					list.get(i).setS_areaIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_areaId2()));
				}

				if (list.get(i).getS_provinceId3() != null
						&& list.get(i).getS_provinceId3() > 1) {
					list.get(i).setS_provinceIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_provinceId3()));
				}
				if (list.get(i).getS_cityId3() != null
						&& list.get(i).getS_cityId3() > 1) {
					list.get(i).setS_cityIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_cityId3()));
				}
				if (list.get(i).getS_areaId3() != null
						&& list.get(i).getS_areaId3() > 1) {
					list.get(i).setS_areaIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getS_areaId3()));
				}
							
							
							
				if (list.get(i).getE_provinceId() != null
						&& list.get(i).getE_provinceId() > 1) {
					list.get(i).setE_provinceIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId()));
				}
				if (list.get(i).getE_cityId() != null
						&& list.get(i).getE_cityId() > 1) {
					list.get(i).setE_cityIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId()));
				}
				if (list.get(i).getE_areaId() != null
						&& list.get(i).getE_areaId() > 1) {
					list.get(i).setE_areaIdString(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId()));
				}
				
				if (list.get(i).getE_provinceId2() != null
						&& list.get(i).getE_provinceId2() > 1) {
					list.get(i).setE_provinceIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId2()));
				}
				if (list.get(i).getE_cityId2() != null
						&& list.get(i).getE_cityId2() > 1) {
					list.get(i).setE_cityIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId2()));
				}
				if (list.get(i).getE_areaId2() != null
						&& list.get(i).getE_areaId2() > 1) {
					list.get(i).setE_areaIdString2(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId2()));
				}
				if (list.get(i).getE_provinceId3() != null
						&& list.get(i).getE_provinceId3() > 1) {
					list.get(i).setE_provinceIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId3()));
				}
				if (list.get(i).getE_cityId3() != null
						&& list.get(i).getE_cityId3() > 1) {
					list.get(i).setE_cityIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId3()));
				}
				if (list.get(i).getE_areaId3() != null
						&& list.get(i).getE_areaId3() > 1) {
					list.get(i).setE_areaIdString3(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId3()));
				}
				if (list.get(i).getE_provinceId4() != null
						&& list.get(i).getE_provinceId4() > 1) {
					list.get(i).setE_provinceIdString4(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId4()));
				}
				if (list.get(i).getE_cityId4() != null
						&& list.get(i).getE_cityId4() > 1) {
					list.get(i).setE_cityIdString4(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId4()));
				}
				if (list.get(i).getE_areaId4() != null
						&& list.get(i).getE_areaId4() > 1) {
					list.get(i).setE_areaIdString4(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId4()));
				}
				if (list.get(i).getE_provinceId5() != null
						&& list.get(i).getE_provinceId5() > 1) {
					list.get(i).setE_provinceIdString5(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_provinceId5()));
				}
				if (list.get(i).getE_cityId5() != null
						&& list.get(i).getE_cityId5() > 1) {
					list.get(i).setE_cityIdString5(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_cityId5()));
				}
				if (list.get(i).getE_areaId5() != null
						&& list.get(i).getE_areaId5() > 1) {
					list.get(i).setE_areaIdString5(
							memberAddressApiService.getAreaString(list.get(i)
									.getE_areaId5()));
				}
				
				
				
				if(list.get(i).getS_areaId()!=null&&list.get(i).getS_provinceId()!=null&&list.get(i).getS_cityId()!=null&&list.get(i).getS_areaId()==0&&list.get(i).getS_provinceId()==0&&list.get(i).getS_cityId()==0){
					list.get(i).setS_provinceIdString("全国");
				}
				if(list.get(i).getS_cityId()!=null&&list.get(i).getS_cityId()!=0){
					if ("市辖区".equals(list.get(i).getS_cityIdString()) || "县".equals(list.get(i).getS_cityIdString())||"市".equals(list.get(i).getS_cityIdString())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString())){
						list.get(i).setS_cityIdString("");
					}else{
						list.get(i).setS_provinceIdString("");
					}
					
				}
				if(list.get(i).getS_areaId()!=null&&list.get(i).getS_areaId()!=0){
					if ("市辖区".equals(list.get(i).getS_areaIdString()) || "县".equals(list.get(i).getS_areaIdString())||"市".equals(list.get(i).getS_areaIdString())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString())){
						list.get(i).setS_areaIdString("");
					}
				}
				if(list.get(i).getE_areaId()!=null&&list.get(i).getE_areaId()!=0){
					if ("市辖区".equals(list.get(i).getE_areaIdString()) || "县".equals(list.get(i).getE_areaIdString())||"市".equals(list.get(i).getE_areaIdString())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString())){
						list.get(i).setE_areaIdString("");
					}
				}
				
				if(list.get(i).getE_areaId()!=null&&list.get(i).getE_provinceId()!=null&&list.get(i).getE_cityId()!=null&&list.get(i).getE_areaId()==0&&list.get(i).getE_provinceId()==0&&list.get(i).getE_cityId()==0){
					list.get(i).setE_provinceIdString("全国");
				}
				
				if(list.get(i).getE_cityId()!=null&&list.get(i).getE_cityId()!=0){
					if ("市辖区".equals(list.get(i).getE_cityIdString()) || "县".equals(list.get(i).getE_cityIdString())||"市".equals(list.get(i).getE_cityIdString())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString())){
						list.get(i).setE_cityIdString("");
					}else{
						list.get(i).setE_provinceIdString("");
					}
				}
				//地址2
				if(list.get(i).getS_areaId2()!=null&&list.get(i).getS_provinceId2()!=null&&list.get(i).getS_cityId2()!=null&&list.get(i).getS_areaId2()==0&&list.get(i).getS_provinceId2()==0&&list.get(i).getS_cityId2()==0){
					list.get(i).setS_provinceIdString2("全国");
				}
				if(list.get(i).getS_cityId2()!=null&&list.get(i).getS_cityId2()!=0){
					if ("市辖区".equals(list.get(i).getS_cityIdString2()) || "县".equals(list.get(i).getS_cityIdString2())||"市".equals(list.get(i).getS_cityIdString2())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString2())){
						list.get(i).setS_cityIdString2("");
					}else{
						list.get(i).setS_provinceIdString2("");
					}
				}
				if(list.get(i).getS_areaId2()!=null&&list.get(i).getS_areaId2()!=0){
					if ("市辖区".equals(list.get(i).getS_areaIdString2()) || "县".equals(list.get(i).getS_areaIdString2())||"市".equals(list.get(i).getS_areaIdString2())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString2())){
						list.get(i).setS_areaIdString2("");
					}
				}
				
				if(list.get(i).getE_areaId2()!=null&&list.get(i).getE_provinceId2()!=null&&list.get(i).getE_cityId2()!=null&&list.get(i).getE_areaId2()==0&&list.get(i).getE_provinceId2()==0&&list.get(i).getE_cityId2()==0){
					list.get(i).setE_provinceIdString2("全国");
				}
				if(list.get(i).getE_cityId2()!=null&&list.get(i).getE_cityId2()!=0){
					if ("市辖区".equals(list.get(i).getE_cityIdString2()) || "县".equals(list.get(i).getE_cityIdString2())||"市".equals(list.get(i).getE_cityIdString2())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString2())){
						list.get(i).setE_cityIdString2("");
					}else{
						list.get(i).setE_provinceIdString2("");
					}
				}
				if(list.get(i).getE_areaId2()!=null&&list.get(i).getE_areaId2()!=0){
					if ("市辖区".equals(list.get(i).getE_areaIdString2()) || "县".equals(list.get(i).getE_areaIdString2())||"市".equals(list.get(i).getE_areaIdString2())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString2())){
						list.get(i).setE_areaIdString2("");
					}
				}
				//地址3
				if(list.get(i).getS_areaId3()!=null&&list.get(i).getS_provinceId3()!=null&&list.get(i).getS_cityId3()!=null&&list.get(i).getS_areaId3()==0&&list.get(i).getS_provinceId3()==0&&list.get(i).getS_cityId3()==0){
					list.get(i).setS_provinceIdString3("全国");
				}
				
				if(list.get(i).getS_cityId3()!=null&&list.get(i).getS_cityId3()!=0){
					if ("市辖区".equals(list.get(i).getS_cityIdString3()) || "县".equals(list.get(i).getS_cityIdString3())||"市".equals(list.get(i).getS_cityIdString3())|| "省直辖行政单位".equals(list.get(i).getS_cityIdString3())){
						list.get(i).setS_cityIdString3("");
					}else{
						list.get(i).setS_provinceIdString3("");
					}
				}
				if(list.get(i).getS_areaId3()!=null&&list.get(i).getS_areaId3()!=0){
					if ("市辖区".equals(list.get(i).getS_areaIdString3()) || "县".equals(list.get(i).getS_areaIdString3())||"市".equals(list.get(i).getS_areaIdString3())|| "省直辖行政单位".equals(list.get(i).getS_areaIdString3())){
						list.get(i).setS_areaIdString3("");
					}
				}
				if(list.get(i).getE_areaId3()!=null&&list.get(i).getE_provinceId3()!=null&&list.get(i).getE_cityId3()!=null&&list.get(i).getE_areaId3()==0&&list.get(i).getE_provinceId3()==0&&list.get(i).getE_cityId3()==0){
					list.get(i).setE_provinceIdString3("全国");
				}
				if(list.get(i).getE_cityId3()!=null&&list.get(i).getE_cityId3()!=0){
					if ("市辖区".equals(list.get(i).getE_cityIdString3()) || "县".equals(list.get(i).getE_cityIdString3())||"市".equals(list.get(i).getE_cityIdString3())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString3())){
						list.get(i).setE_cityIdString3("");
					}else{
						list.get(i).setE_provinceIdString3("");
					}
				}
				if(list.get(i).getE_areaId3()!=null&&list.get(i).getE_areaId3()!=0){
					if ("市辖区".equals(list.get(i).getE_areaIdString3()) || "县".equals(list.get(i).getE_areaIdString3())||"市".equals(list.get(i).getE_areaIdString3())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString3())){
						list.get(i).setE_areaIdString3("");
					}
				}
				//地址4
				if(list.get(i).getE_areaId4()!=null&&list.get(i).getE_provinceId4()!=null&&list.get(i).getE_cityId4()!=null&&list.get(i).getE_areaId4()==0&&list.get(i).getE_provinceId4()==0&&list.get(i).getE_cityId4()==0){
					list.get(i).setE_provinceIdString4("全国");
				}
				if(list.get(i).getE_cityId4()!=null&&list.get(i).getE_cityId4()!=0){
					if ("市辖区".equals(list.get(i).getE_cityIdString4()) || "县".equals(list.get(i).getE_cityIdString4())||"市".equals(list.get(i).getE_cityIdString4())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString4())){
						list.get(i).setE_cityIdString4("");
					}else{
						list.get(i).setE_provinceIdString4("");
					}
				}
				if(list.get(i).getE_areaId4()!=null&&list.get(i).getE_areaId4()!=0){
					if ("市辖区".equals(list.get(i).getE_areaIdString4()) || "县".equals(list.get(i).getE_areaIdString4())||"市".equals(list.get(i).getE_areaIdString4())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString4())){
						list.get(i).setE_areaIdString4("");
					}
				}
				//地址5
				if(list.get(i).getE_areaId5()!=null&&list.get(i).getE_provinceId5()!=null&&list.get(i).getE_cityId5()!=null&&list.get(i).getE_areaId5()==0&&list.get(i).getE_provinceId5()==0&&list.get(i).getE_cityId5()==0){
					list.get(i).setE_provinceIdString5("全国");
				}
				if(list.get(i).getE_cityId5()!=null&&list.get(i).getE_cityId5()!=0){
					if ("市辖区".equals(list.get(i).getE_cityIdString5()) || "县".equals(list.get(i).getE_cityIdString5())||"市".equals(list.get(i).getE_cityIdString5())|| "省直辖行政单位".equals(list.get(i).getE_cityIdString5())){
						list.get(i).setE_cityIdString5("");
					}else{
						list.get(i).setE_provinceIdString5("");
					}
				}
				if(list.get(i).getE_areaId5()!=null&&list.get(i).getE_areaId5()!=0){
					if ("市辖区".equals(list.get(i).getE_areaIdString5()) || "县".equals(list.get(i).getE_areaIdString5())||"市".equals(list.get(i).getE_areaIdString5())|| "省直辖行政单位".equals(list.get(i).getE_areaIdString5())){
						list.get(i).setE_areaIdString5("");
					}
				}
				//去除重复的数据
				List<String> list1  = new ArrayList<String>();
				List<String>  list2  = new ArrayList<String>();
				String s1="";
				if(list.get(i).getS_provinceId()!=null){
					s1=list.get(i).getS_provinceId().toString();
				}
				if(list.get(i).getS_cityId()!=null){
					s1=s1+list.get(i).getS_cityId().toString();
				}
				if(list.get(i).getS_areaId()!=null){
					s1=s1+list.get(i).getS_areaId().toString();
				}
				String s2="";
				if(list.get(i).getS_provinceId2()!=null){
					s2=list.get(i).getS_provinceId2().toString();
				}
				if(list.get(i).getS_cityId2()!=null){
					s2=s2+list.get(i).getS_cityId2().toString();
				}
				if(list.get(i).getS_areaId2()!=null){
					s2=s2+list.get(i).getS_areaId2().toString();
				}
				String s3="";
				if(list.get(i).getS_provinceId3()!=null){
					s3=list.get(i).getS_provinceId3().toString();
				}
				if(list.get(i).getS_cityId3()!=null){
					s3=s3+list.get(i).getS_cityId3().toString();
				}
				if(list.get(i).getS_areaId3()!=null){
					s3=s3+list.get(i).getS_areaId3().toString();
				}
					if(!list1.contains(s1)){
						list1.add(s1);
					}
					if(!list1.contains(s2)){
						list1.add(s2);
					}else{
						s2="no";
						list.get(i).setS_provinceIdString2("");
						list.get(i).setS_areaIdString2("");
						list.get(i).setS_cityIdString2("");
					}
					if(!list1.contains(s3)){
						list1.add(s3);
					}else{
						s3="no";
						list.get(i).setS_provinceIdString3("");
						list.get(i).setS_areaIdString3("");
						list.get(i).setS_cityIdString3("");
					}
					
					String e1="";
					if(list.get(i).getE_provinceId()!=null){
						e1=list.get(i).getE_provinceId().toString();
					}
					if(list.get(i).getE_cityId()!=null){
						e1=e1+list.get(i).getE_cityId().toString();
					}
					if(list.get(i).getE_areaId()!=null){
						e1=e1+list.get(i).getE_areaId().toString();
					}
					
					String e2="";
					if(list.get(i).getE_provinceId2()!=null){
						e2=list.get(i).getE_provinceId2().toString();
					}
					if(list.get(i).getE_cityId2()!=null){
						e2=e2+list.get(i).getE_cityId2().toString();
					}
					if(list.get(i).getE_areaId2()!=null){
						e2=e2+list.get(i).getE_areaId2().toString();
					}
					
					String e3="";
					if(list.get(i).getE_provinceId3()!=null){
						e3=list.get(i).getE_provinceId3().toString();
					}
					if(list.get(i).getE_cityId3()!=null){
						e3=e3+list.get(i).getE_cityId3().toString();
					}
					if(list.get(i).getE_areaId3()!=null){
						e3=e3+list.get(i).getE_areaId3().toString();
					}
					
					String e4="";
					if(list.get(i).getE_provinceId4()!=null){
						e4=list.get(i).getE_provinceId4().toString();
					}
					if(list.get(i).getE_cityId4()!=null){
						e4=e4+list.get(i).getE_cityId4().toString();
					}
					if(list.get(i).getE_areaId4()!=null){
						e4=e4+list.get(i).getE_areaId4().toString();
					}
					
					String e5="";
					if(list.get(i).getE_provinceId5()!=null){
						e5=list.get(i).getE_provinceId5().toString();
					}
					if(list.get(i).getE_cityId5()!=null){
						e5=e5+list.get(i).getE_cityId5().toString();
					}
					if(list.get(i).getE_areaId5()!=null){
						e5=e5+list.get(i).getE_areaId5().toString();
					}

					if(!list2.contains(e1)){
						list2.add(e1);
					}
					if(!list2.contains(e2)){
						list2.add(e2);
					}else{
						e2="no";
						list.get(i).setE_provinceIdString2("");
						list.get(i).setE_areaIdString2("");
						list.get(i).setE_cityIdString2("");
					}
					if(!list2.contains(e3)){
						list2.add(e3);
					}else{
						e3="no";
						list.get(i).setE_provinceIdString3("");
						list.get(i).setE_areaIdString3("");
						list.get(i).setE_cityIdString3("");
					}
					if(!list2.contains(e4)){
						list2.add(e4);
					}else{
						e4="no";
						list.get(i).setE_provinceIdString4("");
						list.get(i).setE_areaIdString4("");
						list.get(i).setE_cityIdString4("");
					}
					if(!list2.contains(e5)){
						list2.add(e5);
					}else{
						e5="no";
						list.get(i).setE_provinceIdString5("");
						list.get(i).setE_areaIdString5("");
						list.get(i).setE_cityIdString5("");
					}
				
			}
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} 
		catch (BusinessException e) {
			logger.info("获取推送线路第二页异常", e);
		} catch (Exception e) {
			logger.info("获取推送线路第二页异常", e);
		}
		renderJson(result, request, response);
	}
   
	@RequestMapping("/getstauts")
	public void getstauts(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
	 try {
		memberCertifiApiService.queryCertStatus(Long.valueOf(4321));
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

}
