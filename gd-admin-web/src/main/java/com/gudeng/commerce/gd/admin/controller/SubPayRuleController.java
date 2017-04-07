package com.gudeng.commerce.gd.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.CarWeighProToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.service.SubLimitRuleToolService;
import com.gudeng.commerce.gd.admin.service.SubRuleToolService;
import com.gudeng.commerce.gd.admin.service.SystemCodeToolService;
import com.gudeng.commerce.gd.admin.util.JSONMessage;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;
import com.gudeng.commerce.gd.order.dto.CategoryTreeDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleParamDTO;
import com.gudeng.commerce.gd.order.entity.SubPayRuleEntity;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;

@Controller
@RequestMapping("/subpayrule")
public class SubPayRuleController extends AdminBaseController {

	private Logger log = LoggerFactory.getLogger(SubPayRuleController.class);

	@Autowired
	public ProCategoryService proCategoryService;

	@Autowired
	public SystemCodeToolService systemCodeToolService;

	@Autowired
	public SubRuleToolService subRuleToolService;
	
	@Autowired
	public CarWeighProToolService carWeighProToolService;

	@Autowired
	public MarketManageService marketManageService;
	
	@Autowired
	public SubLimitRuleToolService subLimitRuleToolService;
	

	@RequestMapping(value = "/getProductCategory", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Object getProductCategory(
			@RequestParam(value = "marketId", required = false) Long marketId) {

		try {
			List<ProductCategoryDTO> list = proCategoryService
					.getProductCategoryByMarketId(marketId);
			log.info(StringUtils.center(list.size() + "   原数据", 30, "="));
			List<CategoryTreeDTO> treeList = new ArrayList<CategoryTreeDTO>();

			// 转换成适应的对象
			for (ProductCategoryDTO dto : list) {
				CategoryTreeDTO tDto = new CategoryTreeDTO();
				tDto.setCategoryId(dto.getCategoryId());
				tDto.setCurLevel(dto.getCurLevel());
				tDto.setName(dto.getCateName());
				tDto.setParentId(dto.getParentId());
				treeList.add(tDto);
			}
			log.info(StringUtils.center(list.size() + "   转换后", 30, "="));
			// list转换成map
			HashMap<Long, CategoryTreeDTO> treeMap = new HashMap<Long, CategoryTreeDTO>();
			for (Iterator<CategoryTreeDTO> it = treeList.iterator(); it
					.hasNext();) {
				CategoryTreeDTO dataRecord = (CategoryTreeDTO) it.next();
				dataRecord.setChildren(new HashSet<CategoryTreeDTO>());
				// 只用到3级分类
				if (dataRecord.getCurLevel() >= 3)
					continue;
				treeMap.put(dataRecord.getCategoryId(), dataRecord);
			}
			log.info(StringUtils.center(treeMap.size() + "   Map", 30, "="));

			// 设置层级
			Set<Entry<Long, CategoryTreeDTO>> entrySet = treeMap.entrySet();
			for (Iterator<Entry<Long, CategoryTreeDTO>> it = entrySet
					.iterator(); it.hasNext();) {
				CategoryTreeDTO node = (CategoryTreeDTO) it.next().getValue();
				if (node == null) {
					log.info(StringUtils.center(" 获取不到对象 ", 30, "="));
					continue;
				}
				topLevel(node, treeMap);
			}

			Set<CategoryTreeDTO> cSet = new HashSet<CategoryTreeDTO>();
			for (Iterator<Entry<Long, CategoryTreeDTO>> it = entrySet
					.iterator(); it.hasNext();) {
				CategoryTreeDTO node = (CategoryTreeDTO) it.next().getValue();
				if (node.getCurLevel() > 0) {
					treeMap.remove(node);
					continue;
				}
				cSet.add(node);
			}
			log.info(StringUtils.center(cSet.size() + "", 30, "="));
			return gson.toJson(cSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param node
	 * @param treeMap
	 */
	private void topLevel(CategoryTreeDTO node,
			Map<Long, CategoryTreeDTO> treeMap) {
		// 如果是第一级
		Long pId = node.getParentId();
		if (pId == null || pId.equals("") || pId <= 0) {
			log.info(StringUtils.center(node.getCategoryId() + "pId为空,当前等级 "
					+ node.getCurLevel(), 30, "="));
			return;
		}
		if (treeMap.get(pId) == null) {
			log.info(StringUtils.center(node.getCategoryId() + "父级对象为空,当前等级 "
					+ node.getCurLevel(), 30, "="));
			return;
		}
		CategoryTreeDTO pNode = treeMap.get(pId);
		if (pNode.getChildren() == null) {
			pNode.setChildren(new HashSet<CategoryTreeDTO>());
		}
		pNode.getChildren().add(node);
		topLevel(pNode, treeMap);
	}

	/**
	 * 添加规则
	 * 
	 * @param subrule
	 * @return
	 */
	@RequestMapping("/addRule/1")
	public String addRule(Model model) {
		try {
			List<MarketDTO> mList = marketManageService.getAllByType("2");
			// systemCodeToolService.getListViaType("ProductUnit");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", 1);
			List<CarWeighProDTO> carlist = carWeighProToolService.getCarWeighProList(map);
			model.addAttribute("carList", carlist);
			model.addAttribute("mList", mList);
			
			map.clear();
			String markerId = getRequest().getParameter("marketId");
			if (StringUtils.isNotBlank(markerId)) {
				map.put("marketId", markerId);
			}
			map.put("status", "1");
			List<SubLimitRuleDTO> slist = subLimitRuleToolService.getSubLimitRuleDetail(map);
			if(!CollectionUtils.isEmpty(slist)){
				SubLimitRuleDTO sublimit = slist.get(0);
				model.addAttribute("sublimit", sublimit);
				model.addAttribute("marketId", sublimit.getMarketId());
			}
			return "sub/addSubPayRule";
		} catch (Exception e) {
			e.printStackTrace();
			return "sub/addSubPayRule";
		}
	}

	/**
	 * 添加规则
	 * 
	 * @param subrule
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRule/2", produces = { "application/json;charset=utf-8" })
	public @ResponseBody
	Object addRule(HttpServletRequest request, SubPayRuleParamDTO subrule) {
		Map<String,Object> code = new HashMap<String,Object>();
		SysRegisterUser user = this.getUser(request);
		SubPayRuleEntity spe = subrule.getSubPayRule();
		spe.setStatus("3");
		if(spe.getTimeStart().getTime()>spe.getTimeEnd().getTime()){
			code.put("code", "timeerror");
			return gson.toJson(code);
		}		
		if(spe.getTimeStart().getTime()<=System.currentTimeMillis()){
			spe.setStatus("1");
		}
		spe.setCreateTime(new Date());
		String rtype = spe.getSubType();
		if (null == user || StringUtils.isBlank(user.getUserID())) {
			code.put("code", "expired");
			return gson.toJson(code);
		}
		int n = 1000;
		spe.setCashCoefficient(subrule.getParse(subrule.getCashCoefficient(), n));
		spe.setPosCoefficient(subrule.getParse(subrule.getPosCoefficient(), n));
		spe.setWalletCoefficient(subrule.getParse(
				subrule.getWalletCoefficient(), n));
		spe.setCreateUserId(user.getUserID());
		if (StringUtils.isBlank(rtype)){
			code.put("code", "fail");
			return gson.toJson(code);
		}
		if(StringUtils.isBlank(spe.getSubRuleName())){
			code.put("code", "rulenamenull");
			return gson.toJson(code);
		}
		Map<String,Object> pMap = new HashMap<String,Object>();
		try {
			pMap.put("subRuleName", spe.getSubRuleName());
			SubPayRuleDTO sub = subRuleToolService.getRuleInfo(pMap);
			if(null!=sub){
				code.put("code", "subname");
				return gson.toJson(code);
			}
			
			
			int subType = Integer.parseInt(rtype);
			if (subType == 1 || subType == 2) {
				subrule.setcList(null);
				subrule.setmList(null);
				subrule.setrList(null);
			}
			if (subType == 1) {
				spe.setSubPercent(subrule.getParse(subrule.getSubPercent(), n));
			}
			subrule.setSubPayRule(spe);

			String cateJson = spe.getCateJson();
			log.info(cateJson);
			ArrayList<Map<String, Object>> fromJson = gson.fromJson(cateJson,
					ArrayList.class);
			log.info(gson.toJson(fromJson));
			Set<CategoryTreeDTO> list = new HashSet<CategoryTreeDTO>();
			list = getCateList(fromJson, list);
			log.info("------------>分类个数" + list.size());
			if(list.size()<=0){
				code.put("code", "catenull");
				return gson.toJson(code);
			}
			
			
			//分类校验，如果有禁用，排队，启用的规则中同时间，同补贴对象，同市场含有该分类就不允许添加
			pMap.clear();
			pMap.put("marketId", spe.getMarketId());
			pMap.put("memberType", spe.getMemberType());
			pMap.put("endTime", spe.getTimeEnd());
			pMap.put("startTime", spe.getTimeStart());
			CategoryTreeDTO cate = subRuleToolService.validateCate(list, pMap);
			//如果获取到结果，就表示已有该分类
			if(null!=cate){
				code.put("cate", cate);
				code.put("code", "same");
				return gson.toJson(code);
			}
			subrule.setcList(list);
			subRuleToolService.addSubRule(subrule);
			code.put("code", "success");
			return gson.toJson(code);
		} catch (Exception e) {
			e.printStackTrace();
			code.put("code", "fail");
			return gson.toJson(code);
		}

	}

	/**
	 * 
	 * @param set
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set<CategoryTreeDTO> getCateList(
			ArrayList<Map<String, Object>> set, Set<CategoryTreeDTO> list) {
		if (set == null)
			return null;
		Iterator<Map<String, Object>> it = set.iterator();
		while (it.hasNext()) {
			Map<String, Object> tempMap = it.next();
			CategoryTreeDTO cdto = new CategoryTreeDTO();
			if (null == tempMap)
				continue;
			String cateId = tempMap.get("categoryId").toString();
			long cid = Long.parseLong(cateId.substring(0, cateId.indexOf(".")));
			String lev = tempMap.get("curLevel").toString();
			int level = Integer.parseInt(lev.substring(0, lev.indexOf(".")));
//			if(level==2){//只需要添加第三级的商品类目就可以
//				cdto.setCategoryId(cid);
//				cdto.setCurLevel(level);
//				list.add(cdto);
//			}
			ArrayList<Map<String, Object>> tempSet = (ArrayList<Map<String, Object>>) tempMap
					.get("children");
			if (null != tempSet && tempSet.size() > 0) {
				getCateList(tempSet, list);
			}else{
				cdto.setCategoryId(cid);
				cdto.setCurLevel(level);
				list.add(cdto);
			}
		}
		return list;
	}

	/**
	 * 规则详情
	 * 
	 * @param subrule
	 * @return
	 */
	@RequestMapping("/ruleDetail/{ruleId}")
	public String ruleDetail(Model model, @PathVariable("ruleId") int ruleId) {
		try {
			List<MarketDTO> mList = marketManageService.getAllByType("2");
			model.addAttribute("mList", mList);
			SubPayRuleDTO dto = subRuleToolService.getRuleInfo(ruleId);
			model.addAttribute("rule", dto);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", 1);
			List<CarWeighProDTO> carlist = carWeighProToolService.getCarWeighProList(map);
			model.addAttribute("carList", carlist);			
			return "sub/editSubPayRule";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 规则详情
	 * 
	 * @param subrule
	 * @return
	 */
	@RequestMapping("/copyRule/{ruleId}")
	public String copyRule(Model model, @PathVariable("ruleId") int ruleId) {
		try {
			List<MarketDTO> mList = marketManageService.getAllByType("2");
			model.addAttribute("mList", mList);
			SubPayRuleDTO dto = subRuleToolService.getRuleInfo(ruleId);
			model.addAttribute("rule", dto);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", 1);
			List<CarWeighProDTO> carlist = carWeighProToolService.getCarWeighProList(map);
			model.addAttribute("carList", carlist);		
			
			
			model.addAttribute("marketId", dto.getMarketId());
			String markerId = getRequest().getParameter("marketId");
			map.clear();
			if (StringUtils.isNotBlank(markerId)) {
				map.put("marketId", markerId);
				
				if(Integer.parseInt(markerId)!=(dto.getMarketId())){
					dto.setCateJson(null);
					model.addAttribute("marketId", markerId);
				}
			}
			map.put("status", "1");
			List<SubLimitRuleDTO> slist = subLimitRuleToolService.getSubLimitRuleDetail(map);
			if(!CollectionUtils.isEmpty(slist)){
				SubLimitRuleDTO sublimit = slist.get(0);
				model.addAttribute("sublimit", sublimit);
			}
			
			return "sub/copySubPayRule";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	

	/**
	 * 规则列表
	 * 
	 * @param model
	 * @param ruleId
	 * @return
	 */
	@RequestMapping("/ruleList")
	public String ruleDetail(Model model) {

		return "sub/subPayRule_list";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("ruleQuery")
	@ResponseBody
	public String ruleListQuery(HttpServletRequest request) {
		try {
			
			String categroy = request.getParameter("categroy");
			String isSearch = request.getParameter("search");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("categroy", categroy);
			map.put("search", isSearch);
			// 获取条件记录总数

			map.put("total", subRuleToolService.getRuleTotal(map));
			

			// 设置分页参数
			setCommParameters(request, map);

			// 数据集合
			List<SubPayRuleDTO> list = subRuleToolService.getRuleList(map);
			// rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param ruleId
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateRuleStatus")
	public @ResponseBody
	String updateRuleStatus(
			@RequestParam(value = "ruleId", required = true) int ruleId,
			@RequestParam(value = "status", required = true) int status) {

		Map<String, Object> map = new HashMap<String, Object>();
		SysRegisterUser user = this.getUser(request);
		map.put("ruleId", ruleId);
		map.put("status", status);
		map.put("createUserId", user.getUserID());
		int result = 0;
		try {
			result = subRuleToolService.updateRuleStatus(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = 0;
		}
		map.put("result", result);
		return JSONObject.toJSONString(map,
				SerializerFeature.WriteDateUseDateFormat);

	}

	/**
	 * 修改规则详情
	 * 
	 * @param subrule
	 * @return
	 */
	@RequestMapping("/editRule/{ruleId}")
	public String editRule(Model model, @PathVariable("ruleId") int ruleId) {
		try {
			List<MarketDTO> mList = marketManageService.getAllByType("2");
			model.addAttribute("mList", mList);
			SubPayRuleDTO dto = subRuleToolService.getRuleInfo(ruleId);
			model.addAttribute("rule", dto);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", 1);
			List<CarWeighProDTO> carlist = carWeighProToolService.getCarWeighProList(map);
			model.addAttribute("carList", carlist);	
			
			model.addAttribute("marketId", dto.getMarketId());
			String markerId = getRequest().getParameter("marketId");
			map.clear();
			if (StringUtils.isNotBlank(markerId)) {
				map.put("marketId", markerId);
				
				if(Integer.parseInt(markerId)!=(dto.getMarketId())){
					dto.setCateJson(null);
					model.addAttribute("marketId", markerId);
				}
			}
			map.put("status", "1");
			List<SubLimitRuleDTO> slist = subLimitRuleToolService.getSubLimitRuleDetail(map);
			SubLimitRuleDTO sublimit = slist.get(0);
			model.addAttribute("sublimit", sublimit);
			return "sub/modifySubPayRule";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 修改补贴规则，只有状态为排队的才能修改	
	 * @param request
	 * @param subrule
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="updateRule", produces = { "application/json;charset=utf-8" })
	@ResponseBody	
	public String updateRule(HttpServletRequest request, SubPayRuleParamDTO subrule){
		Map<String,Object> code = new HashMap<String,Object>();
		SysRegisterUser user = this.getUser(request);
		SubPayRuleEntity spe = subrule.getSubPayRule();
		spe.setStatus("3");
		spe.setCreateTime(new Date());
		String rtype = spe.getSubType();
		if (null == user || StringUtils.isBlank(user.getUserID())) {
			code.put("code", "expired");
			return gson.toJson(code);
		}
		int n = 1000;
		spe.setCashCoefficient(subrule.getParse(subrule.getCashCoefficient(), n));
		spe.setPosCoefficient(subrule.getParse(subrule.getPosCoefficient(), n));
		spe.setWalletCoefficient(subrule.getParse(
				subrule.getWalletCoefficient(), n));
		spe.setUpdateUserId(user.getUserID());
		spe.setUpdateTime(new Date());
		
		
		if (StringUtils.isBlank(rtype)){
			code.put("code", "fail");
			return gson.toJson(code);
		}
		Map<String,Object> pMap = new HashMap<String,Object>();
		try {
			//验证是否存在规则名称
			pMap.put("subRuleName", spe.getSubRuleName());
			pMap.put("rId", spe.getRuleId());
			SubPayRuleDTO sub = subRuleToolService.getRuleInfo(pMap);
			if(null!=sub){
				code.put("code", "subname");
				return gson.toJson(code);
			}
			if(spe.getTimeStart().getTime()>spe.getTimeEnd().getTime()){
				code.put("code", "timeerror");
				return gson.toJson(code);
			}	
			
			int subType = Integer.parseInt(rtype);
			if (subType == 1 || subType == 2) {
				subrule.setcList(null);
				subrule.setmList(null);
				subrule.setrList(null);
				if (subType == 1) {
					spe.setSubPercent(subrule.getParse(subrule.getSubPercent(), n));
					spe.setSubAmount(0.0);
				}
				if (subType == 2) {
					spe.setSubPercent(0);
				}	
			}else{
				spe.setSubAmount(0.0);
				spe.setSubPercent(0);
			}
		
			subrule.setSubPayRule(spe);

			String cateJson = spe.getCateJson();
			log.info(cateJson);
			ArrayList<Map<String, Object>> fromJson = gson.fromJson(cateJson,
					ArrayList.class);
			log.info(gson.toJson(fromJson));
			Set<CategoryTreeDTO> list = new HashSet<CategoryTreeDTO>();
			list = getCateList(fromJson, list);
			log.info("------------>分类个数" + list.size());
			if(list.size()<=0){
				code.put("code", "catenull");
				return gson.toJson(code);
			}			
			//分类校验，如果有禁用，排队，启用的规则中同时间，同补贴对象，同市场含有该分类就不允许添加
			pMap.clear();
			pMap.put("marketId", spe.getMarketId());
			pMap.put("memberType", spe.getMemberType());
			pMap.put("endTime", spe.getTimeEnd());
			pMap.put("startTime", spe.getTimeStart());
			pMap.put("rId", spe.getRuleId());
			CategoryTreeDTO cate = subRuleToolService.validateCate(list, pMap);
			//如果获取到结果，就表示已有该分类
			if(null!=cate){
				code.put("cate", cate);
				code.put("code", "same");
				return gson.toJson(code);
			}
			subrule.setcList(list);
			subRuleToolService.modifyRule(subrule);
			code.put("code", "success");
			return gson.toJson(code);
		} catch (Exception e) {
			e.printStackTrace();
			code.put("code", "fail");
			return gson.toJson(code);
		}
	}
	
	@RequestMapping(value="getLimitRule", produces = { "application/json;charset=utf-8" })
	@ResponseBody	
	public String getLimitRule(HttpServletRequest request, Model model){
		JSONMessage jsonM = new JSONMessage();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String markerId = getRequest().getParameter("marketId");
			if (StringUtils.isNotBlank(markerId)) {
				map.put("marketId", markerId);
			}else{
				jsonM.setFlag("0");
				return jsonM.toString();
			}
			map.put("status", "1");
			List<SubLimitRuleDTO> slist = subLimitRuleToolService.getSubLimitRuleDetail(map);
			SubLimitRuleDTO sublimit = slist.get(0);
			map.clear();
			
			map.put("sublimit", sublimit);
			map.put("marketId", markerId);
			jsonM.setData(map);
			return jsonM.toString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonM.setFlag("0");
			return jsonM.toString();
		}
	}	
	

}
