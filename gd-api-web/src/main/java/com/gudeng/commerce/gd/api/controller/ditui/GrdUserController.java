package com.gudeng.commerce.gd.api.controller.ditui;

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

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.api.service.ditui.GrdMemberToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.JavaMd5;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.SyncUtil;
import com.gudeng.commerce.gd.api.util.Validator;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 
 * @author Semon
 * 2016/06/13
 */
@Controller
@RequestMapping("/ditui")
public class GrdUserController extends GDAPIBaseController  {
	
	/** 记录日志־ */
	private static Logger logger = LoggerFactory.getLogger(GrdUserController.class);	
	
	@Autowired
	private GrdMemberToolService grdMemberToolService;
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	/**
	 * 
	 * @return
	 * 登录
	 */
	@RequestMapping("/login")
	public void dituiLogin(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String mobileStr = (String)GSONUtils.getJsonValueStr(jsonStr, "mobile");
			String pwdStr = (String)GSONUtils.getJsonValueStr(jsonStr, "password");
			if(StringUtils.isBlank(mobileStr)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}
			if(StringUtils.isBlank(pwdStr)){
				setEncodeResult(result, ErrorCodeEnum.PASSWORD_IS_EMPTY, request, response);
				return;
			}
			if(!Validator.isMobile(mobileStr)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
				return;
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mobile", mobileStr);
			
			//获取用户
			GrdMemberDTO user = grdMemberToolService.getMemberDTO(map);
			
			//判断用户是否禁用
			if(null==user){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			
			//判断用户是否禁用
			if(!"1".equals(user.getStatus())){
				setEncodeResult(result, ErrorCodeEnum.ACCOUNT_IS_DISABLE, request, response);
				return;
			}
			
			//判断密码是否正确
			String sec_pwd=JavaMd5.getMD5(pwdStr+"gudeng2015@*&^-").toUpperCase();
			if(!sec_pwd.equals(user.getPassword())){
				setEncodeResult(result, ErrorCodeEnum.PASSWORD_ERROR, request, response);
				return;
			}
			List<Integer> userType = grdMemberToolService.getUserType(user.getId());
			boolean nsyUser=userType.contains(1);
			boolean nstUser=userType.contains(2);
			if(nsyUser&&nstUser){
				user.setUserType(3);
			}else if(nsyUser && !nstUser){
				user.setUserType(1);
			}else if(nstUser&&!nsyUser){
				user.setUserType(2);
			}
			GrdMemberDTO updateUser=new GrdMemberDTO();
			updateUser.setId(user.getId());
			updateUser.setNeedLogin(0);
			grdMemberToolService.update(updateUser);//重新登录成功后，设置为不需要重登。
			result.setObject(user);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			
		}catch(Exception e){
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/*********
	 * 
	 * @return
	 * 修改密码
	 ********/
	@RequestMapping("/modifyPwd")
	public void modifyPwd(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String newPasswordStr = (String)GSONUtils.getJsonValueStr(jsonStr, "newPassword");
			String repeatNewPasswordStr = (String)GSONUtils.getJsonValueStr(jsonStr, "repeatNewPassword");
			String mobile = (String)GSONUtils.getJsonValueStr(jsonStr, "mobile");
			if(StringUtils.isBlank(newPasswordStr)
					||StringUtils.isBlank(newPasswordStr)){
				setEncodeResult(result, ErrorCodeEnum.PASSWORD_IS_EMPTY, request, response);
				return;
			}
			if(StringUtils.isBlank(mobile)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}
			
			if(!newPasswordStr.equals(repeatNewPasswordStr)){
				setEncodeResult(result, ErrorCodeEnum.TWO_PASSWORD_NOT_MATCH, request, response);
				return;
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mobile", mobile);
			GrdMemberDTO user = grdMemberToolService.getMemberDTO(map);
			if(null==user){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			if(!user.getStatus().equals("1")){
				setEncodeResult(result, ErrorCodeEnum.ACCOUNT_IS_DISABLE, request, response);
				return;
			}
			//更新密码
			String sec_pwd=JavaMd5.getMD5(newPasswordStr+"gudeng2015@*&^-").toUpperCase();
			GrdMemberDTO userUpdate = new GrdMemberDTO();
			userUpdate.setId(user.getId());
			userUpdate.setPassword(sec_pwd);
			userUpdate.setStatus("1");
			userUpdate.setLoginStatus("1");
			int update = grdMemberToolService.update(userUpdate);
			if(update>=1){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
			}
			
		}catch(Exception e){
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 
	 * @return
	 * 发送验证码
	 */
	@RequestMapping("/sendCode")
	public void sendCode(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String mobileStr = (String) GSONUtils.getJsonValueStr(jsonStr, "mobile");

			if (StringUtils.isBlank(mobileStr)) {
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}

			if (!Validator.isMobile(mobileStr)) {
				setEncodeResult(result, ErrorCodeEnum.MOBILE_INCORRECT, request, response);
				return;
			}

			// 如果验证码已存在并且还在60秒内, 默认返回成功
			if(ErrorCodeEnum.SUCCESS == CommonUtils.isVerifyMsgInSeconds(mobileStr, 60, redisClient)){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				return;
			}

			// 发送验证码
			String verifycode = CommonUtils.genVerfiyCode(true, 6);
			logger.debug("手机: " + mobileStr + ",验证码: " + verifycode);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mobileStr);
			map.put("status", "1");

			// 获取用户
			GrdMemberDTO user = grdMemberToolService.getMemberDTO(map);
			boolean isExistedAcc = user == null ? false : true;

			// 取redis缓存,获取通道号
			String channel = "";
			try {
				Object obj = redisClient.get("GDSMS_CHANNEL");
				System.out.println("redis channel:###############" + obj);
				channel = obj == null ? "" : obj.toString();
				System.out.println("redis channel:###############" + channel);
			} catch (Exception e) {
				// 处理redis服务器异常
				e.printStackTrace();
				logger.info("获取redis 消息通道出错!");
			}
			String content = null;

			if (!isExistedAcc) {
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			if (Constant.Alidayu.REDISTYPE.equals(channel)) {
				content = CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE1, verifycode);
			} else {
				content = MessageTemplateEnum.FORGET_PWD.getTemplate().replace("{P}", verifycode);
			}
			logger.info("####################content: " + content);
			CommonUtils.sendMsg(channel, content, mobileStr);
			// 保存到缓存中
			SyncUtil.setVerifyCode(mobileStr, verifycode, redisClient);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]获取验证异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 
	 * @return
	 * 校验验证码
	 */
	@RequestMapping("/checkCode")
	public void checkCode(HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try {		
			String jsonStr = getParamDecodeStr(request);
			String mobileStr = (String) GSONUtils.getJsonValueStr(jsonStr, "mobile");
			String verifyCode = (String) GSONUtils.getJsonValueStr(jsonStr, "verifyCode");
//			String mobileStr = request.getParameter("mobile");
//			String verifyCode = request.getParameter("verifyCode");
			if(StringUtils.isBlank(mobileStr)){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_IS_EMPTY, request, response);
				return;
			}
			
			if(StringUtils.isBlank(verifyCode)){
				setEncodeResult(result, ErrorCodeEnum.VERIFYCODE_IS_EMPTY, request, response);
				return;
			}
			logger.debug("User info: username: " + mobileStr + ", verifyCode: " + verifyCode);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mobileStr);
			map.put("status", "1");
			// 获取用户
			GrdMemberDTO user = grdMemberToolService.getMemberDTO(map);
			boolean isExistedAcc = user == null ? false : true;
			
			if (!isExistedAcc){
				setEncodeResult(result, ErrorCodeEnum.MOBILE_NOT_EXISTED, request, response);
				return;
			}
			//比较校验码
			ErrorCodeEnum verifyResult = CommonUtils.isVerifyMsgInSeconds(mobileStr, 300, redisClient);
			if(ErrorCodeEnum.SUCCESS != verifyResult){ 
				setEncodeResult(result, verifyResult, request, response);
					return;
			}
			
			if(!CommonUtils.isVerifyCodeMatched(mobileStr, verifyCode, redisClient)){
				setEncodeResult(result, ErrorCodeEnum.VERIFYCODE_INCORRECT, request, response);
				return;
			}
			
			//验证码已校验正确，移除
			SyncUtil.removeVerifyCode(mobileStr, redisClient);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]校验验证码异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
