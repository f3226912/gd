package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gudeng.commerce.gd.api.dto.OrderAppReturnDTO;
import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.SellerOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.OrderProductDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AccTransInfoToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.DataToolService;
import com.gudeng.commerce.gd.api.service.OrderSubToolService;
import com.gudeng.commerce.gd.api.service.OrderToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.WalletToolService;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.service.DeliveryAddressService;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

public class OrderToolServiceImpl implements OrderToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderToolServiceImpl.class);
	
	@Autowired
	public GdProperties gdProperties;
	
	private OrderBaseinfoService orderBaseinfoService;
	
	private OrderProductDetailService orderProductDetailService;
	
	private PaySerialnumberService paySerialnumberService;
	
	@Autowired
	private ProductToolService productToolService;
	@Autowired
	public OrderSubToolService orderSubToolService;
	@Autowired
	private WalletToolService accInfoToolService;
	@Autowired
	private AccTransInfoToolService accTransInfoToolService;
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private OrderNoToolServiceImpl orderNoToolServiceImpl;
	
	@Autowired
	private DataToolService dataToolService;
	private static DeliveryAddressService deliveryAddressService;

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
	public int updateByOrderNo(OrderBaseinfoDTO orderBaseinfoDTO) throws Exception {
		return getHessianOrderbaseService().updateByOrderNo(orderBaseinfoDTO);
	}

	@Override
	public int resumeStock(Map<String, Object> map) throws Exception {
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
		// 更新产品库存
		getHessianOrderbaseService().batchUpdateStockCount(stockList);
		return 0;
	}

	@Override
	public StatusCodeEnumWithInfo addOrder(OrderAppInputDTO inputDTO) throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo();    //正常返回结果
		Long memberId = inputDTO.getMemberId();
		Long businessId = inputDTO.getBusinessId();
		String productDetials = inputDTO.getjProductDetails();
		String shopName = inputDTO.getShopName();
		Double zero = new Double(0);
		Double orderAmount = inputDTO.getOrderAmount();
		Double discountAmount = inputDTO.getDiscountAmount();
		Double userPayAmount = inputDTO.getPayAmount();
		String tradePwd = inputDTO.getTradePwd();  //交易密码
		String payType = inputDTO.getPayType(); //支付方式： 1 pos刷卡; 2 现金交易; 3 余额交易
		if(memberId == null){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_BUYER_IS_NULL);
			return statusCode;
		}
		if(businessId == null){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.BUSINESS_ID_IS_NULL);
			return statusCode;
		}
		if(StringUtils.isBlank(productDetials)){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_INFO_IS_NULL);
			return statusCode;
		}
		if(StringUtils.isBlank(shopName)){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_SHOPNAME_IS_NULL);
			return statusCode;
		}
		if(!"1".equals(payType) && !"2".equals(payType) && !"3".equals(payType)){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PAYTYPE_ERROR);
			return statusCode;
		}
		if(orderAmount == null || orderAmount.compareTo(zero)==0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_IS_NULL);
			return statusCode;
		}
		if(orderAmount.compareTo(Order.MAX_ORDER_PRICE)>0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_EXCEED_MAX_AMOUNT);
			return statusCode;
		}
		if(discountAmount == null){
			discountAmount = zero;
		}
		if(userPayAmount.compareTo(MathUtil.sub(orderAmount, discountAmount))!=0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PAYAMOUNT_ERROR);
			return statusCode;
		}
		//判断余额是否足够支付
		if(discountAmount.compareTo(zero)>0){
			AccInfoDTO accInfo = accInfoToolService.getWalletIndex(memberId);
			if(accInfo == null){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ACCOUNT_NOT_EXISTED);
				return statusCode;
			}
			
			//校验交易密码
			ErrorCodeEnum validateTradePwdResult = accInfoToolService.validateTransPwd(memberId, tradePwd);
			if(ErrorCodeEnum.SUCCESS != validateTradePwdResult){
				statusCode.setStatusCodeEnum(validateTradePwdResult);
				return statusCode;
			}
			
			Double available = accInfo.getBalAvailable();
			if(available.compareTo(discountAmount) < 0){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.WALLET_BALANCE_NOT_ENOUGH);
				return statusCode;
			}
		}
		
		BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(businessId+"");
		Long sellerId = businessDTO.getUserId();
		if(memberId.compareTo(sellerId) == 0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_BUY_YOUR_OWN_PRODUCT);
			return statusCode;
		}
		
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
		for(int i=0, len=jsonArr.size(); i<len; i++){
			JSONObject jsonObject = (JSONObject) jsonArr.get(i);
			Long productId = jsonObject.getLong("productId");
			String productName = jsonObject.getString("productName");
			Double quantity = jsonObject.getDouble("purQuantity");
			Double price = jsonObject.getDouble("price");
			Double payAmount = jsonObject.getDouble("needToPayAmount");
			
			//检查产品id和名字是否为空
			if(productId == null || StringUtils.isBlank(productName)){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_ID_IS_NULL);
				return statusCode;
			}
			
			//检查产品价格， 数量， 交易价格， 实际支付金额
			//检查商品 数量
			if(quantity == null || quantity.compareTo(zero) == 0){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_PERCHASE_QUANTITY_IS_NULL);
				return statusCode;
			}
			
			//检查商品价格
			if(price == null || price.compareTo(zero) == 0){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_PRICE_IS_NULL);
				return statusCode;
			}
			
			Double productPay = MathUtil.round(MathUtil.mul(price, quantity) ,2);
			//检查产品支付金额和单价*数量是否匹配
			if(productPay.compareTo(payAmount)!=0){
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
			entity.setCreateUserId(memberId+"");
			entityList.add(entity);
		}
		
		//设置订单产品单位信息
		List<ProductDto> productList = productToolService.getListByIds(pIdList);
		for(int i=0, len=productList.size(); i<len; i++){
			ProductDto pDTO = productList.get(i);
			Double existedStock = pDTO.getStockCount();
			String state = pDTO.getState();
			if(!"3".equals(state)  || existedStock.compareTo(zero) == 0){//检查产品是否已下架
				statusCode.setStatusCodeEnum(ErrorCodeEnum.PRODUCT_NO_STOCK_COUNT);
				return statusCode;
			}
			
			for(int j=0, len2 = entityList.size(); j<len2;j++){
				OrderProductDetailEntity entity = entityList.get(j);
				Integer pId = entity.getProductId();
				Double quantity = entity.getPurQuantity();
				if(pId == pDTO.getProductId().intValue()){
					Map<String, Object> map = new HashMap<>();
					map.put("productId", pId);
					//判断库存是否足够
					if(quantity.compareTo(existedStock) > 0){//购买量大于库存，返回失败，errorcode=-5
						logger.warn("库存不足, 订单号："+ orderNo +",产品:"+ entity.getProductName() 
								+"的购买数量:"+ quantity + " 和库存:"+ existedStock);
						statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_STOCKCOUNT_LACK);
						return statusCode;
					}
					else if(quantity.compareTo(existedStock) == 0){//下单后，产品库存为0，不自动下架
						map.put("status", "3");
					}else{                                       //库存充足
						map.put("status", "3");
					}
					
					map.put("stockCount", MathUtil.sub(existedStock, quantity));
					stockList.add(map);
					
					entity.setUnit(pDTO.getUnit());
				}
			}
		}
		
		//订单金额
		if(orderPaySum.compareTo(productPaySum)!=0 
				|| orderPaySum.compareTo(orderAmount)!=0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_NOT_MATCH);
			return statusCode;
		}
		
		//插入订单信息
		orderEntity.setOrderNo(orderNo);
		orderEntity.setOrderSource("2");                //订单来源 1卖家代客下单 2买家下单
		orderEntity.setChannel(inputDTO.getChannel());  //渠道 1android 2ios 3pc
		orderEntity.setOrderAmount(orderAmount);
		orderEntity.setDiscountAmount(discountAmount);
		orderEntity.setPayAmount(userPayAmount);
		orderEntity.setTotalPayAmt(orderEntity.getPayAmount());
		//提交支付方式： 1 pos刷卡; 2 现金交易; 3 余额交易
		//支付方式 1钱包余额 2线下刷卡 3现金交易 12钱包余额+线下刷卡 13钱包余额+现金交易
		String ordePayType = Order.PAYTYPE_BANKCARD;
		//订单状态 1待付款 2部分付款 3已付款 4已出场  8已取消 9已作废 10已完成
		String orderStatus = Order.STATUS_NOT_PAY; 
		if(discountAmount.compareTo(zero) > 0){ //使用钱包支付
			if("1".equals(payType)){ //钱包余额+线下刷卡
				ordePayType = Order.PAYTYPE_WALLET_AND_BANKCARD;
				orderStatus = Order.STATUS_PARTIAL_PAY;
			}else{//钱包余额+现金交易
				ordePayType = Order.PAYTYPE_WALLET_AND_CASH;
				orderStatus = Order.STATUS_FINISH;
			}
			 //实际支付为0, 钱包余额支付完订单
			if(orderAmount.compareTo(discountAmount) == 0){
				ordePayType = Order.PAYTYPE_WALLET;
				orderStatus = Order.STATUS_FINISH;
			}
		}else{
			if("2".equals(payType)){ //现金交易
				ordePayType = Order.PAYTYPE_CASH;
				orderStatus = Order.STATUS_FINISH;
			}
		}
		
		orderEntity.setPayType(ordePayType);
		orderEntity.setOrderStatus(orderStatus);
		orderEntity.setMemberId(memberId.intValue());//买家id
		orderEntity.setSellMemberId(sellerId.intValue());//卖家id
		orderEntity.setOrderTime(DateUtil.getNow());
		orderEntity.setShopName(shopName);
		orderEntity.setBusinessId(businessId.intValue());
		orderEntity.setMarketId(Integer.parseInt(businessDTO.getMarketId()));
		orderEntity.setCreateUserId(memberId+"");
		orderEntity.setCreateTime(DateUtil.getNow());
		orderEntity.setUpdateTime(DateUtil.getNow());
		orderEntity.setOutmarkStatus("0");
		orderEntity.setExamineStatus("0");
		orderEntity.setOrderType(Order.TYPE_FROM_NSY);
		orderEntity.setPromType("0");
		orderEntity.setHasCustomer("0");
		orderEntity.setDistributeMode("0");
		String posInfo = businessBaseinfoToolService.getPosInfoByBusinessId(businessId);
		logger.info("Pos info referense: businessId: " + businessId + ", Pos info: " + posInfo);
		orderEntity.setValidPosNum(posInfo);
		
		//插入订单商品明细
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderBase", orderEntity);
		totalMap.put("orderProductList", entityList);
		totalMap.put("stockList", stockList);  //减少库存
		//如果使用了钱包支付, 扣除会员金额
		if(discountAmount.compareTo(zero)>0){
			AccInfoDTO accInfo = accInfoToolService.getWalletIndex(memberId);
			Double available = accInfo.getBalAvailable();
			Double total = accInfo.getBalTotal();
			accInfo.setBalAvailable(MathUtil.sub(available, discountAmount));
			accInfo.setBalTotal(MathUtil.sub(total, discountAmount));
			totalMap.put("acctInfo", accInfo);
			
			//生成支付订单记录
			PaySerialnumberEntity payRecordEntity = new PaySerialnumberEntity();
			payRecordEntity.setOrderNo(orderNo);
			payRecordEntity.setPayTime(DateUtil.getNow());
			//支付方式 1钱包余额 2线下刷卡 3现金交易
			payRecordEntity.setPayType(PAY_SERIALNUMBER.PAYTYPE_WALLET); 
			//支付状态 0未支付 1已支付 9支付失败
			payRecordEntity.setPayStatus(PAY_SERIALNUMBER.STATUS_PAY);  
			payRecordEntity.setTradeAmount(discountAmount);
			payRecordEntity.setCreateTime(DateUtil.getNow());
			payRecordEntity.setCreateuserid(memberId+"");
			payRecordEntity.setUpdatetime(DateUtil.getNow());
			payRecordEntity.setMemberId(memberId.intValue());
			totalMap.put("paySerialNumEntity", payRecordEntity);
			
			//生成用户金额流水记录
			AccTransInfoDTO accTransInfoDTO = new AccTransInfoDTO();
			accTransInfoDTO.setAccId(accInfo.getAccId());
			accTransInfoDTO.setMemberId(accInfo.getMemberId());
			accTransInfoDTO.setOrderNo(orderNo);
			accTransInfoDTO.setTradeType("1");  //交易类型 1余额抵扣 2用户补贴 3提现
			accTransInfoDTO.setPeType("2");  //收支类型 1收入 2支出
			accTransInfoDTO.setTradeAmount(discountAmount);
			accTransInfoDTO.setCreateUserId(accInfo.getMemberId()+"");
			totalMap.put("acctTranInfo", accTransInfoDTO);
		}
		
		//现金交易
		if(Order.PAYTYPE_CASH.equals(ordePayType) 
				|| Order.PAYTYPE_WALLET_AND_CASH.equals(ordePayType)){
			//生成支付订单记录
			PaySerialnumberEntity cashRecordEntity = new PaySerialnumberEntity();
			cashRecordEntity.setOrderNo(orderNo);
			cashRecordEntity.setPayTime(DateUtil.getNow());
			//支付方式 1钱包余额 2线下刷卡 3现金交易
			cashRecordEntity.setPayType(PAY_SERIALNUMBER.PAYTYPE_CASH);
			cashRecordEntity.setPayStatus(PAY_SERIALNUMBER.STATUS_PAY);  //支付状态 0未支付 1已支付 9支付失败
			cashRecordEntity.setTradeAmount(userPayAmount);
			cashRecordEntity.setCreateTime(DateUtil.getNow());
			cashRecordEntity.setCreateuserid(memberId+"");
			cashRecordEntity.setUpdatetime(DateUtil.getNow());
			cashRecordEntity.setMemberId(memberId.intValue());
			totalMap.put("cashRecordEntity", cashRecordEntity);
		}
		
		getHessianOrderbaseService().addOrder(totalMap);
		
		// 清空缓存
		try {
			if (sellerId != null) {
				// 撤单时，需要根据下单时间判断是否今日、昨日、本月等，然后清除对应缓存，及清除小时缓存【memberId_report_mmddhh】
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
	public ErrorCodeEnum cancelOrder(OrderAppInputDTO inputDTO) throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		
		if(orderNo == null){
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}
		
		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if(orderBaseDTO == null){
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}
		String status = orderBaseDTO.getOrderStatus();
		if(!Order.STATUS_NOT_PAY.equals(status) 
				&& !Order.STATUS_PARTIAL_PAY.equals(status)){
			return ErrorCodeEnum.ORDER_CANNOT_CANCEL;
		}
		
		Integer isLock = orderBaseDTO.getIsLock();
		if(isLock != null && Order.ORDER_IS_LOCKED == isLock){
			return ErrorCodeEnum.ORDER_IS_PAYING;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		
		//组装库存数据， 增加产品库存
		List<OrderProductDetailDTO> productList = getHessianOrderProductService().getListByOrderNo(map);
		
		
		List<Map<String, Object>> stockList = new ArrayList<>();
		//产品id list， 用来查找校验产品信息
		List<Long> pIdList = new ArrayList<>();
		for(int i=0,len=productList.size();i<len;i++){
			OrderProductDetailDTO productDTO = productList.get(i);
			pIdList.add(Long.parseLong(productDTO.getProductId()+""));
		}
		//设置订单产品库存
		List<ProductDto> pList = productToolService.getListByIds(pIdList);
		for(int i=0,len=productList.size();i<len;i++){
			OrderProductDetailDTO productDTO = productList.get(i);
			Integer pId = productDTO.getProductId();
			Double purQuan = productDTO.getPurQuantity();
			Map<String, Object> tmpMap = new HashMap<>();
			for(int j=0,len2=pList.size(); j<len2; j++){
				ProductDto pDTO = pList.get(j);
				Double existedStock = pDTO.getStockCount();
				if(pId.equals(pDTO.getProductId().intValue())){
					tmpMap.put("productId", pId);
					Double newStock = MathUtil.add(existedStock, purQuan);
					//如果库存大于最大值， 则使用最大值
					if(newStock.compareTo(Order.MAX_PRODUCT_STOCK) > 0){
						tmpMap.put("stockCount", Order.MAX_PRODUCT_STOCK);
					}else{
						tmpMap.put("stockCount", newStock);
					}
					tmpMap.put("status", "3");  //产品上架
					
					stockList.add(tmpMap);
				}
			}
		}
		map.put("stockList", stockList);
		//取消订单返回用户使用余额
		Double discountAmount = orderBaseDTO.getDiscountAmount();
		if(discountAmount.compareTo(new Double(0)) > 0){
			AccInfoDTO accInfo = accInfoToolService.getWalletIndex(Long.parseLong(orderBaseDTO.getMemberId()+""));
			Double available = accInfo.getBalAvailable();
			Double frozen =accInfo.getBalblock();
			accInfo.setBalblock(MathUtil.sub(frozen, discountAmount));
			accInfo.setBalAvailable(MathUtil.add(available, discountAmount));
			map.put("acctInfo", accInfo);
			//生成用户金额流水记录
			AccTransInfoDTO accTransInfoDTO = new AccTransInfoDTO();
			accTransInfoDTO.setAccId(accInfo.getAccId());
			accTransInfoDTO.setMemberId(accInfo.getMemberId());
			accTransInfoDTO.setOrderNo(orderNo);
			accTransInfoDTO.setTradeType("4");  //交易类型 1余额抵扣 2用户补贴 3提现 4订单返回
			accTransInfoDTO.setPeType("1");  //收支类型 1收入 2支出
			accTransInfoDTO.setTradeAmount(discountAmount);
			accTransInfoDTO.setCreateUserId(accInfo.getMemberId()+"");
			map.put("acctTranInfo", accTransInfoDTO);
		}
		
		getHessianOrderbaseService().cancelByOrderNo(map);
		
		// 清空缓存
		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				// 撤单时，需要根据下单时间判断是否今日、昨日、本月等，然后清除对应缓存，及清除小时缓存【memberId_report_mmddhh】
				dataToolService.cleanTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), TimeCacheType.HOUR_CACHE);
				dataToolService.cleanOldTradeCacheSpecial(orderBaseDTO.getSellMemberId().longValue(), orderBaseDTO.getOrderTime());
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}
				
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public OrderDetailAppDTO getOrderDetail(Long orderNo, Long memberId) throws Exception {
		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if(orderBaseDTO == null){
			return null;
		}
		
		//查找订单产品详情
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNo(map);
		productOrderList = orderSubToolService.addSubIntoProduct(productOrderList);
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		List<OrderProductDTO> productDetailList = new ArrayList<>();
		if(null != productOrderList && productOrderList.size() > 0){
			for(int j=0,pLen=productOrderList.size();j<pLen;j++){
				OrderProductDetailDTO pDto = productOrderList.get(j);
				if(orderNo.equals(pDto.getOrderNo())){
					OrderProductDTO productDTO = new OrderProductDTO();
					productDTO.setOrderNo(orderNo);
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
					productDetailList.add(productDTO);
				}
			}
		}
		
		OrderDetailAppDTO orderDetailReturn = new OrderDetailAppDTO();
		orderDetailReturn.setOrderNo(orderBaseDTO.getOrderNo());
		orderDetailReturn.setMarketId(orderBaseDTO.getMarketId());
		orderDetailReturn.setMemberId(orderBaseDTO.getMemberId());
		orderDetailReturn.setBusinessId(orderBaseDTO.getBusinessId());
		orderDetailReturn.setShopName(orderBaseDTO.getShopName());
		orderDetailReturn.setMobile(orderBaseDTO.getMobile());
		orderDetailReturn.setBuyerMobile(orderBaseDTO.getBuyerMobile());
		orderDetailReturn.setProductDetails(productDetailList);
		//补贴状态
		orderDetailReturn.setHasBuySubPay(orderBaseDTO.getHasSub()+"");
		orderDetailReturn.setHasSellSubPay(orderBaseDTO.getHasSub()+"");
		orderDetailReturn.setPayType(setAppPayType(orderBaseDTO.getPayType(), 0));
		orderDetailReturn.setOrderAmount(orderBaseDTO.getOrderAmount());
		orderDetailReturn.setDiscountAmount(orderBaseDTO.getDiscountAmount());
		orderDetailReturn.setSubAmount("0");
		orderDetailReturn.setPayAmount(orderBaseDTO.getPayAmount());
		orderDetailReturn.setCreateTime(DateTimeUtils.formatDate(orderBaseDTO.getCreateTime(),""));
		orderDetailReturn.setOrderStatus(orderBaseDTO.getOrderStatus());
		orderDetailReturn.setAuditStatus(Order.STATUS_FINISH.equals(orderBaseDTO.getOrderStatus())? "3" : orderBaseDTO.getExamineStatus());
		orderDetailReturn.setAuditDesc(orderBaseDTO.getAuditDesc());
		//如果有支付记录
		//查找订单支付详情
		PaySerialnumberDTO payDTO = getHessianPaySerialnumberService().getByOrderNo(map);
		if(payDTO != null){
			orderDetailReturn.setPaySerialNo(payDTO.getStatementId());           //银行流水号
			orderDetailReturn.setTradeType(setAppPayType(orderBaseDTO.getPayType(), 1));  //支付方式
			orderDetailReturn.setTradeAmount(payDTO.getTradeAmount());           //交易金额
			orderDetailReturn.setTradeTime(DateTimeUtils.formatDate(payDTO.getCreateTime(),""));              //成交时间
		}
		//设置卖家电话
		BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(orderBaseDTO.getBusinessId()+"");
		orderDetailReturn.setMobile(businessDTO.getMobile());
		if (orderDetailReturn.getMobile()!=null && orderDetailReturn.getMobile().length()==32) {
			orderDetailReturn.setMobile("");
		}
		//插入用户余额信息
		AccInfoDTO accInfo = accInfoToolService.getWalletIndex(memberId);
		if(accInfo != null){
			orderDetailReturn.setBalAvailable(accInfo.getBalAvailable());
			orderDetailReturn.setHasPwd(accInfo.getHasPwd());
		}else{
			orderDetailReturn.setBalAvailable(new Double(0));
			orderDetailReturn.setHasPwd(0);
		}
		map.put("status", 1);
		List<DeliveryAddressDTO> list = getHessianDeliveryAddressService().getList(map);//订单收货地址
		if (list != null && list.size() > 0) {
			orderDetailReturn.setDeliveryAddress(list.get(0));
		}
		return orderDetailReturn;
	}

	@Override
	public List<OrderAppReturnDTO> getBuyerOrderList(Map<String, Object> map)
			throws Exception {
		List<OrderAppReturnDTO> orderList = null;
		List<OrderBaseinfoDTO> orderBaseList = getHessianOrderbaseService().getListByStatusPage(map);
		if(orderBaseList!=null && orderBaseList.size() > 0){
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}
			
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNoList(orderNoList);
			productOrderList = orderSubToolService.addSubIntoProduct(productOrderList);
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				Long orderNo = dto.getOrderNo();
				String status = dto.getOrderStatus();
				OrderAppReturnDTO returnDto = new OrderAppReturnDTO();
				returnDto.setOrderNo(orderNo);
				returnDto.setBusinessId(Long.parseLong(dto.getBusinessId()+""));
				returnDto.setShopName(dto.getShopName());
				returnDto.setSubStatus("");//补贴状态
				returnDto.setSubReason("");//补贴原因
				returnDto.setOrderAmount(dto.getOrderAmount());
				returnDto.setOrderStatus(status);
				returnDto.setHasSubPay(dto.getHasSub()+"");
				returnDto.setAuditStatus(Order.STATUS_FINISH.equals(status)? "3" : dto.getExamineStatus());
				returnDto.setAuditDesc(dto.getAuditDesc());
				List<OrderProductDTO> productDetailList = new ArrayList<>();
				if(null != productOrderList && productOrderList.size() > 0){
					for(int j=0,pLen=productOrderList.size();j<pLen;j++){
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if(orderNo.equals(pDto.getOrderNo())){
							OrderProductDTO productDTO = new OrderProductDTO();
							productDTO.setOrderNo(orderNo);
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
							productDetailList.add(productDTO);
						}
					}
				}
				returnDto.setProductDetails(productDetailList);
				orderList.add(returnDto);
			}
		}
		return orderList;
	}
	
	@Override
	public int getOrdersTotal(Map<String, Object> map) throws Exception {
		return getHessianOrderbaseService().getTotalByStatusPage(map);
	}

	@Override
	public ErrorCodeEnum confirmPay(OrderAppInputDTO inputDTO) throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		Long memberId = inputDTO.getMemberId();
		String payImage = inputDTO.getPayImage();
		String statementId = inputDTO.getStatementId();//参考号
		if(orderNo == null){
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}
		
		if(memberId == null){
			return ErrorCodeEnum.ORDER_MEMBERID_IS_NULL;
		}
		
		if(StringUtils.isBlank(payImage) && StringUtils.isBlank(statementId)){
			return ErrorCodeEnum.ORDER_PAY_INFO_IS_NULL;
		}
		
		if(payImage != null && payImage.split(",").length > Order.MAX_IMAGE_NUM){
			return ErrorCodeEnum.ORDER_PAY_INFO_OVER_LENGTH;
		}
		
		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if(orderBaseDTO == null){
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}
		
		//检查订单状态
		String orderStatus = orderBaseDTO.getOrderStatus();
		if(!Order.STATUS_NOT_PAY.equals(orderStatus) 
				&& !Order.STATUS_PARTIAL_PAY.equals(orderStatus)){
			return ErrorCodeEnum.ORDER_CANNOT_CONFIRM_RECEIVABLES;
		}
		
		//支付方式 1钱包余额 2线下刷卡 3现金交易 12钱包余额+线下刷卡 13钱包余额+现金交易
		String payType = orderBaseDTO.getPayType();
		//生成支付订单记录========= start
		PaySerialnumberEntity payRecordEntity = new PaySerialnumberEntity();
		payRecordEntity.setOrderNo(orderNo);
		payRecordEntity.setPayTime(DateUtil.getNow());
		//设置支付方式 1钱包余额 2线下刷卡 3现金交易
		if(Order.PAYTYPE_BANKCARD.equals(payType) 
				|| Order.PAYTYPE_WALLET_AND_BANKCARD.equals(payType)){
			payRecordEntity.setPayType(PAY_SERIALNUMBER.PAYTYPE_BANKCARD);
		}else{
			payRecordEntity.setPayType(payType);
		}
		
		//支付状态 0未支付 1已支付 9支付失败
		payRecordEntity.setPayStatus(PAY_SERIALNUMBER.STATUS_PAY); 
		payRecordEntity.setTradeAmount(orderBaseDTO.getPayAmount());
		payRecordEntity.setStatementId(statementId);
		payRecordEntity.setPayImage(payImage);
		payRecordEntity.setCreateTime(DateUtil.getNow());
		payRecordEntity.setCreateuserid(memberId+"");
		payRecordEntity.setUpdatetime(DateUtil.getNow());
		payRecordEntity.setUpImageTime(DateUtil.getNow());
		payRecordEntity.setMemberId(orderBaseDTO.getMemberId());
		//生成支付订单记录========= end
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("paySerialNumEntity", payRecordEntity);
		//更新订单状态为已支付
		orderBaseDTO.setOrderStatus(Order.STATUS_PAID);
		//审核状态:0待审核 1审核通过 2审核驳回
		orderBaseDTO.setExamineStatus("0");
		totalMap.put("orderBaseDTO", orderBaseDTO);
		
		int updateNum = getHessianOrderbaseService().confirmPay(totalMap);
		logger.info("[INFO]确认订单， 成功执行， 插入记录条数： " + updateNum);
		
		// 清空缓存
		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				// 撤单时，需要根据下单时间判断是否今日、昨日、本月等，然后清除对应缓存，及清除小时缓存【memberId_report_mmddhh】
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
	public OrderBaseinfoDTO getOrderByOrderNo(Long orderNo) throws Exception {
		return getHessianOrderbaseService().getByOrderNo(orderNo);
	}

	@Override
	public List<SellerOrderListDTO> getSellerOrderList(Map<String, Object> map)
			throws Exception {
		List<SellerOrderListDTO> orderList = null;
		List<OrderBaseinfoDTO> orderBaseList = getHessianOrderbaseService().getListByStatusPage(map);
		if(orderBaseList!=null && orderBaseList.size() > 0){
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}
			
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNoList(orderNoList);
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				SellerOrderListDTO returnDto = new SellerOrderListDTO();
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				Long orderNo = dto.getOrderNo();
				returnDto.setOrderNo(orderNo);
				returnDto.setCreateDate(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_MONTH_AND_DAY));
				returnDto.setOrderAmount(dto.getOrderAmount());
				returnDto.setFormattedPrice(MoneyUtil.format(dto.getOrderAmount()));
				returnDto.setOrderStatus(Order.STATUS_FINISH.equals(dto.getOrderStatus()) ? "3" : dto.getOrderStatus());
				returnDto.setBuyerName(dto.getRealName());
				StringBuilder productSB = new StringBuilder();
				int productCount = 0;
				if(null != productOrderList && productOrderList.size() > 0){
					for(int j=0,pLen=productOrderList.size(); j < pLen; j++){
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if(orderNo.equals(pDto.getOrderNo())){
							productSB.append(pDto.getProductName() + ",");
							productCount++;
						}
					}
				}
				returnDto.setAuditStatus(Order.STATUS_FINISH.equals(dto.getOrderStatus())? "3" : dto.getExamineStatus());
				returnDto.setAuditDesc(dto.getAuditDesc());
				if(productCount > 0){
					returnDto.setProductsName(productSB.deleteCharAt(productSB.length() - 1).toString());
				}
				returnDto.setProductNum(productCount);
				orderList.add(returnDto);
			}
		}
		return orderList;
	}

	@Override
	public ErrorCodeEnum uploadVoucherAgain(OrderAppInputDTO inputDTO)
			throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		Long memberId = inputDTO.getMemberId();
		String payImage = inputDTO.getPayImage();
		String statementId = inputDTO.getStatementId();//参考号
		if(orderNo == null){
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}
		
		if(memberId == null){
			return ErrorCodeEnum.ORDER_MEMBERID_IS_NULL;
		}
		
		if(StringUtils.isBlank(payImage) && StringUtils.isBlank(statementId)){
			return ErrorCodeEnum.ORDER_PAY_INFO_IS_NULL;
		}
		
		if(payImage != null && payImage.split(",").length > Order.MAX_IMAGE_NUM){
			return ErrorCodeEnum.ORDER_PAY_INFO_OVER_LENGTH;
		}
		
		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if(orderBaseDTO == null){
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}
		
		//检查订单状态
		String orderStatus = orderBaseDTO.getOrderStatus();
		if(!Order.STATUS_PAID.equals(orderStatus)){
			return ErrorCodeEnum.ORDER_STATUS_ERROR;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		PaySerialnumberDTO payRecordDTO = getHessianPaySerialnumberService().getByOrderNo(map);
		if(payRecordDTO == null){
			return ErrorCodeEnum.ORDER_PAYSERIAL_NOT_EXISTED;
		}
		
		if(StringUtils.isBlank(payImage)){
			payRecordDTO.setStatementId(statementId);
		}else{
			payRecordDTO.setPayImage(payImage);
		}
		
		payRecordDTO.setUpdateuserid(memberId+"");
		
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("paySerialNumDTO", payRecordDTO);
		//审核状态:0待审核 1审核通过 2审核驳回
		orderBaseDTO.setExamineStatus("0");
		totalMap.put("orderBaseDTO", orderBaseDTO);
		
		int updateNum = getHessianOrderbaseService().uploadVoucherAgain(totalMap);
		logger.info("[INFO]上传凭证， 成功执行，修改记录条数： " + updateNum);
		
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public ErrorCodeEnum confirmReceive(OrderAppInputDTO inputDTO) throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		Long memberId = inputDTO.getMemberId();
		if(orderNo == null){
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}
		
		if(memberId == null){
			return ErrorCodeEnum.ORDER_SELLER_IS_NULL;
		}
		
		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(orderNo);
		if(orderBaseDTO == null){
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}
		
		if(orderBaseDTO.getSellMemberId() != memberId.intValue()){
			return ErrorCodeEnum.ORDER_NOT_ORDER_SELLER;
		}
		
		//校验是否已支付通过审核状态
		if(!Order.STATUS_PAID.equals(orderBaseDTO.getOrderStatus())
				&& !"1".equals(orderBaseDTO.getExamineStatus())){
			return ErrorCodeEnum.ORDER_CANNOT_CONFIRM_RECEIVABLES;
		}
		
		orderBaseDTO.setOrderStatus(Order.STATUS_FINISH);
		getHessianOrderbaseService().updateByOrderNo(orderBaseDTO);
		
		// 清空缓存
		try {
			if (orderBaseDTO.getSellMemberId() != null) {
				// 撤单时，需要根据下单时间判断是否今日、昨日、本月等，然后清除对应缓存，及清除小时缓存【memberId_report_mmddhh】
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
	public int getSubListTotal(Map<String, Object> map) throws Exception {
		return orderSubToolService.getSubListTotal(map);
	}

	@Override
	public List<OrderAppReturnDTO> getSubList(Map<String, Object> map)
			throws Exception {
		List<OrderAppReturnDTO> orderList = new ArrayList<>();
		List<SubAuditDTO> subList = orderSubToolService.getSubList(map);
		if(subList != null && subList.size() > 0){
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for(int i=0,len=subList.size(); i<len;i++){
				SubAuditDTO dto = subList.get(i);
				orderNoList.add(dto.getOrderNo());
			}
			
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNoList(orderNoList);
			productOrderList = orderSubToolService.addSubIntoProduct(productOrderList);
			for(int i=0,len=subList.size(); i<len;i++){
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
				if(null != productOrderList && productOrderList.size() > 0){
					for(int j=0,pLen=productOrderList.size();j<pLen;j++){
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if(orderNo.equals(pDto.getOrderNo())){
							OrderProductDTO productDTO = new OrderProductDTO();
							productDTO.setOrderNo(orderNo);
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
							productDetailList.add(productDTO);
						}
					}
				}
				returnDto.setProductDetails(productDetailList);
				//设置补贴信息
				setAppSubInfo(returnDto, dto);
				orderList.add(returnDto);
			}
		}
		return orderList;
	}

	private String setAppPayType(String payType, int type) {
		String returnStr = "未知";
		if(payType == null){
			return returnStr;
		}
		switch(payType){//支付方式 1钱包余额 2线下刷卡 3现金交易 12 钱包+刷卡 13 钱包+现金
		case PAY_SERIALNUMBER.PAYTYPE_WALLET: 
			returnStr = "钱包余额";
			break;
		case PAY_SERIALNUMBER.PAYTYPE_BANKCARD:
			returnStr = "POS刷卡";
			break;
		case PAY_SERIALNUMBER.PAYTYPE_CASH: 
			returnStr = "现金交易";
			break;
		case PAY_SERIALNUMBER.PAYTYPE_WALLET_AND_BANKCARD: 
			if(type == 1){
				returnStr = "POS刷卡 + 钱包余额";
			}else{
				returnStr = "POS刷卡";
			}
			
			break;
		case PAY_SERIALNUMBER.PAYTYPE_WALLET_AND_CASH: 
			if(type == 1){
				returnStr = "现金交易 + 钱包余额";
			}else{
				returnStr = "现金交易";
			}
			break;
		}
		return returnStr;
	}
	
	private void setAppSubInfo(OrderAppReturnDTO returnDto, SubAuditDTO dto) {
		//补贴状态 1待补贴 2系统驳回 3已补贴 4不予补贴
		String status = dto.getSubStatus();
		String reason = dto.getSubComment();
		switch(status){
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

	@Override
	public List<OrderBaseinfoDTO> getAbleGiftOrder(Long memberId) throws Exception {
		return getHessianOrderbaseService().getAbleGiftOrder(memberId);
	}
}
