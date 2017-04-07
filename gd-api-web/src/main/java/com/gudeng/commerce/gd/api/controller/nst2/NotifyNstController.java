package com.gudeng.commerce.gd.api.controller.nst2;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.input.NstBaseResponseDTO;
import com.gudeng.commerce.gd.api.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.api.service.DeliveryAddressToolService;
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.service.v160512.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.api.util.HttpClients;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.input.CanTakeOrderInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.NstOrderStatus;
import com.gudeng.commerce.gd.api.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.NstOrderNoToolService;
import com.gudeng.commerce.gd.api.service.PushNstMessageApiService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderBaseinfoEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import static com.gudeng.paltform.sms.lxt.LxtConfig.url;

/**
 * 订单支付预付款成功，支付尾款成功，通知农速通
 *
 * @author  
 */
@Controller
@RequestMapping("v1/pay")
public class NotifyNstController extends GDAPIBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(NotifyNstController.class);
	@Autowired
	private GdProperties gdProperties;

	@Autowired
	private NstApiCommonService nstApiCommonService;
	/**
	 *
	 *支付预付款成功，通知农速通
	 *
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/payPrePaymenSucc")
	public void payPrePaymenSucc(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String url = gdProperties.getNstApiUrl() + NstApiRequestV1Enum.PAY_PREPAY_SUCC.getUri();
			Map<String, String> requestParamMap = GSONUtils.fromJsonToMapStr(jsonStr);
			String memberId = (String)GSONUtils.getJsonValueStr(jsonStr, "memberId");
			requestParamMap.put("token", nstApiCommonService.getNstToken(memberId));
			NstBaseResponseDTO dto=nstApiCommonService.sendNstRequest(requestParamMap, url);
			System.out.println(dto.getMsg());
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 *
	 *支付尾款成功，通知农速通
	 *
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/payRestPaymenSucc")
	public void payRestPaymenSucc(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String url = gdProperties.getNstApiUrl() + NstApiRequestV1Enum.PAY_RESTPAY_SUCC.getUri();
			Map<String, String> requestParamMap = GSONUtils.fromJsonToMapStr(jsonStr);
			String memberId = (String)GSONUtils.getJsonValueStr(jsonStr, "memberId");
			requestParamMap.put("token", nstApiCommonService.getNstToken(memberId));
			NstBaseResponseDTO dto=nstApiCommonService.sendNstRequest(requestParamMap, url);
			System.out.println(dto.getMsg());
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
}
