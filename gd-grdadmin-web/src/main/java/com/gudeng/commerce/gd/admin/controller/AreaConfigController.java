package com.gudeng.commerce.gd.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.AreaConfigToolService;
import com.gudeng.commerce.gd.admin.service.QueryAreaService;
import com.gudeng.commerce.gd.customer.dto.AreaConfigDTO;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 
 */
@Controller
@RequestMapping("areaConfig")
public class AreaConfigController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(AreaConfigController.class);

	@Autowired
	public AreaConfigToolService areaConfigToolService;

	@Autowired
	public QueryAreaService queryAreaService;
	

	/**
	 * 列表页面
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String areaConfig(HttpServletRequest request) {
		return "areaConfig/areaConfigList";
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
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("areaName", request.getParameter("queryName"));
			map.put("status",  request.getParameter("queryType"));
			// 记录数
			map.put("total", areaConfigToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<AreaConfigDTO> list = areaConfigToolService.getByCondition(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
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
			areaConfigToolService.deleteById(id);
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
		return "areaConfig/addAreaConfig";
	}

	/**
	 * 增加修改同一个方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request , AreaConfigDTO dto) {
		try {

			String id = request.getParameter("id");
			AreaConfigDTO areaConfigDTO = areaConfigToolService.getById(id);
		 
			int i = 0;
			// 根据id判断是否存在，存在即为更新，不存在即为增加
			if (areaConfigDTO != null) {
				dto.setId(!StringUtils.isEmpty(id) ? Long.parseLong(id) : 0L);
				i = areaConfigToolService.updateAreaConfigDTO(dto);
			} else {
				   AreaDTO area = queryAreaService.getAreaByName(request.getParameter("areaName"));
				   if(area !=null){
				   dto.setAreaID(area.getAreaID());
				   }else {
				   return  "not exist";
				  }
				  i = areaConfigToolService.addAreaConfigDTO(dto);
			}
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.debug("Exception is :"+e);
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
			AreaConfigDTO dto = areaConfigToolService.getById(id);
			putModel("id", dto.getId());
			putModel("areaName", dto.getAreaName());
			putModel("status", dto.getStatus());
			return "areaConfig/addAreaConfig";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	


}
