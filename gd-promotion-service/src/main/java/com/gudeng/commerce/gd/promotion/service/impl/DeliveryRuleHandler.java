package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.common.BaseRuleHandler;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDistributionModeDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityInfoRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.service.CountContextService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 配送方式规则处理
 * @author TerryZhang
 */
public class DeliveryRuleHandler extends BaseRuleHandler {
	private static final GdLogger logger = GdLoggerFactory.getLogger(DeliveryRuleHandler.class);

	@Autowired
	private CountContextService countContextService;
	
	@Override
	public void doHandler(GdOrderActivityQueryDTO queryDTO, 
			GdOrderActivityDetailDTO actInfo, GdActivityRedisDTO activityRedisDTO, 
			boolean isBuyer)
			throws Exception {
		logger.info("No delivery rules");
		//默认带自提
		List<Integer> modeList = new ArrayList<>(Arrays.asList(0));
		actInfo.setDistributeModeList(modeList);	
		if(getNextHandler() != null){
			getNextHandler().doHandler(queryDTO, actInfo, activityRedisDTO, isBuyer);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doHandler(GdProductActInfoDTO productInfo,
			GdOrderActivityDetailDTO productActInfo,
			GdActivityRedisDTO activityRedisDTO,
			GdOrderActivityQueryDTO queryDTO, boolean isBuyer)
			throws Exception {
		int actType = 5;
		GdActivityInfoRedisDTO deliveryInfo = activityRedisDTO.getDeliveryInfo();
		Integer productActId = checkProductActInfo(queryDTO, deliveryInfo, productInfo, actType, isBuyer);
		if(productActId != null){
			logger.info("Product id: " +productInfo.getProductId()+ ", delivery actId:  " + productActId);
			//设置商品活动信息
//			setProductActInfo(productActInfo, productInfo.getProductId(), productActId, actType);
			Map<String, Object> productRuleMap = deliveryInfo.getActRuleMap().get(productActId);
			if(productRuleMap != null && !productRuleMap.isEmpty()){
				List<GdActActivityDistributionModeDTO> comList = null;
				//设置物流规则
				logger.info("Getting delivery info.");
				comList = (List<GdActActivityDistributionModeDTO>) productRuleMap.get("deliveryRule");
				if(comList != null && comList.size() > 0){
					logger.info("Has product delivery rule.");
					for(GdActActivityDistributionModeDTO com : comList){
						productActInfo.getDistributeModeList().add(com.getType());
					}
				}
			}
		}
		
		if(getNextHandler() != null){
			getNextHandler().doHandler(productInfo, productActInfo, activityRedisDTO, queryDTO, isBuyer);
		}
	}

	@SuppressWarnings("unchecked")
	public void doHandler(GdProductActivityQueryDTO queryDTO, GdActivityInfoRedisDTO deliverRedisDTO) throws Exception {
		logger.info("Product query info: " + queryDTO.toString());
		Integer productActId = checkProductActInfo(queryDTO, deliverRedisDTO);
		if(productActId != null){
			logger.info("Product id: " +queryDTO.getProductId()+ ", delivery actId:  " + productActId);
			//设置商品活动信息
			Map<String, Object> productRuleMap = deliverRedisDTO.getActRuleMap().get(productActId);
			if(productRuleMap != null){
				List<GdActActivityDistributionModeDTO> comList = null;
				//设置物流规则
				logger.info("Getting delivery info.");
				comList = (List<GdActActivityDistributionModeDTO>) productRuleMap.get("deliveryRule");
				if(comList != null && comList.size() > 0){
					logger.info("Has product delivery rule.");
					for(GdActActivityDistributionModeDTO com : comList){
						queryDTO.getDistributeModeList().add(com.getType());
						queryDTO.setHasPlatMode(com.getType() == 1 ? true : queryDTO.isHasPlatMode());
					}
				}
			}
		}
		
		logger.info("Product query result info: " + queryDTO.toString());
	}
	
	public Integer checkProductActInfo(GdProductActivityQueryDTO queryDTO, 
			GdActivityInfoRedisDTO actInfoDTO) throws Exception{
		Integer actId = null;
		if(actInfoDTO != null){
			Integer productActId = null;
			//查找最新的活动
			if(productActId == null && actInfoDTO.getProductActMap() != null){
				productActId = actInfoDTO.getProductActMap().get(queryDTO.getProductId());
			}
			//没有最新活动则查找有无市场类型活动
			if(productActId == null && actInfoDTO.getNoLimitSellerActIdMap() != null){
				productActId = actInfoDTO.getNoLimitSellerActIdMap().get(queryDTO.getMarketId());
			}
			
			if(queryDTO.getBuyerId() != null){
				List<Integer> buyerActIdList = new ArrayList<>();
				//是否已存在限制买家的活动
				if(actInfoDTO.getBuyerActMap()!=null 
						&& actInfoDTO.getBuyerActMap().containsKey(queryDTO.getBuyerId())){
					logger.info("Has limit buyer act infos: " + actInfoDTO.getBuyerActMap().get(queryDTO.getBuyerId()));
					buyerActIdList.addAll(actInfoDTO.getBuyerActMap().get(queryDTO.getBuyerId()));
				}
				
				//是否已存在不限制买家的活动
				if(actInfoDTO.getNoLimitBuyerActIdMap() != null){
					List<Integer> noLimitBuyerActIdList = actInfoDTO.getNoLimitBuyerActIdMap().get(queryDTO.getMarketId());
					if(noLimitBuyerActIdList != null && noLimitBuyerActIdList.size() > 0){
						buyerActIdList.addAll(noLimitBuyerActIdList);
						logger.info("No limit buyer act id: " + noLimitBuyerActIdList);
					}
				}
				
				//判断买家是否有活动
				if(productActId != null && buyerActIdList.size() > 0){
					for(Integer id : buyerActIdList){
						if(id.intValue() == productActId.intValue()){
							actId = productActId;
						}
					}
				}
			}
			
			if(productActId != null){
				//判断卖家活动是否有指定买家
				if(actInfoDTO.getActBuyerMap() != null 
						&& actInfoDTO.getActBuyerMap().get(productActId) != null
						&& actInfoDTO.getActBuyerMap().get(productActId).size() > 0){
					logger.info("Limit buyer ids: " + actInfoDTO.getActBuyerMap().get(productActId));
					if(queryDTO.getBuyerId() != null){
						for(Integer buyerId : actInfoDTO.getActBuyerMap().get(productActId)){
							if(buyerId.intValue() == queryDTO.getBuyerId().intValue()){
								actId = productActId;
								break;
							}
						}
					}
				}else{
					actId = productActId;
				}
			}
			
			//判断活动是否过期(未下单)
			if(actId != null && actInfoDTO.getActInfoMap() != null 
					&& isActEnd(actInfoDTO.getActInfoMap().get(productActId))){
				logger.info("Product act is already outdate, actId: " + productActId);
				actId = null;
			}
		}
		return actId;
	}
}
