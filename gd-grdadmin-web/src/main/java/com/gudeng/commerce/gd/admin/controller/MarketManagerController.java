package com.gudeng.commerce.gd.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.JsonConvertUtil;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 街市管理
 * 
 * @author xiaodong
 */
@Controller
@RequestMapping("market")
public class MarketManagerController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(MarketManagerController.class);

	@Autowired
	public MarketManageService marketManageService;

	@Autowired
	public QueryAreaService queryAreaService;

	/**
	 * 列表页面
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String market(HttpServletRequest request) {
		return "market/marketList";
	}

	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			// 设置查询参数
			// 记录数
			map.put("total", marketManageService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MarketDTO> list = marketManageService.getByCondition(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据name查询
	 * 
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryByName")
	@ResponseBody
	public String queryByName(@RequestParam String queryName, @RequestParam String queryType,
			HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("marketName", queryName);
			map.put("marketType", queryType);
			// 记录数
			map.put("total", marketManageService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MarketDTO> list = marketManageService.getByName(map);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			String returnStr = JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deletebyid")
	@ResponseBody
	public String deleteById(@RequestParam String id, HttpServletRequest request) {
		try {
			if (null == id || id.equals("")) {
				return "faild";
			}
			marketManageService.deleteById(id);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request) {
		return "market/addMarket";
	}

	/**
	 * 增加修改同一个方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request) {
		try {
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			String county = request.getParameter("county");
			MarketDTO dto = new MarketDTO();
			dto.setMarketName(request.getParameter("marketName"));
			dto.setAddress(request.getParameter("address"));
			dto.setDescription(request.getParameter("description"));
			dto.setStatus(request.getParameter("status"));
			dto.setMarketType(request.getParameter("marketType"));
			dto.setProvinceId(!StringUtils.isEmpty(province) ? Long
					.parseLong(province) : 0);
			dto.setCityId(!StringUtils.isEmpty(city) ? Long.parseLong(city) : 0);
			dto.setAreaId(!StringUtils.isEmpty(county) ? Long.parseLong(county)
					: 0);

			String id = request.getParameter("id");
			MarketDTO market = marketManageService.getById(id);
			int i = 0;
			// 根据id判断是否存在，存在即为更新，不存在即为增加
			if (market != null) {
				dto.setId(!StringUtils.isEmpty(id) ? Long.parseLong(id) : 0L);
				dto.setUpdateUserId(getUser(request)!=null?getUser(request).getUserID():"1");
				dto.setUpdateTimeString(DateUtil.getSysDateTimeString());
				i = marketManageService.updateMarketDTO(dto);
			} else {
				if (marketManageService.getMarketByName(request.getParameter("marketName")) == null) {
					dto.setCreateUserId(getUser(request)!=null?getUser(request).getUserID():"1");
					dto.setCreateTimeString(DateUtil.getSysDateTimeString());
					i = marketManageService.addMarketDTO(dto);
				} else {
					return "exists";
				}
			}
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 打开编辑页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "edit")
	public String edit(HttpServletRequest request) {
		try {

			String id = request.getParameter("id");
			MarketDTO dto = marketManageService.getById(id);
			putModel("id", dto.getId());
			putModel("marketName", dto.getMarketName());
			putModel("marketType", dto.getMarketType());
			putModel("provinceId", dto.getProvinceId());
			putModel("cityId", dto.getCityId());
			putModel("areaId", dto.getAreaId());
			putModel("address", dto.getAddress());
			putModel("status", dto.getStatus());
			putModel("lon", dto.getLon());
			putModel("lat", dto.getLat());
			putModel("description", dto.getDescription());
			return "market/editMarket";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// *******************区域查询************************//

	// 查询全国行政区代码省
	@RequestMapping(value = "queryProvince")
	@ResponseBody
	public String queryProvince(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<?> list = queryAreaService.findProvince();
		this.jsonUtil(list, response);
		return null;
	}

	// 查询全国行政区代码市
	@RequestMapping(value = "queryCity")
	@ResponseBody
	public String queryCity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<?> list = queryAreaService.findCity(request
				.getParameter("province"));
		this.jsonUtil(list, response);
		return null;
	}

	// 查询全国行政区代码县区
	@RequestMapping(value = "queryCounty")
	@ResponseBody
	public String queryCounty(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String city = request.getParameter("city");
		List<?> list = queryAreaService.findCounty(city);
		this.jsonUtil(list, response);
		return null;
	}

	private void jsonUtil(Object list, HttpServletResponse response)
			throws Exception {
		logger.info("JSON格式：" + list.toString());
		String returnJson = JsonConvertUtil.returnJson(list);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(returnJson);
	}

	private void convertPageList(List<MarketDTO> list) throws Exception {
		if (list != null && list.size() > 0) {
			for (MarketDTO dto : list) {
				AreaDTO province = queryAreaService.getArea(String.valueOf(dto
						.getProvinceId()));
				dto.setProvinceName(province != null ? province.getArea() : "");
				AreaDTO city = queryAreaService.getArea(String.valueOf(dto
						.getCityId()));
				dto.setCityName(city != null ? city.getArea() : "");
				AreaDTO area = queryAreaService.getArea(String.valueOf(dto
						.getAreaId()));
				dto.setAreaName(area != null ? area.getArea() : "");
				dto.setUseStatus("0".equals(dto.getStatus()) ? "启用" : "停用");
				dto.setMarketTypeName(getMarketTypeName(dto.getMarketType()));

			}
		}
	}

	private String getMarketTypeName(String type) {
		String typeName = null;
		if ("0".equals(type))
			typeName = "产地供应商";
		else if ("1".equals(type))
			typeName = "街市";
		else if ("2".equals(type))
			typeName = "市场";
		else if ("3".equals(type))
			typeName = "用户自定义添加";
		return typeName;
	}

	/**
	 * 默认查询和id条件查询结合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("queryByType/{marketType}")
	@ResponseBody
	public String queryByType(@PathVariable("marketType") String marketType,
			HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 记录数
			map.put("total", marketManageService.getAllByType(marketType)
					.size());
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MarketDTO> list = marketManageService.getAllByType(marketType);
			convertPageList(list);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map,
					SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("loc")
	public String showLoc(String marketId,HttpServletRequest request) {
		try {
			if(!StringUtils.isBlank(marketId)){
				MarketDTO marketDTO = marketManageService.getById(marketId);
				putModel("market", marketDTO);
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.getMessage();
		}
		
		return "market/marketLoc";
	
	}
	
	@RequestMapping("changeLoc")
	@ResponseBody
	public String changeLoc(MarketDTO market,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(); 
		try {
			market.setUpdateUserId(getUser(request)!=null ? getUser(request).getUserID() : "1");
			result.put("code", marketManageService.updateMarketDTO(market));
			if(result.get("code").equals(1)) {
				result.put("msg", "成功");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			result.put("msg", "error");
		}
		return JSONArray.toJSONString(result);
	}
	
	@RequestMapping("getAllMarket")
	@ResponseBody
	public String getAllMarket() {
		List<MarketDTO> list = new ArrayList<>();
		try {
			list = marketManageService.getAllByStatusAndType("0","2");//0 启用， 2 类型为市场 
		} catch (Exception e) {
			logger.trace(e.getMessage());
			
		}
		return JSONArray.toJSONString(list);
	}
}
