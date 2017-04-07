package com.gudeng.commerce.gd.api.controller.v3;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;

@Controller
@RequestMapping("v3/paySerialNumber")
public class PaySerialNumberV3Controller extends GDAPIBaseController{

	private static Logger logger = LoggerFactory.getLogger(PaySerialNumberV3Controller.class);
	@Autowired
	private PaySerialnumberToolService paySerialnumberToolService;
	
	
	
	/**
	 * 获取交易记录
	 * @param request
	 * @param response
	 * @param accTransInfoDTO
	 */
	@RequestMapping("/getByMemberIdAndDay")
	public void getByMemberId(HttpServletRequest request, HttpServletResponse response,PaySerialnumberDTO paySerialnumberDTO) {
		ObjectResult result = new ObjectResult();
		try{
			List<PaySerialnumberDTO> paySerialnumberDTOs = paySerialnumberToolService.getByMemberIdAndDay(paySerialnumberDTO);
			result.setObject(paySerialnumberDTOs);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		
		}catch(Exception e){ 
			logger.error("获取交易记录异常", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}

}
