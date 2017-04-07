package com.gudeng.commerce.gd.api.service.impl.pos;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.gudeng.commerce.gd.api.dto.input.GdOrderActivityApiQueryDTO;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.service.DeliveryAddressService;
import com.gudeng.commerce.gd.order.dto.*;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant.Order;
import com.gudeng.commerce.gd.api.Constant.PAY_SERIALNUMBER;
import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.output.MemberInfoAppDTO;
import com.gudeng.commerce.gd.api.dto.output.PosOrderDetailDTO;
import com.gudeng.commerce.gd.api.dto.output.PosOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.PosOrderProductDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.DataToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.engj.PosBankCardToolService;
import com.gudeng.commerce.gd.api.service.impl.OrderNoToolServiceImpl;
import com.gudeng.commerce.gd.api.service.order.OrderActivityToolService;
import com.gudeng.commerce.gd.api.service.order.OrderFeeItemDetailToolService;
import com.gudeng.commerce.gd.api.service.pos.PosOrderToolService;
import com.gudeng.commerce.gd.api.thread.AddReMemCustThread;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PosBankCardDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.commerce.gd.order.enm.EOrderSource;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class PosOrderToolServiceImpl implements PosOrderToolService {

	/**
	 * 记录日志
	 */
	private static Logger logger = LoggerFactory.getLogger(PosOrderToolServiceImpl.class);

	@Autowired
	public GdProperties gdProperties;

	private OrderBaseinfoService orderBaseinfoService;

	private OrderProductDetailService orderProductDetailService;
	private static DeliveryAddressService deliveryAddressService;
	@Autowired
	private ProductToolService productToolService;
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private OrderNoToolServiceImpl orderNoToolServiceImpl;
	@Autowired
	private PaySerialnumberToolService paySerialnumberToolService;
	@Autowired
	private PosBankCardToolService posBankCardToolService;

	@Autowired
	public MemberToolService memberToolService;

	@Autowired
	private DataToolService dataToolService;
	@Autowired
	private OrderActivityToolService orderActivityToolService;
	@Autowired
	private OrderFeeItemDetailToolService orderFeeItemDetailToolService;

	private PaySerialnumberService paySerialnumberService;
	private PaySerialnumberService getHessianPaySerialnumberService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.paySerialnumberService.url");
		if (paySerialnumberService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			paySerialnumberService = (PaySerialnumberService) factory.create(PaySerialnumberService.class, hessianUrl);
		}
		return paySerialnumberService;
	}

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
			orderProductDetailService = (OrderProductDetailService) factory.create(OrderProductDetailService.class, hessianUrl);
		}
		return orderProductDetailService;
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
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo();    //正常返回结果
		Double zero = new Double(0);
		Long memberId = inputDTO.getMemberId();  //下单用户id
		Long businessId = inputDTO.getBusinessId(); //店铺id
		String productDetials = inputDTO.getjProductDetails();  //订单产品详情
//		String shopName = inputDTO.getShopName();  //店铺名
		Double orderAmount = inputDTO.getOrderAmount();  //订单总额
		Double userPayAmount = inputDTO.getPayAmount();  //用户支付订单金额
		String payType = inputDTO.getPayType(); //支付方式： 1 pos刷卡; 2 现金; 3 余额交易

		if (businessId == null) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.BUSINESS_ID_IS_NULL);
			return statusCode;
		}
		if (StringUtils.isBlank(productDetials)) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_INFO_IS_NULL);
			return statusCode;
		}
		if (orderAmount == null || orderAmount.compareTo(zero) == 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_IS_NULL);
			return statusCode;
		}
		if (userPayAmount == null || userPayAmount.compareTo(zero) == 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PAYAMT_IS_NULL);
			return statusCode;
		}
		if (orderAmount.compareTo(Order.MAX_POS_ORDER_PRICE) > 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_EXCEED_MAX_AMOUNT);
			return statusCode;
		}

		Double discountAmount = zero;

		BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(businessId + "");
		Long sellerId = businessDTO.getUserId();

		Long orderNo = new Long(orderNoToolServiceImpl.getOrderNo());
		JSONArray jsonArr = JSONUtils.parseArray(productDetials);
		//订单信息实体类
		OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
		//订单产品信息list
		List<OrderProductDetailEntity> entityList = new ArrayList<>();
		//产品购买数量list， 用于减库存
		List<Map<String, Object>> stockList = new ArrayList<>();
		//产品id list， 用来查找校验产品信息
		List<Long> pIdList = new ArrayList<>();
		Double orderPaySum = zero;
		Double productPaySum = zero;
		//遍历产品信息
		for (int i = 0, len = jsonArr.size(); i < len; i++) {
			JSONObject jsonObject = (JSONObject) jsonArr.get(i);
			Long productId = jsonObject.getLong("productId");
			String productName = jsonObject.getString("productName");
			Double quantity = jsonObject.getDouble("purQuantity");
			Double price = jsonObject.getDouble("price");
			Double payAmount = jsonObject.getDouble("needToPayAmount");

			//检查产品id和名字是否为空
			if (productId == null || StringUtils.isBlank(productName)) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_ID_IS_NULL);
				return statusCode;
			}

			//检查商品 数量
			if (quantity == null || quantity.compareTo(zero) == 0) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_PERCHASE_QUANTITY_IS_NULL);
				return statusCode;
			}

			//检查商品价格
			if (price == null || price.compareTo(zero) == 0) {
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_PRICE_IS_NULL);
				return statusCode;
			}

			Double productPay = MathUtil.round(MathUtil.mul(price, quantity), 2);
			//检查产品支付金额和单价*数量是否匹配
			if (productPay.compareTo(payAmount) != 0) {
				logger.warn("订单产品:" + productName + "的支付金额:" + payAmount +
						" 和应付金额:" + productPay + " 不匹配");
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_NOT_MATCH);
				return statusCode;
			}
			//支付总额
			orderPaySum = MathUtil.add(orderPaySum, payAmount);
			//产品总额
			productPaySum = MathUtil.add(productPaySum, productPay);
			pIdList.add(productId);

			OrderProductDetailEntity entity = new OrderProductDetailEntity();
			entity.setOrderNo(orderNo);
			entity.setProductId(productId.intValue());
			entity.setProductName(productName);
			entity.setPurQuantity(quantity);
			entity.setPrice(price);
			entity.setTradingPrice(payAmount);
			entity.setNeedToPayAmount(payAmount);
			//卖家匿名下单
			if (memberId == null) {
				entity.setCreateUserId("");
			} else {
				entity.setCreateUserId(memberId + "");
			}
			entityList.add(entity);
		}

		//校验订单产品信息
		List<ProductDto> productList = productToolService.getListByIds(pIdList);
		for (int i = 0, len = productList.size(); i < len; i++) {
			ProductDto pDTO = productList.get(i);
			Double existedStock = pDTO.getStockCount();
			String state = pDTO.getState();
			if (!"3".equals(state) || existedStock.compareTo(zero) == 0) {//检查产品是否已下架
				statusCode.setStatusCodeEnum(ErrorCodeEnum.PRODUCT_NO_STOCK_COUNT);
				return statusCode;
			}

			for (int j = 0, len2 = entityList.size(); j < len2; j++) {
				OrderProductDetailEntity entity = entityList.get(j);
				Integer pId = entity.getProductId();
				Double quantity = entity.getPurQuantity();
				if (pId == pDTO.getProductId().intValue()) {
					Map<String, Object> map = new HashMap<>();
					map.put("productId", pId);
					//判断库存是否足够
					if (quantity.compareTo(existedStock) > 0) {//购买量大于库存，返回失败，errorcode=-5
						logger.warn("库存不足, 订单号：" + orderNo + ",产品:" + entity.getProductName()
								+ "的购买数量:" + quantity + " 和库存:" + existedStock);
						statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_STOCKCOUNT_LACK);
						return statusCode;
					} else if (quantity.compareTo(existedStock) == 0) {//下单后，产品库存为0，不自动下架
						map.put("status", "3");
					} else {                                       //库存充足
						map.put("status", "3");
					}

					map.put("stockCount", MathUtil.sub(existedStock, quantity));
					stockList.add(map);

					entity.setUnit(pDTO.getUnit());
				}
			}
		}

		//订单金额
		if (orderPaySum.compareTo(productPaySum) != 0
				|| orderPaySum.compareTo(orderAmount) != 0) {
			logger.warn("订单金额不匹配,订单金额:" + orderAmount + ", 应付金额:" + orderPaySum);
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_NOT_MATCH);
			return statusCode;
		}

		//插入订单信息
		orderEntity.setOrderNo(orderNo);
		orderEntity.setOrderSource("3");                //订单来源 1卖家代客下单 2买家下单
		orderEntity.setChannel("4");  //渠道 1android 2ios 3pc 4pos
		orderEntity.setOrderAmount(orderAmount);
		orderEntity.setDiscountAmount(discountAmount);
		orderEntity.setPayAmount(userPayAmount);
		orderEntity.setTotalPayAmt(orderEntity.getPayAmount());
		orderEntity.setPayType(payType);
		orderEntity.setOrderStatus(Order.STATUS_NOT_PAY);
		orderEntity.setSellMemberId(sellerId.intValue());//卖家id
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
		orderEntity.setHasCustomer("0");
		//卖家匿名下单
		if (memberId == null) {
			orderEntity.setCreateUserId("");
			orderEntity.setMemberId(null);//买家为null
		} else {
			orderEntity.setMemberId(memberId.intValue());//买家id
			orderEntity.setCreateUserId(memberId + "");
		}
		//插入订单商品明细
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderBase", orderEntity);
		totalMap.put("orderProductList", entityList);
		totalMap.put("stockList", stockList);  //减少库存

		getHessianOrderbaseService().addOrder(totalMap);

		try {
			if (sellerId != null) {
				dataToolService.cleanTradeCacheSpecial(sellerId, TimeCacheType.HOUR_CACHE);
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}

		//新建线程  新增客户关联表
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

		//组装库存数据， 增加产品库存
		List<OrderProductDetailDTO> productList = getHessianOrderProductService().getListByOrderNo(map);


		List<Map<String, Object>> stockList = new ArrayList<>();
		//产品id list， 用来查找校验产品信息
		List<Long> pIdList = new ArrayList<>();
		for (int i = 0, len = productList.size(); i < len; i++) {
			OrderProductDetailDTO productDTO = productList.get(i);
			pIdList.add(Long.parseLong(productDTO.getProductId() + ""));
		}
		//设置订单产品库存
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
					//如果库存大于最大值， 则使用最大值
					if (newStock.compareTo(Order.MAX_PRODUCT_STOCK) > 0) {
						tmpMap.put("stockCount", Order.MAX_PRODUCT_STOCK);
					} else {
						tmpMap.put("stockCount", newStock);
					}

					tmpMap.put("status", "3");  //产品上架
					stockList.add(tmpMap);
				}
			}
		}

		map.put("closeUserId", orderBaseDTO.getSellMemberId() + "");
		map.put("stockList", stockList);
		getHessianOrderbaseService().cancelByOrderNo(map);

		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				dataToolService.cleanTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), TimeCacheType.HOUR_CACHE);
				dataToolService.cleanOldTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), orderBaseDTO.getOrderTime());
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}

		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public PosOrderDetailDTO getOrderByOrderNo(Long orderNo) throws Exception {
		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if (orderBaseDTO == null) {
			return null;
		}

		//查找订单产品详情
		Map<String, Object> map = new HashMap<String, Object>();
		PosOrderDetailDTO posDTO = new PosOrderDetailDTO();
		List<PosOrderProductDTO> productList = new ArrayList<>();
		map.put("orderNo", orderNo);
		List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNo(map);
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		if (productOrderList != null && productOrderList.size() > 0) {
			StringBuilder productNameSB = new StringBuilder();
			for (int i = 0, len = productOrderList.size(); i < len; i++) {
				OrderProductDetailDTO dto = productOrderList.get(i);
				PosOrderProductDTO posProduct = new PosOrderProductDTO();
				posProduct.setOrderNo(dto.getOrderNo());
				posProduct.setProductId(dto.getProductId());
				posProduct.setProductName(dto.getProductName());
				posProduct.setPurQuantity(dto.getPurQuantity());
				posProduct.setUnit(dto.getUnit());
				posProduct.setPrice(dto.getPrice());
				posProduct.setTradingPrice(dto.getTradingPrice());
				posProduct.setNeedToPayAmount(dto.getNeedToPayAmount());
				posProduct.setUnitName(dto.getUnitName());
				posProduct.setImageUrl(imageHost + dto.getImageUrl());
				posProduct.setFormattedPrice(MoneyUtil.formatMoney(dto.getPrice()));
				productNameSB.append(dto.getProductName() + ",");
				productList.add(posProduct);
			}
			posDTO.setProductsName(productNameSB.deleteCharAt(productNameSB.length() - 1).toString());
		}

		// 订单收货地址
		map.put("status", 1);
		List<DeliveryAddressDTO> list = getHessianDeliveryAddressService().getList(map);
		if (list != null && list.size() > 0) {
			posDTO.setDeliveryAddress(list.get(0));
		}
		//买家留言
		posDTO.setMessage(orderBaseDTO.getMessage());


		//设置补贴和佣金信息
		GdOrderActivityApiQueryDTO inputDTO = new GdOrderActivityApiQueryDTO();
		inputDTO.setOrderNo(orderNo.toString());
		inputDTO.setFlag("1");
		GdOrderActivityResultDTO queryResultDTO = orderActivityToolService.queryActivity(inputDTO);
		if(queryResultDTO.getBuyerActInfo() != null && queryResultDTO.getBuyerActInfo().isHasBuyerCommsn()){
			posDTO.setHasBuyerCommsn("1");
		}
		if(queryResultDTO.getSellerActInfo() != null){
			if(queryResultDTO.getSellerActInfo().isHasSellerCommsn()){
				posDTO.setHasSellerCommsn("1");
			}
			if(queryResultDTO.getSellerActInfo().getHasSellerSub()){
				posDTO.setHasSellSubPay("1");;
			}
		}
		orderFeeItemDetailToolService.setOrderCommAndSubsidyInfo(posDTO, orderBaseDTO);

		posDTO.setOrderNo(orderBaseDTO.getOrderNo());
		posDTO.setIsLock(orderBaseDTO.getIsLock());
		//买家姓名
		posDTO.setBuyerName(StringUtils.isBlank(orderBaseDTO.getRealName()) ? "农商友客户" : orderBaseDTO.getRealName());
		posDTO.setMobile(orderBaseDTO.getBuyerMobile()); //买家电话
		if (posDTO.getMobile() != null && posDTO.getMobile().length() == 32) {
			posDTO.setMobile("");
		}
		posDTO.setCreateDate(DateTimeUtils.formatDate(orderBaseDTO.getCreateTime(), null));
		posDTO.setFormattedPrice(MoneyUtil.formatMoney(orderBaseDTO.getOrderAmount()));
		posDTO.setOrderAmount(orderBaseDTO.getOrderAmount());
		posDTO.setPayAmount(orderBaseDTO.getPayAmount() + "");
		posDTO.setOrderStatus(orderBaseDTO.getOrderStatus());
		posDTO.setProductNum(productList.size());
		posDTO.setProductList(productList);
		posDTO.setPrepayAmt(orderBaseDTO.getPrepayAmt());//预付款
		posDTO.setRestAmt(orderBaseDTO.getRestAmt());//订单尾款
		posDTO.setArrivedTime(DateUtil.getDate(orderBaseDTO.getArriveTime(), DateUtil.DATE_FORMAT_DATETIME));//送达时间
		posDTO.setDistributeMode(orderBaseDTO.getDistributeMode());//配送方式
		posDTO.setShopName(orderBaseDTO.getShopName());//商铺名称
		posDTO.setTotalPayAmt(orderBaseDTO.getTotalPayAmt());//'总共支付(含佣金)',
		//预付款支付时间
		if("1".equals(orderBaseDTO.getDistributeMode())){
			map.put("serialType", 1);//订单为平台配送时，预付款支付时间
			PaySerialnumberDTO prePayDTO = getHessianPaySerialnumberService().getByOrderNo(map);
			if (prePayDTO != null) {
				posDTO.setPrepayTime(DateTimeUtils.formatDate(prePayDTO.getCreateTime(),""));
			}
			map.put("serialType", 2);//订单为平台配送时，查尾款支付时间
		}
		return posDTO;
	}

	@Override
	public List<PosOrderListDTO> getPosOrderList(Map<String, Object> map)
			throws Exception {
		List<PosOrderListDTO> orderList = null;
		//订单列表
		List<OrderBaseinfoDTO> orderBaseList = getHessianOrderbaseService().getListByStatusPage(map);
		if (orderBaseList != null && orderBaseList.size() > 0) {
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}
			//所有订单下的所有商品列表
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNoList(orderNoList);
			for (int i = 0, len = orderBaseList.size(); i < len; i++) {
				PosOrderListDTO returnDto = new PosOrderListDTO();//返回结果数据模型
				OrderBaseinfoDTO dto = orderBaseList.get(i);//单个订单信息

				// 计算订单预付款
				returnDto.setPrepayAmt(dto.getPrepayAmt());
				// 计算订单尾款
				returnDto.setRestAmt(dto.getRestAmt());
				// 设置送达时间
				returnDto.setArrivedTime(DateUtil.getDate(dto.getArriveTime(), DateUtil.DATE_FORMAT_DATETIME));
				// 订单商铺名称
				returnDto.setShopName(dto.getShopName());
				// 是否平台配送
				returnDto.setDistributeMode(dto.getDistributeMode());
				returnDto.setCompanyId(dto.getCompanyId());//物流公司ID
				returnDto.setBuyerId(dto.getMemberId());//买家id
				returnDto.setSellerId(dto.getSellMemberId());//卖家id
				Long orderNo = dto.getOrderNo();
				returnDto.setOrderNo(orderNo);
				returnDto.setCreateDate(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));
				returnDto.setCreateTime(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
				returnDto.setOrderAmount(dto.getOrderAmount());//订单总额
				returnDto.setPayAmount(dto.getPayAmount() + "");//实际支付
				returnDto.setFormattedPrice(MoneyUtil.formatMoney(dto.getOrderAmount()));
				returnDto.setOrderStatus(dto.getOrderStatus());
				//设置买家姓名
				returnDto.setBuyerName(StringUtils.isBlank(dto.getRealName()) ? "农商友用户" : dto.getRealName());
				returnDto.setTotalPayAmt(dto.getTotalPayAmt());//'总共支付(含佣金)',
				StringBuilder productSB = new StringBuilder();
				int productCount = 0;
				if (null != productOrderList && productOrderList.size() > 0) {
					for (int j = 0, pLen = productOrderList.size(); j < pLen; j++) {
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if (orderNo.equals(pDto.getOrderNo())) {
							productSB.append(pDto.getProductName() + ",");
							productCount++;
						}
					}
				}
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
	public int getPosOrdersTotal(Map<String, Object> map) throws Exception {
		return getHessianOrderbaseService().getTotalByStatusPage(map);
	}

	@Override
	public StatusCodeEnumWithInfo update(Long orderNo, Double payAmount, Integer version) throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo(); // 返回结果
		OrderBaseinfoDTO orderDetail = getHessianOrderbaseService().getByOrderNo(orderNo);
		if (orderDetail == null) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_NOT_EXISTED);
			return statusCode;
		}

//		String status = orderDetail.getOrderStatus();
//		Integer isLock = orderDetail.getIsLock();
//		if(!Order.STATUS_NOT_PAY.equals(status)){
//			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_NOT_UNPAID_STATUS);
//			return statusCode;
//		}
//		if(isLock != null && Order.ORDER_IS_LOCKED == isLock){
//			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_IS_PAYING_STATUS);
//			return statusCode;
//		}

		OrderBaseinfoDTO dto = new OrderBaseinfoDTO();
		dto.setOrderNo(orderNo);
		dto.setPayAmount(payAmount);
		dto.setTotalPayAmt(payAmount);
		//如果有参与活动则更新活动信息
		if (version == 1) {
			Map<String, Object> map = new HashMap<>();
			map.put("sellerMarketCommision", 0);
			statusCode.setObj(map);

			GdOrderActivityResultDTO orderActResult = orderActivityToolService.queryActivity(setQueryParams(orderDetail, payAmount));
			if (orderActResult != null) {
				if (orderActResult.getBuyerActInfo() != null) {
					Double commsn = MathUtil.add(orderActResult.getBuyerActInfo().getMarketCommision(), orderActResult.getBuyerActInfo().getPlatCommision());
					dto.setTotalPayAmt(MathUtil.add(payAmount, commsn));
				}
				
				if (orderActResult.getSellerActInfo() != null && orderActResult.getSellerActInfo().isHasSellerCommsn()) {
					Double commsn = MathUtil.add(orderActResult.getSellerActInfo().getMarketCommision(), orderActResult.getSellerActInfo().getPlatCommision());
					//实收款不能小于订单佣金
					if (commsn.compareTo(payAmount) > 0) {
						statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_ILLEAGLE_CHANGED_PAYAMOUNT);
						return statusCode;
					}
					
					map.put("sellerMarketCommision", commsn);
				}
				
				//获取订单费用信息
				List<OrderFeeItemDetailDTO> orderActFeeList = orderFeeItemDetailToolService.getOrderFeeDetail(orderActResult, orderNo);
				logger.info("Has update order product fee info num: " + orderFeeItemDetailToolService.batchUpdate(orderActFeeList, orderNo));
			}
		}

		getHessianOrderbaseService().updateByOrderNo(dto);
		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		return statusCode;
	}

	private GdOrderActivityQueryDTO setQueryParams(
			OrderBaseinfoDTO orderDetail, Double payAmount) throws Exception {
		GdOrderActivityQueryDTO inputDTO = new GdOrderActivityQueryDTO();
		inputDTO.setBuyerId(orderDetail.getMemberId());
		inputDTO.setBusinessId(orderDetail.getBusinessId());
		inputDTO.setSellerId(orderDetail.getSellMemberId());
		inputDTO.setMarketId(orderDetail.getMarketId());
		inputDTO.setOrderAmount(orderDetail.getOrderAmount());
		inputDTO.setPayAmount(payAmount);
		List<GdProductActInfoDTO> pIdList = new ArrayList<>();
		inputDTO.setProductList(pIdList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderDetail.getOrderNo());
		List<OrderProductDetailDTO> productList = getHessianOrderProductService().getListByOrderNo(map);
		if (productList != null && productList.size() > 0) {
			//查找订单商品活动信息
			List<OrderActRelationDTO> productRefActIdList = orderActivityToolService.getByOrderNo(orderDetail.getOrderNo());
			if(productRefActIdList != null && productRefActIdList.size() > 0){
				for(OrderProductDetailDTO dto : productList){
					GdProductActInfoDTO pDTO = new GdProductActInfoDTO();
					pDTO.setPrice(dto.getPrice());
					pDTO.setProductId(dto.getProductId());
					pDTO.setProductAmount(dto.getNeedToPayAmount());
					pDTO.setQuantity(dto.getPurQuantity());
					boolean hasAct = false;
					for(OrderActRelationDTO actRef : productRefActIdList){
						if(actRef.getProductId().intValue() == dto.getProductId().intValue()){
							GdOrderActivityDetailDTO actInfo = pDTO.getActInfo();
							if(actInfo == null){
								actInfo = new GdOrderActivityDetailDTO();
							}
							
							List<GdOrderActivityDTO> pActList = actInfo.getProductActInfo().get(actRef.getProductId());
							if(pActList == null){
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
					if(hasAct){
						pIdList.add(pDTO);
					}
				}
			}
			
			inputDTO.setHasProduct(true);
		}
		return inputDTO;
	}

	@Override
	public StatusCodeEnumWithInfo addAnonymousOrder(OrderAppInputDTO inputDTO) throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo();   //正常返回结果
		Double zero = new Double(0);
		Long memberId = inputDTO.getMemberId();  //下单用户id
		Long businessId = inputDTO.getBusinessId(); //店铺id
//		String shopName = inputDTO.getShopName();  //店铺名
		Double orderAmount = inputDTO.getOrderAmount();  //订单总额
		Double userPayAmount = inputDTO.getPayAmount();  //用户支付订单金额
		String payType = inputDTO.getPayType(); //支付方式： 1 pos刷卡; 2 现金交易; 3 余额交易
		if (businessId == null) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.BUSINESS_ID_IS_NULL);
			return statusCode;
		}
		if (orderAmount == null || orderAmount.compareTo(zero) == 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_IS_NULL);
			return statusCode;
		}
		if (userPayAmount == null || userPayAmount.compareTo(zero) == 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PAYAMOUNT_ERROR);
			return statusCode;
		}
		if (orderAmount.compareTo(Order.MAX_POS_ORDER_PRICE) > 0) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_EXCEED_MAX_AMOUNT);
			return statusCode;
		}

		Double discountAmount = zero;

		BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(businessId + "");
		Long sellerId = businessDTO.getUserId();

		Long orderNo = new Long(orderNoToolServiceImpl.getOrderNo());
		//订单信息实体类
		OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
		orderEntity.setOrderNo(orderNo);
		//订单来源 1卖家代客下单 2买家下单
		orderEntity.setOrderSource(EOrderSource.PAY_ORDER.getCode());
		orderEntity.setChannel("4");                //渠道 1android 2ios 3pc 4pos
		orderEntity.setOrderAmount(orderAmount);
		orderEntity.setDiscountAmount(discountAmount);
		orderEntity.setPayAmount(userPayAmount);
		orderEntity.setTotalPayAmt(orderEntity.getPayAmount());
		orderEntity.setPayType(payType);
		orderEntity.setPaySubType(inputDTO.getPaySubType());
		orderEntity.setOrderStatus(String.valueOf(inputDTO.getOrderStatus()));
		orderEntity.setSellMemberId(sellerId.intValue());//卖家id
		orderEntity.setOrderTime(DateUtil.getNow());
		orderEntity.setShopName(businessDTO.getShopsName());
		orderEntity.setBusinessId(businessId.intValue());
		orderEntity.setMarketId(Integer.parseInt(businessDTO.getMarketId()));

		orderEntity.setCreateTime(DateUtil.getNow());
		orderEntity.setUpdateTime(DateUtil.getNow());
		orderEntity.setOutmarkStatus("0");
		orderEntity.setExamineStatus("0");
		orderEntity.setIsLock(Order.ORDER_IS_LOCKED);
		orderEntity.setLockTime(new Date());
		orderEntity.setOrderType(Order.TYPE_FROM_NSY);
		orderEntity.setPromType("0");
		//虚拟买家下单
		if (memberId != null && Order.STATUS_PAID.equals(inputDTO.getOrderStatus() + "")) {
			orderEntity.setMemberId(memberId.intValue());//买家id
			orderEntity.setCreateUserId(memberId + "");
		} else {//卖家匿名下单
			orderEntity.setMemberId(null);//买家id
			orderEntity.setCreateUserId(sellerId + "");
		}
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderBase", orderEntity);

		getHessianOrderbaseService().addOrder(totalMap);

		// 清空缓存
		try {
			if (sellerId != null) {
				dataToolService.cleanTradeCacheSpecial(sellerId, TimeCacheType.HOUR_CACHE);
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}

		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		statusCode.setObj(orderNo);
		return statusCode;
	}

	@Override
	public ErrorCodeEnum confirmReceive(OrderAppInputDTO inputDTO) throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		Long memberId = inputDTO.getMemberId();
		if (orderNo == null) {
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}

		if (StringUtils.isBlank(inputDTO.getPayType()) && memberId == null) {
			return ErrorCodeEnum.ORDER_SELLER_IS_NULL;
		}

		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if (orderBaseDTO == null) {
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}

		if (StringUtils.isBlank(inputDTO.getPayType()) &&
				orderBaseDTO.getSellMemberId() != memberId.intValue()) {
			return ErrorCodeEnum.ORDER_NOT_ORDER_SELLER;
		}

		//校验是否未支付状态
		if (!Order.STATUS_NOT_PAY.equals(orderBaseDTO.getOrderStatus())) {
			return ErrorCodeEnum.ORDER_CANNOT_CONFIRM_RECEIVABLES;
		}

		//生成支付订单记录
		PaySerialnumberEntity payRecordEntity = new PaySerialnumberEntity();
		if (StringUtils.isBlank(inputDTO.getPayType())) {
			payRecordEntity.setTradeAmount(orderBaseDTO.getPayAmount());
		} else {
			if (MathUtil.compareTo(inputDTO.getPayAmount(), orderBaseDTO.getPayAmount()) != 0) {
				return ErrorCodeEnum.ORDER_PAYAMOUNT_ERROR;
			}
			payRecordEntity.setTradeAmount(inputDTO.getPayAmount());
		}
		payRecordEntity.setOrderNo(orderNo);
		payRecordEntity.setStatementId(inputDTO.getStatementId());
		//支付方式 1钱包余额 2线下刷卡 3现金
		if (StringUtils.isBlank(inputDTO.getPayType())) {
			payRecordEntity.setPayType(PAY_SERIALNUMBER.PAYTYPE_CASH);
			payRecordEntity.setPayTime(DateUtil.getNow());
		} else {
			payRecordEntity.setPayType(inputDTO.getPayType());
			payRecordEntity.setPayTime(inputDTO.getPayTime());
		}
		//支付状态 0未支付 1已支付 9支付失败
		payRecordEntity.setPayStatus(PAY_SERIALNUMBER.STATUS_PAY);
		payRecordEntity.setCreateTime(DateUtil.getNow());
		payRecordEntity.setCreateuserid(String.valueOf(orderBaseDTO.getSellMemberId()));
		payRecordEntity.setUpdatetime(DateUtil.getNow());
		payRecordEntity.setMemberId(orderBaseDTO.getMemberId());
		payRecordEntity.setRemark(inputDTO.getPayInfo());
		payRecordEntity.setPaymentAcc(inputDTO.getPaymentAccount());
		payRecordEntity.setPosNumber(inputDTO.getPosNumber());

		Map<String, Object> totalMap = new HashMap<>();
		OrderBaseinfoDTO upOrderBase = new OrderBaseinfoDTO();
		upOrderBase.setOrderNo(orderBaseDTO.getOrderNo());
		upOrderBase.setOrderStatus(Order.STATUS_PAID);
		upOrderBase.setPayType(payRecordEntity.getPayType());
		if (EPayType.OFFLINE_CARD.getCode().equals(payRecordEntity.getPayType())) {
			upOrderBase.setPaySubType(inputDTO.getPaySubType());
		}
		totalMap.put("orderBase", upOrderBase);
		totalMap.put("paySerialNumEntity", payRecordEntity);

		getHessianOrderbaseService().confirmReceive(totalMap);

		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				dataToolService.cleanTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), TimeCacheType.HOUR_CACHE);
				dataToolService.cleanOldTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), orderBaseDTO.getOrderTime());
				dataToolService.cleanGoodsCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), TimeCacheType.HOUR_CACHE);
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public String confirmEnongReceive(OrderAppInputDTO inputDTO) throws Exception {
		String result = "OK";
		Long orderNo = inputDTO.getOrderNo();
		if (orderNo == null) {
			result = "订单号不能为空";
			return result;
		}
		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if (orderBaseDTO == null) {
			result = "该订单不存在";
			return result;
		}
		//校验是否未支付状态
		if (!Order.STATUS_NOT_PAY.equals(orderBaseDTO.getOrderStatus())) {
			result = "该订单不能确认收款";
			return result;
		}
		//生成支付订单记录
		PaySerialnumberEntity payRecordEntity = new PaySerialnumberEntity();
		if (MathUtil.compareTo(inputDTO.getPayAmount(), orderBaseDTO.getPayAmount()) != 0) {
			result = "支付金额不一致";
			return result;
		}
		payRecordEntity.setTradeAmount(inputDTO.getPayAmount());
		payRecordEntity.setOrderNo(orderNo);
		payRecordEntity.setStatementId(inputDTO.getStatementId());
		payRecordEntity.setPayType(inputDTO.getPayType());
		payRecordEntity.setPayTime(inputDTO.getPayTime());
		//支付状态 0未支付 1已支付 9支付失败
		payRecordEntity.setPayStatus(PAY_SERIALNUMBER.STATUS_PAY);
		payRecordEntity.setCreateTime(DateUtil.getNow());
		payRecordEntity.setCreateuserid(String.valueOf(orderBaseDTO.getSellMemberId()));
		payRecordEntity.setUpdatetime(DateUtil.getNow());
//		payRecordEntity.setMemberId(orderBaseDTO.getSellMemberId().intValue());
		payRecordEntity.setRemark(inputDTO.getPayInfo());
		payRecordEntity.setPaymentAcc(inputDTO.getPaymentAccount());
		payRecordEntity.setPosNumber(inputDTO.getPosNumber());

		Map<String, Object> totalMap = new HashMap<>();
		OrderBaseinfoDTO upOrderBase = new OrderBaseinfoDTO();
		upOrderBase.setOrderNo(orderBaseDTO.getOrderNo());
		upOrderBase.setOrderStatus(Order.STATUS_PAID);
		upOrderBase.setPayType(payRecordEntity.getPayType());
		if (EPayType.OFFLINE_CARD.getCode().equals(payRecordEntity.getPayType())) {
			upOrderBase.setPaySubType(inputDTO.getPaySubType());
		}
		if (orderBaseDTO.getMemberId() == null || orderBaseDTO.getMemberId() == 0) {
			//根据付款卡号查询会员信息
			MemberInfoAppDTO memberInfoAppDTO = posBankCardToolService.getByBankNo(String.valueOf(inputDTO.getPaymentAccount()));
			if (memberInfoAppDTO == null) {
				logger.info("付款卡号不存在");
				//新增会员，注册类型是7，即pos刷卡支付用户
				MemberBaseinfoEntity member = new MemberBaseinfoEntity();
				member.setAccount(UUID.randomUUID().toString().replace("-", "").toUpperCase());
				member.setMobile(member.getAccount());
				member.setCreateTime(new Date());
				member.setLevel(Integer.parseInt(MemberBaseinfoEnum.LEVEl_3.getKey()));
				member.setStatus(MemberBaseinfoEnum.STATUS_1.getKey());
				member.setCreateUserId("SYS");
				member.setRegetype(MemberBaseinfoEnum.REGETYPE_7.getKey());
				Long mid = memberToolService.addMemberBaseinfoEnt(member);
				logger.info("新增会员成功，memberId=" + mid);
				//新增用户-pos卡号关联关系
				PosBankCardDTO posBankCardDTO = new PosBankCardDTO();
				posBankCardDTO.setBankCardNo(inputDTO.getPaymentAccount());
				posBankCardDTO.setMemberId(mid);
				posBankCardDTO.setCreatUserId("SYS");
				posBankCardToolService.addPosBankCardDTO(posBankCardDTO);
				logger.info("新增用户-pos卡号关联成功");
				upOrderBase.setMemberId(mid.intValue());
			} else {
				upOrderBase.setMemberId(memberInfoAppDTO.getMemberId().intValue());
			}
			payRecordEntity.setMemberId(upOrderBase.getMemberId());
		} else {
			payRecordEntity.setMemberId(orderBaseDTO.getMemberId().intValue());
		}
		totalMap.put("orderBase", upOrderBase);
		totalMap.put("paySerialNumEntity", payRecordEntity);

		getHessianOrderbaseService().confirmReceive(totalMap);

		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				dataToolService.cleanTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), TimeCacheType.HOUR_CACHE);
				dataToolService.cleanOldTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), orderBaseDTO.getOrderTime());
				dataToolService.cleanGoodsCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), TimeCacheType.HOUR_CACHE);
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}
		return result;
	}

	@Override
	@Transactional
	public String payByCard(OrderAppInputDTO inputDTO) throws Exception {
		//根据付款卡号查询会员信息
		MemberInfoAppDTO memberInfoAppDTO = posBankCardToolService.getByBankNo(String.valueOf(inputDTO.getPaymentAccount()));
		if (memberInfoAppDTO == null) {
			logger.info("付款卡号不存在");
			//新增会员，注册类型是7，即pos刷卡支付用户
			MemberBaseinfoEntity member = new MemberBaseinfoEntity();
			member.setAccount(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			member.setMobile(member.getAccount());
			member.setCreateTime(new Date());
			member.setLevel(Integer.parseInt(MemberBaseinfoEnum.LEVEl_3.getKey()));
			member.setStatus(MemberBaseinfoEnum.STATUS_1.getKey());
			member.setCreateUserId("SYS");
			member.setRegetype(MemberBaseinfoEnum.REGETYPE_7.getKey());
			long memberId = memberToolService.addMemberBaseinfoEnt(member);
			logger.info("新增会员成功，memberId=" + memberId);
			//新增用户-pos卡号关联关系
			PosBankCardDTO posBankCardDTO = new PosBankCardDTO();
			posBankCardDTO.setBankCardNo(inputDTO.getPaymentAccount());
			posBankCardDTO.setMemberId(memberId);
			posBankCardDTO.setCreatUserId("SYS");
			posBankCardToolService.addPosBankCardDTO(posBankCardDTO);
			logger.info("新增用户-pos卡号关联成功");
			inputDTO.setMemberId(memberId);
		} else {
			inputDTO.setMemberId(memberInfoAppDTO.getMemberId());
		}
		//新增订单
		inputDTO.setOrderStatus(Integer.parseInt(Order.STATUS_PAID));
		StatusCodeEnumWithInfo addResult = addAnonymousOrder(inputDTO);
		logger.info("刷卡消费增加订单结果：" + addResult);
		if (ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()) {
			//订单中心POS刷卡来源的订单，创建时间需比成交时间早60~100秒（随机增加）
			OrderBaseinfoDTO orderB = new OrderBaseinfoDTO();
			orderB.setOrderNo((Long) addResult.getObj());
			long td = inputDTO.getPayTime().getTime();
			Random rand = new Random();
			int rt = rand.nextInt(40);
			Date ort = new Date();
			ort.setTime(td - (rt + 60) * 1000);
			orderB.setOrderTime(ort);//比交易时间早60-100秒
			orderBaseinfoService.updateByOrderNo(orderB);
			//新增订单支付流水
			PaySerialnumberEntity payRecordEntity = new PaySerialnumberEntity();
			payRecordEntity.setOrderNo((Long) addResult.getObj());
			//支付方式 1钱包余额 2线下刷卡 3现金
			payRecordEntity.setPayType(PAY_SERIALNUMBER.PAYTYPE_BANKCARD);
			payRecordEntity.setStatementId(inputDTO.getStatementId());
			//支付时间
			payRecordEntity.setPayTime(inputDTO.getPayTime());
			//支付状态 0未支付 1已支付 9支付失败
			payRecordEntity.setPayStatus(PAY_SERIALNUMBER.STATUS_PAY);
			payRecordEntity.setTradeAmount(inputDTO.getPayAmount());
			payRecordEntity.setCreateTime(DateUtil.getNow());
			payRecordEntity.setCreateuserid(String.valueOf(inputDTO.getMemberId()));
			payRecordEntity.setUpdatetime(DateUtil.getNow());
			payRecordEntity.setMemberId(inputDTO.getMemberId().intValue());
			payRecordEntity.setRemark(inputDTO.getPayInfo());
			payRecordEntity.setPaymentAcc(inputDTO.getPaymentAccount());
			payRecordEntity.setPosNumber(inputDTO.getPosNumber());
			paySerialnumberToolService.insertEntity(payRecordEntity);
			logger.info("刷卡消费增加支付流水成功");
			inputDTO.setOrderNo((Long) addResult.getObj());
			return "OK";
		}
		return addResult.getStatusCodeEnum().getStatusMsg();
	}

	@Override
	public ErrorCodeEnum lock(OrderAppInputDTO inputDTO, boolean isLock) throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		Long businessId = inputDTO.getBusinessId();
		if (orderNo == null) {
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}

		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if (orderBaseDTO == null) {
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}
		String status = orderBaseDTO.getOrderStatus();
		if (!Order.STATUS_NOT_PAY.equals(status)) {
			return ErrorCodeEnum.ORDER_STATUS_ERROR;
		}

		if (orderBaseDTO.getBusinessId() != businessId.intValue()) {
			return ErrorCodeEnum.ORDER_NOT_ORDER_SELLER;
		}

		if (isLock) {
			orderBaseDTO.setIsLock(Order.ORDER_IS_LOCKED);
			orderBaseDTO.setLockTime(new Date());
		} else {
			orderBaseDTO.setIsLock(Order.ORDER_IS_UNLOCKED);
		}
		getHessianOrderbaseService().updateByOrderNo(orderBaseDTO);
		return ErrorCodeEnum.SUCCESS;
	}
}
