package com.gudeng.commerce.gd.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.DeliveryAddressToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.entity.DeliveryAddress;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 订单收货地址管理
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年8月23日 下午3:15:54
 */
@Controller
public class DeliveryAddressController extends GDAPIBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(DeliveryAddressController.class);

	@Autowired
	private DeliveryAddressToolService deliveryAddressToolService;
	@Autowired
	private IGDBinaryRedisClient redisClient;
	private final static String TOKEN = "DeliveryAddressController";
	private final static int EXPIRE = 60 * 10;// 600秒

	/**
	 * 进入添加/编辑 地址页面,产生随机token，防止重复提交数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "deliveryAddress/token" }, method = { RequestMethod.POST })
	public void token(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String orderNo = GSONUtils.getJsonValueStr(jsonStr, "orderNo");
			if (StringUtils.isEmpty(orderNo)) {
				setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "订单号不可为空", request, response);
				return;
			}
			String token = UUID.randomUUID().toString();
			redisClient.setex(TOKEN + orderNo, EXPIRE, token);
			result.setObject(token);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "", request, response);
		} catch (Exception e) {
			logger.trace("获取token错误，加载异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "加载异常", request, response);
		}
	}

	/**
	 * 订单收货地址列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "deliveryAddress/query" })
	public void query(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String orderNo = GSONUtils.getJsonValueStr(jsonStr, "orderNo");
			map.put("orderNo", orderNo);
			map.put("status", 1);
			List<DeliveryAddressDTO> list = deliveryAddressToolService.getList(map);
			if (list != null && list.size() > 0) {
				result.setObject(list.get(0));
			}
			setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "", request, response);
		} catch (Exception e) {
			logger.trace("获取订单收货地址异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "获取订单收货地址异常", request, response);
		}
	}

	/**
	 * 保存订单收货地址
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "deliveryAddress/save" }, method = { RequestMethod.POST })
	public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String orderNo = GSONUtils.getJsonValueStr(jsonStr, "orderNo");
			String token = GSONUtils.getJsonValueStr(jsonStr, "token");
			// 验证token，防止重复提交
			if (token == null) {
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "页面超时,请重新进入页面", request, response);
				return;
			} else if (!token.equals(redisClient.get(TOKEN + orderNo))) {
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "页面超时,请重新进入页面", request, response);
				return;
			}
			redisClient.del(TOKEN + orderNo);// 清除token，防止重复提交
			// 新增
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			DeliveryAddress entity = (DeliveryAddress) GSONUtils.fromJsonToObject(jsonStr, DeliveryAddress.class);
			entity.setStatus("1");
			if (StringUtils.isEmpty(entity.getGender())) {
				entity.setGender("0");
			}
			entity.setCreateUser(Long.valueOf(memberId));
			entity.setCreateTime(new Date());
			deliveryAddressToolService.insert(entity);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "添加订单收货地址成功", request, response);
		} catch (Exception e) {
			logger.trace("添加订单收货地址异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "添加订单收货地址异常", request, response);
		}
	}

}
