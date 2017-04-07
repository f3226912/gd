package com.gudeng.commerce.gd.api.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.util.Base64;
import com.gudeng.commerce.gd.api.util.Des3Request;
import com.gudeng.commerce.gd.api.util.Des3Response;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.pay.RsaUtil;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;

/**
 * NSY预付款加密接口
 * 
 * @author sss
 *
 *         since:2016年12月8日 version 1.0.0
 */
@Controller
@RequestMapping("nsyAlipay")
public class NsyOrderPayController extends GDAPIBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderPayController.class);

	@Autowired
	private GdProperties gdProperties;

	@Autowired
	private MemberToolService memberToolService;

	/**
	 * 组合支付参数 返回url
	 */
	@RequestMapping("signParam")
	public void signParam(HttpServletRequest request,HttpServletResponse response) throws Exception {

		Map<String, Object> res = new HashMap<>();
		res.put("success", true);
		res.put("msg", "成功");
		try {
			Map<String, String> paramMap = initPageParam(request);
			LOGGER.info("需要加密的参数:" + paramMap);
			// 获取私钥 并生成密文
			String privateKey = gdProperties.getProperties().getProperty("nsy.order.pay.privateKey");
			assertNotEmpty(privateKey, "privateKey没有配置");
			paramMap = paraFilter(paramMap);
			String link = createLinkString(paramMap);
			String sign = RsaUtil.sign(link, privateKey, "UTF-8");
			sign = Base64.encode(sign.getBytes());
			// 生成地址
			String gateWay = gdProperties.getProperties().getProperty("nsy.order.pay.gateWay");
			assertNotEmpty(gateWay, "gateWay没有配置");
			paramMap.put("sign", sign);
			String url = buildUrl(gateWay, paramMap);
			res.put("url", url);
		} catch (RuntimeException e) {
			res.put("success", false);
			res.put("msg", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", "系统异常");
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().write(Des3Response.encode(JSON.toJSONString(res)));
	}

	private String buildUrl(String gateWay, Map<String, String> sParaTemp) {
		StringBuffer sb = new StringBuffer();
		sb.append(gateWay);
		// sb.append("_input_charset="+cf.getInput_charset());
		Iterator<Entry<String, String>> it = sParaTemp.entrySet().iterator();
		try {
			int i = 1;
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				if (i == 1) {
					sb.append("?").append(entry.getKey()).append("=");
				} else {
					sb.append("&").append(entry.getKey()).append("=");
				}
				String value = entry.getValue();// 对value进行编码
				sb.append(URLEncoder.encode(value, "utf-8"));
				i++;
			}
		} catch (Exception e) {
			LOGGER.error("组装地址失败", e);
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
	 * @throws Exception 
	 */
	private Map<String, String> initPageParam(HttpServletRequest request) throws Exception {
		
		String param = Des3Request.decode(request.getParameter("param"));
		
		String orderInfos = JSON.parseObject(param).getString("orderInfos");
		LOGGER.info("农商友预付款加密入参:" + orderInfos);
		// 转成orderInfos
		List<OrderInfo> os = null;
		BigDecimal sumPayAmt = new BigDecimal(0);
		try {
			os = JSONUtils.parseArray(orderInfos, OrderInfo.class);
			for (OrderInfo order : os) {
				assertNotEmpty(order.getOrderNo(), "orderNo不能为空");
				assertNotEmpty(order.getPayeeUserId(), "payerUserId不能为空");
				assertNotEmpty(order.getPayerUserId(), "payeeUserId不能为空");
			
				assertNotEmpty(order.getTotalAmt(), "totalAmt不能为空");
				assertNotEmpty(order.getPayAmt(), "payAmt不能为空");
				validateDecialmal(order.getPayAmt(),2,"交易金额不能超过两位小数");
				String title = gdProperties.getProperties().getProperty("nsy.order.pay.title");
				String requestNo = gdProperties.getProperties().getProperty("nsy.order.pay.requestNo");
				String payCount = gdProperties.getProperties().getProperty("nsy.order.pay.payCount");
				order.setTitle(title);
				order.setRequestNo(requestNo);
				order.setPayCount(payCount);
				sumPayAmt = sumPayAmt.add(new BigDecimal(order.getPayAmt()));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException("orderinfos格式错误");
		}

		String appKey = gdProperties.getProperties().getProperty("nsy.order.pay.appKey");
		String version = gdProperties.getProperties().getProperty("nsy.order.pay.version");
		String timeOut = gdProperties.getProperties().getProperty("nsy.order.pay.timeOut");
		String returnUrl = gdProperties.getProperties().getProperty("nsy.order.pay.returnUrl");
		String requestIp = getIpAddress(request);

		assertNotEmpty(appKey, "appKey没有配置");
		assertNotEmpty(version, "version没有配置");
		assertNotEmpty(timeOut, "timeOut没有配置");
		assertNotEmpty(returnUrl, "returnUrl没有配置");

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("appKey", appKey);
		paramMap.put("version", version);
		paramMap.put("timeOut", timeOut);
		paramMap.put("returnUrl", returnUrl);
		paramMap.put("requestIp", requestIp);

		DecimalFormat df = new DecimalFormat("#0.00");
		paramMap.put("sumPayAmt", df.format(sumPayAmt.doubleValue()));
		if (os.size() == 1) {
			OrderInfo order = os.get(0);

			MemberBaseinfoDTO payer = getMember(order.getPayerUserId());
			String payerAccount = payer.getAccount();
			String payerName = payer.getRealName();
			String payerMobile = payer.getMobile();
			MemberBaseinfoDTO payee = getMember(order.getPayeeUserId());
			String payeeAccount = payee.getAccount();
			String payeeName = payee.getRealName();
			String payeeMobile = payee.getMobile();

			paramMap.put("orderNo", order.getOrderNo());
			paramMap.put("orderTime", order.getOrderTime());
			paramMap.put("payerUserId", order.getPayerUserId());
			paramMap.put("payeeUserId", order.getPayeeUserId());
			paramMap.put("logisticsUserId", order.getLogisticsUserId());
			paramMap.put("totalAmt", order.getTotalAmt());
			paramMap.put("payAmt", order.getPayAmt());
			paramMap.put("title", order.getTitle());
			paramMap.put("payerAccount", payerAccount);
			paramMap.put("payerName", payerName);

			paramMap.put("payerMobile", payerMobile);
			paramMap.put("payeeAccount", payeeAccount);
			paramMap.put("payeeName", payeeName);
			paramMap.put("payeeMobile", payeeMobile);
			paramMap.put("payCount", order.getPayCount());
			paramMap.put("requestNo", order.getRequestNo());
			return paramMap;
		} else {

			paramMap.put("orderInfos", JSONUtils.toJSONString(os));
			return paramMap;
		}

	}

	private void validateDecialmal(String str, int length, String msg) {
		if (StringUtils.isEmpty(str)) {
			return;
		}
		int ind = str.indexOf(".");
		if (ind > -1) {
			if (str.substring(ind + 1).length() > length) {
				throw new RuntimeException(msg);
			}
		}
	}

	private MemberBaseinfoDTO getMember(String memberId) {
		// 获取会员信息
		try {
			MemberBaseinfoDTO payer = memberToolService.getById(memberId);
			if (payer == null) {
				throw new RuntimeException("会员ID[" + memberId + "]不存在");
			}
			return payer;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException("获取会员失败");
		}
	}

	private void assertNotEmpty(String obj, String msg) {
		if (StringUtils.isEmpty(obj)) {
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
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("singType")) {
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

	public static class OrderInfo {

		private String orderNo;

		private String payerUserId;

		private String payeeUserId;

		private String logisticsUserId;// 物流公司会员ID
		private String totalAmt;
		private String payAmt;
		private String title;

		private String requestNo;

		private String payCount;

		private String reParam;

		private String orderTime;

		public String getOrderTime() {
			return orderTime;
		}

		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getPayerUserId() {
			return payerUserId;
		}

		public void setPayerUserId(String payerUserId) {
			this.payerUserId = payerUserId;
		}

		public String getPayeeUserId() {
			return payeeUserId;
		}

		public void setPayeeUserId(String payeeUserId) {
			this.payeeUserId = payeeUserId;
		}

		public String getTotalAmt() {
			return totalAmt;
		}

		public void setTotalAmt(String totalAmt) {
			this.totalAmt = totalAmt;
		}

		public String getPayAmt() {
			return payAmt;
		}

		public void setPayAmt(String payAmt) {
			this.payAmt = payAmt;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getRequestNo() {
			return requestNo;
		}

		public void setRequestNo(String requestNo) {
			this.requestNo = requestNo;
		}

		public String getPayCount() {
			return payCount;
		}

		public void setPayCount(String payCount) {
			this.payCount = payCount;
		}

		public String getReParam() {
			return reParam;
		}

		public void setReParam(String reParam) {
			this.reParam = reParam;
		}

		public String getLogisticsUserId() {
			return logisticsUserId;
		}

		public void setLogisticsUserId(String logisticsUserId) {
			this.logisticsUserId = logisticsUserId;
		}

	}

}
