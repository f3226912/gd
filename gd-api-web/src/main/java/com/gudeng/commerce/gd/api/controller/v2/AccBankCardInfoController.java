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

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AccBankCardInfoToolService;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.util.BankUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.AccBankCardInfoDTO;
import com.gudeng.commerce.gd.customer.entity.Area;
import com.innovane.win9008.exception.BusinessException;


@Controller
@RequestMapping("/v2/accBankCard")
public class AccBankCardInfoController extends GDAPIBaseController{

	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(AccBankCardInfoController.class);
	@Autowired
	private AccBankCardInfoToolService accBankCardInfoToolService;
	@Autowired
	private AreaToolService areaToolService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getBankCards")
	public void getBankCards(HttpServletRequest request, HttpServletResponse response,AccBankCardInfoDTO accBankCardInfoDTO) {
		ObjectResult result = new ObjectResult();
		try{
			List<AccBankCardInfoDTO> accBankCardInfoDTOs = accBankCardInfoToolService.getBankCards(accBankCardInfoDTO.getMemberId());
			for(int i=0, len=accBankCardInfoDTOs.size(); i<len;i++){
				AccBankCardInfoDTO dto = accBankCardInfoDTOs.get(i);
				String bankName = BankUtil.getNameOfBank(dto.getBankCardNo());
				if(bankName.indexOf("·") > 0){
					dto.setCardType(bankName.substring(0, bankName.indexOf("·")));
				}else{
					dto.setCardType(bankName);
				}
			}
			result.setObject(accBankCardInfoDTOs);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){ 
			logger.error("获取绑定的银行卡信息异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}

	@RequestMapping("/updateBankCard")
	public void updateBankCard(HttpServletRequest request, HttpServletResponse response,AccBankCardInfoDTO accBankCardInfoDTO) {
		ObjectResult result = new ObjectResult();
		try{
			accBankCardInfoToolService.deleteBankCard(accBankCardInfoDTO) ;
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}catch(Exception e){ 
			logger.error("获取绑定的银行卡信息异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	
	
	@RequestMapping("/addBankCard")
	public void addBankCard(HttpServletRequest request, HttpServletResponse response,AccBankCardInfoDTO accBankCardInfoDTO) {
		ObjectResult result = new ObjectResult();
		try{
			String bankCardNo = accBankCardInfoDTO.getBankCardNo();
			String cityName = accBankCardInfoDTO.getCityName();
			if(StringUtils.isBlank(bankCardNo)){
				setResult(result, ErrorCodeEnum.BANKCARD_NO_IS_NULL, request, response);
				return;
			}
			if(StringUtils.isNotBlank(cityName)){
				Area cityArea = areaToolService.getByAreaName(cityName);
				if(cityArea == null){
					setResult(result, ErrorCodeEnum.AREA_NAME_NOT_FOUND, request, response);
					return;
				}
				accBankCardInfoDTO.setCityId(Long.parseLong(cityArea.getAreaID()));
				if(cityArea.getFather() != null){
					accBankCardInfoDTO.setProvinceId(Long.parseLong(cityArea.getFather()));
				}
			}
			
			accBankCardInfoDTO.setBankCardNo(bankCardNo.replace(" ", ""));
			accBankCardInfoToolService.addBankCard(accBankCardInfoDTO) ;
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		}
		catch  (BusinessException e){ 
			logger.error("获取绑定的银行卡信息异常：", e);
			result.setMsg(e.getMessage());
		}
		catch(Exception e){ 
			logger.error("获取绑定的银行卡信息异常：", e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	@RequestMapping("/getBankName")
	public void getBankName(HttpServletRequest request, HttpServletResponse response, String bankCardNo) {
		ObjectResult result = new ObjectResult();
		String bankName = BankUtil.getNameOfBank(bankCardNo);
		if(StringUtils.isBlank(bankName)){
			setResult(result, ErrorCodeEnum.BANK_NAME_NOT_FOUND, request, response);
			return;
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("bankName", bankName);
		result.setObject(map);
		setResult(result, ErrorCodeEnum.SUCCESS, request, response);
	}
}
