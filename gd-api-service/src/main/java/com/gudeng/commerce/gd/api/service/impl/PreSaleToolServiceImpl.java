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
import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.SellerOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.OrderProductDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AccTransInfoToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.OrderSubToolService;
import com.gudeng.commerce.gd.api.service.PreSaleToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.WalletToolService;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.api.util.RadomUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.AccTransInfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.PreSaleDTO;
import com.gudeng.commerce.gd.order.dto.PreSaleDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.entity.PreSaleDetailEntity;
import com.gudeng.commerce.gd.order.entity.PreSaleEntity;
import com.gudeng.commerce.gd.order.service.PreSaleDetailService;
import com.gudeng.commerce.gd.order.service.PreSaleService;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

public class PreSaleToolServiceImpl implements PreSaleToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PreSaleToolServiceImpl.class);
	
	@Autowired
	public GdProperties gdProperties;
	
	private PreSaleDetailService preSaleDetailService;
	
	private PreSaleService preSaleService;
	
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
	
	private PreSaleService getHessianPreSaleService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.preSaleService.url");
		if (preSaleService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			preSaleService = (PreSaleService) factory.create(PreSaleService.class, hessianUrl);
		}
		return preSaleService;
	}
	
	private PreSaleDetailService getHessianPreSaleDetailService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.preSaleDetailService.url");
		if (preSaleDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			preSaleDetailService = (PreSaleDetailService) factory.create(PreSaleDetailService.class, hessianUrl);
		}
		return preSaleDetailService;
	}

	@Override
	public StatusCodeEnumWithInfo addOrders(OrderAppInputDTO inputDTO) throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo();    //正常返回结果
		Long memberId = inputDTO.getMemberId();
		Long businessId = inputDTO.getBusinessId();
		String productDetials = inputDTO.getjProductDetails();
		Double zero = new Double(0);
		Double orderAmount = inputDTO.getOrderAmount();
		if(memberId == null){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_MEMBERID_IS_NULL);
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
		if(orderAmount == null || orderAmount.compareTo(zero)==0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_IS_NULL);
			return statusCode;
		}
		if(orderAmount.compareTo(Order.MAX_ORDER_PRICE)>0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_EXCEED_MAX_AMOUNT);
			return statusCode;
		}
		BusinessBaseinfoDTO  businiessDTO = businessBaseinfoToolService.getById(businessId+"");
		if(businiessDTO == null){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.BUSINESS_IS_NOT_EXISTED);
			return statusCode;
		}
		
		Long orderNo = new Long(orderNoToolServiceImpl.getOrderNo());
		JSONArray jsonArr = JSONUtils.parseArray(productDetials);
		PreSaleEntity orderEntity = new PreSaleEntity();
		List<PreSaleDetailEntity> entityList = new ArrayList<>();
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
			if(price.compareTo(zero) == 0 || quantity == null || quantity.compareTo(zero) == 0 
					|| payAmount.compareTo(zero) == 0 ){
				logger.warn("订单商品:"+ productName +"的价格和订购数量不能为空或为0");
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_PERCHASE_QUANTITY_IS_NULL);
				return statusCode;
			}
			Double productPay = MathUtil.round(MathUtil.mul(price, quantity) ,2);
			//检查产品支付金额和单价*数量是否匹配
			if(productPay.compareTo(payAmount)!=0){
				logger.warn("订单号："+ orderNo +",产品:"+ productName +"的支付金额:"+ payAmount +
						" 和应付金额:"+ productPay +" 不匹配");
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_NOT_MATCH);
				return statusCode;
			}
			
			pIdList.add(productId);
			
			//支付总额
			orderPaySum = MathUtil.add(orderPaySum, payAmount);
			//产品总额
			productPaySum = MathUtil.add(productPaySum, productPay);
			
			PreSaleDetailEntity entity = new PreSaleDetailEntity();
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
		
		List<ProductDto> productList = productToolService.getListByIds(pIdList);
		for(int i=0, len=productList.size(); i<len; i++){
			ProductDto pDTO = productList.get(i);
			Double existedStock = pDTO.getStockCount();
			String state = pDTO.getState();
			if(!"3".equals(state)){//检查产品是否已下架
				statusCode.setStatusCodeEnum(ErrorCodeEnum.PRODUCT_NO_STOCK_COUNT);
				return statusCode;
			}
			
			for(int j=0, len2 = entityList.size(); j<len2;j++){
				PreSaleDetailEntity entity = entityList.get(j);
				Integer pId = entity.getProductId();
				Double quantity = entity.getPurQuantity();
				if(pId == pDTO.getProductId().intValue()){
					if(quantity.compareTo(existedStock) > 0){
						logger.warn("库存不足, 订单号："+ orderNo +",产品:"+ entity.getProductName() 
								+"的购买数量:"+ quantity + " 和库存:"+ existedStock);
						statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_STOCKCOUNT_LACK);
						return statusCode;
					}
					entity.setUnit(pDTO.getUnit());
				}
			}
		}
		
		//订单金额
		if(orderPaySum.compareTo(productPaySum)!=0 || orderPaySum.compareTo(orderAmount)!=0){
			logger.warn("订单金额不匹配,订单金额:"+ orderAmount +", 应付金额:"+ orderPaySum);
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_NOT_MATCH);
			return statusCode;
		}
		
		//插入订单信息进预售表
		String qcCode = RadomUtil.generateNumber() + RadomUtil.generateNumber();  //二维码
		orderEntity.setOrderNo(orderNo);
		orderEntity.setOrderSource("1");                //订单来源 1卖家代客下单 2买家下单
		orderEntity.setChannel(inputDTO.getChannel());
		orderEntity.setSumAmount(orderAmount);
		orderEntity.setDeductibleAmount(zero);;
		orderEntity.setAmountPayable(orderAmount);;
		orderEntity.setOrderStatus("1");
		orderEntity.setMemberid(memberId.intValue());
		orderEntity.setOrderTime(DateUtil.getNow());
		orderEntity.setShopsName(businiessDTO.getShopsName());
		orderEntity.setBusinessId(businessId.intValue());
		orderEntity.setMarketId(Integer.parseInt(businiessDTO.getMarketId()));
		orderEntity.setCreateuserid(memberId+"");
		orderEntity.setCreateTime(DateUtil.getNow());
		orderEntity.setUpdatetime(DateUtil.getNow());
		orderEntity.setQRCode(qcCode);
		
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderBase", orderEntity);
		totalMap.put("orderProductList", entityList);
		getHessianPreSaleService().addPreSale(totalMap);
		
		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		statusCode.setObj(qcCode + "#_#" + orderNo);
		return statusCode;
	}

	@Override
	public Integer updateOrderStatus(Long orderNo, String status)
			throws Exception {
		PreSaleDTO dto = new PreSaleDTO();
		dto.setOrderNo(orderNo);
		dto.setOrderStatus(status);
		return getHessianPreSaleService().updateStatusByOrderNo(dto);
	}
	

	@Override
	public StatusCodeEnumWithInfo confirmOrder(OrderAppInputDTO inputDTO) 
			throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo();
		Long orderNo = inputDTO.getOrderNo();
		Long memberId = inputDTO.getMemberId();
		String qcCode = inputDTO.getQcCode();
		String payType = inputDTO.getPayType();    //支付方式： 1 pos刷卡; 2 现金交易; 3 余额交易
		String tradePwd = inputDTO.getTradePwd();  //交易密码
		Double discountAmount = inputDTO.getDiscountAmount();
		if(memberId == null){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_BUYER_IS_NULL);
			return statusCode;
		}
		
		if(!"1".equals(payType) && !"2".equals(payType) && !"3".equals(payType)){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PAYTYPE_ERROR);
			return statusCode;
		}
		
		if(orderNo == null && StringUtils.isBlank(qcCode)){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_QCCODE_IS_NULL);
			return statusCode;
		}
		
		PreSaleDTO preSaleDTO = getByOrderNo(orderNo, qcCode);
		if(preSaleDTO == null){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_NOT_EXISTED);
			return statusCode;
		}
		
		if(!"1".equals(preSaleDTO.getOrderStatus())){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_CANNOT_CANCEL);
			return statusCode;
		}
		
		Integer sellerId = preSaleDTO.getMemberid();
		if(memberId.intValue() == sellerId){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_BUY_YOUR_OWN_PRODUCT);
			return statusCode;
		}
		
		Double zero = new Double(0);
		if(discountAmount == null){
			discountAmount = zero;
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
		
		orderNo = preSaleDTO.getOrderNo();
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderNo", orderNo);
		List<PreSaleDetailDTO> orderPorudctList = getHessianPreSaleDetailService().getListByCondition(totalMap);
		
		OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
		List<OrderProductDetailEntity> entityList = new ArrayList<>();
		List<Map<String, Object>> stockList = new ArrayList<>();
		List<Long> pIdList = new ArrayList<>();
		
		orderNo = new Long(orderNoToolServiceImpl.getOrderNo());
		for(int i=0,len=orderPorudctList.size();i<len;i++){
			PreSaleDetailDTO preSaleDetail = orderPorudctList.get(i);
			pIdList.add(Long.parseLong(preSaleDetail.getProductId()+""));
			
			OrderProductDetailEntity entity = new OrderProductDetailEntity();
			entity.setOrderNo(orderNo);
			entity.setProductId(preSaleDetail.getProductId());
			entity.setProductName(preSaleDetail.getProductName());
			entity.setPurQuantity(preSaleDetail.getPurQuantity());
			entity.setPrice(preSaleDetail.getPrice());
			entity.setTradingPrice(preSaleDetail.getTradingPrice());
			entity.setNeedToPayAmount(preSaleDetail.getNeedToPayAmount());
			entity.setCreateUserId(memberId+"");
			entityList.add(entity);
		}
		
		//设置订单产品单位信息
		List<ProductDto> productList = productToolService.getListByIds(pIdList);
		//
		for(int i=0, len=productList.size(); i<len; i++){
			ProductDto pDTO = productList.get(i);
			Double existedStock = pDTO.getStockCount();
			String state = pDTO.getState();
			if(!"3".equals(state)){//检查产品是否已下架
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_ID_IS_NULL);
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
						statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_PERCHASE_QUANTITY_IS_NULL);
						return statusCode;
					}
					else if(quantity.compareTo(existedStock) == 0){//下单后，产品库存为0，自动下架
						map.put("status", "4");
					}else{                                       //库存充足
						map.put("status", "3");
					}
					
					map.put("stockCount", MathUtil.sub(existedStock, quantity));
					stockList.add(map);
					entity.setUnit(pDTO.getUnit());
				}
			}
		}
		
		for(int i=0, len=productList.size(); i<len; i++){
			ProductDto pDTO = productList.get(i);
			for(int j=0, len2 = entityList.size(); j<len2;j++){
				OrderProductDetailEntity entity = entityList.get(j);
				if(entity.getProductId() == pDTO.getProductId().intValue()){
					entity.setUnit(pDTO.getUnit());
				}
			}
		}
				
		orderEntity.setOrderNo(orderNo);
		orderEntity.setOrderSource("1");                //订单来源 1卖家代客下单 2买家下单
		orderEntity.setChannel(preSaleDTO.getChannel());  //渠道 1android 2ios 3pc
		orderEntity.setOrderAmount(preSaleDTO.getSumAmount());
		Double payAmount = preSaleDTO.getAmountPayable();
		
		//提交支付方式： 1 pos刷卡; 2 现金交易; 3 余额交易
		//支付方式 1钱包余额 2线下刷卡 3现金交易 12钱包余额+线下刷卡 13钱包余额+现金交易
		String ordePayType = Order.PAYTYPE_BANKCARD;
		//订单状态 1待付款 2部分付款 3已付款 4已出场  8已取消 9已作废 10已完成
		String orderStatus = Order.STATUS_NOT_PAY; 
		if(discountAmount.compareTo(zero)>0){ //使用钱包支付
			if("1".equals(payType)){ //钱包余额+线下刷卡
				ordePayType = Order.PAYTYPE_WALLET_AND_BANKCARD;
				orderStatus = Order.STATUS_PARTIAL_PAY;
			}else{//钱包余额+现金交易
				ordePayType = Order.PAYTYPE_WALLET_AND_CASH;
				orderStatus = Order.STATUS_FINISH;
			}
			
			//实际支付为0, 钱包余额支付完订单
			payAmount = MathUtil.sub(payAmount, discountAmount);
			if(payAmount.compareTo(zero)==0){
				ordePayType = Order.PAYTYPE_WALLET;
				orderStatus = Order.STATUS_FINISH;
			}
			
		}else{
			if("2".equals(payType)){ //现金交易
				ordePayType = Order.PAYTYPE_CASH;
				orderStatus = Order.STATUS_FINISH;
			}
		}
		
		orderEntity.setDiscountAmount(discountAmount);
		orderEntity.setPayAmount(payAmount);
		//支付方式 1钱包余额 2线下刷卡 3钱包+线下刷卡
		orderEntity.setPayType(ordePayType); 
		//订单状态 1待付款 2部分付款 3已付款 4已出场  8已取消 9已作废 10已完成
		orderEntity.setOrderStatus(orderStatus); 
		orderEntity.setMemberId(memberId.intValue());
		orderEntity.setSellMemberId(sellerId);//加入卖家id
		orderEntity.setOrderTime(DateUtil.getNow());
		orderEntity.setShopName(preSaleDTO.getShopsName());
		orderEntity.setBusinessId(preSaleDTO.getBusinessId());
		orderEntity.setMarketId(preSaleDTO.getMarketId());
		orderEntity.setCreateUserId(memberId+"");
		orderEntity.setCreateTime(DateUtil.getNow());
		orderEntity.setUpdateTime(DateUtil.getNow());
		orderEntity.setOutmarkStatus("0");
		orderEntity.setExamineStatus("0");
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
			cashRecordEntity.setPayType(PAY_SERIALNUMBER.PAYTYPE_CASH);  //支付方式 1钱包余额 2线下刷卡 3现金交易
			cashRecordEntity.setPayStatus(PAY_SERIALNUMBER.STATUS_PAY);  //支付状态 0未支付 1已支付 9支付失败
			cashRecordEntity.setTradeAmount(payAmount);
			cashRecordEntity.setCreateTime(DateUtil.getNow());
			cashRecordEntity.setCreateuserid(memberId+"");
			cashRecordEntity.setUpdatetime(DateUtil.getNow());
			cashRecordEntity.setMemberId(memberId.intValue());
			totalMap.put("cashRecordEntity", cashRecordEntity);
		}
				
		totalMap.put("orderBase", orderEntity);
		totalMap.put("orderProductList", entityList);
		totalMap.put("stockList", stockList);  //减少库存
		boolean result = getHessianPreSaleService().confirm(totalMap);
		logger.info("订单号： " + ", 二维码： " + qcCode + ", 确认结果： "+ result);
		
		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		statusCode.setObj(orderNo);
		return statusCode;
	}

	@Override
	public OrderDetailAppDTO getOrderDetail(Long orderNo, Long memberId, String qcCode) throws Exception {
		PreSaleDTO orderBaseDTO = getByOrderNo(orderNo, qcCode);
		if(orderBaseDTO == null){
			return null;
		}
		
		//查找订单产品详情
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		List<PreSaleDetailDTO> orderProductList = getHessianPreSaleDetailService().getListByCondition(map);
		
		//图片地址增加图片服务器地址
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		List<OrderProductDetailDTO> productList = getProductDetailList(orderProductList, imageHost);
		productList = orderSubToolService.addSubIntoProduct(productList);
		//设置返回订单详细信息dto
		OrderDetailAppDTO orderDetailReturn = new OrderDetailAppDTO();
		orderDetailReturn.setOrderNo(orderBaseDTO.getOrderNo());
		orderDetailReturn.setOrderStatus(orderBaseDTO.getOrderStatus());
		orderDetailReturn.setMarketId(orderBaseDTO.getMarketId());
		orderDetailReturn.setMemberId(orderBaseDTO.getMemberid());
		orderDetailReturn.setBusinessId(orderBaseDTO.getBusinessId());
		orderDetailReturn.setShopName(orderBaseDTO.getShopsName());
		orderDetailReturn.setMobile(orderBaseDTO.getMobile());
		orderDetailReturn.setProductDetails(getProductList(productList));
		orderDetailReturn.setHasBuySubPay("0");
		orderDetailReturn.setHasSellSubPay("0");
		orderDetailReturn.setQrCode(orderBaseDTO.getQRCode());  //设置二维码
		orderDetailReturn.setOrderAmount(orderBaseDTO.getSumAmount());
		orderDetailReturn.setDiscountAmount(orderBaseDTO.getDeductibleAmount());
		orderDetailReturn.setSubAmount("0");
		orderDetailReturn.setPayAmount(orderBaseDTO.getAmountPayable());
		orderDetailReturn.setCreateTime(DateTimeUtils.formatDate(orderBaseDTO.getCreateTime(),""));
		orderDetailReturn.setOrderStatus("5");
		//设置卖家电话
		BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(orderBaseDTO.getBusinessId()+"");
		orderDetailReturn.setMobile(businessDTO.getMobile());
		//插入用户余额信息
		AccInfoDTO accInfo = accInfoToolService.getWalletIndex(memberId);
		if(accInfo != null){
			orderDetailReturn.setBalAvailable(accInfo.getBalAvailable());
			orderDetailReturn.setHasPwd(accInfo.getHasPwd());
		}else{
			orderDetailReturn.setBalAvailable(new Double(0));
			orderDetailReturn.setHasPwd(0);
		}
		return orderDetailReturn;
	}

	@Override
	public int getOrdersTotal(Map<String, Object> map) throws Exception {
		return getHessianPreSaleService().getTotal(map);
	}

	@Override
	public List<SellerOrderListDTO> getOrderList(Map<String, Object> map)
			throws Exception {
		List<SellerOrderListDTO> orderList = null;
		List<PreSaleDTO> orderBaseList = getHessianPreSaleService().getListByConditionPage(map);
		if(orderBaseList!=null && orderBaseList.size() > 0){
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				PreSaleDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}
			
			List<PreSaleDetailDTO> productOrderList = getHessianPreSaleDetailService().getListByOrderNoList(orderNoList);
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				SellerOrderListDTO returnDto = new SellerOrderListDTO();
				PreSaleDTO dto = orderBaseList.get(i);
				Long orderNo = dto.getOrderNo();
				returnDto.setOrderNo(orderNo);
				returnDto.setCreateDate(DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_MONTH_AND_DAY));
				returnDto.setOrderAmount(dto.getSumAmount());
				returnDto.setFormattedPrice(MoneyUtil.format(dto.getSumAmount()));
				returnDto.setOrderStatus("5");  //待确认状态
				returnDto.setQrCode(dto.getQRCode()); //二维码
				StringBuilder productSB = new StringBuilder();
				int productCount = 0;
				for(int j=0,pLen=productOrderList.size();j<pLen;j++){
					PreSaleDetailDTO pDto = productOrderList.get(j);
					if(orderNo.equals(pDto.getOrderNo())){
						productSB.append(pDto.getProductName() + ",");
						productCount++;
					}
				}
				returnDto.setProductsName(productSB.deleteCharAt(productSB.length() - 1).toString());
				returnDto.setProductNum(productCount);
				orderList.add(returnDto);
			}
		}
		return orderList;
	}

	@Override
	public ErrorCodeEnum cancelOrder(OrderAppInputDTO inputDTO) throws Exception {
		Long orderNo = inputDTO.getOrderNo();
		
		if(orderNo == null){
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}
		
		PreSaleDTO preSaleDTO = getByOrderNo(orderNo, inputDTO.getQcCode());
		if(preSaleDTO == null){
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}
		preSaleDTO.setOrderStatus("3");
		getHessianPreSaleService().updateStatusByOrderNo(preSaleDTO);
		
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public PreSaleDTO getByOrderNo(Long orderNo, String qcCode)
			throws Exception {
		return getHessianPreSaleService().getByOrderNo(orderNo, qcCode);
	}
	
	private List<OrderProductDetailDTO> getProductDetailList(
			List<PreSaleDetailDTO> orderPorudctList, String imageHost) {
		List<OrderProductDetailDTO> returnList = new ArrayList<>();
		for(int i=0,len=orderPorudctList.size();i<len;i++){
			OrderProductDetailDTO pDTO = new OrderProductDetailDTO();
			PreSaleDetailDTO sDTO = orderPorudctList.get(i);
			pDTO.setProductId(sDTO.getProductId());
			pDTO.setProductName(sDTO.getProductName());
			pDTO.setImageUrl(imageHost + sDTO.getImageUrl());
			pDTO.setPurQuantity(sDTO.getPurQuantity());
			pDTO.setPrice(sDTO.getPrice());
			pDTO.setTradingPrice(sDTO.getTradingPrice());
			pDTO.setNeedToPayAmount(sDTO.getNeedToPayAmount());
			pDTO.setUnitName(sDTO.getUnitName());
			pDTO.setFormattedPrice(MoneyUtil.format(sDTO.getPrice()));
			returnList.add(pDTO);
		}
		return returnList;
	}
	
	private List<OrderProductDTO> getProductList(
			List<OrderProductDetailDTO> productOrderList) {
		List<OrderProductDTO> productDetailList = new ArrayList<>();
		for(int j=0,pLen=productOrderList.size();j<pLen;j++){
			OrderProductDetailDTO pDto = productOrderList.get(j);
			OrderProductDTO productDTO = new OrderProductDTO();
			productDTO.setOrderNo(productDTO.getOrderNo());
			productDTO.setFormattedPrice(pDto.getFormattedPrice());
			productDTO.setHasBuySub(pDto.getHasBuySub());
			productDTO.setHasSellSub(pDto.getHasSellSub());
			productDTO.setImageUrl(productDTO.getImageUrl());
			productDTO.setNeedToPayAmount(pDto.getNeedToPayAmount());
			productDTO.setPrice(pDto.getPrice());
			productDTO.setProductId(pDto.getProductId());
			productDTO.setProductName(pDto.getProductName());
			productDTO.setPurQuantity(pDto.getPurQuantity());
			productDTO.setTradingPrice(pDto.getTradingPrice());
			productDTO.setUnitName(pDto.getUnitName());
			productDetailList.add(productDTO);
		}
		return productDetailList;
	}
}
