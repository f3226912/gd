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
import com.gudeng.commerce.gd.promotion.dto.GdActActivityMarketDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityParticipationRuleDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityUserRuleEntity;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 
 * @author gxz
 *
 */
@Controller
@RequestMapping("gdProcurementIntegration")
public class GdProcurementIntegrationController extends GdActActivityController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GdProcurementIntegrationController.class);
	
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
				

				// 商品用户信息
				boolean result4=saveOrUpdateGdActivityUserRuleByJf(i, userRule);
				
				// 如果基本信息插入成功，则插入买家卖家佣金
				boolean result1 = saveOrUpdateGdActActivityCommByJF(i, marketComm);
				
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
	/**
	 * 插入平台佣金
	 * 
	 * @param activityId(活动id)
	 * @param activityRule(规则信息)
	 * @return
	 */
	public boolean saveOrUpdateGdActActivityCommByJF(Integer activityId, String marketComm) {
		/*
		 * 转义 原始JSON数据
		 */
		JSONObject marketCommJson = JSONArray.parseObject(marketComm);

		try {
			// 用于存储JSON数据的对象
			JSONObject dataMarketComm = new JSONObject();

			GdActActivityCommDTO dto = new GdActActivityCommDTO();

			/*
			 * 买家获取积分规则
			 */
		 //
				 dto.setCommRuleType("10"); // 采购积分标识
			
			     // 首单最低奖励积分
				    if(marketCommJson.getString("minIntegralBox")!=null&&marketCommJson.getString("minIntegralBox").equals("1")){
					dataMarketComm.put("minIntegral", marketCommJson.getDouble("minIntegral")==null?0:marketCommJson.getDouble("minIntegral"));
				    }else{
				    	dataMarketComm.put("minIntegral", 0);
				    }
					//单日最好累计积分
				    if(marketCommJson.getString("dayIntegralBox")!=null&&marketCommJson.getString("dayIntegralBox").equals("1")){
					dataMarketComm.put("dayMaxIntegral", marketCommJson.getDouble("dayMaxIntegral")==null?0:marketCommJson.getDouble("dayMaxIntegral"));
				    }else{
				    	dataMarketComm.put("dayMaxIntegral", -1);
				    }
					/*
					 * 区间值 集合
					 */
					JSONArray details = new JSONArray();
					dataMarketComm.put("detail", details);

					/*
					 * 默认第一个的值处理
					 */
					parseOrderLimitByJF(marketCommJson, details, "_id");

					// 读取区间值总数
					Integer count = marketCommJson.getInteger("hid_buyer_id");

					if (count > 0) {
						for (int i = 0; i < count; i++) {

							/*
							 * 处理可能存在多个的值
							 */
							parseOrderLimitByJF(marketCommJson, details, "" + i);
						}

					

				}

				/*
				 * 买家佣金 保存在实体类 准备保存数据库
				 */
				
				dto.setRuleJson(dataMarketComm.toJSONString());
				dto.setActId(activityId);
				//System.out.println(marketCommJson.getJSONArray("detail"));
 				gdActActivityToolService.addRuleComm(dto);
 
  
		

			return true;
		} catch (Exception e) {
			logger.trace("新增保存错误", e);
			return false;
		}

	}
	/**
	 * 进入指定买家列表展示页面
	 * 
	 * @param request
	 * @return
	 * @author gxz
	 */

	@RequestMapping("gdActActivity_shop_buyerJF/{type}")
	public ModelAndView showShopAndBuyer(HttpServletRequest request, @PathVariable("type") String type,String index) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.addObject("index", index);
		mv.setViewName("gdActActivity/gdActActivity_shop_buyerJF");
		return mv;

	}
	/**
	 * 转换金额区间
	 *@author gxz
	 * 
	 * @param marketCommJson
	 * @param details
	 * @param code
	 */
	private void parseOrderLimitByJF(JSONObject marketCommJson, JSONArray details, String code) {
		JSONObject detail1 = new JSONObject();
		detail1.put("startAmt", marketCommJson.getDouble("buyerStartAmt" + code));
		detail1.put("endAmt", marketCommJson.getDouble("buyerEndAmt" + code)==null?-1:marketCommJson.getDouble("buyerEndAmt" + code));
		detail1.put("integral", marketCommJson.getInteger("integral"+code));
		details.add(detail1);

	}
	
	/**
	 * 活动参与用户规则表
	 * 
	 * @param activityId(活动id)
	 * @param participationRule(规则信息)
	 * @return
	 */
	public boolean saveOrUpdateGdActivityUserRuleByJf(long activityId, String userRule) {
		JSONObject userRulesJson = JSONObject.parseObject(userRule);

		JSONObject pageDataJson = userRulesJson.getJSONObject("pageData");
		JSONArray indexJson=userRulesJson.getJSONArray("indexArray");
		try {
			/*
			 * 添加活动市场
			 */
			GdActActivityMarketDTO actMarket = new GdActActivityMarketDTO();
			actMarket.setActivity_id(Integer.parseInt("" + activityId));
			actMarket.setMarket_id(pageDataJson.getInteger("market_type"));
			gdActActivityMarketToolService.insert(actMarket);

			/*
			 * 添加用户规则
			 */
			GdActActivityUserRuleEntity entity = new GdActActivityUserRuleEntity();
			entity.setActivityId(Integer.parseInt(activityId + ""));

			/*
			 * 添加买家 清空多余数据
			 */
			entity.setRemark(null);

			/*
			 * 获取活动买家信息
			 */
			entity.setUserType(1);
			JSONArray addBuyersJson = userRulesJson.getJSONArray("addBuyerData");
             if(pageDataJson.getString("buyerBox0")!=null&&pageDataJson.getString("buyerBox0").equals("0")){
            	 entity.setReferId((long) 0); 
            	 entity.setIntegralRate(pageDataJson.getDouble("integralRate"));
					gdActActivityUserRuleToolService.insert(entity);
             }
			/*
			 * 遍历添加买家信息
			 */
			
			for(int i=0;i<indexJson.size();i++){
				for(int j=0;j<addBuyersJson.size();j++){
					JSONObject obj=addBuyersJson.getJSONObject(j);
					//循环查找获取指定的买家用户
					if(obj.get("addBuyerData"+indexJson.get(i))!=null){
						JSONArray buyerArray=obj.getJSONArray("addBuyerData"+indexJson.get(i));
						for (int k = 0; k < buyerArray.size(); k++) {
							//JSONObject addBuyerJson = addBuyersJson.getJSONObject(i);
							entity.setReferId(buyerArray.getJSONObject(k).getLong("memberId"));
							//积分倍率
							entity.setIntegralRate(pageDataJson.getDouble("integralRate"+indexJson.get(i)));
							gdActActivityUserRuleToolService.insert(entity);
 						}
					}
				}
			}
//			for (int i = 0; i < addBuyersJson.size(); i++) {
//
//				JSONObject addBuyerJson = addBuyersJson.getJSONObject(i);
//				entity.setReferId(addBuyerJson.getLong("memberId"));
//
//				/*
//				 * 添加到数据库
//				 */
//				gdActActivityUserRuleToolService.insert(entity);
//			}

			/*
			 * 添加买家物流配送订单金额范围
			 */
//			GdActActivityParticipationRuleDTO gdActActivityParticipationRuleDTO = new GdActActivityParticipationRuleDTO();
//			gdActActivityParticipationRuleDTO.setActivity_id(Integer.parseInt("" + activityId));
//			gdActActivityParticipationRuleDTO.setMax_cost(pageDataJson.getDouble("max_cost"));
//			gdActActivityParticipationRuleDTO.setMin_cost(pageDataJson.getDouble("min_cost"));

			/*
			 * 插入数据库
			 */
			//gdActActivityParticipationRuleToolService.insert(gdActActivityParticipationRuleDTO);

			return true;
		} catch (Exception e) {
			logger.trace("新增保存错误", e);
		}
		return false;
	}
	
	@Override
	@RequestMapping("toAddProcurementIntegration")
	public String addDto(HttpServletRequest request) throws Exception {
		//查询所有活动市场 4 代表活动市场
		List<MarketDTO> markets = marketManageService.getAllByType("4");
		/*
		 * 初始化市场数据
		 */
		putModel("markets", markets);
		//活动类型
		putModel("type", 6);
		return "gdActActivity/procurementIntegration/add";

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
			List<MarketDTO> markets = marketManageService.getAllByType("4");

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
				//积分规则 	
					buyerArray.add(JSONArray.parseObject(ruleComm.getRuleJson()));
		

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
			JSONObject buyerDate=new JSONObject();
			// 活动商品和商铺为集合 所以定义在集合外层
			JSONArray prodOrShopArray = new JSONArray();
			/*
			 * 整理成前端的JSON格式，便于直接给页面展示
			 */
			JSONArray	buyerDataArray= null;
			JSONObject	buyerDataObject= null;
			boolean b=false;
			for (int i = 0; i < userRules.size(); i++) {
				GdActActivityUserRuleDTO userRule = userRules.get(i);
					// JSON转换字符串后要转换成JSON对象，否则前端 JSON字符串 会多双引号无法转JSON对象
					Map<String, Object> queryMember = new HashMap<>();
					if(userRule.getReferId()!=0){
					queryMember.put("memberId", userRule.getReferId());
					queryMember.put("startRow", 0);
					queryMember.put("endRow", 1);
 					MemberBaseinfoDTO memberDto = memberBaseinfoToolService.getBySearch(queryMember).get(0);
                   for(int j=0;j<buyerRules.size();j++){
                    	  if(buyerRules.getJSONObject(j).getJSONArray(userRule.getIntegralRate()+"")!=null){
                    		  buyerDataArray=buyerRules.getJSONObject(j).getJSONArray(userRule.getIntegralRate()+"");
                    		  buyerDataObject=buyerRules.getJSONObject(j);
                    		  b=true;
                    		 break;
                    	  }else{
                    		  b=false;
                    	  }
                     }
                   if(b){
                	   buyerDataArray.add(JSONArray.parseObject(JSONArray.toJSONString(memberDto)));
                	   buyerDataObject.put(userRule.getIntegralRate()+"", buyerDataArray);
                	  // buyerRules.add(buyerDataObject);
                	   //buyerRules.set(j, buyerDataObject);
                     }else{
                    	 JSONArray	buyerDataArray2= new JSONArray();
             			JSONObject	buyerDataObject2= new JSONObject(); 
             			buyerDataArray2.add(JSONArray.parseObject(JSONArray.toJSONString(memberDto)));
                   	   buyerDataObject2.put(userRule.getIntegralRate()+"", buyerDataArray2);
                 	   buyerRules.add(buyerDataObject2);

                     }
					}else{
						buyerDate.put("integralRate", userRule.getIntegralRate());
					}

			}
			buyerDate.put("other", buyerRules);
			/*
			 * 卖家用户规则 添加到页面中
			 */
			mv.addObject("solderRule", solderRule.toJSONString());
			mv.addObject("buyerRules", buyerDate.toJSONString());

		} catch (Exception e) {
			logger.info("进入查看页面错误: {}", e);
		}
		mv.setViewName("gdActActivity/procurementIntegration/add");
		return mv;
	}

}
