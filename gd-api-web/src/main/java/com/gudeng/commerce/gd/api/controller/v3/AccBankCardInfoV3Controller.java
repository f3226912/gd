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
import com.gudeng.commerce.gd.api.service.AccBankCardInfoToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO;
import com.innovane.win9008.exception.BusinessException;

@Controller
@RequestMapping("v3/accBankCard")
public class AccBankCardInfoV3Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AccBankCardInfoV3Controller.class);
	@Autowired
	private AccBankCardInfoToolService accBankCardInfoToolService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/getBankCards")
	public void getBankCards(HttpServletRequest request, HttpServletResponse response,
			AccBankCardInfoDTO accBankCardInfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String sid = request.getParameter("sid");
			List<AccBankCardInfoDTO> accBankCardInfoDTOs = accBankCardInfoToolService
					.getBankCards(getUser(sid).getMemberId());
			result.setObject(accBankCardInfoDTOs);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("获取绑定的银行卡信息异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}

	@RequestMapping("/updateBankCard")
	public void updateBankCard(HttpServletRequest request, HttpServletResponse response,
			AccBankCardInfoDTO accBankCardInfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String sid = request.getParameter("sid");
			Long memberId=getUser(sid).getMemberId();
			accBankCardInfoDTO.setMemberId(memberId);
			accBankCardInfoToolService.deleteBankCard(accBankCardInfoDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("获取绑定的银行卡信息异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}

	@RequestMapping("/addBankCard")
	public void addBankCard(HttpServletRequest request, HttpServletResponse response,
			AccBankCardInfoDTO accBankCardInfoDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String sid = request.getParameter("sid");
			Long memberId=getUser(sid).getMemberId();
			accBankCardInfoDTO.setMemberId(memberId);
			accBankCardInfoToolService.addBankCard(accBankCardInfoDTO);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (BusinessException e) {
			logger.error("获取绑定的银行卡信息异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		} catch (Exception e) {
			logger.error("获取绑定的银行卡信息异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}

}
