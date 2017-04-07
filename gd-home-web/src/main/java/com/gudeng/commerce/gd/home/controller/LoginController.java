package com.gudeng.commerce.gd.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.customer.dto.IpAddressBlackDTO;
import com.gudeng.commerce.gd.customer.dto.IpAddressLogDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.MemberLoginLogEntity;
import com.gudeng.commerce.gd.home.Constant;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.IpAddressService;
import com.gudeng.commerce.gd.home.service.LoginToolService;
import com.gudeng.commerce.gd.home.service.MarketToolService;
import com.gudeng.commerce.gd.home.service.MemberLoginLogToolService;
import com.gudeng.commerce.gd.home.service.ProCategoryToolService;
import com.gudeng.commerce.gd.home.service.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.home.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.home.util.CommonUtil;
import com.gudeng.commerce.gd.home.util.IpUtils;
import com.gudeng.commerce.gd.home.util.JavaMd5;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;
import com.gudeng.paltform.sms.GdSMS;

@Controller
@RequestMapping("login")
public class LoginController extends HomeBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(LoginController.class);

	@Autowired
	public LoginToolService loginToolService;
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	public MarketToolService marketToolService;
	
	@Autowired
	public ProCategoryToolService proCategoryToolService;
	
	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;
	
	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;
	
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	@Autowired
	private IpAddressService ipAddressService;
	
	@Autowired
	private MemberLoginLogToolService memberLoginLogToolService;
	
	
	/**
	 * 初始化登录页面
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("initLogin")
	public String initLogin(HttpServletRequest request,ModelMap map){
		String BackUrl = request.getParameter("BackUrl");
		map.put("BackUrl", BackUrl);
		return "login/login";
	}
	
	@RequestMapping("initImg")
	public String initImg(HttpServletRequest request,ModelMap map){
		return "login/img";
	}
	 
	/**
	 * 注册页面图片验证码
	 * @param request
	 * @param map
	 * @return
	 * @author lidong
	 */
	@RequestMapping("initImg2")
	public String initImg2(HttpServletRequest request,ModelMap map){
		return "login/img2";
	}
	
	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="commitLogin",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String commitLogin(HttpServletRequest request,HttpServletResponse response,ModelMap rmap) {
		try {
			HttpSession session = request.getSession();
			String loginName = request.getParameter("loginName");
			String loginPassword = request.getParameter("loginPassword");
			String randCode = request.getParameter("randCode");
			//String remember = request.getParameter("remember");
			String rand = (String) session.getAttribute("rand");
			Map<String, Object> map = new HashMap<String, Object>();
			if(null != randCode && randCode.equals(rand)){
				if (loginName != null && !"".equals(loginName)){
					/*String regexMobile = "^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(18[0,0-9]))\\d{8}$";
					Pattern patternMobile = Pattern.compile(regexMobile);
					Matcher matcherMobile = patternMobile.matcher(loginName);
					String regexEmail = "[0-9a-zA-Z]+@[0-9a-zA-Z]+//.[0-9a-zA-Z]+";
					Pattern patternEmail = Pattern.compile(regexEmail);
					Matcher matcherEmail = patternEmail.matcher(loginName);
					if(matcherMobile.matches()){
						map.put("mobile", loginName);
					}else if(matcherEmail.matches()){
						map.put("email", loginName);
					}else{
						map.put("account", loginName);
					}*/
					map.put("loginName", loginName);
					List<MemberBaseinfoDTO> mbdtoList = loginToolService.commitLogin(map);
					if(null != mbdtoList && mbdtoList.size() > 0){
						if(mbdtoList.size() == 1){
							MemberBaseinfoDTO mbdto = mbdtoList.get(0);
							if(null != loginPassword){
								loginPassword = JavaMd5.getMD5(loginPassword+"gudeng2015@*&^-").toUpperCase();
								if(null != mbdto.getPassword() && loginPassword.equals(mbdto.getPassword())){
									/*if("1".equals(remember)){
										addCookie(response, Constant.COOKIE_LOGINNAME, loginName, 604800);
									}*/
									if("1".equals(mbdto.getStatus())){
										insertLoginLog(mbdto,request);
										session.setAttribute(Constant.SYSTEM_LOGINUSER, mbdto);
										rmap.put("msg", "0");
									}else{
										rmap.put("msg", "此账户已经禁用!");
									}
								}else{
									rmap.put("msg", "登录密码错误!");
								}
							}else{
								rmap.put("msg", "登录密码不能为空!");
							}
						}else{
							rmap.put("msg", "帐号异常,请联系客服!");
						}
					}else{
						rmap.put("msg", "登录帐号不存在!");
					}
				}else{
					rmap.put("msg", "登录帐号不能为空!");
				}
			}else{
				rmap.put("msg", "验证码错误!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "系统维护中!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="commitSmallLogin",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String commitSmallLogin(HttpServletRequest request,HttpServletResponse response,ModelMap rmap) {
		try {
			HttpSession session = request.getSession();
			String loginName = request.getParameter("loginName");
			String loginPassword = request.getParameter("loginPassword");
			Map<String, Object> map = new HashMap<String, Object>();
			if (loginName != null && !"".equals(loginName)){
				/*String regexMobile = "^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(18[0,0-9]))\\d{8}$";
				Pattern patternMobile = Pattern.compile(regexMobile);
				Matcher matcherMobile = patternMobile.matcher(loginName);
				String regexEmail = "[0-9a-zA-Z]+@[0-9a-zA-Z]+//.[0-9a-zA-Z]+";
				Pattern patternEmail = Pattern.compile(regexEmail);
				Matcher matcherEmail = patternEmail.matcher(loginName);
				if(matcherMobile.matches()){
					map.put("mobile", loginName);
				}else if(matcherEmail.matches()){
					map.put("email", loginName);
				}else{
					map.put("account", loginName);
				}*/
				map.put("loginName", loginName);
				List<MemberBaseinfoDTO> mbdtoList = loginToolService.commitLogin(map);
				if(null != mbdtoList && mbdtoList.size() > 0){
					if(mbdtoList.size() == 1){
						MemberBaseinfoDTO mbdto = mbdtoList.get(0);
						if(null != loginPassword){
							loginPassword = JavaMd5.getMD5(loginPassword+"gudeng2015@*&^-").toUpperCase();
							if(null != mbdto.getPassword() && loginPassword.equals(mbdto.getPassword())){
								/*if("1".equals(remember)){
									addCookie(response, Constant.COOKIE_LOGINNAME, loginName, 604800);
								}*/
								if("1".equals(mbdto.getStatus())){
									insertLoginLog(mbdto,request);
									session.setAttribute(Constant.SYSTEM_LOGINUSER, mbdto);
									rmap.put("msg", "0");
								}else{
									rmap.put("msg", "此账户已经禁用!");
								}
							}else{
								rmap.put("msg", "登录密码错误!");
							}
						}else{
							rmap.put("msg", "登录密码不能为空!");
						}
					}else{
						rmap.put("msg", "帐号异常,请联系客服!");
					}
				}else{
					rmap.put("msg", "登录帐号不存在!");
				}
			}else{
				rmap.put("msg", "登录帐号不能为空!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "系统维护中!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	
	/**
	 * 注销登录
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("loginOut")
	public String loginOut(HttpServletRequest request,HttpServletResponse response,ModelMap rmap) {
		try {
			request.getSession().removeAttribute(Constant.SYSTEM_LOGINUSER);
			//response.sendRedirect(request.getContextPath() + "/jsp/login/login.jsp");
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 初始化注册页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 * 
	 */
	@RequestMapping("initRegister")
	public String initRegister(HttpServletRequest request,ModelMap map) throws Exception{
		String BackUrl = request.getParameter("BackUrl");
		//获取店铺实体对象
		
		
		List<MarketDTO> markets=marketToolService.getAllByType("2");
		Map<Object,Object> mapM=new HashMap<Object,Object>();
		for(MarketDTO market:markets){
			List<ProductCategoryDTO> lsit = proCategoryToolService.listTopProductCategoryByMarketId(Long.valueOf(market.getId()));
			mapM.put(market.getId(), lsit);
		}
		
		putModel("mapM", mapM); 
		putModel("markets", markets);
		
		map.put("BackUrl", BackUrl);
		//return "login/register";  //暂时停用注册功能
		return "login/login";
		
	}
	
	/**
	 * 验证用户名
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="checkUserName",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkUserName(HttpServletRequest request,HttpServletResponse response,ModelMap rmap){
		try {
			String username = request.getParameter("username");
			Map<String, Object> map = new HashMap<String, Object>();
				if (username != null && !"".equals(username)){
					map.put("account", username);
					MemberBaseinfoDTO mbdto = loginToolService.getLogin(map);
					if(mbdto==null){
						map.clear();
						map.put("mobile", username);
						mbdto = loginToolService.getLogin(map);
					}
					if(mbdto==null){
						map.clear();
						map.put("email", username);
						mbdto = loginToolService.getLogin(map);
					}
					
					if(null != mbdto){
						rmap.put("msg", "用户名已存在!");
					}else{
						rmap.put("msg", "0");
					}
				}else{
					rmap.put("msg", "用户名不能为空!");
				}
				
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "系统维护中!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 验证手机号码
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="checkMobile",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkMobile(HttpServletRequest request,HttpServletResponse response,ModelMap rmap){
		try {
			String mobile = request.getParameter("mobile");
			Map<String, Object> map = new HashMap<String, Object>();
				if (mobile != null && !"".equals(mobile)){
					map.put("account", mobile);
					MemberBaseinfoDTO mbdto = loginToolService.getLogin(map);
					if(mbdto==null){
						map.clear();
						map.put("mobile", mobile);
						mbdto = loginToolService.getLogin(map);
					}
					if(mbdto==null){
						map.clear();
						map.put("email", mobile);
						mbdto = loginToolService.getLogin(map);
					}
					if(null != mbdto){
						rmap.put("msg", "手机号码已存在!");
					}else{
						rmap.put("msg", "0");
					}
				}else{
					rmap.put("msg", "手机号码不能为空!");
				}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "系统维护中!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 产生随机的六位数
	 * @return
	 */
	public String getSixCode(){
		Random rad=new Random();
		
		String result  = rad.nextInt(1000000) +"";
		
		if(result.length()!=6){
			return getSixCode();
		}
		return result;
	}	
	
	/**
	 * 注册发送手机验证码
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="sendCode",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendCode(HttpServletRequest request,HttpServletResponse response,ModelMap rmap){
		String ip = getIpAddr(request);
		Long sessionTime =System.currentTimeMillis();
		Map<String,Object> ipMap = new HashMap<String, Object>();
		ipMap.put("state", "1");
		ipMap.put("ipAddress", ip);
		int countBlack = 0;
		int countLog = 0;
		try {
			countBlack = ipAddressService.countBlackTotal(ipMap);
			if(countBlack>0){
				rmap.put("msg", "-1");
				rmap.put("code", "您已重复提交超过3次验证,请24小时后再试!");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
		} catch (Exception e1) {
			logger.info("统计查询黑名单异常");
			e1.printStackTrace();	
		}
		String mobile = request.getParameter("mobile");
		try {
			HttpSession session = request.getSession();
			String rand = (String) session.getAttribute("rand");
			String randCode = request.getParameter("randCode");
			if(rand==null||randCode==null||!rand.equals(randCode)){
				rmap.put("msg", "5");
				rmap.put("code", "验证码已过期！");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			Map<String, Long> checkmap =(Map<String, Long>) session.getAttribute(Constant.MOBILE_CHECK_TIME);
			if(checkmap!=null&&checkmap.get(mobile)!=null){
				//判断两次时间是否大于1分钟
				Long value=(System.currentTimeMillis()-checkmap.get(mobile))/1000;
				if(value<60){
					rmap.put("msg", "5");
					rmap.put("code", "请60s后发送验证码！");
					rmap.put("time",60-value);
					logger.info("##########剩余时间"+(60-value));
					return	JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
				}
			}
			if(StringUtils.isBlank(mobile)){
				rmap.put("msg", "3");
				rmap.put("code", "手机号码为空");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mobile);
			MemberBaseinfoDTO mbdto = loginToolService.getLogin(map);
			if(null != mbdto){
				rmap.put("msg", "4");
				rmap.put("code", "手机号码已存在!");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			//验证通过，清除图片验证码
			session.removeAttribute("rand");
			//取redis缓存,获取通道号
			String channel = "";
			try{
				Object obj = redisClient.get("GDSMS_CHANNEL");
				System.out.println("redis channel:###############"+ obj);
				channel = obj==null?Constant.Alidayu.DEFAULTNO:obj.toString();
				System.out.println("redis channel:###############"+ channel);
			}catch(Exception e){
				//处理redis服务器异常
				e.printStackTrace();
				logger.info("获取redis 消息通道出错!");
			}
			GdSMS sms = new GdSMS();
			String randi = getSixCode();
			String smsmsg =Constant.SMSMSG.replace("{code}",randi);
			System.out.println("----------------------------->"+randi);
			//阿里大鱼短信通道
			if(Constant.Alidayu.REDISTYPE.equals(channel)){
				smsmsg=CommonUtil.alidayuUtil(Constant.Alidayu.MESSAGETYPE2,randi);
			}
			boolean b= sms.SendSms(channel,smsmsg, mobile);
			//boolean b = false;
			if(b){
				//记录发送成功的手机号
				Map<String, Long> remaps =new HashMap<String, Long>();
				remaps.put(mobile, sessionTime);
				//记录验证码
				session.setAttribute(Constant.MOBILE_CHECK, randi.toString());
				session.setAttribute(Constant.MOBILE_CHECK+"vaild", sessionTime);
				session.setAttribute(Constant.MOBILE_CHECK_TIME, remaps);
				logger.info("######记录session时间"+ System.currentTimeMillis());
				rmap.put("msg", "1");
								
				IpAddressLogDTO addressLogDTO = new IpAddressLogDTO();
				addressLogDTO.setType("1");
				addressLogDTO.setIpAddress(ip);
				addressLogDTO.setChennel("1");
				addressLogDTO.setMobile(mobile);
				ipAddressService.saveLog(addressLogDTO);
				addressLogDTO = null;
				
				countLog = ipAddressService.countLogTotal(ipMap);
				if(countLog == 3){
					IpAddressBlackDTO addressBlackDTO = new IpAddressBlackDTO();
					addressBlackDTO.setType("1");
					addressBlackDTO.setIpAddress(ip);
					addressBlackDTO.setChennel("1");
					addressBlackDTO.setState("1");
					ipAddressService.saveBlack(addressBlackDTO);
					addressBlackDTO = null;					
				}
			}else{
				rmap.put("msg", "27");
				rmap.put("code", "发送短信验证码失败,请重试!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "27");
			rmap.put("code", "服务器异常");
		}
		
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 找回密码发送手机验证码
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="sendCodeByGetPassword",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendCodeByGetPassword(HttpServletRequest request,HttpServletResponse response,ModelMap rmap){
		try {
			HttpSession session = request.getSession();
			String mobile = request.getParameter("mobile");
			if(StringUtils.isBlank(mobile)){
				rmap.put("msg", "3");
				rmap.put("code", "手机号码为空");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			
			//取redis缓存,获取通道号
			String channel = "";
			try{
				Object obj = redisClient.get("GDSMS_CHANNEL");
				System.out.println("redis channel:###############"+ obj);
				channel = obj==null?Constant.Alidayu.DEFAULTNO:obj.toString();
				System.out.println("redis channel:###############"+ channel);
			}catch(Exception e){
				//处理redis服务器异常
				e.printStackTrace();
				logger.info("获取redis 消息通道出错!");
			}
			GdSMS sms = new GdSMS();
			//LxtSMS sms = new LxtSMS();
			String randi = getSixCode();
			String smsmsg = "您好,谷登农批网手机验证码:"+randi+"【谷登科技】";
			//阿里大鱼短信通道
			if(Constant.Alidayu.REDISTYPE.equals(channel)){
				smsmsg=CommonUtil.alidayuUtil(Constant.Alidayu.MESSAGETYPE1,randi);
			}
			//boolean b=sms.SendSms(channel,smsmsg, mobile);
			boolean b=false;
			if(b){
				session.setAttribute(Constant.MOBILE_CHECK, randi.toString());
				rmap.put("msg", "1");
			}else{
				rmap.put("msg", "27");
				rmap.put("code", "发送短信验证码失败,请重试!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "27");
			rmap.put("code", "发送短信验证码失败,请重试!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 确认验证码
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="checkCode",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkCode(HttpServletRequest request,HttpServletResponse response,ModelMap rmap){
		try {
			HttpSession session = request.getSession();
			String code = request.getParameter("code");
			String scode = (String) session.getAttribute(Constant.MOBILE_CHECK);
			//数据判断 semon 20151031
			if(StringUtils.isBlank(code)||
					StringUtils.isBlank(scode)){
				rmap.put("msg", "3");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			
			
			if(code.equals(scode)){
				rmap.put("msg", "1");
			}else{
				rmap.put("msg", "3");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "27");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 初始化告知页面
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("initAgreement")
	public String initAgreement(HttpServletRequest request,ModelMap map){
		String BackUrl = request.getParameter("BackUrl");
		map.put("BackUrl", BackUrl);
		return "login/agreement";
	}
	
	/**
	 * 注册
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="register",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String register(HttpServletRequest request,HttpServletResponse response,ModelMap rmap) {
		try {
			HttpSession session = request.getSession();
			//String username = request.getParameter("username");
			String password = request.getParameter("password");
			String mobile = request.getParameter("mobile");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mobile", mobile);
			//注册用户名手机号
			map.put("account", mobile);
			map.put("email", mobile);
			MemberBaseinfoDTO mbdto = loginToolService.checkUser(map);
			if(mbdto!=null){
				rmap.put("code", "9");
				rmap.put("msg", "已存在该用户");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			//验证码判断逻辑 20151031，之前那个方法先预留
			String code = request.getParameter("mobileVerifyCode");
			String scode = (String) session.getAttribute(Constant.MOBILE_CHECK);
			//数据判断 semon 20151031
			if(StringUtils.isBlank(code)||
					StringUtils.isBlank(scode)){
				rmap.put("code", "5");
				rmap.put("msg", "验证码为空");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			long timeVaild = (Long) session.getAttribute(Constant.MOBILE_CHECK+"vaild");
			long nowtime = System.currentTimeMillis();
			if(nowtime-timeVaild>1*60*1000){
				rmap.put("code", "6");
				rmap.put("msg", "验证码过期");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			
			if(!code.equals(scode)){
				rmap.put("code", "4");
				rmap.put("msg", "验证码错误");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			
			
			// 20151030 semon start
			String regType = request.getParameter("regType");//注册类型0：采购商，1：农批商，2：产地供应商
			//data需要返回{"code":"1","regType":"0","regName":"sss","regstep":"2"}
			if(StringUtils.isBlank(regType)){
				rmap.put("code", "error");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
					
			MemberBaseinfoEntity mb = new MemberBaseinfoEntity();
			String md5password = JavaMd5.getMD5(password+"gudeng2015@*&^-").toUpperCase();
			mb.setAccount(mobile);
			mb.setPassword(md5password);
			mb.setMobile(mobile);
			mb.setStatus("1");
			mb.setCreateTime(new Date());
		    // regetype， 0表示管理后台创建的用户，1表示web前端注册的用户，2表示农速通注册的用户，3表示农商友-买家版注册的用户，4表示农商友-卖家版注册的用户，5表示门岗添加的用户
			mb.setRegetype("1");
			// 20151030 semon 修改注册类型
			if(regType.equals("2")){
				mb.setLevel(4);
			}else{
				mb.setLevel(1);
			}
			
			Long rl = loginToolService.register(mb);
			if(rl > 0){
				MemberBaseinfoDTO mbd = loginToolService.getById(String.valueOf(rl));
				session.setAttribute(Constant.SYSTEM_LOGINUSER, mbd);
				session.removeAttribute(Constant.MOBILE_CHECK+"vaild");
				rmap.put("code", "1");
				rmap.put("regType", regType);
				rmap.put("regName", mobile);
				rmap.put("regstep", 2);
			}else{
				rmap.put("code", "error");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("code", "系统维护中!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	
	
	/**
	 * 农批商注册第二步
	 * @param request
	 * @param response
	 * @param rmap
	 * @return
	 */
	@RequestMapping(value="npsStep",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String npsStep(HttpServletRequest request,HttpServletResponse response,ModelMap rmap) {
		try {
			String shopName = request.getParameter("shopname");
			String tradeType = request.getParameter("tradetype");
			String market = request.getParameter("market");
			String[] categoryIds = request.getParameterValues("categoryId");
			
			if(StringUtils.isBlank(shopName)||
					StringUtils.isBlank(tradeType)||
						StringUtils.isBlank(market)||
							categoryIds.length<=0){
				rmap.put("code", "error");
				rmap.put("msg", "非法请求");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			
			//先新增一个商铺
			MemberBaseinfoDTO mbe=getUser(request);
			MemberBaseinfoDTO mbd=new MemberBaseinfoDTO();
			mbd.setMemberId(mbe.getMemberId());
			mbd.setLevel(1);

			BusinessBaseinfoEntity bb=new BusinessBaseinfoEntity();
			bb.setUserId(mbe.getMemberId());
			bb.setBusinessModel(Integer.parseInt(tradeType));
			
			bb.setName(shopName);
			bb.setShopsName(shopName);
			long businessId=businessBaseinfoToolService.addBusinessBaseinfoEnt(bb);
			
			
			ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
			rbm.setBusinessId(businessId);
			rbm.setMarketId(Long.valueOf(market));
			reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
			
			for(String cateId:categoryIds){
				ReBusinessCategoryDTO rbc=new ReBusinessCategoryDTO();
				rbc.setBusinessId(businessId);
				rbc.setCategoryId(Long.valueOf(cateId));
				reBusinessCategoryToolService.addReBusinessCategoryDTO(rbc);
			}
			rmap.put("code", "1");
			rmap.put("regName",mbe.getAccount() );
			rmap.put("regType", 1);
			rmap.put("regstep", 3);
			rmap.put("msg", "注册成功");
			
		}catch(Exception e){
			e.printStackTrace();
			rmap.put("code", "error");
			rmap.put("msg", "服务器繁忙");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 产地供应商第二步
	 * @param request
	 * @param response
	 * @param rmap
	 * @return
	 */
	@RequestMapping(value="cdsStep",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String cdsStep(HttpServletRequest request,HttpServletResponse response,ModelMap rmap) {
		try {
			String shopName = request.getParameter("shopname");
			String tradeType = request.getParameter("tradetype");
			String tradeProduct = request.getParameter("tradeProduct");
			
			if(StringUtils.isBlank(shopName)||
					StringUtils.isBlank(tradeType)){
				rmap.put("code", "error");
				rmap.put("msg", "非法请求");
				return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
			}
			
			//先新增一个商铺
			MemberBaseinfoDTO mbe=getUser(request);
			MemberBaseinfoDTO mbd=new MemberBaseinfoDTO();
			mbd.setMemberId(mbe.getMemberId());
			mbd.setLevel(1);

			BusinessBaseinfoEntity bb=new BusinessBaseinfoEntity();
			bb.setUserId(mbe.getMemberId());
			bb.setBusinessModel(Integer.parseInt(tradeType));
			bb.setMainProduct(tradeProduct);
			
			bb.setName(shopName);
			bb.setShopsName(shopName);
			long businessId=businessBaseinfoToolService.addBusinessBaseinfoEnt(bb);
			
			
			ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
			rbm.setBusinessId(businessId);
			reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
			

			rmap.put("code", "1");
			rmap.put("regType", 2);
			rmap.put("regName",mbe.getAccount() );
			rmap.put("regstep", 3);
			rmap.put("msg", "注册完成");
			
		}catch(Exception e){
			e.printStackTrace();
			rmap.put("code", "error");
			rmap.put("msg", "服务器繁忙");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}	
	
	
	
	
	/**
	 * 初始化注册成功
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("initRegisterSucc")
	public String initRegisterSucc(HttpServletRequest request,ModelMap map){
		return "login/register-succ";
	}
	
	/**
	 * 初始化找回密码1
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("initGetPassword1")
	public String initGetPassword1(HttpServletRequest request,ModelMap map){
		return "login/getPassword-mobile";
	}
	
	/**
	 * 找回密码1
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="commitGetPassword1",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String commitGetPassword1(HttpServletRequest request,ModelMap rmap){
		try {
			String username = request.getParameter("userName");
			HttpSession session = request.getSession();
			String randCode = request.getParameter("randomCode");
			String rand = (String) session.getAttribute("rand");
			Map<String, Object> map = new HashMap<String, Object>();
			if(null != randCode && randCode.equals(rand)){
				if (username != null && !"".equals(username)){
					map.put("account", username);
					MemberBaseinfoDTO mbdto = loginToolService.getLogin(map);
					if(null != mbdto){
						session.setAttribute(Constant.PASS_CHECK, "true");
						rmap.put("dto", mbdto);
						rmap.put("msg", "0");
					}else{
						rmap.put("msg", "用户名不存在!");
					}
				}else{
					rmap.put("msg", "用户名不能为空!");
				}
			}else{
				rmap.put("msg", "验证码错误!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "系统维护中!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 初始化找回密码2
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("initGetPassword2/{id}")
	public String initGetPassword2(@PathVariable("id") String id,HttpServletRequest request,ModelMap map){
		try {
			MemberBaseinfoDTO mbdto = loginToolService.getById(id);
			String mobile = "该账户没有绑定手机号";
			if(null != mbdto && null != mbdto.getMobile()){
				mobile = mbdto.getMobile().substring(0, 3) + "****" + mbdto.getMobile().substring(7);
			}
			map.put("mobile", mobile);
			map.put("dto", mbdto);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			map.put("msg", "系统维护中!");
		}
		return "login/getPassword-mobile-step2";
	}
	
	
	/**
	 * 找回密码2
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="commitGetPassword2",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String commitGetPassword2(HttpServletRequest request,ModelMap rmap){
		try {
			HttpSession session = request.getSession();
			String code = request.getParameter("mobileVerifyCode");
			String scode = (String) session.getAttribute(Constant.MOBILE_CHECK);
			if(code.equals(scode)){
				String passcheck = (String) session.getAttribute(Constant.PASS_CHECK);
				if("true".equals(passcheck)){
					session.setAttribute(Constant.PASS_CHECK, "true2");
					rmap.put("msg", "0");
				}else{
					rmap.put("msg", "非法跳转链接");
				}
			}else{
				rmap.put("msg", "短信验证码错误!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "系统维护中!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 初始化找回密码3
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("initGetPassword3/{id}")
	public String initGetPassword3(@PathVariable("id") String id,HttpServletRequest request,ModelMap map){
		try {
			map.put("id", id);
			map.put("msg", "0");
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			map.put("msg", "系统维护中!");
		}
		return "login/getPassword-step3";
	}
	
	/**
	 * 找回密码3
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="commitGetPassword3",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String commitGetPassword3(HttpServletRequest request,ModelMap rmap){
		try {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			HttpSession session = request.getSession();
			MemberBaseinfoDTO mbdto = loginToolService.getById(id);
			String md5password = JavaMd5.getMD5(password+"gudeng2015@*&^-").toUpperCase();
			mbdto.setPassword(md5password);
			int ri = loginToolService.updatePassword(mbdto);
			if(ri > 0){
				String passcheck = (String) session.getAttribute(Constant.PASS_CHECK);
				if("true2".equals(passcheck)){
					rmap.put("msg", "0");
				}else{
					rmap.put("msg", "非法跳转链接");
				}
			}else{
				rmap.put("msg", "修改密码失败!");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "系统维护中!");
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 初始化找回密码3
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("initGetPassword4")
	public String initGetPassword4(HttpServletRequest request,ModelMap map){
		return "login/getPassword-success";
	}
	
	
	/**
	 * 判读用户是否登录
	 * 
	 * @param request
	 * @author xiaojun
	 * @return
	 */
	@RequestMapping("/isLogin")
	@ResponseBody
	public String isLogin(HttpServletRequest request,ModelMap rmap){
		
		HttpSession session=request.getSession();
		
		/** 从session获取用户*/
		MemberBaseinfoDTO mbdto=(MemberBaseinfoDTO)session.getAttribute(Constant.SYSTEM_LOGINUSER);
		
		if (mbdto!=null) {
			rmap.put("mbdto", mbdto);
		}
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	/**
	 * 判读用户是否登录
	 * 
	 * @param request
	 * @author xiaojun
	 * @return
	 */
	@RequestMapping("/confirm")
	@ResponseBody
	public String confirm(HttpServletRequest request,ModelMap rmap){
		HttpSession session=request.getSession();
		String rand = (String) session.getAttribute("rand");
		String code = request.getParameter("code");
		if(code==null||code.equals("")){
			rmap.put("code", "2");
			rmap.put("msg", "验证码不能为空。");
			return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
		}
		if(rand==null){
			rmap.put("code", "3");
			rmap.put("msg", "验证码失效请重新获取。");
			session.removeAttribute("rand");
			return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
		}
		if(rand.equals(code)){
			rmap.put("code", "1");
			rmap.put("msg", "");
			return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
		}
			rmap.put("code", "4");
			rmap.put("msg", "验证码不正确，请重新输入。");	
			session.removeAttribute("rand");
		return JSONArray.toJSONString(rmap,SerializerFeature.WriteDateUseDateFormat);
	}
	
	public void insertLoginLog(MemberBaseinfoDTO mbdto,HttpServletRequest request){
		try {
			String ip=IpUtils.getIpAddr(request);
//			String ip=request.getRemoteAddr();
//			ip=ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
			MemberLoginLogEntity entity=new MemberLoginLogEntity();
			entity.setMemberId(mbdto.getMemberId().intValue());
			entity.setAccount(mbdto.getAccount());
			entity.setMobile(mbdto.getMobile());
			entity.setRealName(mbdto.getRealName());
			entity.setIp(ip);
			entity.setLoginType("1");
			entity.setDescription("web主站登录");
			entity.setCreateTime(new Date());
			memberLoginLogToolService.insert(entity);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}	
	}
}
