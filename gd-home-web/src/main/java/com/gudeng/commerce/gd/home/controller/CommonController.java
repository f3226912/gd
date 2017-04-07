package com.gudeng.commerce.gd.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;

@Controller
@RequestMapping("common")
public class CommonController extends HomeBaseController {

	@Autowired
	public QueryAreaToolService queryAreaToolService;

	@ResponseBody
	@RequestMapping("queryProvince")
	public String queryProvince() {
		List<AreaDTO> list = null;
		try {
			list = queryAreaToolService.findProvince();
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
			list = queryAreaToolService.findCity(provinceId);
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
			list = queryAreaToolService.findCounty(cityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}
}
