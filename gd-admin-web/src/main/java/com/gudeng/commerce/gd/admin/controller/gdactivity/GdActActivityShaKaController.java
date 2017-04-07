package com.gudeng.commerce.gd.admin.controller.gdactivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDistributionModeDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityParticipationRuleDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 
 * @author Ailen
 *
 */
@Controller
@RequestMapping("gdActActivityShaKa")
public class GdActActivityShaKaController extends GdActActivityController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GdActActivityShaKaController.class);
	
	/**
	 * 保存刷卡补贴活动的数据
	 */
	@Override
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, String basicRule, String marketComm,
			String subRule, String userRule, String transferRule) throws Exception {
		Map<String, Object> map = new HashMap<>();

		Integer i = 0;
		
		try {
			
			i = saveActBaseInfo(request, basicRule, map);
			
			if(i==-1) {
				throw new RuntimeException("活动已被修改，请重新查询再修改...");
			}else if (i > 0) {
				/*// 如果基本信息插入成功，则插入买家卖家佣金
				boolean result1 = saveOrUpdateGdActActivityMarketComm(i, marketComm);
*/
				// 插入补贴佣金
				boolean result2 = saveOrUpdateGdActActivitySubRule(i, subRule);

				/*// 如果基本信息插入成功，则插入物流配送信息
				boolean result3 = saveOrUpdateGdActActivityDistributionMode(i, transferRule);
*/
				// 活动参与用户规则表
				boolean result4 = saveOrUpdateGdActivityUserRule(i, userRule);

				if (result2 && result4) { // 如果全部保存成功则提示

					// 保存成功，否则保存失败，并删除此条活动
					// 清楚缓存
					gdOrderActivityBaseToolService.deleteCach(1);
					map.put("msg", "success");
				} else {
					map.put("msg", "error");
				}

			} else {
				gdActActivityToolService.deleteById(i + "");
				map.put("msg", "主表活动信息保存失败...");
			}
		} catch (Exception e) {
			gdActActivityToolService.deleteById(i + "");
			map.put("msg", e.getMessage());
			logger.trace("新增保存错误", e);
		}
		return map;
	}
	
	@Override
	@RequestMapping("add")
	public String addDto(HttpServletRequest request) throws Exception {

		List<MarketDTO> markets = marketManageService.getAllByType("2");

		/*
		 * 初始化市场数据
		 */
		putModel("markets", markets);

		/*
		 * 
		 */
		JSONObject result = new JSONObject();
		try {
			JSONObject payChannels = CommonUtil.httpPost(gdProperties.getPayCenterUrl() + "api/getAll", null, false);

			putModel("payChannels", payChannels.toJSONString());
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "支付中心读取支付渠道连接失败");
			putModel("payChannels", result.toJSONString());
		}

		return "gdActActivity/gdActActivity_edit_add";
	}

	@Override
	@RequestMapping("edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id, String views) {
		ModelAndView mv = new ModelAndView();

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("activityId", id);

			/*
			 * //用户类型（1农商友 2农批商 3物流公司） params.put("costUserType",
			 * GdActivityRuleUserTypeEnums.NSY.getKey()); //费用类型（1预付款 2佣金）
			 * params.put("expenseType",
			 * GdActivityRuleCostTypeEnums.ADVANCE.getKey()); //计费方式（1按比例 2按订单）
			 * params.put("billingMethod",
			 * GdActRuleChargeModeEnums.PERCENT.getKey());
			 */

			GdActActivityDTO activity = gdActActivityToolService.getSpecificActivityInfo(params);

			List<GdActActivityDistributionModeDTO> types = gdActActivityToolService
					.getDistributionModeByActivityId(params);

			List<MarketDTO> markets = marketManageService.getAllByType("2");

			/*
			 * 初始化市场数据
			 */
			mv.addObject("markets", markets);

			mv.addObject("types", types);
			mv.addObject("typesJson", JSONArray.toJSONString(types));
			mv.addObject("activity", activity);
			mv.addObject("activityJson", JSONArray.toJSONString(activity));

			mv.addObject("view", views);

			/*
			 * 获得对应的所有rule
			 */

			List<GdActActivityCommDTO> ruleComms = gdActActivityToolService.searchRuleCommForAct(Integer.parseInt(id));

			JSONArray buyerArray = new JSONArray();
			JSONArray solderArray = new JSONArray();
			JSONArray ruleArray = new JSONArray();
			for (int i = 0; i < ruleComms.size(); i++) {
				GdActActivityCommDTO ruleComm = ruleComms.get(i);
				/*
				 * 如果是买家佣金数据
				 */
				if ("3".equals(ruleComm.getCommRuleType())) {
					buyerArray.add(JSONArray.parseObject(ruleComm.getRuleJson()));
				} else if ("4".equals(ruleComm.getCommRuleType())) {
					/*
					 * 如果是卖家佣金数据
					 */
					solderArray.add(JSONArray.parseObject(ruleComm.getRuleJson()));
				} else if ("5".equals(ruleComm.getCommRuleType())) {
					/*
					 * 如果是补贴规则
					 */
					ruleArray.add(JSONArray.parseObject(ruleComm.getRuleJson()));
				}

			}

			mv.addObject("buyerArray", buyerArray.toJSONString());
			mv.addObject("solderArray", solderArray.toJSONString());
			mv.addObject("ruleArray", ruleArray.toJSONString());

			/*
			 * 用户配置读取
			 */
			Map<String, Object> queryUserRule = new HashMap<>();
			queryUserRule.put("activityId", id);
			List<GdActActivityUserRuleDTO> userRules = gdActActivityUserRuleToolService.getList(queryUserRule);

			JSONObject solderRule = new JSONObject();
			JSONArray buyerRules = new JSONArray();

			// 活动商品和商铺为集合 所以定义在集合外层
			JSONArray prodOrShopArray = new JSONArray();
			/*
			 * 整理成前端的JSON格式，便于直接给页面展示
			 */
			for (int i = 0; i < userRules.size(); i++) {
				GdActActivityUserRuleDTO userRule = userRules.get(i);
				/*
				 * 如果是关联类目
				 */
				if (userRule.getUserType() == 3) {

					refUserRuleCategory(solderRule, userRule); // 处理类目的JSON拼装

				} else if (userRule.getUserType() == 4) {

					refProdUserRule(solderRule, prodOrShopArray, userRule); // 处理获得商品关联JSON拼装

				} else if (userRule.getUserType() == 2) {
					/*
					 * 设置为活动商铺
					 */
					if (solderRule.getInteger("type") == null) {
						solderRule.put("type", 2);
						solderRule.put("shops", prodOrShopArray);
					}

					// 活动商品为集合 所以定义在集合外层

					/*
					 * 查询对应的商铺信息 同时转换JSON 存储进json数组 JSON转换字符串后要转换成JSON对象，否则前端
					 * JSON字符串 会多双引号无法转JSON对象
					 */
					Map<String, Object> queryBusiness = new HashMap<>();
					queryBusiness.put("businessId", userRule.getReferId());
					queryBusiness.put("startRow", 0);
					queryBusiness.put("endRow", 1);
					BusinessBaseinfoDTO businessBaseinfoDTO = businessBaseinfoToolService.getBySearch(queryBusiness)
							.get(0);
					prodOrShopArray.add(JSONArray.parseObject(JSONArray.toJSONString(businessBaseinfoDTO)));

				} else if (userRule.getUserType() == 1) {

					// JSON转换字符串后要转换成JSON对象，否则前端 JSON字符串 会多双引号无法转JSON对象
					Map<String, Object> queryMember = new HashMap<>();
					queryMember.put("memberId", userRule.getReferId());
					queryMember.put("startRow", 0);
					queryMember.put("endRow", 1);
					MemberBaseinfoDTO memberDto = memberBaseinfoToolService.getBySearch(queryMember).get(0);
					buyerRules.add(JSONArray.parseObject(JSONArray.toJSONString(memberDto)));
				}

			}

			/*
			 * 卖家用户规则 添加到页面中
			 */
			mv.addObject("solderRule", solderRule.toJSONString());
			mv.addObject("buyerRules", buyerRules.toJSONString());

			/*
			 * 支付渠道
			 */
			JSONObject result = new JSONObject();
			try {
				JSONObject payChannels = CommonUtil.httpPost(gdProperties.getPayCenterUrl() + "/api/getAll", null,
						false);

				mv.addObject("payChannels", payChannels.toJSONString());
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "支付中心读取支付渠道连接失败");
				mv.addObject("payChannels", result.toJSONString());
			}

			/*
			 * 获得配送订单金额区间
			 */
			Map<String, Object> queryPartRule = new HashMap<>();
			queryPartRule.put("activity_id", id);
			queryPartRule.put("startRow", 0);
			queryPartRule.put("endRow", 1);

			List<GdActActivityParticipationRuleDTO> list = gdActActivityParticipationRuleToolService
					.getList(queryPartRule);
			if (list.size() > 0) {
				GdActActivityParticipationRuleDTO actActivityParticipationRuleDTO = list.get(0);
				mv.addObject("partRule", JSONArray.toJSONString(actActivityParticipationRuleDTO));
			}

		} catch (Exception e) {
			logger.info("进入查看页面错误: {}", e);
		}
		mv.setViewName("gdActActivity/gdActActivity_edit_add");
		return mv;
	}
}
