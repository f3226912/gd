package com.gudeng.commerce.gd.m.controller.weixin;

import cn.gdeng.weixin.base.bean.api.UserInfo;
import cn.gdeng.weixin.base.bean.api.oauth.OAuthData;
import cn.gdeng.weixin.base.core.OAuthAPI;
import cn.gdeng.weixin.base.core.UserManageAPI;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.m.Constant;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.service.*;
import com.gudeng.commerce.gd.m.util.BaseWXUtil;
import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.dto.WechatMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dli@gdeng.cn
 * @Description
 * @date 2016/11/18 9:47
 */
@Service
public class WeiXinUtils extends MBaseController {
	@Autowired
	private ActWechatInviteServiceTool actWechatInviteService;
	@Autowired
	private BaseWXUtil gdWXUtil;
	/**
	 * 记录日志
	 */
	private static Logger logger = LoggerFactory.getLogger(WeiXinUtils.class);
	@Autowired
	private ActivityToolService activityToolService;
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;
	@Autowired
	private WechatUserinfoToolService wechatUserinfoToolService;
	@Autowired
	private WechatMemberToolService wechatMemberToolService;

	/**
	 * 用户邀请记录
	 *
	 * @param entity 邀请数据
	 */
	public void invite(ActWechatInviteEntity entity, UserInfo weixinUser) {
		try {
			Integer avtivityId = entity.getAvtivityId();//活动id
			Map<String, Object> map = new HashMap<>();
			map.put("avtivityId", avtivityId);//活动id
			if (weixinUser != null && weixinUser.getOpenid() != null) {
				String openid = weixinUser.getOpenid();//当前访问人openid
				String invitorOpenid = entity.getInvitorOpenid();//邀请人openid
				map.put("visitorOpenid", openid);//当前访问人openid
				int total = actWechatInviteService.getTotal(map);//当前访问者被谷登用户邀请的记录数
				if (total < 1) {
					//当前用户未被邀请
					if (invitorOpenid != null) {
						if (invitorOpenid.equals(openid)) {//邀请人是自己，则返回
							return;
						}
						map.put("openId", invitorOpenid);//邀请人openid
						map.put("actId", avtivityId);//活动id
						List<WechatMemberDTO> list = wechatMemberToolService.getList(map);//查询邀请人信息
						//邀请人已经注册,则记录邀请信息
						if (list != null && list.size() == 1) {
							WechatMemberDTO wechatMemberDTO = list.get(0);//邀请人信息
							Long memberId = wechatMemberDTO.getMemberId().longValue();//邀请人谷登帐号id
							if (memberId > 0) {
								entity.setUserid(memberId);//邀请人id
								entity.setCreateTime(new Date());
								entity.setUpdateTime(new Date());
								entity.setVisitorOpenid(openid);
								actWechatInviteService.save(entity);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("记录分享链接访问数据失败", e);
		}
	}

	/**
	 * 微信授权验证
	 *
	 * @param mv     视图
	 * @param grant  授权验证code
	 * @param entity 邀请数据模型
	 * @param attr   参数列表
	 * @return
	 */
	public ModelAndView oAuth2(ModelAndView mv, OAuthData grant, final ActWechatInviteEntity entity, RedirectAttributes attr) {
		try {
			// 通过code换取网页授权access_token
			grant.setAppId(gdWXUtil.getWeiXinConf().getAppId());
			grant.setAppSecret(gdWXUtil.getWeiXinConf().getAppSecret());
			grant = OAuthAPI.getAccess_token(grant);
			if (grant != null && StringUtils.isNotEmpty(grant.getOpenid())) {
				UserInfo userInfo = OAuthAPI.getUserInfo(grant);
				if (userInfo != null && StringUtils.isNotEmpty(userInfo.getOpenid())) {
					// 保存session
					getRequest().getSession().setAttribute(Constant.SYSTEM_WEIXINUSER, userInfo);
					attr.addAttribute("unionid", userInfo.getUnionid());
					if (entity != null) {
						entity.setUnionid(userInfo.getUnionid());// 访问用户unionid
						entity.setCreateTime(new Date());
						entity.setUpdateTime(new Date());
					}
				} else {
					mv.setViewName("weixin/error/404");
					return mv;
				}
			} else {
				mv.setViewName("weixin/error/404");
				return mv;
			}
		} catch (Exception e) {
			mv.setViewName("weixin/error/404");
			logger.error("OAuthException获取用户信息出错：", e);
			return mv;
		}
		return mv;
	}

	/**
	 * 更新活动访问次数
	 *
	 * @param activityId 活动id
	 */
	public void updatePV(String activityId) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", activityId);
		try {
			activityToolService.updatePV(map);
		} catch (Exception e) {
			logger.error("更新活动浏览次数出错", e);
		}
	}

	/**
	 * 初始化用户得分，即：首登积分、注册得分
	 *
	 * @param type
	 * @param dto
	 */
	public void initUserActScore(String type, WechatMemberEntity dto, String mobile) {
		if (dto.getMemberId() == null) {
			return;
		}
		try {
			MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(dto.getMemberId().toString());
			ActActivityScoreRecordEntity entity = new ActActivityScoreRecordEntity();
			entity.setType(type);
			entity.setOpenId(dto.getOpenId());
			entity.setActivityId(dto.getActId());
			entity.setMobile(mobile == null ? memberBaseinfoDTO.getMobile() : mobile);
			entity.setUserid(dto.getMemberId().longValue());
			entity.setCreateTime(new Date());
			entity.setReceiveTime(new Date());
			if (type.equals("1")) {//登录得分
				entity.setScore(5);
			} else if (type.equals("4")) {//自己注册成功
				entity.setScore(10);
			} else if (type.equals("3")) {//点赞积分
				entity.setScore(1);
			} else if (type.equals("2")) {//分享积分
				entity.setScore(10);
			} else if (type.equals("7")) {//邀请的人注册成功
				entity.setScore(10);
			} else if (type.equals("5")) {//自己成为金牌供应商
				entity.setScore(100);
			} else if (type.equals("6")) {//邀请的人成为金牌供应商
				entity.setScore(100);
			}
			/*Long id = activityToolService.insertReActivityScoreRecoreInfo(entity);
			if (id != null && id > 0) {
				//用户活动总积分
				Integer userScore = activityToolService.getUserScore(dto.getMemberId(), dto.getActId());
				//更新用户活动总积分记录
				ActReUserActivityDto actReUserActivityDto = activityToolService.getReUserActivityInfo(dto.getActId().toString(), dto.getMemberId().toString());
				if (actReUserActivityDto != null) {
					actReUserActivityDto.setScore(userScore);
					activityToolService.updateReUserActivityInfo(actReUserActivityDto);
				} else {
					//新增用户活动总积分记录
					ActReUserActivityEntity userActivity = new ActReUserActivityEntity();
					userActivity.setScore(userScore);
					userActivity.setActivityId(dto.getActId());
					userActivity.setUserid(dto.getMemberId().longValue());
					userActivity.setMobile(memberBaseinfoDTO.getMobile());
					userActivity.setAccount(memberBaseinfoDTO.getAccount());
					Long aLong = activityToolService.insertReUserActivityInfo(userActivity);
					if (aLong != null && aLong > 0) {
						logger.info("初始化" + aLong + "总分完成！");
					}
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 不限制得分次数的得分记录操作,新增得分纪录，并更新用户总积分
	 *
	 * @param type
	 * @param dto
	 * @param score
	 */
	public void updateUserActScore(String type, WechatMemberEntity dto, int score) {
		try {
			MemberBaseinfoDTO member = memberBaseinfoToolService.getById(dto.getMemberId().toString());
			ActActivityScoreRecordEntity record = new ActActivityScoreRecordEntity();
			record.setType(type);//积分积分
			record.setOpenId(dto.getOpenId());
			record.setActivityId(dto.getActId());
			record.setMobile(member.getMobile());
			record.setUserid(dto.getMemberId().longValue());
			record.setCreateTime(new Date());
			record.setScore(score);//得分
			/*Long id = activityToolService.insertReActivityScoreRecoreInfo(record);//增加得分
			if (id != null && id > 0) {
				//更新用户活动总积分记录
				ActReUserActivityDto actReUserActivityDto = activityToolService.getReUserActivityInfo(dto.getActId().toString(), dto.getMemberId().toString());
				actReUserActivityDto.setScore(actReUserActivityDto.getScore() + score);
				activityToolService.updateReUserActivityInfo(actReUserActivityDto);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 记录微信用户信息
	 */
	public void addWeiXinUserInfo(UserInfo user) {
		try {
			if (user != null && user.getOpenid() != null) {
				Map<String, Object> map = new HashMap<>();
				map.put("openid", user.getOpenid());
				int total = wechatUserinfoToolService.getTotal(map);
				if (total < 1) {
					WechatUserinfoEntity entity = new WechatUserinfoEntity();
					entity.setAppId(gdWXUtil.getWeiXinConf().getAppId());
					entity.setCity(user.getCity());
					entity.setCountry(user.getCountry());
					entity.setGroupid(user.getGroupid());
					entity.setHeadimgurl(convertHeadimgurl(user.getHeadimgurl()));
					entity.setLanguage(user.getLanguage());
					if (user.getNickname() != null) {
						entity.setNickname(URLEncoder.encode(user.getNickname(), "UTF-8"));
					}
					entity.setOpenid(user.getOpenid());
					entity.setProvince(user.getProvince());
					entity.setRemark(user.getRemark());
					if (user.getSex() != null) {
						entity.setSex(Integer.valueOf(user.getSex()));
					}
					entity.setSubscribeTime(user.getSubscribe_time());
					entity.setSubscribe(user.getSubscribe());
					entity.setUnionid(user.getUnionid());
					entity.setCreateTime(new Date());
					wechatUserinfoToolService.insert(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将返回的微信用户头像链接转为46尺寸的链接地址
	 *
	 * @param img
	 * @return
	 */
	private String convertHeadimgurl(String img) {
		if (StringUtils.isEmpty(img)) {
			return null;
		}
		if (StringUtils.endsWithAny(img, "/0", "/64", "/96", "/132")) {
			return img.replace("/0", "/46").replace("/64", "/46").replace("/96", "/46").replace("/132", "/46");
		}
		return img;
	}


	/**
	 * 增加邀请人积分
	 *
	 * @param entity
	 */
	/**
	 * @param actId   活动id
	 * @param openId2 点赞微信用户
	 * @param openId1 被点赞微信用户openid,邀请人
	 */
	public void insertInvitorScore(Integer actId, String openId2, String openId1, String mobile) {
		if (openId1 != null && openId2 != null && actId != null) {
			if (openId1.equals(openId2)) {
				//点赞人和被点赞人相同，则返回
				return;
			}
			Map<String, Object> map = new HashMap<>();
			try {
				map.put("openId", openId1);//邀请人openid
				map.put("actId", actId);//活动id
				List<WechatMemberDTO> list = wechatMemberToolService.getList(map);//查询邀请人信息
				if (list != null && list.size() == 1) {
					WechatMemberDTO wechatMemberDTO = list.get(0);//邀请人信息
					Long memberId = wechatMemberDTO.getMemberId().longValue();//邀请人谷登帐号id
					if (memberId > 0) {
						map.put("userid", memberId);//邀请人id
						map.put("visitorOpenid", openId2);//访问人openid
						map.put("avtivityId", actId);//活动id
						Integer total = actWechatInviteService.getTotal(map);//邀请人
						if (total > 0) {
							//该被点赞人是当前用户的邀请人
							map.put("userid", memberId);//邀请人id
							map.put("activityId", actId);//活动id
							map.put("type", 3);//积分类型：点赞积分
							Integer scoreTotal = activityToolService.getScoreRecordTotal2(map);//邀请人被点赞所获总积分
							//点赞积分不超过100则加分
							if (scoreTotal < 100) {
								WechatMemberEntity entity2 = new WechatMemberEntity();
								entity2.setOpenId(openId1);
								entity2.setMemberId(memberId.intValue());
								entity2.setActId(actId);
								initUserActScore("3", entity2, mobile);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 检测用户是否是当前活动期间成为的金牌供应商
	 *
	 * @param memberId 用户id
	 * @param actId    活动id
	 * @return 该用户为金牌会员，且开始生效时间在活动期间，返回true
	 */
	public boolean isJpgys(Integer memberId, String actId) {
		try {
			ActActivityBaseinfoDTO activityInfo = activityToolService.getActivityInfo(actId);
			if (activityInfo != null) {
				if (activityInfo.getLaunch().equals(ActActivityBaseinfoEntity.ACTIVITY_LAUNCH_YES)) {
					if (memberId > 0) {
						MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(memberId.toString()); //会员信息
						Integer memberGrade = memberBaseinfoDTO.getMemberGrade();//会员等级0:普通会员，1:金牌供应商
						Date validTime = memberBaseinfoDTO.getValidTime();//会员生效时间
						if (memberGrade == 1) {
							if (validTime != null) {
								//该用户为金牌会员，且开始生效时间在活动期间
								DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date begin = dateFormat2.parse("2016-11-23 00:00:00");//活动开始时间
								Date end = dateFormat2.parse("2016-12-31 23:59:59");//活动结束时间
								if (validTime.after(begin) && validTime.before(end)) {
									return true;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
