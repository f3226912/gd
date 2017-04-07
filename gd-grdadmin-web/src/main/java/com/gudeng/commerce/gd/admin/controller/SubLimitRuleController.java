/**
 * @Title: SubLimitRuleController.java
 * @Package com.gudeng.commerce.gd.admin.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年11月30日 下午3:20:32
 * @version V1.0
 */
package com.gudeng.commerce.gd.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.SubLimitRuleToolService;
import com.gudeng.commerce.gd.admin.util.JSONMessage;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleWhitelistDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;
import com.gudeng.commerce.gd.order.enm.ERuleTimeRange;
import com.gudeng.commerce.gd.order.enm.ESubLimitType;
import com.gudeng.commerce.gd.order.enm.ETamountLimit;
import com.gudeng.commerce.gd.order.enm.ETwoUtimesLimit;
import com.gudeng.commerce.gd.order.enm.EUamountLimit;
import com.gudeng.commerce.gd.order.enm.EUtimesLimit;
import com.gudeng.commerce.gd.order.enm.EVehLimit;

/**
 * @ClassName: SubLimitRuleController
 * @Description: TODO(补贴限制规则)
 * @author mpan
 * @date 2015年11月30日 下午3:20:32
 */
@Controller
@RequestMapping("sublimitrule")
public class SubLimitRuleController extends AdminBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubLimitRuleController.class);
	
	@Autowired
	public SubLimitRuleToolService subLimitRuleToolService;
	
	@Autowired
	public MarketManageService marketManageService;
	
	@Autowired
	public MemberBaseinfoToolService MemberBaseinfoToolServiceImpl;

	@RequestMapping("init")
	public String index(ModelMap modelMap) {
		try{
		Map<String, Object> map = new HashMap<String, Object>();
		String markerId = getRequest().getParameter("marketId");
		String ruleId = getRequest().getParameter("ruleId");
		String type = getRequest().getParameter("type");
		if (StringUtils.isNotBlank(markerId)) {
			map.put("marketId", markerId);
		}else{
			map.put("marketId", "1");
		}
		if (StringUtils.isNotBlank(ruleId)) {
			map.put("ruleId", ruleId);
		}
		List<SubLimitRuleDTO> subLimitRules = subLimitRuleToolService.getSubLimitRuleDetail(map);
		SubLimitRuleDTO subLimitRule = generatorSubLimitRule(markerId, CollectionUtils.isNotEmpty(subLimitRules) ? subLimitRules.get(0) : null);
		modelMap.put("subLimitRule", subLimitRule);
		List<MarketDTO> markets = marketManageService.getAllByStatusAndType("0", "2");
		//联系人
		String contacts = subLimitRule.getContact();
		if(StringUtils.isNotBlank(contacts)){
			String[] cons = contacts.split(",");
			List<String> conList = Arrays.asList(cons);
			subLimitRule.setContacts(conList);
		}
		//获取白名单列表
//		if(subLimitRule.getRuleId()!=null && subLimitRule.getRuleId()>0){
//			map.clear();
//			map.put("limitruleId", subLimitRule.getRuleId());
//			subLimitRule.setWlist(subLimitRuleToolService.getWhiteList(map));
//		}
		modelMap.put("markets", markets);
		modelMap.put("type", type);
		if(StringUtils.isNotBlank(type)){
			return "sub/editSubLimitRule";
		}else{
			return "sub/subLimitRule";
		}
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(@ModelAttribute SubLimitRuleDTO subLimitRuleUp) throws Exception {
		JSONMessage jsonM = new JSONMessage();
		try {
			if (StringUtils.isNotBlank(subLimitRuleUp.getWhiteLimit())&&subLimitRuleUp.getWhiteLimit().equals(ETamountLimit.OPEN.getCode())) {
				String notAccount = whiteList(subLimitRuleUp);
				if(StringUtils.isNotBlank(notAccount)){
				jsonM.setData(notAccount);
				jsonM.setFlag("3");
				sendJsonMessage(jsonM);
				return;
				}
			}
			
			//判断活动时间
			//System.out.println("=====================>"+subLimitRuleUp.getTimeStartStr());
			
			
			if (subLimitRuleUp.getRuleId() == null) {
				subLimitRuleUp.setCreateUserId(getUser(getRequest()).getUserID());
			} else {
				subLimitRuleUp.setUpdateUserId(getUser(getRequest()).getUserID());
			}
			if (StringUtils.isBlank(subLimitRuleUp.getVehLimit())) {
				subLimitRuleUp.setVehLimit(EVehLimit.CLOSE.getCode());
			}
			if (StringUtils.isBlank(subLimitRuleUp.getUamountLimit())) {
				subLimitRuleUp.setUamountLimit(EUamountLimit.CLOSE.getCode());
			}
			if (StringUtils.isBlank(subLimitRuleUp.getUtimesLimit())) {
				subLimitRuleUp.setUtimesLimit(EUtimesLimit.CLOSE.getCode());
			}
			if (StringUtils.isBlank(subLimitRuleUp.getTwoUtimesLimit())) {
				subLimitRuleUp.setTwoUtimesLimit(ETwoUtimesLimit.CLOSE.getCode());
			}
			if (StringUtils.isBlank(subLimitRuleUp.getTamountLimit())) {
				subLimitRuleUp.setTamountLimit(ETamountLimit.CLOSE.getCode());
			}
			if (StringUtils.isBlank(subLimitRuleUp.getWhiteLimit())) {
				subLimitRuleUp.setWhiteLimit(ETamountLimit.CLOSE.getCode());
			}
			subLimitRuleUp.setStatus("1");//开启状态
			
			subLimitRuleToolService.saveOrUpdateSubLimitRule(subLimitRuleUp);
		} catch (Exception e) {
			LOGGER.error("保存或更新补贴限制规则失败", e);
			jsonM.setFlag(JSONMessage.FAIL);
			jsonM.setMsg("操作失败");
		}
		sendJsonMessage(jsonM);
	}
	
	/**
	 * 删除白名单
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delWhiteList",produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public String delWhiteList(ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//limitruleId=:limitruleId and memberId=:memberId
		String limitruleId = getRequest().getParameter("limitruleId");
		String memberId = getRequest().getParameter("memberId");
		
		JSONMessage jsonM = new JSONMessage();
		if (StringUtils.isBlank(limitruleId)||StringUtils.isBlank(memberId)) {
			jsonM.setFlag("0");
			jsonM.setMsg("fail");
			return jsonM.toString();
		}
		map.put("limitruleId", limitruleId);
		map.put("memberId", memberId);
		subLimitRuleToolService.delWhiteList(map);
		jsonM.setMsg("success");
		return jsonM.toString();
	}	
	
	@RequestMapping(value="updateStatus",produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public String updateStatus(ModelMap modelMap){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONMessage jsonM = new JSONMessage();
		try {
			String ruleId = getRequest().getParameter("ruleId");
			String marketId = getRequest().getParameter("marketId");
			String userId = getUser(getRequest()).getUserID();
			if (StringUtils.isBlank(ruleId)) {
				jsonM.setFlag("0");
				jsonM.setMsg("fail");
				return jsonM.toString();
			}
			map.put("ruleId", ruleId);
			map.put("userId",userId);			
			map.put("marketId",marketId);			
			subLimitRuleToolService.updateSubLimitRuleStatus(map);
			jsonM.setMsg("success");
			return jsonM.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonM.setFlag("0");
			jsonM.setMsg("fail");
			return jsonM.toString();
		}

	}		
	
	
	/**
	 * 验证用户
	 * @param whitelist
	 * @return
	 */
	public String whiteList(SubLimitRuleDTO subLimitRuleUp){
		try{
			String whitelist = subLimitRuleUp.getWhiteList();
			if(StringUtils.isBlank(whitelist)){
				return null;
			}
			String reStr = "";
			String[] userNames = whitelist.split(",");
			List<SubLimitRuleWhitelistDTO> list = new ArrayList<SubLimitRuleWhitelistDTO>();
			for(int i = 0;i<userNames.length;i++){
				String temp=userNames[i];
				MemberBaseinfoDTO user =MemberBaseinfoToolServiceImpl.getByAccountNoCer(StringUtils.trim(temp));
				if(user==null){
//					user.getMarketId()
					reStr += temp;
					if(i!=(userNames.length-1)){
						reStr +=",";
					}
					continue;
				}
				if(null==user.getMarketId()){//不是卖家
					reStr += temp;
					if(i!=(userNames.length-1)){
						reStr +=",";
					}
					continue;
				}
				if(user.getMarketId()!=subLimitRuleUp.getMarketId()){//不是同一市场
					reStr += temp;
					if(i!=(userNames.length-1)){
						reStr +=",";
					}
					continue;
				}
				SubLimitRuleWhitelistDTO swl = new SubLimitRuleWhitelistDTO();
				swl.setAccount(user.getAccount());
				swl.setMemberId(user.getMemberId());
				swl.setCreateTime(new Date());
				list.add(swl);
			}
			subLimitRuleUp.setWlist(list);
			return reStr;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	private SubLimitRuleDTO generatorSubLimitRule(String marketId, SubLimitRuleDTO subLimitRule) {
		if (subLimitRule == null) {
			subLimitRule = new SubLimitRuleDTO();
		}
		if (StringUtils.isNotBlank(marketId)) {
			subLimitRule.setMarketId(Long.valueOf(marketId));
		}
		List<SubRangeLimitRuleDTO> subRangeLimitRules = generatorSubRangeLimitRule(subLimitRule.getSubRangeLimitRules());
		subLimitRule.setSubRangeLimitRules(subRangeLimitRules);
		return subLimitRule;
	}
	
	private Map<String, SubRangeLimitRuleDTO> getSubRangeLimitRuleMap(List<SubRangeLimitRuleDTO> subRangeLimitRules) {
		Map<String, SubRangeLimitRuleDTO> ruleMap = new HashMap<String, SubRangeLimitRuleDTO>();
		for (SubRangeLimitRuleDTO subRule : subRangeLimitRules) {
			ruleMap.put(subRule.getLimitType() + subRule.getTimeRange(), subRule);
		}
		return ruleMap;
	}
	
	private List<SubRangeLimitRuleDTO> generatorSubRangeLimitRule(List<SubRangeLimitRuleDTO> subRangeLimitRules) {
		boolean isEmpty = false;
		Map<String, SubRangeLimitRuleDTO> ruleMap = null;
		if (CollectionUtils.isEmpty(subRangeLimitRules)) {
			isEmpty = true;
		} else {
			ruleMap = getSubRangeLimitRuleMap(subRangeLimitRules);
		}
		List<SubRangeLimitRuleDTO> rtSRLRs = new ArrayList<SubRangeLimitRuleDTO>();
		SubRangeLimitRuleDTO subRangeLimitRule = null;
		// 车辆限制
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.VEH_LIMIT.getCode() + ERuleTimeRange.DAY.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.VEH_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.DAY.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.VEH_LIMIT.getCode() + ERuleTimeRange.DAY.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.VEH_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.VEH_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.WEEK.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.VEH_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.VEH_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.VEH_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.MONTH.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.VEH_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode()));
		}
		
		// 补贴用户额度限制
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.UAMOUNT_LIMIT.getCode() + ERuleTimeRange.DAY.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.UAMOUNT_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.DAY.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.UAMOUNT_LIMIT.getCode() + ERuleTimeRange.DAY.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.UAMOUNT_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.UAMOUNT_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.WEEK.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.UAMOUNT_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.UAMOUNT_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.UAMOUNT_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.MONTH.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.UAMOUNT_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.UAMOUNT_LIMIT.getCode() + ERuleTimeRange.ALL.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.UAMOUNT_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.ALL.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.UAMOUNT_LIMIT.getCode() + ERuleTimeRange.ALL.getCode()));
		}
		
		// 补贴用户次数限制
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.UTIMES_LIMIT.getCode() + ERuleTimeRange.DAY.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.UTIMES_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.DAY.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.UTIMES_LIMIT.getCode() + ERuleTimeRange.DAY.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.UTIMES_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.UTIMES_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.WEEK.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.UTIMES_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.UTIMES_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.UTIMES_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.MONTH.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.UTIMES_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode()));
		}
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.UTIMES_LIMIT.getCode() + ERuleTimeRange.ALL.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.UTIMES_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.ALL.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.UTIMES_LIMIT.getCode() + ERuleTimeRange.ALL.getCode()));
		}		
		
		// 用户间交易频率限制
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.TWO_UTIMES_LIMIT.getCode() + ERuleTimeRange.DAY.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.TWO_UTIMES_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.DAY.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.TWO_UTIMES_LIMIT.getCode() + ERuleTimeRange.DAY.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.TWO_UTIMES_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.TWO_UTIMES_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.WEEK.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.TWO_UTIMES_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.TWO_UTIMES_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.TWO_UTIMES_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.MONTH.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.TWO_UTIMES_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode()));
		}
		
		// 补贴总额数限制
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.TAMOUNT_LIMIT.getCode() + ERuleTimeRange.DAY.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.TAMOUNT_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.DAY.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.TAMOUNT_LIMIT.getCode() + ERuleTimeRange.DAY.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.TAMOUNT_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.TAMOUNT_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.WEEK.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.TAMOUNT_LIMIT.getCode() + ERuleTimeRange.WEEK.getCode()));
		}
		
		if (isEmpty || !ruleMap.containsKey(ESubLimitType.TAMOUNT_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode())) {
			subRangeLimitRule = new SubRangeLimitRuleDTO();
			subRangeLimitRule.setLimitType(ESubLimitType.TAMOUNT_LIMIT.getCode());
			subRangeLimitRule.setTimeRange(ERuleTimeRange.MONTH.getCode());
			rtSRLRs.add(subRangeLimitRule);
		} else {
			rtSRLRs.add(ruleMap.get(ESubLimitType.TAMOUNT_LIMIT.getCode() + ERuleTimeRange.MONTH.getCode()));
		}
		return rtSRLRs;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("limitRuleQuery")
	@ResponseBody
	public String limitRuleQuery(HttpServletRequest request) {
		try {
			
			String market = request.getParameter("market");
			String status = request.getParameter("status");
			String subamount = request.getParameter("subAmount");
			String timeEnd = request.getParameter("timeEnd");
			String timeStart = request.getParameter("timeStart");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("market", market);
			map.put("status", status);
			if(StringUtils.isNotBlank(timeStart)&&StringUtils.isNotBlank(timeEnd)){
				map.put("timeEnd", timeEnd+" 00:00:00");
				map.put("timeStart", timeStart+" 00:00:00");
			}
			map.put("subAmount", subamount);

			// 获取条件记录总数
			map.put("total", subLimitRuleToolService.getSearchLimitRuleListCount(map));
			// 设置分页参数
			setCommParameters(request, map);

			// 数据集合
			List<SubLimitRuleDTO> list = subLimitRuleToolService.searchLimitRuleList(map);
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
	 * 规则列表
	 * 
	 * @param model
	 * @param ruleId
	 * @return
	 */
	@RequestMapping("/limitRuleList")
	public String ruleDetail(Model model) {

		return "sub/LimitRule_list";
	}
	

}
