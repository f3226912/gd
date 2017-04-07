package com.gudeng.commerce.gd.api.controller.v2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.CarBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.CarWeighProToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.order.dto.CarBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;

@Controller
@RequestMapping("/v2/carWeighPro")
public class CarWeighProController extends GDAPIBaseController{

	private static Logger logger = LoggerFactory.getLogger(CarWeighProController.class);
	
	@Autowired
	private CarWeighProToolService carWeighProToolService;
	
	@Autowired
	private CarBaseinfoToolService carToolService;
	
	/**
	 * 获取车型默认载重列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("getCarWeighProList")
	@ResponseBody
	public void getCarWeighProList(CarBaseinfoDTO params, HttpServletRequest request, HttpServletResponse response){
		ObjectResult result = new ObjectResult();
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(params.getCarNumber())){
				CarBaseinfoDTO car = carToolService.getByCarNumber(params.getCarNumber());
				if(car != null && car.getCwpId() != null){
					map.put("cwpId", car.getCwpId());
				}
			}
			List<CarWeighProDTO> list = carWeighProToolService.getValidCarWeighProList(map);
			result.setObject(list);
			result.setMsg("success");
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){
			logger.error("获取车型默认载重列表异常", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
}
