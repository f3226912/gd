package com.gudeng.commerce.gd.api.controller.v170209;

import java.util.ArrayList;
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
import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.output.OrderProductDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.order.OrderActivityToolService;
import com.gudeng.commerce.gd.api.service.order.OrderTool3Service;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;

@Controller
@RequestMapping("/v170209/order")
public class OrderAppController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderAppController.class);
	@Autowired
	private OrderActivityToolService orderActivityToolService;
	@Autowired
	private OrderTool3Service<?> orderTool3Service;
	@Autowired
	public GdProperties gdProperties;
	/**
	 * 现场采销 订单查看功能
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/detail")
	public void detailOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			Long orderNo = inputDTO.getOrderNo();
			Long memberId = inputDTO.getMemberId();

			if(orderNo == null){
				setEncodeResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}

			OrderBaseinfoDTO orderDto=orderTool3Service.getproductDetailListByOrderNo(orderNo); //根据订单号获取订单详情
			if(orderDto == null){
				setEncodeResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
				return;
			}
			if (memberId.toString().equals(orderDto.getSellMemberId().toString())) {
				setEncodeResult(result, ErrorCodeEnum.ORDER_BUYER_EQUAL_SELLER, request, response);
				return;
			}
			if (!orderDto.getActivityType().equals("2")) { //只要活动类型为 2 现场采销的才能进行详情查看
				setEncodeResult(result, ErrorCodeEnum.PRO_ORDER_NOTActivityType, request, response);
				return;
			}
			if (!orderDto.getOrderStatus().equals("13")) {
				setEncodeResult(result, ErrorCodeEnum.PRO_CODE_OLD, request, response);
				return;
			}
			if (null==orderDto.getMarketId()) { //如果订单找不到市场，则不能进行积分计算 
				setEncodeResult(result, ErrorCodeEnum.MARKET_ID_NOT_FOUND, request, response);
				return;
			}
			
			Map<String, Object> paraMap=new HashMap<>();
			//必须的三个参数，订单No 买家id 市场id
			paraMap.put("orderNo", orderNo); 
			paraMap.put("memberId", memberId);
			//paraMap.put("marketId", orderDto.getMarketId());  //市场现在只有 5028 武汉白沙洲市场 要么写死，要么不写，后期需要再维护

			Integer activityIntegral =orderActivityToolService.queryActivityIntegralRate(paraMap); //获取活动积分
			orderDto.setActivityIntegral(activityIntegral); //设置活动积分 -2 提示积分达到上线
			/*******************为了适应之前的app详情接口，故进行转换**********************************/
			OrderDetailAppDTO orderDetailReturn = new OrderDetailAppDTO();
			orderDetailReturn.setOrderNo(orderDto.getOrderNo());
			orderDetailReturn.setActivityType(orderDto.getActivityType());//活动类型1无活动2现场采销
			orderDetailReturn.setActivityIntegral(orderDto.getActivityIntegral());//活动获取积分
			orderDetailReturn.setMarketId(orderDto.getMarketId());
			orderDetailReturn.setMemberId(orderDto.getMemberId());
			orderDetailReturn.setBusinessId(orderDto.getBusinessId());
			orderDetailReturn.setShopName(orderDto.getShopName());
			orderDetailReturn.setMobile(orderDto.getSellMobile());
			orderDetailReturn.setMessage(orderDto.getMessage());
			orderDetailReturn.setActivityIntegral(orderDto.getActivityIntegral());
			orderDetailReturn.setPayAmount(orderDto.getPayAmount());
			orderDetailReturn.setOrderAmount(orderDto.getOrderAmount());
			List<OrderProductDTO> productDetailList = new ArrayList<>(); //订单详情拼装
		 	String imageHost = gdProperties.getProperties().getProperty("gd.image.server"); //图片展示
			if (null != orderDto.getDetailList() && orderDto.getDetailList().size() > 0) {
				for (int j = 0, pLen = orderDto.getDetailList().size(); j < pLen; j++) {
					OrderProductDetailDTO pDto = orderDto.getDetailList().get(j);
					if (orderNo.equals(pDto.getOrderNo())) {
						OrderProductDTO productDTO = setOrderProductInfo(pDto, imageHost);
						Integer status = pDto.getHasDelivered();
					/*	Integer deliveredStatus = ProductDeliverStatusEnum.ALREADY_DELIVERED.getkey();
						Integer deliveringStatus = ProductDeliverStatusEnum.IS_DELIVERING.getkey();*/
						productDTO.setStatus(status);
						productDetailList.add(productDTO);
					}
				}
			}
			orderDetailReturn.setProductDetails(productDetailList);
			/*******************************************************************************/
			result.setObject(orderDetailReturn);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);

		} catch (Exception e) {
			logger.warn("[ERROR]获取用户订单详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	private OrderProductDTO setOrderProductInfo(OrderProductDetailDTO pDto, String imageHost) {
		OrderProductDTO productDTO = new OrderProductDTO();
		productDTO.setOrderNo(pDto.getOrderNo());
		productDTO.setFormattedPrice(MoneyUtil.formatMoney(pDto.getPrice()));
		productDTO.setHasBuySub(pDto.getHasBuySub());
		productDTO.setHasSellSub(pDto.getHasSellSub());
		productDTO.setImageUrl(imageHost + pDto.getImageUrl());
		productDTO.setNeedToPayAmount(pDto.getSubTotal()); //小计
		productDTO.setPrice(pDto.getPrice());
		productDTO.setProductId(pDto.getProductId());
		productDTO.setProductName(pDto.getProductName());
		productDTO.setPurQuantity(pDto.getPurQuantity());
		productDTO.setTradingPrice(pDto.getTradingPrice());
		productDTO.setUnitName(pDto.getUnitName());
		
		return productDTO;
	}
	/**
	 * 确认订单接口
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/conirmOrder")
	public void conirmOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			Long orderNo = inputDTO.getOrderNo();
			Long memberId = inputDTO.getMemberId();

			if(orderNo == null){
				setEncodeResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
			if(memberId == null){
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ID_IS_NULL, request, response);
				return;
			}
			OrderBaseinfoDTO orderDto=orderTool3Service.getproductDetailListByOrderNo(orderNo); //根据订单号获取订单详情
			if (null==orderDto||!orderDto.getActivityType().equals("2")) { //只要活动类型为 2 现场采销的才能进行详情查看
				setEncodeResult(result, ErrorCodeEnum.PRO_ORDER_NOTActivityType, request, response);
				return;
			}
			if (!orderDto.getOrderStatus().equals("13")) {
				setEncodeResult(result, ErrorCodeEnum.PRO_ORDER_ISCONFIRM, request, response);
				return;
			}
			OrderBaseinfoDTO orderbase=new OrderBaseinfoDTO();
			Integer memberId1=Integer.valueOf(memberId.toString()); 
			orderbase.setMemberId(memberId1);
			orderbase.setOrderNo(orderNo);
			orderbase.setOrderStatus("1");
			
			/******获取活动积分 begin************/
			Map<String, Object> paraMap=new HashMap<>();
			//必须的三个参数，订单No 买家id 市场id
			paraMap.put("orderNo", orderNo); 
			paraMap.put("memberId", memberId);
			//paraMap.put("marketId", orderDto.getMarketId());  //市场现在只有 5028 武汉白沙洲市场 要么写死，要么不写，后期需要再维护

			Integer activityIntegral =orderActivityToolService.queryActivityIntegralRate(paraMap); //获取活动积分
			if (activityIntegral<0) {
				activityIntegral=0;
			}
			/******获取活动积分 end************/
			orderbase.setActivityIntegral(activityIntegral); //修改活动积分
			
			int orderN=orderTool3Service.updateByOrderNo(orderbase);
			if(orderN>0){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
			}

		} catch (Exception e) {
			logger.warn("[ERROR]更新订单信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	/**
	 * 支付完成调用订单此接口
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/orderPayfinsh")
	public void orderPayfinsh(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			
			String OrderNo= GSONUtils.getJsonValueStr(jsonStr, "orderNo");
			orderActivityToolService.IntegralRateForPayFinish(Long.valueOf(OrderNo));
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]更新订单信息异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
