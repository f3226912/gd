package com.gudeng.commerce.gd.promotion.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.bo.RedisConstant.RedisKey;
import com.gudeng.commerce.gd.promotion.dto.ActActivityIntegralDTO;
import com.gudeng.commerce.gd.promotion.dto.ActProductDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDistributionModeDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityUserRuleDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityInfoRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderPenaltyQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.entity.GdActActivityUserRuleEntity;
import com.gudeng.commerce.gd.promotion.enums.GdActivityRuleUserTypeEnums;
import com.gudeng.commerce.gd.promotion.service.ActActivityCommService;
import com.gudeng.commerce.gd.promotion.service.GdActActivityDistributionModeService;
import com.gudeng.commerce.gd.promotion.service.GdActActivityService;
import com.gudeng.commerce.gd.promotion.service.GdActActivityUserRuleService;
import com.gudeng.commerce.gd.promotion.service.GdOrderActivityBaseService;
import com.gudeng.commerce.gd.promotion.service.GdOrderActivityFeeProxyService;
import com.gudeng.commerce.gd.promotion.util.MathUtil;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

public class GdOrderActivityBaseServiceImpl implements
		GdOrderActivityBaseService {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(GdOrderActivityBaseServiceImpl.class);
	
	@Autowired
	private GdOrderActivityFeeProxyService gdOrderActivityFeeProxyService;
	@Autowired
	private GdActActivityService gdActActivityService;
	@Autowired
	private GdActActivityUserRuleService gdActActivityUserRuleService;
	@Autowired
	private ActActivityCommService actActivityCommService;
	@Autowired
	private IGDBinaryRedisClient redisClient;
	@Autowired
	private GdActActivityDistributionModeService gdActActivityDistributionModeService;
	@Autowired
	private DeliveryRuleHandler deliveryRuleHandler;
	@Autowired
	private PrepayAndPenaltyRuleHandler prepayAndPenaltyRuleHandler;
	@Override
	public GdOrderActivityResultDTO queryOrderActivty(
			GdOrderActivityQueryDTO queryDTO) throws Exception {
		GdOrderActivityResultDTO resultDTO = new GdOrderActivityResultDTO();
		resultDTO.setBusinessId(queryDTO.getBusinessId());
		resultDTO.setBuyerId(queryDTO.getBuyerId());
		resultDTO.setSellerId(queryDTO.getSellerId());
		resultDTO.setMarketId(queryDTO.getMarketId());
		
		logger.info("-----------------------------------------------------");
		logger.info("Activity market id: " + queryDTO.getMarketId());
		logger.info("orderNo: " + queryDTO.getOrderNo() + ", orderAmt: " + queryDTO.getOrderAmount()
				+ ", payAmt: " + queryDTO.getPayAmount());
		
		//获取活动信息
		GdActivityRedisDTO activityRedisDTO = getActivityInfo();
		
		//设置改价后商品总金额
		if(queryDTO.isHasProduct() 
				&& queryDTO.getPayAmount().compareTo(queryDTO.getOrderAmount()) != 0){
			setProductAmount(queryDTO);
		}
		
		//如果有买家
		if(queryDTO.getBuyerId() != null){
			logger.info("Buyer id " + queryDTO.getBuyerId());
			
			if(queryDTO.getProductList() != null && queryDTO.getProductList().size() > 0){
				GdOrderActivityDetailDTO buyerActInfo = new GdOrderActivityDetailDTO();
				resultDTO.setBuyerActInfo(buyerActInfo);
				boolean hasBuyerCommsn = buyerActInfo.isHasBuyerCommsn();
				boolean hasPrepayAmt = buyerActInfo.isHasPrepayAmt();
				boolean hasPenalty = buyerActInfo.isHasPenalty();
				for(GdProductActInfoDTO productInfo : queryDTO.getProductList()){
					//设置商品买家佣金信息
					gdOrderActivityFeeProxyService.setBuyerActInfo(activityRedisDTO, productInfo, queryDTO);
					GdOrderActivityDetailDTO productActInfo = productInfo.getActInfo();
					buyerActInfo.setMarketCommision(MathUtil.add(buyerActInfo.getMarketCommision(), productActInfo.getMarketCommision()));
					buyerActInfo.setPlatCommision(MathUtil.add(buyerActInfo.getPlatCommision(), productActInfo.getPlatCommision()));
					
					buyerActInfo.setPrepayAmt(MathUtil.add(buyerActInfo.getPrepayAmt(), productActInfo.getPrepayAmt()));
					
					buyerActInfo.setSellerPenalty(MathUtil.add(buyerActInfo.getSellerPenalty(), productActInfo.getSellerPenalty()));
					buyerActInfo.setCompanyPenalty(MathUtil.add(buyerActInfo.getCompanyPenalty(), productActInfo.getCompanyPenalty()));
					buyerActInfo.setPlatPenalty(MathUtil.add(buyerActInfo.getPlatPenalty(), productActInfo.getPlatPenalty()));
					
					buyerActInfo.setDistributeModeList(combineMode(buyerActInfo.getDistributeModeList(), productActInfo.getDistributeModeList()));
					
					buyerActInfo.setSubsidy(buyerActInfo.getSubsidy().compareTo(productActInfo.getSubsidy()) > 0 ? buyerActInfo.getSubsidy() : productActInfo.getSubsidy());
					
					buyerActInfo.setHasBuyerCommsn(hasBuyerCommsn |= productActInfo.isHasBuyerCommsn());
					buyerActInfo.setHasPenalty(hasPenalty |= productActInfo.isHasPenalty());
					buyerActInfo.setHasPrepayAmt(hasPrepayAmt |= productActInfo.isHasPrepayAmt());
					
//					buyerActInfo.getProductActInfo().put(productInfo.getProductId(), productActInfo.getProductActInfo().get(productInfo.getProductId()));
					logger.info("Buyer product market commission: " + productActInfo.getMarketCommision()
							+ ", platform commission: " + productActInfo.getPlatCommision() 
							+ ", subsidy: " + buyerActInfo.getSubsidy());
					logger.info("~~~~~~~~~~~~~~~~");
				}
				
				logger.info("Buyer final act info: " + buyerActInfo.toString());
			}
		}
		
		logger.info("=====================================================");
		//如果有商品
		if(queryDTO.isHasProduct() && queryDTO.getProductList() != null){
			GdOrderActivityDetailDTO sellerActInfo = new GdOrderActivityDetailDTO();
			resultDTO.setSellerActInfo(sellerActInfo);
			
			Double subsidyAmt = 0D;
			boolean hasSellerSub = false;
			boolean hasSellerCommsn = false;
			for(GdProductActInfoDTO productInfo : queryDTO.getProductList()){
				//设置商品佣金信息
				gdOrderActivityFeeProxyService.setSellerActInfo(activityRedisDTO, productInfo, queryDTO);
				GdOrderActivityDetailDTO productActInfo = productInfo.getActInfo();
				sellerActInfo.setHasSellerSub(hasSellerSub |= productActInfo.getHasSellerSub());
				sellerActInfo.setHasSellerCommsn(hasSellerCommsn |= productActInfo.isHasSellerCommsn());
				subsidyAmt = subsidyAmt.compareTo(productActInfo.getSubsidy()) > 0 ? subsidyAmt : productActInfo.getSubsidy();
				sellerActInfo.setSubsidy(subsidyAmt);
				sellerActInfo.setMarketCommision(MathUtil.add(sellerActInfo.getMarketCommision(), productActInfo.getMarketCommision()));
				sellerActInfo.setPlatCommision(MathUtil.add(sellerActInfo.getPlatCommision(), productActInfo.getPlatCommision()));
				
				sellerActInfo.setDistributeModeList(combineMode(sellerActInfo.getDistributeModeList(), productActInfo.getDistributeModeList()));
				sellerActInfo.getProductActInfo().put(productInfo.getProductId(), productActInfo.getProductActInfo().get(productInfo.getProductId()));
				logger.info("Seller product market commission: " + productActInfo.getMarketCommision()
						+ ", platform commission: " + productActInfo.getPlatCommision() 
						+ ", subsidy: " + subsidyAmt);
				logger.info("~~~~~~~~~~~~~~~~");
			}
			
			logger.info("Seller final act info: " + sellerActInfo.toString());
		}else{//如果没商品的逆向刷卡生成订单的流程
			logger.info("Business id:  " + queryDTO.getBusinessId());
			queryDTO.setBuyerId(null);
			gdOrderActivityFeeProxyService.setBusinessActInfo(activityRedisDTO, queryDTO, resultDTO);
			
			logger.info("Business final act info: " + resultDTO.getSellerActInfo().toString());
		}
		
		logger.info("-----------------------------------------------------");
		return resultDTO;
	}
	
	private List<Integer> combineMode(List<Integer> returnList, List<Integer> list) {
		if(returnList.size() == 0){
			returnList = new ArrayList<>(Arrays.asList(0, 1, 2));
		}
		returnList.retainAll(list);
		
		if(returnList.isEmpty()){
			returnList.add(0);
		}
		return returnList;
	}

	private void setProductAmount(GdOrderActivityQueryDTO queryDTO) {
		if(queryDTO.getProductList() != null && queryDTO.getProductList().size() > 0){
			Double payAmt = queryDTO.getPayAmount();
			Double orderAmt = queryDTO.getOrderAmount();
			logger.info("Order amount: "+ orderAmt + ", pay amount: "+ payAmt);
			for(GdProductActInfoDTO productInfo : queryDTO.getProductList()){
				productInfo.setProductAmount(MathUtil.div(MathUtil.mul(productInfo.getProductAmount(), payAmt), orderAmt));
			}
		}
	}

	/**
	 * 获取活动信息
	 * @return
	 * @throws Exception 
	 */
	private GdActivityRedisDTO getActivityInfo() throws Exception {
//		GdActivityRedisDTO dto = redisClient.get(RedisKey.GD_ACTIVITY.value);
//		if(dto != null){
//			return dto;
//		}
		logger.info("Start to get activity info...");
		Long time1 = System.currentTimeMillis();
		GdActivityRedisDTO dto = new GdActivityRedisDTO();
		
		dto.setSubsidyInfo(getRedisDetailByType(1));
		dto.setMarketCommsnInfo(getRedisDetailByType(2));
		dto.setPlatformCommsnInfo(getRedisDetailByType(3));
		dto.setPrepayAmtAndPenaltyInfo(getRedisDetailByType(4));
		dto.setDeliveryInfo(getRedisDetailByType(5));
		
//		redisClient.set(RedisKey.GD_ACTIVITY.value, dto);
		logger.info("End to get activity info, used time: " + (System.currentTimeMillis() - time1));
		return dto;
	}
	

	private GdActivityInfoRedisDTO getRedisDetailByType(int actType) throws Exception {
		GdActivityInfoRedisDTO dto = new GdActivityInfoRedisDTO();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("type", actType);
		List<GdActActivityDTO> actInfoList = gdActActivityService.getActivityInfo(paramsMap);
		if(actInfoList != null && actInfoList.size() > 0){
			/** 不限买家对应的活动map marketId -> actId */
			Map<Integer, List<Integer>> noLimitBuyerActIdMap = new HashMap<>();
			/** 不限卖家对应的活动map marketId -> actId */
			Map<Integer, Integer> noLimitSellerActIdMap = new HashMap<>();
			/** 活动信息对应map actId -> act info dto */
			Map<Integer, GdActActivityDTO> actInfoMap = new HashMap<>();
			
			dto.setNoLimitBuyerActIdMap(noLimitBuyerActIdMap);
			dto.setNoLimitSellerActIdMap(noLimitSellerActIdMap);
			dto.setActInfoMap(actInfoMap);
			
			List<Integer> actIdList = new ArrayList<>();
			for(GdActActivityDTO actInfo : actInfoList){
				Integer marketId = Integer.parseInt(actInfo.getMarketId());
				//市场活动信息
				if(actInfo.getIsMarketAct() == 1 && actInfo.getIsNew() == 1 && actInfo.getState() == 1 
						&& isActStart(actInfo) && !isActEnd(actInfo)){
					noLimitSellerActIdMap.put(marketId, actInfo.getId());
				}
				
				//不限制买家活动信息
				if(actInfo.getIsLimitBuyerAct() == 0){
					if(noLimitBuyerActIdMap.containsKey(marketId)){
						noLimitBuyerActIdMap.get(marketId).add(actInfo.getId());
					}else{
						List<Integer> list = new ArrayList<>();
						list.add(actInfo.getId());
						noLimitBuyerActIdMap.put(marketId, list);
					}
				}
				if(actInfo.getIsNew() == 1 && actInfo.getState() == 1
						&& isActStart(actInfo) && !isActEnd(actInfo)){
					actIdList.add(actInfo.getId());
				}
				actInfoMap.put(actInfo.getId(), actInfo);
			}
			
			if(actIdList.size() > 0){
				paramsMap.put("actIdList", actIdList);
				List<GdActActivityUserRuleDTO> userRuleList = gdActActivityUserRuleService.getList(paramsMap);
				if(userRuleList != null && userRuleList.size() > 0){
					/** 商铺活动信息对应map businessID -> actId */
					Map<Integer, Integer> businessActMap = new HashMap<>();
					dto.setBusinessActMap(businessActMap);
					/** 商品活动信息对应map productId -> actId */
					Map<Integer, Integer> productActMap = new HashMap<>();
					dto.setProductActMap(productActMap);
					
					List<GdActActivityUserRuleDTO> productRuleList = new ArrayList<>();
					Map<Integer, Integer> productCate1IdMap = new HashMap<>(); //商品一级分类
					Map<Integer, Integer> productCate2IdMap = new HashMap<>(); //商品二级分类
					Map<Integer, Integer> productCate3IdMap = new HashMap<>(); //商品三级分类
					
					//将活动对象分类
					for(GdActActivityUserRuleDTO userRule : userRuleList){
						switch(GdActivityRuleUserTypeEnums.getEnumsBykey(userRule.getUserType())){
						case ACT_SHOP: 
							businessActMap.put(userRule.getReferId().intValue(), userRule.getActivityId());
							break;
						case ACT_PRODUCT_CATEGORY:
							switch(userRule.getRemark()){
							case "0": //一级分类
								productCate1IdMap.put(userRule.getReferId().intValue(), userRule.getActivityId());
								break;
							case "1": //二级分类
								productCate2IdMap.put(userRule.getReferId().intValue(), userRule.getActivityId());
								break;
							case "2": //三级分类
								productCate3IdMap.put(userRule.getReferId().intValue(), userRule.getActivityId());
								break;
							}
							break;
						case ACT_PRODUCT: 
							productRuleList.add(userRule);
							productActMap.put(userRule.getReferId().intValue(), userRule.getActivityId());
							break;
						default:
							break;
						}
					}
					
					//设置商品活动信息
					setProductActInfo(productActMap, businessActMap, productCate1IdMap, productCate2IdMap, 
							productCate3IdMap,  getProductIdsInAct(productCate1IdMap, productCate2IdMap, 
									productCate3IdMap, businessActMap));
				}
			}
			
			//查询特定买家的活动id
			/** 买家活动信息对应map buyerId -> actId */
			Map<Integer, List<Integer>> buyerActMap = new HashMap<>();
			dto.setBuyerActMap(buyerActMap);
			/** 活动信息对应买家map actId -> buyerId */
			Map<Integer, List<Integer>> actBuyerMap = new HashMap<>();
			dto.setActBuyerMap(actBuyerMap);
			paramsMap.clear();
			paramsMap.put("type", actType);
			paramsMap.put("userType", "1");
			List<GdActActivityUserRuleDTO> userRuleList = gdActActivityUserRuleService.getList(paramsMap);
			if(userRuleList != null && userRuleList.size() > 0){
				for(GdActActivityUserRuleDTO userRule : userRuleList){
					if(buyerActMap.containsKey(userRule.getReferId().intValue())){
						buyerActMap.get(userRule.getReferId().intValue()).add(userRule.getActivityId());
					}else{
						List<Integer> list = new ArrayList<>();
						list.add(userRule.getActivityId());
						buyerActMap.put(userRule.getReferId().intValue(), list);
					}
					
					if(actBuyerMap.containsKey(userRule.getActivityId().intValue())){
						actBuyerMap.get(userRule.getActivityId().intValue()).add(userRule.getReferId().intValue());
					}else{
						List<Integer> list = new ArrayList<>();
						list.add(userRule.getReferId().intValue());
						actBuyerMap.put(userRule.getActivityId().intValue(), list);
					}
				}
			}
		}
		
		/** 活动对应信息map */
		Map<Integer, Map<String, Object>> actRuleMap = new HashMap<>();
		dto.setActRuleMap(actRuleMap);
		//设置规则信息
		setActRule(actRuleMap, paramsMap, actType);
		return dto;
	}
	
	private boolean isActStart(GdActActivityDTO actInfo) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date dt1 = df.parse(actInfo.getStartTime());
        Date dt2 = new Date();
        logger.info("Act id: " + actInfo.getId() + ", start time: " + actInfo.getStartTime() 
        		+ ", result:" + (dt1.compareTo(dt2) < 0));
        
        return dt1.compareTo(dt2) < 0;
	}
	
	private boolean isActEnd(GdActActivityDTO actInfo) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date dt1 = df.parse(actInfo.getEndTime());
            Date dt2 = new Date();
            logger.info("Act id: " + actInfo.getId() + ", end time: " + actInfo.getEndTime() 
            		+ ", result:" + (dt1.compareTo(dt2) > 0));
        return dt1.compareTo(dt2) < 0;
	}

	private void setActRule(
			Map<Integer, Map<String, Object>> actRuleMap, Map<String, Object> paramsMap, int actType) throws Exception {
		paramsMap.clear();
		logger.info("Act type: " + actType);
		//物流规则查询h支持的配送方式信息
		if(actType == 5){
			paramsMap.put("actType", actType);
			List<GdActActivityDistributionModeDTO> modeList = gdActActivityDistributionModeService.getList(paramsMap);
			if(modeList != null && modeList.size() > 0){
				for(GdActActivityDistributionModeDTO mode : modeList){
					if(!actRuleMap.containsKey(Integer.parseInt(mode.getActivityId()))){
						Map<String, Object> map = new HashMap<>();
						actRuleMap.put(Integer.parseInt(mode.getActivityId()), map);
					}
					
					Map<String, Object> actMap = actRuleMap.get(Integer.parseInt(mode.getActivityId()));
					if(actMap.containsKey("deliveryRule")){
						@SuppressWarnings("unchecked")
						List<GdActActivityDistributionModeDTO> list = (List<GdActActivityDistributionModeDTO>)actMap.get("deliveryRule");
						list.add(mode);
					}else{
						List<GdActActivityDistributionModeDTO> list = new ArrayList<>();
						list.add(mode);
						actMap.put("deliveryRule", list);
					}
				}
			}
			return;
		}
		
		paramsMap.put("type", actType);
		List<GdActActivityCommDTO> comList = actActivityCommService.getList(paramsMap);
		if(comList != null && comList.size() > 0){
			for(GdActActivityCommDTO com : comList){
				if(!actRuleMap.containsKey(com.getActId())){
					Map<String, Object> map = new HashMap<>();
					actRuleMap.put(com.getActId(), map);
				}
				
				Map<String, Object> actMap = actRuleMap.get(com.getActId());
				
				//佣金规则类型 1平台买家佣金 2平台卖家佣金 3市场买家佣金 4市场卖家佣金 5刷卡补贴,
				//6买家预付款，7买家给平台违约金，8买家给卖家违约金，9买家给物流公司违约金
				switch(com.getCommRuleType()){
					case "1": 
						actMap.put("buyerPlatformCommsnRule", com);
						break;
					case "2": 
						actMap.put("sellerPlatformCommsnRule", com);
						break;
					case "3": 
						actMap.put("buyerMarketCommsnRule", com);
						break;
					case "4": 
						actMap.put("sellerMarketCommsnRule", com);
						break;
					case "5": 
						if(actMap.containsKey("sellerSubsidyRule")){
							@SuppressWarnings("unchecked")
							List<GdActActivityCommDTO> subSidyList = (List<GdActActivityCommDTO>)actMap.get("sellerSubsidyRule");
							subSidyList.add(com);
						}else{
							List<GdActActivityCommDTO> subSidyList = new ArrayList<>();
							subSidyList.add(com);
							actMap.put("sellerSubsidyRule", subSidyList);
						}
					break;
					case "6": 
						actMap.put("prepayAmtRule", com);
						break;
					case "7": 
					case "8": 
					case "9": 
						if(actMap.containsKey("penaltyRule")){
							@SuppressWarnings("unchecked")
							List<GdActActivityCommDTO> penaltyList = (List<GdActActivityCommDTO>)actMap.get("penaltyRule");
							if (penaltyList == null){
								penaltyList = new ArrayList<>();
							}
							penaltyList.add(com);
						}else{
							List<GdActActivityCommDTO> penaltyList = new ArrayList<>();
							penaltyList.add(com);
							actMap.put("penaltyRule", penaltyList);
						}
						break;
				}
			}
		}
	}

	private void setProductActInfo(Map<Integer, Integer> productActMap,
			Map<Integer, Integer> businessActMap,
			Map<Integer, Integer> productCate1IdMap,
			Map<Integer, Integer> productCate2IdMap,
			Map<Integer, Integer> productCate3IdMap, 
			List<ActProductDTO> productIdInActList) {
		if(productIdInActList != null && productIdInActList.size() > 0){
			for(ActProductDTO product : productIdInActList){
				//已存在按商品的活动则不需要加入
				if(!productActMap.containsKey(product.getProductId())){
					//优先查找按商铺的活动
					if(businessActMap.containsKey(product.getBusinessId())){
						productActMap.put(product.getProductId(), businessActMap.get(product.getBusinessId()));
					//查找按3级类目的活动
					}else if(productCate3IdMap.containsKey(product.getCateId())){
						productActMap.put(product.getProductId(), productCate3IdMap.get(product.getCateId()));
					//查找按2级类目的活动
					}else if(productCate2IdMap.containsKey(product.getCateId())){
						productActMap.put(product.getProductId(), productCate2IdMap.get(product.getCateId()));
					//查找按1级类目的活动
					}else if(productCate1IdMap.containsKey(product.getCateId())){
						productActMap.put(product.getProductId(), productCate1IdMap.get(product.getCateId()));
					}	
				}
			}
		}
		
	}

	/**
	 * 获取所有活动商品id
	 * @param productCate1IdMap
	 * @param productCate2IdMap
	 * @param productCate3IdMap
	 * @param businessActMap
	 * @return
	 */
	private List<ActProductDTO> getProductIdsInAct(
			Map<Integer, Integer> productCate1IdMap,
			Map<Integer, Integer> productCate2IdMap,
			Map<Integer, Integer> productCate3IdMap, 
			Map<Integer, Integer> businessActMap) {
		Map<String, Object> map = new HashMap<>();
		List<ActProductDTO> actProductList = new ArrayList<>();
		if(!productCate1IdMap.isEmpty()){
			map.put("cate1List", new ArrayList<>(productCate1IdMap.keySet()));
			map.put("cateType", 1);
			actProductList.addAll(gdActActivityService.getActivityProductInfo(map));
		}
		if(!productCate2IdMap.isEmpty()){
			map.clear();
			map.put("cate2List", new ArrayList<>(productCate2IdMap.keySet()));
			map.put("cateType", 2);
			actProductList.addAll(gdActActivityService.getActivityProductInfo(map));
		}
		if(!productCate3IdMap.isEmpty()){
			map.clear();
			map.put("cate3List", new ArrayList<>(productCate3IdMap.keySet()));
			map.put("cateType", 3);
			actProductList.addAll(gdActActivityService.getActivityProductInfo(map));
		}
		if(!businessActMap.isEmpty()){
			map.clear();
			map.put("bIdList", new ArrayList<>(businessActMap.keySet()));
			map.put("cateType", 0);
			actProductList.addAll(gdActActivityService.getActivityProductInfo(map));
		}
		return actProductList;
	}

	@Override
	public List<GdOrderActivityResultDTO> batchQueryOrderActivty(
			List<GdOrderActivityQueryDTO> queryList) throws Exception {
		List<GdOrderActivityResultDTO> resultList = new ArrayList<>();
		for(GdOrderActivityQueryDTO queryDTO : queryList){
			resultList.add(queryOrderActivty(queryDTO));
		}
		return resultList;
	}

	@Override
	public boolean deleteCach(int type) {
		logger.info("Deleting activity cache...");
		//获取活动信息
//		GdActivityRedisDTO activityRedisDTO = redisClient.get(RedisKey.GD_ACTIVITY.value);
//		if(activityRedisDTO != null){
//			try {
//				//清除缓存类型 0全部 1刷卡补贴，2市场，3平台，4预付款违约金，5物流
//				switch(type){
//				case 0: 
//					redisClient.del(RedisKey.GD_ACTIVITY.value);
//					break;
//				case 1: 
//					activityRedisDTO.setSubsidyInfo(getRedisDetailByType(type));
//					redisClient.set(RedisKey.GD_ACTIVITY.value, activityRedisDTO);
//					break;
//				case 2: 
//					activityRedisDTO.setMarketCommsnInfo(getRedisDetailByType(type));
//					redisClient.set(RedisKey.GD_ACTIVITY.value, activityRedisDTO);
//					break;
//				case 3: 
//					activityRedisDTO.setPlatformCommsnInfo(getRedisDetailByType(type));
//					redisClient.set(RedisKey.GD_ACTIVITY.value, activityRedisDTO);
//					break;
//				case 4: 
//					activityRedisDTO.setPrepayAmtAndPenaltyInfo(getRedisDetailByType(type));
//					redisClient.set(RedisKey.GD_ACTIVITY.value, activityRedisDTO);
//					break;
//				case 5: 
//					activityRedisDTO.setDeliveryInfo(getRedisDetailByType(type));
//					redisClient.set(RedisKey.GD_ACTIVITY.value, activityRedisDTO);
//					break;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.info("[ERROR]Delete activity cache exception: " + e.getMessage());
//				return false;
//			}
//		}
		
		return true;
	}

	@Override
	public boolean deleteCach() {
		return deleteCach(0);
	}

	@Override
	public GdProductActivityQueryDTO queryProductAct(GdProductActivityQueryDTO queryDTO) throws Exception {
		deliveryRuleHandler.doHandler(queryDTO, getActivityInfo().getDeliveryInfo());
		return queryDTO;
	}

	@Override
	public List<GdProductActivityQueryDTO> batchQueryProductAct(
			List<GdProductActivityQueryDTO> queryDtoList) {
		if(queryDtoList == null || queryDtoList.size() == 0){
			return queryDtoList;
		}
		
		try {
			for(GdProductActivityQueryDTO queryDTO : queryDtoList){
				queryProductAct(queryDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[ERROR]Batch query product act info exception: " + e.getMessage());
		}
		
		return queryDtoList;
	}

	@Override
	public GdOrderPenaltyQueryDTO queryOrderPenalty(GdOrderPenaltyQueryDTO queryDTO)
			throws Exception {
		logger.info("Penalty query info: " + queryDTO.toString());
		GdActivityRedisDTO activityRedisDTO = new GdActivityRedisDTO();
		activityRedisDTO.setPrepayAmtAndPenaltyInfo(getActivityInfo().getPrepayAmtAndPenaltyInfo());
		
		GdOrderActivityQueryDTO queryParamDTO = new GdOrderActivityQueryDTO();
		queryParamDTO.setBusinessId(queryDTO.getBusinessId());
		queryParamDTO.setBuyerId(queryDTO.getBuyerId());
		queryParamDTO.setSellerId(queryDTO.getSellerId());
		queryParamDTO.setMarketId(queryDTO.getMarketId());
		queryParamDTO.setPayAmount(queryDTO.getPayAmount());
		queryParamDTO.setOrderAmount(queryDTO.getOrderAmount());
		queryParamDTO.setProductList(queryDTO.getProductList());
		
		//设置改价后商品总金额
		if(queryParamDTO.getPayAmount().compareTo(queryParamDTO.getOrderAmount()) != 0){
			setProductAmount(queryParamDTO);
		}
		
		//如果有买家
		if(queryParamDTO.getBuyerId() != null && queryParamDTO.getProductList() != null && queryParamDTO.getProductList().size() > 0){
			logger.info("Buyer id " + queryParamDTO.getBuyerId());
			boolean hasPenalty = queryDTO.isHasPenalty();
			queryDTO.setPrepayAmt(0D);
			for(GdProductActInfoDTO productInfo : queryParamDTO.getProductList()){
				if(productInfo.getActInfo() != null){
					prepayAndPenaltyRuleHandler.doHandler(productInfo, productInfo.getActInfo(), activityRedisDTO, queryParamDTO, true);
					queryDTO.setSellerPenalty(MathUtil.add(queryDTO.getSellerPenalty(), productInfo.getActInfo().getSellerPenalty()));
					queryDTO.setCompanyPenalty(MathUtil.add(queryDTO.getCompanyPenalty(), productInfo.getActInfo().getCompanyPenalty()));
					queryDTO.setPlatPenalty(MathUtil.add(queryDTO.getPlatPenalty(), productInfo.getActInfo().getPlatPenalty()));
					queryDTO.setHasPenalty(hasPenalty |= productInfo.getActInfo().isHasPenalty());
					queryDTO.setPrepayAmt(MathUtil.add(queryDTO.getPrepayAmt(), productInfo.getActInfo().getPrepayAmt()));
				}
			}
		}
		
		logger.info("Penalty query result info: " + queryDTO.toString());
		return queryDTO;
	}

	@Override
	public ActActivityIntegralDTO queryActActivityIntegral(Map<String, Object> pareMap) throws Exception {
	
		List<GdActActivityUserRuleEntity> gdUserRuleEntitys=gdActActivityUserRuleService.queryByMarketId(pareMap);
		//调用要保证userId不能为空的
		Long memberId= pareMap.get("memberId")==null?null:Long.valueOf(pareMap.get("memberId").toString());//获取用户id,如果用户id为空
		
		double userIntegralRate=0.0; //如果用户id跟传过来的用户id相同，则存这个用户的费率
		double actActivityIntegralRate=0.0; //如果找到了当前活动下面有 使用全部用户规则的设置的，则将倍率存在这里
		ActActivityIntegralDTO activityIntegralDTO=null;
		if (null!=gdUserRuleEntitys&&gdUserRuleEntitys.size()>0) {
			for (GdActActivityUserRuleEntity userRuleEntity : gdUserRuleEntitys) {
				if (memberId.compareTo(userRuleEntity.getReferId())==0) {
					pareMap.put("activityId", userRuleEntity.getActivityId()); //存下活动id，为了方便根据活动id查询费率等信息
					pareMap.put("memberId",memberId);
					userIntegralRate=userRuleEntity.getIntegralRate();
					break;
				}
				if (userRuleEntity.getReferId()==0) {
					pareMap.put("activityId", userRuleEntity.getActivityId()); 
					pareMap.put("memberId","0");
					actActivityIntegralRate=userRuleEntity.getIntegralRate();
				} 
			}
			if (null!=pareMap.get("activityId")) {
				activityIntegralDTO=actActivityCommService.getIntegralByUserId(pareMap);
			}
			
		}
		
		if (null!=activityIntegralDTO) {
			activityIntegralDTO.setIntegralRate(actActivityIntegralRate);
			if (userIntegralRate>0) {
				activityIntegralDTO.setIntegralRate(userIntegralRate);
			}
		}
		
		
		return activityIntegralDTO;
	}
	
}
