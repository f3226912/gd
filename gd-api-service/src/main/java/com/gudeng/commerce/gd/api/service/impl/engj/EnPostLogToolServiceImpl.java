package com.gudeng.commerce.gd.api.service.impl.engj;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.input.ENongPayNotifyDTO;
import com.gudeng.commerce.gd.api.dto.input.ENongRequestDTO;
import com.gudeng.commerce.gd.api.service.engj.EnPostLogToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.UtilDES;
import com.gudeng.commerce.gd.order.service.EnPostLogService;

public class EnPostLogToolServiceImpl implements EnPostLogToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(EnPostLogToolServiceImpl.class);
	
	@Autowired
	public GdProperties gdProperties;
	
	private EnPostLogService enPostLogService;

	private EnPostLogService getHessianEnPostLogService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.enPostLogService.url");
		if (enPostLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			enPostLogService = (EnPostLogService) factory.create(EnPostLogService.class, hessianUrl);
		}
		return enPostLogService;
	}
	
	/**
	 * 写请求内容
	 * @param reqObj 请求对象
	 * @param id 
	 * @return
	 */
	public Long writeRequestInfo(Object reqObj, Long id) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			if(reqObj instanceof ENongRequestDTO){
				ENongRequestDTO requestDTO= (ENongRequestDTO)reqObj;
				StringBuilder builder = new StringBuilder();
				builder.append("请求参数：reqdata=").append(StringUtils.defaultIfBlank(requestDTO.getReqdata(), "")).
							append("&signmsg=").append(StringUtils.defaultIfBlank(requestDTO.getSignmsg(), ""));
				map.put("bz", builder.toString());
				map.put("state", "1");
				return getHessianEnPostLogService().insertEnPostLog(map);
			} else {
				ENongPayNotifyDTO payNotifyDTO = (ENongPayNotifyDTO)reqObj;
				map.put("version", payNotifyDTO.getVersion());
				map.put("charset", payNotifyDTO.getCharset());
				map.put("machinenum", payNotifyDTO.getMachinenum());
				map.put("merchantnum", payNotifyDTO.getMerchantnum());
				map.put("transype", payNotifyDTO.getTransype());
				map.put("orderno", payNotifyDTO.getOrderno());
				map.put("orderfee", MathUtil.div(Double.valueOf(payNotifyDTO.getOrderfee()), 100.0));
				map.put("ratefee", MathUtil.div(Double.valueOf(payNotifyDTO.getRatefee()), 100.0));
				map.put("payfee", MathUtil.div(Double.valueOf(payNotifyDTO.getPayfee()), 100.0));
				map.put("payresultcode", payNotifyDTO.getPayresultcode());
				map.put("payresultmsg", payNotifyDTO.getPayresultmsg());
				map.put("paycardno", UtilDES.decryptDES(payNotifyDTO.getPaycardno()));
				map.put("transdate", payNotifyDTO.getTransdate());
				map.put("transtime", payNotifyDTO.getTranstime());
				map.put("transseqno", payNotifyDTO.getTransseqno());
				map.put("reserved", payNotifyDTO.getReserved());
				map.put("ID", id);
				getHessianEnPostLogService().updateEnPostLog(map);
			}
		}catch(Exception e){
			logger.error("e农写日志异常", e);
		}
		return null;
	}
	
	/**
	 * 写响应内容
	 * @param requestDTO
	 * @param payNotifyDTO
	 * @param message  
	 * @return
	 */
	public void writeResponseInfo(ENongRequestDTO requestDTO, ENongPayNotifyDTO payNotifyDTO, String message) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder builder = new StringBuilder();
			if("OK".equals(message)){
				builder.append("操作成功");
				map.put("state", "2");
			} else {
				builder.append("操作失败：").append(message);
				map.put("state", "3");
			}
			builder.append(";请求参数：reqdata=").append(StringUtils.defaultIfBlank(requestDTO.getReqdata(), "")).
				append("&signmsg=").append(StringUtils.defaultIfBlank(requestDTO.getSignmsg(), ""));
			map.put("bz", builder.toString()); 
			if(payNotifyDTO != null){
				map.put("version", payNotifyDTO.getVersion());
				map.put("charset", payNotifyDTO.getCharset());
				map.put("machinenum", payNotifyDTO.getMachinenum());
				map.put("merchantnum", payNotifyDTO.getMerchantnum());
				map.put("transype", payNotifyDTO.getTransype());
				map.put("orderno", payNotifyDTO.getOrderno());
				map.put("orderfee", MathUtil.div(Double.valueOf(payNotifyDTO.getOrderfee()), 100.0));
				map.put("ratefee", MathUtil.div(Double.valueOf(payNotifyDTO.getRatefee()), 100.0));
				map.put("payfee", MathUtil.div(Double.valueOf(payNotifyDTO.getPayfee()), 100.0));
				map.put("payresultcode", payNotifyDTO.getPayresultcode());
				map.put("payresultmsg", payNotifyDTO.getPayresultmsg());
				map.put("paycardno", payNotifyDTO.getPaycardno());
				map.put("transdate", payNotifyDTO.getTransdate());
				map.put("transtime", payNotifyDTO.getTranstime());
				map.put("transseqno", payNotifyDTO.getTransseqno());
				map.put("reserved", payNotifyDTO.getReserved());
			}
			getHessianEnPostLogService().insertEnPostLog(map);
		}catch(Exception e){
			logger.error("e农写日志异常", e);
		}
	}
}
