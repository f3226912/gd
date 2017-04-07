package com.gudeng.commerce.gd.admin.controller.gdactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActAdPayPenalSum;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 预付款-违约金
 * @author lf
 *
 */
@Controller
@RequestMapping("gdAdvancePayPenalSum")
public class GdAdvancePayPenalSumController extends GdActActivityController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GdAdvancePayPenalSumController.class);
	
	/**
	 * 保存预付款活动的数据
	 * @param request
	 * @return
	 * @author lf
	 */
	@RequestMapping("saveAdPayPenalSum")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, GdActAdPayPenalSum params) throws Exception {
		Map<String, Object> map = new HashMap<>();
		Integer i = 0;
		try 
		{
			i = saveActBaseInfo(request, params.getActBasicInfo(), map);
			if(i==-1) 
			{
				throw new RuntimeException("活动已被修改，请重新查询再修改...");
			}else if (i > 0) 
			{
				// 活动参与用户规则表
				boolean result1 = saveOrUpdateGdActivityUserRule(i, params.getGoodsUserRule());
				
				//保存活动预付款
				boolean result2 = saveAdvancePayment(i,params.getAdvancePaymentRule());
				
				//保存 给平台的违约金
				boolean result3 = savePenalSumForPlateformRules(i,params.getPenalSumPlateformRule());
				
				//保存给卖家的违约金
				boolean result4 = savePenalSumForSellerRules(i,params.getPenalSumSellerRule());
				
				//保存给物流公司的违约金
				boolean result5 = savePenalSumForLogisticsRules(i,params.getPenalSumLogisticsRule());
				
				if (result1 && result2 && result3 && result4 && result5) 
				{ 
					// 如果全部保存成功则整体保存成功，并清除缓存，否则，删除基本信息，达到事务回滚的目的
					gdOrderActivityBaseToolService.deleteCach(4);
					map.put("msg", "success");
				} else 
				{
					map.put("msg", "error");
				}

			} else 
			{
				gdActActivityToolService.deleteById(i + "");
				map.put("msg", "主表活动信息保存失败...");
			}
		} catch (Exception e) 
		{
			gdActActivityToolService.deleteById(i + "");
			map.put("msg", e.getMessage());
			logger.trace("新增保存错误", e);
		}
		return map;
	}
	
	/**
	 * 新增/编辑
	 */
	@Override
	@RequestMapping("gdAdvance_edit_add")
	public String addDto(HttpServletRequest request) throws Exception 
	{
		//初始化市场数据
		List<MarketDTO> markets = marketManageService.getAllByType("2");
		putModel("markets", markets);
		//活动类型
		putModel("type", 4);
		return "gdActActivity/advancePayPenalSum/add";

	}
	
	/**
	 * 编辑/查看 根据id查询活动基本信息
	 * 
	 * @param request
	 * @return
	 * @author lf
	 */
	@Override
	@RequestMapping("edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id, String views) {
		ModelAndView mv = new ModelAndView();

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("activityId", id);
			GdActActivityDTO activity = gdActActivityToolService.getSpecificActivityInfo(params);
			List<MarketDTO> markets = marketManageService.getAllByType("2");
			//活动市场信息，活动基本信息
			mv.addObject("markets", markets);
			mv.addObject("activity", activity);
			mv.addObject("activityJson", JSONArray.toJSONString(activity));
			mv.addObject("view", views);
			
		} catch (Exception e) {
			logger.info("进入查看页面错误: {}", e);
		}
		mv.setViewName("gdActActivity/advancePayPenalSum/add");
		return mv;
	}
	
	/**
	 * 根据id查询活动商品用户信息
	 * 
	 * @param request
	 * @return
	 * @author lf
	 */
	@ResponseBody
	@RequestMapping("queryUserRule")
	public Map<String,Object> queryUserRule(String id)
	{
		Map<String,Object> result = new HashMap<>();

		try {
			
			//查询买家信息
			setBuyerRule(id, result);
			
			//查询卖家信息
			setSolderRule(id, result);

		} catch (Exception e) {
			logger.info("进入查看页面错误: {}", e);
		}
		return result;
	}
	
	/**
	 * 根据ID查询预付款-违约金信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAdPayPenalSum")
	public Map<String,Object> getAdPayPenalSum(String id)
	{
		Map<String, Object> result = new HashMap<>();
		try 
		{
			//获取所有活动规则：买家预付款，给卖家的违约金，给平台的违约金，给物流公司的违约金
			List<GdActActivityCommDTO> ruleComms = gdActActivityToolService.searchRuleCommForAct(Integer.parseInt(id));

			for (int i = 0; i < ruleComms.size(); i++) {
				GdActActivityCommDTO ruleComm = ruleComms.get(i);
				String type = ruleComm.getCommRuleType();
				if ("6".equals(type)) {
					//买家预付款
					result.put("advancePaymentRule", ruleComm.getRuleJson());
				} else if ("7".equals(type)) {
					//如果是7买家给平台违约金
					result.put("penalSumPlatFormRule", ruleComm.getRuleJson());
				} else if ("8".equals(type)) {
					//如果是8买家给卖家违约金
					result.put("penalSumSellerRule", ruleComm.getRuleJson());
				}else if("9".equals(type)){
					result.put("penalSumLogisticsRule", ruleComm.getRuleJson());
					//如果是9买家给物流公司违约金
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 查询设置买家信息
	 * @param id
	 * @param result
	 * @throws Exception
	 */
	private void setBuyerRule(String id,Map<String,Object> result) throws Exception
	{
		Map<String, Object> params = new HashMap<>();
		params.put("activityId", id);
		params.put("userType", 1);
		List<GdActActivityUserRuleDTO> userRules = gdActActivityUserRuleToolService.getList(params);

		List<MemberBaseinfoDTO> buyerList = new ArrayList<>();
		for (int i = 0; i < userRules.size(); i++) 
		{
			GdActActivityUserRuleDTO userRule = userRules.get(i);

			Map<String, Object> queryMember = new HashMap<>();
			queryMember.put("memberId", userRule.getReferId());
			queryMember.put("startRow", 0);
			queryMember.put("endRow", 1);
			MemberBaseinfoDTO memberDto = memberBaseinfoToolService.getBySearch(queryMember).get(0);
			buyerList.add(memberDto);
		}

		result.put("buyerRules", buyerList);
	}
	
	/**
	 * 查询设置卖家信息
	 * @param id
	 * @param result
	 * @throws Exception
	 */
	private void setSolderRule(String id,Map<String,Object> result) throws Exception
	{
		List<BusinessBaseinfoDTO> shopList = new ArrayList<>();
		List<ProductBaseinfoDTO> productList = new ArrayList<>();
		
		Map<String,Object> solerRule = new HashMap<>();
		result.put("solderRule", solerRule);
		
		//商铺
		Map<String, Object> params = new HashMap<>();
		params.put("activityId", id);
		params.put("userType", 2);
		List<GdActActivityUserRuleDTO> shopRules = gdActActivityUserRuleToolService.getList(params);
		if(shopRules != null && shopRules.size() > 0)
		{
			for (GdActActivityUserRuleDTO shopRule : shopRules) 
			{
				Map<String, Object> queryBusiness = new HashMap<>();
				queryBusiness.put("businessId", shopRule.getReferId());
				queryBusiness.put("startRow", 0);
				queryBusiness.put("endRow", 1);
				BusinessBaseinfoDTO businessBaseinfoDTO = businessBaseinfoToolService.getBySearch(queryBusiness).get(0);
				shopList.add(businessBaseinfoDTO);
			}
		}
		
		if(shopList.size()>0)
		{
			solerRule.put("type", 2);
			solerRule.put("shops", shopList);
			return;
		}
		
		//商品
		params.put("userType", 4);
		List<GdActActivityUserRuleDTO> productRles = gdActActivityUserRuleToolService.getList(params);
		if(productRles != null && productRles.size() > 0)
		{
			for (GdActActivityUserRuleDTO productRule : productRles) 
			{
				Map<String, Object> queryProd = new HashMap<>();
				queryProd.put("productId", productRule.getReferId());
				ProductBaseinfoDTO productDto = productToolService.getListPage(queryProd).getDataList().get(0);
				productList.add(productDto);
			}
		}
		
		if(productList.size()>0)
		{
			solerRule.put("type", 4);
			solerRule.put("prods", productList);
			return;
		}
		
		//类目
		params.put("userType", 3);
		GdActActivityUserRuleDTO categoryRule = gdActActivityUserRuleToolService.getList(params).get(0);
		if(categoryRule != null)
		{
			ProductCategoryDTO productCategoryDTO = procategoryService.getProductCategoryById(categoryRule.getReferId());
			int level = productCategoryDTO.getCurLevel();
			solerRule.put("type", 3);
			solerRule.put("level",level);
			solerRule.put("categoryId", productCategoryDTO.getCategoryId());
		
			if (level > 0) {
				solerRule.put("parentId", productCategoryDTO.getParentId());
			}
			if (level == 2) {
				//获取一级类目
				ProductCategoryDTO parentCategory = procategoryService.getProductCategoryById(productCategoryDTO.getParentId());
				solerRule.put("topParentId", parentCategory.getParentId());
			}
		}
	}

	/**
	 * 保存活动预付款规则信息
	 * @param i
	 * @param ruleJsonStr
	 * @return
	 * @throws Exception
	 */
	private boolean saveAdvancePayment(Integer i, String ruleJsonStr) throws Exception 
	{
		//前台传过来的json字符串转为JSON对象，便于读取参数
		JSONObject ruleJson = JSON.parseObject(ruleJsonStr);
		
		//将参数转化为实际的规则，即JSON字符串
		JSONObject ruleDetail = new JSONObject();
		JSONObject detail = new JSONObject();
		
		// 按订单
		if ("0".equals(ruleJson.getString("advancePayment")))
		{ 
			//规则内容：预付款
			detail.put("prodLimitAmt", ruleJson.getDouble("advancePaymentAmt"));
			
			//类型
			ruleDetail.put("type", 0);
		}
		//按订单小计金额百分比
		else if("2".equals(ruleJson.getString("advancePayment")))
		{
			detail.put("orderProp", ruleJson.getDouble("advancePaymentPercent"));
			detail.put("orderLimitAmt", ruleJson.getDouble("advancePaymentLimit"));
			
			//类型
			ruleDetail.put("type", 2);
		}
		ruleDetail.put("detail", detail);
		
		GdActActivityCommDTO dto = new GdActActivityCommDTO();
		dto.setCommRuleType("6");
		dto.setActId(i);
		dto.setRuleJson(ruleDetail.toJSONString());
		gdActActivityToolService.addRuleComm(dto);
		return true;
	}
	
	/**
	 * 保存给平台的违约金规则信息
	 * @param i
	 * @param ruleJsonStr
	 * @return
	 * @throws Exception
	 */
	private boolean savePenalSumForPlateformRules(Integer i, String ruleJsonStr) throws Exception 
	{
		//前台传过来的json字符串转为JSON对象，便于读取参数
		JSONObject ruleJson = JSON.parseObject(ruleJsonStr);
		
		//将参数转化为实际的规则，即JSON字符串
		JSONObject ruleDetail = new JSONObject();
		JSONObject detail = new JSONObject();
		
		if (!"".equals(ruleJson.getString("penalSumPlatFormPercent")))
		{ 
			//规则内容：预付款
			detail.put("orderProp", ruleJson.getDouble("penalSumPlatFormPercent"));
			ruleDetail.put("detail", detail);
			ruleDetail.put("type", 2);
			GdActActivityCommDTO dto = new GdActActivityCommDTO();
			dto.setCommRuleType("7");
			dto.setActId(i);
			dto.setRuleJson(ruleDetail.toJSONString());
			gdActActivityToolService.addRuleComm(dto);
		}
		
		return true;
	}
	
	/**
	 * 保存给卖家的违约金信息
	 * @param i
	 * @param ruleJsonStr
	 * @return
	 * @throws Exception
	 */
	private boolean savePenalSumForSellerRules(Integer i, String ruleJsonStr) throws Exception 
	{
		//前台传过来的json字符串转为JSON对象，便于读取参数
		JSONObject ruleJson = JSON.parseObject(ruleJsonStr);
		//将参数转化为实际的规则，即JSON字符串
		JSONObject ruleDetail = new JSONObject();
		JSONObject detail = new JSONObject();
		
		if (!"".equals(ruleJson.getString("penalSumSellerPercent")))
		{ 
			//规则内容：预付款
			detail.put("orderProp", ruleJson.getDouble("penalSumSellerPercent"));
			ruleDetail.put("detail", detail);
			ruleDetail.put("type", 2);
			GdActActivityCommDTO dto = new GdActActivityCommDTO();
			dto.setCommRuleType("8");
			dto.setActId(i);
			dto.setRuleJson(ruleDetail.toJSONString());
			gdActActivityToolService.addRuleComm(dto);
		}
		
		return true;
	}
	
	/**
	 * 保存给物流公司的违约金信息
	 * @param i
	 * @param ruleJsonStr
	 * @return
	 * @throws Exception
	 */
	private boolean savePenalSumForLogisticsRules(Integer i, String ruleJsonStr) throws Exception 
	{
		//前台传过来的json字符串转为JSON对象，便于读取参数
		JSONObject ruleJson = JSON.parseObject(ruleJsonStr);
		//将参数转化为实际的规则，即JSON字符串
		JSONObject ruleDetail = new JSONObject();
		JSONObject detail = new JSONObject();
		if (!"".equals(ruleJson.getString("penalSumLogisticsPercent")))
		{ 
			//规则内容：预付款
			detail.put("orderProp", ruleJson.getDouble("penalSumLogisticsPercent"));
			ruleDetail.put("detail", detail);
			ruleDetail.put("type", 2);
			GdActActivityCommDTO dto = new GdActActivityCommDTO();
			dto.setCommRuleType("9");
			dto.setActId(i);
			dto.setRuleJson(ruleDetail.toJSONString());
			gdActActivityToolService.addRuleComm(dto);
		}
		
		return true;
	}
}
