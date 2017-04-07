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
 * 平台佣金规则处理
 * @author TerryZhang
 */
public class PlatFormCommissionRuleHandler extends BaseRuleHandler{
	private static final GdLogger logger = GdLoggerFactory.getLogger(PlatFormCommissionRuleHandler.class);

	@Autowired
	private CountContextService countContextService;
	
	@Override
	public void doHandler(GdOrderActivityQueryDTO queryDTO, 
			GdOrderActivityDetailDTO actInfo, GdActivityRedisDTO activityRedisDTO, 
			boolean isBuyer) throws Exception {
		int actType = 3;
		GdActivityInfoRedisDTO platCommsnInfo = activityRedisDTO.getPlatformCommsnInfo();
		Integer businessActId = checkBusinessActInfo(queryDTO, platCommsnInfo, actType, isBuyer);
		if(businessActId != null){
			logger.info("Business id: " +queryDTO.getBusinessId()+ ", plat actId:  " + businessActId);
			//设置商铺平台佣金活动信息
			if(!isBuyer){
				setProductActInfo(actInfo, queryDTO.getBusinessId(), businessActId, actType);
			}
			Map<String, Object> platformCommsnRuleMap = platCommsnInfo.getActRuleMap().get(businessActId);
			if(platformCommsnRuleMap != null && !platformCommsnRuleMap.isEmpty()){
				//设置商铺平台佣金规则
				GdActActivityCommDTO com = (GdActActivityCommDTO) platformCommsnRuleMap.get("sellerPlatformCommsnRule");
				if(com != null){
					logger.info("Has platform commission rule.");
					actInfo.setHasSellerCommsn(true);
					actInfo.setPlatCommision(countContextService.getCommission(com, queryDTO, null));
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
			GdOrderActivityQueryDTO queryDTO, boolean isBuyer)
			throws Exception {
		int actType = 3;
		GdActivityInfoRedisDTO platCommsnInfo = activityRedisDTO.getPlatformCommsnInfo();
		Integer productActId = checkProductActInfo(queryDTO, platCommsnInfo, productInfo, actType, isBuyer);
		if(productActId != null){
			logger.info("Product id: " +productInfo.getProductId()+ ", plat actId:  " + productActId);
			//设置商品平台佣金活动信息
			if(!isBuyer){
				setProductActInfo(productActInfo, productInfo.getProductId(), productActId, actType);
			}
			Map<String, Object> productRuleMap = platCommsnInfo.getActRuleMap().get(productActId);
			if(productRuleMap != null && productRuleMap != null){
				GdActActivityCommDTO com = null;
				if(isBuyer){
					//设置买家平台佣金规则
					logger.info("Getting buyer platform commission info.");
					com = (GdActActivityCommDTO) productRuleMap.get("buyerPlatformCommsnRule");
				}else{
					//设置卖家平台佣金规则
					logger.info("Getting seller platform commission info.");
					com = (GdActActivityCommDTO) productRuleMap.get("sellerPlatformCommsnRule");
				}
				
				if(com != null){
					if(isBuyer){
						productActInfo.setHasBuyerCommsn(true);
					}else{
						productActInfo.setHasSellerCommsn(true);
					}
					logger.info("Has product platform commission rule.");
					productActInfo.setPlatCommision(countContextService.getCommission(com, null, productInfo));
				}
			}
		}
		
		if(getNextHandler() != null){
			getNextHandler().doHandler(productInfo, productActInfo, activityRedisDTO, queryDTO, isBuyer);
		}
	}

}
