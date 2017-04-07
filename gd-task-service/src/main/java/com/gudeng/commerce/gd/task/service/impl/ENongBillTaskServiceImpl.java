package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PosBankCardDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.customer.service.PosBankCardService;
import com.gudeng.commerce.gd.customer.service.ReBusinessPosService;
import com.gudeng.commerce.gd.order.dto.EnPostLogDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.enm.EOrderSource;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.enm.EPaySubType;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderBillService;
import com.gudeng.commerce.gd.order.service.OrderNoService;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
import com.gudeng.commerce.gd.task.enm.ENongTransTypeEnum;
import com.gudeng.commerce.gd.task.enm.PaySerialnumberEnum;
import com.gudeng.commerce.gd.task.service.ENongBillTaskService;
import com.gudeng.commerce.gd.task.util.DateUtil;
import com.gudeng.commerce.gd.task.util.GdProperties;
import com.gudeng.commerce.gd.task.util.MathUtil;

public class ENongBillTaskServiceImpl implements ENongBillTaskService {

	private static final Logger logger = LoggerFactory.getLogger(NstComfirmOrderTaskServiceImpl.class); 
	
	private OrderBillService orderBillService;
	
	private ReBusinessPosService reBusinessPosService;
	
	private OrderBaseinfoService orderBaseinfoService;
	
	private PosBankCardService posBankCardService;
	
	private MemberBaseinfoService memberBaseinfoService;
	
	private OrderNoService orderNoService;
	
	private PaySerialnumberService paySerialnumberService;
	
	private BusinessBaseinfoService businessBaseinfoService;
	
	@Autowired
	public GdProperties gdProperties;
	
	@Override
	public void handle() {
		logger.info("===============处理e农异常账单任务开始===============");
		try{
			List<EnPostLogDTO> enPostLogList = getHessianOrderBillService().queryExceptionBill();
			logger.info("共有" + enPostLogList.size() + "条异常账单");
			if(enPostLogList.size() > 0){
				int i = 0;
				for(EnPostLogDTO enPostLogDTO : enPostLogList){
					logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",开始处理");
					if(StringUtils.isBlank(enPostLogDTO.getTransype())){
						logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",交易类型为空");
						continue;
					}
					if(StringUtils.isBlank(enPostLogDTO.getMachinenum())){
						logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",pos终端号为空");
						continue;
					}
					if(StringUtils.isBlank(enPostLogDTO.getPaycardno())){
						logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",付款卡号不能为空");
						continue;
					}
					BusinessBaseinfoDTO businessDTO = getHessianReBusinessPosService().getByPosDevNo(enPostLogDTO.getMachinenum(), enPostLogDTO.getMerchantnum());
					if(businessDTO == null){
						logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",POS终端号错误");
						continue;
					}
					boolean isSuccess = false;
					if(ENongTransTypeEnum.TRANSTYPE_ORDER.getKey().equals(enPostLogDTO.getTransype())){
						isSuccess = handleOrderType(enPostLogDTO);
					} else if(ENongTransTypeEnum.TRANSTYPE_CARD.getKey().equals(enPostLogDTO.getTransype())){
						enPostLogDTO.setBusinessId(businessDTO.getBusinessId().intValue());
						enPostLogDTO.setMarketId(Integer.valueOf(businessDTO.getMarketId()));
						enPostLogDTO.setShopName(businessDTO.getShopsName());
						enPostLogDTO.setSellerId(getHessianBussinessService().getById(String.valueOf(businessDTO.getBusinessId())).getUserId().intValue());
						isSuccess = handleCardType(enPostLogDTO);
					}
					if(isSuccess){
						//防止支付流水重复
						try{
							getPaySerialnumberService().insertPayStatementId(enPostLogDTO.getTransseqno());
						} catch(SQLIntegrityConstraintViolationException e){
							logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",支付流水已存在");
						}
						logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",处理成功");
						i++;
					}
				}
				logger.info("共有" + i + "条异常账单处理成功");
			}
		}catch(Exception e){
			logger.error("处理e农异常账单任务出现系统异常，任务退出",e);
		}
		logger.info("===============处理e农异常账单任务结束===============");
	}
	
	public boolean handleOrderType(EnPostLogDTO enPostLogDTO) throws Exception {
		if(StringUtils.isBlank(String.valueOf(enPostLogDTO.getOrderno()))){
			logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",订单编号为空");
			return false;
		}
		OrderBaseinfoDTO orderBaseDTO = getHessianOrderbaseService().getByOrderNo(enPostLogDTO.getOrderno());
		if(orderBaseDTO == null){
			logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",该订单不存在");
			return false;
		}
		//校验是否未支付状态
		if(!EOrderStatus.WAIT_PAY.getCode().equals(orderBaseDTO.getOrderStatus())){
			logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",订单不是待收款状态");
			return false;
		}
		if(MathUtil.compareTo(Double.valueOf(enPostLogDTO.getPayfee()), orderBaseDTO.getPayAmount()) != 0){
			logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",支付金额不一致");
			return false;
		}
		Integer memberId = null;
		if(orderBaseDTO.getMemberId() == null || orderBaseDTO.getMemberId() == 0){
			memberId = getMemberIdByCardNo(enPostLogDTO.getPaycardno()).intValue();
		} else {
			memberId = orderBaseDTO.getMemberId();
		}
		OrderBaseinfoDTO upOrderBase = new OrderBaseinfoDTO();
		upOrderBase.setOrderNo(orderBaseDTO.getOrderNo());
		upOrderBase.setOrderStatus(EOrderStatus.PAYED.getCode());
		upOrderBase.setPayType(EPayType.OFFLINE_CARD.getCode());
		upOrderBase.setPaySubType(EPaySubType.ENGJ.getCode());
		upOrderBase.setMemberId(memberId);
		PaySerialnumberEntity payRecordEntity = new PaySerialnumberEntity();
		payRecordEntity.setTradeAmount(Double.valueOf(enPostLogDTO.getPayfee())); 
		payRecordEntity.setOrderNo(orderBaseDTO.getOrderNo());
		payRecordEntity.setStatementId(enPostLogDTO.getTransseqno());
		payRecordEntity.setPayType(EPayType.OFFLINE_CARD.getCode());
		payRecordEntity.setPayTime(enPostLogDTO.getTranstime());
		//支付状态 0未支付 1已支付 9支付失败
		payRecordEntity.setPayStatus(PaySerialnumberEnum.PAY_STATUS_1.getKey());  
		payRecordEntity.setCreateTime(DateUtil.getNow());
		payRecordEntity.setCreateuserid(String.valueOf(orderBaseDTO.getSellMemberId()));
		payRecordEntity.setUpdatetime(DateUtil.getNow());
//		payRecordEntity.setMemberId(orderBaseDTO.getSellMemberId().intValue());
		payRecordEntity.setRemark(enPostLogDTO.getPayresultcode() + "-" + enPostLogDTO.getPayresultmsg());
		payRecordEntity.setPaymentAcc(enPostLogDTO.getPaycardno());
		payRecordEntity.setPosNumber(enPostLogDTO.getMachinenum());
		payRecordEntity.setMemberId(memberId);
		Map<String, Object> totalMap = new HashMap<>();
		totalMap.put("orderBase", upOrderBase);
		totalMap.put("paySerialNumEntity", payRecordEntity);
		getHessianOrderbaseService().confirmReceive(totalMap);
		return true;
	}
	
	public boolean handleCardType(EnPostLogDTO enPostLogDTO) throws Exception {
		if(enPostLogDTO.getBusinessId() == null){
			logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",店铺为空");
			return false;
		}
		if(enPostLogDTO.getMarketId() == null){
			logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",市场为空");
			return false;
		}
		if(StringUtils.isBlank(enPostLogDTO.getPayfee()) && 
				Double.valueOf(enPostLogDTO.getPayfee()).compareTo(new Double(0)) == 0){
			logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",订单金额为空或0");
			return false;
		}
		if(Double.valueOf(enPostLogDTO.getPayfee()).compareTo(new Double(9999999.99)) > 0){
			logger.info("交易流水=" + enPostLogDTO.getTransseqno() + ",订单金额超过9999999.99");
			return false;
		}
		Integer memberId = getMemberIdByCardNo(enPostLogDTO.getPaycardno()).intValue();
		Long orderNo = new Long(getHessianOrderNoService().getOrderNo());
		//订单信息实体类
		OrderBaseinfoEntity orderEntity = new OrderBaseinfoEntity();
		
		orderEntity.setOrderNo(orderNo);
		//订单来源 1卖家代客下单 2买家下单
		orderEntity.setOrderSource(EOrderSource.PAY_ORDER.getCode());     
		orderEntity.setChannel("4"); 
		orderEntity.setOrderAmount(Double.valueOf(enPostLogDTO.getPayfee()));
		orderEntity.setDiscountAmount(new Double(0));
		orderEntity.setPayAmount(Double.valueOf(enPostLogDTO.getPayfee()));
		orderEntity.setPayType(EPayType.OFFLINE_CARD.getCode());
		orderEntity.setPaySubType(EPaySubType.ENGJ.getCode());
		orderEntity.setOrderStatus(EOrderStatus.PAYED.getCode());
		orderEntity.setSellMemberId(enPostLogDTO.getSellerId());//卖家id
		long td = enPostLogDTO.getTranstime().getTime();
		Random rand = new Random();
		int rt = rand.nextInt(40);
		Date ort = new Date();
		ort.setTime(td-(rt+60)*1000);//比交易时间早60-100秒
		orderEntity.setOrderTime(DateUtil.getNow(ort));
		
		orderEntity.setShopName(enPostLogDTO.getShopName());
		orderEntity.setBusinessId(enPostLogDTO.getBusinessId());
		orderEntity.setMarketId(enPostLogDTO.getMarketId());
		orderEntity.setCreateTime(DateUtil.getNow());
		orderEntity.setUpdateTime(DateUtil.getNow());
		orderEntity.setOutmarkStatus("0");
		orderEntity.setExamineStatus("0");
		orderEntity.setIsLock(1);
		orderEntity.setLockTime(new Date());
		orderEntity.setOrderType("1");
		orderEntity.setPromType("0");
		//虚拟买家下单
		if(memberId != null){
			orderEntity.setMemberId(memberId);//买家id
			orderEntity.setCreateUserId(String.valueOf(memberId));
		}else{//卖家匿名下单
			orderEntity.setMemberId(null);//买家id
			orderEntity.setCreateUserId(String.valueOf(enPostLogDTO.getSellerId()));
		}	
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderBase", orderEntity);
		getHessianOrderbaseService().addOrder(totalMap);
		PaySerialnumberEntity payRecordEntity = new PaySerialnumberEntity();
		payRecordEntity.setTradeAmount(Double.valueOf(enPostLogDTO.getPayfee())); 
		payRecordEntity.setOrderNo(orderNo);
		payRecordEntity.setStatementId(enPostLogDTO.getTransseqno());
		payRecordEntity.setPayType(EPayType.OFFLINE_CARD.getCode());
		payRecordEntity.setPayTime(enPostLogDTO.getTranstime());
		//支付状态 0未支付 1已支付 9支付失败
		payRecordEntity.setPayStatus(PaySerialnumberEnum.PAY_STATUS_1.getKey());  
		payRecordEntity.setCreateTime(DateUtil.getNow());
		payRecordEntity.setCreateuserid(String.valueOf(memberId));
		payRecordEntity.setUpdatetime(DateUtil.getNow());
		payRecordEntity.setMemberId(memberId);
		payRecordEntity.setRemark(enPostLogDTO.getPayresultcode() + "-" + enPostLogDTO.getPayresultmsg());
		payRecordEntity.setPaymentAcc(enPostLogDTO.getPaycardno());
		payRecordEntity.setPosNumber(enPostLogDTO.getMachinenum());
		getPaySerialnumberService().insertEntity(payRecordEntity);
		return true;
	}
	
	private Long getMemberIdByCardNo(String cardNo) throws Exception {
		//根据付款卡号查询会员信息
		MemberBaseinfoDTO baseDTO = getHessianPosBankCardService().getByBankNo(cardNo);
		if(baseDTO == null){
			//新增会员，注册类型是7，即pos刷卡支付用户
			MemberBaseinfoEntity member = new MemberBaseinfoEntity();
			member.setAccount(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			member.setMobile(member.getAccount());
			member.setCreateTime(new Date());
			member.setLevel(Integer.parseInt(MemberBaseinfoEnum.LEVEl_3.getKey()));
			member.setStatus(MemberBaseinfoEnum.STATUS_1.getKey());
			member.setCreateUserId("SYS");
			member.setRegetype(MemberBaseinfoEnum.REGETYPE_7.getKey());
			Long mid = getHessianMemberService().addMemberBaseinfoEnt(member);
			logger.info("新增会员成功，memberId=" + mid);
			//新增用户-pos卡号关联关系
			PosBankCardDTO posBankCardDTO = new PosBankCardDTO();
			posBankCardDTO.setBankCardNo(cardNo);
			posBankCardDTO.setMemberId(mid);
			posBankCardDTO.setCreatUserId("SYS");
			getHessianPosBankCardService().addPosBankCardDTO(posBankCardDTO);
			logger.info("新增用户-pos卡号关联成功");
			return mid;
		} else {
			return baseDTO.getMemberId();
		}
	}
	
	private OrderBillService getHessianOrderBillService() throws MalformedURLException {
		if (orderBillService == null) {
			String url = gdProperties.getOrderBillUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBillService = (OrderBillService) factory.create(OrderBillService.class, url);
		}
		return orderBillService;
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
	
	private OrderBaseinfoService getHessianOrderbaseService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderBaseinfoService.url");
		if (orderBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBaseinfoService = (OrderBaseinfoService) factory.create(OrderBaseinfoService.class, hessianUrl);
		}
		return orderBaseinfoService;
	}
	
	private PosBankCardService getHessianPosBankCardService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.posBankCardService.url");
		if (posBankCardService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			posBankCardService = (PosBankCardService) factory.create(PosBankCardService.class, hessianUrl);
		}
		return posBankCardService;
	}
	
	protected MemberBaseinfoService getHessianMemberService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.member.url");
		if(memberBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberBaseinfoService) factory.create(MemberBaseinfoService.class, url);
		}
		return memberBaseinfoService;
	}
	
	protected OrderNoService getHessianOrderNoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.orderNoService.url");
		if(orderNoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderNoService = (OrderNoService) factory.create(OrderNoService.class, url);
		}
		return orderNoService;
	}
	
	protected PaySerialnumberService getPaySerialnumberService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.paySerialnumberService.url");
		if(paySerialnumberService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			paySerialnumberService = (PaySerialnumberService) factory.create(PaySerialnumberService.class, url);
		}
		return paySerialnumberService;
	}
	
	protected BusinessBaseinfoService getHessianBussinessService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.businessBaseinfo.url");
		if(businessBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			businessBaseinfoService = (BusinessBaseinfoService) factory.create(BusinessBaseinfoService.class, url);
		}
		return businessBaseinfoService;
	}
}
