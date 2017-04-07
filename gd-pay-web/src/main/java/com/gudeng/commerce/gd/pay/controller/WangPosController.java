package com.gudeng.commerce.gd.pay.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.pay.dto.ResultDTO;
import com.gudeng.commerce.gd.pay.dto.WangPosPayNotifyDTO;
import com.gudeng.commerce.gd.pay.enm.WangPosPayNotifyEnum;
import com.gudeng.commerce.gd.pay.service.WangPosService;
import com.gudeng.commerce.gd.pay.util.GdProperties;
import com.gudeng.commerce.gd.pay.util.StringUtil;

@Controller
@RequestMapping("wangPos")
public class WangPosController extends AdminBaseController
{
	/** 记录日志 */
	private final static Logger logger = LoggerFactory.getLogger(WangPosController.class);
	
	@Autowired
	public WangPosService wangPosService;
	
	@Resource
	private GdProperties gdProperties;
	
	private boolean isSign;
	
	@PostConstruct
	private void init(){
		String sign = gdProperties.getProperties().getProperty("gateway.sign");
		if(StringUtils.equals(sign, "false")){
			isSign = false;
		} else {
			isSign = true;
		}
		
	}
	
	/**
	 * 旺POS 支付通知回调接口，对参数进行加密
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/payNotify")
	@ResponseBody
	public Object payNotify(HttpServletRequest request, HttpServletResponse response) {
		logger.info("来自" + request.getRemoteAddr() + "的请求[isSign="+isSign+"]：" + request.getParameter("param"));
		ResultDTO result = new ResultDTO();
		try{
			String param = request.getParameter("param");
			if(isSign){
				param = getParamDecodeStr(request);
			}
			
			//序列化成 wangPosPayNotifyDTO对象
			WangPosPayNotifyDTO wangPosPayNotifyDTO = gson.fromJson(param, WangPosPayNotifyDTO.class);
			
			if(wangPosPayNotifyDTO.getOrderNo() == null){
				logger.debug("订单编号为空");
				result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_1003.getKey()));
				result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_1003.getValue());
				return result;
			}
			if(StringUtil.isBlank(wangPosPayNotifyDTO.getTradeStatus())){
				logger.debug("交易状态为空");
				result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_2001.getKey()));
				result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_2001.getValue());
				return result;
			}
			if(!WangPosPayNotifyEnum.TRADE_STATUS_PAY.getKey().equals(wangPosPayNotifyDTO.getTradeStatus())){
				logger.debug("交易状态不是已支付状态");
				result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_2004.getKey()));
				result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_2004.getValue());
				return result;
			}
			if(StringUtil.isBlank(wangPosPayNotifyDTO.getCashierTradeNo())){
				logger.debug("收银系统订单号为空");
				result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_2002.getKey()));
				result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_2002.getValue());
				return result;
			}
			if(StringUtil.isBlank(wangPosPayNotifyDTO.getPayType())){
				logger.debug("支付方式为空");
				result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_2005.getKey()));
				result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_2005.getValue());
				return result;
			}
			
			result = wangPosService.execute(wangPosPayNotifyDTO);
			if(result == null || result.getStatusCode() == 0){
				result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_0.getKey()));
				result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_0.getValue());
			}
			logger.debug(result.getMsg());
			return result;
		}catch(Exception e){
			logger.error("旺pos支付通知处理出现异常：", e);
			result.setStatusCode(Integer.parseInt(WangPosPayNotifyEnum.ERROR_CODE_2101.getKey()));
			result.setMsg(WangPosPayNotifyEnum.ERROR_CODE_2101.getValue());
			return result;
		}
	}

}
