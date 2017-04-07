package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.List;
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
 * 预付款和违约金规则处理
 * @author TerryZhang
 */
public class PrepayAndPenaltyRuleHandler extends BaseRuleHandler {
	private static final GdLogger logger = GdLoggerFactory.getLogger(PrepayAndPenaltyRuleHandler.class);

	@Autowired
	private CountContextService countContextService;
	
	@Override
	public void doHandler(GdOrderActivityQueryDTO queryDTO, 
			GdOrderActivityDetailDTO actInfo, GdActivityRedisDTO activityRedisDTO, 
			boolean isBuyer)
			throws Exception {
		logger.info("No prepayAmt and penalty rules");
		
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
		int actType = 4;
		GdActivityInfoRedisDTO prepayAndPanltyInfo = activityRedisDTO.getPrepayAmtAndPenaltyInfo();
		Integer productActId = checkProductActInfo(queryDTO, prepayAndPanltyInfo, productInfo, actType, isBuyer);
		if(productActId != null){
			logger.info("Product id: " +productInfo.getProductId()+ ", prepay actId:  " + productActId);
			//设置商品活动信息
			if(!isBuyer){
				setProductActInfo(productActInfo, productInfo.getProductId(), productActId, actType);
			}
			Map<String, Object> productRuleMap = prepayAndPanltyInfo.getActRuleMap().get(productActId);
			if(productRuleMap != null && !productRuleMap.isEmpty()){
				GdActActivityCommDTO prepayAmtRule = null;
				List<GdActActivityCommDTO> penaltyRuleList = null;
				//设置预付款规则
				logger.info("Getting prepay info.");
				 prepayAmtRule = (GdActActivityCommDTO) productRuleMap.get("prepayAmtRule");
				//设置违约金规则
				logger.info("Getting penalty info.");
				penaltyRuleList = (List<GdActActivityCommDTO>) productRuleMap.get("penaltyRule");
				
				if(prepayAmtRule != null){
					logger.info("Has product prepay rule.");
					productActInfo.setPrepayAmt(countContextService.getCommission(prepayAmtRule, null, productInfo));
					productActInfo.setHasPrepayAmt(true);
				}
				
				if(penaltyRuleList != null && penaltyRuleList.size() > 0){
					logger.info("Has product penalty rule.");
					for(GdActActivityCommDTO com : penaltyRuleList){
						GdProductActInfoDTO tmpProductActInfo = new GdProductActInfoDTO();
						tmpProductActInfo.setProductAmount(productActInfo.getPrepayAmt());
						//7买家给平台违约金，8买家给卖家违约金，9买家给物流公司违约金
						switch(com.getCommRuleType()){
						case "7": 
							productActInfo.setPlatPenalty(countContextService.getCommission(com, null, tmpProductActInfo));
							break;
						case "8": 
							productActInfo.setSellerPenalty(countContextService.getCommission(com, null, tmpProductActInfo));
							break;
						case "9": 
							productActInfo.setCompanyPenalty(countContextService.getCommission(com, null, tmpProductActInfo));
							break;
						}
						productActInfo.setHasPenalty(true);
					}
				}
			}
		}
		
		if(getNextHandler() != null){
			getNextHandler().doHandler(productInfo, productActInfo, activityRedisDTO, queryDTO, isBuyer);
		}
	}
}
