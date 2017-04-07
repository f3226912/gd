package com.gudeng.commerce.gd.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaToolService;
import com.gudeng.commerce.gd.admin.service.NstGoodAssignRuleToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.NstDeptGoodDTO;
import com.gudeng.commerce.gd.customer.dto.NstGoodAssignRuleDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 一手货源分配规则Controller
 * 
 * @author xiaojun
 */
@Controller
@RequestMapping("nstRule")
public class NstGoodAssignRuleController extends AdminBaseController {
	/** 记录日志 */
	@SuppressWarnings("unused")
	private static final GdLogger logger = GdLoggerFactory.getLogger(NstGoodAssignRuleController.class);
	@Autowired
	private NstGoodAssignRuleToolService nstGoodAssignRuleToolService;
	@Autowired
	private AreaToolService areaToolService;

	/**
	 * 跳转规则列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		request(request);
		return "nst_rule/nstGoodAssignRuleList";
	}

	/**
	 * 跳转规则分配区域选择
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("setting")
	public String setting(HttpServletRequest request) {
		request(request);
		return "nst_rule/area_rule_setting";
	}

	/**
	 * 跳转分配各个区域上限和时间
	 * 
	 * @param request
	 * @param nstDto
	 * @param model
	 * @return
	 */
	@RequestMapping("assign")
	public String assign(HttpServletRequest request, NstGoodAssignRuleDTO nstDto, Model model) {
		model.addAttribute("nstDto", nstDto);
		return "nst_rule/area_rule_assign";
	}

	/**
	 * 跳转修改各个区域上限和时间
	 * 
	 * @param request
	 * @param nstDto
	 * @param model
	 * @return
	 */
	@RequestMapping("updateAssign")
	public String updateAssign(HttpServletRequest request, NstGoodAssignRuleDTO nstDto, Model model) {
		model.addAttribute("nstDto", nstDto);
		return "nst_rule/area_rule_update";
	}

	/**
	 * 获取省份
	 * 
	 * @param request
	 */
	private void request(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AreaDTO> provinceList = null;
		try {
			provinceList = areaToolService.findProvince();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("provinceList", provinceList);
	}

	/**
	 * 获取城市
	 * 
	 * @param s_provinceId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getCity/{id}", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getCity(@PathVariable("id") String s_provinceId, Map<String, Object> map) {
		try {
			List<AreaDTO> cityList = areaToolService.findCity(s_provinceId);
			map.put("cityList", cityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 获取规则设置区域信息部名称
	 * 
	 * @param s_provinceId
	 * @param map
	 * @return
	 */
	@RequestMapping("/getArea/{id}")
	@ResponseBody
	public String getArea(@PathVariable("id") String cityId, Map<String, Object> map) {
		try {
			List<NstGoodAssignRuleDTO> areaList = null;
			if (StringUtils.isNotEmpty(cityId)) {
				areaList = nstGoodAssignRuleToolService.getDeptNameListByCityId(Long.parseLong(cityId));
			}
			map.put("areaList", areaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 获取查询信息部名称
	 * 
	 * @param s_provinceId
	 * @param map
	 * @return
	 */
	@RequestMapping("/getQueryArea/{id}")
	@ResponseBody
	public String getQueryArea(@PathVariable("id") String cityId, Map<String, Object> map) {
		try {
			List<NstGoodAssignRuleDTO> areaList = null;
			if (StringUtils.isNotEmpty(cityId)) {
				areaList = nstGoodAssignRuleToolService.getQueryDeptNameListByCityId(Long.parseLong(cityId));
			}
			map.put("areaList", areaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 获取一手货源分配规则列表
	 * 
	 * @param request
	 * @param nstDto
	 * @return
	 */
	@RequestMapping("getNstGoodAssignRuleList")
	@ResponseBody
	public String getNstGoodAssignRuleList(HttpServletRequest request, NstGoodAssignRuleDTO nstDto) {
		try {
			Map<String, Object> map = new HashMap<>();
			getMap(map, nstDto);
			// 设置总记录数
			map.put("total", nstGoodAssignRuleToolService.getAssignRuleDTOListByPageCount(map));
			// 设定分页,排序
			setCommParameters(request, map);
			List<NstGoodAssignRuleDTO> list = nstGoodAssignRuleToolService.getAssignRuleDTOListByPage(map);
			// 查询出规则开关的状态
			NstGoodAssignRuleDTO nstGoodAssignRuleDTO = nstGoodAssignRuleToolService.selectRuleSwith();
			if (nstGoodAssignRuleDTO != null) {
				map.put("rules", nstGoodAssignRuleDTO.getRule());
			} else {
				// 如果查询为空，默认没有设置规则
				map.put("rules", 0);
			}
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存所选区域
	 * 
	 * @param request
	 * @param nstDto
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request, NstGoodAssignRuleDTO nstDto) {
		SysRegisterUser user = getUser(getRequest());
		nstDto.setCreateUserId(user.getUserID());
		try {
			//查询货源分配规则开关，如果没有插入规则开关，有则修改
			NstGoodAssignRuleDTO nstGoodAssignRuleDTO = nstGoodAssignRuleToolService.selectRuleSwith();
			if (nstGoodAssignRuleDTO == null) {
				Integer s = nstGoodAssignRuleToolService.insertRuleSwith(nstDto);
				if (s <= 0) {
					logger.warn("插入规则开关失败");
					return "";
				}
			} else {
				Integer s1 = nstGoodAssignRuleToolService.updateRuleSwithByCode(nstDto.getRule(),
						nstGoodAssignRuleDTO.getCode(), user.getUserID());
				if (s1 <= 0) {
					logger.warn("修改规则开关失败");
					return "";
				}
			}
			if (StringUtils.isEmpty(nstDto.getMemberIdString())) {
				return "success";
			}
			// 获取memberId数组
			String[] memberIdArray = nstDto.getMemberIdString().split(",");
			// 获取companyName数组
			String[] companyNameArray = nstDto.getCompanyNameString().split(",");
			// 组装nstDto
			for (int i = 0; i < memberIdArray.length; i++) {
				nstDto.setMemberId(Long.parseLong(memberIdArray[i]));
				nstDto.setCompanyName(companyNameArray[i]);
				Integer status = nstGoodAssignRuleToolService.insert(nstDto);
				if (status <= 0) {
					logger.warn("插入规则失败");
					return "";
				}
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 设置区域分配规则 (新增和修改)
	 * 
	 * @param request
	 * @param nstDto
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public String update(HttpServletRequest request, NstGoodAssignRuleDTO nstDto) {
		Map<String, Object> map = new HashMap<>();
		getMap(map, nstDto);
		try {
			SysRegisterUser user = getUser(getRequest());
			map.put("updateUserId", user.getUserID());
			map.put("isEffective", 0);
			Integer status = nstGoodAssignRuleToolService.update(map);
			if (status > 0) {
				return "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 每个规则详情
	 * 
	 * @param request
	 * @param nstDto
	 * @return
	 */
	@RequestMapping("detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		try {
			NstGoodAssignRuleDTO nstDTO = nstGoodAssignRuleToolService.getById(id);
			if (StringUtils.isNotEmpty(nstDTO.getCompanyName())) {
				nstDTO.setCompanyName(nstDTO.getCompanyName().substring(0, nstDTO.getCompanyName().indexOf("(")));
			}
			model.addAttribute("nstDTO", nstDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "nst_rule/detail";
	}

	/**
	 * 更新状态(批量和单条)
	 * 
	 * @param idString
	 * @param isEffective
	 * @return
	 */
	@RequestMapping("updatestatus")
	@ResponseBody
	public String updatestatus(@RequestParam("idStr") String idString,
			@RequestParam("isEffective") Integer isEffective) {
		SysRegisterUser user = getUser(getRequest());
		String updateUserId = user.getUserID();
		try {
			Integer status = nstGoodAssignRuleToolService.updateStaus(idString, isEffective, updateUserId);
			if (status > 0) {
				return "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping("deptGoodList/{deptMemberId}")
	public String deptGoodList(@PathVariable("deptMemberId") Long deptMemberId, HttpServletRequest request){
		putModel("deptMemberId", deptMemberId);
		putModel("assignStartTime", request.getParameter("assignStartTime"));
		putModel("assignEndTime", request.getParameter("assignEndTime"));
		return "nst_rule/dept_good_list";
	}
	/**
	 * 分配给信息部货源列表
	 * @param deptMemberId
	 * @return
	 */
	@RequestMapping("queryDeptGood/{deptMemberId}")
	@ResponseBody
	public String queryDeptGood(@PathVariable("deptMemberId") Long deptMemberId, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("deptMemberId", deptMemberId);
			
			String assignStartTime = request.getParameter("assignStartTime");
			String assignEndTime = request.getParameter("assignEndTime");
			if(StringUtils.isNotBlank(assignStartTime)){
				paramMap.put("assignStartTime", CommonUtil.getStartOfDay(DateUtil.formateDate(assignStartTime)));
			}
			if(StringUtils.isNotBlank(assignEndTime)){
				paramMap.put("assignEndTime", CommonUtil.getEndOfDay(DateUtil.formateDate(assignEndTime)));
			}
			
			resultMap.put("total", nstGoodAssignRuleToolService.getDeptGoodTotalCount(paramMap));
			
			setCommParameters(request, paramMap);
			List<NstDeptGoodDTO> list = nstGoodAssignRuleToolService.queryDeptGoodPage(paramMap);
			resultMap.put("rows", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 组装map
	 * 
	 * @param map
	 * @param nstDto
	 */
	private void getMap(Map<String, Object> map, NstGoodAssignRuleDTO nstDto) {
		map.put("account", nstDto.getAccount());
		map.put("provinceId", nstDto.getProvinceId());
		map.put("cityId", nstDto.getCityId());
		map.put("memberId", nstDto.getMemberId());
		map.put("assignStartBeginTime", nstDto.getAssignStartBeginTime());
		map.put("assignStartEndTime", nstDto.getAssignStartEndTime());
		map.put("assignEndBeginTime", nstDto.getAssignEndBeginTime());
		map.put("assignEndEndTime", nstDto.getAssignEndEndTime());
		map.put("id", nstDto.getId());
		map.put("dayAssignMax", nstDto.getDayAssignMax());
		map.put("monthAssignMax", nstDto.getMonthAssignMax());
		map.put("assignStartTime", nstDto.getAssignStartTime());
		map.put("assignEndTime", nstDto.getAssignEndTime());
		map.put("isEffective", nstDto.getIsEffective());
		map.put("assignSumQueryStartTime", nstDto.getAssignSumQueryStartTime());
		map.put("assignSumQueryEndTime", nstDto.getAssignSumQueryEndTime());
	}
}
