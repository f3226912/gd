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
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 
 * @author gxz
 *
 */
@Controller
@RequestMapping("gdActivityInformation")
public class GdActivityInformationController extends GdActActivityController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GdActivityInformationController.class);
	
	/**
	 * 保存平台佣金活动信息
	 * @param request
	 * @return
	 * @author gxz
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
				// 如果基本信息插入成功，则插入买家卖家佣金
				boolean result1 = saveOrUpdateGdActActivityCommByPT(i, marketComm);

				// 商品用户信息
				boolean result4=saveOrUpdateGdActivityUserRule(i, userRule);
				
				//活动预付款信息
				
				//活动违约金信息

				if (result1 && result4) { // 如果全部保存成功则提示
					// 保存成功，否则保存失败，并删除此条活动
					// 清楚缓存
					gdOrderActivityBaseToolService.deleteCach(3);
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
			map.put("msg", "保存失败");
		logger.trace("新增保存错误", e);
		}
		return map;
	}
	@Override
	@RequestMapping("toAddInformation")
	public String addDto(HttpServletRequest request) throws Exception {
		List<MarketDTO> markets = marketManageService.getAllByType("2");
		/*
		 * 初始化市场数据
		 */
		putModel("markets", markets);
		//活动类型
		putModel("type", 4);
		return "gdActActivity/information/add";

	}
	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author gxz
	 */
	@Override
	@RequestMapping("edit/{id}")
	@ResponseBody
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id, String views) {
		ModelAndView mv = new ModelAndView();

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("activityId", id);
			
			GdActActivityDTO activity = gdActActivityToolService.getSpecificActivityInfo(params);
			/*配送规则代码
			List<GdActActivityDistributionModeDTO> types = gdActActivityToolService
					.getDistributionModeByActivityId(params);
			*/
			List<MarketDTO> markets = marketManageService.getAllByType("2");

			/*
			 * 初始化市场数据
			 */
			mv.addObject("markets", markets);
			/*配送规则代码
			mv.addObject("types", types);
			mv.addObject("typesJson", JSONArray.toJSONString(types));
			*/
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
				//买家佣金
				if ("1".equals(ruleComm.getCommRuleType())) {
					buyerArray.add(JSONArray.parseObject(ruleComm.getRuleJson()));
				} else if ("2".equals(ruleComm.getCommRuleType())) {
					/*
					 * 如果是卖家佣金数据
					 */
					solderArray.add(JSONArray.parseObject(ruleComm.getRuleJson()));
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

		} catch (Exception e) {
			logger.info("进入查看页面错误: {}", e);
		}
		mv.setViewName("gdActActivity/information/add");
		return mv;
	}

}
