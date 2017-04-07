package com.gudeng.commerce.gd.admin.controller.gdactivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.dto.GdActivityParamsBean;
import com.gudeng.commerce.gd.admin.dto.KeyValuePair;
import com.gudeng.commerce.gd.admin.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.GdActActivityDistributionModeToolService;
import com.gudeng.commerce.gd.admin.service.GdActActivityMarketToolService;
import com.gudeng.commerce.gd.admin.service.GdActActivityParticipationRuleToolService;
import com.gudeng.commerce.gd.admin.service.GdActActivityToolService;
import com.gudeng.commerce.gd.admin.service.GdActActivityUserRuleToolService;
import com.gudeng.commerce.gd.admin.service.GdOrderActivityBaseToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.service.ProductToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityMarketDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityParticipationRuleDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityDistributionModeEntity;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityEntity;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityUserRuleEntity;
import com.gudeng.commerce.gd.promotion.util.ExcelUtil;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 
 * @author weiwenke
 *
 */
@Controller
@RequestMapping("gdActActivity")
public class GdActActivityController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GdActActivityController.class);

	// 活动信息表
	@Autowired
	protected GdActActivityToolService gdActActivityToolService;
	// 活动参与用户规则表
	@Autowired
	protected GdActActivityUserRuleToolService gdActActivityUserRuleToolService;
	// 活动订单基础
	@Autowired
	protected GdOrderActivityBaseToolService gdOrderActivityBaseToolService;
	// 活动参与配送规则
	@Autowired
	protected GdActActivityDistributionModeToolService gdActActivityDistributionModeToolService;
	// 活动市场关联数据
	@Autowired
	protected GdActActivityMarketToolService gdActActivityMarketToolService;
	// 活动参与基本规则表
	@Autowired
	protected GdActActivityParticipationRuleToolService gdActActivityParticipationRuleToolService;

	@Autowired
	protected MarketManageService marketManageService;

	@Autowired
	protected BusinessBaseinfoToolService businessBaseinfoToolService;

	@Autowired
	protected MemberBaseinfoToolService memberBaseinfoToolService;

	@Autowired
	protected ProCategoryService procategoryService;

	@Autowired
	protected ProductToolService productToolService;

	@Autowired
	protected GdProperties gdProperties;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("main/{state}")
	public String list(HttpServletRequest request, String endPage, ModelMap map, @PathVariable("state") String state) {
		if (StringUtils.isEmpty(endPage)) {
			endPage = "0";
		}
		map.put("endPage", endPage);
		map.put("state", state);

		return "gdActActivity/activityList";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping("query")
	public String query(GdActivityParamsBean params, HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("code", params.getActivityCode());

			if (params.getActivityType() != null)
				map.put("type", params.getActivityType());

			map.put("name", params.getActivityName());
			map.put("state", params.getState());
			map.put("marketId", params.getMarketId());
			map.put("startDate", params.getStartDate());
			map.put("endDate", params.getEndDate());
			map.put("isNew", 1);
			map.put("enablePage", "1");
			map.put("queryState", request.getParameter("queryState"));

			// 记录数
			map.put("total", gdActActivityToolService.getTotalForActivityList(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// 活动列表
			List<GdActActivityDTO> list = gdActActivityToolService.getActivityList(map);
			for (GdActActivityDTO item : list) {
				MarketDTO marketDTO = marketManageService.getById(item.getMarketId());
				if (marketDTO != null)
					item.setMarketName(marketDTO.getMarketName());
				if(item.getEndTime()!=null){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date endDt=sdf.parse(item.getEndTime());
					if(new Date().getTime()>endDt.getTime()){
						item.setState(2);
					}
				}
			}
			map.put("rows", list);
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.info("活动列表查询错误 : {}", e);
		}
		return null;
	}

	/**
	 * 校验活动名称是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = { "isExistsActivityName" })
	public @ResponseBody Map<String, Object> isExistsActivityName(String activityName, String activityId) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("name", activityName.trim());
		resultMap.put("id", activityId);
		boolean flag = true;
		try {
			flag = gdActActivityToolService.isExistsActivityName(resultMap);
			resultMap.put("flag", flag);
			if (flag) {
				resultMap.put("message", "此活动名称已存在！");
			}
			return resultMap;
		} catch (Exception e) {
			logger.info("验证活动名称是否存在异常", e);
			resultMap.put("flag", flag);
			resultMap.put("message", "验证活动名称出现存在异常");
			e.printStackTrace();
			return resultMap;

		}
	}

	@ResponseBody
	@RequestMapping("marketPairs")
	public String queryProvince(String signal, String changeText, String text) {
		List<MarketDTO> list = null;
		List<KeyValuePair> pairs = new ArrayList<KeyValuePair>();

		KeyValuePair defaultPair = new KeyValuePair();
		defaultPair.setKeyString("");
		defaultPair.setValueString("全部");
		pairs.add(defaultPair);
		try {
			// 查询市场
			list = marketManageService.getAllByType("2");
			KeyValuePair pair = null;
			for (MarketDTO item : list) {
				pair = new KeyValuePair();
				pair.setKeyString(String.valueOf(item.getId()));
				pair.setValueString(item.getMarketName());
				pairs.add(pair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(pairs);
	}

	/**
	 * 保存数据（新增、修改） 基本规则
	 * 父类抽象方法
	 * @param request
	 * @return
	 * @author lidong
	 * @throws Exception
	 */
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, String basicRule,
			String marketComm, String subRule, String userRule, String transferRule) throws Exception {
		return null;
	}

	/**
	 * 保存活动基本信息
	 * @param request
	 * @param basicRule
	 * @param map
	 * @return
	 * @throws Exception
	 */
	protected Integer saveActBaseInfo(HttpServletRequest request, String basicRule, Map<String, Object> map)
			throws Exception {
		Integer i;
		JSONObject basicRuleJson = JSONArray.parseObject(basicRule);
		GdActActivityEntity entity = new GdActActivityEntity();
		entity.setName(basicRuleJson.getString("name").toString());

		entity.setType(Integer.parseInt(basicRuleJson.getString("type").toString()));
		// DateFormat df=new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
		entity.setStartTime(basicRuleJson.getString("startTime").toString());
		entity.setEndTime(basicRuleJson.getString("endTime").toString());
		entity.setIsNew(1);
		entity.setIsReverse(String.valueOf(basicRuleJson.getString("isReverse") == null?0:1));
		SysRegisterUser user = getUser(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		String id = basicRuleJson.getString("id");
		GdActActivityDTO dataDto = null;
		/*
		 * 如果存在ID 说明是在修改
		 */
		if (StringUtils.isNotEmpty(id)) {
			
			synchronized(this) {

				/*
				 * 获得数据库原始值 version
				 */
				dataDto = gdActActivityToolService.getById(id.toString());
				
				if(dataDto.getIsNew()==2) {
					map.put("msg", "活动已被修改，请重新查询再修改...");
					return -1;
				}
				

				/*
				 * 修改对应的活动数据为旧的
				 */
				GdActActivityDTO dto = new GdActActivityDTO();

				dto.setId(Integer.parseInt(id.toString()));
				dto.setUpdateUserId(user.getUserCode());
				dto.setUpdateTime(date);
				dto.setIsNew(2);

				gdActActivityToolService.update(dto); // 如果修改，则先删除原来数据，再插入
				
				entity.setCreateUserId(dataDto.getCreateUserId());
				entity.setCreateTime(dataDto.getCreateTime());
				entity.setUpdateUserId(user.getUserCode());
				entity.setUpdateTime(date);

			}
		} else {
			entity.setCreateUserId(user.getUserCode());
			entity.setCreateTime(date);
			entity.setUpdateUserId(user.getUserCode());
			entity.setUpdateTime(date);
		}

		/*
		 * 添加新的获得信息 同时设置版本号+1
		 */

		/*
		 * 判断是否需要生成新的code
		 */
		if (StringUtils.isEmpty(id)) {
			String code = gdActActivityToolService.getSequence(); // 先获取序列
			entity.setCode("hd" + code);
		} else {
			String code = dataDto.getCode(); // 如果存在旧的,使用旧的CODE
			entity.setCode(code);
		}

		entity.setState(1);
		if (dataDto != null) {
			entity.setVersion(dataDto.getVersion() + 1);
		} else {
			entity.setVersion(1);
		}
		entity.setIsNew(1);

		/*
		 * 保存活动基础信息
		 */
		i = Integer.parseInt(gdActActivityToolService.insert(entity).toString());
		return i;
	}

	/**
	 * 插入市场佣金
	 * 
	 * @param activityId(活动id)
	 * @param activityRule(规则信息)
	 * @return
	 */
	public boolean saveOrUpdateGdActActivityMarketComm(Integer activityId, String marketComm) {
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
					dataMarketComm.put("buyerAmt", marketCommJson.getDouble("buyerAmt")==null?0:marketCommJson.getDouble("buyerAmt"));

					/*
					 * 区间值 集合
					 */
					JSONArray details = new JSONArray();
					dataMarketComm.put("detail", details);

					/*
					 * 默认第一个的值处理
					 */
					parseOrderLimit(marketCommJson, details, "_id");

					// 读取区间值总数
					Integer count = marketCommJson.getInteger("hid_buyer_id");

					if (count > 0) {
						for (int i = 0; i < count; i++) {

							/*
							 * 处理可能存在多个的值
							 */
							parseOrderLimit(marketCommJson, details, "" + i);
						}

					}

				}

				/*
				 * 买家佣金 保存在实体类 准备保存数据库
				 */
				dto.setRuleJson(dataMarketComm.toJSONString());
				dto.setActId(activityId);
				gdActActivityToolService.addRuleComm(dto);

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
					dataMarketComm.put("sellerAmt", marketCommJson.getDouble("sellerAmt")==null?0:marketCommJson.getDouble("sellerAmt"));

					/*
					 * 区间值 集合
					 */
					JSONArray details = new JSONArray();
					dataMarketComm.put("detail", details);
//					dataMarketComm.put("isReverse", marketCommJson.getInteger("solderStartAmt_isReverse"));
//					dataMarketComm.put("", value)
					/*
					 * 默认第一个的值处理
					 */
					parseOrderLimitSolder(marketCommJson, details, "_id");
					parseOrderLimitSolderReverse(marketCommJson, details, "_id");

					// 读取区间值总数正
					Integer count = marketCommJson.getInteger("hid_solder_id");
					// 读取区间值总数逆
					Integer count1 = marketCommJson.getInteger("reverse_hid_solder_id")==null?0:marketCommJson.getInteger("reverse_hid_solder_id");					

					if (count > 0) {
						for (int i = 0; i < count; i++) {

							/*
							 * 处理可能存在多个的值
							 */
							parseOrderLimitSolder(marketCommJson, details, "" + i);
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
				gdActActivityToolService.addRuleComm(dto);

			}

			return true;
		} catch (Exception e) {
			logger.trace("新增保存错误", e);
			return false;
		}

	}
	/**
	 * 插入平台佣金
	 * 
	 * @param activityId(活动id)
	 * @param activityRule(规则信息)
	 * @return
	 */
	public boolean saveOrUpdateGdActActivityCommByPT(Integer activityId, String marketComm) {
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
				dto.setCommRuleType("1"); // 买家佣金标识
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
					parseOrderLimitByPT(marketCommJson, details, "_id");

					// 读取区间值总数
					Integer count = marketCommJson.getInteger("hid_buyer_id");

					if (count > 0) {
						for (int i = 0; i < count; i++) {

							/*
							 * 处理可能存在多个的值
							 */
							parseOrderLimitByPT(marketCommJson, details, "" + i);
						}

					}

				}

				/*
				 * 买家佣金 保存在实体类 准备保存数据库
				 */
				
				dto.setRuleJson(dataMarketComm.toJSONString());
				dto.setActId(activityId);
				//System.out.println(marketCommJson.getJSONArray("detail"));
				if(dataMarketComm.getJSONArray("detail")!=null&&dataMarketComm.getJSONArray("detail").size()>0&&marketCommJson.getDouble("buyerAmt")!=null&&!marketCommJson.get("buyerAmt").equals("")){
				gdActActivityToolService.addRuleComm(dto);
				}

			}

			dataMarketComm = new JSONObject();
			dto = new GdActActivityCommDTO();
			/*
			 * 卖家正向
			 */
			if (marketCommJson.getInteger("solder") != null) {
				dto.setCommRuleType("2"); // 卖家佣金标识
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
					/*
					 * 默认第一个的值处理
					 */
					parseOrderLimitSolderByPT(marketCommJson, details, "_id");
					parseOrderLimitSolderReverse(marketCommJson, details, "_id");

					// 读取区间值总数正
					Integer count = marketCommJson.getInteger("hid_solder_id");
					// 读取区间值总数逆
					Integer count1 = marketCommJson.getInteger("reverse_hid_solder_id")==null?0:marketCommJson.getInteger("reverse_hid_solder_id");					

					if (count > 0) {
						for (int i = 0; i < count; i++) {

							/*
							 * 处理可能存在多个的值
							 */
							parseOrderLimitSolderByPT(marketCommJson, details, "" + i);
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
				if(dataMarketComm.getJSONArray("detail")!=null&&dataMarketComm.getJSONArray("detail").size()>0&&marketCommJson.getDouble("sellerAmt")!=null&&!marketCommJson.get("sellerAmt").equals("")){
				gdActActivityToolService.addRuleComm(dto);
				}

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
	private void parseOrderLimit(JSONObject marketCommJson, JSONArray details, String code) {
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
		details.add(detail1);
	}
	/**
	 * 转换订单区间值到数据库结构 for buyer 平台
	 * 
	 * @param marketCommJson
	 * @param details
	 * @param code
	 */
	private void parseOrderLimitByPT(JSONObject marketCommJson, JSONArray details, String code) {
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
		if(detail1.get("startAmt")!=null && detail1.get("orderProp")!=null){
			details.add(detail1);

		}
		}
		if(detail1.getInteger("type")==1){
			if(detail1.get("startAmt")!=null && detail1.get("fixedAmt")!=null&&! detail1.get("fixedAmt").equals("")){
				details.add(detail1);

			}
			}

	}
	

	/**
	 * 转换订单区间值到数据库结构 for solder  正 平台
	 * 
	 * @param marketCommJson
	 * @param details
	 * @param code
	 */
	private void parseOrderLimitSolderByPT(JSONObject marketCommJson, JSONArray details, String code) {
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
		if(detail1.getInteger("type")==0){
			if(detail1.get("startAmt")!=null && detail1.get("orderProp")!=null){
				details.add(detail1);
			}
		}
		if(detail1.getInteger("type")==1){
			if(detail1.get("startAmt")!=null && detail1.get("fixedAmt")!=null){
				details.add(detail1);
			}
		}
		
	}
	/**
	 * 转换订单区间值到数据库结构 for solder 正
	 * 
	 * @param marketCommJson
	 * @param details
	 * @param code
	 */
	private void parseOrderLimitSolder(JSONObject marketCommJson, JSONArray details, String code) {
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
			if(detail1.get("startAmt")!=null&&detail1.get("orderProp")!=null){
				details.add(detail1);
			}
		}
		if(detail1.getInteger("type")==1){
			if(detail1.get("startAmt")!=null &&detail1.get("fixedAmt")!=null){
				details.add(detail1);
			}
		}
		
	}
	/**
	 * 插入补贴规则
	 * 
	 * @param activityId(活动id)
	 * @param activityRule(规则信息)
	 * @return
	 */
	public boolean saveOrUpdateGdActActivitySubRule(Integer activityId, String subRule) {
		/*
		 * 转义 原始JSON数据
		 */
		JSONArray subRulesJson = JSONArray.parseArray(subRule);

		try {
			GdActActivityCommDTO dto = new GdActActivityCommDTO();

			dto.setCommRuleType("5");

			/*
			 * 补贴规则
			 */
			for (int s = 0; s < subRulesJson.size(); s++) {
				JSONObject subRuleJson = subRulesJson.getJSONObject(s);

				JSONObject dataSubRule = new JSONObject();

				dataSubRule.put("busiType1", subRuleJson.getInteger("busiType1"));
				dataSubRule.put("busiType2", subRuleJson.getInteger("busiType2"));
				dataSubRule.put("cardType", subRuleJson.getInteger("cardType"));
				dataSubRule.put("payChannel", subRuleJson.getString("payChannel"));

				JSONObject ruleDetail = new JSONObject();
				dataSubRule.put("rule", ruleDetail);

				/*
				 * 设置卖家补贴上线
				 */
				if (null == subRuleJson.getString("solderSubCheck")) {
					ruleDetail.put("solderLimitType", 0);
				} else {
					ruleDetail.put("solderLimitType", 1);
					ruleDetail.put("solderLimitAmt", subRuleJson.getString("solderSubAmt"));
				}
				if (subRuleJson.getInteger("sub_radio") == 4){
					ruleDetail.put("type", 4);
				JSONObject detail = new JSONObject();
				detail.put("proportion", subRuleJson.getDouble("proportion"));
				detail.put("orderLimitAmt", subRuleJson.getDouble("orderLimit"));
					ruleDetail.put("detail", detail);

				}
				else if (subRuleJson.getInteger("sub_radio") == 1) { // 按订单
					ruleDetail.put("type", 1);
					JSONObject detail = new JSONObject();
					ruleDetail.put("detail", detail);
					detail.put("orderLimitAmt", subRuleJson.getDouble("orderAmt"));
				} else if (subRuleJson.getInteger("sub_radio") == 2) { // 按订单比例
					ruleDetail.put("type", 2);
					JSONObject detail = new JSONObject();
					ruleDetail.put("detail", detail);
					detail.put("orderLimitAmt", subRuleJson.getDouble("orderLimit"));
					detail.put("orderProp", subRuleJson.getDouble("orderSub"));
				} else if (subRuleJson.getInteger("sub_radio") == 3) { // 按订单金额区间
					ruleDetail.put("type", 3);

					/*
					 * 区间值 集合
					 */
					JSONArray details = new JSONArray();
					ruleDetail.put("detail", details);

					/*
					 * 默认第一个的值处理
					 */
					parseOrderLimitSub(subRuleJson, details, "_id");

					// 读取区间值总数
					Integer count = subRuleJson.getInteger("hid_id");

					if (count > 0) {
						for (int i = 0; i < count; i++) {

							/*
							 * 处理可能存在多个的值
							 */
							parseOrderLimitSub(subRuleJson, details, "" + i);
						}
					}

				}

				/*
				 * 每一笔补贴规则都添加一条数据
				 */
				dto.setActId(activityId);
				dto.setRuleJson(dataSubRule.toJSONString());
				gdActActivityToolService.addRuleComm(dto);
			}

			return true;
		} catch (Exception e) {
			logger.trace("新增保存错误", e);
			return false;
		}

	}

	/**
	 * 转换订单区间值到数据库结构 for sub
	 * 
	 * @param marketCommJson
	 * @param details
	 * @param code
	 */
	private void parseOrderLimitSub(JSONObject subRuleJson, JSONArray details, String code) {
		JSONObject detail1 = new JSONObject();
		detail1.put("startAmt", subRuleJson.getDouble("startAmt" + code));
		detail1.put("endAmt", subRuleJson.getDouble("endAmt" + code));

		if (subRuleJson.getInteger("sub_lim_radio" + code) == 0) {
			detail1.put("type", 0);
			detail1.put("orderLimitAmt", subRuleJson.getDouble("subLimit" + code));
			detail1.put("orderProp", subRuleJson.getDouble("subPerc" + code));
		} else {
			detail1.put("type", 1);
			detail1.put("fixedAmt", subRuleJson.getDouble("subAmt" + code));
		}
		details.add(detail1);
	}

	/**
	 * 活动参与用户规则表
	 * 
	 * @param activityId(活动id)
	 * @param participationRule(规则信息)
	 * @return
	 */
	public boolean saveOrUpdateGdActivityUserRule(long activityId, String userRule) {
		JSONObject userRulesJson = JSONObject.parseObject(userRule);

		JSONObject pageDataJson = userRulesJson.getJSONObject("pageData");
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
			 * 插入
			 */
			if (pageDataJson.getInteger("userRuleBuyer") != null) {
				if (pageDataJson.getInteger("userRuleBuyer") == 3) {

					/*
					 * 设置类目
					 */
					ProductCategoryDTO productCategoryDTO = productToolService
							.getCategoryById(pageDataJson.getLong("categoryId"));
					entity.setUserType(3);
					entity.setReferId(pageDataJson.getLong("categoryId"));
					entity.setRemark("" + productCategoryDTO.getCurLevel());

					/*
					 * 插入数据库
					 */
					gdActActivityUserRuleToolService.insert(entity);

				} else if (pageDataJson.getInteger("userRuleBuyer") == 2) {
					entity.setUserType(2);

					/*
					 * 获取活动商铺
					 */
					JSONArray addShopsJson = userRulesJson.getJSONArray("addShopData");

					/*
					 * 遍历添加活动商铺信息
					 */
					for (int i = 0; i < addShopsJson.size(); i++) {

						JSONObject addShopJson = addShopsJson.getJSONObject(i);
						entity.setReferId(addShopJson.getLong("businessId"));

						/*
						 * 添加到数据库
						 */
						gdActActivityUserRuleToolService.insert(entity);
					}

				} else if (pageDataJson.getInteger("userRuleBuyer") == 4) {
					entity.setUserType(4);

					/*
					 * 获取活动商品
					 */
					JSONArray addProdsJson = userRulesJson.getJSONArray("addProdData");

					/*
					 * 遍历添加活动商品信息
					 */
					for (int i = 0; i < addProdsJson.size(); i++) {

						JSONObject addProdJson = addProdsJson.getJSONObject(i);
						entity.setReferId(addProdJson.getLong("productId"));

						/*
						 * 添加到数据库
						 */
						gdActActivityUserRuleToolService.insert(entity);
					}

				}
			}

			/*
			 * 添加买家 清空多余数据
			 */
			entity.setRemark(null);

			/*
			 * 获取活动商铺
			 */
			entity.setUserType(1);
			JSONArray addBuyersJson = userRulesJson.getJSONArray("addBuyerData");

			/*
			 * 遍历添加买家信息
			 */
			for (int i = 0; i < addBuyersJson.size(); i++) {

				JSONObject addBuyerJson = addBuyersJson.getJSONObject(i);
				entity.setReferId(addBuyerJson.getLong("memberId"));

				/*
				 * 添加到数据库
				 */
				gdActActivityUserRuleToolService.insert(entity);
			}

			/*
			 * 添加买家物流配送订单金额范围
			 */
			GdActActivityParticipationRuleDTO gdActActivityParticipationRuleDTO = new GdActActivityParticipationRuleDTO();
			gdActActivityParticipationRuleDTO.setActivity_id(Integer.parseInt("" + activityId));
			gdActActivityParticipationRuleDTO.setMax_cost(pageDataJson.getDouble("max_cost"));
			gdActActivityParticipationRuleDTO.setMin_cost(pageDataJson.getDouble("min_cost"));

			/*
			 * 插入数据库
			 */
			gdActActivityParticipationRuleToolService.insert(gdActActivityParticipationRuleDTO);

			return true;
		} catch (Exception e) {
			logger.trace("新增保存错误", e);
		}
		return false;
	}

	/**
	 * 插入活动参与配送规则
	 * 
	 * @param entity
	 * @param dto
	 * @return
	 */
	public boolean saveOrUpdateGdActActivityDistributionMode(long activityId, String transferRule) {
		JSONArray transferRuleJsons = JSONObject.parseArray(transferRule);

		GdActActivityDistributionModeEntity entity = new GdActActivityDistributionModeEntity();

		entity.setActivity_id(Integer.parseInt(activityId + ""));

		try {
			/*
			 * 拆分物流类型 可能多选
			 */
			for (Object para : transferRuleJsons) {
				entity.setType(((JSONObject) para).getInteger("m_type"));
				gdActActivityDistributionModeToolService.insert(entity);

			}

			return true;
		} catch (Exception e) {
			logger.trace("新增保存错误", e);
		}
		return false;
	}

	/**
	 * 进入新增或编辑页面
	 * 
	 * @param request
	 * @return
	 * @author weiwenke
	 */

	@RequestMapping("gdActActivity_edit_add")
	public String addDto(HttpServletRequest request) throws Exception {

		return "gdActActivity/gdActActivity_sele";

	}

	/**
	 * 进入活动商铺、活动买家信息页面
	 * 
	 * @param request
	 * @return
	 * @author weiwenke
	 */

	@RequestMapping("gdActActivity_shop_buyer/{type}")
	public ModelAndView showShopAndBuyer(HttpServletRequest request, @PathVariable("type") String type,String index) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.addObject("index", index);
		mv.setViewName("gdActActivity/gdActActivity_shop_buyer");
		return mv;

	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	public ModelAndView edit(HttpServletRequest request, String id, String views) {
		return null;
	}

	protected void refProdUserRule(JSONObject solderRule, JSONArray prodOrShopArray, GdActActivityUserRuleDTO userRule)
			throws Exception {
		/*
		 * 设置为活动商品
		 */
		if (solderRule.getInteger("type") == null) {
			solderRule.put("type", 4);
			solderRule.put("prods", prodOrShopArray);
		}

		// 活动商品为集合 所以定义在集合外层

		/*
		 * 查询对应的商品信息 同时转换JSON 存储进json数组
		 */
		Map<String, Object> queryProd = new HashMap<>();
		queryProd.put("productId", userRule.getReferId());
		ProductBaseinfoDTO productDto = productToolService.getListPage(queryProd).getDataList().get(0);
		prodOrShopArray.add(JSONArray.parseObject(JSONArray.toJSONString(productDto)));
	}

	protected void refUserRuleCategory(JSONObject solderRule, GdActActivityUserRuleDTO userRule) throws Exception {
		ProductCategoryDTO productCategoryDTO = procategoryService.getProductCategoryById(userRule.getReferId());

		/*
		 * 设置关联的类目
		 */
		solderRule.put("type", 3);
		solderRule.put("level", productCategoryDTO.getCurLevel());

		/*
		 * 如果为二级或三级类目 设置父类目
		 */
		if (productCategoryDTO.getCurLevel() > 0) {
			solderRule.put("parentId", productCategoryDTO.getParentId());
		}
		/*
		 * 如果为三级类目 需要设置顶级类目的ID
		 */
		if (productCategoryDTO.getCurLevel() == 2) {
			ProductCategoryDTO parentCategory = procategoryService
					.getProductCategoryById(productCategoryDTO.getParentId());

			solderRule.put("topParentId", parentCategory.getParentId());
		}

		// 添加到对应的json
		solderRule.put("categoryId", productCategoryDTO.getCategoryId());
	}

	@RequestMapping("queryMemberList/{id}")
	public ModelAndView queryMemberList(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("activityId", id);
			params.put("userType", 1);
			List<GdActActivityUserRuleDTO> buyerList = gdActActivityToolService.getActivityMemberList(params);
			params.put("userType", 2);
			List<GdActActivityUserRuleDTO> sellerList = gdActActivityToolService.getActivityMemberList(params);
			mv.addObject("buyerList", buyerList);
			mv.addObject("sellerList", sellerList);
		} catch (Exception e) {
			logger.info("queryMemberList with ex : {}", e);
		}
		mv.setViewName("gdActActivity/memberList");
		return mv;
	}

	@RequestMapping("toMemberList/{activityId}/{type}")
	public String toMemberList(HttpServletRequest request, @PathVariable("activityId") String activityId,
			@PathVariable("type") String type) {
		putModel("activityId", activityId);
		putModel("type", type);
		return "gdActActivity/memberList";
	}

	@ResponseBody
	@RequestMapping("loadBuyerList")
	public String loadBuyerList(String activityId, HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("activityId", activityId);
			map.put("userType", 1);
			// 记录数
			map.put("total", gdActActivityToolService.getTotalForActMemberList(map));
			// 设定分页,排序
			setCommParameters(request, map);
			List<GdActActivityUserRuleDTO> buyerList = gdActActivityToolService.getActivityMemberList(map);
			Set<String> ids = new HashSet<String>();
			for (GdActActivityUserRuleDTO item : buyerList) {
				ids.add(String.valueOf(item.getReferId()));
			}
			map.put("userIdList", ids);
			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.getBuyerListForGdActivity(map);
			map.put("rows", list);
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.info("loadBuyerList with ex : {}", e);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("loadSellerList")
	public String loadSellerList(String activityId, HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("activityId", activityId);
			map.put("userType", 2);
			// 记录数
			map.put("total", gdActActivityToolService.getTotalForActMemberList(map));
			// 设定分页,排序
			setCommParameters(request, map);
			List<GdActActivityUserRuleDTO> sellerList = gdActActivityToolService.getActivityMemberList(map);
			Set<String> ids = new HashSet<String>();
			for (GdActActivityUserRuleDTO item : sellerList) {
				ids.add(String.valueOf(item.getReferId()));
			}
			map.put("businessIdList", ids);
			List<BusinessBaseinfoDTO> list = businessBaseinfoToolService.getListForGdActivity(map);
			map.put("rows", list);
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.info("loadSellerList with ex : {}", e);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("checkExistAct")
	public String checkExistAct(Long refer_id, Integer user_type, String isExistStartTime, String isExistEndTime, String type,
			HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("isExistStartTime", isExistStartTime);
			map.put("isExistEndTime", isExistEndTime);
			map.put("type", type);
			if (user_type == 5) { // 5 代表市场
				map.put("marketId", refer_id);
			} else {
				map.put("refer_id", refer_id);
				map.put("user_type", user_type);
			}
			map.put("isNew", 1);
			//map.put("state", 1); 禁用 启用的一起判断
			// 记录数
			List<GdActActivityDTO> result = gdActActivityToolService.getExistList(map);
			map.put("total", result.size());
			if(result.size()>0) {
				map.put("code", result.get(0).getCode());
				map.put("id", result.get(0).getId());
			}

			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.info("loadSellerList with ex : {}", e);
		}
		return null;
	}

	@RequestMapping("gdActActivity_add_shop")
	public String addShop() {

		return "gdActActivity/gdActActivity_add_shop";
	}

	@RequestMapping("gdActActivity_add_sub")
	public String addSub() {
		return "gdActActivity/gdActActivity_add_sub";
	}

	@RequestMapping("gdActActivity_add_prod")
	public String addProd() {
		return "gdActActivity/gdActActivity_add_prod";
	}

	@RequestMapping("gdActActivity_add_buyer")
	public String addBuyer(String index) {
		putModel("index", index);
		return "gdActActivity/gdActActivity_add_buyer";
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				map.put("msg", "未选中删除数据");
			} else {
				List<String> list = Arrays.asList(ids.split(","));
				int i = gdActActivityToolService.deleteBatch(list);
				map.put("msg", "success");
			}
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(GdActivityParamsBean params, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("code", params.getActivityCode());
			map.put("type", params.getActivityType());
			map.put("name", params.getActivityName());
			map.put("state", params.getState());
			map.put("marketId", params.getMarketId());
			map.put("startDate", params.getStartDate());
			map.put("endDate", params.getEndDate());
			map.put("endDate", params.getEndDate());
			map.put("queryState", request.getParameter("queryState"));
			map.put("isNew", 1);

			int total = gdActActivityToolService.getTotalForActivityList(map);
			if (total > 10000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		} catch (Exception e) {
			logger.info("activity checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}

	/**
	 * 导出Excel文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "exportData")
	public String exportData(GdActivityParamsBean params, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream ouputStream = null;
		try {
			// 设置查询参数
			map.put("code", params.getActivityCode());
			map.put("type", params.getActivityType());
			map.put("name", params.getActivityName());
			map.put("state", params.getState());
			map.put("marketId", params.getMarketId());
			map.put("startDate", params.getStartDate());
			map.put("endDate", params.getEndDate());
			map.put("queryState", request.getParameter("queryState"));
			map.put("isNew", 1);
			// 设置输出响应头信息
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(("template.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			List<GdActActivityDTO> list = gdActActivityToolService.getActivityList(map);
			for (GdActActivityDTO item : list) {
				MarketDTO marketDTO = marketManageService.getById(item.getMarketId());
				item.setMarketName(marketDTO.getMarketName());
			}
			XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, GdActActivityDTO.class);
			workBook.write(ouputStream);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "includeExcel", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 得到上传的文件
		MultipartFile mFile = multipartRequest.getFile("file");
		String actId = request.getParameter("actId");

		// 得到上传服务器的路径
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
		// 得到上传的文件的文件名
		String filename = actId + "_includeExcel.xls";
		InputStream inputStream = mFile.getInputStream();
		byte[] b = new byte[1048576];
		int length = inputStream.read(b);
		path += "\\" + filename;
		// 文件流写到服务器端
		FileOutputStream outputStream = new FileOutputStream(path);
		outputStream.write(b, 0, length);
		inputStream.close();
		outputStream.close();

		/*
		 * 解析文件
		 */
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(new File(path));
			Workbook workBook = WorkbookFactory.create(inStream);
			Sheet sheet = workBook.getSheetAt(0);

			int numOfRows = sheet.getLastRowNum() + 1;

			Map<String, JSONObject> map = new HashMap<>();

			String regMoney = "(^(([1-9][0-9]{0,6})|0)(\\.[0-9]{1,2})?$)";
			String regCert = "(^(100|[1-9][0-9]|[1-9])(\\.[0-9]{1,2})?$)";

			JSONArray subDatas = new JSONArray();

			for (int i = 1; i < numOfRows; i++) {
				Row row = sheet.getRow(i);
				String cardType = getCellValue(row.getCell(0));
				String busiType1 = getCellValue(row.getCell(1));
				String busiType2 = getCellValue(row.getCell(2));
				String payChannel = getCellValue(row.getCell(3));

				JSONObject dataSubRule = new JSONObject();

				Integer cardTypeCode = null;
				Integer busiType1Code = null;
				Integer busiType2Code = null;
				String payChannelCode = null;

				if ("借记卡".equals(cardType)) {
					cardTypeCode = 0;
				} else if ("贷记卡".equals(cardType)) {
					cardTypeCode = 1;
				} else {
					throw new RuntimeException("卡类型不存在");
				}

				if ("本行".equals(busiType1Code)) {
					cardTypeCode = 0;
				} else if ("跨行".equals(busiType1Code)) {
					cardTypeCode = 1;
				} else {
					throw new RuntimeException("业务类型不存在");
				}

				dataSubRule.put("busiType1", busiType1Code);
				dataSubRule.put("busiType2", busiType2Code);
				dataSubRule.put("cardType", cardTypeCode);

				JSONObject payChannels = CommonUtil.httpPost(gdProperties.getPayCenterUrl() + "api/getAll", null,
						false);

				if (payChannels.getBooleanValue("success")) {
					JSONArray payChannelsArray = payChannels.getJSONArray("result");
					for (Iterator<Object> iterator = payChannelsArray.iterator(); iterator.hasNext();) {
						JSONObject payChannelObj = (JSONObject) iterator.next();
						if (payChannelObj.getString("payChannelName").equals(payChannel)) {
							payChannelCode = payChannelObj.getString("payChannelCode");
						}
					}
					if (payChannelCode == null) {
						throw new RuntimeException("支付渠道数据错误");
					}
				} else {
					throw new RuntimeException("读取支付渠道数据失败");
				}

				dataSubRule.put("payChannel", payChannelCode);

				String subType = getCellValue(row.getCell(6));

				if ("固定金额".equals(subType)) {
					dataSubRule.put("sub_radio", 1);
					String subAmt = getCellValue(row.getCell(4));
					boolean flg = Pattern.matches(regMoney, subAmt);

					if (flg) {
						dataSubRule.put("orderAmt", subAmt);
					} else {
						throw new RuntimeException("固定金额格式错误");
					}
					if (map.get(cardType + busiType1 + busiType2 + payChannel) == null) {
						map.put(cardType + busiType1 + busiType2 + payChannel, dataSubRule);
					} else {
						throw new RuntimeException("格式错误 重复数据");
					}
				} else if ("固定比例".equals(subType)) {
					dataSubRule.put("sub_radio", 2);
					String subCert = getCellValue(row.getCell(4)).substring(0,
							getCellValue(row.getCell(4)).length() - 1);
					String subLimit = getCellValue(row.getCell(5));
					boolean flg = Pattern.matches(regMoney, subCert);
					flg = flg && Pattern.matches(regCert, subLimit);
					if (flg) {
						dataSubRule.put("orderSub", subCert);
						dataSubRule.put("orderLimit", subLimit);
					} else {
						throw new RuntimeException("固定金额格式错误");
					}
					if (map.get(cardType + busiType1 + busiType2 + payChannel) == null) {
						map.put(cardType + busiType1 + busiType2 + payChannel, dataSubRule);
					} else {
						throw new RuntimeException("格式错误 重复数据");
					}
				} else if ("区间金额".equals(subType)) {
					dataSubRule.put("sub_radio", 3);

					if (map.get(cardType + busiType1 + busiType2 + payChannel) == null) {
						dataSubRule.put("hid_id", 0);
						dataSubRule.put("sub_lim_radio_id", 1);
						String subAmt = getCellValue(row.getCell(4));
						String startAmt = getCellValue(row.getCell(7));
						String endAmt = getCellValue(row.getCell(8));

						boolean flg = Pattern.matches(regMoney, subAmt);
						flg = flg && Pattern.matches(regMoney, startAmt);
						flg = flg && Pattern.matches(regMoney, endAmt);

						if (flg) {
							dataSubRule.put("startAmt_id", startAmt);
							dataSubRule.put("endAmt_id", endAmt);
							dataSubRule.put("subAmt_id", subAmt);
						} else {
							throw new RuntimeException("区间金额格式错误");
						}

						map.put(cardType + busiType1 + busiType2 + payChannel, dataSubRule);

					} else {
						dataSubRule = map.get(cardType + busiType1 + busiType2 + payChannel);

						dataSubRule.put("hid_id", dataSubRule.getInteger("hid_id") + 1);
						dataSubRule.put("sub_lim_radio" + dataSubRule.getInteger("hid_id"), 1);

						String subAmt = getCellValue(row.getCell(4));
						String startAmt = getCellValue(row.getCell(7));
						String endAmt = getCellValue(row.getCell(8));

						boolean flg = Pattern.matches(regMoney, subAmt);
						flg = flg && Pattern.matches(regMoney, startAmt);
						flg = flg && Pattern.matches(regMoney, endAmt);

						if (flg) {
							dataSubRule.put("startAmt" + dataSubRule.getInteger("hid_id"), startAmt);
							dataSubRule.put("endAmt" + dataSubRule.getInteger("hid_id"), endAmt);
							dataSubRule.put("subAmt" + dataSubRule.getInteger("hid_id"), subAmt);
						} else {
							throw new RuntimeException("区间金额格式错误");
						}
					}

				} else if ("区间比例".equals(subType)) {
					dataSubRule.put("sub_radio", 3);

					if (map.get(cardType + busiType1 + busiType2 + payChannel) == null) {
						dataSubRule.put("hid_id", 0);
						dataSubRule.put("sub_lim_radio_id", 0);
						String subCert = getCellValue(row.getCell(4)).substring(0,
								getCellValue(row.getCell(4)).length() - 1);
						String subLimit = getCellValue(row.getCell(6));
						String startAmt = getCellValue(row.getCell(7));
						String endAmt = getCellValue(row.getCell(8));

						boolean flg = Pattern.matches(regCert, subCert);
						flg = flg && Pattern.matches(regMoney, subLimit);
						flg = flg && Pattern.matches(regMoney, startAmt);
						flg = flg && Pattern.matches(regMoney, endAmt);

						if (flg) {
							dataSubRule.put("startAmt_id", startAmt);
							dataSubRule.put("endAmt_id", endAmt);
							dataSubRule.put("subLimit_id", subLimit);
							dataSubRule.put("subPerc_id", subCert);
						} else {
							throw new RuntimeException("区间比例格式错误");
						}

						map.put(cardType + busiType1 + busiType2 + payChannel, dataSubRule);

					} else {
						dataSubRule = map.get(cardType + busiType1 + busiType2 + payChannel);

						dataSubRule.put("hid_id", dataSubRule.getInteger("hid_id") + 1);
						dataSubRule.put("sub_lim_radio" + dataSubRule.getInteger("hid_id"), 0);

						String subCert = getCellValue(row.getCell(4)).substring(0,
								getCellValue(row.getCell(4)).length() - 1);
						String subLimit = getCellValue(row.getCell(6));
						String startAmt = getCellValue(row.getCell(7));
						String endAmt = getCellValue(row.getCell(8));

						boolean flg = Pattern.matches(regCert, subCert);
						flg = flg && Pattern.matches(regMoney, subLimit);
						flg = flg && Pattern.matches(regMoney, startAmt);
						flg = flg && Pattern.matches(regMoney, endAmt);

						if (flg) {
							dataSubRule.put("startAmt" + dataSubRule.getInteger("hid_id"), startAmt);
							dataSubRule.put("endAmt" + dataSubRule.getInteger("hid_id"), endAmt);
							dataSubRule.put("subLimit" + dataSubRule.getInteger("hid_id"), subLimit);
							dataSubRule.put("subPerc" + dataSubRule.getInteger("hid_id"), subCert);
						} else {
							throw new RuntimeException("区间金额格式错误");
						}
					}
				}

				subDatas.add(dataSubRule);
				dataSubRule = new JSONObject();

			}

			request.getSession().setAttribute("includegdActUserRuleMsg", "导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("includegdActUserRuleMsg", "导入失败：" + e.getMessage());
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private String getCellValue(Cell cell) {
		String cellValue = "";
		DataFormatter formatter = new DataFormatter();
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = formatter.formatCellValue(cell);
				} else {
					double value = cell.getNumericCellValue();
					int intValue = (int) value;
					cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR:
				cellValue = "";
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}
		}
		return cellValue.trim();
	}

	/**
	 * 修改活动状态、禁用、启用
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "updateStatus" })
	public @ResponseBody Map<String, Object> updateStatus(HttpServletRequest request, GdActActivityDTO dto)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isNotEmpty(dto.getCode())) {
				int i = gdActActivityToolService.update(dto);
				if (i > 0) {
					gdOrderActivityBaseToolService.deleteCach(0);
					map.put("msg", "success");
				} else {
					map.put("msg", "保存失败");
				}
			}
		} catch (Exception e) {
			map.put("msg", "保存失败");
			logger.trace("修改状态错误", e);
		}
		return map;
	}

}
