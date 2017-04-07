package com.gudeng.commerce.gd.api.controller.v161027;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.input.MemberInfoInputDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;

@Controller
@RequestMapping("/v32/order")
public class OrderAppV32Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderAppV32Controller.class);
	@Autowired
	private OrderTool2Service orderTool2Service;
	
	@RequestMapping("/buyGoldMedal")
	public void buyGoldMedal(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			MemberInfoInputDTO inputDTO = (MemberInfoInputDTO) GSONUtils.fromJsonToObject(jsonStr, MemberInfoInputDTO.class);
			StatusCodeEnumWithInfo addResult = orderTool2Service.buyGoldMedal(inputDTO, request);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				result.setObject(addResult.getObj());
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, addResult.getStatusCodeEnum(), request, response);
			}
		} catch (Exception e) {
			logger.error("[ERROR]购买金牌供应商异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
