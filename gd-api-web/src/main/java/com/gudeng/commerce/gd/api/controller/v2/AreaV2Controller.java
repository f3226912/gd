package com.gudeng.commerce.gd.api.controller.v2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.output.AreaListAppDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;

/**
 * 功能描述：省市区信息控制中心
 * 
 */
@Controller
@RequestMapping("/v2/area")
public class AreaV2Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AreaV2Controller.class);
	@Autowired
	private AreaToolService areaToolService;
	
	@RequestMapping("/getAllProvinceCity")
	public void getAllProvinceCity(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try{
			List<AreaListAppDTO> dataList = areaToolService.getAllProvinceCity();
			result.setObject(dataList);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		}catch(Exception e){
			logger.warn("[ERROR]获取所有省份城市信息异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
