package com.gudeng.commerce.gd.api.controller.v2;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.gudeng.commerce.gd.api.enums.NsyUserTypeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MarketToolService;
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
import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 功能描述：会员信息控制中心
 * 
 */
@Controller
@RequestMapping("v2/member")
public class MemberV2Controller extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MemberV2Controller.class);

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
	public BusinessBaseinfoToolService businessBaseinfoToolService; 
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
//	@Autowired
//	private RedisUtil sadClient;
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request,HttpSession session, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		String username = memberDtoInput.getAccount();
		String password = memberDtoInput.getPassword();
		Integer level =memberDtoInput.getLevel();
		Integer device =memberDtoInput.getDevice();//登录设备
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
			MemberBaseinfoDTO memberDTO =null;
			
			//判断是否是登录门岗
			if(level!=null&&level==Constant.Member.MEMBER_LEVEL_FIVE.intValue()){
				memberDTO = memberToolService.getByMobileAndLevel(username,Constant.Member.MEMBER_LEVEL_FIVE);
			}
			else{
				memberDTO = memberToolService.getByMobile(username);
			}
			//判断用户是否存在, 根据手机号查询

			if(null == memberDTO){
				setResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			//判断是否是产地供应商(device=4)登录卖家版(level=4)，是拒绝登录
			if(device != null && memberDTO.getLevel() == 4 && device == 4){
				setResult(result, ErrorCodeEnum.GYS_ACCOUNT_NOT_PERMIT, request, response);
				return;
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
			
			Map<String, Object> map = new HashMap<String, Object>();
			//会员id
			Long memberId=memberDTO.getMemberId();
			map.put("sid",Constant.Member.MEMBER_SID);
			map.put("memberId", memberId);
			map.put("account", memberDTO.getAccount());
			//add by yanghaoyu ,新增加参数,userType,如果当前用户是 公司,还是个人
			if(memberDTO.getUserType()==null){
				map.put("userType", 0);
			}else{
				map.put("userType", memberDTO.getUserType());
			}
			
			map.put("realName", memberDTO.getRealName());
			map.put("nsyUserType", memberDTO.getNsyUserType());
			//农商友用户类型:1下游批发商,2生鲜超市,3学校食堂,4食品加工工厂,5社区门店,6餐厅,7垂直生鲜,8其它
			map.put("nsyUserTypeName", NsyUserTypeEnum.getNsyUserTypeNameByNsyUserType(memberDTO.getNsyUserType()));
			map.put("sex", memberDTO.getSex());
			map.put("companyName", memberDTO.getCompanyName());
			map.put("companyContact", memberDTO.getCompanyContact());
			
			//获取商铺信息
			BusinessBaseinfoDTO dto = businessBaseinfoToolService.getByUserId(memberId.toString());
			if(dto==null){
				map.put("marketId", "");
				map.put("businessId","");
				map.put("shopName", "");
			}else{
				map.put("marketId", dto.getMarketId());
				map.put("businessId", dto.getBusinessId());
				map.put("shopName", dto.getShopsName());
			}
			result.setObject(map);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.error("[ERROR]用户登录异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	

	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		String username = memberDtoInput.getAccount();//账号
		String verifyCode = request.getParameter("verifyCode");//手机验证码
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
				//0管理后台创建用户，1web前端注册用户，2农速通注册用户，3农商友-买家版注册用户，4农商友-卖家版注册用户，5门岗添加用户
				String regetype = memberDtoInput.getRegetype();
				if(StringUtils.isBlank(regetype)){
					setResult(result, ErrorCodeEnum.USER_RESOURCE_IS_EMPTY, request, response);
					return;
				}
				
				if("2".equals(regetype)){
					setResult(result, ErrorCodeEnum.NST_APP_IS_NOT_USED, request, response);
					return;
				}
				
				if (memberToolService.isAccExisted(username)){
					setResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
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
				memberDTO.setMemberGrade(0);
				//2016-10-20 系统生成账户名
				String account="gd"+UUID.randomUUID().toString().replace("-", "").toUpperCase(); //由系统生成帐号：gd+UUID
				memberDTO.setAccount(account);
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
				logger.debug("===手机号: " + mobile + " 已存在验证码， 并且60秒内重发...");
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			
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
			
			//发送验证码
			String verifycode = CommonUtils.genVerfiyCode(true, 6);
			logger.info("**************手机: "+mobile+",验证码: " + verifycode);
			boolean isExistedAcc = memberToolService.isAccExisted(mobile);
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
				//CommonUtils.sendMsg(channel,MessageTemplateEnum.FORGET_PWD.getTemplate().replace("{P}", verifycode), mobile);
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
			
			//更改真名,进行判断是否传空
			String realName ="";
			if(!StringUtils.isBlank(memberDtoInput.getRealName())){
		    	if(!StringUtils.isBlank(memberDtoInput.getApp())){
			    	if("IOS".equals(memberDtoInput.getApp())){
			    		realName =  new String(memberDtoInput.getRealName().getBytes("utf-8"),"utf-8");
			    	}
			    	}else{
			    		realName =  new String(memberDtoInput.getRealName().getBytes("ISO-8859-1"),"utf-8");
			    	}
				 
			}
			if(!StringUtils.isBlank(realName)){
				memberDTO.setRealName(realName);
			}
			//更改类型
			//if(memberDtoInput.getSex()!=null){
				Integer  sex = memberDtoInput.getSex();
				memberDTO.setSex(sex);
			//}		updateMemberBaseinfoDTO
				
				
				String companyName ="";
				if(!StringUtils.isBlank(memberDtoInput.getCompanyName())){
			    	if(!StringUtils.isBlank(memberDtoInput.getApp())){
				    	if("IOS".equals(memberDtoInput.getApp())){
				    		companyName =  new String(memberDtoInput.getCompanyName().getBytes("utf-8"),"utf-8");
				    	}
				    	}else{
				    		companyName =  new String(memberDtoInput.getCompanyName().getBytes("ISO-8859-1"),"utf-8");
				    	}
					 
				}
				if(!StringUtils.isBlank(companyName)){
					memberDTO.setCompanyName(companyName);
				}
				String companyContact ="";
				if(!StringUtils.isBlank(memberDtoInput.getCompanyContact())){
			    	if(!StringUtils.isBlank(memberDtoInput.getApp())){
				    	if("IOS".equals(memberDtoInput.getApp())){
				    		companyContact =  new String(memberDtoInput.getCompanyContact().getBytes("utf-8"),"utf-8");
				    	}
				    	}else{
				    		companyContact =  new String(memberDtoInput.getCompanyContact().getBytes("ISO-8859-1"),"utf-8");
				    	}
					 
				}
				if(!StringUtils.isBlank(companyContact)){
					memberDTO.setCompanyContact(companyContact);
				}
				
				memberDTO.setUserType(memberDtoInput.getUserType());
			memberToolService.updateMember(memberDTO);
			//一位完善用户信息 ,用户表示有数据了,所以是update,但是认证表,第一次是没有信息的,所以要做判断

			 if(memberDtoInput.getUserType()!=null){
				 if(memberDtoInput.getUserType()==2){
					 int mc= memberCertifiApiService.getMcId(id);
					 MemberCertifiDTO mcd= new MemberCertifiDTO();
					 mcd.setMemberId(Long.valueOf(id));
					 mcd.setType(memberDtoInput.getUserType()+"");
					 mcd.setCompanyName(companyName);
					 mcd.setLinkMan(companyContact);
					 
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
			MemberBaseinfoDTO memberDTO = memberToolService.getById(id + "");
			//判断用户是否存在
			if (null == memberDTO){
				setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
			}
			
			result.setObject(memberDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]更新用户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL,request, response);
		}
	}
	
	/**
	 * 根据手机号码获取会员信息
	 * @param mobile
	 * @param request
	 */
	@RequestMapping("getByMobile")
	public void getMemberByMobile(String mobile, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try{
			MemberBaseinfoDTO memberDTO = memberToolService.getByAccount(mobile);
			if(memberDTO == null){
				memberDTO = memberToolService.getByMobile(mobile);
			}
			if(memberDTO == null){
				result.setMsg("用户不存在");
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			}else{
				memberDTO.setPassword("");
				memberDTO.setDevice_tokens("");
				result.setObject(memberDTO);
				result.setMsg("success");
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			}
		}catch(Exception e){
			logger.info("获取会员信息异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 退出系统
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="logout")
	public String logout(HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		result.setMsg("注销成功");
		this.renderJson(result,request,response);
		return null;
	}
}
