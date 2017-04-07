package com.gudeng.commerce.gd.api.service.impl.sinxin;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant.Order;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.DataToolService;
import com.gudeng.commerce.gd.api.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.WalletToolService;
import com.gudeng.commerce.gd.api.service.impl.OrderNoToolServiceImpl;
import com.gudeng.commerce.gd.api.service.sinxin.OrderInfoToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderSinxinDTO;
import com.gudeng.commerce.gd.order.enm.EOrderSource;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.enm.EPayStatus;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import com.gudeng.commerce.gd.report.dto.TimeCacheType;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

/**
 * 功能描述：
 */
@Service
public class OrderInfoToolServiceImpl implements OrderInfoToolService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoToolServiceImpl.class);

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static OrderBaseinfoService orderBaseinfoService;
	
	@Autowired
	private WalletToolService accInfoToolService;
	
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	private OrderNoToolServiceImpl orderNoToolServiceImpl;
	
	@Autowired
	private PaySerialnumberToolService paySerialnumberToolService;
	
	@Autowired
	private ProductToolService productToolService;
	
	private static PaySerialnumberService paySerialnumberService;
	
	@Autowired
	private DataToolService dataToolService;

	protected OrderBaseinfoService getHessianOrderBaseinfoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.orderBaseinfoService.url");
		if (orderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBaseinfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, url);
		}
		return orderBaseinfoService;
	}
	
	private PaySerialnumberService gethessianPaySerialnumberService() throws MalformedURLException {
		String hessianUrl = gdProperties.getPaySerialnumberUrl();
		if (paySerialnumberService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			paySerialnumberService = (PaySerialnumberService) factory.create(PaySerialnumberService.class, hessianUrl);
		}
		return paySerialnumberService;
	}
	
	@Override
	public Long syncOrder(OrderSinxinDTO orderBaseDTO) throws Exception {
		if (orderBaseDTO == null) {
			throw new Exception("订单信息不能为空");
		}
		Integer memberId = orderBaseDTO.getMemberId();
		String memberIdStr = memberId == null ? "" : memberId.toString();
		Double orderAmount = orderBaseDTO.getOrderAmount();
		Double userPayAmount = orderBaseDTO.getPayAmount();
		
//		if (orderBaseDTO.getMemberId() == null) {
//			throw new Exception("用户ID不能为空");
//		}
		if (StringUtils.isBlank(orderBaseDTO.getMacAddr())) {
			throw new Exception("秤mac不能为空");
		}
		if(orderAmount == null || orderAmount <= 0){
			throw new Exception("订单金额不能为空或0");
		}
		if (userPayAmount == null | userPayAmount <= 0) {
			throw new Exception("用户支付金额不能为空或0");
		}
		if (EPayStatus.PAID.getCode().equals(orderBaseDTO.getPayStatus())) {
			if (StringUtils.isBlank(orderBaseDTO.getPayTime())) {
				throw new Exception("支付时间不能为空");
			}
		}
		if(CollectionUtils.isEmpty(orderBaseDTO.getjProductDetails())){
			throw new Exception("订单产品详细信息不能为空");
		}
		if (memberId != null) {
			AccInfoDTO accInfo = accInfoToolService.getWalletIndex(Long.parseLong(memberId.toString()));
			if(accInfo == null){
				throw new Exception("用户账号信息不存在");
			}
		}
		
		BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getByMacAddr(orderBaseDTO.getMacAddr());
		if(businessDTO == null){
			throw new Exception("秤mac不存在");
		}
		Long sellerId = businessDTO.getUserId();
		if(memberId != null && memberId == sellerId.intValue()){
			throw new Exception("您不能购买自己的商品");
		}
		Long orderNo = new Long(orderNoToolServiceImpl.getOrderNo());
		//订单信息实体类
		OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
		//订单产品信息list
		List<OrderProductDetailEntity> entityList = orderBaseDTO.getjProductDetails();
		List<Long> pIdList = new ArrayList<Long>();
		for(OrderProductDetailEntity productDetail : entityList){
			pIdList.add(Long.valueOf(productDetail.getProductId().toString()));
		}
		List<ProductDto> productList = productToolService.getListByIds(pIdList);
//		if (productList.size() != orderBaseDTO.getjProductDetails().size()) {
//			throw new Exception("产品ID不存在");
//		}
		Map<String, ProductDto> productMap = new HashMap<String, ProductDto>();
		if (CollectionUtils.isNotEmpty(productList)) {
			for (ProductDto product : productList) {
				productMap.put(product.getProductId().toString(), product);
			}
		}
		Double productPaySum = 0D;
		for(OrderProductDetailEntity productDetail : entityList){
			if(productDetail.getProductId() == null || StringUtils.isBlank(productDetail.getProductName())){
				throw new Exception("产品ID或产品名称不正确");
			}
			if(productDetail.getPurQuantity() == null || productDetail.getPurQuantity() <= 0){
				throw new Exception("采购数量或重量不正确");
			}
			if(productDetail.getPrice() == null || productDetail.getPrice() <= 0){
				throw new Exception("产品单价不正确");
			}
			if(productDetail.getNeedToPayAmount() == null || productDetail.getNeedToPayAmount() <= 0){
				throw new Exception("小计不正确");
			}
			//检查产品支付金额和单价*数量是否匹配
//			double goodsAmtSum = MathUtil.mulBig(new BigDecimal(Double.toString(productDetail.getPurQuantity())), new BigDecimal(Double.toString(productDetail.getPrice())), 2, BigDecimal.ROUND_DOWN).doubleValue();
//			if(goodsAmtSum != productDetail.getNeedToPayAmount()){
//				throw new Exception("产品小计和单价*数量不匹配");
//			}
			ProductDto product = productMap.get(productDetail.getProductId().toString());
			if (product != null) {
				double price = productDetail.getPrice();
				if ("1".equals(product.getUnit())) { // 吨
					price = MathUtil.mul(price, 1000);
				} else if ("4".equals(product.getUnit())) { // 克
					price = MathUtil.div(price, 1000, 5);
				}
				productDetail.setPrice(price);
				productDetail.setUnit(product.getUnit());
			}
			productDetail.setOrderNo(orderNo);
			//产品总额
			productPaySum = MathUtil.add(productPaySum, productDetail.getNeedToPayAmount());
		}
		
		//订单金额
		if(orderAmount.compareTo(productPaySum) != 0){
			throw new Exception("订单金额与订单内产品总额不匹配");
		}
		
		//插入订单信息
		orderEntity.setOrderNo(orderNo);
		orderEntity.setOrderSource(EOrderSource.SINXIN.getCode());                //订单来源 1卖家代客下单 2买家下单
		orderEntity.setOrderType(Order.TYPE_FROM_NSY);
		orderEntity.setPromType("0");
		orderEntity.setChannel("5");  //渠道 1android 2ios 3pc 5智能秤
		orderEntity.setOrderAmount(orderAmount);
		orderEntity.setDiscountAmount(0D);
		orderEntity.setPayAmount(userPayAmount);
		if (memberId != null) {
			orderEntity.setMemberId(memberId.intValue());//买家id
		}
		orderEntity.setSellMemberId(sellerId.intValue());//卖家id
		orderEntity.setOrderTime(DateUtil.getNow());
		orderEntity.setShopName(businessDTO.getShopsName());
		orderEntity.setBusinessId(businessDTO.getBusinessId().intValue());
		orderEntity.setMarketId(1);
		orderEntity.setCreateUserId(memberIdStr);
		orderEntity.setCreateTime(DateUtil.getNow());
		orderEntity.setUpdateTime(DateUtil.getNow());
		orderEntity.setOutmarkStatus("0");
		orderEntity.setExamineStatus("0");
		
		//支付信息
		PaySerialnumberEntity payment = null;
		if (EPayStatus.PAID.getCode().equals(orderBaseDTO.getPayStatus())) {
			payment = new PaySerialnumberEntity();
			payment.setOrderNo(orderNo);
			payment.setPayTime(DateUtil.getDate(orderBaseDTO.getPayTime(), DateUtil.DATE_FORMAT_DATETIME));
			payment.setPayType(EPayType.CASH.getCode());
			payment.setPayStatus(EPayStatus.PAID.getCode());
			payment.setTradeAmount(orderBaseDTO.getPayAmount());
			payment.setCreateuserid(memberIdStr);
			payment.setCreateTime(DateUtil.getNow());
			payment.setUpdatetime(DateUtil.getNow());
			
			orderEntity.setPayType(EPayType.CASH.getCode());
			orderEntity.setOrderStatus(EOrderStatus.PAYED.getCode());
		} else {
			orderEntity.setOrderStatus(EOrderStatus.WAIT_PAY.getCode());
		}
		
		//插入订单商品明细
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderBase", orderEntity);
		totalMap.put("orderProductList", entityList);
		getHessianOrderBaseinfoService().addSinXinOrder(totalMap);
		
		if (payment != null) {
			paySerialnumberToolService.insertEntity(payment);
			
			try {
				if (sellerId != null) {
					dataToolService.cleanGoodsCacheSpecial(sellerId, TimeCacheType.HOUR_CACHE);
				}
			} catch (Exception e) {
				logger.error("清空缓存失败", e);
			}
		}
		
		try {
			if (sellerId != null) {
				dataToolService.cleanTradeCacheSpecial(sellerId, TimeCacheType.HOUR_CACHE);
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}
		
		return orderNo;
	}
	
	@Override
	public List<OrderSinxinDTO> queryOrder(OrderSinxinDTO queryDTO) throws Exception {
		return getHessianOrderBaseinfoService().queryOrderForSinxin(queryDTO);
	}

	@Override
	public Long updateOrder(OrderSinxinDTO orderBaseDTO) throws Exception {
		OrderBaseinfoDTO orderBase = new OrderBaseinfoDTO();
		orderBase.setOrderNo(orderBaseDTO.getOrderNo());
		orderBase.setPayAmount(orderBaseDTO.getPayAmount());
		long num = (long) getHessianOrderBaseinfoService().updateByOrderNo(orderBase);
		try {
			OrderBaseinfoDTO order = getHessianOrderBaseinfoService().getByOrderNo(orderBaseDTO.getOrderNo());
			if (order != null && order.getSellMemberId() != null) {
				dataToolService.cleanOldTradeCacheSpecial(order.getSellMemberId().longValue(), order.getOrderTime());
			}
		} catch (Exception e) {
			logger.error("清空缓存失败", e);
		}
		return num;
	}
	
}
