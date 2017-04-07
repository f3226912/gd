package com.gudeng.commerce.gd.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.InvoiceInfoToolService;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.order.dto.InvoiceInfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("invoiceInfo")
public class InvoiceInfoController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(InvoiceInfoController.class);

	@Autowired
	private InvoiceInfoToolService invoiceInfoToolService;
	
	/**
	 * 保存信息
	 * @param persaleId
	 * @return
	 */
	@RequestMapping("insertInvoiceInfo")
	@ResponseBody
	public String insertInvoiceInfo(HttpServletRequest request, String orderNo,String invoiceContent) {
		Map<String,String> resultMap = new HashMap<>();
		try {
			Map<String,String> paramMap = new HashMap<>();
			paramMap.put("orderNo", orderNo);
			InvoiceInfoDTO invoiceInfoDTO = invoiceInfoToolService.getBySearch(paramMap);
			if(invoiceInfoDTO != null){
				resultMap.put("res", "error");
				resultMap.put("msg", "保存失败，该订单已存在发票");
				return JSONObject.toJSONString(resultMap,SerializerFeature.WriteDateUseDateFormat);
			} 
			paramMap.clear();
			paramMap.put("content", invoiceContent);
			invoiceInfoDTO = invoiceInfoToolService.getBySearch(paramMap);
			if(invoiceInfoDTO != null){
				resultMap.put("res", "error");
				resultMap.put("msg", "保存失败，发票已存在");
				return JSONObject.toJSONString(resultMap,SerializerFeature.WriteDateUseDateFormat);
			} 
			Map<String,String> map = new HashMap<>();
			map.put("orderNo", orderNo);
			map.put("content", invoiceContent);
			SysRegisterUser user = getUser(request);
			map.put("createUserId", user.getUserID());
			map.put("updateUserId", user.getUserID());
			invoiceInfoToolService.insertInvoiceInfo(map);
			resultMap.put("res", "success");
		} catch (Exception e) {
			logger.trace("保存发票失败,"+e.getMessage(), e);
			resultMap.put("res", "error");
			resultMap.put("msg", "系统错误，请联系管理员");
		}
		return JSONObject.toJSONString(resultMap,SerializerFeature.WriteDateUseDateFormat);
	}

}
