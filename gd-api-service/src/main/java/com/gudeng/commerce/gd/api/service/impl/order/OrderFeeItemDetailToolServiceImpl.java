package com.gudeng.commerce.gd.api.service.impl.order;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.output.PosOrderDetailDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant.Order;
import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.output.VClearDetailDto;
import com.gudeng.commerce.gd.api.service.order.OrderFeeItemDetailToolService;
import com.gudeng.commerce.gd.api.service.order.PayCenterApiCommonService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.enm.EOrderFeeType;
import com.gudeng.commerce.gd.order.enm.EOrderFeeUserType;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderFeeItemDetailService;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;

public class OrderFeeItemDetailToolServiceImpl implements
		OrderFeeItemDetailToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderFeeItemDetailToolServiceImpl.class);

	@Autowired
	public GdProperties gdProperties;

	private OrderFeeItemDetailService orderFeeItemDetailService;
	@Autowired
	private PayCenterApiCommonService payCenterApiCommonService;
	
	private OrderFeeItemDetailService getHessianOrderFeeItemDetailService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderFeeItem.url");
		if (orderFeeItemDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderFeeItemDetailService = (OrderFeeItemDetailService) factory.create(OrderFeeItemDetailService.class, hessianUrl);
		}
		return orderFeeItemDetailService;
	}
	
	@Override
	public List<OrderFeeItemDetailDTO> getOrderFeeItemList(Long orderNo,
			Integer feeType, String payerType) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		if(feeType != null){
			map.put("feeType", feeType);
		}
		
		if(StringUtils.isNotBlank(payerType)){
			map.put("payerType", payerType);
		}
		
		return getHessianOrderFeeItemDetailService().getList(map);
	}

	@Override
	public void setOrderCommAndSubsidyInfo(OrderDetailAppDTO orderDetailReturn,
			OrderBaseinfoDTO orderBaseDTO) throws Exception {
		boolean hasCountSubsidy = false;
		List<OrderFeeItemDetailDTO> feeItemList = getOrderFeeItemList(orderBaseDTO.getOrderNo(), null, null);
		if(feeItemList != null && feeItemList.size() > 0){
			Double buyerCommsn=0d;
			Double sellerCommsn=0d;
			for(OrderFeeItemDetailDTO feeItem : feeItemList){
				//费用类型
				EOrderFeeType feeType = EOrderFeeType.getEnumByCode(feeItem.getFeeType());
				//付方类型
				EOrderFeeUserType feePayerType = EOrderFeeUserType.getEnumByCode(feeItem.getPayerType());
				//收方类型
				EOrderFeeUserType feePayeeType = EOrderFeeUserType.getEnumByCode(feeItem.getPayeeType());
				logger.info("Fee item info: orderNo:" + orderBaseDTO.getOrderNo() 
						+", feeType: " + feeType.getDesc() + ", feePayerType: " + feePayerType.getDesc()
						+", feePayeeType: " + feePayeeType.getDesc());
				switch(feeType){
					case COMMISION: 
						switch(feePayerType){
							case NSY: 
								//买家市场佣金+平台佣金
								if(feePayeeType == EOrderFeeUserType.MARKET || feePayeeType == EOrderFeeUserType.PLATFORM ){
									orderDetailReturn.setHasBuyerCommsn("1");
									buyerCommsn+=feeItem.getAmount();
								}
								break;
							case NPS: 
								//卖家市场佣金+平台佣金
								if(feePayeeType == EOrderFeeUserType.MARKET || feePayeeType == EOrderFeeUserType.PLATFORM ){
									orderDetailReturn.setHasSellerCommsn("1");
									sellerCommsn+=feeItem.getAmount();
								}
								break;
							default: break;
						}
						break;
					case SUBSIDY: 
						//平台给卖家补贴
						if(feePayeeType == EOrderFeeUserType.NPS && feePayerType == EOrderFeeUserType.PLATFORM){
							orderDetailReturn.setHasSellSubPay("2");
							hasCountSubsidy = true;
							orderDetailReturn.setSubAmount(MoneyUtil.formatMoneyWithZero(feeItem.getAmount()));
						}
						break;
				}
				orderDetailReturn.setBuyerCommsn(MoneyUtil.formatMoneyWithZero(buyerCommsn));
				orderDetailReturn.setSellerCommsn(MoneyUtil.formatMoneyWithZero(sellerCommsn));
			}
		}
		
		//如果已支付且有支付规则及没补贴
		if(Order.STATUS_PAID.equals(orderDetailReturn.getOrderStatus()) 
				&& !hasCountSubsidy && "1".equals(orderDetailReturn.getHasSellSubPay())){
			VClearDetailDto feeDetail = payCenterApiCommonService.getFeeInfoByOrderNo(orderBaseDTO);
			if(feeDetail != null){
				orderDetailReturn.setHasSellSubPay("2");
				orderDetailReturn.setSubAmount(MoneyUtil.formatMoneyWithZero(feeDetail.getSubsidyAmt()));
				
				//插入补贴信息
				OrderFeeItemDetailEntity entity = new OrderFeeItemDetailEntity();
				entity.setOrderNo(orderDetailReturn.getOrderNo());
				entity.setFeeType("2");
				entity.setPayerType(EOrderFeeUserType.PLATFORM.getCode());
				entity.setPayeeType(EOrderFeeUserType.NPS.getCode());
				entity.setAmount(feeDetail.getSubsidyAmt());
				entity.setCreateTime(DateUtil.getNow());
				getHessianOrderFeeItemDetailService().insert(entity);
			}
		}
	}

	@Override
	public void setOrderCommAndSubsidyInfo(PosOrderDetailDTO orderDetailReturn,
										   OrderBaseinfoDTO orderBaseDTO) throws Exception {
		boolean hasCountSubsidy = false;
		List<OrderFeeItemDetailDTO> feeItemList = getOrderFeeItemList(orderBaseDTO.getOrderNo(), null, null);
		if(feeItemList != null && feeItemList.size() > 0){
			Double buyerCommsn=0d;
			Double sellerCommsn=0d;
			for(OrderFeeItemDetailDTO feeItem : feeItemList){
				//费用类型
				EOrderFeeType feeType = EOrderFeeType.getEnumByCode(feeItem.getFeeType());
				//付方类型
				EOrderFeeUserType feePayerType = EOrderFeeUserType.getEnumByCode(feeItem.getPayerType());
				//收方类型
				EOrderFeeUserType feePayeeType = EOrderFeeUserType.getEnumByCode(feeItem.getPayeeType());
				logger.info("Fee item info: orderNo:" + orderBaseDTO.getOrderNo()
						+", feeType: " + feeType.getDesc() + ", feePayerType: " + feePayerType.getDesc()
						+", feePayeeType: " + feePayeeType.getDesc());
				switch(feeType){
					case COMMISION:
						switch(feePayerType){
							case NSY:
								//买家市场佣金+平台佣金
								if(feePayeeType == EOrderFeeUserType.MARKET || feePayeeType == EOrderFeeUserType.PLATFORM ){
									orderDetailReturn.setHasBuyerCommsn("1");
									buyerCommsn+=feeItem.getAmount();
								}
								break;
							case NPS:
								//卖家市场佣金+平台佣金
								if(feePayeeType == EOrderFeeUserType.MARKET || feePayeeType == EOrderFeeUserType.PLATFORM ){
									orderDetailReturn.setHasSellerCommsn("1");
									sellerCommsn+=feeItem.getAmount();
								}
								break;
							default: break;
						}
						break;
					case SUBSIDY:
						//平台给卖家补贴
						if(feePayeeType == EOrderFeeUserType.NPS && feePayerType == EOrderFeeUserType.PLATFORM){
							orderDetailReturn.setHasSellSubPay("2");
							hasCountSubsidy = true;
							orderDetailReturn.setSubAmount(MoneyUtil.formatMoneyWithZero(feeItem.getAmount()));
						}
						break;
				}
				orderDetailReturn.setBuyerCommsn(MoneyUtil.formatMoneyWithZero(buyerCommsn));
				orderDetailReturn.setSellerCommsn(MoneyUtil.formatMoneyWithZero(sellerCommsn));
			}
		}

		//如果已支付且有支付规则及没补贴
		if(Order.STATUS_PAID.equals(orderDetailReturn.getOrderStatus())
				&& !hasCountSubsidy && "1".equals(orderDetailReturn.getHasSellSubPay())){
			VClearDetailDto feeDetail = payCenterApiCommonService.getFeeInfoByOrderNo(orderBaseDTO);
			if(feeDetail != null){
				orderDetailReturn.setHasSellSubPay("2");
				orderDetailReturn.setSubAmount(MoneyUtil.formatMoneyWithZero(feeDetail.getSubsidyAmt()));

				//插入补贴信息
				OrderFeeItemDetailEntity entity = new OrderFeeItemDetailEntity();
				entity.setOrderNo(orderDetailReturn.getOrderNo());
				entity.setFeeType("2");
				entity.setPayerType(EOrderFeeUserType.PLATFORM.getCode());
				entity.setPayeeType(EOrderFeeUserType.NPS.getCode());
				entity.setAmount(feeDetail.getSubsidyAmt());
				entity.setCreateTime(DateUtil.getNow());
				getHessianOrderFeeItemDetailService().insert(entity);
			}
		}
	}

	@Override
	public List<OrderFeeItemDetailDTO> getOrderFeeDetail(
			GdOrderActivityResultDTO orderActResult, Long orderNo)
			throws Exception {
		List<OrderFeeItemDetailDTO> list = new ArrayList<>();;
		//存在买家市场佣金
		if(orderActResult.getBuyerActInfo() != null
			&& orderActResult.getBuyerActInfo().isHasBuyerCommsn()){
			
			OrderFeeItemDetailDTO feeDTO = new OrderFeeItemDetailDTO();
			feeDTO.setOrderNo(orderNo);
			feeDTO.setFeeType("1");
			feeDTO.setPayerType(EOrderFeeUserType.NSY.getCode());
			feeDTO.setPayeeType(EOrderFeeUserType.MARKET.getCode());
			feeDTO.setAmount(orderActResult.getBuyerActInfo().getMarketCommision());
			feeDTO.setDescription("买家市场佣金"); 
			list.add(feeDTO);
			OrderFeeItemDetailDTO platCommisionDTO = new OrderFeeItemDetailDTO();
			platCommisionDTO.setOrderNo(orderNo);
			platCommisionDTO.setFeeType("1");
			platCommisionDTO.setPayerType(EOrderFeeUserType.NSY.getCode());
			platCommisionDTO.setPayeeType(EOrderFeeUserType.PLATFORM.getCode());
			platCommisionDTO.setAmount(orderActResult.getBuyerActInfo().getPlatCommision());
			platCommisionDTO.setDescription("平台佣金");
			list.add(platCommisionDTO);
		}
		//是否有预付款
		if (null!=orderActResult.getBuyerActInfo()&&orderActResult.getBuyerActInfo().isHasPrepayAmt()) {
			OrderFeeItemDetailDTO prepayDTO = new OrderFeeItemDetailDTO();
			prepayDTO.setOrderNo(orderNo);
			prepayDTO.setFeeType("3");
			prepayDTO.setPayerType(EOrderFeeUserType.NSY.getCode());
			prepayDTO.setPayeeType(EOrderFeeUserType.PLATFORM.getCode());
			prepayDTO.setAmount(orderActResult.getBuyerActInfo().getPrepayAmt());
			prepayDTO.setDescription("预付款");
			list.add(prepayDTO);
		}	
		//存在卖家市场佣金
		if(orderActResult.getSellerActInfo() != null 
			&& orderActResult.getSellerActInfo().isHasSellerCommsn()){
			/*if(list == null){
				list = new ArrayList<>();
			}*/
			OrderFeeItemDetailDTO feeDTO = new OrderFeeItemDetailDTO();
			feeDTO.setOrderNo(orderNo);
			feeDTO.setFeeType("1");
			feeDTO.setPayerType(EOrderFeeUserType.NPS.getCode());
			feeDTO.setPayeeType(EOrderFeeUserType.MARKET.getCode());
			feeDTO.setAmount(orderActResult.getSellerActInfo().getMarketCommision());
			feeDTO.setDescription("农批商市场佣金");
			list.add(feeDTO);
			OrderFeeItemDetailDTO sellerPlatDTO = new OrderFeeItemDetailDTO();
			sellerPlatDTO.setOrderNo(orderNo);
			sellerPlatDTO.setFeeType("1");
			sellerPlatDTO.setPayerType(EOrderFeeUserType.NPS.getCode());
			sellerPlatDTO.setPayeeType(EOrderFeeUserType.PLATFORM.getCode());
			sellerPlatDTO.setAmount(orderActResult.getSellerActInfo().getPlatCommision());
			feeDTO.setDescription("农批商平台佣金");
			list.add(sellerPlatDTO);
		}
		return list;
	}

	@Override
	public int batchUpdate(List<OrderFeeItemDetailDTO> orderActFeeList,
			Long orderNo) throws Exception {
		return getHessianOrderFeeItemDetailService().batchUpdate(orderActFeeList, orderNo);
	}

	@Override
	public OrderFeeItemDetailEntity getOrderFeeEntityByType(Long orderNo, Double fee, int type)
			throws Exception {
		OrderFeeItemDetailEntity entity = null;
		switch(type){
			case 1: 
				entity = new OrderFeeItemDetailEntity();
				entity.setOrderNo(orderNo);
				entity.setFeeType("1");
				entity.setPayerType(EOrderFeeUserType.NSY.getCode());
				entity.setPayeeType(EOrderFeeUserType.MARKET.getCode());
				entity.setCreateTime(DateUtil.getNow());
				entity.setAmount(fee);
				break;
			default: 
				break;
		}
		return entity;
	}

	@Override
	public List<OrderFeeItemDetailDTO> getOrderFeeByParam(Map<String, Object> param) throws Exception {
		return getHessianOrderFeeItemDetailService().getList(param);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianOrderFeeItemDetailService().getTotal(map);
	}
	@Override
	public int deleteByParam(Map<String,Object> map) throws Exception {
		return getHessianOrderFeeItemDetailService().deleteByParam(map);
	}
	
	@Override
	public int batchInsert(List<OrderFeeItemDetailEntity> feelList) throws Exception {
		return getHessianOrderFeeItemDetailService().batchInsert(feelList);
	}
}
