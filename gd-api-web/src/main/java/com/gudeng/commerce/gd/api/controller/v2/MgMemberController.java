package com.gudeng.commerce.gd.api.controller.v2;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.JavaMd5;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.UserSettingPropertyUtils;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

@Controller
@RequestMapping("v2/mgMember")
public class MgMemberController extends GDAPIBaseController{

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MgMemberController.class);

	@Autowired
	public MemberToolService memberToolService;
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	/**
	 * 门岗注册用户
	 * @param mobile
	 * @param request
	 * @param response
	 */
	@RequestMapping("register")
	public void register(String mobile, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		String password = "";
		try{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			//判断用户是否存在,用户存在返回空memberId
			if (memberToolService.isAccExisted(mobile)){
//				setResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "用户已存在", request, response);
				resultMap.put("memberId", "");
				result.setObject(resultMap);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				renderJson(result, request, response);
				return;
			}
			
			MemberBaseinfoEntity memberEntity = new MemberBaseinfoEntity();
			memberEntity.setAccount(mobile);
			memberEntity.setMobile(mobile);
			memberEntity.setLevel(3);//农批商
			memberEntity.setStatus("1");
			memberEntity.setCreateTime(new Date());
			memberEntity.setRegetype("5");//注册来源门岗
			//随机生成8位默认密码
			password = CommonUtils.getEightCode();
			memberEntity.setPassword(JavaMd5.getMD5(password+UserSettingPropertyUtils.getEncrytphrase("gd.encrypt.key")).toUpperCase());
			Long memberId = memberToolService.addMemberBaseinfoEnt(memberEntity);
			
			//返回用户id
			resultMap.put("memberId", memberId);
			result.setObject(resultMap);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){
			e.printStackTrace();
			logger.info("注册失败!");
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		
		//发送短信:已经帮您注册成功农批商app,默认密码为XXXXXX您可以登录后自行修改，下载地址：XXXXXXX【谷登科技】
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
		if(Constant.Alidayu.REDISTYPE.equals(channel)){
			try {
				content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE4,password);
			} catch (JSONException e) {
				logger.info("调用阿里大鱼消息通道出错!");
				e.printStackTrace();
			}
			
		}else{
			String appDownloadUrl = UserSettingPropertyUtils.getEncrytphrase("gd.app.downloadUrl");
			content= String.format(MessageTemplateEnum.AUTO_REGIST.getTemplate(), password, appDownloadUrl);
		}
		logger.info("##############content"+content);
		//CommonUtils.autoRegistSendMsg(channel,content, mobile);
		CommonUtils.sendMsg(channel,content, mobile); 
		renderJson(result, request, response);
	}
	
	@RequestMapping("getByMobile")
	public void getMemberByMobile(String mobile, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try{
			MemberBaseinfoDTO memberDTO = memberToolService.getByAccount(mobile);
			if(memberDTO == null){
				memberDTO = memberToolService.getByMobile(mobile);
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(memberDTO != null){
				memberDTO.setPassword("");
				memberDTO.setDevice_tokens("");
				resultMap.put("memberId", memberDTO.getMemberId());
			}else{
				resultMap.put("memberId", "");
			}
			// 返回memberId
			result.setObject(resultMap);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){
			logger.info("获取会员信息异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
		}
		renderJson(result, request, response);
	}
	
	@RequestMapping("updateMember")
	public void updateMember(String memberId, HttpServletRequest request, HttpServletResponse response){
		try {
			MemberBaseinfoDTO mb = memberToolService.getById(memberId);
			memberToolService.updateMember(mb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
