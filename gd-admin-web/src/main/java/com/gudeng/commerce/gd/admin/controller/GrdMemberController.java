package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.gudeng.commerce.gd.admin.dto.KeyValuePair;
import com.gudeng.commerce.gd.admin.exception.BusinessException;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftteamToolService;
import com.gudeng.commerce.gd.admin.service.GrdGiftDetailToolService;
import com.gudeng.commerce.gd.admin.service.GrdGiftRecordToolService;
import com.gudeng.commerce.gd.admin.service.GrdMemberToolService;
import com.gudeng.commerce.gd.admin.service.GrdUserTeamToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.JavaMd5;
import com.gudeng.commerce.gd.admin.util.JxlsExcelUtil;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftteamDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftRecordDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdUserTeamDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdMemberEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/** “地推管理中心--地推人员管理 ”控制类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("grdMember")
public class GrdMemberController extends AdminBaseController {
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdMemberController.class);
	
	/**默认的重置密码
	 * 
	 */
	private static final String DEFAULT_RESET_PWD = "abc123";
	
	/**
	 * 地推人员服务类
	 */
	@Autowired
	public GrdMemberToolService grdMemberToolService;
	
	@Autowired
	public GrdUserTeamToolService grdUserTeamToolService;
	
	/**
	 * 礼品发放记录服务类
	 */
	@Autowired
	public GrdGiftRecordToolService grdGiftRecordToolService;
	/**
	 * 礼品发放明细服务类
	 */
	@Autowired
	public GrdGiftDetailToolService grdGiftDetailToolService;
	/**
	 * 市场服务类
	 */
	@Autowired
	private MarketManageService marketManageService;
	
	/**
	 * 地推团队
	 */
	@Autowired
	private GrdGdGiftteamToolService grdGdGiftteamToolService;
	
	/**
	 * 配置文件
	 */
	@Autowired
	public GdProperties gdProperties;
	
	/** 显示地推人员列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("showList")
	public String grdMemberList(HttpServletRequest request) throws Exception{
		//查询市场
		List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
		request.setAttribute("marketDTOs", marketDTOs);
		return "grdMember/grd_member_list";
	}
	
	
	/** 跳转到新增页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("transferToAddPage")
	public String transferToAddPage(HttpServletRequest request) throws Exception{
		//查询出全部市场
		List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
		//因为新增和修改是同一个页面，为防止空指针，需要创建一个空值的GrdMemberDTO对象
		GrdMemberDTO grdMemberDTO = new GrdMemberDTO();
		
		request.setAttribute("addOrEdit", 1);//1-新增, 2-编辑
		request.setAttribute("marketDTOs", marketDTOs);
		request.setAttribute("grdMemberDTO", grdMemberDTO);
		return "grdMember/grd_member_editor_save";
	}
	
	/** 跳转到修改页面
	 * @param id 地推人员id
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("transferToEditorPage")
	public String transferToEditorPage(String id, HttpServletRequest request) throws Exception{
		//查询出全部市场
		List<MarketDTO> marketDTOs = marketManageService.getAllByType("2");
		GrdMemberDTO grdMemberDTO = grdMemberToolService.getById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		// 所属团队
		map.put("grdUserId", id);
		List<GrdUserTeamDTO> userteams = grdUserTeamToolService.getReUserTeamList(map);
		
		//查询市场下所有的团队
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("marketId", grdMemberDTO.getMarketId());
		List<GrdMemberDTO> giftteamList = grdMemberToolService.getChildTeamInfo(params);
		
		request.setAttribute("giftteamList", giftteamList);
		request.setAttribute("userteams", userteams);
		request.setAttribute("marketDTOs", marketDTOs);
		request.setAttribute("grdMemberDTO", grdMemberDTO);
		request.setAttribute("addOrEdit", 2);//1-新增, 2-编辑
		return "grdMember/grd_member_editor_save";
	}
	
	@ResponseBody
	@RequestMapping("getTeamsOfMarket")
	public String getTeamsOfMarket(String marketId) throws Exception{
		 // 所属团队
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marketId", marketId);
		List<GrdGdGiftteamDTO> giftteamList = grdGdGiftteamToolService.getList(map);
//		List<GrdMemberDTO> giftteamList = grdMemberToolService.getChildTeamInfo(map);
		KeyValuePair pair = null ;
		List<KeyValuePair> pairs = new ArrayList<KeyValuePair>();
		pair = new KeyValuePair();
		pair.setKeyString("");
		pair.setValueString("————全部————");
		pairs.add(pair);
		for(GrdGdGiftteamDTO item : giftteamList){
			pair = new KeyValuePair();
			pair.setKeyString(String.valueOf(item.getId()));
			pair.setValueString(item.getName());
			pairs.add(pair);
		}
		return JSONObject.toJSONString(pairs);
	}
	
	/** 跳转到邀请注册客户信息列表页面
	 * @param userId 地推人员id
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("transferToInviteUserInfoListPage")
	public String transferToInviteRegUserInfoListPage(String userId, HttpServletRequest request) throws Exception{
		//查询出当前地推人员信息
		GrdMemberDTO grdMemberDTO = grdMemberToolService.getById(userId);
		request.setAttribute("grdMemberDTO", grdMemberDTO);
		return "grdMember/invite_user_info_list";
	}
	
	/** 跳转到促成订单信息列表页面
	 * @param userId 地推人员id
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("transferToOrderInfoListPage")
	public String transferToOrderInfoListPage(String userId, HttpServletRequest request) throws Exception{
		//查询出当前地推人员信息
		GrdMemberDTO grdMemberDTO = grdMemberToolService.getById(userId);
		request.setAttribute("grdMemberDTO", grdMemberDTO);
		return "grdMember/order_info_list";
	}
	
	/** 根据查询条件获取礼物发放数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryBySearch", method=RequestMethod.POST)
	@ResponseBody
    public String  queryBySearch(HttpServletRequest request){
		Map<String, Object> resultMap = null;
		try {
			//生成查询参数
			Map<String, Object> searchParam = generateSearchParam(request);
			//设定分页,排序
			setCommParameters(request, searchParam);
			
			int total = grdMemberToolService.countBySearch(searchParam);
			List<GrdMemberDTO> grdMemberDTOs = null;
			if (total < 1) {
				grdMemberDTOs = Collections.emptyList();
			} else {
				grdMemberDTOs = grdMemberToolService.queryBySearch(searchParam);
			}
			searchParam.clear();
			int lastIndex = -1 ;
			StringBuffer bf = new StringBuffer();
			for(GrdMemberDTO item : grdMemberDTOs){
				bf.setLength(0);
				searchParam.put("grdUserId", item.getId());
				List<GrdUserTeamDTO> userteams = grdUserTeamToolService.getReUserTeamList(searchParam);
				for(GrdUserTeamDTO element : userteams){
					bf.append(element.getTeamName() + ",");
				}
				lastIndex = bf.lastIndexOf(",");
				if ( lastIndex != -1){
					item.setGiftteamName(bf.substring(0, lastIndex));
				} else {
					item.setGiftteamName("");
				}
			}
			resultMap = generalResponseResultMap(true,"请求处理成功");
			resultMap.put("total", total);
			resultMap.put("rows", grdMemberDTOs);
		} catch (Exception e) {
			resultMap = generalResponseResultMap(false,"请求处理失败");
			e.printStackTrace();
		}
		
		//保留空值的字段过滤器
		ValueFilter filter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				return value != null ? value : "";
			}
		};
		
		return JSONObject.toJSONString(resultMap, filter, SerializerFeature.WriteDateUseDateFormat);
	}
	
	/** 生成相应的结果集合。
	 * @param isSuccess
	 * @param msg
	 * @return
	 */
	private Map<String, Object> generalResponseResultMap(boolean isSuccess, String msg) {
		Map<String, Object> resultMap = new HashMap<String, Object>(4);
		resultMap.put("isSuccess", isSuccess);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	/** 生成查询参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> generateSearchParam(HttpServletRequest request) {
		Map<String, Object> requestParamMap = request.getParameterMap();
		
		Map<String, Object> searchParamMap = new HashMap<String, Object>(requestParamMap.size());
		String key;
		Object value;
		for (Entry<String, Object> entry : requestParamMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			//如果是数组参数，则获取第一个参数
			if (value != null && value.getClass().isArray()) {
				value = ((String[])value)[0];
			}
			searchParamMap.put(key, value);
		}
		
		return searchParamMap;
	}
	
	/**
	 * 检测导出excel的查询参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportExcelParams", produces="application/json; charset=utf-8")
	public String checkExportExcelParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			//生成查询参数
			Map<String, Object> searchParam = generateSearchParam(request);
		/*	String startDate =(String) request.getParameter("startDate");
			String endDate = (String)request.getParameter("endDate");
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}*/
			int total = grdMemberToolService.countBySearch(searchParam);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}else if (total < 1) {
				result.put("status", 0);
				result.put("message", "导出的结果集无任何数据，请重新修改查询条件...");
			}else{
			result.put("status", 1);
			result.put("message", "参数检测通过");}
		
			
		}catch(Exception e){
			logger.info("地推checkExportExcelParams", new Object[]{e});
		}
		return JSONObject.toJSONString(result);
	}
	
	/**
	 * 导出数据到excel
	 * @param sqb
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="exportExcelData")
	public String exportExcelData(HttpServletRequest request, HttpServletResponse response){
		OutputStream out = null;
		try {
			
			//生成查询参数
			Map<String, Object> searchParam = generateSearchParam(request);
			List<GrdMemberDTO>	grdMemberDTOs = grdMemberToolService.queryBySearch(searchParam);
			StringBuffer bf = new StringBuffer();
			int lastIndex = -1 ;
			for(GrdMemberDTO item :grdMemberDTOs){
				bf.setLength(0);
				searchParam.put("grdUserId", item.getId());
				List<GrdUserTeamDTO> userteams = grdUserTeamToolService.getReUserTeamList(searchParam);
				for(GrdUserTeamDTO element : userteams){
					bf.append(element.getTeamName() + ",");
				}
				lastIndex = bf.lastIndexOf(",");
				if ( lastIndex != -1){
					item.setGiftteamName(bf.substring(0, lastIndex));
				} else {
					item.setGiftteamName("");
				}
			}

			//获取模板文件路径
			String templatePath = gdProperties.getProperties().getProperty("GRDMEMBER_TEMPLETE");
			String srcFilePath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/" + templatePath);

			// 设置文件名和头信息
			String destFileName = RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xls";			//目标文件名
			response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);	// 设置响应
			response.setContentType("application/vnd.ms-excel");

			// 设置列表数据
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("list", grdMemberDTOs);

			//获取输出流
			out = response.getOutputStream();
			JxlsExcelUtil.exportReportFromAbsolutePath(srcFilePath, beans, out);
		} catch (Exception e) {
			e.printStackTrace();
			logger.trace("数据导出失败", e);
		} finally {
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**根据id删除记录
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteByIds")
	public String deleteByIds(String ids) {
		Map<String, Object> resultMap;
		try{
			List<String> idList = Arrays.asList(ids.split(","));
			
			//先判断当前用户是否有礼品发放记录
			int count = grdGiftRecordToolService.countByGrantOrCreateUserIds(idList);
			if (count > 0) {
				throw new BusinessException("地推人员存在礼品发放记录或客户，不允许删除！");
			}
			grdMemberToolService.deleteByIds(idList);
			resultMap = generalResponseResultMap(true, "请求处理成功");
		}catch(BusinessException e){
			resultMap = generalResponseResultMap(false, e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			resultMap = generalResponseResultMap(false, "请求处理失败");
			e.printStackTrace();
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	/** 根据id重置用户密码
	 * @param id 重置密码的用户id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("resetPwdById")
	public String resetPwdById(String ids, HttpServletRequest request) {
		Map<String, Object> resultMap;
		try{
			String defaultPwd = JavaMd5.getMD5(DEFAULT_RESET_PWD+"gudeng2015@*&^-").toUpperCase();
			//封装参数
			Map<String, Object> param = new HashMap<String, Object>();
			List<String> idList = Arrays.asList(ids.split(","));
			param.put("ids", idList);
			param.put("updateUserId", super.getUser(request).getUserID());
			param.put("defaultPwd", defaultPwd);
			
			grdMemberToolService.resetPwdByIds(param);
			
			resultMap = generalResponseResultMap(true,"请求处理成功");
		}catch(Exception e){
			resultMap = generalResponseResultMap(false,"请求处理失败");
			e.printStackTrace();
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	
	
	/** 根据id修改用户状态
	 * @param ids 修改用户状态ids 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("resetStatusById")
	public String resetStatusById(String ids, HttpServletRequest request) {
		Map<String, Object> resultMap;
		try{
			String defaultPwd = JavaMd5.getMD5(DEFAULT_RESET_PWD+"gudeng2015@*&^-").toUpperCase();
			//封装参数
			Map<String, Object> param = new HashMap<String, Object>();
			String userName=request.getParameter("userName");
			List<String> idList = Arrays.asList(ids.split(","));
			param.put("ids", idList);
			param.put("updateUserId", super.getUser(request).getUserID());
			String status=(String)request.getParameter("status");
			param.put("status", status);
			if("0".equals(status)){
				param.put("context", "\n"+super.getUser(request).getUserName()+"在"+ DateUtil.toString(new Date(),
						DateUtil.DATE_FORMAT_DATETIME)+"禁用用户"+userName+";");
			}
			if("1".equals(status)){
				param.put("context", "\n"+super.getUser(request).getUserName()+"在"+DateUtil.toString(new Date(),
						DateUtil.DATE_FORMAT_DATETIME)+"启用用户"+userName+";");
			}
			grdMemberToolService.resetStatusByIds(param);
			resultMap = generalResponseResultMap(true,"请求处理成功");
		}catch(Exception e){
			resultMap = generalResponseResultMap(false,"请求处理失败");
			e.printStackTrace();
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	
	/** 保存或动态更新数据。
	 * 如果参数grdMemberEntity存在id值，则进行动态更新，发反之进行保存。
	 * @param grdMemberEntity 参数对象
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveOrUpdate")
	public String saveOrUpdate(GrdMemberEntity grdMemberEntity, String[] teamGroup, HttpServletRequest request) {
		Map<String, Object> resultMap;
		try{
			Set<String> teamSet = new HashSet<String>();
			teamSet.addAll(Arrays.asList(teamGroup));
			//移除可能存在的空元素
			teamSet.remove("");
			
			Integer grdUserId = grdMemberEntity.getId();
			if(grdUserId != null){
				Map<String, Object> map = new HashMap<String, Object>();
				// 查询地推用户所属团队以确定团队是否变更
				map.put("grdUserId", grdUserId);
				List<GrdUserTeamDTO> userteam = grdUserTeamToolService.getReUserTeamList(map);
				if (isUserteamChanged(teamSet, userteam)){
					grdMemberEntity.setNeedLogin(1);
				}
//				else{
//					grdMemberEntity.setNeedLogin(0);
//				}
				update(grdMemberEntity);
				grdUserTeamToolService.deleteByGrdUserId(String.valueOf(grdUserId));
			} else {
				grdUserId = save(grdMemberEntity).intValue();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			for (String teamId : teamSet){
				map.put("grdUserId", grdUserId);
				map.put("teamId", teamId);
				grdUserTeamToolService.insert(map);
			}
			resultMap = generalResponseResultMap(true,"请求处理成功");
		}catch(DuplicateKeyException e){
			//手机号码加了唯一索引，故抛此异常是手机号码重复导致
			resultMap = generalResponseResultMap(false,"处理失败，当前手机号码已经被注册!");
			e.printStackTrace();
		}catch(Exception e){
				resultMap = generalResponseResultMap(false,"请求处理失败");
				e.printStackTrace();
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	private boolean isUserteamChanged(Set<String> newTeam, List<GrdUserTeamDTO> userteam){
		Set<String> oldTeam = new HashSet<String>();
		for(GrdUserTeamDTO item : userteam){
			oldTeam.add(String.valueOf(item.getTeamId()));
		}
		if (oldTeam.containsAll(newTeam) && newTeam.containsAll(oldTeam)){
			return false;
		}
		return true;
		
	}
	
	/**更新实体
	 * @param grdMemberEntity
	 * @throws Exception
	 */
	private void update(GrdMemberEntity grdMemberEntity) throws Exception{
		//添加修改人
		grdMemberEntity.setUpdateUserId(super.getUser(request).getUserID());
		//添加市场名称
		MarketDTO marketDTO = marketManageService.getById(grdMemberEntity.getMarketId().toString());
		grdMemberEntity.setMarket(marketDTO.getMarketName());
		
		grdMemberToolService.dynamicUpdate(grdMemberEntity);
	}
	
	
	/** 保存实体
	 * @param grdMemberEntity
	 * @throws Exception
	 */
	private Long save(GrdMemberEntity grdMemberEntity) throws Exception {
		processEntityForSave(grdMemberEntity);
		return grdMemberToolService.save(grdMemberEntity);
	}

	/**对于保存的的实体进行加工处理。
	 * @param grdMemberEntity
	 * @throws Exception 
	 */
	private void processEntityForSave(GrdMemberEntity grdMemberEntity) throws Exception {
		SysRegisterUser user = super.getUser(request);
		grdMemberEntity.setCreateUserId(user.getUserID());
		grdMemberEntity.setUpdateUserId(user.getUserID());
		
		MarketDTO marketDTO = marketManageService.getById(grdMemberEntity.getMarketId().toString());
		grdMemberEntity.setMarket(marketDTO.getMarketName());
		
		String defaultPwd = JavaMd5.getMD5(DEFAULT_RESET_PWD+"gudeng2015@*&^-").toUpperCase();
		grdMemberEntity.setPassword(defaultPwd);
		
		Date curDate = new Date();
		grdMemberEntity.setCreateTime(curDate);
		grdMemberEntity.setUpdateTime(curDate);
		
		//首次创建，设置为未登陆
		grdMemberEntity.setLoginStatus("0");
		//默认设置为启用
		grdMemberEntity.setStatus("1");
		
	}
	
	/** 根据地推人员id统计邀请注册的用户信息。
	 * @param userId 地推人员id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryInviteRegUserInfo", method=RequestMethod.POST)
	@ResponseBody
    public String queryInviteRegUserInfo(String userId, HttpServletRequest request){
		Map<String, Object> resultMap = null;
		try {
			//查询参数
			Map<String, Object> searchParam = new HashMap<String, Object>();
			List<String> nstTypeList = new ArrayList<String>();
			nstTypeList.add("4");
			nstTypeList.add("5");
			nstTypeList.add("6");
			nstTypeList.add("7");
			
			searchParam.put("npRegType", 3);
			searchParam.put("nstRegType", 4);
			searchParam.put("nstTypeList", nstTypeList);
			
			searchParam.put("userId", userId);
			searchParam.put("userMobile", request.getParameter("userMobile"));
			searchParam.put("userStartDate", request.getParameter("userStartDate"));
			searchParam.put("userEndDate", request.getParameter("userEndDate"));
			int total = grdGiftRecordToolService.countInviteRegUserInfoByUserId(searchParam);
			
			//设定分页,排序
			setCommParameters(request, searchParam);
			List<GrdGiftRecordDTO> grdGiftRecordDTOs = grdGiftRecordToolService.queryInviteRegUserInfoByUserId(searchParam);
			
			resultMap = generalResponseResultMap(true,"请求处理成功");
			resultMap.put("total", total);
			resultMap.put("rows", grdGiftRecordDTOs);
		} catch (Exception e) {
			resultMap = generalResponseResultMap(false,"请求处理失败");
			e.printStackTrace();
		}
		
		//保留空值的字段过滤器
		ValueFilter filter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				return value != null ? value : "";
			}
		};
		
		return JSONObject.toJSONString(resultMap, filter, SerializerFeature.WriteDateUseDateFormat);
	}
	
	/** 根据地推人员id分页查询促成订单信息。
	 * @param userId 地推人员id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryOrderInfo", method=RequestMethod.POST)
	@ResponseBody
	public String queryOrderInfo(String userId, HttpServletRequest request){
		Map<String, Object> resultMap = null;
		try {
			//查询参数
			Map<String, Object> searchParam = new HashMap<String, Object>();
			searchParam.put("userId", userId);
			String  businessName =(String)request.getParameter("businessName");
			String  orderCode =(String)request.getParameter("orderCode");
			String  queryStartDate =(String)request.getParameter("startDate");
			String  queryEndDate =(String)request.getParameter("endDate");
			if (StringUtil.isNotEmpty(businessName)) {
				searchParam.put("businessName", businessName);
			}
			if (StringUtil.isNotEmpty(orderCode)) {
				searchParam.put("orderCode", orderCode);
			}
			if (StringUtil.isNotEmpty(queryStartDate)) {
				searchParam.put("startDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(queryStartDate)));
			}
			if (StringUtil.isNotEmpty(queryEndDate)) {
				searchParam.put("endDate", queryEndDate);
			}
			
			//设定分页,排序
			setCommParameters(request, searchParam);
			
			int total = grdGiftDetailToolService.countOrderInfoByUserId(searchParam);
			List<GrdGiftDetailDTO> grdGiftDetailDTOs = null;
			if (total < 1) {
				grdGiftDetailDTOs = Collections.emptyList();
			} else {
				grdGiftDetailDTOs = grdGiftDetailToolService.queryOrderInfoByUserId(searchParam);
				
			}
			
			resultMap = generalResponseResultMap(true,"请求处理成功");
			resultMap.put("total", total);
			resultMap.put("rows", grdGiftDetailDTOs);
		} catch (Exception e) {
			resultMap = generalResponseResultMap(false,"请求处理失败");
			e.printStackTrace();
		}
		
		//保留空值的字段过滤器
		ValueFilter filter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				return value != null ? value : "";
			}
		};
		
		return JSONObject.toJSONString(resultMap, filter, SerializerFeature.WriteDateUseDateFormat);
	}
	 /**
     * @Description 获取所属仓库
     * @param marketid
     * @param request
     * @return
     * @CreationDate 2016年8月15日 下午1:42:56
     * @Author liufan
     */
    @RequestMapping("getChildTeamInfo/{marketId}")
    @ResponseBody
    public String getChildTeamInfo(@PathVariable("marketId") String marketId, HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("marketId", marketId);
            List<GrdMemberDTO> list = grdMemberToolService.getChildTeamInfo(map);
            map.put("rows", list);// rows键 存放每页记录 list
            return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }	
	public static void main(String[] args) {
		Set<String> teamSet = new HashSet<String>();
		teamSet.addAll(Arrays.asList(new String[]{"","23", "23", "", "112"}));
		//移除可能存在的空元素
		teamSet.remove("");
		System.out.println(teamSet.size());
	}
}