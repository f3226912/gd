package com.gudeng.commerce.gd.api.service.impl.pos;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.gudeng.commerce.gd.api.dto.*;
import com.gudeng.commerce.gd.api.dto.output.VClearDetailDto;
import com.gudeng.commerce.gd.api.service.*;
import com.gudeng.commerce.gd.api.service.order.PayCenterApiCommonService;
import com.gudeng.commerce.gd.api.util.*;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant.Order;
import com.gudeng.commerce.gd.api.Constant.PAY_SERIALNUMBER;
import com.gudeng.commerce.gd.api.dto.input.GdOrderActivityApiQueryDTO;
import com.gudeng.commerce.gd.api.dto.input.MemberInfoInputDTO;
import com.gudeng.commerce.gd.api.dto.input.NstBaseResponseDTO;
import com.gudeng.commerce.gd.api.dto.output.OrderProductDTO;
import com.gudeng.commerce.gd.api.dto.output.SellerOrderList2DTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.dto.pay.OrderPayDTO;
import com.gudeng.commerce.gd.api.enums.ClosedReasonEnum;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.api.enums.NstStatusEnum;
import com.gudeng.commerce.gd.api.enums.ProductDeliverStatusEnum;
import com.gudeng.commerce.gd.api.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.api.enums.StatusEnum;
import com.gudeng.commerce.gd.api.service.impl.OrderNoToolServiceImpl;
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.service.order.OrderActivityToolService;
import com.gudeng.commerce.gd.api.service.order.OrderFeeItemDetailToolService;
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.service.v160512.ProductDeliveryDetailToolService;
import com.gudeng.commerce.gd.api.service.v160630.ReOrderCustomerToolService;
import com.gudeng.commerce.gd.api.service.v160929.CertifBaseToolService;
import com.gudeng.commerce.gd.api.thread.AddReMemCustThread;
import com.gudeng.commerce.gd.api.util.pay.AccessSysSignUtil;
import com.gudeng.commerce.gd.api.util.pay.NumUtils;
import com.gudeng.commerce.gd.api.util.pay.SerializeUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.DeliveryAddress;
import com.gudeng.commerce.gd.customer.entity.PageStatisMemberEntity;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.commerce.gd.customer.service.DeliveryAddressService;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.ProductDeliveryDetailDTO;
import com.gudeng.commerce.gd.order.dto.ReOrderCustomerDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

public class OrderTool2ServiceImpl implements OrderTool2Service {

	/**
	 * 记录日志
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderTool2ServiceImpl.class);

	@Autowired
	public GdProperties gdProperties;

	private OrderBaseinfoService orderBaseinfoService;

	private OrderProductDetailService orderProductDetailService;

	private PaySerialnumberService paySerialnumberService;

	private static DeliveryAddressService deliveryAddressService;

	@Autowired
	private ProductToolService productToolService;
	@Autowired
	public OrderSubToolService orderSubToolService;
	@Autowired
	private WalletToolService accInfoToolService;
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private OrderNoToolServiceImpl orderNoToolServiceImpl;
	@Autowired
	private MemberToolService memberToolService;
	@Autowired
	private ReOrderCustomerToolService reOrderCustomerToolService;
	@Autowired
	private DataToolService dataToolService;
	@Autowired
	private PaySerialnumberToolService paySerialnumberToolService;
	@Autowired
	public MemberPageStatisticToolService pageToolService;
	@Autowired
	public CertifBaseToolService certifBaseToolService;
	@Autowired
	private OrderFeeItemDetailToolService orderFeeItemDetailToolService;
	@Autowired
	private OrderActivityToolService orderActivityToolService;
	@Autowired
	private GdOrderActivityBaseToolService gdOrderActivityBaseToolService;
	@Autowired
	private ProductDeliveryDetailToolService productDeliveryDetailToolService;
	@Autowired
	private NstApiCommonService nstApiCommonService;
	@Autowired
	private DeliveryAddressToolService deliveryAddressToolService;
	@Autowired
	private AreaToolService areaToolService;
	@Autowired
	private PayCenterApiCommonService payCenterApiCommonService;

	private OrderBaseinfoService getHessianOrderbaseService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderBaseinfo.url");
		if (orderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBaseinfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, hessianUrl);
		}
		return orderBaseinfoService;
	}

	private OrderProductDetailService getHessianOrderProductService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderProductDetail.url");
		if (orderProductDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderProductDetailService = (OrderProductDetailService) factory.create(OrderProductDetailService.class,
					hessianUrl);
		}
		return orderProductDetailService;
	}

	private PaySerialnumberService getHessianPaySerialnumberService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.paySerialnumberService.url");
		if (paySerialnumberService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			paySerialnumberService = (PaySerialnumberService) factory.create(PaySerialnumberService.class, hessianUrl);
		}
		return paySerialnumberService;
	}

	protected DeliveryAddressService getHessianDeliveryAddressService() throws MalformedURLException {
		String url = gdProperties.getDeliveryAddressServiceUrl();
		if (deliveryAddressService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			deliveryAddressService = (DeliveryAddressService) factory.create(DeliveryAddressService.class, url);
		}
		return deliveryAddressService;
	}

	@Override
	public StatusCodeEnumWithInfo addOrder(OrderAppInputDTO inputDTO) throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo(); // 返回结果
		Long memberId = inputDTO.getMemberId();
		Long businessId = inputDTO.getBusinessId();
		String productDetials = inputDTO.getjProductDetails();
		Double zero = new Double(0);
		Double orderAmount = inputDTO.getOrderAmount();
		Double newOrderAmount = zero;
		Double discountAmount = inputDTO.getDiscountAmount();
		Double userPayAmount = inputDTO.getPayAmount();
		String tradePwd = inputDTO.getTradePwd(); // 交易密码
		String payType = inputDTO.getPayType(); // 支付方式： 1 pos刷卡; 2 现金; 3 余额交易
		String message = inputDTO.getMessage();
		Double marketCommsn = ParamsUtil.getDoubleFromString(inputDTO.getMarketCommsn());
		Integer version = ParamsUtil.getIntFromString(inputDTO.getVersion());
		String distributeMode = inputDTO.getDistributeMode(); // 配送方式

		if (businessId == null) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.BUSINESS_ID_IS_NULL);
			return statusCode;
		}

		// 2 买家下单
		if ("2".equals(inputDTO.getOrderSource())) {
			// 特定商铺Id,需填写配送方式
			String[] businessIds = gdProperties.getProperties().getProperty("GD_SENDNOW_BUSINESS_ID").split(",");
			if (businessIds != null && businessIds.length > 0) {
				for (int i = 0; i < businessIds.length; i++) {
					if (businessIds[i].equals(businessId + "") && StringUtils.isBlank(distributeMode)) {
						statusCode.setStatusCodeEnum(ErrorCodeEnum.DISTRIBUTE_MODE_IS_NULL);
						return statusCode;
					}
				}
			}
		}

		if (StringUtils.isBlank(productDetials)) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_INFO_IS_NULL);
			return statusCode;
		}
		if (orderAmount == null || orderAmount.compareTo(zero) == 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_IS_NULL);
			return statusCode;
		}
		if (userPayAmount == null || userPayAmount.compareTo(zero) < 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PAYAMT_IS_NULL);
			return statusCode;
		}
		if (orderAmount.compareTo(Order.MAX_ORDER_PRICE) > 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_EXCEED_MAX_AMOUNT);
			return statusCode;
		}
		if (userPayAmount.compareTo(Order.MAX_ORDER_PRICE) > 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_EXCEED_MAX_AMOUNT);
			return statusCode;
		}
		if (discountAmount == null) {
			discountAmount = zero;
		}
		// 判断余额是否足够支付
		if (discountAmount.compareTo(zero) > 0) {
			if (memberId == null) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_BUYER_IS_NULL);
				return statusCode;
			}

			AccInfoDTO accInfo = accInfoToolService.getWalletIndex(memberId);
			if (accInfo == null) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ACCOUNT_NOT_EXISTED);
				return statusCode;
			}

			// 校验交易密码
			ErrorCodeEnum validateTradePwdResult = accInfoToolService.validateTransPwd(memberId, tradePwd);
			if (ErrorCodeEnum.SUCCESS != validateTradePwdResult) {
				statusCode.setStatusCodeEnum(validateTradePwdResult);
				return statusCode;
			}

			Double available = accInfo.getBalAvailable();
			if (available.compareTo(discountAmount) < 0) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.WALLET_BALANCE_NOT_ENOUGH);
				return statusCode;
			}
		}

		BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(businessId + "");
		Long sellerId = businessDTO.getUserId();
		if (memberId != null && memberId.compareTo(sellerId) == 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_BUY_YOUR_OWN_PRODUCT);
			return statusCode;
		}

		Long orderNo = new Long(orderNoToolServiceImpl.getOrderNo());
		JSONArray jsonArr = JSONUtils.parseArray(productDetials);
		// 订单信息实体类
		OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
		// 订单产品信息list
		List<OrderProductDetailEntity> entityList = new ArrayList<>();
		// 产品购买数量list， 用于减库存
		List<Map<String, Object>> stockList = new ArrayList<>();
		// 产品id list， 用来查找校验产品信息
		List<Long> pIdList = new ArrayList<>();
		for (int i = 0, len = jsonArr.size(); i < len; i++) {
			JSONObject jsonObject = (JSONObject) jsonArr.get(i);
			Long productId = jsonObject.getLong("productId");
			String productName = jsonObject.getString("productName");
			Double quantity = jsonObject.getDouble("purQuantity");
			Double price = jsonObject.getDouble("price");
			Double payAmount = jsonObject.getDouble("needToPayAmount");

			// 检查产品id和名字是否为空
			if (productId == null || StringUtils.isBlank(productName)) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_ID_IS_NULL);
				return statusCode;
			}

			// 检查商品 数量
			if (quantity == null || quantity.compareTo(zero) == 0) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_PERCHASE_QUANTITY_IS_NULL);
				return statusCode;
			}

			// 检查商品价格
			if (price == null || price.compareTo(zero) == 0) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_PRICE_IS_NULL);
				return statusCode;
			}
			pIdList.add(productId);

			OrderProductDetailEntity entity = new OrderProductDetailEntity();
			entity.setOrderNo(orderNo);
			entity.setProductId(productId.intValue());
			entity.setProductName(productName);
			entity.setPurQuantity(quantity);
			entity.setPrice(price);
			entity.setTradingPrice(payAmount);
			entity.setNeedToPayAmount(payAmount);

			// 卖家匿名下单
			if (memberId == null) {
				entity.setCreateUserId("");
			} else {
				entity.setCreateUserId(memberId + "");
			}

			entityList.add(entity);
		}

		// 设置订单产品单位信息
		List<ProductDto> productList = productToolService.getListByIds(pIdList);
		for (int i = 0, len = productList.size(); i < len; i++) {
			ProductDto pDTO = productList.get(i);
			Double pPrice = pDTO.getPrice();
			Double existedStock = pDTO.getStockCount();
			String state = pDTO.getState();
			if (!"3".equals(state) || existedStock.compareTo(zero) == 0) {// 检查产品是否已下架
				statusCode.setStatusCodeEnum(ErrorCodeEnum.PRODUCT_NO_STOCK_COUNT);
				return statusCode;
			}

			if (pPrice.compareTo(zero) == 0) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.PRODUCT_PRICE_CHANGED);
				return statusCode;
			}

			for (int j = 0, len2 = entityList.size(); j < len2; j++) {
				OrderProductDetailEntity entity = entityList.get(j);
				Integer pId = entity.getProductId();
				Double quantity = entity.getPurQuantity();
				if (pId == pDTO.getProductId().intValue()) {
					Map<String, Object> map = new HashMap<>();
					map.put("productId", pId);
					// 判断库存是否足够
					if (quantity.compareTo(existedStock) > 0) {// 购买量大于库存，返回失败，errorcode=-5
						logger.warn("库存不足, 订单号：" + orderNo + ",产品:" + entity.getProductName() + "的购买数量:" + quantity
								+ " 和库存:" + existedStock);
						statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_STOCKCOUNT_LACK);
						return statusCode;
					} else if (quantity.compareTo(existedStock) == 0) {// 下单后，产品库存为0，不自动下架
						map.put("status", "3");
					} else { // 库存充足
						map.put("status", "3");
					}

					entity.setPrice(pPrice);
					Double productPayAmount = MathUtil.round(MathUtil.mul(pPrice, quantity), 2);
					newOrderAmount = MathUtil.add(newOrderAmount, productPayAmount);
					entity.setNeedToPayAmount(productPayAmount);
					entity.setTradingPrice(productPayAmount);

					map.put("stockCount", MathUtil.sub(existedStock, quantity));
					stockList.add(map);

					entity.setUnit(pDTO.getUnit());
				}
			}
		}

		// 插入订单信息
		orderEntity.setOrderNo(orderNo);
		orderEntity.setOrderSource(inputDTO.getOrderSource()); // 订单来源 1卖家代客下单
		// 2买家下单
		orderEntity.setChannel(inputDTO.getChannel()); // 渠道 1android 2ios 3pc
		orderEntity.setOrderAmount(newOrderAmount);
		orderEntity.setDiscountAmount(discountAmount);
		orderEntity.setPayAmount(userPayAmount.compareTo(orderAmount) == 0 ? newOrderAmount : userPayAmount);
		orderEntity.setTotalPayAmt(orderEntity.getPayAmount());
		orderEntity.setPayType(payType);
		orderEntity.setOrderStatus(Order.STATUS_NOT_PAY);
		orderEntity.setSellMemberId(sellerId.intValue());// 卖家id
		orderEntity.setOrderTime(DateUtil.getNow());
		orderEntity.setShopName(businessDTO.getShopsName());
		orderEntity.setBusinessId(businessId.intValue());
		orderEntity.setMarketId(Integer.parseInt(businessDTO.getMarketId()));
		orderEntity.setCreateTime(DateUtil.getNow());
		orderEntity.setUpdateTime(DateUtil.getNow());
		orderEntity.setOutmarkStatus("0");
		orderEntity.setExamineStatus("0");
		orderEntity.setOrderType(Order.TYPE_FROM_NSY);
		orderEntity.setPromType("0");
		orderEntity.setMessage(message);
		orderEntity.setHasCustomer("0");
		orderEntity.setDistributeMode(distributeMode == null ? "0" : distributeMode);
		String posInfo = businessBaseinfoToolService.getPosInfoByBusinessId(businessId);
		logger.info("Pos info referense: businessId: " + businessId + ", Pos info: " + posInfo);
		orderEntity.setValidPosNum(posInfo);
		// 卖家匿名下单
		if (memberId == null) {
			orderEntity.setCreateUserId("");
			orderEntity.setMemberId(null);// 买家为null
		} else {
			orderEntity.setMemberId(memberId.intValue());// 买家id
			orderEntity.setCreateUserId(memberId + "");
		}

		Map<String, Object> totalMap = new HashMap<String, Object>();
		if (version == 1) {
			/*** 活动相关 START ***/
			GdOrderActivityResultDTO orderActResult = orderActivityToolService.checkOrderActivity(orderEntity, entityList);
			//匹配买家市场佣金
			if ("2".equals(inputDTO.getOrderSource()) && marketCommsn.compareTo(0D) > 0 && orderActResult.getBuyerActInfo() == null) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.PROM_ACT_ENDED);
				return statusCode;
			}
			if ("2".equals(inputDTO.getOrderSource()) && orderActResult.getBuyerActInfo() != null
					&& orderActResult.getBuyerActInfo().getMarketCommision().compareTo(marketCommsn) != 0) {
				Double buyerCommsn = MathUtil.add(orderActResult.getBuyerActInfo().getMarketCommision(), orderActResult.getBuyerActInfo().getPlatCommision());
				if (buyerCommsn.compareTo(marketCommsn) != 0) {
					logger.error("佣金不一致，买家佣金为：" + marketCommsn + ",平台佣金和市场佣金为：" + buyerCommsn);
					statusCode.setStatusCodeEnum(ErrorCodeEnum.BUYERCOMSN_NOT_CORRECT);
					return statusCode;
				}
			}

			if (orderActResult.getBuyerActInfo() != null) {
				Double buyerCommsn = MathUtil.add(orderActResult.getBuyerActInfo().getMarketCommision(), orderActResult.getBuyerActInfo().getPlatCommision());
				orderEntity.setTotalPayAmt(MathUtil.add(orderEntity.getPayAmount(), buyerCommsn));
			}

			//农批商来源要匹配卖家市场佣金
			if ("1".equals(inputDTO.getOrderSource()) && marketCommsn.compareTo(0D) > 0
					&& orderActResult.getSellerActInfo() == null) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.PROM_ACT_ENDED);
				return statusCode;
			}
			if ("1".equals(inputDTO.getOrderSource()) && orderActResult.getSellerActInfo() != null
					&& orderActResult.getSellerActInfo().getMarketCommision().compareTo(marketCommsn) != 0) {
				Double sellerCommsn = MathUtil.add(orderActResult.getSellerActInfo().getMarketCommision(), orderActResult.getSellerActInfo().getPlatCommision());
				if (sellerCommsn.compareTo(marketCommsn) != 0) {
					statusCode.setStatusCodeEnum(ErrorCodeEnum.PROM_ACT_ENDED);
					return statusCode;
				}

				//农批商佣金大于订单实收金额
				if (sellerCommsn.compareTo(orderEntity.getPayAmount()) > 0) {
					statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_COMMISSION_ERROR);
					return statusCode;
				}
			}


			//获取订单商品活动信息
			List<OrderActRelationEntity> orderActList = orderActivityToolService.getOrderRelationDetail(orderActResult, orderNo);
			//获取订单费用信息
			List<OrderFeeItemDetailDTO> orderActFeeList = orderFeeItemDetailToolService.getOrderFeeDetail(orderActResult, orderNo);
			if (orderActList != null && orderActList.size() > 0) {
				totalMap.put("orderActList", orderActList);
			}
			if (orderActFeeList != null && orderActFeeList.size() > 0) {
				totalMap.put("orderActFeeList", orderActFeeList);
			}
			/*** 活动相关 END ***/
		}

		// 插入订单商品明细
		totalMap.put("orderBase", orderEntity);
		totalMap.put("orderProductList", entityList);
		totalMap.put("stockList", stockList); // 减少库存
		// 如果使用了钱包支付, 扣除会员金额
		if (discountAmount.compareTo(zero) > 0) {
			AccInfoDTO accInfo = accInfoToolService.getWalletIndex(memberId);
			Double available = accInfo.getBalAvailable();
			Double total = accInfo.getBalTotal();
			accInfo.setBalAvailable(MathUtil.sub(available, discountAmount));
			accInfo.setBalTotal(MathUtil.sub(total, discountAmount));
			totalMap.put("acctInfo", accInfo);

			// 生成支付订单记录
			PaySerialnumberEntity payRecordEntity = new PaySerialnumberEntity();
			payRecordEntity.setOrderNo(orderNo);
			payRecordEntity.setPayTime(DateUtil.getNow());
			// 支付方式 1钱包余额 2线下刷卡 3现金
			payRecordEntity.setPayType(PAY_SERIALNUMBER.PAYTYPE_WALLET);
			// 支付状态 0未支付 1已支付 9支付失败
			payRecordEntity.setPayStatus(PAY_SERIALNUMBER.STATUS_PAY);
			payRecordEntity.setTradeAmount(discountAmount);
			payRecordEntity.setCreateTime(DateUtil.getNow());
			payRecordEntity.setCreateuserid(memberId + "");
			payRecordEntity.setUpdatetime(DateUtil.getNow());
			payRecordEntity.setMemberId(memberId.intValue());
			totalMap.put("paySerialNumEntity", payRecordEntity);

			// 生成用户金额流水记录
			AccTransInfoDTO accTransInfoDTO = new AccTransInfoDTO();
			accTransInfoDTO.setAccId(accInfo.getAccId());
			accTransInfoDTO.setMemberId(accInfo.getMemberId());
			accTransInfoDTO.setOrderNo(orderNo);
			accTransInfoDTO.setTradeType("1"); // 交易类型 1余额抵扣 2用户补贴 3提现
			accTransInfoDTO.setPeType("2"); // 收支类型 1收入 2支出
			accTransInfoDTO.setTradeAmount(discountAmount);
			accTransInfoDTO.setCreateUserId(accInfo.getMemberId() + "");
			totalMap.put("acctTranInfo", accTransInfoDTO);
		}

		getHessianOrderbaseService().addOrder(totalMap);

		// 清空缓存
		try {
			if (sellerId != null) {
				dataToolService.cleanTradeCacheSpecial(sellerId, TimeCacheType.HOUR_CACHE);
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}

		/*** 保存收货地址BEGIN ***/
		String jsonAddress = inputDTO.getJsonAddress();// 收货地址
		if (StringUtils.isNotEmpty(jsonAddress) && orderNo != null && orderNo > 0) {
			DeliveryAddress entity = JSONObject.parseObject(jsonAddress, DeliveryAddress.class);
			entity.setOrderNo(orderNo);
			entity.setCreateUser(memberId);
			entity.setCreateTime(new Date());
			entity.setStatus("1");
			if (StringUtils.isEmpty(entity.getGender())) {
				entity.setGender("0");
			}
			getHessianDeliveryAddressService().insert(entity);
		}
		/*** 保存收货地址END ***/
		// 新建线程 新增客户关联表
		new AddReMemCustThread(memberToolService, orderEntity).start();
		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		statusCode.setObj(orderNo);
		return statusCode;
	}

	@Override
	public ErrorCodeEnum cancelOrder(OrderAppInputDTO inputDTO) throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		Long memberId = inputDTO.getMemberId();
		if (orderNo == null) {
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}

		if (memberId == null) {
			return ErrorCodeEnum.ORDER_MEMBERID_IS_NULL;
		}

		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if (orderBaseDTO == null) {
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}
		String status = orderBaseDTO.getOrderStatus();
		if (!Order.STATUS_NOT_PAY.equals(status)) {
			return ErrorCodeEnum.ORDER_CANNOT_CANCEL;
		}

		Integer isLock = orderBaseDTO.getIsLock();
		if (isLock != null && Order.ORDER_IS_LOCKED == isLock) {
			return ErrorCodeEnum.ORDER_IS_PAYING;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);


		//如果是卖家
		boolean canDelete = false;
		if (orderBaseDTO.getSellMemberId().intValue() == memberId.intValue()) {
			map.put("cancelUserType", 2); //取消用户类型 1买家 2卖家 3超时
			canDelete = true;
		}

		//如果是买家
		if (orderBaseDTO.getMemberId() != null
				&& orderBaseDTO.getMemberId().intValue() == memberId.intValue()) {
			map.put("cancelUserType", 1); //取消用户类型 1买家 2卖家 3超时
			canDelete = true;
		}

		//如果不存在买家
		if (!canDelete) {
			return ErrorCodeEnum.ORDER_NOT_HAS_AUTHORITY;
		}

		// 组装库存数据， 增加产品库存
		List<OrderProductDetailDTO> productList = getHessianOrderProductService().getListByOrderNo(map);

		List<Map<String, Object>> stockList = new ArrayList<>();
		// 产品id list， 用来查找校验产品信息
		List<Long> pIdList = new ArrayList<>();
		for (int i = 0, len = productList.size(); i < len; i++) {
			OrderProductDetailDTO productDTO = productList.get(i);
			pIdList.add(Long.parseLong(productDTO.getProductId() + ""));
		}
		// 设置订单产品库存
		List<ProductDto> pList = productToolService.getListByIds(pIdList);
		for (int i = 0, len = productList.size(); i < len; i++) {
			OrderProductDetailDTO productDTO = productList.get(i);
			Integer pId = productDTO.getProductId();
			Double purQuan = productDTO.getPurQuantity();
			Map<String, Object> tmpMap = new HashMap<>();
			for (int j = 0, len2 = pList.size(); j < len2; j++) {
				ProductDto pDTO = pList.get(j);
				Double existedStock = pDTO.getStockCount();
				if (pId.equals(pDTO.getProductId().intValue())) {
					tmpMap.put("productId", pId);
					Double newStock = MathUtil.add(existedStock, purQuan);
					// 如果库存大于最大值， 则使用最大值
					if (newStock.compareTo(Order.MAX_PRODUCT_STOCK) > 0) {
						tmpMap.put("stockCount", Order.MAX_PRODUCT_STOCK);
					} else {
						tmpMap.put("stockCount", newStock);
					}

					tmpMap.put("status", "3"); // 产品上架
					stockList.add(tmpMap);
				}
			}
		}
		map.put("stockList", stockList);
		// 取消订单返回用户使用余额
		Double discountAmount = orderBaseDTO.getDiscountAmount();
		if (discountAmount != null && discountAmount.compareTo(new Double(0)) > 0) {
			AccInfoDTO accInfo = accInfoToolService.getWalletIndex(Long.parseLong(orderBaseDTO.getMemberId() + ""));
			Double available = accInfo.getBalAvailable();
			Double frozen = accInfo.getBalblock();
			accInfo.setBalblock(MathUtil.sub(frozen, discountAmount));
			accInfo.setBalAvailable(MathUtil.add(available, discountAmount));
			map.put("acctInfo", accInfo);
			// 生成用户金额流水记录
			AccTransInfoDTO accTransInfoDTO = new AccTransInfoDTO();
			accTransInfoDTO.setAccId(accInfo.getAccId());
			accTransInfoDTO.setMemberId(accInfo.getMemberId());
			accTransInfoDTO.setOrderNo(orderNo);
			accTransInfoDTO.setTradeType("4"); // 交易类型 1余额抵扣 2用户补贴 3提现 4订单返回
			accTransInfoDTO.setPeType("1"); // 收支类型 1收入 2支出
			accTransInfoDTO.setTradeAmount(discountAmount);
			accTransInfoDTO.setCreateUserId(accInfo.getMemberId() + "");
			map.put("acctTranInfo", accTransInfoDTO);
		}

		map.put("closeUserId", memberId + "");
		getHessianOrderbaseService().cancelByOrderNo(map);
		try {
			//取消订单后退款 -start 
			Map params = new HashMap();
			params.put("orderNo", orderNo);
			params.put("serialType", 1);
			List<PaySerialnumberDTO> seri = paySerialnumberToolService.getListByCondition(params);
			if (seri != null && seri.size() > 0) {
				params.put("payCenterNumber", seri.get(0).getStatementId());
				params.put("refundAmt", seri.get(0).getTradeAmount());

				params.remove("serialType");
				params.put("refundUserId", orderBaseDTO.getMemberId());
				//如果是卖家
				if (orderBaseDTO.getSellMemberId().intValue() == memberId.intValue()) {
					params.put("refundReason", "卖家取消");
				}
				//如果是买家
				if (orderBaseDTO.getMemberId() != null
						&& orderBaseDTO.getMemberId().intValue() == memberId.intValue()) {
					params.put("refundReason", "买家取消");
				}
				GdOrderActivityQueryDTO queryDTO = new GdOrderActivityQueryDTO();
				queryDTO.setOrderNo(orderNo + "");
				GdOrderActivityResultDTO resultDto = gdOrderActivityBaseToolService.queryOrderActivty(queryDTO);
				GdOrderActivityDetailDTO detailDto = resultDto.getSellerActInfo();
				/** 卖家违约金 */
				params.put("sellerRefundAmt", detailDto.getSellerPenalty());
				/** 平台违约金 */
				params.put("platRefundAmt", detailDto.getPlatPenalty());
				/** 物流公司违约金 */
				params.put("logisRefundAmt", detailDto.getCompanyPenalty());
				getHessianOrderbaseService().preOrderRefund(params);
			}
			//取消订单后退款 -end
		} catch (Exception e) {
			logger.error("生成退款失败", e);
		}
		try {

			//  取消订单后通知农速通 -start
			Map<String, Object> mapDelivery = new HashMap<>();
			mapDelivery.put("orderNo", orderNo);
			List<ProductDeliveryDetailDTO> pdd = (List<ProductDeliveryDetailDTO>) productDeliveryDetailToolService.getByMap(mapDelivery);
			if (pdd != null && pdd.size() > 0) {
				ProductDeliveryDetailDTO productDeliveryDetailDTO = (ProductDeliveryDetailDTO) pdd.get(0);//产品出货详细
				Long memberAddressId = productDeliveryDetailDTO.getMemberAddressId();

				String url = gdProperties.getNstApiUrl() + NstApiRequestV1Enum.REFUND.getUri();
				String token = nstApiCommonService.getNstToken(orderBaseDTO.getMemberId() + "");
				Map<String, Object> requestParamMap = new HashMap<String, Object>();
				requestParamMap.put("token", token);
				requestParamMap.put("sourceId", memberAddressId);
				NstBaseResponseDTO nstResponse = nstApiCommonService.sendNstRequest(requestParamMap, url);
			}
			//  取消订单后通知农速通 -end 
		} catch (Exception e) {
			logger.error("通知农速通失败", e);
		}
		// 清空缓存
		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				// 撤单时，需要根据下单时间判断是否今日、昨日、本月等，然后清除对应缓存，及清除小时缓存【memberId_report_mmddhh】
				dataToolService.cleanTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
						TimeCacheType.HOUR_CACHE);
				dataToolService.cleanOldTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
						orderBaseDTO.getOrderTime());
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}

		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public OrderDetailAppDTO getOrderDetail(Long orderNo, Long memberId) throws Exception {
		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if (orderBaseDTO == null) {
			return null;
		}

		// 查找订单产品详情
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		//查询出订单是否是现场采销活动，只有为2才表示是现场采销，为2时不需要查询图片
		map.put("activityType", orderBaseDTO.getActivityType());
		List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNo(map);
//		productOrderList = orderSubToolService.addSubIntoProduct(productOrderList);
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		List<OrderProductDTO> productDetailList = new ArrayList<>();
		int productCount = 0;
		int isDeliveringCount = 0; // 正在出货商品数
		int isDeliveredCount = 0; // 已经出货商品数
		if (null != productOrderList && productOrderList.size() > 0) {
			for (int j = 0, pLen = productOrderList.size(); j < pLen; j++) {
				OrderProductDetailDTO pDto = productOrderList.get(j);
				if (orderNo.equals(pDto.getOrderNo())) {
					OrderProductDTO productDTO = setOrderProductInfo(pDto, imageHost);
					Integer status = pDto.getHasDelivered();
					Integer deliveredStatus = ProductDeliverStatusEnum.ALREADY_DELIVERED.getkey();
					Integer deliveringStatus = ProductDeliverStatusEnum.IS_DELIVERING.getkey();
					if (status == deliveredStatus) {
						isDeliveredCount++;
						isDeliveringCount++;
					} else if (status == deliveringStatus) {
						isDeliveringCount++;
					}
					productDTO.setStatus(status);
					productDetailList.add(productDTO);
					productCount++;
				}
			}
		}
		OrderDetailAppDTO orderDetailReturn = new OrderDetailAppDTO();
		orderDetailReturn.setOrderNo(orderBaseDTO.getOrderNo());
		orderDetailReturn.setActivityType(orderBaseDTO.getActivityType());//活动类型1无活动2现场采销
		orderDetailReturn.setActivityIntegral(orderBaseDTO.getActivityIntegral());//活动获取积分
		orderDetailReturn.setMarketId(orderBaseDTO.getMarketId());
		orderDetailReturn.setMemberId(orderBaseDTO.getMemberId());
		orderDetailReturn.setBusinessId(orderBaseDTO.getBusinessId());
		orderDetailReturn.setShopName(orderBaseDTO.getShopName());
		orderDetailReturn.setMobile(orderBaseDTO.getMobile());
		orderDetailReturn.setMessage(orderBaseDTO.getMessage());
		orderDetailReturn.setCloseReason(orderBaseDTO.getCancelReason());
		if (orderDetailReturn.getMobile() != null && orderDetailReturn.getMobile().length() == 32) {
			orderDetailReturn.setMobile("");
		}
		orderDetailReturn.setBuyerName(orderBaseDTO.getRealName());
		orderDetailReturn.setBuyerMobile(orderBaseDTO.getBuyerMobile());
		if (orderDetailReturn.getBuyerMobile() != null && orderDetailReturn.getBuyerMobile().length() == 32) {
			orderDetailReturn.setBuyerMobile("");
		}
		orderDetailReturn.setProductDetails(productDetailList);
		// 特定商铺Id,需填写配送方式
		orderDetailReturn.setDistributeMode(orderBaseDTO.getDistributeMode());
		// 已付款订单设置出货状态
		if (orderBaseDTO.getOrderStatus().equals(Order.STATUS_FINISH)
				|| orderBaseDTO.getOrderStatus().equals(Order.STATUS_PAID)) {
			if (isDeliveredCount == productCount && productCount != 0) {
				orderDetailReturn.setStatus(ProductDeliverStatusEnum.ALREADY_DELIVERED.getkey());
			} else if (isDeliveringCount == productCount && productCount != 0) {
				orderDetailReturn.setStatus(ProductDeliverStatusEnum.IS_DELIVERING.getkey());
			} else if (productCount == 0) {
				orderDetailReturn.setStatus(ProductDeliverStatusEnum.NO_NEED_DELIVERED.getkey());
			} else if (isDeliveringCount > 0) {
				orderDetailReturn.setStatus(ProductDeliverStatusEnum.PARTIAL_DELIVERED.getkey());
			} else {
				orderDetailReturn.setStatus(ProductDeliverStatusEnum.NOT_DELIVER.getkey());
			}
		}

		orderDetailReturn.setOrderAmount(orderBaseDTO.getOrderAmount());
		orderDetailReturn.setDiscountAmount(orderBaseDTO.getDiscountAmount());
		orderDetailReturn.setPayAmount(orderBaseDTO.getPayAmount());
		orderDetailReturn.setCreateTime(DateTimeUtils.formatDate(orderBaseDTO.getCreateTime(), null));
		orderDetailReturn.setCloseTime(DateTimeUtils.formatDate(orderBaseDTO.getCloseTime(), ""));
		orderDetailReturn.setOrderStatus(orderBaseDTO.getOrderStatus());
		orderDetailReturn.setAuditStatus(
				Order.STATUS_FINISH.equals(orderBaseDTO.getOrderStatus()) ? "3" : orderBaseDTO.getExamineStatus());
		orderDetailReturn.setAuditDesc(orderBaseDTO.getAuditDesc());
		// 如果有支付记录
		// 查找订单支付详情
		if ("1".equals(orderBaseDTO.getDistributeMode())) {
			map.put("serialType", 1);//订单为平台配送时，预付款支付时间
			PaySerialnumberDTO prePayDTO = getHessianPaySerialnumberService().getByOrderNo(map);
			if (prePayDTO != null) {
				orderDetailReturn.setPrepayTime(DateTimeUtils.formatDate(prePayDTO.getCreateTime(), ""));
			}
			map.put("serialType", 2);//订单为平台配送时，查尾款支付时间
		}
		PaySerialnumberDTO payDTO = getHessianPaySerialnumberService().getByOrderNo(map);
		if (payDTO != null) {
			orderDetailReturn.setPaySerialNo(payDTO.getStatementId()); // 银行流水号
			orderDetailReturn.setTradeType(setAppPayType(payDTO.getPayType(), 1)); // 支付方式
			orderDetailReturn.setTradeAmount(payDTO.getTradeAmount()); // 交易金额
			orderDetailReturn.setTradeTime(DateTimeUtils.formatDate(payDTO.getCreateTime(), "")); // 成交时间
		}
		// 设置卖家电话
		orderDetailReturn.setMobile(orderBaseDTO.getMobile());
		// 设置客户姓名和是否有客户信息
		if (StringUtils.isBlank(orderBaseDTO.getCustomerMobile())) {
			if (orderBaseDTO.getBuyerType() == null || orderBaseDTO.getBuyerType() == 7) {
				orderDetailReturn.setHasCustomer(0);
			}
		} else {
			orderDetailReturn.setBuyerName(orderBaseDTO.getCustomerName());
			orderDetailReturn.setBuyerMobile(orderBaseDTO.getCustomerMobile());
			orderDetailReturn.setMemberId(ParamsUtil.getIntFromString(orderBaseDTO.getMemberIdStr(), 0));
		}


		//设置补贴和佣金信息
		GdOrderActivityApiQueryDTO inputDTO = new GdOrderActivityApiQueryDTO();
		inputDTO.setOrderNo(orderNo.toString());
		inputDTO.setFlag("1");
		GdOrderActivityResultDTO queryResultDTO = orderActivityToolService.queryActivity(inputDTO);
		if (queryResultDTO == null) {
			return orderDetailReturn;
		}
		if (queryResultDTO.getBuyerActInfo() != null && queryResultDTO.getBuyerActInfo().isHasBuyerCommsn()) {
			orderDetailReturn.setHasBuyerCommsn("1");
		}
		if (queryResultDTO.getSellerActInfo() != null) {
			if (queryResultDTO.getSellerActInfo().isHasSellerCommsn()) {
				orderDetailReturn.setHasSellerCommsn("1");
			}
			if (queryResultDTO.getSellerActInfo().getHasSellerSub()) {
				orderDetailReturn.setHasSellSubPay("1");
				;
			}
		}
		orderFeeItemDetailToolService.setOrderCommAndSubsidyInfo(orderDetailReturn, orderBaseDTO);

		map.put("status", 1);
		List<DeliveryAddressDTO> list = getHessianDeliveryAddressService().getList(map);// 订单收货地址
		if (list != null && list.size() > 0) {
			//查找订单收获地址
			DeliveryAddressDTO deliveryAddressDTO = list.get(0);//订单收获地址记录
			orderDetailReturn.setDeliveryAddress(deliveryAddressDTO);
			String nstOrderNo = deliveryAddressDTO.getNstOrderNo();//农速通运单号
			if (StringUtils.isNotEmpty(nstOrderNo)) {
				//调用农速通接口，获取订单物流详情信息
				//订单物流详情请求地址
				if (memberId != null) {
					map.clear();
					map.put("token", nstApiCommonService.getNstToken(memberId + ""));
					map.put("orderNo", nstOrderNo);
					String url = gdProperties.getNstApiUrl() + NstApiRequestV1Enum.QUERY_ORDER_TRANSFER.getUri();
					String sNstApiResult = HttpClients.doPost(url, Des3.encode(new Gson().toJson(map)));
					String decode = Des3.decode(sNstApiResult);//物流详情解密后的json
					decode = decode.replace("code", "statusCode").replace("result", "object");//适配农速通-api数据结构
					OrderObjectResult obj = (OrderObjectResult) GSONUtils.fromJsonToObject(decode, OrderObjectResult.class);//农速通json结构适配到api项目json结构
					orderDetailReturn.setLogisticaldetail(obj);//订单物流详情
				}
			}
		}
		//2016年12月6日15:17:24 增加预付款，和尾款
		orderDetailReturn.setPrepayAmt(orderBaseDTO.getPrepayAmt());
		orderDetailReturn.setRestAmt(orderBaseDTO.getRestAmt());
		orderDetailReturn.setPenalty(orderBaseDTO.getPenalty());
		orderDetailReturn.setRefundment(orderBaseDTO.getRefundment());
		orderDetailReturn.setNstOrderNo(orderBaseDTO.getNstOrderNo());
//		orderDetailReturn.setPrepayTime(DateUtil.toString(orderBaseDTO.getPrepayTime(),DateUtil.DATE_FORMAT_DATETIME));
		orderDetailReturn.setDeliverTime(DateUtil.toString(orderBaseDTO.getDeliverTime(), DateUtil.DATE_FORMAT_DATETIME));
		orderDetailReturn.setArriveTime(DateUtil.toString(orderBaseDTO.getArriveTime(), DateUtil.DATE_FORMAT_DATETIME));//送达时间
		orderDetailReturn.setClosKey(ClosedReasonEnum.getKey(orderBaseDTO.getCancelReason()));
		orderDetailReturn.setCompanyId(orderBaseDTO.getCompanyId());
		/**
		 * 查找商铺省市区+地址
		 */
		BusinessBaseinfoDTO businessBaseinfoDTO = businessBaseinfoToolService.getById(orderBaseDTO.getBusinessId() + "");
		AreaDTO areaDetail = areaToolService.getAreaDetail(businessBaseinfoDTO.getProvinceId(), businessBaseinfoDTO.getCityId(), businessBaseinfoDTO.getAreaId());
		if (areaDetail != null) {
			orderDetailReturn.setAddress(areaDetail.getpParentName() + areaDetail.getParentName() + areaDetail.getArea() + orderBaseDTO.getAddress());//商铺地址
		} else {
			orderDetailReturn.setAddress(orderBaseDTO.getAddress());//商铺地址
		}
		orderDetailReturn = setWords(orderDetailReturn, orderBaseDTO);
		if (memberId != null) {
			VClearDetailDto feeDetail = payCenterApiCommonService.getFeeInfoByOrderNo2(orderBaseDTO);
			if (feeDetail != null) {
				orderDetailReturn.setPosFee(feeDetail.getFeeAmt());//刷卡手续费
			}
		}
		return orderDetailReturn;
	}

	@Override
	public List<OrderAppReturnDTO> getBuyerOrderList(Map<String, Object> map) throws Exception {
		List<OrderAppReturnDTO> orderList = null;
		Long time1 = System.currentTimeMillis();
		List<OrderBaseinfoDTO> orderBaseList = getHessianOrderbaseService().getListByStatusPage(map);
		Long time2 = System.currentTimeMillis();
		System.out.println("Get buyer order list cost time: " + (time2 - time1));
		if (orderBaseList != null && orderBaseList.size() > 0) {
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}

			String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
//			Long time3 = System.currentTimeMillis();
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService()
					.getListByOrderNoList(orderNoList);
//			Long time4 = System.currentTimeMillis();
//			System.out.println("Get buyer order product list cost time: " + (time4 - time3));
//			productOrderList = orderSubToolService.addSubIntoProduct(productOrderList);
//			Long time5 = System.currentTimeMillis();
//			System.out.println("Get buyer order product sub cost time: " + (time5 - time4));
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				Long orderNo = dto.getOrderNo();
				String status = dto.getOrderStatus();
				OrderAppReturnDTO returnDto = new OrderAppReturnDTO();
				returnDto.setOrderNo(orderNo);
				returnDto.setBusinessId(Long.parseLong(dto.getBusinessId() + ""));
				returnDto.setShopName(dto.getShopName());
				returnDto.setSubStatus("");// 补贴状态
				returnDto.setSubReason("");// 补贴原因
				returnDto.setOrderAmount(dto.getTotalPayAmt());
				returnDto.setFormatOrderAmount(MoneyUtil.formatMoney(dto.getTotalPayAmt()));
				returnDto.setOrderStatus(setOrderStatus(status, true));
				returnDto.setHasSubPay(dto.getHasSub() + "");
				returnDto.setAuditStatus(Order.STATUS_FINISH.equals(status) ? "3" : dto.getExamineStatus());
				returnDto.setAuditDesc(dto.getAuditDesc());
				returnDto.setCreateTime(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
				returnDto.setCloseReason(dto.getCancelReason());
				List<OrderProductDTO> productDetailList = new ArrayList<>();
				if (null != productOrderList && productOrderList.size() > 0) {
					List<String> productNameList = new ArrayList<>();
					StringBuilder productSB = new StringBuilder();
					int productCount = 0;
					for (int j = 0, pLen = productOrderList.size(); j < pLen; j++) {
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if (orderNo.equals(pDto.getOrderNo())) {
							OrderProductDTO productDTO = setOrderProductInfo(pDto, imageHost);
							productDetailList.add(productDTO);
							productNameList.add(pDto.getProductName());
							productSB.append(pDto.getProductName() + ",");
							productCount++;
						}
					}
					returnDto.setProductNameArr(productNameList.toArray(new String[productNameList.size()]));
					if (productCount > 0) {
						returnDto.setProductsName(productSB.deleteCharAt(productSB.length() - 1).toString());
					}
				}
				returnDto.setProductDetails(productDetailList);
				returnDto.setDistributeMode(dto.getDistributeMode());
				returnDto.setRestAmt(dto.getRestAmt());
				returnDto.setNstOrderNo(dto.getNstOrderNo());
				returnDto.setArriveTime(dto.getArriveTime());
				returnDto.setCompanyId(dto.getCompanyId());
				returnDto.setPrepayAmt(dto.getPrepayAmt());
				returnDto.setSellMemberId(dto.getSellMemberId());
				returnDto.setActivityType(dto.getActivityType());//活动类型1无活动2现场采销
				returnDto.setActivityIntegral(dto.getActivityIntegral());//活动获取积分
				returnDto = setWords(returnDto, dto);
				orderList.add(returnDto);
			}
			Long time6 = System.currentTimeMillis();
			System.out.println("Finished buyer order list cost time: " + (time6 - time1));
		}

		return orderList;
	}

	@Override
	public int getOrdersTotal(Map<String, Object> map) throws Exception {
		return getHessianOrderbaseService().getTotalByStatusPage(map);
	}

	@Override
	public OrderBaseinfoDTO getOrderByOrderNo(Long orderNo) throws Exception {
		return getHessianOrderbaseService().getByOrderNo(orderNo);
	}

	@Override
	public List<OrderProductDetailDTO> getOrderProductsByOrderNo(Long orderNo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		return getHessianOrderProductService().getListByOrderNo(map);
	}

	@Override
	public List<SellerOrderListDTO> getSellerOrderList(Map<String, Object> map) throws Exception {
		List<SellerOrderListDTO> orderList = null;
		List<OrderBaseinfoDTO> orderBaseList = getHessianOrderbaseService().getListByStatusPage(map);
		if (orderBaseList != null && orderBaseList.size() > 0) {
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}

			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService()
					.getListByOrderNoList(orderNoList);
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				SellerOrderListDTO returnDto = new SellerOrderListDTO();
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				Long orderNo = dto.getOrderNo();
				returnDto.setOrderNo(orderNo);
				returnDto.setCreateDate(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
				returnDto.setOrderAmount(dto.getPayAmount());
				returnDto.setFormattedPrice(MoneyUtil.formatMoney(dto.getPayAmount()));
				returnDto.setOrderStatus(setOrderStatus(dto.getOrderStatus(), false));
				returnDto.setBuyerName(dto.getRealName());
				List<String> productNameList = new ArrayList<>();
				StringBuilder productSB = new StringBuilder();
				int productCount = 0;
				if (null != productOrderList && productOrderList.size() > 0) {
					for (int j = 0, pLen = productOrderList.size(); j < pLen; j++) {
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if (orderNo.equals(pDto.getOrderNo())) {
							productNameList.add(pDto.getProductName());
							productSB.append(pDto.getProductName() + ",");
							productCount++;
						}
					}
				}
				returnDto.setAuditStatus(
						Order.STATUS_FINISH.equals(dto.getOrderStatus()) ? "3" : dto.getExamineStatus());
				returnDto.setAuditDesc(dto.getAuditDesc());
				returnDto.setProductNameArr(productNameList.toArray(new String[productNameList.size()]));
				if (productCount > 0) {
					returnDto.setProductsName(productSB.deleteCharAt(productSB.length() - 1).toString());
				}

				returnDto.setProductNum(productCount);
				orderList.add(returnDto);
			}
		}
		return orderList;
	}

	@Override
	public ErrorCodeEnum confirmReceive(OrderAppInputDTO inputDTO) throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		Long memberId = inputDTO.getMemberId();
		if (orderNo == null) {
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}

		if (memberId == null) {
			return ErrorCodeEnum.ORDER_SELLER_IS_NULL;
		}

		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if (orderBaseDTO == null) {
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}

		if (orderBaseDTO.getSellMemberId() != memberId.intValue()) {
			return ErrorCodeEnum.ORDER_NOT_ORDER_SELLER;
		}

		// 校验是否未支付状态
		if (!Order.STATUS_NOT_PAY.equals(orderBaseDTO.getOrderStatus())) {
			return ErrorCodeEnum.ORDER_CANNOT_CONFIRM_RECEIVABLES;
		}

		orderBaseDTO.setOrderStatus(Order.STATUS_PAID);
		getHessianOrderbaseService().updateByOrderNo(orderBaseDTO);

		// 清空缓存
		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				// 撤单时，需要根据下单时间判断是否今日、昨日、本月等，然后清除对应缓存，及清除小时缓存【memberId_report_mmddhh】
				dataToolService.cleanTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
						TimeCacheType.HOUR_CACHE);
				dataToolService.cleanOldTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
						orderBaseDTO.getOrderTime());
				dataToolService.cleanGoodsCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
						TimeCacheType.HOUR_CACHE);
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public int getSubListTotal(Map<String, Object> map) throws Exception {
		return orderSubToolService.getSubListTotal(map);
	}

	@Override
	public List<OrderAppReturnDTO> getSubList(Map<String, Object> map) throws Exception {
		List<OrderAppReturnDTO> orderList = new ArrayList<>();
		List<SubAuditDTO> subList = orderSubToolService.getSubList(map);
		if (subList != null && subList.size() > 0) {
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for (int i = 0, len = subList.size(); i < len; i++) {
				SubAuditDTO dto = subList.get(i);
				orderNoList.add(dto.getOrderNo());
			}

			String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService()
					.getListByOrderNoList(orderNoList);
			productOrderList = orderSubToolService.addSubIntoProduct(productOrderList);
			for (int i = 0, len = subList.size(); i < len; i++) {
				SubAuditDTO dto = subList.get(i);
				Long orderNo = dto.getOrderNo();
				OrderAppReturnDTO returnDto = new OrderAppReturnDTO();
				returnDto.setOrderNo(orderNo);
				returnDto.setSubDate(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));
				returnDto.setBusinessId(dto.getBusinessId());
				returnDto.setShopName(dto.getBuyerShop());
				returnDto.setOrderAmount(dto.getOrderAmount());
				returnDto.setBuyerSubAmount(dto.getSubAmount());
				returnDto.setSellerSubAmount(dto.getSellSubAmount());
				List<OrderProductDTO> productDetailList = new ArrayList<>();
				if (null != productOrderList && productOrderList.size() > 0) {
					for (int j = 0, pLen = productOrderList.size(); j < pLen; j++) {
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if (orderNo.equals(pDto.getOrderNo())) {
							OrderProductDTO productDTO = setOrderProductInfo(pDto, imageHost);
							productDetailList.add(productDTO);
						}
					}
				}
				returnDto.setProductDetails(productDetailList);
				// 设置补贴信息
				setAppSubInfo(returnDto, dto);
				orderList.add(returnDto);
			}
		}
		return orderList;
	}

	@Override
	public List<Map<String, String>> getENongOrderList(Map<String, Object> map) throws Exception {
		List<Map<String, String>> eNongOrderList = new ArrayList<>();
		List<OrderBaseinfoDTO> orderBaseList = getHessianOrderbaseService().getUnpaidOrderList(map);
		if (orderBaseList != null && orderBaseList.size() > 0) {
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				String mobile = dto.getBuyerMobile();
				Map<String, String> tmpMap = new HashMap<>();
				// 谷登订单号
				tmpMap.put("orderno", dto.getOrderNo() + "");
				// 订单金额
				tmpMap.put("orderfee", MoneyUtil.formatToCentsUnit(dto.getPayAmount()));
				// 收款方名称（预留）
				tmpMap.put("accountname", StringUtils.isBlank(dto.getShopName()) ? "" : dto.getShopName());
				// 付款方名称（买家手机号后四位）
				tmpMap.put("customname", StringUtils.isBlank(mobile) ? "8888" : mobile.substring(mobile.length() - 4));
				eNongOrderList.add(tmpMap);
			}
		}
		return eNongOrderList;
	}

	private String setAppPayType(String payType, int type) {
		String returnStr = "未知";
		switch (payType) {// 支付方式 1 钱包余额 2 POS刷卡 3 现金
			case PAY_SERIALNUMBER.PAYTYPE_WALLET:
				returnStr = "钱包余额";
				break;
			case PAY_SERIALNUMBER.PAYTYPE_BANKCARD:
				returnStr = "POS刷卡";
				break;
			case PAY_SERIALNUMBER.PAYTYPE_CASH:
				returnStr = "现金";
				break;
			case PAY_SERIALNUMBER.PAYTYPE_WALLET_AND_BANKCARD:
				if (type == 1) {
					returnStr = "POS刷卡 + 钱包余额";
				} else {
					returnStr = "POS刷卡";
				}

				break;
			case PAY_SERIALNUMBER.PAYTYPE_WALLET_AND_CASH:
				if (type == 1) {
					returnStr = "现金 + 钱包余额";
				} else {
					returnStr = "现金";
				}
				break;
		}
		return returnStr;
	}

	private void setAppSubInfo(OrderAppReturnDTO returnDto, SubAuditDTO dto) {
		// 补贴状态 1待补贴 2系统驳回 3已补贴 4不予补贴
		String status = dto.getSubStatus();
		String reason = dto.getSubComment();
		switch (status) {
			case "1":
				status = "待人工审核";
				reason = "";
				break;
			case "3":
				status = "已补贴";
				reason = "";
				break;
			case "2":
				status = "系统驳回";
				break;
			case "4":
				status = "不予补贴";
				break;
		}
		returnDto.setSubStatus(status);
		returnDto.setSubReason(reason);
	}

	/**
	 * 修改订单状态 兼容旧版的订单
	 *
	 * @param orderStatus
	 * @param isBuyer
	 * @return
	 */
	private String setOrderStatus(String orderStatus, boolean isBuyer) {
		// 订单状态 1未付款 3已付款 8用户取消 9系统取消 10已完成->已付款
		switch (orderStatus) {
			case "9":
				if (isBuyer) {
					orderStatus = "8";
				}
				break;
			case "10":
				orderStatus = "3";
				break;
			default:
				break;
		}
		return orderStatus;
	}

	@Override
	public List<OrderBaseinfoDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return getHessianOrderbaseService().getListByCondition(map);
	}

	@Override
	public List<SellerOrderList2DTO> getSellerOrderList2(Map<String, Object> map) throws Exception {
		List<SellerOrderList2DTO> orderList = null;
		List<OrderBaseinfoDTO> orderBaseList = getHessianOrderbaseService().getListByStatusPage(map);
		if (orderBaseList != null && orderBaseList.size() > 0) {
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}

			String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService()
					.getListByOrderNoList(orderNoList);
//			productOrderList = orderSubToolService.addSubIntoProduct(productOrderList);
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				Long orderNo = dto.getOrderNo();
				SellerOrderList2DTO returnDto = new SellerOrderList2DTO();
				returnDto.setOrderNo(orderNo);
				returnDto.setDistributeMode(dto.getDistributeMode());//设置配送信息
				returnDto.setActivityType(dto.getActivityType());//活动类型1无活动2现场采销
				returnDto.setActivityIntegral(dto.getActivityIntegral());//活动获取积分
				returnDto.setBuyerName(dto.getRealName());
				returnDto.setMemberId(dto.getMemberId() == null ? null : dto.getMemberId().longValue());
				Double payAmt = MathUtil.sub(dto.getPayAmount(), dto.getSellerCommision());
				returnDto.setOrderAmount(payAmt);
				returnDto.setFormattedPrice(MoneyUtil.formatMoneyWithZero(payAmt));
				returnDto.setPayAmount(MoneyUtil.formatMoneyWithZero(payAmt));
				returnDto.setOrderStatus(dto.getOrderStatus());
				returnDto.setCreateTime(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));
				returnDto.setCompanyId(dto.getCompanyId());//物流公司ID
				returnDto.setArriveTime(DateUtil.getDate(dto.getArriveTime(), DateUtil.DATE_FORMAT_DATETIME));//送达时间
				returnDto.setTotalPayAmt(dto.getTotalPayAmt()); //'总共支付(含佣金)',
				List<OrderProductDTO> productDetailList = new ArrayList<>();
				int productCount = 0;
				if (null != productOrderList && productOrderList.size() > 0) {
					for (int j = 0, pLen = productOrderList.size(); j < pLen; j++) {
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if (orderNo.equals(pDto.getOrderNo())) {
							OrderProductDTO productDTO = setOrderProductInfo(pDto, imageHost);
							productDetailList.add(productDTO);
							productCount++;
						}
					}
				}
				returnDto.setProductNum(productCount);
				returnDto.setProductDetails(productDetailList);
				// 设置客户姓名和是否有客户信息
				if (StringUtils.isBlank(dto.getCustomerMobile())) {
					if (dto.getBuyerType() == null || dto.getBuyerType() == 7) {
						returnDto.setHasCustomer(0);
					}
				} else {
					returnDto.setBuyerName(dto.getCustomerName());
					returnDto.setMemberId(ParamsUtil.getLongFromString(dto.getMemberIdStr(), 0L));
				}
				orderList.add(returnDto);
			}
		}
		return orderList;
	}

	@Override
	public int getDeliveredProductTotal(Map<String, Object> map) throws Exception {
		return getHessianOrderbaseService().getDeliveredProductTotal(map);
	}

	@Override
	public List<ProductDeliverListAppDTO> getDeliveredProductList(Map<String, Object> map) throws Exception {
		List<ProductDeliverListAppDTO> orderList = null;
		List<OrderBaseinfoDTO> orderBaseList = getHessianOrderbaseService().getDeliveredProductList(map);
		if (orderBaseList != null && orderBaseList.size() > 0) {
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}

			String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService()
					.getListByOrderNoList(orderNoList);
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				Long orderNo = dto.getOrderNo();
				ProductDeliverListAppDTO returnDto = new ProductDeliverListAppDTO();
				returnDto.setOrderNo(orderNo);
				returnDto.setBusinessId(Long.parseLong(dto.getBusinessId() + ""));
				returnDto.setShopName(dto.getShopName());
				returnDto.setMemberId(dto.getMemberId());
				returnDto.setBuyerName(dto.getRealName());
				returnDto.setCreateTime(DateUtil.getDate(dto.getUpdateTime(), DateUtil.DATE_FORMAT_DATEONLY));
				List<OrderProductDTO> productDetailList = new ArrayList<>();
				int productCount = 0;
				int isDeliveringCount = 0; // 正在出货商品数
				int isDeliveredCount = 0; // 已经出货商品数
				if (null != productOrderList && productOrderList.size() > 0) {
					for (int j = 0, pLen = productOrderList.size(); j < pLen; j++) {
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if (orderNo.equals(pDto.getOrderNo())) {
							OrderProductDTO productDTO = setOrderProductInfo(pDto, imageHost);
							Integer status = pDto.getHasDelivered();
							Integer deliveredStatus = ProductDeliverStatusEnum.ALREADY_DELIVERED.getkey();
							Integer deliveringStatus = ProductDeliverStatusEnum.IS_DELIVERING.getkey();
							if (status == deliveredStatus) {
								isDeliveredCount++;
								isDeliveringCount++;
							} else if (status == deliveringStatus) {
								isDeliveringCount++;
							}
							productDTO.setStatus(status);
							productDetailList.add(productDTO);
							productCount++;
						}
					}
				}
				if (isDeliveredCount == productCount && productCount != 0) {
					returnDto.setStatus(ProductDeliverStatusEnum.ALREADY_DELIVERED.getkey());
				} else if (isDeliveringCount == productCount && productCount != 0) {
					returnDto.setStatus(ProductDeliverStatusEnum.IS_DELIVERING.getkey());
				} else if (productCount == 0) {
					returnDto.setStatus(ProductDeliverStatusEnum.ALREADY_DELIVERED.getkey());
				} else {
					returnDto.setStatus(ProductDeliverStatusEnum.NOT_DELIVER.getkey());
				}
				returnDto.setProductDetails(productDetailList);
				orderList.add(returnDto);
			}
		}
		return orderList;
	}

	@Override
	public List<OrderProductDetailDTO> getListByOrderNoList(List<Long> orderNoList) throws Exception {
		return getHessianOrderProductService().getListByOrderNoList(orderNoList);
	}

	@Override
	public PageQueryResultDTO<SellerOrderList2DTO> searchSellerOrderList(Map<String, Object> map) throws Exception {
		List<SellerOrderList2DTO> orderList = null;
		PageQueryResultDTO<SellerOrderList2DTO> returnPageDTO = new PageQueryResultDTO<SellerOrderList2DTO>();
		PageQueryResultDTO<OrderBaseinfoDTO> pageDTO = getHessianOrderbaseService().searchSellerOrderList(map);
		List<OrderBaseinfoDTO> orderBaseList = pageDTO.getDataList();
		if (orderBaseList != null && orderBaseList.size() > 0) {
			String imageHost = gdProperties.getProperties().getProperty("gd.image.server");

			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}

			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService()
					.getListByOrderNoList(orderNoList);
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				Long orderNo = dto.getOrderNo();
				SellerOrderList2DTO returnDto = setOrderBaseInfo(dto);
				List<OrderProductDTO> productDetailList = new ArrayList<>();
				int productCount = 0;
				if (null != productOrderList && productOrderList.size() > 0) {
					for (int j = 0, pLen = productOrderList.size(); j < pLen; j++) {
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if (orderNo.equals(pDto.getOrderNo())) {
							OrderProductDTO productDTO = setOrderProductInfo(pDto, imageHost);
							productDetailList.add(productDTO);
							productCount++;
						}
					}
				}
				returnDto.setProductNum(productCount);
				returnDto.setProductDetails(productDetailList);
				orderList.add(returnDto);
			}

			returnPageDTO.setTotalCount(pageDTO.getTotalCount());
			returnPageDTO.setDataList(orderList);
		}

		return returnPageDTO;
	}

	private OrderProductDTO setOrderProductInfo(OrderProductDetailDTO pDto, String imageHost) {
		OrderProductDTO productDTO = new OrderProductDTO();
		productDTO.setOrderNo(pDto.getOrderNo());
		productDTO.setFormattedPrice(MoneyUtil.formatMoney(pDto.getPrice()));
		productDTO.setHasBuySub(pDto.getHasBuySub());
		productDTO.setHasSellSub(pDto.getHasSellSub());
		productDTO.setImageUrl(imageHost + pDto.getImageUrl());
		productDTO.setNeedToPayAmount(pDto.getNeedToPayAmount());
		productDTO.setPrice(pDto.getPrice());
		productDTO.setProductId(pDto.getProductId());
		productDTO.setProductName(pDto.getProductName());
		productDTO.setPurQuantity(pDto.getPurQuantity());
		productDTO.setTradingPrice(pDto.getTradingPrice());
		productDTO.setUnitName(pDto.getUnitName());
		return productDTO;
	}

	private SellerOrderList2DTO setOrderBaseInfo(OrderBaseinfoDTO dto) {
		SellerOrderList2DTO returnDto = new SellerOrderList2DTO();
		returnDto.setOrderNo(dto.getOrderNo());
		returnDto.setBuyerName(dto.getRealName());
		returnDto.setMemberId(dto.getMemberId() == null ? null : dto.getMemberId().longValue());
		returnDto.setOrderAmount(dto.getPayAmount());
		returnDto.setFormattedPrice(MoneyUtil.formatMoney(dto.getPayAmount()));
		returnDto.setOrderStatus(dto.getOrderStatus());
		returnDto.setCreateTime(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));
		// 设置客户姓名和是否有客户信息
		if (StringUtils.isBlank(dto.getCustomerMobile())) {
			if (dto.getBuyerType() == null || dto.getBuyerType() == 7) {
				returnDto.setHasCustomer(0);
			}
		} else {
			returnDto.setBuyerName(dto.getCustomerName());
			returnDto.setMemberId(ParamsUtil.getLongFromString(dto.getMemberIdStr(), 0L));
		}
		return returnDto;
	}

	@Override
	public ErrorCodeEnum addProductInfo(OrderAppInputDTO inputDTO) throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		String productDetials = inputDTO.getjProductDetails();
		Long sellerId = inputDTO.getMemberId();
		if (orderNo == null) {
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}
		if (sellerId == null) {
			return ErrorCodeEnum.ORDER_SELLER_IS_NULL;
		}
		if (StringUtils.isBlank(productDetials)) {
			return ErrorCodeEnum.ORDER_PRODUCT_INFO_IS_NULL;
		}

		OrderBaseinfoDTO orderBaseDTO = getOrderByOrderNo(orderNo);
		if (orderBaseDTO == null) {
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}

		if (orderBaseDTO.getSellMemberId() != sellerId.intValue()) {
			return ErrorCodeEnum.ORDER_NOT_HAS_AUTHORITY;
		}

		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderNo);
		List<OrderProductDetailDTO> productList = getHessianOrderProductService().getListByOrderNo(map);
		if (productList != null && productList.size() > 1) {
			return ErrorCodeEnum.ORDER_ALREADY_HAS_PRODUCT;
		}

		// 订单产品信息list
		List<OrderProductDetailEntity> entityList = new ArrayList<>();
		// 产品id list， 用来查找校验产品信息
		List<Long> pIdList = new ArrayList<>();

		// 校验提交的商品id和购买数量是否合法
		ErrorCodeEnum result = checkProductDetails(productDetials, pIdList, entityList, orderBaseDTO);
		if (ErrorCodeEnum.SUCCESS != result) {
			return result;
		}

		// 检查商品状态 并填充商品信息
		result = checkProductStatus(pIdList, entityList, orderBaseDTO);
		if (ErrorCodeEnum.SUCCESS != result) {
			return result;
		}
		OrderBaseinfoDTO newOrderBaseDTO = new OrderBaseinfoDTO();
		newOrderBaseDTO.setOrderNo(orderBaseDTO.getOrderNo());
		newOrderBaseDTO.setHasCustomer("1");
		newOrderBaseDTO.setOrderAmount(orderBaseDTO.getOrderAmount());
		int count = getHessianOrderbaseService().updateByOrderNo(newOrderBaseDTO);
		if (count > 0) {
			getHessianOrderProductService().batchInsertEntity(entityList);
		}

		// 清空缓存
		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				// 补充商品时，需要根据支付时间判断是否今日、昨日、本月等，然后清除对应缓存，及清除小时缓存【memberId_report_mmddhh】
				PaySerialnumberDTO payDto = paySerialnumberToolService.getByOrderNo(orderBaseDTO.getOrderNo());
				if (DateUtil.getDate(payDto.getPayTime(), "yyyy-MM-dd HH")
						.equals(DateUtil.getDate(new Date(), "yyyy-MM-dd HH"))) { // 当前小时
					dataToolService.cleanTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
							TimeCacheType.HOUR_CACHE);
					dataToolService.cleanGoodsCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
							TimeCacheType.HOUR_CACHE);
				} else {
					dataToolService.cleanOldTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
							payDto.getPayTime());
					dataToolService.cleanOldGoodsCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
							payDto.getPayTime());
				}
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}
		return ErrorCodeEnum.SUCCESS;
	}

	/**
	 * 检查商品状态 并填充商品信息
	 *
	 * @param pIdList
	 * @param entityList
	 * @return 正确返回 "OK"
	 * @throws Exception
	 */
	private ErrorCodeEnum checkProductStatus(List<Long> pIdList, List<OrderProductDetailEntity> entityList,
											 OrderBaseinfoDTO orderDTO) throws Exception {
		Double totalAmt = 0D;
		// 设置订单商品单位信息
		List<ProductDto> productList = productToolService.getListByIds(pIdList);
		for (int i = 0, len = productList.size(); i < len; i++) {
			ProductDto pDTO = productList.get(i);
			Double pPrice = pDTO.getPrice();
			String state = pDTO.getState();
			// 检查商品是否是上架或者下架状态
			if (!ProductStatusEnum.ON.getkey().equals(state) && !ProductStatusEnum.OFF.getkey().equals(state)) {
				logger.warn("[WARNING]ProductId: " + pDTO.getProductId() + ", status: " + state);
				return ErrorCodeEnum.ORDER_PUT_ON_PRODUCT;
			}

			for (int j = 0, len2 = entityList.size(); j < len2; j++) {
				OrderProductDetailEntity entity = entityList.get(j);
				Integer pId = entity.getProductId();
				Double quantity = entity.getPurQuantity();
				if (pId == pDTO.getProductId().intValue()) {
					if (entity.getPrice() != null) {
						pPrice = entity.getPrice();
					} else {
						entity.setPrice(pPrice);
					}
					Double productPayAmount = MathUtil.round(MathUtil.mul(pPrice, quantity), 2);
					totalAmt = MathUtil.add(totalAmt, productPayAmount);
					entity.setProductName(pDTO.getProductName());
					entity.setNeedToPayAmount(productPayAmount);
					entity.setTradingPrice(productPayAmount);
					entity.setUnit(pDTO.getUnit());
				}
			}
		}

		// 校验商品最大金额
		if (totalAmt.compareTo(Order.MAX_ORDER_PRICE) > 0) {
			return ErrorCodeEnum.ORDER_EXCEED_MAX_AMOUNT;
		}
		orderDTO.setOrderAmount(totalAmt);

		return ErrorCodeEnum.SUCCESS;
	}

	/**
	 * 校验提交的商品id和购买数量是否合法
	 *
	 * @param productDetials
	 * @param pIdList
	 * @param entityList
	 * @param orderBaseDTO
	 * @return 正确返回 "OK"
	 */
	public ErrorCodeEnum checkProductDetails(String productDetials, List<Long> pIdList,
											 List<OrderProductDetailEntity> entityList, OrderBaseinfoDTO orderBaseDTO) {
		if (StringUtils.isBlank(productDetials)) {
			return ErrorCodeEnum.ORDER_PRODUCT_INFO_IS_NULL;
		}

		Double zero = new Double(0);
		JSONArray jsonArr = JSONUtils.parseArray(productDetials);
		for (int i = 0, len = jsonArr.size(); i < len; i++) {
			JSONObject jsonObject = (JSONObject) jsonArr.get(i);
			Long productId = jsonObject.getLong("productId");
			Double quantity = jsonObject.getDouble("purQuantity");
			Double price = jsonObject.getDouble("price");

			// 检查商品id是否为空
			if (productId == null || productId.equals(0L)) {
				return ErrorCodeEnum.ORDER_PRODUCT_ID_IS_NULL;
			}

			// 检查商品订购数量
			if (quantity == null || quantity.compareTo(zero) == 0) {
				return ErrorCodeEnum.ORDER_PRODUCT_PERCHASE_QUANTITY_IS_NULL;
			}

			OrderProductDetailEntity entity = new OrderProductDetailEntity();
			entity.setOrderNo(orderBaseDTO.getOrderNo());
			entity.setProductId(productId.intValue());
			entity.setPurQuantity(quantity);
			entity.setCreateUserId(orderBaseDTO.getSellMemberId().toString());

			// 检查商品价格
			if (price != null && price.compareTo(zero) > 0) {
				entity.setPrice(price);
			}
			entityList.add(entity);

			pIdList.add(productId);
		}

		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public ErrorCodeEnum addBuyerInfo(MemberInfoInputDTO inputDTO, boolean hasProductInfo) throws Exception {
		Long orderNo = ParamsUtil.getLongFromString(inputDTO.getOrderNo(), null);
		String mobile = inputDTO.getMobile();
		String realName = inputDTO.getRealName();
		Integer sellerId = ParamsUtil.getIntFromString(inputDTO.getMemberId(), null);
		Integer version = ParamsUtil.getIntFromString(inputDTO.getVersion());
		if (orderNo == null) {
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}
		if (sellerId == null) {
			return ErrorCodeEnum.ORDER_SELLER_IS_NULL;
		}
		if (StringUtils.isBlank(mobile)) {
			return ErrorCodeEnum.MOBILE_IS_EMPTY;
		}
		if (!Validator.isMobile(mobile)) {
			return ErrorCodeEnum.MOBILE_INCORRECT;
		}
		OrderBaseinfoDTO orderBaseDTO = getOrderByOrderNo(orderNo);
		if (orderBaseDTO == null) {
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}
		if (!orderBaseDTO.getSellMemberId().equals(sellerId)) {
			logger.warn("[WARN] OrderNo: " + orderNo + ", seller id: " + orderBaseDTO.getSellMemberId()
					+ ", input member id: " + sellerId);
			return ErrorCodeEnum.ORDER_NOT_HAS_AUTHORITY;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (hasProductInfo) {

			Map<String, Object> mapTmp = new HashMap<>();
			mapTmp.put("orderNo", orderNo);
			List<OrderProductDetailDTO> productList = getHessianOrderProductService().getListByOrderNo(mapTmp);
			if (productList == null || productList.size() < 1) {
				// 订单产品信息list
				List<OrderProductDetailEntity> entityList = new ArrayList<>();
				// 产品id list， 用来查找校验产品信息
				List<Long> pIdList = new ArrayList<>();

				// 校验提交的商品id和购买数量是否合法
				ErrorCodeEnum result = checkProductDetails(inputDTO.getjProductDetails(), pIdList, entityList,
						orderBaseDTO);
				if (ErrorCodeEnum.SUCCESS != result) {
					return result;
				}

				// 检查商品状态 并填充商品信息
				result = checkProductStatus(pIdList, entityList, orderBaseDTO);
				if (ErrorCodeEnum.SUCCESS != result) {
					return result;
				}

				map.put("productEntityList", entityList);
			}
		}

		ReOrderCustomerDTO reOrderCustomer = reOrderCustomerToolService.getByOrderNo(orderNo);
		if (reOrderCustomer != null) {
			return ErrorCodeEnum.ORDER_ALREADY_HAS_CUSTOMER;
		}

		// 添加订单客户关系
		reOrderCustomer = new ReOrderCustomerDTO();
		reOrderCustomer.setOrderNo(orderNo);
		reOrderCustomer.setOrderBuyerId(orderBaseDTO.getMemberId());
		reOrderCustomer.setMobile(mobile);
		reOrderCustomer.setRealName(realName);
		reOrderCustomer.setCreateTime(new Date());
		reOrderCustomer.setCreateUserId(sellerId.toString());

		MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(mobile);
		if (memberDTO == null) {
			// 用户不存在则自动注册
			Long mId = memberToolService.autoRegister(mobile, realName, MemberBaseinfoEnum.REGETYPE_4.getKey());
			reOrderCustomer.setMemberId(mId.intValue());
			orderBaseDTO.setMemberId(mId.intValue());
		} else {
			Integer memberId = memberDTO.getMemberId().intValue();
			if (memberId.equals(orderBaseDTO.getSellMemberId())) {
				return ErrorCodeEnum.ORDER_BUYER_EQUAL_SELLER;
			}

			reOrderCustomer.setMemberId(memberId);
			orderBaseDTO.setMemberId(memberId);
		}

		//未付款补充客户需要增加买家佣金
		logger.info("version: " + version + ", orderNo: " + orderNo + ", order status: " + orderBaseDTO.getOrderStatus());
		if (version == 1 && Order.STATUS_NOT_PAY.equals(orderBaseDTO.getOrderStatus())) {
			GdOrderActivityApiQueryDTO queryDTO = new GdOrderActivityApiQueryDTO();
			queryDTO.setOrderNo(orderNo.toString());
			queryDTO.setBuyerId(orderBaseDTO.getMemberId().toString());
			GdOrderActivityResultDTO actResult = orderActivityToolService.queryActivity(queryDTO);
//			logger.info("Buyer memberId: " + orderBaseDTO.getMemberId()
//					+ ", act info: " + actResult.toString());
			if(actResult != null){
			if (actResult.getBuyerActInfo() != null) {
				logger.info("Added buyer fee: " + actResult.getBuyerActInfo().getMarketCommision());
				orderBaseDTO.setTotalPayAmt(MathUtil.add(orderBaseDTO.getPayAmount(),
						actResult.getBuyerActInfo().getMarketCommision()));

				//获取订单买家费用信息
				OrderFeeItemDetailEntity actFeeEntity = orderFeeItemDetailToolService.getOrderFeeEntityByType(orderNo, actResult.getBuyerActInfo().getMarketCommision(), 1);
				if (actFeeEntity != null) {
					logger.info("Successfully create fee item detail entity.");
					map.put("actFeeEntity", actFeeEntity);
				}
			}
			}
		}

		OrderBaseinfoDTO newOrderBaseDTO = new OrderBaseinfoDTO();
		newOrderBaseDTO.setOrderNo(orderBaseDTO.getOrderNo());
		newOrderBaseDTO.setHasCustomer("1");
		newOrderBaseDTO.setOrderAmount(orderBaseDTO.getOrderAmount());
		newOrderBaseDTO.setTotalPayAmt(orderBaseDTO.getTotalPayAmt());
		newOrderBaseDTO.setMemberId(orderBaseDTO.getMemberId());
		// 修改订单买家id---新增
		map.put("orderBaseEntity", newOrderBaseDTO);
		map.put("reOrderCustomerEntity", reOrderCustomer);
		reOrderCustomerToolService.addCustomerAndProduct(map);

		logger.info("Successfully add customer.");
		// 新建线程 新增客户关联表
		new AddReMemCustThread(memberToolService, orderBaseDTO).start();

		// 清空缓存
		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				// 补充客户时，需要根据支付时间判断是否今日、昨日、本月等，然后清除对应缓存，及清除小时缓存【memberId_report_mmddhh】
				PaySerialnumberDTO payDto = paySerialnumberToolService.getByOrderNo(orderBaseDTO.getOrderNo());
				if (DateUtil.getDate(payDto.getPayTime(), "yyyy-MM-dd HH")
						.equals(DateUtil.getDate(new Date(), "yyyy-MM-dd HH"))) { // 当前小时
					dataToolService.cleanTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
							TimeCacheType.HOUR_CACHE);
				} else {
					dataToolService.cleanOldTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(),
							payDto.getPayTime());
				}
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public StatusCodeEnumWithInfo buyGoldMedal(MemberInfoInputDTO inputDTO, HttpServletRequest request)
			throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo(); // 返回结果
		Integer memberId = ParamsUtil.getIntFromString(inputDTO.getMemberId(), null);
		if (memberId == null) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.MEMBER_ID_IS_NULL);
			return statusCode;
		}

		MemberBaseinfoDTO memberInfo = memberToolService.getById(memberId.toString());
		if (memberInfo == null) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ACCOUNT_NOT_EXISTED);
			return statusCode;
		}

		Integer memberGrade = memberInfo.getMemberGrade();
		if (memberGrade != null && memberGrade == 1) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ALREADY_HAS_GOLD_MEDAL);
			return statusCode;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		Map<String, Object> res = certifBaseToolService.getStatusCombination(params);
		//判断认证信息,默认已认证
		int isCertified = 1;
		if (!"1".equals(res.get("cpstatus")) //个人认证
				&& !"1".equals(res.get("ccstatus")) //合作社认证
				&& !"1".equals(res.get("comstatus"))) {//企业认证
			isCertified = 0;
		}

		Map<String, Object> payMap = new HashMap<>();
		payMap.put("isCertified", isCertified);
		if (isCertified == 0) {
			payMap.put("url", "");
			statusCode.setObj(payMap);
			statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
			return statusCode;
		}
		//未分享价格3998
		Double dGoldMedalAmt = 1998.00;
		if(inputDTO.getIsShare() == 1){
			dGoldMedalAmt = 1998.00;
		}
		//String sGoldMedalAmt = gdProperties.getProperties().getProperty("gold_medal_fee");
		//Double dGoldMedalAmt = ParamsUtil.getDoubleFromString(sGoldMedalAmt);
		//插入用户购买金牌会员统计
		PageStatisMemberEntity pageStatisMemberEntity = new PageStatisMemberEntity();
		pageStatisMemberEntity.setMemberId(memberId.longValue());
		pageStatisMemberEntity.setPageType("2");
		pageToolService.addMemberPage(pageStatisMemberEntity);

		Long orderNo = 0L;

		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("memberId", memberId);
		queryMap.put("orderStatus", 1);
		queryMap.put("orderType", 4);
		queryMap.put("orderAmount", dGoldMedalAmt);
		List<OrderBaseinfoDTO> existedGoldMedalOrderList = getHessianOrderbaseService().getListByCondition(queryMap);
		if (existedGoldMedalOrderList == null || existedGoldMedalOrderList.size() == 0) {
			// 插入订单信息
			orderNo = orderNoToolServiceImpl.getOrderNo("gd_gold");
			OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
			orderEntity.setOrderNo(orderNo);
			//订单来源 1农批商APP 2农商友APP 3POS机 4智能秤 5供应商
			orderEntity.setOrderSource("5");
			// 渠道 1android 2ios 3pc
			orderEntity.setChannel(inputDTO.getChannel());
			orderEntity.setOrderAmount(dGoldMedalAmt);
			orderEntity.setDiscountAmount(0D);
			orderEntity.setPayAmount(dGoldMedalAmt);
			orderEntity.setPayType("4");
			orderEntity.setOrderStatus(Order.STATUS_NOT_PAY);
			orderEntity.setSellMemberId(null);// 卖家id
			orderEntity.setMemberId(memberId);
			orderEntity.setOrderTime(DateUtil.getNow());
			orderEntity.setShopName(null);
			orderEntity.setBusinessId(null);
			orderEntity.setMarketId(null);
			orderEntity.setCreateTime(DateUtil.getNow());
			orderEntity.setUpdateTime(DateUtil.getNow());
			orderEntity.setOutmarkStatus("0");
			orderEntity.setExamineStatus("0");
			orderEntity.setOrderType(Order.TYPE_FOR_GOLD_MEMBER);
			orderEntity.setPromType("0");
			orderEntity.setMessage(null);
			orderEntity.setHasCustomer("0");
			orderEntity.setDistributeMode("0");

			// 订单产品信息list
			List<OrderProductDetailEntity> entityList = new ArrayList<>();
			OrderProductDetailEntity entity = new OrderProductDetailEntity();
			entity.setOrderNo(orderNo);
			entity.setProductId(null);
			entity.setProductName("金牌供应商");
			entity.setPurQuantity(1D);
			entity.setPrice(dGoldMedalAmt);
			entity.setTradingPrice(dGoldMedalAmt);
			entity.setNeedToPayAmount(dGoldMedalAmt);
			entityList.add(entity);

			// 插入订单商品明细
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("orderBase", orderEntity);
			totalMap.put("orderProductList", entityList);
			getHessianOrderbaseService().addOrder(totalMap);
		} else {
			orderNo = existedGoldMedalOrderList.get(0).getOrderNo();
		}

		Date now = new Date();

		OrderPayDTO payDto = new OrderPayDTO();
		payDto.setOrderNo(orderNo.toString());
		payDto.setPayerUserId(memberId.toString());
		payDto.setPayerMobile(memberInfo.getMobile());
		payDto.setPayerName(StringUtils.isBlank(memberInfo.getRealName()) ? "供应商用户" : memberInfo.getRealName());
		payDto.setPayeeUserId(gdProperties.getGdMemberId());
		payDto.setTotalAmt(dGoldMedalAmt.toString());
		payDto.setPayAmt(dGoldMedalAmt.toString());
		payDto.setOrderTime(DateUtil.getDate(now, DateUtil.DATE_FORMAT_DATETIME));
		payMap.put("url", payHandleParamUrl(payDto, request));
		statusCode.setObj(payMap);
		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		return statusCode;
	}

	/**
	 * 组合支付参数 返回url
	 */
	private String payHandleParamUrl(OrderPayDTO payDto, HttpServletRequest request) throws Exception {
		// 获取公用回传参数，封装json中
		Map<String, Object> map = new HashMap<>();
		//买家id
		map.put("memberId", payDto.getPayerUserId());
		//订单号
		map.put("orderNo", payDto.getOrderNo());
		String reParamJsonStr = JSONUtils.toJSONString(map);
		// 将回调参数以base64进行传输
		String reParam = Base64.encode(SerializeUtil.serialize(reParamJsonStr));
		// 获取ip地址
		String requestIp = request.getRemoteAddr();
		// 组装部分dto
		payDto.setRequestIp(requestIp);
		// 将金额设置为最多2位小数
		payDto.setPayAmt(NumUtils.format(Double.valueOf(payDto.getPayAmt()), 0, 2).replace(",", ""));
		payDto.setTotalAmt(payDto.getPayAmt());
		payDto.setReParam(reParam);
		// 处理dto 得到所需参数校验秘钥paramMap参数
		Map<String, String> paramMap = new HashMap<>();
		this.paramMapUrl(payDto, paramMap);
		// 获取私钥 并生成密文
		String privateKey = gdProperties.getPrivateKey();
		String link = AccessSysSignUtil.createLinkString(AccessSysSignUtil.paraFilter(paramMap));
		String sign = AccessSysSignUtil.sign(link, gdProperties.getKeyType(), privateKey);
		sign = Base64.encode(sign.getBytes());
		String url = gdProperties.getCenterPayUrl() + "?" + link + "&sign=" + sign;
		return url;
	}

	/**
	 * 校验秘钥所需map参数
	 *
	 * @param dto
	 * @param paramMap
	 * @return
	 */
	private Map<String, String> paramMapUrl(OrderPayDTO dto, Map<String, String> paramMap) {
		paramMap.put("version", gdProperties.getVersion());
		paramMap.put("appKey", "gys");
		paramMap.put("timeOut", gdProperties.getTimeOut());
		paramMap.put("keyType", gdProperties.getKeyType());
		paramMap.put("returnUrl", gdProperties.getReturnUrl());
		paramMap.put("notifyUrl", "unused");   //参数没用
		paramMap.put("title", gdProperties.getTitle());
		paramMap.put("orderNo", dto.getOrderNo());
		paramMap.put("orderTime", dto.getOrderTime());
		paramMap.put("payerUserId", dto.getPayerUserId());
		//账号使用手机号码
		paramMap.put("payerAccount", dto.getPayerMobile());
		paramMap.put("payerMobile", dto.getPayerMobile());
		paramMap.put("payerName", dto.getPayerName());
		paramMap.put("payeeUserId", dto.getPayeeUserId());
		//账号使用手机号码
		paramMap.put("payeeAccount", dto.getPayeeMobile());
		paramMap.put("payeeMobile", dto.getPayeeMobile());
		paramMap.put("payeeName", dto.getPayeeName());
		paramMap.put("totalAmt", dto.getTotalAmt());
		paramMap.put("payAmt", dto.getPayAmt().toString());
		paramMap.put("reParam", dto.getReParam());
		paramMap.put("requestIp", dto.getRequestIp());
		return paramMap;
	}

	public OrderDetailAppDTO setWords(OrderDetailAppDTO orderDetailReturn, OrderBaseinfoDTO orderBaseDTO) {

		if ("1".equals(orderBaseDTO.getOrderStatus())) {//待付款
			if ("1".equals(orderBaseDTO.getDistributeMode())) {
				orderDetailReturn.setStatusWord(StatusEnum.DFYFK.getWord());
			} else {
				orderDetailReturn.setStatusWord(StatusEnum.DFK.getWord());
			}
			orderDetailReturn.setNstStatusWord(NstStatusEnum.DYH.getWord());
		} else if ("11".equals(orderBaseDTO.getOrderStatus())) {//待付预付款
			orderDetailReturn.setStatusWord(StatusEnum.DYH.getWord());
			orderDetailReturn.setNstStatusWord(NstStatusEnum.DYH.getWord());
		} else if ("12".equals(orderBaseDTO.getOrderStatus())) {//待付尾款
			orderDetailReturn.setStatusWord(StatusEnum.DFWK.getWord());
			if (orderBaseDTO.getArriveTime() != null) {
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YSD.getWord());
			} else {
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YFH.getWord());
			}
		} else if ("8".equals(orderBaseDTO.getOrderStatus())) {

			if (ClosedReasonEnum.BUYER_CANCLE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.WYQXDD.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YGB.getWord());
			} else if (ClosedReasonEnum.SELLER_CANCLE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.MJYQXDD.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YGB.getWord());
			} else if (ClosedReasonEnum.PAY_TIMEOUT.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.FKCS.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YGB.getWord());
			} else if (ClosedReasonEnum.LOGISTICS_FAILURE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.FPWLSB.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.FPWLGSSB.getWord());
			} else if (ClosedReasonEnum.INSPECTION_FAILURE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.YHBTG.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YHBTG.getWord());
			}else if(ClosedReasonEnum.PREPAY_TIMEOUT.getCloseReason().equals(orderBaseDTO.getCancelReason())){
				orderDetailReturn.setStatusWord(StatusEnum.YFKZFCS.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YGB.getWord());
			}else if(ClosedReasonEnum.REFUSED.getCloseReason().equals(orderBaseDTO.getCancelReason())){
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YJS.getWord());
			}else if(ClosedReasonEnum.YH_TIMEOUT.getCloseReason().equals(orderBaseDTO.getCancelReason())){
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YGB.getWord());
			}	
			if ("1".equals(orderBaseDTO.getOorStatus())) {
				orderDetailReturn.setStatusWord(StatusEnum.DTK.getWord());
			} else if ("3".equals(orderBaseDTO.getOorStatus())) {
				orderDetailReturn.setStatusWord(StatusEnum.YTK.getWord());
			}

		} else if ("3".equals(orderBaseDTO.getOrderStatus())) {//已完成
			orderDetailReturn.setStatusWord(StatusEnum.YWC.getWord());
			if ("1".equals(orderBaseDTO.getDistributeMode())) {
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YSD.getWord());
			}
		}
		return orderDetailReturn;
	}

	public OrderAppReturnDTO setWords(OrderAppReturnDTO orderDetailReturn, OrderBaseinfoDTO orderBaseDTO) {

		if ("1".equals(orderBaseDTO.getOrderStatus())) {//待付款
			if ("1".equals(orderBaseDTO.getDistributeMode())) {
				orderDetailReturn.setStatusWord(StatusEnum.DFYFK.getWord());
			} else {
				orderDetailReturn.setStatusWord(StatusEnum.DFK.getWord());
			}
			orderDetailReturn.setNstStatusWord("");
		} else if ("11".equals(orderBaseDTO.getOrderStatus())) {//待付预付款
			orderDetailReturn.setStatusWord(StatusEnum.DYH.getWord());
			orderDetailReturn.setNstStatusWord(NstStatusEnum.DYH.getWord());
		} else if ("12".equals(orderBaseDTO.getOrderStatus())) {//待付尾款
			orderDetailReturn.setStatusWord(StatusEnum.DFWK.getWord());
			if (orderBaseDTO.getArriveTime() != null) {
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YSD.getWord());
			} else {
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YFH.getWord());
			}
		} else if ("8".equals(orderBaseDTO.getOrderStatus())) {
			if (ClosedReasonEnum.BUYER_CANCLE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.WYQXDD.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YGB.getWord());
			} else if (ClosedReasonEnum.SELLER_CANCLE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.MJYQXDD.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YGB.getWord());
			} else if (ClosedReasonEnum.PAY_TIMEOUT.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.FKCS.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YGB.getWord());
			} else if (ClosedReasonEnum.LOGISTICS_FAILURE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.FPWLSB.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.FPWLGSSB.getWord());
			} else if (ClosedReasonEnum.INSPECTION_FAILURE.getCloseReason().equals(orderBaseDTO.getCancelReason())) {
				orderDetailReturn.setStatusWord(StatusEnum.YHBTG.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YHBTG.getWord());
			}else if(ClosedReasonEnum.PREPAY_TIMEOUT.getCloseReason().equals(orderBaseDTO.getCancelReason())){
				orderDetailReturn.setStatusWord(StatusEnum.YFKZFCS.getWord());
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YGB.getWord());
			}else if(ClosedReasonEnum.REFUSED.getCloseReason().equals(orderBaseDTO.getCancelReason())){
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YJS.getWord());
			}else if(ClosedReasonEnum.YH_TIMEOUT.getCloseReason().equals(orderBaseDTO.getCancelReason())){
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YHBTG.getWord());
			}	
			if ("1".equals(orderBaseDTO.getOorStatus())) {
				orderDetailReturn.setStatusWord(StatusEnum.DTK.getWord());
			} else if ("3".equals(orderBaseDTO.getOorStatus())) {
				orderDetailReturn.setStatusWord(StatusEnum.YTK.getWord());
			}
		} else if ("3".equals(orderBaseDTO.getOrderStatus())) {//已完成
			orderDetailReturn.setStatusWord(StatusEnum.YWC.getWord());
			if ("1".equals(orderBaseDTO.getDistributeMode())) {
				orderDetailReturn.setNstStatusWord(NstStatusEnum.YSD.getWord());
			}
		}
		return orderDetailReturn;
	}
}
