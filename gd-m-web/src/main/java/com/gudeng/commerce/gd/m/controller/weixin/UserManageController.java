package com.gudeng.commerce.gd.m.controller.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gudeng.commerce.gd.m.service.*;
import com.gudeng.commerce.gd.promotion.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.commerce.gd.m.Constant;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.util.CommonUtil;
import com.gudeng.commerce.gd.m.util.JavaMd5;
import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;
import com.gudeng.commerce.gd.promotion.entity.ActReUserActivityEntity;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;
import com.gudeng.paltform.sms.GdSMS;

/**
 * @Description 微信公众平台跳转
 * @Project gd-m-web
 * @ClassName BusinessMController.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月25日 下午5:14:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 *
 */
@Controller
@RequestMapping("userManage")
public class UserManageController extends MBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(UserManageController.class);

	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;
	@Autowired
	private IGDBinaryRedisClient redisClient;
	@Autowired
	private ActivityToolService activityToolService;

	@Autowired
	private ActWechatInviteServiceTool actWechatInviteService;
	@Autowired
	private ActWechatShareServiceTool actWechatShareService;

	@Autowired
	private WechatMemberToolService wechatMemberToolService;

	/**
	 * 微信用户点击公众号菜单链接进入，分发用户跳转路径
	 *
	 * @param openId
	 * @param activityId
	 * @param mv
	 * @param attr
	 * @return
	 */
	@RequestMapping(value = "dispatcher")
	public ModelAndView dispatcher(String openId, String unionid, final String activityId, String invitorOpenid, ModelAndView mv, RedirectAttributes attr) {
		// 跳转活动控制器
		mv.setViewName("redirect:" + dispatcherActivty(activityId));
		attr.addAttribute("activityId", activityId);
		attr.addAttribute("openId", openId);
		attr.addAttribute("unionid", unionid);
		attr.addAttribute("invitorOpenid", invitorOpenid);
		final Map<String, Object> map = new HashMap<>();
		map.put("unionid", unionid);
		// 用户自动登录
		try {
			if (StringUtils.isEmpty(unionid)) {
				mv.setViewName("weixin/error/404");
				return mv;
			}
			MemberBaseinfoDTO user = memberBaseinfoToolService.queryMemberInfoForPromotion(map);
			if (user != null) {
				// 保存session
				getRequest().getSession().setAttribute(Constant.SYSTEM_LOGINUSER, user);
			}
			ActActivityBaseinfoDTO activityInfo = activityToolService.getActivityInfo(activityId);
			if (new Date().after(activityInfo.getEffectiveEndTime())) {
				// 活动兑换时间结束
				mv.setViewName("redirect:" + getBasePath(getRequest()) + "activity/activityEnd");
			} else {
				new Thread(new Runnable() {
					public void run() {
						map.put("id", activityId);
						try {
							activityToolService.updatePV(map);
						} catch (Exception e) {
							logger.error("更新活动浏览次数出错", e);
						}
					}
				}).start();
			}
		} catch (Exception e) {
			logger.error("查找用户出错", e);
		}
		return mv;
	}

	private String dispatcherActivty(String activityId) {
		switch (Integer.valueOf(activityId)) {
		case 4:
			return getBasePath(getRequest()) + "activity/initRedPacketActivityData";
		default:
			return getBasePath(getRequest()) + "activity/initRedPacketActivityData";
		}
	}

	/**
	 * @Description 登录(绑定帐号)
	 * @param request
	 * @param dto
	 * @return
	 * @CreationDate 2016年5月12日 下午6:34:09
	 * @Author lidong(dli@gdeng.cn)
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, MemberBaseinfoDTO dto, String activityId, String invitorOpenid) {
		Map<String, Object> map = new HashMap<>();
		WechatMemberDTO user2 = getUser(request);
		if (user2 != null && StringUtils.isNotEmpty(user2.getOpenId())) {
			map.put("msg", "success");
			return map;
		}
		if (StringUtils.isEmpty(dto.getMobile()) || StringUtils.isEmpty(dto.getPassword())) {
			map.put("msg", "帐号或密码错误,请重新输入。");
			return map;
		}
		try {
			map.put("mobile", dto.getMobile());
			MemberBaseinfoDTO user = memberBaseinfoToolService.queryMemberInfoForPromotion(map);
			if (user != null) {
				if (JavaMd5.getMD5(dto.getPassword() + Constant.PASSWORD_SHUFIX).toUpperCase().equals(user.getPassword())) {
					// 登录正确，修改用户openid,unionid
					dto.setPassword(null);
					dto.setMemberId(user.getMemberId());
					dto.setAccount(user.getAccount());
					int result = memberBaseinfoToolService.updateMemberBaseinfoDTO(dto);
					if (result > 0) {
						// 保存session
						request.getSession().setAttribute(Constant.SYSTEM_LOGINUSER, user);
						map.put(Constant.SYSTEM_LOGINUSER, user);
						map.put("msg", "success");
						map.put("userid", user.getMemberId());
						ActActivityBaseinfoDTO activityInfo = activityToolService.getActivityInfo(activityId);
						// 更新微信活动用户分数记录、分享次数等信息
						map = excute(activityInfo, dto, map);
					} else {
						map.put("msg", "帐号绑定失败");
						return map;
					}
				} else {
					map.put("msg", "密码错误");
					return map;
				}
			} else {
				map.put("msg", "帐号不存在");
				return map;
			}
		} catch (Exception e) {
			logger.error("登录失败", e);
			map.put("msg", "服务器异常");
			return map;
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println(JavaMd5.getMD5("888888" + Constant.PASSWORD_SHUFIX).toUpperCase());
	}

	/**
	 * @Description 注册
	 * @param entity
	 * @param dto
	 * @return
	 * @CreationDate 2016年5月12日 下午6:34:02
	 * @Author lidong(dli@gdeng.cn)
	 */
	@RequestMapping(value = "register")
	@ResponseBody
	public Map<String, Object> register(MemberBaseinfoEntity entity, MemberBaseinfoDTO dto, String activityId, String code, String invitorOpenid) {
		Map<String, Object> map = new HashMap<>();
		WechatMemberDTO user2 = getUser(getRequest());
		if (user2 != null && StringUtils.isNotEmpty(user2.getOpenId())) {
			map.put("msg", "success");
			return map;
		}
		if (StringUtils.isEmpty(entity.getMobile()) || StringUtils.isEmpty(entity.getPassword())) {
			map.put("msg", "手机号码或密码不可为空");
			return map;
		}
		int code_state = checkCode(code);
		if (code_state == CODE_STATE_TIMEOUT) {
			map.put("msg", "验证码过期");
			return map;
		}
		if (code_state == CODE_STATE_ERROR) {
			map.put("msg", "验证码错误");
			return map;
		}
		map.put("mobile", entity.getMobile());
		try {
			MemberBaseinfoDTO user = memberBaseinfoToolService.queryMemberInfoForPromotion(map);
			if (user != null) {
				map.put("msg", "手机号码已存在");
				return map;
			}
			String md5password = JavaMd5.getMD5(entity.getPassword() + Constant.PASSWORD_SHUFIX).toUpperCase();
			entity.setAccount(entity.getMobile());
			entity.setPassword(md5password);
			dto.setAccount(entity.getMobile());
			entity.setCreateTime(new Date());
			entity.setLevel(Integer.parseInt(MemberBaseinfoEnum.LEVEl_3.getKey()));
			entity.setRegetype(MemberBaseinfoEnum.REGETYPE_8.getKey());
			entity.setStatus("1");// 启用状态
			long memberId = memberBaseinfoToolService.addMemberBaseinfoEnt(entity);
			if (memberId > 0) {
				entity.setMemberId(memberId);
				dto.setMemberId(memberId);
				// 保存session
				getRequest().getSession().setAttribute(Constant.SYSTEM_LOGINUSER, dto);
				map.put(Constant.SYSTEM_LOGINUSER, entity);
				map.put("msg", "success");
				map.put("userid", memberId);
				ActActivityBaseinfoDTO activityInfo = activityToolService.getActivityInfo(activityId);
				// 更新微信活动用户分数记录、分享次数等信息
				map = excute(activityInfo, dto, map);
			}
		} catch (Exception e) {
			logger.error("注册失败", e);
			map.put("msg", "服务器错误");
		}
		return map;
	}

	/**
	 * 处理用户登录、注册成功之后，活动得分相关数据的处理
	 * 
	 * @param activityInfo
	 * @param dto
	 * @param map
	 * @return
	 */
	public Map<String, Object> excute(ActActivityBaseinfoDTO activityInfo, MemberBaseinfoDTO dto, Map<String, Object> map) {
		// 如果活动启用则增加活动分数相关记录
		if (ActActivityBaseinfoEntity.ACTIVITY_LAUNCH_YES.equals(activityInfo.getLaunch())) {
			// 初始化用户活动记录
			String activityId = activityInfo.getId().toString();
			initUserActivity(activityId, dto);
			// 初始化用户活动得分记录
			initUserActivityScore(activityId, dto.getMemberId(), 2);
			// 更新父级分享记录
			// 从邀请记录表中获取分享人的openid
			Map<String, Object> map2 = new HashMap<>();
			map2.put("visitorOpenid", dto.getOpenId());
			map2.put("avtivityId", activityId);
			map2.put("startRow", 0);
			map2.put("endRow", 1);
			// 获取分享人
			try {
				List<ActWechatInviteDTO> list = actWechatInviteService.getList(map2);
				if (list != null && list.size() > 0) {
					// 获取邀请人
					ActWechatInviteDTO actWechatInviteDTO = list.get(0);
					String invitorOpenid = actWechatInviteDTO.getInvitorOpenid();
					if (StringUtils.isNotEmpty(invitorOpenid)) {
						if (!invitorOpenid.equals(getWeixinUser(getRequest()).getOpenid())) {
							updateFatherShare(2, activityId, invitorOpenid);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return map;
		} else {
			if (ActActivityBaseinfoEntity.ACTIVITY_LAUNCH_WAIT.equals(activityInfo.getLaunch())) {
				map.put("msg", "gameover");
				return map;
			}
			Date now = new Date();
			// 兑换礼物有效期
			if (now.after(activityInfo.getEffectiveStartTime()) && now.before(activityInfo.getEffectiveEndTime())) {
				map.put("msg", "gift");
			} else {
				map.put("msg", "gameover");
			}
			return map;
		}
	}

	/**
	 * 注册发送手机验证码
	 *
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping(value = "sendCode", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendCode(HttpServletRequest request, HttpServletResponse response, ModelMap rmap, String rand) {
		Long sessionTime = System.currentTimeMillis();
		try {
			HttpSession session = request.getSession();
			String mRand = (String) session.getAttribute("rand");// 图片验证码
			if (mRand != null) {
				if (!mRand.equals(rand)) {
					rmap.put("msg", "图片验证码错误");
					return JSONArray.toJSONString(rmap, SerializerFeature.WriteDateUseDateFormat);
				}
			} else {
				rmap.put("msg", "图片验证码失效");
				return JSONArray.toJSONString(rmap, SerializerFeature.WriteDateUseDateFormat);
			}
			session.removeAttribute("rand");
			String mobile = request.getParameter("mobile");
			Map<String, Long> checkmap = (Map<String, Long>) session.getAttribute(Constant.MOBILE_CHECK_TIME);
			if (checkmap != null && checkmap.get(mobile) != null) {
				// 判断两次时间是否大于1分钟
				Long value = (System.currentTimeMillis() - checkmap.get(mobile)) / 1000;
				if (value < 60) {
					rmap.put("msg", "请60s后发送验证码！");
					rmap.put("time", 60 - value);
					logger.info("##########剩余时间" + (60 - value));
					return JSONArray.toJSONString(rmap, SerializerFeature.WriteDateUseDateFormat);
				}
			}
			// 记录发送成功的手机号
			if (checkmap == null) {
				checkmap = new HashMap<String, Long>();
			}
			checkmap.put(mobile, sessionTime);
			session.setAttribute(Constant.MOBILE_CHECK_TIME, checkmap);
			if (StringUtils.isBlank(mobile)) {
				rmap.put("msg", "手机号码为空");
				return JSONArray.toJSONString(rmap, SerializerFeature.WriteDateUseDateFormat);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mobile);
			MemberBaseinfoDTO mbdto = memberBaseinfoToolService.queryMemberInfoForPromotion(map);
			if (null != mbdto) {
				rmap.put("msg", "手机号码已存在!");
				return JSONArray.toJSONString(rmap, SerializerFeature.WriteDateUseDateFormat);
			}
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
			GdSMS sms = new GdSMS();
			String randi = getSixCode();
			String smsmsg = Constant.SMSMSG.replace("{code}", randi);
			System.out.println("----------------------------->" + randi);
			// //阿里大鱼短信通道
			if (Constant.Alidayu.REDISTYPE.equals(channel)) {
				smsmsg = CommonUtil.alidayuUtil(Constant.Alidayu.MESSAGETYPE3, randi);
			}
			boolean b = sms.SendSms(channel, smsmsg, mobile);
			if (b) {
				// 记录验证码
				session.setAttribute(Constant.MOBILE_CHECK, randi.toString());
				logger.info("######记录session时间" + System.currentTimeMillis());
				rmap.put("msg", "success");
			} else {
				rmap.put("msg", "发送短信验证码失败");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "服务器异常");
		}
		return JSONArray.toJSONString(rmap, SerializerFeature.WriteDateUseDateFormat);
	}

	@RequestMapping("initImg")
	public String initImg(HttpServletRequest request, ModelMap map) {
		return "activity/img";
	}

	/**
	 * 确认验证码
	 *
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping(value = "checkCode", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkCode(HttpServletRequest request, HttpServletResponse response, ModelMap rmap) {
		try {
			HttpSession session = request.getSession();
			String code = request.getParameter("code");
			String scode = (String) session.getAttribute(Constant.MOBILE_CHECK);
			// 数据判断 semon 20151031
			if (StringUtils.isBlank(code) || StringUtils.isBlank(scode)) {
				rmap.put("msg", "3");
				return JSONArray.toJSONString(rmap, SerializerFeature.WriteDateUseDateFormat);
			}
			if (code.equals(scode)) {
				rmap.put("msg", "1");
			} else {
				rmap.put("msg", "3");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			rmap.put("msg", "27");
		}
		return JSONArray.toJSONString(rmap, SerializerFeature.WriteDateUseDateFormat);
	}



	/**
	 * 初始化用户活动记录
	 *
	 * @param activityId
	 *            活动ID
	 * @param member
	 *            用户
	 * @author lidong
	 */
	public void initUserActivity(String activityId, MemberBaseinfoDTO member) {
		ActReUserActivityEntity userActivity = new ActReUserActivityEntity();
		userActivity.setActivityId(Integer.valueOf(activityId));
		userActivity.setUserid(member.getMemberId());
		userActivity.setScore(2);
		userActivity.setFirstJoin(ActReUserActivityEntity.IS_FIRST_JOIN_YES);
		// 查询活动信息, 获取活动默认次数, 在此设置剩余次数
		ActActivityBaseinfoDTO activityBaseinfo;
		try {
			activityBaseinfo = activityToolService.getActivityInfo(activityId);
			// 页面初始化剩余次数(即抢红包次数为活动默认次数-1)
			userActivity.setJoinTimesLeft(activityBaseinfo.getTimes() - 1);
			userActivity.setMobile(member.getMobile());
			userActivity.setAccount(member.getAccount());
			// 初始化用户-活动关系表, 为用户创建一条用户活动记录
			ActReUserActivityDto actReUserActivityDto = activityToolService.getReUserActivityInfo(activityId, member.getMemberId().toString());
			if (actReUserActivityDto == null) {
				activityToolService.insertReUserActivityInfo(userActivity);
			} else {
				actReUserActivityDto.setScore(actReUserActivityDto.getScore() + 2);
				activityToolService.updateReUserActivityInfo(actReUserActivityDto);
			}
		} catch (Exception e) {
			logger.error("初始化用户得分纪录失败", e);
		}
	}

	/**
	 * 初始化用户活动得分纪录
	 *
	 * @param activityId
	 * @param userid
	 * @param score
	 */
	public void initUserActivityScore(String activityId, Long userid, Integer score) {
		// 新增积分获取记录
		Date now = new Date();
		ActActivityScoreRecordEntity entity = new ActActivityScoreRecordEntity();
		entity.setActivityId(Integer.valueOf(activityId));
		entity.setUserid(userid);
		entity.setReceiveTime(now);
		entity.setScore(score);
		entity.setCreateTime(now);
		entity.setUpdateTime(now);
		entity.setCreateUserId("system");
		entity.setUpdateUserId("system");
		try {
			activityToolService.insertReActivityScoreRecoreInfo(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户绑定，更新分享人的游戏次数
	 *
	 * @param dept
	 *            更新的人数深度
	 * @param activityId
	 *            活动id
	 * @param invitorOpenid
	 *            邀请人openid
	 */
	public void updateFatherShare(int dept, String activityId, String invitorOpenid) {
		// 分享人的分享人openid
		String pOpenid = null;
		if (StringUtils.isEmpty(invitorOpenid)) {
			// 没有邀请记录
			return;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("openid", invitorOpenid);
		map.put("activityId", activityId);
		try {
			// 获取分享人
			List<ActWechatShareDTO> list2 = actWechatShareService.getList(map);
			if (list2 != null && list2.size() > 0) {
				// 获取分享人
				ActWechatShareDTO actWechatShareDTO = list2.get(0);
				map.clear();
				map.put("avtivityId", activityId);
				map.put("visitorOpenid", invitorOpenid);
				map.put("startRow", 0);
				map.put("endRow", 1);
				// 获取分享人的邀请人
				List<ActWechatInviteDTO> list3 = actWechatInviteService.getList(map);
				if (list3 != null && list3.size() > 0) {
					// 获取分享人的邀请人
					ActWechatInviteDTO wechatInviteDTO = list3.get(0);
					pOpenid = wechatInviteDTO.getInvitorOpenid();// invitorOpenid的invitorOpenid
				}
				map.clear();
				map.put("unionid", actWechatShareDTO.getUnionid());
				// 获取分享人绑定帐号
				MemberBaseinfoDTO mbdto = memberBaseinfoToolService.queryMemberInfoForPromotion(map);
				if (mbdto != null) {
					// 获取用户活动记录
					ActReUserActivityDto reUserActivityInfo = activityToolService.getReUserActivityInfo(activityId, mbdto.getMemberId().toString());
					if (reUserActivityInfo != null) {
						// 分享用户有活动记录
						// 更新活动次数加1
						reUserActivityInfo.setJoinTimesLeft(reUserActivityInfo.getJoinTimesLeft() + 1);
						activityToolService.updateReUserActivityInfo(reUserActivityInfo);
					} else {
						// 分享用户没有活动记录
						ActReUserActivityEntity userActivity = new ActReUserActivityEntity();
						userActivity.setActivityId(Integer.valueOf(activityId));
						userActivity.setUserid(mbdto.getMemberId());
						userActivity.setScore(0);
						userActivity.setFirstJoin(ActReUserActivityEntity.IS_FIRST_JOIN_YES);
						// 查询活动信息, 获取活动默认次数, 在此设置剩余次数
						ActActivityBaseinfoDTO activityBaseinfo = activityToolService.getActivityInfo(activityId);
						userActivity.setJoinTimesLeft(activityBaseinfo.getTimes() + 1);// 活动次数加1
						// 页面初始化剩余次数(即抢红包次数为活动默认次数)
						userActivity.setMobile(mbdto.getMobile());
						userActivity.setAccount(mbdto.getAccount());
						// 初始化用户-活动关系表, 为用户创建一条用户活动记录
						activityToolService.insertReUserActivityInfo(userActivity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dept < 1) {
			// 更新深度次数不足
			return;
		} else {
			updateFatherShare(dept - 1, activityId, pOpenid);
		}
	}

	@RequestMapping(value = "getWeixinUser")
	@ResponseBody
	public Map<String, Object> getUserinfo() {
		Map<String, Object> map = new HashMap<>();
		map.put(Constant.SYSTEM_WEIXINUSER, getWeixinUser(getRequest()));
		return map;
	}

	@RequestMapping(value = "getLoginUser")
	@ResponseBody
	public Map<String, Object> getLoginUserinfo() {
		Map<String, Object> map = new HashMap<>();
		map.put(Constant.SYSTEM_LOGINUSER, getUser(getRequest()));
		return map;
	}
}
