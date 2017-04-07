package com.gudeng.commerce.gd.api.service.impl.v160721;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant.Order;
import com.gudeng.commerce.gd.api.dto.CommonFeeAppDTO;
import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.output.PromotionDetail;
import com.gudeng.commerce.gd.api.dto.output.PurchaseOrderBaseDTO;
import com.gudeng.commerce.gd.api.dto.output.PurchaseOrderDetailDTO;
import com.gudeng.commerce.gd.api.dto.output.PurchaseOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.PurchaseOrderProductDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ActivityTypeEnum;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.impl.OrderNoToolServiceImpl;
import com.gudeng.commerce.gd.api.service.v160721.PromInfoToolService;
import com.gudeng.commerce.gd.api.service.v160721.PurchaseOrderToolService;
import com.gudeng.commerce.gd.api.util.CountFeeUtil;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.service.DeliveryAddressService;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromOrderProminfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdResult;
import com.gudeng.commerce.gd.promotion.entity.PromOrderProdProminfoEntity;
import com.gudeng.commerce.gd.promotion.entity.PromOrderProminfoEntity;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

public class PurchaseOrderToolServiceImpl implements PurchaseOrderToolService {
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PurchaseOrderToolServiceImpl.class);
	
	@Autowired
	public GdProperties gdProperties;
	
	private OrderBaseinfoService orderBaseinfoService;
	
	private OrderProductDetailService orderProductDetailService;
	
	private PaySerialnumberService paySerialnumberService;
	
	private Lock addLock = new ReentrantLock();
	
	private ReadWriteLock rwlock = new ReentrantReadWriteLock();
	
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private OrderNoToolServiceImpl orderNoToolServiceImpl;
	@Autowired
	private ProductToolService productToolService;
	@Autowired
	private PromInfoToolService promInfoToolService;
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

	protected DeliveryAddressService getHessianDeliveryAddressService() throws MalformedURLException {
		String url = gdProperties.getDeliveryAddressServiceUrl();
		if (deliveryAddressService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			deliveryAddressService = (DeliveryAddressService) factory.create(DeliveryAddressService.class, url);
		}
		return deliveryAddressService;
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

	@Override
	public StatusCodeEnumWithInfo addPurchaseOrder(OrderAppInputDTO inputDTO) throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo();
		Long buyerId = inputDTO.getMemberId();
		Long businessId = inputDTO.getBusinessId();
		String productDetials = inputDTO.getjProductDetails();
		Double orderAmount = inputDTO.getOrderAmount();
		Double userPayAmount = inputDTO.getPayAmount();
		
		Double zero = new Double(0);
		Double newOrderAmount = zero;
		if(buyerId == null){
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
		if(orderAmount == null || orderAmount.compareTo(zero)==0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_AMOUNT_IS_NULL);
			return statusCode;
		}
		if(userPayAmount == null || userPayAmount.compareTo(zero)==0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PAYAMT_IS_NULL);
			return statusCode;
		}
		if(orderAmount.compareTo(Order.MAX_ORDER_PRICE)>0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_EXCEED_MAX_AMOUNT);
			return statusCode;
		}
		
		String gdBusinessId = gdProperties.getProperties().getProperty("gudeng_business_id");
		if(StringUtils.isNotBlank(gdBusinessId) && !gdBusinessId.equals(businessId.toString())){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.FUNCTION_DISABLE);
			return statusCode;
		}
		
		BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(businessId + "");
		Long sellerId = businessDTO.getUserId();
		if(buyerId.compareTo(sellerId) == 0){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_BUY_YOUR_OWN_PRODUCT);
			return statusCode;
		}
		
		Long orderNo = new Long(orderNoToolServiceImpl.getOrderNo());
		
		JSONArray jsonArr = JSONUtils.parseArray(productDetials);
		//订单信息实体类
		OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
		//订单商品信息list
		List<OrderProductDetailEntity> entityList = new ArrayList<>();
		//商品购买数量list， 用于减库存
		List<Map<String, Object>> stockList = new ArrayList<>();
		//商品id list， 用来查找校验商品信息
		List<Long> pIdList = new ArrayList<>();
		String hasAct = "0";
		for(int i=0, len=jsonArr.size(); i<len; i++){
			JSONObject jsonObject = (JSONObject) jsonArr.get(i);
			Long productId = jsonObject.getLong("productId");
			Double quantity = jsonObject.getDouble("purQuantity");
			Double price = jsonObject.getDouble("price");
			hasAct = jsonObject.getString("actType");
			
			//检查商品id和名字是否为空
			if(productId == null || productId.compareTo(zero.longValue()) == 0){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_ID_IS_NULL);
				return statusCode;
			}
			
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
			pIdList.add(productId);
			
			OrderProductDetailEntity entity = new OrderProductDetailEntity();
			entity.setOrderNo(orderNo);
			entity.setProductId(productId.intValue());
			entity.setPurQuantity(quantity);
			entity.setPrice(price);
			Double productPayAmount = MathUtil.round(MathUtil.mul(price, quantity) ,2);
			newOrderAmount = MathUtil.add(newOrderAmount, productPayAmount);
			entity.setNeedToPayAmount(productPayAmount);
			entity.setTradingPrice(productPayAmount);
			entityList.add(entity);
		}
		
		//插入订单信息
		orderEntity.setOrderNo(orderNo);
		//订单来源 1农批商APP 2农商友APP 3POS机 4智能秤
		orderEntity.setOrderSource("1");
		//渠道 1android 2ios 3pc
		orderEntity.setChannel(inputDTO.getChannel());  
		orderEntity.setOrderAmount(newOrderAmount);
		orderEntity.setDiscountAmount(zero);
		orderEntity.setPayAmount(userPayAmount.compareTo(orderAmount) == 0 ? newOrderAmount : userPayAmount);
		orderEntity.setPayType(Order.PAYTYPE_BANKCARD);
		orderEntity.setOrderType(Order.TYPE_FROM_NPS);
		orderEntity.setOrderStatus(Order.STATUS_NOT_PAY);
		orderEntity.setMemberId(buyerId.intValue());
		orderEntity.setCloseUserId(buyerId.toString());
		orderEntity.setSellMemberId(sellerId.intValue());//卖家id
		orderEntity.setOrderTime(DateUtil.getNow());
		orderEntity.setShopName(businessDTO.getShopsName());
		orderEntity.setBusinessId(businessId.intValue());
		orderEntity.setMarketId(Integer.parseInt(businessDTO.getMarketId()));
		orderEntity.setCreateTime(DateUtil.getNow());
		orderEntity.setUpdateTime(DateUtil.getNow());
		orderEntity.setOutmarkStatus("0");
		orderEntity.setExamineStatus("0");
		orderEntity.setIsLock(0);
		orderEntity.setPromType("0");
		
		//用于前段跳转列表
		Double prepayAmt = orderEntity.getPayAmount();
		String returnStatus = Order.STATUS_NOT_PAY;
		Map<String, Object> totalMap = new HashMap<String, Object>();
		//如果订单商品数为1， 检验是否参加活动
		if(pIdList.size() == 1){
			Long pId = pIdList.get(0);
			Map<String, Object> actMap = new ConcurrentHashMap<>();
			actMap.put("productId", pId);
			 
			actMap = promInfoToolService.getActProductInfo(actMap);
			//有活动
			if(actMap.get("PromProdResult") != null){
				PromProdResult promResult = (PromProdResult) actMap.get("PromProdResult");
				PromProdInfoDTO actProduct = promResult.getPromProdInfoDTO();
				PromBaseinfoDTO actBaseInfo = promResult.getPromBaseInfo();
				
				//设置订单活动信息
				orderEntity.setPromType("1");
				PromOrderProminfoEntity promOrderEntity = new PromOrderProminfoEntity();
				promOrderEntity.setOrderNo(orderNo);
				promOrderEntity.setCreateUserId(buyerId.toString());
				promOrderEntity.setMarketName(businessDTO.getMarketName());
				
				CommonFeeAppDTO feeDTO = new CommonFeeAppDTO();
				//查看是否已经支付活动费用(按活动收费类型)
				feeDTO = promInfoToolService.getIsFeePaidByAct(orderEntity.getMemberId(), 
						orderEntity.getSellMemberId(), actBaseInfo.getActId(), feeDTO);
				//计算手续费用和预付款
				feeDTO.setFeeRuleList(promResult.getPromBaseInfo().getPromFees());
				feeDTO.setFeeType(1);
				feeDTO.setTradeAmount(orderEntity.getOrderAmount());
				CountFeeUtil.countFee(feeDTO);
				promOrderEntity.setBuyerPoundage(feeDTO.getBuyerFee());
				promOrderEntity.setSellerPoundage(feeDTO.getSellerFee());
				//设置预付款, 尾款， 总额
				prepayAmt = actBaseInfo.getPrepayAmt();
				promOrderEntity.setTotalAmt(MathUtil.add(orderEntity.getOrderAmount(), feeDTO.getBuyerFee()));
				promOrderEntity.setPrepayAmt(prepayAmt);
				promOrderEntity.setRestAmt(MathUtil.sub(promOrderEntity.getTotalAmt(), prepayAmt));
				if(prepayAmt.compareTo(zero) == 0){
					orderEntity.setOrderStatus(Order.STATUS_WAITING_DELIVER);
					returnStatus = Order.STATUS_WAITING_DELIVER;
				}
				//实付金额加上买家手续费
				orderEntity.setPayAmount(promOrderEntity.getTotalAmt());
				//设置订单活动商品信息
				PromOrderProdProminfoEntity promOrderProductEntity = new PromOrderProdProminfoEntity();
				promOrderProductEntity.setActId(actBaseInfo.getActId());
				promOrderProductEntity.setActPrice(actProduct.getActPrice());
				promOrderProductEntity.setOrderNo(orderNo);
				promOrderProductEntity.setProductId(pId);
				promOrderProductEntity.setCreateUserId(buyerId.toString());
				totalMap.put("promOrderEntity", promOrderEntity);
				totalMap.put("promOrderProductEntity", promOrderProductEntity);
			}else{
				//活动已经取消但是前端有活动
				if(StringUtils.isNotBlank(hasAct) && "1".equals(hasAct)){
					statusCode.setStatusCodeEnum(ErrorCodeEnum.PROM_ACT_ENDED);
					return statusCode;
				}
			}
		}
				
		//设置订单商品原价， 名字和单位信息
		addLock.lock();
		try{
			List<ProductDto> productList = productToolService.getListByIds(pIdList);
			for(int i=0, len=productList.size(); i<len; i++){
				ProductDto pDTO = productList.get(i);
				Double pPrice = pDTO.getPrice();
				Double existedStock = pDTO.getStockCount();
				String state = pDTO.getState();
				//检查商品是否上架
				if(!ProductStatusEnum.ON.getkey().equals(state)  || existedStock.compareTo(zero) == 0){
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
						if(existedStock.compareTo(zero) == 0){
							statusCode.setStatusCodeEnum(ErrorCodeEnum.PRODUCT_NO_STOCK_COUNT);
							return statusCode;
						}
						if(quantity.compareTo(existedStock) > 0){//购买量大于库存，返回失败，errorcode=-5
							logger.warn("商品库存不足, 您购买的商品:"+ pDTO.getProductName() 
									+"购买数量:"+ quantity + " 和库存数量:"+ existedStock);
							statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_PRODUCT_STOCKCOUNT_LACK);
							return statusCode;
						}else{//库存充足                                    
							map.put("status", "3");
						}
						
						entity.setPrice(pPrice);
						entity.setProductName(pDTO.getProductName());
						entity.setUnit(pDTO.getUnit());
						map.put("stockCount", MathUtil.sub(existedStock, quantity));
						stockList.add(map);
					}
				}
			}
					
			//插入订单商品明细
			totalMap.put("orderBase", orderEntity);
			totalMap.put("orderProductList", entityList);
			totalMap.put("stockList", stockList);
			
			getHessianOrderbaseService().addPromOrder(totalMap);
		}finally{
			addLock.unlock();
		}
		
		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		statusCode.setObj(orderNo + "#_#" + returnStatus + "#_#" + MoneyUtil.formatMoney(prepayAmt));
		return statusCode;
	}

	@Override
	public ErrorCodeEnum updatePurchaseOrder(OrderAppInputDTO inputDTO)
			throws Exception {
		ErrorCodeEnum result = ErrorCodeEnum.SUCCESS;
		Long orderNo = inputDTO.getOrderNo();
		Long memberId = inputDTO.getMemberId();
		if(orderNo == null){
			return ErrorCodeEnum.ORDER_ORDERNO_IS_NULL;
		}
		
		if(memberId == null || memberId.equals(0)){
			return ErrorCodeEnum.ORDER_MEMBERID_IS_NULL;
		}
		
		//操作类型 1取消订单 2确认发货 3改订单总价
		Integer opType = ParamsUtil.getIntFromString(inputDTO.getOpType());
		if(opType != 1 && opType != 2 && opType != 3){
			return ErrorCodeEnum.ORDER_ILLEAGLE_OPERATION_TYPE;
		}
		
		OrderBaseinfoDTO orderBaseDTO = null;
		
		rwlock.readLock().lock();
		try{
			orderBaseDTO = getPurchaseOrderByOrderNo(orderNo);
		}finally{
			rwlock.readLock().unlock();
		}
		
		if(orderBaseDTO == null){
			return ErrorCodeEnum.ORDER_NOT_EXISTED;
		}
		
		if(opType == 2 && memberId.intValue() != orderBaseDTO.getSellMemberId().intValue()){
			return ErrorCodeEnum.ORDER_NOT_HAS_AUTHORITY;
		}
		
		if(opType == 3 && memberId.intValue() != orderBaseDTO.getSellMemberId().intValue()){
			return ErrorCodeEnum.ORDER_NOT_HAS_AUTHORITY;
		}
		
		if(opType == 1 && memberId.intValue() != orderBaseDTO.getSellMemberId().intValue()
				&& memberId.intValue() != orderBaseDTO.getMemberId().intValue()){
			return ErrorCodeEnum.ORDER_NOT_HAS_AUTHORITY;
		}
		
		rwlock.writeLock().lock();
		try{
			switch(opType){
				case 1: 
					result = cancelPurchaseOrder(orderBaseDTO, memberId);
					break;
				case 2:
					result = deliverPurchaseOrderProduct(orderBaseDTO, memberId);
					break;
				case 3:
					result = updatePurchaseOrderPrice(orderBaseDTO, inputDTO);
					break;
				default:
					result = ErrorCodeEnum.ORDER_ILLEAGLE_OPERATION_TYPE;
			}
		}finally{
			rwlock.writeLock().unlock();
		}
		return result;
	}

	/**
	 * 修改订单价格
	 * @param orderBaseDTO
	 * @param inputDTO
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	private ErrorCodeEnum updatePurchaseOrderPrice(OrderBaseinfoDTO orderBaseDTO,
			OrderAppInputDTO inputDTO) throws MalformedURLException, Exception {
		if(!Order.STATUS_NOT_PAY.equals(orderBaseDTO.getOrderStatus())){
			return ErrorCodeEnum.ORDER_NOT_UNPAID_STATUS;
		}
		
		//活动订单不允许改价
		if(StringUtils.isNotBlank(orderBaseDTO.getPromType()) 
				&& "1".equals(orderBaseDTO.getPromType())){
			return ErrorCodeEnum.ORDER_IN_ACT;
		}
		
		Double payAmt = inputDTO.getPayAmount();
		if(payAmt == null || payAmt.compareTo(0D) == 0){
			return ErrorCodeEnum.ORDER_ILLEAGLE_CHANGED_PAYAMOUNT;
		}
		
		orderBaseDTO.setPayAmount(payAmt);
		getHessianOrderbaseService().updateByOrderNo(orderBaseDTO);
		return ErrorCodeEnum.SUCCESS;
	}

	/**
	 * 确认发货
	 * @param orderBaseDTO
	 * @param memberId
	 * @return
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	private ErrorCodeEnum deliverPurchaseOrderProduct(OrderBaseinfoDTO orderBaseDTO,
			Long memberId) throws MalformedURLException, Exception {
		if(!Order.STATUS_WAITING_DELIVER.equals(orderBaseDTO.getOrderStatus())){
			return ErrorCodeEnum.ORDER_NOT_DELIVERING_STATUS;
		}
		
		if(orderBaseDTO.getCloseTime() != null){
			return ErrorCodeEnum.ORDER_ALREADY_CANCEL;
		}
		
		orderBaseDTO.setDeliverTime(new Date());
		orderBaseDTO.setOrderStatus(Order.STATUS_WAITING_ACCEPT);
		getHessianOrderbaseService().updateByOrderNo(orderBaseDTO);
		return ErrorCodeEnum.SUCCESS;
	}

	/**
	 * 取消订单
	 * @param orderBaseDTO
	 * @param memberId
	 * @return
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	private ErrorCodeEnum cancelPurchaseOrder(OrderBaseinfoDTO orderBaseDTO,
			Long memberId) throws MalformedURLException, Exception {
		
		if(!Order.STATUS_WAITING_DELIVER.equals(orderBaseDTO.getOrderStatus())
				&& !Order.STATUS_NOT_PAY.equals(orderBaseDTO.getOrderStatus())){
			return ErrorCodeEnum.ORDER_CANNOT_CANCEL;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderBaseDTO.getOrderNo());
		map.put("closeUserId", memberId.toString());
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
		
		getHessianOrderbaseService().cancelByOrderNo(map );
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public PurchaseOrderDetailDTO getPurchaseOrderDetail(Long orderNo)
			throws Exception {
		OrderBaseinfoDTO orderBaseDTO = getPurchaseOrderByOrderNo(orderNo);
		if(orderBaseDTO == null){
			return null;
		}
		
		//查找订单商品详情
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNo(map);
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		List<PurchaseOrderProductDTO> productDetailList = new ArrayList<>();
		int productCount = 0;
		if(null != productOrderList && productOrderList.size() > 0){
			for(int j=0,pLen=productOrderList.size();j<pLen;j++){
				OrderProductDetailDTO pDto = productOrderList.get(j);
				if(orderNo.equals(pDto.getOrderNo())){
					PurchaseOrderProductDTO productDTO = setProductInfo(pDto, imageHost);
					productDetailList.add(productDTO);
					productCount++;
				}
			}
		}
		
		PurchaseOrderDetailDTO orderDetail = new PurchaseOrderDetailDTO();
		
		orderDetail.setOrderNo(orderBaseDTO.getOrderNo());
		orderDetail.setOrderAmount(MoneyUtil.formatMoney(orderBaseDTO.getOrderAmount()));
		orderDetail.setTradeAmount(MoneyUtil.formatMoney(orderBaseDTO.getPayAmount()));
		
		orderDetail.setOrderStatus(orderBaseDTO.getOrderStatus());
		
		orderDetail.setBuyerId(orderBaseDTO.getMemberId());
		orderDetail.setBuyerMobile(orderBaseDTO.getBuyerMobile());
		orderDetail.setBuyerName(orderBaseDTO.getRealName());
		
		orderDetail.setCreateTime(DateTimeUtils.formatDate(orderBaseDTO.getCreateTime(), null));
		if(orderBaseDTO.getDeliverTime() != null)
			orderDetail.setDeliverTime(DateTimeUtils.formatDate(orderBaseDTO.getDeliverTime(), null));
		
		if(orderBaseDTO.getCloseTime() != null)
			orderDetail.setCloseTime(DateTimeUtils.formatDate(orderBaseDTO.getCloseTime(), ""));
		
		orderDetail.setShopName(orderBaseDTO.getShopName());
		orderDetail.setBusinessId(orderBaseDTO.getBusinessId().longValue());
		orderDetail.setSellerMobile(orderBaseDTO.getMobile());
		orderDetail.setSellerId(orderBaseDTO.getSellMemberId());
		
		//设置商品信息
		orderDetail.setProductDetails(productDetailList);
		orderDetail.setProductNum(productCount);
		
		//设置活动信息
		if(StringUtils.isNotBlank(orderBaseDTO.getPromType()) 
				&& "1".equals(orderBaseDTO.getPromType())){
			//查询活动信息
			PromOrderProminfoDTO promInfo = promInfoToolService.getPromInfoByOrderNo(orderNo);
			//预付款
			orderDetail.setPrepayAmount(MoneyUtil.formatMoneyWithZero(promInfo.getPrepayAmt()));
			//买家手续费
			orderDetail.setBuyerFee(MoneyUtil.formatMoneyWithZero(promInfo.getBuyerPoundage()));
			//卖家家手续费
			orderDetail.setSellerFee(MoneyUtil.formatMoneyWithZero(promInfo.getSellerPoundage()));
			//仍需付款金额
			orderDetail.setNeedPayAmount(MoneyUtil.formatMoneyWithZero(promInfo.getRestAmt()));
			
			PromotionDetail promDetail = new PromotionDetail();
			promDetail.setPromId(promInfo.getActId());
			promDetail.setPromName(promInfo.getActName());
			promDetail.setPromTypeName(ActivityTypeEnum.getActTypeNameByActType(promInfo.getActType()));
			orderDetail.setActDetail(promDetail);
		}
		
		//查找订单支付详情
		PaySerialnumberDTO payDTO = getHessianPaySerialnumberService().getByOrderNo(map);
		if(payDTO != null){
            //成交时间
			orderDetail.setTradeTime(DateTimeUtils.formatDate(payDTO.getCreateTime(),""));
		}
		//设置发货超时信息
//		setDeliverStatusWords(orderDetail, orderBaseDTO);
		map.put("status", 1);
		List<DeliveryAddressDTO> list = getHessianDeliveryAddressService().getList(map);//订单收货地址
		if (list != null && list.size() > 0) {
			orderDetail.setDeliveryAddress(list.get(0));
		}
		return orderDetail;
	}

	@Override
	public OrderBaseinfoDTO getPurchaseOrderByOrderNo(Long orderNo) throws Exception {
		return getHessianOrderbaseService().getByOrderNo(orderNo);
	}

	@Override
	public int getPurchaseOrderTotal(Map<String, Object> map) throws Exception {
		return getHessianOrderbaseService().getPurchaseOrderTotal(map);
	}

	@Override
	public List<PurchaseOrderListDTO> getPurchaseOrderList(Map<String, Object> map)
			throws Exception {
		List<PurchaseOrderListDTO> orderList = null;
		List<OrderBaseinfoDTO> orderBaseList = getHessianOrderbaseService().getPurchaseOrderListByPage(map);
		if(orderBaseList!=null && orderBaseList.size() > 0){
			orderList = new ArrayList<>();
			List<Long> orderNoList = new ArrayList<>();
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}
			
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNoList(orderNoList);
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				OrderBaseinfoDTO orderBase = orderBaseList.get(i);
				Long orderNo = orderBase.getOrderNo();
				PurchaseOrderListDTO purchaseOrder = setPurOrderBaseInfo(orderBase);
				List<PurchaseOrderProductDTO> productDetailList = new ArrayList<>();
				int productCount = 0;
				if(null != productOrderList && productOrderList.size() > 0){
					for(int j=0,pLen=productOrderList.size();j<pLen;j++){
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if(orderNo.equals(pDto.getOrderNo())){
							PurchaseOrderProductDTO productDTO = setProductInfo(pDto, imageHost);
							productDetailList.add(productDTO);
							productCount++;
						}
					}
				}
				purchaseOrder.setProductNum(productCount);
				purchaseOrder.setProductDetails(productDetailList);
				//设置促销订单信息
				setPurOrderPromInfo(purchaseOrder, orderBase);
				//设置发货超时信息
//				setDeliverStatusWords(purchaseOrder, orderBase);
				orderList.add(purchaseOrder);
			}
		}
		return orderList;
	}

	@Override
	public PageQueryResultDTO<PurchaseOrderListDTO> searchPruchaseOrderList(
			Map<String, Object> map) throws Exception {
		PageQueryResultDTO<PurchaseOrderListDTO> returnPageDTO = new PageQueryResultDTO<PurchaseOrderListDTO>();
		List<PurchaseOrderListDTO> orderList = null;
		List<Long> orderNoList = null;
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		//1. 按手机号或者姓名查找
		PageQueryResultDTO<OrderBaseinfoDTO> pageDTO = getHessianOrderbaseService().searchSellerOrderList(map);
		List<OrderBaseinfoDTO> orderBaseList = pageDTO.getDataList();
		if(orderBaseList!=null && orderBaseList.size() > 0){
			orderList = new ArrayList<>();
			orderNoList = new ArrayList<>();
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				OrderBaseinfoDTO dto = orderBaseList.get(i);
				orderNoList.add(dto.getOrderNo());
			}
			
			List<OrderProductDetailDTO> productOrderList = getHessianOrderProductService().getListByOrderNoList(orderNoList);
			for(int i=0,len=orderBaseList.size(); i<len;i++){
				OrderBaseinfoDTO orderBase = orderBaseList.get(i);
				Long orderNo = orderBase.getOrderNo();
				PurchaseOrderListDTO purchaseOrder = setPurOrderBaseInfo(orderBase);
				List<PurchaseOrderProductDTO> productDetailList = new ArrayList<>();
				int productCount = 0;
				if(null != productOrderList && productOrderList.size() > 0){
					for(int j=0,pLen=productOrderList.size();j<pLen;j++){
						OrderProductDetailDTO pDto = productOrderList.get(j);
						if(orderNo.equals(pDto.getOrderNo())){
							PurchaseOrderProductDTO productDTO = setProductInfo(pDto, imageHost);
							productDetailList.add(productDTO);
							productCount++;
						}
					}
				}
				purchaseOrder.setProductNum(productCount);
				purchaseOrder.setProductDetails(productDetailList);
				setPurOrderPromInfo(purchaseOrder, orderBase);
				//设置发货超时信息
//				setDeliverStatusWords(purchaseOrder, orderBase);
				orderList.add(purchaseOrder);
			}
			
			returnPageDTO.setTotalCount(pageDTO.getTotalCount());
			returnPageDTO.setDataList(orderList);
		}
		return returnPageDTO;
	}
	
	/**
	 * 设置订单商品信息
	 * @param pDto
	 * @param imageHost
	 * @return
	 */
	private PurchaseOrderProductDTO setProductInfo(OrderProductDetailDTO pDto, String imageHost){
		PurchaseOrderProductDTO productDTO = new PurchaseOrderProductDTO();
		productDTO.setOrderNo(pDto.getOrderNo());
		productDTO.setOrgPrice(MoneyUtil.formatMoney(pDto.getPrice()));
		productDTO.setImageUrl(imageHost + pDto.getImageUrl());
		productDTO.setNeedToPayAmount(pDto.getNeedToPayAmount());
		productDTO.setFormatNeedToPayAmount(MoneyUtil.formatMoney(pDto.getNeedToPayAmount()));
		productDTO.setPromPrice(MoneyUtil.formatMoney(pDto.getActPrice()));
		productDTO.setProductId(pDto.getProductId());
		productDTO.setProductName(pDto.getProductName());
		productDTO.setPurQuantity(pDto.getPurQuantity().toString());
		productDTO.setUnitName(pDto.getUnitName());
		if(pDto.getActPrice() != null){
			productDTO.setHasAct(1);
		}
		
		return productDTO;
	}
	
	/**
	 * 设置采购订单基本信息
	 * @param orderBase
	 * @return
	 */
	private PurchaseOrderListDTO setPurOrderBaseInfo(OrderBaseinfoDTO orderBase){
		PurchaseOrderListDTO purchaseOrder = new PurchaseOrderListDTO();
		purchaseOrder.setOrderNo(orderBase.getOrderNo());
		purchaseOrder.setBuyerName(StringUtils.isBlank(orderBase.getRealName()) ? orderBase.getBuyerMobile() : orderBase.getRealName());
		purchaseOrder.setBuyerId(orderBase.getMemberId());
		purchaseOrder.setBusinessId(orderBase.getBusinessId().longValue());
		purchaseOrder.setShopName(orderBase.getShopName());
		purchaseOrder.setOrderStatus(Order.STATUS_INVALID.equals(orderBase.getOrderStatus()) ? Order.STATUS_CANCEL : orderBase.getOrderStatus());
		purchaseOrder.setCreateTime(DateUtil.getDate(orderBase.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));
		purchaseOrder.setOrderAmount(MoneyUtil.formatMoney(orderBase.getPayAmount()));
		purchaseOrder.setFormattedPrice(MoneyUtil.formatMoney(orderBase.getPayAmount()));
		return purchaseOrder;
	}
	
	/**
	 * 设置采购订单活动信息
	 * @param purchaseOrder
	 * @param orderBase
	 */
	private void setPurOrderPromInfo(PurchaseOrderListDTO purchaseOrder, OrderBaseinfoDTO orderBase){
		if(StringUtils.isNotBlank(orderBase.getPromType()) 
				&& "1".equals(orderBase.getPromType())){
			//待付款状态订单金额取预付款
			if(Order.STATUS_NOT_PAY.equals(orderBase.getOrderStatus())){
				purchaseOrder.setOrderAmount(MoneyUtil.formatMoneyWithZero(orderBase.getPrepayAmt()));
				purchaseOrder.setFormattedPrice(MoneyUtil.formatMoneyWithZero(orderBase.getPrepayAmt()));
			//待发货和收货状态取尾款
			}else if(Order.STATUS_WAITING_ACCEPT.equals(orderBase.getOrderStatus())
					|| Order.STATUS_WAITING_DELIVER.equals(orderBase.getOrderStatus())){
				purchaseOrder.setOrderAmount(MoneyUtil.formatMoneyWithZero(orderBase.getRestAmt()));
				purchaseOrder.setFormattedPrice(MoneyUtil.formatMoneyWithZero(orderBase.getRestAmt()));
			}
			purchaseOrder.setHasAct(1);
		}
	}
	
	/**
	 * 设置发货超时信息
	 * @param purchaseOrder
	 * @param orderBase
	 */
	@SuppressWarnings("unused")
	private void setDeliverStatusWords(PurchaseOrderBaseDTO purchaseOrder, OrderBaseinfoDTO orderBase){
		purchaseOrder.setStatusWords("");
		//待发货超时
		if(Order.STATUS_WAITING_DELIVER.equals(orderBase.getOrderStatus())){
			//超时则设置发货已超时X-7天
			int interval = (int) (DateUtil.getBetweenDays(orderBase.getUpdateTime(), new Date()) - 7);
			if(interval > 0){
				purchaseOrder.setStatusWords("发货已超时" + interval + "天");
			}
		//带收货超时	
		}else if(Order.STATUS_WAITING_ACCEPT.equals(orderBase.getOrderStatus())){
			//超时则收货已超时X-7天
			int interval = (int) (DateUtil.getBetweenDays(orderBase.getDeliverTime(), new Date()) - 7);
			if(interval > 0){
				purchaseOrder.setStatusWords("收货已超时" + interval + "天");
			}
		}
	}
}
