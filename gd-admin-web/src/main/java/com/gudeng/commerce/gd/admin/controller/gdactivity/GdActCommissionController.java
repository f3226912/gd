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
 * 市场佣金
 * 
 * @author qrxu
 *
 */
@Controller
@RequestMapping("gdactMarketCommission")
public class GdActCommissionController extends GdActActivityController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GdActCommissionController.class);

	/**
	 * 进入新增或编辑 页面
	 * 
	 * @param request
	 * @return
	 * @author weiwenke
	 */

	@RequestMapping("gdActActivity_edit_add")
	public String addDto(HttpServletRequest request) throws Exception {
		List<MarketDTO> markets = marketManageService.getAllByType("2");
		// 市场
		putModel("markets", markets);
		// 活动类型
		putModel("type", 2);
		return "gdActActivity/marketCommission/gdActActivity_edit_add";
	}
	/**
	 * 新增活动 或者编辑 
	 */
	@RequestMapping("addBaseInfo")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, String basicRule, String userRule,
			String transferRule, String marketComm) throws Exception {

		Map<String, Object> map = new HashMap<>();
		Integer activityId = 0;
		try {
			activityId = saveActBaseInfo(request, basicRule, map);
			if (activityId == -1) {
				throw new RuntimeException("活动已被修改，请重新查询再修改...");
			} else if (activityId > 0) {

				// 活动参与用户规则表
				boolean userRuleRes = saveOrUpdateGdActivityUserRule(activityId, userRule);
				// 市场佣金
				boolean marketCommRes =saveOrUpdateGdActActivityMarket(activityId, marketComm);
				if (userRuleRes && marketCommRes) { // 如果全部保存成功则提示
								// 保存成功，否则保存失败，并删除此条活动
					// 清楚缓存
					gdOrderActivityBaseToolService.deleteCach(2);
					map.put("msg", "success");
				} else {
					map.put("msg", "error");
				}

			} else {
				gdActActivityToolService.deleteById(activityId + "");
				map.put("msg", "主表活动信息保存失败...");
			}
		} catch (Exception e) {
			gdActActivityToolService.deleteById(activityId + "");
			map.put("msg", "保存失败");
			logger.trace("新增保存错误", e);
		}
		return map;
	}
	/**
	 * 插入市场佣金
	 * 
	 * @param activityId(活动id)
	 * @param activityRule(规则信息)
	 * @return
	 */
	public boolean saveOrUpdateGdActActivityMarket(Integer activityId, String marketComm) {
		/*
		 * 转义 原始JSON数据
		 */
		JSONObject marketCommJson = JSONArray.parseObject(marketComm);

		try {
			// 用于存储JSON数据的对象
			JSONObject dataMarketComm = new JSONObject();

			GdActActivityCommDTO dto = new GdActActivityCommDTO();

			/*
			 * 买家
			 */
			if (marketCommJson.getInteger("buyer") != null) { //
				dto.setCommRuleType("3"); // 买家佣金标识
				if (marketCommJson.getInteger("buyer") == 0) { // 按商品
					dataMarketComm.put("type", 0);
					JSONObject detail = new JSONObject();
					dataMarketComm.put("minAmt", marketCommJson.getDouble("buyerAmt")==null?0:marketCommJson.getDouble("buyerAmt"));
					dataMarketComm.put("detail", detail);
					detail.put("prodLimitAmt", marketCommJson.getDouble("buyerOrderProd"));
				} else if (marketCommJson.getInteger("buyer") == 1) { // 按订单
					dataMarketComm.put("type", 1);
					JSONObject detail = new JSONObject();
					dataMarketComm.put("detail", detail);
					detail.put("orderLimitAmt", marketCommJson.getDouble("buyerOrderAmt"));
				} else if (marketCommJson.getInteger("buyer") == 2) { // 按订单比例
					dataMarketComm.put("type", 2);
					JSONObject detail = new JSONObject();
					dataMarketComm.put("detail", detail);
					detail.put("orderLimitAmt", marketCommJson.getDouble("orderBuyerLimit"));
					detail.put("orderProp", marketCommJson.getDouble("orderBuyerSub"));
				} else if (marketCommJson.getInteger("buyer") == 3) { // 按订单金额区间
					dataMarketComm.put("type", 3);
					dataMarketComm.put("minAmt", marketCommJson.getDouble("buyerAmt")==null?0:marketCommJson.getDouble("buyerAmt"));

					/*
					 * 区间值 集合
					 */
					JSONArray details = new JSONArray();
					dataMarketComm.put("detail", details);

					/*
					 * 默认第一个的值处理
					 */
					parseOrderLimitMarker(marketCommJson, details, "_id");

					// 读取区间值总数
					Integer count = marketCommJson.getInteger("hid_buyer_id");

					if (count > 0) {
						for (int i = 0; i < count; i++) {

							/*
							 * 处理可能存在多个的值
							 */
							parseOrderLimitMarker(marketCommJson, details, "" + i);
						}

					}

				}

				/*
				 * 买家佣金 保存在实体类 准备保存数据库
				 */
				dto.setRuleJson(dataMarketComm.toJSONString());
				dto.setActId(activityId);
				if(marketCommJson.getDouble("buyerAmt")!=null&&!marketCommJson.get("buyerAmt").equals("")){
					gdActActivityToolService.addRuleComm(dto);
					}
//				gdActActivityToolService.addRuleComm(dto);

			}

			dataMarketComm = new JSONObject();
			dto = new GdActActivityCommDTO();
			/*
			 * 卖家正向
			 */
			if (marketCommJson.getInteger("solder") != null) {
				dto.setCommRuleType("4"); // 卖家佣金标识
				if (marketCommJson.getInteger("solder") == 1) { // 按订单
					dataMarketComm.put("type", 1);
					JSONObject detail = new JSONObject();
					dataMarketComm.put("detail", detail);
					detail.put("orderLimitAmt", marketCommJson.getDouble("solderOrderAmt"));
				} else if (marketCommJson.getInteger("solder") == 2) { // 按订单比例
					dataMarketComm.put("type", 2);
					JSONObject detail = new JSONObject();
					dataMarketComm.put("detail", detail);
					detail.put("orderLimitAmt", marketCommJson.getDouble("orderSolderLimit"));
					detail.put("orderProp", marketCommJson.getDouble("orderSolderSub"));
				} else if (marketCommJson.getInteger("solder") == 3) { // 按订单金额区间
					dataMarketComm.put("type", 3);
					dataMarketComm.put("minAmt", marketCommJson.getDouble("sellerAmt")==null?0:marketCommJson.getDouble("sellerAmt"));

					/*
					 * 区间值 集合
					 */
					JSONArray details = new JSONArray();
					dataMarketComm.put("detail", details);
//					dataMarketComm.put("isReverse", marketCommJson.getInteger("solderStartAmt_isReverse"));
//					dataMarketComm.put("", value)
				
					//处理买家正向
					parseOrderLimitSolderMarker(marketCommJson, details, "_id");
					
					//处理买家逆向
//					if (marketCommJson.getInteger("isReverse_cb") == 0) {
						parseOrderLimitSolderReverse(marketCommJson, details, "_id");
//				}
					
					// 读取区间值总数正
					Integer count = marketCommJson.getInteger("hid_solder_id");
					// 读取区间值总数逆
					Integer count1 = marketCommJson.getInteger("reverse_hid_solder_id")==null?0:marketCommJson.getInteger("reverse_hid_solder_id");					

					if (count > 0) {
						for (int i = 0; i < count; i++) {

							/*
							 * 处理可能存在多个的值
							 */
							parseOrderLimitSolderMarker(marketCommJson, details, "" + i);
						}
					}
					if (count1 > 0) {
						for (int i = 0; i < count1; i++) {

							/*
							 * 处理可能存在多个的值
							 */
							parseOrderLimitSolderReverse(marketCommJson, details, "" + i);
						}
					}

				}

				/*
				 * 买家佣金 保存在实体类 准备保存数据库
				 */
				dto.setRuleJson(dataMarketComm.toJSONString());
				dto.setActId(activityId);
				if(marketCommJson.getDouble("sellerAmt")!=null&& !marketCommJson.get("sellerAmt").equals("")){
					gdActActivityToolService.addRuleComm(dto);
					}
//				gdActActivityToolService.addRuleComm(dto);

			}

			return true;
		} catch (Exception e) {
			logger.trace("新增保存错误", e);
			return false;
		}

	}
	/**
	 * 转换订单区间值到数据库结构 for buyer
	 * 
	 * @param marketCommJson
	 * @param details
	 * @param code
	 */
	private void parseOrderLimitMarker(JSONObject marketCommJson, JSONArray details, String code) {
		JSONObject detail1 = new JSONObject();
		detail1.put("startAmt", marketCommJson.getDouble("buyerStartAmt" + code));
		detail1.put("endAmt", marketCommJson.getDouble("buyerEndAmt" + code));

		if (marketCommJson.getInteger("buyer_order" + code) == 0) {
			detail1.put("type", 0);
			detail1.put("orderLimitAmt", marketCommJson.getDouble("buyerLimitAmt" + code));
			detail1.put("orderProp", marketCommJson.getDouble("buyerPropAmt" + code));
		} else {
			detail1.put("type", 1);
			detail1.put("fixedAmt", marketCommJson.getDouble("buyerFixedAmt" + code));
		}
		if(detail1.getInteger("type")==0){
			if(detail1.get("startAmt")==null||detail1.get("orderProp")==null){
				return;
			}
			}
			if(detail1.getInteger("type")==1){
				if(detail1.get("fixedAmt")==null|| detail1.get("fixedAmt").equals("")){
					return;
				}
				}
		details.add(detail1);
	}

	/**
	 * 转换订单区间值到数据库结构 for solder 正
	 * 
	 * @param marketCommJson
	 * @param details
	 * @param code
	 */
	private void parseOrderLimitSolderMarker(JSONObject marketCommJson, JSONArray details, String code) {
		JSONObject detail1 = new JSONObject();
		detail1.put("startAmt", marketCommJson.getDouble("solderStartAmt" + code));
		detail1.put("endAmt", marketCommJson.getDouble("solderEndAmt" + code));
		detail1.put("isReverse", marketCommJson.getDouble("solderStartAmt_isReverse" + code));

		if (marketCommJson.getInteger("solder_order" + code) == 0) {
			detail1.put("type", 0);
			detail1.put("orderLimitAmt", marketCommJson.getDouble("solderLimitAmt" + code));
			detail1.put("orderProp", marketCommJson.getDouble("solderPropAmt" + code));
		} else {
			detail1.put("type", 1);
			detail1.put("fixedAmt", marketCommJson.getDouble("solderFixedAmt" + code));
		}
		//  处理逆向
		if(detail1.getInteger("type")==0){
			if(detail1.get("startAmt")==null||detail1.get("orderProp")==null){
				  return;
			}
		}
		if(detail1.getInteger("type")==1){
			if(detail1.get("startAmt")==null||detail1.get("fixedAmt")==null){
				  return;
			}
		}
		details.add(detail1);
	}

	/**
	 * 转换订单区间值到数据库结构 for solder 逆
	 * 
	 * @param marketCommJson
	 * @param details
	 * @param code
	 */
	private void parseOrderLimitSolderReverse(JSONObject marketCommJson, JSONArray details, String code) {
		JSONObject detail1 = new JSONObject();
		detail1.put("startAmt", marketCommJson.getDouble("solderStartAmt1" + code));
		detail1.put("endAmt", marketCommJson.getDouble("solderEndAmt1" + code));
		detail1.put("isReverse", marketCommJson.getDouble("solderStartAmt1_isReverse" + code));

		if (marketCommJson.getInteger("solder_order1" + code) == 0) {
			detail1.put("type", 0);
			detail1.put("orderLimitAmt", marketCommJson.getDouble("solderLimitAmt1" + code));
			detail1.put("orderProp", marketCommJson.getDouble("solderPropAmt1" + code));
		} else {
			detail1.put("type", 1);
			detail1.put("fixedAmt", marketCommJson.getDouble("solderFixedAmt1" + code));
		}
		if(detail1.getInteger("type")==0){
			if(detail1.get("startAmt")==null||detail1.get("orderProp")==null){
				  return;
			}
		}
		if(detail1.getInteger("type")==1){
			if(detail1.get("startAmt")==null||detail1.get("fixedAmt")==null){
				  return;
			}
		}
		details.add(detail1);
	}

	/**
	 * 编辑
	 */
	@Override
	@RequestMapping("toEdit/{id}")
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
				if ("3".equals(ruleComm.getCommRuleType())) {
					buyerArray.add(JSONArray.parseObject(ruleComm.getRuleJson()));
				} else if ("4".equals(ruleComm.getCommRuleType())) {
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
				mv.setViewName("gdActActivity/marketCommission/gdActActivity_edit_add");
				return mv;
	}
}
