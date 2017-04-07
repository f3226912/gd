package com.gudeng.commerce.gd.api.controller.pos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MarketToolService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductCategoryToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JavaMd5;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.UserSettingPropertyUtils;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * @Description: TODO(会员信息控制中心)
 * @author mpan
 * @date 2016年3月1日 上午10:35:06
 */
@Controller(value="posMemberController")
@RequestMapping("pos/member")
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
			if(device!=null&&memberDTO.getLevel()==4&&device==4){
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
			//add by yanghaoyu ,新增加参数,userType,如果当前用户是 公司,还是个人
			if(memberDTO.getUserType()==null){
				map.put("userType", 0);
			}else{
				map.put("userType", memberDTO.getUserType());
			}
			
			map.put("realName", memberDTO.getRealName());
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
			logger.error("[ERROR]查找用户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
		Long busiMemberId = memberDtoInput.getBusiMemberId();
		String mobile = memberDtoInput.getMobile();
		String realName = memberDtoInput.getRealName();
		if(busiMemberId == null){
			setResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
			return;
		}
		if(StringUtils.isBlank(mobile)){
			setResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
			return;
		}
		
		if(!Validator.isMobile(mobile)){
			setResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
			return;
		}
		
		try {
			//判断用户是否存在
			MemberBaseinfoDTO memberInfo = memberToolService.checkAccExisted(mobile);
			if (memberInfo != null){
//				setResult(result, ErrorCodeEnum.FAIL.getValue(), "用户名已存在", request, response);
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("memberId", memberInfo.getMemberId());
				result.setObject(resultMap);
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}
			MemberBaseinfoDTO memberDto = new MemberBaseinfoDTO();
			memberDto.setBusiMemberId(memberDtoInput.getBusiMemberId());
			memberDto.setAccount(mobile);
			memberDto.setMobile(mobile);
			memberDto.setRealName(realName);
			memberDto.setLevel(3);//农商友
			memberDto.setStatus("1");
			memberDto.setCreateTime(new Date());
			if(StringUtils.isBlank(memberDtoInput.getRegetype())){
				memberDto.setRegetype(MemberBaseinfoEnum.REGETYPE_4.getKey());//pos注册用户
			} else {
				memberDto.setRegetype(memberDtoInput.getRegetype());//pos注册用户
			}
			//随机生成8位默认密码
			String password = CommonUtils.getEightCode();
			memberDto.setPassword(JavaMd5.getMD5(password+UserSettingPropertyUtils.getEncrytphrase("gd.encrypt.key")).toUpperCase());
			Long memberId = memberToolService.addMemberEnt(memberDto);
			
			//发送短信:已经帮您注册成功农批商app,默认密码为XXXXXX您可以登录后自行修改，下载地址：XXXXXXX【谷登科技】
			//取redis缓存,获取通道号
			String channel = "";
			try{
				Object obj = redisClient.get("GDSMS_CHANNEL");
				channel = obj==null? Constant.Alidayu.DEFAULTNO:obj.toString();
			}catch(Exception e){
				logger.info("获取redis 消息通道出错!");
			}
			String content=null;
			if(Constant.Alidayu.REDISTYPE.equals(channel)){
				content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE4,password);
				
			}else{
				String appDownloadUrl = UserSettingPropertyUtils.getEncrytphrase("gd.app.downloadUrl");
				content= String.format(MessageTemplateEnum.AUTO_REGIST.getTemplate(), password, appDownloadUrl);
			}
			logger.info("##############content"+content);
			//CommonUtils.autoRegistSendMsg(channel,content, mobile);
			CommonUtils.sendMsg(channel,content, mobile); 
			//返回用户id
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("memberId", memberId);
			result.setObject(resultMap);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]用户注册异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/queryMemberInfo")
	public void queryMemberInfo(HttpServletRequest request, HttpServletResponse response, MemberBaseinfoDTO memberDtoInput) {
		ObjectResult result = new ObjectResult();
//		Long busiMemberId = memberDtoInput.getBusiMemberId();
//		if(busiMemberId == null){
//			setResult(result, ErrorCodeEnum.FAIL.getValue(), "商家会员id不能为空", request, response);
//			return;
//		}
		try {
			List<MemberBaseinfoDTO> memberList = memberToolService.queryMemberListByMap(memberDtoInput);
			result.setObject(memberList);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询客户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

}
