package com.gudeng.commerce.gd.api.controller.v160630;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.thread.GetNstTokenThread;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.MemberLoginLogUtil;
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
@RequestMapping("v31/member")
public class MemberV31Controller extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MemberV31Controller.class);

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
	private NstApiCommonService nstApiCommonService;
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request,HttpSession session, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		MemberBaseinfoDTO memberDtoInput = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
		} catch (Exception e1) {
			logger.error("[ERROR]用户登录数据异常", e1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		String username = memberDtoInput.getAccount();
		String password = memberDtoInput.getPassword();
		Integer level =memberDtoInput.getLevel();
		Integer device =memberDtoInput.getDevice();//登录设备
		String deviceTokens = memberDtoInput.getDevice_tokens();
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
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			//判断是否是产地供应商(device=4)登录卖家版(level=4)，是拒绝登录
			if(device != null && memberDTO.getLevel() == 4 && device == 4){
				setEncodeResult(result, ErrorCodeEnum.GYS_ACCOUNT_NOT_PERMIT, request, response);
				return;
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
			
			//更新deviceTokens
			MemberBaseinfoDTO newMemberDTO = new MemberBaseinfoDTO();
			newMemberDTO.setMemberId(memberDTO.getMemberId());
			newMemberDTO.setDevice_tokens(deviceTokens);
			memberToolService.updateMember(newMemberDTO);
			
			MemberLoginLogUtil.insertLoginLog(memberDTO, "4", request);

			Map<String, Object> map = new HashMap<String, Object>();;
			//会员id
			Long memberId=memberDTO.getMemberId();
			map.put("sid",Constant.Member.MEMBER_SID);
			map.put("memberId", memberId);
			map.put("account", memberDTO.getAccount());
			//电话号码
			map.put("mobile", memberDTO.getMobile());
			//add by yanghaoyu ,新增加参数,userType,如果当前用户是 公司,还是个人
			if(memberDTO.getUserType()==null){
				map.put("userType", 0);
			}else{
				map.put("userType", memberDTO.getUserType());
			}
			map.put("level", memberDTO.getLevel());
			map.put("realName", memberDTO.getRealName());
			map.put("nsyUserType", memberDTO.getNsyUserType());
			//农商友用户类型:1下游批发商,2生鲜超市,3学校食堂,4食品加工工厂,5社区门店,6餐厅,7垂直生鲜,8其它
			map.put("nsyUserTypeName", NsyUserTypeEnum.getNsyUserTypeNameByNsyUserType(memberDTO.getNsyUserType()));
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
			//获取商铺信息
			BusinessBaseinfoDTO dto = businessBaseinfoToolService.getByUserId(memberId.toString());
			if(dto==null){
				map.put("marketId", "");
				map.put("businessId","");
				map.put("shopName", "");
				//是否是现场采销
				map.put("isSiteProdSales", "0");
			}else{
				map.put("marketId", dto.getMarketId());
				map.put("businessId", dto.getBusinessId());
				map.put("shopName", dto.getShopsName());
				//如果有自己的商铺，且是武汉白沙洲批发市场，那么需要设置现场采销为1(0代表不是现场采销，1代表是现场采销)
				if(null != dto.getBusinessId() && "1".equals(dto.getMarketId())){
					map.put("isSiteProdSales", "1");
				}else{
					map.put("isSiteProdSales", "0");
				}
			}
			result.setObject(map);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			//获取农速通token
			new Thread(new GetNstTokenThread(nstApiCommonService, memberId.intValue())).start();
		} catch (Exception e) {
			logger.error("[ERROR]用户登录异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	

	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		MemberBaseinfoDTO memberDtoInput = null;
		String verifyCode = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
			verifyCode = (String)GSONUtils.getJsonValueStr(jsonStr, "verifyCode");//手机验证码 
		} catch (Exception e1) {
			logger.error("[ERROR]用户注册数据异常", e1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		String username = memberDtoInput.getAccount();//账号
		
		if(StringUtils.isBlank(username)){
			setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(verifyCode)){
			setEncodeResult(result, ErrorCodeEnum.VERIFYCODE_IS_EMPTY, request, response);
			return;
		}
		
		if(!Validator.isMobile(username)){
			setEncodeResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
			return;
		}
		
		try {
			logger.debug("User info: username: " + username + ", verifyCode: " + verifyCode);
			//判断用户是否存在
			if (memberToolService.isAccExisted(username)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
				return;
			}
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
			logger.warn("[ERROR]用户注册异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		MemberBaseinfoDTO memberDtoInput = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
		} catch (Exception e1) {
			logger.error("[ERROR]用户更新数据异常", e1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		Long id = memberDtoInput.getMemberId();
		if(id == null){
			setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		
		try {
			MemberBaseinfoDTO memberDTO = memberToolService.getById(id + "");
			//判断用户是否存在
			if (null == memberDTO){
				setEncodeResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
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
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]更新用户信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/forgetPwd")
	public void forgetPwd(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		MemberBaseinfoDTO memberDtoInput = null;
		String verifyCode = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
			verifyCode = (String)GSONUtils.getJsonValueStr(jsonStr, "verifyCode");//手机验证码 
		} catch (Exception e1) {
			logger.error("[ERROR]用户忘记密码数据异常", e1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		String username = memberDtoInput.getAccount();
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
		
		try {
			//判断用户是否存在
			if (!memberToolService.isAccExisted(username)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
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
	
	@RequestMapping("/getVerifyCode")
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		MemberBaseinfoDTO memberDtoInput = null;
		String typeStr = null;
		String encryptStr = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
			typeStr = (String)GSONUtils.getJsonValueStr(jsonStr, "type");
			encryptStr = (String)GSONUtils.getJsonValueStr(jsonStr, "encryptStr");
		} catch (Exception e1) {
			logger.error("[ERROR]用户获取验证码数据异常", e1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		String mobile = memberDtoInput.getMobile();
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
		if(type != 0 && type != 1 && type != 6 && type != 7){//6 验证旧手机，7 绑定新手机
			setEncodeResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
			return;
		}
		
		try {
			if(!CommonUtils.verifyMobile(mobile, encryptStr)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_NOT_MATCHED, request, response);
				return;
			}
			
			//如果验证码已存在并且还在60秒内, 默认返回成功
			if(ErrorCodeEnum.SUCCESS == CommonUtils.isVerifyMsgInSeconds(mobile, 60, redisClient)){
				logger.info("===手机号: " + mobile + " 已存在验证码， 并且60秒内重发...");
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
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
					setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
					return;
				}
				if(Constant.Alidayu.REDISTYPE.equals(channel)){
					content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE3,verifycode);
					
				}else{
					content=MessageTemplateEnum.REGISTER.getTemplate().replace("{P}", verifycode);
				}
			}else if(type==6){//6 验证旧手机
				if(!isExistedAcc){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				}
				
				if(Constant.Alidayu.REDISTYPE.equals(channel)){
					content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE6,verifycode);
				}else{
					content=MessageTemplateEnum.CONFIRM_MOBILE.getTemplate().replace("{P}", verifycode);
				}
			}else if(type==7){//7 绑定新手机
				if(isExistedAcc){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
				}
				if(Constant.Alidayu.REDISTYPE.equals(channel)){
					content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE7,verifycode);
					
				}else{
					content=MessageTemplateEnum.CONFIRM_NEW_MOBILE.getTemplate().replace("{P}", verifycode);
				}
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
				//CommonUtils.sendMsg(channel,MessageTemplateEnum.FORGET_PWD.getTemplate().replace("{P}", verifycode), mobile);
			}
			logger.info("####################content: "+content);
			//保存到缓存中
			SyncUtil.setVerifyCode(mobile, verifycode, redisClient);
			CommonUtils.sendMsg(channel,content, mobile);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch (Exception e) {
			logger.warn("[ERROR]获取验证异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/checkVerifyCode")
	public void checkVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		MemberBaseinfoDTO memberDtoInput = null;
		String verifyCode = null;
		String typeStr = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
			verifyCode = (String)GSONUtils.getJsonValueStr(jsonStr, "verifyCode");
			typeStr = (String)GSONUtils.getJsonValueStr(jsonStr, "type");
		} catch (Exception e1) {
			logger.error("[ERROR]校验验证码数据异常", e1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		String username = memberDtoInput.getAccount();
		if(StringUtils.isBlank(username)){
			setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(verifyCode)){
			setEncodeResult(result, ErrorCodeEnum.VERIFYCODE_IS_EMPTY, request, response);
			return;
		}
		
		if(StringUtils.isBlank(typeStr)){
			setEncodeResult(result, ErrorCodeEnum.TYPE_IS_EMPTY, request, response);
			return;
		}
		
		try {
			int type = Integer.parseInt(typeStr);
			if(type != 0 && type != 1){
				setEncodeResult(result, ErrorCodeEnum.TYPE_IS_INCORRECT, request, response);
				return;
			}
			
			logger.debug("User info: username: " + username + ", verifyCode: " + verifyCode);
			boolean isExistedAcc = memberToolService.isAccExisted(username);
			//判断用户是否存在
			if(type == 0){            //注册操作
				if (isExistedAcc){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EXISTED, request, response);
					return;
				}
			}else{                    //忘记密码操作
				if (!isExistedAcc){
					setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
					return;
				}
			}
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
			logger.warn("[ERROR]校验验证码异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/getInfo")
	public void getInfo(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		MemberBaseinfoDTO memberDtoInput = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
		} catch (Exception e1) {
			logger.error("[ERROR]获取用户数据异常", e1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		Long memberId = memberDtoInput.getMemberId();
		String cityName = memberDtoInput.getCityName();
		if(memberId == null){
			setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		try{
			MemberBaseinfoDTO memberInfo = memberToolService.getById(memberId+"");
			if (null == memberInfo){
				setEncodeResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
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
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch (Exception e) {
			logger.warn("[ERROR]获取会员信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 农速宝APP,个人中心,当前用户在个人中心需要维护姓名,性别
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/updateAppMember")
	public void updateAppMember(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		MemberBaseinfoDTO memberDtoInput = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
		} catch (Exception e1) {
			logger.error("[ERROR]更新会员数据异常", e1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		Long id = memberDtoInput.getMemberId();
		String actityNo = memberDtoInput.getActityNo();
		String nst_CompanyAddress = memberDtoInput.getNst_CompanyAddress();
		
		MemberBaseinfoDTO mt = null;
		if(id == null){
			setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		
		try {
			//判断用户是否存在, 如果不存在,则看是否是工作人员提供的推荐码,直接送积分,如果不是,提醒用户
			int flag =0;
			if(memberDtoInput.getActityNo()!=null&&!"".equals(memberDtoInput.getActityNo())){
				//APP前端输入的
				mt = memberToolService.getByMobile(actityNo);
				if (null == mt ){
						flag=2;
				}else{
					flag=1;
				}
			}
			
			MemberBaseinfoDTO memberDTO = memberToolService.getById(id + "");
			//判断用户是否存在
			if (null == memberDTO){
				setEncodeResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
				return;
			}
			
			String companyName ="";
			
//			//更改真名,进行判断是否传空
			String realName ="";
//			if(!StringUtils.isBlank(memberDtoInput.getRealName())){
//				if(!StringUtils.isBlank(memberDtoInput.getApp())){
//					if("IOS".equals(memberDtoInput.getApp())){
//						realName =  new String(memberDtoInput.getRealName().getBytes("utf-8"),"utf-8");
//					}
//			    }else{
//			    	realName =  memberDtoInput.getRealName();
//			    }
//				 
//			}
	    	realName =  memberDtoInput.getRealName();

			if(!StringUtils.isBlank(realName)){
				
			  	Pattern pattern = Pattern.compile("^[A-Za-z\u4e00-\u9fa5]+$");
		    	Matcher matcher = pattern.matcher(realName);
		    	if (!matcher.matches()) {
					result.setObject(null);
					setEncodeResult(result, ErrorCodeEnum.INPUT_NOT_CHINSES_ENGLISH, request, response);
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
					setEncodeResult(result, ErrorCodeEnum.INPUT_NOT_CHINSES_ENGLISH, request, response);
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
					setEncodeResult(result, ErrorCodeEnum.ACCOUNT_ALREADY_RECOMMEND, request, response);
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
		    	   setEncodeResult(result, ErrorCodeEnum.RECOMMEND_CODE_INCRECT, request, response);
					return;
		       }
		       if(msg==3){
		    	   setEncodeResult(result, ErrorCodeEnum.RECOMMEND_CODE_ALREADY_USED, request, response);
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
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]更新用户信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 根据手机号码获取会员信息
	 * @param mobile
	 * @param request
	 */
	@RequestMapping("getByMobile")
	public void getMemberByMobile(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		
		MemberBaseinfoDTO memberDtoInput = null;
		try {
			String jsonStr = getParamDecodeStr(request);
			memberDtoInput = (MemberBaseinfoDTO) GSONUtils.fromJsonToObject(jsonStr, MemberBaseinfoDTO.class);
		} catch (Exception e1) {
			logger.error("[ERROR]获取会员信息数据异常", e1);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
		
		try{
			MemberBaseinfoDTO memberDTO = memberToolService.getByAccount(memberDtoInput.getMobile());
			if(memberDTO == null){
				memberDTO = memberToolService.getByMobile(memberDtoInput.getMobile());
			}
			if(memberDTO == null){
				setEncodeResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
			}else{
				memberDTO.setPassword("");
				memberDTO.setDevice_tokens("");
				result.setObject(memberDTO);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}
		}catch(Exception e){
			logger.info("获取会员信息异常：", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
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
