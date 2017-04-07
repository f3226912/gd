package com.gudeng.commerce.gd.api.service.impl.order;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.gudeng.commerce.gd.api.dto.input.BusinessInputDTO;
import com.gudeng.commerce.gd.api.dto.input.GdOrderActivityApiQueryDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ActivityUserIntegralChangeToolService;
import com.gudeng.commerce.gd.api.service.ActivityUserintegralToolService;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.nst.NstApiCommonService;
import com.gudeng.commerce.gd.api.service.order.OrderActivityToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.MathUtil;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.api.util.RuleJson;
import com.gudeng.commerce.gd.api.util.RuleJsonDetail;
import com.gudeng.commerce.gd.customer.dto.ActivityUserIntegralChangeDTO;
import com.gudeng.commerce.gd.customer.dto.ActivityUserintegralDTO;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessAreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.order.dto.OrderActRelationDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.OrderRefundRecordDTO;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderActRelationService;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.order.service.OrderRefundRecordService;
import com.gudeng.commerce.gd.promotion.dto.ActActivityIntegralDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderPenaltyQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdPayBankCardInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.GdOrderActivityBaseService;

public class OrderActivityToolServiceImpl implements OrderActivityToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderActivityToolServiceImpl.class);

	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private BusinessBaseinfoToolService businessBaseinfoToolService;
	@Autowired
	private AreaToolService areaToolService;
	@Autowired
	private NstApiCommonService nstApiCommonService;

	private OrderActRelationService orderActRelationService;

	private OrderRefundRecordService orderRefundRecordService;

	private GdOrderActivityBaseService gdOrderActivityBaseService;

	private OrderBaseinfoService orderBaseinfoService;

	private OrderProductDetailService orderProductDetailService;

	@Autowired
	private ActivityUserintegralToolService activityUserintegralService;

	@Autowired
	private ActivityUserIntegralChangeToolService activityUserIntegralChangeToolService;

	@Autowired
	private MemberToolService memberToolService;

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

	private OrderActRelationService getHessianOrderActRelationService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderActRelation.url");
		if (orderActRelationService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderActRelationService = (OrderActRelationService) factory.create(OrderActRelationService.class, hessianUrl);
		}
		return orderActRelationService;
	} 

	private OrderRefundRecordService getHessianOrderRefundRecordService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.orderRefundRecord.url");
		if (orderRefundRecordService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderRefundRecordService = (OrderRefundRecordService) factory.create(OrderRefundRecordService.class, hessianUrl);
		}
		return orderRefundRecordService;
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
	public GdOrderActivityResultDTO queryActivity(GdOrderActivityQueryDTO queryDTO) throws Exception{
		return getHessianOrderActivityBaseService().queryOrderActivty(queryDTO);
	}

	@Override
	public GdOrderActivityResultDTO queryActivity(
			GdOrderActivityApiQueryDTO inputDTO) throws Exception {
		GdOrderActivityQueryDTO queryDTO = setQueryParams(inputDTO); 
		ErrorCodeEnum queryResultEnum = checkQueryParams(queryDTO);
		if(queryResultEnum != ErrorCodeEnum.SUCCESS){
			return null;
		}
		return queryActivity(queryDTO);
	}

	@Override
	public GdOrderActivityResultDTO checkOrderActivity(OrderBaseinfoEntity orderEntity,
			List<OrderProductDetailEntity> entityList)
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
		queryDTO.setHasProduct(true);
		queryDTO.setOrdered(false);
		for(OrderProductDetailEntity product : entityList){
			GdProductActInfoDTO dto = new GdProductActInfoDTO();
			dto.setPrice(product.getPrice());
			dto.setProductAmount(product.getNeedToPayAmount());
			dto.setProductId(product.getProductId());
			dto.setQuantity(product.getPurQuantity());
			productList.add(dto);
		}
		return queryActivity(queryDTO);
	}

	@Override
	public StatusCodeEnumWithInfo queryOrderActivity(
			GdOrderActivityApiQueryDTO inputDTO) throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo();
		GdOrderActivityQueryDTO queryDTO = setQueryParams(inputDTO); 
		ErrorCodeEnum queryResultEnum = checkQueryParams(queryDTO);
		if(queryResultEnum != ErrorCodeEnum.SUCCESS){
			statusCode.setStatusCodeEnum(queryResultEnum);
			return statusCode;
		}

		GdOrderActivityResultDTO resultDTO = queryActivity(queryDTO);
		int platform = nstApiCommonService.getCustomerPlatform(queryDTO.getBuyerId());

		checkSupportPlatformMode(resultDTO, platform, queryDTO.getPayAmount());

		if("1".equals(inputDTO.getFlag())){
			if(resultDTO.getSellerActInfo() != null){
				resultDTO.getSellerActInfo().setProductActInfo(null);
			}

			//如果有退款
			if(resultDTO.getBuyerActInfo() != null && resultDTO.getBuyerActInfo().isHasPenalty()){
				//查找是否有退款记录
				Map<String, Object> map = new HashMap<>();
				map.put("orderNo", queryDTO.getOrderNo());
				List<OrderRefundRecordDTO> refundList = getHessianOrderRefundRecordService().getList(map);
				if(refundList == null || refundList.size() == 0){
					resultDTO.getBuyerActInfo().setHasPenalty(false);
					resultDTO.getBuyerActInfo().setCompanyPenalty(0D);
					resultDTO.getBuyerActInfo().setSellerPenalty(0D);
					resultDTO.getBuyerActInfo().setPlatPenalty(0D);
				}
			}
			statusCode.setObj(resultDTO);
		}else{
			statusCode.setObj(setResultMap(resultDTO, new HashMap<String, Map<String, Object>>()));
		}
		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		return statusCode;
	}

	@Override
	public List<OrderActRelationEntity> getOrderRelationDetail(
			GdOrderActivityResultDTO orderActResult, Long orderNo)
					throws Exception {
		List<OrderActRelationEntity> list = null;
		if(orderActResult.getSellerActInfo() != null && !orderActResult.getSellerActInfo().getProductActInfo().isEmpty()){
			list = new ArrayList<>();
			for(Integer productId : orderActResult.getSellerActInfo().getProductActInfo().keySet()){
				if(orderActResult.getSellerActInfo().getProductActInfo().get(productId) == null){
					continue;
				}

				for(GdOrderActivityDTO actDTO : orderActResult.getSellerActInfo().getProductActInfo().get(productId)){
					OrderActRelationEntity entity = new OrderActRelationEntity();
					entity.setOrderNo(orderNo);
					entity.setActId(actDTO.getActId());
					entity.setProductId(productId);
					entity.setActType(actDTO.getActType());
					entity.setCreateTime(DateUtil.getNow());
					list.add(entity);
				}
			}
		}
		return list;
	}

	@Override
	public StatusCodeEnumWithInfo batchQueryOrderActivity(
			JSONArray businessDetailsJsonArr) throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo(); // 返回结果
		List<GdOrderActivityQueryDTO> queryList = new ArrayList<>();
		for(int i=0, length=businessDetailsJsonArr.size(); i<length; i++){
			JSONObject jsonObject = (JSONObject) businessDetailsJsonArr.get(i);
			GdOrderActivityApiQueryDTO queryInfo = JSONObject.toJavaObject(jsonObject, GdOrderActivityApiQueryDTO.class);
			//订单号和商品信息二者必须有一个
			if(StringUtils.isBlank(queryInfo.getOrderNo()) 
					&& StringUtils.isBlank(queryInfo.getProductListStr())){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.PARAM_IS_NULL);
				return statusCode;
			}

			GdOrderActivityQueryDTO queryDTO = setQueryParams(queryInfo); 
			ErrorCodeEnum queryResultEnum = checkQueryParams(queryDTO);
			if(queryResultEnum != ErrorCodeEnum.SUCCESS){
				statusCode.setStatusCodeEnum(queryResultEnum);
				return statusCode;
			}
			queryList.add(queryDTO);
		}

		int platform = nstApiCommonService.getCustomerPlatform(queryList.get(0).getBuyerId());
		Map<String, Map<String, Object>> resultMap = new HashMap<>();
		List<GdOrderActivityResultDTO> resultList = getHessianOrderActivityBaseService().batchQueryOrderActivty(queryList);
		for(int i=0, len=resultList.size(); i<len; i++){
			checkSupportPlatformMode(resultList.get(i), platform, queryList.get(i).getPayAmount());
			setResultMap(resultList.get(i), resultMap);
		}

		statusCode.setObj(resultMap);
		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		return statusCode;
	}

	/**
	 * 检查是否支持平台配送
	 * @param resultDTO
	 * @param platform 0不支持 1支持
	 * @param double1 
	 */
	private void checkSupportPlatformMode(GdOrderActivityResultDTO resultDTO,
			int platform, Double payAmt) {
		//农速通未设置平台配送
		Integer mode = 1; //平台配送
		if(platform == 0 && resultDTO.getBuyerActInfo() != null 
				&& resultDTO.getBuyerActInfo().getDistributeModeList().contains(mode)){
			resultDTO.getBuyerActInfo().getDistributeModeList().remove(mode);
		}else if(resultDTO.getBuyerActInfo() != null && resultDTO.getBuyerActInfo().isHasPrepayAmt()){
			//判断订单金额是否大于预付款
			Double prepayAmt = resultDTO.getBuyerActInfo().getPrepayAmt();
			payAmt = MathUtil.add(payAmt, resultDTO.getBuyerActInfo().getMarketCommision());
			payAmt = MathUtil.add(payAmt, resultDTO.getBuyerActInfo().getPlatCommision());
			if(prepayAmt.compareTo(payAmt) >= 0 
					&& resultDTO.getBuyerActInfo().getDistributeModeList().contains(mode)){
				resultDTO.getBuyerActInfo().getDistributeModeList().remove(mode);
			}
		}else if(resultDTO.getBuyerActInfo() != null && resultDTO.getBuyerActInfo().getPrepayAmt().compareTo(0D) == 0
				&& resultDTO.getBuyerActInfo().getDistributeModeList().contains(mode)){
			//预付款为0也不显示
			resultDTO.getBuyerActInfo().getDistributeModeList().remove(mode);
		}
	}

	private Map<String, Map<String, Object>> setResultMap(
			GdOrderActivityResultDTO result, Map<String, Map<String, Object>> resultMap) {
		Map<String, Object> actMap = new HashMap<>();
		actMap.put("buyerMarketCommision", "0");
		actMap.put("buyerPlatCommision", "0");
		actMap.put("buyerSubsidy", "0");
		actMap.put("prepayAmt", "0");
		actMap.put("sellerMarketCommision", "0");
		actMap.put("sellerPlatCommision", "0");
		actMap.put("sellerSubsidy", "0");
		actMap.put("hasSellerSub", "0");
		actMap.put("hasSellerCommsn", "0");
		actMap.put("hasBuyerCommsn", "0");
		actMap.put("hasPrepayAmt", "0");
		List<Integer> modeList = new ArrayList<>(Arrays.asList(0));
		actMap.put("distributeMode", modeList );
		resultMap.put(result.getBusinessId().toString(), actMap);
		if(result.getBuyerActInfo() != null){
			Double commsn = MathUtil.add(result.getBuyerActInfo().getMarketCommision(), result.getBuyerActInfo().getPlatCommision());
			actMap.put("buyerMarketCommision", commsn.toString());
			actMap.put("buyerSubsidy", result.getBuyerActInfo().getSubsidy().toString());
			actMap.put("prepayAmt", result.getBuyerActInfo().getPrepayAmt().toString());
			if(result.getBuyerActInfo().isHasBuyerCommsn()){
				actMap.put("hasBuyerCommsn", "1");
			}
			if(result.getBuyerActInfo().isHasPrepayAmt()){
				actMap.put("hasPrepayAmt", "1");
			}

			actMap.put("distributeMode", result.getBuyerActInfo().getDistributeModeList());
		}

		if(result.getSellerActInfo() != null){
			Double commsn = MathUtil.add(result.getSellerActInfo().getMarketCommision(), result.getSellerActInfo().getPlatCommision());
			actMap.put("sellerMarketCommision", commsn.toString());
			actMap.put("sellerSubsidy", result.getSellerActInfo().getSubsidy().toString());
			if(result.getSellerActInfo().getHasSellerSub()){
				actMap.put("hasSellerSub", "1");
			}
			if(result.getSellerActInfo().isHasSellerCommsn()){
				actMap.put("hasSellerCommsn", "1");
			}
		}
		return resultMap;
	}

	private GdOrderActivityQueryDTO setQueryParams(
			GdOrderActivityApiQueryDTO inputDTO) {
		GdOrderActivityQueryDTO queryDTO = new GdOrderActivityQueryDTO();
		queryDTO.setOrderNo(inputDTO.getOrderNo());
		queryDTO.setOrderAmount(ParamsUtil.getDoubleFromString(inputDTO.getOrderAmount(), null));
		queryDTO.setPayAmount(ParamsUtil.getDoubleFromString(inputDTO.getPayAmount(), null));
		queryDTO.setBusinessId(ParamsUtil.getIntFromString(inputDTO.getBusinessId(), null));
		queryDTO.setBuyerId(ParamsUtil.getIntFromString(inputDTO.getBuyerId(), null));
		queryDTO.setFlag(inputDTO.getFlag());
		//如果有商品信息则加入商品信息
		if(StringUtils.isNotBlank(inputDTO.getProductListStr())){
			//商品id_购买量_单价_购买金额#_#商品id_购买量_单价_购买金额
			String[] strArr = inputDTO.getProductListStr().split("#_#");
			List<GdProductActInfoDTO> productList = new ArrayList<>();
			queryDTO.setProductList(productList );
			for(int i=0, len=strArr.length; i<len; i++){
				String[] str = strArr[i].split("_");
				GdProductActInfoDTO productInfo = new GdProductActInfoDTO();
				productInfo.setProductId(Integer.parseInt(str[0]));
				productInfo.setQuantity(Double.parseDouble(str[1]));
				productInfo.setPrice(Double.parseDouble(str[2]));
				productInfo.setProductAmount(Double.parseDouble(str[3]));
				productList.add(productInfo);
			}
			queryDTO.setHasProduct(true);
			queryDTO.setOrdered(false);
		}

		//如果有银行卡信息则加入
		if(StringUtils.isNotBlank(inputDTO.getBusiType1())
				&& StringUtils.isNotBlank(inputDTO.getBusiType2())
				&& StringUtils.isNotBlank(inputDTO.getCardType())
				&&StringUtils.isNotBlank(inputDTO.getPayChannel())){
			GdPayBankCardInfoDTO payCardInfo = new GdPayBankCardInfoDTO();
			payCardInfo.setBusiType1(Integer.parseInt(inputDTO.getBusiType1()));
			payCardInfo.setBusiType2(Integer.parseInt(inputDTO.getBusiType2()));
			payCardInfo.setCardType(Integer.parseInt(inputDTO.getCardType()));
			payCardInfo.setPayChannel(inputDTO.getPayChannel());
			payCardInfo.setBuyerSubsidyAmt(ParamsUtil.getDoubleFromString(inputDTO.getBuyerSubsidyAmt()));
			payCardInfo.setSellerSubsidyAmt(ParamsUtil.getDoubleFromString(inputDTO.getSellerSubsidyAmt()));
			payCardInfo.setTradingFee(ParamsUtil.getDoubleFromString(inputDTO.getTradingFee()));
			queryDTO.setPayCardInfo(payCardInfo);
		}
		return queryDTO;
	}

	/**
	 * 检查查询对象的必要参数
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private ErrorCodeEnum checkQueryParams(GdOrderActivityQueryDTO queryDTO) throws Exception {
		//存在订单号则查询订单
		if(StringUtils.isNotBlank(queryDTO.getOrderNo())){
			Long orderNo = Long.parseLong(queryDTO.getOrderNo().trim());
			OrderBaseinfoDTO orderInfo = getHessianOrderbaseService().getByOrderNo(orderNo);

			if(orderInfo == null){
				logger.warn("订单信息不存在, orderNo:" + orderNo);
				return ErrorCodeEnum.ORDER_NOT_EXISTED;
			}
			queryDTO.setOrderAmount(orderInfo.getOrderAmount());
			queryDTO.setPayAmount(orderInfo.getPayAmount());
			queryDTO.setMarketId(orderInfo.getMarketId());
			queryDTO.setBusinessId(orderInfo.getBusinessId());
			queryDTO.setSellerId(orderInfo.getSellMemberId());
			//pos刷卡生成的订单不计算买家佣金
			if(queryDTO.getBuyerId() == null){
				queryDTO.setBuyerId(orderInfo.getMemberId());
			}

			//查找订单商品信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderNo", orderNo);
			List<OrderProductDetailDTO> productList = getHessianOrderProductService().getListByOrderNo(map);
			//查找订单活动信息
			List<OrderActRelationDTO> productRefActIdList = getByOrderNo(orderNo);

			if(productList != null && productList.size() > 0){
				if(productRefActIdList != null && productRefActIdList.size() > 0){
					List<GdProductActInfoDTO> pIdList = new ArrayList<>();
					queryDTO.setProductList(pIdList);
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

				//pos来源订单默认没商品 
				if(!"3".equals(orderInfo.getOrderSource())){
					queryDTO.setHasProduct(true);
				}
			}else{
				if(productRefActIdList != null && productRefActIdList.size() > 0){
					for(OrderActRelationDTO actRef : productRefActIdList){
						//反向订单活动没有商品id
						if(actRef.getProductId() == null){
							List<GdOrderActivityDTO> actIdList = queryDTO.getActIdList();
							if(actIdList == null){
								actIdList = new ArrayList<>();
							}
							GdOrderActivityDTO actDto = new GdOrderActivityDTO();
							actDto.setActId(actRef.getActId());
							actDto.setActType(actRef.getActType());
							actIdList.add(actDto);
							queryDTO.setActIdList(actIdList);
						}
					}
				}
			}
		}

		if (queryDTO.getBusinessId() == null) {
			return ErrorCodeEnum.BUSINESS_ID_IS_NULL;
		}


		if(queryDTO.getOrderAmount() == null || queryDTO.getOrderAmount().compareTo(0D) <=0){
			return ErrorCodeEnum.ORDER_AMOUNT_IS_NULL;
		}

		if(queryDTO.getPayAmount() == null || queryDTO.getPayAmount().compareTo(0D) < 0 ){
			return ErrorCodeEnum.ORDER_PAYAMT_IS_NULL;
		}

		if (queryDTO.getPayAmount().compareTo(Order.MAX_ORDER_PRICE) > 0 
				|| queryDTO.getOrderAmount().compareTo(Order.MAX_ORDER_PRICE) > 0) {
			return ErrorCodeEnum.ORDER_EXCEED_MAX_AMOUNT;
		}

		BusinessBaseinfoDTO businessDTO = businessBaseinfoToolService.getById(queryDTO.getBusinessId() + "");
		if(businessDTO == null){
			logger.warn("商铺信息不存在");
			return ErrorCodeEnum.BUSINESS_IS_EXISTED;
		}

		queryDTO.setMarketId(Integer.parseInt(businessDTO.getMarketId()));
		queryDTO.setSellerId(businessDTO.getUserId().intValue());
		return ErrorCodeEnum.SUCCESS;
	}

	@Override
	public List<OrderActRelationDTO> getByOrderNo(Long orderNo)
			throws Exception {
		return getHessianOrderActRelationService().getByOrderNo(orderNo);
	}

	@Override
	public StatusCodeEnumWithInfo querySameCity(BusinessInputDTO inputDTO)
			throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo(); // 返回结果
		Integer receiptCityId = ParamsUtil.getIntFromString(inputDTO.getReceiptCityId(), null);
		if(receiptCityId == null){
			String receiptCityName = inputDTO.getReceiptCityName().trim();
			AreaDTO areaDTO = areaToolService.getByAreaName(receiptCityName);
			if(areaDTO == null){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.AREA_NAME_NOT_FOUND);
				return statusCode;
			}

			receiptCityId = Integer.parseInt(areaDTO.getAreaID());
		}

		String[] businessIdArr = inputDTO.getBusinessIdListStr().split("#_#");
		List<String> businessIdList = new ArrayList<>(Arrays.asList(businessIdArr));
		List<BusinessAreaDTO> areaList = areaToolService.getByBusinessIdList(businessIdList);
		if(areaList != null && areaList.size() > 0){
			Map<String, Object> map = new HashMap<>();
			for(BusinessAreaDTO businessArea : areaList){
				if(businessArea.getBusinessCityId() != null 
						&& businessArea.getBusinessCityId().intValue() == receiptCityId.intValue()){
					map.put(businessArea.getBusinessId().toString(), "1");
				}

				if(businessArea.getMarketCityId() != null 
						&& businessArea.getMarketCityId().intValue() == receiptCityId.intValue()){
					map.put(businessArea.getBusinessId().toString(), "1");
				}
			}

			for(String businessId : businessIdList){
				if(!map.containsKey(businessId)){
					map.put(businessId, "0");
				}
			}

			statusCode.setObj(map);
		}
		statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
		return statusCode;
	}

	@Override
	public GdOrderPenaltyQueryDTO queryOrderPenalty(GdOrderPenaltyQueryDTO queryDTO) throws Exception {
		return getHessianOrderActivityBaseService().queryOrderPenalty(queryDTO);
	}
	/**
	 * map参数：memberId marketId orderNo  这三个参数不可缺少
	 * return 活动积分
	 */
	@Override
	public Integer queryActivityIntegralRate(Map<String, Object> paraMap) throws Exception {

		ActActivityIntegralDTO actActivityIntegralDTO= getHessianOrderActivityBaseService().queryActActivityIntegral(paraMap);
		Integer actActivityIntegral=0;  //最终积分
		Map orderMap=new HashMap<>();
		orderMap.put("orderNo", paraMap.get("orderNo"));
		List<OrderBaseinfoDTO> orderInfo=getHessianOrderbaseService().getOrderByCondition(orderMap);
		if (null==orderInfo||orderInfo.size()<1) {
			logger.error("订单号:"+paraMap.get("orderNo")+"未查到订单数据");
			return actActivityIntegral;
		}
		double payAmount=orderInfo.get(0).getPayAmount(); //获取订单的实收金额
		Integer rangeRate=0;  //区间积分
		//如果费率是0.0 说明此用户未参加当前市场的活动，则不进行计算
		double integralRate=0.0;
		if (null!=actActivityIntegralDTO&&actActivityIntegralDTO.getIntegralRate()!=0.0) {
			integralRate=actActivityIntegralDTO.getIntegralRate(); //获取倍率
			String ruleJson=actActivityIntegralDTO.getRuleJson(); //规则json
			RuleJson ruleJsonStr=JSONObject.parseObject(ruleJson, RuleJson.class);
			Integer minIntegral=ruleJsonStr.getMinIntegral();//首单积分
			Integer dayMaxIntegral=ruleJsonStr.getDayMaxIntegral();//当日最大积分 如果是-1表示是上不封顶 
			Integer dayHaveGetIntegral=activityUserIntegralChangeToolService.getTotalIntegralByMemberId(paraMap);//当日已获积分
			dayHaveGetIntegral=dayHaveGetIntegral==null?0:dayHaveGetIntegral;
			String detail=ruleJsonStr.getDetail(); //获取detail信息
			JSONArray detailArr = JSONUtils.parseArray(detail);//订单商品详情信息
			for (int i = 0; i < detailArr.size(); i++) {
				JSONObject jsonObject = (JSONObject) detailArr.get(i);
				RuleJsonDetail ruleJsonDetail = JSONObject.toJavaObject(jsonObject,RuleJsonDetail.class);
				double startAmt=ruleJsonDetail.getStartAmt(); //开始区间
				double endAmt=ruleJsonDetail.getEndAmt(); //结束区间

				if ((endAmt==-1&&payAmount>=startAmt)||(endAmt!=-1&&payAmount>=startAmt&&payAmount<endAmt)) {  
					rangeRate=ruleJsonDetail.getIntegral(); //获取此区间积分
					break;
				}
			}
			//是否首单  查询规则：根据memberId和marketId，活动类型是2 现场采销 订单状态是3  已付款  查询是否有订单，如果有则表明，此用户已经参加过首单奖励，否则则是首单
			paraMap.put("orderNo1",paraMap.get("orderNo"));  //订单号必须有，加个不等的判断
			paraMap.remove("orderNo");   
			paraMap.put("activityType", "2"); //查询活动类型是 2：现场采销
		 	paraMap.put("orderStatus1", "3"); //订单状态是已付款的  或已完成
			List<OrderBaseinfoDTO> orderList=getHessianOrderbaseService().getOrderByCondition(paraMap); 
			boolean isFirest=true; //是否是首单
			if ((null!=orderList&&orderList.size()>0)) {
				isFirest=false;
			}
			Integer surplusIntegral=dayMaxIntegral-dayHaveGetIntegral; //剩余积分
			if (surplusIntegral<=0) {
				return -2;  //当日积分已用完 
			}
			if (rangeRate>0) { //如果大于0 说明满足相关的区间
				if(isFirest) { //是否首单
					if (rangeRate>minIntegral) { //如果区间积分>首单积分，则以首单积分为主，否则以区间积分为主
						actActivityIntegral=rangeRate;  
					}else{
						actActivityIntegral=minIntegral;
					}
				}else{ //不是首单
					actActivityIntegral=rangeRate;  //默认是区间积分就是当前订单积分
					if (dayMaxIntegral!=-1) { //是否设置了每日积分上线，如果设置了就要考虑是否达到了积分上线，反之 就不考虑 上线问题 
						if (surplusIntegral<rangeRate) {
							actActivityIntegral=surplusIntegral;  //如果剩余积分小于区间积分，则返回 剩余积分
						}
					}
				}

			}else{  //如果不满足相关的区间，是否是首单，是首单则提供首单积分，不是首单则没有积分
				if(isFirest) {
					actActivityIntegral=minIntegral;
				}
			}

		}
		String result= Math.round(actActivityIntegral==null?0:actActivityIntegral*integralRate)+"";
		return Integer.parseInt(result);
	}

	@Override
	public void IntegralRateForPayFinish(Long orderNo) throws Exception {
		if (null==orderNo) {
			return;
		}
		OrderBaseinfoDTO orderBaseinfoDTO=getHessianOrderbaseService().getByOrderNo(orderNo);
		//订单的活动类型是 2 现场采销 并且是已付款状态的订单 才可以同步积分数据到订单积分表，积分表和积分变更表中
		
		if (null!=orderBaseinfoDTO&&
				null!=orderBaseinfoDTO.getActivityType()&&orderBaseinfoDTO.getActivityType().equals("2")
				&&null!=orderBaseinfoDTO.getOrderStatus()&&orderBaseinfoDTO.getOrderStatus().equals("3")) {
			String memberId=orderBaseinfoDTO.getMemberId()+""; //买家id
			Integer marketId=orderBaseinfoDTO.getMarketId(); //市场id
			Map<String, Object> pareMap=new HashMap<>();
			pareMap.put("orderNo", orderNo);
			pareMap.put("memberId", memberId);
			//pareMap.put("marketId", marketId);

			Integer activityIntegral=queryActivityIntegralRate(pareMap); //获取可获得的积分
			ActActivityIntegralDTO actActivityIntegralDTO= getHessianOrderActivityBaseService().queryActActivityIntegral(pareMap);//获取活动信息
			//1.将当前订单可获得的积分更新到订单明细表中
			if (activityIntegral<0) {
				activityIntegral=0;
			}
			orderBaseinfoDTO.setActivityIntegral(activityIntegral);
			int updateResult=getHessianOrderbaseService().updateByOrderNo(orderBaseinfoDTO);
			if (updateResult<1) {
				logger.warn("失败的修改：将订单编号："+orderNo+"的活动获取积分为:"+activityIntegral);
				return;

			}
			ActivityUserIntegralChangeDTO changeDto=new ActivityUserIntegralChangeDTO();
			if (null!=actActivityIntegralDTO&&actActivityIntegralDTO.getActivityId()>0) {

				changeDto.setActivityId(Integer.valueOf(actActivityIntegralDTO.getActivityId()+"")); //设置活动Id
			}
			MemberCertifiDTO mberCertifiDTO =memberToolService.getByUserId(Long.valueOf(memberId));
			changeDto.setMemberId(Integer.valueOf(memberId)); //买家id
			changeDto.setMobile(mberCertifiDTO.getMobile()); //买家手机号
			changeDto.setIntegral(activityIntegral);  //所获积分
			changeDto.setIntegralType("1"); //增加积分
			changeDto.setCreateTime(new Date()); //创建时间
			Integer changeResult=activityUserIntegralChangeToolService.insert(changeDto); 
			if (changeResult<1) {
				logger.warn("失败的修改：将订单编号："+orderNo+"积分修改到积分变更表失败");
				return;
			}
			pareMap.put("activityType","2"); //是现场采销的数据 
			pareMap.put("status","1"); //是可用状态的
			List<ActivityUserintegralDTO> activityUserintegrals=activityUserintegralService.getList(pareMap); //根据memberId获取积分表信息
			ActivityUserintegralDTO activityUserintegralDTO=new ActivityUserintegralDTO(); 
			if (null!=activityUserintegrals&&activityUserintegrals.size()>0) {
				activityUserintegralDTO=activityUserintegrals.get(0); 
				if (null!=activityUserintegralDTO) {
					Integer totalIntegral= activityUserintegralDTO.getTotalIntegral();//获取已经累积的积分
					Integer doIntegral= activityUserintegralDTO.getDoIntegral();//获取可用的积分
					totalIntegral=totalIntegral==null?0:totalIntegral;
					doIntegral=doIntegral==null?0:doIntegral;
					activityUserintegralDTO.setTotalIntegral(totalIntegral+=activityIntegral);//累计积分
					activityUserintegralDTO.setDoIntegral(doIntegral+=activityIntegral); //可用积分
					activityUserintegralService.update(activityUserintegralDTO); //修改积分表
				}
			}else{
				activityUserintegralDTO.setMemberId(Integer.parseInt(memberId));
				activityUserintegralDTO.setActivityType("2"); //2 现场采销
				activityUserintegralDTO.setTotalIntegral(activityIntegral);
				activityUserintegralDTO.setDoIntegral(activityIntegral);
				//activityUserintegralDTO.setCreateTime(new Date());
				activityUserintegralDTO.setStatus("1");
				activityUserintegralService.insert(activityUserintegralDTO);
			}


		}
	}
	
	@Override
	public void saveOrderActRelations(List<OrderActRelationEntity> entitys) throws Exception {
         getHessianOrderActRelationService().batchInsertEntity(entitys);		
	}

	@Override
	public List<OrderProductDetailDTO> findOrderProductDetailByOrderNo(String orderNo) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderNo", orderNo);
		return getHessianOrderProductService().getListByOrderNo(param);
	}

}
