package com.gudeng.commerce.gd.m.controller.weixin.jpgys;

import cn.gdeng.weixin.base.bean.api.UserInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.commerce.gd.m.Constant;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.controller.weixin.WeiXinUtils;
import com.gudeng.commerce.gd.m.service.ActWechatInviteServiceTool;
import com.gudeng.commerce.gd.m.service.ActivityToolService;
import com.gudeng.commerce.gd.m.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.m.service.WechatMemberToolService;
import com.gudeng.commerce.gd.m.util.*;
import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActWechatInviteDTO;
import com.gudeng.commerce.gd.promotion.dto.WechatMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;
import com.gudeng.commerce.gd.promotion.entity.WechatMemberEntity;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;
import com.gudeng.paltform.sms.GdSMS;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 微信公众平台跳转
 * @Project gd-m-web
 * @ClassName BusinessMController.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月25日 下午5:14:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
@Controller(value = "jpgysUserManageController")
@RequestMapping("jpgys/userManage")
public class UserManageController extends MBaseController {
	/**
	 * 记录日志
	 */
	private static Logger logger = LoggerFactory.getLogger(UserManageController.class);
	@Autowired
	private BaseWXUtil gdWXUtil;
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;
	@Autowired
	private ActivityToolService activityToolService;
	@Autowired
	private WechatMemberToolService wechatMemberToolService;
	@Autowired
	private WeiXinUtils weiXinUtils;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Autowired
	private IGDBinaryRedisClient redisClient;
	@Autowired
	private ActWechatInviteServiceTool actWechatInviteService;

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
	public ModelAndView dispatcher(final String openId, String unionid, final String activityId, String invitorOpenid, ModelAndView mv, RedirectAttributes attr) {
		// 跳转活动控制器
		mv.setViewName("redirect:" + get80BasePath(getRequest()) + "jpgys/activity/init");
		attr.addAttribute("activityId", activityId);
		attr.addAttribute("openId", openId);
		attr.addAttribute("unionid", unionid);
		attr.addAttribute("invitorOpenid", invitorOpenid);
		// 用户自动登录
		try {
			if (StringUtils.isEmpty(openId)) {
				mv.setViewName("weixin/error/404");
				return mv;
			}
			final Map<String, Object> map = new HashMap<>();
			map.put("unionId", unionid);
			map.put("openId", openId);
			List<WechatMemberDTO> userList = wechatMemberToolService.getList(map);
			if (userList != null && userList.size() == 1) {
				WechatMemberDTO user = userList.get(0);
				final Integer memberId = user.getMemberId();
				// 保存session
				getRequest().getSession().setAttribute(Constant.SYSTEM_LOGINUSER, user);
				//1 首登积分
				taskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							//首登积分
							ActActivityBaseinfoDTO activityInfo = activityToolService.getActivityInfo(activityId);
							Map<String, Object> condition = new HashMap<>();
							condition.put("activityId", activityInfo.getId());
							condition.put("userid", memberId);
							condition.put("type", "1");
							Integer total = activityToolService.getScoreRecordTotal2(condition);//登录得分记录次数
							if (total == null || total < 1) {
								WechatMemberEntity wechatMemberEntity = new WechatMemberEntity();
								wechatMemberEntity.setOpenId(openId);
								wechatMemberEntity.setActId(Integer.valueOf(activityId));
								wechatMemberEntity.setMemberId(memberId);
								excute("1", activityInfo, wechatMemberEntity, map);//增加登录得分
							}
							//金牌供应商积分
							//如果自己是金牌供应商
							if (weiXinUtils.isJpgys(memberId, activityId)) {
								//检测是否已经给自己增加过金牌供应商积分
								Map<String, Object> map2 = new HashMap<>();
								map2.put("activityId", activityId);//活动id
								map2.put("userid", memberId);//当前用户
								map2.put("type", "5");//自己成为金牌供应商得分
								map2.put("openId", openId);//当前用户
								Integer scoreRecordTotal2 = activityToolService.getScoreRecordTotal2(map2);
								//如果自己成为金牌供应商未加过分
								if (scoreRecordTotal2 < 1) {
									WechatMemberEntity wechatMemberEntity = new WechatMemberEntity();
									wechatMemberEntity.setOpenId(openId);
									wechatMemberEntity.setActId(Integer.valueOf(activityId));
									wechatMemberEntity.setMemberId(memberId);
									weiXinUtils.initUserActScore("5", wechatMemberEntity, null);//增加金牌供应商积分
								}
								//给邀请人增加金牌供应商积分
								map2.clear();
								map2.put("activityId", activityId);//活动id
								map2.put("visitorOpenid", openId);//当前用户
								//根据活动id、当前用户的openid，查找我的邀请人
								List<ActWechatInviteDTO> list = actWechatInviteService.getList(map2);
								//存在邀请人
								if (list != null && list.size() == 1) {
									ActWechatInviteDTO actWechatInviteDTO = list.get(0);//邀请信息
									Long userid = actWechatInviteDTO.getUserid();//邀请人id
									map2.clear();
									map2.put("activityId", activityId);//活动id
									map2.put("userid", userid);//邀请人id
									map2.put("type", "6");//邀请的人成为金牌供应商得分
									map2.put("openId", openId);//被邀请人的openid
									Integer scoreRecordTotal3 = activityToolService.getScoreRecordTotal2(map2);
									//如果邀请的人成为金牌供应商未加过分
									if (scoreRecordTotal3 < 1) {
										WechatMemberEntity wechatMemberEntity = new WechatMemberEntity();
										wechatMemberEntity.setOpenId(openId);
										wechatMemberEntity.setActId(Integer.valueOf(activityId));
										wechatMemberEntity.setMemberId(userid.intValue());
										//查找邀请人信息
										MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(memberId.toString());
										//邀请人增加金牌供应商积分,加上被邀请人的mobile
										weiXinUtils.initUserActScore("6", wechatMemberEntity, memberBaseinfoDTO.getMobile());
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			ActActivityBaseinfoDTO activityInfo = activityToolService.getActivityInfo(activityId);
			DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end = dateFormat2.parse("2016-12-31 23:59:59");//活动结束时间
			if (new Date().after(activityInfo.getEffectiveEndTime())) {
				// 活动兑换时间结束
//				mv.setViewName("redirect:" + getBasePath(getRequest()) + "jpgys/activity/activityEnd");
			}
			//更新活动访问次数
			//记录微信用户信息
			final UserInfo weixinUser = getWeixinUser(getRequest());
			taskExecutor.execute(new Runnable() {
				public void run() {
					weiXinUtils.updatePV(activityId);
					weiXinUtils.addWeiXinUserInfo(weixinUser);
				}
			});
		} catch (Exception e) {
			logger.error("查找用户出错", e);
		}
		return mv;
	}


	/**
	 * @param request
	 * @param dto
	 * @return
	 * @Description 登录(绑定帐号)
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
			map.put("msg", "帐号或密码错误,请重新输入");
			return map;
		}
		try {
			MemberBaseinfoDTO user = memberBaseinfoToolService.getByMobile(dto.getMobile());
			if (user != null && user.getMemberId() != null && user.getMemberId() > 0) {
				if (JavaMd5.getMD5(dto.getPassword() + Constant.PASSWORD_SHUFIX).toUpperCase().equals(user.getPassword())) {
					// 登录正确，绑定用户openid,unionid
					UserInfo weixinUser = getWeixinUser(request);
					WechatMemberEntity wechatMemberEntity = new WechatMemberEntity(weixinUser.getOpenid(), weixinUser.getUnionid(), user.getMemberId().intValue(), Integer.valueOf(activityId), invitorOpenid, "2", gdWXUtil.getWeiXinConf().getAppId());
					wechatMemberEntity.setCreateTime(new Date());
					Map<String, Object> map1 = new HashMap<>();
					map1.put("memberId", user.getMemberId());
					map1.put("actId", activityId);
					int total1 = wechatMemberToolService.getTotal(map1);//是否已经绑定
					Long result = 0L;
					if (total1 < 1) {
						//未绑定
						result = wechatMemberToolService.insert(wechatMemberEntity);//绑定谷登系统用户和微信用户
						if (result > 0) {
							WechatMemberDTO wechatMemberDTO = new WechatMemberDTO();
							wechatMemberDTO.setOpenId(wechatMemberEntity.getOpenId());
							wechatMemberDTO.setActId(wechatMemberEntity.getActId());
							wechatMemberDTO.setId(result.intValue());
							wechatMemberDTO.setInviteId(wechatMemberEntity.getInviteId());
							wechatMemberDTO.setMemberId(wechatMemberEntity.getMemberId());
							wechatMemberDTO.setType(wechatMemberEntity.getType());
							wechatMemberDTO.setUnionId(wechatMemberEntity.getUnionId());
							// 保存session
							request.getSession().setAttribute(Constant.SYSTEM_LOGINUSER, wechatMemberDTO);
							map.put(Constant.SYSTEM_LOGINUSER, user);
							map.put("msg", "success");
							map.put("userid", user.getMemberId());
						}
					} else {
						//已经绑定过
						List<WechatMemberDTO> list = wechatMemberToolService.getList(map1);
						if (list != null && list.size() == 1) {
							WechatMemberDTO wechatMemberDTO = list.get(0);
							// 保存session
							request.getSession().setAttribute(Constant.SYSTEM_LOGINUSER, wechatMemberDTO);
							map.put(Constant.SYSTEM_LOGINUSER, user);
							map.put("msg", "success");
							map.put("userid", user.getMemberId());
						}
					}
					ActActivityBaseinfoDTO activityInfo = activityToolService.getActivityInfo(activityId);
					// 更新微信活动用户分数记录、分享次数等信息
					Map<String, Object> condition = new HashMap<>();
					condition.put("activityId", activityInfo.getId());
					condition.put("userid", user.getMemberId());
					condition.put("type", "1");
					Integer total = activityToolService.getScoreRecordTotal2(condition);//登录得分记录次数
					if (total == null || total < 1) {
						map = excute("1", activityInfo, wechatMemberEntity, map);//增加登录得分
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

	/**
	 * @param entity
	 * @param dto
	 * @return
	 * @Description 注册
	 * @CreationDate 2016年5月12日 下午6:34:02
	 * @Author lidong(dli@gdeng.cn)
	 */
	@RequestMapping(value = "register")
	@ResponseBody
	public Map<String, Object> register(final MemberBaseinfoEntity entity, MemberBaseinfoDTO dto, final String activityId, String code, final String invitorOpenid) {
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
		try {
			MemberBaseinfoDTO user = memberBaseinfoToolService.getByMobile(entity.getMobile());
			if (user != null) {
				map.put("msg", "手机号码已存在");
				return map;
			}
			String md5password = JavaMd5.getMD5(entity.getPassword() + Constant.PASSWORD_SHUFIX).toUpperCase();
			entity.setAccount(entity.getMobile());
			entity.setPassword(md5password);
			entity.setAccount("gd" + IdGenerateUtil.create32UUID());
			entity.setCreateTime(new Date());
			entity.setLevel(Integer.parseInt(MemberBaseinfoEnum.LEVEl_4.getKey()));//供应商
			entity.setRegetype(MemberBaseinfoEnum.REGETYPE_8.getKey());//来源微信注册
			entity.setStatus("1");// 启用状态
			// 登录正确，绑定用户openid,unionid
			final UserInfo weixinUser = getWeixinUser(getRequest());
			if (weixinUser == null) {
				map.put("msg", "超时，请重新进入页面");
				return map;
			}
			long memberId = memberBaseinfoToolService.addMemberBaseinfoEnt(entity);
			if (memberId > 0) {
				WechatMemberEntity wechatMemberEntity = new WechatMemberEntity(weixinUser.getOpenid(), weixinUser.getUnionid(), (int) memberId, Integer.valueOf(activityId), invitorOpenid, "1", gdWXUtil.getWeiXinConf().getAppId());
				wechatMemberEntity.setCreateTime(new Date());
				//查找邀请人
				Map<String, Object> map1 = new HashMap<>();
				map1.put("visitorOpenid", weixinUser.getOpenid());//访问人openid
				map1.put("avtivityId", activityId);//活动id
				try {
					List<ActWechatInviteDTO> userlist = actWechatInviteService.getList(map1);//该用户邀请人记录
					if (userlist != null && userlist.size() == 1) {
						ActWechatInviteDTO entity1 = userlist.get(0);
						//存在邀请人
						if (entity1.getUserid() > 0) {
							wechatMemberEntity.setInviteId(entity1.getInvitorOpenid());//注册邀请人
							//记录邀请人得分
							WechatMemberEntity entity2 = new WechatMemberEntity();//邀请人
							entity2.setActId(Integer.valueOf(activityId));//活动id
							entity2.setOpenId(invitorOpenid);//邀请人openid
							entity2.setMemberId(entity1.getUserid().intValue());//邀请人id
							//记录邀请人得分，加上被邀请人手机号
							weiXinUtils.initUserActScore("7", entity2, entity.getMobile());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				Long result = wechatMemberToolService.insert(wechatMemberEntity);//绑定谷登系统用户和微信用户
				if (result != null && result > 0) {
					WechatMemberDTO wechatMemberDTO = new WechatMemberDTO();
					wechatMemberDTO.setOpenId(wechatMemberEntity.getOpenId());
					wechatMemberDTO.setActId(wechatMemberEntity.getActId());
					wechatMemberDTO.setId(result.intValue());
					wechatMemberDTO.setInviteId(wechatMemberEntity.getInviteId());
					wechatMemberDTO.setMemberId(wechatMemberEntity.getMemberId());
					wechatMemberDTO.setType(wechatMemberEntity.getType());
					wechatMemberDTO.setUnionId(wechatMemberEntity.getUnionId());
					// 保存session
					getRequest().getSession().setAttribute(Constant.SYSTEM_LOGINUSER, wechatMemberDTO);
					map.put(Constant.SYSTEM_LOGINUSER, user);
					map.put("msg", "success");
					map.put("userid", memberId);
				}
				ActActivityBaseinfoDTO activityInfo = activityToolService.getActivityInfo(activityId);
				// 更新微信活动用户分数记录、分享次数等信息
				Map<String, Object> condition = new HashMap();
				condition.put("activityId", activityId);
				condition.put("userid", memberId);
				condition.put("type", "4");
				Integer total = activityToolService.getScoreRecordTotal2(condition);//注册得分记录次数
				if (total == null || total < 1) {
					// 更新微信活动用户分数记录、分享次数等信息
					map = excute("4", activityInfo, wechatMemberEntity, map);//增加注册得分
					//增加注册时首登积分0分
					MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById("" + memberId);
					ActActivityScoreRecordEntity entity2 = new ActActivityScoreRecordEntity();
					entity2.setType("1");
					entity2.setOpenId(dto.getOpenId());
					entity2.setActivityId(wechatMemberEntity.getActId());
					entity2.setMobile(memberBaseinfoDTO.getMobile());
					entity2.setUserid(memberId);
					entity2.setCreateTime(new Date());
					entity2.setScore(0);//登录得分
					entity2.setReceiveTime(new Date());
					activityToolService.insertReActivityScoreRecoreInfo(entity2);//增加登录积分记录
				}
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
	 * @param type         积分来源类型 1： 首登积分 2： 分享积分 3： 点赞积分 4：注册成功 5：（自己或自己邀请的人）成为金牌供应商
	 * @param activityInfo
	 * @param dto
	 * @param map
	 * @return
	 */
	public Map<String, Object> excute(String type, ActActivityBaseinfoDTO activityInfo, WechatMemberEntity dto, Map<String, Object> map) {
		// 如果活动启用则增加活动分数相关记录
		if (ActActivityBaseinfoEntity.ACTIVITY_LAUNCH_YES.equals(activityInfo.getLaunch())) {
			weiXinUtils.initUserActScore(type, dto, null);
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
		}
		return map;
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

	/**
	 * 注册发送手机验证码
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "sendCode", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendCode(HttpServletRequest request, HttpServletResponse response, ModelMap rmap, String rand) {
		Long sessionTime = System.currentTimeMillis();
		try {
			HttpSession session = request.getSession();
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

}
