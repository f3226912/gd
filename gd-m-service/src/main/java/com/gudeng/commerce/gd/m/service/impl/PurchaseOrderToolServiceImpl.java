package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.m.dto.CommonFeeAppDTO;
import com.gudeng.commerce.gd.m.dto.Constant.Order;
import com.gudeng.commerce.gd.m.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.m.dto.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.enums.ProductStatusEnum;
import com.gudeng.commerce.gd.m.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.m.service.OrderNoToolService;
import com.gudeng.commerce.gd.m.service.ProductToolService;
import com.gudeng.commerce.gd.m.service.PromInfoToolService;
import com.gudeng.commerce.gd.m.service.PurchaseOrderToolService;
import com.gudeng.commerce.gd.m.util.CountFeeUtil;
import com.gudeng.commerce.gd.m.util.DateUtil;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.m.util.JSONUtils;
import com.gudeng.commerce.gd.m.util.MathUtil;
import com.gudeng.commerce.gd.m.util.MoneyUtil;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
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
	
	private Lock addLock = new ReentrantLock();
	
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private OrderNoToolService orderNoToolServiceImpl;
	@Autowired
	private ProductToolService productToolService;
	@Autowired
	private PromInfoToolService promInfoToolService;

	private OrderBaseinfoService getHessianOrderbaseService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderBaseinfo.url");
		if (orderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBaseinfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, hessianUrl);
		}
		return orderBaseinfoService;
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
				if(!ProductStatusEnum.ON.getkey().equals(state)){
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

}
