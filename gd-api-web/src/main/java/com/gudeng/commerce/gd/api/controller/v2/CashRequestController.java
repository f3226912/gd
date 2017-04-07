package com.gudeng.commerce.gd.api.controller.v2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.CashRequestToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.order.dto.CashRequestDTO;
import com.innovane.win9008.exception.BusinessException;

@Controller
@RequestMapping("/v2/cashRequest")
public class CashRequestController extends GDAPIBaseController{
	private static Logger logger = LoggerFactory.getLogger(AccBankCardInfoController.class);
	@Autowired
	private CashRequestToolService cashRequestToolService;
	
	@RequestMapping("/getByMemberId")
	public void getByMemberId(HttpServletRequest request, HttpServletResponse response,CashRequestDTO cashRequestDTO) {
		ObjectResult result = new ObjectResult();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", cashRequestDTO.getMemberId());
			CommonPageDTO pageDTO = getPageInfo(request, map);
			List<CashRequestDTO> cashRequestDTOs = cashRequestToolService.getByMemberId(map);
			int total = cashRequestToolService.getTotal(map);
			
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(cashRequestDTOs);
			result.setObject(pageDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){ 
			logger.error("获取提现记录异常", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 *  提现
	 * @param request
	 * @param response
	 * @param cashRequestDTO
	 */
	@RequestMapping("/withdraw")
	public void withdraw(HttpServletRequest request, HttpServletResponse response,CashRequestDTO cashRequestDTO) {
		ObjectResult result = new ObjectResult();
		try{
			cashRequestToolService.withdraw(cashRequestDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}
		catch(BusinessException e){
			logger.error("提现异常", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		catch(Exception e){ 
			logger.error("提现异常", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	} 
}
