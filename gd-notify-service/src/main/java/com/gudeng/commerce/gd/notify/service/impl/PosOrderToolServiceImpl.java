package com.gudeng.commerce.gd.notify.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PosBankCardDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;
import com.gudeng.commerce.gd.customer.service.ReBusinessPosService;
import com.gudeng.commerce.gd.notify.dto.PosPayNotifyDto;
import com.gudeng.commerce.gd.notify.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.notify.service.PayResultToolService;
import com.gudeng.commerce.gd.notify.service.PosBankCardToolService;
import com.gudeng.commerce.gd.notify.service.PosOrderToolService;
import com.gudeng.commerce.gd.notify.util.DateUtil;
import com.gudeng.commerce.gd.notify.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.enm.EOrderFeeUserType;
import com.gudeng.commerce.gd.order.enm.EOrderSource;
import com.gudeng.commerce.gd.order.enm.EPaySubType4Stament;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderNoService;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.GdOrderActivityBaseService;

public class PosOrderToolServiceImpl implements PosOrderToolService{
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PosOrderToolServiceImpl.class);
	
	@Autowired
	public GdProperties gdProperties;
	
	@Autowired
	private PosBankCardToolService posBankCardToolService;
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Resource
	private PayResultToolService payResultToolService;
	

	private OrderBaseinfoService orderBaseinfoService;
	
	private OrderNoService orderNoService;
	
	private ReBusinessPosService reBusinessPosService;
	
	private BusinessBaseinfoService businessBaseinfoService;
	
	private GdOrderActivityBaseService gdOrderActivityBaseService;


	private OrderBaseinfoService getHessianOrderbaseService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderBaseinfo.url");
		if (orderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBaseinfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, hessianUrl);
		}
		return orderBaseinfoService;
	}


	protected OrderNoService getOrderNoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.orderNoService.url");
		if(orderNoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderNoService = (OrderNoService) factory.create(OrderNoService.class, url);
		}
		return orderNoService;
	}
	

	private ReBusinessPosService getHessianReBusinessPosService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.reBusinessPosService.url");
		if (reBusinessPosService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessPosService = (ReBusinessPosService) factory.create(ReBusinessPosService.class, hessianUrl);
		}
		return reBusinessPosService;
	}
	
	protected BusinessBaseinfoService getHessianBussinessService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.businessBaseinfo.url");
		if (businessBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			businessBaseinfoService = (BusinessBaseinfoService) factory.create(BusinessBaseinfoService.class, url);
		}
		return businessBaseinfoService;
	}

	private GdOrderActivityBaseService getHessianOrderActivityBaseService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderActivityBaseService.url");
		if (gdOrderActivityBaseService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			gdOrderActivityBaseService = (GdOrderActivityBaseService) factory.create(GdOrderActivityBaseService.class, hessianUrl);
		}
		return gdOrderActivityBaseService;
	}



	@Override
	public OrderBaseinfoDTO payByCard(PosPayNotifyDto dto) throws Exception {
		
		ReBusinessPosDTO reBusinessPos = getByPosDevNo(dto.getPosClientNo(), dto.getBusinessNo());
		if(reBusinessPos == null){
			logger.error("POS终端号["+dto.getPosClientNo()+"]错误");
			throw new Exception("POS终端号["+dto.getPosClientNo()+"]错误");
		}
		//根据付款卡号查询会员信息
		MemberBaseinfoDTO memberInfoAppDTO = posBankCardToolService.getByBankNo(dto.getBankCardNo());
		long memberId = 0;
		if(memberInfoAppDTO == null){
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
			memberId = memberBaseinfoToolService.addMemberBaseinfoEnt(member);
			logger.info("新增会员成功，memberId=" + memberId);
			//新增用户-pos卡号关联关系
			PosBankCardDTO posBankCardDTO = new PosBankCardDTO();
			posBankCardDTO.setBankCardNo(dto.getBankCardNo());
			posBankCardDTO.setMemberId(memberId);
			posBankCardDTO.setCreatUserId("SYS");
			posBankCardToolService.addPosBankCardDTO(posBankCardDTO);
			logger.info("新增用户-pos卡号关联成功");

		} else {
			memberId = memberInfoAppDTO.getMemberId();
		}

		Long orderNo = null;
		//查询支付流水是否存在
		PaySerialnumberEntity paySerial = payResultToolService.getByStatementId(dto.getPayCenterNumber());
		if(null == paySerial){
			logger.info("流水号不存，需要新增流水，流水号:" + dto.getPayCenterNumber());
			orderNo = new Long(getOrderNo());
			//支付流水
			PaySerialnumberEntity payRecordEntity = new PaySerialnumberEntity();
			payRecordEntity.setOrderNo(orderNo);
			//支付方式 1钱包余额 2线下刷卡 3现金
			payRecordEntity.setPayStatus("1");  
			payRecordEntity.setCreateTime(DateUtil.getNow());
			payRecordEntity.setUpdatetime(DateUtil.getNow());

			payRecordEntity.setStatementId(dto.getPayCenterNumber());
			payRecordEntity.setTradeAmount(dto.getPayAmt());
			payRecordEntity.setPayType("2");
			if(StringUtils.equals(dto.getPayChannelCode(), EPaySubType4Stament.GXRCB.getChannel())){
				payRecordEntity.setPaySubType(EPaySubType4Stament.GXRCB.getCode());
				payRecordEntity.setPayAreaId("450000");
			} else if(StringUtils.equals(dto.getPayChannelCode(), EPaySubType4Stament.NNCCB.getChannel())){
				payRecordEntity.setPaySubType(EPaySubType4Stament.NNCCB.getCode());
				payRecordEntity.setPayAreaId("450100");
			}
			payRecordEntity.setPaymentAcc(dto.getBankCardNo());
			payRecordEntity.setRecipientAcc(dto.getGdBankCardNo());
			payRecordEntity.setPayTime(dto.getTransDate());
			payRecordEntity.setThirdStatementId(dto.getTransNo());
			payRecordEntity.setPosNumber(dto.getPosClientNo());
			payResultToolService.addPaySerialnumber(payRecordEntity);
			logger.info("刷卡消费增加支付流水成功");
			
		} else {
			orderNo = paySerial.getOrderNo();
		}
		
		//查询订单是否存在
		OrderBaseinfoDTO orderBaseInfoDto = getHessianOrderbaseService().getByOrderNo(orderNo);
		if(orderBaseInfoDto == null || orderBaseInfoDto.getOrderNo() == null){
			logger.info("订单不存在，需要新增订单，订单号:" + orderNo);
			BusinessBaseinfoDTO businessDTO = getBusinessBaseinfoById(reBusinessPos.getBusinessId()+"");
			//订单信息实体类
			OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();

			//新增订单
			orderEntity.setOrderStatus("3");//已付款
			orderEntity.setOrderNo(orderNo);
			//订单来源 3 POS机
			orderEntity.setOrderSource(EOrderSource.PAY_ORDER.getCode());     
			orderEntity.setChannel("4");//渠道 1android 2ios 3pc 4pos
			orderEntity.setOrderAmount(dto.getPayAmt());
			orderEntity.setDiscountAmount(0d);
			orderEntity.setPayAmount(dto.getPayAmt());
			orderEntity.setTotalPayAmt(dto.getPayAmt());
			//需要计算拥金  调用爱兵写的接口

			orderEntity.setPayType("2");//线下刷卡
			orderEntity.setPaySubType("3");//1 旺POS 2 E农 3 南宁建行
			orderEntity.setSellMemberId(businessDTO.getUserId().intValue());//卖家id
			//订单中心POS刷卡来源的订单，创建时间需比成交时间早60~100秒（随机增加）
			long td =dto.getTransDate().getTime();
			Random rand = new Random();
			int rt = rand.nextInt(40);
			Date orderTime = new Date();
			orderTime.setTime(td-(rt+60)*1000); //交易时间
			
			orderEntity.setOrderTime(orderTime);
			orderEntity.setShopName(businessDTO.getShopsName());
			orderEntity.setBusinessId(reBusinessPos.getBusinessId().intValue());
			orderEntity.setMarketId(Integer.parseInt(businessDTO.getMarketId()));
			
			orderEntity.setCreateTime(DateUtil.getNow());
			orderEntity.setUpdateTime(DateUtil.getNow());
			orderEntity.setOutmarkStatus("0");
			orderEntity.setExamineStatus("0");

			orderEntity.setOrderType("1");//农商友采购订单
			orderEntity.setPromType("0");
			orderEntity.setMemberId((int)memberId);//买家id
			orderEntity.setCreateUserId(memberId + "");
			
			String ids = getHessianReBusinessPosService().getPosInfoByBusinessId(reBusinessPos.getBusinessId());
			orderEntity.setValidPosNum(ids);
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("orderBase", orderEntity);
			
			/*** 活动相关 START ***/
			GdOrderActivityResultDTO orderActResult = checkOrderActivity(orderEntity);
			logger.info("刷卡消费增加订单信息:orderActResult="+ JSON.toJSONString(orderActResult));
			
			//获取订单商品活动信息
			List<OrderActRelationEntity> orderActList = getOrderRelationDetail(orderActResult, orderNo, orderEntity.getBusinessId());
			logger.info("刷卡消费增加订单信息:orderActList="+ JSON.toJSONString(orderActList));
		
			//获取订单费用信息
			List<OrderFeeItemDetailDTO> orderActFeeList = getOrderFeeDetail(orderActResult, orderNo);
			logger.info("刷卡消费增加订单信息:orderActFeeList="+ JSON.toJSONString(orderActFeeList));
			
			if(orderActList != null && orderActList.size() > 0){
				totalMap.put("orderActList", orderActList);
			}
			if(orderActFeeList != null && orderActFeeList.size() > 0){
				totalMap.put("orderActFeeList", orderActFeeList);
			}
			logger.info("刷卡消费增加订单信息:totalMap="+ JSON.toJSONString(totalMap));
			getHessianOrderbaseService().addOrder(totalMap);
			
			logger.info("刷卡消费增加订单成功");
		}
		//返回订单信息给支付中心
		OrderBaseinfoDTO res = getHessianOrderbaseService().getByOrderNo(orderNo);
		return res;
	}
	
	private GdOrderActivityResultDTO checkOrderActivity(OrderBaseinfoEntity orderEntity)
			throws Exception {
		GdOrderActivityQueryDTO queryDTO = new GdOrderActivityQueryDTO();
		queryDTO.setBusinessId(orderEntity.getBusinessId());
		queryDTO.setOrderAmount(orderEntity.getOrderAmount());
		queryDTO.setPayAmount(orderEntity.getPayAmount());
		queryDTO.setSellerId(orderEntity.getSellMemberId());
		queryDTO.setBuyerId(orderEntity.getMemberId());
		queryDTO.setMarketId(orderEntity.getMarketId());
		List<GdProductActInfoDTO> productList = new ArrayList<>();
		queryDTO.setProductList(productList);
		queryDTO.setHasProduct(false);
		queryDTO.setOrdered(false);
		return getHessianOrderActivityBaseService().queryOrderActivty(queryDTO);
	}
	
	private List<OrderActRelationEntity> getOrderRelationDetail(
			GdOrderActivityResultDTO orderActResult, Long orderNo, Integer businessId)
			throws Exception {
		List<OrderActRelationEntity> list = null;
		if(orderActResult.getSellerActInfo() != null){
			List<GdOrderActivityDTO> actList = orderActResult.getSellerActInfo().getProductActInfo().get(businessId);
			if(actList != null && actList.size() > 0){
				list = new ArrayList<>();
				for(GdOrderActivityDTO actInfo : actList){
					OrderActRelationEntity entity = new OrderActRelationEntity();
					entity.setOrderNo(orderNo);
					entity.setActId(actInfo.getActId());
					entity.setProductId(null);
					entity.setActType(actInfo.getActType());
					entity.setCreateTime(DateUtil.getNow());
					list.add(entity);
				}
			}
		}
		return list;
	}
	
	private List<OrderFeeItemDetailDTO> getOrderFeeDetail(
			GdOrderActivityResultDTO orderActResult, Long orderNo)
			throws Exception {
		List<OrderFeeItemDetailDTO> list = null;
		//存在卖家市场佣金
		if(orderActResult.getSellerActInfo() != null 
			&& orderActResult.getSellerActInfo().isHasSellerCommsn()){
			list = new ArrayList<>();
			OrderFeeItemDetailDTO feeDTO = new OrderFeeItemDetailDTO();
			feeDTO.setOrderNo(orderNo);
			feeDTO.setFeeType("1");
			feeDTO.setPayerType(EOrderFeeUserType.NPS.getCode());
			feeDTO.setPayeeType(EOrderFeeUserType.MARKET.getCode());
			feeDTO.setAmount(orderActResult.getSellerActInfo().getMarketCommision());
			list.add(feeDTO);
			
			//存在卖家平台佣金
			feeDTO = new OrderFeeItemDetailDTO();
			feeDTO.setOrderNo(orderNo);
			feeDTO.setFeeType("1");
			feeDTO.setPayerType(EOrderFeeUserType.NPS.getCode());
			feeDTO.setPayeeType(EOrderFeeUserType.PLATFORM.getCode());
			feeDTO.setAmount(orderActResult.getSellerActInfo().getPlatCommision());
			list.add(feeDTO);
		}
		
		return list;
	}

	@Override
	public String getOrderNo() throws Exception {
		return getOrderNoService().getOrderNo();
	}
	
	@Override
	public ReBusinessPosDTO getByPosDevNo(String posDevNo, String businessNo)
			throws Exception {
		
		BusinessBaseinfoDTO dto = getHessianReBusinessPosService().getByPosDevNo(posDevNo, businessNo);
		if(dto != null){
			ReBusinessPosDTO reBusinessPos = new ReBusinessPosDTO();
			reBusinessPos.setBusinessId(dto.getBusinessId());
			return reBusinessPos;
		}
		return null;
	}
	
	@Override
	public BusinessBaseinfoDTO getBusinessBaseinfoById(String id) throws Exception {
		return getHessianBussinessService().getById(id);
	}


	@Override
	public String getNstOrderNo(Long orderNo) throws Exception {
		OrderBaseinfoDTO res = getHessianOrderbaseService().getByOrderNo(orderNo);
		if(res!=null){
			return res.getNstOrderNo();
		}
		return null;
	}

}
