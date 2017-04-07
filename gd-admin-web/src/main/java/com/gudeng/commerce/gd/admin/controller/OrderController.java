package com.gudeng.commerce.gd.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.gudeng.commerce.gd.admin.dto.NstLogisticalProgressDTO;
import com.gudeng.commerce.gd.admin.dto.NstProgressLog;
import com.gudeng.commerce.gd.admin.dto.NstResult;
import com.gudeng.commerce.gd.admin.service.ActActivityBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.AuditLogToolService;
import com.gudeng.commerce.gd.admin.service.GdActActivityToolService;
import com.gudeng.commerce.gd.admin.service.GdOrderActivityBaseToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.OrderActRelationToolService;
import com.gudeng.commerce.gd.admin.service.OrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.OrderDeliveryAddressToolService;
import com.gudeng.commerce.gd.admin.service.OrderFeeItemDetailToolService;
import com.gudeng.commerce.gd.admin.service.OrderProductDetailToolService;
import com.gudeng.commerce.gd.admin.service.OrderRefundRecordToolService;
import com.gudeng.commerce.gd.admin.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.admin.service.PreSaleDetailToolService;
import com.gudeng.commerce.gd.admin.service.PreSaleToolService;
import com.gudeng.commerce.gd.admin.util.Base64;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.Des3;
import com.gudeng.commerce.gd.admin.util.GSONUtils;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.HttpClients;
import com.gudeng.commerce.gd.admin.util.MathUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.exception.RefundException;
import com.gudeng.commerce.gd.order.dto.AuditLogDTO;
import com.gudeng.commerce.gd.order.dto.ClearingLogDTO;
import com.gudeng.commerce.gd.order.dto.MsgCons;
import com.gudeng.commerce.gd.order.dto.OrderActRelationDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderClearDetail;
import com.gudeng.commerce.gd.order.dto.OrderDeliveryAddressDTO;
import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderRefundRecordDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.RefundResponseDTO;
import com.gudeng.commerce.gd.order.dto.RefundResponseLogResult;
import com.gudeng.commerce.gd.order.enm.EOrderCloseReason;
import com.gudeng.commerce.gd.order.enm.EOrderFeeType;
import com.gudeng.commerce.gd.order.enm.EOrderFeeUserType;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.enm.EOrderStatusWord;
import com.gudeng.commerce.gd.order.enm.EOrderType;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;
import com.gudeng.commerce.gd.order.util.AccessSysSignUtil;
import com.gudeng.commerce.gd.order.util.RsaUtil;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderPenaltyQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@Controller
@RequestMapping("order")
public class OrderController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(OrderController.class);
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	public OrderBaseinfoToolService orderBaseinfoToolService;
	@Autowired
	public OrderProductDetailToolService orderProductDetailToolService;
	@Autowired
	public PaySerialnumberToolService paySerialnumberToolService;
	@Autowired
	public PreSaleDetailToolService preSaleDetailToolService;
	@Autowired
	public PreSaleToolService preSaleToolService;
	@Autowired
	public AuditLogToolService auditLogToolService;
	@Autowired
	public MarketManageService marketService;
	@Autowired
	private OrderDeliveryAddressToolService orderDeliveryAddressToolService;
	@Autowired
	private OrderFeeItemDetailToolService orderFeeItemDetailToolService;
	@Autowired
	private GdOrderActivityBaseToolService gdOrderActivityBaseToolService;

	@Autowired
	private OrderActRelationToolService orderActRelationToolService;

	@Resource
	private ActActivityBaseinfoToolService actActivityBaseinfoToolService;// 活动明细
	private static final Integer CALLBACK_SUCCESS = 10000;

	/**
	 * 退款服务
	 */
	@Autowired
	private OrderRefundRecordToolService orderRefundRecordToolService;

	@Autowired
	private GdActActivityToolService gdActActivityToolService;

	/**
	 * 订单列表页面
	 * 
	 * @return
	 */
	@RequestMapping("orderList/{type}")
	public String orderList(@PathVariable("type") String type, HttpServletRequest request) {
		putModel("type", type);
		try {
			// putModel("allMarket1", marketService.getAllByType("1"));
			putModel("allMarket2", marketService.getAllByType("2"));
			// putModel("allMarket3", marketService.getAllByType("3"));

		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "order/order_list";
	}

	/**
	 * 查询买家违约金
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getRefund/{orderNo}")
	public ModelAndView getRefund(HttpServletRequest request, @PathVariable("orderNo") String orderNo) {
		ModelAndView mv = new ModelAndView();
		GdOrderPenaltyQueryDTO penalty = new GdOrderPenaltyQueryDTO();
		try {
			initQueryObject(penalty, orderNo);
			penalty = gdOrderActivityBaseToolService.queryOrderPenalty(penalty);
			mv.addObject("penalty", penalty);
		} catch (Exception e) {
			mv.addObject("msg", "查询买家预付款违约金接口异常");
			// ----------------------test-------------------------------------
			// penalty.setPrepayAmt(0.01D);
			// penalty.setSellerPenalty(0D);
			// penalty.setCompanyPenalty(0D);
			// penalty.setPlatPenalty(0D);
			// penalty.setOrderNo("412016000216543");
			// request.setAttribute("statementId", "2016121254960676");
			mv.addObject("penalty", penalty);

			// ----------------------test-------------------------------------
			logger.info("getRefund with ex :" + orderNo, e);
		}
		mv.setViewName("order/order_refund");
		return mv;

	}

	/**
	 * 初始化查预付款违约金参数对象
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void initQueryObject(GdOrderPenaltyQueryDTO queryDTO, String orderNo) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo", orderNo);
		List<OrderBaseinfoDTO> orderList = orderBaseinfoToolService.getListByCondition(paramMap);
		paramMap.put("payType", EPayType.ALIPAY.getCode());
		List<PaySerialnumberDTO> payList = paySerialnumberToolService.getListByCondition(paramMap);
		if (CollectionUtils.isNotEmpty(payList)) {
			putModel("statementId", payList.get(0).getStatementId());
		}
		OrderBaseinfoDTO orderInfo = null;
		if (CollectionUtils.isNotEmpty(orderList)) {
			orderInfo = orderList.get(0);
			queryDTO.setOrderNo(orderNo);
			queryDTO.setOrderAmount(orderInfo.getOrderAmount());
			queryDTO.setPayAmount(orderInfo.getPayAmount());
			queryDTO.setMarketId(orderInfo.getMarketId());
			queryDTO.setBusinessId(orderInfo.getBusinessId());
			queryDTO.setSellerId(orderInfo.getSellMemberId());
			queryDTO.setBuyerId(orderInfo.getMemberId());
			// queryDTO.setPrepayAmt(orderInfo.getPrepayAmt());

			List<OrderProductDetailDTO> productList = orderProductDetailToolService.getListByOrderNo(paramMap);
			// 查找订单活动信息
			List<OrderActRelationDTO> productRefActIdList = orderActRelationToolService
					.getByOrderNo(Long.parseLong(orderNo));

			if (productList != null && productList.size() > 0) {
				if (productRefActIdList != null && productRefActIdList.size() > 0) {
					List<GdProductActInfoDTO> pIdList = new ArrayList<>();
					queryDTO.setProductList(pIdList);
					for (OrderProductDetailDTO dto : productList) {
						GdProductActInfoDTO pDTO = new GdProductActInfoDTO();
						pDTO.setPrice(dto.getPrice());
						pDTO.setProductId(dto.getProductId());
						pDTO.setProductAmount(dto.getNeedToPayAmount());
						pDTO.setQuantity(dto.getPurQuantity());
						boolean hasAct = false;
						for (OrderActRelationDTO actRef : productRefActIdList) {
							if (actRef.getProductId().intValue() == dto.getProductId().intValue()) {
								GdOrderActivityDetailDTO actInfo = pDTO.getActInfo();
								if (actInfo == null) {
									actInfo = new GdOrderActivityDetailDTO();
								}

								List<GdOrderActivityDTO> pActList = actInfo.getProductActInfo()
										.get(actRef.getProductId());
								if (pActList == null) {
									pActList = new ArrayList<>();
								}
								GdOrderActivityDTO actDto = new GdOrderActivityDTO();
								actDto.setActId(actRef.getActId());
								actDto.setActType(actRef.getActType());
								pActList.add(actDto);
								actInfo.getProductActInfo().put(actRef.getProductId(), pActList);
								pDTO.setActInfo(actInfo);
								hasAct = true;
							}
						}
						if (hasAct) {
							pIdList.add(pDTO);
						}

					}
				}
			}
		}
	}

	/**
	 * 预付款退款
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "orderRefund", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String orderRefund(HttpServletRequest request, OrderBaseinfoDTO dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			OrderBaseinfoDTO order = orderBaseinfoToolService.getByOrderNo(dto.getOrderNo());
			if (StringUtils.equals(EOrderStatus.WAIT_SIGN.getCode(), order.getOrderStatus())) {
				Map<String, String> params = initValidatePageParam(request);
				dealFeeDetail(params);
				params.put("source", "management");// 管理后台退款标识
				logger.info("调用退款接口参数：" + params.toString() + "");
				RefundResponseDTO result = orderBaseinfoToolService.preOrderRefund(params);
				logger.info(result.getRefundNo() + "调用退款接口结果：code:" + result.getCode() + " msg:" + result.getMsg());
				if (result.getCode().equals(MsgCons.C_10000)) {
					map.put("msg", "success");
				} else {
					map.put("msg", result.getMsg());
				}
			} else {
				map.put("msg", "该订单已被修改状态，请刷新后再试");
			}

		} catch (RefundException e) {
			logger.warn("调用退款接口失败：" + e.getMessage());
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage());
			map.put("msg", MsgCons.M_50000);
		}
		return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
	}

	private void validateParam(String str, String msg) throws Exception {
		if (StringUtils.isEmpty(str)) {
			throw new RefundException(msg);
		}
	}

	/**
	 * 添加违约金记录
	 * 
	 * @param params
	 * @throws Exception
	 */
	private void dealFeeDetail(Map<String, String> params) throws Exception {
		Map<String, Object> feelMap = new HashMap<String, Object>();
		// 查询农商友用户订单预付款违约金记录
		String orderNo = params.get("orderNo");// 订单号
		String payerType = "nsy"; // 付方类型
		String feeType = EOrderFeeType.PENALTY_PAY.getCode(); // 4违约金

		feelMap.put("orderNo", orderNo);
		feelMap.put("feeType", feeType);
		feelMap.put("payerType", payerType);
		int total = orderFeeItemDetailToolService.getTotal(feelMap);
		if (total > 0) {
			orderFeeItemDetailToolService.deleteByParam(feelMap);
		}
		List<OrderFeeItemDetailEntity> feelList = new ArrayList<OrderFeeItemDetailEntity>();
		OrderFeeItemDetailEntity entity1 = new OrderFeeItemDetailEntity();// 违约金（卖家）
		entity1.setOrderNo(Long.parseLong(orderNo));
		entity1.setFeeType(feeType);
		entity1.setPayerType(payerType);
		entity1.setCreateUser(params.get("refundUserId"));
		entity1.setPayeeType(EOrderFeeUserType.NPS.getCode());
		entity1.setAmount(Double.parseDouble(params.get("sellerRefundAmt")));
		feelList.add(entity1);

		OrderFeeItemDetailEntity entity2 = new OrderFeeItemDetailEntity();// 违约金（物流公司）
		entity2.setOrderNo(Long.parseLong(orderNo));
		entity2.setFeeType(feeType);
		entity2.setPayerType(payerType);
		entity2.setCreateUser(params.get("refundUserId"));
		entity2.setPayeeType(EOrderFeeUserType.WLGS.getCode());
		entity2.setAmount(Double.parseDouble(params.get("logisRefundAmt")));
		feelList.add(entity2);

		OrderFeeItemDetailEntity entity3 = new OrderFeeItemDetailEntity();// 违约金（平台）
		entity3.setOrderNo(Long.parseLong(orderNo));
		entity3.setFeeType(feeType);
		entity3.setPayerType(payerType);
		entity3.setCreateUser(params.get("refundUserId"));
		entity3.setPayeeType(EOrderFeeUserType.PLATFORM.getCode());
		entity3.setAmount(Double.parseDouble(params.get("platRefundAmt")));
		feelList.add(entity3);

		orderFeeItemDetailToolService.batchInsert(feelList);
	}

	/**
	 * 初始化支付页面参数转map
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> initValidatePageParam(HttpServletRequest request) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		String orderNo = request.getParameter("orderNo");
		validateParam(orderNo, "订单号不能为空");
		paramMap.put("orderNo", orderNo); // 订单号
		String payCenterNumber = request.getParameter("statementId");
		validateParam(payCenterNumber, "支付中心流水号不能为空");
		paramMap.put("payCenterNumber", payCenterNumber);// 支付中心流水号
		String refundAmt = request.getParameter("buyerPrepayRefund");
		validateParam(refundAmt, "退款金额不能为空");
		paramMap.put("refundAmt", refundAmt);// 退款金额
		SysRegisterUser user = getUser(request);
		validateParam(user.getUserID(), " 退款人不能为空");
		paramMap.put("refundUserId", user.getUserCode());// 退款人ID
		String refundReason = request.getParameter("refundReason");
		validateParam(refundReason, "退款原因不能为空");
		paramMap.put("refundReason", refundReason);// 退款原因
		String sellerRefundAmt = request.getParameter("buyerPenaltyToSeller");
		validateParam(sellerRefundAmt, "赔付卖家金额为空");
		paramMap.put("sellerRefundAmt", sellerRefundAmt);// 赔付卖家金额
		String platRefundAmt = request.getParameter("buyerPenaltyToPlatform");
		validateParam(platRefundAmt, "赔付平台金额不能为空");
		paramMap.put("platRefundAmt", platRefundAmt);// 赔付平台金额
		String logisRefundAmt = request.getParameter("buyerPenaltyToWLCompany");
		validateParam(logisRefundAmt, "赔付物流公司金额不能为空");
		paramMap.put("logisRefundAmt", logisRefundAmt);// 赔付物流公司金额
		paramMap.put("appKey", "nsy");
		paramMap.put("refundRequestNo", "0");
		return paramMap;
	}

	/**
	 * 订单管理列表数据查询
	 *
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("orderQuery")
	@ResponseBody
	public String orderQuery(HttpServletRequest request) {
		try {
			String orderNo = request.getParameter("orderNo");
			String orderAmount = request.getParameter("orderAmount");
			String orderStatus = request.getParameter("orderStatus");
			String payType = request.getParameter("payType");
			String account = request.getParameter("account");
			String mobile = request.getParameter("mobile");
			String shopName = request.getParameter("shopName");
			// String examineStatus = request.getParameter("examineStatus");
			// String upPayFlag = request.getParameter("upPayFlag");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String orderSource = request.getParameter("orderSource");
			String statementId = request.getParameter("statementId");
			String posNumber = request.getParameter("posNumber");
			String marketId = request.getParameter("marketId");
			String isFirst = request.getParameter("isFirst");
			String activityType = request.getParameter("activityType");
			String totalPayAmt = request.getParameter("totalPayAmt");
			// String hasCustomer = request.getParameter("hasCustomer");
			String asFlow = request.getParameter("asFlow");

			// 设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotEmpty(activityType)) {
				map.put("activityType",activityType);
			}
			if (StringUtils.isNotEmpty(totalPayAmt)) {
				map.put("totalPayAmt", totalPayAmt);
			}
			if (startDate != null && !"".equals(startDate)) {
				map.put("startDate", startDate);
			}
			if (isFirst != null && !"".equals(isFirst)) {
				map.put("isFirst", isFirst);
			}
			if (endDate != null && !"".equals(endDate)) {
				map.put("endDate", endDate);
			}
			if (orderNo != null && !"".equals(orderNo)) {
				map.put("orderNo", orderNo);
			}
			if (orderAmount != null && !"".equals(orderAmount)) {
				map.put("orderAmount", orderAmount);
			}
			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)) {
				map.put("orderStatus", orderStatus);
			}
			if (payType != null && !"".equals(payType)) {
				map.put("payType", payType);
			}
			if (mobile != null && !"".equals(mobile)) {
				map.put("mobile", mobile);
			}
			if (mobile != null && !"".equals(mobile)) {
				map.put("mobile", mobile);
			}
			if (shopName != null && !"".equals(shopName)) {
				map.put("shopName", shopName);
			}
			if (StringUtils.isNotBlank(orderSource)) {
				map.put("orderSource", orderSource);
			}
			if (StringUtils.isNotBlank(statementId)) {
				map.put("statementId", statementId);
			}
			if (StringUtils.isNotBlank(posNumber)) {
				map.put("posNumber", posNumber);
			}
			if (marketId != null && !"".equals(marketId)) {
				map.put("marketId", marketId);
			}
			if (asFlow != null && !"".equals(asFlow)) {
				map.put("asFlow", asFlow);
			}
			if (account != null && !"".equals(account)) {
				map.put("account", account);
			}

			map.put("orderType", 1);
			// if (examineStatus != null && !"".equals(examineStatus)){
			// map.put("examineStatus", examineStatus);
			// }
			// if (upPayFlag != null && !"".equals(upPayFlag)){
			// map.put("upPayFlag", upPayFlag);
			// }

			// 获取条件/记录总数
			// map.put("total", orderBaseinfoToolService.getTotal(map));
			map.put("total", orderBaseinfoToolService.getOrderTotal(map));
			// 设置分页参数
			setCommParameters(request, map);
			// 数据集合
			// List<OrderBaseinfoDTO> list =
			// orderBaseinfoToolService.getListByConditionPage(map);
			List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getOrderListByConditionPage(map);
			// 设置默认农商友用户
			if (CollectionUtils.isNotEmpty(list)) {
				for (OrderBaseinfoDTO orderBase : list) {
					orderBase.setRealName(
							StringUtils.isBlank(orderBase.getRealName()) ? "农商友用户" : orderBase.getRealName());
				}
			}

			// rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询订单的相似流水，待付款和已关闭的订单使用
	 *
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("queryOrderSameStatement")
	@ResponseBody
	public String queryOrderSameStatement(String persaleId) {
		Map<String, String> map = new HashMap<>();
		map.put("res", "success");
		try {
			String statementId = orderBaseinfoToolService.queryOderSameStatement(persaleId);
			map.put("statementId", statementId);
		} catch (Exception e) {
			logger.trace("查询订单相似流水失败," + e.getMessage(), e);
			// e.printStackTrace();
			map.put("res", "error");
		}
		return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 保存补单信息
	 * 
	 * @param persaleId
	 * @return
	 */
	@RequestMapping("saveSupplementOrder")
	@ResponseBody
	public String saveSupplementOrder(HttpServletRequest request, String persaleId, String statementId) {
		Map<String, String> map = new HashMap<>();
		map.put("res", "success");
		try {
			SysRegisterUser user = getUser(request);
			orderBaseinfoToolService.saveSupplementOrder(user.getUserID(), persaleId, statementId);
		} catch (RuntimeException e) {
			map.put("res", "error");
			map.put("msg", e.getCause().getMessage());// 事物引起的
			logger.trace("保存补单失败," + e.getMessage(), e);
		} catch (Exception e) {
			logger.trace("保存补单失败," + e.getMessage(), e);
			// e.printStackTrace();
			map.put("res", "error");
			map.put("res", "系统错误，请联系管理员");
		}
		return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 初始化 查看详细信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("orderDetailById/{persaleId}")
	public String orderDetailById(@PathVariable("persaleId") String persaleId, ModelMap map) {
		OrderBaseinfoDTO order = null;
		try {
			Long id = Long.valueOf(persaleId);
			order = orderBaseinfoToolService.getById(id);

			if (order != null) {

				/**
				 * 订单商品信息
				 */
				getProductDetail(order);
				/**
				 * 审核日志
				 */
				getAuditLog(order);
				/**
				 * 用户订单收货地址信息:包括手机号,收货人,地址,及部分物流信息
				 */
				getLogisticalProgress(order, map);

				/**
				 * 查询佣金、补贴、违约金、预付款
				 */
				getOrderFeeItemDetail(order);
				/**
				 * 支付流水记录，包含POS及支付宝
				 */
				getPayLog(order);
				/**
				 * 退款信息查询
				 */
				getRefundDetail(order);

				/**
				 * 获取订单清分后卖家收款信息
				 */
				getSellerFeeDetail(order);
				/**
				 * 状态词
				 */
				setWords(order);
			}

		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		map.put("dto", order);
		return "order/order_detail";
	}

	/**
	 * 获取订单状态词
	 * 
	 * @param orderBaseDTO
	 */
	public void setWords(OrderBaseinfoDTO orderBaseDTO) {

		if ("1".equals(orderBaseDTO.getOrderStatus())) {// 待付款
			if ("1".equals(orderBaseDTO.getDistributeMode())) {
				orderBaseDTO.setStatusWord(EOrderStatusWord.DFYFK.getWord());
			} else {
				orderBaseDTO.setStatusWord(EOrderStatusWord.DFK.getWord());
			}
		} else if ("11".equals(orderBaseDTO.getOrderStatus())) {// 待付预付款
			orderBaseDTO.setStatusWord(EOrderStatusWord.DYH.getWord());
		} else if ("12".equals(orderBaseDTO.getOrderStatus())) {// 待付尾款
			orderBaseDTO.setStatusWord(EOrderStatusWord.DFWK.getWord());
		} else if ("8".equals(orderBaseDTO.getOrderStatus())) {

			if (EOrderCloseReason.BUYER_CANCLE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderBaseDTO.setStatusWord(EOrderStatusWord.WYQXDD.getWord());
			} else if (EOrderCloseReason.SELLER_CANCLE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderBaseDTO.setStatusWord(EOrderStatusWord.MJYQXDD.getWord());
			} else if (EOrderCloseReason.PAY_TIMEOUT.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderBaseDTO.setStatusWord(EOrderStatusWord.FKCS.getWord());
			} else if (EOrderCloseReason.LOGISTICS_FAILURE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderBaseDTO.setStatusWord(EOrderStatusWord.FPWLSB.getWord());
			} else if (EOrderCloseReason.INSPECTION_FAILURE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderBaseDTO.setStatusWord(EOrderStatusWord.YHBTG.getWord());
			} else if (EOrderCloseReason.PREPAY_TIMEOUT.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderBaseDTO.setStatusWord(EOrderStatusWord.YFKZFCS.getWord());
			} else if ("1".equals(orderBaseDTO.getOorStatus())) {
				orderBaseDTO.setStatusWord(EOrderStatusWord.DTK.getWord());
			} else if ("3".equals(orderBaseDTO.getOorStatus())) {
				orderBaseDTO.setStatusWord(EOrderStatusWord.YTK.getWord());
			} else {
				orderBaseDTO.setStatusWord("退款失败");
			}

		} else if ("3".equals(orderBaseDTO.getOrderStatus())) {// 已完成
			orderBaseDTO.setStatusWord(EOrderStatusWord.YWC.getWord());

		}
	}

	/**
	 * 获取订单清分后卖家收款信息
	 */
	private void getSellerFeeDetail(OrderBaseinfoDTO order) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderNo", String.valueOf(order.getOrderNo()));
		params.put("MemberId", String.valueOf(order.getSellMemberId()));
		params.put("userType", "nps");
		ClearingLogDTO dto = orderBaseinfoToolService.getSellerFeeDetail(params);
		if (dto.isSuccess()) {
			List<OrderClearDetail> list = dto.getData();
			if (CollectionUtils.isNotEmpty(list))
				super.putModel("clearingLog", list.get(0));
		}
	}

	/**
	 * 支付流水记录（包含预付款跟尾款记录）
	 * 
	 * @param order
	 * @throws Exception
	 */
	private void getPayLog(OrderBaseinfoDTO order) throws Exception {
		Map<String, Object> detailmap = new HashMap<String, Object>();
		detailmap.put("orderNo", order.getOrderNo());
		List<PaySerialnumberDTO> payList = paySerialnumberToolService.getListByCondition(detailmap);
		if (CollectionUtils.isNotEmpty(payList)) {
			for (PaySerialnumberDTO log : payList) {
				String payType = log.getPayType();
				// 支付类型为支付宝-》则为预付款流水记录
				if (StringUtils.equals(payType, EPayType.ALIPAY.getCode())) {
					order.setPaySerialnumberAllipay(log);
				} else {
					// 订单为平台配送，则为POS尾款支付，否则为普通支付
					if ("1".equals(order.getDistributeMode())) {
						order.setPaySerialnumberPosFinal(log);
					} else {
						order.setPaySerialnumberPosAll(log);
					}
				}
			}
		}
	}

	/**
	 * 订单物流信息
	 * 
	 * @param order
	 * @throws Exception
	 */
	private void getLogisticalProgress(OrderBaseinfoDTO order, Map<String, Object> map) throws Exception {
		Map<String, Object> detailmap = new HashMap<String, Object>();
		detailmap.put("orderNo", order.getOrderNo());
		/**
		 * 用户订单收货地址信息
		 */
		List<OrderDeliveryAddressDTO> orderDeliverys = orderDeliveryAddressToolService.getList(detailmap);
		if (CollectionUtils.isNotEmpty(orderDeliverys)) {
			OrderDeliveryAddressDTO deliveryAddress = orderDeliverys.get(0);
			order.setOrderDelivery(deliveryAddress);
			/**
			 * 调用弄速通接口返回物流进度信息
			 */
			if (StringUtils.isNotEmpty(deliveryAddress.getNstOrderNo())) {
				NstLogisticalProgressDTO responseResult = getNstProgressLog(deliveryAddress.getNstOrderNo());
				if (responseResult != null && Integer.compare(CALLBACK_SUCCESS, responseResult.getCode()) == 0) {
					NstResult result = responseResult.getResult();
					if (result != null) {
						int len = 0;
						List<NstProgressLog> logList = result.getOrderInfoTransList();
						for (NstProgressLog progress : logList) {
							/**
							 * 获取最新物流状态，NST接口是按ASC排序返回
							 */
							len++;
							if (len == result.getOrderInfoTransList().size()) {
								result.setStatus(progress.getTransStatusVar());
							}
						}
						map.put("logisticalProgress", result);
					}
				}
			}
		}
	}

	/**
	 * 订单审核日志
	 * 
	 * @param order
	 * @throws Exception
	 */
	private void getAuditLog(OrderBaseinfoDTO order) throws Exception {
		List<AuditLogDTO> logList = auditLogToolService.getListByOrderNo(order.getOrderNo());
		if (CollectionUtils.isNotEmpty(logList)) {
			order.setAuditLogList(logList);
		}
	}

	/**
	 * 订单商品信息
	 * 
	 * @param order
	 * @throws Exception
	 */
	private void getProductDetail(OrderBaseinfoDTO order) throws Exception {
		Map<String, Object> detailmap = new HashMap<String, Object>();
		detailmap.put("orderNo", order.getOrderNo());
		//查询出订单是否是现场采销活动，只有为2才表示是现场采销，为2时不需要查询图片
		detailmap.put("activityType", order.getActivityType());
		List<OrderProductDetailDTO> productList = orderProductDetailToolService.getListByOrderNo(detailmap);
		if (CollectionUtils.isNotEmpty(productList)) {
			order.setDetailList(productList);
		}
	}

	/**
	 * 查询退款信息
	 * 
	 * @param order
	 * @throws Exception
	 */
	private void getRefundDetail(OrderBaseinfoDTO order) throws Exception {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo", order.getOrderNo());
		List<OrderRefundRecordDTO> refundList = orderRefundRecordToolService.getList(paramMap);
		if (CollectionUtils.isNotEmpty(refundList)) {
			OrderRefundRecordDTO dto = refundList.get(0);
			order.setOorStatus(dto.getStatus());
			try {
				// 调用支付中心退款查询记录接口补充其他退款信息
				paramMap.put("payType", EPayType.ALIPAY.getCode());
				// 参数拼接、sign生成
				Map<String, String> params = initPageParam(dto);
				String link = AccessSysSignUtil.createLinkString(AccessSysSignUtil.paraFilter(params));
				String sign = AccessSysSignUtil.sign(link, RsaUtil.KEYTYPE, RsaUtil.PRIVATEKEY);
				String encodeSign = Base64.encode(sign.getBytes());
				params.put("sign", encodeSign);

				// 调用支付中心退款日志查询服务
				logger.info("调用退款查询接口参数：" + params.toString() + "");
				RefundResponseLogResult result = orderBaseinfoToolService.getOrderRefundLog(params);
				order.setRefundLog(result);
				order.setRefundRecord(dto);
			} catch (Exception e) {
				logger.info("调用退款查询接口异常：" + e);
			}
		}
	}

	/**
	 * 参数处理
	 * 
	 * @param dto
	 * @return
	 */
	private Map<String, String> initPageParam(OrderRefundRecordDTO dto) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNo", dto.getOrderNo().toString());
		map.put("appKey", "nsy");
		// map.put("refundRequestNo", dto.getRefundNo());
		map.put("refundRequestNo", "0");
		return map;
	}

	/**
	 * 查询佣金、违约金、补贴
	 * 
	 * @param map
	 * @param order
	 * @throws Exception
	 */
	private void getOrderFeeItemDetail(OrderBaseinfoDTO order) throws Exception {
		EOrderFeeUserType feeUser = null;
		/**
		 * 根据订单类型判断买家类型
		 */
		EOrderType orderType = EOrderType.getItemByCode(order.getOrderType());
		switch (orderType) {
		case NSY:
		case NSY_6_1:
			feeUser = EOrderFeeUserType.NSY;
			break;
		case NPS:
			feeUser = EOrderFeeUserType.NPS;
			break;
		default:
			break;
		}
		if (feeUser != null) {
			Map<String, Object> detailmap = new HashMap<String, Object>();
			detailmap.put("orderNo", order.getOrderNo());
			// 根据订单号查询1佣金 2补贴 3预付款，4违约金
			List<OrderFeeItemDetailDTO> feeList = orderFeeItemDetailToolService.getList(detailmap);

			if (CollectionUtils.isNotEmpty(feeList)) {
				Double buyerComm = 0D;// 买家佣金之和
				for (OrderFeeItemDetailDTO dto : feeList) {
					EOrderFeeType type = EOrderFeeType.getEnumByCode(dto.getFeeType());// 费用类型
					String payerType = dto.getPayerType();// 付方类型
					String payeeType = dto.getPayeeType();// 收方类型

					switch (type) {
					// 佣金
					case COMMISION:
						// 买家佣金 （平台、市场、物流）
						if (StringUtils.equals(payerType, feeUser.getCode())) {

							if ("platform".equals(payeeType)) {
								order.setBuyerPlatformCommision(dto.getAmount());
								buyerComm = MathUtil.add(buyerComm, dto.getAmount());
							}
							if ("market".equals(payeeType)) {
								order.setBuyerMarketCommision(dto.getAmount());
								buyerComm = MathUtil.add(buyerComm, dto.getAmount());
							}
							if ("wlgs".equals(payeeType)) {
								order.setBuyerLogisticsCommision(dto.getAmount());
								buyerComm = MathUtil.add(buyerComm, dto.getAmount());
							}

						}
						// 卖家佣金（平台、市场）
						else {
							if ("platform".equals(payeeType)) {
								order.setSellerPlatformCommision(dto.getAmount());
							}
							if ("market".equals(payeeType)) {
								order.setSellerMarketCommision(dto.getAmount());
							}
						}
						break;
					// 补贴
					case SUBSIDY:
						order.setSellerPosSubsidyCommision(dto.getAmount());
						break;
					// 预付款
					case PRE_PAY:
						order.setPrepayAmt(dto.getAmount());
						break;
					// 违约金
					case PENALTY_PAY:
						// 违约金买家
						if (StringUtils.equals(payerType, feeUser.getCode())) {
							if ("nps".equals(payeeType)) {
								// 违约金（NPS）
								order.setBuyerPenaltyToSeller(dto.getAmount());
							}
							if ("platform".equals(payeeType)) {
								// 违约金（平台）
								order.setBuyerPenaltyToPlatform(dto.getAmount());
							}
							if ("wlgs".equals(payeeType)) {
								// 违约金（物流公司）
								order.setBuyerPenaltyToWLCompany(dto.getAmount());
							}
						}
						// 违约金卖家
						else {

							order.setSellerPenaltyToSeller(dto.getAmount());
						}
						break;
					}
				}
				order.setBuyerCommision(buyerComm);
			}
		}
	}

	/**
	 * 弄速通物流进度查询
	 * 
	 * @param nstOrderNo
	 *            运单号
	 * @return
	 * @throws Exception
	 */
	public NstLogisticalProgressDTO getNstProgressLog(String nstOrderNo) throws Exception {
		String paramValue = null;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderNo", nstOrderNo);
		logger.info("调用农速通获取物流信息接口入参:" + paramMap.toString());
		try {
			paramValue = Des3.encode(new Gson().toJson(paramMap));
			String url = gdProperties.getNstApiUrl();
			logger.info("调用农速通获取物流信息接口参数:" + paramMap.toString() + " url:" + url);
			String sNstApiResult = HttpClients.doPost(url, paramValue);
			if (StringUtils.isNotBlank(sNstApiResult)) {
				// 得到农速通接口结果
				String sNstRepJson = Des3.decode(sNstApiResult);
				logger.info("调用农速通获取物流信息接口结果:" + sNstRepJson);
				NstLogisticalProgressDTO nstResponse = (NstLogisticalProgressDTO) GSONUtils
						.fromJsonToObject(sNstRepJson, NstLogisticalProgressDTO.class);
				return nstResponse;
			}

		} catch (Exception e) {
			logger.info("调用农速通获取物流信息接口异常" + e.getMessage());
		}
		return null;
	}

	/**
	 * 提交更新数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "orderSaveEdit")
	@ResponseBody
	public String orderSaveEdit(PaySerialnumberDTO dto, HttpServletRequest request) {
		try {
			SysRegisterUser user = this.getUser(request);
			int i = 0;
			Integer payId = dto.getPayId();
			Long orderNo = dto.getOrderNo();
			String type = request.getParameter("type");
			String description = dto.getDescription();
			String statementId = dto.getStatementId();
			if (null != statementId && !"".equals(statementId))
				statementId = statementId.replace("，", ",");
			if (null != description && !"".equals(description))
				description = description.replace("，", ",");
			String updateUserId = user.getUserID();
			String userName = user.getUserName();
			i = orderBaseinfoToolService.orderExamine(payId, orderNo, type, description, statementId, updateUserId,
					userName);
			if (i > 0) {
				return "success";
			} else if (i == -1) {
				return "failedbylsh";
			} else if (i == -2) {
				return "failedbysh";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "failed";
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
//			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)) {
//				result.put("status", 0);
//				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
//				return JSONObject.toJSONString(result);
//			}
			String orderNo = request.getParameter("orderNo");
			String totalPayAmt = request.getParameter("totalPayAmt");
			String orderStatus = request.getParameter("orderStatus");
			String payType = request.getParameter("payType");
			String account = request.getParameter("account");
			String shopName = request.getParameter("shopName");
			String marketId = request.getParameter("marketId");
			String orderSource = request.getParameter("orderSource");
			String statementId = request.getParameter("statementId");
			String posNumber = request.getParameter("posNumber");
			String isFirst = request.getParameter("isFirst");
			String mobile = request.getParameter("mobile");
			String asFlow = request.getParameter("asFlow");
			// 设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (asFlow != null && !"".equals(asFlow)) {
				map.put("asFlow", asFlow);
			}
			if (isFirst != null && !"".equals(isFirst)) {
				map.put("isFirst", isFirst);
			}
			if (mobile != null && !"".equals(mobile)) {
				map.put("mobile", mobile);
			}
			if (startDate != null && !"".equals(startDate)) {
				map.put("startDate", startDate);
			}
			if (marketId != null && !"".equals(marketId)) {
				map.put("marketId", marketId);
			}
			if (endDate != null && !"".equals(endDate)) {
				map.put("endDate", endDate);
			}
			if (orderNo != null && !"".equals(orderNo)) {
				map.put("orderNo", orderNo);
			}
			if (totalPayAmt != null && !"".equals(totalPayAmt)) {
				map.put("totalPayAmt", totalPayAmt);
			}
			if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)) {
				map.put("orderStatus", orderStatus);
			}
			if (payType != null && !"".equals(payType)) {
				map.put("payType", payType);
			}
			if (account != null && !"".equals(account)) {
				map.put("account", account);
			}
			if (mobile != null && !"".equals(mobile)) {
				map.put("mobile", mobile);
			}
			if (shopName != null && !"".equals(shopName)) {
				map.put("shopName", shopName);
			}
			if (StringUtils.isNotBlank(orderSource)) {
				map.put("orderSource", orderSource);
			}
			if (StringUtils.isNotBlank(statementId)) {
				map.put("statementId", statementId);
			}
			if (StringUtils.isNotBlank(posNumber)) {
				map.put("posNumber", posNumber);
			}
			map.put("orderType", 1);
			int total = orderBaseinfoToolService.getOrderTotal(map);
			if (total > 100000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>100000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		} catch (Exception e) {
			logger.info("product checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}

	/**
	 * @Description exportData 数据导出
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		OutputStream ouputStream = null;
		try {
			Map<String, Object> 	map = getRequestParams(request);
			WritableWorkbook wwb = null;
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName = null;
			String agent = request.getHeader("USER-AGENT");
			if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
				fileName = URLEncoder.encode("农商友采购订单列表", "UTF-8");
			} else if (agent != null && -1 != agent.indexOf("Mozilla")) {	// 火狐,chrome等
				fileName = new String("农商友采购订单列表".getBytes("UTF-8"), "ISO-8859-1");
			}

			// 根据checkExportParams接口中对日期的限制, startDate必须不能为空,
			// 若endDate为空则endDate自动被设置为当前日期
			//modify by lf on 20170217 由于导出日期控制被注释了，当不选择日期会报空指针，因此此处加上条件控制
			String suffix = "";
			if(null != map.get("startDate") && null != map.get("endDate")){
				suffix = String.format("%tF", DateUtil.sdfDateTime.parse(map.get("startDate").toString())).replace("-", "") + "_"
						+ String.format("%tF", DateUtil.sdfDateTime.parse(map.get("endDate").toString())).replace("-", "");
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + suffix + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getOrderListByConditionPage(map);
			for (OrderBaseinfoDTO dto : list) {
				StringBuilder activityStr = new StringBuilder();
				List<OrderActRelationDTO> actList = orderActRelationToolService.getByOrderNo(dto.getOrderNo());
				if (CollectionUtils.isNotEmpty(actList)) {
					for (OrderActRelationDTO actRel : actList) {
						GdActActivityDTO act = gdActActivityToolService.getById(String.valueOf(actRel.getActId()));
						if (act != null)
							activityStr.append(act.getName() + ",");
					}
					if (activityStr.length() > 0)
						dto.setActivityMessage(activityStr.toString().substring(0, activityStr.length() - 1));
				}

			}
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("订单信息", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				int col = 0;
				Label label00 = new Label(col++, 0, "订单编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(col++, 0, "订单总价");// 填充第一行第四个单元格的内容
				Label label301 = new Label(col++, 0, "实付款");
				Label label40 = new Label(col++, 0, "支付方式");// 填充第一行第二个单元格的内容
				Label label401 = new Label(col++, 0, "订单来源");// 填充第一行第二个单元格的内容
				Label label402 = new Label(col++, 0, "终端号");// 填充第一行第二个单元格的内容
				Label label403 = new Label(col++, 0, "银行卡");// 填充第一行第二个单元格的内容
				Label label404 = new Label(col++, 0, "流水号");// 填充第一行第二个单元格的内容
				Label label50 = new Label(col++, 0, "用户账号");// 填充第一行第六个单元格的内容
				Label label51 = new Label(col++, 0, "手机号码");// 填充第一行第六个单元格的内容
				Label label60 = new Label(col++, 0, "买家姓名");// 填充第一行第八个单元格的内容
				Label label80 = new Label(col++, 0, "商铺名称");// 填充第一行第三个单元格的内容
				Label label802 = new Label(col++, 0, "主营分类");// 填充第一行第三个单元格的内容
				Label label803 = new Label(col++, 0, "其他分类");// 填充第一行第三个单元格的内容
				Label label801 = new Label(col++, 0, "创建时间");// 填充第一行第三个单元格的内容
				Label label90 = new Label(col++, 0, "订单状态");// 填充第一行第三个单元格的内容
				Label label112 = new Label(col++, 0, "活动类型");// 填充第一行第三个单元格的内容
				Label label113 = new Label(col++, 0, "成交时间");// 填充第一行第三个单元格的内容
				// 填充第一行第三个单元格的内容
				Label label115 = new Label(col++, 0, "所属市场");// 填充第一行第三个单元格的内容
				Label label16 = new Label(col++, 0, "活动信息");
				Label label17 = new Label(col++, 0, "活动类型");
				Label label18 = new Label(col++, 0, "活动积分");
				/*********add by weiwenke********/
				Label label19 = new Label(col++, 0, "商铺名称");
				Label label20 = new Label(col++, 0, "买家留言");
				Label label21 = new Label(col++, 0, "关闭时间");
				
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label301);
				sheet.addCell(label40);
				sheet.addCell(label401);
				sheet.addCell(label402);
				sheet.addCell(label403);
				sheet.addCell(label404);
				sheet.addCell(label50);
				sheet.addCell(label51);
				sheet.addCell(label60);
				sheet.addCell(label80);
				sheet.addCell(label802);
				sheet.addCell(label803);
				sheet.addCell(label801);
				sheet.addCell(label90);
				sheet.addCell(label112);
				sheet.addCell(label113);
				sheet.addCell(label115);
				sheet.addCell(label16);
				sheet.addCell(label17);
				sheet.addCell(label18);
				sheet.addCell(label19);
				sheet.addCell(label20);
				sheet.addCell(label21);
				sheet.setColumnView(19, 25);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						OrderBaseinfoDTO orderBaseinfoDTO = list.get(i);
						col = 0;
						Label label0 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderNo().toString());
						Label label1 = new Label(col++, i + 1, orderBaseinfoDTO.getTotalPayAmt() == null ? ""
								: new DecimalFormat("#.##").format(orderBaseinfoDTO.getTotalPayAmt()));
						Label label31 = new Label(col++, i + 1, orderBaseinfoDTO.getTotalPayAmt() == null ? ""
								: new DecimalFormat("#.##").format(orderBaseinfoDTO.getTotalPayAmt()));
						Label label4 = new Label(col++, i + 1, orderBaseinfoDTO.getPayTypeView());
						Label label41 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderSourceView());
						Label label42 = new Label(col++, i + 1, orderBaseinfoDTO.getPosNumber());
						Label label43 = new Label(col++, i + 1, orderBaseinfoDTO.getPaymentAcc());
						Label label44 = new Label(col++, i + 1, orderBaseinfoDTO.getStatementId());
						Label label5 = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getAccount()) ? ""
								: orderBaseinfoDTO.getAccount());
						Label label5a = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getBuyerMobile())
								? "" : orderBaseinfoDTO.getBuyerMobile());
						Label label6 = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getRealName()) ? ""
								: orderBaseinfoDTO.getRealName());
						Label label8 = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getShopName()) ? ""
								: orderBaseinfoDTO.getShopName());
						Label label82 = new Label(col++, i + 1, StringUtils.isEmpty(orderBaseinfoDTO.getCateNames())
								? "" : orderBaseinfoDTO.getCateNames());
						Label label83 = new Label(col++, i + 1,
								StringUtils.isEmpty(orderBaseinfoDTO.getOtherCateNames()) ? ""
										: orderBaseinfoDTO.getOtherCateNames());
						Label label81 = new Label(col++, i + 1,
								DateUtil.toString(orderBaseinfoDTO.getOrderTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label9 = new Label(col++, i + 1, orderBaseinfoDTO.getOrderStatusView());
						Label label111 = new Label(col++, i + 1, orderBaseinfoDTO.getIsFirst() == 1 ? "首单" : "");
						Label label114 = new Label(col++, i + 1,
								DateUtil.toString(orderBaseinfoDTO.getFinishedTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label1150 = new Label(col++, i + 1, orderBaseinfoDTO.getMarketName());
						Label labelMessage = new Label(col++, i + 1, orderBaseinfoDTO.getActivityMessage());
						Label activityType = null;
						if (orderBaseinfoDTO.getActivityType() != null) {
							activityType = new Label(col++, i + 1, orderBaseinfoDTO.getActivityType().equals("1")?"无活动":"现场采销");
						}else{
						    activityType = new Label(col++, i + 1, "");
						}
						Label activityIntegral = new Label(col++, i + 1, orderBaseinfoDTO.getActivityIntegral()==null?"0":orderBaseinfoDTO.getActivityIntegral().toString());
						Label shopName = new Label(col++, i + 1, orderBaseinfoDTO.getShopName()==null?"":orderBaseinfoDTO.getShopName()); //商品名称
						Label message = new Label(col++, i + 1, orderBaseinfoDTO.getMessage()==null?"":orderBaseinfoDTO.getMessage()); //买家留言
						Label closeTime = new Label(col++, i + 1, orderBaseinfoDTO.getCloseTimeStr()==null?"":orderBaseinfoDTO.getCloseTimeStr().toString()); //关闭时间
						
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label31);
						sheet.addCell(label4);
						sheet.addCell(label41);
						sheet.addCell(label42);
						sheet.addCell(label43);
						sheet.addCell(label44);
						sheet.addCell(label5);
						sheet.addCell(label5a);
						sheet.addCell(label6);
						sheet.addCell(label8);
						sheet.addCell(label82);
						sheet.addCell(label83);
						sheet.addCell(label81);
						sheet.addCell(label9);
						sheet.addCell(label111);
						sheet.addCell(label114);
						sheet.addCell(label1150);
						sheet.addCell(labelMessage);
						sheet.addCell(activityType);
						sheet.addCell(activityIntegral);
						sheet.addCell(shopName);
						sheet.addCell(message);
						sheet.addCell(closeTime);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Map<String, Object> getRequestParams(HttpServletRequest	request){
		Map<String, Object> 	map = new HashMap<>();
		
		String orderNo = request.getParameter("orderNo");
		String totalPayAmt = request.getParameter("totalPayAmt");
		String orderStatus = request.getParameter("orderStatus");
		String payType = request.getParameter("payType");
		String account = request.getParameter("account");
		String shopName = request.getParameter("shopName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderSource = request.getParameter("orderSource");
		String statementId = request.getParameter("statementId");
		String posNumber = request.getParameter("posNumber");
		String marketId = request.getParameter("marketId");
		String isFirst = request.getParameter("isFirst");
		String mobile = request.getParameter("mobile");
		String asFlow = request.getParameter("asFlow");
		// 设置查询参数
		if (asFlow != null && !"".equals(asFlow)) {
			map.put("asFlow", asFlow);
		}
		if (mobile != null && !"".equals(mobile)) {
			map.put("mobile", mobile);
		}
		if (startDate != null && !"".equals(startDate)) {
			map.put("startDate", startDate);
		}
		if (isFirst != null && !"".equals(isFirst)) {
			map.put("isFirst", isFirst);
		}
		if (CommonUtil.isEmpty(endDate)) {
			endDate = DateUtil.getSysDateTimeString();
		}
		map.put("endDate", endDate);
		if (orderNo != null && !"".equals(orderNo)) {
			map.put("orderNo", orderNo);
		}
		if (totalPayAmt != null && !"".equals(totalPayAmt)) {
			map.put("totalPayAmt", totalPayAmt);
		}
		if (orderStatus != null && !"".equals(orderStatus) && !"0".equals(orderStatus)) {
			map.put("orderStatus", orderStatus);
		}
		if (payType != null && !"".equals(payType)) {
			map.put("payType", payType);
		}
		if (account != null && !"".equals(account)) {
			map.put("account", account);
		}
		if (mobile != null && !"".equals(mobile)) {
			map.put("mobile", mobile);
		}
		if (shopName != null && !"".equals(shopName)) {
			map.put("shopName", shopName);
		}
		if (StringUtils.isNotBlank(orderSource)) {
			map.put("orderSource", orderSource);
		}
		if (StringUtils.isNotBlank(statementId)) {
			map.put("statementId", statementId);
		}
		if (StringUtils.isNotBlank(posNumber)) {
			map.put("posNumber", posNumber);
		}
		if (StringUtils.isNotBlank(marketId)) {
			map.put("marketId", marketId);
		}
		map.put("orderType", 1);
		map.put("startRow", 0);
		map.put("endRow", 10000);
		return map;
	}
	
	/**
	 * @Description exportOrderProductData 订单商品导出
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "exportOrderProductData")
	public String exportOrderProductData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> 	map = getRequestParams(request);
		try {
			List<OrderBaseinfoDTO> list = orderBaseinfoToolService.getOrderListByConditionPage(map);
			for(OrderBaseinfoDTO orderBaseinfoDTO : list){
				if(null != orderBaseinfoDTO.getOrderNo() && !"".equals(orderBaseinfoDTO.getOrderNo())){
					getProductDetail(orderBaseinfoDTO);
					getPayLog(orderBaseinfoDTO);
				}
			}
			responseExcel(response,map,list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	List<String> order_file_names = new ArrayList<>();
	public void responseExcel(HttpServletResponse response,Map<String, Object> 	map,List<OrderBaseinfoDTO> list){
		String fileName = null;
		String agent = request.getHeader("USER-AGENT");
		try {
			if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
				fileName = URLEncoder.encode("农商友采购订单商品列表", "UTF-8");
			} else if (agent != null && -1 != agent.indexOf("Mozilla")) {	// 火狐,chrome等
				fileName = new String("农商友采购订单商品列表".getBytes("UTF-8"), "ISO-8859-1");
			}
			String suffix = "";
			if(null != map.get("startDate") && null != map.get("endDate")){
				suffix = String.format("%tF", DateUtil.sdfDateTime.parse(map.get("startDate").toString())).replace("-", "") + "_"
						+ String.format("%tF", DateUtil.sdfDateTime.parse(map.get("endDate").toString())).replace("-", "");
			}
			String path = request.getSession().getServletContext().getRealPath("order_excel");
			writeExcel(path,"农商友采购订单商品列表"+suffix,list,0); //生成excel 文件
			
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + suffix + ".zip");
			OutputStream	ouputStream = response.getOutputStream();
			//将zip 文件写入到输出流下载
			InputStream		inputStream = new FileInputStream(getZip(path));
			int byteCount = 0;  
			byte[] bytes = new byte[1024 * 1024];
			while((byteCount = inputStream.read(bytes)) != -1){
				ouputStream.write(bytes,0,byteCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//将生成的excel文件，打包ｚｉｐ　文件
	public File getZip(String path){
		String zipFileName = new Date().getTime()+".zip";
		File zipFile = new File(path,zipFileName);
		try {
			ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
			for(String excelFileName:order_file_names){
				File excelfile = new File(path,excelFileName);
				InputStream	inputStream = new FileInputStream(excelfile);
				zipOutputStream.putNextEntry(new ZipEntry(excelFileName));
				int byteCount = 0;  
				byte[] bytes = new byte[1024 * 1024];
				while((byteCount = inputStream.read(bytes)) != -1){
					zipOutputStream.write(bytes,0,byteCount);
				}
				inputStream.close();
			}
			order_file_names.clear();
			zipOutputStream.flush();
			zipOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zipFile;
	}
	
	public void writeExcel(String path,String	fileName,List<OrderBaseinfoDTO> list,int start){
		List<String> excelColumns = new ArrayList<>();
		excelColumns.add("订单编号");
		excelColumns.add("用户账号");
		excelColumns.add("手机号码");
		excelColumns.add("买家姓名");
		excelColumns.add("配送方式");
		excelColumns.add("商品名称");
		excelColumns.add("采购量");
		excelColumns.add("单价");
		excelColumns.add("小计1");
		excelColumns.add("其他费用1采购量");
		excelColumns.add("单价");
		excelColumns.add("小计2");
		excelColumns.add("小计3");
		excelColumns.add("收货地");
		excelColumns.add("发货人");
		excelColumns.add("联系电话");
		excelColumns.add("创建时间");
		excelColumns.add("买家留言");
		excelColumns.add("订单总价");
		excelColumns.add("实付款");
		excelColumns.add("成交时间");
		excelColumns.add("关闭时间");
		excelColumns.add("商铺名称");
		excelColumns.add("主营分类");
		excelColumns.add("其他分类");
		excelColumns.add("订单状态");
		excelColumns.add("活动类型");
		
		fileName = fileName+"_"+new Date().getTime()+".xls";
		File   targetFile = new File(path,fileName);
		targetFile.getParentFile().mkdirs();
		order_file_names.add(fileName);
		try {
			WritableWorkbook	wwb = Workbook.createWorkbook(new FileOutputStream(targetFile));
//			WritableWorkbook wwb = Workbook.createWorkbook(ouputStream);
			if(null != wwb){
				WritableSheet sheet = wwb.createSheet("订单商品信息", 0);
				for(int i =0;i<excelColumns.size();i++){
					sheet.addCell(new Label(i, 0,excelColumns.get(i)));
				}
				int temp = 0;
				int count = 0;
				for(int a = start;a<list.size();a++){
					OrderBaseinfoDTO	orderBaseinfoDTO = list.get(a);
					List<OrderProductDetailDTO>	productList = orderBaseinfoDTO.getDetailList();
					if(null != productList){
						count = count + productList.size();
					}
					if (count > 50000){
						writeExcel(path,fileName,list,a); 
						break;
					}
					Map<String, Object> map = new HashMap<>();
					getLogisticalProgress(orderBaseinfoDTO,map);
					NstResult nstResult = (NstResult)map.get("logisticalProgress");
					if(null != productList){
						for(int i = 0;i<productList.size();i++){
							temp +=1;
							OrderProductDetailDTO orderProductDetailDTO = productList.get(i);
							int col = 0;
							sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getOrderNo().toString()));
							sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getAccount()));
							sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getBuyerMobile()));
							sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getRealName()));
							if(null != orderBaseinfoDTO.getDistributeMode()){
								sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getDistributeMode().equals("1")?"平台配送":"自提"));
							}else{
								sheet.addCell(new Label(col++,temp,"自提"));
							}
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getProductName()));
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getPurQuantity()==null?"":orderProductDetailDTO.getPurQuantity().toString()+orderProductDetailDTO.getUnitName()));
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getPrice()==null?"":orderProductDetailDTO.getPrice().toString()+"/"+orderProductDetailDTO.getUnitName()));
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getNeedToPayAmount()!= null?orderProductDetailDTO.getNeedToPayAmount().toString():""));
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getPurQuantity1()!=null?orderProductDetailDTO.getPurQuantity1().toString():""));//其他费用1采购量
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getPrice1()!=null?orderProductDetailDTO.getPrice1().toString():""));//其他费用1单价
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getPrice2()!=null?orderProductDetailDTO.getPrice2().toString():""));//小计2
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getPrice2()!=null?orderProductDetailDTO.getPrice2().toString():""));//小计3
							sheet.addCell(new Label(col++,temp,nstResult!=null?nstResult.getE_detail():"")); //收货地址
							sheet.addCell(new Label(col++,temp,nstResult!=null?nstResult.getShipperName():""));	//收货人
							sheet.addCell(new Label(col++,temp,nstResult!=null?nstResult.getShipperMobile():""));//联系电话

							sheet.addCell(new Label(col++,temp,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderBaseinfoDTO.getCreateTime())));
							sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getMessage()==null?"":orderBaseinfoDTO.getMessage()));

							sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getTotalPayAmt()!=null?orderBaseinfoDTO.getTotalPayAmt().toString():""));
							if(null != orderBaseinfoDTO.getPaySerialnumberPosAll()){
								sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getPaySerialnumberPosAll().getTradeAmount().toString()));
								sheet.addCell(new Label(col++,temp,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderBaseinfoDTO.getPaySerialnumberPosAll().getPayTime())));
							}else{
								sheet.addCell(new Label(col++,temp,""));
								sheet.addCell(new Label(col++,temp,""));
							}
							sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getCloseTime()==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderBaseinfoDTO.getCloseTime())));
							sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getShopName()));
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getShopMainCateName()));
							sheet.addCell(new Label(col++,temp,orderProductDetailDTO.getShopOtherCateName()));
							sheet.addCell(new Label(col++,temp,orderBaseinfoDTO.getOrderStatusView()));
							if("1".equals(orderBaseinfoDTO.getActivityType())){
								sheet.addCell(new Label(col++,temp,"无活动"));
							}else if("2".equals(orderBaseinfoDTO.getActivityType())){
								sheet.addCell(new Label(col++,temp,"现场采销"));
							}
						}
					}else{
						System.out.println(orderBaseinfoDTO.getOrderNo()+" 没有商品.......");
					}
				}
			}
			wwb.write();
			wwb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(RowsExceededException e){
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
