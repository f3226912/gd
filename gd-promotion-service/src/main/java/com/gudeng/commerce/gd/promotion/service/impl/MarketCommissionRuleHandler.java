package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.common.BaseRuleHandler;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityInfoRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActivityRedisDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityDetailDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;
import com.gudeng.commerce.gd.promotion.service.CountContextService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 市场佣金处理类
 * @author TerryZhang
 */
public class MarketCommissionRuleHandler extends BaseRuleHandler {
	
	private static final GdLogger logger = GdLoggerFactory.getLogger(MarketCommissionRuleHandler.class);
	
	@Autowired
	private CountContextService countContextService;

	@Override
	public void doHandler(GdOrderActivityQueryDTO queryDTO, 
			GdOrderActivityDetailDTO actInfo, GdActivityRedisDTO activityRedisDTO, 
			boolean isBuyer) throws Exception {
		int actType = 2;
		GdActivityInfoRedisDTO marketCommsnInfo = activityRedisDTO.getMarketCommsnInfo();
		Integer businessActId = checkBusinessActInfo(queryDTO, marketCommsnInfo, actType, isBuyer);
		if(businessActId != null){
			logger.info("Business id: " +queryDTO.getBusinessId()+ ", market actId:  " + businessActId);
			//设置商铺市场佣金活动信息
			setProductActInfo(actInfo, queryDTO.getBusinessId(), businessActId, actType);
			Map<String, Object> marketCommsnRuleMap = marketCommsnInfo.getActRuleMap().get(businessActId);
			if(marketCommsnRuleMap != null && !marketCommsnRuleMap.isEmpty()){
				//设置商铺市场佣金规则
				GdActActivityCommDTO com = (GdActActivityCommDTO) marketCommsnRuleMap.get("sellerMarketCommsnRule");
				if(com != null){
					logger.info("Has market commission rule.");
					actInfo.setHasSellerCommsn(true);
					actInfo.setMarketCommision(countContextService.getCommission(com, queryDTO, null));
				}
			}
		}
		
		if(getNextHandler() != null){
			getNextHandler().doHandler(queryDTO, actInfo, activityRedisDTO, isBuyer);
		}
	}

	@Override
	public void doHandler(GdProductActInfoDTO productInfo,
			GdOrderActivityDetailDTO productActInfo,
			GdActivityRedisDTO activityRedisDTO,
			GdOrderActivityQueryDTO queryDTO, boolean isBuyer) throws Exception {
		int actType = 2;
		GdActivityInfoRedisDTO marketCommsnInfo = activityRedisDTO.getMarketCommsnInfo();
		Integer productActId = checkProductActInfo(queryDTO, marketCommsnInfo, productInfo, actType, isBuyer);
		if(productActId != null){
			logger.info("Product id: " +productInfo.getProductId()+ ", market actId:  " + productActId);
			//设置商品市场佣金活动信息
			if(!isBuyer){
				setProductActInfo(productActInfo, productInfo.getProductId(), productActId, actType);
			}
			
			Map<String, Object> productRuleMap = marketCommsnInfo.getActRuleMap().get(productActId);
			if(productRuleMap != null && !productRuleMap.isEmpty()){
				GdActActivityCommDTO com = null;
				if(isBuyer){
					//设置买家市场佣金规则
					logger.info("Getting buyer market commission info.");
					com = (GdActActivityCommDTO) productRuleMap.get("buyerMarketCommsnRule");
				}else{
					//设置卖家市场佣金规则
					logger.info("Getting seller market commission info.");
					com = (GdActActivityCommDTO) productRuleMap.get("sellerMarketCommsnRule");
				}
				
				if(com != null){
					if(isBuyer){
						productActInfo.setHasBuyerCommsn(true);
					}else{
						productActInfo.setHasSellerCommsn(true);
					}
					logger.info("Has product market commission rule.");
					productActInfo.setMarketCommision(countContextService.getCommission(com, null, productInfo));
				}
			}
		}
		
		if(getNextHandler() != null){
			getNextHandler().doHandler(productInfo, productActInfo, activityRedisDTO, queryDTO, isBuyer);
		}
	}
}
