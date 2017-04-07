package com.gudeng.commerce.gd.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.admin.service.AreaToolService;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.entity.Area;

@Controller
@RequestMapping("common")
public class CommonController extends AdminBaseController {

	@Autowired
	public AreaToolService areaToolService;

	@ResponseBody
	@RequestMapping("queryProvince")
	public String queryProvince() {
		List<AreaDTO> list = null;
		try {
			list = areaToolService.findProvince();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping("queryCity/{provinceId}")
	public String queryCity(@PathVariable("provinceId") String provinceId) {
		List<AreaDTO> list = null;
		try {
			list = areaToolService.findCity(provinceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping("queryArea/{cityId}")
	public String queryArea(@PathVariable("cityId") String cityId) {
		List<AreaDTO> list = null;
		try {
			list = areaToolService.findCounty(cityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}
	
	@ResponseBody
	@RequestMapping("getAreaType/{areaId}")
	public String getAreaType(@PathVariable("areaId") String areaId) {
		Area area;
		try {
			area = areaToolService.getArea(areaId);
			return JSONObject.toJSONString(area.getAreaType());
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.toJSONString(null);
		}

		
	}
	
}
