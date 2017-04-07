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
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AccTransInfoToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AccTransInfoDTO;

@Controller
@RequestMapping("/v2/accTrans")
public class AccTransInfoController extends GDAPIBaseController{

	private static Logger logger = LoggerFactory.getLogger(AccBankCardInfoController.class);
	@Autowired
	private AccTransInfoToolService accTransInfoToolService;
	
	
	
	/**
	 * 获取交易记录
	 * @param request
	 * @param response
	 * @param accTransInfoDTO
	 */
	@RequestMapping("/getByMemberId")
	public void getByMemberId(HttpServletRequest request, HttpServletResponse response,AccTransInfoDTO accTransInfoDTO) {
		ObjectResult result = new ObjectResult();
		try{
			List<AccTransInfoDTO> accTransInfoDTOs = accTransInfoToolService.getByMemberIdAndDay(accTransInfoDTO);
			result.setObject(accTransInfoDTOs);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		
		}catch(Exception e){ 
			logger.error("获取交易记录异常", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}

}
