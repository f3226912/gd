package com.gudeng.commerce.gd.m.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.m.controller.act.GDAPIBaseController;
import com.gudeng.commerce.gd.m.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.m.util.Base64;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.m.util.JSONUtils;
import com.gudeng.commerce.gd.m.util.RsaUtil;
import com.gudeng.commerce.gd.m.util.SerializeUtil;

/**
 * 订单支付control
 * 
 * @author xiaojun
 */
@Controller
@RequestMapping("nstFare")
public class OrderPayController extends GDAPIBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderPayController.class);

	@Autowired
	private GdProperties gdProperties;
	
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;


	/**
	 * 组合支付参数 返回url
	 */
	@RequestMapping("signParam")
	@ResponseBody
	public Map<String,Object> signParam(HttpServletRequest request) throws Exception {
		
		Map<String,Object> res = new HashMap<>();
		res.put("success", true);
		res.put("msg", "成功");
		try {
			Map<String, String> paramMap = initPageParam(request);
			LOGGER.info("需要加密的参数:"+paramMap);
			// 获取私钥 并生成密文
			String privateKey = gdProperties.getProperties().getProperty("order.pay.privateKey");
			assertNotEmpty(privateKey,"privateKey没有配置");
			paramMap = paraFilter(paramMap);
			String link = createLinkString(paramMap);
			String sign = RsaUtil.sign(link, privateKey, "UTF-8");
			sign=Base64.encode(sign.getBytes());
			//生成地址 
			String gateWay =  gdProperties.getProperties().getProperty("order.pay.gateWay");
			assertNotEmpty(gateWay,"gateWay没有配置");
			paramMap.put("sign", sign);
			String url = buildUrl(gateWay,paramMap);
			res.put("url", url);
		} catch (RuntimeException e) {
			res.put("success", false);
			res.put("msg", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			res.put("success", false);
			res.put("msg", "系统异常");
		}
		return res;
	}
	
	private String buildUrl(String gateWay,Map<String, String> sParaTemp){
		StringBuffer sb = new StringBuffer();
		sb.append(gateWay);
		//sb.append("_input_charset="+cf.getInput_charset());
		Iterator<Entry<String, String>> it = sParaTemp.entrySet().iterator();
		try {
			int i=1;
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				if(i == 1){
					sb.append("?").append(entry.getKey()).append("=");
				} else {
					sb.append("&").append(entry.getKey()).append("=");
				}
				String value = entry.getValue();//对value进行编码
				sb.append(URLEncoder.encode(value,"utf-8"));
				i++;
			}
		} catch (Exception e) {
			LOGGER.error("组装地址失败",e);
			throw new RuntimeException("组装地址失败");
		}
		String url = sb.toString();
		return url;
	}

	
	/**
	 * 初始化支付页面参数转map
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> initPageParam(HttpServletRequest request) {

		String orderNo = request.getParameter("orderNo");
		String orderTime = request.getParameter("orderTime");
		String payerUserId = request.getParameter("payerUserId");
		String payeeUserId = request.getParameter("payeeUserId");
		String totalAmt =request.getParameter("totalAmt");
		String payAmt = request.getParameter("payAmt");
		String sourceId = request.getParameter("sourceId");
		assertNotEmpty(sourceId,"sourceId不能为空");
		assertNotEmpty(orderNo,"orderNo不能为空");
		assertNotEmpty(payerUserId,"payerUserId不能为空");
		assertNotEmpty(payeeUserId,"payeeUserId不能为空");
		assertNotEmpty(totalAmt,"totalAmt不能为空");
		assertNotEmpty(payAmt,"payAmt不能为空");
		String appKey = gdProperties.getProperties().getProperty("order.pay.appKey");
		
		String version = gdProperties.getProperties().getProperty("order.pay.version");
		String title = gdProperties.getProperties().getProperty("order.pay.title");
		String timeOut = gdProperties.getProperties().getProperty("order.pay.timeOut");
		String returnUrl = gdProperties.getProperties().getProperty("order.pay.returnUrl");
		//需要拼接memberId
		returnUrl = returnUrl + "&memberId="+payerUserId;
		String requestIp = getIpAddress(request);
		assertNotEmpty(appKey,"appKey没有配置");

		assertNotEmpty(version,"version没有配置");
		assertNotEmpty(title,"title没有配置");
		
		assertNotEmpty(timeOut,"timeOut没有配置");
		assertNotEmpty(returnUrl,"returnUrl没有配置");
		MemberBaseinfoDTO payer = getMember(payerUserId);
		String payerAccount = payer.getAccount();
		String payerName = payer.getRealName();
		String payerMobile = payer.getMobile();
		MemberBaseinfoDTO payee = getMember(payeeUserId);
		String payeeAccount = payee.getAccount();
		String payeeName = payee.getRealName();
		String payeeMobile = payee.getMobile();
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("orderNo", orderNo);
		paramMap.put("orderTime", orderTime);
		paramMap.put("payerUserId", payerUserId);
		paramMap.put("payeeUserId", payeeUserId);
		paramMap.put("totalAmt", totalAmt);
		paramMap.put("payAmt", payAmt);
		paramMap.put("appKey", appKey);
		paramMap.put("version", version);
		paramMap.put("title", title);
		paramMap.put("timeOut", timeOut);
		paramMap.put("returnUrl", returnUrl);
		paramMap.put("requestIp", requestIp);
		paramMap.put("payerAccount", payerAccount);
		paramMap.put("payerName", payerName);
		paramMap.put("payerMobile", payerMobile);
		paramMap.put("payeeAccount", payeeAccount);
		paramMap.put("payeeName", payeeName);
		paramMap.put("payeeMobile", payeeMobile);
		
		//reParam;
		// 获取公用回传参数，封装json中
		Map<String, Object> map = new HashMap<>();
		map.put("orderType", "2");
		// 如果是信息订单则是信息id ,货运订单就是sourceId
		map.put("sourceId", sourceId);
		// 传入司机memberId
		map.put("driverMemberId", payeeUserId);
		// 传入货主memberId
		map.put("shipperMemberId", payerUserId);
		String reParamJsonStr = JSONUtils.toJSONString(map);
		// 将回调参数以base64进行传输
		String reParam = Base64.encode(SerializeUtil.serialize(reParamJsonStr));
		paramMap.put("reParam", reParam);
		return paramMap;
	}
	
	private MemberBaseinfoDTO getMember(String memberId){
		//获取会员信息
		try {
			MemberBaseinfoDTO payer = memberBaseinfoToolService.getById(memberId);
			if(payer == null){
				throw new RuntimeException("会员ID["+memberId+"]不存在");
			}
			return payer;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new RuntimeException("获取会员失败");
		}
	}
	
	private void assertNotEmpty(String obj,String msg){
		if(StringUtils.isEmpty(obj)){
			throw new RuntimeException(msg);
		}
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private Map<String, String> paraFilter(Map<String, String> sArray) {
		Map<String, String> result = new HashMap<String, String>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("singType")) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}
	
	private String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

}
